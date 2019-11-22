package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;
import java.util.ArrayList;

//Das hier ist eine Mapper-Klasse, die Spielzeit-Objekte auf eine relationale DB abbildet.

public class SpielzeitMapper {
	
//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	
	private static SpielzeitMapper spielzeitMapper; 
	
// Gesch�tzter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	
	protected SpielzeitMapper () {
		
	}
	
// Es folgt eine Reihe Methoden, die wir im StarUML aufgef�hrt haben. Sie erm�glichen, dass man Objekte z.B. suchen, l�schen und updaten kann.

	public Spielzeit insert (Spielzeit spielzeit) {
		
	}
	
	public Spielzeit update (Spielzeit spielzeit) {
		
	}
	
	public void delete (Spielzeit spielzeit) {
		
	}
	
	public ArrayList <Spielzeit> findAllSpielzeiten() {
		
	}
	
	public Spielzeit findById (int id) {
		
	}
	
	
	

}
