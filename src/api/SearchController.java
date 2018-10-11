package api;

import data.models.*;
import java.util.*;
import com.google.gson.Gson;
import java.net.*;

public class SearchController {
	
	private DatagramSocket socket;
	
	public SearchController(DatagramSocket socket) {
		this.socket = socket;
	}

	public List<Artist> SearchByArtist(String query) {
		
		// get JSON
		String queryJson = new Gson().toJson(query);
		
		return null;
	}
	
	public List<Album> SearchByAlbum(String query) {
			
		// get Json
		String queryJson = new Gson().toJson(query);
		
		return null;
	}
	
	public List<Artist> SearchBySong(String query) {
	
		// get JSON
		return null;
	}
}
