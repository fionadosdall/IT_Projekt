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
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

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

	private ArrayList<Gruppe> gruppen;
	private ArrayList<Kino> kinos;
	private ArrayList<Spielzeit> spielzeiten;
	private ArrayList<Film> filme;
	
	private Vorstellung v;

	private UmfrageCellTable uct = new UmfrageCellTable(v);
	private NeueCellTable n = new NeueCellTable();
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.Widget#onLoad()
	 */
	public void onLoad() {

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
		filternBoxMitte.add(SpiezeitLabel);
		filternBoxMitte.add(spielzeitListBox);
		filternBoxRechts.add(filmLabel);
		filternBoxRechts.add(filmListBox);

		detailsunten.add(detailsBoxSpeichern);
		detailsBoxSpeichern.add(erstellenButton);

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

	}

	/***********************************************************************
	 * CLICKHANDLER
	 ***********************************************************************/

	private class UmfrageErstellenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			String gruppenname = gruppenListBox.getSelectedValue();
			Gruppe g = null;

			if (gruppenname != "") {
				// g = kinoplaner.getGruppeByName(gruppenname);

			} else {
				Window.alert("Bitte zuerst eine Gruppe auswählen");
				return;
			}
			// TODO Auto-generated method stub
			kinoplaner.erstellenUmfrage(umfrageTextBox.getValue(), g.getId(), new UmfrageErstellenCallback());

		}

	}

	/***********************************************************************
	 * CALLBACKS
	 ***********************************************************************/

	private class GruppenCallback implements AsyncCallback<ArrayList<Gruppe>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Es können noch keine Gruppen geladen werden");
		}

		@Override
		public void onSuccess(ArrayList<Gruppe> result) {
			// TODO Auto-generated method stub
			gruppen = result;

			if (result != null) {

				for (Gruppe g : result) {

					gruppenListBox.addItem(g.getName());

				}

			} else {

				gruppenListBox.addItem("Keine Gruppen verfügbar");
				gruppenListBox.setEnabled(false);

			}
		}

	}


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

			if (result != null) {

				for (Spielzeit s : result) {

//					DateFormaterSpielzeit date = new DateFormaterSpielzeit(s.getZeit());
//
//					spielzeitListBox.addItem(date.toString());

					spielzeitListBox.addItem(s.getZeit().toGMTString());

				}

			} else {

				spielzeitListBox.addItem("Keine Spielzeit verfügbar");
				spielzeitListBox.setEnabled(false);

			}

		}

	}

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

	private class UmfrageErstellenCallback implements AsyncCallback<Umfrage> {

		private Umfrage umfrage;

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Umfrage erstellen hat NICHT funktioniert");
		}

		@Override
		public void onSuccess(Umfrage result) {
			// TODO Auto-generated method stub
			umfrage = result;
			RootPanel.get("details").clear();
			UmfrageAnzeigenForm uaf = new UmfrageAnzeigenForm(result);
			RootPanel.get("details").add(uaf);

		}

		public void setUmfrage(Umfrage umfrage) {
			this.umfrage = umfrage;
		}

	}

}
