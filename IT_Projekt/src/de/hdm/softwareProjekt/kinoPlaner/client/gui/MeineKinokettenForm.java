package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;

public class MeineKinokettenForm extends VerticalPanel{

	
	private KinoplanerAsync administration = ClientsideSettings.getKinoplaner();
	
	Kinokette kinokette;
	
	private Label kinokettenFormLabel = new Label("Kinoketten");
	
	private Button bearbeitenButton = new Button("Edit");
}
