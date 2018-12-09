package application;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

import api.p2p.MetadataService;
import api.p2p.MetadataServiceMap;
import api.p2p.Peer;
import api.server.ClientHandler;
import data.constants.Files;
import data.constants.Net;
import data.index.MetadataFile;
import services.MetadataFileService;


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
			for (int i = 0; i < Net.TOTAL_PEERS; i++) {
				Peer peer = new Peer();
				peers.add(peer);
			}

			MetadataServiceMap msm = new MetadataServiceMap(peers);
			msm.initMap();
			
			List<String> results = msm.search(Files.SONGTYPE, "S");

			System.out.println("-----------------");
			for (String s : results) {
				System.out.println("Result: " + s);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("DONE");

	}

}
