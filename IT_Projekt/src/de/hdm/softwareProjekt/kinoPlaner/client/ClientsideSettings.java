package de.hdm.softwareProjekt.kinoPlaner.client;

//import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;

import de.hdm.softwareProjekt.kinoPlaner.shared.CommonSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.Kinoplaner;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
/*
 * Klasse dient zur Bereitstellung aller Eigenschaften und Dienste 
 * für die Client Seite
 */

public class ClientsideSettings extends CommonSettings {

	/*
	 * Remote Service Proxy zur Verbindung mit dem Server-Seitgen Dienst des Editors
	 */

	private static KinoplanerAsync kinoplaner = null;

	/*
	 * Remote Service Proxy zur Verbindung mit dem Server-Seitgen Dienst des
	 * Login
	 */

//	private static LoginServiceAsync loginService = null:
//		
//	//Client-Seitig
//		
//	private static final String LOGGER_NAME = "Kinoplaner Web Client";
//	
//	//Client-Seitig
//	
//	private static final Logger log = Logger.getLogger(LOGGER_NAME);
//	
//	/*
//	 * Auslesen des zentralen eingeloggten Anwenders
//	 */
//	
//	public static Logger getLogger() {
//		return log;
//	}
//
//	/*
//	 * Die Methode ermöglicht das erstellen einer Instant der Klasse LoginService
//	 */
//	public static LoginServiceAsync getLoginService() {
//		if (loginService == null) {
//			loginService = GWT.create(LoginService.class);
//		}
//		return loginService;
//	}
	
	

	public static KinoplanerAsync getKinoplaner() {

		if (kinoplaner == null) {
			// Erstellen des Proxy-Objekts durch GWT.create
			kinoplaner = GWT.create(Kinoplaner.class);
		}
		// zurückeben des Kinoplaners
		return kinoplaner;

	}
}
