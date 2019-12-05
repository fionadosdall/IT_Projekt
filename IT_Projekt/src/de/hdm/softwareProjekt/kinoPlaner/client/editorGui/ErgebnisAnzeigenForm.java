package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import com.google.gwt.user.client.ui.FlowPanel;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;

public class ErgebnisAnzeigenForm extends FlowPanel{
	private Umfrage umfrage;

	public void setUmfrage(Umfrage umfrage) {
		this.umfrage = umfrage;
		
	}

}
