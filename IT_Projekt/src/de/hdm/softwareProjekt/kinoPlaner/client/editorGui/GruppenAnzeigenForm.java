
package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

//import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
//import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Label;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;
//import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;

public class GruppenAnzeigenForm extends FlowPanel {
		
		ArrayList<Gruppe> gruppen;

		public void onLoad() {
			KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();
			kinoplaner.getGruppenByAnwender(new SucheGruppenByAnwenderCallback());

			Grid felder = new Grid();
			felder.setWidget(0, 0, new Label("Gruppen"));
			int i = 1;
			for (Gruppe gruppe : gruppen) {
				Label gruppenname = new Label(gruppe.getName());
				gruppenname.addDoubleClickHandler(new UmfrageAuswaehlenClickHandler());
				felder.setWidget(i, 0, gruppenname);
				i++;
			}
			this.add(felder);

		}

		private class UmfrageAuswaehlenClickHandler implements DoubleClickHandler {

			@Override
			public void onDoubleClick(DoubleClickEvent event) {

			}

		}

		private class SucheGruppenByAnwenderCallback implements AsyncCallback<ArrayList<Gruppe>> {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Gruppen nicht abrufbar.");

			}

			@Override
			public void onSuccess(ArrayList<Gruppe> result) {
				gruppen = result;

			}

		}

}
