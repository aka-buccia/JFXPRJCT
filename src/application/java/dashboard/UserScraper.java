package application.java.dashboard;

public class UserScraper {
	private String username = null;
	private String password = null;
	private String nome = null;
	private String cognome = null;
	
	public UserScraper(String username, String password, String nome, String cognome) {
		this.username = username;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		ControllerDashboard controllerDashboard = new ControllerDashboard();
		controllerDashboard.setWelcomeText(username);
		this.printInfo();
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public String getCognome() {
		return this.cognome;
	}
	
	public void printInfo() {
		System.out.println(
			"USER INFO:" + "\n" +
			"username: " + this.getUsername() + "\n" + 
			"password: " + this.getPassword() + "\n" + 
			"nome: " + this.getNome() + "\n" + 
			"cognome: " + this.getCognome() 
		);
	}
}





