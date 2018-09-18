package services;

import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import data.models.*;

/**
 * 
 * 
 */
public class UserService {

	private String usersFilePath = "./src/data/users.json";
	/**
	 * 
	 */
	public UserService() {
		//
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	public User getUser(String username) {
		List<User> UserList = getUsers();
		try {
			return UserList.stream().filter(u -> u.getUsername().equals(username)).findFirst().get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public List<User> getUsers() {
		try {
			String filePath = usersFilePath;
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			Type listType = new TypeToken<List<User>>() {}.getType();
			List<User> UserList = new Gson().fromJson(br, listType);
			return UserList;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}
	
	/**
	 * 
	 * @param user
	 */
	public void AddUser(User user) {
		
		int userID = GetLatestUserID() + 1;
		
		// Set new user to new ID
		user.setUserID(userID);
		
		// Add to users.json
		List<User> UserList = getUsers();
		
		// If there are no user, initialize the list
		if (UserList == null) {
			UserList = new ArrayList<User>();
		}
		UserList.add(user);
		
		// Write to users.json
		String filePath = usersFilePath;
		try (Writer writer = new FileWriter(filePath)) {
		    Gson gson = new GsonBuilder().setPrettyPrinting().create();
		    gson.toJson(UserList, writer);
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Create a user profile
		filePath = "./src/data/userprofile/" + userID + ".json";
		
	    File file = new File(filePath);
	    try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
		// Write to userprofile's #.json
		try (Writer writer = new FileWriter(filePath)) {
			// Set up gson writer
		    Gson gson = new GsonBuilder().setPrettyPrinting().create();
		    
		    // Initialize UserProfile
		    UserProfile userProfile = new UserProfile();
		    userProfile.setUserID(userID);
		    // Write UserProfile to file
		    gson.toJson(userProfile, writer);
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private int GetLatestUserID() {
		int id;
	
		List<User> UserList = getUsers();
		if (UserList != null && !UserList.isEmpty()) {
			id = Collections.max(UserList).getUserID();
		}
		else {
			id = -1;
		}

		System.out.println("Last ID: " + id);
		return id;
	}
	
}