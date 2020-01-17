package de.hdm.softwareProjekt.kinoPlaner.client.gui;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielplan;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

public class SpielplaneintragForm extends PopupPanel {
	
	private KinoplanerAsync administration = ClientsideSettings.getKinoplaner();

	private VerticalPanel popupPanel = new VerticalPanel();
	private HorizontalPanel obenPanel = new HorizontalPanel();
	private HorizontalPanel untenPanel = new HorizontalPanel();
	
	private Grid spielplaneintragGrid = new Grid(5, 3);
	
	private Label spielplaneintragFormLabel = new Label("Spielplaneintrag erstellen");
	private Label spielplaneintragBearbeitenFormLabel = new Label("Spielplan bearbeiten");
	private Label filmLabel = new Label("Film");
	private Label spielzeitLabel = new Label("Spielzeit");
	
	
	private Button speichernButton = new Button("Speichern");
	private Button filmErstellenButton = new Button("Neuen Film erstellen");
	private Button spielzeitErstellenButton = new Button("Neue Spielzeit erstellen");
	private Button filmBearbeitenButton = new Button("Film bearbeiten");
	private Button spielzeitBearbeitenButton = new Button("Spielzeit bearbeiten");

	private Vorstellung vorstellung;
	
	private static ListBox filmListBox = new ListBox();
	private static ListBox spielzeitListBox = new ListBox();
	
	public static Boolean edit = false;
	
	public SpielplaneintragForm() {
		
		super(true);

		
	}
	
	public SpielplaneintragForm(Vorstellung vorstellung) {
		super(true);
		this.vorstellung = vorstellung;
	}
	
	public void onLoad() {
		
		/* Setzen der Style-Namen */
		
		this.addStyleName("center");
		this.addStyleName("detailscontainer");
		this.addStyleName("popupPanel");
		
		spielplaneintragFormLabel.addStyleName("formHeaderLabel");
		spielplaneintragBearbeitenFormLabel.addStyleName("formHeaderLabel");
		filmLabel.addStyleName("textLabel");
		spielzeitLabel.addStyleName("textLabel");
		obenPanel.addStyleName("popupObenPanel");
		untenPanel.addStyleName("popupUntenPanel");
		speichernButton.addStyleName("speichernButton");

		spielzeitListBox.setSize("180px", "25px");
		filmListBox.setSize("180px", "25px");
		
		
		/*** Zusammensetzen der Widgets ***/
		
		
		
		if(edit == true) {
			
			obenPanel.add(spielplaneintragBearbeitenFormLabel);
		}else {
			obenPanel.add(spielplaneintragFormLabel);
			//clearForm();
		}
		
		popupPanel.add(obenPanel);
		
	
		
		spielplaneintragGrid.setWidget(1, 0, filmLabel);
		spielplaneintragGrid.setWidget(1, 1, filmListBox);
		spielplaneintragGrid.setWidget(1, 2, filmErstellenButton);
		
		//spielplaneintragGrid.setWidget(2, 2, filmBearbeitenButton);
		
		spielplaneintragGrid.setWidget(3, 0, spielzeitLabel);
		spielplaneintragGrid.setWidget(3, 1, spielzeitListBox);
		spielplaneintragGrid.setWidget(3, 2, spielzeitErstellenButton);
		
		//spielplaneintragGrid.setWidget(4, 2, spielzeitBearbeitenButton);
		
		popupPanel.add(spielplaneintragGrid);
		
		untenPanel.add(speichernButton);
		popupPanel.add(untenPanel);
		
		speichernButton.addClickHandler(new SpeichernClickHandler());
		filmErstellenButton.addClickHandler(new NeuerFilmClickHandler());
		spielzeitErstellenButton.addClickHandler(new NeueSpielzeitClickHandler());
		
		//this.center();
		//this.setPopupPosition(30, 50);
		this.add(popupPanel);
		
		
	}
	
	/***Clickhandler***/
	
	private class SpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			SpielplaneintragForm.this.hide();
			
			
		}
		
	}
	
	private class NeuerFilmClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
			FilmErstellenForm film = new FilmErstellenForm();
			film.show();
			
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
