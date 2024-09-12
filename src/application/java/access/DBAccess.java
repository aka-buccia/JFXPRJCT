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
	private final static String location = "jdbc:sqlite:src/application/resources/general/database.db";  //indirizzo database sqlite locale

	//metodo per verifica dati di login
	public static boolean loginUser(ActionEvent event, Label errorMessage, ArrayList<TextField> data){
		String username = data.get(0).getText(); //estrapola username 
		String password = data.get(1).getText(); //estrapola password
		

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		
		try (Connection connection = DBUtils.connect(location)) {  //stabilisce una connessione con il database
			
			if (connection == null)
				return false; //se non è stata stabilita una connessione interrompe l'esecuzione del login
			
			
			//query al database per ottenere tutti i record di Utenti con username combaciante a quello inserito
			preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE username = ?");
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery(); //insieme dei risultati della query
			
			
			// se non vi sono record lo username non è nel database
			if (!resultSet.isBeforeFirst()) { 
				GraphicalAnswer.alertMessage(data, errorMessage, "Username o password errati");  //segnala errore nei dati di login
				return false; //interrompe l'esecuzione del login
			}
			else { //ci sono dei record contenenti username
				
				//ciclo sui record di risultato (che dovrebbero essere uno solo poichè username è campo univoco) per controllare se la password del record combacia con quella inserita
				while (resultSet.next()) { 
					String retrievedPassword = resultSet.getString("password"); //estrapola la password dal record
					if (retrievedPassword.equals(password)) {
						UserScraper.scraper(resultSet.getInt("idUser"), username, resultSet.getString("name"), resultSet.getString("surname")); //salva i dati dell'utente entrato
						return true; //login andato a buon fine
					}
					else {
						GraphicalAnswer.alertMessage(data, errorMessage, "Username o password errati"); //segnala errore nei dati di login
					}
				}
				return false;  //login fallito
			}
		}
		catch (SQLException e) { //errori nello stabilire la connessione o nell'effettuare le query
			Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + "Errore DB durante verifica dati");
			DBUtils.showDBError(e);
			return false;
		}
	}
	
	//metodo per registrazione nuovo utente
	public static boolean signUpUser(ActionEvent event, Label errorMessage, ArrayList<TextField> data) {
		//estrapola username, password, nome e cognome dai TextField
		String username = data.get(0).getText();
		String password = data.get(1).getText();
		String name = data.get(2).getText();
		String surname = data.get(3).getText();
		
		PreparedStatement psInsert = null; 
		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;
		
		try (Connection connection = DBUtils.connect(location)) {  //stabilisce una connessione con il database 
			
			if (connection == null)
				return false; //se non è stata stabilita una connessione interrompe la registrazione del nuovo utente
			
			//query al database per ottenere tutti i record di Utenti con username combaciante a quello inserito
			psCheckUserExists = connection.prepareStatement("SELECT * FROM Users WHERE username = ?");
			psCheckUserExists.setString(1, username);
			resultSet = psCheckUserExists.executeQuery(); //insieme dei risultati della query
			
			// se la query produce risultati username è già stato usato da un'altro utente
			if (resultSet.isBeforeFirst()) {  
				GraphicalAnswer.alertMessage(new ArrayList<TextField>(data.subList(0, 1)), errorMessage, "username già scelto");  //segnala che lo username è già stato scelto
				return false;  //interrompe la registrazione del nuovo utente
			}
			else {
				//inserisce nel database un nuovo record contenente i dati inseriti
				psInsert = connection.prepareStatement("INSERT INTO Users (username, password, name, surname) VALUES (?, ?, ?, ?)");
				psInsert.setString(1, username);
				psInsert.setString(2, password);
				psInsert.setString(3, name);
				psInsert.setString(4, surname);
				psInsert.executeUpdate();
				return true;  //registrazione nuovo utente andata a buon fine
			}
		}
		catch (SQLException e) {  //errori nello stabilire la connessione o nell'effettuare le query
			Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + "Errore DB durante registrazione dati");
			DBUtils.showDBError(e);
			return false;
		}		
	}	
	
}




