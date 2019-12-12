package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielplan;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

public class MeineVorstellungForm extends FlowPanel {
	
	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();
	private FlowPanel detailsboxInhalt = new FlowPanel();
	
	
	private Label title = new Label ("Deine Vorstellungen");
	
	private ArrayList <Vorstellung> vorstellungen;
	private Spielplan spielplan;
	private Spielzeit spielzeit;
	private Film film;
	
	private Label vorstellungLabel = new Label ("Vorstellung");
	
	private Grid felder = new Grid (3,2);
	private HomeBar hb = new HomeBar ();
	
	
	public void onLoad() {
		KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();
		
this.addStyleName("detailscontainer");
		
		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");
		detailsboxInhalt.addStyleName("detailsboxInhalt");

		title.addStyleName("title");
		

		this.add(detailsoben);
		this.add(detailsunten);
		this.add(detailsboxInhalt);
		
		detailsoben.add(hb);
		detailsoben.add(title);
		
		vorstellungLabel.setStyleName("detailsboxLabels");
		
		kinoplaner.getVorstellungenBySpielplan(spielplan, new GetVorstellungBySpielplanCallback());
		
		if (vorstellungen != null) {
			felder.resizeRows(vorstellungen.size() +1);
			int i= 1;
			
			
			for (Vorstellung vorstellung : vorstellungen) {
				Label vorstellungname = new Label (vorstellung.getName());
				VorstellungAuswaehlenClickHandler click = new VorstellungAuswaehlenClickHandler ();
				click.setVorstellung(vorstellung);
				vorstellungname.addDoubleClickHandler(click);
				felder.setWidget(i, 0, vorstellungname);
				i++;
			}
		}else {
			felder.setWidget(1, 0, new Label ("Keine Vorstellungen verfügbar"));
			Button erstellenButton = new Button ("Erstelle deine erste Vorstellung");
			erstellenButton.setStyleName("navButton");
			erstellenButton.addDoubleClickHandler(new VorstellungErstellenClickHandler());
			felder.setWidget(2, 0, erstellenButton);
		}
		
		detailsboxInhalt.add(felder);
	}

	
	
	private class VorstellungAuswaehlenClickHandler implements DoubleClickHandler {
		private Vorstellung vorstellung ;

		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			RootPanel.get("details").clear();
			MeineVorstellungForm anzeigen = new MeineVorstellungForm();
			RootPanel.get("details").add(anzeigen);
			
		}
		
		public void setVorstellung(Vorstellung vorstellung) {
			this.vorstellung = vorstellung;
		}
		
		
		
	}
	
	private class VorstellungErstellenClickHandler implements DoubleClickHandler {

		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			RootPanel.get("details").clear();
			VorstellungErstellenForm erstellen = new VorstellungErstellenForm();
			RootPanel.get("details").add(erstellen);
			
	
		}
		
	}
	
	private class GetVorstellungBySpielplanCallback implements AsyncCallback <ArrayList<Vorstellung>> {

		@Override
		public void onFailure(Throwable caught) {
			
			Systemmeldung.anzeigen("Vorstellungen nicht abrufbar");
			
		}

		@Override
		public void onSuccess(ArrayList<Vorstellung> result) {
			vorstellungen = result;
			
		}
		
	}}
	

