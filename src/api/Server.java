package api;

import java.io.IOException;
import java.net.*;

import application.ClientHandler;
import data.constants.Net;

public class Server {
	
	/**
	 * Created a server socket
	 * @param args - arguments
	 * @throws IOException if input or output is invalid.
	 */
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
