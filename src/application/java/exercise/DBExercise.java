package application.java.exercise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DBExercise {
	private final static String location = "jdbc:sqlite:database.db";
	
	public static void loadText(int tipologia) {
		Connection connection = connect(location);
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			preparedStatement = connection.prepareStatement(""); //TODO: inserire query sql
			preparedStatement.setString();
			resultSet = preparedStatement.executeQuery();
			
		}
	}
	
	
	public static Connection connect(String location) {
		Connection connection;
		checkDrivers();
		try {
			connection = DriverManager.getConnection(location);
		}
		catch (SQLException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not connect to SQLite DB at " + location);
			return null;
		}
		
		return connection;
	}

	
	public static boolean checkDrivers(){
		try {
			Class.forName("org.sqlite.JDBC"); //controlla che sia presente sqlite-jdbc
			DriverManager.registerDriver(new org.sqlite.JDBC());
	        return true;
		}
		catch (ClassNotFoundException | SQLException classNotFoundException) {
	        Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not start SQLite Drivers");
	        return false;
	    }
	}
	
}