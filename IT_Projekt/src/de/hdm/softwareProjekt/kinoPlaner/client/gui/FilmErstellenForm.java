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
import de.hdm.softwareProjekt.kinoPlaner.client.AdminEntry.AktuellerAnwender;
//import de.hdm.softwareProjekt.kinoPlaner.client.gui.FilmBearbeiten.FilmHinzufuegenClickHandler;
//import de.hdm.softwareProjekt.kinoPlaner.client.gui.FilmBearbeiten.FilmLoeschenClickHandler;
//import de.hdm.softwareProjekt.kinoPlaner.client.gui.SpielzeitErstellenForm.SpeichernClickHandler;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;

/**
 * Formular für das Anlegen einer neuen Gruppe im Datenstamm
 *
 */
public class FilmErstellenForm extends PopupPanel {
	
	Anwender aktuellerAnwender = AktuellerAnwender.getAnwender();

	private KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

	private VerticalPanel popupPanel = new VerticalPanel();
	private HorizontalPanel obenPanel = new HorizontalPanel();
	private HorizontalPanel untenPanel = new HorizontalPanel();

	private Label filmFormLabel = new Label("Neuer Film");
	private Label nameLabel = new Label("Filmname:");
	private Label beschreibungLabel = new Label("Beschreibung:");
	private Label bewertungLabel = new Label("Bewertung:");
	private Label laengeLabel = new Label("Filmlänge");
	private Label filme = new Label("Alle Filme");

	private TextBox nameTextBox = new TextBox();
	private TextBox beschreibungTextBox = new TextBox();
	private TextBox bewertungTextBox = new TextBox();

	private Grid filmGrid = new Grid(4, 2);
	private Button speichernButton = new Button("speichern");
	private Button aenderungSpeichernButton = new Button("Änderungen speichern");
	private Button loeschenButton = new Button("Löschen");
	private Button abbrechenButton = new Button("abbrechen");

	private Film film = null;

	private SpielplaneintragForm parent;

	/*****
	 * Bei der Instanziierung wird der ClickHandler dem Button hinzugefügt
	 * 
	 */

	/**
	 * Kontruktor
	 */
	public FilmErstellenForm() {

		super(true);

	}

	public FilmErstellenForm(Film film) {
		super(true);
		this.film = film;

	}

	public FilmErstellenForm(SpielplaneintragForm parent) {
		super(true);
		this.parent = parent;
	}

	public FilmErstellenForm(SpielplaneintragForm parent, Film film) {
		super(true);
		this.parent = parent;
		this.film = film;
	}

	public void onLoad() {

		/*
		 * Setzen der Style Namen
		 * 
		 */
		this.addStyleName("center");
		this.addStyleName("detailscontainer");
		this.addStyleName("popupPanel");

		filmFormLabel.addStyleName("formHeaderLabel");
		nameLabel.addStyleName("textLabel");
		beschreibungLabel.addStyleName("textLabel");
		bewertungLabel.addStyleName("textLabel");
		laengeLabel.addStyleName("textLabel");
		speichernButton.addStyleName("speichernButton");
		aenderungSpeichernButton.addStyleName("speichernButton");
		abbrechenButton.addStyleName("abbrechenButton");
		loeschenButton.addStyleName("loeschenButton");
		obenPanel.addStyleName("popupObenPanel");
		untenPanel.addStyleName("popupUntenPanel");
		nameTextBox.addStyleName("formularTextBox");
		beschreibungTextBox.addStyleName("formularTextBox");
		bewertungTextBox.addStyleName("formularTextBox");

		/******
		 * Zusammensetzen der Widgets
		 */

		popupPanel.add(obenPanel);
		obenPanel.add(filmFormLabel);

		filmGrid.setWidget(0, 0, nameLabel);
		filmGrid.setWidget(0, 1, nameTextBox);
		filmGrid.setWidget(1, 0, beschreibungLabel);
		filmGrid.setWidget(1, 1, beschreibungTextBox);
		filmGrid.setWidget(2, 0, bewertungLabel);
		filmGrid.setWidget(2, 1, bewertungTextBox);

		popupPanel.add(filmGrid);
		untenPanel.add(abbrechenButton);
		
		if (film == null) {
			nameTextBox.getElement().setPropertyString("placeholder", "Name eingeben");
			beschreibungTextBox.getElement().setPropertyString("placeholder", "Beschreibung eingeben");
			bewertungTextBox.getElement().setPropertyString("placeholder", "Bewertung eingeben");
			untenPanel.add(speichernButton);
		} else {
			nameTextBox.setText(film.getName());
			beschreibungTextBox.setText(film.getBeschreibung());
			bewertungTextBox.setText(film.getBewertung());
			filmFormLabel.setText("Film bearbeiten");
			untenPanel.add(aenderungSpeichernButton);
		}

		popupPanel.add(untenPanel);
		

		/**
		 * ClickHandler für Speichern und Löschen der Filme
		 */
		speichernButton.addClickHandler(new SpeichernClickHandler());
		aenderungSpeichernButton.addClickHandler(new SpeichernClickHandler());
		loeschenButton.addClickHandler(new FilmLoeschenClickHandler());
		abbrechenButton.addClickHandler(new AbbrechenClickHandler());

		this.add(popupPanel);
		popupPanel.setHeight("200px");
		popupPanel.setWidth("100%");
	}

	/*********************************
	 * CLICKHANDLER
	 * 
	 *
	 ************************************/

	/**
	 * ClickHandler für den Speichern-Button, um Änderungen am Film zu speichern.
	 *
	 */
	public class SpeichernClickHandler implements ClickHandler {

		/**
		 * OnClick-Methode: Ist kein Film zum Speichern vorhanden, wird ein
		 * FilmErstellenCallback erstellt. Ansonsten wird der Film mit Name,
		 * Beschreibung und Bewertung gespeichert
		 */
		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub

			if (film == null) {
			

				kinoplaner.erstellenFilm(nameTextBox.getValue(), beschreibungTextBox.getValue(),
						bewertungTextBox.getValue(), aktuellerAnwender, new FilmErstellenCallback());


			} else {

			
				film.setName(nameTextBox.getValue());
				film.setBewertung(bewertungTextBox.getValue());
				film.setBeschreibung(beschreibungTextBox.getValue());

				kinoplaner.speichern(film, new AsyncCallback<Film>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
						caught.printStackTrace();

					}

					@Override
					public void onSuccess(Film result) {
						if (result != null) {
							Systemmeldung.anzeigen("Film wurde geupdatet");
							parent.refresh();
							removeFromParent();
						
						} else {
							Window.alert("Film existiert bereits!");
						
						}

					}
				});
			}

		}

	}
/*
 * ClickHandler um eine Film Instanz zu löschen
 */
	private class FilmLoeschenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub

			RootPanel.get("details").clear();

		}

	}
	
	private class AbbrechenClickHandler implements ClickHandler{
		
		public void onClick(ClickEvent event) {
			FilmErstellenForm.this.hide();
		}
	}

	/****************************
	 * CALLBACKS
	 * 
	 *
	 ******************************/

	/**
	 * Das FilmErstellenCallback wird durch den SpeichernClickhandler aufgerufen.
	 *
	 */
	private class FilmErstellenCallback implements AsyncCallback<Film> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();
		}

		@Override
		public void onSuccess(Film result) {
			// TODO Auto-generated method stub
			if (result == null) {
				Window.alert("Film bereits verfügbar!");
			} else {
				Systemmeldung.anzeigen("Film wurde angelegt");

				// SpielplaneintragForm sef = new SpielplaneintragForm();
				parent.refresh();

				Window.alert(result.getName());
			}
		}

	}

	
	/*
	 * Callback um eine Film-Instanz aus dem System zu löschen
	 */
	private class FilmLoeschenCallback implements AsyncCallback<Film> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();
		}

		@Override
		public void onSuccess(Film result) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Film wurde gelöscht");

		}
	}

	/*********
	 * Methoden
	 */

	public void setBearbeiten() {

		nameTextBox.setText(film.getName());
		beschreibungTextBox.setText(film.getBeschreibung());
		bewertungTextBox.setText(film.getBewertung());

	}

	/**
	 * Alle Eingaben in der FilmErstellenForm entfernen.
	 */
	public void clearForm() {
		nameTextBox.setText("");
		beschreibungTextBox.setText("");
		bewertungTextBox.setText("");

	}

}
