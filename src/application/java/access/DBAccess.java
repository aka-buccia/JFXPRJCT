package application.java.access;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.java.general.DBUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DBAccess {
	private final static String location = "jdbc:sqlite:database.db";

	public static void loginUser(ActionEvent event, String username, String password) throws IOException {
		System.out.println("username inserito: " + username + "\npassword inserita: " + password);

		Connection connection = DBUtils.connect(location);
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			preparedStatement = connection.prepareStatement("SELECT password FROM Utenti WHERE username = ?");
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			
			if (!resultSet.isBeforeFirst()) { // se non è nel database
				System.out.println("Utente non trovato nel database!");
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Le credenziali fornite non sono corrette!");
				alert.show();
			}
			else { // compariamo i dati se esiste l'username
				while (resultSet.next()) {
					String retrievedPassword = resultSet.getString("password");
					if (retrievedPassword.equals(password)) {
						//UserScraper userScraper = new UserScraper(username, password, resultSet.getString("nome"), resultSet.getString("cognome"));
						//System.out.println("nome => " + resultSet.getString("nome"));
						Controller controller = new Controller();
						controller.switchToDashboardScene(event);
					}
					else {
						System.out.println("La password non coincide!");
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setContentText("Le credenziali fornite non sono corrette!");
						alert.show();
					}
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public static boolean signUpUser(ActionEvent event, Label errorMessage, TextField [] data) {
		Connection connection = DBUtils.connect(location);
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
				return true;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		
	}	
}



