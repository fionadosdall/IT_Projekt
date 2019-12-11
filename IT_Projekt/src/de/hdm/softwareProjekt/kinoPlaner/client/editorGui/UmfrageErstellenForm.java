package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrageoption;
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

	private Label title = new Label("Umfrage erstellen");
	private Label umfrageLabel = new Label("Umfrage");
	private Label gruppenLabel = new Label("Gruppe");
	private Label terminLabel = new Label("Mögliche Termine");
	private Label filternLabel = new Label("Termine Filtern");

	private TextBox umfrageTextBox = new TextBox();
	private ListBox gruppenListBox = new ListBox();

	private Button erstellenButton = new Button("Umfrage starten");

	private ArrayList<Gruppe> gruppen;

	private ArrayList<Vorstellung> vorstellungen = null;
	private ArrayList<Umfrageoption> umfrageoptionen = null;

	private Grid umfrageGrid = new Grid(2, 5);
	private Grid spielplanGrid = new Grid(2, 5);

	private Label umfrageFilmLabel = new Label("Film");
	private Label umfrageSpielzeitLabel = new Label("Uhrzeit");
	private Label umfrageKinoLabel = new Label("Kino");
	private Label umfrageKinokettenLabel = new Label("Kinokette");
	private Label umfrageStadtLabel = new Label("Ort");

	private Label filmLabel = new Label("Film");
	private Label spielzeitLabel = new Label("Uhrzeit");
	private Label kinoLabel = new Label("Kino");
	private Label kinokettenLabel = new Label("Kinokette");
	private Label stadtLabel = new Label("Ort");

	private Film film = null;
	private Spielzeit spielzeit = null;
	private Kino kino = null;
	private Kinokette kinokette = null;

	//DateFormaterSpielzeit date = new DateFormaterSpielzeit(spielzeit.getZeit());

//	private ScrollPanel scrollPanelUmfrage = new ScrollPanel();
//	private ScrollPanel scrollPanelSpielplan = new ScrollPanel();

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

		erstellenButton.addStyleName("speichernButton");

		umfrageTextBox.addStyleName("gruppenameTB");

		umfrageTextBox.getElement().setPropertyString("placeholder", "Anlass für die Umfrage?");

		umfrageGrid.setWidget(0, 0, umfrageFilmLabel);
		umfrageGrid.setWidget(0, 1, umfrageSpielzeitLabel);
		umfrageGrid.setWidget(0, 2, umfrageKinoLabel);
		umfrageGrid.setWidget(0, 3, umfrageKinokettenLabel);
		umfrageGrid.setWidget(0, 4, umfrageStadtLabel);
		
		spielplanGrid.setCellSpacing(3);
		spielplanGrid.setBorderWidth(1);
		spielplanGrid.setTitle("Mögliche Termine");
		umfrageGrid.setCellSpacing(3);
		umfrageGrid.setBorderWidth(1);
		umfrageGrid.setTitle("Umfrageoptionen");
		

//		scrollPanelUmfrage.setHeight("30px");
//		scrollPanelSpielplan.setHeight("30px");

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
		detailsBoxUmfrage.add(umfrageGrid);
		detailsBoxUmfrage.add(spielplanGrid);
		
//		detailsBoxUmfrage.add(scrollPanelUmfrage);
//		detailsBoxUmfrage.add(scrollPanelSpielplan);
//		scrollPanelUmfrage.add(umfrageGrid);
//		scrollPanelSpielplan.add(spielplanGrid);

		detailsunten.add(detailsBoxFiltern);
		detailsBoxFiltern.add(filternLabel);

		detailsunten.add(detailsBoxSpeichern);
		detailsBoxSpeichern.add(erstellenButton);

		// KlickHandler
		erstellenButton.addClickHandler(new UmfrageErstellenClickHandler());

		gruppenListBox.setSize("200px", "25px");

		kinoplaner.getGruppenByAnwender(new GruppenCallback());

		if (gruppen == null) {
			gruppenListBox.addItem("Keine Gruppen verfügbar");
			gruppenListBox.setEnabled(false);

		} else {

			for (Gruppe g : gruppen) {

				gruppenListBox.addItem(g.getName());

			}

		}

		// findBy

		kinoplaner.getAllVorstellungen(new VorstellungenCallback());

		if (vorstellungen != null) {
			spielplanGrid.setWidget(0, 0, filmLabel);
			spielplanGrid.setWidget(0, 1, spielzeitLabel);
			spielplanGrid.setWidget(0, 2, kinoLabel);
			spielplanGrid.setWidget(0, 3, kinokettenLabel);
			spielplanGrid.setWidget(0, 4, stadtLabel);

			spielplanGrid.resizeRows(vorstellungen.size() + 1);
			int i = 1;

			for (Vorstellung v : vorstellungen) {

				kinoplaner.getFilmById(v.getFilmId(), new FilmByIdCallback());
				// kinoplaner.getSpielzeitBy
				kinoplaner.getKinoById(kino.getId(), new KinoByIdCallback());
				
				if (kino.getKinokettenId() != 0 ) {
				kinoplaner.getKinoketteById(kino.getKinokettenId(), new KinoketteByIdCallback());
				} else {
					spielplanGrid.setWidget(i, 3, new Label("keine Kette"));
				}
			
				spielplanGrid.setWidget(i, 0, new Label(film.getName()));
			//	spielplanGrid.setWidget(i, 1, new Label(date.toString()));
				spielplanGrid.setWidget(i, 2, new Label(kino.getName()));
				spielplanGrid.setWidget(i, 3, new Label(kinokette.getName()));
				spielplanGrid.setWidget(i, 4, new Label(kino.getStadt()));

				i++;

			}

		} else {
			spielplanGrid.setWidget(0, 0, filmLabel);
			spielplanGrid.setWidget(0, 1, spielzeitLabel);
			spielplanGrid.setWidget(0, 2, kinoLabel);
			spielplanGrid.setWidget(0, 3, kinokettenLabel);
			spielplanGrid.setWidget(0, 4, stadtLabel);
			
			spielplanGrid.setWidget(1, 0, new Label("kein Film"));
			spielplanGrid.setWidget(1, 1, new Label("keine Zeit"));
			spielplanGrid.setWidget(1, 2, new Label("kein Kino"));
			spielplanGrid.setWidget(1, 3, new Label("keine Kinokette"));
			spielplanGrid.setWidget(1, 4, new Label("keine Stadt"));

		}
	}

	/***********************************************************************
	 * CLICKHANDLER
	 ***********************************************************************/

	private class UmfrageErstellenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			// kinoplaner.erstellenUmfrage(name, gruppenListBox.getSelectedIndex(),
			// callback);

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
		}

	}

	private class VorstellungenCallback implements AsyncCallback<ArrayList<Vorstellung>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Es sind noch keine Vorstellungen verfügbar");

		}

		@Override
		public void onSuccess(ArrayList<Vorstellung> result) {
			// TODO Auto-generated method stub
			vorstellungen = result;
		}

	}

	private class FilmByIdCallback implements AsyncCallback<Film> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Film result) {
			// TODO Auto-generated method stub
			film = result;

		}

	}

	private class KinoByIdCallback implements AsyncCallback<Kino> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Kino result) {
			// TODO Auto-generated method stub
			kino = result;

		}

	}

	private class KinoketteByIdCallback implements AsyncCallback<Kinokette> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Kinokette result) {
			// TODO Auto-generated method stub
			kinokette = result;

		}

	}

	private class UmfrageErstellenCallback implements AsyncCallback<Umfrage> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Umfrage result) {
			// TODO Auto-generated method stub

		}

	}

}
