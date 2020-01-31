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

import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.RegistrierungsForm;
import de.hdm.softwareProjekt.kinoPlaner.client.gui.AdminDashboardForm;
import de.hdm.softwareProjekt.kinoPlaner.client.gui.Footer;
import de.hdm.softwareProjekt.kinoPlaner.client.gui.Header;
import de.hdm.softwareProjekt.kinoPlaner.shared.LoginServiceAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;

/**
 * Entry point classes define <code>onModuleLoad()</code>. Die AdminEntry Klasse
 * implementiert das Interface Entry Point & definiert somit auch die Methode
 * onModuleLoad(). Die Methode wird zu Beginn des Seitenaufrufs aufgerufen
 */

public class AdminEntry implements EntryPoint {

	Header header;
	Footer footer = new Footer();
	AdminDashboardForm home = new AdminDashboardForm();

	private LoginServiceAsync loginService = null;

	private VerticalPanel loginPanel = new VerticalPanel();

	private Button loginButton = new Button("Einloggen");

	private Anchor signInLink = new Anchor("Einloggen");

	private Label loginLabel = new Label(
			"Bitte mit Deinem Google-Konto anmelden, um auf den Kinoplaner zuzugreifen zu können.");


	public void onModuleLoad() {

		loginService = ClientsideSettings.getLoginService();

		loginService.login(GWT.getHostPageBaseURL() + "AdminClient.html", new loginServiceCallback());

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
					kinoplanerEditorLink.setHref(GWT.getHostPageBaseURL() + "AdminClient.html");

					RootPanel.get("details").add(new RegistrierungsForm(kinoplanerEditorLink, result));

				} else {
					header  = new Header();
					RootPanel.get("header").add(header);
					RootPanel.get("details").add(home);
					RootPanel.get("footer").add(footer);

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
	 * Die Klasse dient jederzeit zum Aufrufen des aktuellen Anwenders Klasse ist
	 * public, damit global auf sie zugeriffen werden kann zb von anderen GUI -
	 * Klassen
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
