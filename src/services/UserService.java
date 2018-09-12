package services;

import java.io.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import data.models.*;

/**
 * 
 * 
 */
public class UserService {

	private String fPath = "./src/data/store.txt";
	
	/**
	 * 
	 */
	public UserService() {
		//
	}
	
	/**
	 * 
	 * @return
	 */
	public List<User> getUsers() {
		
		List<User> users;
		
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
			Type listType = new TypeToken<List<User>>() {}.getType();
			Gson gson = new Gson();
			
			users = gson.fromJson(json, listType);
			
			return users;
			
		} catch(Exception e) {
			
			System.out.print(e);
			
			return Collections.emptyList();
			
		}
	}
}