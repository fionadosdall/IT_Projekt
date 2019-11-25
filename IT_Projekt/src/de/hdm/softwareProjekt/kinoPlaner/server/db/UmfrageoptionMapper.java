package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrageoption;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

// Das hier ist eine Mapper-Klasse, die Umfrageoption-Objekte auf eine relationale DB abbildet. 

public class UmfrageoptionMapper {

//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	private static UmfrageoptionMapper umfrageoptionMapper;

// Gesch�tzter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	protected UmfrageoptionMapper() {
	}

	/**
	 * Um eine Instanz dieses Mappers erstellen zu k�nnen, nutzt man NICHT den
	 * KONSTRUKTOR, sondern die folgende Methode. Sie ist statisch, dadurch stellt
	 * sie sicher, dass nur eine einzige Instanz dieser Klasse existiert.
	 * 
	 * @param anwender
	 */

	public static UmfrageoptionMapper umfrageoptionMapper() {
		if (umfrageoptionMapper == null) {
			umfrageoptionMapper = new UmfrageoptionMapper();
		}
		return umfrageoptionMapper;
	}

// Es folgt eine Reihe Methoden, die wir im StarUML aufgef�hrt haben. Sie erm�glichen, dass man Objekte z.B. suchen, l�schen und updaten kann.

	public Umfrageoption insert(Umfrageoption umfrageoption) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			// Jetzt wird geschaut, welches die h�chste Id der schon bestehenden
			// Umfrageoptionen ist.
			ResultSet resultset = stmt.executeQuery("SELECT MAX (id) AS maxId " + "FROM umfrageoption");
			if (resultset.next()) {
				// Wenn die h�chste Id gefunden wurde, wird eine neue Id mit +1 h�her erstellt
				umfrageoption.setId(resultset.getInt("maxId") + 1);
				stmt = con.createStatement();

				// Jetzt wird die Id tats�chlich eingef�gt:
				stmt.executeUpdate("INSERT INTO umfrageoption (id, umfrageId, vorstellungsId)" + "VALUES("
						+ umfrageoption.getId() + "','" + umfrageoption.getUmfrageId() + "','"
						+ umfrageoption.getVorstellungsId() + ")");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return umfrageoption;
	}

	
	
	public Umfrageoption update(Umfrageoption umfrageoption) {
		return umfrageoption;
	}

	
	
	
	public void delete(Umfrageoption umfrageoption) {
		Connection con = DBConnection.connection();

	    try {
	      Statement stmt = con.createStatement();

	      stmt.executeUpdate("DELETE FROM umfrageoption " + "WHERE id=" + umfrageoption.getId());

	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }
	}

	
	
	
	public ArrayList<Umfrageoption> findAllByUmfrage(Umfrage umfrage) {
		Connection con = DBConnection.connection();

		ArrayList<Umfrageoption> resultarray = new ArrayList<Umfrageoption>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery("SELECT id, umfrageId, vorstellungsId FROM umfrageoption" + 
					"ORDER BY name");

			while (resultset.next()) {
				Umfrageoption uo = new Umfrageoption();
				uo.setId(resultset.getInt("id"));
				uo.setUmfrageId(resultset.getInt("umfrageId"));
				uo.setVorstellungsId(resultset.getInt("vorstellungsId"));

				// Hinzuf�gen des neuen Objekts zur ArrayList
				resultarray.add(uo);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultarray;
	}

	
	
	
	
	public Umfrageoption findById(int id) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			ResultSet resultset = stmt.executeQuery("SELECT id, umfrageId, vorstellungsId FROM umfrageoption"
					+ "WHERE id=" + id + " ORDER BY vorstellungsId");
			// Pr�fe ob das geklappt hat, also ob ein Ergebnis vorhanden ist:
			if (resultset.next()) {
				Umfrageoption uopt = new Umfrageoption();
				uopt.setId(resultset.getInt("id"));
				uopt.setUmfrageId(resultset.getInt("umfrageId"));
				uopt.setVorstellungsId(resultset.getInt("vorstellungsId"));
				return uopt;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	public ArrayList <Umfrageoption> findAllByVorstellung (Vorstellung vorstellung) {
		return; 
	}
}
