package services;

import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.*;

import javax.sound.sampled.*;

import data.models.*;

/**
 * 
 * 
 */
public class LibraryService {
	private String fPath = "./src/data/library.json";
	private String libraryPath = " ./music/";
	private File[] library = new File(libraryPath).listFiles();
	private Artist artist;
	private Album album;
	private Song song;

	private List<Artist> artists = new ArrayList<Artist>();
	private List<Album> albums = null;
	private List<Song> songs;
	
	/**
	 * Creates library json file
	 */
	public LibraryService() {
		createLibrary();
	}

	/**
	 * Creates and makes relations for artists, albums, and songs and stores the information as json file
	 * Assigns unique IDs
	 */
	public void createLibrary(){
		AudioFileFormat baseFileFormat = null;

		int songID = 1;
		int albumID = 1;
		int artistID = 1;

		for (File file : library) {
			//Getting properties of .wav file
			try {
				baseFileFormat = AudioSystem.getAudioFileFormat(file);
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			//Mapping .wav file properties 
			Map<String, Object> properties = baseFileFormat.properties();
			String artistName = (String) properties.get("Contributing artists");
			String albumName = (String) properties.get("Album");
			String songTitle = (String) properties.get("Title");
			double minutes =  (double) properties.get("length")/60000000.0;

			//Create new Song
			Song so = createSong(songID,songTitle,minutes);

			//Creating new album and adding song or adding song to pre-existing album
			Album al = createAlbum(albumID, albumName, so);

			//Creating new artist and adding album or adding album to pre-existing artist
			Artist ar = createArtist(artistID, artistName, al);

			//List of Artists
			artists.add(ar);

			songID++;
		}	

		//Writing list of artists into library json file
		try (Writer writer = new FileWriter(fPath)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(artists, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates new artist or updates existing artist
	 * Assigns unique IDs
	 * @param {int} artistID - Unique ID for artist
	 * @param {String} artistName - Name of artist
	 * @param {Album} al - Album object to be added into an artist
	 * @return artist
	 */	
	private Artist createArtist(int artistID, String artistName, Album al) {
		if(artists.isEmpty()) { //list of albums doesn't exist
			artist = new Artist();
			artist.setArtistID(artistID);
			artist.setName(artistName);
			albums = new ArrayList<Album>();
			albums.add(album);
			artist.setAlbums(albums);
			artistID++;
		}
		else {
			for(int index = 1; index <= artists.size(); index++) {
				if(artistName.equals(artists.get(index-1).getName())) { //album exists
					artists.get(index-1).getAlbums().add(album);
					break;
				}
				else if (index == artists.size()) { //album doesn't exist
					artist = new Artist();
					artist.setArtistID(artistID);
					artist.setName(artistName);
					albums = new ArrayList<Album>();
					albums.add(album);
					artist.setAlbums(albums);
					artistID++;
				}	
			}
		}
		return artist;
	}
	
	/**
	 * Creates new album or updates existing album
	 * Assigns unique IDs
	 * @param {int} albumID - Unique ID for album
	 * @param {String} albumName - Name of album
	 * @param {Song} so - Song object to be added into an album
	 * @return album 
	 */	
	private Album createAlbum(int albumID, String albumName, Song so) {
		if(albums.isEmpty()) { //list of albums doesn't exist
			album = new Album();
			album.setAlbumID(albumID);
			album.setName(albumName);
			songs = new ArrayList<Song>();
			songs.add(song);
			album.setSongs(songs);
			albums.add(album);
			albumID++;
		}
		else {
			for(int index = 1; index <= albums.size(); index++) {
				if(albumName.equals(albums.get(index-1).getName())) { //album exists
					albums.get(index-1).getSongs().add(song);
					break;
				}
				else if (index == albums.size()) { //album doesn't exist
					album = new Album();
					album.setAlbumID(albumID);
					album.setName(albumName);
					songs = new ArrayList<Song>();
					songs.add(song);
					album.setSongs(songs);
					albums.add(album);
					albumID++;
				}	
			}
		}

		return album;
	}
	
	/**
	 * Creates new song
	 * Assigns unique IDs
	 * @param {int} songID - Unique ID for song
	 * @param {String} songTitle - Title of song
	 * @param {double} minutes - Length of song
	 * @return song
	 */	
	private Song createSong(int songID, String songTitle, double minutes) {
		song = new Song();
		song.setSongID(songID);
		song.setTitle(songTitle);
		song.setDuration(minutes);
		return song;
	}

	/**
	 * Getter for specific artist	
	 * @param {String} artistName - Name of the artist being searched for 
	 * @return artist
	 */
	public Artist getArtist(String artistName) {
		Artist foundArtist = null;
		// Create file object from library
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

			// Get Artist
			for (Artist artist : library.getArtists()) {
				if (artist.getName().equals(artistName)) {
					foundArtist = artist;
					break;
				}
				else {
					System.out.println("Artist does not exist");
				}
			}

		} catch(Exception e) {

			System.out.print(e);
		}
		return foundArtist;
	}
	
	/**
	 * Getter for list of artist from library json file
	 * @return list of all artists
	 */
	public List<Artist> getAllArtists() {
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

			// Get Artists
			List<Artist> allArtists = new ArrayList<Artist>();
			for (Artist artist : library.getArtists()) {
				allArtists.add(artist);
			}

			return allArtists;

		} catch(Exception e) {

			System.out.print(e);

			return Collections.emptyList();

		}
	}

	/**
	 * Getter for specific artist's album	
	 * @param {String} albumName - Name of the album being searched for 
	 * @return album
	 */
	public Album getAlbum(String albumName) {
		Album foundAlbum = null;
		// Create file object from library
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

			// Get artist's album
			for (Artist artist : library.getArtists()) {
				for(Album album : artist.getAlbums()) {
					if (album.getName().equals(albumName)) {
						foundAlbum = album;
						break;
					}
					else {
						System.out.println("Album does not exist");
					}
				}

			}

		} catch(Exception e) {

			System.out.print(e);
		}
		return foundAlbum;
	}

	/**
	 * Getter for list of albums from library json file
	 * @return list of all albums
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
	 * Getter for specific album's song	
	 * @param {String} songName - Name of the song being searched for 
	 * @return song
	 */
	public Song getSong(String songName) {
		Song foundSong = null;
		// Create file object from library
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

			// Get album's song
			for (Artist artist : library.getArtists()) {
				for(Album album : artist.getAlbums()) {
					for(Song song : album.getSongs()) {
						if (song.getTitle().equals(songName)) {
							foundSong = song;
							break;
						}
						else {
							System.out.println("Song does not exist");
						}
					}
				}

			}

		} catch(Exception e) {

			System.out.print(e);
		}
		return foundSong;
	}

	/**
	 * Getter for list of songs from library json file
	 * @return list of all songs
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