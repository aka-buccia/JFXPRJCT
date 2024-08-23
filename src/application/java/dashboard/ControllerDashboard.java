package application.java.dashboard;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.java.exercise.ControllerExercise;
import application.java.exercise.DBExercise;
import application.java.exercise.Exercise;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControllerDashboard {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private Label welcomeTextDashboard;
	
	
	public void setWelcomeText(String username) {
		welcomeTextDashboard.setText("Ciao " + username + "!");
	}
	
	public void switchScene(Event event, Parent root) {
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/application/resources/general/application.css").toExternalForm());
		stage.setScene(scene);
        Platform.runLater(() -> root.requestFocus());
		stage.show();
	}
	
	public void loadFXML(Event event, String location) {
		try {
			root = FXMLLoader.load(getClass().getResource(location));
			switchScene(event, root);
		}
		catch (IOException | RuntimeException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() +
					": Errore nel caricamento della scena" + 
					"\n Localizzazione: " +
					location +
					"\nMessaggio di errore: " + 
					e.getMessage());
		}
	}
	
	public void logout(MouseEvent event){
		UserScraper.removeInfo();
		System.out.println(UserScraper.printInfo()); // non necessario ma utile per capire se è andato tutto a buon fine
		loadFXML(event, "/application/resources/access/fxml/LoginScene.fxml");
	}
	
	public void switchToUserInfoScene(MouseEvent event) {
		loadFXML(event, "/application/resources/dashboard/fxml/UserInfoScene.fxml");
	}
	
	public void switchToDashboardScene(MouseEvent event) {
		loadFXML(event, "/application/resources/dashboard/fxml/DashboardScene.fxml");
	}
	
	public void switchToFindErrorScene(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/resources/exercise/fxml/FindErrorScene.fxml"));
			Parent findErrorRoot = loader.load();
		
			ControllerExercise ce = loader.getController(); // gli passa il controller di loader che è ControllerExercise
			
			Exercise ex = DBExercise.loadEx(1);
			if (ex != null) {
				ce.setText(ex.getText());
				switchScene(event, findErrorRoot);
			}
			
		}
		catch (IOException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() +
					": Errore nel passaggio al ControllerDashboard" + 
					"\nMessaggio di errore: " + 
					e.getMessage());
		}
	}
}








