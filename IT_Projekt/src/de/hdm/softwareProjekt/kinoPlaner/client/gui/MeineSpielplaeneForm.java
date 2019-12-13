package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.GruppeAnzeigenForm;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.HomeBar;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielplan;

public class MeineSpielplaeneForm extends FlowPanel {

	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();
	private FlowPanel detailsboxInhalt = new FlowPanel();
	
	
	private Label title = new Label ("Deine Spielpläne");
	
	private ArrayList <Spielplan> spielplaene;
	private Kino kino;
	private MeineSpielplaeneForm anzeigen;
	private SpielplanErstellenForm erstellen;
	;
	private Label spielplanLabel = new Label ("Spielplan");
	
	private Grid felder = new Grid (3,1);
	private HomeBar hb = new HomeBar();
	
	
	public void onLoad() {
		KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner ();
	
		this.addStyleName("detailscontainer");
		
		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");
		detailsboxInhalt.addStyleName("detailsboxInhalt");

		title.addStyleName("title");
		

		this.add(detailsoben);
		this.add(detailsunten);
		this.add(detailsboxInhalt);

		//TODO detailsoben.add(hb);
		detailsoben.add(title);
		
		spielplanLabel.setStyleName("detailsboxLabels");
		
		kinoplaner.getSpielplaeneByAnwenderOwner(new GetSpielplaeneByAnwenderOwnerCallback());
		
		
		if (spielplaene != null) {
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
	}
	
	
	
	
	private class SpielplanAuswaehlenClickHandler implements DoubleClickHandler {
		private Spielplan spielplan;

		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			RootPanel.get("details").clear();
			anzeigen = new MeineSpielplaeneForm();
			RootPanel.get("details").add(anzeigen);
			
		}

		public void setSpielplan(Spielplan spielplan) {
			this.spielplan = spielplan;
			
		}


		
	
	}
	
	private class SpielplanErstellenClickHandler implements DoubleClickHandler {

		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			RootPanel.get("details").clear();
			erstellen = new SpielplanErstellenForm();
			RootPanel.get("details").add(erstellen);
			
		}
		
		
	}
	
	private class GetSpielplaeneByAnwenderOwnerCallback implements AsyncCallback<ArrayList<Spielplan>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Spielplaene nicht abrufbar");
		}

		@Override
		public void onSuccess(ArrayList<Spielplan> result) {
			spielplaene = result;
		}
		
	}
	
	
	}

	
		


	
	
