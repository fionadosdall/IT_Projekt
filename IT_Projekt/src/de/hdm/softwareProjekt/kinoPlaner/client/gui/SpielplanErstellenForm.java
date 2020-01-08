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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielplan;

public class SpielplanErstellenForm extends FlowPanel {
	
	

	public static Boolean edit = false;

	private int kinoId;

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
	
	
	private Label spielplanformLabel = new Label("Neuen Spielplan erstellen");
	private Label spielplanBearbeitenFormLabel = new Label("Spielplan bearbeiten");
	private Label spielplanname = new Label ("Spielplanname");
	private Label vorstellung = new Label ("Vorstellung hinzufügen");
	private Label vorstellungen = new Label ("Spielplan-Vorstellungen");
	
	private TextBox spielplannameTB = new TextBox();
	
	private SpielplanErstellenForm bearbeiten;
	
	private MultiWordSuggestOracle alleKinosOracle = new MultiWordSuggestOracle ();
	private SuggestBox vorstellungTB = new SuggestBox(alleKinosOracle);
	
	private ArrayList<Kino> kinoTB = new ArrayList<Kino>();
	
	
	private Button hinzufuegenButton = new Button("Hinzufügen");
	private Button entfernenButton = new Button ("entfernen");
	private Button speichernButton = new Button("Speichern");

	private Spielplan sp;
	
	private CellTable<Kino> kinoCellTable = new CellTable<Kino>(KEY_PROVIDER);
	
	private ListDataProvider<Kino> dataProvider = new ListDataProvider<Kino>();
	private List<Kino> list = dataProvider.getList();
	
	private static final ProvidesKey<Kino> KEY_PROVIDER = new ProvidesKey<Kino>() {
		
		public Object getKey (Kino kino) {
			return kino.getId();
		}
	};
	
	
	private Kino neuesKino = null;
	private Spielplan spielplan = null;
	
	private MeineSpielplaeneForm spielplaeneF;
	
	/** Konstruktor zur Übergabe des zu bearbeitenden eines Spielplans **/
	
	public SpielplanErstellenForm(Spielplan sp) {
		this.sp = sp;
	}
	
	/** Default-Konstruktor **/
	
	public SpielplanErstellenForm() {
		
	}

	public void onLoad() {
		
		// Vergeben der Stylenamen
		
		this.addStyleName("center");
		this.addStyleName("detailscontainer");

		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");
		spielplannameTB.addStyleName("formularTextBox");
		vorstellungTB.addStyleName("formularSearchTextBox");
		
		hinzufuegenButton.addStyleName("hinzufuegenButton");
		entfernenButton.addStyleName("entfernenButton");
		speichernButton.addStyleName("speichernButton");

		detailsObenBox.addStyleName("detailsuntenBoxen");
		detailsMitteBox.addStyleName("detailsuntenBoxen");
		detailsUntenBox.addStyleName("detailsuntenBoxen");

		speichernBox.addStyleName("speichernBox");
		detailsBoxObenMitte.addStyleName("detailsBoxMitte");
		detailsBoxMitteMitte.addStyleName("detailsBoxMitte");
		detailsBoxMitteUnten.addStyleName("detailsBoxMitte");
		detailsBoxUntenMitte.addStyleName("detailsBoxMitte");
		detailsBoxUnten.addStyleName("detailsBoxUnten");
		
		spielplanformLabel.addStyleName("formHeaderLabel");
		spielplanBearbeitenFormLabel.addStyleName("formHeaderLabel");
		spielplanname.addStyleName("detailsboxLabels");
		vorstellung.addStyleName("detailsboxLabels");
		vorstellungen.addStyleName("detailsboxLabels");
		
		spielplannameTB.getElement().setPropertyString("placeholder", "Spielplanname eingeben");
		vorstellungTB.getElement().setPropertyString("placeholder",  "Vorstellung suchen");
		
		
		// Zusammenbauen der Widgets
		
		if(edit == true) {
			
			detailsoben.add(spielplanBearbeitenFormLabel);
		}else {
			detailsoben.add(spielplanformLabel);
			clearForm();
		}
		
		this.add(detailsoben);
		this.add(detailsunten);

		

		detailsunten.add(detailsObenBox);
		detailsunten.add(detailsMitteBox);
		detailsunten.add(detailsUntenBox);
		
		detailsObenBox.add(spielplanname);
		detailsObenBox.add(detailsBoxObenMitte);
		detailsBoxObenMitte.add(spielplannameTB);
		
		detailsMitteBox.add(vorstellung);
		detailsMitteBox.add(detailsBoxMitteMitte);
		detailsBoxMitteMitte.add(vorstellungTB);
		detailsMitteBox.add(detailsBoxMitteUnten);
		detailsBoxMitteUnten.add(hinzufuegenButton);
		
		detailsUntenBox.add(vorstellungen);
		detailsUntenBox.add(detailsBoxUntenMitte);
		detailsBoxUntenMitte.add(kinoCellTable);
		detailsUntenBox.add(detailsBoxUnten);
		detailsBoxUnten.add(entfernenButton);
		
		detailsunten.add(speichernBox);
		speichernBox.add(speichernButton);
		
		//Click Handler
		
		hinzufuegenButton.addClickHandler(new KinoHinzufuegenClickHandler());
		//entfernenButton.addClickHandler(new KinoEntfernenClickHandler());
		speichernButton.addClickHandler(new SpeichernClickHandler());
		
		
		
		// Alle Kinos die im System vorhanden sind werden geladen
		
		kinoplaner.getAllKinos(new AsyncCallback<ArrayList<Kino>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Systemmeldung.anzeigen("Kino konnten nicht geladen werden");
				
			}

			@Override
			public void onSuccess(ArrayList<Kino> result) {
				for ( Kino u: result) {
					kinoTB.add(u);
					alleKinosOracle.add(u.getName());
				}
				
			}
			
		});
		
		
		/***********************************************************
		 * CELL TABLE
		 */
		
		
		TextCell namenTextCell = new TextCell();
		
		Column<Kino, String> namenColumn = new Column<Kino, String>(namenTextCell) {

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
		
		Column <Kino, String> loeschenColumn = new Column <Kino, String>(loeschenCell) {

			@Override
			public String getValue(Kino object) {
				// TODO Auto-generated method stub
				return "-";
			}
			
		};
		
		loeschenColumn.setFieldUpdater(new FieldUpdater <Kino, String>() {

			@Override
			public void update(int index, Kino kino, String value) {
				// TODO Auto-generated method stub
				dataProvider.getList().remove(kino);
				
				AsyncCallback<Kino> loeschenCallback = new AsyncCallback <Kino>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(Kino result) {
						// TODO Auto-generated method stub
						
					}
					
				};
				
				//TODO kinoplaner.kinoketteEntfernen(kino, loeschenCallback);
				
			}
			
		});
		
		namenColumn.setFieldUpdater(new FieldUpdater<Kino, String>() {

			@Override
			public void update(int index, Kino kino, String name) {
				// TODO Auto-generated method stub
				kino.setName(name);
			}
			
		});
		
		kinoCellTable.addColumn(namenColumn, "Vorstellungen");
		kinoCellTable.addColumn(loeschenColumn, "Vorstellung entfernen");
		kinoCellTable.setColumnWidth(namenColumn, 20, Unit.PC);
		kinoCellTable.setColumnWidth(loeschenColumn, 20, Unit.PC);
		
		dataProvider.addDataDisplay(kinoCellTable);
	}
	
	
	/**************************
	 * CLICKHANDLER
	 */
	
	
	private class KinoHinzufuegenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			//TODO kinoplaner.getVorstellungenBySpielplan(vorstellungTB.getValue(), new KinoCallback());
			vorstellungTB.setText("");
			
		}
		
	}
	
	
	private class SpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			kinoplaner.erstellenSpielplanKino(spielplannameTB.getValue(), kinoId, new SpielplanErstellenCallback());
			
			
		}
		
	}
	
	private class KinoketteBearbeitenClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			//Kinokette ausgewaehlteKinokette = felder.getSelectionModel().getSelected();
			
			KinoketteErstellenForm.setEdit(edit);
			bearbeiten = new SpielplanErstellenForm(sp);
			//SpielplanErstellenForm.setBearbeiten();
			RootPanel.get("details").add(bearbeiten);
		}
		
	}
	
	
	/*****
	 * CALLBACKS
	 */
	
	private class KinoCallback implements AsyncCallback <Kino> {

		@Override
		public void onFailure(Throwable caught) {
			
			Systemmeldung.anzeigen("KinoCallback funktioniert nicht");
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Kino kino) {
			// TODO Auto-generated method stub
			
			neuesKino = kino;
			kino.getName();
			
			//TODO kinoplaner.kinoHinzufuegen(neuesKino, new KinoHinzufuegenCallback());
			
			//Updaten des DataProviders
			
			dataProvider.getList().add(neuesKino);
			dataProvider.refresh();
			
		}
		
	}
	
	
	private class KinoHinzufuegenCallback implements AsyncCallback<Kino> {

		@Override
		public void onFailure(Throwable caught) {
			
			Systemmeldung.anzeigen("KinoHinzufügenCallback funktioniert nicht");
			
		}

		@Override
		public void onSuccess(Kino result) {
			
			
			Systemmeldung.anzeigen("Kino wurde hinzugefügt");
			
		}
		
	}
	
	
	//private class KinoEntfernenCallback implements AsyncCallback<Kino> {

		//@Override
		//public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
	//	}

	//	@Override
		//public void onSuccess(Kino result) {
			// TODO Auto-generated method stub
			
	//	}
		
	//}
	
	
	
	

	
	
	

		
	/* Callback */
				
	private class SpielplanErstellenCallback implements AsyncCallback<Spielplan> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Eine neuer Spielplan konnte leider nicht erstellt werden");
		}

		@Override
		public void onSuccess(Spielplan result) {
			// TODO Auto-generated method stub
			
			if (spielplannameTB.getValue() == "" ) {
				Systemmeldung.anzeigen("Es wurde kein Spielplanname eingegebnen");
			} else {
				RootPanel.get("details").clear();
				spielplaeneF =new MeineSpielplaeneForm();
				RootPanel.get("details").add(spielplaeneF);
			}
			
		}
		
	}
	
	/**Methoden***/
	
	public Boolean getEdit() {
		return edit;
	}

	public static void setEdit(Boolean edit) {
		SpielplanErstellenForm.edit = edit;
	}

	
	public static void setBearbeiten(Spielplan spielplan) {
		
		
		
			
		
	} 
	
	public void clearForm() {
		spielplannameTB.setText("");
		
	}
	
	
}
	

