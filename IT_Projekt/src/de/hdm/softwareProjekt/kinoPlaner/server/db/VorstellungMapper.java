package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielplan;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

// Das hier ist eine Mapper-Klasse, die Umfrageoption-Objekte auf eine relationale DB abbildet.

public class VorstellungMapper {

//	Hier folgt die Klassenvariable. 
//  Diese Variable ist wegen "static" nur einmal vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	private static VorstellungMapper vorstellungMapper;

// Gesch�tzter Konstruktor, damit man nicht einfach neue Instanzen bilden kann?? Weil eigentlich wird von 
// einem Mapper nur 1x eine Instanz erzeugt
	protected VorstellungMapper() {
	}

	/**
	 * Um eine Instanz dieses Mappers erstellen zu k�nnen, nutzt man NICHT den
	 * KONSTRUKTOR, sondern die folgende Methode. Sie ist statisch, dadurch stellt
	 * sie sicher, dass nur eine einzige Instanz dieser Klasse existiert.
	 * 
	 * @param anwender
	 */

	public static VorstellungMapper vorstellungMapper() {
		if (vorstellungMapper == null) {
			vorstellungMapper = new VorstellungMapper();
		}
		return vorstellungMapper;
	}

// Es folgt eine Reihe Methoden, die wir im StarUML aufgef�hrt haben. Sie erm�glichen, dass man Objekte z.B. suchen, l�schen und updaten kann.

	/**
	 * Bei der Erstellung eines neuen Objektes soll zunächst geprüft werden, ob der
	 * gewünschte Name für das Objekt nicht bereits in der entsprechenden Tabelle
	 * der Datenbank vorhanden ist. Damit soll verhindert werden, dass mehrere
	 * Objekte den selben Namen tragen.
	 * 
	 * @param name den das zu erstellende Objekt tragen soll
	 * @return false, wenn der Name bereits einem anderen, existierenden Objekt
	 *         zugeordnet ist. True, wenn der Name in der Datenbanktabelle noch
	 *         nicht vergeben ist.
	 */
	public boolean nameVerfügbar(String name) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery("SELECT name FROM vorstellung" + "WHERE name =" + name);

			if (resultset.next()) {
				return false;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return true;
	}

	public Vorstellung insert(Vorstellung vorstellung) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			// Jetzt wird geschaut, welches die h�chste Id der schon bestehenden
			// Vorstellungen ist.
			ResultSet resultset = stmt.executeQuery("SELECT MAX (id) AS maxId " + "FROM vorstellung");
			if (resultset.next()) {
				// Wenn die h�chste Id gefunden wurde, wird eine neue Id mit +1 h�her erstellt
				vorstellung.setId(resultset.getInt("maxId") + 1);
				stmt = con.createStatement();

				// Jetzt wird die Id tats�chlich eingef�gt:
				stmt.executeUpdate("INSERT INTO vorstellung (id, name, filmId, spielzeitId, spielplanId, erstellDatum)"
						+ "VALUES(" + vorstellung.getId() + "','" + vorstellung.getName() + "','"
						+ vorstellung.getFilmId() + "','" + vorstellung.getSpielzeitId() + "','"
						+ vorstellung.getSpielplanId() + "','" + vorstellung.getErstellDatum() + ")");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return vorstellung;
	}

	public Vorstellung update(Vorstellung vorstellung) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE vorstellung SET " + "name=\"" + vorstellung.getName() + "\", "
					+ "erstellDatum=\"" + vorstellung.getErstellDatum() + "\", " + "spielplanId=\""
					+ vorstellung.getSpielzeitId() + "\", " + "spielzeitId=\"" + vorstellung.getSpielzeitId() + "\", "
					+ "filmId=\"" + vorstellung.getFilmId() + "\" " + "WHERE id=" + vorstellung.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return vorstellung;
	}

	public void delete(Vorstellung vorstellung) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM vorstellung " + "WHERE id=" + vorstellung.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	public ArrayList<Vorstellung> findAllVorstellungen() {
		Connection con = DBConnection.connection();

		ArrayList<Vorstellung> resultarray = new ArrayList<Vorstellung>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt
					.executeQuery("SELECT id, name, filmId, spielzeitId, spielplanId, erstellDatum FROM vorstellung"
							+ "ORDER BY name");

			while (resultset.next()) {
				Vorstellung v = new Vorstellung();
				v.setId(resultset.getInt("id"));
				v.setName(resultset.getString("name"));
				v.setFilmId(resultset.getInt("filmId"));
				v.setSpielzeitId(resultset.getInt("spielzeitId"));
				v.setSpielplanId(resultset.getInt("spielplanId"));
				v.setErstellDatum(resultset.getTimestamp("erstellDatum"));

				// Hinzuf�gen des neuen Objekts zur ArrayList
				resultarray.add(v);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultarray;
	}

	public ArrayList<Vorstellung> findAllBySpielplan(Spielplan spielplan) {
		Connection con = DBConnection.connection();

		ArrayList<Vorstellung> resultarray = new ArrayList<Vorstellung>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt
					.executeQuery("SELECT id, name, filmId, spielzeitId, spielplanId, erstellDatum FROM vorstellung"
							+ "WHERE spielplanId = " + spielplan.getId() + "ORDER BY name");

			/**
			 * F�r jeden Eintrag im Suchergebnis wird jetzt ein Anwender-Objekt erstellt und
			 * die ArrayListe St�ck f�r St�ck aufgebaut/gefuellt.
			 */

			while (resultset.next()) {
				Vorstellung v = new Vorstellung();
				v.setId(resultset.getInt("id"));
				v.setName(resultset.getString("name"));
				v.setFilmId(resultset.getInt("filmId"));
				v.setSpielzeitId(resultset.getInt("spielzeitId"));
				v.setSpielplanId(resultset.getInt("spielplanId"));
				v.setErstellDatum(resultset.getTimestamp("erstellDatum"));

				// Hinzuf�gen des neuen Objekts zur ArrayList
				resultarray.add(v);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();

		}
		return resultarray;
	}

	public Vorstellung findById(int id) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			ResultSet resultset = stmt
					.executeQuery("SELECT id, name, filmId, spielzeitId, spielplanId, erstellDatum FROM vorstellung"
							+ "WHERE id=" + id + " ORDER BY name");
			// Pr�fe ob das geklappt hat, also ob ein Ergebnis vorhanden ist:
			if (resultset.next()) {
				Vorstellung v = new Vorstellung();
				v.setId(resultset.getInt("id"));
				v.setName(resultset.getString("name"));
				v.setFilmId(resultset.getInt("filmId"));
				v.setSpielzeitId(resultset.getInt("spielzeitId"));
				v.setSpielplanId(resultset.getInt("spielplanId"));
				v.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				return v;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

}
