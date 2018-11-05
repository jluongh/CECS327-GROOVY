package data.models;

import java.io.Serializable;

public class Song implements Serializable {

	//global variables
	private int songID;
	private String title;
	private String artist;
	private String album;
	private double duration;
	
	public Song() {
		//no initialization
	}
	
	/**
	 * Overload constructor for a Song object with three arguments
	 * Initializing the songID, title, and duration 
	 * @param songID - unique identification for song
	 * @param title - song title
	 * @param artist - artist name
	 * @param album - album name
	 * @param duration - length of song
	 */
	public Song(int songID, String title, String artist, String album, double duration) {
		this.songID = songID;
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.duration = duration;
	}

	/**
	 * Getter method for song ID
	 * @return songID
	 */
	public int getSongID() {
		return songID;
	}

	/**
	 * Setter method for song ID
	 * @param songID - {int} unique identification for song
	 */
	public void setSongID(int songID) {
		this.songID = songID;
	}

	/**
	 * Getter method for song title
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter method for song title 
	 * @param title - {String} song title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	/**
	 * Getter method for song duration
	 * @return duration
	 */
	public double getDuration() {
		return duration;
	}

	/**
	 * Setter method for length of song
	 * @param duration - {double} length of song
	 */
	public void setDuration(double duration) {		
		this.duration = duration;
	}
}