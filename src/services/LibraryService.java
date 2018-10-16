package services;

import java.io.*;

import com.drew.imaging.riff.RiffProcessingException;
import com.drew.imaging.wav.WavMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.*;

import data.models.*;

public class LibraryService {

	//global variables
	private String fPath = "./src/data/library.json";

	private String libraryPath = "./music/";
	private File[] library;
	
	private Artist artist;
	private Album album;
	private Song song;

	private List<Artist> artists = new ArrayList<Artist>();
	private List<Album> albums;
	private List<Song> songs;
	
	private List<Album> allAlbums = new ArrayList<Album>();
	private List<Artist > allArtists = new ArrayList<Artist>();

	int songID;
	int albumID;
	int artistID;
	
	/**
	 * Creates library json file
	 */
	public LibraryService() {
//		System.out.println(dir(libraryPath));
		library = dir(libraryPath);
		
//		for (File file : library) {
//			System.out.println(file.getName());
//		}
		//createLibrary();
	}
	
	/**
	 * Creates library of a files with only the .wav files located in folder
	 * @param dirName - the directory for the file
	 * @return .wav files
	 */
	private File[] dir (String dirName) {
		File file = new File (dirName);
		return file.listFiles(new FilenameFilter(){
			public boolean accept(File file, String filename) {return filename.endsWith(".wav");}
		});
	}
	
	/**
	 * Creates and makes relations for artists, albums, and songs and stores the information as json file
	 * Assigns unique IDs
	 */
	public void createLibrary(){
		library = dir(libraryPath);		
		
		for (File file : library) {
			String songTitleDesc = null;
			String albumNameDesc = null;
			String artistNameDesc = null;
			double minutes = 0;
			//Mapping .wav file metadata tags into data models
			try {
				Metadata metadata = WavMetadataReader.readMetadata(file);
				Iterable<Directory> dir = metadata.getDirectories();
				
				for (Directory d : dir) {
					for(Tag tag : d.getTags()) {
						if(tag.getTagName().equals("Duration")) {
							String duration = tag.getTagName();
							String durationDesc = tag.getDescription();
							String durationTime = durationDesc.replaceAll(":", "");
							minutes = Double.parseDouble(durationTime)/100;
						}
						if (tag.getTagName().equals("Product")) {
							String albumName = tag.getTagName();
							albumNameDesc = tag.getDescription();
						}
						if (tag.getTagName().equals("Title")) {
							String songTitle = tag.getTagName();
							songTitleDesc = tag.getDescription();
						}
						if (tag.getTagName().equals("Artist")) {
							String artistName = tag.getTagName();
							artistNameDesc = tag.getDescription();
						}
					}	
				}
				
			} catch (RiffProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Create new Song
			Song so = createSong(songTitleDesc, minutes);

			//Creating new album and adding song or adding song to pre-existing album
			Album al = createAlbum(albumNameDesc, so);
			
			//Creating new artist and adding album or adding album to pre-existing artist
			Artist ar = createArtist(artistNameDesc, al);

			//List of Artists
			artists.add(ar);
			
//			System.out.println(minutes);
//			System.out.println(songTitleDesc);
//			System.out.println(albumNameDesc);
//			System.out.println(artistNameDesc);
//			System.out.println("********************************");
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
	 * @param artistName - {String} Name of artist
	 * @param al - {Album} Album object to be added into an artist
	 * @return artist
	 */	
	private Artist createArtist(String artistName, Album al) {
		if (allArtists.isEmpty()) {//first artist
			artist = new Artist();
			artistID++;
			artist.setArtistID(artistID);
			artist.setName(artistName);
			albums = new ArrayList<Album>();
			albums.add(al);
			artist.setAlbums(albums);
			allArtists.add(artist);
		}
		else {
			for (int index = 0; index <= allArtists.size(); index++) { //search array
				if(index == allArtists.size()) { //new artist
					artist = new Artist();
					artistID++;
					artist.setArtistID(artistID);
					artist.setName(artistName);
					albums = new ArrayList<Album>();
					albums.add(al);
					artist.setAlbums(albums);
					allArtists.add(artist);
					break;
				}
				if (allArtists.get(index).getName().equals(artistName)) { //artist exists
					allArtists.get(index).getAlbums().add(al); //add new album to artist
					artist = allArtists.get(index); //return existing artist
					break;
				}	
			}
		}	
//		System.out.println(artist.getArtistID()+ " - "+ artist.getName());
//		System.out.println("*******************************************");
		return artist;
	}
	
	/**
	 * Creates new album or updates existing album
	 * Assigns unique IDs
	 * @param albumName - {String} Name of album
	 * @param so - {Song} Song object to be added into an album
	 * @return album 
	 */	
	private Album createAlbum(String albumName, Song so) {
		if (allAlbums.isEmpty()) {//first album
			album = new Album();
			albumID++;
			album.setAlbumID(albumID);
			album.setName(albumName);
			songs = new ArrayList<Song>();
			songs.add(so);
			album.setSongs(songs);
			allAlbums.add(album);
		}
		else {
			for (int index = 0; index <= allAlbums.size(); index++) { //search array
				if(index == allAlbums.size()) { //new album
					album = new Album();
					albumID++;
					album.setAlbumID(albumID);
					album.setName(albumName);
					songs = new ArrayList<Song>();
					songs.add(so);
					album.setSongs(songs);
					allAlbums.add(album);
					break;
				}
				if (allAlbums.get(index).getName().equals(albumName)) { //album exists
					allAlbums.get(index).getSongs().add(so); //add new song to album
					album = allAlbums.get(index); //return existing album
					break;
				}	
			}
		}	
//		System.out.println(album.getAlbumID()+ " - "+ album.getName());
		return album;
	}
	
	/**
	 * Creates new song
	 * Assigns unique IDs
	 * @param songTitle - {String} Title of song
	 * @param minutes - {double} Length of song
	 * @return song
	 */	
	private Song createSong(String songTitle, double minutes) {
		songID++;
		song = new Song();
		song.setSongID(songID);
		song.setTitle(songTitle);
		song.setDuration(minutes);
//		System.out.println(song.getSongID()+ " - "+ song.getTitle());
		return song;
	}

	/**
	 * Getter for specific artist	
	 * @param artistName - {String} Name of the artist being searched for 
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
			List<Artist> artists = gson.fromJson(json, new TypeToken<List<Artist>>() {}.getType());

			// Get Artist
			for (Artist artist : artists) {
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
			List<Artist> artists = gson.fromJson(json, new TypeToken<List<Artist>>() {}.getType());

			// Get Artists
			List<Artist> allAr = new ArrayList<Artist>();
			for (Artist artist : artists) {
				if (allAr.isEmpty()) { //first artist
					allAr.add(artist);
				}
				else{ //search array
					for(int index = 0; index <= allAr.size(); index++ ) {
						if (index == allAr.size()) { //new artist
							allAr.add(artist);
							break;
						}
						if(allAr.get(index).getArtistID() == artist.getArtistID()) { //artist exist
							break;
						}
					}
				}
			}
			return allAr;
		} catch(Exception e) {
			System.out.print(e);
			return Collections.emptyList();
		}
	}

	/**
	 * Getter for specific artist's album	
	 * @param albumName - {String} Name of the album being searched for 
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
			List<Artist> artists = gson.fromJson(json, new TypeToken<List<Artist>>() {}.getType());

			// TODO: Use while loop /remove break
			// Get artist's album
			for (Artist artist : artists) {
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
			List<Artist> artists = gson.fromJson(json, new TypeToken<List<Artist>>() {}.getType());

			// Get Albums from each Artists
			List<Album> allAl = new ArrayList<Album>();
			for (Artist artist : artists) {
				for (Album album : artist.getAlbums()) {
					if (allAl.isEmpty()) { //first album
						allAl.add(album);
					}
					else{ //search array
						for(int index = 0; index <= allAl.size(); index++ ) {
							if (index == allAl.size()) { //new album
								allAl.add(album);
								break;
							}
							if(allAl.get(index).getAlbumID() == album.getAlbumID()) { //album exist
								break;
							}
						}
					}
				}
			}
			return allAl;
		} catch(Exception e) {
			System.out.print(e);
			return Collections.emptyList();
		}
	}
	
	/**
	 * Getter for specific album's song	
	 * @param songName - {String} Name of the song being searched for 
	 * @return song
	 */
	public Song getSong(int songID) {
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
			List<Artist> artists = gson.fromJson(json, new TypeToken<List<Artist>>() {}.getType());
			
			// Get album's song
			for (Artist artist : artists) {
				for(Album album : artist.getAlbums()) {
					for(Song song : album.getSongs()) {
						if (song.getSongID() == (songID)) {
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
	 * Getter for specific album's song	
	 * @param songName - {String} Name of the song being searched for 
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
			List<Artist> artists = gson.fromJson(json, new TypeToken<List<Artist>>() {}.getType());
			
			// Get album's song
			for (Artist artist : artists) {
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
			List<Artist> artists = gson.fromJson(json, new TypeToken<List<Artist>>() {}.getType());
			
			// Get Albums from each Artists
			List<Song> allSo = new ArrayList<Song>();
			
			for (Artist artist : artists) {
				for (Album album : artist.getAlbums()) {
					for(Song song : album.getSongs()) {
						if (allSo.isEmpty()) { //first song
							allSo.add(song);
						}
						else{ //search array
							for(int index = 0; index <= allSo.size(); index++ ) {
								if (index == allSo.size()) { //new song
									allSo.add(song);
									break;
								}
								if(allSo.get(index).getSongID() == song.getSongID()) { //album exist
									break;
								}
							}
						}
					}
				}
			}
			return allSo;

		} catch(Exception e) {
			System.out.print(e);
			return Collections.emptyList();
		}
	}
}