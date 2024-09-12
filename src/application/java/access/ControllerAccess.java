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
	
	
	//passaggio alla scena di login	(accesso)
	public void switchToLoginScene(ActionEvent event){
		ControllerUtils.loadFXML(event, "/application/resources/access/fxml/LoginScene.fxml");
	}
	
	//passaggio alla scena di signup (registrazione)
	public void switchToSignupScene(MouseEvent event){
		ControllerUtils.loadFXML(event, "/application/resources/access/fxml/SignupScene.fxml");
	}
	
	public void switchToDashboardScene(ActionEvent event) {
		String location = "/application/resources/dashboard/fxml/DashboardScene.fxml";
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
			Parent dashboardRoot = loader.load();
		
			ControllerDashboard cd = loader.getController(); // gli da il controller di loader che è ControllerDashboard
			cd.updateDashboardData();
			cd.setWelcomeText();
		
			ControllerUtils.switchScene((Node) event.getSource(), dashboardRoot);
		}
		catch (IOException | RuntimeException e) {
			ControllerUtils.showControllerError(e, location);
		}
	}
	
	public void login(ActionEvent event){
		ArrayList<TextField> data = new ArrayList<TextField>(Arrays.asList(usernameLogin, passwordLogin));
		ArrayList<TextField> empty = GraphicalAnswer.missingData(event, data);
		
		if (empty.isEmpty()) {
			if (DBAccess.loginUser(event, errorMessageLogin, data)) {
				switchToDashboardScene(event);
			}
		}
		else {
			GraphicalAnswer.alertMessage(empty, errorMessageLogin, "Dati mancanti");
		}

	}
	
	public void signup(ActionEvent event){
		ArrayList<TextField> data = new ArrayList<TextField>(Arrays.asList(usernameSignup, passwordSignup, nameSignup, surnameSignup));
		ArrayList<TextField> empty = GraphicalAnswer.missingData(event, data);
		
		if (empty.isEmpty()) {  //se non mancano dati si procede al signup
			if (DBAccess.signUpUser(event, errorMessageSignup, data)) {
				switchToLoginScene(event);
				ControllerUtils.showAlertWindow("Registrazione avvenuta con successo!", "Ora puoi procedere con il login");
			}
		}
		else {
			GraphicalAnswer.alertMessage(empty, errorMessageSignup, "Dati mancanti");
		}
	}
}






