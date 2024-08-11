package application.java.access;

import java.io.IOException;

import javafx.application.Platform;
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
		scene.getStylesheets().add(getClass().getResource("/application/resources/general/application.css").toExternalForm());
		stage.setScene(scene);
        Platform.runLater(() -> root.requestFocus());
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
}
