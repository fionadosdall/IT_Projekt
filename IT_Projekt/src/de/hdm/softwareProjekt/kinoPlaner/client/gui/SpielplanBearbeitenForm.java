package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielplan;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;

public class SpielplanBearbeitenForm extends VerticalPanel {
	
	private Spielplan spielplan = null;
	private Spielzeit spielzeit = null;
	private Film film = null;
	private Kino kino = null;
	
	
	

	private KinoplanerAsync administration = ClientsideSettings.getKinoplaner();
	
	private Label filmFormLabel = new Label("Spielplan erstellen");
	private Label nameLabel = new Label("name:");
	private Label spielzeitLabel = new Label("Spielzeit:");
	private Label filmHinzufuegenLabel = new Label("Film  hinzuf&uuml;gen");
	private Label filmBearbeitenLabel = new Label("Film bearbeiten");
	private Label spielzeitHinzufuegenLabel = new Label("Spielzeit  hinzuf&uuml;gen");
	private Label spielzeitBearbeitenLabel = new Label("Spielzeit bearbeiten");
	
	private Button speichernButton = new Button("Speichern");
	
}
