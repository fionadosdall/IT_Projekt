package de.hdm.softwareProjekt.kinoPlaner.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.gui.Footer;
import de.hdm.softwareProjekt.kinoPlaner.client.gui.Header;
import de.hdm.softwareProjekt.kinoPlaner.client.gui.Navigator;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */


public class AdminEntry implements EntryPoint {
	
	
	Header header = new Header();
	Navigator navigator = new Navigator();
	Footer footer = new Footer();
	
	private KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();
	
	public void onModuleLoad() {
		
		//kinoplaner.erstellenAnwender("Hansi Test", "testmail@test.de", new AnwenderErstellenCallback());

		RootPanel.get("header").add(header);
		RootPanel.get("navigator").add(navigator);
		RootPanel.get("footer").add(footer);
		
		
	}
	
	/**Callbacks**/
	
	private class AnwenderErstellenCallback implements AsyncCallback<Anwender> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Initilanwender konnte nicht erstellt werden");
			
		}

		@Override
		public void onSuccess(Anwender result) {
			kinoplaner.setAnwender(result, new SetAnwenderCallback());
			aktuellerAnwender.setAnwender(result);
			
		}
		
	}
	
	private class SetAnwenderCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Void result) {
			RootPanel.get("header").add(header);
			RootPanel.get("navigator").add(navigator);
			RootPanel.get("footer").add(footer);
			
		}
		
	}
	
	
	/*
	 * Die Klasse dient jederzeit zum Aufrufen des aktuellen Anwenders
	 */

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
