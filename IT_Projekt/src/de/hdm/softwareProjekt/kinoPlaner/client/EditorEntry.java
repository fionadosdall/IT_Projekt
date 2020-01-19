package de.hdm.softwareProjekt.kinoPlaner.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.Footer;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.Header;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.Navigator;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.UmfragenAnzeigenForm;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;

/**
 * EntryPoint-Klasse des Editors. Entry point classes define
 * <code>onModuleLoad()</code>. Denn diese Klasse implementiert das Interface
 * EntryPoint, demzufolge muss auch die Methode onModuleLoad() definiert sein.
 * Die Methode wird beim Seitenaufruf zu Beginn aufgerufen.
 * 
 */
public class EditorEntry implements EntryPoint {

	Header header = new Header();
	Footer footer = new Footer();
	private KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

	public void onModuleLoad() {
		kinoplaner.getAnwenderById(1, new GetAnwenderByIdCallback());

	}

	/*
	 * Im Folgenden erstellt die onModuleLoad()-Methode Instanzen des asynchronen
	 * Interfaces KinoplanerAsync:
	 */

	private class GetAnwenderByIdCallback implements AsyncCallback<Anwender> {

		/*
		 * Wenn fehlgeschlagen: Ein neuer User soll erstellt werden --> new
		 * AnwenderErstellenCallback()
		 */
		@Override
		public void onFailure(Throwable caught) {
			kinoplaner.erstellenAnwender("Hansi Test", "testmail@test.de", new AnwenderErstellenCallback());

		}

		/*
		 * Wenn ein User eingeloggt ist, wird dieser zurückgegeben. Andernfalls soll ein
		 * neuer User erstellt werden --> new AnwenderErstellenCallback()
		 */
		@Override
		public void onSuccess(Anwender result) {
			if (result.getId() == 1) {
				kinoplaner.setAnwender(result, new SetAnwenderCallback());
				aktuellerAnwender.setAnwender(result);
			} else {
				kinoplaner.erstellenAnwender("Hansi Test", "testmail@test.de", new AnwenderErstellenCallback());
			}

		}

	}

	private class AnwenderErstellenCallback implements AsyncCallback<Anwender> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Initilanwender konnte nicht erstellt werden");
			Window.alert(caught.getMessage());

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

		/*
		 * Bei Erfolg: Der User in der Rolle als Editor eingeloggt und erhält als
		 * Startseite automatisch eine Übersicht seiner Umfragen.
		 */
		@Override
		public void onSuccess(Void result) {
			RootPanel.get("header").add(header);
			RootPanel.get("footer").add(footer);
			UmfragenAnzeigenForm uaf = new UmfragenAnzeigenForm();
			RootPanel.get("details").add(uaf);
		}

	}

	/*
	 * Diese Klasse repräsentiert den User, der aktuell im Kinoplaner eingeloggt
	 * ist. Die Klasse ist public, damit global auf sie zugegriffen werden kann
	 * (z.B. von anderen GUI Klassen)
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
