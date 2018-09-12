package data.models;

import java.util.*;

public class Store {
	
	private List<User> users;
	
	public Store() {
		
	}

	public Store(List<User> users) {
		this.users = users;
	}
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
