package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;


public class HomeBar extends FlowPanel {
	
	private GruppenAnzeigenForm gaf;
	private UmfragenAnzeigenForm uaf;
	private ErgebnisseAnzeigenForm eaf;
	
	private FlowPanel homeBarLinks = new FlowPanel();
	private FlowPanel homeBarMitte = new FlowPanel();
	private FlowPanel homeBarRechts = new FlowPanel();
	
	private Anchor gruppen = new Anchor ("Gruppen");
	private Anchor umfragen = new Anchor ("Umfragen");
	private Anchor ergebnisse = new Anchor ("Ergebnisse");
	
	public void onLoad() {
		
		this.addStyleName("homeBar");
		
		homeBarLinks.addStyleName("homeBarItem");
		homeBarMitte.addStyleName("homeBarItem");
		homeBarRechts.addStyleName("homeBarItem");
		
		gruppen.addStyleName("homeBarAnchor");
		umfragen.addStyleName("homeBarAnchor");
		ergebnisse.addStyleName("homeBarAnchor");
		
		homeBarLinks.add(gruppen);
		homeBarMitte.add(umfragen);
		homeBarRechts.add(ergebnisse);
		
		this.add(homeBarLinks);
		this.add(homeBarMitte);
		this.add(homeBarRechts);
		
		gruppen.addClickHandler(new GruppenAnzeigenClickHandler());
		umfragen.addClickHandler(new UmfragenAnzeigenClickHandler());
		ergebnisse.addClickHandler(new ErgebnisseAnzeigenClickHandler());
		

	}
	
	private class GruppenAnzeigenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			gaf = new GruppenAnzeigenForm();
			RootPanel.get("details").add(gaf);
			
		}
		
	}
	
	private class UmfragenAnzeigenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			uaf = new UmfragenAnzeigenForm();
//			RootPanel.get("details").add(uaf);
			
		}
	}
	
	private class ErgebnisseAnzeigenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			eaf = new ErgebnisseAnzeigenForm();
			RootPanel.get("details").add(eaf);
			
		}
	}
		
		
	}


