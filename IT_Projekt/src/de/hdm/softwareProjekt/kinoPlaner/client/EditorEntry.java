package de.hdm.softwareProjekt.kinoPlaner.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.EditorDashboardForm;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.Footer;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.Header;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.RegistrierungsForm;
import de.hdm.softwareProjekt.kinoPlaner.shared.LoginServiceAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;

/**
 * EntryPoint-Klasse des Editors. Entry point classes define
 * <code>onModuleLoad()</code>. Denn diese Klasse implementiert das Interface
 * EntryPoint, demzufolge muss auch die Methode onModuleLoad() definiert sein.
 * Die Methode wird beim Seitenaufruf zu Beginn aufgerufen.
 * 
 */
public class EditorEntry implements EntryPoint {

	Header header;
	Footer footer = new Footer();

	private LoginServiceAsync loginService = null;

	private VerticalPanel loginPanel = new VerticalPanel();

	private Button loginButton = new Button("Einloggen");

	private Anchor signInLink = new Anchor("Einloggen");

	private Label loginLabel = new Label(
			"Bitte mit Deinem Google-Konto anmelden, um auf den Kinoplaner zuzugreifen zu können.");

	public void onModuleLoad() {

		loginService = ClientsideSettings.getLoginService();

		loginService.login(GWT.getHostPageBaseURL() + "IT_Projekt.html", new loginServiceCallback());

	}

	private class loginServiceCallback implements AsyncCallback<Anwender> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Hier " + caught.getMessage());

		}

		@Override
		public void onSuccess(Anwender result) {
			// TODO Auto-generated method stub

			AktuellerAnwender.setAnwender(result);

			if (result.isIstEingeloggt()) {
	
				if (result.getName().equals("Null")) {
					Anchor kinoplanerEditorLink = new Anchor();
					kinoplanerEditorLink.setHref(GWT.getHostPageBaseURL() + "IT_Projekt.html");

					RootPanel.get("details").add(new RegistrierungsForm(kinoplanerEditorLink, result));

				} else {
					header =  new Header();;
					RootPanel.get("header").add(header);
					RootPanel.get("footer").add(footer);

					EditorDashboardForm edf = new EditorDashboardForm();
					RootPanel.get("details").add(edf);

				}

			} else {

				loadLogin();
			}

		}

	}

	private void loadLogin() {

		loginPanel.setSpacing(10);
		loginPanel.add(loginLabel);
		loginPanel.add(loginButton);
		loginPanel.setCellHorizontalAlignment(loginLabel, HasHorizontalAlignment.ALIGN_CENTER);
		loginPanel.setCellHorizontalAlignment(loginButton, HasHorizontalAlignment.ALIGN_CENTER);
		signInLink.setHref(AktuellerAnwender.getAnwender().getLoginUrl());

		RootPanel.get("header").setVisible(false);
		RootPanel.get("footer").setVisible(false);
		RootPanel.get("details").add(loginPanel);

		loginButton.addClickHandler(new LoginClickHandler());

	}

	private class LoginClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub

			Window.open(signInLink.getHref(), "_self", "");

		}

	}

	/*
	 * Diese Klasse repräsentiert den User, der aktuell im Kinoplaner eingeloggt
	 * ist. Die Klasse ist public, damit global auf sie zugegriffen werden kann
	 * (z.B. von anderen GUI Klassen)
	 */

	public static class AktuellerAnwender {

		public static Anwender anwender = null;

		public static Anwender getAnwender() {
			return anwender;
		}

		public static void setAnwender(Anwender anwender) {
			AktuellerAnwender.anwender = anwender;
		}

	}
}
