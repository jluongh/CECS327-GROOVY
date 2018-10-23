package api.server;

import java.io.IOException;
import java.net.*;

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

		// socket = null;
		DatagramSocket socket = new DatagramSocket(4445);

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
