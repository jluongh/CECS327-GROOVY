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
import data.models.*;

public class UserProfileController {

	private final static int REQUEST = 0;
	private final static int REPLY = 1;
	private final static int REQUEST_ID_GETPROFILE = 1;
	private final static int REQUEST_ID_ADDSONGTOPLAYLIST = 2;
	private static final String HOST = "localhost";

	private UserProfile userProfile;
	public UserProfile GetUserProfile(DatagramSocket socket, int userID) throws IOException {
		UserProfile userProfile = new UserProfile();
		userProfile.setUserID(userID);
		String profileJson = new Gson().toJson(userProfile);

		byte[] messageType = ByteBuffer.allocate(4).putInt(REQUEST).array();
		byte[] requestIdSend = ByteBuffer.allocate(4).putInt(REQUEST_ID_GETPROFILE).array();
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
		System.out.println(messageTypeReceive);
		if (messageTypeReceive == REPLY) {

			switch (requestIdReceive) {
			// Loading User Profile
			
			case REQUEST_ID_GETPROFILE:
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
	
	public List<Playlist> GetPlaylists() {
		return this.userProfile.getPlaylists();
	}

//
//	/**
//	 * Add song to playlist based on song information
//	 *
//	 * @param playlistID
//	 *                       - {int} ID of playlist to delete
//	 * @param songInfo
//	 *                       - {SongInfo} song information
//	 * @throws IOException
//	 */
//	public void AddToPlaylistBySongInfo(int playlistID, SongInfo songInfo) throws IOException {
//		if (userProfile != null) {
//			Playlist playlist = userProfile.getPlaylists().stream().filter(p -> p.getPlaylistID() == playlistID)
//					.findFirst().get();
//			List<SongInfo> songInfos = playlist.getSongInfos();
//			songInfos.add(songInfo);
//
//			playlist.setSongInfos(songInfos);
//
//			String playlistJson = new Gson().toJson(playlist);
//
//			byte[] id = ByteBuffer.allocate(4).putInt(1).array();
//			byte[] requestId = ByteBuffer.allocate(4).putInt(1).array();
//
//			// convert json to bytes
//			byte[] fragment = playlistJson.getBytes();
//
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			baos.write(id);
//			baos.write(requestId);
//			baos.write(fragment);
//
//			byte[] send = baos.toByteArray();
//
//			InetAddress address = InetAddress.getByName("localhost");
//			DatagramPacket packet = new DatagramPacket(send, send.length, address, PORT);
//			System.out.println("Sent request");
//
//			socket.send(packet);
//		}
//	}
}
