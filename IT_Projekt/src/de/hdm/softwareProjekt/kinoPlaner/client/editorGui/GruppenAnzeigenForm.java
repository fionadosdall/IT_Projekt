
package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
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

	public void onLoad() {
		this.addStyleName("detailscontainer");
		
		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");
		
		
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

	private class SucheGruppenByAnwenderCallback implements AsyncCallback<ArrayList<Gruppe>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Gruppen nicht abrufbar.");

		}

		@Override
		public void onSuccess(ArrayList<Gruppe> result) {
			bov.setGruppen(result);

		}

		/**
		 * private FlowPanel detailsoben = new FlowPanel(); private FlowPanel
		 * detailsunten = new FlowPanel(); private FlowPanel detailsboxInhalt = new
		 * FlowPanel();
		 * 
		 * private Label title = new Label("Deine Gruppen");
		 * 
		 * private ArrayList<Gruppe> gruppen; private GruppeAnzeigenForm anzeigen;
		 * private GruppeErstellenForm erstellen; private Label gruppe = new
		 * Label("Gruppen");
		 * 
		 * private Grid felder = new Grid(3, 1); private HomeBar hb = new HomeBar();
		 * 
		 * public void onLoad() {
		 * 
		 * 
		 * this.addStyleName("detailscontainer");
		 * 
		 * detailsoben.addStyleName("detailsoben");
		 * detailsunten.addStyleName("detailsunten");
		 * detailsboxInhalt.addStyleName("detailsboxInhalt");
		 * 
		 * title.addStyleName("title");
		 * 
		 * this.add(detailsoben); this.add(detailsunten); this.add(detailsboxInhalt);
		 * 
		 * detailsoben.add(hb); detailsoben.add(title);
		 * 
		 * 
		 * 
		 * }
		 * 
		 * private class GruppeErstellenClickHandler implements ClickHandler {
		 * 
		 * @Override public void onClick(ClickEvent event) {
		 *           RootPanel.get("details").clear(); erstellen = new
		 *           GruppeErstellenForm(); RootPanel.get("details").add(erstellen);
		 * 
		 *           }
		 * 
		 *           }
		 * 
		 * 
		 *           Window.alert(""); gruppen = result;
		 *           gruppe.setStyleName("detailsboxLabels"); felder.setWidget(0, 0,
		 *           gruppe);
		 * 
		 *           if (result != null) {
		 * 
		 *           felder.resizeRows(result.size() +2); int i = 1; for (Gruppe gruppe
		 *           : result) { Label gruppenname = new Label(gruppe.getName());
		 * 
		 *           GruppeAuswaehlenClickHandler click = new
		 *           GruppeAuswaehlenClickHandler(); click.setGruppe(gruppe);
		 *           gruppenname.addDoubleClickHandler(click); felder.setWidget(i, 0,
		 *           gruppenname); i++;
		 * 
		 *           } } else { felder.setWidget(1, 0, new Label("Keine Gruppen
		 *           verfügbar.")); Button erstellenButton= new Button("Erstelle deine
		 *           erste Gruppe!"); erstellenButton.setStyleName("navButton");
		 *           erstellenButton.addClickHandler(new GruppeErstellenClickHandler());
		 *           felder.setWidget(2, 0, erstellenButton);
		 * 
		 *           }
		 * 
		 *           detailsboxInhalt.add(felder);
		 * 
		 *           }
		 * 
		 *           }
		 **/
	}
}
