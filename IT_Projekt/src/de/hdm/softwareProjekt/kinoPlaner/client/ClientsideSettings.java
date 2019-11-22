package de.hdm.softwareProjekt.kinoPlaner.client;

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
	 * Admin-Clients
	 */

	// private static AdminClientAsny adminclient = null; --> Muss noch erstellt
	// werden

	public static KinoplanerAsync getKinoplaner() {

		if (kinoplaner == null) {
			// Erstellen des Proxy-Objekts durch GWT.create
			kinoplaner = GWT.create(Kinoplaner.class);
		}
		// zurückeben des Kinoplaners
		return kinoplaner;

	}
}
