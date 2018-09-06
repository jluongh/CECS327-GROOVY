package data.Configuration;

import java.io.FileOutputStream;

/**
 * Loader loads empty tables into file system
 * 
 * 
 * @author trishaechual
 */
public class Loader {
	// file path (UNIX family)
	private final String UNIX_PATH = "src/data/";
	
	// Account
	private String account = "username,password\n";
	
	// Profile
	private String profile = "username,playlistID\n";
			
	// Playlist
	private String playlist = "playlistID,title,totalDuration,creationDate,songCount\n";
			
	// PlaylistItems
	private String playlistItems = "playlistID,songID\n";
	
	// Song
	private String song = "songID,title,artist,album,duration\n";
	
	
	public Loader() {
		//
	}
	

	public void loadStore() {
		try {
			FileOutputStream out = new FileOutputStream(UNIX_PATH + "Account.txt");
			out.write(account.getBytes());
			out.close();
			
			out = new FileOutputStream(UNIX_PATH + "Profile.txt");
			out.write(profile.getBytes());
			out.close();
			
			out = new FileOutputStream(UNIX_PATH + "Playlist.txt");
			out.write(playlist.getBytes());
			out.close();
			
			out = new FileOutputStream(UNIX_PATH + "PlaylistItems.txt");
			out.write(playlistItems.getBytes());
			out.close();
			
			out = new FileOutputStream(UNIX_PATH + "Song.txt");
			out.write(song.getBytes());
			out.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
