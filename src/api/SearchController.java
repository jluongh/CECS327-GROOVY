package api;

import data.constants.Net;
import data.constants.Packet;
import data.models.*;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.*;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class SearchController {

	private DatagramSocket socket;
	
	public SearchController(DatagramSocket socket) {
		this.socket = socket;
	}

	/**
	 * 
	 * @param query
	 * @return
	 * @throws IOException
	 */
	public List<Artist> SearchByArtist(String query) throws IOException {

		// construct message
		Message requestMsg = new Message();
		requestMsg.messageType = Packet.REQUEST;
		requestMsg.requestID = Packet.REQUEST_ID_SEARCHBYARTIST;
		requestMsg.fragment = query.getBytes();
		
		String requestString = new Gson().toJson(requestMsg);
		byte[] requestBytes = requestString.getBytes();
		
		// Send request
		InetAddress address = InetAddress.getByName(Net.HOST);
		DatagramPacket request = new DatagramPacket(requestBytes, requestBytes.length, address, Net.PORT);
		socket.send(request);
					
		// Receive reply 
		byte[] buffer = new byte[1024 * 1000];
		DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
		socket.receive(reply);

		String replyString = new String(reply.getData(), 0, reply.getLength());
		
		// Deserialize message string 
		Message replyMsg = new Gson().fromJson(replyString, Message.class);
		
		if (replyMsg.messageType == Packet.REPLY) {
			switch (replyMsg.requestID) {
			case Packet.REQUEST_ID_SEARCHBYARTIST:
				String artistsString = new String(replyMsg.fragment, 0, replyMsg.fragment.length);
				
				Type listType = new TypeToken<List<Artist>>() {
				}.getType();
				List<Artist> artists = new Gson().fromJson(artistsString, listType);
				
				return artists;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 * @throws IOException
	 */
	public List<Album> SearchByAlbum(String query) throws IOException {

		// construct message
		Message requestMsg = new Message();
		requestMsg.messageType = Packet.REQUEST;
		requestMsg.requestID = Packet.REQUEST_ID_SEARCHBYALBUM;
		requestMsg.fragment = query.getBytes();
		
		String requestString = new Gson().toJson(requestMsg);
		byte[] requestBytes = requestString.getBytes();
		
		// Send request
		InetAddress address = InetAddress.getByName(Net.HOST);
		DatagramPacket request = new DatagramPacket(requestBytes, requestBytes.length, address, Net.PORT);
		socket.send(request);
					
		// Receive reply 
		byte[] buffer = new byte[1024 * 1000];
		DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
		socket.receive(reply);

		String replyString = new String(reply.getData(), 0, reply.getLength());
		
		// Deserialize message string 
		Message replyMsg = new Gson().fromJson(replyString, Message.class);
		
		if (replyMsg.messageType == Packet.REPLY) {
			switch (replyMsg.requestID) {
			case Packet.REQUEST_ID_SEARCHBYALBUM:
				String albumsString = new String(replyMsg.fragment, 0, replyMsg.fragment.length);
				
				Type listType = new TypeToken<List<Album>>() {
				}.getType();
				List<Album> albums = new Gson().fromJson(albumsString, listType);
				
				return albums;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 * @throws IOException
	 */
	public List<Song> SearchBySong(String query) throws IOException {

		// construct message
		Message requestMsg = new Message();
		requestMsg.messageType = Packet.REQUEST;
		requestMsg.requestID = Packet.REQUEST_ID_SEARCHBYSONG;
		requestMsg.fragment = query.getBytes();
		
		String requestString = new Gson().toJson(requestMsg);
		byte[] requestBytes = requestString.getBytes();
		
		// Send request
		InetAddress address = InetAddress.getByName(Net.HOST);
		DatagramPacket request = new DatagramPacket(requestBytes, requestBytes.length, address, Net.PORT);
		socket.send(request);
					
		// Receive reply 
		byte[] buffer = new byte[1024 * 1000];
		DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
		socket.receive(reply);

		String replyString = new String(reply.getData(), 0, reply.getLength());
		
		// Deserialize message string 
		Message replyMsg = new Gson().fromJson(replyString, Message.class);
		
		if (replyMsg.messageType == Packet.REPLY) {
			switch (replyMsg.requestID) {
			case Packet.REQUEST_ID_SEARCHBYSONG:
				String songsString = new String(replyMsg.fragment, 0, replyMsg.fragment.length);
				
				Type listType = new TypeToken<List<Song>>() {
				}.getType();
				List<Song> songs = new Gson().fromJson(songsString, listType);
				
				return songs;
			}
		}
		return null;
	}
}
