package api;

import java.util.List;

import data.models.User;
import services.UserService;

public class ConnectUser 
{
	UserService us = new UserService();
	List<User> users = us.getUsers();
	User currentUser;
	public ConnectUser()
	{

	}
	
	public User getUser(String username)
	{
		for(int i = 0; i<users.size();i++)
		{
			if(username.equals(users.get(i).getUsername()))
			{
				currentUser = users.get(i);
			}
		}
		return currentUser;
	}
}
