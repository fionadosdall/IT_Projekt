package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.HomeBar;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;

public class FilmAnzeigenForm extends FlowPanel{
	
	private KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

	private Film film; 
	
	private ArrayList <Film> filme; 
	
	HomeBar hb = new HomeBar();
	
	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();
	private FlowPanel detailsboxInhalt = new FlowPanel();
	
	private Label title = new Label("Filmname");
	
	private Grid felder = new Grid(2, 4);
	
	
	public void onLoad () {
		
		this.addStyleName("detailscontainer");
		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");
		
		title.addStyleName("title");
		
		kinoplaner.getFilmeByAnwenderOwner(new FilmCallback());
	
		if(filme != null) {
			felder.resizeRows(filme.size());
			int i = 0; 
			for (Film film : filme) {
				felder.setWidget(i, 0, new Label (film.getName()));
			}
		} else {
			felder.setWidget(0, 0, new Label ("Keine Filme verfügbar."));
			Button erstellenButton = new Button ("Erstele deinen ersten Film!");
			erstellenButton.setStyleName("navButton");
			erstellenButton.addClickHandler(new FilmErstellenClickHandler());
			felder.setWidget(2, 0, erstellenButton);
		}
		detailsboxInhalt.add(felder);
	}
	
	public FilmAnzeigenForm (Film film) {
		this.film = film; 
	}
	
	/***********************************************************************
	 * CLICKHANDLER
	 ***********************************************************************/
	
	private class FilmErstellenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			FilmErstellenForm erstellen = new FilmErstellenForm();
			RootPanel.get("details").add(erstellen);
		}
		
	}
	
	/***********************************************************************
	 * CALLBACKS
	 ***********************************************************************/
	 
	private class FilmCallback implements AsyncCallback <ArrayList <Film>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("FilmCallback funktioniert nicht.");
		}

		@Override
		public void onSuccess(ArrayList <Film> result) {
			// TODO Auto-generated method stub
			filme = result;
		}
		
	}
	
}
