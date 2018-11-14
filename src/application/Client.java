package application;

import java.io.*;
import java.util.*;

import api.p2p.MetadataService;
import api.p2p.PeerService;
import data.constants.Files;
import data.models.*;


public class Client {

	/**
	 * Created a client socket and streaming the playlist to play the playlist's songs 
	 * @param args - arguments
	 * @throws IOException if input or output is invalid.
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) {
		
		try {
			PeerService ps = new PeerService();
			MetadataService ms = new MetadataService(ps);
			ms.init();
			List<Song> lists = ms.search(Files.SONG_INDEX, "s");
			
			for (Song s : lists) {
				System.out.println(s.getTitle());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
