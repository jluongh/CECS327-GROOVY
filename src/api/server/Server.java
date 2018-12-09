package api.server;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import api.p2p.MetadataService;
import api.p2p.MetadataServiceMap;
import data.constants.Net;
import api.p2p.Peer;

public class Server {

	/**
	 * Created a server socket
	 * 
	 * @param args
	 *                 - arguments
	 * @throws IOException
	 *                         if input or output is invalid.
	 */

	public static void main(String[] args) throws IOException {

		
		// Initialize counters
		// MapCounter
		
		
		try {
			List<Peer> peers = new ArrayList<Peer>();
			for (int i = 0; i < Net.TOTAL_PEERS; i++) {
				Peer peer = new Peer();
				peers.add(peer);
			}

			
//			// Create MetadataServiceMap that passes in List<Peer>
			MetadataServiceMap msm = new MetadataServiceMap(peers);
			msm.initMap();
			
//			MetadataService ms = new MetadataService(peers.get(0));
//			ms.init();

			// socket = null;
			DatagramSocket socket = new DatagramSocket(Net.PORT);

			while (true) {
				byte[] message = new byte[1024 * 1000];

				// receive request
				DatagramPacket request = new DatagramPacket(message, message.length);
				socket.receive(request);

				Thread t = new ClientHandler(socket, request, msm);
				t.start();

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
