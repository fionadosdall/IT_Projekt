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
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;

public class GruppeAnzeigenForm extends FlowPanel {

	private KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();
	// private Anwender anwender = CurrentAnwender.getAnwender();
	// private Anwender newAnwender= null;

	private Gruppe gruppe;
	
	private ArrayList<Anwender> mitglieder;

	// private TextBox addAnwenderTextBox = new TextBox ();
	// private Button speicherGruppenButton = new Button ("Speichern");
	// private FlexTable mitgliedFlexTable = new FlexTable();
	//

	HomeBar hb = new HomeBar();

	private ArrayList<Umfrage> umfragen;

	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();
	private FlowPanel detailsboxInhalt = new FlowPanel();

	private Label title = new Label("Gruppenname");
	private Label mitgliederLabel = new Label("Gruppenmitglieder");
	private Label umfrageLabel = new Label("Umfragen");

	private Grid felder = new Grid(2, 4);
	private Grid felder2 = new Grid(2, 4);

	public void onLoad() {

		this.addStyleName("detailscontainer");

		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");

		title.addStyleName("title");
		mitgliederLabel.addStyleName("detailsboxLabels");
		umfrageLabel.addStyleName("detailsboxLabels");

		this.add(detailsoben);
		this.add(detailsunten);

		detailsoben.add(hb);
		detailsoben.add(title);
		
		detailsboxInhalt.add(umfrageLabel);

		kinoplaner.getUmfragenByGruppe(gruppe, new SucheUmfrageByGruppeCallback());

		if (umfragen != null) {
			felder.resizeRows(umfragen.size());
			int i = 0;
			for (Umfrage umfrage : umfragen) {
				Label umfragename = new Label(umfrage.getName());
				UmfrageAuswaehlenClickHandler click = new UmfrageAuswaehlenClickHandler();
				click.setUmfrage(umfrage);
				umfragename.addDoubleClickHandler(click);
				felder.setWidget(i, 0, umfragename);
				i++;
				gruppe = null;
			}
		} else {
			felder.setWidget(0, 0, new Label("Keine Umfragen verfügbar."));
			Button erstellenButton = new Button("Erstelle deine erste Umfrage!");
			erstellenButton.setStyleName("navButton");
			erstellenButton.addClickHandler(new UmfrageErstellenClickHandler());

			felder.setWidget(2, 0, erstellenButton);
		}
		detailsboxInhalt.add(felder);
		detailsboxInhalt.add(mitgliederLabel);
		
		kinoplaner.getGruppenmitgliederByGruppe(gruppe, new SucheGruppenmitgliederByGruppeCallback());
		if (mitglieder != null) {
			felder.resizeRows(mitglieder.size());
			int i = 0;
			for (Anwender a : mitglieder) {
				felder.setWidget(i, 0, new Label(a.getName()));
				i++;
		
			}
		} else {
			felder.setWidget(0, 0, new Label("Keine Gruppenmitglieder verfügbar."));

		}
		detailsboxInhalt.add(felder);

	}

	public GruppeAnzeigenForm(Gruppe gruppe) {
		this.gruppe = gruppe;
	}

	private class UmfrageAuswaehlenClickHandler implements DoubleClickHandler {
		private Umfrage umfrage;

		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			RootPanel.get("details").clear();
			UmfrageAnzeigenForm anzeigen = new UmfrageAnzeigenForm(umfrage);
			RootPanel.get("details").add(anzeigen);

		}

		public void setUmfrage(Umfrage umfrage) {
			this.umfrage = umfrage;

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
	
	private class SucheGruppenmitgliederByGruppeCallback implements AsyncCallback<ArrayList<Anwender>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(ArrayList<Anwender> result) {
			mitglieder = result;
			
		}
		
	}

	private class SucheUmfrageByGruppeCallback implements AsyncCallback<ArrayList<Umfrage>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(ArrayList<Umfrage> result) {
			umfragen = result;

		}

	}

}
