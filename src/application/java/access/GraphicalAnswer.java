package application.java.access;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GraphicalAnswer {
	
	public static ArrayList<TextField> missingData(ActionEvent event, ArrayList<TextField> data) {
		ArrayList<TextField> empty = new ArrayList<TextField>();
		
		for (TextField d : data) {
			if (d.getText().isEmpty()) {
				empty.add(d);
			}
			else
				d.setStyle("");//ripristina allo stato normale il bordo se il dato è stato inserito
		}
		
		return empty;
		
	}
	
	public static void alertMessage(ArrayList<TextField> data, Label messageLabel, String message) {
		
		for (TextField d : data) {
			d.setStyle("-fx-border-color: #bf616a; -fx-border-width: 2;");
		}
		
		messageLabel.setText(message);
	}
	
}
