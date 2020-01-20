package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

/****
 * Formular für das Anlegen einer neuen Umfrage im  Datenstamm
 * 
 *
 */
public class UmfrageErstellenForm extends FlowPanel {

	KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();
	private FlowPanel detailsObenBox = new FlowPanel();
	private FlowPanel detailsMitteBox = new FlowPanel();
	private FlowPanel detailsBoxObenMitte = new FlowPanel();
	private FlowPanel detailsBoxMitteMitte = new FlowPanel();
	private FlowPanel detailsBoxUmfrage = new FlowPanel();
	private FlowPanel detailsBoxFiltern = new FlowPanel();
	private FlowPanel detailsBoxSpeichern = new FlowPanel();
	private FlowPanel filternBox = new FlowPanel();
	private FlowPanel filternBoxLinks = new FlowPanel();
	private FlowPanel filternBoxMitte = new FlowPanel();
	private FlowPanel filternBoxRechts = new FlowPanel();

	private Label title = new Label("Umfrage erstellen");
	private Label umfrageLabel = new Label("Umfrage");
	private Label gruppenLabel = new Label("Gruppe");
	private Label terminLabel = new Label("Mögliche Termine");
	private Label filternLabel = new Label("Termine Filtern");
	private Label kinoLabel = new Label("Kino");
	private Label SpiezeitLabel = new Label("Spielzeit");
	private Label filmLabel = new Label("Filme");

	
	private TextBox umfrageTextBox = new TextBox();

	private ListBox gruppenListBox = new ListBox();
	private ListBox kinoListBox = new ListBox();
	private ListBox spielzeitListBox = new ListBox();
	private ListBox filmListBox = new ListBox();

	private Button erstellenButton = new Button("Umfrage starten");
	private Button filternButton = new Button("Filtern");

	private ArrayList<Gruppe> gruppen;
	private ArrayList<Kino> kinos;
	private ArrayList<Spielzeit> spielzeiten;
	private ArrayList<Film> filme;
	private ArrayList<Vorstellung> resultSet = new ArrayList<Vorstellung>();

	private Vorstellung v;

	private UmfrageInfo uI;

	private Umfrage umfrage;

	public void setUmfrage(Umfrage umfrage) {
		this.umfrage = umfrage;
	}

	private NeueCellTable n;
	private UmfrageCellTable uct;

	private class UmfrageInfo {

		private Gruppe g;

		public Gruppe getG() {
			return g;
		}

		public void setG(Gruppe g) {
			this.g = g;
		}

	}

	/****************************************************************
	 * onLoad-Methode
	 * 
	 * **************************************************************/
	public void onLoad() {
		
		// Vergeben der Stylenames

		this.addStyleName("detailscontainer");

		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");

		title.addStyleName("title");

		umfrageLabel.addStyleName("detailsboxLabels");
		gruppenLabel.addStyleName("detailsboxLabels");
		terminLabel.addStyleName("detailsboxLabels");
		filternLabel.addStyleName("detailsboxLabels");

		detailsObenBox.addStyleName("detailsuntenBoxen");
		detailsMitteBox.addStyleName("detailsuntenBoxen");
		detailsBoxUmfrage.addStyleName("detailsuntenBoxen");
		detailsBoxFiltern.addStyleName("detailsuntenBoxen");
		detailsBoxSpeichern.addStyleName("speichernBox");

		detailsBoxObenMitte.addStyleName("detailsBoxMitte");
		detailsBoxMitteMitte.addStyleName("detailsBoxMitte");

		filternBox.addStyleName("filternBox");

		erstellenButton.addStyleName("speichernButton");

		umfrageTextBox.addStyleName("gruppenameTB");

		umfrageTextBox.getElement().setPropertyString("placeholder", "Anlass für die Umfrage?");

		// Zusammenbauen der Widgets

		this.add(detailsoben);
		this.add(detailsunten);

		detailsoben.add(title);

		detailsunten.add(detailsObenBox);
		detailsObenBox.add(umfrageLabel);
		detailsObenBox.add(detailsBoxObenMitte);
		detailsBoxObenMitte.add(umfrageTextBox);

		detailsunten.add(detailsMitteBox);
		detailsMitteBox.add(gruppenLabel);
		detailsMitteBox.add(detailsBoxMitteMitte);
		detailsBoxMitteMitte.add(gruppenListBox);

		detailsunten.add(detailsBoxUmfrage);
		detailsBoxUmfrage.add(terminLabel);

		if (umfrage != null) {
			title.setText("Umfrage bearbeiten");
			n = new NeueCellTable(umfrage);
			umfrageTextBox.setText(umfrage.getName());
			erstellenButton.setText("Umfrage aktualisieren");
		} else {
			n = new NeueCellTable();
		}

		uct = new UmfrageCellTable(n);

		detailsBoxUmfrage.add(uct);
		detailsBoxUmfrage.add(n);

		detailsunten.add(detailsBoxFiltern);
		detailsBoxFiltern.add(filternLabel);
		detailsBoxFiltern.add(filternBox);
		filternBox.add(filternBoxLinks);
		filternBox.add(filternBoxMitte);
		filternBox.add(filternBoxRechts);
		filternBoxLinks.add(kinoLabel);
		filternBoxLinks.add(kinoListBox);
		filternBoxRechts.add(SpiezeitLabel);
		filternBoxRechts.add(spielzeitListBox);
		filternBoxMitte.add(filmLabel);
		filternBoxMitte.add(filmListBox);

		detailsunten.add(detailsBoxSpeichern);
		detailsBoxSpeichern.add(erstellenButton);
		detailsBoxFiltern.add(filternButton);

		kinoListBox.setSize("180px", "25px");
		spielzeitListBox.setSize("180px", "25px");
		filmListBox.setSize("180px", "25px");
		gruppenListBox.setSize("200px", "25px");

		kinoplaner.getGruppenByAnwender(new GruppenCallback());

		kinoplaner.getAllKinos(new KinoCallback());

		kinoplaner.getAllSpielzeiten(new SpielzeitCallback());

		kinoplaner.getAllFilme(new FilmeCalllback());

		// ClickHandler
		erstellenButton.addClickHandler(new UmfrageErstellenClickHandler());
		filternButton.addClickHandler(new FilternClickHandler());

	}

	/***********************************************************************
	 * CLICKHANDLER
	 ***********************************************************************/
	/**
	 * Hier werden die unterschiedlichen Filter für das Erstellen einer Umfrage
	 * bereitgestellt
	 * 
	 *
	 */
	
	
	private class FilternClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			ArrayList<Vorstellung> results = n.getVorFilterVorstellungen();
			for (Vorstellung v : results) {
				Window.alert(v.getName());
			}

			if (filmListBox.getSelectedValue().equals("Keine Auswahl")) {
				kinoplaner.filterResultVorstellungenByFilm(n.getVorFilterVorstellungen(), null,
						new FilterResultVorstellungenByFilm());

			} else {
				kinoplaner.getFilmByName(filmListBox.getSelectedValue(), new GetFilmByNameCallback());
			}

		}

	}

	/***
	 * Hier findet der Erstellvorgang einer Umfrage statt.
	 * Hierfür muss zuerst der gruppenname ausgewählt werden. Ist dieser 
	 * vorhanden kann die Gruppe ausgewählt werden und die Umfrage dazu erstellt
	 * werden. Ist kein Gruppenname eingegeben meldet dass System, dass zuerst
	 * eine Gruppe aussgewählt werden soll
	 * 
	 *
	 */
	private class UmfrageErstellenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			String gruppenname = gruppenListBox.getSelectedValue();

			if (gruppenname != "") {

				kinoplaner.getGruppeByName(gruppenname, new GruppeByNameCallback(uI));

			} else {
				Window.alert("Bitte zuerst eine Gruppe auswählen");
				return;
			}
			// TODO Auto-generated method stub

		}

	}

	/***********************************************************************
	 * CALLBACKS
	 ***********************************************************************/

	private class GetFilmByNameCallback implements AsyncCallback<Film> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(Film result) {

			kinoplaner.filterResultVorstellungenByFilm(n.getVorFilterVorstellungen(), result,
					new FilterResultVorstellungenByFilm());

		}

	}

	private class FilterResultVorstellungenByFilm implements AsyncCallback<ArrayList<Vorstellung>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(ArrayList<Vorstellung> result) {

			if (kinoListBox.getSelectedValue().equals("Keine Auswahl")) {
				kinoplaner.filterResultVorstellungenByKino(result, null, new FilterResultVorstellungenByKinoCallback());
			} else {
				resultSet = result;
				kinoplaner.getKinoByName(kinoListBox.getSelectedValue(), new GetKinoByName());
			}

		}

	}

	private class GetKinoByName implements AsyncCallback<Kino> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();
		}

		@Override
		public void onSuccess(Kino result) {
			kinoplaner.filterResultVorstellungenByKino(resultSet, result,
					new FilterResultVorstellungenByKinoCallback());
		}

	}

	private class FilterResultVorstellungenByKinoCallback implements AsyncCallback<ArrayList<Vorstellung>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(ArrayList<Vorstellung> result) {
			if (kinoListBox.getSelectedValue().equals("Keine Auswahl")) {
				kinoplaner.filterResultVorstellungenByKinokette(result, null,
						new FilterResultVorstellungenByKinoketteCallback());
			} else {
				resultSet = result;
				kinoplaner.getKinoketteByName(kinoListBox.getSelectedValue(), new GetKinoketteByNameCallback());
			}

		}

	}

	private class FilterResultVorstellungenByKinoketteCallback implements AsyncCallback<ArrayList<Vorstellung>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(ArrayList<Vorstellung> result) {
			if (spielzeitListBox.getSelectedValue().equals("Keine Auswahl")) {
				kinoplaner.filterResultVorstellungenBySpielzeit(result, null,
						new FilterResultVorstellungenBySpielzeitCallback());
			} else {
				resultSet = result;
				kinoplaner.getSpielzeitByName(spielzeitListBox.getSelectedValue(), new GetSpielzeitByNameCallback());
			}
		}

	}
	/*
	 * Wenn die Vorstellung anhand des Namens der Kinokette gefilter wurde, wird diese
	 * zurückgegeben. Ist dies nicht möglich so werden Client-& Serverseitefehler ausgegeben
	 */

	private class GetKinoketteByNameCallback implements AsyncCallback<Kinokette> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}
		
		@Override
		public void onSuccess(Kinokette result) {
			kinoplaner.filterResultVorstellungenByKinokette(resultSet, result,
					new FilterResultVorstellungenByKinoketteCallback());

		}

	}

	
	/**
	 * 
	 * 
	 *
	 */
	private class FilterResultVorstellungenBySpielzeitCallback implements AsyncCallback<ArrayList<Vorstellung>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(ArrayList<Vorstellung> result) {

			n.filterResultUpdaten(result);

		}

	}
	
	/**
	 * Hier wird die Vorstellung anhand der Spielzeit gefiltert, und zurückggegeben.
	 * Ist dies nicht möglich so werden Clients- & Serverseitige Fehler ausgegeben.
	 *
	 */

	private class GetSpielzeitByNameCallback implements AsyncCallback<Spielzeit> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(Spielzeit result) {
			kinoplaner.filterResultVorstellungenBySpielzeit(resultSet, result,
					new FilterResultVorstellungenBySpielzeitCallback());

		}

	}
	
	/***
	 * Hier wird die Gruppe geladen für welche eine Umfrage erstellt werden soll
	 * 
	 * @author fiona
	 *
	 */

	private class GruppenCallback implements AsyncCallback<ArrayList<Gruppe>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Es können noch keine Gruppen geladen werden");
		}

		@Override
		public void onSuccess(ArrayList<Gruppe> result) {

			gruppen = result;
			int indexSelected = 0;
			int counter = 0;

			if (result != null) {

				for (Gruppe g : result) {

					gruppenListBox.addItem(g.getName());

					uI = new UmfrageInfo();

					uI.setG(g);

					if (umfrage != null) {

						if (g.getId() == umfrage.getGruppenId()) {

							indexSelected = counter;
						} else {
							counter++;
						}
					}

				}
				if (umfrage != null)
					gruppenListBox.setSelectedIndex(indexSelected);

			} else {

				gruppenListBox.addItem("Keine Gruppen verfügbar");
				gruppenListBox.setEnabled(false);

			}
		}

	}
	
	/***
	 * Hier wird das Kino geladen welches bei der Erstellung der Umfrage
	 * ausgewählt werden kann
	 * 
	 *
	 */

	private class KinoCallback implements AsyncCallback<ArrayList<Kino>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Kinos konnten nicht geladen werden");

		}

		@Override
		public void onSuccess(ArrayList<Kino> result) {
			// TODO Auto-generated method stub
			kinos = result;

			kinoListBox.addItem("Keine Auswahl");

			if (result != null) {

				for (Kino k : result) {

					kinoListBox.addItem(k.getName());

				}

			} else {

				kinoListBox.addItem("Keine Gruppen verfügbar");
				kinoListBox.setEnabled(false);

			}
		}

	}
	
	/*
	 * Hier wird die Spielzeit geladen, welche bei der Erstellung der Umfrage ausgewählt werden kann
	 */

	private class SpielzeitCallback implements AsyncCallback<ArrayList<Spielzeit>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Spielzeiten konnten nicht geladen werden");

		}

		@Override
		public void onSuccess(ArrayList<Spielzeit> result) {
			// TODO Auto-generated method stub
			spielzeiten = result;

			spielzeitListBox.addItem("Keine Auswahl");

			if (result != null) {

				for (Spielzeit s : result) {

					// DateFormaterSpielzeit date = new DateFormaterSpielzeit(s.getZeit());
					//
					// spielzeitListBox.addItem(date.toString());

					spielzeitListBox.addItem(s.getZeit().toGMTString());

				}

			} else {

				spielzeitListBox.addItem("Keine Spielzeit verfügbar");
				spielzeitListBox.setEnabled(false);

			}

		}

	}
	
	/***
	 * Hier werden die Filme geladen, welche bei der Erstellung einer Umfrage 
	 * ausgewählt werden können
	 * @author fiona
	 *
	 */

	private class FilmeCalllback implements AsyncCallback<ArrayList<Film>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Filme konnten nicht geladen werden");

		}

		@Override
		public void onSuccess(ArrayList<Film> result) {
			// TODO Auto-generated method stub
			filme = result;
			filmListBox.addItem("Keine Auswahl");

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
	
	/*
	 * 
	 */

	private class GruppeByNameCallback implements AsyncCallback<Gruppe> {

		UmfrageInfo info = null;

		GruppeByNameCallback(UmfrageInfo info) {
			this.info = info;
		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Gruppe result) {
			// TODO Auto-generated method stub
			if(umfrage!=null) {
				umfrage.setName(umfrageTextBox.getValue());
				umfrage.setGruppenId(result.getId());
				kinoplaner.updateUmfrage(umfrage, n.getUmfrageOptionen(), new updateUmfrageCallback());
				
			}else {
				
			kinoplaner.erstellenUmfrage(umfrageTextBox.getValue(), n.getUmfrageOptionen(), result.getId(),
					new UmfrageErstellenCallback());
			}

		}

	}
	
	private class updateUmfrageCallback implements AsyncCallback<Umfrage> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Umfrage result) {
			
			if(result!=null) {
			RootPanel.get("details").clear();
			UmfrageAnzeigenForm uaf = new UmfrageAnzeigenForm(result);



			RootPanel.get("details").add(uaf);
			}else {
				Window.alert("Name bereits vergeben");
			}
		}
		
	}

	/****
	 * Callback wird benötigt um eine Umfrage zu erstellen
	 * 
	 *
	 */
	private class UmfrageErstellenCallback implements AsyncCallback<Umfrage> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Umfrage erstellen hat NICHT funktioniert");
		}

		@Override
		public void onSuccess(Umfrage result) {

			if(result!=null) {
				RootPanel.get("details").clear();
				UmfrageAnzeigenForm uaf = new UmfrageAnzeigenForm(result);



				RootPanel.get("details").add(uaf);
				}else {
					Window.alert("Name bereits vergeben");
				}

		}

		

	}

}
