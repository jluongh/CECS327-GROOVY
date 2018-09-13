package data.models;

import java.util.*;

public class Library {
	
	private List<Artist> artists;
	
	public Library() {
		
	}
	
	public Library(List<Artist> artists) {
		this.artists = artists;
	}

	public List<Artist> getArtists() {
		return artists;
	}

	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}
}
