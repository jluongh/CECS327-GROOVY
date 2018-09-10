package services;


/**
 * Here only for testing purposes!!
 * 
 * 
 */
public class TestLogin {

	public static void main(String[] args) {
		
		AccountService as = new AccountService();
		System.out.print(as.checkCredentials("user1", "cecs327"));
		
	}
}
