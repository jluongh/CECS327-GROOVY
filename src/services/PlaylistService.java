package services;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import data.models.Playlist;
import data.models.User;
import data.models.UserProfile;

public class PlaylistService {

	// Get UserProfile file path
	String profileFilePath;
	private UserProfileService ups = new UserProfileService();
	UserProfile userProfile;
	
	public PlaylistService(int userID) {
		profileFilePath = "./src/data/userprofile/" + userID + ".json";
		userProfile = ups.GetUserProfile(userID);

	}

	public boolean CreatePlaylist(Playlist playlist) {
		// Get new playlist ID
		int playlistID = GetLatestPlaylistID() + 1;
		
		// Set new playlist to new ID
		playlist.setPlaylistID(playlistID);
		
		// Get user's playlists
		List<Playlist> playlists = userProfile.getPlaylists();
		
		// If there are no playlists, initialize the list
		if (playlists == null) {
			playlists = new ArrayList<Playlist>();
		}
		
		// Add playlist to list
		playlists.add(playlist);
		userProfile.setPlaylists(playlists);
		
		// Write to userprofile.json
		try (Writer writer = new FileWriter(profileFilePath)) {
		    Gson gson = new GsonBuilder().setPrettyPrinting().create();
		    gson.toJson(userProfile, writer);
		    return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean DeletePlaylist(int playlistID) {
		// Get user's playlists
		List<Playlist> playlists = userProfile.getPlaylists();
		
		// Removes playlist if found in userprofile.json
		if (playlists.removeIf(p -> p.getPlaylistID() == playlistID)) {
			userProfile.setPlaylists(playlists);
			
			// Write to userprofile.json
			try (Writer writer = new FileWriter(profileFilePath)) {
			    Gson gson = new GsonBuilder().setPrettyPrinting().create();
			    gson.toJson(userProfile, writer);
			    return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	
	private int GetLatestPlaylistID() {
		int id;
	
		List<Playlist> playlists = userProfile.getPlaylists();
		
		if (playlists != null && !playlists.isEmpty()) {
			id = Collections.max(playlists).getPlaylistID();
		}
		else {
			id = -1;
		}

		System.out.println("Last Playlist ID: " + id);
		return id;
	}
	
//	public User DeletePlaylist(User user, Playlist playlist) {
//		List<Playlist> playlists = user.getPlaylists();
//		// Remove playlist from list for the given playlist ID
//		playlists.removeIf(p -> p.getPlaylistID() == playlist.getPlaylistID());
//		user.setPlaylists(playlists);
//		return user;
//	}
}

