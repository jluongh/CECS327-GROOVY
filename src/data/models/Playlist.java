package data.models;

import java.util.*;

public class Playlist {
	
	private int playlistID;
	private String name;
	private List<SongInfo> songInfos;
	private int songCount;
	private String totalDuration;
	private Date creationDate;
	
	public Playlist() {
		//
	}
	
	public Playlist(int playlistID, String name, List<SongInfo> songInfos, int songCount, String totalDuration, Date creationDate) {
		this.playlistID = playlistID;
		this.name = name;
		this.songInfos = songInfos;
		this.songCount = songCount;
		this.totalDuration = totalDuration;
		this.creationDate = creationDate;
	}

	public int getPlaylistID() {
		return playlistID;
	}

	public void setPlaylistID(int playlistID) {
		this.playlistID = playlistID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SongInfo> getSongInfos() {
		return songInfos;
	}

	public void setSongInfos(List<SongInfo> songInfos) {
		this.songInfos = songInfos;
	}

	public int getSongCount() {
		return songCount;
	}

	public void setSongCount(int songCount) {
		this.songCount = songCount;
	}

	public String getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(String totalDuration) {
		this.totalDuration = totalDuration;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
