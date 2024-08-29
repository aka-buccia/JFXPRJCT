package application.java.dashboard;

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
		System.out.println(printInfo());
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
	
	public static String printInfo() {
		return "USER INFO:" + "\n" +
				"id utente: " + getIdUtente() + "\n" +  
				"username: " + getUsername() + "\n" +  
				"nome: " + getNome() + "\n" + 
				"cognome: " + getCognome();
	}
	
	public static void removeInfo() {
		idUtente = 0;
		username = null;
		nome = null;
		cognome = null;
	}
}


