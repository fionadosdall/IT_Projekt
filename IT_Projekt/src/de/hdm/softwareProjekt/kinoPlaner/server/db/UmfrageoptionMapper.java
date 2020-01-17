package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrageoption;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

/**
 * Das hier ist eine Mapper-Klasse, die Umfrageoption-Objekte auf eine
 * relationale DB abbildet.
 * 
 * @author annaf
 *
 */

public class UmfrageoptionMapper {

	/**
	 * Hier folgt die Klassenvariable. Diese Variable ist wegen "static" nur einmal
	 * vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	 */
	private static UmfrageoptionMapper umfrageoptionMapper;

	/**
	 * Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann.
	 * Denn von diesem Mapper wird nur 1x eine Instanz erzeugt
	 */
	protected UmfrageoptionMapper() {
	}

	/**
	 * Um eine Instanz dieses Mappers erstellen zu können, nutzt man NICHT den
	 * KONSTRUKTOR, sondern die folgende Methode. Sie ist statisch, dadurch stellt
	 * sie sicher, dass nur eine einzige Instanz dieser Klasse existiert. Außerdem
	 * wird zuerst überprüft, ob bereis ein umfrageoptionMapper existiert, falls
	 * nein, wird ein neuer instanziiert. Existiert bereits ein umfrageoptionMapper,
	 * wird dieser zurückgegeben.
	 */

	public static UmfrageoptionMapper umfrageoptionMapper() {
		if (umfrageoptionMapper == null) {
			umfrageoptionMapper = new UmfrageoptionMapper();
		}
		return umfrageoptionMapper;
	}

	/**
	 * Es folgt eine Reihe von Methoden, die wir im StarUML aufgeführt haben. Sie
	 * ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.
	 */

	/**
	 * Suche nach allen Umfrageoptionen über vorgegebenen Namen.
	 * 
	 * @param name den die gesuchten Umfrageoptionen tragen
	 * @return Eine ArrayList, die alle gefundenen Umfrageoptionen enthält. Falls
	 *         eine Exception geworfen wird, kann es passieren, dass die ArrayList
	 *         leer oder nur teilweise befüllt zurück gegeben wird.
	 */
	public ArrayList<Umfrageoption> findAllByName(String name) {
		Connection con = DBConnection.connection();

		ArrayList<Umfrageoption> resultarray = new ArrayList<Umfrageoption>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery(
					"SELECT uoId, uoName, umfrageoption_umfrage_Id, umfrageoption_vorstellung_Id, erstellDatum "
							+ "FROM Umfrageoption" + " WHERE uoName = '" + name + "' ORDER BY uoName");

			/**
			 * Für jeden Eintrag im Suchergebnis wird jetzt ein Umfrageoption-Objekt
			 * erstellt und die ArrayListe Stück für Stück aufgebaut/gefuellt.
			 */

			while (resultset.next()) {
				Umfrageoption uo = new Umfrageoption();
				uo.setId(resultset.getInt("uoId"));
				uo.setName(resultset.getString("uoName"));
				uo.setUmfrageId(resultset.getInt("umfrageoption_umfrage_Id"));
				uo.setVorstellungsId(resultset.getInt("umfrageoption_vorstellung_Id"));
				uo.setErstellDatum(resultset.getTimestamp("erstellDatum"));

				// Hinzufügen des neuen Objekts zur ArrayList
				resultarray.add(uo);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// ArrayList mit Ergebnis zurückgeben
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

			ResultSet resultset = stmt
					.executeQuery("SELECT uoName FROM Umfrageoption" + " WHERE uoName = '" + name + "'");

			if (resultset.next()) {
				return false;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return true;
	}

	/**
	 * Die insert-Methode fügt ein neues Umfrageoption-Objekt zur Datenbank hinzu.
	 * 
	 * @param umfrageoption als das zu speichernde Objekt
	 * @return Das bereits übergeben Objekt, ggf. mit abgeänderter Id
	 */
	public Umfrageoption insert(Umfrageoption umfrageoption) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			/**
			 * Im Folgenden: Überprüfung, welches die höchste Id der schon bestehenden
			 * Umfrageoptionen ist.
			 */
			ResultSet resultset = stmt.executeQuery("SELECT MAX(uoId) AS maxId " + "FROM Umfrageoption");
			if (resultset.next()) {
				// Wenn die höchste Id gefunden wurde, wird eine neue Id mit +1 höher erstellt
				umfrageoption.setId(resultset.getInt("maxId") + 1);
				stmt = con.createStatement();

				// Jetzt wird die Id tatsächlich eingefügt:
				stmt.executeUpdate(
						"INSERT INTO Umfrageoption (uoId, uoName, umfrageoption_umfrage_Id, umfrageoption_vorstellung_Id, voteErgebnis)"
								+ " VALUES(" + umfrageoption.getId() + ", '" + umfrageoption.getName()  
								+ "', " + umfrageoption.getUmfrageId() + ", " + umfrageoption.getVorstellungsId() + ","+umfrageoption.getVoteErgebnis()+")");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		/**
		 * Rückgabe der Umfrageoption. Durch die Methode wurde das Objekt ggf. angepasst
		 * (z.B. angepasste Id)
		 */
		return umfrageoption;
	}

	/**
	 * Das Objekt wird wiederholt, in geupdateter Form in die Datenbank eingetragen.
	 * 
	 * @param umfrageoption als das Objekt, das verändert werden soll.
	 * @return Das Objekt, welches im Parameter übergeben wurde.
	 */
	public Umfrageoption update(Umfrageoption umfrageoption) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			/**
			 * Update wird in die Datenbank eingetragen.
			 */
			stmt.executeUpdate("UPDATE Umfrageoption SET " + "uoName=\" '" + umfrageoption.getName() + "' "
					+ "erstellDatum=\"" + umfrageoption.getErstellDatum() + "\", " + "umfrageoption_umfrage_Id=\""
					+ umfrageoption.getUmfrageId() + "\", " + "umfrageoption_vorstellung_Id=\""
					+ umfrageoption.getVorstellungsId() + "\"" + " WHERE uoId=" + umfrageoption.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		/**
		 * Rückgabe des neuen, veränderten Umfrageoption-Objektes
		 */
		return umfrageoption;
	}

	/**
	 * Mit dieser Methode kann ein Umfrageoption-Objekt aus der Datenbank gelöscht
	 * werden.
	 * 
	 * @param umfrageoption Objekt, welches gelöscht werden soll.
	 */
	public void delete(Umfrageoption umfrageoption) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM Umfrageoption " + "WHERE uoId=" + umfrageoption.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Suche nach allen Umfrageoption-Objekten, die eine Beziehung mit einer
	 * vorgegebenen Umfrage haben. Liegt eine Beziehung zwischen Umfrageoption und
	 * Umfrage vor, ist in der Datenbank in der Umfrageoption-Tabelle die
	 * entsprechende umfrageId hinterlegt. Diese umfrageId muss mit der Id der im
	 * Methodenparamter übergebenen Umfrage übereinstimmen.
	 * 
	 * @param umfrage Objekt, deren Id mit den umfrageIds in der
	 *                Umfrageoption-Tabelle übereinstimmen soll.
	 * @return Alle Umfrageoption-Objekte in einer ArrayList, deren umfrageId der
	 *         übergebenen Umfrage entsprechen.
	 */
	public ArrayList<Umfrageoption> findAllByUmfrage(Umfrage umfrage) {
		Connection con = DBConnection.connection();

		ArrayList<Umfrageoption> resultarray = new ArrayList<Umfrageoption>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery(
					"SELECT uoId, uoName, umfrageoption_umfrage_Id, umfrageoption_vorstellung_Id, erstellDatum "
							+ "FROM Umfrageoption" + " WHERE umfrageoption_umfrage_Id=" + umfrage.getId()
							+ " ORDER BY uoName");
			/**
			 * Für jeden Eintrag im Suchergebnis wird jetzt ein Umfrageoption-Objekt
			 * erstellt und die ArrayListe Stück für Stück aufgebaut/gefuellt.
			 */
			while (resultset.next()) {
				Umfrageoption uo = new Umfrageoption();
				uo.setId(resultset.getInt("uoId"));
				uo.setName(resultset.getString("uoName"));
				uo.setUmfrageId(resultset.getInt("umfrageoption_umfrage_Id"));
				uo.setVorstellungsId(resultset.getInt("umfrageoption_vorstellung_Id"));
				uo.setErstellDatum(resultset.getTimestamp("erstellDatum"));

				// Hinzufügen des neuen Objekts zur ArrayList
				resultarray.add(uo);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// ArrayList mit Ergebnis zurückgeben
		return resultarray;
	}

	/**
	 * Suche nach einer Umfrageoption mit vorgegebener Umfrageoption-Id
	 * 
	 * @param id zugehörig zu einer Umfrageoption, nach welcher gesucht werden soll,
	 *           also der Primärschlüssel in der Datenbank.
	 * @return Das Umfrageoption-Objekt, das mit seiner Umfrageoption-Id der
	 *         übergebenen Id entspricht. Falls keine Umfrageoption zur übergebenen
	 *         Id gefunden wurde, wird null zurückgegeben.
	 */
	public Umfrageoption findById(int id) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			ResultSet resultset = stmt.executeQuery(
					"SELECT uoId, uoName, umfrageoption_umfrage_Id, umfrageoption_vorstellung_Id, erstellDatum FROM Umfrageoption"
							+ " WHERE uoId=" + id + " ORDER BY umfrageoption_vorstellung_Id");
			// Prüfe ob das geklappt hat, also ob ein Ergebnis vorhanden ist:
			if (resultset.next()) {
				Umfrageoption uopt = new Umfrageoption();
				uopt.setId(resultset.getInt("uoId"));
				uopt.setName(resultset.getString("uoName"));
				uopt.setUmfrageId(resultset.getInt("umfrageoption_umfrage_Id"));
				uopt.setVorstellungsId(resultset.getInt("umfrageoption_vorstellung_Id"));
				uopt.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				return uopt;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * Suche nach allen Umfrageoption-Objekten, die eine Beziehung mit einer
	 * vorgegebenen Vorstellung haben. Liegt eine Beziehung zwischen Umfrageoption
	 * und Vorstellung vor, ist in der Datenbank in der Umfrageoption-Tabelle die
	 * entsprechende vorstellungsId hinterlegt. Diese vorstellungsId muss mit der Id
	 * der im Methodenparamter übergebenen Vorstellung übereinstimmen.
	 * 
	 * @param vorstellung Objekt, deren Id mit den vorstellungsIds in der
	 *                    Umfrageoption-Tabelle übereinstimmen soll.
	 * @return Alle Umfrageoption-Objekte in einer ArrayList, deren vorstellungsId
	 *         der übergebenen Vorstellung entsprechen.
	 */
	public ArrayList<Umfrageoption> findAllByVorstellung(Vorstellung vorstellung) {
		Connection con = DBConnection.connection();

		ArrayList<Umfrageoption> resultarray = new ArrayList<Umfrageoption>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery(
					"SELECT uoId, uoName, umfrageoption_umfrage_Id, umfrageoption_vorstellung_Id, erstellDatum "
							+ "FROM Umfrageoption" + " WHERE umfrageoption_vorstellung_Id=" + vorstellung.getId()
							+ "ORDER BY uoName");

			while (resultset.next()) {
				Umfrageoption uo = new Umfrageoption();
				uo.setId(resultset.getInt("uoId"));
				uo.setName(resultset.getString("uoName"));
				uo.setUmfrageId(resultset.getInt("umfrageoption_umfrage_Id"));
				uo.setVorstellungsId(resultset.getInt("umfrageoption_vorstellung_Id"));
				uo.setErstellDatum(resultset.getTimestamp("erstellDatum"));

				// Hinzuf�gen des neuen Objekts zur ArrayList
				resultarray.add(uo);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// ArrayList mit Ergebnis zurückgeben.
		return resultarray;
	}
}
