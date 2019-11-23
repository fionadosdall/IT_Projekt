package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.Umfrageoption;
import de.hdm.softwareProjekt.kinoPlaner.shared.Vorstellung;

// Das hier ist eine Mapper-Klasse, die Umfrageoption-Objekte auf eine relationale DB abbildet.

public class VorstellungMapper {

//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	private static VorstellungMapper vorstellungMapper; 
	
// Gesch�tzter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	protected VorstellungMapper() {
		
	}
	
	
// Es folgt eine Reihe Methoden, die wir im StarUML aufgef�hrt haben. Sie erm�glichen, dass man Objekte z.B. suchen, l�schen und updaten kann.
	
	public void insert (Vorstellung vorstellung) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			//Jetzt wird geschaut, welches die h�chste Id der schon bestehenden Vorstellungen ist.
			ResultSet resultset = stmt.executeQuery("SELECT MAX (id) AS maxId " + "FROM vorstellung"); 
			if (resultset.next()) {
				// Wenn die h�chste Id gefunden wurde, wird eine neue Id mit +1 h�her erstellt
				vorstellung.setId(resultset.getInt("maxId")+1);
				stmt = con.createStatement();
				
				//Jetzt wird die Id tats�chlich eingef�gt: 
				stmt.executeUpdate("INSERT INTO vorstellung (id, name, filmId, spielzeitId)" +
						"VALUES(" + vorstellung.getId() + "','" + vorstellung.getName() + "','" + 
							vorstellung.getFilmId() + "','" + vorstellung.getSpielzeitId() + ")"); 
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void update (Vorstellung vorstellung) {
		
	}
	
	public void delete (Vorstellung vorstellung) {
		
	}
	
	public ArrayList <Vorstellung> findAllVorstellungen () {
		
	}
	
	public ArrayList <Vorstellung> findAllBySpielplan (Spielplan spielplan) {
		
	}
	
	public Vorstellung findById (int id) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			ResultSet resultset = stmt.executeQuery("SELECT id, name, filmId, spielzeitId FROM vorstellung" + 
					"WHERE id=" + id + " ORDER BY name"); 
			// Pr�fe ob das geklappt hat, also ob ein Ergebnis vorhanden ist: 
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
