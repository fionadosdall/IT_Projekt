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

public class MeineKinokettenForm extends FlowPanel{
	
	
	private FlowPanel detailsoben = new FlowPanel ();
	private FlowPanel detailsunten = new FlowPanel ();
	private FlowPanel detailsboxInhalt = new FlowPanel ();
	
	private Label title = new Label ("Die Kinoketten");
	
	private ArrayList <Kinokette> kinoketten;
	private Kinokette kinokette;
	private Kino kino;
	private MeineKinokettenForm anzeigen;
	private KinoketteErstellenForm erstellen;
	private Label kinokettenlabel = new Label("Kinokette");
	private Label kinolabel = new Label("Kino");
	
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
		
		kinokettenlabel.setStyleName("detailsboxLabels");
		kinolabel.setStyleName("detailsboxLabels");
		
		kinoplaner.getAllKinoketten(new SucheKinoketteCallback());
		
		felder.setWidget(0, 0, kinokettenlabel);
		felder.setWidget(0, 1, kinolabel);
		
		if (kinoketten != null) {
			felder.resizeRows(kinoketten.size());
			int i=1;
			int j=0;
			
			for ( Kinokette kinokette : kinoketten) {
				Label kinokettename = new Label (kinokette.getName());
				
				KinoketteAuswaehlenClickHandler click = new KinoketteAuswaehlenClickHandler();
				click.setKinokette(kinokette);
				kinokettename.addDoubleClickHandler(click);
				felder.setWidget(i, 0, kinokettename);
				j++;
				kinoplaner.getKinoById(kinokette.getKinokettenId(),new KinoketteByIdCallback());
				felder.setWidget(i, j, new Label(kino.getName()));
				i++;
				j=0;
				kino= null;
				}
			
			} else {
					felder.setWidget(1, 0, new Label("Keine Kinoketten verfügbar"));
					Button erstellenButton = new Button ("Erstelle deine erste Kinokette");
					erstellenButton.setStyleName("navButton");
					erstellenButton.addDoubleClickHandler(new KinoketteErstellenClickHandler());
					felder.setWidget(2, 0, erstellenButton);
		}
		
		this.add(felder);
		
	}
	

private class KinoketteAuswaehlenClickHandler implements DoubleClickHandler {
	private Kinokette kinokette;

	@Override
	public void onDoubleClick(DoubleClickEvent event) {
		// TODO Auto-generated method stub
		RootPanel.get("details").clear();
		anzeigen = new MeineKinokettenForm ();
		anzeigen.setKinokette(kinokette);
		RootPanel.get("details").add(anzeigen);
	}

	Kinokette kinokette;
	
	private Label kinokettenFormLabel = new Label("Kinoketten");

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
	
	private class SucheKinoketteCallback implements AsyncCallback {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Kinokette nicht abrufbar");
			
		}

	
		
		public void onSuccess (ArrayList <Kinokette> result) {
			kinoketten = result;
			
		}
		
	}
	
	private class KinoketteByIdCallback implements AsyncCallback <Kinokette> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Kinokette nicht auffindbar");
			
		}

		@Override
		public void onSuccess(Kinokette result) {
			kinokette = result;
			
		}
		
	}
	



}


