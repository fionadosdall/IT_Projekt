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
	
	/**
	 * Hier folgt die Klassenvariable. Diese Variable ist wegen "static" nur einmal
	 * vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	 */
	private static AuswahlMapper votingMapper; 
	
	
	/**
	 * Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann.
	 * Denn von diesem Mapper wird nur 1x eine Instanz erzeugt
	 */
	protected AuswahlMapper () {
	}
	
	
	/** Um eine Instanz dieses Mappers erstellen zu können, nutzt man NICHT den KONSTRUKTOR, 
	 * sondern die folgende Methode. 
	 * Sie ist statisch, dadurch stellt sie sicher, dass nur eine einzige Instanz dieser Klasse existiert.
	 * Außerdem wird zuerst überprüft, ob bereits ein votingMapper existiert, falls nein, wird ein neuer
	 * instanziiert. Existiert bereits ein votingMapper, wird dieser zurück gegeben.	
	 */
		
		public static AuswahlMapper auswahlMapper() {
			if (votingMapper == null) {
				votingMapper = new AuswahlMapper();
			}
			return votingMapper; 
		}

	/**
	 * Es folgt eine Reihe von Methoden, die wir im StarUML aufgeführt haben. Sie
	 * ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.
	 */

	/**
	 * Die insert-Methode fügt ein neues Anwender-Objekt zur Datenbank hinzu.
	 * @param auswahl bzw. das zu speichernde Objekt
	 * @return Das übergeben Objekt, ggf. mit abgeänderter Id
	 */
	public Auswahl insert (Auswahl auswahl) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			/**
			 * Im Folgenden: Überprüfung, welches die höchste Id der schon bestehenden Anwender ist.
			 */
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
		} 
		/**
		 * Rückgabe der Auswahl. Durch die Methode wurde das Objekt ggf. angepasst (z.B. angepasste Id)
		 */
		return auswahl;
	}
	
	
	/**
	 * Das Objekt wieder wiederholt, in upgedateter Form in die Datenbank eingetragen.
	 * @param auswahl bzw. das Objekt das verändert werden soll.
	 * @return Das Objekt, welches im Parameter übergeben wurde.
	 */
	public Auswahl update (Auswahl auswahl) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			/**
			 * Update wird in die Datenbank eingetragen
			 */
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
		/**
		 * Rückgabe des neuen, veränderten Auswahl-Objektes
		 */
		return auswahl; 
	}
	
	
	
	/**
	 * Mit dieser Methode kann ein Auswahl-Objekt aus der Datenbank gelöscht werden.
	 * @param auswahl Objekt, welches gelöscht werden soll
	 */
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
	
	
	
	/**
	 * Suche nach einer bestimmten Auswahl mithilfe einer vorgegebenen Auswahl-Id
	 * @param id der Auswahl, nach welcher gesucht werden soll
	 * @return Das Auswahl-Objekt, das mit seiner Auswahl-Id der übergebenen Id entspricht.
	 * Falls kein Auswahl-Objekt zur übergebenen Id gefunden wird, wird null zurückgegeben. 
	 */
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
	
	
	
	/** 
	 * Suche nach allen getroffenen Auswahlen, die zu einer bestimmten, im Parameter vorgegebenen
	 * Umfrageoption gehören. 
	 * @param umfrageoption zu welcher man alle getroffenen Auswahlen ausgegebenen haben möchte.
	 * @return Eine ArrayList, die alle gefundenen Auswahlen enthält. Falls eine Exception geworfen wird, 
	 * kann es passieren, dass die ArrayList leer oder nur teilweise befüllt zurückgegeben wird.
	 */
	public ArrayList <Auswahl> findAllByUmfrageoption (Umfrageoption umfrageoption) {
		Connection con = DBConnection.connection(); 
		
		ArrayList <Auswahl> resultarray = new ArrayList <Auswahl> (); 
		
		try {
			Statement stmt = con.createStatement(); 
			
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, umfrageoptionId, voting, erstellDatum FROM auswahl" +
					"WHERE umfrageoptionId=" + umfrageoption.getId() + "ORDER BY besitzerId"); 
			
			/**
			 * Für jeden Eintrag im Sucheregbnis wird nun ein Auswahl-Objekt erstellt. Damit wird die 
			 * ArrayListe nun Durchlauf für Durchlauf der Schleife aufgebaut/befüllt.
			 */
			while (resultset.next()) { 
				Auswahl a = new Auswahl(); 
				a.setId(resultset.getInt("id")); 
				a.setName(resultset.getString("name"));
				a.setBesitzerId(resultset.getInt("besitzerId")); 
				a.setUmfrageoptionId(resultset.getInt("umfrageoptionId"));
				a.setVoting(resultset.getInt("voting")); 
				a.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				
				// Hinzufügen des neuen Objektes zur ArrayList
				resultarray.add(a);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultarray; 
	}
	
	
	
	/**
	 * Eine Auswahl erhält eine BesitzerId. Diese BesitzerId wird einem bestimmten Anwender zugewiesen. 
	 * Dadurch lässt sich eine Eigentumsbeziehung zwischen den beiden Objekten herstellen. 
	 * Wenn ein Anwender Besitzer eines (hier: Auswahl-)Objektes ist, fallen ihm besondere Rechte zu. Er kann
	 * z.B. als einziger Veränderungen vornehmen. 
	 * @param anwender welcher als Besitzer der Auswahl in der Datenbank eingetragen werden soll.
	 * @param auswahl Objekt, welches einem Anwender zugeordnet werden soll.
	 */
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
	
	
	
	/**
	 * Eine Auswahl hat eine BesitzerId, diese wurde einem bestimmten Anwender zugewiesen und soll nun
	 * gelöscht werden. Die Eigentumsbeziehung wird demnach aufgehoben und in der DB gelöscht. 
	 * @param auswahl Objekt bei welchem die BesitzerId in der Datenbank zurückgesetzt werden soll.
	 */
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
	
	
	/**
	 * Ein Auswahl-Objekt wird mithilfe einer vorgegebenen Umfrageoption, zu welcher die Auswahl 
	 * gehört gesucht. Außerdem wird der Anwender angegeben, welcher die Umfrageoption entsprechend der
	 * Auswahl beantwortet hat. 
	 * @param anwender Objekt nach welchem die Suche nach der Auswahl gefiltert wird.
	 * @param umfrageoption zu der das gesuchte Auswahl-Objekt gehört.
	 * @return Das gesuchte Auswahl-Objekt. Befindet sich kein Auswahl-Objekt in der Datenbank, welches
	 * sowohl zum Anwender, als auch zur Umfrageoption zugehörig ist, wird null zurückgegeben.
	 */
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
	
	/**
	 * Suche nach allen Auswahl-Objekten, die eine Eigentumsbeziehung mit einem vorgegebenen Anwender haben. 
	 * @param anwender Objekt, dessen Id mit der BesitzerId der gesuchten Auswahl-Objekte übereinstimmen soll
	 * @return Alle Auswahl-Objekte, die die Id des vorgegebenen Anwenders als BesitzerId in der DB 
	 * eingetragen haben.
	 */
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
				
				// Hinzufügen des neuen Objekts zur ArrayList
				resultarray.add(a);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultarray; 
	}
	

}
