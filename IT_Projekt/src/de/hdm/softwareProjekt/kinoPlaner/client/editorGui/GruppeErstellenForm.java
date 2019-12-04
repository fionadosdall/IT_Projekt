package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;

public class GruppeErstellenForm extends FlowPanel {

	KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();
	private FlowPanel speichernBox = new FlowPanel();
	private FlowPanel detailsBoxObenMitte = new FlowPanel();
	private FlowPanel detailsBoxMitteMitte = new FlowPanel();
	private FlowPanel detailsBoxMitteUnten = new FlowPanel();
	private FlowPanel detailsBoxUntenMitte = new FlowPanel();
	private FlowPanel detailsBoxUnten = new FlowPanel();
	private FlowPanel detailsObenBox = new FlowPanel();
	private FlowPanel detailsMitteBox = new FlowPanel();
	private FlowPanel detailsUntenBox = new FlowPanel();

	private Label title = new Label("Gruppe erstellen");
	private Label gruppenname = new Label("Gruppenname");
	private Label mitglied = new Label("Mitglied hinzufügen");
	private Label mitglieder = new Label("Gruppenmitglieder");

	private TextBox gruppenameTB = new TextBox();

	private MultiWordSuggestOracle alleAnwender = new MultiWordSuggestOracle();
	private SuggestBox mitgliedTB = new SuggestBox(alleAnwender);

	private Button hinzufuegenButton = new Button("Hinzufügen");
	private Button entfernenButton = new Button("Mitglied entfernen");
	private Button speichernButton = new Button("Speichern");

	private ListBox mitgliederLB = new ListBox();

	public void onLoad() {

		// Vergeben der Stylenames

		this.addStyleName("detailscontainer");

		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");

		detailsObenBox.addStyleName("detailsuntenBoxen");
		detailsMitteBox.addStyleName("detailsuntenBoxen");
		detailsUntenBox.addStyleName("detailsuntenBoxen");
		
		speichernBox.addStyleName("speichernBox");
		detailsBoxObenMitte.addStyleName("detailsBoxMitte");
		detailsBoxMitteMitte.addStyleName("detailsBoxMitte");
		detailsBoxMitteUnten.addStyleName("detailsBoxMitte");
		detailsBoxUntenMitte.addStyleName("detailsBoxMitte");
		detailsBoxUnten.addStyleName("detailsBoxUnten");

		title.addStyleName("title");
		gruppenname.addStyleName("detailsboxLabels");
		mitglied.addStyleName("detailsboxLabels");
		mitglieder.addStyleName("detailsboxLabels");

		gruppenameTB.addStyleName("gruppenameTB");
		mitgliedTB.addStyleName("nameTextBox");

		hinzufuegenButton.addStyleName("hinzufuegenButton");
		entfernenButton.addStyleName("entfernenButton");
		speichernButton.addStyleName("speichernButton");

		gruppenameTB.getElement().setPropertyString("placeholder", "Gruppenname eingeben");
		mitgliedTB.getElement().setPropertyString("placeholder", "User suchen");

		// Zusammenbauen der Widgets

		this.add(detailsoben);
		this.add(detailsunten);

		detailsoben.add(title);

		detailsunten.add(detailsObenBox);
		detailsunten.add(detailsMitteBox);
		detailsunten.add(detailsUntenBox);

		detailsObenBox.add(gruppenname);
		detailsObenBox.add(detailsBoxObenMitte);
		detailsBoxObenMitte.add(gruppenameTB);

		detailsMitteBox.add(mitglied);
		detailsMitteBox.add(detailsBoxMitteMitte);
		detailsBoxMitteMitte.add(mitgliedTB);
		detailsMitteBox.add(detailsBoxMitteUnten);
		detailsBoxMitteUnten.add(hinzufuegenButton);

		detailsUntenBox.add(mitglieder);
		detailsUntenBox.add(detailsBoxUntenMitte);
		detailsBoxUntenMitte.add(mitgliederLB);
		detailsUntenBox.add(detailsBoxUnten);
		detailsBoxUnten.add(entfernenButton);

		detailsunten.add(speichernBox);
		speichernBox.add(speichernButton);

		// Click-Handler
		hinzufuegenButton.addClickHandler(new MitgliedHinzufuegenClickHandler());
		entfernenButton.addClickHandler(new MitgliedEntfernenClickHandler());
		speichernButton.addClickHandler(new SpeichernClickHandler());

	}

	/***********************************************************************
	 * CLICKHANDLER
	 ***********************************************************************/

	private class MitgliedHinzufuegenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub

		}

	}

	private class MitgliedEntfernenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub

		}

	}

	private class SpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub

		}

	}

}
