package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.user.client.ui.FlowPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

public class SpielplanCellTable extends FlowPanel {
	
	private KinoplanerAsync administration = ClientsideSettings.getKinoplaner();

	private Vorstellung vorstellung;
	
}
