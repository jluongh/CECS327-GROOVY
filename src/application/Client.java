package application;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.*;
import data.constants.Net;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import api.*;
import data.models.*;
import services.*;
import api.audio.AudioPlayer;

public class Client {
	private static final String hostname = "localhost";

	public static void main(String[] args) throws IOException {
		PlayerController pc = new PlayerController();

		// get a datagram socket
		DatagramSocket socket = new DatagramSocket();
		socket.setSoTimeout(5000);
		socket.setReceiveBufferSize(60011 * 30 * 100);
		// send request
		byte[] message = pc.LoadSong(1);
		InetAddress address = InetAddress.getByName(hostname);
		DatagramPacket request = new DatagramPacket(message, message.length, address, Net.PORT);
		socket.send(request);

//		while (true) {						
			// get reply
			byte[] buffer = new byte[1024 * 1000];
			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
			socket.receive(reply);

			// display response
//			String received = new String(reply.getData(), 0, reply.getLength());
//			System.out.println(received);
			
			// read response
			// 0 = request, 1 = reply
			ByteBuffer wrapped = ByteBuffer.wrap(reply.getData(), 0, 4);
			int messageTypeReceive = wrapped.getInt();
			wrapped = ByteBuffer.wrap(reply.getData(), 4, 4);
			int requestId = wrapped.getInt();
			byte[] fragment = Arrays.copyOfRange(reply.getData(), 8, reply.getLength());
						
			if (messageTypeReceive == 1) {
				System.out.println(requestId);
				switch (requestId) {
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
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					
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
							reply = new DatagramPacket(buffer, buffer.length);
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
					    AudioInputStream audioStream =new AudioInputStream(bis, audioFormat, received1.length);
					    if (audioStream != null) {
							AudioPlayer.loadStream("1", audioStream);
							AudioPlayer.play("1", false);
						}
					}	
					
					break;
				}
				
			
//			}
		}


//		socket.close();
	}

}
