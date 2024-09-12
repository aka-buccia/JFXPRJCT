package application.java.general;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/resources/access/fxml/EnterScene.fxml")); // caricamento della scena iniziale
			Scene scene = new Scene(root); // creazione di una nuova scena attraverso root
			scene.getStylesheets().add(getClass().getResource("/application/resources/general/application.css").toExternalForm()); // caricamento del file css
			
			stage.setTitle("SPT - JFXPRJCT"); // titolo della finestra
			Image logo = new Image("/application/resources/general/logo.png"); // caricamento logo
			stage.getIcons().add(logo); // aggiunta logo nella finestra
			stage.setResizable(false); // disabilitazione del ridimensionamento della finestra
					
			// visualizzazione della finestra
			stage.setScene(scene);
			stage.show();
		} 
		catch(Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Errore catastrofico: applicazione non partita" + "\nMessaggio di errore: " + e.getMessage());
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}




