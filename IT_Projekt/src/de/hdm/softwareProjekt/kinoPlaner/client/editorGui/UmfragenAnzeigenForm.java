package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;

import com.google.gwt.user.client.ui.Label;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;

public class UmfragenAnzeigenForm extends FlowPanel {
	ArrayList<Umfrage> umfragen;
	Gruppe gruppe;
	UmfrageAnzeigenForm anzeigen;

	public void onLoad() {
		KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();
		kinoplaner.getUmfragenByAnwender(new SucheUmfrageByAnwenderCallback());

		Grid felder = new Grid();
		felder.setWidget(0, 0, new Label("Umfrage: "));
		felder.setWidget(0, 1, new Label("Gruppe: "));
		int i = 1;
		int j = 0;
		for (Umfrage umfrage : umfragen) {
			Label umfragename = new Label(gruppe.getName());
			umfragename.addDoubleClickHandler(new UmfrageAuswaehlenClickHandler());
			felder.setWidget(i, 0, umfragename);
			felder.setWidget(i, j, new Label(umfrage.getName()));
			j++;
			kinoplaner.getGruppeById(umfrage.getGruppenId(), new GruppeByIdCallback());
			felder.setWidget(i, j, new Label(gruppe.getName()));
			i++;
			j = 0;
			gruppe = null;
		}
		this.add(felder);

	}

	private class UmfrageAuswaehlenClickHandler implements DoubleClickHandler {

		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			anzeigen = new UmfrageAnzeigenForm();

		}

	}

	private class SucheUmfrageByAnwenderCallback implements AsyncCallback<ArrayList<Umfrage>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Umfragen nicht abrufbar");

		}

		@Override
		public void onSuccess(ArrayList<Umfrage> result) {
			umfragen = result;

		}

	}

	private class GruppeByIdCallback implements AsyncCallback<Gruppe> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Gruppe nicht auffindbar.");

		}

		@Override
		public void onSuccess(Gruppe result) {
			gruppe = result;

		}

	}

}
