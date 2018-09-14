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
	 * 
	 * @param query
	 * @return
	 */
	public List<Artist> findFromArtists(String query) {
		
		LibraryService ls = new LibraryService();
		
		List<Artist> artists = new ArrayList<Artist>();
		artists = ls.getArtists();
		
		Collections.sort(artists, new Comparator<Artist>() {
	        
			@Override
			public int compare(Artist a, Artist b) {
				
				if (a.getName().contains(query) && b.getName().contains(query)) 
					return a.getName().compareTo(b.getName());
				
			    if (a.getName().contains(query) && !b.getName().contains(query)) 
			    	return -1;
			    
			    if (!a.getName().contains(query) && b.getName().contains(query)) 
			    	return 1;
			            
			    return 0;
			}
		});
		
		return artists;
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public List<Album> findFromAlbums(String query) {
		
		LibraryService ls = new LibraryService();
		
		List<Album> albums = new ArrayList<Album>();
		albums = ls.getAllAlbums();
		
		Collections.sort(albums, new Comparator<Album>() {
	        
			@Override
			public int compare(Album a, Album b) {
				
				if (a.getName().contains(query) && b.getName().contains(query)) 
					return a.getName().compareTo(b.getName());
				
			    if (a.getName().contains(query) && !b.getName().contains(query)) 
			    	return -1;
			    
			    if (!a.getName().contains(query) && b.getName().contains(query)) 
			    	return 1;
			            
			    return 0;
			}
		});
		
		return albums;
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public List<Song> findFromSongs(String query) {
		
		LibraryService ls = new LibraryService();
		
		List<Song> songs = new ArrayList<Song>();
		songs = ls.getAllSongs();
		
		Collections.sort(songs, new Comparator<Song>() {
	        
			@Override
			public int compare(Song a, Song b) {
				
				if (a.getTitle().contains(query) && b.getTitle().contains(query)) 
					return a.getTitle().compareTo(b.getTitle());
				
			    if (a.getTitle().contains(query) && !b.getTitle().contains(query)) 
			    	return -1;
			    
			    if (!a.getTitle().contains(query) && b.getTitle().contains(query)) 
			    	return 1;
			            
			    return 0;
			}
		});
		
		return songs;
	}
}
