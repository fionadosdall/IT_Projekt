package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AdminDashboardForm extends VerticalPanel {
	
	
	private HorizontalPanel dashboardboardPanel = new HorizontalPanel();
	private HorizontalPanel hbPanel = new HorizontalPanel();
	private HorizontalPanel obenPanel = new HorizontalPanel();
	private HorizontalPanel untenPanel = new HorizontalPanel();
	
	private HomeBarAdmin hb = new HomeBarAdmin();
	private MeineKinokettenForm mkkf = new MeineKinokettenForm();
	private MeineKinosForm mkf = new MeineKinosForm();
	private MeineSpielplaeneForm mspf = new MeineSpielplaeneForm();
	
	private Label dashboardFormLabel = new Label("Dashboard");
	private Button loeschenButton = new Button(" Auswahl löschen");
	private Button bearbeitenButton = new Button("Auswahl bearbeiten");
	
	public void onLoad() {
		
		/* Vergeben der Stylename*/
		
		this.addStyleName("center");
		this.addStyleName("detailscontainer");
		
		dashboardFormLabel.addStyleName("formHeaderLabel");
		hbPanel.addStyleName("hbPanel");
		untenPanel.addStyleName("untenPanel");
		obenPanel.addStyleName("obenPanel");
			
		
		
		
		/* Widgets werden zusammengebaut */
		
		obenPanel.add(dashboardFormLabel);
		this.add(obenPanel);
		hbPanel.add(hb);
		this.add(hbPanel);
		
		//dashboardboardPanel.add(mkkf);
		//dashboardboardPanel.add(mkf);
		//dashboardboardPanel.add(mspf);
		//this.add(dashboardboardPanel);
		
		//untenPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		//untenPanel.add(loeschenButton);
		//untenPanel.add(bearbeitenButton);
		//this.add(untenPanel);
		
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
