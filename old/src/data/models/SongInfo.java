package data.models;

import java.util.Date;

public class SongInfo {

	private Song song;
	private Date addedDate;
	
	public SongInfo() {
		//
	}
	
	public SongInfo(Song song, Date addedDate) {
		this.song = song;
		this.addedDate = addedDate;
	}
	
}
