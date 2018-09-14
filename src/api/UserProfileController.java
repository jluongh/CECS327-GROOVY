package api;

import data.models.UserProfile;
import services.UserProfileService;

/**
 * Controller to view/manipulate user profile data
 * @param userID	ID of user
 */
public class UserProfileController {
	UserProfileService userProfileService = new UserProfileService();

	/**
	 * Get a user profile 
	 * @param ID of user
	 * @return	user profile
	 */
	public UserProfile GetUserProfile(int userID) {
		try {
			return userProfileService.GetUserProfile(userID);
		} catch (Exception e) {
			return null;
		}
	}
}