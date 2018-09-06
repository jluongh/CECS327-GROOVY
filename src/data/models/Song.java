package data.models;

public class Song {

	private int songID;
	private String title;
	private String artist;
	private String album;
	private String duration;
	
	public Song() {
		//
	}
	
	public Song(int songID, String title, String artist, String album, String duration) {
		this.songID = songID;
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.duration = duration;
	}
}
