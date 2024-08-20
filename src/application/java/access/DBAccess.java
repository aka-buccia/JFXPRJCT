package application.java.access;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.java.dashboard.UserScraper;
import application.java.general.DBUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DBAccess {
	private final static String location = "jdbc:sqlite:database.db";

	public static boolean loginUser(ActionEvent event, Label errorMessage, TextField usernameTF, TextField passwordTF) throws IOException {
		String username = usernameTF.getText();
		String password = passwordTF.getText();
		System.out.println("username inserito: " + username + "\npassword inserita: " + password);

		Connection connection = DBUtils.connect(location);
		
		if (connection == null)
			return false; //se non è stata stabilita una connessione interrompe l'esecuzione del login
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM Utenti WHERE username = ?");
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			
			if (!resultSet.isBeforeFirst()) { // se non è nel database
				System.out.println("Utente non trovato nel database!");
				GraphicalAnswer.alertMessage(usernameTF, passwordTF, errorMessage, "Username o password errati");
				return false;
			}
			else { // compariamo i dati se esiste l'username
				while (resultSet.next()) {
					String retrievedPassword = resultSet.getString("password");
					if (retrievedPassword.equals(password)) {
						UserScraper.scraper(resultSet.getString("idUtente"), username, resultSet.getString("nome"), resultSet.getString("cognome"));
						return true;
					}
					else {
						System.out.println("La password non coincide!");
						GraphicalAnswer.alertMessage(usernameTF, passwordTF, errorMessage, "username o password errati");
					}
				}
				return false;
			}
		}
		catch (SQLException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + "Errore DB durante verifica dati");
			DBUtils.showError(e);
			return false;
		}
	}
	
	public static boolean signUpUser(ActionEvent event, Label errorMessage, TextField [] data) {
		Connection connection = DBUtils.connect(location);
		
		if (connection == null)
			return false; //se non è stata stabilita una connessione interrompe l'esecuzione del signup
		
		PreparedStatement psInsert = null; 
		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;
		
		try {
			psCheckUserExists = connection.prepareStatement("SELECT * FROM Utenti WHERE username = ?");
			psCheckUserExists.setString(1, data[0].getText());
			resultSet = psCheckUserExists.executeQuery();
			
			// username già usato da un'altro utente
			if (resultSet.isBeforeFirst()) {  
				System.out.println("Questo username è già stato scelto");
				GraphicalAnswer.alertMessage(data[0], errorMessage, "username già scelto");
				return false;
			}
			else {
				psInsert = connection.prepareStatement("INSERT INTO Utenti (username, password, nome, cognome) VALUES (?, ?, ?, ?)");
				psInsert.setString(1, data[0].getText());
				psInsert.setString(2, data[1].getText());
				psInsert.setString(3, data[2].getText());
				psInsert.setString(4, data[3].getText());
				psInsert.executeUpdate();
				System.out.println("Nuovo utente registrato");
				UserScraper.scraper(data[0].getText(), data[1].getText(), data[2].getText(), data[3].getText());
				return true;
			}
		}
		catch (SQLException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + "Errore DB durante registrazione dati");
			DBUtils.showError(e);
			return false;
		}		
	}	
	
}


