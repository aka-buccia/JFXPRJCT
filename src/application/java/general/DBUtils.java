package application.java.general;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBUtils {
	// metodo per connettersi al database
	public static Connection connect(String location) {
		Connection connection;
		
		// verifica che i driver SQLite siano disponibili
		if (checkDrivers()) {
			try {
				// tentativo di stabilire la connessione con il database attraverso la location passata come parametro
				connection = DriverManager.getConnection(location);
			}
			catch (SQLException e) { // connessione fallita
				Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Connessione non riuscita al DB SQLite localizzato: " + location);
				return null;
			}
			
			return connection;
		}
		else {
			return null;
		}
	}

	// metodo per controllare se i driver SQLite sono presenti e registrati
	public static boolean checkDrivers(){
		try {
			Class.forName("org.sqlite.JDBC"); // controlla che sia presente sqlite-jdbc
			DriverManager.registerDriver(new org.sqlite.JDBC()); // registra il driver SQLite nel DriverManager
	        return true;
		}
		catch (ClassNotFoundException | SQLException e) { // driver non caricati o registrati correttamente
	        Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": SQLite Drivers non caricati");
	        return false;
	    }
	}
	
	// metodo per mostrare messaggio di errore 
	public static void showDBError(SQLException e) {
		System.out.println("SQLState: " + e.getSQLState());
		System.out.println("Message: " + e.getMessage());
	}
}



