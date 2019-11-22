package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;
import java.util.ArrayList;

//Das hier ist eine Mapper-Klasse, die Gruppen-Objekte auf eine relationale DB abbildet.

public class GruppeMapper {

//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	
	private static GruppeMapper gruppeMapper; 
	
// Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	protected GruppeMapper() {
			
	}
	
// Es folgt eine Reihe Methoden, die wir im StarUML aufgeführt haben. Sie ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.
	
	public Gruppe insert (Gruppe gruppe) {
		
	}
	
	public Gruppe update (Gruppe gruppe) {
		
	}
	
	public void delete (Gruppe gruppe) {
		
	}
	
	public ArrayList <Gruppe> findAllGruppen () {
		
	}
	
	public Gruppe findById (int id) {
		
	}
	
	public void addGruppenmitgliedschaft (Anwender anwender, Gruppe gruppe) {
		
	}
	
	public void deleteGruppenmitgliedschaft (Anwender anwender, Gruppe gruppe) {
		
	}
	
	public ArrayList <Gruppe> findAllByAnwender (Anwender anwender) {
		
	}
	
	public ArrayList <Gruppe> findAllByAnwenderOwner (Anwender anwenderOwner) {
		
	}
	
	public void addEigentumsstruktur (Anwender anwender) {
		
	}
	
	public void deleteEigentumsstruktur (Anwender anwender) {
		
	}
	
	
	
	
}
