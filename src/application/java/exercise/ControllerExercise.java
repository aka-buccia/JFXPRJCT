package application.java.exercise;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.java.dashboard.ControllerDashboard;
import application.java.dashboard.UserScraper;
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
	
	public void loadFXML(Event event, String location) {
		try {
			root = FXMLLoader.load(getClass().getResource(location));
			switchScene(event, root);
		}
		catch(IOException | RuntimeException e){
			Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() +
					": Errore nel caricamento della scena" + 
					"\n Localizzazione: " +
					location +
					"\nMessaggio di errore: " + 
					e.getMessage());
		}
	}
	
	public void switchToDashboardScene(ActionEvent event) {
		//root = FXMLLoader.load(getClass().getResource("/application/resources/dashboard/fxml/DashboardScene.fxml"));
		//switchScene(event, root);
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/resources/dashboard/fxml/DashboardScene.fxml"));
			Parent dashboardRoot = loader.load();
		
			ControllerDashboard cd = loader.getController(); // gli da il controller di loader che è ControllerDashboard
			String username = UserScraper.getUsername();
			cd.setWelcomeText(username);
		
			switchScene(event, dashboardRoot);
		}
		catch (IOException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() +
					": Errore nel passaggio al ControllerDashboard" + 
					"\nMessaggio di errore: " + 
					e.getMessage());
		}
	}
	

	
}

