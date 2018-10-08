package application;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import data.models.UserProfile;
import services.UserProfileService;

public class ClientHandler extends Thread {
	
    final DatagramSocket socket; 
    
	public ClientHandler(DatagramSocket socket) {
		this.socket = socket;
	}
	
	public void run() {
		while (true) {
			try {
				byte[] buf = new byte[1024 * 1000 * 50];
				// receive request
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);

				// display response
				String received = new String(packet.getData(), 0, packet.getLength());
				System.out.println("Server:\n" + received);

				if (received != null) {
					// read header
					// 0 = request, 1 = reply
					ByteBuffer wrapped = ByteBuffer.wrap(packet.getData(), 0, 4);
					int messageType = wrapped.getInt();
					wrapped = ByteBuffer.wrap(packet.getData(), 4, 4);
					int requestId = wrapped.getInt();
					
					if (messageType == 0) {
						byte[] send = null;
						
						switch(requestId) {
						// Loading User Profile
						case 0:
							send = LoadUserProfile(received);
							break;
						case 1:
							send = AddSongToPlaylist(received);
							break;
						}

						if (send != null) {
							InetAddress address = packet.getAddress();
							int port = packet.getPort();
							packet = new DatagramPacket(send, send.length, address, port);
							socket.send(packet);
						}
					}
				}

				System.out.println("Server: Done");

			} catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}
	
	protected byte[] LoadUserProfile(String received) {
		Gson gson = new GsonBuilder().create();
		UserProfile userProfile = new UserProfile();
		userProfile = gson.fromJson(received, new TypeToken<UserProfile>() {
		}.getType());

		if (userProfile != null) {

			UserProfileService ups = new UserProfileService();
			UserProfile toSend = ups.GetUserProfile(userProfile.getUserID());
			String profileJson = new Gson().toJson(toSend);
			System.out.println("Sending back: " + profileJson);
			byte[] send = profileJson.getBytes();
			return send;
		}
	
		return null;
	}

	protected byte[] AddSongToPlaylist(String received) {
		Gson gson = new GsonBuilder().create();
		UserProfile userProfile = new UserProfile();
		userProfile = gson.fromJson(received, new TypeToken<UserProfile>() {
		}.getType());

		String profileFilePath = "./src/data/userprofile/" + userProfile.getUserID() + ".json";

		try (Writer writer = new FileWriter(profileFilePath)) {
		    gson.toJson(userProfile, writer);
		    // if successful then return userprofile
		    String userProfileJson = new Gson().toJson(userProfile);
		    return userProfileJson.getBytes();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return null;
	}
}
