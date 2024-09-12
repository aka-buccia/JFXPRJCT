package application.java.dashboard;

// classe con soli metodi statici per salvare a tempo di esecuzione i dati dell'utente che accede
public class UserScraper {
	private static int idUser = 0;
	private static String username = null;
	private static String name = null;
	private static String surname = null;
	
	public static void scraper(int idUser, String username, String name, String surname) {
		UserScraper.idUser = idUser;
		UserScraper.username = username;
		UserScraper.name = name;
		UserScraper.surname = surname;
	}
	
	public static int getIdUser() {
		return idUser;
	}
	
	public static String getUsername() {
		return username;
	}
	
	public static String getName() {
		return name;
	}
	
	public static String getSurname() {
		return surname;
	}
	
	// metodo per svuotare i dati, viene chiamato al momento del logout dell'utente
	public static void removeInfo() {
		idUser = 0;
		username = null;
		name = null;
		surname = null;
	}
}




