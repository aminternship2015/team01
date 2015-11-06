package com.endava.twitt.dao;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
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
		try {
			String email = user.getEmail();
			User user1 = getUserByName(email);
			if (null == user1) {
				logger.debug("Try to save user." + user.getEmail());
				this.sessionFactory.getCurrentSession().persist(user);
			}
		} catch (ConstraintViolationException e) {
			logger.error("Person wasn't saved." + e);
		}
		logger.debug("Person saved successfully, Person Details="
				+ user.getEmail());
	}

	@SuppressWarnings("unchecked")
	public List<User> getUser() {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Query query = session.createQuery("from User");
			List<User> userList = (List<User>) query.list();
			for (User user : userList) {
				logger.debug("User List:" + user.getEmail());
			}
			return userList;
		} catch (NullPointerException e) {
			logger.error("Couldn't list users: " + e);
			return null;
		}

		/* return null; */

	}

	public void deleteUser(String userEmail) {

		try {
			User user = getUserByName(userEmail);
			if (null != user) {
				sessionFactory.getCurrentSession().delete(user);
			}
			logger.debug("Person deleted successfully, person details="
					+ user.getEmail());
		} catch (NullPointerException e) {
			logger.error("Couldn't deleted user." + e);
			return;
		}
	}

	public User getUserByName(String name) {

		try {
			Session session = this.sessionFactory.getCurrentSession();
			User user = (User) session.get(User.class, new String(name));
			logger.debug("Person loaded successfully, Person details="
					+ user.getEmail());
			return user;
		} catch (NullPointerException e) {
			logger.error("Person wasn't loaded with provided name =" + name);
			return null;
		}
	}

	public User getUserById(Integer id) {

		try {
			Session session = this.sessionFactory.getCurrentSession();

			String stringQuery = "Select email FROM User where id_user='" + id
					+ "'";
			Query query = session.createQuery(stringQuery);
			String name = (String) query.uniqueResult();
			System.out.println("Name is: " + name);

			if (name != null) {
				User user = (User) session.get(User.class, new String(name));
				logger.debug("Person loaded successfully, Person details="
						+ user.getEmail());
				return user;
			} else {
				return null;
			}

		} catch (NullPointerException e) {
			logger.error("Person wasn't loaded with provided name =" + id);
			return null;
		}
	}

	public void updateUser(User user) {
		try {
			this.sessionFactory.getCurrentSession().update(user);
			logger.debug("Person updated successfully, Person Details="
					+ user.getEmail());
		} catch (NullPointerException e) {
			logger.error("Couldn't update user." + e);
		}
	}

	public User loginUser(String userEmail, String password) {

		try {
			User user = this.getUserByName(userEmail);
			if ((user != null) && user.getPassword().equals(password)) {
				logger.debug("Person's credentials was verified successfully, Person details="
						+ user.getEmail());
				return user;
			}
		} catch (NullPointerException e) {
			logger.error("Login credentials validation failed with person's email="
					+ userEmail);
			return null;
		}

		return null;

	}

}
