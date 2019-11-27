package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;

/**
 * Das hier ist eine Mapper-Klasse, die Kinoketten-Objekte auf eine relationale
 * DB abbildet.
 * 
 * @author annaf
 *
 */

public class KinoketteMapper {

	/**
	 * Hier folgt die Klassenvariable. Diese Variable ist wegen "static" nur einmal
	 * vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	 */
	private static KinoketteMapper kinoketteMapper;

	/**
	 * Gesch�tzter Konstruktor, damit man nicht einfach neue Instanzen bilden kann.
	 * Denn von diesem Mapper wird nur 1x eine Instanz erzeugt
	 */
	protected KinoketteMapper() {
	}

	/**
	 * Um eine Instanz dieses Mappers erstellen zu k�nnen, nutzt man NICHT den
	 * KONSTRUKTOR, sondern die folgende Methode. Sie ist statisch, dadurch stellt
	 * sie sicher, dass nur eine einzige Instanz dieser Klasse existiert. Au�erdem
	 * wird zuerst �berpr�ft, ob bereits ein kinoketteMapper existiert. Falls nein,
	 * wird ein neuer instanziiert. Existiert bereits ein kinoketteMapper, wird
	 * dieser zur�ck gegeben.
	 */

	public static KinoketteMapper kinoketteMapper() {
		if (kinoketteMapper == null) {
			kinoketteMapper = new KinoketteMapper();
		}
		return kinoketteMapper;
	}

	/**
	 * Es folgt eine Reihe von Methoden, die wir im StarUML aufgef�hrt haben. Sie
	 * erm�glichen, dass man Objekte z.B. suchen, l�schen und updaten kann.
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

			ResultSet resultset = stmt.executeQuery("SELECT name FROM kinokette" + "WHERE name =" + name);

			if (resultset.next()) {
				return false;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return true;
	}

	/**
	 * Die insert-Methode f�gt ein neues Kinoketten-Objekt zur Datenbank hinzu.
	 * 
	 * @param kinokette als das zu speichernde Objekt
	 * @return Das �bergeben Objekt, ggf. mit abge�nderter Id
	 */
	public Kinokette insert(Kinokette kinokette) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			/**
			 * Im Folgenden: �berpr�fung, welches die h�chste Id der schon bestehenden
			 * Anwender ist.
			 */
			ResultSet resultset = stmt.executeQuery("SELECT MAX (id) AS maxId " + "FROM kinokette");
			if (resultset.next()) {
				// Wenn die h�chste Id gefunden wurde, wird eine neue Id mit +1 h�her erstellt
				kinokette.setId(resultset.getInt("maxId") + 1);
				stmt = con.createStatement();

				// Jetzt wird die Id tats�chlich eingef�gt:
				stmt.executeUpdate("INSERT INTO kinokette (id, name, besitzerId)" + "VALUES(" + kinokette.getId()
						+ "','" + kinokette.getName() + "','" + kinokette.getBesitzerId() + ")");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		/**
		 * R�ckgabe der Kinokette. Durch die Methode wurde das Objekt ggf. angepasst
		 * (z.B. angepasste Id)
		 */
		return kinokette;
	}

	/**
	 * Das Objekt wieder wiederholt, in upgedateter Form in die Datenbank
	 * eingetragen.
	 * 
	 * @param kinokette als das Objekt das ver�ndert werden soll.
	 * @return Das Objekt, welches im Parameter �bergeben wurde.
	 */
	public Kinokette update(Kinokette kinokette) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			/**
			 * Update wird in die Datenbank eingetragen
			 */
			stmt.executeUpdate("UPDATE kinokette SET " + "besitzerId=\"" + kinokette.getBesitzerId() + "\", "
					+ "name=\"" + kinokette.getName() + "\", " + "erstellDatum=\"" + kinokette.getErstellDatum()
					+ "\", " + "sitz=\"" + kinokette.getSitz() + "\", " + "website=\"" + kinokette.getWebsite() + "\" "
					+ "WHERE id=" + kinokette.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		/**
		 * R�ckgabe des neuen, veränderten Kinoketten-Objektes
		 */
		return kinokette;
	}

	/**
	 * Mit dieser Methode kann ein Kinoketten-Objekt aus der Datenbank gel�scht
	 * werden.
	 * 
	 * @param kinokette Objekt, welches gel�scht werden soll
	 */
	public void delete(Kinokette kinokette) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM kinokette " + "WHERE id=" + kinokette.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Alle in der Datenbank vorhandenen Kinoketten sollen gesucht und ausgegeben
	 * werden
	 * 
	 * @return Alle Kinoketten-Objekte, die in der Datenbank eingetragen sind,
	 *         werden in einer ArrayList zur�ckgegeben.
	 */
	public ArrayList<Kinokette> findAllKinoketten() {
		Connection con = DBConnection.connection();

		ArrayList<Kinokette> resultarray = new ArrayList<Kinokette>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery(
					"SELECT id, name, besitzerId, sitz, website, erstellDatum FROM kinokette" + "ORDER BY name");

			while (resultset.next()) {
				Kinokette kk = new Kinokette();
				kk.setId(resultset.getInt("id"));
				kk.setBesitzerId(resultset.getInt("besitzerId"));
				kk.setName(resultset.getString("name"));
				kk.setSitz(resultset.getString("sitz"));
				kk.setWebsite(resultset.getString("website"));
				kk.setErstellDatum(resultset.getTimestamp("erstellDatum"));

				// Hinzuf�gen des neuen Objekts zur ArrayList
				resultarray.add(kk);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultarray;
	}

	/**
	 * Suche nach einer bestimmten Kinokette (KK) mithilfe einer vorgegebenen
	 * Kinoketten-Id
	 * 
	 * @param id der KK, nach welcher gesucht werden soll
	 * @return Das KK-Objekt, das mit seiner KK-Id der �bergebenen Id entspricht.
	 *         Falls kein KK-Objekt zur �bergebenen Id gefunden wird, wird null
	 *         zur�ckgegeben.
	 */
	public Kinokette findById(int id) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			ResultSet resultset = stmt
					.executeQuery("SELECT id, name, besitzerId, sitz, website, erstellDatum FROM kinokette"
							+ "WHERE id=" + id + " ORDER BY name");
			// Pr�fe ob das geklappt hat, also ob ein Ergebnis vorhanden ist:
			if (resultset.next()) {
				Kinokette kk = new Kinokette();
				kk.setId(resultset.getInt("id"));
				kk.setBesitzerId(resultset.getInt("besitzerId"));
				kk.setName(resultset.getString("name"));
				kk.setSitz(resultset.getString("sitz"));
				kk.setWebsite(resultset.getString("website"));
				kk.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				return kk;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * Suche nach allen Kinoketten-Objekten, die eine Eigentumsbeziehung mit einem
	 * vorgegebenen Anwender haben. Daraus wird ersichtlich, welcher Anwender
	 * besondere Rechte in Bezug auf welche Kinoketten hat. Besondere Rechte können
	 * zum Beispiel sein, dass der Anwender das jeweilige Objekt verändern darf.
	 * 
	 * @param anwender Objekt, dessen Id mit der BesitzerId der gesuchten
	 *                 Kinokette-Objekte übereinstimmen soll
	 * @return Alle Kinokette-Objekte, die die Id des vorgegebenen Anwenders als
	 *         BesitzerId in der DB eingetragen haben.
	 */
	public ArrayList<Kinokette> findAllByAnwenderOwner(Anwender anwenderOwner) {
		Connection con = DBConnection.connection();

		ArrayList<Kinokette> resultarray = new ArrayList<Kinokette>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt
					.executeQuery("SELECT id, name, besitzerId, sitz, website, erstellDatum FROM kinokette"
							+ "WHERE besitzerId = " + anwenderOwner.getId() + "ORDER BY name");

			while (resultset.next()) {
				Kinokette kk = new Kinokette();
				kk.setId(resultset.getInt("id"));
				kk.setBesitzerId(resultset.getInt("besitzerId"));
				kk.setName(resultset.getString("name"));
				kk.setSitz(resultset.getString("sitz"));
				kk.setWebsite(resultset.getString("website"));
				kk.setErstellDatum(resultset.getTimestamp("erstellDatum"));

				// Hinzuf�gen des neuen Objekts zur ArrayList
				resultarray.add(kk);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultarray;
	}

	/**
	 * Eine Kinokette erh�lt eine BesitzerId. Diese BesitzerId wird einem bestimmten
	 * Anwender zugewiesen. Dadurch l�sst sich eine Eigentumsbeziehung zwischen den
	 * beiden Objekten herstellen. Wenn ein Anwender Besitzer eines (hier:
	 * Kinoketten-)Objektes ist, fallen ihm besondere Rechte zu. Er kann z.B. als
	 * einziger Ver�nderungen vornehmen.
	 * 
	 * @param anwender  welcher als Besitzer der Kinokette in der Datenbank
	 *                  eingetragen werden soll.
	 * @param kinokette Objekt, welches einem Anwender zugeordnet werden soll.
	 */
	public void addEigentumsstruktur(Anwender anwender, Kinokette kinokette) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE kinokette SET " + "besitzerId=\"" + anwender.getId() + "\" " + "WHERE id="
					+ kinokette.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Eine Kinokette hat eine BesitzerId, diese wurde einem bestimmten Anwender
	 * zugewiesen und soll nun gel�scht werden. Die Eigentumsbeziehung wird demnach
	 * aufgehoben und in der DB gel�scht.
	 * 
	 * @param kinokette Objekt bei welchem die BesitzerId in der Datenbank
	 *                  zur�ckgesetzt werden soll.
	 */
	public void deleteEigentumsstruktur(Kinokette kinokette) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate(
					"UPDATE kinokette SET " + "besitzerId=\"" + "" + "\" " + "WHERE id=" + kinokette.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

}
