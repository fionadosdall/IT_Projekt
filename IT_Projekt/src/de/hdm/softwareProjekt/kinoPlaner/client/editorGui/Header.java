package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.client.EditorEntry.aktuellerAnwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;

/**
 * Der obere Bereich/Kopfbereich des Kinoplaners wird durch die Header-Klasse
 * dargestellt. Der Header wird auf jeder Seite/Ansicht im Kinoplaner identisch
 * angezeigt. Der Header stellt dem Benutzer folgende Funktionen zur Verfügung:
 * Volltextsuche, Home-Button im zurück zum Dashboard (Startseite) zu gelangen,
 * Wechsel zwischen den beiden Clients Editor und Admin, User-Button um zu
 * dessen Profil zu gelangen.
 *
 */
public class Header extends FlowPanel {

	KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

	private FlowPanel headerLinks = new FlowPanel();
	private FlowPanel headerRechts = new FlowPanel();
	private FlowPanel headerLogo = new FlowPanel();
	private FlowPanel headerRechtsElementSuchen = new FlowPanel();
	private FlowPanel headerRechtsElementLupe = new FlowPanel();
	private FlowPanel headerRechtsElementHome = new FlowPanel();
	private FlowPanel headerRechtsElementUser = new FlowPanel();
	private FlowPanel headerImage = new FlowPanel();

	private Label headerLogoInput = new Label("K I N O P L A N E R");

	private MultiWordSuggestOracle alleDaten = new MultiWordSuggestOracle();
	private SuggestBox suchenTextBox = new SuggestBox(alleDaten);

	private Image suchenImage = new Image();
	private Anchor homeAnchor = new Anchor("HOME");
	private Button userButton = new Button("USER");
	private BurgerMenue burgerMenue = new BurgerMenue();

	private Home home;
	private UserForm uf;
	private VolltextSucheForm vsf;

	/**
	 * onLoad()-Methode: Die Widgets werden dem Header hinzugefügt und formatiert.
	 */
	public void onLoad() {

		// Stylenamen vergeben

		this.addStyleName("headerGesamt");

		headerLinks.addStyleName("headerLinks");
		headerLogo.addStyleName("headerLogo");
		headerLogoInput.addStyleName("headerLogoInput");

		headerRechts.addStyleName("headerRechts");
		headerRechtsElementSuchen.addStyleName("headerRechtsElement");
		headerRechtsElementLupe.addStyleName("headerRechtsElement");
		headerRechtsElementHome.addStyleName("headerRechtsElement");
		headerRechtsElementUser.addStyleName("headerRechtsElement");

		headerImage.addStyleName("headerImage");
		suchenImage.addStyleName("suchenImage");
		homeAnchor.addStyleName("homeAnchor");
		userButton.addStyleName("userButton");

		suchenImage.setUrl("/images/suchen.png");

		suchenTextBox.addStyleName("nameTextBox");

		suchenTextBox.getElement().setPropertyString("placeholder", "Suchen...");

		// Zusammenbauen der Widgets

		this.add(headerLinks);
		this.add(headerRechts);

		headerLinks.add(headerLogo);
		headerLogo.add(headerLogoInput);

		headerRechts.add(headerRechtsElementSuchen);
		headerRechts.add(headerRechtsElementLupe);
		headerRechts.add(headerRechtsElementHome);
		headerRechts.add(headerRechtsElementUser);

		headerRechtsElementSuchen.add(suchenTextBox);
		headerRechtsElementLupe.add(headerImage);
		headerImage.add(suchenImage);

		headerRechtsElementHome.add(homeAnchor);
		headerRechtsElementUser.add(userButton);
		headerRechts.add(burgerMenue);

		// Click-Handler

		homeAnchor.addClickHandler(new HomeClickHandler());
		userButton.addClickHandler(new UserFormClickHandler());
		suchenImage.addClickHandler(new SuchenClickHandler());
		suchenTextBox.addKeyPressHandler(new SuchenKeyPressHandler());

		// kinoplaner.getGruppenByAnwender(new GetGruppenByAnwenderCallback());

		kinoplaner.getUmfragenByAnwender(new GetUmfragenByAnwenderCallback());
		// kinoplaner.anzeigenVonClosedUmfragen(new
		// AnzeigenVonClosedUmfragenCallback());

	}

	/***********************************************************************
	 * CLICKHANDLER
	 ***********************************************************************/
	/**
	 * ClickHandler für den Suchen-Button (dargestellt durch eine Lupe)
	 *
	 */
	private class SuchenKeyPressHandler implements KeyPressHandler {

		@Override
		public void onKeyPress(KeyPressEvent event) {
			if (event.getCharCode() == KeyCodes.KEY_ENTER) {
				RootPanel.get("details").clear();
				vsf = new VolltextSucheForm(suchenTextBox.getText());
				RootPanel.get("details").add(vsf);
			}

		}

	}

	/**
	 * Click-Handler um beim Klicken auf den Homebutton wieder zurück zur Startseite
	 * zu gelangen, dem Dashboard
	 * 
	 *
	 */
	private class HomeClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			home = new Home();
			RootPanel.get("details").add(home);

		}

	}

	/**
	 * ClickHandler, um den Benutzer durch Klicken des User-Buttons auf sein Profil
	 * weiterzuleiten. Im User-Profil wird angezeigt welcher Benutzer eingeloggt ist
	 * und man kann sich ausloggen.
	 * 
	 *
	 */
	private class UserFormClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			uf = new UserForm(aktuellerAnwender.getAnwender());
			RootPanel.get("details").add(uf);

		}

	}

	/**
	 * ClickHandler für die Volltext-Suche
	 *
	 */
	private class SuchenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			vsf = new VolltextSucheForm(suchenTextBox.getText());
			RootPanel.get("details").add(vsf);

		}

	}

	/***********************************************************************
	 * CALLBACKS
	 ***********************************************************************/
	/**
	 * Alle Gruppen sollen ausgegeben werden, zu denen der eingeloggte Anwender/User
	 * gehört.So sieht der User auf der Startseite ein Dashboard über alle für ihn
	 * relevanten Business Objekte, darunter auch seine Gruppen.
	 *
	 */
	private class GetGruppenByAnwenderCallback implements AsyncCallback<ArrayList<Gruppe>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(ArrayList<Gruppe> result) {
			for (Gruppe g : result) {
				alleDaten.add(g.getName());
			}

		}

	}

	/**
	 * Alle Umfragen sollen ausgegeben werden, zu denen der eingeloggte
	 * Anwender/User gehört. So sieht der User auf der Startseite ein Dashboard über
	 * alle für ihn relevanten Business Objekte, darunter auch seine Umfragen.
	 *
	 */
	private class GetUmfragenByAnwenderCallback implements AsyncCallback<ArrayList<Umfrage>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(ArrayList<Umfrage> result) {
			for (Umfrage u : result) {
				alleDaten.add(u.getName());
			}

		}

	}

	/**
	 * Alle geschlossenen Umfragen sollen ausgegeben werden, zu denen der
	 * eingeloggte Anwender/User gehört. So sieht der User auf der Startseite ein
	 * Dashboard über alle für ihn relevanten Business Objekte, darunter auch seine
	 * geschlossenen Umfragen.
	 *
	 */
	private class AnzeigenVonClosedUmfragenCallback implements AsyncCallback<ArrayList<Umfrage>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(ArrayList<Umfrage> result) {
			for (Umfrage e : result) {
				alleDaten.add(e.getName());
			}

		}

	}
}
