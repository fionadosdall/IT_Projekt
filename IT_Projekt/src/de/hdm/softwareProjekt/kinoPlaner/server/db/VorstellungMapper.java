package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;
import java.util.ArrayList;

// Das hier ist eine Mapper-Klasse, die Umfrageoption-Objekte auf eine relationale DB abbildet.

public class VorstellungMapper {

//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	private static VorstellungMapper vorstellungMapper; 
	
// Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	protected VorstellungMapper() {
		
	}
	
	
// Es folgt eine Reihe Methoden, die wir im StarUML aufgeführt haben. Sie ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.
	
	public Vorstellung insert (Vorstellung vorstellung) {
		
	}
	
	public Vorstellung update (Vorstellung vorstellung) {
		
	}
	
	public void delete (Vorstellung vorstellung) {
		
	}
	
	public ArrayList <Vorstellung> findAllVorstellungen () {
		
	}
	
	public ArrayList <Vorstellung> findAllBySpielplan (Spielplan spielplan) {
		
	}
	
	public Vorstellung findById (int id) {
		
	}
	
}
