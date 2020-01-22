package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

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
	 * wird ein neuer instanziiert. Existiert bereits ein spielzeitMapper, wird
	 * dieser zurückgegeben.
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
	 * Suche nach allen Spielzeiten über vorgegebenen Namen.
	 * 
	 * @param name
	 *            den die gesuchten Spielzeiten tragen
	 * @return Eine ArrayList, die alle gefundenen Spielzeiten enthält. Falls eine
	 *         Exception geworfen wird, kann es passieren, dass die ArrayList leer
	 *         oder nur teilweise befüllt zurück gegeben wird.
	 */
	public ArrayList<Spielzeit> findAllByName(String name) {
		Connection con = DBConnection.connection();

		ArrayList<Spielzeit> resultarray = new ArrayList<Spielzeit>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery("SELECT szId, szName, spielzeit_anwender_Id, zeit, erstellDatum"
					+ "FROM Spielzeit" + " WHERE szName = '" + name + "' ORDER BY szName");

			/**
			 * Für jeden Eintrag im Suchergebnis wird jetzt ein Spielzeit-Objekt erstellt
			 * und die ArrayListe Stück für Stück aufgebaut/gefuellt.
			 */

			while (resultset.next()) {
				Spielzeit sz = new Spielzeit();
				sz.setId(resultset.getInt("szId"));
				sz.setName(resultset.getString("szName"));
				sz.setBesitzerId(resultset.getInt("spielzeit_anwender_Id"));
				Date date = new Date(resultset.getTimestamp("zeit").getTime());
				sz.setZeit(date);
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
	 * Bei der Erstellung eines neuen Objektes soll zunächst geprüft werden, ob der
	 * gewünschte Name für das Objekt nicht bereits in der entsprechenden Tabelle
	 * der Datenbank vorhanden ist. Damit soll verhindert werden, dass mehrere
	 * Objekte den selben Namen tragen.
	 * 
	 * @param name
	 *            den das zu erstellende Objekt tragen soll
	 * @return false, wenn der Name bereits einem anderen, existierenden Objekt
	 *         zugeordnet ist. True, wenn der Name in der Datenbanktabelle noch
	 *         nicht vergeben ist.
	 */
	public boolean nameVerfügbar(Spielzeit spielzeit) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery("SELECT szName FROM Spielzeit" + " WHERE szName= '"
					+ spielzeit.getName() + "' AND NOT szId= '" + spielzeit.getId() + "'");

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
	 * @param spielzeit
	 *            bzw. das zu speichernde Objekt
	 * @return Das bereits übergeben Objekt, ggf. mit abgeänderter Id
	 */
	public Spielzeit insert(Spielzeit spielzeit) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			/**
			 * Im Folgenden: Überprüfung, welches die höchste Id der schon bestehenden
			 * Spielzeiten ist.
			 */
			ResultSet resultset = stmt.executeQuery("SELECT MAX(szId) AS maxId " + "FROM Spielzeit");
			if (resultset.next()) {
				// Wenn die höchste Id gefunden wurde, wird eine neue Id mit +1 höher erstellt
				spielzeit.setId(resultset.getInt("maxId") + 1);
				stmt = con.createStatement();

				// Jetzt wird die Id tatsächlich eingefügt:
				stmt.executeUpdate("INSERT INTO Spielzeit (szId, szName, Zeit, spielzeit_anwender_Id)" + " VALUES("
						+ spielzeit.getId() + ", '" + spielzeit.getName() + "', '" + spielzeit.dateToString() + "', "
						+ spielzeit.getBesitzerId() + ")");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		/**
		 * Rückgabe des Spielzeit-Objekts. Durch die Methode wurde das Objekt ggf.
		 * angepasst (z.B. angepasste Id)
		 */
		return spielzeit;
	}

	/**
	 * Das Objekt wird wiederholt, in geupdateter Form in die Datenbank eingetragen.
	 * 
	 * @param spielzeit
	 *            als das Objekt, das verändert werden soll.
	 * @return Das Objekt, welches im Parameter übergeben wurde.
	 */
	public Spielzeit update(Spielzeit spielzeit) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			/**
			 * Update wird in die Datenbank eingetragen.
			 */
			stmt.executeUpdate("UPDATE Spielzeit SET " + "szName= '" + spielzeit.getName() + "' , "
					+ "spielzeit_anwender_Id= '" + spielzeit.getBesitzerId() + "' , " + "zeit= '"
					+ spielzeit.dateToString() + "' WHERE szId=" + spielzeit.getId());
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
	 * @param spielzeit
	 *            Objekt, welches gelöscht werden soll.
	 */
	public void delete(Spielzeit spielzeit) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM Spielzeit " + "WHERE szId=" + spielzeit.getId());

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

			ResultSet resultset = stmt.executeQuery(
					"SELECT szId, szName, spielzeit_anwender_Id, zeit, erstellDatum FROM Spielzeit" + " ORDER BY zeit");

			while (resultset.next()) {
				Spielzeit sz = new Spielzeit();
				sz.setId(resultset.getInt("szId"));
				sz.setName(resultset.getString("szName"));
				sz.setBesitzerId(resultset.getInt("spielzeit_anwender_Id"));
				Date date = new Date(resultset.getTimestamp("zeit").getTime());
				sz.setZeit(date);
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
	 * @param anwender
	 *            Objekt, dessen Id mit der BesitzerId der gesuchten
	 *            Spielzeit-Objekte übereinstimmen soll.
	 * @return Alle Spielzeit-Objekte, die die Id des vorgegebenen Anwenders als
	 *         BesitzerId in der Datenbank eingetragen haben.
	 */
	public ArrayList<Spielzeit> findAllByAnwenderOwner(Anwender anwender) {
		Connection con = DBConnection.connection();

		ArrayList<Spielzeit> resultarray = new ArrayList<Spielzeit>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt
					.executeQuery("SELECT szId, szName, spielzeit_anwender_Id, zeit, erstellDatum FROM Spielzeit"
							+ " WHERE spielzeit_anwender_Id=" + anwender.getId() + " ORDER BY zeit");

			while (resultset.next()) {
				Spielzeit sz = new Spielzeit();
				sz.setId(resultset.getInt("szId"));
				sz.setName(resultset.getString("szName"));
				sz.setBesitzerId(resultset.getInt("spielzeit_anwender_Id"));
				Date date = new Date(resultset.getTimestamp("zeit").getTime());
				sz.setZeit(date);
				sz.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				// Hinzuf�gen des neuen Objekts zur ArrayList
				resultarray.add(sz);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// Rückgabe des Ergebnisses in Form einer ArrayList
		return resultarray;
	}

	/**
	 * Suche nach einer Spielzeit mit vorgegebener Spielzeit-Id
	 * 
	 * @param id
	 *            zugehörig zu einer Spielzeit, nach welcher gesucht werden soll,
	 *            also der Primärschlüssel in der Datenbank.
	 * @return Das Spielzeit-Objekt, das mit seiner Spielzeit-Id der übergebenen Id
	 *         entspricht. Falls keine Spielzeit zur übergebenen Id gefunden wurde,
	 *         wird null zurückgegeben.
	 */
	public Spielzeit findById(int id) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			ResultSet resultset = stmt
					.executeQuery("SELECT szId, szName, spielzeit_anwender_Id, zeit, erstellDatum FROM Spielzeit"
							+ " WHERE szId=" + id + " ORDER BY zeit");
			// Pr�fe ob das geklappt hat, also ob ein Ergebnis vorhanden ist:
			if (resultset.next()) {
				Spielzeit sz = new Spielzeit();
				sz.setId(resultset.getInt("szId"));
				sz.setName(resultset.getString("szName"));
				sz.setBesitzerId(resultset.getInt("spielzeit_anwender_Id"));
				Date date = new Date(resultset.getTimestamp("zeit").getTime());
				sz.setZeit(date);
				sz.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				return sz;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	public Spielzeit findByName(String name) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			ResultSet resultset = stmt
					.executeQuery("SELECT szId, szName, spielzeit_anwender_Id, zeit, erstellDatum FROM Spielzeit"
							+ " WHERE szName='" + name + "' ORDER BY zeit");
			// Pr�fe ob das geklappt hat, also ob ein Ergebnis vorhanden ist:
			if (resultset.next()) {
				Spielzeit sz = new Spielzeit();
				sz.setId(resultset.getInt("szId"));
				sz.setName(resultset.getString("szName"));
				sz.setBesitzerId(resultset.getInt("spielzeit_anwender_Id"));
				Date date = new Date(resultset.getTimestamp("zeit").getTime());
				sz.setZeit(date);
				sz.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				return sz;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

}
