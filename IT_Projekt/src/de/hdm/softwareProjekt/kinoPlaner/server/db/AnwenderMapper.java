package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.Anwender;

//Das hier ist eine Mapper-Klasse, die Anwender-Objekte auf eine relationale DB abbildet. 


public class AnwenderMapper {

//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	private static AnwenderMapper anwenderMapper; 
	
	
// Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	protected AnwenderMapper () {
		
	}
	
/*Es folgt eine Reihe von Methoden, die wir im StarUML aufgeführt haben. 
Sie ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.
 */
	
	public void insert (Anwender anwender) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			//Jetzt wird geschaut, welches die höchste Id der schon bestehenden Anwender ist.
			ResultSet resultset = stmt.executeQuery("SELECT MAX (id) AS maxId " + "FROM anwender"); 
			if (resultset.next()) {
				// Wenn die höchste Id gefunden wurde, wird eine neue Id mit +1 höher erstellt
				anwender.setId(resultset.getInt("maxId")+1);
				stmt = con.createStatement();
				
				//Jetzt wird die Id tatsächlich eingefügt: 
				stmt.executeUpdate("INSERT INTO anweder (id, gmail, name)" +
						"VALUES(" + anwender.getId() + "','" + anwender.getGmail() + "','" + anwender.getName() + ")"); 
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void update (Anwender anwender) {
		
	}
	
	public void delete (Anwender anwender) {
		
	}
	
	public ArrayList <Anwender> findAllByAnwender (Anwender anwender) {
		
	}
	
	
	public Anwender findById (int id) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			ResultSet resultset = stmt.executeQuery("SELECT id, gmail, name FROM anwender" + "WHERE id=" + id + " ORDER BY name"); 
			// Prüfe ob das geklappt hat, also ob ein Ergebnis vorhanden ist: 
			if (resultset.next()) {
				Anwender a = new Anwender(); 
				a.setId(resultset.getInt("id")); 
				a.setGmail(resultset.getString("gmail")); 
				a.setName(resultset.getString("name")); 
				return a; 	
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null; 
	}
	
	
	public Anwender findByName (String name) {
		
	}
	
	public ArrayList <Anwender> findAllByGruppe (Gruppe gruppe){
		
	}
	
	
	
}
