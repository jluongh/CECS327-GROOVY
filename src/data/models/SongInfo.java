package data.models;

import java.io.Serializable;
import java.util.Date;

public class SongInfo implements Serializable {

	//global variables
	private Song song;
	private Date addedDate;
	
	public SongInfo() {
		//no initialization
	}
	
	/**
	 * Overload constructor for a SongInfo object with two arguments
	 * Initializing the song object and the date the song was added
	 * @param song - song object
	 * @param addedDate - date of when the song was added to a playlist
	 */
	public SongInfo(Song song, Date addedDate) {
		this.song = song;
		this.addedDate = addedDate;
	}

	/**
	 * Getter method for a songs object
	 * @return song
	 */
	public Song getSong() {
		return song;
	}

	/**
	 * Setter method for the song object
	 * @param song - {Song} song object
	 */
	public void setSong(Song song) {
		this.song = song;
	}

	/**
	 * Getter method for the date when the song was added to a playlist
	 * @return addedDate 
	 */
	public Date getAddedDate() {
		return addedDate;
	}

	/**
	 * Setter method for the date when the song was added to a playlist 
	 * @param addedDate - {Date} date of when the song was added to a playlist
	 */
	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}
}