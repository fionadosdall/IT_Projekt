package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import java.util.ArrayList;

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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;

public class KinoBearbeitenForm extends FlowPanel{

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
	
	
	private Label title = new Label("Kino bearbeiten");
	private Label kinoname = new Label("Kinoname:");
	private Label kinokette = new Label ("Kinokette hinzufügen");
	private Label kinoketten = new Label ("Kinoketten");
	private Label spielplan = new Label ("Spielplan hinzufügen");
	private Label spielplaene = new Label ("Spielplaene");

	private TextBox kinonameTB = new TextBox();
	
	private MultiWordSuggestOracle alleKinokettenOracle = new MultiWordSuggestOracle ();
	private SuggestBox kinoketteTB = new SuggestBox (alleKinokettenOracle);
	
	private ArrayList <Kinokette> kinokettenTB = new ArrayList<Kinokette>();
	
	
	private Button hinzufuegenButton = new Button("Hinzufügen");
	private Button entfernenButton = new Button("Kinokette entfernen");
	private Button speichernButton = new Button("Speichern");
	
	private Image papierkorb = new Image();

	private ProvidesKey<Kinokette> KEY_PROVIDER;
	
	private CellTable<Kinokette> kinoketteCellTable = new CellTable<Kinokette>(KEY_PROVIDER);
	
	private ListDataProvider<Kinokette> dataProvider = new ListDataProvider<Kinokette>();
	
	
	 
	
	private Kinokette neueKinokette = null;
	private Kino kino;
	
	MeineKinosForm kf = new MeineKinosForm();
	
	private MeineKinosForm kinosF;
	
	public KinoBearbeitenForm(Kino kino) {
		this.kino = kino;
	}
	
	public void onLoad() {
	
	// Vergeben der Stylenames
	
	
	
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
	kinoname.addStyleName("detailsboxLabels");
	kinokette.addStyleName("detailsboxLabels");
	kinoketten.addStyleName("detailsboxLabels");
	
	kinonameTB.addStyleName("kinoTB");
	kinoketteTB.addStyleName("nameTextBox");
	
	hinzufuegenButton.addStyleName("hinzufuegenButton");
	entfernenButton.addStyleName("entfernenButton");
	speichernButton.addStyleName("speichernButton");
	
	kinonameTB.getElement().setPropertyString("placeholder", "Kinoname: ");
	kinoketteTB.getElement().setPropertyString("placeholder", "Kinokette suchen: ");

	papierkorb.setUrl("/images/papierkorb.png");
	
	
	//Zusammenbauen der Widgets
	
	this.add(detailsoben);
	this.add(detailsunten);

	detailsoben.add(title);

	detailsunten.add(detailsObenBox);
	detailsunten.add(detailsMitteBox);
	detailsunten.add(detailsUntenBox);

	detailsObenBox.add(kinoname);
	detailsObenBox.add(detailsBoxObenMitte);
	detailsBoxObenMitte.add(kinonameTB);

	detailsMitteBox.add(kinokette);
	detailsMitteBox.add(detailsBoxMitteMitte);
	detailsBoxMitteMitte.add(kinoketteTB);
	detailsMitteBox.add(detailsBoxMitteUnten);
	detailsBoxMitteUnten.add(hinzufuegenButton);

	detailsUntenBox.add(kinoketten);
	detailsUntenBox.add(detailsBoxUntenMitte);
	detailsBoxUntenMitte.add(kinoketteCellTable);
	detailsUntenBox.add(detailsBoxUnten);
	detailsBoxUnten.add(entfernenButton);

	detailsunten.add(speichernBox);
	speichernBox.add(speichernButton);
	

	// Click-Handler
	hinzufuegenButton.addClickHandler(new KinoketteHinzufuegenClickHandler());
	// entfernenButton.addClickHandler(new MitgliedEntfernenClickHandler());
	speichernButton.addClickHandler(new SpeichernClickHandler());
	papierkorb.addClickHandler(new KinoLoeschenClickHandler());
	
	// Alle Kinoketten die im System vorhanden sind werden geladen
	kinoplaner.getAllKinoketten(new AsyncCallback<ArrayList<Kinokette>>() {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Kinoketten konnte nicht geladen werden");
		}

		@Override
		public void onSuccess(ArrayList<Kinokette> result) {
			for (Kinokette u : result) {
				kinokettenTB.add(u);
				alleKinokettenOracle.add(u.getName());
			}
			
		}
		
	});
	
	// Hier msss noch das Kino aus der listbox gezogen werden
	/* kinoplaner.getKinoById(kino, new AsyncCallback<ArrayList<Kinokette>>() {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Kinokeete für Kino konnte nicht gefunden werden");
			
		}

		@Override
		public void onSuccess(ArrayList<Kinokette> kinokette) {
			
			
			for (Kinokette a : kinokette) {
				//TODO list.add(a);
			}
		}
		
	}); */
	
	/******
	 * CELLL TABLE
	 */
	
	TextCell namenTextCell = new TextCell();
	
	Column <Kinokette, String> namenColumn = new Column <Kinokette, String> (namenTextCell) {

		@Override
		public String getValue(Kinokette kinokette) {
			
			if (kinokette == null) {
				return "test";
			}else {
				
				return kinokette.getName();
			}
		}
		
	};
	
	Cell<String> loeschenCell = new ButtonCell();
	
	Column <Kinokette, String> loeschenColumn = new Column <Kinokette, String>(loeschenCell) {

		@Override
		public String getValue(Kinokette object) {
			// TODO Auto-generated method stub
			return "-";
		}
		
	};
	
	
	loeschenColumn.setFieldUpdater(new FieldUpdater <Kinokette, String> () {

		@Override
		public void update(int index, Kinokette kinokette, String value) {
			// TODO Auto-generated method stub
			dataProvider.getList().remove(kinokette);
			
			AsyncCallback<Kinokette> loeschenCallback = new AsyncCallback < Kinokette> () {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(Kinokette result) {
					// TODO Auto-generated method stub
					
				}
				
			};
			
			//TODO kinoplaner.kinoketteEntfernen(kinokette, loeschenCallback);
		}
		
	});
	
	namenColumn.setFieldUpdater(new FieldUpdater<Kinokette, String>() {

		@Override
		public void update(int index, Kinokette kinokette, String name) {
			// TODO Auto-generated method stub
			kinokette.setName(name);
			
		}

		
			
			
		});
	
		kinoketteCellTable.addColumn(namenColumn, "Kinokettem");
		kinoketteCellTable.addColumn(loeschenColumn, "Kinokette enternen");
		kinoketteCellTable.setColumnWidth(namenColumn, 20, Unit.PC);
		kinoketteCellTable.setColumnWidth(loeschenColumn, 20, Unit.PC);
		
		dataProvider.addDataDisplay(kinoketteCellTable);
		
	}
	
	
	/******
	 * CLICKHANDLER
	 */
	private class KinoketteHinzufuegenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
			//TODO kinoplaner.getKinoketteById(kinoketteTB.getValue(),new KinoketteCallback() );
			kinoketteTB.setText("");
			
			
		}
	}
	
	private class SpeichernClickHandler  implements ClickHandler  {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
			kinoplaner.speichern(kino, new KinoSpeichernCallback());
			
		}
		
	}
	
	private class KinoLoeschenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			kinoplaner.loeschen(kino, new LoeschenKinoCallback());
			
		}
		
		
}
	/*****
	 * CALLBACKS
	 */


	private class KinoketteCallback implements AsyncCallback<Kinokette> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("KinokettenCallback funktioniert nicht");
		}

		@Override
		public void onSuccess(Kinokette kinokette) {
			// TODO Auto-generated method stub
			
			neueKinokette = kinokette;
			kinokette.getName();
			
			
			//TODO kinoplaner.kinoDerKinoketteHinzufuegen(neueKinokette, new KinoketteHinzufuegenCallback());
			
			//Updaten des DataProvider
			
			dataProvider.getList().add(neueKinokette);
			dataProvider.refresh();
		}
		
	}
	
	private class KinoketteHinzufuegenCallback implements AsyncCallback<Kinokette> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("KinoketteHinzufügenCallback funktioniet nicht");
			
		}

		@Override
		public void onSuccess(Kinokette result) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Kinokette wurde hinzugefügt");
		}
		
	}
	
	private class KinoSpeichernCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			
			if (kinonameTB.getValue() == "") {
				Systemmeldung.anzeigen("Es wurde kein Kinoname eingegeben");
			}else {
				
				RootPanel.get("details").clear();
				kinosF = new MeineKinosForm();
				RootPanel.get("details").add(kinosF);
			}
			
		}
		
	}
	
	private class LoeschenKinoCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			
		}
		
		
	}}

