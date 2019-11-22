package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;
import java.util.ArrayList;

//Das hier ist eine Mapper-Klasse, die Film-Objekte auf eine relationale DB abbildet. 

public class FilmMapper {
	
//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	private static FilmMapper filmMapper; 
	
	
// Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	protected FilmMapper () {
		
	}
	
/*Es folgt eine Reihe von Methoden, die wir im StarUML aufgeführt haben. 
Sie ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.
*/

	public Film insert (Film film) {
		
	}
	
	public Film update (Film film) {
		
	}
	
	public void delete (Film film) {
		
	}
	
	public Film findById (int id) {
		
	}
	
	public ArrayList <Film> findAll() {
		
	}
	
		
	
}

