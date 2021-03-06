package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.AdminEntry.AktuellerAnwender;
import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.BusinessObjektView;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielplan;

public class MeineSpielplaeneForm extends VerticalPanel {

	Anwender aktuellerAnwender = AktuellerAnwender.getAnwender();

	/* Erstellen der Widgets */
	private HorizontalPanel obenPanel = new HorizontalPanel();
	private HorizontalPanel hbPanel = new HorizontalPanel();
	private VerticalPanel inhaltPanel = new VerticalPanel();
	private HorizontalPanel untenPanel = new HorizontalPanel();
	private BusinessObjektView bov = new BusinessObjektView();
	private HomeBarAdmin hb = new HomeBarAdmin();

	private Label formHeaderLabel = new Label("Dashboard");
	private Label bearbeitenLabel = new Label("Zum Bearbeiten gewünschten Spielplan anklicken.");

	private MeineSpielplaeneForm anzeigen;
	private SpielplanErstellenForm erstellen;
	private Button spielplanErstellenButton = new Button("Spielplan erstellen");

	/* Erstellen der Buttons */

	/* Vergeben der Style-Namen */

	public void onLoad() {
		KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

		this.addStyleName("detailscontainer");
		this.addStyleName("center");

		obenPanel.addStyleName("obenPanel");
		hbPanel.addStyleName("hbPanel");
		inhaltPanel.addStyleName("detailsboxInhalt");

		formHeaderLabel.addStyleName("formHeaderLabel");

		untenPanel.addStyleName("untenPanel");

		spielplanErstellenButton.addStyleName("speichernButton");

		/* Zusammenbauen der Widgets */

		obenPanel.add(formHeaderLabel);
		this.add(obenPanel);
		hbPanel.add(hb);
		this.add(hbPanel);

		bov.setTitel("Meine Spielpläne");

		inhaltPanel.add(bov);
		this.add(inhaltPanel);

		// untenPanel.add(bearbeitenLabel);
		untenPanel.add(spielplanErstellenButton);
		this.add(untenPanel);

		spielplanErstellenButton.addClickHandler(new SpielplanErstellenClickHandler());

		kinoplaner.getSpielplaeneByAnwenderOwner(aktuellerAnwender, new GetSpielplaeneByAnwenderOwnerCallback());

	}

	/*** CLickHandler ***/
	/*
	 * ClickHandler um einen Spielplan auszuwählen
	 */

	class SpielplanAuswaehlenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			anzeigen = new MeineSpielplaeneForm();
			RootPanel.get("details").add(anzeigen);

		}

	}

	/*
	 * ClickHandler um einen Spielplan zu erstellen
	 */

	private class SpielplanErstellenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			erstellen = new SpielplanErstellenForm();
			RootPanel.get("details").add(erstellen);

		}

	}

	/****************************************************
	 * Callbacks
	 ***************************************/

	/*
	 * private Klasse um alle Spielplan-Instanzen des Nutzers aus dem System zu
	 * bekommen
	 */

	private class GetSpielplaeneByAnwenderOwnerCallback implements AsyncCallback<ArrayList<Spielplan>> {

		@Override
		public void onFailure(Throwable caught) {
			Systemmeldung.anzeigen("Spielplaene nicht abrufbar");
		}

		@Override
		public void onSuccess(ArrayList<Spielplan> result) {
			bov.setSpielplaene(result);
			inhaltPanel.add(bov);
			inhaltPanel.add(bearbeitenLabel);
		}

	}

}
