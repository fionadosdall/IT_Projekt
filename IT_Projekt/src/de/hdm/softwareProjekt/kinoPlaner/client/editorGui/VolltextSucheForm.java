package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

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
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;

public class VolltextSucheForm extends FlowPanel {
	private String suchText;
	private ArrayList<Gruppe> gruppen;
	private ArrayList<Umfrage> umfragen;
	private ArrayList<Umfrage> ergebnisse;

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
	private Label gruppe = new Label("Gruppen");
	private Label umfrage = new Label("Umfragen");
	private Label ergebnis = new Label("Ergebnisse");

	private Grid gruppenGrid = new Grid(3, 2);
	private Grid umfragenGrid = new Grid(3, 2);
	private Grid ergebnisseGrid = new Grid(3, 2);

	public VolltextSucheForm(String text) {
		this.suchText = text;
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");

		detailsObenBox.addStyleName("detailsObenBoxen");
		detailsMitteBox.addStyleName("detailsMitteBoxen");
		detailsUntenBox.addStyleName("detailsUntenBoxen");

		speichernBox.addStyleName("speichernBox");
		detailsBoxObenMitte.addStyleName("detailsBoxObenMitte");
		detailsBoxMitteMitte.addStyleName("detailsBoxMitteMitte");
		detailsBoxMitteUnten.addStyleName("detailsBoxMitteUnten");
		detailsBoxUntenMitte.addStyleName("detailsBoxUntenMitte");
		detailsBoxUnten.addStyleName("detailsBoxUnten");

		title.addStyleName("title");
		gruppe.addStyleName("detailsboxLabels");
		umfrage.addStyleName("detailsboxLabels");
		ergebnis.addStyleName("detailsboxLabels");

		this.add(detailsoben);
		this.add(detailsunten);

		detailsoben.add(title);

		detailsunten.add(detailsObenBox);
		detailsunten.add(detailsMitteBox);
		detailsunten.add(detailsUntenBox);

		detailsObenBox.add(gruppe);
		detailsObenBox.add(detailsBoxObenMitte);
		detailsBoxObenMitte.add(gruppenGrid);

		detailsMitteBox.add(umfrage);
		detailsMitteBox.add(detailsBoxMitteMitte);
		detailsBoxMitteMitte.add(umfragenGrid);

		detailsUntenBox.add(ergebnis);
		detailsUntenBox.add(detailsBoxUntenMitte);
		detailsBoxUntenMitte.add(ergebnisseGrid);

		kinoplaner.volltextSucheGruppen(suchText, new VolltextSucheGruppenCallback());
		if (gruppen != null) {
			gruppenGrid.resizeRows(gruppen.size());
			int i = 0;
			for (Gruppe g : gruppen) {
				Label gruppenename = new Label(g.getName());
				GruppeAuswaehlenClickHandler click = new GruppeAuswaehlenClickHandler();
				click.setGruppe(g);
				gruppenename.addDoubleClickHandler(click);
				gruppenGrid.setWidget(i, 0, gruppenename);
				i++;
			}
		} else {
			gruppenGrid.setWidget(0, 0, new Label("Keine Gruppen gefunden."));
			Button erstellenButton = new Button("Erstelle eine Gruppe!");
			erstellenButton.setStyleName("navButton");
			erstellenButton.addClickHandler(new GruppeErstellenClickHandler());

			gruppenGrid.setWidget(1, 0, erstellenButton);
		}

		kinoplaner.volltextSucheUmfragen(suchText, new VolltextSucheUmfrageCallback());
		if (umfragen != null) {
			umfragenGrid.resizeRows(umfragen.size());
			int i = 0;
			for (Umfrage u : umfragen) {
				Label umfragename = new Label(u.getName());
				UmfrageAuswaehlenClickHandler click = new UmfrageAuswaehlenClickHandler();
				click.setUmfrage(u);
				umfragename.addDoubleClickHandler(click);
				umfragenGrid.setWidget(i, 0, umfragename);
				i++;
			}
		} else {
			umfragenGrid.setWidget(0, 0, new Label("Keine Umfragen gefunden."));
			Button erstellenButton = new Button("Erstelle eine Umfrage!");
			erstellenButton.setStyleName("navButton");
			erstellenButton.addClickHandler(new UmfrageErstellenClickHandler());

			umfragenGrid.setWidget(1, 0, erstellenButton);
		}

		kinoplaner.volltextSucheErgebnisse(suchText, new VolltextSucheErgebnisseCallback());
		if (ergebnisse != null) {
			ergebnisseGrid.resizeRows(ergebnisse.size());
			int i = 0;
			for (Umfrage e : ergebnisse) {
				Label ergebnissename = new Label(e.getName());
				ErgebnisAuswaehlenClickHandler click = new ErgebnisAuswaehlenClickHandler();
				click.setErgebnis(e);
				ergebnissename.addDoubleClickHandler(click);
				ergebnisseGrid.setWidget(i, 0, ergebnissename);
				i++;
			}
		} else {
			ergebnisseGrid.setWidget(0, 0, new Label("Keine Ergebnisse gefunden."));

		}

	}

	private class ErgebnisAuswaehlenClickHandler implements DoubleClickHandler {

		private Umfrage ergebnis;

		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			RootPanel.get("details").clear();
			ErgebnisAnzeigenForm anzeigen = new ErgebnisAnzeigenForm(ergebnis);
			RootPanel.get("details").add(anzeigen);

		}

		public void setErgebnis(Umfrage e) {
			this.ergebnis = e;

		}

	}

	private class UmfrageErstellenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			UmfrageErstellenForm erstellen = new UmfrageErstellenForm();
			RootPanel.get("details").add(erstellen);

		}

	}

	private class UmfrageAuswaehlenClickHandler implements DoubleClickHandler {
		private Umfrage umfrage;

		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			RootPanel.get("details").clear();
			UmfrageAnzeigenForm anzeigen = new UmfrageAnzeigenForm(umfrage);
			RootPanel.get("details").add(anzeigen);

		}

		public void setUmfrage(Umfrage u) {
			umfrage = u;

		}

	}

	private class GruppeErstellenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			GruppeErstellenForm erstellen = new GruppeErstellenForm();
			RootPanel.get("details").add(erstellen);

		}

	}

	private class GruppeAuswaehlenClickHandler implements DoubleClickHandler {

		private Gruppe gruppe;

		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			RootPanel.get("details").clear();
			GruppeAnzeigenForm anzeigen = new GruppeAnzeigenForm(gruppe);
			RootPanel.get("details").add(anzeigen);

		}

		public void setGruppe(Gruppe gruppe) {
			this.gruppe = gruppe;
		}

	}

	private class VolltextSucheErgebnisseCallback implements AsyncCallback<ArrayList<Umfrage>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(ArrayList<Umfrage> result) {
			ergebnisse = result;

		}

	}

	private class VolltextSucheUmfrageCallback implements AsyncCallback<ArrayList<Umfrage>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(ArrayList<Umfrage> result) {
			umfragen = result;

		}

	}

	private class VolltextSucheGruppenCallback implements AsyncCallback<ArrayList<Gruppe>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(ArrayList<Gruppe> result) {
			gruppen = result;

		}

	}

}
