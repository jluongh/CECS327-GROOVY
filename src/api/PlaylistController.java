package api;

import java.util.Collections;
import java.util.List;

import services.PlaylistService;
import data.models.Playlist;
import data.models.SongInfo;
public class PlaylistController {

	PlaylistService playlistService;
	
	
	/**
	 * Controller to manipulate playlist data
	 * @param userID	ID of user
	 */
	public PlaylistController(int userID) {
		playlistService = new PlaylistService(userID);
	}
	
	/**
	 * Get all playlists for a given user
	 * @return	List of playlists
	 */
	public List<Playlist> GetPlaylists() {
		try {
			return playlistService.GetPlaylists();
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}
	
	public Playlist GetPlaylistByID(int playlistID) {
		try {
			return playlistService.GetPlaylistByID(playlistID);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Create a playlist
	 * @param name	Name of the playlist to create
	 */
	public void CreatePlaylist(String name) {
		try {
			playlistService.CreatePlaylist(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Delete a playlist 
	 * @param playlistID	ID of playlist to delete
	 */
	public void DeletePlaylist(int playlistID) {
		try {
			playlistService.DeletePlaylist(playlistID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void AddToPlaylistBySongInfo(int playlistID, SongInfo songInfo) {
		try {
			playlistService.AddToPlaylistBySongInfo(playlistID, songInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
