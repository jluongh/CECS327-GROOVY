package data.Configuration;

import java.io.FileOutputStream;
import data.Models.Account;

/**
 * Seeder seeds file system with initial accounts
 * 
 * 
 * @author trishaechual
 */
public class Seeder {
	// file path (UNIX family)
	private final String UNIX_PATH = "src/data/";
	
	private Account account1;
	private Account account2;
	
	private String username1 = "user1";
	private String password1 = "cecs327";
	
	private String username2 = "user2";
	private String password2 = "cecs327";
	
	public Seeder() {
		account1 = new Account(username1, password1);
		account2 = new Account(username2, password2);
	}
	
	public void seedStore() {
		try {
			
			// Add account 1
			FileOutputStream out = new FileOutputStream(UNIX_PATH + "Account.txt", true);
			out.write(account1.toOutputStream());
			out.close();
			
			// Add account 2
			out = new FileOutputStream(UNIX_PATH + "Account.txt", true);
			out.write(account2.toOutputStream());
			out.close();
			
		} catch(Exception e) {
			
			System.out.println(e);
			
		}
	}
}
