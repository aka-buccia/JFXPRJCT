package application.java.general;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

public class ControllerUtils {	
	
	public static void showControllerError(Exception e, String location) {
		Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() +
				": Errore nel caricamento della scena" + 
				"\nPercorso scena: '" +
				location + "'" + 
				"\nMessaggio di errore: " + 
				e.getMessage());
	}
	
	public static void showAlertWindow( String title, String description) {
		Alert successSignupAlert = new Alert(Alert.AlertType.INFORMATION);
		successSignupAlert.setTitle(title);
		successSignupAlert.setHeaderText(null);
		successSignupAlert.setContentText(description);
		successSignupAlert.setGraphic(null);
		
		DialogPane dialogPane = successSignupAlert.getDialogPane();
		dialogPane.getStylesheets().add(ControllerUtils.class.getResource("/application/resources/general/application.css").toExternalForm());
		dialogPane.getStyleClass().add("custom-alert");
		
		successSignupAlert.showAndWait();
	}
	
	public static void loadFXML(Event event, String location) {
		try {
			Parent root = FXMLLoader.load(ControllerUtils.class.getResource(location));
			switchScene((Node) event.getSource(), root);
		}
		catch(IOException | RuntimeException e){
			ControllerUtils.showControllerError(e, location);
		}
	}
	
	public static void switchScene(Node source, Parent root) {
		Stage stage = (Stage) source.getScene().getWindow();
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(ControllerUtils.class.getResource("/application/resources/general/application.css").toExternalForm());
		stage.setScene(scene);
        Platform.runLater(() -> root.requestFocus()); // sposta il focus sulla scena
		stage.show();
	}





}
