package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;

public class ErgebnisseAnzeigenForm extends VerticalPanel {

	BusinessObjektView bov = new BusinessObjektView();
	KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();
	VerticalPanel p = new VerticalPanel();

	public void onLoad() {
		p.setStyleName("");
		bov.setTitel("Meine Ergebnisse");
		p.add(bov);
		this.add(p);

		kinoplaner.anzeigenVonClosedUmfragen(new SucheClosedUmfragenCallback());

	}

	private class SucheClosedUmfragenCallback implements AsyncCallback<ArrayList<Umfrage>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Ergebnisse nicht abrufbar.");

		}

		@Override
		public void onSuccess(ArrayList<Umfrage> result) {
			bov.setErgebnisse(result);
		}
		/**
		 * 
		 * private FlowPanel detailsoben = new FlowPanel(); private FlowPanel
		 * detailsunten = new FlowPanel(); private FlowPanel detailsboxInhalt = new
		 * FlowPanel();
		 * 
		 * private Label title = new Label("Deine Ergebnisse");
		 * 
		 * private ArrayList<Umfrage> umfragen; private Gruppe gruppe; private
		 * ErgebnisAnzeigenForm anzeigen; private Label umfrageLabel = new
		 * Label("Ergebnisse"); private Label gruppeLabel = new Label("Gruppen");
		 * 
		 * private Grid felder = new Grid(3, 2); private HomeBar hb = new HomeBar();
		 * 
		 * public void onLoad() { KinoplanerAsync kinoplaner =
		 * ClientsideSettings.getKinoplaner();
		 * 
		 * this.addStyleName("detailscontainer");
		 * 
		 * detailsoben.addStyleName("detailsoben");
		 * detailsunten.addStyleName("detailsunten");
		 * detailsboxInhalt.addStyleName("detailsboxInhalt");
		 * 
		 * 
		 * 
		 * title.addStyleName("title");
		 * 
		 * this.add(detailsoben); this.add(detailsunten); this.add(detailsboxInhalt);
		 * 
		 * detailsoben.add(hb); detailsoben.add(title);
		 * 
		 * gruppeLabel.setStyleName("detailsboxLabels");
		 * umfrageLabel.setStyleName("detailsboxLabels");
		 * 
		 * kinoplaner.anzeigenVonClosedUmfragen(new
		 * SucheErgebnisseByAnwenderCallback());
		 * 
		 * felder.setWidget(0, 0, umfrageLabel);
		 * 
		 * if (umfragen != null) { felder.setWidget(0, 1, gruppeLabel);
		 * felder.resizeRows(umfragen.size()); int i = 1; int j = 0; for (Umfrage
		 * umfrage : umfragen) { Label umfragename = new Label(umfrage.getName());
		 * UmfrageAuswaehlenClickHandler click = new UmfrageAuswaehlenClickHandler();
		 * click.setUmfrage(umfrage); umfragename.addDoubleClickHandler(click);
		 * felder.setWidget(i, 0, umfragename); j++;
		 * kinoplaner.getGruppeById(umfrage.getGruppenId(), new GruppeByIdCallback());
		 * felder.setWidget(i, j, new Label(gruppe.getName())); i++; j = 0; gruppe =
		 * null; } } else { felder.setWidget(1, 0, new Label("Keine Ergebnisse
		 * verfügbar.")); } this.add(felder);
		 * 
		 * }
		 * 
		 * private class UmfrageAuswaehlenClickHandler implements DoubleClickHandler {
		 * private Umfrage umfrage;
		 * 
		 * @Override public void onDoubleClick(DoubleClickEvent event) {
		 * 
		 *           RootPanel.get("details").clear(); anzeigen = new
		 *           ErgebnisAnzeigenForm(umfrage);
		 *           RootPanel.get("details").add(anzeigen);
		 * 
		 *           }
		 * 
		 *           public void setUmfrage(Umfrage umfrage) { this.umfrage = umfrage;
		 * 
		 *           }
		 * 
		 *           }
		 * 
		 *           private class SucheErgebnisseByAnwenderCallback implements
		 *           AsyncCallback<ArrayList<Umfrage>> {
		 * 
		 * @Override public void onFailure(Throwable caught) { Window.alert("Ergebnis
		 *           nicht abrufbar");
		 * 
		 *           }
		 * 
		 * @Override public void onSuccess(ArrayList<Umfrage> result) { umfragen =
		 *           result;
		 * 
		 *           }
		 * 
		 *           }
		 * 
		 *           private class GruppeByIdCallback implements AsyncCallback<Gruppe> {
		 * 
		 * @Override public void onFailure(Throwable caught) { Window.alert("Gruppe
		 *           nicht auffindbar.");
		 * 
		 *           }
		 * 
		 * @Override public void onSuccess(Gruppe result) { gruppe = result;
		 * 
		 *           }
		 **/

	}

}
