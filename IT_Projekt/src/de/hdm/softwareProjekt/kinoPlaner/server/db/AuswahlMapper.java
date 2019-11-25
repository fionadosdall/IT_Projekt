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
		
		public static AuswahlMapper auswahlMapper() {
			if (votingMapper == null) {
				votingMapper = new AuswahlMapper();
			}
			return votingMapper; 
		}
/*Es folgt eine Reihe von Methoden, die wir im StarUML aufgeführt haben. 
Sie ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.
*/
	
	public Auswahl insert (Auswahl auswahl) {
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
				stmt.executeUpdate("INSERT INTO auswahl (id, name, besitzerId, umfrageoptionId, voting, erstellDatum)" +
						"VALUES(" + auswahl.getId() + "','" + auswahl.getName() + "','" + auswahl.getBesitzerId() + "','" 
						+ auswahl.getUmfrageoptionId() + "','" + auswahl.getVoting() + "','" + auswahl.getErstellDatum() 
						+ ")"); 
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} return auswahl;
	}
	
	
	
	public Auswahl update (Auswahl auswahl) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE auswahl SET " + "besitzerId=\""
				+ auswahl.getBesitzerId() + "\", " + "umfrageoptionId=\""
				+ auswahl.getUmfrageoptionId() + "\", " + "voting=\"" 
				+ auswahl.getVoting()+ "\", " + "name=\"" 
				+ auswahl.getName()+ "\", " + "erstellDatum=\"" 
				+ auswahl.getErstellDatum()+ "\" " + "WHERE id=" + auswahl.getId());
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
		return auswahl; 
	}
	
	
	
	
	public void delete (Auswahl auswahl) {
		Connection con = DBConnection.connection();

	    try {
	      Statement stmt = con.createStatement();

	      stmt.executeUpdate("DELETE FROM auswahl " + "WHERE id=" + auswahl.getId());

	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }
	}
	
	
	
	
	public Auswahl findById (int id) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, umfrageoptionId, voting, erstellDatum FROM auswahl" + 
					"WHERE id=" + id + " ORDER BY umfrageoptionId"); 
			// Prüfe ob das geklappt hat, also ob ein Ergebnis vorhanden ist: 
			if (resultset.next()) {
				Auswahl a = new Auswahl(); 
				a.setId(resultset.getInt("id")); 
				a.setName(resultset.getString("name"));
				a.setBesitzerId(resultset.getInt("besitzerId")); 
				a.setUmfrageoptionId(resultset.getInt("umfrageoptionId"));
				a.setVoting(resultset.getInt("voting")); 
				a.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				return a; 	
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null; 
		
	}
	
	
	
	
	public ArrayList <Auswahl> findAllByUmfrageoption (Umfrageoption umfrageoption) {
		Connection con = DBConnection.connection(); 
		
		ArrayList <Auswahl> resultarray = new ArrayList <Auswahl> (); 
		
		try {
			Statement stmt = con.createStatement(); 
			
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, umfrageoptionId, voting, erstellDatum FROM auswahl" +
					"WHERE umfrageoptionId=" + umfrageoption.getId() + "ORDER BY besitzerId"); 
			while (resultset.next()) { 
				Auswahl a = new Auswahl(); 
				a.setId(resultset.getInt("id")); 
				a.setName(resultset.getString("name"));
				a.setBesitzerId(resultset.getInt("besitzerId")); 
				a.setUmfrageoptionId(resultset.getInt("umfrageoptionId"));
				a.setVoting(resultset.getInt("voting")); 
				a.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				
				resultarray.add(a);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultarray; 
	}
	
	
	
	
	public void addEigentumsstruktur (Anwender anwender, Auswahl auswahl) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE auswahl SET " + "besitzerId=\""
				+ anwender.getId() + "\" " + "WHERE id=" + auswahl.getId());
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
		
	}
	
	
	
	
	
	public void deleteEigentumsstruktur ( Auswahl auswahl) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE auswahl SET " + "besitzerId=\""
				+ "" + "\" " + "WHERE id=" + auswahl.getId());
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	
	public Auswahl findByAnwenderAndUmfrageoption(Anwender anwender, Umfrageoption umfrageoption) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, umfrageoptionId, voting, erstellDatum FROM auswahl" + 
					"WHERE besitzerId=" + anwender.getId() + "AND umfrageoptionId=" +umfrageoption.getId()); 
			// Prüfe ob das geklappt hat, also ob ein Ergebnis vorhanden ist: 
			if (resultset.next()) {
				Auswahl a = new Auswahl(); 
				a.setId(resultset.getInt("id")); 
				a.setName(resultset.getString("name"));
				a.setBesitzerId(resultset.getInt("besitzerId")); 
				a.setUmfrageoptionId(resultset.getInt("umfrageoptionId"));
				a.setVoting(resultset.getInt("voting")); 
				a.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				return a; 	
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null; 
		
	}
	
	public ArrayList<Auswahl> findAllByAnwenderOwner(Anwender anwender) {
		Connection con = DBConnection.connection(); 
		
		ArrayList <Auswahl> resultarray = new ArrayList <Auswahl> (); 
		
		try {
			Statement stmt = con.createStatement(); 
			
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, umfrageoptionId, voting, erstellDatum FROM auswahl" +
					"WHERE besitzerId=" + anwender.getId() + "ORDER BY id"); 
			while (resultset.next()) { 
				Auswahl a = new Auswahl(); 
				a.setId(resultset.getInt("id")); 
				a.setName(resultset.getString("name"));
				a.setBesitzerId(resultset.getInt("besitzerId")); 
				a.setUmfrageoptionId(resultset.getInt("umfrageoptionId"));
				a.setVoting(resultset.getInt("voting")); 
				a.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				
				resultarray.add(a);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultarray; 
	}
	

}
