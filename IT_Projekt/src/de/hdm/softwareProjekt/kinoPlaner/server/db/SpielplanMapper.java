package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielplan;

/**
 * Das hier ist eine Mapper-Klasse, die Spielplan-Objekte auf eine relationale
 * DB abbildet.
 * 
 *
 */

public class SpielplanMapper {

	/**
	 * Hier folgt die Klassenvariable. Diese Variable ist wegen "static" nur einmal
	 * vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	 */

	private static SpielplanMapper spielplanMapper;

	/**
	 * Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann.
	 * Denn von diesem Mapper wird nur 1x eine Instanz erzeugt
	 */

	protected SpielplanMapper() {
	}

	/**
	 * Um eine Instanz dieses Mappers erstellen zu können, nutzt man NICHT den
	 * KONSTRUKTOR, sondern die folgende Methode. Sie ist statisch, dadurch stellt
	 * sie sicher, dass nur eine einzige Instanz dieser Klasse existiert. Außerdem
	 * wird zuerst überprüft, ob bereis ein spielplanMapper existiert, falls nein,
	 * wird ein neuer instanziiert. Existiert bereits ein spielplanMapper, wird
	 * dieser zurückgegebene.
	 */

	public static SpielplanMapper spielplanMapper() {
		if (spielplanMapper == null) {
			spielplanMapper = new SpielplanMapper();
		}
		return spielplanMapper;
	}

	/**
	 * Es folgt eine Reihe von Methoden, die wir im StarUML aufgeführt haben. Sie
	 * ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.
	 */

	/**
	 * Suche nach allen Spielplan-Objekten über vorgegebenen Namen.
	 * 
	 * @param name
	 *            den die gesuchten Spielpläne tragen
	 * @return Eine ArrayList, die alle gefundenen Spielpläne enthält. Falls eine
	 *         Exception geworfen wird, kann es passieren, dass die ArrayList leer
	 *         oder nur teilweise befüllt zurück gegeben wird.
	 */
	public ArrayList<Spielplan> findAllByName(String name) {
		Connection con = DBConnection.connection();

		ArrayList<Spielplan> resultarray = new ArrayList<Spielplan>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery(
					"SELECT spId, spName, spielplan_anwender_Id, spielplan_kino_Id, spielplan_kinokette_Id, erstellDatum, isKinokettenSpielplan, spielplan_kinokette_Id "
							+ "FROM Spielplan" + " WHERE spName = '" + name + "' ORDER BY spName");

			/**
			 * Für jeden Eintrag im Suchergebnis wird jetzt ein Spielplan-Objekt erstellt
			 * und die ArrayListe Stück für Stück aufgebaut/gefuellt.
			 */

			while (resultset.next()) {
				Spielplan sp = new Spielplan();
				sp.setId(resultset.getInt("spId"));
				sp.setName(resultset.getString("spName"));
				sp.setBesitzerId(resultset.getInt("spielplan_anwender_Id"));
				sp.setKinoId(resultset.getInt("spielplan_kino_Id"));
				sp.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				sp.setKinokettenSpielplan(resultset.getBoolean("isKinokettenSpielplan"));
				// Hinzufügen des neuen Objekts zur ArrayList
				resultarray.add(sp);
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
	public boolean nameVerfügbar(String name) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery("SELECT spName FROM Spielplan" + " WHERE spName = '" + name + "'");

			if (resultset.next()) {
				return false;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return true;
	}

	public ArrayList<Spielplan> findSpielplaeneByName(String name) {
		Connection con = DBConnection.connection();
		ArrayList<Spielplan> resultarray = new ArrayList<Spielplan>();
		try {
			Statement stmt = con.createStatement();
			ResultSet resultset = stmt.executeQuery(
					"SELECT spId, spName, spielplan_anwender_Id, spielplan_kino_Id, erstellDatum, isKinokettenSpielplan, spielplan_kinokette_Id FROM Spielplan"
							+ " WHERE spName= '" + name + "' ORDER BY spielplan_kino_Id");
			// Pr�fe ob das geklappt hat, also ob ein Ergebnis vorhanden ist:
			while (resultset.next()) {
				Spielplan sp = new Spielplan();
				sp.setId(resultset.getInt("spId"));
				sp.setName(resultset.getString("spName"));
				sp.setBesitzerId(resultset.getInt("spielplan_anwender_Id"));
				sp.setKinoId(resultset.getInt("spielplan_kino_Id"));
				sp.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				sp.setKinokettenSpielplan(resultset.getBoolean("isKinokettenSpielplan"));
				// Hinzufügen des neuen Objekts zur ArrayList
				resultarray.add(sp);

			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * Die insert-Methode fügt ein neues Spielplan-Objekt zur Datenbank hinzu.
	 * 
	 * @param spielplan
	 *            bzw. das zu speichernde Objekt
	 * @return Das bereits übergeben Objekt, ggf. mit abgeänderter Id
	 */
	public Spielplan insertKino(Spielplan spielplan) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			/**
			 * Im Folgenden: Überprüfung, welches die höchste Id der schon bestehenden
			 * Spielpläne ist.
			 */
			ResultSet resultset = stmt.executeQuery("SELECT MAX(spId) AS maxId " + "FROM Spielplan");
			if (resultset.next()) {
				// Wenn die h�chste Id gefunden wurde, wird eine neue Id mit +1 h�her erstellt
				spielplan.setId(resultset.getInt("maxId") + 1);
				stmt = con.createStatement();

				// Jetzt wird die Id tats�chlich eingef�gt:
				stmt.executeUpdate(
						"INSERT INTO Spielplan (spId, spName, spielplan_anwender_Id, spielplan_kino_Id, isKinokettenSpielplan)"
								+ " VALUES(" + spielplan.getId() + ", '" + spielplan.getName() + "', "
								+ spielplan.getBesitzerId() + ", " + spielplan.getKinoId() + ", "
								+ spielplan.iskinokettenSpielplanToTinyint() + ")");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		/**
		 * Rückgabe des Spielplan-Objekts. Durch die Methode wurde das Objekt ggf.
		 * angepasst (z.B. angepasste Id)
		 */
		return spielplan;
	}
	
	public Spielplan insertKinokette(Spielplan spielplan) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			/**
			 * Im Folgenden: Überprüfung, welches die höchste Id der schon bestehenden
			 * Spielpläne ist.
			 */
			ResultSet resultset = stmt.executeQuery("SELECT MAX(spId) AS maxId " + "FROM Spielplan");
			if (resultset.next()) {
				// Wenn die h�chste Id gefunden wurde, wird eine neue Id mit +1 h�her erstellt
				spielplan.setId(resultset.getInt("maxId") + 1);
				stmt = con.createStatement();

				// Jetzt wird die Id tats�chlich eingef�gt:
				stmt.executeUpdate(
						"INSERT INTO Spielplan (spId, spName, spielplan_anwender_Id, spielplan_kino_Id, isKinokettenSpielplan, spielplan_kinokette_Id)"
								+ " VALUES(" + spielplan.getId() + ", '" + spielplan.getName() + "', "
								+ spielplan.getBesitzerId() + ", " + spielplan.getKinoId() + ", "
								+ spielplan.iskinokettenSpielplanToTinyint() + ", "
								+ spielplan.getKinokettenId() +")");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		/**
		 * Rückgabe des Spielplan-Objekts. Durch die Methode wurde das Objekt ggf.
		 * angepasst (z.B. angepasste Id)
		 */
		return spielplan;
	}

	/**
	 * Das Objekt wird wiederholt, in geupdateter Form in die Datenbank eingetragen.
	 * 
	 * @param spielplan
	 *            als das Objekt, welches verändert werden soll.
	 * @return Das Objekt, welches im Parameter übergeben wurde.
	 */
	public Spielplan update(Spielplan spielplan) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			/**
			 * Update wird in die Datenbank eingetragen.
			 */
			stmt.executeUpdate("UPDATE Spielplan SET " + "spName= '" + spielplan.getName() + "' , "
					+ "spielplan_anwender_Id= '" + spielplan.getBesitzerId() + "' , " + "spielplan_kino_Id= '"
					+ spielplan.getKinoId() + "' , " + "spielplan_kinokette_Id= '" + spielplan.getKinokettenId()
					+ "' , " + "isKinokettenSpielplan= '" + spielplan.iskinokettenSpielplanToTinyint() + "' WHERE spId="
					+ spielplan.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		/**
		 * Rückgabe des neuen, veränderten Spielplan-Objektes
		 */
		return spielplan;
	}

	/**
	 * Mit dieser Methode kann ein Spielplan-Objekt aus der Datenbank gelöscht
	 * werden.
	 * 
	 * @param spielplan
	 *            Objekt, welches gelöscht werden soll.
	 */
	public void delete(Spielplan spielplan) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM Spielplan " + "WHERE spId=" + spielplan.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Suche nach einem Spielplan mit vorgegebener Kino-Id
	 * 
	 * @param id
	 *            zugehörig zu einem Spielplan, nach welchem gesucht werden soll,
	 *            also der Primärschlüssel in der Datenbank.
	 * @return Das Spielplan-Objekt, das mit seiner Spielplan-Id der übergebenen Id
	 *         entspricht. Falls kein Spielplan zur übergebenen Id gefunden wurde,
	 *         wird null zurückgegeben.
	 */
	public Spielplan findById(int id) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			ResultSet resultset = stmt.executeQuery(
					"SELECT spId, spName, spielplan_anwender_Id, spielplan_kino_Id, erstellDatum, isKinokettenSpielplan, spielplan_kinokette_Id FROM Spielplan"
							+ " WHERE spId=" + id + " ORDER BY spielplan_kino_Id");
			// Pr�fe ob das geklappt hat, also ob ein Ergebnis vorhanden ist:
			if (resultset.next()) {
				Spielplan sp = new Spielplan();
				sp.setId(resultset.getInt("spId"));
				sp.setName(resultset.getString("spName"));
				sp.setBesitzerId(resultset.getInt("spielplan_anwender_Id"));
				sp.setKinoId(resultset.getInt("spielplan_kino_Id"));
				sp.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				sp.setKinokettenSpielplan(resultset.getBoolean("isKinokettenSpielplan"));
				sp.setKinokettenId(resultset.getInt("spielplan_kinokette_Id"));

				return sp;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * Alle in der Datenbank vorhandenen Spielpläne sollen gesucht und ausgegeben
	 * werden
	 * 
	 * @return Alle Spielplan-Objekte, die in der Datenbank eingetragen sind, werden
	 *         in einer ArrayList zurückgegeben.
	 */
	public ArrayList<Spielplan> findAll() {
		Connection con = DBConnection.connection();

		ArrayList<Spielplan> resultarray = new ArrayList<Spielplan>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery(
					"SELECT spId, spName, spielplan_anwender_Id, spielplan_kino_Id, erstellDatum, isKinokettenSpielplan, spielplan_kinokette_Id "
							+ "FROM Spielplan" + " ORDER BY spielplan_kino_Id");
			/**
			 * Für jeden Eintrag im Suchergebnis wird jetzt ein Spielplan-Objekt erstellt
			 * und die ArrayListe Stück für Stück aufgebaut/gefuellt.
			 */
			while (resultset.next()) {
				Spielplan sp = new Spielplan();
				sp.setId(resultset.getInt("spId"));
				sp.setName(resultset.getString("spName"));
				sp.setBesitzerId(resultset.getInt("spielplan_anwender_Id"));
				sp.setKinoId(resultset.getInt("spielplan_kino_Id"));
				sp.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				sp.setKinokettenSpielplan(resultset.getBoolean("isKinokettenSpielplan"));
				sp.setKinokettenId(resultset.getInt("spielplan_kinokette_Id"));
				// Hinzufügen des neuen Objekts zur ArrayList
				resultarray.add(sp);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// Rückgabe des Ergebnisses in Form einer ArrayList
		return resultarray;
	}

	/**
	 * Suche nach allen Spielplan-Objekten, die eine Eigentumsbeziehung mit einem
	 * vorgegebenen Anwender haben. Daraus wird ersichtlich, welcher Anwender
	 * besondere Rechte in Bezug auf welche Spielpläne hat. Besondere Rechte können
	 * zum Beispiel sein, dass der Anwender das jeweilige Objekt verändern darf.
	 * 
	 * @param anwender
	 *            Objekt, dessen Id mit der BesitzerId der gesuchten
	 *            Spielplan-Objekte übereinstimmen soll.
	 * @return Alle Spielplan-Objekte, die die Id des vorgegebenen Anwenders als
	 *         BesitzerId in der Datenbank eingetragen haben.
	 */
	public ArrayList<Spielplan> findAllByAnwenderOwner(Anwender anwenderOwner) {
		Connection con = DBConnection.connection();

		ArrayList<Spielplan> resultarray = new ArrayList<Spielplan>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery(
					"SELECT spId, spName, spielplan_anwender_Id, spielplan_kino_Id, erstellDatum, isKinokettenSpielplan, spielplan_kinokette_Id "
							+ "FROM Spielplan" + " WHERE spielplan_anwender_Id = " + anwenderOwner.getId()
							+ " ORDER BY spielplan_kino_Id");

			while (resultset.next()) {
				Spielplan sp = new Spielplan();
				sp.setId(resultset.getInt("spId"));
				sp.setName(resultset.getString("spName"));
				sp.setBesitzerId(resultset.getInt("spielplan_anwender_Id"));
				sp.setKinoId(resultset.getInt("spielplan_kino_Id"));
				sp.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				sp.setKinokettenSpielplan(resultset.getBoolean("isKinokettenSpielplan"));
				sp.setKinokettenId(resultset.getInt("spielplan_kinokette_Id"));

				// Hinzufügen des neuen Objekts zur ArrayList
				resultarray.add(sp);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// ArrayList mit Ergebnis zurückgeben
		return resultarray;
	}

	/**
	 * Suche nach allen Spielplan-Objekten, die eine Beziehung mit einem
	 * vorgegebenen Kino-Objekt haben. Liegt eine Beziehung zwischen Spielplan und
	 * Kino vor, ist in der Datenbank in der Spielplan-Tabelle die entsprechende
	 * kinoId hinterlegt. Diese kinoId muss mit der Id des im Methodenparamter
	 * übergebenen Kinos übereinstimmen.
	 * 
	 * @param kino
	 *            Objekt, dessen Id mit den kinoId in der Spielplan-Tabelle
	 *            übereinstimmen soll.
	 * @return Alle Spielplan-Objekte in einer ArrayList, deren kinoId des
	 *         übergebenen Kinos entsprechen.
	 */
	public ArrayList<Spielplan> findAllByKino(Kino kino) {
		Connection con = DBConnection.connection();

		ArrayList<Spielplan> resultarray = new ArrayList<Spielplan>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery(
					"SELECT spId, spName, spielplan_anwender_Id, spielplan_kino_Id, erstellDatum, isKinokettenSpielplan, spielplan_kinokette_Id FROM Spielplan"
							+ " WHERE spielplan_kino_Id = " + kino.getId() + " ORDER BY spielplan_kino_Id");
			/**
			 * FÜr jeden Eintrag im Suchergebnis wird jetzt ein Spielplan-Objekt erstellt
			 * und die ArrayListe Stück für Stück aufgebaut/gefuellt.
			 */
			while (resultset.next()) {
				Spielplan sp = new Spielplan();
				sp.setId(resultset.getInt("spId"));
				sp.setName(resultset.getString("spName"));
				sp.setBesitzerId(resultset.getInt("spielplan_anwender_Id"));
				sp.setKinoId(resultset.getInt("spielplan_kino_Id"));
				sp.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				sp.setKinokettenSpielplan(resultset.getBoolean("isKinokettenSpielplan"));
				sp.setKinokettenId(resultset.getInt("spielplan_kinokette_Id"));

				// Hinzuf�gen des neuen Objekts zur ArrayList
				resultarray.add(sp);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// ArrayList mit Ergebnis zurückgeben
		return resultarray;
	}

	/**
	 * Suche nach allen Spielplan-Objekten, die eine Beziehung mit einer
	 * vorgegebenen Kinokette haben. Liegt eine Beziehung zwischen Spielplan und
	 * Kinokette vor, ist in der Datenbank in der Spielplan-Tabelle die
	 * entsprechende kinokettenId hinterlegt. Diese kinokettenId muss mit der Id der
	 * im Methodenparamter übergebenen Kinokette übereinstimmen.
	 * 
	 * @param kinokette
	 *            Objekt, dessen Id mit den kinokettenIds in der Kino-Tabelle
	 *            übereinstimmen soll.
	 * @return Alle Spielplan-Objekte in einer ArrayList, deren kinokettenId der
	 *         übergebenen Kinokette entspricht.
	 */
	public ArrayList<Spielplan> findAllByKinokette(Kinokette kinokette) {
		Connection con = DBConnection.connection();

		ArrayList<Spielplan> resultarray = new ArrayList<Spielplan>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery(
					"SELECT spId, spName, spielplan_anwender_Id, spielplan_kino_Id, erstellDatum, isKinokettenSpielplan, spielplan_kinokette_Id FROM Spielplan"
							+ " WHERE spielplan_kinokette_Id = " + kinokette.getId()
							+ " ORDER BY spielplan_kinokette_Id");
			/**
			 * FÜr jeden Eintrag im Suchergebnis wird jetzt ein Spielplan-Objekt erstellt
			 * und die ArrayListe Stück für Stück aufgebaut/gefuellt.
			 */
			while (resultset.next()) {
				Spielplan sp = new Spielplan();
				sp.setId(resultset.getInt("spId"));
				sp.setName(resultset.getString("spName"));
				sp.setBesitzerId(resultset.getInt("spielplan_anwender_Id"));
				sp.setKinoId(resultset.getInt("spielplan_kinokette_Id"));
				sp.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				sp.setKinokettenSpielplan(resultset.getBoolean("isKinokettenSpielplan"));
				sp.setKinokettenId(resultset.getInt("spielplan_kinokette_Id"));

				// Hinzufügen des neuen Objekts zur ArrayList
				resultarray.add(sp);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// ArrayList mit Ergebnis zurückgeben
		return resultarray;
	}

	/**
	 * Ein Spielplan erhält eine BesitzerId. Diese BesitzerId wird einem bestimmten
	 * Anwender zugewiesen. Dadurch lässt sich eine Eigentumsbeziehung zwischen den
	 * beiden Objekten herstellen. Wenn ein Anwender Besitzer eines (hier:
	 * Spielplan-)Objektes ist, fallen ihm besondere Rechte zu. Er kann z.B. als
	 * einziger Veränderungen vornehmen.
	 * 
	 * @param anwender
	 *            welcher als Besitzer des Spielplans in der Datenbank eingetragen
	 *            werden soll.
	 * @param spielplan
	 *            Objekt, welches einem Anwender zugeordnet werden soll.
	 */
	public void addEigentumsstruktur(Anwender anwender, Spielplan spielplan) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE Spielplan SET " + "spielplan_anwender_Id=" + anwender.getId() + ""
					+ " WHERE spId=" + spielplan.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Ein Spielplan hat eine BesitzerId, diese wurde einem bestimmten Anwender
	 * zugewiesen und soll nun gelöscht werden. Die Eigentumsbeziehung wird demnach
	 * aufgehoben und in der DB gelöscht.
	 * 
	 * @param spielplan
	 *            Objekt bei welchem die BesitzerId in der Datenbank zurückgesetzt
	 *            werden soll.
	 */
	public void deleteEigentumsstruktur(Spielplan spielplan) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate(
					"UPDATE Spielplan SET " + "spielplan_anwender_Id=" + "" + " WHERE spId=" + spielplan.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	public Object findByName(String name) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			ResultSet resultset = stmt.executeQuery(
					"SELECT spId, spName, spielplan_anwender_Id, spielplan_kino_Id, erstellDatum, isKinokettenSpielplan, spielplan_kinokette_Id FROM Spielplan"
							+ " WHERE spName='" + name + "' ORDER BY spielplan_kino_Id");
			// Pr�fe ob das geklappt hat, also ob ein Ergebnis vorhanden ist:
			if (resultset.next()) {
				Spielplan sp = new Spielplan();
				sp.setId(resultset.getInt("spId"));
				sp.setName(resultset.getString("spName"));
				sp.setBesitzerId(resultset.getInt("spielplan_anwender_Id"));
				sp.setKinoId(resultset.getInt("spielplan_kino_Id"));
				sp.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				sp.setKinokettenSpielplan(resultset.getBoolean("isKinokettenSpielplan"));
				sp.setKinokettenId(resultset.getInt("spielplan_kinokette_Id"));

				return sp;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

}
