package de.hdm.softwareProjekt.kinoPlaner.client.gui;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielplan;

public class SpielplaneintragForm extends VerticalPanel {
	
	private KinoplanerAsync administration = ClientsideSettings.getKinoplaner();

	private HorizontalPanel obenPanel = new HorizontalPanel();
	private HorizontalPanel untenPanel = new HorizontalPanel();
	
	private Grid spielplaneintragGrid = new Grid(5, 3);
	
	private Label spielplaneintragFormLabel = new Label("Spielplaneintrag erstellen");
	private Label spielplaneintragBearbeitenFormLabel = new Label("Spielplan bearbeiten");
	private Label nameLabel = new Label("Name");
	private Label filmLabel = new Label("Film");
	private Label spielzeitLabel = new Label("Spielzeit");
	
	
	private Button speichernButton = new Button("Speichern");
	private Button filmErstellenButton = new Button("Neuen Film erstellen");
	private Button spielzeitErstellenButton = new Button("Neue Spielzeit erstellen");
	
	private static TextBox nameTextBox = new TextBox();
	private static ListBox filmListBox = new ListBox();
	private static ListBox spielzeitListBox = new ListBox();
	
	public static Boolean edit = false;
	
	public void onLoad() {
		
		/* Setzen der Style-Namen */
		
		this.addStyleName("center");
		this.addStyleName("detailscontainer");
		
		spielplaneintragFormLabel.addStyleName("formHeaderLabel");
		spielplaneintragBearbeitenFormLabel.addStyleName("formHeaderLabel");
		nameLabel.addStyleName("textLabel");
		filmLabel.addStyleName("textLabel");
		spielzeitLabel.addStyleName("textLabel");
		obenPanel.addStyleName("obenPanel");
		untenPanel.addStyleName("untenPanel");
		nameTextBox.addStyleName("formularTextBox");

		spielzeitListBox.setSize("180px", "25px");
		filmListBox.setSize("180px", "25px");
		
		
		/*** Zusammensetzen der Widgets ***/
		
		
		if(edit == true) {
			
			obenPanel.add(spielplaneintragBearbeitenFormLabel);
		}else {
			obenPanel.add(spielplaneintragFormLabel);
			//clearForm();
		}
		
		this.add(obenPanel);
		
		spielplaneintragGrid.setWidget(0, 0, nameLabel);
		spielplaneintragGrid.setWidget(0, 1, nameTextBox);
		
		spielplaneintragGrid.setWidget(1, 0, filmLabel);
		spielplaneintragGrid.setWidget(1, 1, filmListBox);
		spielplaneintragGrid.setWidget(1, 2, filmErstellenButton);
		
		//spielplaneintragGrid.setWidget(2, 2, nameLabel);
		
		spielplaneintragGrid.setWidget(3, 0, spielzeitLabel);
		spielplaneintragGrid.setWidget(3, 1, spielzeitListBox);
		spielplaneintragGrid.setWidget(3, 2, spielzeitErstellenButton);
		
		//spielplaneintragGrid.setWidget(4, 2, nameLabel);
		
		this.add(spielplaneintragGrid);
		
		untenPanel.add(speichernButton);
		this.add(untenPanel);
		
		speichernButton.addClickHandler(new SpeichernClickHandler());
		filmErstellenButton.addClickHandler(new NeuerFilmClickHandler());
		spielzeitErstellenButton.addClickHandler(new NeueSpielzeitClickHandler());
		
	}
	
	/***Clickhandler***/
	
	private class SpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
			
			
		}
		
	}
	
	private class NeuerFilmClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			FilmErstellenForm film = new FilmErstellenForm();
			RootPanel.get("details").add(film);
			
		}
		
	}
	
	
	private class NeueSpielzeitClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			SpielzeitErstellenForm spielzeit = new SpielzeitErstellenForm();
			RootPanel.get("details").add(spielzeit);
			
		}
		
	}
	
	
	
	/*** Callbacks ***/
	
	private class SpielplaneintragErstellenCallback implements AsyncCallback<Spielplan>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Spielplan result) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
