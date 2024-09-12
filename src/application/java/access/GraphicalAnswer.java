package application.java.access;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

//classe per la gestione delle segnalazioni di errore durante accesso e registrazione
public class GraphicalAnswer {
	
	//metodo per controllare se i TextField sono vuoti 
	public static ArrayList<TextField> missingData(ActionEvent event, ArrayList<TextField> data) {
		ArrayList<TextField> empty = new ArrayList<TextField>();
		
		for (TextField d : data) {
			if (d.getText().isEmpty()) {
				empty.add(d); //inserisce il TextField nell'arraylist di output se è vuoto
			}
			else
				d.setStyle("");//ripristina allo stato normale il bordo se il TextField è pieno
		}
		
		return empty; //restituisce arraylist contenente TextField vuoti
		
	}
	
	//metodo per segnalare errore nell'inserimento di dati
	public static void alertMessage(ArrayList<TextField> data, Label messageLabel, String message) {
		
		for (TextField d : data) { //ciclo sui TextField da contornare di rosso perchè vuoti o contenenti dati errati
			d.setStyle("-fx-border-color: #bf616a; -fx-border-width: 2;"); //imposta bordo rosso sul TextField
		}
		
		messageLabel.setText(message); //scrive messaggio di errore nella Label indicata
	}
	
}
