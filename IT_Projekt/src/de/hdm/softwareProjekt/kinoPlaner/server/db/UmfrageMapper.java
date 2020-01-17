package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;

/**
 * Das hier ist eine Mapper-Klasse, die Umfragen-Objekte auf eine relationale DB
 * abbildet.
 * 
 * @author annaf
 *
 */

public class UmfrageMapper {

	/**
	 * Hier folgt die Klassenvariable. Diese Variable ist wegen "static" nur einmal
	 * vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	 */
	private static UmfrageMapper umfrageMapper;

	/**
	 * Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann.
	 * Denn von diesem Mapper wird nur 1x eine Instanz erzeugt
	 */
	protected UmfrageMapper() {
	}

	/**
	 * Um eine Instanz dieses Mappers erstellen zu können, nutzt man NICHT den
	 * KONSTRUKTOR, sondern die folgende Methode. Sie ist statisch, dadurch stellt
	 * sie sicher, dass nur eine einzige Instanz dieser Klasse existiert. Außerdem
	 * wird zuerst überprüft, ob bereis ein umfrageMapper existiert, falls nein,
	 * wird ein neuer instanziiert. Existiert bereits ein umfrageMapper, wird dieser
	 * zurückgegeben.
	 */

	public static UmfrageMapper umfrageMapper() {
		if (umfrageMapper == null) {
			umfrageMapper = new UmfrageMapper();
		}
		return umfrageMapper;
	}

	/**
	 * Es folgt eine Reihe von Methoden, die wir im StarUML aufgeführt haben. Sie
	 * ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.
	 */

	/**
	 * Suche nach allen Umfragen über vorgegebenen Namen.
	 * 
	 * @param name den die gesuchten Umfragen tragen
	 * @return Eine ArrayList, die alle gefundenen Umfragen enthält. Falls eine
	 *         Exception geworfen wird, kann es passieren, dass die ArrayList leer
	 *         oder nur teilweise befüllt zurück gegeben wird.
	 */
	public ArrayList<Umfrage> findAllByName(String name) {
		Connection con = DBConnection.connection();

		ArrayList<Umfrage> resultarray = new ArrayList<Umfrage>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt
					.executeQuery("SELECT uId, uName, umfrage_anwender_Id, umfrage_gruppen_Id, erstellDatum, isGewählt, isOffen "
							+ "FROM Umfrage" + "WHERE uName = '" + name + "' ORDER BY uName");

			/**
			 * Für jeden Eintrag im Suchergebnis wird jetzt ein Umfrage-Objekt erstellt und
			 * die ArrayListe Stück für Stück aufgebaut/gefuellt.
			 */

			while (resultset.next()) {
				Umfrage u = new Umfrage();
				u.setId(resultset.getInt("uId"));
				u.setName(resultset.getString("uName"));
				u.setGruppenId(resultset.getInt("umfrage_gruppen_Id"));
				u.setBesitzerId(resultset.getInt("umfrage_anwender_Id"));
				u.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				u.setVoted(resultset.getBoolean("isGewählt"));
				u.setOpen(resultset.getBoolean("isOffen"));

				// Hinzuf�gen des neuen Objekts zur ArrayList
				resultarray.add(u);
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
	 * @param name den das zu erstellende Objekt tragen soll
	 * @return false, wenn der Name bereits einem anderen, existierenden Objekt
	 *         zugeordnet ist. True, wenn der Name in der Datenbanktabelle noch
	 *         nicht vergeben ist.
	 */
	public boolean nameVerfügbar(String name) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery("SELECT uName FROM Umfrage" + " WHERE uName= '" + name + "'");

			if (resultset.next()) {
				return false;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return true;
	}

	/**
	 * Die insert-Methode fügt ein neues Umfrage-Objekt zur Datenbank hinzu.
	 * 
	 * @param umfrage als das zu speichernde Objekt
	 * @return Das bereits übergeben Objekt, ggf. mit abgeänderter Id
	 */
	public Umfrage insert(Umfrage umfrage) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			/**
			 * Im Folgenden: Überprüfung, welches die höchste Id der schon bestehenden
			 * Umfragen ist.
			 */
			ResultSet resultset = stmt.executeQuery("SELECT MAX(uId) AS maxId" + " FROM Umfrage");
			if (resultset.next()) {
				// Wenn die h�chste Id gefunden wurde, wird eine neue Id mit +1 h�her erstellt
				umfrage.setId(resultset.getInt("maxId") + 1);
				
				stmt = con.createStatement();

				// Jetzt wird die Id tats�chlich eingef�gt:
				stmt.executeUpdate(
						"INSERT INTO umfrage (uId, uName, umfrage_gruppen_Id, umfrage_anwender_Id, isGewählt, isOffen)"
								+ " VALUES(" + umfrage.getId() + ", '" + umfrage.getName()  
								+ "', " + umfrage.getGruppenId() + ", " + umfrage.getBesitzerId() + ", " 
								+ umfrage.isVotedToTinyint()+ ", " + umfrage.isOpenToTinyint() + ")");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		/**
		 * Rückgabe der Umfrage. Durch die Methode wurde das Objekt ggf. angepasst (z.B.
		 * angepasste Id)
		 */
		return umfrage;
	}

	/**
	 * Das Objekt wird wiederholt, in geupdateter Form in die Datenbank eingetragen.
	 * 
	 * @param umfrage als das Objekt, das verändert werden soll.
	 * @return Das Objekt, welches im Parameter übergeben wurde.
	 */
	public Umfrage update(Umfrage umfrage) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			/**
			 * Update wird in die Datenbank eingetragen.
			 */
			stmt.executeUpdate("UPDATE Umfrage SET " + "umfrage_anwender_Id='" + umfrage.getBesitzerId() + "', "
					+ "uName= '" + umfrage.getName() + "' , " + "erstellDatum='" + umfrage.getErstellDatum()
					+ "', " + "umfrage_gruppen_Id='" + umfrage.getGruppenId() + "', " +"isGewählt='"+umfrage.isVotedToTinyint()
					+"', " + "isOffen='" + umfrage.isOpenToTinyint() + "' WHERE uId="
					+ umfrage.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		/**
		 * Rückgabe des neuen, veränderten Umfrage-Objektes
		 */
		return umfrage;
	}

	/**
	 * Mit dieser Methode kann ein Umfrage-Objekt aus der Datenbank gelöscht werden.
	 * 
	 * @param umfrage Objekt, welches gelöscht werden soll.
	 */
	public void delete(Umfrage umfrage) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM Umfrage " + " WHERE uId=" + umfrage.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Suche nach einer Umfrage mit vorgegebener Umfrage-Id
	 * 
	 * @param id zugehörig zu einer Umfrage, nach welcher gesucht werden soll, also
	 *           der Primärschlüssel in der Datenbank.
	 * @return Das Umfrage-Objekt, das mit seiner Umfrage-Id der übergebenen Id
	 *         entspricht. Falls keine Umfrage zur übergebenen Id gefunden wurde,
	 *         wird null zurückgegeben.
	 */
	public Umfrage findById(int id) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			ResultSet resultset = stmt.executeQuery(
					"SELECT uId, uName, umfrage_anwender_Id, umfrage_gruppen_Id, erstellDatum, isGewählt, isOffen FROM Umfrage"
							+ " WHERE uId=" + id + " ORDER BY umfrage_gruppen_Id");
			// Pr�fe ob das geklappt hat, also ob ein Ergebnis vorhanden ist:
			if (resultset.next()) {
				Umfrage u = new Umfrage();
				u.setId(resultset.getInt("uId"));
				u.setName(resultset.getString("uName"));
				u.setGruppenId(resultset.getInt("umfrage_gruppen_Id"));
				u.setBesitzerId(resultset.getInt("umfrage_anwender_Id"));
				u.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				u.setVoted(resultset.getBoolean("isGewählt"));
				u.setOpen(resultset.getBoolean("isOffen"));
				return u;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * Suche nach allen Umfrage-Objekten, an denen ein vorgegebener Anwender
	 * beteiligt ist. Nur wenn ein Anwender an einer Umfrage beteiligt ist, ist es
	 * ihm erlaubt an der Umfrage teilzunehmen.
	 * 
	 * @param anwender Objekt, für das herausgefunden werden soll, an welchen
	 *                 Umfragen es teilnimmt.
	 * @return Alle Umfrage-Objekte in Form einer ArrayList, an denen der
	 *         vorgegebene Anwender teilnehmen darf.
	 */
	public ArrayList<Umfrage> findAllByAnwender(Anwender anwender) {
		Connection con = DBConnection.connection();

		ArrayList<Umfrage> resultarray = new ArrayList<Umfrage>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery(
					"SELECT uId, uName, umfrage_anwender_Id, umfrage_gruppen_Id, erstellDatum, isGewählt, isOffen "
							+ "FROM umfrage" + " WHERE umfrage_anwender_Id = " + anwender.getId() + " ORDER BY uName");

			/**
			 * Für jeden Eintrag im Suchergebnis wird jetzt ein Umfrage-Objekt erstellt und
			 * die ArrayListe Stück für Stück aufgebaut/gefüllt.
			 */

			while (resultset.next()) {
				Umfrage u = new Umfrage();
				u.setId(resultset.getInt("uId"));
				u.setName(resultset.getString("uName"));
				u.setGruppenId(resultset.getInt("umfrage_gruppen_Id"));
				u.setBesitzerId(resultset.getInt("umfrage_anwender_Id"));
				u.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				u.setVoted(resultset.getBoolean("isGewählt"));
				u.setOpen(resultset.getBoolean("isOffen"));

				// Hinzuf�gen des neuen Objekts zur ArrayList
				resultarray.add(u);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();

		}
		// Rückgabe des Ergebnisses in Form einer ArrayList
		return resultarray;
	}

	/**
	 * Suche nach allen Umfrage-Objekten, die eine Eigentumsbeziehung mit einem
	 * vorgegebene Anwender haben. Daraus wird ersichtlich, welcher Anwender
	 * besondere Rechte in Bezug auf welche Umfragen hat. Besondere Rechte können
	 * zum Beispiel sein, dass der Anwender das jeweilige Objekt verändern darf.
	 * 
	 * @param anwender Objekt, dessen Id mit der BesitzerId der gesuchten
	 *                 Umfrage-Objekte übereinstimmen soll.
	 * @return Alle Umfrage-Objekte, die die Id des vorgegebenen Anwenders als
	 *         BesitzerId in der Datenbank eingetragen haben.
	 */
	public ArrayList<Umfrage> findAllByAnwenderOwner(Anwender anwenderOwner) {
		Connection con = DBConnection.connection();

		ArrayList<Umfrage> resultarray = new ArrayList<Umfrage>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery(
					"SELECT uId, uName, umfrage_anwender_Id, umfrage_gruppen_Id, erstellDatum, isGewählt, isOffen FROM umfrage"
							+ " WHERE umfrage_anwender_Id = " + anwenderOwner.getId() + " ORDER BY uName");

			while (resultset.next()) {
				Umfrage u = new Umfrage();
				u.setId(resultset.getInt("uId"));
				u.setName(resultset.getString("uName"));
				u.setGruppenId(resultset.getInt("umfrage_gruppen_Id"));
				u.setBesitzerId(resultset.getInt("umfrage_anwender_Id"));
				u.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				u.setVoted(resultset.getBoolean("isGewählt"));
				u.setOpen(resultset.getBoolean("isOffen"));
				// Hinzuf�gen des neuen Objekts zur ArrayList
				resultarray.add(u);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// Rückgabe des Ergebnisses in Form einer ArrayList
		return resultarray;
	}

	/**
	 * Umfragen können von einem Anwender geschlossen werden. Damit wird das Ende
	 * der Umfrage signalisiert, es kann nicht weiter an ihr teilgenommen werden. In
	 * dieser Methode kann man sich alle geschlossenen Umfragen ausgeben lassen, die
	 * von einem vorgegebenen Anwender geschlossen worden sind.
	 * 
	 * @param anwender dessen geschlossene Umfragen zurückgegeben werden sollen.
	 * @return Eine ArrayList mit allen Umfragen, die von dem vorgegebenen Anwender
	 *         geschlossen worden sind.
	 */
	public ArrayList<Umfrage> findAllClosedByAnwender(Anwender anwender) {
		Connection con = DBConnection.connection();

		ArrayList<Umfrage> resultarray = new ArrayList<Umfrage>();
		// Wie kann ich den Boolean isOpen testen?
		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery(
					"SELECT uId, uName, umfrage_anwender_Id, umfrage_gruppen_Id, umfrage.erstellDatum, , isGewählt, isOffen , gruppenmitglieder.gruppID, gruppenmitglieder.anwendID"
							+ " FROM umfrage "
							+ "INNER JOIN gruppenmitglieder ON gruppenmitglieder.gruppID = umfrage.umfrage_gruppen_Id "
							+ " WHERE isOffen = 'false' AND gruppenmitglieder.anwendId = " + anwender.getId()
							+ " ORDER BY uName");

			while (resultset.next()) {
				Umfrage u = new Umfrage();
				u.setId(resultset.getInt("uId"));
				u.setName(resultset.getString("uName"));
				u.setGruppenId(resultset.getInt("umfrage_gruppen_Id"));
				u.setBesitzerId(resultset.getInt("umfrage_anwender_Id"));
				u.setErstellDatum(resultset.getTimestamp("umfrage.erstellDatum"));
				u.setVoted(resultset.getBoolean("isGewählt"));
				u.setOpen(resultset.getBoolean("isOffen"));

				// Hinzuf�gen des neuen Objekts zur ArrayList
				resultarray.add(u);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// Rückgabe des Ergebnisses in Form einer ArrayList
		return resultarray;
	}

	/**
	 * Suche nach allen Umfrage-Objekten, die eine Zugehörigkeit zu einer
	 * vorgegebenen Gruppe haben. Liegt eine Zugehörigkeit zwischen Umfrage und
	 * Gruppe vor, ist in der Datenbank in der Umfrage-Tabelle die entsprechende
	 * gruppenId hinterlegt. Diese gruppenId muss mit der Id der im Methodenparamter
	 * übergebenen Gruppe übereinstimmen.
	 * 
	 * @param gruppe Objekt, dessen Id mit den gruppenIds in der Umfrage-Tabelle
	 *               übereinstimmen soll.
	 * @return Alle Umfrage-Objekte in einer ArrayList, deren gruppenId der
	 *         übergebenen Kinokette entspricht.
	 */
	public ArrayList<Umfrage> findAllByGruppe(Gruppe gruppe) {
		Connection con = DBConnection.connection();

		ArrayList<Umfrage> resultarray = new ArrayList<Umfrage>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery(
					"SELECT uId, uName, umfrage_anwender_Id, umfrage_gruppen_Id, erstellDatum, isGewählt, isOffen "
							+ "FROM umfrage " + "WHERE umfrage_gruppen_Id = " + gruppe.getId() + " ORDER BY uName");

			/**
			 * Für jeden Eintrag im Suchergebnis wird jetzt ein Umfrage-Objekt erstellt und
			 * die ArrayListe Stück für Stück aufgebaut/gefuellt.
			 */
			while (resultset.next()) {
				Umfrage u = new Umfrage();
				u.setId(resultset.getInt("uId"));
				u.setName(resultset.getString("uName"));
				u.setGruppenId(resultset.getInt("umfrage_gruppen_Id"));
				u.setBesitzerId(resultset.getInt("umfrage_anwender_Id"));
				u.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				u.setVoted(resultset.getBoolean("isGewählt"));
				u.setOpen(resultset.getBoolean("isOffen"));

				// Hinzuf�gen des neuen Objekts zur ArrayList
				resultarray.add(u);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// ArrayList mit Ergebnis zurückgeben
		return resultarray;
	}

	/**
	 * Eine Umfrage erhält eine BesitzerId. Diese BesitzerId wird einem bestimmten
	 * Anwender zugewiesen. Dadurch lässt sich eine Eigentumsbeziehung zwischen den
	 * beiden Objekten herstellen. Wenn ein Anwender Besitzer eines (hier:
	 * Umfrage-)Objektes ist, fallen ihm besondere Rechte zu. Er kann z.B. als
	 * einziger Veränderungen vornehmen.
	 * 
	 * @param anwender welcher als Besitzer der Umfrage in der Datenbank eingetragen
	 *                 werden soll.
	 * @param umfrage  Objekt, welches einem Anwender zugeordnet werden soll.
	 */
	public void addEigentumsstruktur(Anwender anwender, Umfrage umfrage) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE Umfrage SET " + "umfrage_anwender_Id=\"" + anwender.getId() + "\""
					+ " WHERE uId=" + umfrage.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Eine Umfrage hat eine BesitzerId, diese wurde einem bestimmten Anwender
	 * zugewiesen und soll nun gelöscht werden. Die Eigentumsbeziehung wird demnach
	 * aufgehoben und in der DB gelöscht.
	 * 
	 * @param umfrage Objekt bei welchem die BesitzerId in der Datenbank
	 *                zurückgesetzt werden soll.
	 */
	public void deleteEigentumsstruktur(Umfrage umfrage) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate(
					"UPDATE Umfrage SET " + "umfrage_anwender_Id=\"" + "\" " + " WHERE uId=" + umfrage.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

}
