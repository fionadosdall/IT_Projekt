package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielplan;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrageoption;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

// Das hier ist eine Mapper-Klasse, die Umfrageoption-Objekte auf eine relationale DB abbildet.

public class VorstellungMapper {

//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	private static VorstellungMapper vorstellungMapper; 
	
// Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	protected VorstellungMapper() {
	}
	
	/** Um eine Instanz dieses Mappers erstellen zu können, nutzt man NICHT den KONSTRUKTOR, 
	 * sondern die folgende Methode. 
	 * Sie ist statisch, dadurch stellt sie sicher, dass nur eine einzige Instanz dieser Klasse existiert.	
	 * @param anwender
	 */
		
		public static VorstellungMapper vorstellungMapper () {
			if (vorstellungMapper == null) {
				vorstellungMapper = new VorstellungMapper();
			}
			return vorstellungMapper; 
		}
	
	
// Es folgt eine Reihe Methoden, die wir im StarUML aufgeführt haben. Sie ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.
	
	public Vorstellung insert (Vorstellung vorstellung) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			//Jetzt wird geschaut, welches die höchste Id der schon bestehenden Vorstellungen ist.
			ResultSet resultset = stmt.executeQuery("SELECT MAX (id) AS maxId " + "FROM vorstellung"); 
			if (resultset.next()) {
				// Wenn die höchste Id gefunden wurde, wird eine neue Id mit +1 höher erstellt
				vorstellung.setId(resultset.getInt("maxId")+1);
				stmt = con.createStatement();
				
				//Jetzt wird die Id tatsächlich eingefügt: 
				stmt.executeUpdate("INSERT INTO vorstellung (id, name, filmId, spielzeitId)" +
						"VALUES(" + vorstellung.getId() + "','" + vorstellung.getName() + "','" + 
							vorstellung.getFilmId() + "','" + vorstellung.getSpielzeitId() + ")"); 
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}return vorstellung; 
	}
	
	
	
	public Vorstellung update (Vorstellung vorstellung) {
		return vorstellung;
	}
	
	
	
	public void delete (Vorstellung vorstellung) {
		
	}
	
	
	
	public ArrayList <Vorstellung> findAllVorstellungen () {
		Connection con = DBConnection.connection();

		ArrayList<Vorstellung> resultarray = new ArrayList<Vorstellung>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery("SELECT id, name, filmId, spielzeitId FROM vorstellung" + 
					"ORDER BY name");

			while (resultset.next()) {
				Vorstellung v = new Vorstellung();
				v.setId(resultset.getInt("id"));
				v.setName(resultset.getString("name"));
				v.setFilmId(resultset.getInt("filmId"));
				v.setSpielzeitId(resultset.getInt("spielzeitId"));

				// Hinzufügen des neuen Objekts zur ArrayList
				resultarray.add(v);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultarray;
	}
	
	
	
	
	public ArrayList <Vorstellung> findAllBySpielplan (Spielplan spielplan) {
		Connection con = DBConnection.connection(); 
		
		ArrayList <Vorstellung> resultarray = new ArrayList <Vorstellung>(); 
		
		try {
			Statement stmt = con.createStatement(); 
			
			ResultSet resultset = stmt.executeQuery("SELECT id, name, filmId, spielplanId FROM vorstellung" + 
					"WHERE spielplanId = " + spielplan + "ORDER BY name"); 
		
			/**FÜr jeden Eintrag im Suchergebnis wird jetzt ein Anwender-Objekt erstellt und die 
			 * ArrayListe Stück für Stück aufgebaut/gefuellt.
			 */
			
			while (resultset.next()) {
		        Vorstellung v = new Vorstellung();
		        v.setId(resultset.getInt("id"));
				v.setName(resultset.getString("name"));
				v.setFilmId(resultset.getInt("filmId"));
				v.setSpielplanId(resultset.getInt("spielplanId"));

		        // Hinzufügen des neuen Objekts zur ArrayList
		        resultarray.add(v); 
		      }
		    } catch (SQLException e1) {
		      e1.printStackTrace();
			
		}
		return resultarray;
	}
	
	
	
	
	public Vorstellung findById (int id) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			ResultSet resultset = stmt.executeQuery("SELECT id, name, filmId, spielzeitId FROM vorstellung" + 
					"WHERE id=" + id + " ORDER BY name"); 
			// Prüfe ob das geklappt hat, also ob ein Ergebnis vorhanden ist: 
			if (resultset.next()) {
				Vorstellung v = new Vorstellung();
				v.setId(resultset.getInt("id"));
				v.setName(resultset.getString("name"));
				v.setFilmId(resultset.getInt("filmId"));
				v.setSpielzeitId(resultset.getInt("spielzeitId"));
				return v; 	
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
}
