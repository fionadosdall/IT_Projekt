package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Auswahl;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrageoption;

/**
 * Das hier ist eine Mapper-Klasse, die Auswahl-Objekte auf eine relationale DB
 * abbildet.
 * 
 * @author annaf
 *
 */

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
	protected AuswahlMapper() {
	}

	/**
	 * Um eine Instanz dieses Mappers erstellen zu können, nutzt man NICHT den
	 * KONSTRUKTOR, sondern die folgende Methode. Sie ist statisch, dadurch stellt
	 * sie sicher, dass nur eine einzige Instanz dieser Klasse existiert. Außerdem
	 * wird zuerst überprüft, ob bereits ein votingMapper existiert, falls nein,
	 * wird ein neuer instanziiert. Existiert bereits ein votingMapper, wird dieser
	 * zurück gegeben.
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
	 * Suche nach allen Auswahl-Objekten über vorgegebenen Namen.
	 * 
	 * @param name den die gesuchten Auswahl-Objekte tragen
	 * @return Eine ArrayList, die alle gefundenen Auswahl-Objekte enthält. Falls eine
	 *         Exception geworfen wird, kann es passieren, dass die ArrayList leer
	 *         oder nur teilweise befüllt zurück gegeben wird.
	 */
	public ArrayList<Auswahl> findAllByName(String name) {
		Connection con = DBConnection.connection();

		ArrayList<Auswahl> resultarray = new ArrayList<Auswahl>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt
					.executeQuery("SELECT id, name, besitzerId, umfrageoptionId, voting, erstellDatum " 
					+ "FROM auswahl" + "WHERE name = " + name + "ORDER BY name");

			/**
			 * Für jeden Eintrag im Suchergebnis wird jetzt ein Auswahl-Objekt erstellt und
			 * die ArrayListe Stück für Stück aufgebaut/gefuellt.
			 */

			while (resultset.next()) {
				Auswahl a = new Auswahl();
				a.setId(resultset.getInt("id"));
				a.setName(resultset.getString("name"));
				a.setBesitzerId(resultset.getInt("besitzerId"));
				a.setUmfrageoptionId(resultset.getInt("umfrageoptionId"));
				a.setVoting(resultset.getInt("voting"));
				a.setErstellDatum(resultset.getTimestamp("erstellDatum"));

				// Hinzuf�gen des neuen Objektes zur ArrayList
				resultarray.add(a);

			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// Ergebnis zurückgeben in Form der ArrayList
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

			ResultSet resultset = stmt.executeQuery("SELECT name FROM auswahl" + "WHERE name =" + name);

			if (resultset.next()) {
				return false;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return true;
	}

	/**
	 * Die insert-Methode fügt ein neues Auswahl-Objekt zur Datenbank hinzu.
	 * 
	 * @param auswahl bzw. das zu speichernde Objekt
	 * @return Das übergeben Objekt, ggf. mit abgeänderter Id
	 */
	public Auswahl insert(Auswahl auswahl) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			/**
			 * Im Folgenden: Überprüfung, welches die höchste Id der schon bestehenden
			 * Auswahl-Objekte ist.
			 */
			ResultSet resultset = stmt.executeQuery("SELECT MAX (id) AS maxId " + "FROM auswahl");
			if (resultset.next()) {
				// Wenn die höchste Id gefunden wurde, wird eine neue Id mit +1 höher erstellt
				auswahl.setId(resultset.getInt("maxId") + 1);
				stmt = con.createStatement();

				// Jetzt wird die Id tatsächlich eingefügt:
				stmt.executeUpdate("INSERT INTO auswahl (id, name, besitzerId, umfrageoptionId, voting, erstellDatum)"
						+ "VALUES(" + auswahl.getId() + "','" + auswahl.getName() + "','" + auswahl.getBesitzerId()
						+ "','" + auswahl.getUmfrageoptionId() + "','" + auswahl.getVoting() + "','"
						+ auswahl.getErstellDatum() + ")");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		/**
		 * Rückgabe der Auswahl. Durch die Methode wurde das Objekt ggf. angepasst (z.B.
		 * angepasste Id)
		 */
		return auswahl;
	}

	/**
	 * Das Objekt wieder wiederholt, in upgedateter Form in die Datenbank
	 * eingetragen.
	 * 
	 * @param auswahl bzw. das Objekt das verändert werden soll.
	 * @return Das Objekt, welches im Parameter übergeben wurde.
	 */
	public Auswahl update(Auswahl auswahl) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			/**
			 * Update wird in die Datenbank eingetragen
			 */
			stmt.executeUpdate("UPDATE auswahl SET " + "besitzerId=\"" + auswahl.getBesitzerId() + "\", "
					+ "umfrageoptionId=\"" + auswahl.getUmfrageoptionId() + "\", " + "voting=\"" + auswahl.getVoting()
					+ "\", " + "name=\"" + auswahl.getName() + "\", " + "erstellDatum=\"" + auswahl.getErstellDatum()
					+ "\" " + "WHERE id=" + auswahl.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		/**
		 * Rückgabe des neuen, veränderten Auswahl-Objektes
		 */
		return auswahl;
	}

	/**
	 * Mit dieser Methode kann ein Auswahl-Objekt aus der Datenbank gelöscht werden.
	 * 
	 * @param auswahl Objekt, welches gelöscht werden soll
	 */
	public void delete(Auswahl auswahl) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM auswahl " + "WHERE id=" + auswahl.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Suche nach einer bestimmten Auswahl mithilfe einer vorgegebenen Auswahl-Id
	 * 
	 * @param id der Auswahl, nach welcher gesucht werden soll
	 * @return Das Auswahl-Objekt, das mit seiner Auswahl-Id der �bergebenen Id
	 *         entspricht. Falls kein Auswahl-Objekt zur �bergebenen Id gefunden
	 *         wird, wird null zur�ckgegeben.
	 */
	public Auswahl findById(int id) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			ResultSet resultset = stmt
					.executeQuery("SELECT id, name, besitzerId, umfrageoptionId, voting, erstellDatum FROM auswahl"
							+ "WHERE id=" + id + " ORDER BY umfrageoptionId");
			// Pr�fe ob das geklappt hat, also ob ein Ergebnis vorhanden ist:
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
	 * Suche nach allen getroffenen Auswahlen, die zu einer bestimmten, im Parameter
	 * vorgegebenen Umfrageoption geh�ren.
	 * 
	 * @param umfrageoption zu welcher man alle getroffenen Auswahlen ausgegebenen
	 *                      haben m�chte.
	 * @return Eine ArrayList, die alle gefundenen Auswahl-Objekte enth�lt. Falls eine
	 *         Exception geworfen wird, kann es passieren, dass die ArrayList leer
	 *         oder nur teilweise bef�llt zur�ckgegeben wird.
	 */
	public ArrayList<Auswahl> findAllByUmfrageoption(Umfrageoption umfrageoption) {
		Connection con = DBConnection.connection();

		ArrayList<Auswahl> resultarray = new ArrayList<Auswahl>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt
					.executeQuery("SELECT id, name, besitzerId, umfrageoptionId, voting, erstellDatum FROM auswahl"
							+ "WHERE umfrageoptionId=" + umfrageoption.getId() + "ORDER BY besitzerId");

			/**
			 * F�r jeden Eintrag im Sucheregbnis wird nun ein Auswahl-Objekt erstellt. Damit
			 * wird die ArrayListe nun Durchlauf für Durchlauf der Schleife
			 * aufgebaut/befüllt.
			 */
			while (resultset.next()) {
				Auswahl a = new Auswahl();
				a.setId(resultset.getInt("id"));
				a.setName(resultset.getString("name"));
				a.setBesitzerId(resultset.getInt("besitzerId"));
				a.setUmfrageoptionId(resultset.getInt("umfrageoptionId"));
				a.setVoting(resultset.getInt("voting"));
				a.setErstellDatum(resultset.getTimestamp("erstellDatum"));

				// Hinzuf�gen des neuen Objektes zur ArrayList
				resultarray.add(a);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		//Ergebnis zurückgeben in Form der ArrayList 
		return resultarray;
	}

	/**
	 * Eine Auswahl erhält eine BesitzerId. Diese BesitzerId wird einem bestimmten
	 * Anwender zugewiesen. Dadurch lässt sich eine Eigentumsbeziehung zwischen den
	 * beiden Objekten herstellen. Wenn ein Anwender Besitzer eines (hier:
	 * Auswahl-)Objektes ist, fallen ihm besondere Rechte zu. Er kann z.B. als
	 * einziger Ver�nderungen vornehmen.
	 * 
	 * @param anwender welcher als Besitzer der Auswahl in der Datenbank eingetragen
	 *                 werden soll.
	 * @param auswahl  Objekt, welches einem Anwender zugeordnet werden soll.
	 */
	public void addEigentumsstruktur(Anwender anwender, Auswahl auswahl) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate(
					"UPDATE auswahl SET " + "besitzerId=\"" + anwender.getId() + "\" " + "WHERE id=" + auswahl.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

	}

	/**
	 * Eine Auswahl hat eine BesitzerId, diese wurde einem bestimmten Anwender
	 * zugewiesen und soll nun gel�scht werden. Die Eigentumsbeziehung wird demnach
	 * aufgehoben und in der DB gel�scht.
	 * 
	 * @param auswahl Objekt bei welchem die BesitzerId in der Datenbank
	 *                zur�ckgesetzt werden soll.
	 */
	public void deleteEigentumsstruktur(Auswahl auswahl) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE auswahl SET " + "besitzerId=\"" + "" + "\" " + "WHERE id=" + auswahl.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Ein Auswahl-Objekt wird mithilfe einer vorgegebenen Umfrageoption, zu welcher
	 * die Auswahl gehört, gesucht. Außerdem wird der Anwender angegeben, welcher die
	 * Umfrageoption entsprechend der Auswahl beantwortet hat.
	 * 
	 * @param anwender      Objekt nach welchem die Suche nach der Auswahl gefiltert
	 *                      wird.
	 * @param umfrageoption zu der das gesuchte Auswahl-Objekt gehört.
	 * @return Das gesuchte Auswahl-Objekt. Befindet sich kein Auswahl-Objekt in der
	 *         Datenbank, welches sowohl zum Anwender, als auch zur Umfrageoption
	 *         zugehörig ist, wird null zurückgegeben.
	 */
	public Auswahl findByAnwenderAndUmfrageoption(Anwender anwender, Umfrageoption umfrageoption) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			ResultSet resultset = stmt
					.executeQuery("SELECT id, name, besitzerId, umfrageoptionId, voting, erstellDatum FROM auswahl"
							+ "WHERE besitzerId=" + anwender.getId() + "AND umfrageoptionId=" + umfrageoption.getId());
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
	 * Suche nach allen Auswahl-Objekten, die eine Eigentumsbeziehung mit einem
	 * vorgegebenen Anwender haben. Daraus wird ersichtlich, welcher Anwender
	 * besondere Rechte in Bezug auf welche Auswahlen hat. Besondere Rechte können
	 * zum Beispiel sein, dass der Anwender das jeweilige Objekt verändern darf.
	 * 
	 * @param anwender Objekt, dessen Id mit der BesitzerId der gesuchten
	 *                 Auswahl-Objekte übereinstimmen soll
	 * @return Alle Auswahl-Objekte, die die Id des vorgegebenen Anwenders als
	 *         BesitzerId in der DB eingetragen haben.
	 */
	public ArrayList<Auswahl> findAllByAnwenderOwner(Anwender anwender) {
		Connection con = DBConnection.connection();

		ArrayList<Auswahl> resultarray = new ArrayList<Auswahl>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt
					.executeQuery("SELECT id, name, besitzerId, umfrageoptionId, voting, erstellDatum FROM auswahl"
							+ "WHERE besitzerId=" + anwender.getId() + "ORDER BY id");
			while (resultset.next()) {
				Auswahl a = new Auswahl();
				a.setId(resultset.getInt("id"));
				a.setName(resultset.getString("name"));
				a.setBesitzerId(resultset.getInt("besitzerId"));
				a.setUmfrageoptionId(resultset.getInt("umfrageoptionId"));
				a.setVoting(resultset.getInt("voting"));
				a.setErstellDatum(resultset.getTimestamp("erstellDatum"));

				// Hinzuf�gen des neuen Objekts zur ArrayList
				resultarray.add(a);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		//Ergebnis zurückgeben in Form der ArrayList 
		return resultarray;
	}

}
