
package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.client.EditorEntry.AktuellerAnwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;

/*
 * Diese Klasse stellt das Formular für das Anzeigen der Gruppen. Gruppenübersicht.
 */

public class GruppenAnzeigenForm extends FlowPanel {
	
	Anwender aktuellerAnwender = AktuellerAnwender.getAnwender();

	/*
	 * BusinessObjectView = Vorlage um die Ansicht von Business Objekten zu
	 * erstellen. BOs werden in CellLists angezeigt.
	 */
	BusinessObjektView bov = new BusinessObjektView();

	KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

	/*
	 * Erstellen der Widgets
	 */
	VerticalPanel p = new VerticalPanel();
	private HomeBar hb = new HomeBar();
	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();
	private FlowPanel detialsbox = new FlowPanel();
	private FlowPanel detailsboxinhalt = new FlowPanel();

	/*
	 * Button erstellen
	 */
	private Button gruppeErstellen = new Button("Gruppe erstellen");

	/*
	 * onLoad()-Methode: Die Widgets werden der Form hinzugefügt und formatiert.
	 */
	public void onLoad() {

		/*
		 * Style-Namen vergeben
		 */
		this.addStyleName("detailscontainer");

		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");
		gruppeErstellen.addStyleName("speichernButton");
		
		detialsbox.addStyleName("detailsbox");
		detailsboxinhalt.addStyleName("detailsboxInahlt");
		
		/*
		 * Zusammenbauen der Widgets
		 */
		this.add(detailsoben);
		this.add(detailsunten);
		
		detailsunten.add(detialsbox);
		detialsbox.add(p);

		detailsoben.add(hb);

		p.setStyleName("");
		bov.setTitel("Meine Gruppen");
		p.add(bov);

		kinoplaner.getGruppenByAnwender(aktuellerAnwender, new SucheGruppenByAnwenderCallback());

	}

	/*********************************************************************************************
	 * CLICKHANDLER
	 *********************************************************************************************/

	/*
	 * Click-Handler: Wenn der Nutzer die passende Gruppe noch nicht in der Anzeige
	 * vorfindet, kann er eine neue Gruppe erstellen. Mit Klick auf den Button
	 * gelangt er zur Erstellen-Form einer Gruppe.
	 */
	private class GruppeErstellenHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			GruppeErstellenForm erstellen = new GruppeErstellenForm();
			RootPanel.get("details").add(erstellen);

		}

	}

	/***********************************************************************************************
	 * ASYNCCALLBACKS
	 ***********************************************************************************************/

	/*
	 * Private Klasse, um alle Gruppen-Instanzen, zu denen der Anwender gehört, aus
	 * dem System zu bekommen.
	 */
	private class SucheGruppenByAnwenderCallback implements AsyncCallback<ArrayList<Gruppe>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Gruppen nicht abrufbar.");

		}

		@Override
		public void onSuccess(ArrayList<Gruppe> result) {
			if (result.size() != 0) {
				bov.setGruppen(result);
			} else {
				Label labelT = new Label();
				labelT.setText("Keine Umfragen verfügbar!");
				detailsunten.add(labelT);
			}
			gruppeErstellen.addClickHandler(new GruppeErstellenHandler());
			detailsunten.add(gruppeErstellen);

		}

	}
}
