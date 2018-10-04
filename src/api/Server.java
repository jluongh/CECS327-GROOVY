package api;

import java.io.IOException;

import api.threads.PlaylistThread;

public class Server {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new PlaylistThread().start();
	}
}
