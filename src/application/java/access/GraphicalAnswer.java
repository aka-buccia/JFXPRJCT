package application.java.access;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GraphicalAnswer {
	
	public static boolean missingData(ActionEvent event, TextField [] data, Label messageLabel, String message) {
		boolean empty = false;
		
		for (TextField d : data) {
			if (d.getText().isEmpty()) {
				alertMessage(d, messageLabel, message);
				empty = true;
			}
			else {
				d.setStyle(""); //ripristina allo stato normale il bordo se il dato è stato inserito
			}
		}
		
		return empty;
	}
	
	public static void alertMessage(TextField d, Label messageLabel, String message) {
		d.setStyle("-fx-border-color: #bf616a; -fx-border-width: 2;");
		messageLabel.setText(message);
	}
	
	public static void alertMessage(TextField username, TextField password, Label messageLabel, String message) {
		username.setStyle("-fx-border-color: #bf616a; -fx-border-width: 2;");
		password.setStyle("-fx-border-color: #bf616a; -fx-border-width: 2;");
		messageLabel.setText(message);
	}
}
