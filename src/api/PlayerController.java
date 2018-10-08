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

import api.audio.AudioPlayer;
import data.constants.Net;
import data.models.Song;

public class PlayerController {

	final static int REQUEST = 0;
	final static int REPLY = 1;
	final static int REQUEST_ID = 4;

	// public byte[] LoadSong(int songID) throws IOException {
	// List<Song> songs = new ArrayList<Song>();
	// Song song = new Song();
	// song.setSongID(songID);
	// songs.add(song);
	// String songsJson = new Gson().toJson(songs);
	//
	// byte[] messageType = ByteBuffer.allocate(4).putInt(REQUEST).array();
	// byte[] requestId = ByteBuffer.allocate(4).putInt(REQUEST_ID).array();
	// byte[] fragment = songsJson.getBytes();
	//
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	// baos.write(messageType);
	// baos.write(requestId);
	// baos.write(fragment);
	//
	// byte[] send = baos.toByteArray();
	//
	// return send;
	// }

	public AudioInputStream LoadSong(DatagramSocket socket, int songID) throws IOException {
		List<Song> songs = new ArrayList<Song>();
		Song song = new Song();
		song.setSongID(songID);
		songs.add(song);
		String songsJson = new Gson().toJson(songs);

		byte[] messageType = ByteBuffer.allocate(4).putInt(REQUEST).array();
		byte[] requestIdSend = ByteBuffer.allocate(4).putInt(REQUEST_ID).array();
		byte[] fragment = songsJson.getBytes();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(messageType);
		baos.write(requestIdSend);
		baos.write(fragment);

		// byte[] send = baos.toByteArray();

		// send request
		byte[] message = baos.toByteArray();
		InetAddress address = InetAddress.getByName("localhost");
		DatagramPacket request = new DatagramPacket(message, message.length, address, Net.PORT);
		socket.send(request);

		// while (true) {
		// get reply
		byte[] buffer = new byte[1024 * 1000];
		DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
		socket.receive(reply);

		// display response
		// String received = new String(reply.getData(), 0, reply.getLength());
		// System.out.println(received);

		// read response
		// 0 = request, 1 = reply
		ByteBuffer wrapped = ByteBuffer.wrap(reply.getData(), 0, 4);
		int messageTypeReceive = wrapped.getInt();
		wrapped = ByteBuffer.wrap(reply.getData(), 4, 4);
		int requestIdReceive = wrapped.getInt();
		fragment = Arrays.copyOfRange(reply.getData(), 8, reply.getLength());

		if (messageTypeReceive == 1) {
			System.out.println(requestIdReceive);
			switch (requestIdReceive) {
			// Loading User Profile
			case 0:
				buffer = "Test".getBytes("UTF-8");
				break;
			// case 1:
			// buffer = AddSongToPlaylist(received);
			// break;
			case 4:
				boolean receiving = true;
				int receivedCount = 0, count = 0;
				byte[] received1 = null;
				baos = new ByteArrayOutputStream();

				while (receiving) {
					wrapped = ByteBuffer.wrap(fragment, 0, 4); // big-endian by default
					int offset = wrapped.getInt();
					wrapped = ByteBuffer.wrap(fragment, 4, 4);
					count = wrapped.getInt();
					byte[] data = Arrays.copyOfRange(fragment, 12, fragment.length);

					System.out.println(offset + "/" + count);
					if (offset < count) {
						System.out.println("Writing fragment to bytes with length: " + data.length);
						baos.write(data);
						receivedCount++;

						socket.receive(reply);
						fragment = Arrays.copyOfRange(reply.getData(), 8, reply.getLength());

					} else if (offset == count) {
						// if the packet is empty or null, then the server is done sending?
						receiving = false;
						System.out.println("Receiving done");
					}
				}
				received1 = baos.toByteArray();

				int percent = (int) (((double) receivedCount / (double) count) * 100);
				System.out.println(percent);
				if (percent > 80) {
					ByteArrayInputStream bis = new ByteArrayInputStream(received1);
					AudioFormat audioFormat = new AudioFormat(44100, 16, 2, true, false);
					AudioInputStream audioStream = new AudioInputStream(bis, audioFormat, received1.length);
					return audioStream;
				}

				break;
			}

		}
		return null;
	}
}
