package api;

import java.io.IOException;
import java.net.*;

import application.ClientHandler;

public class Server {
	
	/**
	 * Created a server socket
	 * @param args
	 * @throws IOException if input or output is invalid.
	 */
	public static void main(String[] args) throws IOException {
		
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
