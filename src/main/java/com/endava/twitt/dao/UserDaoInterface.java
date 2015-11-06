package com.endava.twitt.dao;

import java.util.List;

import com.endava.twitt.models.User;

public interface UserDaoInterface {

	public void insertUser(User user);

	public List<User> getUser();

	public void deleteUser(String userEmail);
	
	public User getUserByName(String name);
	
	public User getUserById(Integer id);

	public void updateUser(User user);
	
	public User loginUser(String userEmail, String password);

}
