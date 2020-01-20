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
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.DateFormaterSpielzeit;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielplan;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

public class SpielplaneintragForm extends PopupPanel {

	private KinoplanerAsync administration = ClientsideSettings.getKinoplaner();

	private VerticalPanel popupPanel = new VerticalPanel();
	private HorizontalPanel obenPanel = new HorizontalPanel();
	private HorizontalPanel untenPanel = new HorizontalPanel();

	private Grid spielplaneintragGrid = new Grid(5, 3);

	private Label spielplaneintragFormLabel = new Label("Spielplaneintrag erstellen");
	private Label spielplaneintragBearbeitenFormLabel = new Label("Spielplan bearbeiten");
	private Label filmLabel = new Label("Film");
	private Label spielzeitLabel = new Label("Spielzeit");

	private Button speichernButton = new Button("Speichern");
	private Button filmErstellenButton = new Button("Neuen Film erstellen");
	private Button spielzeitErstellenButton = new Button("Neue Spielzeit erstellen");
	private Button filmBearbeitenButton = new Button("Film bearbeiten");
	private Button spielzeitBearbeitenButton = new Button("Spielzeit bearbeiten");

	private Vorstellung vorstellung;
	private SpielplanVorstellungenCellTable svct;
	private FilmErstellenForm film;
	private SpielzeitErstellenForm spielzeit;

	private ListBox filmListBox = new ListBox();

	public  ListBox getFilmListBox() {
		return filmListBox;
	}

	private  ListBox spielzeitListBox = new ListBox();
	
	private SpielplaneintragForm eigeneForm;

	private HashMap<String, Integer> spielzeitenHastable = new HashMap<String, Integer>();

	public  ListBox getSpeilzeitListBox() {
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

		// spielplaneintragGrid.setWidget(2, 2, filmBearbeitenButton);

		spielplaneintragGrid.setWidget(3, 0, spielzeitLabel);
		spielplaneintragGrid.setWidget(3, 1, spielzeitListBox);
		spielplaneintragGrid.setWidget(3, 2, spielzeitErstellenButton);

		// spielplaneintragGrid.setWidget(4, 2, spielzeitBearbeitenButton);

		popupPanel.add(spielplaneintragGrid);

		untenPanel.add(speichernButton);
		popupPanel.add(untenPanel);

		speichernButton.addClickHandler(new SpeichernClickHandler());
		filmErstellenButton.addClickHandler(new NeuerFilmClickHandler());
		spielzeitErstellenButton.addClickHandler(new NeueSpielzeitClickHandler());

		// this.center();
		// this.setPopupPosition(30, 50);
		this.add(popupPanel);

		administration.getAllFilme(new FilmeCallback());
		administration.getAllSpielzeiten(new SpielzeitenCallback());

	}

	/*** Clickhandler ***/

	private class SpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
			Vorstellung v = new Vorstellung();

			administration.getFilmByName(filmListBox.getSelectedValue(), new GetFilmByNameCallback(v));

		}

	}

	private class NeuerFilmClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
			film = new FilmErstellenForm(eigeneForm);
			film.show();	

		}

	}
	
	public void hideFilmPopup() {
		film.hide();
	}

	private class NeueSpielzeitClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			spielzeit = new SpielzeitErstellenForm();
			spielzeit.show();

		}

	}

	/*** Callbacks ***/

	private class FilmeCallback implements AsyncCallback<ArrayList<Film>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(ArrayList<Film> result) {
			// TODO Auto-generated method stub

			if (result != null) {

				for (Film f : result) {
					filmListBox.addItem(f.getName());
				}
			} else {
				filmListBox.addItem("Keine Filme verfügbar");
				filmListBox.setEnabled(false);
			}
		}

	}

	private class SpielzeitenCallback implements AsyncCallback<ArrayList<Spielzeit>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(ArrayList<Spielzeit> result) {
			// TODO Auto-generated method stub

			if (result != null) {

				for (Spielzeit s : result) {
					DefaultDateTimeFormatInfo infoDDTFI = new DefaultDateTimeFormatInfo();
					String pattern ="EEEE dd.MM.yyyy HH:mm";
					DateTimeFormat dft = new DateTimeFormat(pattern, infoDDTFI) {};
					spielzeitListBox.addItem(dft.format(s.getZeit()));
					spielzeitenHastable.put(dft.format(s.getZeit()), s.getId());

				}
			} else {
				spielzeitListBox.addItem("Keine Spielzeiten verfügbar");
				spielzeitListBox.setEnabled(false);
			}
		}

	}

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
			svct.addVorstellung(v);
			
		}

	}

	public void refresh() {
		filmListBox.clear();
		spielzeitListBox.clear();
		administration.getAllFilme(new FilmeCallback());
		administration.getAllSpielzeiten(new SpielzeitenCallback());
		film.hide();
		spielzeit.hide();
		
	}

}
