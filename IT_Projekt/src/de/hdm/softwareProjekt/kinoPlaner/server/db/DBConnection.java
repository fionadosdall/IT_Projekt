package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 * Hier wird die Verbindung zu unserer Datenbank verwaltet. Es kann nur auf
 * unsere festgelegte Datenbank zugegriffen werden (Singleton-Eigenschaft)
 * 
 * @author annaf
 */
	public class DBConnection {
	/**
	 * Diese Klasse wird nur einmal instanziiert. Die Klassenvariable con ist
	 * static, damit sie NUR EINMAL für sämtliche Instanzen der DB Connection-Klasse
	 * vorkommt.
	 */
		private static Connection con = null;

//		private static Logger logger = ServersideSettings.getLogger();
	/**
	 * Im Folgenden sind die URLs aufgeführt, mit deren Hilfe wir die Datenbank
	 * ansprechen können.
	 */
	
		//	private static String googleURL = "jdbc:google:mysql://34.89.183.164:3306/itprojekt?user=projekt_19&password=";
		private static String localURL = "jdbc:mysql://localhost:3306/itprojekt?user=root&password=H1lfig3r!";
	
	/**
	 * Diese statische Methode wird von allen Mappern aufgerufen:
	 * <code>DBConnection.connection()</code>. Diese Methode ist Grund für die
	 * anfangs erwähnte Singleton-Eigenschaft. Sie stellt sicher, dass immer nur
	 * eine einzige Instanz dieser DBConnection-Klasse exisitiert.
	 * 
	 * Merke: DBConnection sollte nicht mittels <code>new</code>
     * instantiiert werden, sondern stets durch Aufruf dieser statischen
     * Methode.
	 * 
	 * @return Die Verbindung wird zurückgebeben
	 */

		public static Connection connection() {
		// Wenn bisher noch keine Verbindung zur DB aufgebaut wurde:
		if (con == null) {
			String url = null;
			try {
	/**			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
                    // Load the class that provides the new
                    // "jdbc:google:mysql://" prefix.
                    Class.forName("com.mysql.jdbc.GoogleDriver");
                   url = googleURL;
				} else {
	*/		
					// Local MySQL instance to use during development.
						Class.forName("com.mysql.jdbc.Driver");
						url = localURL; 
					
						
				
		/**
		 * Jetzt kann der DriverManager die Verbindung mit Hilfe der beiden angegebenen 
		 * URLs aufbauen.Die Verbindung wird in Zukunft dann in der statischen Variable 
		 * con abgespeichert und verwendet. 
		 */
						
				con = DriverManager.getConnection(url);
			} catch (Exception e) {
                  con = null;
                  	e.printStackTrace();
                  		throw new RuntimeException(e.getMessage());
              }
      }
		/**
		 * Zurückgeben der Verbindung
		 */
		return con;
	}
}
