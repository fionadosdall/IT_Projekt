package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;
import java.util.ArrayList;

//Das hier ist eine Mapper-Klasse, die Spielplan-Objekte auf eine relationale DB abbildet.

public class SpielplanMapper {
	
//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	
	private static SpielplanMapper spielplanMapper; 
	
// Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	
	protected SpielplanMapper () {
		
	}
	
// Es folgt eine Reihe Methoden, die wir im StarUML aufgeführt haben. Sie ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.
	
	
	public Spielplan insert (Spielplan spielplan) {
		
	}
	
	public Spielplan update (Spielplan spielplan) {
		
	}
	
	public void delete (Spielplan spielplan) {
		
	}
	
	public Spielplan findById (int id) {
		
	}
	
	public ArrayList <Spielplan> findAllByAnwenderOwner (Anwender anwenderOwner) {
		
	}
	
	public void addEigentumsstruktur (Anwender anwender, Spielplan spielplan) {
		
	}
	
	public void deleteEigentumsstruktur (Anwender anwender, Spielplan spielplan) {
		
	}
	

}
