package api;

import data.models.*;

/**
 * For testing purposes only!!
 * 
 * 
 * @author trishaechual
 *
 */
public class Test {
	public static void main(String[] args) {
//		String username = "user1";
//		String password = "cecs327";
//		
//		UserValidator uv = new UserValidator();
//		System.out.println(uv.isValid(username));
//		System.out.println(uv.isValid(password));
//		uv.isValidCredentials(username, password);
		
		Searcher s = new Searcher();
		SearchResult sr = s.find("bang");
		
		for (User u : sr.getUsers()) {
			System.out.println(u.getUsername());
		}
		
		for (Playlist p : sr.getPlaylists()) {
			System.out.println(p.getName());
		}
	}
}