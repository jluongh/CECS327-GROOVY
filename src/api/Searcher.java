package api;

import java.util.*;
import data.models.*;
import services.UserService;

public class Searcher {

	/**
	 * 
	 */
	public Searcher() {
		
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public SearchResult find(String query) {
		
		SearchResult sr = new SearchResult();
		
		UserService us = new UserService();
		
		sr.setUsers(findFromUsersHelper(us, query));  // CHANGE TO Artists
		sr.setPlaylists(findFromPlaylistsHelper(us, query)); // CHANGE TO Albums
		// call helper for Songs
		
		return sr;
	}
	
	/**
	 * 
	 * @param us
	 * @param query
	 * @return
	 */
	private List<User> findFromUsersHelper(UserService us, String query) {
		
		List<User> users = new ArrayList<User>();
		users = us.getUsers();
		
		Collections.sort(users, new Comparator<User>() {
	        
			@Override
			public int compare(User a, User b) {
				
				if (a.getUsername().contains(query) && b.getUsername().contains(query)) 
					return a.getUsername().compareTo(b.getUsername());
				
			    if (a.getUsername().contains(query) && !b.getUsername().contains(query)) 
			    	return -1;
			    
			    if (!a.getUsername().contains(query) && b.getUsername().contains(query)) 
			    	return 1;
			            
			    return 0;
			}
		});
		
//		for (User u : users) {
//		    System.out.println(u.getUsername());
//		}
		
		return users;
	}
	
	/**
	 * 
	 * @param us
	 * @param query
	 * @return
	 */
	private List<Playlist> findFromPlaylistsHelper(UserService us, String query) {
		
		List<Playlist> playlists = new ArrayList<Playlist>();
		playlists = us.getAllPlaylists();
		
		Collections.sort(playlists, new Comparator<Playlist>() {
	        
			@Override
			public int compare(Playlist a, Playlist b) {
				
				if (a.getName().contains(query) && b.getName().contains(query)) 
					return a.getName().compareTo(b.getName());
				
			    if (a.getName().contains(query) && !b.getName().contains(query)) 
			    	return -1;
			    
			    if (!a.getName().contains(query) && b.getName().contains(query)) 
			    	return 1;
			            
			    return 0;
			}
		});

//		for (Playlist p : playlists) {
//		    System.out.println(p.getName());
//		}
		
		return playlists;
	}
}
