package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.Umfrage;

//Das hier ist eine Mapper-Klasse, die Umfragen-Objekte auf eine relationale DB abbildet.

public class UmfrageMapper {

	
//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	
	private static UmfrageMapper umfrageMapper; 
	
// Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	
	protected UmfrageMapper () {
		
	}
	
// Es folgt eine Reihe Methoden, die wir im StarUML aufgeführt haben. Sie ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.

	public void insert (Umfrage umfrage) {
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
				stmt.executeUpdate("INSERT INTO umfrage (id, name, gruppenId, besitzerId)" +
						"VALUES(" + umfrage.getId() + "','" + umfrage.getName() + "','" + 
							umfrage.getGruppenId() + "','" + umfrage.getBesitzerId() + ")"); 
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void update (Umfrage umfrage) {
		
	}
	
	public void delete (Umfrage umfrage) {
		
	}
	
	public Umfrage findById (int id) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			ResultSet resultset = stmt.executeQuery("SELECT id, name, gruppenId, besitzerId FROM umfrage" + 
					"WHERE id=" + id + " ORDER BY gruppenId"); 
			// Prüfe ob das geklappt hat, also ob ein Ergebnis vorhanden ist: 
			if (resultset.next()) {
				Umfrage u = new Umfrage();
				u.setId(resultset.getInt("id"));
				u.setName(resultset.getString("name"));
				u.setGruppenId(resultset.getInt("gruppenId"));
				u.setBesitzerId(resultset.getInt("besitzerId")); 
				return u; 	
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public ArrayList <Umfrage> findAllByAnwender (Anwender anwender) {
		
	}
	
	public ArrayList <Umfrage> findAllByAnwenderOwner (Anwender anwenderOwner) {
		
	}
	
	public ArrayList <Umfrage> findAllClosedByAnwender (Anwender anwender) {
		
	}
	
	public ArrayList <Umfrage> findAllByGruppe (Gruppe gruppe) {
		
	}
	
	public void addEigentumsstruktur (Anwender anwender, Umfrage umfrage) {
		
	}
	
	public void deleteEigentumsstruktur (Anwender anwender, Umfrage umfrage) {
		
	}
	
	


}


