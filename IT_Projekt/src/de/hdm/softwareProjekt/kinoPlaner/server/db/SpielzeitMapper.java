package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.Spielzeit;

//Das hier ist eine Mapper-Klasse, die Spielzeit-Objekte auf eine relationale DB abbildet.

public class SpielzeitMapper {
	
//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	
	private static SpielzeitMapper spielzeitMapper; 
	
// Gesch�tzter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	
	protected SpielzeitMapper () {
		
	}
	
// Es folgt eine Reihe Methoden, die wir im StarUML aufgef�hrt haben. Sie erm�glichen, dass man Objekte z.B. suchen, l�schen und updaten kann.

	public void insert (Spielzeit spielzeit) {
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
				stmt.executeUpdate("INSERT INTO spielzeit (id, zeit)" +
						"VALUES(" + spielzeit.getId() + "','" + spielzeit.getZeit() + ")"); 
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void update (Spielzeit spielzeit) {
		
	}
	
	public void delete (Spielzeit spielzeit) {
		
	}
	
	public ArrayList <Spielzeit> findAllSpielzeiten() {
		
	}
	
	public Spielzeit findById (int id) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			ResultSet resultset = stmt.executeQuery("SELECT id, zeit FROM spielzeit" + 
					"WHERE id=" + id + " ORDER BY zeit"); 
			// Pr�fe ob das geklappt hat, also ob ein Ergebnis vorhanden ist: 
			if (resultset.next()) {
				Spielzeit sz = new Spielzeit();
				sz.setId(resultset.getInt("id"));
				sz.setZeit(resultset.getTime("zeit"));
				return sz; 	
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	
	

}
