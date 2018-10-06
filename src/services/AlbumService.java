package services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import data.models.*;

public class AlbumService {

	public AlbumService() {
		//no initialization
	}
	
	/**
	 * Method gets Album by Song title
	 * @param targetSong - song title
	 * @return albumOfSong
	 */
	public Album getAlbumBySongTitle(String targetSong) { // TODO: Change to song ID?
		try {
			Album albumOfSong = null;
			String filePath = "./src/data/library.json";
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			Type listType = new TypeToken<List<Artist>>() {}.getType();
			List<Artist> artists = new Gson().fromJson(br, listType);
			
			for (Artist artist : artists) {
				List<Album> albums = artist.getAlbums();
				for (Album album : albums) {
					List<Song> songs = album.getSongs();
					for (Song song : songs) {
						if (song.getTitle().equals(targetSong)) {
							albumOfSong = album;
							break; // TODO
						}
					}
				}
			}
			
			return albumOfSong;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
