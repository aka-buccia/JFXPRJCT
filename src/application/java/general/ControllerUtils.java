package application.java.general;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerUtils {
	public static void showControllerError(Exception e, String location) {
		Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() +
				": Errore nel caricamento della scena" + 
				"\nPercorso scena: '" +
				location + "'" + 
				"\nMessaggio di errore: " + 
				e.getMessage());
	}
}
