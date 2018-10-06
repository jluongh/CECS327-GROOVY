package data.models;

public class User implements Comparable {
	
	//global variables
	private int userID;
	private String username;
	private String password;
	
	public User() {
		//no initialization
	}
	
	/**
	 * Overload constructor for an User object with two arguments
	 * Initializing the username and password
	 * @param username - name of profile user 
	 * @param password - verification of profile user
	 */
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * Getter method for user ID
	 * @return userID
	 */
	public int getUserID() {
		return userID;
	}
	
	/**
	 * Setter method for user ID
	 * @param userID - {int} unique identification for user
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	/**
	 * Getter method for profile user's name
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Setter method for profile user's name
	 * @param username - {String} profile user's name
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Getter method for password
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter method for password
	 * @param password - {String} verification of profile user
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Comparing user for validation
	 * @param o - user object
	 * @return userID
	 */
	@Override
	public int compareTo(Object o) {
		User user = (User) o;
		return this.userID - user.userID;
	}
}