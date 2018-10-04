package application;
import java.io.*;
import java.net.*;
import java.util.*;
import data.constants.Net;

public class Client {
	
	public static void main(String[] args) throws IOException {
		 
        if (args.length != 1) {
             System.out.println("Usage: java QuoteClient <hostname>");
             return;
        }
        
        // get a datagram socket
        DatagramSocket socket = new DatagramSocket();
        
        // send request
        byte[] buf = new byte[Net.PACKET_SIZE];
        InetAddress address = InetAddress.getByName(args[0]);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, Net.PORT);
        socket.send(packet);
     
        // get response
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
 
        // display response
        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println(received);
     
        
        socket.close();
    }
	
}
