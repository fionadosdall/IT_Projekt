package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;

public class SpielzeitErstellenForm extends VerticalPanel {

	private KinoplanerAsync administration = ClientsideSettings.getKinoplaner();
	
	private Label spielzeitFormLabel = new Label("Neue Spielzeit");
	private Label nameLabel = new Label("Name:");
	
	private TextBox nameTextBox = new TextBox();

	private Button speichernButton = new Button("Speichern");
	
	
}
