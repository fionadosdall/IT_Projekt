package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.user.client.ui.FlowPanel;

public class Home extends FlowPanel {
	
	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel ();
	
	private HomeBar hb = new HomeBar();
	
	public void onLoad() {
		
		this.addStyleName("detailscontainer");
		
		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");
		
		
		
		//Zusammenbau Widgets
		
		this.add(detailsoben);
		this.add(detailsunten);
		
		
		detailsoben.add(hb);
	}

}
