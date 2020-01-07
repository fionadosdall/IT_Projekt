package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
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
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;

public class GruppeErstellenForm extends FlowPanel {

	KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

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

	private MultiWordSuggestOracle alleAnwenderOracle = new MultiWordSuggestOracle();
	private SuggestBox mitgliedTB = new SuggestBox(alleAnwenderOracle);

	private ArrayList<Anwender> anwenderTB = new ArrayList<Anwender>();

	private Button hinzufuegenButton = new Button("Hinzufügen");
	private Button entfernenButton = new Button("Mitglied entfernen");
	private Button speichernButton = new Button("Speichern");

	private CellTable<Anwender> anwenderCellTable = new CellTable<Anwender>(KEY_PROVIDER);

	private ListDataProvider<Anwender> dataProvider = new ListDataProvider<Anwender>();
	private List<Anwender> list = dataProvider.getList();

	private static final ProvidesKey<Anwender> KEY_PROVIDER = new ProvidesKey<Anwender>() {

		@Override
		public Object getKey(Anwender anwender) {
			// TODO Auto-generated method stub

			return anwender.getId();
		}

	};

	private Anwender neuerAnwender = null;
	private Gruppe gruppe = null;

	private GruppenAnzeigenForm gruppenAF;

	public void onLoad() {

		// Vergeben der Stylenames

		this.addStyleName("detailscontainer");

		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");

		detailsObenBox.addStyleName("detailsObenBoxen");
		detailsMitteBox.addStyleName("detailsMitteBoxen");
		detailsUntenBox.addStyleName("detailsUntenBoxen");

		speichernBox.addStyleName("speichernBox");
		detailsBoxObenMitte.addStyleName("detailsBoxObenMitte");
		detailsBoxMitteMitte.addStyleName("detailsBoxMitteMitte");
		detailsBoxMitteUnten.addStyleName("detailsBoxMitteUnten");
		detailsBoxUntenMitte.addStyleName("detailsBoxUntenMitte");
		detailsBoxUnten.addStyleName("detailsBoxUnten");

		title.addStyleName("title");
		gruppenname.addStyleName("detailsboxLabels");
		mitglied.addStyleName("detailsboxLabels");
		mitglieder.addStyleName("detailsboxLabels");

		gruppenameTB.addStyleName("gruppenameTB");
		mitgliedTB.addStyleName("nameTextBox");

		hinzufuegenButton.addStyleName("hinzufuegenButton");
		entfernenButton.addStyleName("entfernenButton");
		speichernButton.addStyleName("speichernButton");

		gruppenameTB.getElement().setPropertyString("placeholder", "Gruppenname eingeben");
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
		detailsBoxMitteUnten.add(hinzufuegenButton);

		detailsUntenBox.add(mitglieder);
		detailsUntenBox.add(detailsBoxUntenMitte);
		detailsBoxUntenMitte.add(anwenderCellTable);
		detailsUntenBox.add(detailsBoxUnten);
		detailsBoxUnten.add(entfernenButton);

		detailsunten.add(speichernBox);
		speichernBox.add(speichernButton);
		
		anwenderCellTable.setEmptyTableWidget(new Label("Es wurde noch kein Mitglied hinzugefügt"));

		// Click-Handler
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
					anwenderTB.add(u);
					alleAnwenderOracle.add(u.getName());
				}

			}
		});

		/***********************************************************************
		 * CELL TABLE
		 ***********************************************************************/

		TextCell namenTextCell = new TextCell();

		Column<Anwender, String> namenColumn = new Column<Anwender, String>(namenTextCell) {

			@Override
			public String getValue(Anwender anwender) {
				// TODO Auto-generated method stub

					return anwender.getName();

			}

		};

		Cell<String> loeschenCell = new ButtonCell();

		Column<Anwender, String> loeschenColumn = new Column<Anwender, String>(loeschenCell) {

			@Override
			public String getValue(Anwender object) {
				// TODO Auto-generated method stub
				return "-";
			}

		};

		loeschenColumn.setFieldUpdater(new FieldUpdater<Anwender, String>() {

			@Override
			public void update(int index, Anwender anwender, String value) {
				// TODO Auto-generated method stub
				dataProvider.getList().remove(anwender);

				AsyncCallback<Anwender> loeschenCallback = new AsyncCallback<Anwender>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Anwender result) {
						// TODO Auto-generated method stub

					}

				};

				kinoplaner.gruppenmitgliedEntfernen(anwender, loeschenCallback);
			}

		});

		namenColumn.setFieldUpdater(new FieldUpdater<Anwender, String>() {

			@Override
			public void update(int index, Anwender anwender, String name) {
				// TODO Auto-generated method stub
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

	private class MitgliedHinzufuegenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			kinoplaner.getAnwenderByName(mitgliedTB.getValue(), new AnwenderCallback());
			mitgliedTB.setText("");

		}

	}

	private class SpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			kinoplaner.erstellenGruppe(gruppenameTB.getValue(), new GruppeErstellenCallback());

		}
	}

	/***********************************************************************
	 * CALLBACKS
	 ***********************************************************************/

	private class AnwenderCallback implements AsyncCallback<Anwender> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("AnwenderCallback funktioniert nicht");

		}

		public void onSuccess(Anwender anwender) {
			// TODO Auto-generated method stub

			neuerAnwender = anwender;
			anwender.getName();

			kinoplaner.gruppenmitgliedHinzufuegen(neuerAnwender, new AnwenderHinzufuegenCallback());

			// Updaten des DataProviders
			dataProvider.getList().add(neuerAnwender);
			dataProvider.refresh();

		}

	}

	private class AnwenderHinzufuegenCallback implements AsyncCallback<Anwender> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("AnwenderHinzufügenCallback funktioniert nicht");

		}

		@Override
		public void onSuccess(Anwender result) {
			// TODO Auto-generated method stub
			Window.alert("Gruppenmitglied wurde hinzugefügt");

		}

	}

//	private class MitgliedEntfernenCallback implements AsyncCallback<Anwender> {
//
//		@Override
//		public void onFailure(Throwable caught) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onSuccess(Anwender result) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//	}

	private class GruppeErstellenCallback implements AsyncCallback<Gruppe> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Gruppe erstellen ist fehlgeschlagen");

		}

		@Override
		public void onSuccess(Gruppe result) {
			// TODO Auto-generated method stub

			if (gruppenameTB.getValue() == "") {
				Window.alert("Es wurde kein Gruppenname eingegeben");
			} else {
				RootPanel.get("details").clear();
				gruppenAF = new GruppenAnzeigenForm();
				RootPanel.get("details").add(gruppenAF);

			}

		}

	}
}
