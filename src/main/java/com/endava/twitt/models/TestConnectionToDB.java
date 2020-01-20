package com.endava.twitt.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TestConnectionToDB {
	
	private static final Logger logger = LoggerFactory
			.getLogger(TestConnectionToDB.class);	
	
	public TestConnectionToDB() {

		try {
			logger.debug("Try to Conect to Database with name Test.");
			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/test", "root", "admin");			
			boolean reachable = connection.isValid(5);
			
			if (reachable) {
				logger.info("Successful Connection to Database with name Test.");
			}

		} catch (SQLException e) {
			logger.error("Error to conect to Database with name Test: " + e);			

		} 
		
		
		
	}

}
