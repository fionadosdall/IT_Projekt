package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;

public class SpielplanBearbeitenForm extends VerticalPanel {

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
