package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Auswahl;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrageoption;

//Das hier ist eine Mapper-Klasse, die Auswahl-Objekte auf eine relationale DB abbildet. 


public class AuswahlMapper {
	
//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	private static AuswahlMapper votingMapper; 
	
	
// Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	protected AuswahlMapper () {
	}
	
	
	/** Um eine Instanz dieses Mappers erstellen zu können, nutzt man NICHT den KONSTRUKTOR, 
	 * sondern die folgende Methode. 
	 * Sie ist statisch, dadurch stellt sie sicher, dass nur eine einzige Instanz dieser Klasse existiert.	
	 * @param anwender
	 */
		
		public static AuswahlMapper auswahlmapperInstanziieren () {
			if (votingMapper == null) {
				votingMapper = new AuswahlMapper();
			}
			return votingMapper; 
		}
/*Es folgt eine Reihe von Methoden, die wir im StarUML aufgeführt haben. 
Sie ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.
*/
	
	public void insert (Auswahl auswahl) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			//Jetzt wird geschaut, welches die höchste Id der schon bestehenden Auswahlen ist.
			ResultSet resultset = stmt.executeQuery("SELECT MAX (id) AS maxId " + "FROM auswahl"); 
			if (resultset.next()) {
				// Wenn die höchste Id gefunden wurde, wird eine neue Id mit +1 höher erstellt
				auswahl.setId(resultset.getInt("maxId")+1);
				stmt = con.createStatement();
				
				//Jetzt wird die Id tatsächlich eingefügt: 
				stmt.executeUpdate("INSERT INTO auswahl (id, besitzerId, umfrageoptionId, voting)" +
						"VALUES(" + auswahl.getId() + "','" + auswahl.getBesitzerId() + "','" + auswahl.getUmfrageoptionId() + auswahl.getVoting() + ")"); 
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	
	
	public void update (Auswahl auswahl) {
		
	}
	
	
	
	
	public void delete (Auswahl auswahl) {
		
	}
	
	
	
	
	public Auswahl findById (int id) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			ResultSet resultset = stmt.executeQuery("SELECT id, besitzerId, umfrageoptionId, voting FROM auswahl" + 
					"WHERE id=" + id + " ORDER BY umfrageoptionId"); 
			// Prüfe ob das geklappt hat, also ob ein Ergebnis vorhanden ist: 
			if (resultset.next()) {
				Auswahl a = new Auswahl(); 
				a.setId(resultset.getInt("id")); 
				a.setBesitzerId(resultset.getInt("besitzerId")); 
				a.setUmfrageoptionId(resultset.getInt("umfrageoptionId"));
				a.setVoting(resultset.getInt("voting")); 
				return a; 	
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null; 
		
	}
	
	
	
	
	public ArrayList <Auswahl> findByUmfrageoption (Umfrageoption umfrageoption) {
		Connection con = DBConnection.connection(); 
		
		ArrayList <Auswahl> resultarray = new ArrayList <Auswahl> (); 
		
		try {
			Statement stmt = con.createStatement(); 
			
			ResultSet resultset = stmt.executeQuery("SELECT id, besitzerId, umfrageoptionId, voting FROM auswahl" +
					"WHERE umfrageoptionId=" + umfrageoption + "ORDER BY besitzerId"); 
			while (resultset.next()) { 
				Auswahl a = new Auswahl(); 
				a.setId(resultset.getInt("id"));
				a.setBesitzerId(resultset.getInt("besitzerId"));
				a.setUmfrageoptionId(resultset.getInt("umfrageoptionId"));
				a.setVoting(resultset.getInt("voting"));
				
				resultarray.add(a);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null; 
	}
	
	
	
	
	public void addEigentumsstruktur (Anwender anwender, Auswahl auswahl) {
		
	}
	
	
	
	
	
	public void deleteEigentumsstruktur (Anwender anwender, Auswahl auswahl) {
		
	}
	
	
	
	
	

}
