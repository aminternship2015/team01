package com.endava.test;


import com.endava.twitt.models.User;
import com.endava.twitt.services.UserServicesInterface;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by ibalanici on 19/10/2015.
 */
public class UserServicesTest {
    private UserServicesInterface userService;
    private User user;

    private static ClassPathXmlApplicationContext applicationContext;

    @BeforeClass
    public static void setUpOnce() {
        applicationContext = new ClassPathXmlApplicationContext("/spring/appServlet/servlet-context-test.xml");
    }

    @Before
    public void setUp() {
        userService = (UserServicesInterface) applicationContext.getBean("userService");
        user = (User) applicationContext.getBean("testUser");
    }

    @Test
    @Ignore("that test uses all operations")
    public void crudUserServicesTest() {
        userService.insertUser(user);
        assertEquals(userService.getUserByName("test@test.com").getEmail(), user.getEmail());

        User user1 = userService.getUserByName("test@test.com");
        user1.setFirstName("Vasea");
        userService.updateUser(user1);

        assertEquals(userService.getUserByName("test@test.com").getFirstName(), user1.getFirstName());

        userService.deleteUser("test@test.com");
        assertNull(userService.getUserByName("test@test.com"));

    }
    
    @Test
    public void insertUserTest() {
        userService.insertUser(user);
        assertEquals(userService.getUserByName("test@test.com").getEmail(), user.getEmail());   
    }
    
    @Test
    public void updateUserTest() {  
    	
        User user1 = userService.getUserByName("ion@rosca.com");
        user1.setFirstName("Ion");
        userService.updateUser(user1);
        assertEquals(userService.getUserByName("ion@rosca.com").getFirstName(), user1.getFirstName());    
    }
        
    @Test
    public void insertDeliteUserTest() {
        userService.insertUser(user);
        assertEquals(userService.getUserByName("test@test.com").getEmail(), user.getEmail());

        userService.deleteUser("test@test.com");
        assertNull(userService.getUserByName("test@test.com"));
    }
    
    @Test
    public void deleteUserTest() {       
        userService.deleteUser("test@test.com");
        assertNull(userService.getUserByName("test@test.com"));
    }

}
