package application;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import api.p2p.MetadataService;
import api.p2p.Peer;


public class Client {

	/**
	 * Created a client socket and streaming the playlist to play the playlist's songs 
	 * @param args - arguments
	 * @throws IOException if input or output is invalid.
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) {
		
		try {
			List<Peer> peers = new ArrayList<Peer>();
			for(int i = 0; i < 5; i++) {
				Peer peer = new Peer();
				peers.add(peer);
			}
			
			MetadataService ms = new MetadataService(peers.get(0));
			ms.init();

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
