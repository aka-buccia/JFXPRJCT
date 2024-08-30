package application.java.general;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

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
}
