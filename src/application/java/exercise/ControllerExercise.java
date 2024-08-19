package application.java.exercise;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControllerExercise {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	
	public void switchScene(Event event, Parent root) {
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/application/resources/general/application.css").toExternalForm());
		stage.setScene(scene);
        Platform.runLater(() -> root.requestFocus()); // sposta il focus sulla scena
		stage.show();
	}
	
	public void switchToDashboard(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/application/resources/dashboard/fxml/DashboardScene.fxml"));
		switchScene(event, root);
	}
	
	
}

