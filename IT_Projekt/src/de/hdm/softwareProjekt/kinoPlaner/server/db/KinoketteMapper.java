package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.Gruppe;
import de.hdm.softwareProjekt.kinoPlaner.shared.Kinokette;

//Das hier ist eine Mapper-Klasse, die Kinoketten-Objekte auf eine relationale DB abbildet.

public class KinoketteMapper {

//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	
	private static KinoketteMapper kinoketteMapper; 
	

// Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	protected KinoketteMapper() {
		
	}
	
// Es folgt eine Reihe Methoden, die wir im StarUML aufgeführt haben. Sie ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.
	
	public void insert (Kinokette kinokette) {
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
		}
	}
	
	public void update (Kinokette kinokette) {
		
	}
	
	public void delete (Kinokette kinokette) {
		
	}
	
	public ArrayList <Kinokette> findAllKinoketten () {
		
	}
	
	public Kinokette findById (int id) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId FROM kinokette" + 
					"WHERE id=" + id + " ORDER BY name"); 
			// Prüfe ob das geklappt hat, also ob ein Ergebnis vorhanden ist: 
			if (resultset.next()) {
				Kinokette kk = new Kinokette(); 
				kk.setId(resultset.getInt("id"));
				kk.setName(resultset.getString("name"));
				kk.setBesitzerId(resultset.getInt("besitzerId")); 
				return kk; 	
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null; 
	}
	
	public ArrayList <Kinokette> findAllByAnwenderOwner (Anwender anwenderOwner) {
		
	}
	
	public void addEigentumsstruktur (Anwender anwender, Kinokette kinokette) {
		
	}
	
	public void deleteEigentumsstruktur (Anwender anwender, Kinokette kinokette) {
		
	}
	
	
}
