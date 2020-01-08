package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielplan;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;

public class VolltextSucheAdminForm extends FlowPanel{
	
	private String suchText;
	private ArrayList<Kinokette> kinoketten;
	private ArrayList<Kino> kinos;
	private ArrayList<Spielplan> spielplaene;
	
	
	
	KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();
	private FlowPanel speichernBox = new FlowPanel();
	private FlowPanel detailsBoxObenMitte = new FlowPanel();
	private FlowPanel detailsBoxMitteMitte = new FlowPanel();
	private FlowPanel detailsBoxMitteUnten = new FlowPanel();
	private FlowPanel detailsBoxUntenMitte = new FlowPanel();
	private FlowPanel detailsBoxUnten = new FlowPanel();
	private FlowPanel detailsObenBox = new FlowPanel();
	private FlowPanel detailsMitteBox = new FlowPanel();
	private FlowPanel detailsUntenBox = new FlowPanel();

	private Label title = new Label("Suche");
	private Label kinokettenLabel = new Label("Kinoketten");
	private Label kinosLabel = new Label("Kinos");
	private Label spielplaeneLabel = new Label("Spielpläne");

	private Grid kinokettenGrid = new Grid(3, 2);
	private Grid kinosGrid = new Grid(3, 2);
	private Grid spielplaeneGrid = new Grid(3, 2);
	
	private static Boolean edit;

	public VolltextSucheAdminForm(String text) {
		this.suchText = text;
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
		
		/* Vergeben der Style */
		
		
		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");

		detailsObenBox.addStyleName("detailsuntenBoxen");
		detailsMitteBox.addStyleName("detailsuntenBoxen");
		detailsUntenBox.addStyleName("detailsuntenBoxen");

		speichernBox.addStyleName("speichernBox");
		detailsBoxObenMitte.addStyleName("detailsBoxMitte");
		detailsBoxMitteMitte.addStyleName("detailsBoxMitte");
		detailsBoxMitteUnten.addStyleName("detailsBoxMitte");
		detailsBoxUntenMitte.addStyleName("detailsBoxMitte");
		detailsBoxUnten.addStyleName("detailsBoxUnten");

		title.addStyleName("title");
		kinokettenLabel.addStyleName("detailsboxLabels");
		kinosLabel.addStyleName("detailsboxLabels");
		spielplaeneLabel.addStyleName("detailsboxLabels");

		/* Zusammensetzen der Widgets */
		
		this.add(detailsoben);
		this.add(detailsunten);

		detailsoben.add(title);

		detailsunten.add(detailsObenBox);
		detailsunten.add(detailsMitteBox);
		detailsunten.add(detailsUntenBox);

		detailsObenBox.add(kinokettenLabel);
		detailsObenBox.add(detailsBoxObenMitte);
		detailsBoxObenMitte.add(kinokettenGrid);

		detailsMitteBox.add(kinosLabel);
		detailsMitteBox.add(detailsBoxMitteMitte);
		detailsBoxMitteMitte.add(kinosGrid);

		detailsUntenBox.add(spielplaeneLabel);
		detailsUntenBox.add(detailsBoxUntenMitte);
		detailsBoxUntenMitte.add(spielplaeneGrid);
		
		
		kinoplaner.volltextSucheKinoketten(suchText, new VolltextSucheKinokettenCallback());
		if (kinoketten != null) {
			kinokettenGrid.resizeRows(kinoketten.size());
			int i = 0;
			for (Kinokette g : kinoketten) {
				Label kinokettenename = new Label(g.getName());
				KinoketteBearbeitenClickHandler click = new KinoketteBearbeitenClickHandler();
				click.setKinokette(g);
				kinokettenename.addDoubleClickHandler(click);
				kinokettenGrid.setWidget(i, 0, kinokettenename);
				i++;
			}
		} else {
			kinokettenGrid.setWidget(0, 0, new Label("Keine kinoketten gefunden."));
			Button erstellenButton = new Button("Erstelle eine Kinokette!");
			erstellenButton.setStyleName("navButton");
			erstellenButton.addClickHandler(new KinoketteErstellenClickHandler());

			kinokettenGrid.setWidget(1, 0, erstellenButton);
		}

		kinoplaner.volltextSucheKinos(suchText, new VolltextSucheKinoCallback());
		if (kinos != null) {
			kinosGrid.resizeRows(kinos.size());
			int i = 0;
			for (Kino u : kinos) {
				Label kinosname = new Label(u.getName());
				KinoBearbeitenClickHandler click = new KinoBearbeitenClickHandler();
				click.setKino(u);
				kinosname.addDoubleClickHandler(click);
				kinosGrid.setWidget(i, 0, kinosname);
				i++;
			}
		} else {
			kinosGrid.setWidget(0, 0, new Label("Keine Kinos gefunden."));
			Button erstellenButton = new Button("Erstelle eine Kino!");
			erstellenButton.setStyleName("navButton");
			erstellenButton.addClickHandler(new KinoErstellenClickHandler());

			kinosGrid.setWidget(1, 0, erstellenButton);
		}

		kinoplaner.volltextSucheSpielplaene(suchText, new VolltextSucheSpielplaeneCallback());
		if (spielplaene != null) {
			spielplaeneGrid.resizeRows(spielplaene.size());
			int i = 0;
			for (Spielplan sp : spielplaene) {
				Label spielplaenename = new Label(sp.getName());
				SpielplanBearbeitenClickHandler click = new SpielplanBearbeitenClickHandler();
				click.setSpielplan(sp);
				spielplaenename.addDoubleClickHandler(click);
				spielplaeneGrid.setWidget(i, 0, spielplaenename);
				i++;
			}
		} else {
			spielplaeneGrid.setWidget(0, 0, new Label("Keine Spielpläne gefunden."));
			Button erstellenButton = new Button("Erstelle einen Spielplan!");
			erstellenButton.setStyleName("navButton");
			erstellenButton.addClickHandler(new SpielplanErstellenClickHandler());

			spielplaeneGrid.setWidget(1, 0, erstellenButton);

		}

		
	}
	
	/*** CLickHandler ***/
	
	
	private class KinoketteBearbeitenClickHandler implements DoubleClickHandler {

		
		private KinoketteErstellenForm bearbeiten;
		private Kinokette kk;

		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			
			KinoketteErstellenForm.setEdit(edit);
			bearbeiten = new KinoketteErstellenForm(kk);
			RootPanel.get("details").add(bearbeiten);
		
		}
		
		public void setKinokette(Kinokette kk) {
			this.kk = kk;

		}
		
	}
	
	private class KinoketteErstellenClickHandler implements ClickHandler {
		
		private KinoketteErstellenForm erstellen;

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			erstellen = new KinoketteErstellenForm();
			RootPanel.get("details").add(erstellen);
		}
		
	}
	
	private class KinoBearbeitenClickHandler implements DoubleClickHandler {
		
		private KinoErstellenForm bearbeiten;
		private Kino k;

		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			KinoErstellenForm.setEdit(edit);
			bearbeiten = new KinoErstellenForm(k);
			RootPanel.get("details").add(bearbeiten);
		}

		public void setKino(Kino k) {
			this.k = k;

		}
		
	}
	
	private class KinoErstellenClickHandler implements ClickHandler {

		private KinoErstellenForm erstellen;
		
		
		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			erstellen = new KinoErstellenForm();
			RootPanel.get("details").add(erstellen);
		}
		
	}
	
	
	private class SpielplanBearbeitenClickHandler implements DoubleClickHandler {

		private SpielplanErstellenForm bearbeiten;
		private Spielplan sp;

		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			KinoketteErstellenForm.setEdit(edit);
			bearbeiten = new SpielplanErstellenForm(sp);
			RootPanel.get("details").add(bearbeiten);
		}
		
		public void setSpielplan(Spielplan sp) {
			this.sp = sp;

		}
		
	}
	
	private class SpielplanErstellenClickHandler implements ClickHandler {
		
		private SpielplanErstellenForm erstellen;

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			erstellen = new SpielplanErstellenForm();
			RootPanel.get("details").add(erstellen);
			
		}
		
	}
	
	
	/*** Callbacks ***/
	
	
	private class VolltextSucheKinokettenCallback implements AsyncCallback<ArrayList<Kinokette>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(ArrayList<Kinokette> result) {
			kinoketten = result;

		}

	}

	private class VolltextSucheKinoCallback implements AsyncCallback<ArrayList<Kino>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(ArrayList<Kino> result) {
			kinos = result;

		}

	}

	private class VolltextSucheSpielplaeneCallback implements AsyncCallback<ArrayList<Spielplan>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(ArrayList<Spielplan> result) {
			spielplaene = result;

		}

	}
	

}
