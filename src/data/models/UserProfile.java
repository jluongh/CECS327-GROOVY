package data.models;

import java.util.List;

public class UserProfile {
	
	//global variables
	private int userID;
	private List<Playlist> playlists;
	
	/**
	 * Getter method for user ID
	 * @return userID
	 */
	public int getUserID() {
		return userID;
	}
	
	/**
	 * Setter method for userID
	 * @param userID - {int} unique identification for user
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	/**
	 * Getter method for playlist
	 * @return playlists
	 */
	public List<Playlist> getPlaylists() {
		return playlists;
	}
	
	/**
	 * Setter method for playlist objects in a list
	 * @param playlists - {List} list of playlist objects
	 */
	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}
}
