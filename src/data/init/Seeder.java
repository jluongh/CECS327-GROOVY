package data.init;

import java.io.FileOutputStream;
import java.util.*;

import com.google.gson.Gson;
import data.models.User;

public class Seeder {
	private static final String UNIX_PATH = "src/data/"; 
	
	public static void main(String[] args) {
		User user1 = new User("user1", "cecs327", null);
		User user2 = new User("user2", "cecs327", null);
		
		List<User> users = new ArrayList<User>();
		users.add(user1);
		users.add(user2);
		
		Gson gson = new Gson();
		
		try {
			
			FileOutputStream out = new FileOutputStream(UNIX_PATH + "store.txt");
			out.write(gson.toJson(users).getBytes());
			out.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
