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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.BusinessObjektView;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.GruppeAnzeigenForm;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.HomeBar;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielplan;

public class MeineSpielplaeneForm extends VerticalPanel {

	
	/* Erstellen der Widgets*/ 
	private HorizontalPanel obenPanel = new HorizontalPanel();
	private HorizontalPanel hbPanel = new HorizontalPanel();
	private VerticalPanel inhaltPanel = new VerticalPanel();
	private HorizontalPanel untenPanel = new HorizontalPanel();
	private BusinessObjektView bov = new BusinessObjektView();
	private Grid felder = new Grid (3,1);
	private HomeBarAdmin hb = new HomeBarAdmin();
	
	private Label formHeaderLabel = new Label ("Dashboard");
	private Label bearbeitenLabel = new Label("Zum Bearbeiten gewünschten Spielplan anklicken.");
	
	private ArrayList <Spielplan> spielplaene;
	private Kino kino;
	private MeineSpielplaeneForm anzeigen;
	private SpielplanErstellenForm erstellen;
	private SpielplanErstellenForm bearbeiten;
	private Button spielplanErstellenButton = new Button("Spielplan erstellen");

	
	private static Boolean edit;
	
	
	
	/*Erstellen der Buttons*/
	
	
	
	/*Vergeben der Style-Namen*/
	
	
	public void onLoad() {
		KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner ();
	
		this.addStyleName("detailscontainer");
		this.addStyleName("center");
		
		obenPanel.addStyleName("obenPanel");
		hbPanel.addStyleName("hbPanel");
		inhaltPanel.addStyleName("detailsboxInhalt");
		
		formHeaderLabel.addStyleName("formHeaderLabel");
	
		untenPanel.addStyleName("untenPanel");
		
		/* Zusammenbauen der Widgets*/
		
		
		obenPanel.add(formHeaderLabel);
		this.add(obenPanel);
		hbPanel.add(hb);
		this.add(hbPanel);
		
		bov.setTitel("Meine Spielpläne");
	
		
		inhaltPanel.add(bov);
		this.add(inhaltPanel);

		
		
		
		//untenPanel.add(bearbeitenLabel);
		untenPanel.add(spielplanErstellenButton);
		this.add(untenPanel);
		
		spielplanErstellenButton.addClickHandler(new SpielplanErstellenClickHandler());
		
		kinoplaner.getSpielplaeneByAnwenderOwner(new GetSpielplaeneByAnwenderOwnerCallback());
		
		
		/*if (spielplaene != null) {
			felder.resizeRows(spielplaene.size()+1);
			int i=1;
			
			
			for (Spielplan spielplan : spielplaene) {
				Label spielplanname = new Label(spielplan.getName());
				SpielplanAuswaehlenClickHandler click = new SpielplanAuswaehlenClickHandler();
				click.setSpielplan(spielplan);
				spielplanname.addDoubleClickHandler(click);
				felder.setWidget(i, 0, spielplanname);
				i++;
				
	
				
			}
		} else {
			felder.setWidget(1, 0, new Label("Keine Spielpläne verfügbar."));
			Button erstellenButton = new Button ("Erstelle deinen ersten Spielplan");
			erstellenButton.setStyleName("navButton");
			erstellenButton.addDoubleClickHandler(new SpielplanErstellenClickHandler());
			felder.setWidget(2, 0, erstellenButton);
		}
		detailsboxInhalt.add(felder);
		bearbeitenButton.addClickHandler(new SpielplanBearbeitenClickHandler());*/

	}
	
	
	/***CLickHandler***/
/*
 * ClickHandler um einen Spielplan auszuwählen
 */
	
	class SpielplanAuswaehlenClickHandler implements ClickHandler {
		private Spielplan spielplan;

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			anzeigen = new MeineSpielplaeneForm();
			RootPanel.get("details").add(anzeigen);
			
		}

		public void setSpielplan(Spielplan spielplan) {
			this.spielplan = spielplan;
			
		}


		
	
	}
	
	/*
	 * ClickHandler um einen Spielplan zu erstellen
	 */
	
	private class SpielplanErstellenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			erstellen = new SpielplanErstellenForm();
			RootPanel.get("details").add(erstellen);
			
		}
		
		
		
	}
	
	
	/*
	 * ClickHandler um einen Spielplan zu bearbeiten
	 */
	
	private class SpielplanBearbeitenClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			
			bearbeiten = new SpielplanErstellenForm();
			RootPanel.get("details").add(bearbeiten);
		}
		
	}
	
	/****************************************************
	 * Callbacks
	 * ***************************************/
	
	/*
	 * private Klasse um alle Spielplan-Instanzen des Nutzers aus dem System
	 * zu bekommen
	 */
	
	private class GetSpielplaeneByAnwenderOwnerCallback implements AsyncCallback<ArrayList<Spielplan>> {

		@Override
		public void onFailure(Throwable caught) {
			Systemmeldung.anzeigen("Spielplaene nicht abrufbar");
		}

		@Override
		public void onSuccess(ArrayList<Spielplan> result) {
			bov.setSpielplaene(result);
			inhaltPanel.add(bov);
			inhaltPanel.add(bearbeitenLabel);
		}
		
	}
	
	
	}

	
		


	
	
