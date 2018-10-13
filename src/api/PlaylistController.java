//package api;
//
//import java.util.Collections;
//import java.util.List;
//
//import services.PlaylistService;
//import data.models.Playlist;
//import data.models.SongInfo;
//public class PlaylistController {
//
//	//global variables
//	PlaylistService playlistService;
//	
//	/**
//	 * Controller to manipulate playlist data
//	 * @param userID - ID of user
//	 */
//	public PlaylistController(int userID) {
//		playlistService = new PlaylistService(userID);
//	}
//	
//	/**
//	 * Get all playlists for a given user
//	 * @return	playlists
//	 */
//	public List<Playlist> GetPlaylists() {
//		try {
//			return playlistService.GetPlaylists();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return Collections.emptyList();
//		}
//	}
//	
//	public Playlist GetPlaylistByID(int playlistID) {
//		try {
//			return playlistService.GetPlaylistByID(playlistID);
//		} catch (Exception e) {
//			return null;
//		}
//	}
//	
//	/**
//	 * Create a playlist
//	 * @param name - {String} name of the playlist to create
//	 */
//	public void CreatePlaylist(String name) {
//		try {
//			playlistService.CreatePlaylist(name);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	/**
//	 * Delete a playlist 
//	 * @param playlistID - {int} ID of playlist to delete
//	 */
//	public void DeletePlaylist(int playlistID) {
//		try {
//			playlistService.DeletePlaylist(playlistID);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	/**
//	 * Add song to playlist based on song information
//	 * @param playlistID - {int} ID of playlist to delete
//	 * @param songInfo - {SongInfo} song information 
//	 */
//	public void AddToPlaylistBySongInfo(int playlistID, SongInfo songInfo) {
//		try {
//			playlistService.AddToPlaylistBySongInfo(playlistID, songInfo);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
