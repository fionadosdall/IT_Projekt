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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
//TODO import de.hdm.softwareProjekt.kinoPlaner.client.gui.VorstellungErstellenForm.FilmCallback;
//TODO import de.hdm.softwareProjekt.kinoPlaner.client.gui.VorstellungErstellenForm.FilmHinzufuegenClickHandler;
//TODOimport de.hdm.softwareProjekt.kinoPlaner.client.gui.VorstellungErstellenForm.SpeichernClickHandler;
//TODOimport de.hdm.softwareProjekt.kinoPlaner.client.gui.VorstellungErstellenForm.VorstellungErstellenCallback;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

public class VorstellungBearbeitenForm extends FlowPanel {
	
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
	
	
	private Label title = new Label ("Vorstellung erstellen");
	private Label vorstellungLabel = new Label ("Vorstellung");
	private Label film = new Label ("Film hinzufügen");
	private Label filme = new Label ("Film-Vorstellungen");
	private Label spielzeit = new Label ("Spielzeit hinzufügen");
	private Label spielzeiten = new Label ("Spielzeiten");
	
	
	private TextBox vorstellungsnameTB = new TextBox ();
	
	private MultiWordSuggestOracle alleFilmeOracle = new MultiWordSuggestOracle();
	private SuggestBox filmTB = new SuggestBox (alleFilmeOracle);
	
	private ArrayList<Film> film2TB = new ArrayList<Film>();
	
	private Button hinzufuegenButton = new Button("Hinzufügen");
	private Button entfernenButton = new Button ("entfernen");
	private Button speichernButton = new Button("Speichern");

	private CellTable<Film> filmCellTable = new CellTable<Film>(KEY_PROVIDER);
	
	private ListDataProvider<Kino> dataProvider = new ListDataProvider<Kino>();
	private List<Kino> list = dataProvider.getList();
	
	private static final ProvidesKey <Film> KEY_PROVIDER = new ProvidesKey<Film>() {
		
		public Object getKey (Film film) {
			return film.getId();
	

		}
	};
	
	
	private Film neuerFilm = null;
	private Vorstellung vorstellung = null;
	
	
	private MeineVorstellungForm vorstellungF;
	
	public void onLoad( ) {
		
		this.addStyleName("detailscontainer");

		detailsoben.addStyleName("detailsOben");
		detailsunten.addStyleName("detailsUnten");

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
		vorstellungLabel.addStyleName("detailsboxLabels");
		film.addStyleName("detailsboxLabels");
		film.addStyleName("detailsboxLabels");
		
		
		vorstellungsnameTB.getElement().setPropertyString("placeholder", "Vorstellung eingeben");
		filmTB.getElement().setPropertyString("placeholder", "Film suchen");
		
		
		// Zusammenbauen der Widgets
		
		this.add(detailsoben);
		this.add(detailsunten);

		detailsoben.add(title);

		detailsunten.add(detailsObenBox);
		detailsunten.add(detailsMitteBox);
		detailsunten.add(detailsUntenBox);
		
		detailsObenBox.add(vorstellungLabel);
		detailsObenBox.add(detailsBoxObenMitte);
		detailsBoxObenMitte.add(vorstellungsnameTB);
		
		detailsMitteBox.add(film);
		detailsMitteBox.add(detailsBoxMitteMitte);
		detailsBoxMitteMitte.add(filmTB);
		detailsMitteBox.add(detailsBoxMitteUnten);
		detailsBoxMitteUnten.add(hinzufuegenButton);
		
		detailsUntenBox.add(film);
		detailsUntenBox.add(detailsBoxUntenMitte);
		detailsBoxUntenMitte.add(filmCellTable);
		detailsUntenBox.add(detailsBoxUnten);
		detailsBoxUnten.add(entfernenButton);
		
		detailsunten.add(speichernBox);
		speichernBox.add(speichernButton);
		
		//Click Handler
		
		hinzufuegenButton.addClickHandler(new FilmHinzufuegenClickHandler());
		//entfernen.addClickHandler (new FilmEntfernenClickHandler());
		speichernButton.addClickHandler (new  SpeichernClickHandler());
		
		
		// Alle Filme die im System vorhanden sind werden geladen
		
		
		kinoplaner.getAllFilme(new AsyncCallback<ArrayList<Film>>() {

			@Override
			public void onFailure(Throwable caught) {
				
				Systemmeldung.anzeigen("Film konnte nicht geladen werden");
				
			}

			@Override
			public void onSuccess(ArrayList<Film> result) {
				// TODO Auto-generated method stub
				
				for (Film u: result) {
					film2TB.add(u);
					alleFilmeOracle.add(u.getName());
					
				}
				
			}
			
		});
		
		
		/**
		 * CELL TABLE
		 */
		
		
		TextCell namenTextCell = new TextCell();
		
		Column<Film, String> namenColumn = new Column<Film, String>(namenTextCell) {

			@Override
			public String getValue(Film film) {
				
			    if  (film == null ) {
			    	return "test";
			    	
			    } else {
			    	return film.getName();
			    }
			}
			
		};
		
		
		Cell <String> loeschenCell = new ButtonCell();
		
		Column <Film, String> loeschenColumn = new Column <Film, String> (loeschenCell) {

			@Override
			public String getValue(Film object) {
				// TODO Auto-generated method stub
				return "-";
			}
			
		};
		
		
		loeschenColumn.setFieldUpdater(new FieldUpdater <Film, String>() {

			@Override
			public void update(int index, Film film, String value) {
				// TODO Auto-generated method stub
				
				dataProvider.getList().remove(film);
				
				AsyncCallback<Film> loeschenCallback = new AsyncCallback <Film> () {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(Film result) {
						// TODO Auto-generated method stub
						
					}
					
					
				};
				
				kinoplaner.loeschen(film, loeschenCallback);
				
			}
			
		});
		
		namenColumn.setFieldUpdater(new FieldUpdater <Film, String> () {

			@Override
			public void update(int index, Film film, String name) {
				
				film.setName(name);
			}
			
		});
		
		filmCellTable.addColumn(namenColumn, "Filme");
		filmCellTable.addColumn(loeschenColumn, "Film entfernen");
		filmCellTable.setColumnWidth(namenColumn, 20, Unit.PC);
		filmCellTable.setColumnWidth(loeschenColumn, 20, Unit.PC);
		
		//TODO dataProvider.addDataDisplay(filmCellTable);
	
	}
	
	
	/****
	 * CLICKHANDLER
	 */

	
	
	private class FilmHinzufuegenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
			//TODO	kinoplaner.getAllFilme(filmTB.getValue(), new FilmCallback());
			filmTB.setText("");
			
		}
		
	}
	
	private class SpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
			//TODO	kinoplaner.erstellenVorstellung(vorstellungsnameTB.getValue(), new VorstellungErstellenCallback());
			
		
		}
		
	}
	
	private class VorstellungLoeschenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			
			
			kinoplaner.loeschen(vorstellung, new LoeschenVorstellungCallback());
			
		}
		
	}
	
	
	/**
	 * CALLBACKS
	 * 
	 */
	
	
	private class FilmCallback implements AsyncCallback <Film> {

		@Override
		public void onFailure(Throwable caught) {
			
			Systemmeldung.anzeigen("FilmCallback funktioniert nicht");
			
		}

		@Override
		public void onSuccess(Film film) {
			
			
			neuerFilm = film;
			film.getName();
			
			//TODO kinoplaner.vorstellungHinzufuegen(neuerFilm, new FilmHinzufuegenCallback());
			
			//TODO	dataProvider.getList().add(neuerFilm);
			dataProvider.refresh();
			
		}
		
	}
	
	private class FilmHinzufuegenCallback implements AsyncCallback<Film> {

		@Override
		public void onFailure(Throwable caught) {
			
			Systemmeldung.anzeigen("FilmHinzufügenCallback funktioniert nicht");
			
		}

		@Override
		public void onSuccess(Film result) {
			
			
			Systemmeldung.anzeigen("Film wurde hinzugefügt");
		}
		
	}
	
	private class VorstellungSpeichernCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			
			if (vorstellungsnameTB.getValue() == "" ) {
				Systemmeldung.anzeigen("Es wurde keine Vorstellung eingegeben");
			}else {
				RootPanel.get("details").clear();
				vorstellungF = new MeineVorstellungForm ();
				RootPanel.get("details").add(vorstellungF);
			}
			
		}
		
	}
	
	private class LoeschenVorstellungCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	

}
