package data.init;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import data.models.*;

public class Seeder {
	private static final String filePath = "src/data/"; 
	
	public static void main(String[] args) {
		seedLibrary();
		seedUsers();	
	}
	
	private static void seedUsers() {
	
		// Create Users
		User user1 = new User("user1", "cecs327");
		user1.setUserID(0);
		User user2 = new User("user2", "cecs327");
		user2.setUserID(1);
		
		List<User> users = new ArrayList<User>();
		users.add(user1);
		users.add(user2);
		
		// Create user profiles
		Playlist playlist1 = new Playlist("sample1");
		playlist1.setPlaylistID(0);
		playlist1.setSongCount(0);
		
		Playlist playlist2 = new Playlist("sample2");
		playlist2.setPlaylistID(1);
		playlist2.setSongCount(0);
		
		List<Playlist> playlists = new ArrayList<Playlist>();
		playlists.add(playlist1);
		playlists.add(playlist2);
		
		UserProfile up1 = new UserProfile();
		up1.setUserID(0);
		up1.setPlaylists(playlists);
		
		UserProfile up2 = new UserProfile();
		up2.setUserID(1);
		
		String uFilePath = filePath + "users.json";
		
		try (Writer writer = new FileWriter(uFilePath)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(users, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		uFilePath = filePath + "userprofile/0.json";
		try (Writer writer = new FileWriter(uFilePath)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(up1, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		uFilePath = filePath + "userprofile/1.json";
		try (Writer writer = new FileWriter(uFilePath)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(up2, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void seedLibrary() {
		//TODO : Will need function to convert length of song to string and to total the duration for playlists 
		//TODO : Auto generate song name, id based on what's in arraylist
		//TODO : Auto generate name and duration based on title and length of .wav file properties
		
		// Create songs
		Song song1 = new Song(1, "Everybody Wants To Rule The World", 252000000);
		Song song2 = new Song(2, "September", 200400000);
		Song song3 = new Song(3, "Dancing Queen", 210600000);
		Song song4 = new Song(4, "Bohemian Rhapsody", 363600000);
		Song song5 = new Song(5, "Mamma Mia", 198000000);
		Song song6 = new Song(6, "Let's Groove", 213000000);
		
		
		/* ---------- Abba ---------- */
		// Albums: Abba, Arrival
		// Songs: Mamma Mia in Abba, Dancing Queen in Arrival
		List<Song> songs = new ArrayList<Song>();
		songs.add(song5);
		Album album1 = new Album(1, "ABBA", songs);
		
		songs = new ArrayList<Song>();
		songs.add(song3);
		Album album2 = new Album(2, "Arrival", songs);
		
		List<Album> albums = new ArrayList<Album>();
		albums.add(album1);
		albums.add(album2);
		
		Artist artist1 = new Artist(1, "ABBA");
		artist1.setAlbums(albums);
		
		
		/* ---------- Earth, Wind, & Fire ---------- */
		// Albums: Greatest Hits
		// Songs: September, Let's Groove
		songs = new ArrayList<Song>();
		songs.add(song2);
		songs.add(song6);
		Album album3 = new Album(3, "Greatest Hits", songs);
		
		albums = new ArrayList<Album>();
		albums.add(album3);
		
		Artist artist2 = new Artist(2, "Earth, Wind, & Fire");
		artist2.setAlbums(albums);
		
		/* ---------- Queen ---------- */
		// Albums: Stone Cold Classics
		// Songs: Bohemian Rhapsody
		songs = new ArrayList<Song>();
		songs.add(song4);
		Album album4 = new Album(4, "Stone Cold Classics", songs);
		
		albums = new ArrayList<Album>();
		albums.add(album4);
		
		Artist artist3 = new Artist(2, "Queen");
		artist3.setAlbums(albums);
		
		/* ---------- Tears for Fears ---------- */
		// Album: Songs From the Big Chair
		// Songs: Everybody wants to Rule the World
		songs = new ArrayList<Song>();
		songs.add(song1);
		Album album5 = new Album(5, "Songs from the Big Chair", songs);
		
		albums = new ArrayList<Album>();
		albums.add(album5);
		
		Artist artist4 = new Artist(2, "Tears for Fears");
		artist4.setAlbums(albums);
		
		
		//TODO : Auto generate album name, id based on what's in arraylist
		//TODO : Auto generate album based on album in .wav file properties
		
		
		List<Artist> artists = new ArrayList<Artist>();		
		artists.add(artist1);
		artists.add(artist2);
		artists.add(artist3);
		artists.add(artist4);
		
		
		// Write to file
		String uFilePath = filePath + "library.json";
		
		try (Writer writer = new FileWriter(uFilePath)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(artists, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}



