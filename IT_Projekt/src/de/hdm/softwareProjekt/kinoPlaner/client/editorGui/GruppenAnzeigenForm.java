
package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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

public class GruppenAnzeigenForm extends FlowPanel {

	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();
	private FlowPanel detailsboxInhalt = new FlowPanel();

	private Label title = new Label("Deine Gruppen");

	private ArrayList<Gruppe> gruppen;
	private GruppeAnzeigenForm anzeigen;
	private GruppeErstellenForm erstellen;
	private Label gruppe = new Label("Gruppen");

	private Grid felder = new Grid(3, 1);
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

		kinoplaner.getGruppenByAnwender(new SucheGruppenByAnwenderCallback());

	}
	
	private class GruppeErstellenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			erstellen = new GruppeErstellenForm();
			RootPanel.get("details").add(erstellen);		
			
		}
		
	}

	private class GruppeAuswaehlenClickHandler implements DoubleClickHandler {
		private Gruppe gruppe;

		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			RootPanel.get("details").clear();
			anzeigen = new GruppeAnzeigenForm(gruppe);
			RootPanel.get("details").add(anzeigen);

		}

		public void setGruppe(Gruppe gruppe) {
			this.gruppe = gruppe;
		}

	}

	private class SucheGruppenByAnwenderCallback implements AsyncCallback<ArrayList<Gruppe>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Gruppen nicht abrufbar.");

		}

		@Override
		public void onSuccess(ArrayList<Gruppe> result) {
			Window.alert("");
			gruppen = result;
			gruppe.setStyleName("detailsboxLabels");
			felder.setWidget(0, 0, gruppe);
			
			if (result != null) {
				
				felder.resizeRows(result.size() +2);
				int i = 1;
				for (Gruppe gruppe : result) {
					Label gruppenname = new Label(gruppe.getName());
					
					GruppeAuswaehlenClickHandler click = new GruppeAuswaehlenClickHandler();
					click.setGruppe(gruppe);
					gruppenname.addDoubleClickHandler(click);
					felder.setWidget(i, 0, gruppenname);
					i++;

				}
			} else {
				felder.setWidget(1, 0, new Label("Keine Gruppen verfügbar."));
				Button erstellenButton= new Button("Erstelle deine erste Gruppe!");
				erstellenButton.setStyleName("navButton");
				erstellenButton.addClickHandler(new GruppeErstellenClickHandler());
				felder.setWidget(2, 0, erstellenButton);
				
			}

			detailsboxInhalt.add(felder);

		}

	}

}
