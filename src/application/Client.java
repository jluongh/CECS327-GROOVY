package application;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;

import data.models.Song;
import data.models.UserProfile;
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
        song.setSongID(1);
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
			int offset = wrapped.getInt();
			System.out.println("Offset: " + offset);
			wrapped = ByteBuffer.wrap(packet.getData(), 8, 4); // big-endian by default

			int count = wrapped.getInt();	
			System.out.println("Count: " + count);
			
			byte[] data = Arrays.copyOfRange(packet.getData(), 12, packet.getLength());
			if (offset < count) {
				System.out.println("Writing fragment to bytes with length: " + data.length);
				baos.write(data);
			}
			else if (offset == count) {
	            //if the packet is empty or null, then the server is done sending?
            	receiving = false;
            	System.out.println("Receiving done");
			}

        }
        
        byte[] received1 = baos.toByteArray();
        System.out.println("End : " + received1.length);

     // Create the AudioData object from the byte array
        AudioData audiodata = new AudioData(received1);
        // Create an AudioDataStream to play back
        AudioDataStream audioStream = new AudioDataStream(audiodata);
        // Play the sound
        AudioPlayer.player.start(audioStream);

//		AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0f, 16, 1, 2, 44100.0f, false);
//		try {
//			Clip clip = AudioSystem.getClip();
//			clip.open(format, received1, 0, received1.length);
//			clip.start();
//
//		} catch (LineUnavailableException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

        
        socket.close();
    }
	
}
