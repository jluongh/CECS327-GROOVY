package data.models;

import java.util.List;


public class Artist {
	private int artistID;
	private String name;
	private  List<Album> albums;
	
	public Artist() {
		//
	}
	
	/**
	 * @param artistID
	 * @param name
	 */
	public Artist(int artistID, String name) {
		this.artistID = artistID;
		this.name = name;
	}

	/**
	 * @return
	 */
	public int getArtistID() {
		return artistID;
	}
	
	/**
	 * @param artistID
	 */
	public void setArtistID(int artistID) {
		this.artistID = artistID;
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
	public List<Album> getAlbums() {
		return albums;
	}

	/**
	 * @param albums
	 */
	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}
	
}
