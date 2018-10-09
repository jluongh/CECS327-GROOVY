package api.threads;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import data.models.FileEvent;
import data.models.Song;

public class AudioPlayerThread extends Thread {
	protected DatagramSocket socket = null;
	protected BufferedReader br = null;

	public AudioPlayerThread() throws IOException {
		this("AudioPlayerThread");
	}

	public AudioPlayerThread(String name) throws IOException {
		super(name);
		socket = new DatagramSocket(4446);
	}

	public void run() {
		while (true) {
			try {
				byte[] buf = new byte[1024 * 1000 * 50];
				int sizeOfPacket = 63000;
				// receive request
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);

				// display response
				String received = new String(packet.getData(), 0, packet.getLength());
				System.out.println("Server:\n" + received);

				if (received != null) {
					// read buffer -- songs to play
					List<Song> songs = LoadSongs(received);

					if (songs != null) {
						for (int i = 0; i < songs.size(); i++) {
							try {

								byte[] data = getFileEvent(songs.get(i).getSongID());
								// byte[] (int idOfPacket + int positionOfPacket + int numberOfPackets) + byte[]
								// data

								// divide the data into chunks
								int packetcount = data.length / sizeOfPacket;
								int start = 0;

								for (int j = 1; j <= packetcount; j++) {
									int end = sizeOfPacket * j;

									byte[] id = ByteBuffer.allocate(4).putInt(1).array();
									byte[] offset = ByteBuffer.allocate(4).putInt(j).array();
									byte[] count = ByteBuffer.allocate(4).putInt(packetcount).array();
									byte[] fragment = Arrays.copyOfRange(data, start, end);

									ByteArrayOutputStream baos = new ByteArrayOutputStream();
									baos.write(id);
									baos.write(offset);
									baos.write(count);
									baos.write(fragment);

									byte[] send = baos.toByteArray();

									start = end;

									InetAddress address = packet.getAddress();
									int port = packet.getPort();

									packet = new DatagramPacket(send, send.length, address, port);
									socket.send(packet);

									if (j % 50 == 0) {
										Thread.sleep(1000);
									}
								}

								System.out.println("Server: Done");

							} catch (FileNotFoundException e) {
								System.err.println("Could not open quote file. Serving time instead.");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}
					// br.close();
				}
			} catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
				// socket.close();
			}
		}
	}

	public byte[] getFileEvent(int songID) {
		FileEvent fileEvent = new FileEvent();

		String fileName = songID + "";
		String path = "music/" + fileName + ".wav";
		fileEvent.setPath(path);
		fileEvent.setfileName(fileName);
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
				// System.out.println("Success");

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

	/**
	 * Iterator for songs
	 *
	 * @param received
	 *                     - string from packet
	 */
	protected List<Song> LoadSongs(String received) {
		Gson gson = new GsonBuilder().create();
		List<Song> songs = new ArrayList<Song>();
		songs = gson.fromJson(received, new TypeToken<List<Song>>() {
		}.getType());
		// this.iterator = songs.listIterator();
		return songs;
	}


}
