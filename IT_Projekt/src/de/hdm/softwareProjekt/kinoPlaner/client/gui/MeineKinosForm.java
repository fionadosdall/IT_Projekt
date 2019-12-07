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
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;

public class MeineKinosForm extends FlowPanel {
	
	
	private FlowPanel detailsoben = new FlowPanel ();
	private FlowPanel detailsunten = new FlowPanel ();
	private FlowPanel detailsboxInhalt = new FlowPanel ();
	
	
	private Label title = new Label ("Deine Kinos");
	
	private ArrayList<Kino> kinos;
	
	private Kinokette kinokette;
	private MeineKinosForm anzeigen;
	private KinoErstellenForm erstellen;
	
	private Label kinokettenLabel = new Label ("Kinokette");
	private Label kinoLabel = new Label ("Kino");
	private Label spielplanLabel = new Label ("Spielplan");
	
	private Grid felder = new Grid(3,2);
	private HomeBar hb = new HomeBar ();
	
	public void onLoad() {
		KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();
	
		this.addStyleName("detailscontainer");
		
		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");
		
		title.addStyleName("title");
		
		this.add(detailsoben);
		this.add(detailsunten);
		this.add(detailsboxInhalt);
		
		detailsoben.add(hb);
		detailsoben.add(title);
		
		kinokettenLabel.setStyleName("detailsboxLabels");
		kinoLabel.setStyleName("detailsboxLabels");
		spielplanLabel.setStyleName("detailsboxLabels");
		
		kinoplaner.getKinosByAnwenderOwner(new SucheKinosByAnwenderCallback());
		
		felder.setWidget(0, 0, kinokettenLabel);
		felder.setWidget(0,1,kinoLabel);
		felder.setWidget(0, 2, spielplanLabel);
		
		if (kinos != null) {
			felder.resizeRows(kinos.size());
			int i= 1;
			int j=0;
			
			for (Kino kino : kinos) {
				Label kinoname = new Label (kino.getName());
				KinoAuswaehlenClickHandler click  = new KinoAuswaehlenClickHandler();
				click.setKino(kino);
				kinoname.addDoubleClickHandler(click);
				felder.setWidget(i, 0,kinoname);
				j++;
				kinoplaner.getKinoketteById(kino.getKinokettenId(), new KinoketteByIdCallback());
				
				
			}
		} else {
			felder.setWidget(1,0, new Label("Keine Kinos verfügbar"));
			Button erstellenButton = new Button ("Erstelle dein erstes Kino");
			erstellenButton.setStyleName("navButton");
			erstellenButton.addDoubleClickHandler(new KinoErstellenClickHandler());
			felder.setWidget(2, 0,  erstellenButton);
		}
		
		this.add(felder);
	}
	
	private class KinoAuswaehlenClickHandler implements DoubleClickHandler {
		private Kino kino;

		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			RootPanel.get("details").clear();
			anzeigen = new MeineKinosForm();
			anzeigen.setKino(kino);
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
