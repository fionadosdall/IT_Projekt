package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;

import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;

import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielplan;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

public class SpielplanErstellenForm extends VerticalPanel {

	private int kinoId;

	private KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

	private HorizontalPanel detailsoben = new HorizontalPanel();
	private HorizontalPanel detailsunten = new HorizontalPanel();
	private VerticalPanel inhaltObenPanel = new VerticalPanel();
	private HorizontalPanel inhaltUntenPanel = new HorizontalPanel();
	private HorizontalPanel administrationPanel = new HorizontalPanel();
	// private VerticalPanel inhaltUntenLinksPanel = new VerticalPanel();
	// private VerticalPanel inhaltUntenRechtsPanel = new VerticalPanel();

	private Label spielplanformLabel = new Label("Neuen Spielplan erstellen");
	private Label spielplanBearbeitenFormLabel = new Label("Spielplan bearbeiten");
	private Label spielplanNameLabel = new Label("Spielplanname");
	private Label vorstellung = new Label("Vorstellung hinzufügen");
	private Label vorstellungen = new Label("Spielplan-Vorstellungen");
	private Label kinoLabel = new Label("Kino:");

	private TextBox spielplannameTextBox = new TextBox();
	private ListBox kinoListBox = new ListBox();
	private CheckBox kinokettenCheckBox = new CheckBox("Für alle Kinos der Kinokette verwenden.");

	private SpielplanErstellenForm bearbeiten;
	private SpielplanVorstellungenCellTable vorstellungenCellTable;

	private Grid spielplanGrid = new Grid(3, 2);

	private Button hinzufuegenButton = new Button("Vorstellung Hinzufügen");
	private Button entfernenButton = new Button("Vorstellung entfernen");
	private Button speichernButton = new Button("Speichern");

	private ArrayList<Kino> kinos = new ArrayList<Kino>();
	private ListDataProvider<Kino> dataProvider = new ListDataProvider<Kino>();
	private List<Kino> list = dataProvider.getList();

	private static final ProvidesKey<Kino> KEY_PROVIDER = new ProvidesKey<Kino>() {

		public Object getKey(Kino kino) {
			return kino.getId();
		}
	};

	private Spielplan spielplan = null;

	private MeineSpielplaeneForm spielplaeneF;

	/** Konstruktor zur Übergabe des zu bearbeitenden eines Spielplans **/

	public SpielplanErstellenForm(Spielplan spielplan) {
		this.spielplan = spielplan;
	}

	/** Default-Konstruktor **/

	public SpielplanErstellenForm() {

	}

	private SpielplaneintragForm neuerSpielplaneintrag;

	public void onLoad() {

		if (spielplan != null) {
			vorstellungenCellTable = new SpielplanVorstellungenCellTable(spielplan);

		} else {
			vorstellungenCellTable = new SpielplanVorstellungenCellTable();
		}

		vorstellungenCellTable.setParent(this);

		// Vergeben der Stylenamen

		this.addStyleName("center");
		this.addStyleName("detailscontainer");

		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");
		spielplannameTextBox.addStyleName("formularTextBox");
		kinokettenCheckBox.addStyleName("checkBox");

		inhaltObenPanel.addStyleName("inhaltSpielplanPanel");
		inhaltUntenPanel.addStyleName("inhaltSpielplanPanel");
		administrationPanel.addStyleName("detailsunten");
		// inhaltUntenLinksPanel.addStyleName("splitPanel");
		// inhaltUntenRechtsPanel.addStyleName("splitPanel");

		hinzufuegenButton.addStyleName("hinzufuegenButton");
		entfernenButton.addStyleName("entfernenButton");
		speichernButton.addStyleName("speichernButton");
		spielplanformLabel.addStyleName("formHeaderLabel");
		spielplanBearbeitenFormLabel.addStyleName("formHeaderLabel");
		spielplanNameLabel.addStyleName("textLabel");
		kinoLabel.addStyleName("textLabel");
		vorstellung.addStyleName("detailsboxLabels");
		vorstellungen.addStyleName("detailsboxLabels");

		spielplannameTextBox.getElement().setPropertyString("placeholder", "Spielplanname eingeben");

		kinoListBox.setSize("185px", "25px");

		// Zusammenbauen der Widgets

		if (spielplan != null) {

			detailsoben.add(spielplanBearbeitenFormLabel);
			spielplannameTextBox.setText(spielplan.getName());
		} else {
			detailsoben.add(spielplanformLabel);
			clearForm();
		}

		this.add(detailsoben);

		spielplanGrid.setWidget(0, 0, spielplanNameLabel);
		spielplanGrid.setWidget(0, 1, spielplannameTextBox);

		kinoplaner.getKinosByAnwenderOwner(new KinoCallback());

		spielplanGrid.setWidget(1, 0, kinoLabel);
		spielplanGrid.setWidget(1, 1, kinoListBox);

		inhaltObenPanel.add(spielplanGrid);
		//inhaltObenPanel.add(kinokettenCheckBox);
		this.add(inhaltObenPanel);

		// TODO kinoplaner.getVorstellungenBySpielplan(spielplan, new
		// SucheVorstellungenBySpielplanCallback());
		// inhaltUntenLinksPanel.add(vorstellungenCellTable);

		administrationPanel.add(hinzufuegenButton);
		this.add(administrationPanel);
		hinzufuegenButton.addClickHandler(new SpielplaneintragHinzufuegenClickHandler());

		inhaltUntenPanel.add(vorstellungenCellTable);
		this.add(inhaltUntenPanel);

		detailsunten.add(speichernButton);
		this.add(detailsunten);

		/*
		 * detailsunten.add(detailsObenBox); detailsunten.add(detailsMitteBox);
		 * detailsunten.add(detailsUntenBox);
		 * 
		 * detailsObenBox.add(spielplanname); detailsObenBox.add(detailsBoxObenMitte);
		 * detailsBoxObenMitte.add(spielplannameTB);
		 * 
		 * detailsMitteBox.add(vorstellung); detailsMitteBox.add(detailsBoxMitteMitte);
		 * detailsBoxMitteMitte.add(vorstellungTB);
		 * detailsMitteBox.add(detailsBoxMitteUnten);
		 * detailsBoxMitteUnten.add(hinzufuegenButton);
		 * 
		 * detailsUntenBox.add(vorstellungen);
		 * detailsUntenBox.add(detailsBoxUntenMitte);
		 * detailsBoxUntenMitte.add(kinoCellTable);
		 * detailsUntenBox.add(detailsBoxUnten); detailsBoxUnten.add(entfernenButton);
		 * 
		 * detailsunten.add(speichernBox); speichernBox.add(speichernButton);
		 * 
		 * //Click Handler
		 */

		// entfernenButton.addClickHandler(new KinoEntfernenClickHandler());
		speichernButton.addClickHandler(new SpeichernClickHandler());

		/*
		 * 
		 * // Alle Kinos die im System vorhanden sind werden geladen
		 * 
		 * kinoplaner.getAllKinos(new AsyncCallback<ArrayList<Kino>>() {
		 * 
		 * @Override public void onFailure(Throwable caught) { // TODO Auto-generated
		 * method stub Systemmeldung.anzeigen("Kino konnten nicht geladen werden");
		 * 
		 * }
		 * 
		 * @Override public void onSuccess(ArrayList<Kino> result) { for ( Kino u:
		 * result) { kinoTB.add(u); alleKinosOracle.add(u.getName()); }
		 * 
		 * }
		 * 
		 * });
		 * 
		 * 
		 * /*********************************************************** CELL TABLE
		 */

		/*
		 * TextCell namenTextCell = new TextCell();
		 * 
		 * Column<Kino, String> namenColumn = new Column<Kino, String>(namenTextCell) {
		 * 
		 * @Override public String getValue(Kino kino) {
		 * 
		 * if (kino == null) { return "test"; } else {
		 * 
		 * return kino.getName(); }
		 * 
		 * }
		 * 
		 * };
		 * 
		 * Cell <String> loeschenCell = new ButtonCell();
		 * 
		 * Column <Kino, String> loeschenColumn = new Column <Kino,
		 * String>(loeschenCell) {
		 * 
		 * @Override public String getValue(Kino object) { // TODO Auto-generated method
		 * stub return "-"; }
		 * 
		 * };
		 * 
		 * loeschenColumn.setFieldUpdater(new FieldUpdater <Kino, String>() {
		 * 
		 * @Override public void update(int index, Kino kino, String value) { // TODO
		 * Auto-generated method stub dataProvider.getList().remove(kino);
		 * 
		 * AsyncCallback<Kino> loeschenCallback = new AsyncCallback <Kino>() {
		 * 
		 * @Override public void onFailure(Throwable caught) { // TODO Auto-generated
		 * method stub
		 * 
		 * }
		 * 
		 * @Override public void onSuccess(Kino result) { // TODO Auto-generated method
		 * stub
		 * 
		 * }
		 * 
		 * };
		 * 
		 * //TODO kinoplaner.kinoketteEntfernen(kino, loeschenCallback);
		 * 
		 * }
		 * 
		 * });
		 * 
		 * namenColumn.setFieldUpdater(new FieldUpdater<Kino, String>() {
		 * 
		 * @Override public void update(int index, Kino kino, String name) { // TODO
		 * Auto-generated method stub kino.setName(name); }
		 * 
		 * });
		 * 
		 * kinoCellTable.addColumn(namenColumn, "Vorstellungen");
		 * kinoCellTable.addColumn(loeschenColumn, "Vorstellung entfernen");
		 * kinoCellTable.setColumnWidth(namenColumn, 20, Unit.PC);
		 * kinoCellTable.setColumnWidth(loeschenColumn, 20, Unit.PC);
		 * 
		 * dataProvider.addDataDisplay(kinoCellTable);
		 */
	}

	public void closeSpielplaneintragForm() {
		neuerSpielplaneintrag.hide();
	}

	/**************************
	 * CLICKHANDLER
	 */

	private class SpielplaneintragHinzufuegenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			// RootPanel.get("details").clear();

			neuerSpielplaneintrag = new SpielplaneintragForm(vorstellungenCellTable);
			neuerSpielplaneintrag.show();
			// neuerSpielplaneintrag.center();
			// RootPanel.get("details").add(neuerSpielplaneintrag);

		}

	}

	private class SpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			kinoplaner.getKinoByName(kinoListBox.getSelectedValue(), new GetKinoByNameCallback());

		}

	}

	/*****
	 * CALLBACKS
	 */

	private class GetKinoByNameCallback implements AsyncCallback<Kino> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(Kino result) {
			if (spielplan != null) {
			//	if (kinokettenCheckBox.getValue() == true) {

					////if (result.getKinokettenId() != 0) {
						//if (spielplan.isKinokettenSpielplan() == true) {

						//} else {

						//}
					//} else {
						//Window.alert("Das Kino " + result.getName() + " hat keine Kinokette!");
					//}
					//if (spielplan.isKinokettenSpielplan() == true) {

					//} else {
			
				spielplan.setName(spielplannameTextBox.getValue());
				spielplan.setKinoId(result.getId());
				
				ArrayList<Vorstellung> vorstellungen = new ArrayList<Vorstellung>();
				vorstellungen = vorstellungenCellTable.getVorstellungenArray();
				
				kinoplaner.updateSpielplanKino(vorstellungen, spielplan, new AsyncCallback<Spielplan>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
						caught.printStackTrace();
						
					}

					@Override
					public void onSuccess(Spielplan result) {
						if (result == null) {
							Window.alert("Name bereits vergeben!");
						} else {
							RootPanel.get("details").clear();
							spielplaeneF = new MeineSpielplaeneForm();
							RootPanel.get("details").add(spielplaeneF);
						}
						
					}
				});
					//}
				//}

			} else {
				//if (kinokettenCheckBox.getValue() == true) {
					//if (result.getKinokettenId() != 0) {
					//	kinoplaner.erstellenSpielplaeneKinokette(spielplannameTextBox.getValue(),
							//	result.getKinokettenId(), vorstellungenCellTable.getVorstellungenArray(),
							//	new ErstellenSpielplaeneKinoketteCallback());
					//} else {
					//	Window.alert("Das Kino " + result.getName() + " hat keine Kinokette!");
					//}
				//} else {
					kinoplaner.erstellenSpielplanKino(spielplannameTextBox.getValue(), result.getId(),
							vorstellungenCellTable.getVorstellungenArray(), new ErstellenSpielplanKinoCallback());
				//}
			}
		}

	}

	private class KinoCallback implements AsyncCallback<ArrayList<Kino>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(ArrayList<Kino> result) {

			kinos = result;
			int indexSelected = 0;
			int counter = 0;

			if (result.size() != 0) {

				for (Kino k : result) {

					kinoListBox.addItem(k.getName());

					if (spielplan != null) {
						if (k.getId() == spielplan.getKinoId()) {
							indexSelected = counter;
						} else {
							counter++;
						}
					}

				}
				if (spielplan != null) {
					kinoListBox.setSelectedIndex(indexSelected);
				}

			} else {

				kinoListBox.addItem("Kein Kino verfügbar");
				kinoListBox.setEnabled(false);

			}

		}

	}

	// private class KinoEntfernenCallback implements AsyncCallback<Kino> {

	// @Override
	// public void onFailure(Throwable caught) {
	// TODO Auto-generated method stub

	// }

	// @Override
	// public void onSuccess(Kino result) {
	// TODO Auto-generated method stub

	// }

	// }

	/* Callback */

	private class ErstellenSpielplaeneKinoketteCallback implements AsyncCallback<ArrayList<Spielplan>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();
		}

		@Override
		public void onSuccess(ArrayList<Spielplan> result) {

			if (result == null) {
				Window.alert("Name bereits vergeben!");
			} else {
				RootPanel.get("details").clear();
				spielplaeneF = new MeineSpielplaeneForm();
				RootPanel.get("details").add(spielplaeneF);
			}

		}

	}

	public class ErstellenSpielplanKinoCallback implements AsyncCallback<Spielplan> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(Spielplan result) {
			if (result == null) {
				Window.alert("Name bereits vergeben!");
			} else {
				RootPanel.get("details").clear();
				spielplaeneF = new MeineSpielplaeneForm();
				RootPanel.get("details").add(spielplaeneF);
			}
		}

	}

	/** Methoden ***/

	public void clearForm() {
		// spielplannameTB.setText("");

	}

}