package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.user.client.ui.HorizontalPanel;

public class AdminDashboardForm extends HorizontalPanel {
	
	private MeineKinokettenForm mkkf = new MeineKinokettenForm();
	private MeineKinosForm mkf = new MeineKinosForm();
	private MeineSpielplaeneForm mspf = new MeineSpielplaeneForm();
	
	public void onLoad() {
		
		this.add(mkkf);
		this.add(mkf);
		this.add(mspf);
		
	}

}
