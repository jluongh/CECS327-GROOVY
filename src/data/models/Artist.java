package data.models;

import java.util.List;


public class Artist {
	
	//global variables
	private int artistID;
	private String name;
	private  List<Album> albums;
	
	public Artist() {
		//no initialization
	}
	
	/**
	 * Overload constructor for an Artist object with three arguments
	 * Initializing the albumID, name, and list of songs
	 * @param artistID - unique identification for artist
	 * @param name - artist name
	 * @param albums - list of albums by an artist
	 */
	public Artist(int artistID, String name, List<Album> albums) {
		this.artistID = artistID;
		this.name = name;
		this.albums = albums;
	}

	/**
	 * Getter method for artist ID
	 * @return artistID
	 */
	public int getArtistID() {
		return artistID;
	}
	
	/**
	 * Setter method for artist ID
	 * @param artistID - {int} unique identification for artist
	 */
	public void setArtistID(int artistID) {
		this.artistID = artistID;
	}

	/**
	 * Getter method for artist name
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter method for artist name
	 * @param name - {String} artist name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter method for a list of album objects
	 * @return albums
	 */
	public List<Album> getAlbums() {
		return albums;
	}

	/**
	 * Setter method for the album objects in a list
	 * @param albums - {List} list of albums by an artist 
	 */
	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}	
}