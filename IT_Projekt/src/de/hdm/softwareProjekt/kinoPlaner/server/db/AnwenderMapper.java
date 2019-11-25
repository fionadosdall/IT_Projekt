package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;

//Das hier ist eine Mapper-Klasse, die Anwender-Objekte auf eine relationale DB abbildet. 

public class AnwenderMapper {

	/**
	 * Hier folgt die Klassenvariable. Diese Variable ist wegen "static" nur einmal
	 * vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	 */
	private static AnwenderMapper anwenderMapper;

	/**
	 * Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann.
	 * Denn von diesem Mapper wird nur 1x eine Instanz erzeugt
	 */
	protected AnwenderMapper() {
	}

	/**
	 * Um eine Instanz dieses Mappers erstellen zu können, nutzt man NICHT den
	 * KONSTRUKTOR, sondern die folgende Methode. Sie ist statisch, dadurch stellt
	 * sie sicher, dass nur eine einzige Instanz dieser Klasse existiert.
	 * 
	 * @param anwender
	 */
	public static AnwenderMapper anwenderMapper() {
		if (anwenderMapper == null) {
			anwenderMapper = new AnwenderMapper();
		}
		return anwenderMapper;
	}

	/**
	 * Es folgt eine Reihe von Methoden, die wir im StarUML aufgeführt haben. Sie
	 * ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.
	 */

	/**
	 * Die insert-Methode fügt ein neues Anwender-Objekt zur Datenbank hinzu.
	 * @param anwender, das zu speichernde Objekt
	 * @return Das bereits übergeben Objekt, ggf. mit abgeänderter Id
	 */
	public Anwender insert(Anwender anwender) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			/**
			 * Im Folgenden: Überprüfung, welches die höchste Id der schon bestehenden Anwender ist.
			 */
			ResultSet resultset = stmt.executeQuery("SELECT MAX (id) AS maxId " + "FROM anwender");
			if (resultset.next()) {
				// Wenn die höchste Id gefunden wurde, wird eine neue Id mit +1 höher erstellt
				anwender.setId(resultset.getInt("maxId") + 1);
				stmt = con.createStatement();

				// Jetzt wird die Id tatsächlich eingefügt:
				stmt.executeUpdate("INSERT INTO anweder (id, gmail, name)" + "VALUES(" + anwender.getId() + "','"
						+ anwender.getGmail() + "','" + anwender.getName() + ")");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		/** 
		 * Rückgabe des Anwenders. Durch die Methode wurde das Objekt ggf. angepasst (z.B. angepasste Id)
		 */
		return anwender;
	}

	/**
	 * Das Objekt wird wiederholt, in geupdateter Form in die Datenbank eingetragen.
	 * @param anwender, Objekt das verändert werden soll.
	 * @return Das Objekt, welches im Paramter übergeben wurde.
	 */
	public Anwender update(Anwender anwender) {
		return anwender;
	}

	/**
	 * Mit dieser Methode kann ein Anwender-Objekt aus der Datenbank gelöscht werden.
	 * @param anwender, Objekt das gelöscht werden soll.
	 */
	public void delete(Anwender anwender) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM anwender " + "WHERE id=" + anwender.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * KOMISCH. KÖNNEN WIR DIE LÖSCHEN? 
	 * @param anwender
	 * @return Eine ArrayList, die alle gefundenen Anwendern enthält. Falls eine Exception geworfen wird, 
	 * kann es passieren, dass die ArrayList leer oder nur teilweise befüllt zurück gegeben wird. 
	 */
	public ArrayList<Anwender> findAllByAnwender(Anwender anwender) {
		Connection con = DBConnection.connection();

		ArrayList<Anwender> resultarray = new ArrayList<Anwender>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt
					.executeQuery("SELECT id, gmail, name FROM anwender" + "WHERE id = " + anwender + "ORDER BY id");

			/**
			 * FÜr jeden Eintrag im Suchergebnis wird jetzt ein Anwender-Objekt erstellt und
			 * die ArrayListe Stück für Stück aufgebaut/gefuellt.
			 */

			while (resultset.next()) {
				Anwender a = new Anwender();
				a.setId(resultset.getInt("id"));
				a.setGmail(resultset.getString("gmail"));
				a.setName(resultset.getString("name"));

				// Hinzufügen des neuen Objekts zur ArrayList
				resultarray.add(a);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();

		}
		return resultarray;
	}

	/**
	 * Suche nach einem Anwender mit vorgegebener Anwender-Id
	 * @param id zugehörig zu einem Anwender, nach welchem gesucht werden soll, also der Primärschlüssel 
	 * in der Datenbank.
	 * @return Das Anwender-Objekt, das der übergebenen Id entspricht. Falls kein Anwender zur übergebenen
	 * Id gefunden wurde, wird null zurückgegeben. 
	 */
	public Anwender findById(int id) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			ResultSet resultset = stmt
					.executeQuery("SELECT id, gmail, name FROM anwender" + "WHERE id=" + id + " ORDER BY name");
			// Prüfe ob das geklappt hat, also ob ein Ergebnis vorhanden ist:
			if (resultset.next()) {
				Anwender a = new Anwender();
				a.setId(resultset.getInt("id"));
				a.setGmail(resultset.getString("gmail"));
				a.setName(resultset.getString("name"));
				return a;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * Suche nach einem Anwender mit vorgegebenem Namen.
	 * @param name zu dem der dazugehörige Anwender gesucht werden soll.
	 * @return Das ANwender-Objekt, das dem übergebenen Namen entspricht. Falls kein Anwender zum übergebenen
	 * Namen gefunden wurde, wird null zurückgegeben. 
	 */
	public Anwender findByName(String name) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			ResultSet resultset = stmt
					.executeQuery("SELECT id, gmail, name FROM anwender" + "WHERE name=" + name + "ORDER BY name");
			if (resultset.next()) {
				Anwender a = new Anwender();
				a.setId(resultset.getInt("id"));
				a.setGmail(resultset.getString("gmail"));
				a.setName(resultset.getString("name"));
				return a;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * Suche nach allen Anwendern, die zu einer vorgegebenen Gruppe gehören. Man kann sich dadurch also die 
	 * Gruppenmitglieder einer bestimmten Gruppe ausgeben lassen.
	 * @param gruppe zu welcher man alle dazugehörigen Anwender ausgegeben haben möchte. 
	 * @return Eine ArrayList, die alle gefundenen Anwender der Gruppe enthält. Falls eine Exception geworfen
	 * wird, kann es passieren, dass die ArrayList leer oder nur teilweise befüllt zurück gegeben wird.
	 */
	public ArrayList<Anwender> findAllByGruppe(Gruppe gruppe) {
		Connection con = DBConnection.connection();

		ArrayList<Anwender> resultarray = new ArrayList<Anwender>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery(
					"SELECT id, gmail, name, gruppeId FROM anwender" + "WHERE gruppeId = " + gruppe + "ORDER BY id");

			/**
			 * FÜr jeden Eintrag im Suchergebnis wird jetzt ein Anwender-Objekt erstellt und
			 * die ArrayListe Stück für Stück aufgebaut/gefuellt.
			 */

			while (resultset.next()) {
				Anwender a = new Anwender();
				a.setId(resultset.getInt("id"));
				a.setGmail(resultset.getString("gmail"));
				a.setName(resultset.getString("name"));

				// Hinzufügen des neuen Objekts zur ArrayList
				resultarray.add(a);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();

		}
		return resultarray;
	}

}
