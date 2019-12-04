package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;

public class MeineKinosForm extends VerticalPanel {
	
private KinoplanerAsync administration = ClientsideSettings.getKinoplaner();
	
	private Label kinosFormLabel = new Label("Kinos");
	
	private Button bearbeitenButton = new Button("Edit");

}
