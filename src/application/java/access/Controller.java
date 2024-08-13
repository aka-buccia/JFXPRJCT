package application.java.access;

import java.io.IOException;

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

public class Controller {
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
	private Label errorMessage;
	
	
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
	
	public void login(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/application/resources/dashboard/fxml/DashboardScene.fxml"));
		switchScene(event, root);
	}
	
	public void signUp(ActionEvent event) throws IOException{
		
		TextField [] data = {usernameSignup, passwordSignup, nameSignup, surnameSignup};
		
		if (! GraphicalAnswer.missingData(event, data, errorMessage)) {  //se non mancano dati si procede al signup
			DBUtils.signUpUser(event, errorMessage, data[0].getText(), data[1].getText(), data[2].getText(), data[3].getText());
		}
	}
}
<<<<<<< HEAD
	
    
=======


>>>>>>> f71491c909e1eb6c57f2f8e747d4c16597fca068
