package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielplan;

//Das hier ist eine Mapper-Klasse, die Spielplan-Objekte auf eine relationale DB abbildet.

public class SpielplanMapper {
	
//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	
	private static SpielplanMapper spielplanMapper; 
	
// Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	
	protected SpielplanMapper () {
	}
	
	
	/** Um eine Instanz dieses Mappers erstellen zu können, nutzt man NICHT den KONSTRUKTOR, 
	 * sondern die folgende Methode. 
	 * Sie ist statisch, dadurch stellt sie sicher, dass nur eine einzige Instanz dieser Klasse existiert.	
	 * @param anwender
	 */
		
		public static SpielplanMapper spielplanMapper () {
			if (spielplanMapper == null) {
				spielplanMapper = new SpielplanMapper();
			}
			return spielplanMapper; 
		}
	
// Es folgt eine Reihe Methoden, die wir im StarUML aufgeführt haben. Sie ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.
	
	
	public Spielplan insert (Spielplan spielplan) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			//Jetzt wird geschaut, welches die höchste Id der schon bestehenden Spielplaene ist.
			ResultSet resultset = stmt.executeQuery("SELECT MAX (id) AS maxId " + "FROM spielplan"); 
			if (resultset.next()) {
				// Wenn die höchste Id gefunden wurde, wird eine neue Id mit +1 höher erstellt
				spielplan.setId(resultset.getInt("maxId")+1);
				stmt = con.createStatement();
				
				//Jetzt wird die Id tatsächlich eingefügt: 
				stmt.executeUpdate("INSERT INTO spielplan (id, kinoId)" +
						"VALUES(" + spielplan.getId() + "','" + spielplan.getKinoId() + ")"); 
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} return spielplan;
	}
	
	
	
	
	public Spielplan update (Spielplan spielplan) {
		return spielplan;
	}
	
	
	
	
	public void delete (Spielplan spielplan) {
		
	}
	
	
	
	
	
	public Spielplan findById (int id) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			ResultSet resultset = stmt.executeQuery("SELECT id, kinoId FROM spielplan" + 
					"WHERE id=" + id + " ORDER BY kinoId"); 
			// Prüfe ob das geklappt hat, also ob ein Ergebnis vorhanden ist: 
			if (resultset.next()) {
				Spielplan sp = new Spielplan();
				sp.setId(resultset.getInt("id"));
				sp.setKinoId(resultset.getInt("kinoId")); 
				return sp; 	
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	public ArrayList <Spielplan> findAllByAnwenderOwner (Anwender anwenderOwner) {
		Connection con = DBConnection.connection(); 
		
		ArrayList <Spielplan> resultarray = new ArrayList <Spielplan> (); 
		
		try {
			Statement stmt = con.createStatement(); 
			
			ResultSet resultset = stmt.executeQuery("SELECT id, besitzerId, kinoId FROM spielplan" +
					"WHERE besitzerId = " + anwenderOwner + "ORDER BY kinoId"); 
			
			while (resultset.next()) {
				Spielplan sp = new Spielplan();
				sp.setId(resultset.getInt("id"));
				sp.setBesitzerId(resultset.getInt("besitzerId"));
				sp.setKinoId(resultset.getInt("kinoId"));
				
				 // Hinzufügen des neuen Objekts zur ArrayList
		        resultarray.add(sp); 
			} 
		} catch (SQLException e1) {
			e1.printStackTrace();
		} return resultarray;
	}
	
	
	
	
	
	public void addEigentumsstruktur (Anwender anwender, Spielplan spielplan) {
		
	}
	
	public void deleteEigentumsstruktur (Anwender anwender, Spielplan spielplan) {
		
	}
	

}
