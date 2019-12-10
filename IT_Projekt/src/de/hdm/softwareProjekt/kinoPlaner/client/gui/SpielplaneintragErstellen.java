package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;


public class SpielplaneintragErstellen extends VerticalPanel {
	
	private HorizontalPanel obenPanel = new HorizontalPanel();
	private HorizontalPanel untenPanel = new HorizontalPanel();

	private KinoplanerAsync administration = ClientsideSettings.getKinoplaner();
	
	private Label spielplaneintragFormLabel = new Label("Neuer Spielplaneintrag");
	private Label nameLabel = new Label ("Name:");
	private Label filmLabel = new Label ("Film:");
	private Label spielzeitLabel = new Label("Spielzeit:");
	
	private Button speichernButton = new Button("Speichern");
	
	

}
