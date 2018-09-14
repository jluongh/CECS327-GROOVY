package services;

import java.io.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.*;
import data.models.*;

/**
 * 
 * 
 */
public class LibraryService {

	private String fPath = "./src/data/library.json";
	
	/**
	 * 
	 */
	public LibraryService() {
		//
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Artist> getArtists() {
		
		// Create file object from store
		File file = new File(fPath);
		
		String json = "";
		
		// Read each character token in file
		try (FileInputStream in = new FileInputStream(file)) {
			
			int w;
			while ((w = in.read()) != -1) {
				
				json = json + (char) w;
				
			}
			
			// Identify token type for deserialization
			Gson gson = new Gson();
			Library library = gson.fromJson(json, new TypeToken<Library>() {}.getType());
			
			return library.getArtists();
			
		} catch(Exception e) {
			
			System.out.print(e);
			
			return Collections.emptyList();
			
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Album> getAllAlbums() {
		
		// Create file object from store
		File file = new File(fPath);
				
		String json = "";
				
		// Read each character token in file
		try (FileInputStream in = new FileInputStream(file)) {
			
			int w;
			while ((w = in.read()) != -1) {
						
				json = json + (char) w;
						
			}
					
			// Identify token type for deserialization
			Gson gson = new Gson();
			Library library = gson.fromJson(json, new TypeToken<Library>() {}.getType());
					
			// Get Albums from each Artists
			List<Album> allAlbums = new ArrayList<Album>();
			for (Artist artist : library.getArtists()) {
				for (Album album : artist.getAlbums()) {
					allAlbums.add(album);
				}
			}
			
			return allAlbums;
					
		} catch(Exception e) {
					
			System.out.print(e);
					
			return Collections.emptyList();
					
		}
		
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Song> getAllSongs() {
		// Create file object from store
		File file = new File(fPath);
						
		String json = "";
						
		// Read each character token in file
		try (FileInputStream in = new FileInputStream(file)) {
					
			int w;
			while ((w = in.read()) != -1) {
								
				json = json + (char) w;
								
			}
							
			// Identify token type for deserialization
			Gson gson = new Gson();
			Library library = gson.fromJson(json, new TypeToken<Library>() {}.getType());
							
			// Get Albums from each Artists
			List<Song> allSongs = new ArrayList<Song>();
			for (Artist artist : library.getArtists()) {
				for (Album album : artist.getAlbums()) {
					for (Song song : album.getSongs()) {
						allSongs.add(song);
					}
				}
			}
					
			return allSongs;
							
		} catch(Exception e) {
							
			System.out.print(e);
							
			return Collections.emptyList();
							
		}
	}
}