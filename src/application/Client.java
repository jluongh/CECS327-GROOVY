package application;

import java.io.*;
import java.net.*;
import java.util.*;
import data.constants.Net;

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
        
        // send request
        byte[] message = pc.LoadSong(1);
        InetAddress address = InetAddress.getByName(hostname);
        DatagramPacket request = new DatagramPacket(message, message.length, address, Net.PORT);
        socket.send(request);
     
        // get reply
        byte[] buffer = new byte[4000];
        DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
        socket.receive(reply);
 
        // display response
        String received = new String(reply.getData(), 0, reply.getLength());
        System.out.println(received);
     
        
        socket.close();
    }
	
}
