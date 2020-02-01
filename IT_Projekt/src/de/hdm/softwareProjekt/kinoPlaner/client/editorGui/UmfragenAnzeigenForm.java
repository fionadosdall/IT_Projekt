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
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;


/***
 * Die Klasse stellt das Formular für das Anzeigen einer Umfrage
 * 
 */
public class UmfragenAnzeigenForm extends FlowPanel {
	
	Anwender aktuellerAnwender = AktuellerAnwender.getAnwender();
	
	/*
	 * BusinessObjectView = Vorlage um die Ansicht von Business Object
	 * zu erstellen. Bo's werden in CellLists angezeigt
	 */

	private BusinessObjektView bov = new BusinessObjektView();
	private KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();
	
	/**
	 * Erstellen der Widgets
	 */
	private VerticalPanel p = new VerticalPanel();
	private HomeBar hb = new HomeBar();
	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();
	private FlowPanel detialsbox = new FlowPanel();
	private FlowPanel detailsboxinhalt = new FlowPanel();
	
	private Button erstellenButton = new Button("Umfrage erstellen!");
	
	/*
	 * onLoad()- Methode: Die Widgets werden der Form hinzugefügt
	 * und formatiert
	 */

	public void onLoad() {
		
		
		/*
		 * Style-Namen vergeben
		 */
		this.addStyleName("detailscontainer");


		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");
		
		detialsbox.addStyleName("detailsBox");
		detailsboxinhalt.addStyleName("detailsboxInahlt");
		
		erstellenButton.setStyleName("speichernButton.gwt-Button");

		

		// Zusammenbauen der Widgets
		this.add(detailsoben);
		this.add(detailsunten);

		detailsoben.add(hb);
		
		detailsunten.add(detialsbox);
		detialsbox.add(bov);

//		p.setStyleName("");
		bov.setTitel("Meine Umfragen");
//		p.add(bov);

		kinoplaner.getOpenUmfragenByAnwender(aktuellerAnwender, new SucheUmfragenByAnwenderCallback());

	}
	
	/***
	 * 
	 * Private Klasse
	 * alle Umfrage-Instanzen, zu denen der Anwender gehört, aus dem System bekommen
	 *
	 */

	private class SucheUmfragenByAnwenderCallback implements AsyncCallback<ArrayList<Umfrage>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(ArrayList<Umfrage> result) {
			if (result.size() != 0) {
				bov.setUmfragen(result);
			} else {
				Label labelT = new Label();
				labelT.setText("Keine Umfragen verfügbar!");
				detailsunten.add(labelT);
			}
			erstellenButton.addClickHandler(new UmfrageErstellenClickHandler());
			detailsunten.add(erstellenButton);
	

		}
		
		
		/**
		 * Click-Handler: Wenn Anwender, die passende Umfrage noch nicht vorfindent,
		 * kann er eine neue Umfrage erstellen.
		 * Mit dem Klick auf den Button gelangt er zur Erstellen-Form einer Gruppe
		 * 
		 *
		 */

		private class UmfrageErstellenClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get("details").clear();
				UmfrageErstellenForm erstellen = new UmfrageErstellenForm();
				RootPanel.get("details").add(erstellen);

			}

		}

	}
}
