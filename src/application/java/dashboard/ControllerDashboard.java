package application.java.dashboard;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.java.exercise.ControllerExercise;
import application.java.exercise.DBExercise;
import application.java.exercise.Exercise;
import application.java.userInfo.ControllerUserInfo;
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
	
	public void switchToUserInfo(MouseEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/resources/userInfo/fxml/UserInfoScene.fxml"));
			Parent userInfoRoot = loader.load();
			
			ControllerUserInfo cui = loader.getController();
			cui.loadUserInfo(); // carichiamo le informazioni prima dello switch della scena in modo tale che l'utente non veda l'improvviso cambio di valori da quelli standard ai suoi
			switchScene(event, userInfoRoot);
		}
		catch (IOException e) { // da controllare
			Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + 
					": Errore nel passaggio al ControllerUserInfo" +
					"\nMessaggio di errore: " + 
					e.getMessage());
		}
	}
	
	public void switchToFindError(ActionEvent event) {
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








