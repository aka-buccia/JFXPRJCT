package application.java.dashboard;

import java.io.IOException;
import application.java.general.ControllerUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;

public class ControllerDashboard {
	
	//due arraylist statiche per salvare a tempo di esecuzione esercizi svolti e esercizi totali di ogni tipologia
	//il numero di esercizi totali per ogni tipologia nei due array viene inizializzato arbitrariamente ad un numero diverso da 0 per evitare errori di divisione per 0 
	private static int [] FEresult = {0, 1}; 
	private static int [] PRresult = {0, 1}; 
	//due interi statici per salvare a tempo di esecuzione il livello raggiunto dall'utente in ogni tipologia
	private static int FElevel = 1; 
	private static int PRlevel = 1; 
	
	@FXML
	private Label welcomeTextDashboard;
	
	@FXML
	private Label usernameLabel;
	
	@FXML
	private Label nameLabel;
	
	@FXML
	private Label surnameLabel;
	
	@FXML
	private Label levelFELabel;
	
	@FXML
	private Label levelPRLabel;
	
	@FXML
	private Label progressFELabel;
	
	@FXML
	private Label progressPRLabel;
	
	@FXML
	private Button openFEButton;
	
	@FXML
	private Button openPRButton;
	
	@FXML 
	private ProgressBar FEProgressBar;
	
	@FXML 
	private ProgressBar PRProgressBar;
	
	@FXML
	private ProgressBar progressBarUserInfo;
	
	@FXML
	private Label percentageUserInfo;

	
	//metodo per impostare le scritte della scena Dashboard all'apertura
	public void setWelcomeText() {
		welcomeTextDashboard.setText("Ciao " + UserScraper.getUsername() + "!"); 
		levelFELabel.setText("LIVELLO: " + String.valueOf(FElevel)); 
		levelPRLabel.setText("LIVELLO: " + String.valueOf(PRlevel)); 
		progressFELabel.setText(String.valueOf(FEresult[0] * 100 / FEresult[1]) + "%");  
		progressPRLabel.setText(String.valueOf(PRresult[0] * 100 / PRresult[1]) + "%");  
		FEProgressBar.setProgress((double)FEresult[0] / FEresult[1]); 
		PRProgressBar.setProgress((double)PRresult[0] / PRresult[1]); 
	}
	
	//metodo per aggiornare FEresult, PRresult, FElevel e PRlevel
	public void updateDashboardData() {
		FElevel = DBDashboard.loadProgress(1, FEresult, FElevel); //aggiorna numero esercizi svolti e esercizi totali Trova Errore
		PRlevel = DBDashboard.loadProgress(2, PRresult, PRlevel); //aggiorna numero esercizi svolti e esercizi totali Prevedi Risultato
		
		// disattiva bottoni per avviare gli esercizi se tutti gli esercizi sono stati svolti
		if (FEresult[0] == FEresult[1])  
			openFEButton.setDisable(true);
		
		if (PRresult[0] == PRresult[1])
			openPRButton.setDisable(true);
	}

	//torna alla scena di Login
	public void logout(MouseEvent event){
		UserScraper.removeInfo(); //cancella dati utente salvati a tempo di esecuzione
		ControllerUtils.loadFXML(event, "/application/resources/access/fxml/LoginScene.fxml"); //cambia scena
	}
	
	//passaggio alla scena UserInfo e relativo cambio di controller
	public void switchToUserInfoScene(MouseEvent event) {
		String location = "/application/resources/dashboard/fxml/UserInfoScene.fxml";
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(location)); //nuova istanza di FXMLLoader per caricare la scena UserInfo
			Parent dashboardRoot = loader.load(); //carica il file fxml e restituisce la radice Parent della gerarchia di nodi
		
			ControllerDashboard cd = loader.getController(); // recupera l'istanza del controller associato alla UserInfoScene
			cd.loadUserInfo(); //chiama il metodo del controller per caricare i dati nella UserInfo
			
			ControllerUtils.switchScene((Node) event.getSource(), dashboardRoot); //cambia scena
		}
		catch (IOException | RuntimeException e) {
			ControllerUtils.showControllerError(e, location);
		}
	}
	
	//passaggio alla scena Dashboard e relativo cambio di controller
	public void switchToDashboardScene(MouseEvent event) {
		String location = "/application/resources/dashboard/fxml/DashboardScene.fxml";
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(location)); //nuova istanza di FXMLLoader per caricare la scena Dashboard
			Parent dashboardRoot = loader.load(); //carica il file fxml e restituisce la radice Parent della gerarchia di nodi
		
			ControllerDashboard cd = loader.getController(); // recupera l'istanza del controller associato alla DashboardScene
			cd.updateDashboardData(); //chiama il metodo del controller per aggiornare i dati relativi alla dashboard
			cd.setWelcomeText(); //chiama il metodo del controller per caricare i dati nella Dashboard
		
			ControllerUtils.switchScene((Node) event.getSource(), dashboardRoot); //cambia scena
		}
		catch (IOException | RuntimeException e) {
			ControllerUtils.showControllerError(e, location);
		}
	}
	
	//passaggio alla scena FERulesScene (scena con regole dell'esercizio Trova Errore)
	public void switchToFERulesScene(ActionEvent event) {
		ControllerUtils.loadFXML(event, "/application/resources/exercise/fxml/FERulesScene.fxml");
	}
	
	//passaggio alla scena PRRulesScene (scena con regole dell'esercizio Prevedi Risultato)
	public void switchToPRRulesScene(ActionEvent event) {
		ControllerUtils.loadFXML(event, "/application/resources/exercise/fxml/PRRulesScene.fxml");
	}
	
	//metodo per impostare scritte della scena UserInfo 
	public void loadUserInfo() {
		usernameLabel.setText(UserScraper.getUsername()); //username
		nameLabel.setText(UserScraper.getNome()); //nome
		surnameLabel.setText(UserScraper.getCognome()); //cognome
		updateProgressBar(); //carica barra progressiva esercizi svolti di qualsiasi tipo
	}
	
	//metodo per mostrare barra progressivae numero esercizi svolti sul totale nella scena UserInfo
	public void updateProgressBar() {
		int totalCompleted = FEresult[0] + PRresult[0]; 
		int totalEx = FEresult[1] + PRresult[1]; 
		percentageUserInfo.setText(totalCompleted + " su " + totalEx); //mostra testo con numero esercizi svolti sul totale
		progressBarUserInfo.setProgress((double)totalCompleted / totalEx);  //mostra barra progressiva
	}
}



