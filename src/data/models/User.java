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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}
	
}


