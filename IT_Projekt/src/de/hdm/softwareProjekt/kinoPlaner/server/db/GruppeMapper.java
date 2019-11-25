package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;

//Das hier ist eine Mapper-Klasse, die Gruppen-Objekte auf eine relationale DB abbildet.

public class GruppeMapper {

//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	
	private static GruppeMapper gruppeMapper; 
	
// Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	protected GruppeMapper() {	
	}
	
	
	/** Um eine Instanz dieses Mappers erstellen zu können, nutzt man NICHT den KONSTRUKTOR, 
	 * sondern die folgende Methode. 
	 * Sie ist statisch, dadurch stellt sie sicher, dass nur eine einzige Instanz dieser Klasse existiert.	
	 * @param anwender
	 */
		
		public static GruppeMapper gruppeMapper() {
			if (gruppeMapper == null) {
				gruppeMapper = new GruppeMapper();
			}
			return gruppeMapper; 
		}
		
		
	
// Es folgt eine Reihe Methoden, die wir im StarUML aufgeführt haben. Sie ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.
	
	public Gruppe insert (Gruppe gruppe) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			//Jetzt wird geschaut, welches die höchste Id der schon bestehenden Gruppen ist.
			ResultSet resultset = stmt.executeQuery("SELECT MAX (id) AS maxId " + "FROM gruppen"); 
			if (resultset.next()) {
				// Wenn die höchste Id gefunden wurde, wird eine neue Id mit +1 höher erstellt
				gruppe.setId(resultset.getInt("maxId")+1);
				stmt = con.createStatement();
				
				//Jetzt wird die Id tatsächlich eingefügt: 
				stmt.executeUpdate("INSERT INTO gruppen (id, name, besitzerId)" +
						"VALUES(" + gruppe.getId() + "','" + gruppe.getName() + "','" + gruppe.getBesitzerId() + ")"); 
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} return gruppe; 
	}
	
	
	
	
	public Gruppe update (Gruppe gruppe) {
		return gruppe;
	}
	
	
	
	
	public void delete (Gruppe gruppe) {
		Connection con = DBConnection.connection();

	    try {
	      Statement stmt = con.createStatement();

	      stmt.executeUpdate("DELETE FROM gruppe " + "WHERE id=" + gruppe.getId());

	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }
	}
	
	
	
	
	
	public ArrayList <Gruppe> findAllGruppen () {
		Connection con = DBConnection.connection(); 
		
		ArrayList <Gruppe> resultarray = new ArrayList <Gruppe> (); 
		
		try {
			Statement stmt = con.createStatement(); 
			
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId FROM gruppe" + "ORDER BY name"); 
			
			while (resultset.next()) {
				Gruppe g = new Gruppe(); 
				g.setId(resultset.getInt("id"));
				g.setBesitzerId(resultset.getInt("besitzerId"));
				g.setName(resultset.getString("name"));
				
				 // Hinzufügen des neuen Objekts zur ArrayList
		        resultarray.add(g); 
			} 
		} catch (SQLException e1) {
			e1.printStackTrace();
		} return resultarray; 
	}
	
	
	
	
	
	public Gruppe findById (int id) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId FROM gruppe" + 
					"WHERE id=" + id + " ORDER BY besitzerId"); 
			// Prüfe ob das geklappt hat, also ob ein Ergebnis vorhanden ist: 
			if (resultset.next()) {
				Gruppe g = new Gruppe();
				g.setId(resultset.getInt("id"));
				g.setName(resultset.getString("name"));
				g.setBesitzerId(resultset.getInt("besitzerId")); 
				return g; 	
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null; 
	}
	
	
	
	public void addGruppenmitgliedschaft (Anwender anwender, Gruppe gruppe) {
		
	}
	
	
	
	
	public void deleteGruppenmitgliedschaft (Anwender anwender, Gruppe gruppe) {
		
	}
	
	
	
	
	public ArrayList <Gruppe> findAllByAnwender (Anwender anwender) {
		Connection con = DBConnection.connection(); 
		
		ArrayList <Gruppe> resultarray = new ArrayList <Gruppe> (); 
		
		try {
			Statement stmt = con.createStatement(); 
			
			ResultSet resultset = stmt.executeQuery("SELECT id, name, anwenderId FROM gruppe" + 
					"WHERE anwenderId = " + anwender + "ORDER BY name"); 
			
			while (resultset.next()) {
				Gruppe g = new Gruppe(); 
				g.setId(resultset.getInt("id"));
				g.setBesitzerId(resultset.getInt("besitzerId"));
				g.setName(resultset.getString("name"));
				
				 // Hinzufügen des neuen Objekts zur ArrayList
		        resultarray.add(g); 
			} 
		} catch (SQLException e1) {
			e1.printStackTrace();
		} return resultarray; 
	}
	
	
	
	
	
	public ArrayList <Gruppe> findAllByAnwenderOwner (Anwender anwenderOwner) {
		Connection con = DBConnection.connection(); 
		
		ArrayList <Gruppe> resultarray = new ArrayList <Gruppe> (); 
		
		try {
			Statement stmt = con.createStatement(); 
			
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId FROM gruppe" +
					"WHERE besitzerId = " + anwenderOwner + "ORDER BY name"); 
			
			while (resultset.next()) {
				Gruppe g = new Gruppe(); 
				g.setId(resultset.getInt("id"));
				g.setBesitzerId(resultset.getInt("besitzerId"));
				g.setName(resultset.getString("name"));
				
				 // Hinzufügen des neuen Objekts zur ArrayList
		        resultarray.add(g); 
			} 
		} catch (SQLException e1) {
			e1.printStackTrace();
		} return resultarray; 
	}
	
	
	
	
	public void addEigentumsstruktur (Anwender anwender) {
		
	}
	
	
	
	
	
	public void deleteEigentumsstruktur (Anwender anwender) {
		
	}
	
	
	
	
}
