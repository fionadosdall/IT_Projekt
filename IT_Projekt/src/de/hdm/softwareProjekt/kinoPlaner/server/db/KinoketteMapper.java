package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;

//Das hier ist eine Mapper-Klasse, die Kinoketten-Objekte auf eine relationale DB abbildet.

public class KinoketteMapper {

//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	
	private static KinoketteMapper kinoketteMapper; 
	

// Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	protected KinoketteMapper() {
	}
	
	
	/** Um eine Instanz dieses Mappers erstellen zu können, nutzt man NICHT den KONSTRUKTOR, 
	 * sondern die folgende Methode. 
	 * Sie ist statisch, dadurch stellt sie sicher, dass nur eine einzige Instanz dieser Klasse existiert.	
	 * @param anwender
	 */
		
		public static KinoketteMapper kinoketteMapper () {
			if (kinoketteMapper == null) {
				kinoketteMapper = new KinoketteMapper();
			}
			return kinoketteMapper; 
		}
		
		
		
		
	
// Es folgt eine Reihe Methoden, die wir im StarUML aufgeführt haben. Sie ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.
	
	public Kinokette insert (Kinokette kinokette) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			//Jetzt wird geschaut, welches die höchste Id der schon bestehenden Kinoketten ist.
			ResultSet resultset = stmt.executeQuery("SELECT MAX (id) AS maxId " + "FROM kinokette"); 
			if (resultset.next()) {
				// Wenn die höchste Id gefunden wurde, wird eine neue Id mit +1 höher erstellt
				kinokette.setId(resultset.getInt("maxId")+1);
				stmt = con.createStatement();
				
				//Jetzt wird die Id tatsächlich eingefügt: 
				stmt.executeUpdate("INSERT INTO kinokette (id, name, besitzerId)" +
						"VALUES(" + kinokette.getId() + "','" + kinokette.getName() + "','" + kinokette.getBesitzerId() + ")"); 
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} return kinokette;
	}
	
	
	
	
	public Kinokette update (Kinokette kinokette) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE kinokette SET " + "besitzerId=\""
				+ kinokette.getBesitzerId() + "\", " + "name=\""
				+ kinokette.getName() + "\", " + "erstellDatum=\""
				+ kinokette.getErstellDatum() + "\", " + "sitz=\""
				+ kinokette.getSitz() + "\", " + "website=\""
				+ kinokette.getWebsite() +   "\" " + "WHERE id=" + kinokette.getId());
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
		return kinokette; 
	}
	
	
	
	
	public void delete (Kinokette kinokette) {
		Connection con = DBConnection.connection();

	    try {
	      Statement stmt = con.createStatement();

	      stmt.executeUpdate("DELETE FROM kinokette " + "WHERE id=" + kinokette.getId());

	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }
	}
	
	
	
	
	
	public ArrayList <Kinokette> findAllKinoketten () {
		Connection con = DBConnection.connection(); 
		
		ArrayList <Kinokette> resultarray = new ArrayList <Kinokette> (); 
		
		try {
			Statement stmt = con.createStatement(); 
			
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, sitz, website, erstellDatum FROM kinokette" + "ORDER BY name"); 
			
			while (resultset.next()) {
				Kinokette kk = new Kinokette(); 
				kk.setId(resultset.getInt("id"));
				kk.setBesitzerId(resultset.getInt("besitzerId"));
				kk.setName(resultset.getString("name"));
				kk.setSitz(resultset.getString("sitz"));
				kk.setWebsite(resultset.getString("website"));
				kk.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				
				 // Hinzufügen des neuen Objekts zur ArrayList
		        resultarray.add(kk); 
			} 
		} catch (SQLException e1) {
			e1.printStackTrace();
		} return resultarray; 
	}
	
	
	
	
	
	
	public Kinokette findById (int id) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, sitz, website, erstellDatum FROM kinokette" + 
					"WHERE id=" + id + " ORDER BY name"); 
			// Prüfe ob das geklappt hat, also ob ein Ergebnis vorhanden ist: 
			if (resultset.next()) {
				Kinokette kk = new Kinokette(); 
				kk.setId(resultset.getInt("id"));
				kk.setBesitzerId(resultset.getInt("besitzerId"));
				kk.setName(resultset.getString("name"));
				kk.setSitz(resultset.getString("sitz"));
				kk.setWebsite(resultset.getString("website"));
				kk.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				return kk; 	
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null; 
	}
	
	
	
	
	public ArrayList <Kinokette> findAllByAnwenderOwner (Anwender anwenderOwner) {
		Connection con = DBConnection.connection(); 
		
		ArrayList <Kinokette> resultarray = new ArrayList <Kinokette> (); 
		
		try {
			Statement stmt = con.createStatement(); 
			
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, sitz, website, erstellDatum FROM kinokette" +
					"WHERE besitzerId = " + anwenderOwner.getId() + "ORDER BY name"); 
			
			while (resultset.next()) {
				Kinokette kk = new Kinokette(); 
				kk.setId(resultset.getInt("id"));
				kk.setBesitzerId(resultset.getInt("besitzerId"));
				kk.setName(resultset.getString("name"));
				kk.setSitz(resultset.getString("sitz"));
				kk.setWebsite(resultset.getString("website"));
				kk.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				
				 // Hinzufügen des neuen Objekts zur ArrayList
		        resultarray.add(kk); 
			} 
		} catch (SQLException e1) {
			e1.printStackTrace();
		} return resultarray; 
	}
	
	
	
	
	
	public void addEigentumsstruktur (Anwender anwender, Kinokette kinokette) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE kinokette SET " + "besitzerId=\""
				+ anwender.getId() + "\" " + "WHERE id=" + kinokette.getId());
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
		
	
	public void deleteEigentumsstruktur ( Kinokette kinokette) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE kinokette SET " + "besitzerId=\""
				+ "" + "\" " + "WHERE id=" + kinokette.getId());
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	
	
}
