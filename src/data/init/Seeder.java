package data.init;

import java.io.FileOutputStream;
import java.util.*;

import com.google.gson.Gson;
import data.models.*;

public class Seeder {
	private static final String UNIX_PATH = "src/data/"; 
	
	public static void main(String[] args) {
		seedLibrary();
		seedStore();	
	}
	
	private static void seedStore() {
		// Create Date object
		Calendar today = Calendar.getInstance();
		today.clear(Calendar.HOUR); 
		today.clear(Calendar.MINUTE); 
		today.clear(Calendar.SECOND);
		Date todayDate = today.getTime();
		
		// Create playlists
		Playlist playlist1 = new Playlist(1, "80s love songs", Collections.emptyList(), 16, "1 hr 2 min", todayDate);
		Playlist playlist2 = new Playlist(2, "Bangerz", Collections.emptyList(), 11, "45 min", todayDate);
		
		List<Playlist> playlists = new ArrayList<Playlist>();
		playlists.add(playlist1);
		playlists.add(playlist2);
		
		// Create users
		User user1 = new User("user1", "cecs327", playlists);
		User user2 = new User("user2", "cecs327", playlists);
		User user3 = new User("user3", "cecs327", Collections.emptyList());
		
		List<User> users = new ArrayList<User>();
		users.add(user1);
		users.add(user2);
		users.add(user3);
		
		// Create store
		Store store = new Store(users);
		
		
		try {
			Gson gson = new Gson();
		
			FileOutputStream out = new FileOutputStream(UNIX_PATH + "store.json");
			out.write(gson.toJson(store).getBytes());
			out.close();
		
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private static void seedLibrary() {
		//TODO : Will need function to convert length of song to string and to total the duration for playlists 
		//TODO : Auto generate song name, id based on what's in arraylist
		//TODO : Auto generate name and duration based on title and length of .wav file properties
		// Create songs
		Song song1 = new Song(1, "Everybody Wants To Rule The World", "4 min 12 sec");
		Song song2 = new Song(2, "September", "3 min 34 sec");
		Song song3 = new Song(3, "Dancing Queen", "3 min 51 sec");
		Song song4 = new Song(4, "Bohemian Rhapsody", "6 min 6 sec");
		Song song5 = new Song(5, "Mamma Mia", "3 min 30 sec");
		Song song6 = new Song(6, "Let's Groove", "3 min 55 sec");
		
		List<Song> GreatestHits = new ArrayList<Song>();
		GreatestHits.add(song2);
		GreatestHits.add(song6);
		
		List<Song> StoneColdClassics = new ArrayList<Song>();
		StoneColdClassics.add(song4);
		
		List<Song> Abba = new ArrayList<Song>();
		Abba.add(song5);
		
		List<Song> Arrival = new ArrayList<Song>();
		Arrival.add(song3);
		
		List<Song> SongsFromTheBigChair = new ArrayList<Song>();
		SongsFromTheBigChair.add(song1);
		
		
		//TODO : Auto generate album name, id based on what's in arraylist
		//TODO : Auto generate album based on album in .wav file properties
		// Create albums
		Album album1 = new Album(1, "Greatest Hits", GreatestHits);
		Album album2 = new Album(2, "Stone Cold Classics", StoneColdClassics);
		Album album3 = new Album(3, "Abba", Abba);
		Album album4 = new Album(4, "Arrival", Arrival);
		Album album5 = new Album(5, "Songs From The Big Chair", SongsFromTheBigChair);
		
		List<Album> ABBA = new ArrayList<Album>();
		ABBA.add(album3);
		ABBA.add(album4);
		
		List<Album> EarthWindAndFire = new ArrayList<Album>();
		EarthWindAndFire.add(album1);
		
		List<Album> Queen = new ArrayList<Album>();
		Queen.add(album2);
		
		List<Album> TearsForFears= new ArrayList<Album>();
		TearsForFears.add(album5);

		
		// Create artists
		Artist artist1 = new Artist(1, "ABBA", ABBA);
		Artist artist2 = new Artist(2, "Earth, Wind, & Fire", EarthWindAndFire);
		Artist artist3 = new Artist(3, "Queen", Queen);
		Artist artist4 = new Artist(4, "Tears For Fears", TearsForFears); 
		
		List<Artist> artists = new ArrayList<Artist>();		
		artists.add(artist1);
		artists.add(artist2);
		artists.add(artist3);
		artists.add(artist4);
		
		// Create library
		Library library = new Library(artists);
				
		
		try {
			Gson gson = new Gson();
		
			FileOutputStream out = new FileOutputStream(UNIX_PATH + "library.json");
			out.write(gson.toJson(library).getBytes());
			out.close();
		
		} catch (Exception e) {
			System.out.println(e);
		}
				
	}
}



