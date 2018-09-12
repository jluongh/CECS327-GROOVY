package data.init;

import java.io.FileOutputStream;
import java.util.*;

import com.google.gson.Gson;
import data.models.*;

public class Seeder {
	private static final String UNIX_PATH = "src/data/"; 
	
	public static void main(String[] args) {
		
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
}
