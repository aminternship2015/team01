package com.endava.test;


import com.endava.twitt.models.Tweets;
import com.endava.twitt.models.User;
import com.endava.twitt.services.TweetServiceInterface;
import com.endava.twitt.services.UserServicesInterface;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by ibalanici on 20/10/2015.
 */
public class TweetServiceImplementTest {
    public static final Logger log = LoggerFactory.getLogger(TweetServiceImplementTest.class);
    private ClassPathXmlApplicationContext applicationContext;
    private TweetServiceInterface tweetServiceInterface;
    private UserServicesInterface userServicesInterface;
    private List<Tweets> tweetsList;
    private Tweets tweet;
    private User user;

    @Before
    public void setUp() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("/spring/appServlet/servlet-context-test.xml");
        tweetServiceInterface = (TweetServiceInterface) applicationContext.getBean("tweetService");
        userServicesInterface = (UserServicesInterface) applicationContext.getBean("userService");
    }

    @After
    public void tearDown() throws Exception {
        applicationContext.close();
        tweetServiceInterface = null;
        userServicesInterface = null;
    }

    @Test
    @Ignore (value = "Do not need this testing")
    public void testSetTweetDao() throws Exception {

    }

    @Test
    public void testInsertTweets() throws Exception {
        tweet = (Tweets) applicationContext.getBean("testTweet");
        assertNotNull(tweet);
        log.debug("Get testTweet");
        user = userServicesInterface.getUserByName("igor.balanici@gmail.com");
//        assertNotNull(user);
        log.debug("Loaded user: " + user.getEmail());
        tweet.setUser(user);
        log.debug("Assign tweet to a user");
        tweetServiceInterface.insertTweets(tweet);
        assertTrue(tweet.getId() > 0);
        log.debug("Inserted tweet for user: " + user.getEmail());
    }

    @Test
    public void testGetTweets() throws Exception {
        List<Tweets> tweetsList = tweetServiceInterface.getTweets();
        assertNotNull(tweetsList);
        assertTrue(tweetsList.size() > 0);
        log.debug("Loaded all tweets: " + tweetsList.size());
    }

    @Test
    public void testGetTweetsByUser() throws Exception {
        User user = userServicesInterface.getUserByName("igor.balanici@gmail.com");
//        assertNotNull(user);
        List<Tweets> tweetsList = tweetServiceInterface.getTweetsByUser(user.getEmail());
        assertNotNull(tweetsList);
        assertTrue(tweetsList.size() > 0);
    }

    @Test
    public void testUpdateTweet() throws Exception {
        User user = userServicesInterface.getUserByName("igor.balanici@gmail.com");
//        assertNotNull(user);
        /*not ok, but i need at least 1 tweet in DB to test updateTweet*/
        tweet = (Tweets) applicationContext.getBean("testTweet");
        tweet.setUser(user);
        tweetServiceInterface.insertTweets(tweet);
        assertTrue(tweet.getId() > 0);
        tweet.setDescription("Another description for this tweet");
        tweetServiceInterface.updateTweet(tweet);
    }
}