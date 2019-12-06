package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;

import javax.swing.RootPaneContainer;

import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.client.Window;
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

public class UmfragenAnzeigenForm extends FlowPanel {
	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();
	private FlowPanel detailsboxInhalt = new FlowPanel();

	private Label title = new Label("Deine Umfragen");

	private ArrayList<Umfrage> umfragen;
	private Gruppe gruppe;
	private UmfrageAnzeigenForm anzeigen;
	private UmfrageErstellenForm erstellen;
	private Label umfrageLabel = new Label("Umfrage");
	private Label gruppeLabel = new Label("Gruppe");

	private Grid felder = new Grid(3, 2);
	private HomeBar hb = new HomeBar();

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

		gruppeLabel.setStyleName("detailsboxLabels");
		umfrageLabel.setStyleName("detailsboxLabels");

		kinoplaner.getUmfragenByAnwender(new SucheUmfrageByAnwenderCallback());



		if (umfragen != null) {
			felder.setWidget(0, 0, umfrageLabel);
			felder.setWidget(0, 1, gruppeLabel);
			felder.resizeRows(umfragen.size());
			int i = 1;
			int j = 0;
			for (Umfrage umfrage : umfragen) {
				Label umfragename = new Label(umfrage.getName());
				UmfrageAuswaehlenClickHandler click = new UmfrageAuswaehlenClickHandler();
				click.setUmfrage(umfrage);
				umfragename.addDoubleClickHandler(click);
				felder.setWidget(i, 0, umfragename);
				j++;
				kinoplaner.getGruppeById(umfrage.getGruppenId(), new GruppeByIdCallback());
				felder.setWidget(i, j, new Label(gruppe.getName()));
				i++;
				j = 0;
				gruppe = null;
			}
		} else {
			felder.setWidget(0, 0, new Label("Keine Umfragen verfügbar."));
			Button erstellenButton = new Button("Erstelle deine erste Umfrage!");
			erstellenButton.setStyleName("navButton");
			erstellenButton.addDoubleClickHandler(new UmfrageErstellenClickHandler());
			felder.setWidget(2, 0, erstellenButton);
		}
		this.add(felder);

	}

	private class UmfrageAuswaehlenClickHandler implements DoubleClickHandler {
		private Umfrage umfrage;

		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			RootPanel.get("details").clear();
			anzeigen = new UmfrageAnzeigenForm();
			anzeigen.setUmfrage(umfrage);
			RootPanel.get("details").add(anzeigen);

		}

		public void setUmfrage(Umfrage umfrage) {
			this.umfrage = umfrage;

		}

	}

	private class UmfrageErstellenClickHandler implements DoubleClickHandler {

		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			RootPanel.get("details").clear();
			erstellen = new UmfrageErstellenForm();
			RootPanel.get("details").add(erstellen);

		}

	}

	private class SucheUmfrageByAnwenderCallback implements AsyncCallback<ArrayList<Umfrage>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Umfragen nicht abrufbar");

		}

		@Override
		public void onSuccess(ArrayList<Umfrage> result) {
			umfragen = result;

		}

	}

	private class GruppeByIdCallback implements AsyncCallback<Gruppe> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Gruppe nicht auffindbar.");

		}

		@Override
		public void onSuccess(Gruppe result) {
			gruppe = result;

		}

	}

}
