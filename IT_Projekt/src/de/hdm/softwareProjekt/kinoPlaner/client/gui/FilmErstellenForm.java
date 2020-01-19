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
import com.google.gwt.user.client.ui.PopupPanel;
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


public class FilmErstellenForm extends PopupPanel {
	

	private KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();
	
	private VerticalPanel popupPanel = new VerticalPanel();
	private HorizontalPanel obenPanel = new HorizontalPanel();
	private HorizontalPanel untenPanel = new HorizontalPanel();

	private Label filmFormLabel = new Label("Neuer Film");
	private Label filmBearbeitenFormLabel = new Label("Film bearbeiten");
	private Label nameLabel = new Label("Filmname:");
	private Label beschreibungLabel = new Label("Beschreibung:");
	private Label bewertungLabel = new Label("Bewertung:");
	private Label laengeLabel = new Label("Filmlänge");
	private Label filme = new Label ("Alle Filme");
	
	
	
	private static TextBox nameTextBox = new TextBox();
	private static TextBox beschreibungTextBox = new TextBox();
	private static TextBox bewertungTextBox = new TextBox();
	private TextBox laengeTextBox = new TextBox();
	
	
	private Grid filmGrid = new Grid(4,2);
	private Button speichernButton = new Button("Speichern");
	private Button loeschenButton = new Button("Löschen");
	
	
	private static Boolean edit = false;
	private Film filmBearbeiten;
	private Film film;
	
	/*****
	 * Bei der Instanzzierung wird der ClickHandler dem  Button hinzugefügt
	 * 
	 */

	public FilmErstellenForm() {
		
		super(true);
		
	}
	
	public FilmErstellenForm (Film film) {
		super(true);
		this.film = film;
		
	}
	
	
	public void onLoad() {
	
	/* Setzen der Style Namen
	 * 
	 */
		this.addStyleName("center");
		this.addStyleName("detailscontainer");
		this.addStyleName("popupPanel");


		filmFormLabel.addStyleName("formHeaderLabel");
		filmBearbeitenFormLabel.addStyleName("formHeaderLabel");
		nameLabel.addStyleName("textLabel");
		beschreibungLabel.addStyleName("textLabel");
		bewertungLabel.addStyleName("textLabel");
		laengeLabel.addStyleName("textLabel");
		speichernButton.addStyleName("speichernButton");
		loeschenButton.addStyleName("loeschenButton");
		obenPanel.addStyleName("popupObenPanel");
		untenPanel.addStyleName("popupUntenPanel");
		nameTextBox.addStyleName("formularTextBox");
		beschreibungTextBox.addStyleName("formularTextBox");
		bewertungTextBox.addStyleName("formularTextBox");
		laengeTextBox.addStyleName("formularTextBox");
		
		
		/******
		 * Zusammensetzen der Widgets
		 */
		
		
		if (edit == true) {
			
			obenPanel.add(filmBearbeitenFormLabel);
		}else {
			obenPanel.add(filmFormLabel);
			clearForm();
		}
		
		popupPanel.add(obenPanel);
		
		
		filmGrid.setWidget(0, 0, nameLabel);
		filmGrid.setWidget(0, 1, nameTextBox);
		filmGrid.setWidget(1, 0, beschreibungLabel);
		filmGrid.setWidget(1, 1, beschreibungTextBox);
		filmGrid.setWidget(2, 0, bewertungLabel);
		filmGrid.setWidget(2, 1, bewertungTextBox);
		filmGrid.setWidget(3, 0, laengeLabel);
		filmGrid.setWidget(3, 1, laengeTextBox);
		
		
		popupPanel.add(filmGrid);
		
		
		
		if(edit == true) {
			untenPanel.add(loeschenButton);
			untenPanel.add(speichernButton);
			
		} else {
			clearForm();
			untenPanel.add(speichernButton);
		}
		
		
		popupPanel.add(untenPanel);
		
		speichernButton.addClickHandler(new SpeichernClickHandler());
		loeschenButton.addClickHandler(new FilmLoeschenClickHandler());
		
		this.add(popupPanel);
	}
		//CLICKHANDLER 
		
		
	public class SpeichernClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				
				kinoplaner.erstellenFilm(nameTextBox.getValue(), beschreibungTextBox.getValue(), bewertungTextBox.getValue(),
						new FilmErstellenCallback());
				
				Window.alert(nameTextBox.getValue());
				Window.alert(beschreibungTextBox.getValue());
				Window.alert(bewertungTextBox.getValue());
				

			}
		
	}
	
	private class FilmLoeschenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
			RootPanel.get("details").clear();
			
			
		}
		
	}
		
		
 // Callback 
	
		private class FilmErstellenCallback implements AsyncCallback<Film> {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Systemmeldung.anzeigen("Ein neuer Film konnte leider nicht angelegt werden");
			}

			@Override
			public void onSuccess(Film result) {
				// TODO Auto-generated method stub
				Systemmeldung.anzeigen("Film wurde angelegt");
				
				Window.alert(nameTextBox.getValue());
				Window.alert(beschreibungTextBox.getValue());
				Window.alert(bewertungTextBox.getValue());
				
				clearForm();
				FilmErstellenForm.this.hide();
				
				SpielplaneintragForm sef = new SpielplaneintragForm();
				SpielplaneintragForm.getFilmListBox().addItem(result.getName());
			
				Window.alert(result.getName());
			}
	
		}
		
		
		private class FilmLoeschenCallback implements AsyncCallback<Film>{

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Systemmeldung.anzeigen("Film konnte nicht gelöscht werden");
			}

			@Override
			public void onSuccess(Film result) {
				// TODO Auto-generated method stub
				Systemmeldung.anzeigen("Film wurde gelöscht");
				
			}}
			
		
		
		
/*********
 * Methoden
 */
			
		public Boolean getEdit() {
			return edit;
		}
		
		public static void setEdit (Boolean edit) {
			FilmErstellenForm.edit=edit;
			
		}
	
	public static void setBearbeiten(Film film) {
		
		nameTextBox.setText(film.getName());
		beschreibungTextBox.setText(film.getBeschreibung());
		bewertungTextBox.setText(film.getBewertung());
		
	}
	
	
	
		public void clearForm() {
			nameTextBox.setText("");
			beschreibungTextBox.setText("");
			bewertungTextBox.setText("");
			laengeTextBox.setText("");
		}
		
	}


