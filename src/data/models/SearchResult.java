package data.models;

import java.util.*;

public class SearchResult {

	private List<User> users; // CHANGE TO List<Artist> artists
	
	private List<Playlist> playlists; // CHANGE TO List<Album> albums
	
	// private List<Song> songs
	
	public SearchResult() {
		
	}
	
	public SearchResult(List<User> users, List<Playlist> playlists) {
		this.users = users;
		this.playlists = playlists;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}
}
