package application.java.exercise;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

// classe per salvare le informazioni degli esercizi
public class Exercise {
	private int idExercise;
	private int level;
	private int type;
	private int number;
	private String path;
	private String answer1;
	private String answer2;
	private int n1;
	private int n2;
	
	public Exercise(int idExercise, int level, int type, int number, String path, String answer1, String answer2, int n1, int n2) {
		this.idExercise = idExercise;
		this.level = level;
		this.type = type;
		this.number = number;
		this.path = path;
		this.answer1 = answer1;
		this.answer2 = answer2;
		this.n1 = n1;
		this.n2 = n2;
	}
	
	public int getIdExercise() {
		return this.idExercise;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	public String getAnswer1() {
		return this.answer1;
	}
	
	public String getAnswer2() {
		return this.answer2;
	}
	
	public int getN1() {
		return this.n1;
	}

	public int getN2() {
		return this.n2;
	}
	
	// metodo per salvare dentro la variabile text il codice contenuto nel file dell'esercizio
	public String getText() { 
		File f = new File(this.path);
		String text = "";
		
		try {
			Scanner scan = new Scanner(f);
			int i = 1;
			while (scan.hasNextLine()) {
				text += i + " " +  scan.nextLine() + "\n";
				i ++;
			}
			scan.close();
		}
		catch (IOException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + "Errore File durante lettura testo" + "\nMessaggio di errore: " + e.getMessage());
		}
		
		return text;
	}
}



