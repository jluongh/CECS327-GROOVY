package api;

import java.util.*;
import data.models.*;
import services.LibraryService;

public class Searcher {

	/**
	 * 
	 */
	public Searcher() {
		
	}
	
	/**
	 * Method searches for queried artist in music library
	 * @param query
	 * @return
	 */
	public List<Artist> findFromArtists(String query) {
        
        LibraryService ls = new LibraryService(); // TODO: Trish - decompose LibraryService
        
        List<Artist> artists = new ArrayList<Artist>();
        artists = ls.getAllArtists();
        
        List<Artist> response = new ArrayList<Artist>();
        
        for (Artist artist : artists) {
            if (artist.getName().toLowerCase().contains(query.toLowerCase())) {
                response.add(artist);
            }
        }
        
        return response;
    }
	/**
	 * Method searches for queried album in music library
	 * @param query
	 * @return
	 */
	public List<Album> findFromAlbums(String query) {
        
        LibraryService ls = new LibraryService();
        
        List<Album> albums = new ArrayList<Album>();
        albums = ls.getAllAlbums();

        List<Album> albumsToReturn = new ArrayList<Album>();
        
        for (int i = 0; i < albums.size(); i++) {
            if (albums.get(i).getName().toLowerCase().contains(query.toLowerCase())) {
                albumsToReturn.add(albums.get(i));
            }
        }
        
        return albumsToReturn;
    }
	
	/**
	 * Method searched for queried song in music library
	 * @param query
	 * @return
	 */
	public List<Song> findFromSongs(String query) {
        
        LibraryService ls = new LibraryService();
        
        List<Song> songs = new ArrayList<Song>();
        songs = ls.getAllSongs();
        
        List<Song> songsToReturn = new ArrayList<Song>();

        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).getTitle().toLowerCase().contains(query.toLowerCase())) {
                songsToReturn.add(songs.get(i));
            }
        }
        
        return songsToReturn;
    }
}
