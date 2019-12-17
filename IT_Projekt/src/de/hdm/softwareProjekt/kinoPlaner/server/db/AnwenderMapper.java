package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;

/**
 * Das hier ist eine Mapper-Klasse, die Anwender-Objekte auf eine relationale DB
 * abbildet.
 * 
 * @author annaf
 *
 */

public class AnwenderMapper {

	/**
	 * Hier folgt die Klassenvariable. Diese Variable ist wegen "static" nur einmal
	 * vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	 */
	private static AnwenderMapper anwenderMapper;

	/**
	 * Gesch�tzter Konstruktor, damit man nicht einfach neue Instanzen bilden kann.
	 * Denn von diesem Mapper wird nur 1x eine Instanz erzeugt
	 */
	protected AnwenderMapper() {
	}

	/**
	 * Um eine Instanz dieses Mappers erstellen zu k�nnen, nutzt man NICHT den
	 * KONSTRUKTOR, sondern die folgende Methode. Sie ist statisch, dadurch stellt
	 * sie sicher, dass nur eine einzige Instanz dieser Klasse existiert. Au�erdem
	 * wird zuerst �berpr�ft, ob bereits ein anwenderMapper existiert, falls nein,
	 * wird ein neuer instanziiert. Existiert bereits ein anwenderMapper, wird
	 * dieser zur�ck gegeben.
	 */
	public static AnwenderMapper anwenderMapper() {
		if (anwenderMapper == null) {
			anwenderMapper = new AnwenderMapper();
		}
		return anwenderMapper;
	}

	/**
	 * Es folgt eine Reihe von Methoden, die wir im StarUML aufgef�hrt haben. Sie
	 * ermöglichen, dass man Objekte z.B. suchen, l�schen und updaten kann.
	 */

	/**
	 * Suche nach allen Anwendern über vorgegebenen Namen.
	 * 
	 * @param name den die gesuchten Anwender tragen 
	 * @return Eine ArrayList, die alle gefundenen Anwender enthält. Falls eine
	 *         Exception geworfen wird, kann es passieren, dass die ArrayList leer
	 *         oder nur teilweise befüllt zurück gegeben wird.
	 */
	public ArrayList<Anwender> findAllByName(String name) {
		Connection con = DBConnection.connection();

		ArrayList<Anwender> resultarray = new ArrayList<Anwender>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery("SELECT aId, gMail, aName, erstellDatum " + "FROM Anwender"
					+ "WHERE aName = " + name + "ORDER BY aName");

			/**
			 * Für jeden Eintrag im Suchergebnis wird jetzt ein Anwender-Objekt erstellt und
			 * die ArrayListe Stück für Stück aufgebaut/gefuellt.
			 */

			while (resultset.next()) {
				Anwender a = new Anwender();
				a.setId(resultset.getInt("aId"));
				a.setGmail(resultset.getString("gMail"));
				a.setName(resultset.getString("aName"));
				a.setErstellDatum(resultset.getTimestamp("erstellDatum"));

				// Hinzufügen des neuen Objekts zur ArrayList
				resultarray.add(a);

			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		//Ergebnis zurückgeben in Form der ArrayList
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

			ResultSet resultset = stmt.executeQuery("SELECT aName FROM Anwender" + "WHERE aName =" + name);

			if (resultset.next()) {
				return false;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return true;
	}

	/**
	 * Die insert-Methode f�gt ein neues Anwender-Objekt zur Datenbank hinzu.
	 * 
	 * @param anwender bzw. das zu speichernde Objekt
	 * @return Das bereits übergeben Objekt, ggf. mit abgeänderter Id
	 */
	public Anwender insert(Anwender anwender) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			/**
			 * Im Folgenden: Überprüfung, welches die h�chste Id der schon bestehenden
			 * Anwender ist.
			 */
			ResultSet resultset = stmt.executeQuery("SELECT MAX (aId) AS maxId " + "FROM Anwender");
			if (resultset.next()) {
				// Wenn die h�chste Id gefunden wurde, wird eine neue Id mit +1 h�her erstellt
				anwender.setId(resultset.getInt("maxId") + 1);
				stmt = con.createStatement();

				// Jetzt wird die Id tats�chlich eingef�gt:
				stmt.executeUpdate("INSERT INTO Anweder (aId, gMail, aName, erstellDatum)" + "VALUES(" + anwender.getId()
						+ "','" + anwender.getGmail() + "','" + anwender.getName() + "','" + anwender.getErstellDatum()
						+ ")");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		/**
		 * Rückgabe des Anwenders. Durch die Methode wurde das Objekt ggf. angepasst
		 * (z.B. angepasste Id)
		 */
		return anwender;
	}

	/**
	 * Das Objekt wird wiederholt, in geupdateter Form in die Datenbank eingetragen.
	 * 
	 * @param anwender bzw. Objekt, welches verändert werden soll.
	 * @return Das Objekt, welches im Paramter übergeben wurde.
	 */
	public Anwender update(Anwender anwender) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			/**
			 * Update wird in die Datenbank eingetragen.
			 */
			stmt.executeUpdate("UPDATE Anwender SET " + "aName=\"" + anwender.getName() + "\", " + "gMail=\""
					+ anwender.getGmail() + "\", " + "erstellDatum=\"" + anwender.getErstellDatum() + "\" "
					+ "WHERE aId=" + anwender.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		/**
		 * Rückgabe des neuen, veränderten Anwender-Objektes
		 */
		return anwender;
	}

	/**
	 * Mit dieser Methode kann ein Anwender-Objekt aus der Datenbank gelöscht
	 * werden.
	 * 
	 * @param anwender Objekt, welches gelöscht werden soll.
	 */
	public void delete(Anwender anwender) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM Anwender " + "WHERE aId=" + anwender.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Alle in der Datenbank vorhandenen Anwender sollen gesucht und ausgegeben werden
	 * 
	 * @return Alle Anwender-Objekte, die in der Datenbank eingetragen sind, werden in
	 *         einer ArrayList zurückgegeben.
	 */
	public ArrayList<Anwender> findAll() {
		Connection con = DBConnection.connection();

		ArrayList<Anwender> resultarray = new ArrayList<Anwender>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery("SELECT aId, gMail, aName, erstellDatum FROM Anwender"
			+ "ORDER BY aName");

			/**
			 * F�r jeden Eintrag im Suchergebnis wird jetzt ein Anwender-Objekt erstellt und
			 * die ArrayListe Stück für Stück aufgebaut/gefuellt.
			 */

			while (resultset.next()) {
				Anwender a = new Anwender();
				a.setId(resultset.getInt("aId"));
				a.setGmail(resultset.getString("gMail"));
				a.setName(resultset.getString("aName"));
				a.setErstellDatum(resultset.getTimestamp("erstellDatum"));

				// Hinzuf�gen des neuen Objekts zur ArrayList
				resultarray.add(a);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();

		}
		// Rückgabe des Ergebnisses in Form einer ArrayList
		return resultarray;
	}

	/**
	 * Suche nach einem Anwender mit vorgegebener Anwender-Id
	 * 
	 * @param id zugeh�rig zu einem Anwender, nach welchem gesucht werden soll, also
	 *           der Prim�rschl�ssel in der Datenbank.
	 * @return Das Anwender-Objekt, das mit seiner Anwender-id der �bergebenen Id
	 *         entspricht. Falls kein Anwender zur �bergebenen Id gefunden wurde,
	 *         wird null zur�ckgegeben.
	 */
	public Anwender findById(int id) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			ResultSet resultset = stmt.executeQuery(
					"SELECT aId, gMail, aName, erstellDatum FROM Anwender" + "WHERE aId=" + id + " ORDER BY aName");
			// Pruefung, ob ein Ergebnis vorhanden ist:
			if (resultset.next()) {
				Anwender a = new Anwender();
				a.setId(resultset.getInt("aId"));
				a.setGmail(resultset.getString("gMail"));
				a.setName(resultset.getString("aName"));
				a.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				return a;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * Suche nach einem Anwender mit vorgegebenem Namen.
	 * 
	 * @param name zu dem der dazugeh�rige Anwender gesucht werden soll.
	 * @return Das Anwender-Objekt, das dem �bergebenen Namen entspricht. Falls kein
	 *         Anwender zum �bergebenen Namen gefunden wurde, wird null
	 *         zur�ckgegeben.
	 */
	public Anwender findByName(String name) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			ResultSet resultset = stmt.executeQuery(
					"SELECT aId, gMail, aName, erstellDatum FROM Anwender" + "WHERE aName=" + name + "ORDER BY aName");
			if (resultset.next()) {
				Anwender a = new Anwender();
				a.setId(resultset.getInt("aId"));
				a.setGmail(resultset.getString("gMail"));
				a.setName(resultset.getString("aName"));
				a.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				return a;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	/**
	 * find by gmail
	 */
	
	public Anwender findByGmail (String gmail) {
		
		Connection con = DBConnection.connection();
		String sql = "SELECT * FROM Anwender WHERE gMail= ' " + gmail + " ' ";
		
		try {
			
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
			
				if (rs.next()) {
				Anwender anwender = new Anwender ();
				
				
				anwender.setId(rs.getInt("aId"));
				anwender.setName(rs.getString("aName"));
				anwender.setGmail(rs.getString("gMail"));
				anwender.setErstellDatum(rs.getTimestamp("erstellDatum"));
				
				return anwender;
				}
			
		}catch (SQLException e) {
				e.printStackTrace();
			
		} return null;
	}

	/**
	 * Suche nach allen Anwendern, die zu einer vorgegebenen Gruppe geh�ren. Man
	 * kann sich dadurch also die Gruppenmitglieder einer bestimmten Gruppe ausgeben
	 * lassen.
	 * 
	 * @param gruppe zu welcher man alle dazugeh�rigen Anwender ausgegeben haben
	 *               m�chte.
	 * @return Eine ArrayList, die alle gefundenen Anwender der Gruppe enth�lt.
	 *         Falls eine Exception geworfen wird, kann es passieren, dass die
	 *         ArrayList leer oder nur teilweise bef�llt zur�ck gegeben wird.
	 */
	public ArrayList<Anwender> findAllByGruppe(Gruppe gruppe) {
		Connection con = DBConnection.connection();

		ArrayList<Anwender> resultarray = new ArrayList<Anwender>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery(
					"SELECT Gruppe.gId, Gruppe.gruppe_anwender_Id, Anwender.gMail, Anwender.aName, "
							+ "Anwender.erstellDatum FROM Gruppe " + "INNER JOIN Anwender "
							+ "ON Gruppe.gruppe_anwender_Id = Anwender.aId " + "WHERE gId = " + gruppe.getId()
							+ "ORDER BY aId");

			/**
			 * F�r jeden Eintrag im Suchergebnis wird jetzt ein Anwender-Objekt erstellt und
			 * damit wird die ArrayListe Durchlauf für Durchlauf der Schleife
			 * aufgebaut/gefuellt.
			 */

			while (resultset.next()) {
				Anwender a = new Anwender();
				a.setId(resultset.getInt("aId"));
				a.setGmail(resultset.getString("gMail"));
				a.setName(resultset.getString("aName"));
				a.setErstellDatum(resultset.getTimestamp("erstellDatum"));

				// Hinzuf�gen des neuen Objekts zur ArrayList
				resultarray.add(a);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();

		}
		return resultarray;
	}

}
