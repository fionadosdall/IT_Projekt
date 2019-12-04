package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;

public class FilmBearbeiten extends VerticalPanel {

private KinoplanerAsync administration = ClientsideSettings.getKinoplaner();
	
	private Label filmFormLabel = new Label("Film beabeiten");
	private Label nameLabel = new Label("Filmname:");
	private Label beschreibungLabel = new Label("Beschreibung:");
	private Label bewertungLabel = new Label("Bewertung:");
	private Label laengeLabel = new Label("L&auml;nge:");
	
	private TextBox nameTextBox = new TextBox();
	private TextBox beschreibungTextBox = new TextBox();
	private TextBox bewertungTextBox = new TextBox();
	private TextBox laengeTextBox = new TextBox();
	
	private Button speichernButton = new Button("Speichern");
	
}
