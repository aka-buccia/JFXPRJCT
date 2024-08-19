package application.java.exercise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.java.general.DBUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DBExercise {
	private final static String location = "jdbc:sqlite:database.db";
	
	public static void loadText(int tipologia) {
		Connection connection = DBUtils.connect(location);
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			preparedStatement = connection.prepareStatement(""); //TODO: inserire query sql
			preparedStatement.setString();
			resultSet = preparedStatement.executeQuery();
			
		}
		catch(SQLException e) {
			
		}
	}
	
	

	
}