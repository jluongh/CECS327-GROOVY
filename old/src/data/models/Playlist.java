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
}
