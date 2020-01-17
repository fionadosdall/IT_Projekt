package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import java.sql.Date;
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
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.client.gui.KinoketteErstellenForm.SpeichernClickHandler;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

public class SpielzeitErstellenForm extends VerticalPanel {
	
	private static Boolean edit1 = false;
	
	KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();
	
	private HorizontalPanel detailsoben = new HorizontalPanel();
	private HorizontalPanel detailsunten = new HorizontalPanel();
	private VerticalPanel inhaltObenPanel = new VerticalPanel();
	private HorizontalPanel inhaltUntenPanel = new HorizontalPanel();
	private VerticalPanel inhaltUntenLinksPanel = new VerticalPanel();
	private VerticalPanel inhaltUntenRechtsPanel = new VerticalPanel();
	
	private FlowPanel speichernBox = new FlowPanel();
	private FlowPanel detailsBoxObenMitte = new FlowPanel();
	private FlowPanel detailsBoxMitteMitte = new FlowPanel();
	private FlowPanel detailsBoxMitteUnten = new FlowPanel();
	private FlowPanel detailsBoxUntenMitte = new FlowPanel();
	private FlowPanel detailsObenBox = new FlowPanel();
	private FlowPanel detailsMitteBox = new FlowPanel();
	private FlowPanel detailsBoxUnten = new FlowPanel();

	
	private Label title = new Label ("Neue Spielzeit erstellen");
	private Label spielzeitname = new Label ("Name: ");
	private Label spielzeit = new Label ("Spielzeit ");
	private Label datum = new Label ("Datum: ");
	private Label vorstellung = new Label ("Spielzeit einer Vorstellung hinzufügen");
	private Label vorstellungen = new Label ("Vorstellungen");
	

	private TextBox spielzeitnameTB = new TextBox();
	private TextBox spielzeitTB = new TextBox();
	private DateBox dateBox = new DateBox();
	
	public static Boolean edit = false;

	private MultiWordSuggestOracle alleVorstellungenOracle = new MultiWordSuggestOracle();
	private SuggestBox vorstellungTB = new SuggestBox(alleVorstellungenOracle);
	
	private SpielzeitErstellenForm bearbeiten;
		
	
	private ArrayList<Vorstellung> vorstellung2TB = new ArrayList<Vorstellung>();
	
	private Grid spielzeitGrid = new Grid (3, 2);
	
	private Button hinzufuegenButton = new Button("Hinzufügen");
	private Button entfernenButton = new Button ("Vorstellung entfernen");
	private Button speichernButton = new Button("Speichern");
	
	private Spielzeit spz;
	
	private CellTable<Vorstellung> vorstellungCellTable = new CellTable<Vorstellung>(KEY_PROVIDER);
	
	private ListDataProvider<Vorstellung> dataProvider = new ListDataProvider<Vorstellung>();
	
	private List<Vorstellung> list = dataProvider.getList();
	
	private static final ProvidesKey<Vorstellung> KEY_PROVIDER = new ProvidesKey<Vorstellung>() {

		@Override
		public Object getKey(Vorstellung vorstellung) {
			// TODO Auto-generated method stub
			return vorstellung.getId();
		}
		
	};
	
	private Vorstellung neueVorstellung = null;
	private Spielzeit spielzeit2 = null;
	
	private SpielzeitBearbeitenForm spielezeitenF;
	
	/** Kunstruktor zur Übergabe des zu bearbeiteden Spielplan **/
	
	public SpielzeitErstellenForm(Spielzeit spz) {
		this.spz = spz;
	}
	
	/** Default-Konstruktor **/
	
	public SpielzeitErstellenForm() {
		
	}
	
	public void onLoad() {
		
	// Vergeben der Stylenames
	
		this.addStyleName("detailscontainer");
		this.addStyleName("center");
		
		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");
		spielzeitnameTB.addStyleName("formularTextBox");

		detailsObenBox.addStyleName("detailsObenBoxen");
		detailsMitteBox.addStyleName("detailsMitteBoxen");
		detailsBoxUnten.addStyleName("detailsuntenBoxen");

		speichernBox.addStyleName("speichernBox");
		detailsBoxObenMitte.addStyleName("detailsBoxObenMitte");
		detailsBoxMitteMitte.addStyleName("detailsBoxMitteMitte");
		detailsBoxMitteUnten.addStyleName("detailsBoxMitteUnten");
		detailsBoxUntenMitte.addStyleName("detailsBoxUntenMitte");
		detailsBoxUnten.addStyleName("detailsBoxUnten");
		
		title.addStyleName("title");
		spielzeitname.addStyleName("detailsboxLabels");
		spielzeit.addStyleName("detailsboxLabels");
		datum.addStyleName("detailsboxLabels");
		
		spielzeitnameTB.addStyleName("nameTB");
		spielzeitTB.addStyleName("SpielzeitTB");
		dateBox.addStyleName("DatumB");
		vorstellungTB.addStyleName("VorstellungTB");
		
		hinzufuegenButton.addStyleName("hinzufuegenButton");
		entfernenButton.addStyleName("entfernenButton");
		speichernButton.addStyleName("speichernButton");
		
		spielzeitnameTB.getElement().setPropertyString("placeholder", "Name eingeben");
		spielzeitTB.getElement().setPropertyString("placeholder", "Spielzeit eingeben");
		vorstellungTB.getElement().setPropertyString("placeholder", "Vorstellung eingeben");
		
		// Zusammenbauen der Widgets
		
		this.add(detailsoben);
		//this.add(detailsunten);
		
		spielzeitGrid.setWidget(0, 0, spielzeitname);
		spielzeitGrid.setWidget(0, 1, spielzeitnameTB);
		
		spielzeitGrid.setWidget(1, 0, spielzeit);
		spielzeitGrid.setWidget(1, 1, spielzeitTB);
		
		spielzeitGrid.setWidget(2, 0, datum);
		spielzeitGrid.setWidget(2, 1, dateBox);
		

		inhaltObenPanel.add(spielzeitGrid);
		this.add(inhaltObenPanel);
		
		if(edit1 == true) {
			
			inhaltUntenPanel.add(speichernButton);
			inhaltUntenPanel.add(hinzufuegenButton);
			inhaltUntenPanel.add(entfernenButton);
		}else {
			clearFormular();
			inhaltUntenPanel.add(hinzufuegenButton);
		}
		
		this.add(hinzufuegenButton);
		
/*
		detailsoben.add(title);

		detailsunten.add(detailsObenBox);
		detailsunten.add(detailsMitteBox);
		detailsunten.add(detailsBoxUnten);

		detailsObenBox.add(spielzeitname);
		detailsObenBox.add(detailsBoxObenMitte);
		detailsBoxObenMitte.add(spielzeitnameTB);

		detailsMitteBox.add(vorstellung);
		detailsMitteBox.add(detailsBoxMitteMitte);
		detailsBoxMitteMitte.add(vorstellungTB);
		detailsMitteBox.add(detailsBoxMitteUnten);
		detailsBoxMitteUnten.add(hinzufuegenButton);

		detailsUntenBox.add(vorstellungen);
		detailsUntenBox.add(detailsBoxUntenMitte);
		detailsBoxUntenMitte.add(vorstellungCellTable);
		detailsUntenBox.add(detailsBoxUnten);
		detailsBoxUnten.add(entfernenButton);

		if(edit1 == true) {
			//detailsunten.add(loeschenButton);
			detailsunten.add(speichernButton);
		} else {
			//clearForm();
			detailsunten.add(speichernButton);
		}
		speichernBox.add(speichernButton);
	*/	
		vorstellungCellTable.setEmptyTableWidget(new Label("Es wurde noch keine Vorstellung hinzugefügt "));
		
		
		/* ClickHandler */
		
/*		private class HinzufuegenClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				kinoplaner.erstellenSpielzeit(spielzeitnameTB.getText(), spielzeitTB.getText(),
						dateBox.getDatePicker(), spielzeitbearbTB.getText());
								
			}
			
		}
		
*/
		
		// Alle Vorstellungen die im System vorhanden sind werden geladen
		kinoplaner.getAllVorstellungen(new AsyncCallback<ArrayList<Vorstellung>>() {

			@Override
			public void onFailure(Throwable caught) {
				Systemmeldung.anzeigen("Vorstellung konnte nicht geladen werden");
				
			}

			@Override
			public void onSuccess(ArrayList<Vorstellung> result) {
				for (Vorstellung v: result) {
					//vorstellungTB.add(v);
					alleVorstellungenOracle.add(v.getName());
				}
				
			}
	});
		
/**
 * CELL TABLE
 * 
 */
		
		TextCell namenTextCell = new TextCell();
		
		Column <Vorstellung, String> namenColumn = new Column <Vorstellung, String>(namenTextCell) {

			@Override
			public String getValue(Vorstellung vorstellung) {
				// TODO Auto-generated method stub
				return vorstellung.getName();
			}
			
		};
		
	
		Cell<String> loeschenCell = new ButtonCell();
		
		Column<Vorstellung, String> loeschenColumn = new Column<Vorstellung, String>(loeschenCell) {

			@Override
			public String getValue(Vorstellung object) {
				// TODO Auto-generated method stub
				return "-";
			}
			
		};
		
		
		loeschenColumn.setFieldUpdater(new FieldUpdater<Vorstellung, String>() {

			@Override
			public void update(int index, Vorstellung vorstellung, String value) {
				// TODO Auto-generated method stub
				dataProvider.getList().remove(vorstellung);
				
				AsyncCallback<Vorstellung> loeschenCallback = new AsyncCallback<Vorstellung> () {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(Vorstellung result) {
						// TODO Auto-generated method stub
						
					}
					
				};
				
				// kinoplaner.
				
			}
			
		});
		
		namenColumn.setFieldUpdater(new FieldUpdater<Vorstellung,String> () {

			@Override
			public void update(int index, Vorstellung vorstellung, String name) {
				// TODO Auto-generated method stub
				vorstellung.setName(name);
			}
		
		});
		
	vorstellungCellTable.addColumn(namenColumn, "Vorstellung hinzufügen");
	vorstellungCellTable.addColumn(loeschenColumn, "Vorstellung entfernen");
	vorstellungCellTable.setColumnWidth(namenColumn, 20, Unit.PC);
	vorstellungCellTable.setColumnWidth(loeschenColumn, 20, Unit.PC);
	
	dataProvider.addDataDisplay(vorstellungCellTable);
/**
 * Bei der Instanziierung  wird der ClickHandler dem Button und dem Panel hinzugefügt
 */	
	
/**
 * CLICKHANDLER
 * 
 *
 */
	
		
		
	}
	private void clearFormular() {
		// TODO Auto-generated method stub
		
		spielzeitnameTB.setText("");
		spielzeitTB.setText("");
		dateBox.setTitle("");
		
	}
	private class VorstellungHinzufuegenClickHandler implements ClickHandler {

	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub
		
		vorstellungTB.setText("");
		
			}
	}
	
	
	private class SpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
	//kinoplaner.erstellenSpielzeit(spielzeitnameTB, zeit, new SpielzeitErstellenCallback() );
			
		}
		
	}
	
	//private class VorstellungEntfernenClickHandler implements ClickHandler {

		//@Override
		//public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
		//}
		
	//}
	
/**
 * Callbacks
 * @author fiona
 *
 */
	
private class VorstellungCallback implements AsyncCallback<Vorstellung> {

	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		Systemmeldung.anzeigen("SpielzeitCallback funktioniert nicht");
		
	}

	@Override
	public void onSuccess(Vorstellung vorstellung) {
		
		neueVorstellung = vorstellung;
		vorstellung.getName();
		
		//kinoplaner.erstellenSpielzeit(neueSpielzeit,date, new SpielzeitHinzufuegenCallback());
		
		// Update des DataProviders
		
		dataProvider.getList().add(neueVorstellung);
		dataProvider.refresh();
	}
	
}
	
	
	
	private class VorstellungHinzufuegenCallback implements AsyncCallback<Vorstellung> {

	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		Systemmeldung.anzeigen("VorstellungHinzufügenCallback funktioniert nicht");
	}

	@Override
	public void onSuccess(Vorstellung result) {
		// TODO Auto-generated method stub
		Systemmeldung.anzeigen("Vorstellung wurde hinzugefügt");
	}
	}
	
	
private class VorstellungEntfernenCallback implements AsyncCallback<Vorstellung> {

	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(Vorstellung result) {
		// TODO Auto-generated method stub
		
	}
	
}
	
		
	/* Callback */
				
	private class SpielzeitErstellenCallback implements AsyncCallback<Spielzeit> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Eine neue Spielzeit konnte leider nicht erstellt werden");
		}

		@Override
		public void onSuccess(Spielzeit result) {
			// TODO Auto-generated method stub
			
			if (spielzeitnameTB.getValue() == "") {
				Systemmeldung.anzeigen("Es wurde kein Spielzeitname eingegeben");
			}else {
				
				RootPanel.get("details").clear();
				
			}
			
		
				
			}
		}
		
	}



