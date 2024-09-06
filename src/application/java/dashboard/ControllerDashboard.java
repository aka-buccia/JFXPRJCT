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
import javafx.scene.input.MouseEvent;

public class ControllerDashboard {
	
	private static int [] FEresult = {0, 9};
	private static int [] PRresult = {0, 9};
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
	
	public void setWelcomeText() {
		welcomeTextDashboard.setText("Ciao " + UserScraper.getUsername() + "!");
		levelFELabel.setText("LIVELLO: " + String.valueOf(FElevel));
		levelPRLabel.setText("LIVELLO: " + String.valueOf(PRlevel));
		progressFELabel.setText(String.valueOf(FEresult[0] * 100 / FEresult[1]) + "%");
		progressPRLabel.setText(String.valueOf(PRresult[0] * 100 / PRresult[1]) + "%");
	}
	
	public void updateDashboardData() {
		FElevel = DBDashboard.loadProgress(1, FEresult, FElevel);
		PRlevel = DBDashboard.loadProgress(2, PRresult, PRlevel);
		
		// disattiva bottoni per avviare gli esercizi se tutti gli esercizi sono stati svolti
		if (FEresult[0] == FEresult[1])  
			openFEButton.setDisable(true);
		
		if (PRresult[0] == PRresult[1])
			openPRButton.setDisable(true);
	}

	public void logout(MouseEvent event){
		UserScraper.removeInfo();
		System.out.println(UserScraper.printInfo()); // non necessario ma utile per capire se è andato tutto a buon fine
		ControllerUtils.loadFXML(event, "/application/resources/access/fxml/LoginScene.fxml");
	}
	
	public void switchToUserInfoScene(MouseEvent event) {
		String location = "/application/resources/dashboard/fxml/UserInfoScene.fxml";
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
			Parent dashboardRoot = loader.load();
		
			ControllerDashboard cd = loader.getController(); // gli da il controller di loader che è ControllerDashboard
			cd.loadUserInfo();
			
			ControllerUtils.switchScene((Node) event.getSource(), dashboardRoot);
		}
		catch (IOException | RuntimeException e) {
			ControllerUtils.showControllerError(e, location);
		}
	}
	
	public void switchToDashboardScene(MouseEvent event) {
		String location = "/application/resources/dashboard/fxml/DashboardScene.fxml";
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
			Parent dashboardRoot = loader.load();
		
			ControllerDashboard cd = loader.getController(); // gli da il controller di loader che è ControllerDashboard
			cd.setWelcomeText();
			cd.updateDashboardData();
		
			ControllerUtils.switchScene((Node) event.getSource(), dashboardRoot);
		}
		catch (IOException | RuntimeException e) {
			ControllerUtils.showControllerError(e, location);
		}
	}
	
	public void switchToFERulesScene(ActionEvent event) {
		ControllerUtils.loadFXML(event, "/application/resources/exercise/fxml/FERulesScene.fxml");
	}
	
	public void switchToPRRulesScene(ActionEvent event) {
		ControllerUtils.loadFXML(event, "/application/resources/exercise/fxml/PRRulesScene.fxml");
	}
	
	public void loadUserInfo() {
		usernameLabel.setText(UserScraper.getUsername());
		nameLabel.setText(UserScraper.getNome());
		surnameLabel.setText(UserScraper.getCognome());
	}
}







