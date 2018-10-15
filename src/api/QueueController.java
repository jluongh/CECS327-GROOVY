package api;

import java.util.List;

import data.models.*;
import java.util.Random;
import java.util.*;

public class QueueController {
	
	private SongQueue sq;
	private Song current;
	private Song previous;
	private Song next;
	private Stack<Song> stack;
	Random rand = new Random();
		
	/**
	 * Adds a song to the queue
	 * @param songs - {Songs} list of song objects
	 * @param song - {Song} song object
	 */
	public void loadSongs(List <Song> songs) {
		sq.setSongs(songs);
	}
	
	/**
	 * Plays the next song
	 * @return next
	 */
	public void next() {
		if (!sq.getSongs().isEmpty() && sq.getSongs().listIterator().hasNext()){
			next = sq.getSongs().remove(0);
			stack.push(next);
		}
	}
	
	/**
	 * Plays the previous song 
	 * @return previous
	 */
	public Song previous() {
		if (!stack.isEmpty()){
			previous = stack.pop();
		}
		return previous;
	}
	
	/**
	 * Shuffle the songs in the queue
	 * @param songQ - {SongQueue} the queue of songs
	 */
	public void shuffle() {
	    for (int i = 0; i < sq.getSongs().size(); i++) {
	    	int change = i + rand.nextInt(sq.getSongs().size() - i);
	        swap(sq, i, change);
	    }
	}

	/**
	 * Swapping queue positions for two songs
	 * @param songQ - {SongQueue} the queue of songs
	 * @param i - {int} position of song 1 
	 * @param change - {int} position of song 2 that was randomly selected
	 */
	public void swap(SongQueue songQ, int i, int change) {
		Song song1 = songQ.getSongs().get(i);
		Song song2 = songQ.getSongs().get(change);
		songQ.getSongs().set(i, song2);
		songQ.getSongs().set(change, song1);
	}
	
	/**
	 * Repeat the current song
	 * @return current
	 */
	public Song repeat() {
		if (!sq.getSongs().isEmpty()){
			current = sq.getSongs().get(0);
		}
		return current;
	}
	
	/**
	 * Remove songs from queue
	 */
	public void clear() {
		if (!sq.getSongs().isEmpty()){
			sq.getSongs().clear();
		}
	}
}
