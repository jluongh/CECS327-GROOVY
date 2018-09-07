package data.models;

import java.util.List;

public class User {
	
	private String username;
	private String password;
	private List<Playlist> playlists;
	
	public User() {
		//
	}
	
	public User(String username, String password, List<Playlist> playlists) {
		this.username = username;
		this.password = password;
		this.playlists = playlists;
	}
}
