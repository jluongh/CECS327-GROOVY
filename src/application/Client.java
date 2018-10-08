package application;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.*;
import data.constants.Net;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import api.*;
import data.models.*;
import services.*;
import api.audio.AudioPlayer;

public class Client {
	private static final String hostname = "localhost";

	public static void main(String[] args) throws IOException {
		PlayerController pc = new PlayerController();

		// get a datagram socket
		DatagramSocket socket = new DatagramSocket();
		socket.setSoTimeout(5000);
		socket.setReceiveBufferSize(60011 * 30 * 100);
		
		AudioInputStream audioStream = pc.LoadSong(socket, 1);
		if (audioStream != null) {
			AudioPlayer.loadStream("1", audioStream);
			AudioPlayer.play("1", false);
		}

//		socket.close();
	}

}
