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
	private final static String location = "jdbc:sqlite:database.db";
	
	public static int loadProgress(int tipologia, int [] progress, int level) {
		
		try (Connection connection = DBUtils.connect(location)){
			
			if (connection == null)
				return 0;
			
			int idUtente = UserScraper.getIdUtente();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Esercizi WHERE tipologia = ?");
			preparedStatement.setInt(1, tipologia);
			ResultSet allExerciseResult = preparedStatement.executeQuery();
			
			progress[1] = 0;
			progress[0] = 0;
			
			preparedStatement = connection.prepareStatement("SELECT * FROM EserciziSvolti WHERE idUtente = ?");
			preparedStatement.setInt(1, idUtente);
			ResultSet doneExerciseResult = preparedStatement.executeQuery();
			
			Set<Integer> idEserciziSvolti = new HashSet<>();
            while (doneExerciseResult.next()) {
                idEserciziSvolti.add(doneExerciseResult.getInt("idEsercizio"));
            }
            
            while (allExerciseResult.next()) {
            	progress[1] ++;
            	if (idEserciziSvolti.contains(allExerciseResult.getInt("idEsercizio"))) {
            		progress[0] ++;
            		if (allExerciseResult.getInt("grado") > level)
            			level = allExerciseResult.getInt("grado");		
            	}		
			}
            
            return level;

		}
		catch (SQLException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + "Errore DB durante caricamento progressi");
			DBUtils.showDBError(e);
			return 0;
		}
	}
}

