package application;

import java.io.*;
import java.util.*;


import api.*;
import api.audio.AudioPlayer;
import api.p2p.ChunkService;
import data.constants.Files;
import data.models.*;
import services.LibraryService;

public class Client {

	/**
	 * Created a client socket and streaming the playlist to play the playlist's songs 
	 * @param args - arguments
	 * @throws IOException if input or output is invalid.
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) {
		
		ChunkService cs = new ChunkService();
		List<Song> lists = cs.search(Files.SONG_INDEX, "s");
		
		for (Song s : lists) {
			System.out.println(s.getTitle());
		}
	}

}
