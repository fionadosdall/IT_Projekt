package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;



public class MenuForm extends FlowPanel {
	
	private FlowPanel navelement = new FlowPanel();
	
	private Button kinoketteErstellenButton = new Button("Kinokette erstellen");
	private Button kinoErstellenButton = new Button("Kino erstellen");
	private Button spielplanErstellenButton = new Button("Spielplan erstellen");

	
	private KinoketteErstellenForm kkef;
	private KinoErstellenForm kef;
	private SpielplanErstellenForm spe;
	
	
	public void onLoad() {
		
		this.addStyleName("navcontainer");
		navelement.addStyleName("navelement");
		
		kinoketteErstellenButton.addStyleName("navButton");
		kinoErstellenButton.addStyleName("navButton");
		spielplanErstellenButton.addStyleName("navButton");
		
		// Zusammenbauen der Widgets
		this.add(navelement);
		this.add(navelement);
		
		navelement.add(kinoketteErstellenButton);
		navelement.add(kinoErstellenButton);
		navelement.add(spielplanErstellenButton);
		
		// Click-Handler
		
		kinoketteErstellenButton.addClickHandler(new KinoketteErstellenClickHandler());
		kinoErstellenButton.addClickHandler(new KinoErstellenClickHandler());
		spielplanErstellenButton.addClickHandler(new SpielplanErstellenClickHandler());
	}
	
	
	
	private class KinoketteErstellenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			kkef = new KinoketteErstellenForm();
//			RootPanel.get("details").add(kkef);
		}
		
	}
	
	private class KinoErstellenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			kef = new KinoErstellenForm();
		//	RootPanel.get("details").add(kef);
			
		}
		
	}
	
	private class SpielplanErstellenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			spe = new SpielplanErstellenForm();
		//	RootPanel.get("details").add(spe);
		}
		
	}
}
