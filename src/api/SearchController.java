package api;

import data.constants.Net;
import data.constants.Packet;
import data.models.*;
import java.util.*;
import com.google.gson.*;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class SearchController {

	private DatagramSocket socket;
	
	public SearchController(DatagramSocket socket) {
		this.socket = socket;
	}

	public List<Artist> SearchByArtist(String query) throws IOException {
		
		// get JSON
		String queryJson = new Gson().toJson(query);
		
		// construct message
		byte[] messageType = ByteBuffer.allocate(4).putInt(Packet.REQUEST).array();
		byte[] requestIdSend = ByteBuffer.allocate(4).putInt(Packet.REQUEST_ID_SEACRHBYARTIST).array();
		byte[] fragment = queryJson.getBytes();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(messageType);
		baos.write(requestIdSend);
		baos.write(fragment);
		
		// Send request
		byte[] message = baos.toByteArray();
		InetAddress address = InetAddress.getByName(Net.HOST);
		DatagramPacket request = new DatagramPacket(message, message.length, address, Net.PORT);
		socket.send(request);
					
		// Receive reply 
		byte[] buffer = new byte[1024 * 1000];
		DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
		socket.receive(reply);
					
		// Read reply
		ByteBuffer wrapped = ByteBuffer.wrap(reply.getData(), 0, 4);
		int messageTypeReceive = wrapped.getInt();
		wrapped = ByteBuffer.wrap(reply.getData(), 4, 4);
		int requestIdReceive = wrapped.getInt();
		fragment = Arrays.copyOfRange(reply.getData(), 8, reply.getLength());
		
		if (messageTypeReceive == Packet.REPLY) {
			switch (requestIdReceive) {
			
			case Packet.REQUEST_ID_SEACRHBYARTIST:
				String data = new String(fragment);
				Type listType = new TypeToken<List<Artist>>() {}.getType();
				
				
				break;
			}
		}
		
		return null;
	}
	
	public List<Album> SearchByAlbum(String query) throws IOException {
		// get JSON
		String queryJson = new Gson().toJson(query);
				
		// construct message
		byte[] messageType = ByteBuffer.allocate(4).putInt(Packet.REQUEST).array();
		byte[] requestIdSend = ByteBuffer.allocate(4).putInt(Packet.REQUEST_ID_SEACRHBYALBUM).array();
		byte[] fragment = queryJson.getBytes();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(messageType);
		baos.write(requestIdSend);
		baos.write(fragment);
				
		// send request
				
		// receive reply
				
		return null;
	}
	
	public List<Artist> SearchBySong(String query) throws IOException {
		// get JSON
		String queryJson = new Gson().toJson(query);
				
		// construct message
		byte[] messageType = ByteBuffer.allocate(4).putInt(Packet.REQUEST).array();
		byte[] requestIdSend = ByteBuffer.allocate(4).putInt(Packet.REQUEST_ID_SEACRHBYARTIST).array();
		byte[] fragment = queryJson.getBytes();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(messageType);
		baos.write(requestIdSend);
		baos.write(fragment);
				
		// send request
		
		// receive reply
				
		return null;
	}
}
