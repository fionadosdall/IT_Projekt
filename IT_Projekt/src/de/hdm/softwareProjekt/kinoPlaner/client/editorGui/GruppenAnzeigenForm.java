
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
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;

public class GruppenAnzeigenForm extends FlowPanel {
	BusinessObjektView bov = new BusinessObjektView();
	KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();
	VerticalPanel p = new VerticalPanel();
	private HomeBar hb = new HomeBar();
	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();
	private Button gruppeErstellen = new Button("Gruppe erstellen");
	

	public void onLoad() {
		this.addStyleName("detailscontainer");
		
		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");
		gruppeErstellen.addStyleName(".speichernButton.gwt-Button");
		
		
		
		// Zusammenbauen der Widgets
		this.add(detailsoben);
		this.add(detailsunten);
		
		
		detailsoben.add(hb);
		
		p.setStyleName("");
		bov.setTitel("Meine Gruppen");
		p.add(bov);
		detailsunten.add(p);
		

		kinoplaner.getGruppenByAnwender(new SucheGruppenByAnwenderCallback());

	}
	
	private class GruppeErstellenHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			GruppeErstellenForm erstellen = new GruppeErstellenForm();
			RootPanel.get("details").add(erstellen);

		}

	}

	private class SucheGruppenByAnwenderCallback implements AsyncCallback<ArrayList<Gruppe>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Gruppen nicht abrufbar.");

		}

		@Override
		public void onSuccess(ArrayList<Gruppe> result) {
			if (result != null) {
				bov.setGruppen(result);
			}else {
			Label labelT = new Label();
			labelT.setText("Keine Umfragen verfügbar!");
			detailsunten.add(labelT);
			}
			gruppeErstellen.addClickHandler(new GruppeErstellenHandler());
			detailsunten.add(gruppeErstellen);

		}

	}
}
