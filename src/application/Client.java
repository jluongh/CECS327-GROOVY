package application;

import java.io.*;
import java.net.*;
import java.util.*;
import data.constants.Net;

import javax.sound.sampled.AudioInputStream;

import api.*;
import data.models.*;
import services.*;
import api.audio.AudioPlayer;


public class Client {

	public static void main(String[] args) throws IOException {
		PlayerController playerController = new PlayerController();
		AudioInputStream ais = playerController.LoadSong(1);
		if (ais != null) {
			AudioPlayer.loadStream("1", ais);
			AudioPlayer.play("1", false);
		}

		try {
			Thread.sleep(5000);
			AudioPlayer.stop("1");
			AudioPlayer.resume("1");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		DatagramSocket socket;
//		try {
//			socket = new DatagramSocket();
//			socket.setSoTimeout(1000 * 5);
//			socket.setReceiveBufferSize(60011 * 30 * 100);
//			
//			while(true) {
//				byte[] buf = new byte[1024 * 1000 * 50];
//				// get response
//				DatagramPacket packet = new DatagramPacket(buf, buf.length);
//				socket.receive(packet);
//				
//				
//			}
////			UserProfileController userProfileController = new UserProfileController(socket);
////			UserProfile userProfile = userProfileController.GetUserProfile(0);
////			LibraryService libraryService = new LibraryService();
////			List<Song> songs = libraryService.getAllSongs();
////			
////			Song toAdd = songs.get(1);
////			SongInfo songInfo = new SongInfo();
////			songInfo.setSong(toAdd);
////			songInfo.setAddedDate(new Date());
////			System.out.println("Song to Add: " + toAdd.getTitle() + " to playlistID " + userProfile.getPlaylists().get(0).getPlaylistID());
////			
////			userProfileController.AddToPlaylistBySongInfo(userProfile.getPlaylists().get(0).getPlaylistID(), songInfo);
//			
//		} catch (SocketException e) {
//			e.printStackTrace();
//		}
//		


	}

}
