package com.endava.twitt.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.endava.twitt.models.User;

@Repository
public class UserDaoImplement implements UserDaoInterface {

    private static final Logger logger = LoggerFactory
            .getLogger(UserDaoImplement.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void insertUser(User user) {
        this.sessionFactory.getCurrentSession().persist(user);
        logger.info("Person saved successfully, Person Details=" + user.getEmail());
    }

    @SuppressWarnings("unchecked")
    public List<User> getUser() {
        Session session = this.sessionFactory.getCurrentSession();
        List<User> userList = (List<User>) session.createQuery("from User").list();
        for (User user : userList) {
            logger.info("User List::" + user.getEmail());
        }
        return userList;
    }

    public void deleteUser(String userEmail) {
        Session session = this.sessionFactory.getCurrentSession();
        User user = (User) session.load(User.class, new String(userEmail));
        if (null != user) {
            session.delete(user);
        }
        logger.info("Person deleted successfully, person details=" + user.getEmail());
    }

    public User getUserByName(String name) {

        try {
            Session session = this.sessionFactory.getCurrentSession();
            User user = (User) session.get(User.class, new String(name));
            logger.info("Person loaded successfully, Person details=" + user.getEmail());
            return user;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public void updateUser(User user) {
        this.sessionFactory.getCurrentSession().update(user);
        logger.info("Person updated successfully, Person Details=" + user.getEmail());
    }

    public User loginUser(String userEmail, String password) {
        User user = this.getUserByName(userEmail);
        if ((user != null) && user.getPassword().equals(password)) {
            logger.info("Person was verified successfully, Person details=" + user.getEmail());
            return user;
        }
        return null;
    }

}
