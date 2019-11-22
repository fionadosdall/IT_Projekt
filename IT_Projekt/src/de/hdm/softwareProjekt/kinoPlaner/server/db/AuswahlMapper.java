package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;
import java.util.ArrayList;

//Das hier ist eine Mapper-Klasse, die Auswahl-Objekte auf eine relationale DB abbildet. 


public class AuswahlMapper {
	
//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	private static AuswahlMapper votingMapper; 
	
	
// Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	protected AuswahlMapper () {
		
	}
	
/*Es folgt eine Reihe von Methoden, die wir im StarUML aufgeführt haben. 
Sie ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.
*/
	
	public Auswahl insert (Auswahl auswahl) {
		
	}
	
	public Auswahl update (Auswahl auswahl) {
		
	}
	
	public void delete (Auswahl auswahl) {
		
	}
	
	public Auswahl findById (int id) {
		
	}
	
	public ArrayList <Auswahl> findByUmfrageoption (Umfrageoption umfrageoption) {
		
	}
	
	public void addEigentumsstruktur (Anwender anwender, Auswahl auswahl) {
		
	}
	
	public void deleteEigentumsstruktur (Anwender anwender, Auswahl auswahl) {
		
	}
	
	
	
	
	

}
