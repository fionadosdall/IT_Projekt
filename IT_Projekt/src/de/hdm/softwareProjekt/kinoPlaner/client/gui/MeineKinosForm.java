package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import java.util.ArrayList;

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
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;

public class MeineKinosForm extends FlowPanel {
	
	
	private FlowPanel detailsoben = new FlowPanel ();
	private HorizontalPanel hbPanel = new HorizontalPanel();
	private FlowPanel detailsunten = new FlowPanel ();
	private FlowPanel detailsboxInhalt = new FlowPanel ();
	
	
	private Label title = new Label ("Dashboard");
	
	private ArrayList<Kino> kinos;
	
	private Kinokette kinokette;
	private MeineKinosForm anzeigen;
	private KinoErstellenForm erstellen;
	private Label kino = new Label ("Kino");
	
	
	private Grid felder = new Grid(3,1);
	private HomeBarAdmin hb = new HomeBarAdmin();
	private Button loeschenButton = new Button(" Auswahl löschen");
	private Button bearbeitenButton = new Button("Auswahl bearbeiten");
	
	public void onLoad() {
		KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();
	
		this.addStyleName("detailscontainer");
		this.addStyleName("center");
		
		detailsoben.addStyleName("detailsoben");
		hbPanel.addStyleName("hbPanel");
		detailsunten.addStyleName("detailsunten");
		detailsboxInhalt.addStyleName("deatilsboxInhalt");
		
		title.addStyleName("formHeaderLabel");
		
		this.add(detailsoben);
		hbPanel.add(hb);
		this.add(hbPanel);
		this.add(detailsunten);
		this.add(detailsboxInhalt);
		
		//TODO detailsoben.add(hb);
		detailsoben.add(title);
		
		
		
		kinoplaner.getKinosByAnwenderOwner(new SucheKinosByAnwenderCallback());
		
		kino.setStyleName("detailsboxLabel");
		
		if (kinos != null) {
			felder.resizeRows(kinos.size() +1 );
			int i= 1;
			
			
			for (Kino kino : kinos) {
				Label kinoname = new Label (kino.getName());
				KinoAuswaehlenClickHandler click  = new KinoAuswaehlenClickHandler();
				click.setKino(kino);
				kinoname.addDoubleClickHandler(click);
				felder.setWidget(i, 0,kinoname);
				i++;
				
				
				
			}
		} else {
			felder.setWidget(1,0, new Label("Keine Kinos verfügbar"));
			Button erstellenButton = new Button ("Erstelle dein erstes Kino");
			erstellenButton.setStyleName("navButton");
			erstellenButton.addDoubleClickHandler(new KinoErstellenClickHandler());
			felder.setWidget(2, 0,  erstellenButton);
		}
		
		detailsboxInhalt.add(felder);
	}
	
	private class KinoAuswaehlenClickHandler implements DoubleClickHandler {
		private Kino kino;

		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			RootPanel.get("details").clear();
			anzeigen = new MeineKinosForm();
			RootPanel.get("details").add(anzeigen);
			
		}
		
		public void setKino(Kino kino) {
			this.kino = kino;
		}
		
	}
	
	private class KinoErstellenClickHandler implements DoubleClickHandler {

		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			RootPanel.get("details").clear();
			erstellen = new KinoErstellenForm();
			RootPanel.get("details").add(erstellen);
			
		}
		
		
	}
	
	private class SucheKinosByAnwenderCallback  implements AsyncCallback <ArrayList<Kino>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Kinos nicht abrufbar");
			
		}

		@Override
		public void onSuccess(ArrayList<Kino> result) {
			 kinos = result;
			
		}
		
	}
	
	private class KinoketteByIdCallback implements AsyncCallback<Kinokette> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Kinokette nicht auffindbar.");
		}

		@Override
		public void onSuccess(Kinokette result) {
			kinokette = result;
		}
		
	}
	
	
	
	
}
