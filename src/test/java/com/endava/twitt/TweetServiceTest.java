package com.endava.twitt;


import com.endava.twitt.models.Tweets;
import com.endava.twitt.models.User;
import com.endava.twitt.services.TweetServiceInterface;
import com.endava.twitt.services.UserServicesInterface;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by ibalanici on 20/10/2015.
 */
public class TweetServiceTest {
	private ClassPathXmlApplicationContext applicationContext;
	private TweetServiceInterface tweetService;
	private UserServicesInterface userService;
	private Tweets tweet;
	private User user;

	@Before
	public void setup() {
		applicationContext = new ClassPathXmlApplicationContext(
				"/spring/appServlet/servlet-context-test.xml");
	}

	@Test
	public void insertTweetsTest() {
		tweetService = (TweetServiceInterface) applicationContext
				.getBean("tweetService");
		assertNotNull(tweetService);
		userService = (UserServicesInterface) applicationContext
				.getBean("userService");
		assertNotNull(userService);
		user = userService.getUserByName("testInsertTweet@testTweet.com");
		tweet = (Tweets) applicationContext.getBean("testTweet");
		tweet.setUser(user);

		tweetService.insertTweets(tweet);
		assertTrue(tweet.getId() > 0);
	}

	@Test
	public void getAllTweetsTest() {
		tweetService = (TweetServiceInterface) applicationContext
				.getBean("tweetService");
		assertNotNull(tweetService);
		List<Tweets> tweetsList = tweetService.getTweets();
		assertNotNull(tweetsList);
		assertTrue(tweetsList.size() >= 0);
	}

	@Test
	public void updateTweetTest() {
		tweetService = (TweetServiceInterface) applicationContext
				.getBean("tweetService");
		assertNotNull(tweetService);
		userService = (UserServicesInterface) applicationContext
				.getBean("userService");
		assertNotNull(userService);
		user = userService.getUserByName("testInsertTweet@testTweet.com");

		String updatedTweet = "new descriptiont 34";
		Tweets tweets = new Tweets();
		tweets.setId(465);
		tweets.setUser(user);
		tweets.setDescription(updatedTweet);
		tweets.setPublishedDate(new Date());
		tweetService.updateTweet(tweets);

		User user2 = userService.getUserByName("testInsertTweet@testTweet.com");
		List<Tweets> lisetOfUserTweets = (List<Tweets>) user2.getTweet();
		String newTweet = "";
		for (Tweets tweet : lisetOfUserTweets) {
			if (tweet.getId() == 465) {
				newTweet = tweet.getDescription();
			}
		}
		assertTrue(newTweet.equals(updatedTweet) == true);
	}

	@Test
	public void deleteUserTweetTest() {
		tweetService = (TweetServiceInterface) applicationContext
				.getBean("tweetService");
		assertNotNull(tweetService);
		userService = (UserServicesInterface) applicationContext
				.getBean("userService");
		assertNotNull(userService);
		user = userService.getUserByName("testInsertTweet@testTweet.com");
		tweet = (Tweets) applicationContext.getBean("testTweet");
		tweet.setUser(user);

		tweetService.insertTweets(tweet);

		int id = tweet.getId();
		
		tweetService.deleteUser(tweet);
		
		User user2 = userService.getUserByName("testInsertTweet@testTweet.com");
		List<Tweets> lisetOfUserTweets = (List<Tweets>) user2.getTweet();
		boolean tweetExists = false;
		for (Tweets tweet : lisetOfUserTweets) {
			if (tweet.getId() == id) {
				tweetExists = true;
				break;
			}
		}
		assertFalse(tweetExists);
	}
}
