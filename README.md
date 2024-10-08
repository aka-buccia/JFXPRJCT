<img src="src/application/resources/general/logo-redesign.png" alt="JFXPRJCT logo" width="400" height="150"/> 


# Descrizione
JFXPRJCT è una applicazione desktop sviluppata in JAVAFX dal gruppo SPT per il progetto del corso di Programmazione Internet.
L'obiettivo finale è creare un'applicazione multiutente per l'apprendimento e l'allenamento delle conoscenze del linguaggio Java.

# Funzionamento

## Utilizzo
![schema jfxprjct](https://github.com/user-attachments/assets/645cc5fc-2a82-451f-98f8-e7e04f50f45e)

## Struttura progetto
Il progetto è strutturato in due macropacchetti, _resources_ contenente tutte le risorse (immagini, file, scene) e _java_ contenente i file java da compilare. 

I file java sono divisi a loro volta per momento di esecuzione all'interno dell'applicazione, ossia _access_, _dashboard_, _exercise_ e _general_ per quelli che gestiscono operazioni generali richiamate in più momenti dell'esecuzione.
Per ogni cartella è presente un controller, che connette backend e frontend, e un interrogatore db che si occupa delle interrogazioni al database necessarie. 

## Struttura database
È un database relazionale SQlite locale serverless con tre tabelle:
- **Users** contenente nei record gli utenti che si registrano, con chiave primaria _idUser_
- **Exercises** contenente nei record gli esercizi, con chiave primaria _idExercise_
- **DoneExercises** che riflette l'associazione M:N Utente ha svolto Esercizio, in ogni record è presente idUser e idExercise
  
## Librerie esterne
Per l'esecuzione dell'applicazione è richiesta l'installazione di tre librerie esterne:
- _javafx_ per le componenti grafiche
- _slf4j-api_ e _slf4j-nop_ per il sistema di logging
- _sqlite-jdbc_ per la connessione al database
  
# Regole di sviluppo
- nomi variabili esclusivamente in inglese seguendo regola camelCase
- commenti codice, commit e readme in italiano
- gli issue, quando aperti, vanno contrassegnati con le etichette appropriate, una che distingua fra _bug_, _feature_ e _enhancement_ e una seconda che specifichi quale aspetto è coinvolto (_ux_, _ui_, _database_, _codice_).

# Crediti
All'interno del progetto ci siamo serviti della palette [NordTheme](https://www.nordtheme.com/) e dell'immagine di [Pawel Czerwinski](https://unsplash.com/it/foto/BPrk2cOoCq8)
