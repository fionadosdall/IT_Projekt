package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
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
import de.hdm.softwareProjekt.kinoPlaner.client.EditorEntry.AktuellerAnwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;

/**
 * Der obere Bereich/Kopfbereich des Kinoplaners wird durch die Header-Klasse
 * dargestellt. Der Header wird auf jeder Seite/Ansicht im Kinoplaner identisch
 * angezeigt. Der Header stellt dem Benutzer folgende Funktionen zur Verfügung:
 * Volltextsuche, Home-Button im zurück zum Dashboard (Startseite) zu gelangen,
 * Wechsel zwischen den beiden Clients Editor und Admin, User-Button um zu
 * dessen Profil zu gelangen. Außerdem kann der Nutzer im Header ein Burger-Menü aufrufen, um schnell 
 * zu diversen Aktionen zu gelangen.
 *
 */
public class Header extends FlowPanel {
	
	Anwender aktuellerAnwender = AktuellerAnwender.getAnwender();

	KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

	private FlowPanel headerLinks = new FlowPanel();
	private FlowPanel headerRechts = new FlowPanel();
	private FlowPanel headerLogo = new FlowPanel();
	private FlowPanel headerRechtsEins = new FlowPanel();
	private FlowPanel headerRechtsZwei = new FlowPanel();		
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

	private EditorDashboardForm home;
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
		headerRechtsEins.addStyleName("headerRechtsEins");
		headerRechtsZwei.addStyleName("headerRechtsZwei");
		headerRechtsElementSuchen.addStyleName("headerRechtsElementSuchen");
		headerRechtsElementLupe.addStyleName("headerRechtsElement");
		headerRechtsElementHome.addStyleName("headerRechtsElement");
		headerRechtsElementUser.addStyleName("headerRechtsElement");

		headerImage.addStyleName("headerImage");
		suchenImage.addStyleName("suchenImage");
		homeAnchor.addStyleName("homeAnchor");
		userButton.addStyleName("userButton");

		suchenImage.setUrl("/images/suchen.png");

		suchenTextBox.addStyleName("headerTextBox");

		suchenTextBox.getElement().setPropertyString("placeholder", "Suchen...");

		// Zusammenbauen der Widgets

		this.add(headerLinks);
		this.add(headerRechts);
		
		headerRechts.add(headerRechtsEins);
		headerRechts.add(headerRechtsZwei);

		headerLinks.add(headerLogo);
		headerLogo.add(headerLogoInput);
		

		headerRechtsEins.add(headerRechtsElementHome);
		headerRechtsEins.add(headerRechtsElementUser);
		headerRechtsEins.add(headerRechtsElementSuchen);
		
		headerRechtsElementHome.add(homeAnchor);
		headerRechtsElementUser.add(userButton);
		headerRechtsElementSuchen.add(suchenTextBox);
		headerRechtsElementSuchen.add(headerImage);
		headerImage.add(suchenImage);

		
		headerRechtsZwei.add(burgerMenue);

		// Click-Handler

		homeAnchor.addClickHandler(new HomeClickHandler());
		userButton.addClickHandler(new UserFormClickHandler());
		suchenImage.addClickHandler(new SuchenClickHandler());
		suchenTextBox.addKeyPressHandler(new SuchenKeyPressHandler());

		kinoplaner.getGruppenByAnwender(aktuellerAnwender, new GetGruppenByAnwenderCallback());
		kinoplaner.getUmfragenByAnwender(aktuellerAnwender, new GetUmfragenByAnwenderCallback());
		kinoplaner.anzeigenVonClosedUmfragen(aktuellerAnwender, new AnzeigenVonClosedUmfragenCallback());

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
			home = new EditorDashboardForm();
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
			uf = new UserForm(AktuellerAnwender.getAnwender());
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
