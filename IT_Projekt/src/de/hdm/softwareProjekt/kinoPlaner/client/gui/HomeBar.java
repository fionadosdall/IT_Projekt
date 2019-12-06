package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class HomeBar extends FlowPanel {
	
	
	private MeineKinokettenForm kkf;
	private MeineKinosForm kf;
	private MeineSpielplaeneForm spf;
	
	private FlowPanel homeBarLinks = new FlowPanel ();
	private FlowPanel homeBarMitte = new FlowPanel ();
	private FlowPanel homeBarRechts = new FlowPanel ();
	
	private Anchor kinoketten = new Anchor ("Kinoketten");
	private Anchor kinos = new Anchor ("Kinos");
	private Anchor spielplaene = new Anchor ("Spielplaene");
	
	public void onLoad () {
		
		this.addStyleName("homeBar");
		
		homeBarLinks.addStyleName("homeBarItem");
		homeBarMitte.addStyleName("homeBarItem");
		homeBarRechts.addStyleName("homeBarItem");
		
		homeBarLinks.add(kinoketten);
		homeBarMitte.add(kinos);
		homeBarRechts.add(spielplaene);
		
		this.add(homeBarLinks);
		this.add(homeBarMitte);
		this.add(homeBarRechts);
		
		kinoketten.addClickHandler(new MeineKinoKettenClickHandler());
		kinos.addClickHandler(new MeineKinosClickHandler());
		spielplaene.addClickHandler(new MeineSpielplaeneClickHandler());
		
	}
	
	
	private class MeineKinoKettenClickHandler implements ClickHandler {
		
		public void onClick (ClickEvent event) {
			
			RootPanel.get("details").clear();
			kkf = new MeineKinoKettenForm();
			RootPanel.get("details").add(kkf);
			
		}
	}
	
	
	private class MeineKinosClickHandler implements ClickHandler {
		
		
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			kf = new MeineKinosForm();
			RootPanel.get("details").add(kf);
		}
	}
	
	
	private class MeineSpielplaeneClickHandler implements ClickHandler {
		
		public void onClikc (ClickEvent event) {
			
			RootPanel.get("details").clear();
			spf = new MeineSpielplaeneForm();
			RootPanel.get("details").add(spf);
		}
}}
