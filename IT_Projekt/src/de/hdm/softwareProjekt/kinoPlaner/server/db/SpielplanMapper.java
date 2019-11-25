package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.sun.crypto.provider.RSACipher;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielplan;

//Das hier ist eine Mapper-Klasse, die Spielplan-Objekte auf eine relationale DB abbildet.

public class SpielplanMapper {
	
//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	
	private static SpielplanMapper spielplanMapper; 
	
// Gesch�tzter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	
	protected SpielplanMapper () {
	}
	
	
	/** Um eine Instanz dieses Mappers erstellen zu k�nnen, nutzt man NICHT den KONSTRUKTOR, 
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
	
// Es folgt eine Reihe Methoden, die wir im StarUML aufgef�hrt haben. Sie erm�glichen, dass man Objekte z.B. suchen, l�schen und updaten kann.
	
	
	public Spielplan insert (Spielplan spielplan) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			//Jetzt wird geschaut, welches die h�chste Id der schon bestehenden Spielplaene ist.
			ResultSet resultset = stmt.executeQuery("SELECT MAX (id) AS maxId " + "FROM spielplan"); 
			if (resultset.next()) {
				// Wenn die h�chste Id gefunden wurde, wird eine neue Id mit +1 h�her erstellt
				spielplan.setId(resultset.getInt("maxId")+1);
				stmt = con.createStatement();
				
				//Jetzt wird die Id tats�chlich eingef�gt: 
				stmt.executeUpdate("INSERT INTO spielplan (id, name, besitzerId, kinoId, erstellDatum)" +
						"VALUES(" + spielplan.getId() + "','" + spielplan.getName() + "','" + spielplan.getBesitzerId() 
						+ "','" + spielplan.getKinoId() + "','" + spielplan.getErstellDatum() + ")"); 
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} return spielplan;
	}
	
	
	
	
	public Spielplan update (Spielplan spielplan) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE spielplan SET " + "name=\""
				+ spielplan.getName() + "\", " + "besitzerId=\""
				+ spielplan.getBesitzerId() + "\", " + "kinoId=\""
				+ spielplan.getKinoId() + "\", " + "erstellDatum=\""
				+ spielplan.getErstellDatum() +  "\" " + "WHERE id=" + spielplan.getId());
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
		return spielplan;
	}
	
	
	
	
	public void delete (Spielplan spielplan) {
		Connection con = DBConnection.connection();

	    try {
	      Statement stmt = con.createStatement();

	      stmt.executeUpdate("DELETE FROM spielplan " + "WHERE id=" + spielplan.getId());

	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }
	}
	
	
	
	
	
	public Spielplan findById (int id) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, kinoId, erstellDatum FROM spielplan" + 
					"WHERE id=" + id + " ORDER BY kinoId"); 
			// Pr�fe ob das geklappt hat, also ob ein Ergebnis vorhanden ist: 
			if (resultset.next()) {
				Spielplan sp = new Spielplan();
				sp.setId(resultset.getInt("id"));
				sp.setName(resultset.getString("name"));
				sp.setBesitzerId(resultset.getInt("besitzerId"));
				sp.setKinoId(resultset.getInt("kinoId")); 
				sp.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				
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
			
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, kinoId, erstellDatum FROM spielplan" +
					"WHERE besitzerId = " + anwenderOwner.getId() + "ORDER BY kinoId"); 
			
			while (resultset.next()) {
				Spielplan sp = new Spielplan();
				sp.setId(resultset.getInt("id"));
				sp.setName(resultset.getString("name"));
				sp.setBesitzerId(resultset.getInt("besitzerId"));
				sp.setKinoId(resultset.getInt("kinoId")); 
				sp.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				
				 // Hinzuf�gen des neuen Objekts zur ArrayList
		        resultarray.add(sp); 
			} 
		} catch (SQLException e1) {
			e1.printStackTrace();
		} return resultarray;
	}
	
	public ArrayList<Spielplan> findAllByKino(Kino kino) {
		Connection con = DBConnection.connection(); 
		
		ArrayList <Spielplan> resultarray = new ArrayList <Spielplan> (); 
		
		try {
			Statement stmt = con.createStatement(); 
			
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, kinoId, erstellDatum FROM spielplan" +
					"WHERE kinoId = " + kino.getId() + "ORDER BY kinoId"); 
			
			while (resultset.next()) {
				Spielplan sp = new Spielplan();
				sp.setId(resultset.getInt("id"));
				sp.setName(resultset.getString("name"));
				sp.setBesitzerId(resultset.getInt("besitzerId"));
				sp.setKinoId(resultset.getInt("kinoId")); 
				sp.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				
				 // Hinzuf�gen des neuen Objekts zur ArrayList
		        resultarray.add(sp); 
			} 
		} catch (SQLException e1) {
			e1.printStackTrace();
		} return resultarray;
	}
	
	
	
	public void addEigentumsstruktur (Anwender anwender, Spielplan spielplan) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE spielplan SET " + "besitzerId=\""
				+ anwender.getId() + "\" " + "WHERE id=" + spielplan.getId());
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	
	public void deleteEigentumsstruktur (Anwender anwender, Spielplan spielplan) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE spielplan SET " + "besitzerId=\""
				+ "" + "\" " + "WHERE id=" + spielplan.getId());
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

}
