package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.user.client.ui.FlowPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;

public class FilmAnzeigenForm extends FlowPanel{
	
	private KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

}
