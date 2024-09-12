package application.java.dashboard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.java.general.DBUtils;

public class DBDashboard {
	private final static String location = "jdbc:sqlite:src/application/resources/general/database.db"; //indirizzo database sqlite locale
	
	//metodo per ottenere il numero di esercizi svolti dall'utente
	public static int loadProgress(int type, int [] progress, int level) {
		try (Connection connection = DBUtils.connect(location)){ //stabilisce una connessione con il database
			
			if (connection == null)
				return 0;  //se non è stata stabilita una connessione interrompe l'esecuzione del metodo restituendo valore di fallimento
			
			int idUser = UserScraper.getIdUser(); 
			
			//query al database per ottenere tutti i record degli Esercizi aventi tipologia richiesta
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Exercises WHERE type = ?");
			preparedStatement.setInt(1, type);
			ResultSet allExerciseResult = preparedStatement.executeQuery();
			
			progress[1] = 0; //numero totale esercizi della tipologia
			progress[0] = 0; //numero esercizi completati della tipologia
			
			//query al database per ottenere tutti gli esercizi svolti dall'utente
			preparedStatement = connection.prepareStatement("SELECT * FROM DoneExercises WHERE idUser = ?");
			preparedStatement.setInt(1, idUser);
			ResultSet doneExerciseResult = preparedStatement.executeQuery();
			
			Set<Integer> idDoneExercises = new HashSet<>();
            while (doneExerciseResult.next()) {
                idDoneExercises.add(doneExerciseResult.getInt("idExercise")); //riempe l'insieme con idEsercizi degli esercizi svolti da utente
            }
            
            while (allExerciseResult.next()) {
            	progress[1] ++; //conta numero totale di esercizi della tipologia
            	if (idDoneExercises.contains(allExerciseResult.getInt("idExercise"))) { //se l'esercizio ha id contenuto nell'insieme di quelli già svolti
            		progress[0] ++; //conta numero di esercizi già svolti
            		if (allExerciseResult.getInt("level") > level) //se il livello dell'esercizio svolto è maggiore di quello attuale segnato
            			level = allExerciseResult.getInt("level"); //aumenta il livello degli esercizi svolti
            	}		
			}
            
            return level; //restituisce livello esercizi raggiunto dall'utente
		}
		catch (SQLException e) {  //errori nello stabilire la connessione o nell'effettuare le query
			Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + "Errore DB durante caricamento progressi");
			DBUtils.showDBError(e);
			return 0;
		}
	}
}

