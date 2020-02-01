package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


/*
 * Klasse dient der Darstellung des Dashboards des Admins
 */
public class AdminDashboardForm extends VerticalPanel {
	
	
	private HorizontalPanel obenPanel = new HorizontalPanel();
	private FlowPanel dahboardPanel = new FlowPanel();
	
	private MeineKinokettenForm mkkf;
	private MeineKinosForm mkf;
	private MeineSpielplaeneForm mspf;
	
	private Label dashboardFormLabel = new Label("Dashboard");
	
	private Button meineKinokettenButton = new Button("Meine Kinoketten verwalten");
	private Button meineKinosButton = new Button("Meine Kinos verwalten");
	private Button meineSpielplaeneButton = new Button("Meine Spielpläne verwalten");
	
	
	
	public void onLoad() {
		
		/* Vergeben der Stylename*/
		
		this.addStyleName("center");
		this.addStyleName("detailscontainer");
		
		dashboardFormLabel.addStyleName("formHeaderLabel");
		obenPanel.addStyleName("obenPanel");
		meineKinokettenButton.addStyleName("dashboardButton");
		meineKinosButton.addStyleName("dashboardButton");
		meineSpielplaeneButton.addStyleName("dashboardButton");
		dahboardPanel.addStyleName("dashboardKachelnPanel");
		
		/* Widgets werden zusammengebaut */
		
		this.add(obenPanel);
		obenPanel.add(dashboardFormLabel);
		
		this.add(dahboardPanel);
		dahboardPanel.add(meineKinokettenButton);
		dahboardPanel.add(meineKinosButton);
		dahboardPanel.add(meineSpielplaeneButton);
	
		meineKinokettenButton.addClickHandler(new MeineKinokettenClickHandler());
		meineKinosButton.addClickHandler(new MeineKinosClickHandler());
		meineSpielplaeneButton.addClickHandler(new MeineSpielplaeneClickHandler());
		
		
	}
	
		
		/*ClickHandler */
		

		
		private class MeineKinokettenClickHandler implements ClickHandler {


			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				RootPanel.get("details").clear();
				mkkf = new MeineKinokettenForm();
				RootPanel.get("details").add(mkkf);
			}

			
			
		}
		/*
		 * ClickHandler um die Auswahl zu bearbeiten
		 */
		
		private class MeineKinosClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				RootPanel.get("details").clear();
				mkf = new MeineKinosForm();
				RootPanel.get("details").add(mkf);
			}

			
			
		}
		
		private class MeineSpielplaeneClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				RootPanel.get("details").clear();
				mspf = new MeineSpielplaeneForm();
				RootPanel.get("details").add(mspf);
			}
			
		}
		


}
