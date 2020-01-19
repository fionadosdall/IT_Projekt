package de.hdm.softwareProjekt.kinoPlaner.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import de.hdm.softwareProjekt.kinoPlaner.client.gui.AdminDashboardForm;
import de.hdm.softwareProjekt.kinoPlaner.client.gui.Footer;
import de.hdm.softwareProjekt.kinoPlaner.client.gui.Header;
import de.hdm.softwareProjekt.kinoPlaner.client.gui.Navigator;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * Die AdminEntry Klasse implementiert das Interface Entry Point & definiert somit auch
 * die Methode onModuleLoad().
 * Die Methode wird zu Beginn des Seitenaufrufs aufgerufen
 */


public class AdminEntry implements EntryPoint {
	
	
	Header header = new Header();
	Navigator navigator = new Navigator();
	Footer footer = new Footer();
	AdminDashboardForm home = new AdminDashboardForm();
	
	private KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();
	
	public void onModuleLoad() {
		
		kinoplaner.getAnwenderById(1, new GetAnwenderByIdCallback());
		
		
	}
	
	/**Callbacks**/
	/***********************************
	 * Hier erstellt die onModuleLoad()-Methode Instanzen des asynchronen
	 * Interfaces KinoplanerAsynC
	 * @author fiona
	 *
	 */
	
	private class GetAnwenderByIdCallback implements AsyncCallback<Anwender> {

		/***************
		 * Wenn fehlgeschlagen: Ein neuer Anwender/User soll erstellt werden
		 */
		@Override
		public void onFailure(Throwable caught) {
			kinoplaner.erstellenAnwender("Hansi Test", "testmail@test.de", new AnwenderErstellenCallback());
			
		}
		
		
		/*
		 * 
		 * Wenn ein Anwender eingeloogt ist, wird dieser zurückggegeben. 
		 * Anderenfalls soll ein neuer Anwender erstellt werden
		 */

		@Override
		public void onSuccess(Anwender result) {
			if (result.getId() == 1) {
				kinoplaner.setAnwender(result, new SetAnwenderCallback());
				aktuellerAnwender.setAnwender(result);
			}else {
				kinoplaner.erstellenAnwender("Hansi Test", "testmail@test.de", new AnwenderErstellenCallback());
			}
			
		}
		
	}
	
	private class AnwenderErstellenCallback implements AsyncCallback<Anwender> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Initilanwender konnte nicht erstellt werden" + caught.getMessage());
			
		}

		@Override
		public void onSuccess(Anwender result) {
			Window.alert(result.getName());
			kinoplaner.setAnwender(result, new SetAnwenderCallback());
			aktuellerAnwender.setAnwender(result);
			
		}
		
	}
	
	private class SetAnwenderCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}
		
		/*
		 * onSucces: Anwender ist in als Editor eingeloogt
		 */

		@Override
		public void onSuccess(Void result) {
			RootPanel.get("header").add(header);
			RootPanel.get("details").add(home);
			RootPanel.get("footer").add(footer);
			
		}
		
	}
	
	
	/*
	 * Die Klasse dient jederzeit zum Aufrufen des aktuellen Anwenders
	 * Klasse ist public, damit global auf sie zugeriffen werden kann
	 * zb von anderen GUI - Klassen
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
