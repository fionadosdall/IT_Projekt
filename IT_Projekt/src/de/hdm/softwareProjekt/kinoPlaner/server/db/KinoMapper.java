package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;

/**
 * Das hier ist eine Mapper-Klasse, die Kino-Objekte auf eine relationale DB
 * abbildet.
 * 
 *
 */

public class KinoMapper {

	/**
	 * Hier folgt die Klassenvariable. Diese Variable ist wegen "static" nur einmal
	 * vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	 */
	private static KinoMapper kinoMapper;

	/**
	 * Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann.
	 * Denn von diesem Mapper wird nur 1x eine Instanz erzeugt
	 */
	protected KinoMapper() {
	}

	/**
	 * Um eine Instanz dieses Mappers erstellen zu können, nutzt man NICHT den
	 * KONSTRUKTOR, sondern die folgende Methode. Sie ist statisch, dadurch stellt
	 * sie sicher, dass nur eine einzige Instanz dieser Klasse existiert. Außerdem
	 * wird zuerst überprüft, ob bereis ein kinoMapper existiert, falls nein, wird
	 * ein neuer instanziiert. Existiert bereits ein kinoMapper, wird dieser
	 * zurückgegeben.
	 */
	public static KinoMapper kinoMapper() {
		if (kinoMapper == null) {
			kinoMapper = new KinoMapper();
		}
		return kinoMapper;
	}

	/**
	 * Es folgt eine Reihe von Methoden, die wir im StarUML aufgeführt haben. Sie
	 * ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.
	 */

	/**
	 * Suche nach allen Kinos über vorgegebenen Namen.
	 * 
	 * @param name den die gesuchten Kinos tragen
	 * @return Eine ArrayList, die alle gefundenen Kinos enthält. Falls eine
	 *         Exception geworfen wird, kann es passieren, dass die ArrayList leer
	 *         oder nur teilweise befüllt zurück gegeben wird.
	 */
	public ArrayList<Kino> findAllByName(String name) {
		Connection con = DBConnection.connection();

		ArrayList<Kino> resultarray = new ArrayList<Kino>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery(
					"SELECT kId, kName, kino_anwender_Id, kino_kinokette_Id, plz, stadt, strasse, hausnummer, erstellDatum"
							+ " FROM Kino" + " WHERE kName = '" + name + "' ORDER BY kName");

			/**
			 * Für jeden Eintrag im Suchergebnis wird jetzt ein Kino-Objekt erstellt und die
			 * ArrayListe Stück für Stück aufgebaut/gefuellt.
			 */

			while (resultset.next()) {
				Kino k = new Kino();
				k.setId(resultset.getInt("kId"));
				k.setBesitzerId(resultset.getInt("kino_anwender_Id"));
				k.setName(resultset.getString("kName"));
				k.setPlz(resultset.getInt("plz"));
				k.setStadt(resultset.getString("stadt"));
				k.setStrasse(resultset.getString("strasse"));
				k.setHausnummer(resultset.getString("hausnummer"));
				k.setErstellDatum(resultset.getTimestamp("erstellDatum"));

				// Hinzufügen des neuen Objekts zur ArrayList
				resultarray.add(k);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// Rückgabe des Ergebnisses in Form der zuvor erstellten ArrayList
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

			ResultSet resultset = stmt.executeQuery("SELECT kName FROM Kino" + " WHERE kName = '" + name + "'");

			if (resultset.next()) {
				return false;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return true;
	}

	/**
	 * Die insert-Methode fügt ein neues Kino-Objekt zur Datenbank hinzu.
	 * 
	 * @param kino als das zu speichernde Objekt
	 * @return Das bereits übergeben Objekt, ggf. mit abgeänderter Id
	 */
	public Kino insert(Kino kino) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			/**
			 * Im Folgenden: Überprüfung, welches die höchste Id der schon bestehenden Kinos
			 * ist.
			 */
			ResultSet resultset = stmt.executeQuery("SELECT MAX(kId) AS maxId " + "FROM Kino");
			if (resultset.next()) {
				// Wenn die höchste Id gefunden wurde, wird eine neue Id mit +1 höher erstellt
				kino.setId(resultset.getInt("maxId") + 1);
				stmt = con.createStatement();

				// Jetzt wird die Id tatsächlich eingefügt:
				stmt.executeUpdate(
						"INSERT INTO Kino (kId, kName, kino_anwender_Id, plz, stadt, strasse, hausnummer, erstellDatum)"
								+ " VALUES(" + kino.getId() + ", '" + kino.getName() + "', " + kino.getBesitzerId()
								+ ", " + kino.getPlz() + ", '" + kino.getStadt() + "', '" + kino.getStrasse() + "', "
								+ kino.getHausnummer() + ", " + kino.getErstellDatum() + ")");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		/**
		 * Rückgabe des Kinos. Durch die Methode wurde das Objekt ggf. angepasst (z.B.
		 * angepasste Id)
		 */
		return kino;
	}

	/**
	 * Das Objekt wird wiederholt, in geupdateter Form in die Datenbank eingetragen.
	 * 
	 * @param kino als das Objekt, das verändert werden soll.
	 * @return Das Objekt, welches im Parameter übergeben wurde.
	 */
	public Kino update(Kino kino) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			/**
			 * Update wird in die Datenbank eingetragen.
			 */
			stmt.executeUpdate("UPDATE Kino SET " + "kino_anwender_Id= '" + kino.getBesitzerId() + "' , " + "kName= '"
					+ kino.getName() + "', " + "erstellDatum= '" + kino.getErstellDatum() + "' , " + "plz= '"
					+ kino.getPlz() + "' , " + "stadt= '" + kino.getStadt() + "', " + "strasse= '" + kino.getStrasse()
					+ "' , " + "hausnummer= '" + kino.getHausnummer() + "' WHERE kId=" + kino.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		/**
		 * Rückgabe des neuen, veränderten Kino-Objektes
		 */
		return kino;
	}

	/**
	 * Mit dieser Methode kann ein Kino-Objekt aus der Datenbank gelöscht werden.
	 * 
	 * @param kino Objekt, welches gelöscht werden soll.
	 */
	public void delete(Kino kino) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM Kino " + "WHERE kId=" + kino.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Alle in der Datenbank vorhandenen Kinos sollen gesucht und ausgegeben werden
	 * 
	 * @return Alle Kino-Objekte, die in der Datenbank eingetragen sind, werden in
	 *         einer ArrayList zurückgegeben.
	 */
	public ArrayList<Kino> findAllKinos() {
		Connection con = DBConnection.connection();

		ArrayList<Kino> resultarray = new ArrayList<Kino>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt
					.executeQuery("SELECT kId, kName, kino_anwender_Id, plz, stadt, strasse, hausnummer, erstellDatum"
							+ " FROM Kino" + " ORDER BY kName");

			while (resultset.next()) {
				Kino k = new Kino();
				k.setId(resultset.getInt("kId"));
				k.setBesitzerId(resultset.getInt("kino_anwender_Id"));
				k.setName(resultset.getString("kName"));
				k.setPlz(resultset.getInt("plz"));
				k.setStadt(resultset.getString("stadt"));
				k.setStrasse(resultset.getString("strasse"));
				k.setHausnummer(resultset.getString("hausnummer"));
				k.setErstellDatum(resultset.getTimestamp("erstellDatum"));

				// Hinzufügen des neuen Objekts zur ArrayList
				resultarray.add(k);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// Rückgabe des Ergebnisses in Form einer ArrayList
		return resultarray;
	}

	/**
	 * Suche nach einem Kino mit vorgegebener Kino-Id
	 * 
	 * @param id zugehörig zu einem Kino, nach welchem gesucht werden soll, also der
	 *           Primärschlüssel in der Datenbank.
	 * @return Das Kino-Objekt, das mit seiner Kino-Id der übergebenen Id
	 *         entspricht. Falls kein Kino zur übergebenen Id gefunden wurde, wird
	 *         null zurückgegeben.
	 */
	public Kino findById(int id) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			ResultSet resultset = stmt.executeQuery(
					"SELECT kId, kName, kino_anwender_Id, plz, stadt, strasse, hausnummer, erstellDatum FROM Kino"
							+ " WHERE kId=" + id + " ORDER BY kName");
			// Pr�fe ob das geklappt hat, also ob ein Ergebnis vorhanden ist:
			if (resultset.next()) {
				Kino k = new Kino();
				k.setId(resultset.getInt("kId"));
				k.setBesitzerId(resultset.getInt("kino_anwender_Id"));
				k.setName(resultset.getString("kName"));
				k.setPlz(resultset.getInt("plz"));
				k.setStadt(resultset.getString("stadt"));
				k.setStrasse(resultset.getString("strasse"));
				k.setHausnummer(resultset.getString("hausnummer"));
				k.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				return k;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * Suche nach allen Kino-Objekten, die eine Eigentumsbeziehung mit einem
	 * vorgegebenen Anwender haben. Daraus wird ersichtlich, welcher Anwender
	 * besondere Rechte in Bezug auf welche Kinos hat. Besondere Rechte können zum
	 * Beispiel sein, dass der Anwender das jeweilige Objekt verändern darf.
	 * 
	 * @param anwender Objekt, dessen Id mit der BesitzerId der gesuchten
	 *                 Kino-Objekte übereinstimmen soll.
	 * @return Alle Kino-Objekte, die die Id des vorgegebenen Anwenders als
	 *         BesitzerId in der Datenbank eingetragen haben.
	 */
	public ArrayList<Kino> findAllByAnwenderOwner(Anwender anwenderOwner) {
		Connection con = DBConnection.connection();

		ArrayList<Kino> resultarray = new ArrayList<Kino>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery(
					"SELECT kId, kName, kino_anwender_Id, plz, stadt, strasse, hausnummer, erstellDatum FROM Kino"
							+ " WHERE kino_anwender_Id = " + anwenderOwner.getId() + " ORDER BY kName");

			while (resultset.next()) {
				Kino k = new Kino();
				k.setId(resultset.getInt("kId"));
				k.setBesitzerId(resultset.getInt("kino_anwender_Id"));
				k.setName(resultset.getString("kName"));
				k.setPlz(resultset.getInt("plz"));
				k.setStadt(resultset.getString("stadt"));
				k.setStrasse(resultset.getString("strasse"));
				k.setHausnummer(resultset.getString("hausnummer"));
				k.setErstellDatum(resultset.getTimestamp("erstellDatum"));

				// Hinzufügen des neuen Objekts zur ArrayList
				resultarray.add(k);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// Rückgabe des Ergebnisses in Form einer ArrayList
		return resultarray;
	}

	/**
	 * Kino-Objekte können zu einer bestimmten Kinokette hinzugefügt werden. Dadurch
	 * wird in der Datenbank eine Beziehung zwischen den beiden Objekten angelegt.
	 * 
	 * @param kinokette die ein vorgegebenes Kino enthalten soll.
	 * @param kino      das zu einer Kinokette hinzugefügt werden soll.
	 */
	public void addKinokette(Kinokette kinokette, Kino kino) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE Kino SET " + "kino_kinokette_Id=\"" + kinokette.getId() + "\"" + " WHERE kId="
					+ kino.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Die Beziehung zwischen einem Kino-Objekt und einem Kinoketten-Objekt kann in
	 * der Datenbank gelöscht werden. Der passende Datenbankeintrag wird anhand der
	 * vorgegebenen kinoId gefunden. Anschließend wird in der Kino-Tabelle die
	 * kinokettenId zurückgesetzt.
	 * 
	 * @param kino Objekt, bei welchem die Beziehung zu einer beliebligen Kinokette
	 *             gelöscht werden soll.
	 */
	public void deleteKinokette(Kino kino) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE Kino SET " + "kino_kinokette_Id=\"" + "\"" + " WHERE kId=" + kino.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Ein Kino erhält eine BesitzerId. Diese BesitzerId wird einem bestimmten
	 * Anwender zugewiesen. Dadurch lässt sich eine Eigentumsbeziehung zwischen den
	 * beiden Objekten herstellen. Wenn ein Anwender Besitzer eines (hier:
	 * Kino-)Objektes ist, fallen ihm besondere Rechte zu. Er kann z.B. als einziger
	 * Veränderungen vornehmen.
	 * 
	 * @param anwender welcher als Besitzer des Kinos in der Datenbank eingetragen
	 *                 werden soll.
	 * @param kino     Objekt, welches einem Anwender zugeordnet werden soll.
	 */
	public void addEigentumsstruktur(Anwender anwender, Kino kino) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE Kino SET " + "kino_anwender_Id=\"" + anwender.getId() + "\"" + " WHERE kId="
					+ kino.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Ein Kino hat eine BesitzerId, diese wurde einem bestimmten Anwender
	 * zugewiesen und soll nun gelöscht werden. Die Eigentumsbeziehung wird demnach
	 * aufgehoben und in der DB gelöscht.
	 * 
	 * @param kino Objekt bei welchem die BesitzerId in der Datenbank zurückgesetzt
	 *             werden soll.
	 */
	public void deleteEigentumsstruktur(Kino kino) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE Kino SET " + "kino_anwender_Id=\"" + "\"" + " WHERE kId=" + kino.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Suche nach allen Kino-Objekten, die eine Beziehung mit einer vorgegebenen
	 * Kinokette haben. Liegt eine Beziehung zwischen Kino und Kinokette vor, ist in
	 * der Datenbank in der Kino-Tabelle die entsprechende kinokettenId hinterlegt.
	 * Diese kinokettenId muss mit der Id der im Methodenparamter übergebenen
	 * Kinokette übereinstimmen.
	 * 
	 * @param kinokette Objekt, dessen Id mit den kinokettenIds in der Kino-Tabelle
	 *                  übereinstimmen soll.
	 * @return Alle Kino-Objekte in einer ArrayList, deren kinokettenId der
	 *         übergebenen Kinokette entspricht.
	 */
	public ArrayList<Kino> findAllByKinokette(Kinokette kinokette) {
		Connection con = DBConnection.connection();

		ArrayList<Kino> resultarray = new ArrayList<Kino>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery(
					"SELECT kId, kName, kino_anwender_Id, plz, stadt, strasse, hausnummer, erstellDatum FROM Kino"
							+ " WHERE kino_kinokette_Id = " + kinokette.getId() + " ORDER BY kName");
			/**
			 * FÜr jeden Eintrag im Suchergebnis wird jetzt ein Kino-Objekt erstellt und die
			 * ArrayListe Stück für Stück aufgebaut/gefuellt.
			 */
			while (resultset.next()) {
				Kino k = new Kino();
				k.setId(resultset.getInt("kId"));
				k.setBesitzerId(resultset.getInt("kino_anwender_Id"));
				k.setName(resultset.getString("kName"));
				k.setPlz(resultset.getInt("plz"));
				k.setStadt(resultset.getString("stadt"));
				k.setStrasse(resultset.getString("strasse"));
				k.setHausnummer(resultset.getString("hausnummer"));
				k.setErstellDatum(resultset.getTimestamp("erstellDatum"));

				// Hinzufügen des neuen Objekts zur ArrayList
				resultarray.add(k);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// ArrayList mit Ergebnis zurückgeben
		return resultarray;
	}

	public Kino findByName(String name) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			ResultSet resultset = stmt.executeQuery(
					"SELECT kId, kName, kino_anwender_Id, plz, stadt, strasse, hausnummer, erstellDatum FROM Kino"
							+ " WHERE kName='" + name + "' ORDER BY kName");
			// Pr�fe ob das geklappt hat, also ob ein Ergebnis vorhanden ist:
			if (resultset.next()) {
				Kino k = new Kino();
				k.setId(resultset.getInt("kId"));
				k.setBesitzerId(resultset.getInt("kino_anwender_Id"));
				k.setName(resultset.getString("kName"));
				k.setPlz(resultset.getInt("plz"));
				k.setStadt(resultset.getString("stadt"));
				k.setStrasse(resultset.getString("strasse"));
				k.setHausnummer(resultset.getString("hausnummer"));
				k.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				return k;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

}
