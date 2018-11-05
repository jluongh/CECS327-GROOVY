package api.server;

import java.io.IOException;
import java.net.*;

import api.p2p.PeerService;
import data.constants.Net;
import net.tomp2p.p2p.Peer;
import net.tomp2p.p2p.PeerBuilder;
import services.HashService;

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
		
		PeerService ps = new PeerService();
		
		// socket = null;
		DatagramSocket socket = new DatagramSocket(Net.PORT);

		while (true) {
			byte[] message = new byte[1024 * 1000];

			// receive request
			DatagramPacket request = new DatagramPacket(message, message.length);
			socket.receive(request);

			Thread t = new ClientHandler(socket, request);
			t.start();

		}

	}
}
