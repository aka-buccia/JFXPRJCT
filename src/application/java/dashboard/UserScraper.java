package application.java.dashboard;

public class UserScraper {
	private int idUtente = 0;
	private String username = null;
	private String name = null;
	private String surname = null;
	
	public UserScraper(int idUtente, String username, String name, String surname) {
		this.idUtente = idUtente;
		this.username = username;
		this.name = name;
		this.surname = surname;
		ControllerDashboard controllerDashboard = new ControllerDashboard();
		controllerDashboard.setWelcomeText(username);
		this.printInfo();
	}
	
	public int getIdUtente() {
		return this.idUtente;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getSurname() {
		return this.surname;
	}
	
	public void printInfo() {
		System.out.println(
			"USER INFO:" + "\n" +
			"id utente: " + this.getIdUtente() + "\n" +
			"username: " + this.getUsername() + "\n" +  
			"nome: " + this.getName() + "\n" + 
			"cognome: " + this.getSurname() 
		);
	}
}





