package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;
import java.util.ArrayList;

//Das hier ist eine Mapper-Klasse, die Umfragen-Objekte auf eine relationale DB abbildet.

public class UmfrageMapper {

	
//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	
	private static UmfrageMapper umfrageMapper; 
	
// Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	
	protected UmfrageMapper () {
		
	}
	
// Es folgt eine Reihe Methoden, die wir im StarUML aufgeführt haben. Sie ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.

	public Umfrage insert (Umfrage umfrage) {
		
	}
	
	public Umfrage update (Umfrage umfrage) {
		
	}
	
	public void delete (Umfrage umfrage) {
		
	}
	
	public Umfrage findById (int id) {
		
	}
	
	public ArrayList <Umfrage> findAllByAnwender (Anwender anwender) {
		
	}
	
	public ArrayList <Umfrage> findAllByAnwenderOwner (Anwender anwenderOwner) {
		
	}
	
	public ArrayList <Umfrage> findAllClosedByAnwender (Anwender anwender) {
		
	}
	
	public ArrayList <Umfrage> findAllByGruppe (Gruppe gruppe) {
		
	}
	
	public void addEigentumsstruktur (Anwender anwender, Umfrage umfrage) {
		
	}
	
	public void deleteEigentumsstruktur (Anwender anwender, Umfrage umfrage) {
		
	}
	
	


}


