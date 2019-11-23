package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.Spielplan;

//Das hier ist eine Mapper-Klasse, die Spielplan-Objekte auf eine relationale DB abbildet.

public class SpielplanMapper {
	
//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	
	private static SpielplanMapper spielplanMapper; 
	
// Gesch�tzter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	
	protected SpielplanMapper () {
		
	}
	
// Es folgt eine Reihe Methoden, die wir im StarUML aufgef�hrt haben. Sie erm�glichen, dass man Objekte z.B. suchen, l�schen und updaten kann.
	
	
	public void insert (Spielplan spielplan) {
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
				stmt.executeUpdate("INSERT INTO spielplan (id, kinoId)" +
						"VALUES(" + spielplan.getId() + "','" + spielplan.getKinoId() + ")"); 
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void update (Spielplan spielplan) {
		
	}
	
	public void delete (Spielplan spielplan) {
		
	}
	
	public Spielplan findById (int id) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			ResultSet resultset = stmt.executeQuery("SELECT id, kinoId FROM spielplan" + 
					"WHERE id=" + id + " ORDER BY kinoId"); 
			// Pr�fe ob das geklappt hat, also ob ein Ergebnis vorhanden ist: 
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
		
	}
	
	public void addEigentumsstruktur (Anwender anwender, Spielplan spielplan) {
		
	}
	
	public void deleteEigentumsstruktur (Anwender anwender, Spielplan spielplan) {
		
	}
	

}
