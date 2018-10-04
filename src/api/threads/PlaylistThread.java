package api.threads;
import java.io.*;
import java.net.*;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import data.models.Playlist;
import data.models.UserProfile;
public class PlaylistThread extends Thread {
	protected DatagramSocket socket = null;
	protected BufferedReader br = null;
	protected boolean morePlaylists = true;

    public PlaylistThread() throws IOException {
    		this("PlaylistThread");
    }
    
    public PlaylistThread(String name) throws IOException {
    		super(name);
    		socket = new DatagramSocket(4445);
    		int userID = 0;
    		try {
    			String profileFilePath = "./src/data/userprofile/" + userID + ".json";
    			br = new BufferedReader(new FileReader(profileFilePath));
    		} catch (FileNotFoundException e) {
    			System.err.println("Could not open quote file. Serving time instead.");
    		}
    }
    
    public void run() {
	    	while (true) {
	            try {
	                byte[] buf = new byte[4000];
	 
	                // receive request
	                DatagramPacket packet = new DatagramPacket(buf, buf.length);
	                socket.receive(packet);
	 
	                // figure out response
	                List<Playlist> playlists = null;
	                if (br == null)
	                		playlists = null;
	                else {
	                	playlists = getPlaylist();
		            
	                	if (playlists != null) {
		                	for (int i = 0; i < playlists.size(); i++) {
		                		buf = serialize(playlists.get(i));
		                		System.out.println("GOt one");
		        	        // send the response to the client at "address" and "port"
	        	                InetAddress address = packet.getAddress();
	        	                int port = packet.getPort();
	        	                packet = new DatagramPacket(buf, buf.length, address, port);
	        	                socket.send(packet);
		                	}
		         
	                	}
	                }
	              	
	                
	                
	            } catch (IOException e) {
	                e.printStackTrace();
	                System.out.println(e.getMessage());
	    	        socket.close();

	            }
	        }
    }
    
    public static byte[] serialize(Object obj) throws IOException {
	    Gson gson = new GsonBuilder().create();
		String objString = gson.toJson(obj);
		return objString.getBytes();
        
    }
    
    protected List<Playlist> getPlaylist() {
		UserProfile response = new Gson().fromJson(br, UserProfile.class);
        return response.getPlaylists();
    }
}
