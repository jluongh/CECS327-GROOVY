package api;

import java.io.*;
import java.net.*;

import com.google.gson.Gson;

import data.constants.*;
import data.models.Message;
import data.models.User;

public class UserController {
	
	//global variables
	private int MAX_LENGTH = 15;
	private DatagramSocket socket = null;

	/**
	 * Setter for socket for UserController
	 * 
	 * @param socket
	 *                   - {DatagramSocket}
	 */
	public UserController(DatagramSocket socket) {
		this.socket = socket;
	}
	
	/**
	 * Method checks if entered credential is too long or is empty
	 * @param textField - {String} input text
	 * @return boolean
	 */
	public boolean isValid(String textField) { // TODO: Trish - create separate methods for pw and un
		if (textField.equals("")) {
			return false;
		} else if (textField.length() > MAX_LENGTH) {
			return false;
		}
		
		return true;
	}

	
	/**
	 * Gets user
	 * @param username - {String} name of user profile
	 * @return user
	 */
	public User getUser(String username, String password) throws IOException {
		User userToSend = new User();
		userToSend.setUsername(username);
		userToSend.setPassword(password);
		
		String userString = new Gson().toJson(userToSend);
		
		User user = null;
		
		Message requestMsg = new Message();
		requestMsg.messageType = Packet.REQUEST;
		requestMsg.requestID = Packet.REQUEST_ID_GETUSER;
		requestMsg.fragment = userString.getBytes();
		
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
			case Packet.REQUEST_ID_GETUSER:
				if (replyMsg.fragment != null) {
					String data = new String(replyMsg.fragment);
					user = new Gson().fromJson(data, User.class);
				}
				else {
					user = null;
				}
				break;
			}

		}
		return user;
		
	}
}
