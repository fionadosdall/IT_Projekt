package de.hdm.softwareProjekt.kinoPlaner.client.gui;

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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.GruppeBearbeitenForm.GruppeLoeschenClickHandler;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.GruppeBearbeitenForm.MitgliedHinzufuegenClickHandler;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.GruppeBearbeitenForm.SpeichernClickHandler;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielplan;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;

public class SpielplanBearbeitenForm extends FlowPanel {
	
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
	
	
	private Label title = new Label ("Spielplan bearbeiten");
	private Label spielplanname = new Label ("Spielplanname");
	private Label kino = new Label ("Kino hinzufügen");
	private Label kinos = new Label ("Kinos");
	private Label spielzeitHinzufuegenLabel = new Label("Spielzeit  hinzuf&uuml;gen");
	private Label spielzeitBearbeitenLabel = new Label("Spielzeit bearbeiten");
	
	
	private TextBox spielplannameTB = new TextBox();
	
	private MultiWordSuggestOracle alleKinosOracle = new MultiWordSuggestOracle();
	private SuggestBox kinoTB = new SuggestBox (alleKinosOracle);
	
	
	private Button hinzufuegenButton = new Button("Hinzufügen");
	private Button entfernenButton = new Button("Kino entfernen");
	private Button speichernButton = new Button("Speichern");
	
	private Image papierkorb = new Image();
	
	private CellTable<Kino> kinoCellTable = new CellTable<Kino>(KEY_PROVIDER);
	
	private ListDataProvider <Kino> dataProvider = new ListDataProvider<Kino>();
	private List <Kino> list = dataProvider.getList();
	
	private static final ProvidesKey<Kino> KEY_PROVIDER = new ProvidesKey<Kino>() {

		@Override
		public Object getKey(Kino kino) {
			// TODO Auto-generated method stub
			return kino.getId();
		}
		 
	};
	
	private Kino neuesKino = null;
	
	private Spielplan spielplan;
	
	MeineSpielplaeneForm sf = new MeineSpielplaeneForm ();
	
	private MeineSpielplaeneForm spielplaeneF;
	
	public void onLoad() {
		//Vergeben der Stylenamens
		
		
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
		spielplanname.addStyleName("detailsboxLabels");
		kino.addStyleName("detailsboxLabels");
		kinos.addStyleName("detailsboxLabels");

		spielplannameTB.addStyleName("gruppenameTB");
		kinoTB.addStyleName("nameTextBox");

		hinzufuegenButton.addStyleName("hinzufuegenButton");
		entfernenButton.addStyleName("entfernenButton");
		speichernButton.addStyleName("speichernButton");

		spielplannameTB.getElement().setPropertyString("placeholder", "Gruppenname: " + gruppe.getName());
		kinoTB.getElement().setPropertyString("placeholder", "User suchen");

		papierkorb.setUrl("/images/papierkorb.png");
		
		//Zusammenbauen der Widgets
		
		this.add(detailsoben);
		this.add(detailsunten);

		detailsoben.add(title);

		detailsunten.add(detailsObenBox);
		detailsunten.add(detailsMitteBox);
		detailsunten.add(detailsUntenBox);

		detailsObenBox.add(spielplanname);
		detailsObenBox.add(detailsBoxObenMitte);
		detailsBoxObenMitte.add(spielplannameTB);

		detailsMitteBox.add(kino);
		detailsMitteBox.add(detailsBoxMitteMitte);
		detailsBoxMitteMitte.add(kinoTB);
		detailsMitteBox.add(detailsBoxMitteUnten);
		detailsBoxMitteUnten.add(hinzufuegenButton);

		detailsUntenBox.add(kinos);
		detailsUntenBox.add(detailsBoxUntenMitte);
		detailsBoxUntenMitte.add(kinoCellTable);
		detailsUntenBox.add(detailsBoxUnten);
		detailsBoxUnten.add(entfernenButton);

		detailsunten.add(speichernBox);
		speichernBox.add(speichernButton);

		// Click-Handler
		hinzufuegenButton.addClickHandler(new KinoHinzufuegenClickHandler());
		// entfernenButton.addClickHandler(new MitgliedEntfernenClickHandler());
		speichernButton.addClickHandler(new SpeichernClickHandler());
		papierkorb.addClickHandler(new SpielplanLoeschenClickHandler());
		
		// Alle Kinos die im System vorhanden sind werden geladen
		kinoplaner.getAllKinos(new AsyncCallback <ArrayList<Kino>> () {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Systemmeldung.anzeigen("Kino konnte nicht geladen werden");
			}

			@Override
			public void onSuccess(ArrayList<Kino> result) {
				
				for (Kino u: result) {
					kinoTB.add(u);
					alleKinosOracle.add(u.getName());
				}
				
			}
			
		});
		
		kinoplaner.getKinosByKinoketteId(kinokette, new AsyncCallback<ArrayList<Kino>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Systemmeldung.anzeigen("Kino für Spielplan konnnte nicht geladen werden");
			}

			@Override
			public void onSuccess(ArrayList<Kino> kino) {
				// TODO Auto-generated method stub
				
				
				for (Kino k: kino) {
					
					list.add(k);
				}
			}
			
		});
		
		/***
		 * CELL TABLE
		 */
		
		TextCell namenTextCell = new TextCell();
		
		Column <Kino, String> namenColumn = new Column <Kino, String> (namenTextCell) {

			@Override
			public String getValue(Kino kino) {
				
				if (kino == null) {
					return "test";
					
				} else {
					
					return kino.getName();
				}
			}
			
		};
		
		Cell <String> loeschenCell = new ButtonCell();
		
		Column <Kino, String> loeschenColumn = new Column <Kino, String> (loeschenCell ) {

			@Override
			public String getValue(Kino object) {
				// TODO Auto-generated method stub
				return "-";
			}
			
		};
		
		loeschenColumn.setFieldUpdater(new FieldUpdater<Kino, String>() {

			@Override
			public void update(int index, Kino kino, String value) {
				// TODO Auto-generated method stub
				
				dataProvider.getList().remove(kino);
				
				AsyncCallback <Kino> loeschenCallback = new AsyncCallback<Kino> () {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(Kino result) {
						// TODO Auto-generated method stub
						
					}
					
				};
				
			
				
			}
			
		});
		
		namenColumn.setFieldUpdater(new FieldUpdater <Kino, String> () {

			@Override
			public void update(int index, Kino kino, String name) {
				
				kino.setName(name);
				
			}
			
		});
		
		kinoCellTable.addColumn(namenColumn, "Kinos");
		kinoCellTable.addColumn(loeschenColumn, "Kino entfernen");
		kinoCellTable.setColumnWidth(namenColumn, 20, Unit.PC);
		kinoCellTable.setColumnWidth(loeschenColumn, 20, Unit.PC);
		
		dataProvider.addDataDisplay(kinoCellTable);
	}
	
	
	/*****
	 * CLICKHANDLER
	 */
	
	private class KinoHinzufuegenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			kinoplaner.getKinoById(kinoTB.getValue(), new KinoCallback());
			kinoTB.setText("");
		}
		
	}
	
	private class SpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
			kinoplaner.speichern(spielplan, new SpielplanSpeichernCallback());
		}
		
	}
	
	
	/***
	 * CALLBACKS
	 */
}
