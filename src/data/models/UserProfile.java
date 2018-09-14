package data.models;

import java.util.List;

public class UserProfile {
	
	private int userID;
	private List<Playlist> playlists;
	
	/**
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}
	/**
	 * @param userID the userID to set
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}
	/**
	 * @return the playlists
	 */
	public List<Playlist> getPlaylists() {
		return playlists;
	}
	/**
	 * @param playlists the playlists to set
	 */
	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}

	
}
