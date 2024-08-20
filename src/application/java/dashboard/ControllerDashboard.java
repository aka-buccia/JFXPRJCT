package application.java.dashboard;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
		catch(IOException | RuntimeException e){
			Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() +
					": Errore nel caricamento della scena" + 
					"\n Localizzazione: " +
					location +
					"\nMessaggio di errore: " + 
					e.getMessage());
		}
	}
	
	public void logout(MouseEvent event){
		// ...
		loadFXML(event, "/application/resources/access/fxml/LoginScene.fxml");
	}
	
	public void switchToUserInfo(MouseEvent event) throws IOException {
		// ...
	}
}



