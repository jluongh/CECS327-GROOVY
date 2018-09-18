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

	/**
	 * @return
	 */
	public int getAlbumID() {
		return albumID;
	}

	/**
	 * @param albumID
	 */
	public void setAlbumID(int albumID) {
		this.albumID = albumID;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public List<Song> getSongs() {
		return songs;
	}

	/**
	 * @param songs
	 */
	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}	
}
