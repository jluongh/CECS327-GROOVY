package data.Models;

/**
 * Account model for object instantiation
 * 
 * @author trishaechual
 */
public class Account {
	private String username;
	private String password;
	
	public Account() {
		//
	}
	
	public Account(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public byte[] toOutputStream() {
		return (username + "," + password + "\n").getBytes();
	}
}
