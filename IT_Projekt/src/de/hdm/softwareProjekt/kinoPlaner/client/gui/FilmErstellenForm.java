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
import com.google.gwt.user.client.Window;
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
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
//import de.hdm.softwareProjekt.kinoPlaner.client.gui.FilmBearbeiten.FilmHinzufuegenClickHandler;
//import de.hdm.softwareProjekt.kinoPlaner.client.gui.FilmBearbeiten.FilmLoeschenClickHandler;
//import de.hdm.softwareProjekt.kinoPlaner.client.gui.SpielzeitErstellenForm.SpeichernClickHandler;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;


public class FilmErstellenForm extends FlowPanel {
	

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

	private Label title = new Label("Film beabeiten");
	private Label filmname = new Label("Filmname:");
	private Label beschreibungLabel = new Label("Beschreibung:");
	private Label bewertungLabel = new Label("Bewertung:");
	private Label laengeLabel = new Label("Filmlänge");
	private Label filme = new Label ("Alle Filme");
	
	
	
	private TextBox filmnameTB = new TextBox();
	private TextBox beschreibungTextBox = new TextBox();
	private TextBox bewertungTextBox = new TextBox();
	private TextBox laengeTextBox = new TextBox();
	
	private MultiWordSuggestOracle alleFilmeOracle = new MultiWordSuggestOracle();
	private SuggestBox filmTB = new SuggestBox(alleFilmeOracle);

	private ArrayList<Film> alleFilme = new ArrayList<Film>();

	private Button hinzufuegenButton = new Button("Hinzufügen");
	private Button entfernenButton = new Button("Film entfernen");
	private Button speichernButton = new Button("Speichern");
	
	
	private CellTable<Film> filmCellTable = new CellTable<Film>(KEY_PROVIDER);
	
	private ListDataProvider<Film> dataProvider = new ListDataProvider<Film>();
	private List<Film> list = dataProvider.getList();
	
	
	private static final ProvidesKey<Film> KEY_PROVIDER = new ProvidesKey<Film>() {

		@Override
		public Object getKey(Film film) {
			// TODO Auto-generated method stub
			return film.getName();
		}
		
	};
	
		
		
	private Film film;
	private Film neuerFilm = null;
	
	private FilmAnzeigenForm filmAF;
	
	public void onLoad() {
		// Vergebn der Stylenames

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
		filmname.addStyleName("detailsboxLabels");
		beschreibungLabel.addStyleName("detailsboxLabels");
		bewertungLabel.addStyleName("detailsboxLabels");
		laengeLabel.addStyleName("detailsboxLabels");

		filmTB.addStyleName("filmnameTB");
		beschreibungTextBox.addStyleName("beschreibungTextBox");
		bewertungTextBox.addStyleName("bewertungTextBox");
		laengeTextBox.addStyleName("filmlängeTextBox");

		hinzufuegenButton.addStyleName("hinzufuegenButton");
		entfernenButton.addStyleName("entfernenButton");
		speichernButton.addStyleName("speichernButton");

		filmTB.getElement().setPropertyString("placeholder", "Filmname: " + film.getName());
		beschreibungTextBox.getElement().setPropertyString("placeholder",
				"Filmbeschreibung: " + film.getBeschreibung());
		bewertungTextBox.getElement().setPropertyString("placeholder", "Filmbewertung: " + film.getBewertung());
		laengeTextBox.getElement().setPropertyString("placeholder", "Filmlänge: " + film.getFilmlaenge());

		// Zusammenbauen der Widgets
		
		this.add(detailsoben);
		this.add(detailsunten);

		detailsoben.add(title);

		detailsunten.add(detailsObenBox);
		detailsunten.add(detailsMitteBox);
		detailsunten.add(detailsUntenBox);

		detailsObenBox.add(filmname);
		detailsObenBox.add(detailsBoxObenMitte);
		detailsBoxObenMitte.add(filmTB);

		detailsMitteBox.add(beschreibungLabel);
		detailsMitteBox.add(detailsBoxMitteMitte);
		detailsBoxMitteMitte.add(beschreibungTextBox);
		detailsMitteBox.add(detailsBoxMitteUnten);
		detailsBoxMitteUnten.add(hinzufuegenButton);

		detailsMitteBox.add(bewertungLabel);
		detailsMitteBox.add(detailsBoxMitteMitte);
		detailsBoxMitteMitte.add(bewertungTextBox);
		detailsMitteBox.add(detailsBoxMitteUnten);
		detailsBoxMitteUnten.add(hinzufuegenButton);

		detailsMitteBox.add(laengeLabel);
		detailsMitteBox.add(detailsBoxMitteMitte);
		detailsBoxMitteMitte.add(laengeTextBox);
		detailsMitteBox.add(detailsBoxMitteUnten);
		detailsBoxMitteUnten.add(hinzufuegenButton);
		
		detailsUntenBox.add(filme);
		detailsUntenBox.add(detailsBoxUntenMitte);
		detailsBoxUntenMitte.add(filmCellTable);
		detailsUntenBox.add(detailsBoxUnten);
		detailsBoxUnten.add(entfernenButton);

		detailsunten.add(speichernBox);
		speichernBox.add(speichernButton);
		
		
		//CLICKHANDLER 
		
		hinzufuegenButton.addClickHandler(new FilmHinzufuegenClickHandler());
		speichernButton.addClickHandler(new SpeichernClickHandler());
		//entfernenButton.addClickHandler(new FilmLoeschenClickHandler());
	
		/*
		 * Alle Filme, die im System vorhanden sind, werden geladen
		 */
		kinoplaner.getAllFilme(new AsyncCallback<ArrayList<Film>>() {

			public void onFailure(Throwable caught) {
				Window.alert("Filme konnten nicht geladen werden");

			}

			public void onSuccess(ArrayList<Film> result) {
				for (Film u : result) {
					alleFilmeOracle.add(u.getName());
				}

			}
		});

		
		/**************************************
		 * CELL TABLE
		 */
		
		TextCell namenTextCell = new TextCell();
		Column <Film, String> namenColumn = new Column <Film, String> (namenTextCell) {

			@Override
			public String getValue(Film film) {
				// TODO Auto-generated method stub
				return film.getName();
			}
		};
		
		Cell<String> loeschenCell = new ButtonCell();
		Column <Film, String> loeschenColumn = new Column <Film,String> (loeschenCell) {

			@Override
			public String getValue(Film object) {
				// TODO Auto-generated method stub
				return "-";
			}
		};
		
		loeschenColumn.setFieldUpdater(new FieldUpdater<Film, String>() {

			@Override
			public void update(int index, Film film, String value) {
				// TODO Auto-generated method stub
				dataProvider.getList().remove(film); 
				
				AsyncCallback<Film> loeschenCallback = new AsyncCallback<Film>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(Film result) {
						// TODO Auto-generated method stub
						
					}
					
				};
				
			}
			
		});
		
		namenColumn.setFieldUpdater(new FieldUpdater<Film, String> () {

			@Override
			public void update(int index, Film film, String name) {
				// TODO Auto-generated method stub
				film.setName(name); 
			}
			
		});
		
		filmCellTable.addColumn(namenColumn, "Film hinzufügen");
		filmCellTable.addColumn(loeschenColumn, "Film entfernen");
		filmCellTable.setColumnWidth(namenColumn, 20, Unit.PC);
		filmCellTable.setColumnWidth(loeschenColumn, 20, Unit.PC);
		
		dataProvider.addDataDisplay(filmCellTable);
		
		}
	
	/*****
	 * CLICKHANDLER
	 * 
	 * 
	 */
	
	private class FilmHinzufuegenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			//kinoplaner.getFilmeByAnwenderOwner(new );
			filmTB.setText("");
		}
}
	
	
	private class SpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			//kinoplaner.erstellenFilm(filmnameTB.getValue(), new FilmErstellenCallback());
		}
		
	}
	
	/************************
	 * CALLBACKS
	 */
	
	private class FilmHinzufuegenCallback implements AsyncCallback<Kino> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("FilmHinzufügenCallback funktioniert nicht");
		}

		@Override
		public void onSuccess(Kino result) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Hinzufügen");
		}
		
	}
	
	private class FilmErstellenCallback implements AsyncCallback<Film> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Film erstellen ist fehlgeschlagen");
		}

		@Override
		public void onSuccess(Film result) {
			// TODO Auto-generated method stub
			
			if (filmnameTB.getValue() == "") {
				Systemmeldung.anzeigen("Es wurde kein Filmname eingegeben");
			} else {
				RootPanel.get("details").clear();
				filmAF = new FilmAnzeigenForm(neuerFilm);
				RootPanel.get("details").add(filmAF);
				
			}
			
		}
		
	}

}
