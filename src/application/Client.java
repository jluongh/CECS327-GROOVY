package application;

import java.io.*;

import api.PlayerController;


public class Client {

	public static void main(String[] args) throws IOException {
		PlayerController playerController = new PlayerController();
		playerController.LoadSong(1);
		playerController.LoadSong(2);
		playerController.Play();
//		playerController.Next();

	}

}
