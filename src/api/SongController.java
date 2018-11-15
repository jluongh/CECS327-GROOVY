package api;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.google.gson.Gson;

import data.constants.Net;
import data.constants.Packet;
import data.models.*;
import services.SongService;

public class SongController {
	
	//global variables
	private SongService songService = new SongService();
	private DatagramSocket socket = null;

	public SongController(DatagramSocket socket) {
		this.socket = socket;
	}
	
	/**
	 * Method formats duration of song from double to formatted string (mm:ss)
	 * @param duration - {double} length of the song
	 * @return duration
	 */
	public String FormatDuration(double duration) {
		try {
			return songService.FormatDuration(duration);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @param songID
	 * @return
	 * @throws IOException
	 */
	public Song GetSongBySongID(int songID) throws IOException {
		
		Song song = null;
		
		Message requestMsg = new Message();
		requestMsg.messageType = Packet.REQUEST;
		requestMsg.requestID = Packet.REQUEST_ID_GETSONGBYSONGID;
		requestMsg.objectID = songID;

		String requestString = new Gson().toJson(requestMsg);
		byte[] requestBytes = requestString.getBytes();

		// send request
		InetAddress address = InetAddress.getByName(Net.HOST);
		DatagramPacket request = new DatagramPacket(requestBytes, requestBytes.length, address, Net.PORT);
		socket.send(request);

		// get reply
		byte[] buffer = new byte[1024 * 1000];
		DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
		socket.receive(reply);

		String replyString = new String(reply.getData(), 0, reply.getLength());
		Message replyMsg = new Gson().fromJson(replyString, Message.class);
		if (replyMsg.messageType == Packet.REPLY) {
			switch (replyMsg.requestID) {
			case Packet.REQUEST_ID_GETSONGBYSONGID:
				String data = new String(replyMsg.fragment);
				song = new Gson().fromJson(data, Song.class);
				break;

			}

		}
		return song;
	}
	
	
}
