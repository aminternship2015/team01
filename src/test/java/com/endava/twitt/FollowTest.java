package com.endava.twitt;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.endava.twitt.services.FollowServiceInterface;
import com.endava.twitt.services.TweetServiceInterface;
import com.endava.twitt.services.UserServicesInterface;

public class FollowTest {
	
	private FollowServiceInterface followService;

	@Autowired(required = true)
	@Qualifier(value = "followService")
	public void setFollowService(FollowServiceInterface followService) {
		this.followService = followService;
	}
	
	private UserServicesInterface userService;

	@Autowired(required = true)
	@Qualifier(value = "userService")
	public void setUserService(UserServicesInterface userService) {
		this.userService = userService;
	}

	private TweetServiceInterface tweetService;

	@Autowired(required = true)
	@Qualifier(value = "tweetService")
	public void setTweetService(TweetServiceInterface tweetService) {
		this.tweetService = tweetService;
	}
	
	@Test
	public void insertFollowTest() {
		
	}
	
	
	@Test
	public void getFollowsTest() {
		
	}
	
	@Test
	public void getFollowByUserTest() {
		
	}
	
	@Test
	public void deleteUserFollowTest() {
		
	}
	
	@Test
	public void deleteAllUserFollowTest(){
		
	}

}
