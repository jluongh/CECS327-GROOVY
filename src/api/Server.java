package api;

import java.io.IOException;

import api.threads.*;

public class Server {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		new PlaylistThread().start();
		new AudioPlayerThread().start();
//		new UserProfileThread().start();
	}
}
