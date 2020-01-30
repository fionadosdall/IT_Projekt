package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;

import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;

import de.hdm.softwareProjekt.kinoPlaner.client.AdminEntry.AktuellerAnwender;
import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielplan;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

/*
 * Klasse stellt das Formular bereit um einen Spielplan zu erstellen und zu bearbeiten
 */
public class SpielplanErstellenForm extends VerticalPanel {

	Anwender aktuellerAnwender = AktuellerAnwender.getAnwender();

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
	private Button aenderungSpeichernButton = new Button("Änderung speichern");
	private Button loeschenButton = new Button("Löschen");

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

		hinzufuegenButton.addStyleName("hinzufuegenButton");
		entfernenButton.addStyleName("entfernenButton");
		speichernButton.addStyleName("speichernButton");
		aenderungSpeichernButton.addStyleName("speichernButton");
		spielplanformLabel.addStyleName("formHeaderLabel");
		spielplanBearbeitenFormLabel.addStyleName("formHeaderLabel");
		spielplanNameLabel.addStyleName("textLabel");
		kinoLabel.addStyleName("textLabel");
		vorstellung.addStyleName("detailsboxLabels");
		vorstellungen.addStyleName("detailsboxLabels");
		loeschenButton.addStyleName("loeschenButton");

		spielplannameTextBox.getElement().setPropertyString("placeholder", "Spielplanname eingeben");

		kinoListBox.setSize("185px", "25px");

		// Zusammenbauen der Widgets

		if (spielplan != null) {

			detailsoben.add(spielplanBearbeitenFormLabel);
			spielplannameTextBox.setText(spielplan.getName());

		} else {
			detailsoben.add(spielplanformLabel);

		}

		this.add(detailsoben);

		spielplanGrid.setWidget(0, 0, spielplanNameLabel);
		spielplanGrid.setWidget(0, 1, spielplannameTextBox);

		kinoplaner.getKinosByAnwenderOwner(aktuellerAnwender, new KinoCallback());

		spielplanGrid.setWidget(1, 0, kinoLabel);
		spielplanGrid.setWidget(1, 1, kinoListBox);

		inhaltObenPanel.add(spielplanGrid);
		// inhaltObenPanel.add(kinokettenCheckBox);
		this.add(inhaltObenPanel);

		// TODO kinoplaner.getVorstellungenBySpielplan(spielplan, new
		// SucheVorstellungenBySpielplanCallback());
		// inhaltUntenLinksPanel.add(vorstellungenCellTable);

		administrationPanel.add(hinzufuegenButton);
		// administrationPanel.add(entfernenButton);
		this.add(administrationPanel);
		hinzufuegenButton.addClickHandler(new SpielplaneintragHinzufuegenClickHandler());

		inhaltUntenPanel.add(vorstellungenCellTable);
		this.add(inhaltUntenPanel);

		if (spielplan != null) {
			detailsunten.add(loeschenButton);
			detailsunten.add(aenderungSpeichernButton);
		} else {
			detailsunten.add(speichernButton);
		}

		this.add(detailsunten);

		speichernButton.addClickHandler(new SpeichernClickHandler());
		aenderungSpeichernButton.addClickHandler(new SpeichernClickHandler());
		loeschenButton.addClickHandler(new LoeschenClickHandler());

	}

	public void closeSpielplaneintragForm() {
		neuerSpielplaneintrag.hide();
	}

	/*
	 * Vor dem Löschen eines Spielplans soll der Nutzer über eine Dialogbox nochmal
	 * um Bestätigung des Löschvorgangs gebeten werden
	 */
	private class SpielplanLoeschenDialogBox extends DialogBox {

		private VerticalPanel verticalPanel = new VerticalPanel();
		private HorizontalPanel buttonPanel = new HorizontalPanel();

		private Label nachfrage = new Label("Spielplan endgültig löschen?");

		private Button jaButton = new Button("Ja");
		private Button neinButton = new Button("Nein");

		// Konstruktor

		public SpielplanLoeschenDialogBox() {

			nachfrage.addStyleName("Abfrage");
			jaButton.addStyleName("buttonAbfrage");
			neinButton.addStyleName("buttonAbfrage");

			buttonPanel.add(jaButton);
			buttonPanel.add(neinButton);
			verticalPanel.add(nachfrage);
			verticalPanel.add(buttonPanel);

			this.add(verticalPanel);

			// ClickHandler für SpielplanLoeschenDialogBox

			jaButton.addClickHandler(new SpielplanLoeschenBestaetigenClickHandler(this));
			neinButton.addClickHandler(new SpielplanLoeschenAbbrechenClickHandler(this));

		}
	}

	/*
	 * ClickHandler zur Lösch-Bestätigung des Spielplans
	 */

	private class SpielplanLoeschenBestaetigenClickHandler implements ClickHandler {

		private SpielplanLoeschenDialogBox spielplanLoeschenDB;

		public SpielplanLoeschenBestaetigenClickHandler(SpielplanLoeschenDialogBox spielplanLoeschenDBn) {
			this.spielplanLoeschenDB = spielplanLoeschenDBn;

		}

		@Override
		public void onClick(ClickEvent event) {
			kinoplaner.loeschen(spielplan, aktuellerAnwender, new LoeschenSpielplanCallback());
			spielplanLoeschenDB.hide();
		}

	}

	/*
	 * ClickHandler um das Löschen des Spielplans abzubrechen
	 */

	private class SpielplanLoeschenAbbrechenClickHandler implements ClickHandler {

		private SpielplanLoeschenDialogBox spielplanLoeschenDB;

		public SpielplanLoeschenAbbrechenClickHandler(SpielplanLoeschenDialogBox spielplanLoeschenDB) {
			this.spielplanLoeschenDB = spielplanLoeschenDB;
		}

		@Override
		public void onClick(ClickEvent event) {
			spielplanLoeschenDB.hide();

		}

	}

	/**************************
	 * CLICKHANDLER
	 * 
	 ****************************************************
	 */

	/*
	 * Wenn der Nutzer den angezeigten Spielplan löschen möchte, kann er dies über
	 * den Löschen-Button tun. Dabei öffnet sich automatisch die DialogBox. Die
	 * Dialogbox bittet den Nutzer, erneut zu bestätigten, dass er die Umfrage
	 * löschen möchte
	 */
	private class LoeschenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			SpielplanLoeschenDialogBox spielplanLoeschenDB = new SpielplanLoeschenDialogBox();
			spielplanLoeschenDB.center();

		}

	}

	/*
	 * Hier kann eine Spielplaneintrags-Instanz dem Spielplan hinzugefügt werden
	 */

	private class SpielplaneintragHinzufuegenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			neuerSpielplaneintrag = new SpielplaneintragForm(vorstellungenCellTable);
			neuerSpielplaneintrag.show();

		}

	}

	/*
	 * Hier kann die erstellte Spielplan-Instanz gespeichert werden
	 * 
	 */

	private class SpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			kinoplaner.getKinoByName(kinoListBox.getSelectedValue(), new GetKinoByNameCallback());

		}

	}

	/****************************************
	 * CALLBACKS ********************************************
	 */

	/*
	 * Private Klasse um alle Kino-Instanzen, aufgrund eines Namens aus dem System
	 * zu bekommen. Ist bei der SpielplanErstellung der Name des Kinos bereits
	 * vergeben, so gibt das System eine Fehlermeldung aus
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
				// if (kinokettenCheckBox.getValue() == true) {

				//// if (result.getKinokettenId() != 0) {
				// if (spielplan.isKinokettenSpielplan() == true) {

				// } else {

				// }
				// } else {
				// Window.alert("Das Kino " + result.getName() + " hat keine Kinokette!");
				// }
				// if (spielplan.isKinokettenSpielplan() == true) {

				// } else {
				if (spielplannameTextBox.getValue().equals("")) {
					Window.alert("Kein Name eingegeben");
				} else {
					spielplan.setName(spielplannameTextBox.getValue());
					spielplan.setKinoId(result.getId());

					ArrayList<Vorstellung> vorstellungen = new ArrayList<Vorstellung>();
					vorstellungen = vorstellungenCellTable.getVorstellungenArray();

					kinoplaner.updateSpielplanKino(vorstellungen, spielplan, aktuellerAnwender,
							new AsyncCallback<Spielplan>() {

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
					// }
					// }
				}
			} else {
				// if (kinokettenCheckBox.getValue() == true) {
				// if (result.getKinokettenId() != 0) {
				// kinoplaner.erstellenSpielplaeneKinokette(spielplannameTextBox.getValue(),
				// result.getKinokettenId(), vorstellungenCellTable.getVorstellungenArray(),
				// new ErstellenSpielplaeneKinoketteCallback());
				// } else {
				// Window.alert("Das Kino " + result.getName() + " hat keine Kinokette!");
				// }
				// } else {
				kinoplaner.erstellenSpielplanKino(spielplannameTextBox.getValue(), result.getId(),
						vorstellungenCellTable.getVorstellungenArray(), aktuellerAnwender,
						new ErstellenSpielplanKinoCallback());
				// }
			}
		}

	}

	/*
	 * Pprivate Klasse um eine ausgewählte Spielplan-Instanz zu löschen
	 */

	private class LoeschenSpielplanCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(Void result) {
			Systemmeldung.anzeigen("Spielplan wurde gelöscht");
			RootPanel.get("details").clear();
			spielplaeneF = new MeineSpielplaeneForm();
			RootPanel.get("details").add(spielplaeneF);

		}

	}
	/*
	 * Private Klasse um alle Kino-Instanzen aus dem System zu bekommen
	 */

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

	/*
	 * Private Klasse um einen Spielplan im System zu erstellen
	 */

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