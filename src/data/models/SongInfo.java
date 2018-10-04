package data.models;

import java.io.Serializable;
import java.util.Date;

public class SongInfo implements Serializable {

	private Song song;
	private Date addedDate;
	
	public SongInfo() {
		//
	}
	
	public SongInfo(Song song, Date addedDate) {
		this.song = song;
		this.addedDate = addedDate;
	}

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}
	
}
