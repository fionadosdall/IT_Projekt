package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;

/**
 * Das hier ist eine Mapper-Klasse, die Spielzeit-Objekte auf eine relationale
 * DB abbildet.
 * 
 * @author annaf
 *
 */

public class SpielzeitMapper {

	/**
	 * Hier folgt die Klassenvariable. Diese Variable ist wegen "static" nur einmal
	 * vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	 */
	private static SpielzeitMapper spielzeitMapper;

	/**
	 * Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann.
	 * Denn von diesem Mapper wird nur 1x eine Instanz erzeugt
	 */
	protected SpielzeitMapper() {
	}

	/**
	 * Um eine Instanz dieses Mappers erstellen zu können, nutzt man NICHT den
	 * KONSTRUKTOR, sondern die folgende Methode. Sie ist statisch, dadurch stellt
	 * sie sicher, dass nur eine einzige Instanz dieser Klasse existiert. Außerdem
	 * wird zuerst überprüft, ob bereis ein spielzeitMapper existiert, falls nein,
	 * wird ein neuer instanziiert. Existiert bereits ein gruppeMapper, wird dieser
	 * zurückgegeben.
	 */

	public static SpielzeitMapper spielzeitMapper() {
		if (spielzeitMapper == null) {
			spielzeitMapper = new SpielzeitMapper();
		}
		return spielzeitMapper;
	}

	/**
	 * Es folgt eine Reihe von Methoden, die wir im StarUML aufgeführt haben. Sie
	 * ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.
	 */

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

			ResultSet resultset = stmt.executeQuery("SELECT name FROM spielzeit" + "WHERE name =" + name);

			if (resultset.next()) {
				return false;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return true;
	}

	/**
	 * Die insert-Methode fügt ein neues Spielzeit-Objekt zur Datenbank hinzu.
	 * 
	 * @param spielzeit bzw. das zu speichernde Objekt
	 * @return Das bereits übergeben Objekt, ggf. mit abgeänderter Id
	 */
	public Spielzeit insert(Spielzeit spielzeit) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			/**
			 * Im Folgenden: Überprüfung, welches die höchste Id der schon bestehenden
			 * Anwender ist.
			 */
			ResultSet resultset = stmt.executeQuery("SELECT MAX (id) AS maxId " + "FROM spielzeit");
			if (resultset.next()) {
				// Wenn die höchste Id gefunden wurde, wird eine neue Id mit +1 höher erstellt
				spielzeit.setId(resultset.getInt("maxId") + 1);
				stmt = con.createStatement();

				// Jetzt wird die Id tatsächlich eingefügt:
				stmt.executeUpdate("INSERT INTO spielzeit (id, name, besitzerId, zeit, erstellDatum)" + "VALUES("
						+ spielzeit.getId() + "','" + spielzeit.getName() + "','" + spielzeit.getBesitzerId() + "','"
						+ spielzeit.getZeit() + "','" + spielzeit.getErstellDatum() + ")");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		/**
		 * Rückgabe des Kinos. Durch die Methode wurde das Objekt ggf. angepasst (z.B.
		 * angepasste Id)
		 */
		return spielzeit;
	}

	/**
	 * Das Objekt wird wiederholt, in geupdateter Form in die Datenbank eingetragen.
	 * 
	 * @param spielzeit als das Objekt, das verändert werden soll.
	 * @return Das Objekt, welches im Parameter übergeben wurde.
	 */
	public Spielzeit update(Spielzeit spielzeit) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			/**
			 * Update wird in die Datenbank eingetragen.
			 */
			stmt.executeUpdate("UPDATE spielzeit SET " + "name=\"" + spielzeit.getName() + "\", " + "erstellDatum=\""
					+ spielzeit.getErstellDatum() + "\", " + "besitzerId=\"" + spielzeit.getBesitzerId() + "\", "
					+ "zeit=\"" + spielzeit.getZeit() + "\" " + "WHERE id=" + spielzeit.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		/**
		 * Rückgabe des neuen, veränderten Spielzeit-Objektes
		 */
		return spielzeit;
	}

	/**
	 * Mit dieser Methode kann ein Spielzeit-Objekt aus der Datenbank gelöscht
	 * werden.
	 * 
	 * @param spielzeit Objekt, welches gelöscht werden soll.
	 */
	public void delete(Spielzeit spielzeit) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM spielzeit " + "WHERE id=" + spielzeit.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Alle in der Datenbank vorhandenen Spielzeiten sollen gesucht und ausgegeben
	 * werden
	 * 
	 * @return Alle Spielzeit-Objekte, die in der Datenbank eingetragen sind, werden
	 *         in einer ArrayList zurückgegeben.
	 */
	public ArrayList<Spielzeit> findAllSpielzeiten() {
		Connection con = DBConnection.connection();

		ArrayList<Spielzeit> resultarray = new ArrayList<Spielzeit>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt
					.executeQuery("SELECT id, name, besitzerId, zeit, erstellDatum FROM spielzeit" + "ORDER BY zeit");

			while (resultset.next()) {
				Spielzeit sz = new Spielzeit();
				sz.setId(resultset.getInt("id"));
				sz.setName(resultset.getString("name"));
				sz.setBesitzerId(resultset.getInt("besitzerId"));
				sz.setZeit(resultset.getDate("zeit"));
				sz.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				// Hinzufügen des neuen Objekts zur ArrayList
				resultarray.add(sz);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// Rückgabe des Ergebnisses in Form einer ArrayList
		return resultarray;
	}

	/**
	 * Suche nach allen Spielzeit-Objekten, die eine Eigentumsbeziehung mit einem
	 * vorgegebene Anwender haben. Daraus wird ersichtlich, welcher Anwender
	 * besondere Rechte in Bezug auf welche Spielzeiten hat. Besondere Rechte können
	 * zum Beispiel sein, dass der Anwender das jeweilige Objekt verändern darf.
	 * 
	 * @param anwender Objekt, dessen Id mit der BesitzerId der gesuchten
	 *                 Spielzeit-Objekte übereinstimmen soll.
	 * @return Alle Spielzeit-Objekte, die die Id des vorgegebenen Anwenders als
	 *         BesitzerId in der Datenbank eingetragen haben.
	 */
	public ArrayList<Spielzeit> findAllByAnwenderOwner(Anwender anwender) {
		Connection con = DBConnection.connection();

		ArrayList<Spielzeit> resultarray = new ArrayList<Spielzeit>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, zeit, erstellDatum FROM spielzeit"
					+ "WHERE besitzerId=" + anwender.getId() + "ORDER BY zeit");

			while (resultset.next()) {
				Spielzeit sz = new Spielzeit();
				sz.setId(resultset.getInt("id"));
				sz.setName(resultset.getString("name"));
				sz.setBesitzerId(resultset.getInt("besitzerId"));
				sz.setZeit(resultset.getDate("zeit"));
				sz.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				// Hinzuf�gen des neuen Objekts zur ArrayList
				resultarray.add(sz);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		//Rückgabe des Ergebnisses in Form einer ArrayList
		return resultarray;
	}

	/**
	 * Suche nach einer Spielzeit mit vorgegebener Spielzeit-Id
	 * 
	 * @param id zugehörig zu einer Spielzeit, nach welcher gesucht werden soll,
	 *           also der Primärschlüssel in der Datenbank.
	 * @return Das Spielzeit-Objekt, das mit seiner Spielzeit-Id der übergebenen Id
	 *         entspricht. Falls keine Spielzeit zur übergebenen Id gefunden wurde,
	 *         wird null zurückgegeben.
	 */
	public Spielzeit findById(int id) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, zeit, erstellDatum FROM spielzeit"
					+ "WHERE id=" + id + " ORDER BY zeit");
			// Pr�fe ob das geklappt hat, also ob ein Ergebnis vorhanden ist:
			if (resultset.next()) {
				Spielzeit sz = new Spielzeit();
				sz.setId(resultset.getInt("id"));
				sz.setName(resultset.getString("name"));
				sz.setBesitzerId(resultset.getInt("besitzerId"));
				sz.setZeit(resultset.getDate("zeit"));
				sz.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				return sz;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

}
