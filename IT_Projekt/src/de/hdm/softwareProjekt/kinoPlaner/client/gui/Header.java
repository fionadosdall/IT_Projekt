package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class Header extends HorizontalPanel{
	
	private HorizontalPanel headerPanel = new HorizontalPanel();
	
	private Button homeButton = new Button("Home");
	private Button einstellungenButton = new Button("Einstellungen");
	
	public Header() {
		
	}
	
	private class HomeButtonClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class einstellungenButtonClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
