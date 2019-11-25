package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;

//Das hier ist eine Mapper-Klasse, die Spielzeit-Objekte auf eine relationale DB abbildet.

public class SpielzeitMapper {
	
//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	
	private static SpielzeitMapper spielzeitMapper; 
	
// Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	
	protected SpielzeitMapper () {
	}
	
	
	/** Um eine Instanz dieses Mappers erstellen zu können, nutzt man NICHT den KONSTRUKTOR, 
	 * sondern die folgende Methode. 
	 * Sie ist statisch, dadurch stellt sie sicher, dass nur eine einzige Instanz dieser Klasse existiert.	
	 * @param anwender
	 */
		
		public static SpielzeitMapper spielzeitMapper () {
			if (spielzeitMapper == null) {
				spielzeitMapper = new SpielzeitMapper();
			}
			return spielzeitMapper; 
		}
	
		
		
// Es folgt eine Reihe Methoden, die wir im StarUML aufgeführt haben. Sie ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.

	public Spielzeit insert (Spielzeit spielzeit) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			//Jetzt wird geschaut, welches die höchste Id der schon bestehenden Spielzeiten ist.
			ResultSet resultset = stmt.executeQuery("SELECT MAX (id) AS maxId " + "FROM spielzeit"); 
			if (resultset.next()) {
				// Wenn die höchste Id gefunden wurde, wird eine neue Id mit +1 höher erstellt
				spielzeit.setId(resultset.getInt("maxId")+1);
				stmt = con.createStatement();
				
				//Jetzt wird die Id tatsächlich eingefügt: 
				stmt.executeUpdate("INSERT INTO spielzeit (id, zeit)" +
						"VALUES(" + spielzeit.getId() + "','" + spielzeit.getZeit() + ")"); 
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} return spielzeit;
	}
	
	
	
	
	public Spielzeit update (Spielzeit spielzeit) {
		return spielzeit;
	}
	
	
	
	
	
	public void delete (Spielzeit spielzeit) {
		
	}
	
	
	
	
	
	public ArrayList <Spielzeit> findAllSpielzeiten() {
		Connection con = DBConnection.connection(); 
		
		ArrayList <Spielzeit> resultarray = new ArrayList <Spielzeit> (); 
		
		try {
			Statement stmt = con.createStatement(); 
			
			ResultSet resultset = stmt.executeQuery("SELECT id, zeit FROM spielzeit" + "ORDER BY zeit"); 
			
			while (resultset.next()) {
				Spielzeit sz = new Spielzeit(); 
				sz.setId(resultset.getInt("id"));
				sz.setZeit(resultset.getDate("zeit"));
				 // Hinzufügen des neuen Objekts zur ArrayList
		        resultarray.add(sz); 
			} 
		} catch (SQLException e1) {
			e1.printStackTrace();
		} return resultarray; 
	}
	
	
	
	
	
	public Spielzeit findById (int id) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			ResultSet resultset = stmt.executeQuery("SELECT id, zeit FROM spielzeit" + 
					"WHERE id=" + id + " ORDER BY zeit"); 
			// Prüfe ob das geklappt hat, also ob ein Ergebnis vorhanden ist: 
			if (resultset.next()) {
				Spielzeit sz = new Spielzeit();
				sz.setId(resultset.getInt("id"));
				sz.setZeit(resultset.getDate("zeit"));
				return sz; 	
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	
	

}
