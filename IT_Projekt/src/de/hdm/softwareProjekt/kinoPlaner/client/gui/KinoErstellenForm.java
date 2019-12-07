package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;

public class KinoErstellenForm extends VerticalPanel {
	
	
	private KinoplanerAsync administration = ClientsideSettings.getKinoplaner();
	
	private Label kinoFormLabel = new Label("Neue Kinokette");
	private Label nameLabel = new Label("Kinoname:");
	private Label plzLabel = new Label("PLZ:");
	private Label stadtLabel = new Label("Stadt:");
	private Label strasseLabel = new Label("Straße:");
	private Label hnrLabel = new Label("Hausnummer:");
	
	private TextBox nameTextBox = new TextBox();
	private TextBox plzTextBox = new TextBox();
	private TextBox stadtTextBox = new TextBox();
	private TextBox strasseTextBox = new TextBox();
	private TextBox hnrTextBox = new TextBox();
	
	private Button speichernButton = new Button("Speichern");
	
}
