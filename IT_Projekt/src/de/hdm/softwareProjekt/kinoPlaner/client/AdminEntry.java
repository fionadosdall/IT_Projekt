package de.hdm.softwareProjekt.kinoPlaner.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.gui.Footer;
import de.hdm.softwareProjekt.kinoPlaner.client.gui.Header;
import de.hdm.softwareProjekt.kinoPlaner.client.gui.Navigator;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;


public class AdminEntry implements EntryPoint {
	
	
	Header header = new Header();
	Navigator navigator = new Navigator();
	Footer footer = new Footer();

	
	public void onModuleLoad() {
		
		RootPanel.get("header").add(header);
		RootPanel.get("navigator").add(navigator);
		RootPanel.get("footer").add(footer);
		
	}
	
	

	public static class aktuellerAnwender {
		
		public static Anwender anwender = null;
		
		public static Anwender getAnwender() {
			return anwender;
		}
		
		public static void setAnwender(Anwender anwender) {
			aktuellerAnwender.anwender = anwender;
		}
		
	}


}
