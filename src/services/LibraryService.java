package services;

import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.*;

import javax.sound.sampled.*;

import data.models.*;

/**
 * 
 * 
 */
public class LibraryService {
	private String fPath = "./src/data/library.json";
	String libraryPath = " ./music/";
	private File[] library = new File(libraryPath).listFiles();

	/**
	 * 
	 */
	public LibraryService() {
		//
	}

	/**
	 * getter for artist's albums	 
	 * @return
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
	 * getter for list of artist from library json file
	 * @return
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


	Artist artist;
	Album album;
	Song song;

	List<Artist> artists = new ArrayList<Artist>();
	List<Album> albums = null;
	List<Song> songs;

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

			// Mapping .wav file properties 
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

			// List of Artists
			artists.add(ar);

			songID++;
		}	

		try (Writer writer = new FileWriter(fPath)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(artists, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}


	}


	private Artist createArtist(int artistID, String artistName, Album al) {
		if(artists.isEmpty()) {//list of albums doesn't exist
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

	private Album createAlbum(int albumID, String albumName, Song so) {
		if(albums.isEmpty()) {//list of albums doesn't exist
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

	private Song createSong(int songID, String songTitle, double minutes) {
		song = new Song();
		song.setSongID(songID);
		song.setTitle(songTitle);
		song.setDuration(minutes);
		return song;
	}

	/**
	 * getter for artist's album	 
	 * @return
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
	 * getter for album's song
	 * @return
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



	//add song into library.json, gets highest songid and increment by 1 to assign new id to new song
	//get duration

	// Getting file property duration (length) and converting to seconds		


	//get everything
	//sent to json
}