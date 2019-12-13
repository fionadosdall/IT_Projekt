package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;
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

	private CellTable<Vorstellung> vorstellungenCellTable = new CellTable<Vorstellung>(KEY_PROVIDER);
	private final MultiSelectionModel<Vorstellung> selectionModel = new MultiSelectionModel<Vorstellung>();
	private ListDataProvider<Vorstellung> dataProvider = new ListDataProvider<Vorstellung>();
	private List<Vorstellung> list = dataProvider.getList();

	private static final ProvidesKey<Vorstellung> KEY_PROVIDER = new ProvidesKey<Vorstellung>() {
		public Object getKey(Vorstellung v) {
			return v.getId();
		}
	};

	private Label stadtLabel = new Label("Ort");

	private Film film = null;
	private Spielzeit spielzeit = null;
	private Kino kino = null;
	private Kinokette kinokette = null;

	// DateFormaterSpielzeit date = new DateFormaterSpielzeit(spielzeit.getZeit());

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
		detailsBoxUmfrage.add(vorstellungenCellTable);

		detailsunten.add(detailsBoxFiltern);
		detailsBoxFiltern.add(filternLabel);

		detailsunten.add(detailsBoxSpeichern);
		detailsBoxSpeichern.add(erstellenButton);

		// ClickHandler
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

		/***********************************************************************
		 * CELL TABLE
		 ***********************************************************************/

		kinoplaner.getAllVorstellungen(new VorstellungenCallback());

//		for (Vorstellung v : vorstellungen) {
//				
//				list.add(v);

		CheckboxCell cbCell = new CheckboxCell(true, false);
		Column<Vorstellung, Boolean> checkBoxColumn = new Column<Vorstellung, Boolean>(cbCell) {

			public Boolean getValue(Vorstellung object) {

				return selectionModel.isSelected(object);

			}
		};

		vorstellungenCellTable.addColumn(checkBoxColumn, "Auswählen");

		TextCell filmCell = new TextCell();
		Column<Vorstellung, String> filmColumn = new Column<Vorstellung, String>(filmCell) {

			@Override
			public String getValue(Vorstellung object) {
				// TODO Auto-generated method stub
				kinoplaner.getFilmById(object.getFilmId(), new FilmByIdCallback());
				return film.getName();
			}

		};

		vorstellungenCellTable.addColumn(filmColumn, "Film");

		TextCell spielzeitCell = new TextCell();
		Column<Vorstellung, String> spielzeitColumn = new Column<Vorstellung, String>(spielzeitCell) {

			@Override
			public String getValue(Vorstellung object) {
				// TODO Auto-generated method stub
				return null;
			}

		};

		vorstellungenCellTable.addColumn(spielzeitColumn, "Spielzeit");

		TextCell kinoCell = new TextCell();
		Column<Vorstellung, String> kinoColumn = new Column<Vorstellung, String>(kinoCell) {

			@Override
			public String getValue(Vorstellung object) {
				// TODO Auto-generated method stub
				 //TODO kinoplaner.getKinoById(kino.getId(), new KinoByIdCallback());
				return kino.getName();
			}

		};

		vorstellungenCellTable.addColumn(kinoColumn, "Kino");

		TextCell kinokettenCell = new TextCell();
		Column<Vorstellung, String> kinokettenCellColumn = new Column<Vorstellung, String>(kinokettenCell) {

			@Override
			public String getValue(Vorstellung object) {
				// TODO Auto-generated method stub
				kinoplaner.getKinoketteById(kino.getKinokettenId(), new KinoketteByIdCallback());
				return kinokette.getName();
			}

		};

		vorstellungenCellTable.addColumn(kinokettenCellColumn, "Kinokette");

		TextCell stadtCell = new TextCell();
		Column<Vorstellung, String> stadtCellColumn = new Column<Vorstellung, String>(stadtCell) {

			@Override
			public String getValue(Vorstellung object) {
				// TODO Auto-generated method stub
				return kino.getStadt();
			}

		};

		vorstellungenCellTable.addColumn(stadtCellColumn, "Stadt");

		vorstellungenCellTable.setSelectionModel(selectionModel,
				DefaultSelectionEventManager.<Vorstellung>createCheckboxManager());

		dataProvider.addDataDisplay(vorstellungenCellTable);

//}

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
