package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class Navigator extends FlowPanel {
	
	private FlowPanel navelementOben = new FlowPanel();
	private FlowPanel navelementUnten = new FlowPanel();
	
	private Button gruppeErstellenButton = new Button("Gruppe Erstellen");
	private Button umfrageErstellenButton = new Button("Umfrage Erstellen");
	
	private GruppeErstellenForm gef;
	private UmfrageErstellenForm uef;
	
	public void onLoad() {
		
		this.addStyleName("navcontainer");
		navelementOben.addStyleName("navelement");
		navelementUnten.addStyleName("navelement");
		
		gruppeErstellenButton.addStyleName("navButton");
		umfrageErstellenButton.addStyleName("navButton");
		
		// Zusammenbauen der Widgets
		this.add(navelementOben);
		this.add(navelementUnten);
		
		navelementOben.add(gruppeErstellenButton);
		navelementUnten.add(umfrageErstellenButton);
		
		// Click-Handler
		
		gruppeErstellenButton.addClickHandler(new GruppeErstellenClickHandler());
		umfrageErstellenButton.addClickHandler(new UmfrageErstellenClickHandler());
		
	}
	
	private class GruppeErstellenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			gef = new GruppeErstellenForm();
			RootPanel.get("details").add(gef);
			
		}
		
	}
	
	private class UmfrageErstellenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			uef = new UmfrageErstellenForm();
		 	RootPanel.get("details").add(uef);
			
		}
		
	}
	

}
