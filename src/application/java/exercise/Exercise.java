package application.java.exercise;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Exercise {
	private int idEsercizio;
	private int grado;
	private int tipologia;
	private int numero;
	private String path;
	private String risposta1;
	private String risposta2;
	private int n1;
	private int n2;
	
	public Exercise(int idEsercizio, int grado, int tipologia, int numero, String path, String risposta1, String risposta2, int n1, int n2) {
		this.idEsercizio = idEsercizio;
		this.grado = grado;
		this.tipologia = tipologia;
		this.numero = numero;
		this.path = path;
		this.risposta1 = risposta1;
		this.risposta2 = risposta2;
		this.n1 = n1;
		this.n2 = n2;
	}
	
	public int getIdEsercizio() {
		return this.idEsercizio;
	}
	
	public int getGrado() {
		return this.grado;
	}
	
	public int getNumero() {
		return this.numero;
	}
	public String getRisposta1() {
		return this.risposta1;
	}
	
	public String getRisposta2() {
		return this.risposta2;
	}
	
	public int getN1() {
		return this.n1;
	}

	public int getN2() {
		return this.n2;
	}
	
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
	
	@Override
	public String toString() {
		String s = "[" + 
				this.idEsercizio + ", " +
				this.tipologia + ", " +
				this.grado+ ", " +
				this.numero + ", " +
				this.path + "]";
		
		return s;
	}
	
}
