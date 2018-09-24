package services;

import java.util.*;
import data.models.*;

public class TestService {

	public static void main(String[] args) {
		LibraryService ls = new LibraryService();
		List<Artist> artists = ls.getAllArtists();
		List<Album> albums = ls.getAllAlbums();
		List<Song> songs = ls.getAllSongs();
		
		System.out.println("**********List of Songs************");
		for (Song song : songs) {
			System.out.println("Song ID: " +song.getSongID() + " - "+ song.getTitle());
			System.out.println("     Duration: " + song.getDuration());
		}
		
		System.out.println("**********List of Albums************");
		for (Album album : albums) {
			System.out.println("Album ID: " +album.getAlbumID() + " - "+ album.getName());
		}
		
		System.out.println("**********List of Artist************");
		for (Artist artist : artists) {
			System.out.println("Artist ID: " +artist.getArtistID() + " - "+ artist.getName());
			
		}
	}
}