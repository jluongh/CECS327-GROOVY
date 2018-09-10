package services;

import java.io.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import data.models.*;

/**
 * Just the blueprint for the login logic.
 * We can organize and talk about what goes in a service later..
 * 
 * Please revise for improvements in functionality and optimization.
 * 
 */
public class AccountService {

	private String fPath = "./src/data/store.txt";
	
	public AccountService() {
		
	}
	
	public String checkCredentials(String inUsername, String inPassword) {
		
		String response = "";
		
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
			
			List<User> users = gson.fromJson(json, listType);
			
			// Check if inputs equal store information
			for (User user : users) {
				if (inUsername.equals(user.getUsername())) {
					if (inPassword.equals(user.getPassword())) {
						response = "Successful";
						break; // TODO: Improve logic... maybe use while loop
					} else {
						response = "Error";
					}
				} else {
					response = "Error";
				}
			}
			
			return response;
			
		} catch(Exception e) {
			
			System.out.print(e);
			return response;
			
		}
	}
}
