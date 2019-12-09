package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RadioButton;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Auswahl;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrageoption;

public class VotingRadioButtonPanel extends FlowPanel {

	private static int counter = 0;
	private String radioButtonGroup = "Voting" + counter;
	private RadioButton rb1 = new RadioButton(radioButtonGroup, "Ja");
	private RadioButton rb2 = new RadioButton(radioButtonGroup, "Nein");
	private KinoplanerAsync kinoplaner;
	private boolean jaIsClicked = false;
	private boolean neinIsClicked = false;
	private Umfrageoption umfrageoption;

	public VotingRadioButtonPanel(Umfrageoption umfrageoption) {
		this.umfrageoption = umfrageoption;
	}

	@Override
	public void onLoad() {
		super.onLoad();
		kinoplaner = ClientsideSettings.getKinoplaner();
		counter++;
		rb1.addClickHandler(new AuswahlClickHandler());
		rb1.addClickHandler(new AuswahlClickHandler());

		this.add(rb1);
		this.add(rb2);

	}
	
	public void setValueJa(boolean value) {
		rb1.setValue(value);
		jaIsClicked = value;
	}
	
	public void setValueNein(boolean value) {
		rb2.setValue(value);
		neinIsClicked = value;
	}

	public void save() {
		String name = umfrageoption.getName() + counter;
		if (jaIsClicked == true) {
			kinoplaner.erstellenAuswahl(name, 1, umfrageoption.getId(), new ErstellenAuswahlCallback());
		} else if (neinIsClicked == true) {
			kinoplaner.erstellenAuswahl(name, -1, umfrageoption.getId(), new ErstellenAuswahlCallback());
		}
	}

	public void setUmfrageoption(Umfrageoption umfrageoption) {
		this.umfrageoption = umfrageoption;
	}

	private class AuswahlClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			jaIsClicked = rb1.getValue();
			neinIsClicked = rb2.getValue();
		}

	}

	private class ErstellenAuswahlCallback implements AsyncCallback<Auswahl> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Anlegen der Auswahl nicht möglich.");

		}

		@Override
		public void onSuccess(Auswahl result) {
			Window.alert("Erfolgreich!");

		}

	}

}
