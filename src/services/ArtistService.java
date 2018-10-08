package services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import data.models.*;

public class ArtistService {

	public ArtistService() {
		//no initialization
	}
	
	/**
	 * Method gets Artist by Song title
	 * @param targetSong - song title
	 * @return artistOfSong
	 */
	public Artist getArtistBySongTitle(String targetSong) { // TODO: Change to ID
		try {
			Artist artistOfSong = null;
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
							artistOfSong = artist;
							break; // TODO
						}
					}
				}
			}
			
			return artistOfSong;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
