package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;

public class KinoketteBearbeitenForm extends VerticalPanel{

private KinoplanerAsync administration = ClientsideSettings.getKinoplaner();
	
	private Label kinoketteFormLabel = new Label("Kinokette bearbeiten");
	private Label nameLabel = new Label("Kinokettenname:");
	private Label sitzLabel = new Label("Sitz:");
	private Label websiteLabel = new Label("Website:");
	
	private TextBox nameTextBox = new TextBox();
	private TextBox sitzTextBox = new TextBox();
	private TextBox websiteTextBox = new TextBox();
	
	private Button speichernButton = new Button("Speichern");
	
}
