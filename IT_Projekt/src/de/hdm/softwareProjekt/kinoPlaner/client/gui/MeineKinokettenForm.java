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
	
	
	/* Erstellen der Widgets */
	
	private HorizontalPanel obenPanel = new HorizontalPanel();
	private HorizontalPanel homebarPanel = new HorizontalPanel();
	private HorizontalPanel formHeaderPanel = new HorizontalPanel();
	private VerticalPanel inhaltPanel = new VerticalPanel();
	private HorizontalPanel untenPanel = new HorizontalPanel();
	private BusinessObjektView bov = new BusinessObjektView();
	private Grid felder = new Grid (2,1);
	private HomeBarAdmin homebar = new HomeBarAdmin();
	
	
	private Boolean edit = true;
	
	private ArrayList <Kinokette> kinoketten;
	private Kinokette kinokette;
	
	private Kino kino;
	private KinoketteErstellenForm anzeigen;
	private KinoketteErstellenForm erstellen;
	private KinoketteErstellenForm bearbeiten;
	
	
	private Label formHeaderLabel = new Label("Dashboard");
	private Label bearbeitenLabel = new Label("Zum bearbeiten gewünschte Kinokette anklicken.");

	
	
	/* Erstellen der Buttons */
	
	private Button loeschenButton = new Button(" Auswahl löschen");
	private Button bearbeitenButton = new Button("Auswahl bearbeiten");

	
	public void onLoad() {
		KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();
		
		
		
		
		
		/*Vergeben der Style-Namen*/
		
		this.addStyleName("detailscontainer");
		this.addStyleName("center");
		
		obenPanel.addStyleName("obenPanel");
		homebarPanel.addStyleName("hbPanel");
		inhaltPanel.addStyleName("inhaltPanel");
		untenPanel.addStyleName("untenPanel");
		formHeaderLabel.addStyleName("formHeaderLabel");
		loeschenButton.addStyleName("loeschenButton");
		bearbeitenButton.addStyleName("bearbeitenButton");	
		
		
		
		
		
		/*Zusammensetzen der Widgets */
		
		
		
		
		
		
		
		obenPanel.add(formHeaderLabel);
		this.add(obenPanel);
		
		homebarPanel.add(homebar);
		this.add(homebarPanel);
		
		
		bov.setTitel("Meine Kinoketten");
		administration.getKinokettenByAnwenderOwner(new SucheKinokettenByAnwenderCallback());
		inhaltPanel.add(bov);
		this.add(inhaltPanel);
		
		
		bearbeitenButton.addClickHandler(new KinoketteBearbeitenClickHandler());
		loeschenButton.addClickHandler(new KinoketteLoeschenClickHandler());
		
	
		
		
		//untenPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		//untenPanel.add(loeschenButton);
		untenPanel.add(bearbeitenLabel);
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
			
			
			bearbeiten = new KinoketteErstellenForm();
			//	KinoketteErstellenForm.setBearbeiten(ausgewaehlteKinokette);
			RootPanel.get("details").add(bearbeiten);
		}
		
	}
	
	
	private class KinoketteLoeschenClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			
			
			administration.loeschenKinoketteById(kinokette.getId(), new KinoketteLoeschenCallback());
			
		}
		
	}
	
	
	/***Callbacks***/
	
	
	
	private class KinoketteLoeschenCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Die Kinokette konnte nicht gelöscht werden");
		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Die Kinokette "+kinokette.getName()+" wurde gelöscht.");
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

