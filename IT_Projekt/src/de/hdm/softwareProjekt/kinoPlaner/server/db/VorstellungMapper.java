package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielplan;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

/**
 * Das hier ist eine Mapper-Klasse, die Umfrageoption-Objekte auf eine
 * relationale DB abbildet.
 * 
 * @author annaf
 *
 */

public class VorstellungMapper {

	/**
	 * Hier folgt die Klassenvariable. Diese Variable ist wegen "static" nur einmal
	 * vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	 */
	private static VorstellungMapper vorstellungMapper;

	/**
	 * Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann.
	 * Denn von diesem Mapper wird nur 1x eine Instanz erzeugt
	 */
	protected VorstellungMapper() {
	}

	/**
	 * Um eine Instanz dieses Mappers erstellen zu können, nutzt man NICHT den
	 * KONSTRUKTOR, sondern die folgende Methode. Sie ist statisch, dadurch stellt
	 * sie sicher, dass nur eine einzige Instanz dieser Klasse existiert. Außerdem
	 * wird zuerst überprüft, ob bereis ein vorstellungMapper existiert, falls nein,
	 * wird ein neuer instanziiert. Existiert bereits ein vorstellungMapper, wird
	 * dieser zurückgegeben.
	 */

	public static VorstellungMapper vorstellungMapper() {
		if (vorstellungMapper == null) {
			vorstellungMapper = new VorstellungMapper();
		}
		return vorstellungMapper;
	}

	/**
	 * Es folgt eine Reihe von Methoden, die wir im StarUML aufgeführt haben. Sie
	 * ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.
	 */

	/**
	 * Suche nach allen Vorstellungen über vorgegebenen Namen.
	 * 
	 * @param name den die gesuchten Vorstellungen tragen
	 * @return Eine ArrayList, die alle gefundenen Vorstellungen enthält. Falls eine
	 *         Exception geworfen wird, kann es passieren, dass die ArrayList leer
	 *         oder nur teilweise befüllt zurück gegeben wird.
	 */
	public ArrayList<Vorstellung> findAllByName(String name) {
		Connection con = DBConnection.connection();

		ArrayList<Vorstellung> resultarray = new ArrayList<Vorstellung>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery("SELECT id, name, filmId, spielzeitId, spielplanId, erstellDatum"
					+ "FROM vorstellung" + "WHERE name = " + name + "ORDER BY name");

			/**
			 * Für jeden Eintrag im Suchergebnis wird jetzt ein Vorstellungs-Objekt erstellt
			 * und die ArrayListe Stück für Stück aufgebaut/gefuellt.
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
		/**
		 * Rückgabe der Vorstellungen als ArrayList.
		 */
		return resultarray;
	}

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

	/**
	 * Die insert-Methode fügt ein neues Vorstellungs-Objekt zur Datenbank hinzu.
	 * 
	 * @param vorstellung als das zu speichernde Objekt
	 * @return Das bereits übergeben Objekt, ggf. mit abgeänderter Id
	 */
	public Vorstellung insert(Vorstellung vorstellung) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			/**
			 * Im Folgenden: Überprüfung, welches die höchste Id der schon bestehenden
			 * Vorstellungen ist.
			 */
			ResultSet resultset = stmt.executeQuery("SELECT MAX (id) AS maxId " + "FROM vorstellung");
			if (resultset.next()) {
				// Wenn die höchste Id gefunden wurde, wird eine neue Id mit +1 höher erstellt
				vorstellung.setId(resultset.getInt("maxId") + 1);
				stmt = con.createStatement();

				// Jetzt wird die Id tatsächlich eingefügt:
				stmt.executeUpdate("INSERT INTO vorstellung (id, name, filmId, spielzeitId, spielplanId, erstellDatum)"
						+ "VALUES(" + vorstellung.getId() + "','" + vorstellung.getName() + "','"
						+ vorstellung.getFilmId() + "','" + vorstellung.getSpielzeitId() + "','"
						+ vorstellung.getSpielplanId() + "','" + vorstellung.getErstellDatum() + ")");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		/**
		 * Rückgabe der Vostellung. Durch die Methode wurde das Objekt ggf. angepasst
		 * (z.B. angepasste Id)
		 */
		return vorstellung;
	}

	/**
	 * Das Objekt wird wiederholt, in geupdateter Form in die Datenbank eingetragen.
	 * 
	 * @param vorstellung als das Objekt, das verändert werden soll.
	 * @return Das Objekt, welches im Parameter übergeben wurde.
	 */
	public Vorstellung update(Vorstellung vorstellung) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			/**
			 * Update wird in die Datenbank eingetragen.
			 */
			stmt.executeUpdate("UPDATE vorstellung SET " + "name=\"" + vorstellung.getName() + "\", "
					+ "erstellDatum=\"" + vorstellung.getErstellDatum() + "\", " + "spielplanId=\""
					+ vorstellung.getSpielzeitId() + "\", " + "spielzeitId=\"" + vorstellung.getSpielzeitId() + "\", "
					+ "filmId=\"" + vorstellung.getFilmId() + "\" " + "WHERE id=" + vorstellung.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		/**
		 * Rückgabe des neuen, veränderten Vorstellungs-Objektes
		 */
		return vorstellung;
	}

	/**
	 * Mit dieser Methode kann ein Vorstellungs-Objekt aus der Datenbank gelöscht
	 * werden.
	 * 
	 * @param vorstellung Objekt, welches gelöscht werden soll.
	 */
	public void delete(Vorstellung vorstellung) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM vorstellung " + "WHERE id=" + vorstellung.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Alle in der Datenbank vorhandenen Vorstellungen sollen gesucht und ausgegeben
	 * werden
	 * 
	 * @return Alle Vorstellungs-Objekte, die in der Datenbank eingetragen sind,
	 *         werden in einer ArrayList zurückgegeben.
	 */
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

				// Hinzufügen des neuen Objekts zur ArrayList
				resultarray.add(v);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		/**
		 * Rückgabe der Vorstellungen als ArrayList.
		 */
		return resultarray;
	}

	/**
	 * Suche nach allen Vorstellungs-Objekten, die eine Beziehung mit einem
	 * vorgegebenen Spielplan haben. Liegt eine Beziehung zwischen Vorstellung und
	 * Spielplan vor, ist in der Datenbank in der Vorstellungs-Tabelle die
	 * entsprechende spielplanId hinterlegt. Diese spielplanId muss mit der Id des
	 * im Methodenparamter übergebenen Spielplans übereinstimmen.
	 * 
	 * @param spielplan Objekt, dessen Id mit den spielplanIds in der
	 *                  Vorstellungs-Tabelle übereinstimmen soll.
	 * @return Alle Vorstellungs-Objekte in einer ArrayList, deren spielplanId dem
	 *         übergebenen Spielplan entsprechen.
	 */
	public ArrayList<Vorstellung> findAllBySpielplan(Spielplan spielplan) {
		Connection con = DBConnection.connection();

		ArrayList<Vorstellung> resultarray = new ArrayList<Vorstellung>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery("SELECT id, name, filmId, spielzeitId, spielplanId, erstellDatum"
					+ "FROM vorstellung" + "WHERE spielplanId = " + spielplan.getId() + "ORDER BY name");

			/**
			 * Für jeden Eintrag im Suchergebnis wird jetzt ein Vorstellungs-Objekt erstellt
			 * und die ArrayListe Stück für Stück aufgebaut/gefuellt.
			 */
			while (resultset.next()) {
				Vorstellung v = new Vorstellung();
				v.setId(resultset.getInt("id"));
				v.setName(resultset.getString("name"));
				v.setFilmId(resultset.getInt("filmId"));
				v.setSpielzeitId(resultset.getInt("spielzeitId"));
				v.setSpielplanId(resultset.getInt("spielplanId"));
				v.setErstellDatum(resultset.getTimestamp("erstellDatum"));

				// Hinzufügen des neuen Objekts zur ArrayList
				resultarray.add(v);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();

		}
		// ArrayList mit Ergebnis zurückgeben
		return resultarray;
	}

	/**
	 * Suche nach einer Vorstellung mit vorgegebener Vorstellungs-Id
	 * 
	 * @param id zugehörig zu einer Vorstellung, nach welcher gesucht werden soll,
	 *           also der Primärschlüssel in der Datenbank.
	 * @return Das Vorstellungs-Objekt, das mit seiner Vorstellungs-Id der
	 *         übergebenen Id entspricht. Falls keine Vorstellung zur übergebenen Id
	 *         gefunden wurde, wird null zurückgegeben.
	 */
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
