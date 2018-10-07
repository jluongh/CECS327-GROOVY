package application;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.google.gson.Gson;

import data.models.Song;
import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;

public class Client {
	
	public static void main(String[] args) throws IOException {
        
        // get a datagram socket
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(1000 * 5); 
        socket.setReceiveBufferSize(60011 * 30 * 100);
        
        List<Song> songs = new ArrayList<Song>();
        Song song = new Song();
        song.setSongID(2);
        songs.add(song);
        
//        for (int i = 0; i < songs.size(); i++) {
//        	// convert to json
//        	String songJson = new Gson().toJson(songs.get(i));
//            // convert json to bytes
//            byte[] buf = songJson.getBytes();
//
//            InetAddress address = InetAddress.getByName("localhost");
//            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4446);
//            
//            socket.send(packet);
//         
//            // get response
//            packet = new DatagramPacket(buf, buf.length);
//            socket.receive(packet);
//            
//            // display response
//            String received = new String(packet.getData(), 0, packet.getLength());
//            System.out.println(received);
//        }

        byte[] received1 = null;
        
        int offset = 0, count = 0;
        String songJson = new Gson().toJson(songs);
        // convert json to bytes
        byte[] buf = new byte[1024 * 1000 * 50];
        buf = songJson.getBytes();

        InetAddress address = InetAddress.getByName("localhost");
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4446);
        System.out.println("Sent request");
        socket.send(packet);
        System.out.println("Waiting for response");
  
        boolean receiving = true;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
		int receivedCount = 0;
		try {
			while (receiving) {
	    		buf = new byte[1024 * 1000 * 50];
	            // get response
	            packet = new DatagramPacket(buf, buf.length);
	            socket.receive(packet);
	            

	            
	            System.out.println("Client: Received from server");
	            
	            ByteArrayInputStream bais = new ByteArrayInputStream(packet.getData());
	            
	            System.out.println("Packet size: " + packet.getLength());
				int id = bais.read(packet.getData(), 0, 4);
				ByteBuffer wrapped = ByteBuffer.wrap(packet.getData(), 4, 4); // big-endian by default
				offset = wrapped.getInt();
				System.out.println("Offset: " + offset);
				wrapped = ByteBuffer.wrap(packet.getData(), 8, 4); // big-endian by default

				count = wrapped.getInt();	
				System.out.println("Count: " + count);
				
				byte[] data = Arrays.copyOfRange(packet.getData(), 12, packet.getLength());
				if (offset < count) {
					System.out.println("Writing fragment to bytes with length: " + data.length);
					baos.write(data);
					receivedCount++;
				}
				else if (offset == count) {
		            //if the packet is empty or null, then the server is done sending?
	            	receiving = false;
	            	System.out.println("Receiving done");
				}

	        }
	        
	        received1 = baos.toByteArray();
//	        System.out.println("End : " + received1.length);
//	        
//	        // Create the AudioData object from the byte array
//	        AudioData audiodata = new AudioData(received1);
//	        // Create an AudioDataStream to play back
//	        AudioDataStream audioStream = new AudioDataStream(audiodata);
//	        // Play the sound
//	        AudioPlayer.player.start(audioStream);
	        
	        
		} catch (SocketTimeoutException e) {
			
		}
        
		int percent = (int) (((double) receivedCount / (double) count) * 100);
		System.out.println(percent);
		if(percent > 60) {
	        // Create the AudioData object from the byte array
	        AudioData audiodata = new AudioData(received1);
	        // Create an AudioDataStream to play back
	        AudioDataStream audioStream = new AudioDataStream(audiodata);
	        // Play the sound
	        AudioPlayer.player.start(audioStream);
		}


		

        socket.close();
    }
	
}
