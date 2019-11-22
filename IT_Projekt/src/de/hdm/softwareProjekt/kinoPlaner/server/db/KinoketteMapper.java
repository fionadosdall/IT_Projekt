package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;
import java.util.ArrayList;

//Das hier ist eine Mapper-Klasse, die Kinoketten-Objekte auf eine relationale DB abbildet.

public class KinoketteMapper {

//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	
	private static KinoketteMapper kinoketteMapper; 
	

// Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	protected KinoketteMapper() {
		
	}
	
// Es folgt eine Reihe Methoden, die wir im StarUML aufgeführt haben. Sie ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.
	
	public Kinokette instert (Kinokette kinokette) {
		
	}
	
	public Kinokette update (Kinokette kinokette) {
		
	}
	
	public void delete (Kinokette kinokette) {
		
	}
	
	public ArrayList <Kinokette> findAllKinoketten () {
		
	}
	
	public Kinokette findById (int id) {
		
	}
	
	public ArrayList <Kinokette> findAllByAnwenderOwner (Anwender anwenderOwner) {
		
	}
	
	public void addEigentumsstruktur (Anwender anwender, Kinokette kinokette) {
		
	}
	
	public void deleteEigentumsstruktur (Anwender anwender, Kinokette kinokette) {
		
	}
	
	
}
