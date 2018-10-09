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

	private static final String HOST = "localhost";

	private UserProfile userProfile;
	private DatagramSocket socket = null;
	
	/**
	 * Setter for socket for UserProfileController
	 * @param socket - {DatagramSocket} 
	 */
	public UserProfileController(DatagramSocket socket) {
		this.socket = socket;
	}
	
	/**
	 * Writing into the json file the UserProfile 
	 * Sending requests
	 * Getting replies
	 * Reading the response
	 * Loading the User Profile
	 * @param userID - {int} unique identification for user
	 * @return userProfile
	 * @throws IOException if input or output is invalid.
	 */
	public UserProfile GetUserProfile(int userID) throws IOException {
		UserProfile userProfile = new UserProfile();
		userProfile.setUserID(userID);
		String profileJson = new Gson().toJson(userProfile);

		byte[] messageType = ByteBuffer.allocate(4).putInt(Packet.REQUEST).array();
		byte[] requestIdSend = ByteBuffer.allocate(4).putInt(Packet.REQUEST_ID_GETPROFILE).array();
		byte[] fragment = profileJson.getBytes();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(messageType);
		baos.write(requestIdSend);
		baos.write(fragment);

		// send request
		byte[] message = baos.toByteArray();
		InetAddress address = InetAddress.getByName(HOST);
		DatagramPacket request = new DatagramPacket(message, message.length, address, Net.PORT);
		socket.send(request);

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
		//System.out.println(messageTypeReceive);
		if (messageTypeReceive == Packet.REPLY) {

			switch (requestIdReceive) {
			// Loading User Profile
			
			case Packet.REQUEST_ID_GETPROFILE:
				String data = new String(fragment);
				userProfile = new Gson().fromJson(data, UserProfile.class);
				break;
			// case 1:
			// buffer = AddSongToPlaylist(received);
			// break;
			}

		}
		this.userProfile = userProfile;
		return userProfile;
	}
	
	/**
	 * Getter method for Playlists
	 * @return playlists
	 */
	public List<Playlist> GetPlaylists() {
		return this.userProfile.getPlaylists();
	}


	/**
	 * Add song to playlist based on song information
	 * @param playlistID - {int} ID of playlist to delete
	 * @param songInfo - {SongInfo} song information
	 * @return saveSuccessful
	 * @throws IOException if input or output is invalid.
	 */
	public boolean AddToPlaylistBySongInfo(int playlistID, SongInfo songInfo) throws IOException {
		boolean saveSuccessful = false;
		
		if (this.userProfile != null) {
			
			// Update new playlist
			Playlist playlist = userProfile.getPlaylists().stream().filter(p -> p.getPlaylistID() == playlistID)
					.findFirst().get();
			List<SongInfo> songInfos = playlist.getSongInfos();
			songInfos.add(songInfo);
			playlist.setSongInfos(songInfos);

			// Update user profile
			List<Playlist> playlists = userProfile.getPlaylists();
			for (int i = 0; i < playlists.size(); i++) {
				if (playlists.get(i).getPlaylistID() == playlist.getPlaylistID()) {
					playlists.set(i, playlist);
				}
			}
			this.userProfile.setPlaylists(playlists);
			
			// Get userprofile JSON
			String userProfileJson = new Gson().toJson(userProfile);

			// Write to byte array
			byte[] messageType = ByteBuffer.allocate(4).putInt(Packet.REQUEST).array();
			byte[] requestIdSend = ByteBuffer.allocate(4).putInt(Packet.REQUEST_ID_ADDSONGTOPLAYLIST).array();
			byte[] fragment = userProfileJson.getBytes();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			baos.write(messageType);
			baos.write(requestIdSend);
			baos.write(fragment);
			
			// Send request
			byte[] message = baos.toByteArray();
			InetAddress address = InetAddress.getByName(HOST);
			DatagramPacket request = new DatagramPacket(message, message.length, address, Net.PORT);
			socket.send(request);
			
			// Receive reply 
			byte[] buffer = new byte[1024 * 1000];
			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
			socket.receive(reply);
			
			// Read reply
			ByteBuffer wrapped = ByteBuffer.wrap(reply.getData(), 0, 4);
			int messageTypeReceive = wrapped.getInt();
			wrapped = ByteBuffer.wrap(reply.getData(), 4, 4);
			int requestIdReceive = wrapped.getInt();
			fragment = Arrays.copyOfRange(reply.getData(), 8, reply.getLength());

			//System.out.println(messageTypeReceive);
			
			if (messageTypeReceive == Packet.REPLY) {
				switch (requestIdReceive) {
				
				case Packet.REQUEST_ID_ADDSONGTOPLAYLIST:
					String data = new String(fragment);
					if (data.equals("1"))
						saveSuccessful = true;
					else
						saveSuccessful = false;
					break;
				}
			}
		}
		return saveSuccessful;
	}
}
