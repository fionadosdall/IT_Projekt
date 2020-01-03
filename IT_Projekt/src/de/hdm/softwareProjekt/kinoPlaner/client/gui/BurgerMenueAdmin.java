package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;



public class BurgerMenueAdmin extends FlowPanel{

	Image menue = new Image();
	VerticalPanel menueDropdown = new VerticalPanel();
	VerticalPanel fl = new VerticalPanel();
	FlowPanel detailsOben = new FlowPanel();
	VerticalPanel dropdown = new  VerticalPanel();

	@Override
	protected void onLoad() {
		
		super.onLoad();
		this.setStyleName("headerRechtsElement");
		detailsOben.setStyleName("");
		fl.setStyleName("headerRechtsElement");
		menue.setStyleName("löschenImage");
		menue.setUrl("/images/burger-menu.png");
		fl.add(menue);
		menue.addClickHandler(new BurgerMenueClickHandler());
		this.add(fl);
		dropdown.setStyleName("dropdownGesamt");
		RootPanel.get("dropdown").add(dropdown);

	}

	private class BurgerMenueClickHandler implements ClickHandler {
		private int clickCounter = 0;

		@Override
		public void onClick(ClickEvent event) {
			if (clickCounter == 0) {
				menueDropdown.setStyleName("menueBar");
				Button buttonErstellenKinokette = new Button("Kinokette erstellen");
				Button buttonErstellenKino = new Button("Kino erstellen");
				Button buttonErstellenSpielplan = new Button("Spielplan erstellen");
				buttonErstellenKinokette.setStyleName("dropdownButton");
				buttonErstellenKino.setStyleName("dropdownButton");
				buttonErstellenSpielplan.setStyleName("dropdownButton");
				buttonErstellenKinokette.addClickHandler(new KinoErstellenClickHandler());
				buttonErstellenKino.addClickHandler(new KinoketteErstellenClickHandler());
				buttonErstellenSpielplan.addClickHandler(new SpielplanErstellenClickHandler());
				menueDropdown.add(buttonErstellenKinokette);
				menueDropdown.add(buttonErstellenKino);
				menueDropdown.add(buttonErstellenSpielplan);
				dropdown.add(menueDropdown);
				clickCounter++;
			}	else if (clickCounter % 2 == 0) {
				
				dropdown.add(menueDropdown);
				clickCounter++;
					
				
				
				
			} else {
				dropdown.clear();
				clickCounter++;
			}

		}

	}

	private class KinoErstellenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			KinoErstellenForm erstellen = new KinoErstellenForm();
			RootPanel.get("details").add(erstellen);

		}

	}

	private class KinoketteErstellenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			KinoketteErstellenForm erstellen = new KinoketteErstellenForm();
			RootPanel.get("details").add(erstellen);

		}

	}
	
	
	private class SpielplanErstellenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			SpielplanErstellenForm erstellen = new SpielplanErstellenForm();
			RootPanel.get("details").add(erstellen);
		}
		
	}
	
	
	
}
