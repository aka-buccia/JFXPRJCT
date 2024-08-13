package application.java.access;

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

public class DBUtils {
	private final static String location = "jdbc:sqlite:database.db";

	
	public static void signUpUser(ActionEvent event, Label errorMessage, TextField [] data) {
		
		Connection connection = connect(location);
		PreparedStatement psInsert = null; 
		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;
		
		try {
			psCheckUserExists = connection.prepareStatement("SELECT * FROM Utenti WHERE username = ?");
			psCheckUserExists.setString(1, data[0].getText());
			resultSet = psCheckUserExists.executeQuery();
			
			//username già usato da un'altro utente
			if (resultSet.isBeforeFirst()) {  
				System.out.println("Questo username è già stato scelto");
				GraphicalAnswer.alertMessage(data[0], errorMessage, "username già scelto");
				
			}
			else {
				psInsert = connection.prepareStatement("INSERT INTO Utenti (username, password, nome, cognome) VALUES (? ? ? ?)");
				psInsert.setString(1, data[0].getText());
				psInsert.setString(2, data[1].getText());
				psInsert.setString(3, data[2].getText());
				psInsert.setString(4, data[3].getText());
				psInsert.executeUpdate();
				
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (psCheckUserExists != null) {
				try {
					psCheckUserExists.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (psInsert != null) {
				try {
					psInsert.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (connection != null) {
				try {
					connection.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
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
