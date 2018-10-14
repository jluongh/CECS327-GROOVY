package application;

import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import data.constants.Packet;
import data.models.*;
import services.SearchService;
import data.models.Message;
import data.models.Song;
import data.models.UserProfile;
import services.UserProfileService;
import services.UserService;

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
						case Packet.REQUEST_ID_GETUSER:
							sendMsg = new Message();
							sendMsg.messageType = Packet.REPLY;
							sendMsg.requestID = Packet.REQUEST_ID_GETUSER;
							String userString = new String(receivedMsg.fragment, 0, receivedMsg.fragment.length);
							User user = new Gson().fromJson(userString, User.class);
							sendMsg.fragment = GetUser(user.getUsername(), user.getPassword());
							System.out.println("1: " + sendMsg.fragment);
							break;
						case Packet.REQUEST_ID_GETPROFILE:
							sendMsg = new Message();
							sendMsg.messageType = Packet.REPLY;
							sendMsg.requestID = Packet.REQUEST_ID_GETPROFILE;
							sendMsg.objectID = receivedMsg.objectID;
							sendMsg.fragment = GetUserProfile(receivedMsg.objectID);
							break;
						case Packet.REQUEST_ID_BYTECOUNT:
							sendMsg = new Message();
							sendMsg.messageType = Packet.REPLY;
							sendMsg.requestID = Packet.REQUEST_ID_BYTECOUNT;
							byte[] bytes = GetFileBytes(receivedMsg.objectID);
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
							sendMsg.fragment = bytes;
							break;
						case Packet.REQUEST_ID_CREATEPLAYLIST:
							sendMsg = new Message();
							sendMsg.messageType = Packet.REPLY;
							sendMsg.requestID = Packet.REQUEST_ID_CREATEPLAYLIST;
							int userID = receivedMsg.objectID;
							String playlistName = new String(receivedMsg.fragment, 0, receivedMsg.fragment.length);
							byte[] fragment = CreatePlaylist(userID, playlistName);
							sendMsg.fragment = fragment;
							break;

						case Packet.REQUEST_ID_DELETEPLAYLIST:
							sendMsg = new Message();
							sendMsg.messageType = Packet.REPLY;
							sendMsg.requestID = Packet.REQUEST_ID_DELETEPLAYLIST;
							userID = receivedMsg.objectID;
							ByteBuffer wrapped = ByteBuffer.wrap(receivedMsg.fragment, 0, 4);
							int playlistId = wrapped.getInt();
							fragment = DeletePlaylist(userID, playlistId);
							sendMsg.fragment = fragment;
							break;
						case Packet.REQUEST_ID_ADDSONGTOPLAYLIST:
							sendMsg = new Message();
							sendMsg.messageType = Packet.REPLY;
							sendMsg.requestID = Packet.REQUEST_ID_ADDSONGTOPLAYLIST;
							userID = receivedMsg.objectID;
							ByteArrayInputStream bais = new ByteArrayInputStream(receivedMsg.fragment);
							playlistId = bais.read();
							int songId = bais.read();
							fragment = AddSongToPlaylist(userID, playlistId, songId);
							sendMsg.fragment = fragment;
							break;
						case Packet.REQUEST_ID_DELETESONGFROMPLAYLIST:
							sendMsg = new Message();
							sendMsg.messageType = Packet.REPLY;
							sendMsg.requestID = Packet.REQUEST_ID_ADDSONGTOPLAYLIST;
							userID = receivedMsg.objectID;
							bais = new ByteArrayInputStream(receivedMsg.fragment);
							playlistId = bais.read();
							songId = bais.read();
							fragment = DeleteSongFromPlaylist(userID, playlistId, songId);
							sendMsg.fragment = fragment;
							break;
						// case Packet.REQUEST_ID_SEARCHBYARTIST:
						// buffer = SearchByArtist(new String(data));
						// break;
						// case Packet.REQUEST_ID_SEARCHBYALBUM:
						// buffer = SearchByAlbum(new String(data));
						// break;
						// case Packet.REQUEST_ID_SEARCHBYSONG:
						// buffer = SearchBySong(new String(data));
						// break;

						}

						if (sendMsg != null) {
							InetAddress address = request.getAddress();
							int port = request.getPort();

							String sendMsgS = new Gson().toJson(sendMsg);
							byte[] send = sendMsgS.getBytes();

							DatagramPacket packet = new DatagramPacket(send, send.length, address, port);
							socket.send(packet);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private byte[] GetUser(String username, String password) {
		UserService us = new UserService();
		User user = us.getUser(username, password);
		if (user != null) {
			String send = new Gson().toJson(user);
			return send.getBytes();
		} else {
			return null;
		}

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
	private byte[] GetUserProfile(int userID) {
		UserProfileService ups = new UserProfileService(userID);
		UserProfile userProfile = ups.GetUserProfile();
		String send = new Gson().toJson(userProfile);
		return send.getBytes();
	}

	private byte[] CreatePlaylist(int userID, String name) {
		UserProfileService ups = new UserProfileService(userID);
		UserProfile userProfile = ups.CreatePlaylist(name);
		if (userProfile != null && ups.SaveUserProfile(userProfile)) {
			return Packet.SUCCESS;
		} else {
			return Packet.FAIL;
		}
	}

	private byte[] DeletePlaylist(int userID, int playlistId) {
		UserProfileService ups = new UserProfileService(userID);
		UserProfile userProfile = ups.DeletePlaylist(playlistId);
		if (userProfile != null && ups.SaveUserProfile(userProfile)) {
			return Packet.SUCCESS;
		} else {
			return Packet.FAIL;
		}
	}

	/**
	 * Add song to playlist Creating a userProfileService to be serialized in a json
	 * Returing the bytes
	 * 
	 * @param request
	 *                    - {String} the text to be serialized
	 * @return data
	 */
	private byte[] AddSongToPlaylist(int userID, int playlistID, int songID) {
		UserProfileService ups = new UserProfileService(userID);
		UserProfile userProfile = ups.AddSongToPlaylist(playlistID, songID);
		if (userProfile != null && ups.SaveUserProfile(userProfile)) {
			return Packet.SUCCESS;
		} else {
			return Packet.FAIL;
		}
	}

	/**
	 * Add song to playlist Creating a userProfileService to be serialized in a json
	 * Returing the bytes
	 * 
	 * @param userID
	 *                   - {int} Id of user
	 * @return data
	 */
	private byte[] DeleteSongFromPlaylist(int userID, int playlistID, int songID) {
		UserProfileService ups = new UserProfileService(userID);
		UserProfile userProfile = ups.DeleteSongFromPlaylist(playlistID, songID);
		if (userProfile != null && ups.SaveUserProfile(userProfile)) {
			return Packet.SUCCESS;
		} else {
			return Packet.FAIL;
		}
	}

	/**
	 * Setting the path of the song file Creating a stream for the song by setting
	 * the file size and bytes to play the song
	 * 
	 * @param songID
	 *                   - {int} unique identification for song
	 * @return fileBytes
	 */
	private byte[] GetFileBytes(int songID, int start, int end) {
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
	private byte[] GetFileBytes(int songID) {
		String fileName = songID + "";
		String path = "music/" + fileName + ".wav";
		Path fileLocation = Paths.get(path);
		try {
			byte[] buffer = Files.readAllBytes(fileLocation);
			return buffer;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param query
	 * @return
	 */
	private byte[] SearchByArtist(String query) {
		SearchService ss = new SearchService();

		List<Artist> artists = ss.GetArtistsByQuery(query);
		Type listType = new TypeToken<List<Artist>>() {
		}.getType();
		String send = new Gson().toJson(artists, listType);

		return send.getBytes();
	}

	/**
	 * 
	 * @param query
	 * @return
	 */
	private byte[] SearchByAlbum(String query) {
		SearchService ss = new SearchService();

		List<Album> albums = ss.GetAlbumsByQuery(query);
		Type listType = new TypeToken<List<Album>>() {
		}.getType();
		String send = new Gson().toJson(albums, listType);

		return send.getBytes();
	}

	/**
	 * 
	 * @param query
	 * @return
	 */
	private byte[] SearchBySong(String query) {
		SearchService ss = new SearchService();

		List<Song> songs = ss.GetSongsByQuery(query);
		Type listType = new TypeToken<List<Song>>() {
		}.getType();
		String send = new Gson().toJson(songs, listType);

		return send.getBytes();
	}
}