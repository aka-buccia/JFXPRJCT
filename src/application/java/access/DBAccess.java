package application.java.access;

import java.sql.Connection;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DBAccess {
	private final static String location = "jdbc:sqlite:database.db";

	public static boolean loginUser(ActionEvent event, Label errorMessage, ArrayList<TextField> data){
		String username = data.get(0).getText();
		String password = data.get(1).getText();
		

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		
		try (Connection connection = DBUtils.connect(location)) {
			
			if (connection == null)
				return false; //se non è stata stabilita una connessione interrompe l'esecuzione del login
			
			
			preparedStatement = connection.prepareStatement("SELECT * FROM Utenti WHERE username = ?");
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			
			if (!resultSet.isBeforeFirst()) { // se non è nel database
				System.out.println("Utente non trovato nel database!");
				GraphicalAnswer.alertMessage(data, errorMessage, "Username o password errati");
				return false;
			}
			else { // compariamo i dati se esiste l'username
				while (resultSet.next()) {
					String retrievedPassword = resultSet.getString("password");
					if (retrievedPassword.equals(password)) {
						UserScraper.scraper(resultSet.getInt("idUtente"), username, resultSet.getString("nome"), resultSet.getString("cognome"));
						return true;
					}
					else {
						System.out.println("La password non coincide!");
						GraphicalAnswer.alertMessage(data, errorMessage, "Username o password errati");
					}
				}
				return false;
			}
		}
		catch (SQLException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + "Errore DB durante verifica dati");
			DBUtils.showDBError(e);
			return false;
		}
	}
	
	public static boolean signUpUser(ActionEvent event, Label errorMessage, ArrayList<TextField> data) {
		String username = data.get(0).getText();
		String password = data.get(1).getText();
		String name = data.get(2).getText();
		String surname = data.get(3).getText();
		
		PreparedStatement psInsert = null; 
		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;
		
		try (Connection connection = DBUtils.connect(location)) {
			
			if (connection == null)
				return false; //se non è stata stabilita una connessione interrompe l'esecuzione del signup
			
			psCheckUserExists = connection.prepareStatement("SELECT * FROM Utenti WHERE username = ?");
			psCheckUserExists.setString(1, username);
			resultSet = psCheckUserExists.executeQuery();
			
			// username già usato da un'altro utente
			if (resultSet.isBeforeFirst()) {  
				System.out.println("Questo username è già stato scelto");
				GraphicalAnswer.alertMessage(new ArrayList<TextField>(data.subList(0, 1)), errorMessage, "username già scelto");
				return false;
			}
			else {
				psInsert = connection.prepareStatement("INSERT INTO Utenti (username, password, nome, cognome) VALUES (?, ?, ?, ?)");
				psInsert.setString(1, username);
				psInsert.setString(2, password);
				psInsert.setString(3, name);
				psInsert.setString(4, surname);
				psInsert.executeUpdate();
				return true;
			}
		}
		catch (SQLException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + "Errore DB durante registrazione dati");
			DBUtils.showDBError(e);
			return false;
		}		
	}	
	
}




