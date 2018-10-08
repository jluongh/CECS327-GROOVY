package data.models;

import java.util.List;


public class Album {
	
	//global variables
	private int albumID;
	private String name;
	private List<Song> songs;
	
	
	public Album() {
		//no initialization
	}
	
	/**
	 * Overload constructor for an Album object with three arguments
	 * Initializing the albumID, name, and list of songs
	 * @param albumID - unique identification for album
	 * @param name - album name
	 * @param songs - list of songs in an album
	 */
	public Album(int albumID, String name, List<Song> songs) {
		this.albumID = albumID;
		this.name = name;
		this.songs = songs;
	}

	/**
	 * Getter method for album ID
	 * @return albumID
	 */
	public int getAlbumID() {
		return albumID;
	}

	/**
	 * Setter method for album ID
	 * @param albumID - {int} unique identification for album
	 */
	public void setAlbumID(int albumID) {
		this.albumID = albumID;
	}

	/**
	 * Getter method for album name
	 * @return album name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter method for album name
	 * @param name - {String} album name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter method for a list of songs objects
	 * @return songs
	 */
	public List<Song> getSongs() {
		return songs;
	}

	/**
	 * Setter method for the song objects in a list
	 * @param songs - {List} list of songs in an album
	 */
	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}	
}
