package data.models;


import java.util.List;


public class SongQueue {

	//global variables
	private  List<Song> songs;
	
	public SongQueue() {
		//no initialization
	}
	
	/**
	 * Overload constructor for a SongQueue object with one argument
	 * Initializing list of songs
	 * @param songs - list of songs by an artist
	 */
	public SongQueue(List<Song> songs) {
		this.songs = songs;
	}
	
	/**
	 * Getter method for a list of song objects, the queue
	 * @return songs
	 */
	public List<Song> getSongs() {
		return songs;
	}

	/**
	 * Setter method for the song objects in a list
	 * @param songs - {List} list of songs by an artist 
	 */
	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}	
	
	
}
