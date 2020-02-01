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
import de.hdm.softwareProjekt.kinoPlaner.client.AdminEntry.AktuellerAnwender;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.BusinessObjektView;
import de.hdm.softwareProjekt.kinoPlaner.shared.Kinoplaner;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;


/*
 * Klasse stellt das Formular um die Kinoketten anzuzeigen bereit
 */
public class MeineKinokettenForm extends VerticalPanel{
	
	Anwender aktuellerAnwender = AktuellerAnwender.getAnwender();
	
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
	
	private Button kinoketteErstellenButton = new Button("Neue Kinokette erstellen");
	
	private Boolean edit = true;
	
	private ArrayList <Kinokette> kinoketten;
	private Kinokette kinokette;
	
	private Kino kino;
	private KinoketteErstellenForm anzeigen;
	private KinoketteErstellenForm erstellen;
	private KinoketteErstellenForm bearbeiten;
	
	
	private Label formHeaderLabel = new Label("Dashboard");
	private Label bearbeitenLabel = new Label("Zum Bearbeiten gewünschte Kinokette anklicken.");

	
	
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
		
		kinoketteErstellenButton.addStyleName("speichernButton");
		
		
		
		/*Zusammensetzen der Widgets */
		
		
		
		
		
		
		
		obenPanel.add(formHeaderLabel);
		this.add(obenPanel);
		
		homebarPanel.add(homebar);
		this.add(homebarPanel);
		
		
		bov.setTitel("Meine Kinoketten");
		administration.getKinokettenByAnwenderOwner(aktuellerAnwender, new SucheKinokettenByAnwenderCallback());
		inhaltPanel.add(bov);
		this.add(inhaltPanel);
		
		
		bearbeitenButton.addClickHandler(new KinoketteBearbeitenClickHandler());
		loeschenButton.addClickHandler(new KinoketteLoeschenClickHandler());
		kinoketteErstellenButton.addClickHandler(new KinoketteErstellenClickHandler());
	
		
		
		//untenPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		//untenPanel.add(loeschenButton);
		untenPanel.add(kinoketteErstellenButton);
		this.add(untenPanel);
		
	}
	

	/*** CLickHandler ***/
	
	/*
	 * CLickHandler um eine Kinokette auszuwählen
	 */ 
	
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

	
/*
 * ClickHandler um eine Kinokette zu erstellen
 */

	private class KinoketteErstellenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			erstellen = new KinoketteErstellenForm();
			RootPanel.get("details").add(erstellen);
		}
		
}
	
	/*
	 * ClickHandler um eine Kinokette zu bearbeiten
	 */
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
	
	/*
	 * ClickHandler um eine Kinokette zu löschen
	 */
	
	private class KinoketteLoeschenClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			
			
			administration.loeschenKinoketteById(kinokette.getId(), aktuellerAnwender, new KinoketteLoeschenCallback());
			
		}
		
	}
	
	
	/***Callbacks***/
	
	/*
	 * Callback für die Abfrage ob die Kinoketten-Instanz zu löschen
	 */
	
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
	
	/*
	 * Callback für die Abfrae das ausgewählte KinokettenObjekt mit dem Namen zu sucehn
	 * 
	 */
	
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
			inhaltPanel.add(bearbeitenLabel);
			
		}
		
	}
		
	
	
	
		
	



}

