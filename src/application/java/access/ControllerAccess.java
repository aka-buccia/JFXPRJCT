package application.java.access;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import application.java.dashboard.ControllerDashboard;
import application.java.general.ControllerUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;

public class ControllerAccess {
	
	@FXML
	private TextField usernameSignup;
	
	@FXML
	private TextField passwordSignup;
	
	@FXML
	private TextField nameSignup;
	
	@FXML
	private TextField surnameSignup;
	
	@FXML
	private TextField usernameLogin;
	
	@FXML
	private TextField passwordLogin;
	
	@FXML
	private Label errorMessageLogin;
	
	@FXML
	private Label errorMessageSignup;
	
	
	//passaggio alla scena Login (accesso)
	public void switchToLoginScene(ActionEvent event){
		ControllerUtils.loadFXML(event, "/application/resources/access/fxml/LoginScene.fxml");
	}
	
	//passaggio alla scena Signup (registrazione)
	public void switchToSignupScene(MouseEvent event){
		ControllerUtils.loadFXML(event, "/application/resources/access/fxml/SignupScene.fxml");
	}
	
	//passaggio alla scena Dashboard e relativo cambio di controller
	public void switchToDashboardScene(ActionEvent event) {
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
	
	//gestione del login
	public void login(ActionEvent event){
		ArrayList<TextField> data = new ArrayList<TextField>(Arrays.asList(usernameLogin, passwordLogin)); //inserisce in un arraylist i TextField di accesso
		ArrayList<TextField> empty = GraphicalAnswer.missingData(event, data); //salva nell'arraylist i TextField vuoti
		
		//se non ci sono TextField vuoti
		if (empty.isEmpty()) {
			if (DBAccess.loginUser(event, errorMessageLogin, data)) {
				switchToDashboardScene(event); //se l'accesso va a buon fine (username e password corrette) cambia scena
			}
		}
		else {
			GraphicalAnswer.alertMessage(empty, errorMessageLogin, "Dati mancanti"); //segnala i TextField vuoti
		}

	}
	
	//gestione del signup
	public void signup(ActionEvent event){
		ArrayList<TextField> data = new ArrayList<TextField>(Arrays.asList(usernameSignup, passwordSignup, nameSignup, surnameSignup)); //inserisce in un arraylist i TextField di registrazione
		ArrayList<TextField> empty = GraphicalAnswer.missingData(event, data); //riempe l'arraylist con i TextField vuoti
		
		//se non ci sono TextField vuoti
		if (empty.isEmpty()) {  
			if (DBAccess.signUpUser(event, errorMessageSignup, data)) {
				switchToLoginScene(event); //se la registrazione è andata a buon fine cambia scena
				ControllerUtils.showAlertWindow("Registrazione avvenuta con successo!", "Ora puoi procedere con il login");  //comunica l'avvenuta registrazione
			}
		}
		else {
			GraphicalAnswer.alertMessage(empty, errorMessageSignup, "Dati mancanti"); //segnala i TextField vuoti
		}
	}
}






