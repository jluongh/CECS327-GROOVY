package api;

import java.io.*;
import java.net.*;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;

import javax.sound.sampled.*;

import com.google.gson.*;

import data.constants.Net;
import data.constants.Packet;
import data.models.*;

public class PlayerController {

	private static final String HOST = "localhost";

	private DatagramSocket socket;
	private SourceDataLine sdl;
	private AudioFormat audioFormat;
	private DataLine.Info info;

	public boolean playing;
	public int pos;

	public int current;

	private SongQueue sq;

	public PlayerController(DatagramSocket socket) {
		this.socket = socket;
		sq = new SongQueue();
		audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
		info = new DataLine.Info(SourceDataLine.class, audioFormat);
		try {
			sdl = (SourceDataLine) AudioSystem.getLine(info);
			sdl.open();
			sdl.start();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

	}

	public void loadSongs(List<Song> songs) {
		sq.setSongs(songs);
		current = 0;
	}

	public void playQueue() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (current < sq.getSongs().size()) {
					System.out.println("Current: " + current);
					Song song = sq.getSongs().get(current);
					try {
						playing = true;
						playSong(song.getSongID());
						current++;						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	public void playSong(int songID) throws IOException {
		Gson gson = new GsonBuilder().setLenient().create();
		Message send = new Message();
		send.messageType = Packet.REQUEST;
		send.objectID = songID;
		send.requestID = Packet.REQUEST_ID_BYTECOUNT;

		String sendString = new Gson().toJson(send);
		byte[] sendByte = sendString.getBytes();
		// send request
		InetAddress address = InetAddress.getByName(HOST);
		DatagramPacket request = new DatagramPacket(sendByte, sendByte.length, address, Net.PORT);
		socket.send(request);

		// get reply
		byte[] buffer = new byte[1024 * 1000];
		DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
		socket.receive(reply);

		// read response
		Message msg = new Message();
		String bitString = new String(reply.getData(), 0, reply.getLength());
		msg = gson.fromJson(bitString, Message.class);

		if (msg.messageType == Packet.REPLY) {
			switch (msg.requestID) {
			case Packet.REQUEST_ID_BYTECOUNT:
				int count = msg.count;
				int offset = 0;

				try {
					sdl = (SourceDataLine) AudioSystem.getLine(info);
					sdl.open();
					sdl.start();

					while (offset < count && playing) {
						msg = new Message();
						msg.messageType = Packet.REQUEST;
						msg.requestID = Packet.REQUEST_ID_LOADSONG;
						msg.offset = offset;
						msg.objectID = songID;
						msg.count = count;

						String sendJson = gson.toJson(msg);
						byte[] message = sendJson.getBytes();
						request = new DatagramPacket(message, message.length, address, Net.PORT);
						socket.send(request);

						// Get reply back and play
						DatagramPacket reply1 = new DatagramPacket(buffer, buffer.length);
						socket.receive(reply1);

						// Translate it back into a message
						msg = new Message();
						bitString = new String(reply1.getData(), 0, reply1.getLength());
						msg = gson.fromJson(bitString, Message.class);

						ByteArrayInputStream bis = new ByteArrayInputStream(msg.fragment);
						AudioInputStream audioStream = new AudioInputStream(bis, audioFormat, msg.fragment.length);

						int bytesRead;
						try {
							if (!sdl.isRunning() && sdl.available() > Packet.BYTESIZE) {
								sdl.start();
							}
							if ((bytesRead = audioStream.read(msg.fragment, 0, msg.fragment.length)) != -1) {
								sdl.write(msg.fragment, 0, bytesRead);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
						offset++;
					}
				} catch (LineUnavailableException e1) {
					e1.printStackTrace();
				}

			}
		}
		sdl.close();
	}

	/**
	 * Plays the next song
	 * 
	 * @return next
	 * @throws IOException
	 */
	public void next() {
		if (!playing) {
			current++;
		}
		reset();
	}
	
	public void previous() {
		if (playing) {
			current-=2;
		}
		reset();
	}

	public void reset() {
		playing = false;
		sdl.stop();
		sdl.flush();
	}

	public void pause() {
		playing = false;
		sdl.stop();
	}

	public void resume() {
		sdl.start();
	}
}
