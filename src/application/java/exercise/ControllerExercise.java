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
	private TextField numberResponsePR1;
	
	@FXML
	private TextField numberResponsePR2;
	
	@FXML
	private Label resultMessageLabelFE;
	
	@FXML
	private Label resultMessageLabelPR;
	
	@FXML
	private Label levelNumExFE;
	
	@FXML
	private Label numExFE;
	
	@FXML
	private Label levelNumExPR;
	
	@FXML
	private Label numExPR;
	
	@FXML
	private Label n1TextPR;

	@FXML
	private Label n2TextPR;
	
	@FXML
	private Button nextBtn;
	
	@FXML
	private Button respondBtn;
	
	// passaggio alla scena Dashboard e relativo cambio di controller
	public void switchToDashboardScene(Event event) {
		String location = "/application/resources/dashboard/fxml/DashboardScene.fxml";
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(location)); // nuova istanza di FXMLLoader per caricare la scena Dashboard
			Parent dashboardRoot = loader.load(); // carica il file fxml e restituisce la radice Parent della gerarchia di nodi
		
			ControllerDashboard cd = loader.getController(); // recupera l'istanza del controller associato alla DashboardScene
			cd.updateDashboardData(); // chiama il metodo del controller per aggiornare i dati relativi alla dashboard
			cd.setWelcomeText(); // chiama il metodo del controller per caricare i dati nella Dashboard
		
			ControllerUtils.switchScene((Node) event.getSource(), dashboardRoot); // cambia scena
		}
		catch (IOException |RuntimeException e) {
			ControllerUtils.showControllerError(e, location);
		}
	}
	
	// metodo per modificare il testo contenuto all'interno di codeContainer
	public void setText(String text) {
		codeContainer.setText(text);
	}
	
	// metodo per aggiornare le informazioni (grado e numero) dell'esercizio corrente nella scena FindError
	public void setExerciseInfoFE() {
		levelNumExFE.setText("Livello: " + ControllerExercise.currentExercise.getGrado());
		numExFE.setText("Esercizio: " + ControllerExercise.currentExercise.getNumero());
	}
	
	// metodo per aggiornare le informazioni (grado e numero) dell'esercizio corrente nella scena PredictResult
	public void setExerciseInfoPR() {
		levelNumExPR.setText("Livello: " + ControllerExercise.currentExercise.getGrado());
		numExPR.setText("Esercizio: " + ControllerExercise.currentExercise.getNumero());
	}
	
	// metodo per modificare i valori di n nella scena PredictResult
	public void setNValuesPRExercise() {
		n1TextPR.setText("Risultato con n = " + ControllerExercise.currentExercise.getN1());
		n2TextPR.setText("Risultato con n = " + ControllerExercise.currentExercise.getN2());
	}
	
	// metodo per controllare le risposte dell'utente con quelle presenti nel database
	public void checkResponse(String userResponse1, String userResponse2, String dbResponse1, String dbResponse2, Label resultMessageLabel, Button nextBtn, Button respondBtn) {
		if (userResponse1.equals(dbResponse1) && userResponse2.equals(dbResponse2)) { // esercizio giusto
			// aggiungere esercizio svolto nel database in "Esercizi svolti"
			if (DBExercise.updateCompletedEx(ControllerExercise.currentExercise.getIdEsercizio())) {
				// cambiare testo e colore testo in verde di "resultMessageLabelFE"
				resultMessageLabel.setText("ESATTO");
				resultMessageLabel.setStyle("-fx-text-fill: #a3be8c");
				nextBtn.setDisable(false); // abilita il bottone AVANTI	
				respondBtn.setDisable(true); // disabilita il bottone RISPONDI
			}
		}
		else { // esercizio sbagliato
			// cambiare testo e colore testo in rosso di "resultMessageLabel"
			resultMessageLabel.setText("SBAGLIATO");
			resultMessageLabel.setStyle("-fx-text-fill: #bf616a");
		}
	}
	
	// metodo per controllare le risposte nella scena FindError
	public void checkResponseFEExercise(ActionEvent event) {
		String userResponse1 = numberResponseFE.getText().trim();
		String userResponse2 = codeResponseFE.getText().trim();
		String dbResponse1 = ControllerExercise.currentExercise.getRisposta1().trim();
		String dbResponse2 = ControllerExercise.currentExercise.getRisposta2().trim();
		
		checkResponse(userResponse1, userResponse2, dbResponse1, dbResponse2, resultMessageLabelFE, nextBtn, respondBtn);
	}
	
	// metodo per controllare le risposte nella scena PredictResult
	public void checkResponsePRExercise(ActionEvent event) {
		String userResponse1 = numberResponsePR1.getText().trim();
		String userResponse2 = numberResponsePR2.getText().trim();
		String dbResponse1 = ControllerExercise.currentExercise.getRisposta1().trim();
		String dbResponse2 = ControllerExercise.currentExercise.getRisposta2().trim();
		
		checkResponse(userResponse1, userResponse2, dbResponse1, dbResponse2, resultMessageLabelPR, nextBtn, respondBtn);
	}
	
	// passaggio alla scena FindError e relativo cambio di controller
	public void switchToFindErrorScene(ActionEvent event) {
		String location = "/application/resources/exercise/fxml/FindErrorScene.fxml";
		
		try {
			Exercise ex = DBExercise.loadEx(1); // caricamento esercizio tipologia 1
			
			if (ex != null) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
				Parent controllerRoot = loader.load();
			
				ControllerExercise ce = loader.getController(); 
				ControllerExercise.currentExercise = ex;
				ce.setText(ex.getText()); // caricamento testo dentro codeContainer
				ce.setExerciseInfoFE(); // caricamento info "livello" ed "esercizio"
				ControllerUtils.switchScene((Node) event.getSource(), controllerRoot);
			}
			else { // esercizi da svolgere finiti
				switchToDashboardScene(event); // torna alla Dashboard
				ControllerUtils.showAlertWindow("TROVA L'ERRORE completato", "Hai terminato gli esercizi da svolgere"); // mostra finestra di alert
			}
			
		}
		catch (IOException | RuntimeException e) {
			ControllerUtils.showControllerError(e, location);
		}
	}
	
	// passaggio alla scena PredictResult e relativo cambio di controller
	public void switchToPredictResultScene(ActionEvent event) {
		String location = "/application/resources/exercise/fxml/PredictResultScene.fxml";
		try {
			Exercise ex = DBExercise.loadEx(2); // caricamento esercizio tipologia 2
			
			if (ex != null) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
				Parent controllerRoot = loader.load();
			
				ControllerExercise ce = loader.getController(); 
				ControllerExercise.currentExercise = ex;
				ce.setText(ex.getText()); // caricamento testo dentro codeContainer
				ce.setExerciseInfoPR(); // caricamento info "livello" ed "esercizio"
				ce.setNValuesPRExercise(); // caricamento valori di n nella scena
				ControllerUtils.switchScene((Node) event.getSource(), controllerRoot);
			}
			else { // esercizi da svolgere finiti
				switchToDashboardScene(event); // torna alla Dashboard
				ControllerUtils.showAlertWindow("PREVEDI RISULTATO completato", "Hai terminato gli esercizi da svolgere"); // mostra finestra di alert
			}

		}
		catch (IOException | RuntimeException e) {
			ControllerUtils.showControllerError(e, location);
		}
	}
}


