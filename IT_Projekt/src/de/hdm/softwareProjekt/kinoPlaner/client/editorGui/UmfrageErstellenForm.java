package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class UmfrageErstellenForm extends FlowPanel {
	
	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();
	
	private Label title = new Label("Umfrage erstellen");
	
	public void onLoad() {
		
		this.addStyleName("detailscontainer");

		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");
		
		title.addStyleName("title");
		
		// Zusammenbauen der Widgets
		
		this.add(detailsoben);
		this.add(detailsunten);
		
		detailsoben.add(title);
		
	}


}
