package application.java.exercise;

import java.io.*;
import java.time.LocalDateTime;
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

	public String getText() {
		String text = "";
		
		// Usa getResourceAsStream per ottenere un InputStream e leggere il file anche dentro al jar
		try (InputStream inputStream = getClass().getResourceAsStream(this.path)) {
			//Se il file è stato trovato procede con la lettura 
			if (inputStream != null){
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				
				String line;
				int i = 1;
				while ((line = reader.readLine()) != null) {
					text += i + " " + line + "\n";
					i++;
				}
			}
			else
				Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + " File " + this.path + " non trovato");
		} catch (IOException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + " Errore File durante lettura testo" + "\nMessaggio di errore: " + e.getMessage());
		}
		
		return text;
	}

	
}



