package application.java.general;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBUtils {
	public static Connection connect(String location) {
		Connection connection;
		if (checkDrivers()) {
			try {
				connection = DriverManager.getConnection(location);
			}
			catch (SQLException e) {
				Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Connessione non riuscita al DB SQLite localizzato: " + location);
				return null;
			}
			
			return connection;
		}
		else {
			return null;
		}
	}

	public static boolean checkDrivers(){
		try {
			Class.forName("org.sqlite.JDBC"); //controlla che sia presente sqlite-jdbc
			DriverManager.registerDriver(new org.sqlite.JDBC());
	        return true;
		}
		catch (ClassNotFoundException | SQLException classNotFoundException) {
	        Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": SQLite Drivers non caricati");
	        return false;
	    }
	}
	
	public static void showError(SQLException e) {
		System.out.println("SQLState: " + e.getSQLState());
		System.out.println("Message: " + e.getMessage());
	}
}
