package com.endava.twitt.dao;

import java.util.List;
import java.util.Set;

import com.endava.twitt.models.Follow;

public interface FollowDaoInterface {

	public void insertFollow(Follow follow);

	public List<Follow> getFollows();

	public List<Follow> getFollowByUser(String userEmail);

	public void deleteUserFollow(Follow follow);
	
	public void deleteAllUserFollow(String user_email);

}
