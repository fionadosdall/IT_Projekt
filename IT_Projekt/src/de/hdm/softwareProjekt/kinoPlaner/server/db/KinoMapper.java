package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;
import java.util.ArrayList;

//Das hier ist eine Mapper-Klasse, die Kino-Objekte auf eine relationale DB abbildet.


public class KinoMapper {
	
//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	private static KinoMapper kinoMapper; 
	
// Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	protected KinoMapper() {
			
	}
	
// Es folgt eine Reihe Methoden, die wir im StarUML aufgeführt haben. Sie ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.
	

	public Kino insert (Kino kino) {
		
	}
	
	public Kino update (Kino kino) {
		
	}
	
	public void delete (Kino kino) {
		
	}
	
	public ArrayList <Kino> findAllKinos () {
		
	}
	
	public Kino findById (int id) {
		
	}
	
	public ArrayList <Kino> findAllByAnwenderOwner (Anwender anwenderOwner) {
		
	}
	
	public void addSpielplan (Spielplan spielplan, Kino kino) {
		
	}
	
	public void deleteSpielplan (Spielplan spielplan, Kino kino) {
		
	}
	
	public void addKinokette (Kinokette kinokette, Kino kino) {
		
	}
	
	public void deleteKinokette (Kinokette kinokette, Kino kino) {
		
	}
	
	public void addEigentumsstruktur (Anwender anwender, Kino kino) {
		
	}
	
	public void deleteEigentumsstruktur (Anwender anwender, Kino kino) {
		
	}
	
		
	
	
}
