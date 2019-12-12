package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AdminDashboardForm extends VerticalPanel {
	
	
	private HorizontalPanel dashboardboardPanel = new HorizontalPanel();
	private HorizontalPanel obenPanel = new HorizontalPanel();
	private HorizontalPanel untenPanel = new HorizontalPanel();
	
	private MeineKinokettenForm mkkf = new MeineKinokettenForm();
	private MeineKinosForm mkf = new MeineKinosForm();
	private MeineSpielplaeneForm mspf = new MeineSpielplaeneForm();
	
	private Label dashboardFormLabel = new Label("Dashboard");
	private Button loeschenButton = new Button(" Auswahl löschen");
	private Button bearbeitenButton = new Button("Auswahl bearbeiten");
	
	public void onLoad() {
		
		/* Vergeben der Stylename*/
		
		dashboardFormLabel.setStylePrimaryName("formHeaderLabel");
		untenPanel.setStylePrimaryName("untenPanel");
		obenPanel.setStylePrimaryName("obenPanel");
		loeschenButton.setStylePrimaryName("loeschenButton");
		bearbeitenButton.setStylePrimaryName("bearbeitenButton");		
		
		
		
		/* Widgets werden zusammengebaut */
		
		obenPanel.add(dashboardFormLabel);
		this.add(obenPanel);
		
		
		dashboardboardPanel.add(mkkf);
		dashboardboardPanel.add(mkf);
		dashboardboardPanel.add(mspf);
		this.add(dashboardboardPanel);
		
		
		untenPanel.add(loeschenButton);
		untenPanel.add(bearbeitenButton);
		this.add(untenPanel);
		
		loeschenButton.addClickHandler(new AuswahlLoeschenClickHandler());
		bearbeitenButton.addClickHandler(new AuswahlBearbeitenClickHandler());
		
	}
	
		
		/*ClickHandler */
		
		
		class AuswahlLoeschenClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
			}

			
			
		}
		
		private class AuswahlBearbeitenClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
			}

			
			
		}
		


}
