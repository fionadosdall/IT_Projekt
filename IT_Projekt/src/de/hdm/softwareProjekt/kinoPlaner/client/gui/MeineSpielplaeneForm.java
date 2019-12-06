package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.GruppeAnzeigenForm;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.HomeBar;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielplan;

public class MeineSpielplaeneForm extends VerticalPanel {

	private VerticalPanel detailsoben = new VerticalPanel();
	private VerticalPanel detailsboxInhalt = new VerticalPanel();
	private Label spielplan = new Label("Spielpläne");

	private Button bearbeitenButton = new Button("Edit");

	private SpielplanBearbeitenForm bearbeiten;
	private SpielplanAnzeigenForm anzeigen;
	private ArrayList<Spielplan> spielplaene;
	// Achtung Grid-Entdeckung von Jenny&Moe: Immer eine Nr. größer angeben, als man
	// braucht...
	private Grid felder = new Grid(2, 2);
	private HomeBar hb = new HomeBar();

	public void onLoad() {
		KinoplanerAsync administration = ClientsideSettings.getKinoplaner();

		this.addStyleName("detailscontainer");

		detailsoben.addStyleName("detailsoben");
		detailsboxInhalt.addStyleName("detailsboxInhalt");
		spielplan.addStyleName("title");
		bearbeitenButton.addStyleName("bearbeitenButton");

		this.add(detailsoben);
		this.add(detailsboxInhalt);
		this.add(bearbeitenButton);

		detailsoben.add(spielplan);
		detailsoben.add(bearbeitenButton);

		administration.getSpielplaeneByAnwenderOwner(null);

		felder.setWidget(0, 1, spielplan);

		if (spielplaene != null) {
			felder.resizeRows(spielplaene.size() + 1);
			int i = 1;
			for (Spielplan spielplan : spielplaene) {
				Label spielplanname = new Label();
				SpielplanAuswaehlenClickHandler click = new SpielplanAuswaehlenClickHandler();
				click.setSpielplan(spielplan);
				spielplanname.addDoubleClickHandler(click);
				felder.setWidget(i, 0, spielplanname);
				i++;

			}
		} else {
			felder.setWidget(1, 0, new Label("Keine Spielpläne verfügbar."));
			Button erstellenButton = new Button("Erstelle deinen ersten Spielplan!");
			erstellenButton.setStyleName("navButton");
			erstellenButton.addDoubleClickHandler(new SpielplanErstellenClickHandler());
			felder.setWidget(2, 0, erstellenButton);

		}
	}

	private class SpielplanErstellenClickHandler implements DoubleClickHandler {

		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			// TODO Auto-generated method stub

		}

	}

	private class SpielplanAuswaehlenClickHandler implements DoubleClickHandler {
		private Spielplan spielplan;

		public void onDoubleClick(DoubleClickEvent event) {
			RootPanel.get("details").clear();
			anzeigen = new SpielplanAnzeigenForm();
			RootPanel.get("details").add(anzeigen);
			anzeigen.setSpielplan(spielplan);

		}

		public void setSpielplan(Spielplan spielplan) {
			this.spielplan = spielplan;

		}

	}

}
