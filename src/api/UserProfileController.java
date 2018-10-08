package api;

import java.io.*;
import java.nio.*;
import java.net.*;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import data.models.*;

public class UserProfileController {

	// global variables
	static int PORT = 4447;
	private UserProfile userProfile;
	private DatagramSocket socket;

	final static int REQUEST = 0;
	final static int REPLY = 1;

	final static int GETPROFILE = 0;
	final static int ADDSONGTOPLAYLIST = 1;
	public UserProfileController(DatagramSocket socket) {
		this.socket = socket;

	}

	/**
	 * Get a user profile
	 *
	 * @param userID
	 *                   - unique identification for user
	 * @return user profile
	 */

	public UserProfile GetUserProfile(int userID) throws IOException {

		UserProfile userProfile = new UserProfile();
		userProfile.setUserID(userID);
		String profileJson = new Gson().toJson(userProfile);

		byte[] messageType = ByteBuffer.allocate(4).putInt(REQUEST).array();
		byte[] requestId = ByteBuffer.allocate(4).putInt(GETPROFILE).array();
		// convert json to bytes
		byte[] fragment = profileJson.getBytes();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(messageType);
		baos.write(requestId);
		baos.write(fragment);

		byte[] send = baos.toByteArray();

		InetAddress address = InetAddress.getByName("localhost");
		DatagramPacket packet = new DatagramPacket(send, send.length, address, PORT);
		System.out.println("Sent request");
		socket.send(packet);
		System.out.println("Waiting for response");

		boolean receiving = true;

		try {
			while (receiving) {
				byte [] buf = new byte[1024 * 1000 * 50];
				// get response
				packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);

				System.out.println("Client: Received from server");
				String received = new String(packet.getData(), 0, packet.getLength());
				UserProfile fromReceived = LoadUserProfile(received);
				receiving = false;
				return fromReceived;

			}
		} catch (SocketTimeoutException e) {

		}

		socket.close();

		return null;
	}

	private UserProfile LoadUserProfile(String received) {
		Gson gson = new GsonBuilder().create();
		UserProfile userProfile = new UserProfile();
		userProfile = gson.fromJson(received, new TypeToken<UserProfile>() {
		}.getType());
		this.userProfile = userProfile;
		return userProfile;
	}

	/**
	 * Add song to playlist based on song information
	 *
	 * @param playlistID
	 *                       - {int} ID of playlist to delete
	 * @param songInfo
	 *                       - {SongInfo} song information
	 * @throws IOException
	 */
	public void AddToPlaylistBySongInfo(int playlistID, SongInfo songInfo) throws IOException {
		if (userProfile != null) {
			Playlist playlist = userProfile.getPlaylists().stream().filter(p -> p.getPlaylistID() == playlistID)
					.findFirst().get();
			List<SongInfo> songInfos = playlist.getSongInfos();
			songInfos.add(songInfo);

			playlist.setSongInfos(songInfos);

			String playlistJson = new Gson().toJson(playlist);

			byte[] id = ByteBuffer.allocate(4).putInt(1).array();
			byte[] requestId = ByteBuffer.allocate(4).putInt(1).array();

			// convert json to bytes
			byte[] fragment = playlistJson.getBytes();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			baos.write(id);
			baos.write(requestId);
			baos.write(fragment);

			byte[] send = baos.toByteArray();

			InetAddress address = InetAddress.getByName("localhost");
			DatagramPacket packet = new DatagramPacket(send, send.length, address, PORT);
			System.out.println("Sent request");

			socket.send(packet);
		}
	}
}
