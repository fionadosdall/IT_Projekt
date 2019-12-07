package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;


public class FilmErstellen extends VerticalPanel {
	
	private HorizontalPanel obenPanel = new HorizontalPanel();
	private HorizontalPanel untenPanel = new HorizontalPanel();

	private KinoplanerAsync administration = ClientsideSettings.getKinoplaner();
	
	private Label filmFormLabel = new Label("Neuer Film");
	private Label nameLabel = new Label("Filmname:");
	private Label beschreibungLabel = new Label("Beschreibung:");
	private Label bewertungLabel = new Label("Bewertung:");
	private Label laengeLabel = new Label("L&auml;nge:");
	
	private TextBox nameTextBox = new TextBox();
	private TextBox beschreibungTextBox = new TextBox();
	private TextBox bewertungTextBox = new TextBox();
	private TextBox laengeTextBox = new TextBox();
	
	private Button speichernButton = new Button("Speichern");
	private Grid filmGrid = new Grid(4, 2);
	
	
	
	
	public void onLoad() {
	
	filmFormLabel.setStylePrimaryName("FormHeaderLabel");
	obenPanel.setStylePrimaryName("obenPanel");
	untenPanel.setStylePrimaryName("untenPanel");
	speichernButton.setStylePrimaryName("speichernButton");
	
	
	obenPanel.add(filmFormLabel);
	
	filmGrid.setWidget(0, 0, nameLabel);
	filmGrid.setWidget(0, 1, nameTextBox);
	filmGrid.setWidget(1, 0, beschreibungLabel);
	filmGrid.setWidget(1, 1, beschreibungTextBox);
	filmGrid.setWidget(2, 0, bewertungLabel);
	filmGrid.setWidget(2, 1, bewertungTextBox);
	filmGrid.setWidget(3, 0, laengeLabel);
	filmGrid.setWidget(3, 1, laengeTextBox);
	

	

	untenPanel.add(speichernButton);
	
	
}


private class SpeichernClickHandler implements ClickHandler {

	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}		
	
}

	
/* Callback */
			
private class FilmErstellenCallback implements AsyncCallback<Film> {

	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		Systemmeldung.anzeigen("Eine neuer Film konnte leider nicht erstellt werden");
	}

	@Override
	public void onSuccess(Film result) {
		// TODO Auto-generated method stub
		Systemmeldung.anzeigen("Film wurde angelegt");
	}
	
}
	
}
