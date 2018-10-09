package application;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.sound.sampled.AudioInputStream;

import api.*;
import api.audio.AudioPlayer;
import data.models.*;

public class Client {

	public static void main(String[] args) throws IOException {
		// get a datagram socket
		DatagramSocket socket = new DatagramSocket();
		socket.setSoTimeout(5000);
		socket.setReceiveBufferSize(60011 * 30 * 100);

		UserProfileController upc = new UserProfileController(socket);
		UserProfile user = upc.GetUserProfile(0);
		
//		Song song = new Song(3, "Dancing Queen", 210600000);
//		SongInfo songinfo = new SongInfo(song, null);
//		System.out.println(upc.AddToPlaylistBySongInfo(1, songinfo));
		
		List<Playlist> playlists = user.getPlaylists();
		
		Playlist playlist = user.getPlaylists().get(0);
		
		PlayerController pc = new PlayerController(socket);
		
		List<Song> songs = new ArrayList<Song>();
		for (int i = 0; i < playlist.getSongInfos().size(); i++) {
			songs.add(playlist.getSongInfos().get(i).getSong());
		}

//		List<AudioInputStream> streams = pc.LoadSongs(songs);
//
//		AudioPlayer player = new AudioPlayer();
//		player.playSongs(streams);
		
		
		System.out.println("Done");


		socket.close();
	}

}
