package data.models;

import java.io.*;
import java.util.*;

public class Playlist implements Comparable, Serializable {
	
	//global variables
	private int playlistID;
	private String name;
	private List<SongInfo> songInfos;
	private int songCount;
	private String totalDuration;
	private Date creationDate;
	
	/**
	 * Initializing the name of the playlist and date created 
	 * @param name - name of the playlist 
	 */
	public Playlist(String name) {
		this.name = name;
//		this.songInfos = songInfos;
//		this.songCount = songCount;
//		this.totalDuration = totalDuration;
		this.creationDate = new Date();
	}
	
	/**
	 * Getter method for playlist ID
	 * @return playlistID
	 */
	public int getPlaylistID() {
		return playlistID;
	}

	/**
	 * Setter method for playlist ID
	 * @param playlistID - {int} unique identification for playlist
	 */
	public void setPlaylistID(int playlistID) {
		this.playlistID = playlistID;
	}

	/**
	 * Getter method for the name of the playlist
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter method for the name of the playlist
	 * @param name - {String} name of the playlist
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter method for songInfos objects in a list
	 * @return songInfos
	 */
	public List<SongInfo> getSongInfos() {
		return songInfos;
	}

	/**
	 * Setter method for songInfos objects in a list
	 * @param songInfos - {List} list of song information objects
	 */
	public void setSongInfos(List<SongInfo> songInfos) {
		this.songInfos = songInfos;
	}

	/**
	 * Getter method for number of songs
	 * @return songCount
	 */
	public int getSongCount() {
		return songCount;
	}

	/**
	 * Setter method for number of songs
	 * @param songCount - {int} number of songs in a playlist
	 */
	public void setSongCount(int songCount) {
		this.songCount = songCount;
	}

	/**
	 * Getter method for the length of the playlist
	 * @return totalDuration
	 */
	public String getTotalDuration() {
		return totalDuration;
	}

	/**
	 * Setter method for the length of the playlist
	 * @param totalDuration - {String} duration of all the song in the playlist
	 */
	public void setTotalDuration(String totalDuration) {
		this.totalDuration = totalDuration;
	}

	/**
	 * Getter method for the date of when the playlist was created
	 * @return creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Setter method for the date of when the playlist was created
	 * @param creationDate - {Date} date the playlist was created
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/** 
	 * Comparing user for validation
	 * @param o - user object
	 * @return playlistID
	 */
	@Override
	public int compareTo(Object o) {
		Playlist playlist = (Playlist) o;
		return this.playlistID - playlist.playlistID;
	}
}