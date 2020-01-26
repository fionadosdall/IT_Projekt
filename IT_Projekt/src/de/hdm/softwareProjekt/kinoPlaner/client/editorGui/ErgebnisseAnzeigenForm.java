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

/* Die Klasse stellt das Formular um die Umfragen anzuzeigen
 * 
 */

public class ErgebnisseAnzeigenForm extends FlowPanel {
	
	Anwender aktuellerAnwender = AktuellerAnwender.getAnwender();
	
	/* 
	 * Erstellen der Widgets
	 */

	private BusinessObjektView bov = new BusinessObjektView();
	private KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();
	private VerticalPanel p = new VerticalPanel();
	private HomeBar hb = new HomeBar();
	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();
	private FlowPanel detialsbox = new FlowPanel();
	private FlowPanel detailsboxinhalt = new FlowPanel();
	
	private Button erstellenButton = new Button("Umfrage erstellen!");
	/** 
	 * onLoad()- Methode: Die Widgets werden der Form hinzugefügt und formatiert
	 */

	public void onLoad() {
		
		//Vergeben der Stylenamen 
		
		this.addStyleName("detailscontainer");

		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");
		
		erstellenButton.setStyleName("speichernButton");
		
		detialsbox.addStyleName("detailsbox");
		detailsboxinhalt.addStyleName("detailsboxInahlt");
		

		// Zusammenbauen der Widgets
		this.add(detailsoben);
		this.add(detailsunten);

		detailsoben.add(hb);
		detailsunten.add(detialsbox);
		detialsbox.add(p);
		
		p.add(bov);
		
		p.setStyleName("");
		bov.setTitel("Meine Ergebnisse");

		kinoplaner.anzeigenVonClosedUmfragen(aktuellerAnwender, new AnzeigenVonClosedUmfragenCallback());

	}
	
	/**
	 * Callback ruft Methode onFailure() auf, wenn das Anzeigen der Umfragen
	 * misslungen ist und onSuccess () wenn der Aufruf erfolgreich war.
	 *
	 */
	

	private class AnzeigenVonClosedUmfragenCallback implements AsyncCallback<ArrayList<Umfrage>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		
		/**
		 * onSuceess() - Methode: Umfrage Ergebnisse werden auf Verfügbarkeit
		 * geprüft. Es werden Buttons zur verfügung gestellt, mit denen man eine Umfrage 
		 * erstellt od. gespeichert werden kann.
		 * 
		 */
		
		@Override
		public void onSuccess(ArrayList<Umfrage> result) {
			
//			for (Umfrage u : result) {
//				Window.alert(u.getName());
//			}
			
			if (result.size() != 0) {
				bov.setErgebnisse(result);
			} else {
				Label labelT = new Label();
				labelT.setText("Keine Ergebnisse verfügbar!");
				detailsunten.add(labelT);
			}
	
			erstellenButton.addClickHandler(new UmfrageErstellenClickHandler());
			detailsunten.add(erstellenButton);

		}
		
		//Erstellung der ClickHandler

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
