package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;


import com.google.gwt.user.client.ui.FlowPanel;

public class ErgebnisseAnzeigenForm extends FlowPanel {
	
	HomeBar hb = new HomeBar();
	
	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();
	
	
	public void onLoad() {

		this.addStyleName("detailscontainer");
		
		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");
		
		this.add(detailsoben);
		this.add(detailsunten);
		
		detailsoben.add(hb);
		
		
		
	
	}

}
