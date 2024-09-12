package application.java.exercise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.java.dashboard.UserScraper;
import application.java.general.DBUtils;


public class DBExercise {
	private final static String location = "jdbc:sqlite:database.db";
	
	// metodo per caricare l'esercizio in base alla tipologia
	public static Exercise loadEx(int tipologia) {
		try (Connection connection = DBUtils.connect(location)) { // connessione con il database
			
			if (connection == null)
				return null; // connessione fallita
			
			int idUtente = UserScraper.getIdUtente();
			// creazione di un arraylist contenente tutti gli esercizi della tipologia passata come parametro
			ArrayList<Exercise> exerciseList = extractExercise(connection, "SELECT * FROM Esercizi WHERE tipologia = ?", tipologia);
			discardExercise(connection, exerciseList, idUtente);
			
			if (exerciseList.isEmpty())
				return null;  // tutti gli esercizi sono stati svolti
			else
				return exerciseList.getFirst();
		}
		catch (SQLException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + "Errore DB durante caricamento esercizi");
			DBUtils.showDBError(e);
			return null;
		}
	}
	
	// metodo per memorizzare gli esercizi in una lista
	public static ArrayList<Exercise> extractExercise(Connection connection, String statement, int number) throws SQLException {
		ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();
		
		PreparedStatement preparedStatement = connection.prepareStatement(statement); // preparazione della query attraverso il parametro passato statement
		preparedStatement.setInt(1, number); // imposta il valore number del parametro nella query
		ResultSet resultSet = preparedStatement.executeQuery(); // esecuzione della query
		
		while (resultSet.next()) { // creazione un nuovo oggetto Exercise per ogni esercizio presente in resultSet
			Exercise ex = new Exercise(
					resultSet.getInt("idEsercizio"),
					resultSet.getInt("grado"),
					resultSet.getInt("tipologia"),
					resultSet.getInt("numero"),
					resultSet.getString("PathTesto"),
					resultSet.getString("risposta1"),
					resultSet.getString("risposta2"),
					resultSet.getInt("N1"),
					resultSet.getInt("N2"));
			exerciseList.add(ex);
		}
		
		return exerciseList;
	}
	
	// metodo per memorizzare gli id degli esercizi svolti dall'utente in un set
	public static void discardExercise(Connection connection, ArrayList<Exercise> exerciseList, int idUtente) throws SQLException {
		TreeSet<Integer> idSet = new TreeSet<Integer>();
		// preparazione della query per ottenere tutti gli esercizi svolti dall'utente con l'idUtente passato come parametro
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM EserciziSvolti WHERE idUtente = ?");
		preparedStatement.setInt(1, idUtente); // imposta il parametro idUtente nella query
		ResultSet resultSet = preparedStatement.executeQuery(); // esecuzione della query
		
		while (resultSet.next()) {
			idSet.add(resultSet.getInt("idEsercizio")); // aggiunta dell'idEsercizio dell'esercizio svolto in idSet
		}
		
		// rimozione da exerciseList di ogni esercizio il cui id è presente in idSet
		exerciseList.removeIf(ex -> idSet.contains(ex.getIdEsercizio()));
	}
	
	public static boolean updateCompletedEx(int idEsercizio) { // aggiornamento di EserciziSvolti all'interno del database
		int idUtente = UserScraper.getIdUtente(); // ottiene l'id dell'utente 
		PreparedStatement psInsert = null;
		
		try (Connection connection = DBUtils.connect(location)) { // tentativo di connessione al database
			if (connection == null)
				return false; // connessione fallita
			
			// preparazione della query per ottenere tutti gli esercizi svolti dall'utente con l'idUtente passato come parametro
			psInsert = connection.prepareStatement("INSERT INTO EserciziSvolti (idEsercizio, idUtente) VALUES (?, ?)");
			psInsert.setInt(1, idEsercizio); // imposta il parametro idEsercizio nella query
			psInsert.setInt(2, idUtente); // imposta il parametro idUtente nella query
			psInsert.executeUpdate(); // inserimento dati in EserciziSvolti
			
			return true;
		}
		catch (SQLException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + "Errore DB durante registrazione esercizio svolto");
			DBUtils.showDBError(e);
			return false;
		}	
	}
}







