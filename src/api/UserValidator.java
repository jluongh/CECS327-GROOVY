package api;

import java.util.*;

import data.models.User;
import services.UserService;

public class UserValidator {
	
	private int MAX_LENGTH = 15;

	/**
	 * 
	 */
	public UserValidator() {
		
	}
	
	/**
	 * 
	 * @param textField
	 * @return
	 */
	public boolean isValid(String textField) {
		if (textField.equals("")) {
			return false;
		} else if (textField.length() > MAX_LENGTH) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 
	 * @param inUsername
	 * @param inPassword
	 * @return
	 */
	public boolean isValidCredentials(String inUsername, String inPassword) {
		
		UserService us = new UserService();
		List<User> users = us.getUsers();
		
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
}
