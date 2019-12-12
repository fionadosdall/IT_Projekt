package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Hier wird die Verbindung zu unserer Datenbank verwaltet. Es kann nur auf
 * unsere festgelegte Datenbank zugegriffen werden (Singleton-Eigenschaft)
 * 
 * @author annaf
 *
 */

public class DBConnection {

	/**
	 * Diese Klasse wird nur einmal instanziiert. Die Klassenvariable con ist
	 * static, damit sie NUR EINMAL für sämtliche Instanzen der DB Connection-Klasse
	 * vorkommt.
	 * 
	 */

	private static Connection con = null;

	/**
	 * Im Folgenden sind die URLs aufgeführt, mit deren Hilfe wir die Datenbank
	 * ansprechen können.
	 */
	private static String googleURL = null;
	private static String localURL = "jdbc:mysql://127.0.0.1:3306/itProjekt?user=root&password=H1lfig3r!";

	/**
	 * Diese statische Methode wird von allen Mappern aufgerufen:
	 * <code>DBConnection.connection()</code>. Diese Methode ist Grund für die
	 * eingangs erwähnte Singleton-Eigenschaft. Sie stellt sicher, dass immer nur
	 * eine einzige Instanz dieser DBConnection-Klasse exisitiert.
	 * 
	 * @return Die Verbindung wird zurückgebeben
	 */

	public static Connection connection() {
		// Wenn bisher noch keine Verbindung zur DB aufgebaut wurde:
//        if (con == null) {
//            String url = null;
//            try {
//                
//                } else {
//                    
//                }
		/**
		 * Jetzt kann der DriverManager die Verbindung mit Hilfe der beiden angegebenen 
		 * URLs aufbauen.
		 */
//                con = DriverManager.getConnection(url);
//            } catch (Exception e) {
//                con = null;
//                e.printStackTrace();
//                throw new RuntimeException(e.getMessage());
//            }catch (Exception e) {
//            con = null;
//            e.printStackTrace();
//            throw new RuntimeException(e.getMessage());
//        }
//        }
		/**
		 * Zurückgeben der Verbindung
		 */
		return con;
	}
}
