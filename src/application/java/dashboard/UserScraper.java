package application.java.dashboard;

// classe con soli metodi statici per salvare a tempo di esecuzione i dati dell'utente che accede
public class UserScraper {
	private static int idUtente = 0;
	private static String username = null;
	private static String nome = null;
	private static String cognome = null;
	
	public static void scraper(int idUtente, String username, String nome, String cognome) {
		UserScraper.idUtente = idUtente;
		UserScraper.username = username;
		UserScraper.nome = nome;
		UserScraper.cognome = cognome;
	}
	
	public static int getIdUtente() {
		return idUtente;
	}
	
	public static String getUsername() {
		return username;
	}
	
	public static String getNome() {
		return nome;
	}
	
	public static String getCognome() {
		return cognome;
	}
	
	// metodo per svuotare i dati, viene chiamato al momento del logout dell'utente
	public static void removeInfo() {
		idUtente = 0;
		username = null;
		nome = null;
		cognome = null;
	}
}




