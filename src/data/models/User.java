package data.models;

public class User implements Comparable {
	
	private int userID;
	private String username;
	private String password;
	
	public User() {
		//
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int compareTo(Object o) {
		User user = (User) o;
		return this.userID - user.userID;
	}
	
	
	
}


