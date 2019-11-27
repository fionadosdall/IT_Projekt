package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;

/**
 * Das hier ist eine Mapper-Klasse, die Gruppen-Objekte auf eine relationale DB
 * abbildet.
 * 
 * @author annaf
 *
 */
public class GruppeMapper {

	/**
	 * Hier folgt die Klassenvariable. Diese Variable ist wegen "static" nur einmal
	 * vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	 */

	private static GruppeMapper gruppeMapper;

	/**
	 * Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann.
	 * Denn von diesem Mapper wird nur 1x eine Instanz erzeugt
	 */
	protected GruppeMapper() {
	}

	/**
	 * Um eine Instanz dieses Mappers erstellen zu können, nutzt man NICHT den
	 * KONSTRUKTOR, sondern die folgende Methode. Sie ist statisch, dadurch stellt
	 * sie sicher, dass nur eine einzige Instanz dieser Klasse existiert. Außerdem
	 * wird zuerst überprüft, ob bereis ein gruppeMapper existiert, falls nein, wird
	 * ein neuer instanziiert. Existiert bereits ein gruppeMapper, wird dieser
	 * zurückgegeben.
	 */

	public static GruppeMapper gruppeMapper() {
		if (gruppeMapper == null) {
			gruppeMapper = new GruppeMapper();
		}
		return gruppeMapper;
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

			ResultSet resultset = stmt.executeQuery("SELECT name FROM gruppe" + "WHERE name =" + name);

			if (resultset.next()) {
				return false;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return true;
	}

	/**
	 * Die insert-Methode fügt ein neues Gruppen-Objekt zur Datenbank hinzu.
	 * 
	 * @param gruppe bzw. das zu speichernde Objekt
	 * @return Das bereits übergeben Objekt, ggf. mit abgeänderter Id
	 */
	public Gruppe insert(Gruppe gruppe) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			/**
			 * Im Folgenden: Überprüfung, welches die höchste Id der schon bestehenden
			 * Anwender ist.
			 */
			ResultSet resultset = stmt.executeQuery("SELECT MAX (id) AS maxId " + "FROM gruppen");
			if (resultset.next()) {
				// Wenn die höchste Id gefunden wurde, wird eine neue Id mit +1 höher erstellt
				gruppe.setId(resultset.getInt("maxId") + 1);
				stmt = con.createStatement();

				// Jetzt wird die Id tatsächlich eingefügt:
				stmt.executeUpdate("INSERT INTO gruppen (id, name, besitzerId, erstellDatum)" + "VALUES("
						+ gruppe.getId() + "','" + gruppe.getName() + "','" + gruppe.getBesitzerId() + "','"
						+ gruppe.getErstellDatum() + ")");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		/**
		 * Rückgabe der Gruppe. Durch die Methode wurde das Objekt ggf. angepasst (z.B.
		 * angepasste Id)
		 */
		return gruppe;
	}

	/**
	 * Das Objekt wird wiederholt, in geupdateter Form in die Datenbank eingetragen.
	 * 
	 * @param gruppe bzw. Objekt, welches verändert werden soll.
	 * @return Das Objekt, welches im Parameter übergeben wurde.
	 */
	public Gruppe update(Gruppe gruppe) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			/**
			 * Update wird in die Datenbank eingetragen.
			 */
			stmt.executeUpdate("UPDATE gruppe SET " + "besitzerId=\"" + gruppe.getBesitzerId() + "\", " + "name=\""
					+ gruppe.getName() + "\", " + "erstellDatum=\"" + gruppe.getErstellDatum() + "\" " + "WHERE id="
					+ gruppe.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		/**
		 * Rückgabe des neuen, veränderten Gruppen-Objektes
		 */
		return gruppe;
	}

	/**
	 * Mit dieser Methode kann ein Gruppen-Objekt aus der Datenbank gelöscht werden.
	 * 
	 * @param gruppe Objekt, welches gelöscht werden soll.
	 */
	public void delete(Gruppe gruppe) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM gruppe " + "WHERE id=" + gruppe.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Alle in der Datenbank vorhandenen Gruppen sollen gesucht und ausgegeben
	 * werden
	 * 
	 * @return Alle Gruppen-Objekte, die in der Datenbank eingetragen sind, werden
	 *         in einer ArrayList zurückgegeben.
	 */
	public ArrayList<Gruppe> findAllGruppen() {
		Connection con = DBConnection.connection();

		ArrayList<Gruppe> resultarray = new ArrayList<Gruppe>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt
					.executeQuery("SELECT id, name, besitzerId, erstellDatum FROM gruppe" + "ORDER BY name");

			while (resultset.next()) {
				Gruppe g = new Gruppe();
				g.setId(resultset.getInt("id"));
				g.setBesitzerId(resultset.getInt("besitzerId"));
				g.setName(resultset.getString("name"));
				g.setErstellDatum(resultset.getTimestamp("erstellDatum"));

				// Hinzufügen des neuen Objekts zur ArrayList
				resultarray.add(g);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// ArrayList mit Ergebnis zurückgeben
		return resultarray;
	}

	/**
	 * Suche nach einer Gruppe mit vorgegebener Gruppen-Id
	 * 
	 * @param id zugehörig zu einer Gruppe, nach welcher gesucht werden soll, also
	 *           der Primärschlüssel in der Datenbank.
	 * @return Das Gruppen-Objekt, das mit seiner Gruppen-Id der übergebenen Id
	 *         entspricht. Falls keine Gruppe zur übergebenen Id gefunden wurde,
	 *         wird null zurückgegeben.
	 */
	public Gruppe findById(int id) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, erstellDatum FROM gruppe"
					+ "WHERE id=" + id + " ORDER BY besitzerId");
			// Prüfe ob das geklappt hat, also ob ein Ergebnis vorhanden ist:
			if (resultset.next()) {
				Gruppe g = new Gruppe();
				g.setId(resultset.getInt("id"));
				g.setBesitzerId(resultset.getInt("besitzerId"));
				g.setName(resultset.getString("name"));
				g.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				return g;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * Anwender-Objekte können zu einer bestimmten Gruppe hinzugefügt werden.
	 * Dadurch wird in der Datenbank eine Mitgliedschaftsbeziehung angelegt. Die
	 * anwenderId und die gruppeId werden in eine Hilfstabelle namens
	 * gruppenmitglieder eingetragen.
	 * 
	 * @param anwender welcher zu einer Gruppe hinzugefügt werden soll.
	 * @param gruppe   zu welcher der vorgegebene Anwender hinzugefügt werden soll.
	 */
	public void addGruppenmitgliedschaft(Anwender anwender, Gruppe gruppe) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("INSERT INTO gruppenmitglieder (gruppenId, anwenderId)" + "VALUES(" + gruppe.getId()
					+ "','" + anwender.getId() + ")");
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

	}

	/**
	 * Die Mitgliedschaftsbeziehung zwischen einem Anwender-Objekt und einem
	 * Gruppen-Objekt kann in der Datenbank auch wieder gelöscht werden. Der
	 * dazugehörige Eintrag in der Hilfstabelle gruppenmitglieder wird gelöscht. Der
	 * passende Eintrag wird anhand der vorgegebenen anwenderId und gruppenId
	 * gefunden.
	 * 
	 * @param anwender Objekt wessen Mitgliedschaftsbeziehung zu einem vorgegebenen
	 *                 Gruppen-Objekt gelöscht werden soll.
	 * @param gruppe   aus der ein vorgegebener Anwender als Mitglied gelöscht
	 *                 werden soll.
	 */
	public void deleteGruppenmitgliedschaft(Anwender anwender, Gruppe gruppe) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM gruppenmitglieder " + "WHERE gruppenId=" + gruppe.getId()
					+ "AND anwenderId=" + anwender.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Suche nach allen Gruppen-Objekten, die eine Mitgliedschaftsbeziehung mit
	 * einem vorgegebenen Anwender haben. Dabei wird die Hilfstabelle
	 * gruppenmitglieder mit einem INNER JOIN erweitert.
	 * 
	 * @param anwender Objekt, dessen Id mit der Mitgliedschaft bei den Gruppen
	 *                 übereinstimmen soll
	 * @return Alle Gruppe-Objekte in einer ArrayList, die in der Hilfstabelle
	 *         aufgeführt sind.
	 */
	public ArrayList<Gruppe> findAllByAnwender(Anwender anwender) {
		Connection con = DBConnection.connection();

		ArrayList<Gruppe> resultarray = new ArrayList<Gruppe>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery(
					"SELECT gruppenmitglieder.gruppenId, gruppenmitglieder.anwenderId, gruppe.besitzerId, gruppe.name, "
							+ "gruppe.erstellDatum FROM gruppenmitglieder " + "INNER JOIN gruppe "
							+ "ON gruppenmitglieder.gruppeId = gruppe.id " + "WHERE anwenderId = " + anwender.getId()
							+ "ORDER BY gruppenId");

			/**
			 * FÜr jeden Eintrag im Suchergebnis wird jetzt ein Gruppe-Objekt erstellt und
			 * die ArrayListe Stück für Stück aufgebaut/gefuellt.
			 */

			while (resultset.next()) {
				Gruppe g = new Gruppe();
				g.setId(resultset.getInt("id"));
				g.setBesitzerId(resultset.getInt("besitzerId"));
				g.setName(resultset.getString("name"));
				g.setErstellDatum(resultset.getTimestamp("erstellDatum"));

				// Hinzufügen des neuen Objekts zur ArrayList
				resultarray.add(g);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();

		}
		// Rückgabe des Ergebnisses in Form einer ArrayList
		return resultarray;
	}

	/**
	 * Suche nach allen Gruppen-Objekten, die eine Eigentumsbeziehung mit einem
	 * vorgegebene Anwender haben. Daraus wird ersichtlich, welcher Anwender
	 * besondere Rechte in Bezug auf welche Filme hat. Besondere Rechte können zum
	 * Beispiel sein, dass der Anwender das jeweilige Objekt verändern darf.
	 * 
	 * @param anwender Objekt, dessen Id mit der BesitzerId der gesuchten
	 *                 Gruppen-Objekte übereinstimmen soll.
	 * @return Alle Gruppen-Objekte, die die Id des vorgegebenen Anwenders als
	 *         BesitzerId in der Datenbank eingetragen haben.
	 */
	public ArrayList<Gruppe> findAllByAnwenderOwner(Anwender anwenderOwner) {
		Connection con = DBConnection.connection();

		ArrayList<Gruppe> resultarray = new ArrayList<Gruppe>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, erstellDatum FROM gruppe"
					+ "WHERE besitzerId = " + anwenderOwner.getId() + "ORDER BY name");

			while (resultset.next()) {
				Gruppe g = new Gruppe();
				g.setId(resultset.getInt("id"));
				g.setBesitzerId(resultset.getInt("besitzerId"));
				g.setName(resultset.getString("name"));
				g.setErstellDatum(resultset.getTimestamp("erstellDatum"));

				// Hinzufügen des neuen Objekts zur ArrayList
				resultarray.add(g);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// Rückgabe des Ergebnisses in Form einer ArrayList
		return resultarray;
	}

	/**
	 * Eine Gruppe erhält eine BesitzerId. Diese BesitzerId wird einem bestimmten
	 * Anwender zugewiesen. Dadurch lässt sich eine Eigentumsbeziehung zwischen den
	 * beiden Objekten herstellen. Wenn ein Anwender Besitzer eines (hier:
	 * Gruppen-)Objektes ist, fallen ihm besondere Rechte zu. Er kann z.B. als
	 * einziger Veränderungen vornehmen.
	 * 
	 * @param anwender welcher als Besitzer der Gruppe in der Datenbank eingetragen
	 *                 werden soll.
	 * @param gruppe   Objekt, welches einem Anwender zugeordnet werden soll.
	 */
	public void addEigentumsstruktur(Anwender anwender, Gruppe gruppe) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate(
					"UPDATE gruppe SET " + "besitzerId=\"" + anwender.getId() + "\" " + "WHERE id=" + gruppe.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Eine Gruppe hat eine BesitzerId, diese wurde einem bestimmten Anwender
	 * zugewiesen und soll nun gelöscht werden. Die Eigentumsbeziehung wird demnach
	 * aufgehoben und in der DB gelöscht.
	 * 
	 * @param gruppe Objekt bei welchem die BesitzerId in der Datenbank
	 *               zurückgesetzt werden soll.
	 */
	public void deleteEigentumsstruktur(Gruppe gruppe) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE gruppe SET " + "besitzerId=\"" + "" + "\" " + "WHERE id=" + gruppe.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

}
