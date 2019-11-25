package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;

//Das hier ist eine Mapper-Klasse, die Spielzeit-Objekte auf eine relationale DB abbildet.

public class SpielzeitMapper {
	
//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	
	private static SpielzeitMapper spielzeitMapper; 
	
// Gesch�tzter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	
	protected SpielzeitMapper () {
	}
	
	
	/** Um eine Instanz dieses Mappers erstellen zu k�nnen, nutzt man NICHT den KONSTRUKTOR, 
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
	
		
		
// Es folgt eine Reihe Methoden, die wir im StarUML aufgef�hrt haben. Sie erm�glichen, dass man Objekte z.B. suchen, l�schen und updaten kann.

	public Spielzeit insert (Spielzeit spielzeit) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			//Jetzt wird geschaut, welches die h�chste Id der schon bestehenden Spielzeiten ist.
			ResultSet resultset = stmt.executeQuery("SELECT MAX (id) AS maxId " + "FROM spielzeit"); 
			if (resultset.next()) {
				// Wenn die h�chste Id gefunden wurde, wird eine neue Id mit +1 h�her erstellt
				spielzeit.setId(resultset.getInt("maxId")+1);
				stmt = con.createStatement();
				
				//Jetzt wird die Id tats�chlich eingef�gt: 
				stmt.executeUpdate("INSERT INTO spielzeit (id, name, besitzerId, zeit, erstellDatum)" +
						"VALUES(" + spielzeit.getId() + "','" + spielzeit.getName() + "','" + spielzeit.getBesitzerId() 
						+ "','" + spielzeit.getZeit() + "','" + spielzeit.getErstellDatum() + ")"); 
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} return spielzeit;
	}
	
	
	
	
	public Spielzeit update (Spielzeit spielzeit) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE spielzeit SET " + "name=\""
				+ spielzeit.getName() + "\", " + "erstellDatum=\""
				+ spielzeit.getErstellDatum() + "\", " + "besitzerId=\""
				+ spielzeit.getBesitzerId() + "\", " + "zeit=\""
				+ spielzeit.getZeit() +  "\" " + "WHERE id=" + spielzeit.getId());
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
		return spielzeit;
	}
	
	
	
	
	
	public void delete (Spielzeit spielzeit) {
		Connection con = DBConnection.connection();

	    try {
	      Statement stmt = con.createStatement();

	      stmt.executeUpdate("DELETE FROM spielzeit " + "WHERE id=" + spielzeit.getId());

	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }
	}
	
	
	
	
	
	public ArrayList <Spielzeit> findAllSpielzeiten() {
		Connection con = DBConnection.connection(); 
		
		ArrayList <Spielzeit> resultarray = new ArrayList <Spielzeit> (); 
		
		try {
			Statement stmt = con.createStatement(); 
			
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, zeit, erstellDatum FROM spielzeit" + "ORDER BY zeit"); 
			
			while (resultset.next()) {
				Spielzeit sz = new Spielzeit(); 
				sz.setId(resultset.getInt("id"));
				sz.setName(resultset.getString("name"));
				sz.setBesitzerId(resultset.getInt("besitzerId"));
				sz.setZeit(resultset.getDate("zeit"));
				sz.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				 // Hinzuf�gen des neuen Objekts zur ArrayList
		        resultarray.add(sz); 
			} 
		} catch (SQLException e1) {
			e1.printStackTrace();
		} return resultarray; 
	}
	
	public ArrayList<Spielzeit> findAllByAnwenderOwner(Anwender anwender) {
Connection con = DBConnection.connection(); 
		
		ArrayList <Spielzeit> resultarray = new ArrayList <Spielzeit> (); 
		
		try {
			Statement stmt = con.createStatement(); 
			
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, zeit, erstellDatum FROM spielzeit" 
			+"WHERE besitzerId=" + anwender.getId() + "ORDER BY zeit"); 
			
			while (resultset.next()) {
				Spielzeit sz = new Spielzeit(); 
				sz.setId(resultset.getInt("id"));
				sz.setName(resultset.getString("name"));
				sz.setBesitzerId(resultset.getInt("besitzerId"));
				sz.setZeit(resultset.getDate("zeit"));
				sz.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				 // Hinzuf�gen des neuen Objekts zur ArrayList
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
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, zeit, erstellDatum FROM spielzeit" + 
					"WHERE id=" + id + " ORDER BY zeit"); 
			// Pr�fe ob das geklappt hat, also ob ein Ergebnis vorhanden ist: 
			if (resultset.next()) {
				Spielzeit sz = new Spielzeit();
				sz.setId(resultset.getInt("id"));
				sz.setName(resultset.getString("name"));
				sz.setBesitzerId(resultset.getInt("besitzerId"));
				sz.setZeit(resultset.getDate("zeit"));
				sz.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				return sz; 	
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	

	

}
