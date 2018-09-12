package api;

/**
 * For testing purposes only!!
 * 
 * 
 * @author trishaechual
 *
 */
public class Test {
	public static void main(String[] args) {
		String username = "user1";
		String password = "cecs327";
		
		UserValidator uv = new UserValidator();
		System.out.println(uv.isValid(username));
		System.out.println(uv.isValid(password));
		uv.isValidCredentials(username, password);
	}
}