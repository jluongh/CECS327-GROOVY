package application;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class ClientHandler extends Thread {
	
    final DatagramSocket socket; 
    
	public ClientHandler(DatagramSocket socket) {
		this.socket = socket;
	}
	
	public void run() {
		while (true) {
			try {
				byte[] message = new byte[4000];
				// receive request
				DatagramPacket request = new DatagramPacket(message, message.length);
				socket.receive(request);

				// display request
				String received = new String(request.getData(), 0, request.getLength());
				System.out.println("Server:\n" + received);

				if (received != null) {
					// read header
					// 0 = request, 1 = reply
					ByteBuffer wrapped = ByteBuffer.wrap(request.getData(), 0, 4);
					int messageType = wrapped.getInt();
					wrapped = ByteBuffer.wrap(request.getData(), 4, 4);
					int requestId = wrapped.getInt();
					
					if (messageType == 0) {
						byte[] buffer = null;
						
						switch(requestId) {
						// Loading User Profile
						case 0:
							buffer = "Test".getBytes("UTF-8");
							break;
//						case 1:
//							buffer = AddSongToPlaylist(received);
//							break;
						}

						if (buffer != null) {
							InetAddress address = request.getAddress();
							int port = request.getPort();
							DatagramPacket reply = new DatagramPacket(buffer, buffer.length, address, port);
							socket.send(reply);
						}
					}
				}

				System.out.println("Server: Done");

			} catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}
}