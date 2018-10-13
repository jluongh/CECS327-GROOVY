package services;

import java.util.ArrayList;
import java.util.List;

import data.models.Album;
import data.models.Artist;
import data.models.Song;

public class SearchService {

	public SearchService() {
		
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public List<Artist> GetArtistsByQuery(String query) {
		
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
	 * 
	 * @param query
	 * @return
	 */
	public List<Album> GetAlbumsByQuery(String query) {
		
		LibraryService ls = new LibraryService();
		
		List<Album> albums = new ArrayList<Album>();
		albums = ls.getAllAlbums();
		
		List<Album> response = new ArrayList<Album>();
		
		for (Album album : albums) {
			if (album.getName().toLowerCase().contains(query.toLowerCase())) {
				response.add(album);
			}
		}
		
		return response;
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public List<Song> GetSongsByQuery(String query) {
		
		LibraryService ls = new LibraryService();
		
		List<Song> songs = new ArrayList<Song>();
		songs = ls.getAllSongs();
		
		List<Song> response = new ArrayList<Song>();
		
		for (Song song : songs) {
			if (song.getTitle().toLowerCase().contains(query.toLowerCase())) {
				response.add(song);
			}
		}
		
		return response;
	}
}
