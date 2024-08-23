package application.java.exercise;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.java.dashboard.ControllerDashboard;
import application.java.dashboard.UserScraper;
import application.java.general.DBUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerExercise {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private final static String location = "jdbc:sqlite:database.db";
	
	@FXML
	private TextArea codeContainer;
	
	@FXML
	private TextField numberResponseTF;
	
	@FXML
	private TextField codeResponseTF;
	
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
	
	public void setText(String text) {
		codeContainer.setText(text);
	}
	
	public boolean checkResponseExercise(ActionEvent event) {
		String response1 = numberResponseTF.getText();
		String response2 = codeResponseTF.getText();
		
		// confronto tra la risposta dell'utente e quella presente nel database
		Connection connection = DBUtils.connect(location);
		if (connection == null) 
			return false;
		
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Esercizi WHERE tipologia = ?");
		
		return true;
		
	}
}








