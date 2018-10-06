package api;

import java.io.*;
import java.util.*;

import javax.sound.sampled.*;
import data.models.*;

public class AudioPlayer {

	//global variables
	public Song currentSong;
	private Clip clip;
	ListIterator<Song> iterator;
	private boolean isPlaying;
	
	/**
	 * Iterator for songs 
	 * @param songs - {List} list of song objects
	 */
	public void LoadSongs(List<Song> songs) {
		this.iterator = songs.listIterator();
	}
	
	/**
	 * Searching and loading song to be played
	 * @param song - {Song} song to be played
	 */
	public void Load(Song song) {
		currentSong = song;
		String filename = "music/" + currentSong.getSongID() + ".wav";
		try {

			if (isPlaying) {
				Reset();
			}
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File(filename)));

		} catch(LineUnavailableException e) {
            System.out.println("Audio Error");
		} catch(IOException e) {
            System.out.println("File Not Found");
		} catch(UnsupportedAudioFileException e) {
			System.out.println("Wrong File Type");
		}
	}
	
	/**
	 * Stopping song in order to play new song 
	 */
	private void Reset() {
		clip.stop();
		clip.setMicrosecondPosition(0);		
		isPlaying = false;
	}
	
	/**
	 * Playing song
	 */
	public void Play() {
		System.out.println("Play");
		clip.start();
		isPlaying = true;
	}
	
	/**
	 * Pausing song 
	 */
	public void Pause() {
		System.out.println("Pause");
		clip.stop();
		isPlaying = false;
	}
	
	/**
	 * Stopping song in order to play new song
	 * @return iterator 
	 */
	public boolean HasNext() {
		return iterator.hasNext();
	}
	
	/**
	 * Stopping song in order to play new song 
	 */
	public void Next() {
		if (iterator.hasNext()) {
			System.out.println("Next");
			Reset();
			Load(iterator.next());
			Play();
		}
	}
	
	/**
	 * Iterator has previous song
	 * @return iterator
	 */
	public boolean HasPrevious() {
		return iterator.hasPrevious();
	}
	
	/**
	 * Play previous song
	 */
	public void Previous() {
		if (iterator.hasPrevious()) {
			System.out.println("Previous");
			Reset();
			Load(iterator.previous());
			Play();
		}
	}
	
	/**
	 * Get the volume
	 * @return gainControl
	 */

	public float getVolume() {
	    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
	    return (float) Math.pow(10f, gainControl.getValue() / 20f);
	}

	/**
	 * Changing the volume
	 * @param volume - {float} the sound 
	 */
	public void setVolume(float volume) {
	    if (volume < 0f || volume > 1f)
	        throw new IllegalArgumentException("Volume not valid: " + volume);
	    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
	    gainControl.setValue(20f * (float) Math.log10(volume));
	}
}