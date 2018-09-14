package data.models;

import java.util.*;

public class Playlist implements Comparable {
	
	private int playlistID;
	private String name;
	private List<SongInfo> songInfos;
	private int songCount;
	private String totalDuration;
	private Date creationDate;
	
	public Playlist(String name) {
		this.name = name;
//		this.songInfos = songInfos;
//		this.songCount = songCount;
//		this.totalDuration = totalDuration;
		this.creationDate = new Date();
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

	@Override
	public int compareTo(Object o) {
		Playlist playlist = (Playlist) o;
		return this.playlistID - playlist.playlistID;
	}
}
