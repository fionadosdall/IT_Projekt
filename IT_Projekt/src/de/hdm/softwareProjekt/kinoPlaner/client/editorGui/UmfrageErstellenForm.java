package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;

public class UmfrageErstellenForm extends FlowPanel {

	KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();
	private FlowPanel detailsObenBox = new FlowPanel();
	private FlowPanel detailsMitteBox = new FlowPanel();
	private FlowPanel detailsBoxObenMitte = new FlowPanel();
	private FlowPanel detailsBoxMitteMitte = new FlowPanel();
	private FlowPanel detailsBoxUmfrage = new FlowPanel();
	private FlowPanel detailsBoxFiltern = new FlowPanel();
	private FlowPanel detailsBoxSpeichern = new FlowPanel();

	private Label title = new Label("Umfrage erstellen");
	private Label umfrageLabel = new Label("Umfrage");
	private Label gruppenLabel = new Label("Gruppe");
	private Label terminLabel = new Label("Mögliche Termine");
	private Label filternLabel = new Label("Termine Filtern");

	private TextBox umfrageTextBox = new TextBox();
	private ListBox gruppenListBox = new ListBox();

	private Button erstellenButton = new Button("Umfrage starten");

	private ArrayList<Gruppe> gruppen;
	
	private UmfrageCellTable uct = new UmfrageCellTable();
	private VorstellungCellTable vct = new VorstellungCellTable();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.Widget#onLoad()
	 */
	public void onLoad() {

		this.addStyleName("detailscontainer");

		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");

		title.addStyleName("title");

		umfrageLabel.addStyleName("detailsboxLabels");
		gruppenLabel.addStyleName("detailsboxLabels");
		terminLabel.addStyleName("detailsboxLabels");
		filternLabel.addStyleName("detailsboxLabels");

		detailsObenBox.addStyleName("detailsuntenBoxen");
		detailsMitteBox.addStyleName("detailsuntenBoxen");
		detailsBoxUmfrage.addStyleName("detailsuntenBoxen");
		detailsBoxFiltern.addStyleName("detailsuntenBoxen");
		detailsBoxSpeichern.addStyleName("speichernBox");

		detailsBoxObenMitte.addStyleName("detailsBoxMitte");
		detailsBoxMitteMitte.addStyleName("detailsBoxMitte");

		erstellenButton.addStyleName("speichernButton");

		umfrageTextBox.addStyleName("gruppenameTB");

		umfrageTextBox.getElement().setPropertyString("placeholder", "Anlass für die Umfrage?");

		// Zusammenbauen der Widgets

		this.add(detailsoben);
		this.add(detailsunten);

		detailsoben.add(title);

		detailsunten.add(detailsObenBox);
		detailsObenBox.add(umfrageLabel);
		detailsObenBox.add(detailsBoxObenMitte);
		detailsBoxObenMitte.add(umfrageTextBox);

		detailsunten.add(detailsMitteBox);
		detailsMitteBox.add(gruppenLabel);
		detailsMitteBox.add(detailsBoxMitteMitte);
		detailsBoxMitteMitte.add(gruppenListBox);

		detailsunten.add(detailsBoxUmfrage);
		detailsBoxUmfrage.add(terminLabel);
		detailsBoxUmfrage.add(uct);
		detailsBoxUmfrage.add(vct);

		detailsunten.add(detailsBoxFiltern);
		detailsBoxFiltern.add(filternLabel);

		detailsunten.add(detailsBoxSpeichern);
		detailsBoxSpeichern.add(erstellenButton);

		// ClickHandler
		erstellenButton.addClickHandler(new UmfrageErstellenClickHandler());

		gruppenListBox.setSize("200px", "25px");

		kinoplaner.getGruppenByAnwender(new GruppenCallback());

		if (gruppen == null) {
			gruppenListBox.addItem("Keine Gruppen verfügbar");
			gruppenListBox.setEnabled(false);

		} else {

			for (Gruppe g : gruppen) {

				gruppenListBox.addItem(g.getName());

			}
		}

	}

	/***********************************************************************
	 * CLICKHANDLER
	 ***********************************************************************/

	private class UmfrageErstellenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			String gruppenname = gruppenListBox.getSelectedValue();
			Gruppe g = null;

			if (gruppenname != "") {
				// g = kinoplaner.getGruppeByName(gruppenname);

			} else {
				Window.alert("Bitte zuerst eine Gruppe auswählen");
				return;
			}
			// TODO Auto-generated method stub
			kinoplaner.erstellenUmfrage(umfrageTextBox.getValue(), g.getId(), new UmfrageErstellenCallback());

		}

	}

	/***********************************************************************
	 * CALLBACKS
	 ***********************************************************************/

	private class GruppenCallback implements AsyncCallback<ArrayList<Gruppe>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Es können noch keine Gruppen geladen werden");
		}

		@Override
		public void onSuccess(ArrayList<Gruppe> result) {
			// TODO Auto-generated method stub
			gruppen = result;
		}

	}

	private class UmfrageErstellenCallback implements AsyncCallback<Umfrage> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Umfrage result) {
			// TODO Auto-generated method stub

		}

	}

}
