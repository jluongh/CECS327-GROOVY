package services;

import java.io.*;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import data.models.Playlist;
import data.models.User;

public class PlaylistService {

	private UserService us = new UserService();
	private String fPath = "./src/data/user.json";

	public void CreatePlaylist(String username, Playlist playlist) {
		List<User> users = us.getUsers();
		User user = users.stream().filter(u -> u.getUsername().equals(username)).findFirst().get();
		int index = users.indexOf(user);
		List<Playlist> playlists = user.getPlaylists();
		
		
		playlists.add(playlist);
		user.setPlaylists(playlists);
		users.set(index, user);
		
		
		try (Writer writer = new FileWriter(fPath)) {
		    Gson gson = new GsonBuilder().setPrettyPrinting().create();
		    gson.toJson(users, writer);
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
//	public User DeletePlaylist(User user, Playlist playlist) {
//		List<Playlist> playlists = user.getPlaylists();
//		// Remove playlist from list for the given playlist ID
//		playlists.removeIf(p -> p.getPlaylistID() == playlist.getPlaylistID());
//		user.setPlaylists(playlists);
//		return user;
//	}
}

