package api;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sound.sampled.*;

import com.google.gson.Gson;

import data.models.Song;


public class PlayerController {
	
	final static int REQUEST = 0;
	final static int REPLY = 1;
	final static int REQUEST_ID = 4;
	
	public byte[] LoadSong(int songID) throws IOException {
		List<Song> songs = new ArrayList<Song>();
		Song song = new Song();
		song.setSongID(songID);
		songs.add(song);
		String songsJson = new Gson().toJson(songs);
		
		byte[] messageType = ByteBuffer.allocate(4).putInt(REQUEST).array();
		byte[] requestId = ByteBuffer.allocate(4).putInt(REQUEST_ID).array();
		byte[] fragment = songsJson.getBytes();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(messageType);
		baos.write(requestId);
		baos.write(fragment);

		byte[] send = baos.toByteArray();
		
		return send;
	}
	
	public AudioInputStream LoadSong(int songID) throws IOException {
		// get a datagram socket
		DatagramSocket socket = new DatagramSocket();
		socket.setSoTimeout(1000 * 5);
		socket.setReceiveBufferSize(60011 * 30 * 100);

		List<Song> songsSend = new ArrayList<Song>();
		Song song = new Song();
		song.setSongID(songID);
		songsSend.add(song);

		byte[] received1 = null;

		int offset = 0, count = 0;
		String songJson = new Gson().toJson(songsSend);
		System.out.println(songJson);
		// convert json to bytes
		byte[] buf = new byte[1024 * 1000 * 50];
		buf = songJson.getBytes();

		InetAddress address = InetAddress.getByName("localhost");
		DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4446);
		System.out.println("Sent request");
		socket.send(packet);
		System.out.println("Waiting for response");

		boolean receiving = true;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		int receivedCount = 0;
		try {
			while (receiving) {
				buf = new byte[1024 * 1000 * 50];
				// get response
				packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);

				System.out.println("Client: Received from server");

				ByteArrayInputStream bais = new ByteArrayInputStream(packet.getData());

				System.out.println("Packet size: " + packet.getLength());
				int id = bais.read(packet.getData(), 0, 4);
				ByteBuffer wrapped = ByteBuffer.wrap(packet.getData(), 4, 4); // big-endian by default
				offset = wrapped.getInt();
				System.out.println("Offset: " + offset);
				wrapped = ByteBuffer.wrap(packet.getData(), 8, 4); // big-endian by default

				count = wrapped.getInt();
				System.out.println("Count: " + count);

				byte[] data = Arrays.copyOfRange(packet.getData(), 12, packet.getLength());
				if (offset < count) {
					System.out.println("Writing fragment to bytes with length: " + data.length);
					baos.write(data);
					receivedCount++;
				} else if (offset == count) {
					// if the packet is empty or null, then the server is done sending?
					receiving = false;
					System.out.println("Receiving done");
				}
			}

			received1 = baos.toByteArray();

		} catch (SocketTimeoutException e) {

		}
		
		socket.close();

		int percent = (int) (((double) receivedCount / (double) count) * 100);
		System.out.println(percent);
		if (percent > 80) {
			ByteArrayInputStream bis = new ByteArrayInputStream(received1);
		    AudioFormat audioFormat = new AudioFormat(44100, 16, 2, true, false);
		    AudioInputStream audioStream =new AudioInputStream(bis, audioFormat, received1.length);
		    AudioInputStream ais = audioStream
			if (ais != null) {
				AudioPlayer.loadStream("1", ais);
				AudioPlayer.play("1", false);
			}
		}
		else {
			return null;
		}
	}
	

	
}
