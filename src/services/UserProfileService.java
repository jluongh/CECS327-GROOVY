package services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import data.models.UserProfile;

public class UserProfileService {
	
	/**
	 * Getting user profile from json file
	 * @param UserID - unique identification for user
	 * @return response
	 */ 
	public UserProfile GetUserProfile(int UserID) {
		try {
			String filePath = "./src/data/userprofile/" + UserID + ".json";
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			UserProfile response = new Gson().fromJson(br, UserProfile.class);
			return response;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Save user profile updates
	 * @param up - {UserProfile} user profile object
	 * @return boolean when json file is created and serialized 
	 */
	public boolean SaveUserProfile(UserProfile up) {
		String filePath = "./src/data/userprofile/" + up.getUserID() + ".json";
		
		System.out.println("saving user profile...");
		
		try (Writer writer = new FileWriter(filePath)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(up, writer);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
