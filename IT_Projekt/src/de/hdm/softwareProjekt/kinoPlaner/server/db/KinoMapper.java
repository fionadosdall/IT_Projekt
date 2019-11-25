package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielplan;

//Das hier ist eine Mapper-Klasse, die Kino-Objekte auf eine relationale DB abbildet.


public class KinoMapper {
	
//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	private static KinoMapper kinoMapper; 
	
// Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	protected KinoMapper() {		
	}
	
	
	
	
	/** Um eine Instanz dieses Mappers erstellen zu können, nutzt man NICHT den KONSTRUKTOR, 
	 * sondern die folgende Methode. 
	 * Sie ist statisch, dadurch stellt sie sicher, dass nur eine einzige Instanz dieser Klasse existiert.	
	 * @param anwender
	 */
		
		public static KinoMapper kinoMapper () {
			if (kinoMapper == null) {
				kinoMapper = new KinoMapper();
			}
			return kinoMapper; 
		}
	
		
		
		
// Es folgt eine Reihe Methoden, die wir im StarUML aufgeführt haben. Sie ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.
	

	public Kino insert (Kino kino) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			//Jetzt wird geschaut, welches die höchste Id der schon bestehenden Kinos ist.
			ResultSet resultset = stmt.executeQuery("SELECT MAX (id) AS maxId " + "FROM kino"); 
			if (resultset.next()) {
				// Wenn die höchste Id gefunden wurde, wird eine neue Id mit +1 höher erstellt
				kino.setId(resultset.getInt("maxId")+1);
				stmt = con.createStatement();
				
				//Jetzt wird die Id tatsächlich eingefügt: 
				stmt.executeUpdate("INSERT INTO kino (id, name, besitzerId, plz, stadt, strasse, hausnummer, erstellDatum)" +
						"VALUES(" + kino.getId() + "','" + kino.getName() + "','" + kino.getBesitzerId() + "','" 
						+ kino.getPlz() + "','" + kino.getStadt() + "','" + kino.getStrasse() + "','" 
						+ kino.getHausnummer() + "','" + kino.getErstellDatum() + ")"); 
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} return kino;
	}
	
	
	
	
	public Kino update (Kino kino) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE kino SET " + "besitzerId=\""
				+ kino.getBesitzerId() + "\", " + "name=\""
				+ kino.getName() + "\", " + "erstellDatum=\""
				+ kino.getErstellDatum() + "\", " + "plz=\""
				+ kino.getPlz() + "\", " + "stadt=\""
				+ kino.getStadt() + "\", " + "strasse=\""
				+ kino.getStrasse()+ "\", " + "hausnummer=\""
				+ kino.getHausnummer() +  "\" " + "WHERE id=" + kino.getId());
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
		return kino;
	}
	
	
	
	
	public void delete (Kino kino) {
		Connection con = DBConnection.connection();

	    try {
	      Statement stmt = con.createStatement();

	      stmt.executeUpdate("DELETE FROM kino " + "WHERE id=" + kino.getId());

	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }
	}
	
	
	
	
	public ArrayList <Kino> findAllKinos () {
		Connection con = DBConnection.connection(); 
		
		ArrayList <Kino> resultarray = new ArrayList <Kino> (); 
		
		try {
			Statement stmt = con.createStatement(); 
			
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, plz, stadt, strasse, hausnummer, erstellDatum FROM kino" + "ORDER BY name"); 
			
			while (resultset.next()) {
				Kino k = new Kino(); 
				k.setId(resultset.getInt("id"));
				k.setBesitzerId(resultset.getInt("besitzerId"));
				k.setName(resultset.getString("name"));
				k.setPlz(resultset.getInt("plz"));
				k.setStadt(resultset.getString("stadt"));
				k.setStrasse(resultset.getString("strasse"));
				k.setHausnummer(resultset.getString("hausnummer"));			
				k.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				
				 // Hinzufügen des neuen Objekts zur ArrayList
		        resultarray.add(k); 
			} 
		} catch (SQLException e1) {
			e1.printStackTrace();
		} return resultarray; 
	}
	
	
	
	
	public Kino findById (int id) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, plz, stadt, strasse, hausnummer, erstellDatum FROM kino" + 
					"WHERE id=" + id + " ORDER BY name"); 
			// Prüfe ob das geklappt hat, also ob ein Ergebnis vorhanden ist: 
			if (resultset.next()) {
				Kino k = new Kino();
				k.setId(resultset.getInt("id"));
				k.setBesitzerId(resultset.getInt("besitzerId"));
				k.setName(resultset.getString("name"));
				k.setPlz(resultset.getInt("plz"));
				k.setStadt(resultset.getString("stadt"));
				k.setStrasse(resultset.getString("strasse"));
				k.setHausnummer(resultset.getString("hausnummer"));			
				k.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				return k; 	
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null; 
	}
	
	
	
	
	public ArrayList <Kino> findAllByAnwenderOwner (Anwender anwenderOwner) {
		Connection con = DBConnection.connection(); 
		
		ArrayList <Kino> resultarray = new ArrayList <Kino> (); 
		
		try {
			Statement stmt = con.createStatement(); 
			
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, plz, stadt, strasse, hausnummer, erstellDatum FROM kino" +
					"WHERE besitzerId = " + anwenderOwner.getId() + "ORDER BY name"); 
			
			while (resultset.next()) {
				Kino k = new Kino(); 
				k.setId(resultset.getInt("id"));
				k.setBesitzerId(resultset.getInt("besitzerId"));
				k.setName(resultset.getString("name"));
				k.setPlz(resultset.getInt("plz"));
				k.setStadt(resultset.getString("stadt"));
				k.setStrasse(resultset.getString("strasse"));
				k.setHausnummer(resultset.getString("hausnummer"));			
				k.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				
				 // Hinzufügen des neuen Objekts zur ArrayList
		        resultarray.add(k); 
			} 
		} catch (SQLException e1) {
			e1.printStackTrace();
		} return resultarray;
	}
	

	
	public void addKinokette (Kinokette kinokette, Kino kino) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE kino SET " + "kinokettenId=\""
				+ kinokette.getId() + "\" " + "WHERE id=" + kino.getId());
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	
	
	
	
	public void deleteKinokette ( Kino kino) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE kino SET " + "kinokettenId=\""
				+ "" + "\" " + "WHERE id=" + kino.getId());
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	
	
	
	
	
	public void addEigentumsstruktur (Anwender anwender, Kino kino) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE kino SET " + "besitzerId=\""
				+ anwender.getId() + "\" " + "WHERE id=" + kino.getId());
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	
	
	
	
	
	public void deleteEigentumsstruktur ( Kino kino) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE kino SET " + "besitzerId=\""
				+ "" + "\" " + "WHERE id=" + kino.getId());
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	
	public ArrayList<Kino> findAllByKinokette(Kinokette kinokette) {
	Connection con = DBConnection.connection(); 
		
		ArrayList <Kino> resultarray = new ArrayList <Kino> (); 
		
		try {
			Statement stmt = con.createStatement(); 
			
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, plz, stadt, strasse, hausnummer, erstellDatum FROM kino" +
					"WHERE kinokettenId = " + kinokette.getId() + "ORDER BY name"); 
			
			while (resultset.next()) {
				Kino k = new Kino(); 
				k.setId(resultset.getInt("id"));
				k.setBesitzerId(resultset.getInt("besitzerId"));
				k.setName(resultset.getString("name"));
				k.setPlz(resultset.getInt("plz"));
				k.setStadt(resultset.getString("stadt"));
				k.setStrasse(resultset.getString("strasse"));
				k.setHausnummer(resultset.getString("hausnummer"));			
				k.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				
				 // Hinzufügen des neuen Objekts zur ArrayList
		        resultarray.add(k); 
			} 
		} catch (SQLException e1) {
			e1.printStackTrace();
		} return resultarray;
	}
	
	
}
