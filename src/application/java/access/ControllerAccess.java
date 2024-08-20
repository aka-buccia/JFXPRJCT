package application.java.access;

import java.io.IOException;

import application.java.dashboard.ControllerDashboard;
import application.java.dashboard.UserScraper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControllerAccess {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
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
	
	
	public void switchScene(Event event, Parent root) {
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/application/resources/general/application.css").toExternalForm());
		stage.setScene(scene);
        Platform.runLater(() -> root.requestFocus()); // sposta il focus sulla scena
		stage.show();
	}
	
	public void switchToLoginScene(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/application/resources/access/fxml/LoginScene.fxml"));
		switchScene(event, root);
	}
	
	public void switchToSignupScene(MouseEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/application/resources/access/fxml/SignupScene.fxml"));
		switchScene(event, root);
	}
	
	public void backToLogin(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/application/resources/access/fxml/LoginScene.fxml"));
		switchScene(event, root);
	}
	
	public void switchToDashboardScene(ActionEvent event) throws IOException {
		//root = FXMLLoader.load(getClass().getResource("/application/resources/dashboard/fxml/DashboardScene.fxml"));
		//switchScene(event, root);
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/resources/dashboard/fxml/DashboardScene.fxml"));
		Parent dashboardRoot = loader.load();
		
		ControllerDashboard cd = loader.getController(); // gli da il controller di loader che è ControllerDashboard
		String username = UserScraper.getUsername();
		cd.setWelcomeText(username);
		
		switchScene(event, dashboardRoot);
	}
	
	public void login(ActionEvent event) throws IOException {
		if (DBAccess.loginUser(event, errorMessageLogin, usernameLogin, passwordLogin)) {
			switchToDashboardScene(event);
		}
	}
	
	public void signUp(ActionEvent event) throws IOException{
		TextField [] data = {usernameSignup, passwordSignup, nameSignup, surnameSignup};
		
		if (! GraphicalAnswer.missingData(event, data, errorMessageSignup, "Dati mancanti")) {  //se non mancano dati si procede al signup
			if (DBAccess.signUpUser(event, errorMessageSignup, data)) {
				switchToDashboardScene(event);
			}
		}
	}
}




