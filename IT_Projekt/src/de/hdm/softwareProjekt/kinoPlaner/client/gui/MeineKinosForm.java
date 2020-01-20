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
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;

public class MeineKinosForm extends VerticalPanel {
	
	
	private HorizontalPanel obenPanel = new HorizontalPanel();
	private HorizontalPanel hbPanel = new HorizontalPanel();
	private HorizontalPanel formHeaderPanel = new HorizontalPanel();
	private VerticalPanel inhaltPanel = new VerticalPanel();
	private HorizontalPanel untenPanel = new HorizontalPanel();
	
	private BusinessObjektView bov = new BusinessObjektView();
	
	
	
	private ArrayList<Kino> kinos;
	private KinoErstellenForm bearbeiten;
	private static Boolean edit = true;
	
	private Kinokette kinokette;
	private MeineKinosForm anzeigen;
	private KinoErstellenForm erstellen;
	private Label formHeaderLabel = new Label("Dashboard");
	private Label bearbeitenLabel = new Label("Zum bearbeiten gewünschtes Kino anklicken.");

	
	
	private Grid felder = new Grid(3,1);
	private HomeBarAdmin hb = new HomeBarAdmin();
	

	
	
	
	public void onLoad() {
		KinoplanerAsync administration = ClientsideSettings.getKinoplaner();
	
		
		/* Vergeben der Stylename*/
		
		this.addStyleName("detailscontainer");
		this.addStyleName("center");
		
		obenPanel.addStyleName("obenPanel");
		hbPanel.addStyleName("hbPanel");
		inhaltPanel.addStyleName("inhaltPanel");
		
		untenPanel.addStyleName("untenPanel");
		formHeaderLabel.addStyleName("formHeaderLabel");
		
		/*Zusammen bauen der Widgets*/
		
		
		
		obenPanel.add(formHeaderLabel);
		this.add(obenPanel);
		hbPanel.add(hb);
		this.add(hbPanel);
		
		bov.setTitel("Meine Kinos");
		administration.getAllKinos(new SucheKinosByAnwenderCallback());
		
		inhaltPanel.add(bov);
		this.add(inhaltPanel);
		
		
		
		
		
		
		
		
		/*kinoplaner.getKinosByAnwenderOwner(new SucheKinosByAnwenderCallback());
		
	
		
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
		
		//detailsboxInhalt.add(felder);*/
		
		untenPanel.add(bearbeitenLabel);
		this.add(untenPanel);
		
		

	}
	
	
	/*CLickHandler*/
	
	
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
	
	private class KinoBearbeitenClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();

			
			
			bearbeiten = new KinoErstellenForm();
			
			RootPanel.get("details").add(bearbeiten);
		}
		
	}
	
	/*Callbacks*/
	
	private class SucheKinosByAnwenderCallback  implements AsyncCallback <ArrayList<Kino>> {

		@Override
		public void onFailure(Throwable caught) {
			Systemmeldung.anzeigen("Kinos nicht abrufbar");
			
		}

		@Override
		public void onSuccess(ArrayList<Kino> result) {
			bov.setKinos(result);
			inhaltPanel.add(bov);
			
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
	
	private class KinoLoeschenCallback implements AsyncCallback<Kinokette> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Kinokette result) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
}
