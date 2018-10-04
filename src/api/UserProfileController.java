package api;

import data.models.UserProfile;
import services.UserProfileService;

public class UserProfileController {
	
	//global variables
	UserProfileService userProfileService = new UserProfileService();

	/**
	 * Get a user profile 
	 * @param userID - unique identification for user
	 * @return user profile
	 */
	public UserProfile GetUserProfile(int userID) {
		try {
			return userProfileService.GetUserProfile(userID);
		} catch (Exception e) {
			return null;
		}
	}
}