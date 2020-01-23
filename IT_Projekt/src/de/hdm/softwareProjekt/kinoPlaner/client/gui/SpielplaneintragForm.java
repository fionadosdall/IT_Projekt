package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.i18n.shared.DefaultDateTimeFormatInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.Kinoplaner;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;


/*
 * Klasse stellt das Formular um ein Spielplaneintrag zu erstellen
 */
public class SpielplaneintragForm extends PopupPanel {

	private KinoplanerAsync administration = ClientsideSettings.getKinoplaner();

	private VerticalPanel popupPanel = new VerticalPanel();
	private HorizontalPanel obenPanel = new HorizontalPanel();
	private HorizontalPanel untenPanel = new HorizontalPanel();

	private Grid spielplaneintragGrid = new Grid(7, 3);

	private Label spielplaneintragFormLabel = new Label("Spielplaneintrag erstellen");
	private Label spielplaneintragBearbeitenFormLabel = new Label("Spielplan bearbeiten");
	private Label filmLabel = new Label("Film");
	private Label spielzeitLabel = new Label("Spielzeit");

	private Button speichernButton = new Button("Speichern");
	private Button aenderungSpeichernButton = new Button("Änderung speichern");
	private Button closeButton = new Button("Schließen");
	private Button filmErstellenButton = new Button("Neuen Film erstellen");
	private Button spielzeitErstellenButton = new Button("Neue Spielzeit erstellen");
	private Button filmBearbeitenButton = new Button("Film bearbeiten");
	private Button spielzeitBearbeitenButton = new Button("Spielzeit bearbeiten");
	private Button filmLoeschenButton = new Button("Film löschen");
	private Button spielzeitLoeschenButton = new Button("Spielzeit löschen");

	private Vorstellung vorstellung;
	private SpielplanVorstellungenCellTable svct;
	private FilmErstellenForm film;
	private SpielzeitErstellenForm spielzeit;

	private FilmErstellenForm erstellen;

	private Film filmB;

	private ListBox filmListBox = new ListBox();

	public ListBox getFilmListBox() {
		return filmListBox;
	}

	private ListBox spielzeitListBox = new ListBox();

	private SpielplaneintragForm eigeneForm;

	private HashMap<String, Integer> spielzeitenHastable = new HashMap<String, Integer>();
	private HashMap<Integer, String> spielzeitenHastable2 = new HashMap<Integer, String>();

	public ListBox getSpeilzeitListBox() {
		return spielzeitListBox;
	}

	public SpielplaneintragForm(SpielplanVorstellungenCellTable svct) {
		super(true);
		this.svct = svct;

	}

	public SpielplaneintragForm(SpielplanVorstellungenCellTable svct, Vorstellung vorstellung) {
		super(true);
		this.svct = svct;
		this.vorstellung = vorstellung;
	}

	public void onLoad() {

		/* Setzen der Style-Namen */

		this.addStyleName("center");
		this.addStyleName("detailscontainer");
		this.addStyleName("popupPanel");

		eigeneForm = this;

		spielplaneintragFormLabel.addStyleName("formHeaderLabel");
		spielplaneintragBearbeitenFormLabel.addStyleName("formHeaderLabel");
		filmLabel.addStyleName("textLabel");
		spielzeitLabel.addStyleName("textLabel");
		obenPanel.addStyleName("popupObenPanel");
		untenPanel.addStyleName("popupUntenPanel");
		speichernButton.addStyleName("speichernButton");
		aenderungSpeichernButton.addStyleName("speichernButton");
		closeButton.addStyleName("entfernenButton");
		filmLoeschenButton.addStyleName("spielplaneintragLoeschenButton");
		spielzeitLoeschenButton.addStyleName("spielplaneintragLoeschenButton");
		filmErstellenButton.addStyleName("spielplaneintragSpeichernButton");
		filmBearbeitenButton.addStyleName("bearbeitenButton");
		spielzeitErstellenButton.addStyleName("spielplaneintragSpeichernButton");
		spielzeitBearbeitenButton.addStyleName("bearbeitenButton");

		spielzeitListBox.setSize("180px", "25px");
		filmListBox.setSize("180px", "25px");

		/*** Zusammensetzen der Widgets ***/

		if (vorstellung != null) {

			obenPanel.add(spielplaneintragBearbeitenFormLabel);
		} else {
			obenPanel.add(spielplaneintragFormLabel);
			// clearForm();
		}

		popupPanel.add(obenPanel);

		spielplaneintragGrid.setWidget(1, 0, filmLabel);
		spielplaneintragGrid.setWidget(1, 1, filmListBox);
		spielplaneintragGrid.setWidget(1, 2, filmErstellenButton);

		spielplaneintragGrid.setWidget(2, 2, filmBearbeitenButton);
		spielplaneintragGrid.setWidget(3, 2, filmLoeschenButton);

		spielplaneintragGrid.setWidget(4, 0, spielzeitLabel);
		spielplaneintragGrid.setWidget(4, 1, spielzeitListBox);
		spielplaneintragGrid.setWidget(4, 2, spielzeitErstellenButton);

		spielplaneintragGrid.setWidget(5, 2, spielzeitBearbeitenButton);
		spielplaneintragGrid.setWidget(6, 2, spielzeitLoeschenButton);

		popupPanel.add(spielplaneintragGrid);

		if(vorstellung != null) {
			untenPanel.add(aenderungSpeichernButton);
		}else {
			untenPanel.add(speichernButton);
		}
		

		popupPanel.add(untenPanel);

		closeButton.addClickHandler(new CloseClickHandler());
		speichernButton.addClickHandler(new SpeichernClickHandler());
		aenderungSpeichernButton.addClickHandler(new SpeichernClickHandler());
		filmErstellenButton.addClickHandler(new NeuerFilmClickHandler());
		spielzeitErstellenButton.addClickHandler(new NeueSpielzeitClickHandler());
		filmBearbeitenButton.addClickHandler(new FilmBearbeitenClickHandler());
		spielzeitBearbeitenButton.addClickHandler(new SpielzeitBearbeitenClickHandler());
		spielzeitLoeschenButton.addClickHandler(new SpielzeitLoeschenClickHandler());
		filmLoeschenButton.addClickHandler(new FilmLoeschenClickHandler());

		// this.center();
		// this.setPopupPosition(30, 50);
		this.add(popupPanel);

		administration.getAllFilme(new FilmeCallback());
		administration.getAllSpielzeiten(new SpielzeitenCallback());

	}
	/*
	 * Vor dem Löschen eines Films, soll der Nutzer über eine Dialbox noch einmal
	 * um Bestätigung des Löschvorgangs gebeten werden
	 */
	
	private class FilmLoeschenDialogBox extends DialogBox {
		
		private VerticalPanel verticalPanel = new VerticalPanel();
		private HorizontalPanel buttonPanel = new HorizontalPanel();
		
		private Label nachfrage = new Label ("Film endgültig löschen?");
		
		private Button jaButton = new Button ("Ja");
		private Button neinButton = new Button ("Nein");
		
		// Konstruktor
		
		public FilmLoeschenDialogBox() {
			
			nachfrage.addStyleName("Abfrage");
			jaButton.addStyleName("buttonAbfrage");
			neinButton.addStyleName("buttonAbfrage");

			buttonPanel.add(jaButton);
			buttonPanel.add(neinButton);
			verticalPanel.add(nachfrage);
			verticalPanel.add(buttonPanel);

			this.add(verticalPanel);
			
			// ClickHandler für FilmLöschenDialogBox
			
			jaButton.addClickHandler(new FilmLoeschenBestaetigenClickHandler(this));
			neinButton.addClickHandler(new FilmLoeschenAbbrechenCLickHandler(this));
			
		}
	}
	
	
	/*Vor dem Löschen der Spielzeit, soll der Nutzer über eine Dialbox noch einmal
	 * um Bestätigung des Löschvorgangs gebeten werden
	*/

	private class SpielzeitLoeschenDialogBox extends DialogBox {
		
		private VerticalPanel verticalPanel = new VerticalPanel();
		private HorizontalPanel buttonPanel = new HorizontalPanel();
		
		private Label nachfrage = new Label ("Spielzeit endgültig löschen?");
		
		private Button jaButton = new Button ("Ja");
		private Button neinButton = new Button ("Nein");
		
		// Konstruktor
		
		public SpielzeitLoeschenDialogBox() {
			
			nachfrage.addStyleName("Abfrage");
			jaButton.addStyleName("buttonAbfrage");
			neinButton.addStyleName("buttonAbfrage");

			buttonPanel.add(jaButton);
			buttonPanel.add(neinButton);
			verticalPanel.add(nachfrage);
			verticalPanel.add(buttonPanel);

			this.add(verticalPanel);
			
			// ClickHandler für SpielzeitLöschenDialogBox
			
			jaButton.addClickHandler(new SpielzeitLoeschenBestaetigenClickHandler(this));
			neinButton.addClickHandler(new SpielzeitLoeschenAbbrechenClickHandler(this));
			
		}
	}
		
	
	// Erstellen ClickHandler für Löschen- und AbbrechenClickHandler
	
	/*
	 * Wenn der Nutzer den angezeigten Film löschen möchte, kann er dies über den
	 * Lösch-Button tun. Dabei öffent sich automatisch die DialogBox. Diese
	 * bittent den Nutzer, erneut zu bestätigen, dass er den Film löschen möchte
	 */
	
	private class FilmLoeschenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			

			FilmLoeschenDialogBox filmLoeschenDB = new FilmLoeschenDialogBox();
			filmLoeschenDB.center();
			
		}

	}
	

	
	
	
	//ClickHandler Film löschen DIalogBox
	/*
	 * ClickHandler zur Lösch-Bestätigung des Films
	 */
	
	private class FilmLoeschenBestaetigenClickHandler implements ClickHandler {
		
		private FilmLoeschenDialogBox filmLoeschenDB;
		
		public FilmLoeschenBestaetigenClickHandler(FilmLoeschenDialogBox filmLoeschenDB) {
			this.filmLoeschenDB = filmLoeschenDB;
			
		}

		@Override
		public void onClick(ClickEvent event) {
			administration.getFilmByName(filmListBox.getSelectedValue(), new FilmGetNameFueLoeschenCallback());
			filmLoeschenDB.hide();

			
		}
		
	}
	
	/*
	 * ClickHandler um das Löschen des Films abzubrechen
	 */
	
	private class FilmLoeschenAbbrechenCLickHandler implements ClickHandler {
		
		private FilmLoeschenDialogBox filmLoeschenDB;

		public FilmLoeschenAbbrechenCLickHandler(FilmLoeschenDialogBox filmLoeschenDialogBox) {
			this.filmLoeschenDB = filmLoeschenDialogBox;
		}

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			filmLoeschenDB.hide();
		}
		
	}
	
	
	
	/*** Clickhandler für Spielzeit löschen ***/
	
	
/*
 * Wenn der Nutzer die angezeigte Spielzeit löschen möchte, kann er dies
 * über den Lösch-Button tun. Dabei öffnet sich automatisch die DialogBox. Diese
 * bittet den Nutzer erneut zu bestätigen, dass er die Spielzeit löschen möchte
 */

	private class SpielzeitLoeschenClickHandler implements ClickHandler {
		
		@Override
		public void onClick(ClickEvent event) {
			
			SpielzeitLoeschenDialogBox spielzeitLoeschenDB = new SpielzeitLoeschenDialogBox();
			spielzeitLoeschenDB.center();

		}

	}
	
	/**ClickHandler für SpielzeitLöschen DialogBox **/
	/*
	 * ClickHandler zur Lösch-Bestätigung der Spielzeit
	*/
	
	private class SpielzeitLoeschenBestaetigenClickHandler implements ClickHandler {
		
		private SpielzeitLoeschenDialogBox spielzeitLoeschenDB;
		
		public SpielzeitLoeschenBestaetigenClickHandler(SpielzeitLoeschenDialogBox spielzeitLoeschenDB) {
			this.spielzeitLoeschenDB = spielzeitLoeschenDB;
		}

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			administration.getSpielzeitById(spielzeitenHastable.get(spielzeitListBox.getSelectedValue()),
					new SpielzeitByIdFuerLoeschenCallback());
			spielzeitLoeschenDB.hide();
		}
		
	}
	
	/*
	 * ClickHandler um das Löschen der Spielzeit abzubrechen
	 */
	
private class SpielzeitLoeschenAbbrechenClickHandler implements ClickHandler {
		
		private SpielzeitLoeschenDialogBox spielzeitLoeschenDB;
		
	
			
			public SpielzeitLoeschenAbbrechenClickHandler(SpielzeitLoeschenDialogBox spielzeitLoeschenDB) {
				this.spielzeitLoeschenDB = spielzeitLoeschenDB;
			
		}

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
			spielzeitLoeschenDB.hide();
			
		}
		
	}

	/* 
	 * ClickHandler um das Fenster des Spielplaneintrags zu schließen
	 */
	

	private class CloseClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			eigeneForm.hide();

		}

	}

	
	/*
	 *ClickHandler um die Spielzeit zu bearbeiten 
	 */
	private class SpielzeitBearbeitenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {			
			
			administration.getSpielzeitById(spielzeitenHastable.get(spielzeitListBox.getSelectedValue()),
					new SpielzeitByIDCallback());

		}

	}
	
	/*
	 * ClickHandler  um den Spielplaneintrag zu speichern
	 */

	private class SpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (vorstellung == null) {
				Vorstellung v = new Vorstellung();

				administration.getFilmByName(filmListBox.getSelectedValue(), new GetFilmByNameCallback(v));
			} else {
				administration.getFilmByName(filmListBox.getSelectedValue(), new GetFilmByNameCallback(vorstellung));
			}
			
			SpielplaneintragForm.this.hide();
		}
		
	

	}
	
	/*
	 * ClickHandler um einen neuen Film in Spielplaneintrag anzulegen
	 */

	private class NeuerFilmClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			film = new FilmErstellenForm(eigeneForm);
			film.show();

		}

	}

	/*
	 * ClickHandler um den Film des Spielplaneintrags zu bearbeiten
	 */
	private class FilmBearbeitenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {


			administration.getFilmByName(filmListBox.getSelectedValue(), new FilmByNameCallback());

		}

	}

	public void hideFilmPopup() {
		film.hide();
	}
	
	
	/*
	 * ClickHandler um eine neue Spielzeit im Spielplaneintrag anzulegenn
	 */

	private class NeueSpielzeitClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			spielzeit = new SpielzeitErstellenForm(eigeneForm);

			spielzeit.show();

		}

	}

	/*** *******************************************************
	 * Callbacks
	 * ********************************************
	 *  ***/
	
	
	/*
	 * Callback Klasse zur Abfrage des ausgewählten Spielzeit Objekts mit der ID, um dann
	 * die Spielzeit zu löschen
	 */

	private class SpielzeitByIdFuerLoeschenCallback implements AsyncCallback<Spielzeit> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();
		}

		@Override
		public void onSuccess(Spielzeit result) {
			administration.loeschen(result, new SpielzeitLoeschenCalback());

		}

	}
	
	/*
	 * Callback Klasse zur Löschung einer Spielzeit-Instanz im System
	 */

	private class SpielzeitLoeschenCalback implements AsyncCallback<Boolean> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(Boolean result) {
			if (result == true) {
				spielzeitRefresh();
				Systemmeldung.anzeigen("Spielzeit wurde gelöscht.");
			} else {
				Systemmeldung.anzeigen("Löschen der Spielzeit nicht möglich, wird verwendet.");
			}

		}

	}
	
	/*
	 * Callback Klasse zur Abfrage des ausgewählen Filmobjekts mit dem Namen,
	 * um dann den Film zu löschen
	 */
	
	

	private class FilmGetNameFueLoeschenCallback implements AsyncCallback<Film> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(Film result) {
			administration.loeschen(result, new FilmLoeschenCallback());

		}

	}

	private class FilmLoeschenCallback implements AsyncCallback<Boolean> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(Boolean result) {
			if (result == true) {
				refresh();
				Systemmeldung.anzeigen("Film wurde gelöscht.");
			} else {
				Systemmeldung.anzeigen("Löschen des Films nicht möglich, wird verwendet.");
			}

		}

	}
	
	/*
	 * Callback um alle Film-Instanzen aus dem System aufzurufen
	 */

	private class FilmeCallback implements AsyncCallback<ArrayList<Film>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(ArrayList<Film> result) {

			int indexSelected = 0;
			int counter = 0;

			if (result.size() != 0) {

				for (Film f : result) {

					filmListBox.addItem(f.getName());

					if (vorstellung != null) {
						if (f.getId() == vorstellung.getFilmId()) {
							indexSelected = counter;

						} else {
							counter++;
						}
					}

				}
				if (vorstellung != null) {
					filmListBox.setSelectedIndex(indexSelected);
				}

			} else {

				filmListBox.addItem("Kein Kino verfügbar");
				filmListBox.setEnabled(false);

			}

		}

	}
	
	/*
	 * Callback Klasse um SpielzeitObjekte, anhand der ID aus dem System
	 * aufzurufen
	 */

	private class SpielzeitByIDCallback implements AsyncCallback<Spielzeit> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(Spielzeit result) {
			spielzeit = new SpielzeitErstellenForm(eigeneForm, result);

			if (result != null) {
				spielzeit.show();
			}

		}

	}
	
	/*
	 * Callback Abfrage um FilmObjekte anhand des Names aus dem System 
	 * abzufragen
	 */

	private class FilmByNameCallback implements AsyncCallback<Film> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(Film result) {
			// TODO Auto-generated method stub

			erstellen = new FilmErstellenForm(eigeneForm, result);

			if (result != null) {

				erstellen.show();

			}
		}

	}
	
	/*
	 * Callback um Spielzeiten aus dem System abzufragen
	 */

	private class SpielzeitenCallback implements AsyncCallback<ArrayList<Spielzeit>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(ArrayList<Spielzeit> result) {
			int indexSelected = 0;
			int counter = 0;

			if (result.size() != 0) {

				for (Spielzeit s : result) {
					DefaultDateTimeFormatInfo infoDDTFI = new DefaultDateTimeFormatInfo();
					String pattern = "EEEE dd.MM.yyyy HH:mm";
					DateTimeFormat dft = new DateTimeFormat(pattern, infoDDTFI) {
					};
					spielzeitListBox.addItem(dft.format(s.getZeit()));
					spielzeitenHastable.put(dft.format(s.getZeit()), s.getId());
					spielzeitenHastable2.put(s.getId(), dft.format(s.getZeit()));

					if (vorstellung != null) {
						if (s.getId() == vorstellung.getSpielzeitId()) {
							indexSelected = counter;

						} else {
							counter++;
						}
					}

				}
				if (vorstellung != null) {
					spielzeitListBox.setSelectedIndex(indexSelected);
				}

			} else {

				spielzeitListBox.addItem("Keine Spielzeiten verfügbar");
				spielzeitListBox.setEnabled(false);

			}

		}

	}
	
	/*
	 * Callback abfrage um FilmObjekte aufgrund des Namens aus dem System
	 * abzufragen
	 */

	private class GetFilmByNameCallback implements AsyncCallback<Film> {

		Vorstellung v;

		public GetFilmByNameCallback(Vorstellung v) {
			this.v = v;
		}

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(Film result) {

			v.setFilmId(result.getId());

			administration.getSpielzeitById(spielzeitenHastable.get(spielzeitListBox.getSelectedValue()),
					new GetSpielzeitByIdCallback(v));

		}

	}
	
	/*
	 * Callback um SpielzeitObjekte augrund der Id aus dem
	 * System abzufragen
	 */

	private class GetSpielzeitByIdCallback implements AsyncCallback<Spielzeit> {

		Vorstellung v;

		public GetSpielzeitByIdCallback(Vorstellung v) {
			this.v = v;
		}

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(Spielzeit result) {
			v.setSpielzeitId(result.getId());
			if (vorstellung == null) {

				v.setId(0);
				svct.addVorstellung(v);
			} else {

				svct.updateVorstellung(v, eigeneForm);

			}

		}

	}

	public void refresh() {
		filmListBox.clear();

		administration.getAllFilme(new FilmeCallback());

		film.hide();

	}

	public void spielzeitRefresh() {
		spielzeitListBox.clear();
		administration.getAllSpielzeiten(new SpielzeitenCallback());
		spielzeit.hide();

	}

}
