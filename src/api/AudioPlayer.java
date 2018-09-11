package api;

import java.io.*;
import java.util.*;

import javax.sound.sampled.*;
import data.models.*;

public class AudioPlayer {

	public Song currentSong;
	ListIterator<Song> iterator;

	public void LoadSongs(List<Song> songs) {
		this.iterator = songs.listIterator(); 
	}
	
	public Clip Load(int id) {
		String filename = "music/" + id + ".wav";
		
		try {
			Clip clip = AudioSystem.getClip();
			clip.open( AudioSystem.getAudioInputStream(new File(filename)));
			return clip;
		} catch(LineUnavailableException e) {
            System.out.println("Audio Error");
		} catch(IOException e) {
            System.out.println("File Not Found");
		} catch(UnsupportedAudioFileException e) {
			System.out.println("Wrong File Type");
		}
        return null;
	}
	
	public void Play(Clip clip) {
		clip.start();
	}
	
	public void Pause(Clip clip) {
		clip.stop();
	}
	
	public void Next() {
		if (iterator.hasNext()) {
			currentSong = iterator.next();
			int songID = currentSong.getSongID();
			Clip clip = Load(songID);
			Play(clip);
		}
	}
	
	public void Previous() {
		if (iterator.hasPrevious()) {
			currentSong = iterator.previous();
			int songID = currentSong.getSongID();
			Clip clip = Load(songID);
			Play(clip);
		}
	}
}
