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

public class UmfragenAnzeigenForm extends FlowPanel {

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

		p.setStyleName("");
		bov.setTitel("Meine Umfragen");
		p.add(bov);
		detailsunten.add(p);

		kinoplaner.getUmfragenByAnwender(new SucheUmfragenByAnwenderCallback());

	}

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
			Button erstellenButton = new Button("Umfrage erstellen!");
			erstellenButton.setStyleName("speichernButton.gwt-Button");
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
