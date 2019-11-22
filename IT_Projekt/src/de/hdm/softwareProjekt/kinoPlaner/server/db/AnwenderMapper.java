package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;
import java.util.ArrayList;

//Das hier ist eine Mapper-Klasse, die Anwender-Objekte auf eine relationale DB abbildet. 


public class AnwenderMapper {

//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	private static AnwenderMapper anwenderMapper; 
	
	
// Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	protected AnwenderMapper () {
		
	}
	
	
/*Es folgt eine Reihe von Methoden, die wir im StarUML aufgeführt haben. 
Sie ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.
 */
	
	public Anwender insert (Anwender anwender) {
		
	}
	
	public Anwender update (Anwender anwender) {
		
	}
	
	public void delete (Anwender anwender) {
		
	}
	
	public ArrayList <Anwender> findAllByAnwender (Anwender anwender) {
		
	}
	
	public Anwender findById (int id) {
		
	}
	
	public Anwender findByName (String name) {
		
	}
	
	public ArrayList <Anwender> findAllByGruppe (Gruppe gruppe){
		
	}
	
	
	
}
