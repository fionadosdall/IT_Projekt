package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;

//Das hier ist eine Mapper-Klasse, die Umfragen-Objekte auf eine relationale DB abbildet.

public class UmfrageMapper {

	
//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	
	private static UmfrageMapper umfrageMapper; 
	
// Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	
	protected UmfrageMapper () {
	}
	
	
	
	/** Um eine Instanz dieses Mappers erstellen zu können, nutzt man NICHT den KONSTRUKTOR, 
	 * sondern die folgende Methode. 
	 * Sie ist statisch, dadurch stellt sie sicher, dass nur eine einzige Instanz dieser Klasse existiert.	
	 * @param anwender
	 */
		
		public static UmfrageMapper umfrageMapper () {
			if (umfrageMapper == null) {
				umfrageMapper = new UmfrageMapper();
			}
			return umfrageMapper; 
		}
	
		
	
// Es folgt eine Reihe Methoden, die wir im StarUML aufgeführt haben. Sie ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.

	public Umfrage insert (Umfrage umfrage) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			//Jetzt wird geschaut, welches die höchste Id der schon bestehenden Umfragen ist.
			ResultSet resultset = stmt.executeQuery("SELECT MAX (id) AS maxId " + "FROM umfrage"); 
			if (resultset.next()) {
				// Wenn die höchste Id gefunden wurde, wird eine neue Id mit +1 höher erstellt
				umfrage.setId(resultset.getInt("maxId")+1);
				stmt = con.createStatement();
				
				//Jetzt wird die Id tatsächlich eingefügt: 
				stmt.executeUpdate("INSERT INTO umfrage (id, name, besitzerId, gruppenId, erstellDatum)" +
						"VALUES(" + umfrage.getId() + "','" + umfrage.getName() + "','" + umfrage.getBesitzerId() + "','" 
						+ umfrage.getGruppenId() + "','" + umfrage.getErstellDatum() + ")"); 
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} return umfrage;
	}
	
	
	
	
	public Umfrage update (Umfrage umfrage) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE umfrage SET " + "besitzerId=\""
				+ umfrage.getBesitzerId() + "\", " + "name=\""
				+ umfrage.getName() + "\", " + "erstellDatum=\""
				+ umfrage.getErstellDatum() + "\", " + "gruppenId=\""
				+ umfrage.getGruppenId() +  "\" " + "WHERE id=" + umfrage.getId());
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
		return umfrage;
	}
	
	
	
	
	public void delete (Umfrage umfrage) {
		Connection con = DBConnection.connection();

	    try {
	      Statement stmt = con.createStatement();

	      stmt.executeUpdate("DELETE FROM umfrage " + "WHERE id=" + umfrage.getId());

	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }
	}
	
	
	
	
	public Umfrage findById (int id) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, gruppenId, erstellDatum FROM umfrage" + 
					"WHERE id=" + id + " ORDER BY gruppenId"); 
			// Prüfe ob das geklappt hat, also ob ein Ergebnis vorhanden ist: 
			if (resultset.next()) {
				Umfrage u = new Umfrage();
				u.setId(resultset.getInt("id"));
				u.setName(resultset.getString("name"));
				u.setGruppenId(resultset.getInt("gruppenId"));
				u.setBesitzerId(resultset.getInt("besitzerId")); 
				u.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				return u; 	
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	public ArrayList <Umfrage> findAllByAnwender (Anwender anwender) {
		Connection con = DBConnection.connection(); 
		
		ArrayList <Umfrage> resultarray = new ArrayList <Umfrage>(); 
		
		try {
			Statement stmt = con.createStatement(); 
			
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, gruppenId, erstellDatum FROM umfrage" + 
					"WHERE anwenderId = " + anwender.getId() + "ORDER BY name"); 
		
			/**FÜr jeden Eintrag im Suchergebnis wird jetzt ein Anwender-Objekt erstellt und die 
			 * ArrayListe Stück für Stück aufgebaut/gefuellt.
			 */
			
			while (resultset.next()) {
		        Umfrage u = new Umfrage();
				u.setId(resultset.getInt("id"));
				u.setName(resultset.getString("name"));
				u.setGruppenId(resultset.getInt("gruppenId"));
				u.setBesitzerId(resultset.getInt("besitzerId")); 
				u.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				
		        // Hinzufügen des neuen Objekts zur ArrayList
		        resultarray.add(u); 
		      }
		    } catch (SQLException e1) {
		      e1.printStackTrace();
			
		}
		return resultarray;
	}
	
	
	
	
	
	public ArrayList <Umfrage> findAllByAnwenderOwner (Anwender anwenderOwner) {
		Connection con = DBConnection.connection(); 
		
		ArrayList <Umfrage> resultarray = new ArrayList <Umfrage> (); 
		
		try {
			Statement stmt = con.createStatement(); 
			
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, gruppenId, erstellDatum FROM umfrage" +
					"WHERE besitzerId = " + anwenderOwner.getId() + "ORDER BY name"); 
			
			while (resultset.next()) {
				Umfrage u = new Umfrage (); 
				u.setId(resultset.getInt("id"));
				u.setName(resultset.getString("name"));
				u.setGruppenId(resultset.getInt("gruppenId"));
				u.setBesitzerId(resultset.getInt("besitzerId")); 
				u.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				 // Hinzufügen des neuen Objekts zur ArrayList
		        resultarray.add(u); 
			} 
		} catch (SQLException e1) {
			e1.printStackTrace();
		} return resultarray;
	}
	
	
	
	
	
	public ArrayList <Umfrage> findAllClosedByAnwender (Anwender anwender) {
		Connection con = DBConnection.connection(); 
		
		ArrayList <Umfrage> resultarray = new ArrayList <Umfrage> (); 
	// Wie kann ich den Boolean isOpen testen?
		try {
			Statement stmt = con.createStatement(); 
			
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, gruppenId, erstellDatum FROM umfrage" +
					"WHERE isOpen = false, besitzerId = " + anwender.getId() + "ORDER BY name"); 
			
			while (resultset.next()) {
				Umfrage u = new Umfrage (); 
				u.setId(resultset.getInt("id"));
				u.setName(resultset.getString("name"));
				u.setGruppenId(resultset.getInt("gruppenId"));
				u.setBesitzerId(resultset.getInt("besitzerId")); 
				u.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				
				 // Hinzufügen des neuen Objekts zur ArrayList
		        resultarray.add(u); 
			} 
		} catch (SQLException e1) {
			e1.printStackTrace();
		} return resultarray;
	}
	
	
	
	
	
	public ArrayList <Umfrage> findAllByGruppe (Gruppe gruppe) {
		Connection con = DBConnection.connection(); 
		
		ArrayList <Umfrage> resultarray = new ArrayList <Umfrage> (); 
		
		try {
			Statement stmt = con.createStatement(); 
			
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, gruppenId, erstellDatum FROM umfrage" +
					"WHERE gruppeId = " + gruppe.getId() + "ORDER BY name"); 
			
			while (resultset.next()) {
				Umfrage u = new Umfrage (); 
				u.setId(resultset.getInt("id"));
				u.setName(resultset.getString("name"));
				u.setGruppenId(resultset.getInt("gruppenId"));
				u.setBesitzerId(resultset.getInt("besitzerId")); 
				u.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				
				 // Hinzufügen des neuen Objekts zur ArrayList
		        resultarray.add(u); 
			} 
		} catch (SQLException e1) {
			e1.printStackTrace();
		} return resultarray;
	}
	
	
	
	
	
	public void addEigentumsstruktur (Anwender anwender, Umfrage umfrage) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE umfrage SET " + "besitzerId=\""
				+ anwender.getId() + "\" " + "WHERE id=" + umfrage.getId());
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	
	
	
	
	
	
	public void deleteEigentumsstruktur ( Umfrage umfrage) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE umfrage SET " + "besitzerId=\""
				+ "" + "\" " + "WHERE id=" + umfrage.getId());
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	
	


}


