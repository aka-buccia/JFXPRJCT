package application.java.userInfo;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.java.dashboard.ControllerDashboard;
import application.java.dashboard.UserScraper;
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

public class ControllerUserInfo {
	private Stage stage;
	private Scene scene;
	
	@FXML
	private Label usernameLabel;
	
	@FXML
	private Label nameLabel;
	
	@FXML
	private Label surnameLabel;
	
	
	public void switchScene(Event event, Parent root) {
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/application/resources/general/application.css").toExternalForm());
		stage.setScene(scene);
        Platform.runLater(() -> root.requestFocus()); // sposta il focus sulla scena
		stage.show();
	}
	
	public void switchToDashboardScene(Event event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/resources/dashboard/fxml/DashboardScene.fxml"));
			Parent dashboardRoot = loader.load();
		
			ControllerDashboard cd = loader.getController(); // gli da il controller di loader che è ControllerDashboard
			String username = UserScraper.getUsername(); 
			cd.setWelcomeText(username); // ricarica l'informazione "username" per inserirla nuovamente nella scena visualizzata
		
			switchScene(event, dashboardRoot);
		}
		catch (IOException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() +
					": Errore nel passaggio al ControllerDashboard" + 
					"\nMessaggio di errore: " + 
					e.getMessage());
		}
	}
	
	public void loadUserInfo() {
		usernameLabel.setText(UserScraper.getUsername());
		nameLabel.setText(UserScraper.getName());
		surnameLabel.setText(UserScraper.getSurname());
	}
}

