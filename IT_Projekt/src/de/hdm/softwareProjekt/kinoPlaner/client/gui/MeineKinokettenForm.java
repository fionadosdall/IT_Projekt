package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;

public class MeineKinokettenForm extends VerticalPanel{
	
	
	private HorizontalPanel detailsoben = new HorizontalPanel();
	private HorizontalPanel homebarPanel = new HorizontalPanel();
	private FlowPanel detailsunten = new FlowPanel();
	private HorizontalPanel hPanel = new HorizontalPanel();
	private HorizontalPanel untenPanel = new HorizontalPanel();
	private Boolean edit = true;
	
	private Label title = new Label("Dashboard");
	
	private ArrayList <Kinokette> kinoketten;
	private Kinokette kinokette;
	private Kino kino;
	private KinoketteErstellenForm anzeigen;
	private KinoketteErstellenForm erstellen;
	private KinoketteErstellenForm bearbeiten;
	private Label kinokettenlabel = new Label("Meine Kinoketten");
	
	
	
	private Grid felder = new Grid (2,1);
	private HomeBarAdmin homebar = new HomeBarAdmin();
	private Button loeschenButton = new Button(" Auswahl löschen");
	private Button bearbeitenButton = new Button("Auswahl bearbeiten");

	
	public void onLoad() {
		KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();
		
		KinoketteErstellenForm.setEdit(edit);
		
		/*Vergeben der Style-Namen*/
		
		this.addStyleName("detailscontainer");
		this.addStyleName("center");
		
		detailsoben.addStyleName("obenPanel");
		homebarPanel.addStyleName("hbPanel");
		untenPanel.addStyleName("untenPanel");
		kinokettenlabel.addStyleName("formHeaderLabel");
		hPanel.addStyleName("h2Panel");
		loeschenButton.addStyleName("loeschenButton");
		bearbeitenButton.addStyleName("bearbeitenButton");	
		title.addStyleName("formHeaderLabel");
		
		
		
		/*Zusammensetzen der Widgets */
		
		
		
		detailsoben.add(title);
		this.add(detailsoben);
		
		homebarPanel.add(homebar);
		this.add(homebarPanel);
		
		hPanel.add(kinokettenlabel);
		this.add(hPanel);
		
		
		bearbeitenButton.addClickHandler(new KinoketteBearbeitenClickHandler());
		
	
		kinoplaner.getKinokettenByAnwenderOwner(new SucheKinoKettenByAnwenderOwnerCallback());
		
		
		//felder.setWidget(0, 0, kinokettenlabel);
		
		
		
		if (kinoketten != null) {
			felder.resizeRows(kinoketten.size()+1);
			int i=1;
			
			for ( Kinokette kinokette : kinoketten) {
				Label kinokettename = new Label (kinokette.getName());
				
				KinoketteAuswaehlenClickHandler click = new KinoketteAuswaehlenClickHandler();
				click.setKinokette(kinokette);
				kinokettename.addDoubleClickHandler(click);
				felder.setWidget(i, 0, kinokettename);
				i++;
				
				}
			
			} else {
					felder.setWidget(0, 0, new Label("Keine Kinoketten verfügbar"));
					Button erstellenButton = new Button ("Erstelle deine erste Kinokette");
					erstellenButton.setStyleName("navButton");
					erstellenButton.addDoubleClickHandler(new KinoketteErstellenClickHandler());
					felder.setWidget(1, 0, erstellenButton);
		}
		
		this.add(felder);
		
		untenPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		untenPanel.add(loeschenButton);
		untenPanel.add(bearbeitenButton);
		this.add(untenPanel);
		
	}
	

	/*** CLickHandler ***/
	
private class KinoketteAuswaehlenClickHandler implements DoubleClickHandler {
	private Kinokette kinokette;

	@Override
	public void onDoubleClick(DoubleClickEvent event) {
		
		RootPanel.get("details").clear();
		anzeigen = new KinoketteErstellenForm();
		RootPanel.get("details").add(anzeigen);
	}

	

	public void setKinokette (Kinokette kinokette) {
		this.kinokette = kinokette;
	}
}

	

	private class KinoketteErstellenClickHandler implements DoubleClickHandler {

		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			erstellen = new KinoketteErstellenForm();
			RootPanel.get("details").add(erstellen);
		}
		
}
	
	private class KinoketteBearbeitenClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			bearbeiten = new KinoketteErstellenForm();
			RootPanel.get("details").add(bearbeiten);
		}
		
	}
	
	
	private class KinoketteLoeschenClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	/***Callbacks***/
	
	private class SucheKinoKettenByAnwenderOwnerCallback implements AsyncCallback<ArrayList<Kinokette>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Kinoketten nicht abrufbar");
			
		}

		@Override
		public void onSuccess(ArrayList<Kinokette> result) {
			kinoketten = result;
			
		}

		
		
			
		}
		
	
	
	
		
	



}

