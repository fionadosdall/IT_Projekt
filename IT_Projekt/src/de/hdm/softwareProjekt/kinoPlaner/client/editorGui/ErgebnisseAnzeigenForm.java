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
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;

/* Die Klasse stellt das Formular um die Umfragen anzuzeigen
 * 
 */

public class ErgebnisseAnzeigenForm extends FlowPanel {
	
	/* 
	 * Erstellen der Widgets
	 */

	private BusinessObjektView bov = new BusinessObjektView();
	private KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();
	private VerticalPanel p = new VerticalPanel();
	private HomeBar hb = new HomeBar();
	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();

	public void onLoad() {
		
		this.addStyleName("detailscontainer");

		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");

		// Zusammenbauen der Widgets
		this.add(detailsoben);
		this.add(detailsunten);

		detailsoben.add(hb);
		detailsunten.add(p);
		
		p.add(bov);
		
		p.setStyleName("");
		bov.setTitel("Meine Ergebnisse");

		kinoplaner.anzeigenVonClosedUmfragen(new AnzeigenVonClosedUmfragenCallback());

	}

	private class AnzeigenVonClosedUmfragenCallback implements AsyncCallback<ArrayList<Umfrage>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(ArrayList<Umfrage> result) {
			
//			for (Umfrage u : result) {
//				Window.alert(u.getName());
//			}
			
			if (result != null) {
				bov.setErgebnisse(result);
			} else {
				Label labelT = new Label();
				labelT.setText("Keine Ergebnisse verfügbar!");
				detailsunten.add(labelT);
			}
			Button erstellenButton = new Button("Umfrage erstellen!");
			erstellenButton.setStyleName("speichernButton");
			erstellenButton.addClickHandler(new UmfrageErstellenClickHandler());
			detailsunten.add(erstellenButton);

		}

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
