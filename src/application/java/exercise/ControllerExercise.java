package application.java.exercise;

import java.io.IOException;

import application.java.dashboard.ControllerDashboard;
import application.java.general.ControllerUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ControllerExercise {

	private static Exercise currentExercise;
		
	@FXML
	private TextArea codeContainer;
	
	@FXML
	private TextField numberResponseFE;
	
	@FXML
	private TextField codeResponseFE;
	
	@FXML
	private Label resultMessageLabelFE;
	
	@FXML
	private Label levelNumExFE;
	
	@FXML
	private Label numExFE;
	
	@FXML
	private Button nextBtn;
	
	@FXML
	private Button respondBtn;
	

	
	public void switchToDashboardScene(Event event) {
		String location = "/application/resources/dashboard/fxml/DashboardScene.fxml";
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
			Parent dashboardRoot = loader.load();
		
			ControllerDashboard cd = loader.getController(); // gli da il controller di loader che è ControllerDashboard
			cd.updateDashboardData();
			cd.setWelcomeText();
		
			ControllerUtils.switchScene((Node) event.getSource(), dashboardRoot);
		}
		catch (IOException |RuntimeException e) {
			ControllerUtils.showControllerError(e, location);
		}
	}
	
	public void setText(String text) {
		codeContainer.setText(text);
	}
	
	public void setExerciseInfo() {
		levelNumExFE.setText("Livello: " + ControllerExercise.currentExercise.getGrado());
		numExFE.setText("Esercizio: " + ControllerExercise.currentExercise.getNumero());
	}
	
	public void checkResponseFEExercise(ActionEvent event) {
		String userResponse1 = numberResponseFE.getText().trim();
		String userResponse2 = codeResponseFE.getText().trim();
		String dbResponse1 = ControllerExercise.currentExercise.getRisposta1().trim();
		String dbResponse2 = ControllerExercise.currentExercise.getRisposta2().trim();
		
		if (userResponse1.equals(dbResponse1) && userResponse2.equals(dbResponse2)) { // esercizio giusto
			// aggiungere esercizio svolto nel database in "Esercizi svolti"
			if (DBExercise.updateCompletedEx(ControllerExercise.currentExercise.getIdEsercizio())) {
				// cambiare testo e colore testo in verde di "resultMessageLabelFE"
				resultMessageLabelFE.setText("ESATTO");
				resultMessageLabelFE.setStyle("-fx-text-fill: #a3be8c");
				nextBtn.setDisable(false);  //abilita il bottone AVANTI	
				respondBtn.setDisable(true);  //disabilita il bottone RISPONDI
				
				
			}
		}
		else { // esercizio sbagliato
			// cambiare testo e colore testo in rosso di "resultMessageLabel"
			resultMessageLabelFE.setText("SBAGLIATO");
			resultMessageLabelFE.setStyle("-fx-text-fill: #bf616a");
		}
	}
	
	public void switchToFindErrorScene(ActionEvent event) {
		String location = "/application/resources/exercise/fxml/FindErrorScene.fxml";
		
		try {
			Exercise ex = DBExercise.loadEx(1);
			
			if (ex != null) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
				Parent controllerRoot = loader.load();
			
				ControllerExercise ce = loader.getController(); 
				ControllerExercise.currentExercise = ex;
				ce.setText(ex.getText());
				ce.setExerciseInfo();
				ControllerUtils.switchScene((Node) event.getSource(), controllerRoot);
			}
			else { //esercizi da svolgere finiti
				switchToDashboardScene(event);
				ControllerUtils.showAlertWindow("TROVA L'ERRORE completato", "Hai terminato gli esercizi da svolgere");
			}
			
		}
		catch (IOException | RuntimeException e) {
			ControllerUtils.showControllerError(e, location);
		}
	}
	
	public void switchToPredictResultScene(ActionEvent event) {
		String location = "/application/resources/exercise/fxml/PredictResultScene.fxml";
		/*
		try {
			Exercise ex = DBExercise.loadEx(2);
			
			if (ex != null) {
				System.out.println("sono dentro");
				FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
				Parent controllerRoot = loader.load();
			
				ControllerExercise ce = loader.getController(); 
				ControllerExercise.currentExercise = ex;
				ce.setText(ex.getText());
				ce.setExerciseInfo();
				switchScene(event, controllerRoot);
			}
			else { //esercizi da svolgere finiti
				switchToDashboardScene(event);
				ControllerUtils.showAlertWindow("PREVEDI RISULTATO completato", "Hai terminato gli esercizi da svolgere");
			}

		}
		catch (IOException | RuntimeException e) {
			ControllerUtils.showControllerError(e, location);
		}
		*/
	}
}






