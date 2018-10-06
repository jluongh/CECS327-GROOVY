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
            
            //if the packet is empty or null, then the server is done sending?
            if (packet.getData().length == 0) {
            	receiving = false;
            	System.out.println("Receiving done");
            }
            
            System.out.println("Client: Received from server");
            
            ByteArrayInputStream bais = new ByteArrayInputStream(packet.getData());
            
			int id = bais.read(packet.getData(), 0, 4);
			ByteBuffer wrapped = ByteBuffer.wrap(packet.getData(), 4, 4); // big-endian by default
			int offset = wrapped.getInt();
			System.out.println("Offset: " + offset);
			wrapped = ByteBuffer.wrap(packet.getData(), 8, 4); // big-endian by default

			int count = wrapped.getInt();	
			System.out.println("Count: " + count);
			
			byte[] fragment = IOUtils.toByteArray(bais);
			byte[] data = Arrays.copyOfRange(fragment, 12, fragment.length);
			if (offset <= count) {
				System.out.println("Writing fragment to bytes");
				baos.write(data);
			}

        }
        
        byte[] received1 = baos.toByteArray();
        System.out.println("End : " + received1.length);
               

//		AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0f, 16, 1, 2, 44100.0f, false);
//		try {
//			Clip clip = AudioSystem.getClip();
//			clip.open(format, received.getBytes(), 0, received.length());
//			clip.start();
//
//		} catch (LineUnavailableException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

        
        socket.close();
    }
	
}
