package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;


/*
 * Die Klasse stellt as Formular um die Voting anzuzeigen
 * 
 */
public class VotingsAnzeigenForm extends FlowPanel {
	private Umfrage umfrage;
	
	/*
	 * Erstellen der Widgets
	 */

	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();
	private FlowPanel detailsboxInhalt = new FlowPanel();

	private Label filmLabel = new Label("Film");
	private Label spielzeitLabel = new Label("Spielzeit");
	private Label kinoLabel = new Label("Kino");
	private Label kinoketteLabel = new Label("Kinokette");
	private Label votingLabel = new Label("Voten");
	private Label stadtLabel = new Label("Stadt");

	private VotingsAnzeigenTable vat;

	private Label title = new Label("Umfrage: ");

	/* 
	 * Button erstellen
	 */
	private Button voten = new Button("Voten");
	
	/*
	 * onLoad()-Methode: Die Widgets werden der Form hinzugefügt und formatiert
	 * 
	 */

	public void onLoad() {
		
		/*
		 * Style-Namen vergeben
		 */

		vat = new VotingsAnzeigenTable(umfrage);

		this.addStyleName("detailscontainer");

		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");
		detailsboxInhalt.addStyleName("detailsboxInhalt");
		filmLabel.setStyleName("detailsboxLabels");
		spielzeitLabel.setStyleName("detailsboxLabels");
		kinoLabel.setStyleName("detailsboxLabels");
		kinoketteLabel.setStyleName("detailsboxLabels");
		votingLabel.setStyleName("detailsboxLabels");
		stadtLabel.setStyleName("detailsboxLabels");
		voten.setStyleName("");

		title.addStyleName("title");
		
		/*
		 * Zusammenbauen der Widgets
		 */

		this.add(detailsoben);

		this.add(detailsunten);

		detailsunten.add(detailsboxInhalt);
		detailsunten.add(voten);
		detailsboxInhalt.add(vat);

		detailsoben.add(title);
		title.setText("Umfrage: " + umfrage.getName());

		// if (RootPanel.get("container").getOffsetHeight() > 800
		// & RootPanel.get("container").getOffsetWidth() > 480) {
		//
		// }

		voten.addClickHandler(new VotingsAnzeigenClickHandler());

	}
	
	/*
	 * Konstruktor
	 */

	public VotingsAnzeigenForm(Umfrage umfrage) {
		this.umfrage = umfrage;

	}
	
	/******************************************************
	 * 
	 *CLICKHANDLER 
	 * 
	 *****************************************************
	 */
	
	/*
	 * Click-Handler: Wenn das Voting beendet ist, kann sich der Nutzer, das 
	 * Ergebnis der Umfrage anzeigen lassen. Mit Klick auf den Button
	 * gelangt er zur Anzeigen-Form einer Umfrage
	 */

	private class VotingsAnzeigenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			UmfrageAnzeigenForm anzeigen = new UmfrageAnzeigenForm(umfrage);
			RootPanel.get("details").add(anzeigen);

		}

	}

}
