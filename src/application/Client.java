package application;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.sound.sampled.AudioInputStream;

import api.*;
import api.audio.AudioPlayer;
import api.p2p.ChunkService;
import data.models.*;
import services.LibraryService;

public class Client {

	/**
	 * Created a client socket and streaming the playlist to play the playlist's songs 
	 * @param args - arguments
	 * @throws IOException if input or output is invalid.
	 */
	public static void main(String[] args) throws IOException {
		
		ChunkService cs = new ChunkService();
		List<Song> lists = cs.search(data.constants.Files.SONG_IDX, "s");
		
		for (Song s : lists) {
			System.out.println(s.getTitle());
		}
	}

}
