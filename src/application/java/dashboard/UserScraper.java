package application.java.dashboard;

public class UserScraper {
	private static String idUtente = null;
	private static String username = null;
	private static String name = null;
	private static String surname = null;
	
	public static void scraper(String idUtente, String username, String name, String surname) {
		UserScraper.idUtente = idUtente;
		UserScraper.username = username;
		UserScraper.name = name;
		UserScraper.surname = surname;
		printInfo();
	}
	
	public static String getIdUtente() {
		return idUtente;
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
	
	public static void printInfo() {
		System.out.println(
			"USER INFO:" + "\n" +
			"id utente: " + getIdUtente() + "\n" +  
			"username: " + getUsername() + "\n" +  
			"nome: " + getName() + "\n" + 
			"cognome: " + getSurname() 
		);
	}
}

