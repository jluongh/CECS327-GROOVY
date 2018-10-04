package services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;


import com.google.gson.Gson;
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
			String filePath = "./src/data/userprofile/" + UserID + ".json";;
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			UserProfile response = new Gson().fromJson(br, UserProfile.class);
			return response;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
