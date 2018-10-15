package api;

import java.io.*;
import java.nio.*;
import java.net.*;
import java.util.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import data.constants.Net;
import data.constants.Packet;
import data.models.*;

public class UserProfileController {

	private UserProfile userProfile;
	private DatagramSocket socket = null;

	/**
	 * Setter for socket for UserProfileController
	 * 
	 * @param socket
	 *                   - {DatagramSocket}
	 */
	public UserProfileController(DatagramSocket socket) {
		this.socket = socket;
	}

	/**
	 * Writing into the json file the UserProfile Sending requests Getting replies
	 * Reading the response Loading the User Profile
	 * 
	 * @param userID
	 *                   - {int} unique identification for user
	 * @return userProfile
	 * @throws IOException
	 *                         if input or output is invalid.
	 */
	public UserProfile GetUserProfile(int userID) throws IOException {
		Message requestMsg = new Message();
		requestMsg.messageType = Packet.REQUEST;
		requestMsg.requestID = Packet.REQUEST_ID_GETPROFILE;
		requestMsg.objectID = userID;

		String requestString = new Gson().toJson(requestMsg);
		byte[] requestBytes = requestString.getBytes();

		// send request
		InetAddress address = InetAddress.getByName(Net.HOST);
		DatagramPacket request = new DatagramPacket(requestBytes, requestBytes.length, address, Net.PORT);
		socket.send(request);

		// get reply
		byte[] buffer = new byte[1024 * 1000];
		DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
		socket.receive(reply);

		String replyString = new String(reply.getData(), 0, reply.getLength());
		Message replyMsg = new Gson().fromJson(replyString, Message.class);
		if (replyMsg.messageType == Packet.REPLY) {
			switch (replyMsg.requestID) {
			case Packet.REQUEST_ID_GETPROFILE:
				String data = new String(replyMsg.fragment);
				userProfile = new Gson().fromJson(data, UserProfile.class);
				break;

			}

		}
		return userProfile;
	}

	public void RefreshUserProfile() {
		try {
			GetUserProfile(userProfile.getUserID());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Getter method for Playlists
	 * 
	 * @return playlists
	 */
	public List<Playlist> GetPlaylists() {
		RefreshUserProfile();
		return this.userProfile.getPlaylists();
	}

	public boolean CreatePlaylist(String name) throws IOException {
		Playlist playlist = new Playlist();
		playlist.setName(name);
		String playlistString = new Gson().toJson(playlist);

		Message requestMsg = new Message();
		requestMsg.messageType = Packet.REQUEST;
		requestMsg.requestID = Packet.REQUEST_ID_CREATEPLAYLIST;
		requestMsg.objectID = userProfile.getUserID();
		requestMsg.fragment = playlistString.getBytes();

		String requestString = new Gson().toJson(requestMsg);
		byte[] requestBytes = requestString.getBytes();

		// send request
		InetAddress address = InetAddress.getByName(Net.HOST);
		DatagramPacket request = new DatagramPacket(requestBytes, requestBytes.length, address, Net.PORT);
		socket.send(request);

		// get reply
		byte[] buffer = new byte[1024 * 1000];
		DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
		socket.receive(reply);

		String replyString = new String(reply.getData(), 0, reply.getLength());
		Message replyMsg = new Gson().fromJson(replyString, Message.class);
		if (replyMsg.messageType == Packet.REPLY) {
			switch (replyMsg.requestID) {
			case Packet.REQUEST_ID_CREATEPLAYLIST:
				byte[] logBytes = replyMsg.fragment;
				String logString = new String(logBytes, 0, logBytes.length);
				Log log = new Gson().fromJson(logString, Log.class);
				
				Message ackMsg = new Message();
				ackMsg.messageType = Packet.ACKNOWLEDGEMENT;
				ackMsg.objectID = log.logID;
				
				String ackString = new Gson().toJson(ackMsg);
				byte[] ackBytes = ackString.getBytes();
				
				DatagramPacket acknowledgement = new DatagramPacket(ackBytes, ackBytes.length, address, Net.PORT);
				socket.send(acknowledgement);
				
				return log.success;
			}
		}
		return false;
	}

	public boolean DeletePlaylist(int playlistID) throws IOException {
		Message requestMsg = new Message();
		requestMsg.messageType = Packet.REQUEST;
		requestMsg.requestID = Packet.REQUEST_ID_DELETEPLAYLIST;
		requestMsg.objectID = userProfile.getUserID();
		byte[] playlist = ByteBuffer.allocate(4).putInt(playlistID).array();
		requestMsg.fragment = playlist;
		String requestString = new Gson().toJson(requestMsg);
		byte[] requestBytes = requestString.getBytes();

		// send request
		InetAddress address = InetAddress.getByName(Net.HOST);
		DatagramPacket request = new DatagramPacket(requestBytes, requestBytes.length, address, Net.PORT);
		socket.send(request);

		// get reply
		byte[] buffer = new byte[1024 * 1000];
		DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
		socket.receive(reply);

		String replyString = new String(reply.getData(), 0, reply.getLength());
		Message replyMsg = new Gson().fromJson(replyString, Message.class);
		if (replyMsg.messageType == Packet.REPLY) {
			switch (replyMsg.requestID) {
			case Packet.REQUEST_ID_DELETEPLAYLIST:
				byte[] logBytes = replyMsg.fragment;
				String logString = new String(logBytes, 0, logBytes.length);
				Log log = new Gson().fromJson(logString, Log.class);
				
				Message ackMsg = new Message();
				ackMsg.messageType = Packet.ACKNOWLEDGEMENT;
				ackMsg.objectID = log.logID;
				
				String ackString = new Gson().toJson(ackMsg);
				byte[] ackBytes = ackString.getBytes();
								
				DatagramPacket acknowledgement = new DatagramPacket(ackBytes, ackBytes.length, address, Net.PORT);
				socket.send(acknowledgement);
				
				return log.success;
			}
		}
		return false;
	}

	/**
	 * Add song to playlist based on song information
	 * 
	 * @param playlistID
	 *                       - {int} ID of playlist to delete
	 * @param songInfo
	 *                       - {SongInfo} song information
	 * @return saveSuccessful
	 * @throws IOException
	 *                         if input or output is invalid.
	 */
	public boolean AddSongToPlaylist(int playlistID, int songID) throws IOException {
		// prepare message
		Message requestMsg = new Message();
		requestMsg.messageType = Packet.REQUEST;
		requestMsg.requestID = Packet.REQUEST_ID_ADDSONGTOPLAYLIST;
		requestMsg.objectID = userProfile.getUserID();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(playlistID);
		baos.write(songID);
		requestMsg.fragment = baos.toByteArray();

		// convert to json
		String requestString = new Gson().toJson(requestMsg);
		byte[] requestBytes = requestString.getBytes();

		// send request
		InetAddress address = InetAddress.getByName(Net.HOST);
		DatagramPacket request = new DatagramPacket(requestBytes, requestBytes.length, address, Net.PORT);
		socket.send(request);

		// get reply
		byte[] buffer = new byte[1024 * 1000];
		DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
		socket.receive(reply);

		String replyString = new String(reply.getData(), 0, reply.getLength());
		Message replyMsg = new Gson().fromJson(replyString, Message.class);
		if (replyMsg.messageType == Packet.REPLY) {
			switch (replyMsg.requestID) {
			case Packet.REQUEST_ID_ADDSONGTOPLAYLIST:
				byte[] logBytes = replyMsg.fragment;
				String logString = new String(logBytes, 0, logBytes.length);
				Log log = new Gson().fromJson(logString, Log.class);
				
				Message ackMsg = new Message();
				ackMsg.messageType = Packet.ACKNOWLEDGEMENT;
				ackMsg.objectID = log.logID;
				
				String ackString = new Gson().toJson(ackMsg);
				byte[] ackBytes = ackString.getBytes();
				
				DatagramPacket acknowledgement = new DatagramPacket(ackBytes, ackBytes.length, address, Net.PORT);
				socket.send(acknowledgement);
				
				return log.success;
			}
		}
		return false;
	}

	/**
	 * Delete song to playlist based on song information
	 * 
	 * @param playlistID
	 *                       - {int} ID of playlist to delete
	 * @param songInfo
	 *                       - {SongInfo} song information
	 * @return saveSuccessful
	 * @throws IOException
	 *                         if input or output is invalid.
	 */
	public boolean DeleteSongFromPlaylist(int playlistID, int songID) throws IOException {
		// prepare message
		Message requestMsg = new Message();
		requestMsg.messageType = Packet.REQUEST;
		requestMsg.requestID = Packet.REQUEST_ID_DELETESONGFROMPLAYLIST;
		requestMsg.objectID = userProfile.getUserID();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(playlistID);
		baos.write(songID);
		requestMsg.fragment = baos.toByteArray();

		// convert to json
		String requestString = new Gson().toJson(requestMsg);
		byte[] requestBytes = requestString.getBytes();

		// send request
		InetAddress address = InetAddress.getByName(Net.HOST);
		DatagramPacket request = new DatagramPacket(requestBytes, requestBytes.length, address, Net.PORT);
		socket.send(request);

		// get reply
		byte[] buffer = new byte[1024 * 1000];
		DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
		socket.receive(reply);

		String replyString = new String(reply.getData(), 0, reply.getLength());
		Message replyMsg = new Gson().fromJson(replyString, Message.class);
		if (replyMsg.messageType == Packet.REPLY) {
			switch (replyMsg.requestID) {
			case Packet.REQUEST_ID_DELETESONGFROMPLAYLIST:
				byte[] logBytes = replyMsg.fragment;
				String logString = new String(logBytes, 0, logBytes.length);
				Log log = new Gson().fromJson(logString, Log.class);
				
				Message ackMsg = new Message();
				ackMsg.messageType = Packet.ACKNOWLEDGEMENT;
				ackMsg.objectID = log.logID;
				
				String ackString = new Gson().toJson(ackMsg);
				byte[] ackBytes = ackString.getBytes();
				
				DatagramPacket acknowledgement = new DatagramPacket(ackBytes, ackBytes.length, address, Net.PORT);
				socket.send(acknowledgement);
				
				return log.success;
			}
		}
		return false;
	}

	/**
	 * Telling whether a playist is empty or not
	 * 
	 * @return boolean
	 */
	public boolean IsPlaylistEmpty() {
		return userProfile.getPlaylists().isEmpty();
	}
}
