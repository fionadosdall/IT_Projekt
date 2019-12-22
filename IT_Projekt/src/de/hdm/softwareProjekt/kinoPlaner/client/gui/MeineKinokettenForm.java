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
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.BusinessObjektView;
import de.hdm.softwareProjekt.kinoPlaner.shared.Kinoplaner;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;

public class MeineKinokettenForm extends VerticalPanel{
	
	private KinoplanerAsync administration = ClientsideSettings.getKinoplaner();
	
	private HorizontalPanel detailsoben = new HorizontalPanel();
	private HorizontalPanel homebarPanel = new HorizontalPanel();
	private HorizontalPanel formHeaderPanel = new HorizontalPanel();
	private VerticalPanel inhaltPanel = new VerticalPanel();
	private HorizontalPanel untenPanel = new HorizontalPanel();
	private Boolean edit = true;
	
	private Label title = new Label("Dashboard");
	
	private ArrayList <Kinokette> kinoketten;
	private Kinokette kinokette;
	private Kinokette ausgewaehlteKinokette = new Kinokette();
	private Kino kino;
	private KinoketteErstellenForm anzeigen;
	private KinoketteErstellenForm erstellen;
	private KinoketteErstellenForm bearbeiten;
	private BusinessObjektView bov = new BusinessObjektView();
	
	private Label kinokettenlabel = new Label("Meine Kinoketten");
	
	
	
	private Grid felder = new Grid (2,1);
	private HomeBarAdmin homebar = new HomeBarAdmin();
	private Button loeschenButton = new Button(" Auswahl löschen");
	private Button bearbeitenButton = new Button("Auswahl bearbeiten");

	
	public void onLoad() {
		KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();
		
		
		/***Anzeigetest***/
		
		ausgewaehlteKinokette.setId(1);
		ausgewaehlteKinokette.setName("UFA");
		ausgewaehlteKinokette.setSitz("Stuttgart");
		ausgewaehlteKinokette.setWebsite("www.ufa.de");
		
		
		
		/*Vergeben der Style-Namen*/
		
		this.addStyleName("detailscontainer");
		this.addStyleName("center");
		
		detailsoben.addStyleName("obenPanel");
		homebarPanel.addStyleName("hbPanel");
		inhaltPanel.addStyleName("inhaltPanel");
		untenPanel.addStyleName("untenPanel");
		kinokettenlabel.addStyleName("formHeaderLabel");
		formHeaderPanel.addStyleName("h2Panel");
		loeschenButton.addStyleName("loeschenButton");
		bearbeitenButton.addStyleName("bearbeitenButton");	
		title.addStyleName("formHeaderLabel");
		
		
		
		
		/*Zusammensetzen der Widgets */
		
		bov.setTitel("Meine Kinoketten");
		kinoplaner.getAllKinoketten(new SucheKinokettenByAnwenderCallback());
		
		
		detailsoben.add(title);
		this.add(detailsoben);
		
		homebarPanel.add(homebar);
		this.add(homebarPanel);
		
		formHeaderPanel.add(kinokettenlabel);
		this.add(formHeaderPanel);
		
		
		this.add(inhaltPanel);
		
		
		bearbeitenButton.addClickHandler(new KinoketteBearbeitenClickHandler());
		
	
		
		
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
			//Kinokette ausgewaehlteKinokette = felder.getSelectionModel().getSelected();
			
			KinoketteErstellenForm.setEdit(edit);
			bearbeiten = new KinoketteErstellenForm();
			KinoketteErstellenForm.setBearbeiten(ausgewaehlteKinokette);
			RootPanel.get("details").add(bearbeiten);
		}
		
	}
	
	
	private class KinoketteLoeschenClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
			//TODO administration.kinoketteEntfernen(kinokette.getId(), new KinoketteLoeschenCallback());
			
		}
		
	}
	
	
	/***Callbacks***/
	
	
	
	private class KinoketteLoeschenCallback implements AsyncCallback<Kinokette> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Die Kinokette konnte nicht gelöscht werden");
		}

		@Override
		public void onSuccess(Kinokette result) {
			// TODO Auto-generated method stub
			//administration.kinoketteEntfernen(kino, loeschenCallback);
		}
		
	}
	
	
	private class SucheKinokettenByAnwenderCallback implements AsyncCallback<ArrayList<Kinokette>>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Kinoketten können nicht abgerufen werden");
		}

		@Override
		public void onSuccess(ArrayList<Kinokette> result) {
			// TODO Auto-generated method stub
			bov.setKinoketten(result);
			inhaltPanel.add(bov);
			
		}
		
	}
		
	
	
	
		
	



}

