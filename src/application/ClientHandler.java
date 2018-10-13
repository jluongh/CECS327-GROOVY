package application;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import data.constants.Packet;
import data.models.FileEvent;
import data.models.Message;
import data.models.Song;
import data.models.UserProfile;
import services.UserProfileService;
import data.models.Playlist;

public class ClientHandler extends Thread {

	// global variables
	final DatagramSocket socket;

	/**
	 * Setter for socket for ClientHandler
	 * 
	 * @param socket
	 *                   - {DatagramSocket}
	 */
	public ClientHandler(DatagramSocket socket) {
		this.socket = socket;
	}

	/**
	 * Run the client handler to handle the datagrampacket requests Sending the data
	 * packets Divides the packets into smaller packet chunks
	 */
	public void run() {
		while (true) {
			byte[] message = new byte[1024 * 1000];

			// receive request
			DatagramPacket request = new DatagramPacket(message, message.length);
			try {
				socket.receive(request);

				// display request
				String received = new String(request.getData(), 0, request.getLength());
				Message receivedMsg = new Gson().fromJson(received, Message.class);
				Message sendMsg = null;
				if (received != null) {
					if (receivedMsg.messageType == Packet.REQUEST) {
						switch (receivedMsg.requestID) {
						case Packet.REQUEST_ID_BYTECOUNT:
							sendMsg = new Message();
							sendMsg.messageType = Packet.REPLY;
							sendMsg.requestID = Packet.REQUEST_ID_BYTECOUNT;
							byte[] bytes = getFileEvent(receivedMsg.objectID);
							int count = bytes.length / Packet.BYTESIZE;
							sendMsg.count = count;
							break;
						case Packet.REQUEST_ID_LOADSONG:
							sendMsg = new Message();
							sendMsg.messageType = Packet.REPLY;
							sendMsg.requestID = Packet.REQUEST_ID_LOADSONG;
							sendMsg.objectID = receivedMsg.objectID;
							sendMsg.offset = receivedMsg.offset;
							sendMsg.count = receivedMsg.count;
							int start = receivedMsg.offset * Packet.BYTESIZE;
							int end = start + Packet.BYTESIZE;
							bytes = GetFileBytes(receivedMsg.objectID, start, end);
							System.out.println("HELLO: " + bytes.length);
							sendMsg.fragment = bytes;
//							try {
//								currentThread().sleep(2000);
//							} catch (InterruptedException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
							break;

						}

					}

					if (sendMsg != null) {
						InetAddress address = request.getAddress();
						int port = request.getPort();

						String sendMsgS = new Gson().toJson(sendMsg);
						byte[] send = sendMsgS.getBytes();
//						System.out.println("Server:\n" + sendMsgS);

						DatagramPacket packet = new DatagramPacket(send, send.length, address, port);
						socket.send(packet);
						System.out.println("Sent");
					}
					// // read header
					// // 0 = request, 1 = reply
					// ByteBuffer wrapped = ByteBuffer.wrap(request.getData(), 0, 4);
					// int messageTypeReceive = wrapped.getInt();
					// wrapped = ByteBuffer.wrap(request.getData(), 4, 4);
					// int requestIdReceive = wrapped.getInt();
					// byte[] data = Arrays.copyOfRange(request.getData(), 8, request.getLength());
					// if (messageTypeReceive == 0) {
					// byte[] buffer = null;
					//
					// switch (requestIdReceive) {
					// // Loading User Profile
					//// case 0:
					//// buffer = LoadUser(received);
					//// break;
					// case Packet.REQUEST_ID_GETPROFILE:
					// requestId = Packet.REQUEST_ID_GETPROFILE;
					// buffer = LoadUserProfile(new String(data));
					// break;
					// case Packet.REQUEST_ID_ADDSONGTOPLAYLIST:
					// requestId = Packet.REQUEST_ID_ADDSONGTOPLAYLIST;
					// buffer = AddSongToPlaylist(new String(data));
					// break;
					//// case 3:
					//// buffer = DeleteSongFromPlaylist(received);
					//// break;
					// case Packet.REQUEST_ID_LOADSONG:
					//
					// requestId = Packet.REQUEST_ID_LOADSONG;
					//
					// buffer = LoadSong(new String(data));
					// break;
					//// case 5:
					//// buffer = Search(received);
					//// break;
					//// case 6:
					//// buffer = CreatePlaylist(received);
					//// break;
					//// case 7:
					//// buffer = DeletePlaylist(received);
					//// break;
					// case Packet.REQUEST_ID_BYTECOUNT:
					// requestId = Packet.REQUEST_ID_BYTECOUNT;
					// byte[] bytes = LoadSong(new String(data));
					// int count = bytes.length / Packet.BYTESIZE;
					// buffer = ByteBuffer.allocate(4).putInt(count).array();
					// }
					//
					// if (buffer != null) {
					// InetAddress address = request.getAddress();
					// int port = request.getPort();
					//
					// byte[] messageTypeSend = ByteBuffer.allocate(4).putInt(1).array();
					// byte[] requestIdSend = ByteBuffer.allocate(4).putInt(requestId).array();
					//
					//
					// if (buffer.length > sizeOfPacket) {
					// // divide the data into chunks
					// int packetcount = buffer.length / sizeOfPacket;
					// int start = 0;
					//
					// for (int j = 1; j <= packetcount; j++) {
					// int end = sizeOfPacket * j;
					//
					// byte[] offset = ByteBuffer.allocate(4).putInt(j).array();
					// byte[] count = ByteBuffer.allocate(4).putInt(packetcount).array();
					// byte[] fragment = Arrays.copyOfRange(buffer, start, end);
					//
					// ByteArrayOutputStream baos = new ByteArrayOutputStream();
					// baos.write(messageTypeSend);
					// baos.write(requestIdSend);
					// baos.write(offset);
					// baos.write(count);
					// baos.write(fragment);
					//
					// byte[] send = baos.toByteArray();
					// System.out.println("Sending message with: " + j + "/" + packetcount );
					// start = end;
					//
					// DatagramPacket packet = new DatagramPacket(send, send.length, address, port);
					// socket.send(packet);
					//
					// if (j % 100 == 0) {
					// Thread.sleep(1000);
					// }
					// }
					// }
					// else {
					// ByteArrayOutputStream baos = new ByteArrayOutputStream();
					// baos.write(messageTypeSend);
					// baos.write(requestIdSend);
					// baos.write(buffer);
					//
					// byte[] send = baos.toByteArray();
					//
					// DatagramPacket reply = new DatagramPacket(send, send.length, address, port);
					// socket.send(reply);
					// }
					// }
					// }
					// }
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Server: Done");

			// } catch (IOException e) {
			// System.out.println("Error: " + e.getMessage());
			// }
		}
	}

	/**
	 * Loading Song Creating a json and for the list of songs Getting the bytes of
	 * the json for the list of songs
	 * 
	 * @param request
	 *                    - {String} the text to be serialized
	 * @return data
	 * @throws IOException
	 *                         if input or output is invalid.
	 */
	private byte[] LoadSong(String request) {
		Gson gson = new GsonBuilder().create();
		Song song = gson.fromJson(request, new TypeToken<Song>() {
		}.getType());

		byte[] data = null;
		if (song != null) {
			data = getFileEvent(song.getSongID());
		}
		return data;
	}

	/**
	 * Loading User Profile Creating a json and for the UserProfile based on the
	 * UserProfile class Getting the bytes of the json for UserProfile
	 * 
	 * @param request
	 *                    - {String} the text to be serialized
	 * @return data
	 * @throws IOException
	 *                         if input or output is invalid.
	 */
	private byte[] LoadUserProfile(String request) {
		UserProfileService ups = new UserProfileService();
		Gson gson = new GsonBuilder().create();
		UserProfile userProfile = gson.fromJson(request, UserProfile.class);
		byte[] data = null;
		if (userProfile != null) {
			userProfile = ups.GetUserProfile(userProfile.getUserID());
			String send = gson.toJson(userProfile);
			data = send.getBytes();
		}
		return data;
	}

	/**
	 * Add song to playlist Creating a userProfileService to be serialized in a json
	 * Returing the bytes
	 * 
	 * @param request
	 *                    - {String} the text to be serialized
	 * @return data
	 */
	private byte[] AddSongToPlaylist(String request) {
		UserProfileService ups = new UserProfileService();
		Gson gson = new GsonBuilder().create();
		UserProfile userProfile = gson.fromJson(request, UserProfile.class);
		byte[] data = null;
		if (userProfile != null && ups.SaveUserProfile(userProfile)) {
			data = "1".getBytes();
		} else
			data = "0".getBytes();
		return data;
	}

	private byte[] GetFileBytes (int songID, int start, int end) {
		String fileName = songID + "";
		String path = "music/" + fileName + ".wav";
		Path fileLocation = Paths.get(path);
		try {
			byte[] buffer = Files.readAllBytes(fileLocation);
			return Arrays.copyOfRange(buffer, start, end);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return null;
	}
	/**
	 * Setting the path of the song file Creating a stream for the song by setting
	 * the file size and bytes to play the song
	 * 
	 * @param songID
	 *                   - {int} unique identification for song
	 * @return fileBytes
	 */
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
	 * Setting the path of the song file Creating a stream for the song by setting
	 * the file size and bytes to play the song
	 * 
	 * @param songID
	 *                   - {int} unique identification for song
	 * @return fileBytes
	 */
	public byte[] getFileEvent(int songID, int start, int end) {
		FileEvent fileEvent = new FileEvent();

		String fileName = songID + "";
		String path = "music/" + fileName + ".wav";
		fileEvent.setPath(path);
		fileEvent.setfileName(fileName);
		File file = new File(path);
		if (file.isFile()) {
			try {
				DataInputStream diStream= new DataInputStream(new FileInputStream(file));
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

				return Arrays.copyOfRange(fileBytes, start, end);

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