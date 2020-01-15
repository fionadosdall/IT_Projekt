package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;

/**
 * Das hier ist eine Mapper-Klasse, die Film-Objekte auf eine relationale DB
 * abbildet.
 * 
 *
 */

public class FilmMapper {

	/**
	 * Hier folgt die Klassenvariable. Diese Variable ist wegen "static" nur einmal
	 * vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	 */
	private static FilmMapper filmMapper;

	/**
	 * Gesch�tzter Konstruktor, damit man nicht einfach neue Instanzen bilden kann.
	 * Denn von diesem Mapper wird nur 1x eine Instanz erzeugt
	 */
	protected FilmMapper() {
	}

	/**
	 * Um eine Instanz dieses Mappers erstellen zu können, nutzt man NICHT den
	 * KONSTRUKTOR, sondern die folgende Methode. Sie ist statisch, dadurch stellt
	 * sie sicher, dass nur eine einzige Instanz dieser Klasse existiert. Außerdem
	 * wird zuerst überprüft, ob bereis ein filmMapper existiert, falls nein, wird
	 * ein neuer instanziiert. Existiert bereits ein filmMapper, wird dieser
	 * zurückgegebene.
	 */

	public static FilmMapper filmMapper() {
		if (filmMapper == null) {
			filmMapper = new FilmMapper();
		}
		return filmMapper;
	}

	/**
	 * Es folgt eine Reihe von Methoden, die wir im StarUML aufgef�hrt haben. Sie
	 * erm�glichen, dass man Objekte z.B. suchen, l�schen und updaten kann.
	 */

	/**
	 * Suche nach allen Film-Objekten über vorgegebenen Namen.
	 * 
	 * @param name den die gesuchten Filme tragen
	 * @return Eine ArrayList, die alle gefundenen Film-Objekte enthält. Falls eine
	 *         Exception geworfen wird, kann es passieren, dass die ArrayList leer
	 *         oder nur teilweise befüllt zurück gegeben wird.
	 */
	public ArrayList<Film> findAllByName(String name) {
		Connection con = DBConnection.connection();

		ArrayList<Film> resultarray = new ArrayList<Film>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt
					.executeQuery("SELECT fId, fName, film_anwender_Id, fBeschreibung, bewertung, erstellDatum " + "FROM film"
							+ " WHERE fName = '" + name + "' ORDER BY fName");

			/**
			 * Für jeden Eintrag im Suchergebnis wird jetzt ein Film-Objekt erstellt und die
			 * ArrayListe Stück für Stück aufgebaut/gefuellt.
			 */

			while (resultset.next()) {
				Film f = new Film();
				f.setId(resultset.getInt("fId"));
				f.setName(resultset.getString("fName"));
				f.setBesitzerId(resultset.getInt("film_anwender_Id"));
				f.setBeschreibung(resultset.getString("fBeschreibung"));
				f.setBewertung(resultset.getInt("bewertung"));
				f.setErstellDatum(resultset.getTimestamp("erstellDatum"));

				resultarray.add(f);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// Ergebnis zurückgeben in Form einer ArrayList
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

			ResultSet resultset = stmt.executeQuery("SELECT fName FROM film" + " WHERE fName = '" + name + "'");

			if (resultset.next()) {
				return false;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return true;
	}

	/**
	 * Die insert-Methode fügt ein neues Film-Objekt zur Datenbank hinzu.
	 * 
	 * @param film Objekt welches gespeichert werden soll
	 * @return Das übergebene Objekt, ggf. mit abgeänderter Id.
	 */

	public Film insert(Film film) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			/**
			 * Im Folgenden: Überprüfung, welches die h�chste Id der schon bestehenden
			 * Filme ist.
			 */
			ResultSet resultset = stmt.executeQuery("SELECT MAX(fId) AS maxId " + "FROM film");
			if (resultset.next()) {
				// Wenn die h�chste Id gefunden wurde, wird eine neue Id mit +1 h�her erstellt
				film.setId(resultset.getInt("maxId") + 1);
				stmt = con.createStatement();

				// Jetzt wird die Id tats�chlich eingef�gt:
				stmt.executeUpdate("INSERT INTO film (fId, fName, film_anwender_Id, erstellDatum, fBeschreibung, bewertung)"
						+ " VALUES( " + film.getId() + ", '" + film.getName() + "', " + film.getBesitzerId() + ", "
						+ film.getErstellDatum() + ", '" + film.getBeschreibung() + "', " + film.getBewertung() + ") ");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		/**
		 * Rückgabe des Film-Objektes. Durch die Methode wurde das Objekt ggf. angepasst (z.B:
		 * angepasste Id)
		 */
		return film;
	}

	/**
	 * Das Objekt wieder wiederholt, in upgedateter Form in die Datenbank
	 * eingetragen.
	 * 
	 * @param film Objekt, welches verändert werden soll.
	 * @return Das Objekt, welches im Parameter �bergeben wurde.
	 */
	public Film update(Film film) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			/**
			 * Update wird in die Datenbank eingetragen
			 */
			stmt.executeUpdate("UPDATE film SET " + "film_anwender_Id=\"" + film.getBesitzerId() + "\", " + "fName=\" '"
					+ film.getName() + "' \", " + "fBeschreibung=\" '" + film.getBeschreibung() + "' \", " + "bewertung=\""
					+ film.getBewertung() + "\", " + "erstellDatum=\"" + film.getErstellDatum() + "\"" + " WHERE fId="
					+ film.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		/**
		 * R�ckgabe des neuen, veränderten Film-Objektes
		 */
		return film;
	}

	/**
	 * Mit dieser Methode kann ein Film-Objekt aus der Datenbank gelöscht werden.
	 * 
	 * @param film Objekt, welches gel�scht werden soll
	 */
	public void delete(Film film) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM film " + "WHERE fId=" + film.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Suche nach einem bestimmten Film mithilfe einer vorgegebenen Film-Id
	 * 
	 * @param id des Films, nach welchem gesucht werden soll
	 * @return Das Film-Objekt, bei dem die FilmId mit der übergebenen Id
	 *         übereinstimmt. Falls kein Film zur übergebenen Id gefunden wurde,
	 *         wird null zur�ckgegeben.
	 */
	public Film findById(int id) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			ResultSet resultset = stmt
					.executeQuery("SELECT fId, fName, film_anwender_Id, fBeschreibung, bewertung, erstellDatum FROM film"
							+ " WHERE fId=" + id + " ORDER BY fName");
			// Pr�fe ob das geklappt hat, also ob ein Ergebnis vorhanden ist:
			if (resultset.next()) {
				Film f = new Film();
				f.setId(resultset.getInt("fId"));
				f.setName(resultset.getString("fName"));
				f.setBesitzerId(resultset.getInt("film_anwender_Id"));
				f.setBeschreibung(resultset.getString("fBeschreibung"));
				f.setBewertung(resultset.getInt("bewertung"));
				f.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				return f;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * Alle in der Datenbank vorhandenen Filme sollen gesucht und ausgegeben werden
	 * 
	 * @return Alle Film-Objekte, die in der Datenbank eingetragen sind, werden in
	 *         einer ArrayList zur�ckgegeben.
	 */
	public ArrayList<Film> findAll() {
		Connection con = DBConnection.connection();

		ArrayList<Film> resultarray = new ArrayList<Film>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt.executeQuery(
					"SELECT fId, fName, film_anwender_Id, fBeschreibung, bewertung, erstellDatum FROM film" + " ORDER BY fName");

			while (resultset.next()) {
				Film f = new Film();
				f.setId(resultset.getInt("fId"));
				f.setName(resultset.getString("fName"));
				f.setBesitzerId(resultset.getInt("film_anwender_Id"));
				f.setBeschreibung(resultset.getString("fBeschreibung"));
				f.setBewertung(resultset.getInt("bewertung"));
				f.setErstellDatum(resultset.getTimestamp("erstellDatum"));

				resultarray.add(f);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// Ergebnis zurückgeben in Form einer ArrayList
		return resultarray;
	}

	/**
	 * Suche nach allen Film-Objekten, die eine Eigentumsbeziehung mit einem
	 * vorgegebene Anwender haben. Daraus wird ersichtlich, welcher Anwender
	 * besondere Rechte in Bezug auf welche Filme hat. Besondere Rechte können zum
	 * Beispiel sein, dass der Anwender das jeweilige Objekt verändern darf.
	 * 
	 * @param anwender Objekt, dessen Id mit der BesitzerId der gesuchten
	 *                 Film-Objekte übereinstimmen soll.
	 * @return Alle Film-Objekte, die die Id des vorgegebenen Anwenders als
	 *         BesitzerId in der Datenbank eingetragen haben.
	 */
	public ArrayList<Film> findAllByAnwenderOwner(Anwender anwender) {
		Connection con = DBConnection.connection();

		ArrayList<Film> resultarray = new ArrayList<Film>();

		try {
			Statement stmt = con.createStatement();

			ResultSet resultset = stmt
					.executeQuery("SELECT fId, fName, film_anwender_Id, fBeschreibung, bewertung, erstellDatum FROM film"
							+ " WHERE film_anwender_Id=" + anwender.getId() + " ORDER BY film_anwender_Id");
			while (resultset.next()) {
				Film f = new Film();
				f.setId(resultset.getInt("fId"));
				f.setName(resultset.getString("fName"));
				f.setBesitzerId(resultset.getInt("film_anwender_Id"));
				f.setBeschreibung(resultset.getString("fBeschreibung"));
				f.setBewertung(resultset.getInt("bewertung"));
				f.setErstellDatum(resultset.getTimestamp("erstellDatum"));

				resultarray.add(f);

			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		//Ergebnis zurückgeben in Form der eingangs erstellten ArrayList
		return resultarray;
	}

}
