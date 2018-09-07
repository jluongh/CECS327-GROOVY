package data.init;

import java.io.FileOutputStream;

import com.google.gson.Gson;
import data.models.User;

public class Seeder {
	private static final String UNIX_PATH = "src/data/"; 
	
	public static void main(String[] args) {
		User user1 = new User("user1", "cecs327", null);
		User user2 = new User("user2", "cecs327", null);
		
		Gson gson = new Gson();
		
		try {
			
			FileOutputStream out = new FileOutputStream(UNIX_PATH + "store.txt");
			out.write(gson.toJson(user1).getBytes());
			out.write("\n".getBytes());
			out.write(gson.toJson(user2).getBytes());
			out.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
