package application.java.access;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GraphicalAnswer {
	
	public static boolean missingData(ActionEvent event, TextField [] data, Label messageLabel) {
		boolean empty = false;
		
		for (TextField d : data) {
			if (d.getText().isEmpty()) {
				alertMessage(d, messageLabel, "Dati mancanti");
				empty = true;
			}
			else {
				d.setStyle(""); //ripristina allo stato normale il bordo se il dato è stato inserito
			}
		}
		
		return empty;
	}
	
	public static void alertMessage(TextField d, Label messageLabel, String message) {
		d.setStyle("-fx-border-color: red; -fx-border-width: 2;");
		messageLabel.setText(message);
	}
}
