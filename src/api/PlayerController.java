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
	private FloatControl gainControl;
;
	public boolean playing;
	public boolean repeat;
	public Song current;

	private SongQueue sq;
	private Stack<Song> st;
	Random rand = new Random();


	public PlayerController(DatagramSocket socket) {
		this.socket = socket;
		sq = new SongQueue();
		audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
		info = new DataLine.Info(SourceDataLine.class, audioFormat);
		try {
			sdl = (SourceDataLine) AudioSystem.getLine(info);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public void loadSongs(List<Song> songs) {
		sq.setSongs(songs);
	}

	public void playQueue() {
		new Thread(new Runnable() {
			@Override
			public void run() {
//				while (current < sq.getSongs().size()) {
//					Song song = sq.getSongs().get(current);
//					try {
//						reset();
//						playSong(song.getSongID());
//						current++;						
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//					
//					if (repeat) {
//						current = 0;
//					}
//				}
				while(!sq.getSongs().isEmpty()) {
					Song song;	
					if (repeat) {
						song = current;
					}
					else {
						song = next();
					}
					try {
						reset();
						playSong(song.getSongID());						
					} catch (Exception e) {
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
					if (!sdl.isOpen()) {
						sdl.open();
						sdl.start();
					}
					playing = true;
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
							if (sdl.isRunning()) {
								System.out.println(sdl.available());
								if (sdl.available() > 80000) {
									sdl.stop();
								}
							}
							else {
								if (sdl.available() < 80000) {
									sdl.start();
									System.out.println("hello2");

								}
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
	 * @return current 
	 */
	public Song next() {
		if (!playing) {
			current = sq.getSongs().remove(0);
			st.push(current);
		}
//		if (!playing) {
//			current++;
//		}
		reset();
		return current;
	}
	
	/**
	 * Plays the previous song
	 * @return current
	 */
	public Song previous() {
		if (playing) {
			current = st.pop();
		}
//		if (playing) {
//			current-=2;
//		}
		reset();
		return current;
	}

	/**
	 * Resets dataline
	 * 
	 */
	public void reset() {
		playing = false;
		sdl.stop();
		sdl.flush();
	}

	/**
	 * Pauses a song
	 * 
	 */
	public void pause() {
		playing = false;
		sdl.stop();
	}

	/**
	 * Resume song
	 * 
	 */
	public void resume() {
		sdl.start();
	}
	
	/**
	 * Shuffle the songs in the queue
	 * @param songQ - {SongQueue} the queue of songs
	 */
	public void shuffle() {
	    for (int i = 0; i < sq.getSongs().size(); i++) {
	    	int change = i + rand.nextInt(sq.getSongs().size() - i);
	        swap(sq, i, change);
	    }
	}
	
	/**
	 * Swapping queue positions for two songs
	 * @param songQ - {SongQueue} the queue of songs
	 * @param i - {int} position of song 1 
	 * @param change - {int} position of song 2 that was randomly selected
	 */
	public void swap(SongQueue songQ, int i, int change) {
		Song song1 = songQ.getSongs().get(i);
		Song song2 = songQ.getSongs().get(change);
		songQ.getSongs().set(i, song2);
		songQ.getSongs().set(change, song1);
	}
	
	/**
	 * Repeat the current song
	 * @return current
	 */
	public void repeat(boolean flag) {
		repeat = flag;
	}
	
	/**
	 * Get the volume
	 * @return gainControl
	 */

	public float getVolume() {
	    gainControl = (FloatControl) sdl.getControl(FloatControl.Type.MASTER_GAIN);        
	    return (float) Math.pow(10f, gainControl.getValue() / 20f);
	}

	/**
	 * Changing the volume
	 * @param volume - {float} the sound 
	 */
	public void setVolume(float volume) {
	    if (volume < 0f || volume > 1f)
	        throw new IllegalArgumentException("Volume not valid: " + volume);
	    gainControl = (FloatControl) sdl.getControl(FloatControl.Type.MASTER_GAIN);        
	    gainControl.setValue(20f * (float) Math.log10(volume));
	}
}
