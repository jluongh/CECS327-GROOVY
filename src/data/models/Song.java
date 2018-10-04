package data.models;

import java.io.Serializable;

public class Song implements Serializable {

	private int songID;
	private String title;
//	private String artist;
//	private String album;
	private double duration;
	
	public Song() {
		//
	}
	
	public Song(int songID, String title, double duration) { // constructors- String artist, String album aren't used
		this.songID = songID;
		this.title = title;
//		this.artist = artist;
//		this.album = album;
		this.duration = duration;
	}

	public int getSongID() {
		return songID;
	}

	public void setSongID(int songID) {
		this.songID = songID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

//	public String getArtist() {
//		return artist;
//	}
//
//	public void setArtist(String artist) {
//		this.artist = artist;
//	}
//
//	public String getAlbum() {
//		return album;
//	}
//
//	public void setAlbum(String album) {
//		this.album = album;
//	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {		
		this.duration = duration;
	}
}
