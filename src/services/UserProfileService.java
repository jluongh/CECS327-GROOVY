package services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import data.models.Playlist;
import data.models.Song;
import data.models.SongInfo;
import data.models.UserProfile;

public class UserProfileService {

	private UserProfile userProfile;
	private String filePath;

	public UserProfileService(int UserId) {
		filePath = "./src/data/userprofile/" + UserId + ".json";

		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			UserProfile response = new Gson().fromJson(br, UserProfile.class);
			userProfile = response;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Getting user profile from json file
	 * 
	 * @param UserID
	 *                   - unique identification for user
	 * @return response
	 */
	public UserProfile GetUserProfile() {
		return userProfile;
	}

	/**
	 * Get all playlists for a given user
	 * 
	 * @return playlists
	 */
	public List<Playlist> GetPlaylists() {
		// Get user's playlists
		List<Playlist> playlists = userProfile.getPlaylists();

		// If there are no playlists, initialize the list
		if (playlists == null) {
			playlists = new ArrayList<Playlist>();
		}
		return playlists;
	}
	
	/**
	 * Get playlist ID from a list of user's playlist
	 * @param playlistID - {int} unique identification for playlist
	 * @return playlist
	 */
	public Playlist GetPlaylistByID(int playlistID) {
		List<Playlist> playlists = userProfile.getPlaylists();
		try {
			return playlists.stream().filter(p -> p.getPlaylistID() == playlistID).findFirst().get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	/**
	 * Save user profile updates
	 * 
	 * @param up
	 *               - {UserProfile} user profile object
	 * @return boolean when json file is created and serialized
	 */
	public boolean SaveUserProfile(UserProfile up) {
		String filePath = "./src/data/userprofile/" + up.getUserID() + ".json";

		System.out.println("saving user profile...");

		try (Writer writer = new FileWriter(filePath)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(up, writer);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Create a playlist
	 * 
	 * @param name
	 *                 - {String} name of the playlist to create
	 */
	public UserProfile CreatePlaylist(String name) {
		
		Playlist playlist = new Playlist();
		playlist.setName(name);
		// Get new playlist ID
		int playlistID = GetLatestPlaylistID() + 1;

		// Set new playlist to new ID
		playlist.setPlaylistID(playlistID);

		// Get user's playlists
		List<Playlist> playlists = GetPlaylists();

		// Add playlist to list
		playlists.add(playlist);
		userProfile.setPlaylists(playlists);

		return userProfile;
	}

	/**
	 * Delete a playlist
	 * 
	 * @param playlistID
	 *                       - {int} ID of playlist to delete
	 */
	public UserProfile DeletePlaylist(int playlistID) {
		// Get user's playlists
		List<Playlist> playlists = GetPlaylists();

		// Removes playlist if found in userprofile.json
		if (playlists.removeIf(p -> p.getPlaylistID() == playlistID)) {
			userProfile.setPlaylists(playlists);

			return userProfile;
		}
		return null;

	}
	
	/**
	 * Add a song to the playlist 
	 * @param playlistID - {int} ID of playlist to add
	 * @param song - {SongInfo} the song and date of when the song is added 
	 */
	public UserProfile AddSongToPlaylist (int playlistID, int songID) {
		SongInfo songInfo = new SongInfo();
		LibraryService ls = new LibraryService();
		Song song = ls.getSong(songID);
		songInfo.setSong(song);
		songInfo.setAddedDate(new Date());
		Playlist playlist = GetPlaylistByID(playlistID);
		if (playlist != null) {
			List<SongInfo> songs;
			
			if (playlist.getSongInfos() == null) 
				songs = new ArrayList<SongInfo>();
			else
				songs = playlist.getSongInfos();
			
			songs.add(songInfo);
			playlist.setSongInfos(songs);
			
			DeletePlaylist(playlistID);
			
			List<Playlist> playlists = userProfile.getPlaylists();
			playlists.add(playlist);
			Collections.sort(playlists, Comparator.comparingLong(Playlist::getPlaylistID));
			userProfile.setPlaylists(playlists);
			return userProfile;
		}
		return null;
	}
	
	public UserProfile DeleteSongFromPlaylist (int playlistID, int songID) {
		Playlist playlist = GetPlaylistByID(playlistID);
		if (playlist != null) {
			List<SongInfo> songs;
			
			if (playlist.getSongInfos() == null) 
				songs = new ArrayList<SongInfo>();
			else
				songs = playlist.getSongInfos();
			
			songs.removeIf(s -> s.getSong().getSongID() == songID);
			playlist.setSongInfos(songs);
			
			DeletePlaylist(playlistID);
			
			List<Playlist> playlists = userProfile.getPlaylists();
			playlists.add(playlist);
			Collections.sort(playlists, Comparator.comparingLong(Playlist::getPlaylistID));
			userProfile.setPlaylists(playlists);
			return userProfile;
		}
		return null;
	}

	/**
	 * Get latest playlist id
	 * 
	 * @return id
	 */
	private int GetLatestPlaylistID() {
		int id;

		List<Playlist> playlists = userProfile.getPlaylists();

		if (playlists != null && !playlists.isEmpty()) {
			id = Collections.max(playlists).getPlaylistID();
		} else {
			id = -1;
		}

		System.out.println("Last Playlist ID: " + id);
		return id;
	}
}
