package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.Kinokette;

//Das hier ist eine Mapper-Klasse, die Kino-Objekte auf eine relationale DB abbildet.


public class KinoMapper {
	
//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	private static KinoMapper kinoMapper; 
	
// Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	protected KinoMapper() {
			
	}
	
// Es folgt eine Reihe Methoden, die wir im StarUML aufgeführt haben. Sie ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.
	

	public void insert (Kino kino) {
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
				stmt.executeUpdate("INSERT INTO kino (id, name, besitzerId)" +
						"VALUES(" + kino.getId() + "','" + kino.getName() + "','" + kino.getBesitzerId() + ")"); 
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void update (Kino kino) {
		
	}
	
	public void delete (Kino kino) {
		
	}
	
	public ArrayList <Kino> findAllKinos () {
		
	}
	
	public Kino findById (int id) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId FROM kino" + 
					"WHERE id=" + id + " ORDER BY name"); 
			// Prüfe ob das geklappt hat, also ob ein Ergebnis vorhanden ist: 
			if (resultset.next()) {
				Kino k = new Kino();
				k.setId(resultset.getInt("id"));
				k.setName(resultset.getString("name"));
				k.setBesitzerId(resultset.getInt("besitzerId")); 
				return k; 	
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null; 
	}
	
	public ArrayList <Kino> findAllByAnwenderOwner (Anwender anwenderOwner) {
		
	}
	
	public void addSpielplan (Spielplan spielplan, Kino kino) {
		
	}
	
	public void deleteSpielplan (Spielplan spielplan, Kino kino) {
		
	}
	
	public void addKinokette (Kinokette kinokette, Kino kino) {
		
	}
	
	public void deleteKinokette (Kinokette kinokette, Kino kino) {
		
	}
	
	public void addEigentumsstruktur (Anwender anwender, Kino kino) {
		
	}
	
	public void deleteEigentumsstruktur (Anwender anwender, Kino kino) {
		
	}
	
		
	
	
}
