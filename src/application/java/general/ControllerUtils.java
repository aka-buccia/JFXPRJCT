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
	// metodo per comunicare errore nel caricamento della scena
	public static void showControllerError(Exception e, String location) {
		Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() +
				": Errore nel caricamento della scena" + 
				"\nPercorso scena: '" +
				location + "'" + 
				"\nMessaggio di errore: " + 
				e.getMessage());
	}
	
	// metodo per mostrare la finestra di alert
	public static void showAlertWindow( String title, String description) {
		// finestra di alert informativo 
		Alert successSignupAlert = new Alert(Alert.AlertType.INFORMATION);
		// stile dell'alert informativo
		successSignupAlert.setTitle(title);
		successSignupAlert.setHeaderText(null);
		successSignupAlert.setContentText(description);
		successSignupAlert.setGraphic(null);
		
		// applica stile presente nel file css
		DialogPane dialogPane = successSignupAlert.getDialogPane();
		dialogPane.getStylesheets().add(ControllerUtils.class.getResource("/application/resources/general/application.css").toExternalForm());
		dialogPane.getStyleClass().add("custom-alert");
		
		// mostra alert e attende input dell'utente
		successSignupAlert.showAndWait();
	}
	
	// metodo per caricare scena attraverso file fxml
	public static void loadFXML(Event event, String location) {
		try {
			Parent root = FXMLLoader.load(ControllerUtils.class.getResource(location));
			switchScene((Node) event.getSource(), root); // cambia la scena attuale con la nuova scena caricata
		}
		catch(IOException | RuntimeException e){
			ControllerUtils.showControllerError(e, location);
		}
	}
	
	// metodo per cambiare scena
	public static void switchScene(Node source, Parent root) {
		Stage stage = (Stage) source.getScene().getWindow(); // cambia la scena attuale 
		// Crea una nuova scena con il parametro root e gli applica lo stile caricato dal file css
		Scene scene = new Scene(root);
		scene.getStylesheets().add(ControllerUtils.class.getResource("/application/resources/general/application.css").toExternalForm());
		stage.setScene(scene); // imposta la nuova scena sullo stage
        Platform.runLater(() -> root.requestFocus()); // sposta il focus sulla scena
		stage.show(); // mostra la finestra aggiornata
	}
}





