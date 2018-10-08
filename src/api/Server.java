package api;

import java.io.IOException;
import java.net.*;

import api.threads.PlaylistThread;
import application.ClientHandler;
import data.constants.Net;

public class Server {
	public static void main(String[] args) throws IOException {

		//new PlaylistThread().start();
		
		DatagramSocket socket = null;
		
		try {
			socket = new DatagramSocket(4445);
				
			Thread t = new ClientHandler(socket);
			t.start();
				
		} catch(SocketException se) {
			if (socket != null)
				socket.close();
			se.getMessage();
		}
	}
}
