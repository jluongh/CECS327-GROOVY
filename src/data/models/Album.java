package data.models;

import java.util.List;


public class Album {
	private int albumID;
	private String name;
	private List<Song> songs;
	
	public Album() {
		//
	}
	
	public Album(int albumID, String name, List<Song> songs) {
		this.albumID = albumID;
		this.name = name;
		this.songs = songs;
	}

	public int getAlbumID() {
		return albumID;
	}

	public void setAlbumID(int albumID) {
		this.albumID = albumID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}	
}
