package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void switchScene(ActionEvent event, Parent root) {
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToLoginScene(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/application/Scenes/LoginScene.fxml"));
		switchScene(event, root);
	}
	
	public void switchToSignupScene(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/application/Scenes/SignupScene.fxml"));
		switchScene(event, root);
	}
	
	public void backToLogin(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/application/Scenes/LoginScene.fxml"));
		switchScene(event, root);
	}
}
