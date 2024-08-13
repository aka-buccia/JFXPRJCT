package application.java.access;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
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
	
	
	public void switchScene(ActionEvent event, Parent root) {
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
	
	public void switchToSignupScene(ActionEvent event) throws IOException {
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
		DBUtils.signUpUser(event, usernameSignup.getText(), passwordSignup.getText(), nameSignup.getText(), surnameSignup.getText());
	}
}




