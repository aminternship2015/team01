package com.endava.twitt;


import com.endava.twitt.models.User;
import com.endava.twitt.services.UserServicesInterface;

import org.junit.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ibalanici on 20/10/2015.
 */
public class UserServicesImplementTest {
    private static ClassPathXmlApplicationContext applicationContext;
    private static UserServicesInterface userServicesInterface;
    private User user;

    @BeforeClass
    public static void setUpOnce() {
        applicationContext = new ClassPathXmlApplicationContext("/spring/appServlet/servlet-context-test.xml");
        userServicesInterface = (UserServicesInterface) applicationContext.getBean("userService");
    }

    @AfterClass
    public static void tearDownOnce() {
        applicationContext.close();
    }

    //This test I'll fail to test JIRA. I changed "testUser" to "testUser1"
    @Before
    public void setUp() throws Exception {
        user = (User) applicationContext.getBean("testUser1");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testInsertUser() throws Exception {
        userServicesInterface.insertUser(user);
        assertEquals(user.getEmail(), userServicesInterface.getUserByName(user.getEmail()).getEmail());
        userServicesInterface.deleteUser(user.getEmail());
    }

    @Test
    public void testGetUser() throws Exception {
        List<User> userList = userServicesInterface.getUser();
        assertNotNull(userList);
        assertTrue(userList.size() >=0);
    }

    @Test
    public void testDeleteUser() throws Exception {
        userServicesInterface.insertUser(user);
        userServicesInterface.deleteUser(user.getEmail());
        assertNull(userServicesInterface.getUserByName(user.getEmail()));
    }

    @Test
    public void testGetUserByName() throws Exception {
        userServicesInterface.insertUser(user);
        assertNotNull(userServicesInterface.getUserByName(user.getEmail()));
        assertEquals(userServicesInterface.getUserByName(user.getEmail()).getEmail(), user.getEmail());
        userServicesInterface.deleteUser(user.getEmail());
    }

    @Test
    public void testUpdateUser() throws Exception {
        userServicesInterface.insertUser(user);
        user.setFirstName("NewFirstName");
        userServicesInterface.updateUser(user);
        assertEquals(userServicesInterface.getUserByName(user.getEmail()).getFirstName(), user.getFirstName());
        userServicesInterface.deleteUser(user.getEmail());
    }
}