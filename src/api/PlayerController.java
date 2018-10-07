package api;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import javax.sound.sampled.*;

import com.google.gson.Gson;

import data.models.Song;


public class PlayerController {

	private boolean isPlaying;
	List<byte[]> songs = new ArrayList<byte[]>();
	ListIterator<byte[]> iterator = songs.listIterator();
	private SourceDataLine sLine;

	
	public void LoadSong(int songID) throws IOException {
		// get a datagram socket
		DatagramSocket socket = new DatagramSocket();
		socket.setSoTimeout(1000 * 5);
		socket.setReceiveBufferSize(60011 * 30 * 100);

		List<Song> songsSend = new ArrayList<Song>();
		Song song = new Song();
		song.setSongID(songID);
		songsSend.add(song);

		
		// for (int i = 0; i < songs.size(); i++) {
		// // convert to json
		// String songJson = new Gson().toJson(songs.get(i));
		// // convert json to bytes
		// byte[] buf = songJson.getBytes();
		//
		// InetAddress address = InetAddress.getByName("localhost");
		// DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4446);
		//
		// socket.send(packet);
		//
		// // get response
		// packet = new DatagramPacket(buf, buf.length);
		// socket.receive(packet);
		//
		// // display response
		// String received = new String(packet.getData(), 0, packet.getLength());
		// System.out.println(received);
		// }

		byte[] received1 = null;

		int offset = 0, count = 0;
		String songJson = new Gson().toJson(songsSend);
		System.out.println(songJson);
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
				} else if (offset == count) {
					// if the packet is empty or null, then the server is done sending?
					receiving = false;
					System.out.println("Receiving done");
				}

			}

			received1 = baos.toByteArray();

		} catch (SocketTimeoutException e) {

		}

		int percent = (int) (((double) receivedCount / (double) count) * 100);
		System.out.println(percent);
		if (percent > 60) {
			songs.add(received1);
		}
		socket.close();
	}
	
	/**
	 * Playing song
	 */
	public void Play() {	
		iterator = songs.listIterator();

		while (iterator.hasNext()) {
			byte[] currentSong = iterator.next();
			
			ByteArrayInputStream bis = new ByteArrayInputStream(currentSong);
		    AudioFormat audioFormat = new AudioFormat(44100, 16, 2, true, false);
		    AudioInputStream audioInputStream2=new AudioInputStream(bis, audioFormat, currentSong.length);
		    
		    DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		    try  {
		    	 sLine=(SourceDataLine) AudioSystem.getLine(info);
		        System.out.println(sLine.getLineInfo());
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    try {
		        sLine.open(audioFormat);
		    } catch (Exception e){
		        e.printStackTrace();
		    }
		    sLine.start();
		    isPlaying = true;
		    System.out.println("Line Started");

		    try {
		        byte bytes[] =  new byte[1024];
		        int bytesRead=0;
		        int loop=0;
		        while ((bytesRead=audioInputStream2.read(bytes, 0, bytes.length))!= -1) {
		            //getVolumeLevel(bytes);
		            try {
		                sLine.write(bytes, 0, bytesRead);
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		            loop+=1;
		        }
		        System.out.println("No more bytes!");

		    } catch (Exception e) {
		        e.printStackTrace();
		    }       
		    System.out.println("Line stopped");
		}
	    
	}
	
	
	/**
	 * Pausing song 
	 */
	public void Pause() {
		System.out.println("Pause");
		sLine.stop();
		isPlaying = false;
	}

	/**
	 * Stopping song in order to play new song
	 * @return iterator 
	 */
	public boolean HasNext() {
		return iterator.hasNext();
	}
	
	/**
	 * Stopping song in order to play new song 
	 */
	public void Next() {
		if (iterator.hasNext()) {
			System.out.println("Next");
			Reset();
			iterator.next();
			Play();
		}
	}
	
	/**
	 * Iterator has previous song
	 * @return iterator
	 */
	public boolean HasPrevious() {
		return iterator.hasPrevious();
	}
	
	/**
	 * Play previous song
	 */
	public void Previous() {
		if (iterator.hasPrevious()) {
			System.out.println("Previous");
			Reset();
			iterator.previous();
			Play();
		}
	}

	private void Reset() {
		sLine.stop();
		sLine.flush();		
		isPlaying = false;
	}
}
