package application;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import data.models.FileEvent;
import data.models.Song;

public class ClientHandler extends Thread {

	final DatagramSocket socket;

	public ClientHandler(DatagramSocket socket) {
		this.socket = socket;
	}

	public void run() {
		while (true) {
			try {
				byte[] message = new byte[1024 * 1000];
				int sizeOfPacket = 63000;

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
					int messageTypeReceive = wrapped.getInt();
					wrapped = ByteBuffer.wrap(request.getData(), 4, 4);
					int requestId = wrapped.getInt();
					byte[] data = Arrays.copyOfRange(request.getData(), 8, request.getLength());
					if (messageTypeReceive == 0) {
						byte[] buffer = null;

						switch (requestId) {
						// Loading User Profile
						case 0:
							buffer = "Test".getBytes("UTF-8");
							break;
//						case 0:
//							buffer = LoadUser(received);
//							break;
//						case 1:
//							buffer = LoadUserProfile(received);
//							break;
//						case 2:
//							buffer = AddSongToPlaylist(received);
//							break;
//						case 3:
//							buffer = DeleteSongFromPlaylist(received);
//							break;
              case 4:
							  buffer = LoadSong(new String(data));
							  break;
//						case 5:
//							buffer = Search(received);
//							break;
//						case 6:
//							buffer = CreatePlaylist(received);
//							break;
//						case 7:
//							buffer = DeletePlaylist(received);
//							break;
						}

						if (buffer != null) {
							InetAddress address = request.getAddress();
							int port = request.getPort();
							

							if (buffer.length > sizeOfPacket) {
								// divide the data into chunks
								byte[] requestIdSend = ByteBuffer.allocate(4).putInt(4).array();
								int packetcount = buffer.length / sizeOfPacket;
								int start = 0;

								for (int j = 1; j <= packetcount; j++) {
									int end = sizeOfPacket * j;

									byte[] messageTypeSend = ByteBuffer.allocate(4).putInt(1).array();
									byte[] offset = ByteBuffer.allocate(4).putInt(j).array();
									byte[] count = ByteBuffer.allocate(4).putInt(packetcount).array();
									byte[] fragment = Arrays.copyOfRange(buffer, start, end);

									ByteArrayOutputStream baos = new ByteArrayOutputStream();
									baos.write(messageTypeSend);
									baos.write(requestIdSend);
									baos.write(offset);
									baos.write(count);
									baos.write(fragment);

									byte[] send = baos.toByteArray();
									System.out.println(requestIdSend);
									System.out.println("Sending message with: " + j + "/" + packetcount );
									start = end;

									DatagramPacket packet = new DatagramPacket(send, send.length, address, port);
									socket.send(packet);

									if (j % 50 == 0) {
										Thread.sleep(1000);
									}
								}
							}
							else {
								DatagramPacket reply = new DatagramPacket(buffer, buffer.length, address, port);
								socket.send(reply);
							}
						}
					}
				}

				System.out.println("Server: Done");

			} catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private byte[] LoadSong(String request) {		
		Gson gson = new GsonBuilder().create();
		List<Song> songs = new ArrayList<Song>();
		songs = gson.fromJson(request, new TypeToken<List<Song>>() {
		}.getType());
		
		Song song = songs.get(0);
		
		byte[] data = null;
		if (song != null) {
			 data = getFileEvent(song.getSongID());
			 System.out.println("Got Data");
		}
		return data;
	}

	public byte[] getFileEvent(int songID) {
		FileEvent fileEvent = new FileEvent();

		String fileName = songID + "";
		String path = "music/" + fileName + ".wav";
		fileEvent.setPath(path);
		fileEvent.setFilename(fileName);
		File file = new File(path);
		if (file.isFile()) {
			try {
				DataInputStream diStream = new DataInputStream(new FileInputStream(file));
				long len = (int) file.length();
				byte[] fileBytes = new byte[(int) len];
				int read = 0;
				int numRead = 0;
				while (read < fileBytes.length
						&& (numRead = diStream.read(fileBytes, read, fileBytes.length - read)) >= 0) {
					read = read + numRead;
				}
				fileEvent.setFileSize(len);
				fileEvent.setFileData(fileBytes);
				fileEvent.setStatus("Success");

				return fileBytes;

			} catch (Exception e) {
				e.printStackTrace();
				fileEvent.setStatus("Error");
			}
		} else {
			System.out.println("path specified is not pointing to a file");
			fileEvent.setStatus("Error");
		}
		return null;
	}
}