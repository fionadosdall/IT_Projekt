package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.client.EditorEntry.AktuellerAnwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;

/*****
 * Formular für das Anlegen einer neuen Gruppe im Datenstamm
 * 
 * @author
 *
 */
public class GruppeErstellenForm extends FlowPanel {

	Anwender aktuellerAnwender = AktuellerAnwender.getAnwender();

	private KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();
	private FlowPanel speichernBox = new FlowPanel();
	private FlowPanel detailsBoxObenMitte = new FlowPanel();
	private FlowPanel detailsBoxMitteMitte = new FlowPanel();
	private FlowPanel detailsBoxMitteUnten = new FlowPanel();
	private FlowPanel detailsBoxUntenMitte = new FlowPanel();
	private FlowPanel detailsBoxUnten = new FlowPanel();
	private FlowPanel detailsObenBox = new FlowPanel();
	private FlowPanel detailsMitteBox = new FlowPanel();
	private FlowPanel detailsUntenBox = new FlowPanel();

	private Label title = new Label("Gruppe erstellen");
	private Label gruppenname = new Label("Gruppenname");
	private Label mitglied = new Label("Mitglied hinzufügen");
	private Label mitglieder = new Label("Gruppenmitglieder");

	private TextBox gruppenameTB = new TextBox();

	/**
	 * Hier werden alle Anwendernamen, die im System vorhanden sind, gespeichert
	 */
	private MultiWordSuggestOracle alleAnwenderOracle = new MultiWordSuggestOracle();

	private SuggestBox mitgliedTB = new SuggestBox(alleAnwenderOracle);

	private ArrayList<Anwender> anwenderTB = new ArrayList<Anwender>();

	private Button hinzufuegenButton = new Button("Hinzufügen");
	private Button speichernButton = new Button("Speichern");

	public interface CellTableResources extends CellTable.Resources {

		@Source({ CellTable.Style.DEFAULT_CSS, "CellTable.css" })
		TableStyle cellTableStyle();

		interface TableStyle extends CellTable.Style {
		}
	}

	CellTable.Resources tableRes = GWT.create(CellTableResources.class);

	private CellTable<Anwender> anwenderCellTable = new CellTable<Anwender>(100, tableRes, KEY_PROVIDER);

	private ListDataProvider<Anwender> dataProvider = new ListDataProvider<Anwender>();
	private ArrayList<Anwender> anwenderListe = new ArrayList<Anwender>();

	private static final ProvidesKey<Anwender> KEY_PROVIDER = new ProvidesKey<Anwender>() {

		@Override
		public Object getKey(Anwender anwender) {
			// TODO Auto-generated method stub

			return anwender.getId();
		}

	};

	private GruppenAnzeigenForm gruppenAF;

	/*********
	 * Konstruktor
	 */
	public GruppeErstellenForm() {

	}

	public GruppeErstellenForm(Gruppe gruppe) {
		this.gruppe = gruppe;
	}

	private Gruppe gruppe = null;

	public Gruppe getGruppe() {
		return gruppe;
	}

	public void setGruppe(Gruppe gruppe) {
		this.gruppe = gruppe;
	}

	/**************************************
	 * onLoad Methode
	 ****************************************/

	public void onLoad() {

		/**
		 * Vergeben der Stylenames
		 */

		this.addStyleName("detailscontainer");

		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");

		detailsObenBox.addStyleName("detailsuntenBoxen");
		detailsMitteBox.addStyleName("detailsuntenBoxen");
		detailsUntenBox.addStyleName("detailsuntenBoxen");

		speichernBox.addStyleName("speichernBox");
		detailsBoxObenMitte.addStyleName("detailsBoxMitte");
		detailsBoxMitteMitte.addStyleName("detailsBoxMitte");
		detailsBoxMitteUnten.addStyleName("detailsBoxMitte");
		detailsBoxUntenMitte.addStyleName("detailsBoxMitte");
		detailsBoxUnten.addStyleName("detailsBoxUnten");

		title.addStyleName("title");
		gruppenname.addStyleName("detailsboxLabels");
		mitglied.addStyleName("detailsboxLabels");
		mitglieder.addStyleName("detailsboxLabels");

		gruppenameTB.addStyleName("gruppenameTB");
		mitgliedTB.addStyleName("nameTextBox");

		hinzufuegenButton.addStyleName("hinzufuegenButton");
		speichernButton.addStyleName("speichernButton");

		if (gruppe == null) {
			gruppenameTB.getElement().setPropertyString("placeholder", "Gruppenname eingeben");

		} else {
			gruppenameTB.setText(gruppe.getName());
			title.setText("Gruppe bearbeiten");
		}
		mitgliedTB.getElement().setPropertyString("placeholder", "User suchen");

		// Zusammenbauen der Widgets

		this.add(detailsoben);
		this.add(detailsunten);

		detailsoben.add(title);

		detailsunten.add(detailsObenBox);
		detailsunten.add(detailsMitteBox);
		detailsunten.add(detailsUntenBox);

		detailsObenBox.add(gruppenname);
		detailsObenBox.add(detailsBoxObenMitte);
		detailsBoxObenMitte.add(gruppenameTB);

		detailsMitteBox.add(mitglied);
		detailsMitteBox.add(detailsBoxMitteMitte);
		detailsBoxMitteMitte.add(mitgliedTB);
		detailsMitteBox.add(detailsBoxMitteUnten);
		detailsBoxMitteUnten.add(new Label("Gib einen Buchstaben ein und die Box liefert dir Vorschläge"));
		detailsBoxMitteUnten.add(hinzufuegenButton);

		detailsUntenBox.add(mitglieder);
		detailsUntenBox.add(detailsBoxUntenMitte);
		detailsBoxUntenMitte.add(anwenderCellTable);
		detailsBoxUntenMitte.add(new Label("Entferne Gruppenmitglieder mit Click auf -"));

		detailsUntenBox.add(detailsBoxUnten);

		detailsunten.add(speichernBox);
		speichernBox.add(speichernButton);

		anwenderCellTable.setEmptyTableWidget(new Label("Es wurde noch kein Mitglied hinzugefügt"));

		/**
		 * Click-Handler
		 */
		hinzufuegenButton.addClickHandler(new MitgliedHinzufuegenClickHandler());
		// entfernenButton.addClickHandler(new MitgliedEntfernenClickHandler());
		speichernButton.addClickHandler(new SpeichernClickHandler());

		// Alle Anwender die im System vorhanden sind werden geladen
		kinoplaner.getAllAnwender(new AsyncCallback<ArrayList<Anwender>>() {

			public void onFailure(Throwable caught) {
				Window.alert("Anwender konnten nicht geladen werden");

			}

			public void onSuccess(ArrayList<Anwender> result) {
				for (Anwender u : result) {
					if (AktuellerAnwender.getAnwender().getId() != u.getId()) {
						anwenderTB.add(u);
						alleAnwenderOracle.add(u.getName());
					}
				}

			}
		});

		if (gruppe != null)
			kinoplaner.getGruppenmitgliederByGruppe(gruppe, new SucheGruppenmitgliederByGruppeCallback());

		/***********************************************************************
		 * CELL TABLE
		 **
		 *********************************************************************/

		TextCell namenTextCell = new TextCell();

		Column<Anwender, String> namenColumn = new Column<Anwender, String>(namenTextCell) {

			@Override
			public String getValue(Anwender anwender) {
				// TODO Auto-generated method stub

				return anwender.getName();

			}

		};

		/**
		 * LoeschenButton Hinzufuegen
		 */

		Cell<String> loeschenCell = new ButtonCell();

		Column<Anwender, String> loeschenColumn = new Column<Anwender, String>(loeschenCell) {

			@Override
			public String getValue(Anwender object) {

				return "-";
			}

		};

		/**
		 * FieldUpdater fuer Betaetigung des LoeschenButton erstellen
		 */

		loeschenColumn.setFieldUpdater(new FieldUpdater<Anwender, String>() {

			@Override
			public void update(int index, Anwender anwender, String value) {

				// Update des DataProvider

				dataProvider.getList().remove(anwender);
				dataProvider.refresh();

				// Update der AnwenderListe

				anwenderListe.remove(anwender);

			}

		});

		namenColumn.setFieldUpdater(new FieldUpdater<Anwender, String>() {

			@Override
			public void update(int index, Anwender anwender, String name) {

				anwender.setName(name);
			}

		});

		anwenderCellTable.addColumn(namenColumn, "Mitglieder");
		anwenderCellTable.addColumn(loeschenColumn, "Mitglied entfernen");
		anwenderCellTable.setColumnWidth(namenColumn, 20, Unit.PC);
		anwenderCellTable.setColumnWidth(loeschenColumn, 20, Unit.PC);

		dataProvider.addDataDisplay(anwenderCellTable);

	}

	/***********************************************************************
	 * CLICKHANDLER
	 ***********************************************************************/

	/*****************************
	 * Hier wird der Gruppe ein neues Mitglied hinzugefügt und ein neues
	 * AnwenderByNameCallback initialisiert.
	 * 
	 *
	 */

	private class MitgliedHinzufuegenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			kinoplaner.getAnwenderByName(mitgliedTB.getValue(), new AnwenderByNameCallback());
			mitgliedTB.setText("");

		}

	}

	/*******
	 * Sobald das Textfeld mit einem Gruppenname ausgefüllt ist, wird eine neue
	 * Gruppe nach dem Klicken des add-Buttons erstellt (-->
	 * GruppeErstellenCallback). Wenn schon eine Gruppe vorhanden war, wird nur ihr
	 * geänderter Name neu abgespeichert (--> UpdateGruppeCallback)
	 * 
	 *
	 */

	private class SpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (gruppenameTB.getValue() == "") {
				Window.alert("Es wurde kein Gruppenname eingegeben");
				return;
			}

			if (gruppe == null) {
				kinoplaner.erstellenGruppe(gruppenameTB.getValue(), anwenderListe, aktuellerAnwender,
						new GruppeErstellenCallback());
			} else {
				gruppe.setName(gruppenameTB.getValue());
				kinoplaner.updateGruppe(gruppe, anwenderListe, aktuellerAnwender, new UpdateGruppeCallback());
			}

		}
	}

	/***********************************************************************
	 * CALLBACKS
	 ***********************************************************************/

	/**
	 * Callback wird durch den SpeichernClickHandler aufgerufen. Der Name der Gruppe
	 * wird geupdated. Ist dies erfolgreich geschehen, wird der User auf die
	 * GruppenAnzeigenForm weitergeleitet und sieht eine Übersicht seiner Gruppen -
	 * darunter die geupdatete Gruppe mit neuem Namen.
	 *
	 */
	private class UpdateGruppeCallback implements AsyncCallback<Gruppe> {

		@Override
		public void onFailure(Throwable caught) {
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(Gruppe result) {
			if (result != null) {
				RootPanel.get("details").clear();
				gruppenAF = new GruppenAnzeigenForm();
				RootPanel.get("details").add(gruppenAF);

			} else {
				Window.alert("Gruppenname bereits vergeben!");
			}

		}

	}

	/*******************************************************
	 * 
	 * Hier wird ein Gruppenmitglied anhand seiner Gruppe gesucht, wird nur gemacht
	 * wenn die Gruppe bearbeitet wird, nicht wenn sie erstellt wird
	 *
	 **********************************************************/

	private class SucheGruppenmitgliederByGruppeCallback implements AsyncCallback<ArrayList<Anwender>> {

		@Override
		public void onFailure(Throwable caught) {
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(ArrayList<Anwender> result) {
			for (Anwender a : result) {
				if (a.equals(AktuellerAnwender.getAnwender())) {

				} else {
					dataProvider.getList().add(a);
					anwenderListe.add(a);
				}
				dataProvider.refresh();

			}
		}

	}

	/********
	 * Der String des Namens wird aus der SuggestBox rausgeholt und mit dem Callback
	 * gesucht. Damit wird der passende Anwender zu dem Name zurückggeben um ihn der
	 * Gruppe hinzuzufügen
	 * 
	 * @author fiona
	 *
	 */

	private class AnwenderByNameCallback implements AsyncCallback<Anwender> {

		@Override
		public void onFailure(Throwable caught) {
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(Anwender result) {
			if (result == null) {
				Window.alert("Kein gültiger Anwender ausgewählt!");
				return;
			} else {
				if (result.getId() != AktuellerAnwender.getAnwender().getId()) {

					// Updaten des DataProviders

					dataProvider.getList().add(result);
					dataProvider.refresh();

					// Update der AnwenderListe

					anwenderListe.add(result);
				} else {
					Window.alert("Du bist bereits in der Gruppe!");
				}
			}

		}

	}

	/******
	 * Callback wird durch den SpeichernClickHandler aufgerufen. Wird benötigt, um
	 * eine neue Gruppe zu erstellen.
	 * 
	 *
	 */

	private class GruppeErstellenCallback implements AsyncCallback<Gruppe> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(Gruppe result) {

			if (result != null) {
				RootPanel.get("details").clear();
				gruppenAF = new GruppenAnzeigenForm();
				RootPanel.get("details").add(gruppenAF);

			} else {
				Window.alert("Gruppenname bereits vergeben!");
			}

		}

	}
}
