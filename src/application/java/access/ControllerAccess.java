package application.java.access;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import application.java.dashboard.ControllerDashboard;
import application.java.general.ControllerUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
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
	
	public void loadFXML(Event event, String location) {
		try {
			root = FXMLLoader.load(getClass().getResource(location));
			switchScene(event, root);
		}
		catch(IOException | RuntimeException e){
			ControllerUtils.showControllerError(e, location);
		}
	}
	
	public void switchToLoginScene(ActionEvent event){
		loadFXML(event, "/application/resources/access/fxml/LoginScene.fxml");
	}
	
	public void switchToSignupScene(MouseEvent event){
		loadFXML(event, "/application/resources/access/fxml/SignupScene.fxml");
	}
	
	public void backToLoginScene(ActionEvent event){
		loadFXML(event, "/application/resources/access/fxml/LoginScene.fxml");
	}
	
	public void switchToDashboardScene(ActionEvent event) {
		String location = "/application/resources/dashboard/fxml/DashboardScene.fxml";
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
			Parent dashboardRoot = loader.load();
		
			ControllerDashboard cd = loader.getController(); // gli da il controller di loader che è ControllerDashboard
			cd.updateDashboardData();
			cd.setWelcomeText();
		
			switchScene(event, dashboardRoot);
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
				backToLoginScene(event);
				
				Alert successSignupAlert = new Alert(Alert.AlertType.INFORMATION);
				successSignupAlert.setTitle("Registrazione avvenuta con successo!");
				successSignupAlert.setHeaderText(null);
				successSignupAlert.setContentText("Ora puoi procedere con il login!");
				successSignupAlert.setGraphic(null);
				
				DialogPane dialogPane = successSignupAlert.getDialogPane();
				dialogPane.getStylesheets().add(getClass().getResource("/application/resources/general/application.css").toExternalForm());
				dialogPane.getStyleClass().add("custom-alert");
				
				successSignupAlert.showAndWait();
			}
		}
		else {
			GraphicalAnswer.alertMessage(empty, errorMessageSignup, "Dati mancanti");
		}
	}
}






