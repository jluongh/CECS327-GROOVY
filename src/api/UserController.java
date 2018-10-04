package api;

import java.util.*;

import data.models.User;
import services.UserService;

public class UserController {
	
	//global variables
	private int MAX_LENGTH = 15;
	private UserService us = new UserService();
	private List<User> users = us.getUsers();

	public UserController() {
		//no initialization
	}
	
	/**
	 * Method checks if entered credential is too long or is empty
	 * @param textField - {String} input text
	 * @return boolean
	 */
	public boolean isValid(String textField) { // TODO: Trish - create separate methods for pw and un
		if (textField.equals("")) {
			return false;
		} else if (textField.length() > MAX_LENGTH) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Method checks if username and password is valid/stored
	 * @param inUsername - {String} username input text
	 * @param inPassword - {String} password input text
	 * @return boolean
	 */
	public boolean isValidCredentials(String inUsername, String inPassword) {
		
		boolean isMatch = false;
		
		if (!users.isEmpty()) {
			
			Iterator it = users.iterator();
			
			while(it.hasNext() && !isMatch) {
				
				User tempUser = (User) it.next();
				
				if (inUsername.equals(tempUser.getUsername())
						&& inPassword.equals(tempUser.getPassword())) {
					
					isMatch = true;
				}
			}
		}
		
		return isMatch;
	}
	
	/**
	 * Gets user
	 * @param username
	 * @return user
	 */
	public User getUser(String username)
	{
		for (User user : users)
		{
			if (username.equals(user.getUsername()))
				return user;
		}
		return null;
	}
}
