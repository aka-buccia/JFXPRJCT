package application.java.exercise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.java.dashboard.UserScraper;
import application.java.general.DBUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DBExercise {
	private final static String location = "jdbc:sqlite:database.db";
	
	
	public static Exercise loadEx(int tipologia) {
		Connection connection = DBUtils.connect(location);
		
		if (connection == null)
			return null;
		
		int idUtente = Integer.parseInt(UserScraper.getIdUtente());
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM Esercizi WHERE tipologia = ?");
			preparedStatement.setInt(1, tipologia);
			resultSet = preparedStatement.executeQuery();
			ArrayList<Exercise> listExercise = new ArrayList<Exercise>();
			
			while(resultSet.next()) {
				Exercise ex = new Exercise(
						Integer.parseInt(resultSet.getString("idEsercizio")),
						Integer.parseInt(resultSet.getString("grado")),
						Integer.parseInt(resultSet.getString("tipologia")),
						Integer.parseInt(resultSet.getString("numero")),
						resultSet.getString("PathTesto"),
						resultSet.getString("risposta1"),
						resultSet.getString("risposta2"));
				listExercise.add(ex);
			}
			
			
			return listExercise.getFirst();
		}
		catch(SQLException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + "Errore DB durante caricamento esercizi");
			DBUtils.showDBError(e);
			return null;
		}
	}
	
	

	
}