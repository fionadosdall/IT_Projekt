package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;
import java.util.ArrayList;

// Das hier ist eine Mapper-Klasse, die Umfrageoption-Objekte auf eine relationale DB abbildet. 

public class UmfrageoptionMapper {

	
	
//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	private static UmfrageoptionMapper umfrageoptionMapper; 
	
// Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	protected UmfrageoptionMapper() {
		
	}
	
	
// Es folgt eine Reihe Methoden, die wir im StarUML aufgeführt haben. Sie ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.
	
public Umfrageoption insert(Umfrageoption umfrageoption) {
	
}

public Umfrageoption update (Umfrageoption umfrageoption) {
	
}
	
public void delete (Umfrageoption umfrageoption) {
	
}

public ArrayList <Umfrageoption> findAllUmfragen (Umfrage umfrage) {
	
}
	
public Umfrageoption findById (int id) {
	
}
	
	
}
