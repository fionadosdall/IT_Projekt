package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Auswahl;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;

//Das hier ist eine Mapper-Klasse, die Film-Objekte auf eine relationale DB abbildet. 

public class FilmMapper {
	
	/**
	 * Hier folgt die Klassenvariable. Diese Variable ist wegen "static" nur einmal
	 * vorhanden. Hier wird die einzige Instanz der Klasse gespeichert.
	 */
	private static FilmMapper filmMapper; 
	
	
	/**
	 * Geschützter Konstruktor, damit man nicht einfach neue Instanzen bilden kann.
	 * Denn von diesem Mapper wird nur 1x eine Instanz erzeugt
	 */
	protected FilmMapper () {	
	}

	/** Um eine Instanz dieses Mappers erstellen zu können, nutzt man NICHT den KONSTRUKTOR, 
	 * sondern die folgende Methode. 
	 * Sie ist statisch, dadurch stellt sie sicher, dass nur eine einzige Instanz dieser Klasse existiert.
	 * Außerdem wird zuerst überprüft, ob bereits ein votingMapper existiert, falls nein, wird ein neuer
	 * instanziiert. Existiert bereits ein votingMapper, wird dieser zurück gegeben.	
	 */
		
		public static FilmMapper filmMapper() {
			if (filmMapper == null) {
				filmMapper = new FilmMapper();
			}
			return filmMapper; 
		}
		
		
		
	/**
	 * Es folgt eine Reihe von Methoden, die wir im StarUML aufgeführt haben. Sie
	 * ermöglichen, dass man Objekte z.B. suchen, löschen und updaten kann.
	 */
		
	
	/** 
	 * Die insert-Methode fügt ein neues Anwender-Objekt zur Datenbank hinzu. 
	 * @param film Objekt welches gespeichert werden soll
	 * @return Das übergebene Objekt, gg. mit abgeänderter Id. 
	 */

	public Film insert(Film film) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			/**
			 * Im Folgenden: Überprüfung, welches die höchste Id der schon bestehenden
			 * Anwender ist.
			 */
			ResultSet resultset = stmt.executeQuery("SELECT MAX (id) AS maxId " + "FROM film");
			if (resultset.next()) {
				// Wenn die höchste Id gefunden wurde, wird eine neue Id mit +1 höher erstellt
				film.setId(resultset.getInt("maxId") + 1);
				stmt = con.createStatement();

				// Jetzt wird die Id tatsächlich eingefügt: 
				stmt.executeUpdate("INSERT INTO film (id, name, besitzerId, erstellDatum, beschreibung, bewertung)" +
						"VALUES(" + film.getId() + "','" + film.getName() + "','" + film.getBesitzerId()
						+ "','" + film.getErstellDatum() + "','" + film.getBeschreibung()+ "','" + 
						film.getBewertung()+ ")"); 
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
		/**
		 * Rückgabe des Films. Durch die Methode wurde das Objekt ggf. angepasst (z.B: angepasste Id)
		 */
		return film;
	}
	
	
	
	/**
	 * Das Objekt wieder wiederholt, in upgedateter Form in die Datenbank eingetragen.
	 * @param film Objekt, welches verändert werden soll.
	 * @return Das Objekt, welches im Parameter übergeben wurde.
	 */
	public Film update (Film film) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			/**
			 * Update wird in die Datenbank eingetragen
			 */
			stmt.executeUpdate("UPDATE film SET " + "besitzerId=\""
				+ film.getBesitzerId() + "\", " + "name=\""
				+ film.getName() + "\", " + "beschreibung=\"" 
				+ film.getBeschreibung()+ "\", " + "bewertung=\"" 
				+ film.getBewertung() + "\", " + "erstellDatum=\"" 
				+ film.getErstellDatum()+ "\" " + "WHERE id=" + film.getId());
		}
		catch (SQLException e2) {
			e2.printStackTrace();
		}
		/**
		 * Rückgabe des neuen, veränderten Film-Objektes
		 */
		return film;
	}
	
	
	
	/**
	 * Mit dieser Methode kann ein Film-Objekt aus der Datenbank gelöscht werden.
	 * @param film Objekt, welches gelöscht werden soll
	 */
	public void delete (Film film) {
		Connection con = DBConnection.connection();

	    try {
	      Statement stmt = con.createStatement();

	      stmt.executeUpdate("DELETE FROM film " + "WHERE id=" + film.getId());

	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }
	}
	
	
	
	/**
	 * Suche nach einem bestimmten Film mithilfe einer vorgegebenen Film-Id
	 * @param id des Films, nach welchem gesucht werden soll
	 * @return Das Film-Objekt, bei dem die FilmId mit der übergebenen Id übereinstimmt.
	 * Falls kein Film zur übergebenen Id gefunden wurde, wird null zurückgegeben. 
	 */
	public Film findById (int id) {
		Connection con = DBConnection.connection(); 
		try {
			Statement stmt = con.createStatement(); 
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, beschreibung, bewertung, erstellDatum FROM film" + 
					"WHERE id=" + id + " ORDER BY name"); 
			// Prüfe ob das geklappt hat, also ob ein Ergebnis vorhanden ist: 
			if (resultset.next()) {
				Film f = new Film ();  
				f.setId(resultset.getInt("id")); 
				f.setName(resultset.getString("name")); 
				f.setBesitzerId(resultset.getInt("besitzerId"));
				f.setBeschreibung(resultset.getString("beschreibung"));
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
	 * @return Alle Film-Objekte, die in der Datenbank eingetragen sind, werden in einer ArrayList
	 * zurückgegeben. 
	 */
	public ArrayList <Film> findAll() {
		Connection con = DBConnection.connection(); 
		
		ArrayList <Film> resultarray = new ArrayList <Film> (); 
		
		try {
			Statement stmt = con.createStatement();
			
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, beschreibung, bewertung, erstellDatum FROM film" + "ORDER BY name"); 
			
			while (resultset.next()) {
				Film f = new Film(); 
				f.setId(resultset.getInt("id")); 
				f.setName(resultset.getString("name")); 
				f.setBesitzerId(resultset.getInt("besitzerId"));
				f.setBeschreibung(resultset.getString("beschreibung"));
				f.setBewertung(resultset.getInt("bewertung"));
				f.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				
				resultarray.add(f); 
			}
		}catch (SQLException e1) {
			e1.printStackTrace();
		}return null; 
	}
	
	/**
	 * Suche nach allen Film-Objekten, die eine Eigentumsbeziehung mit einem vorgegebene Anwender haben. 
	 * @param anwender Objekt, dessen Id mit der BesitzerId der gesuchten Film-Objekte übereinstimmen soll.
	 * @return Alle Film-Objekte, die die Id des vorgegebenen Anwenders als BesitzerId in der Datenbank
	 * eingetragen haben. 
	 */
	public ArrayList<Film> findAllByAnwenderOwner(Anwender anwender) {
		Connection con = DBConnection.connection(); 
		
		ArrayList <Film> resultarray = new ArrayList <Film> (); 
		
		try {
			Statement stmt = con.createStatement(); 
			
			ResultSet resultset = stmt.executeQuery("SELECT id, name, besitzerId, beschreibung, bewertung, erstellDatum FROM film" +
					"WHERE besitzerId=" + anwender.getId() + "ORDER BY id"); 
			while (resultset.next()) { 
				Film f = new Film(); 
				f.setId(resultset.getInt("id")); 
				f.setName(resultset.getString("name")); 
				f.setBesitzerId(resultset.getInt("besitzerId"));
				f.setBeschreibung(resultset.getString("beschreibung"));
				f.setBewertung(resultset.getInt("bewertung"));
				f.setErstellDatum(resultset.getTimestamp("erstellDatum"));
				
				resultarray.add(f); 

			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultarray; 
	}
	
	
}

