package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.client.EditorEntry.aktuellerAnwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;

/***
 * Die Klasse stellt das Formular um die Umfragen anzuzeigen
 * 
 * @author fiona
 *
 */
public class UmfrageAnzeigenForm extends FlowPanel {
	private Umfrage umfrage = null;

	private KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

	/**
	 * Erstellen der Widgets
	 * 
	 */
	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();
	private FlowPanel detailsboxInhalt = new FlowPanel();
	private FlowPanel detailsBoxUmfrage = new FlowPanel();
	private FlowPanel detailsboxlöschen = new FlowPanel();
	private FlowPanel löschenImage = new FlowPanel();

	private Label filmLabel = new Label("Film");
	private Label spielzeitLabel = new Label("Spielzeit");
	private Label kinoLabel = new Label("Kino");
	private Label kinoketteLabel = new Label("Kinokette");
	private Label votingLabel = new Label("Voten");
	private Label stadtLabel = new Label("Stadt");

	private Label title = new Label("Umfrage: ");

	private UmfrageAnzeigenTable uat;

	/***
	 * Buttons erstellen
	 */
	private Button speichern = new Button("Speichern");
	private Button votingsAnzeigen = new Button("Votings anzeigen");
	private Button bearbeiten = new Button("Bearbeiten");

	private Image papierkorb = new Image();

	/**
	 * onLoad()- Methode : Die Widgets werden der Form hinzugefügt und formatiert
	 */
	public void onLoad() {

		/**
		 * Stylenamen vergeben
		 * 
		 */

		uat = new UmfrageAnzeigenTable(umfrage);

		this.addStyleName("detailscontainer");

		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");

		detailsboxInhalt.addStyleName("detailsboxInhalt");
		detailsBoxUmfrage.addStyleName("detailsuntenBoxen");
		detailsboxlöschen.addStyleName("detailsboxlöschen");
		filmLabel.setStyleName("detailsboxLabels");
		spielzeitLabel.setStyleName("detailsboxLabels");
		kinoLabel.setStyleName("detailsboxLabels");
		kinoketteLabel.setStyleName("detailsboxLabels");
		votingLabel.setStyleName("detailsboxLabels");
		stadtLabel.setStyleName("detailsboxLabels");
		papierkorb.addStyleName("papierkorb");
		papierkorb.setUrl("/images/papierkorb.png");
		speichern.setStyleName("");
		votingsAnzeigen.setStyleName("");
		bearbeiten.setStyleName("");
		löschenImage.addStyleName("löschenImage");
		löschenImage.add(papierkorb);
		papierkorb.addClickHandler(new UmfrageLoeschenClickHandler());

		title.addStyleName("title");

		/**
		 * Zusammenbauen der Widgets
		 */
		this.add(detailsoben);
		this.add(detailsunten);

		detailsoben.add(title);
		title.setText("Umfrage: " + umfrage.getName());
		if (umfrage.getBesitzerId() == aktuellerAnwender.getAnwender().getId())
			detailsunten.add(detailsboxlöschen);

		detailsunten.add(detailsBoxUmfrage);
		detailsunten.add(detailsboxInhalt);

		detailsBoxUmfrage.add(uat);
		detailsboxInhalt.add(speichern);
		detailsboxInhalt.add(votingsAnzeigen);
		if (umfrage.getBesitzerId() == aktuellerAnwender.getAnwender().getId())
			detailsboxInhalt.add(bearbeiten);
		detailsboxlöschen.add(löschenImage);

		speichern.addClickHandler(new SpeichernClickHandler());
		votingsAnzeigen.addClickHandler(new VotingsAnzeigenClickHandler());
		bearbeiten.addClickHandler(new UmfrageBearbeitenClickHandler());

	}

	/**
	 * Vor dem Löschen einer Umfrage soll der Nutzer über eine Dialogbox nochmal um
	 * Bestätigung des Löschvorgangs gebeten werden.
	 * 
	 * @author
	 *
	 */
	private class UmfrageLoeschenDialogBox extends DialogBox {

		private VerticalPanel verticalPanel = new VerticalPanel();
		private HorizontalPanel buttonPanel = new HorizontalPanel();

		private Label nachfrage = new Label("Umfrage endgültig löschen?");

		private Button jaButton = new Button("Ja");
		private Button neinButton = new Button("Nein");

		/**
		 * Konstruktor
		 */

		public UmfrageLoeschenDialogBox() {

			nachfrage.addStyleName("Abfrage");
			jaButton.addStyleName("buttonAbfrage");
			neinButton.addStyleName("buttonAbfrage");

			buttonPanel.add(jaButton);
			buttonPanel.add(neinButton);
			verticalPanel.add(nachfrage);
			verticalPanel.add(buttonPanel);

			this.add(verticalPanel);

			/**
			 * ClickHandler für die UmfrageLöschenDialogbox
			 */

			jaButton.addClickHandler(new UmfrageLoeschenBestaetigenClickHandler(this));
			neinButton.addClickHandler(new UmfrageLoeschenAbbrechenClickHandler(this));

		}
	}

	// ClickHandler zur Lösch-Bestätigung der Umfrage

	private class UmfrageLoeschenBestaetigenClickHandler implements ClickHandler {

		private UmfrageLoeschenDialogBox umfrageLoeschenDB;

		/**
		 * Konstruktor
		 */
		public UmfrageLoeschenBestaetigenClickHandler(UmfrageLoeschenDialogBox umfrageLoeschenDB) {
			this.umfrageLoeschenDB = umfrageLoeschenDB;
		}

		@Override
		public void onClick(ClickEvent event) {
			kinoplaner.loeschen(umfrage, new UmfrageLoeschenCallback());
			umfrageLoeschenDB.hide();

		}

	}

	// ClickHandler um Löschen der Umfrage abzubrechen.

	private class UmfrageLoeschenAbbrechenClickHandler implements ClickHandler {

		private UmfrageLoeschenDialogBox umfrageLoeschenDB;

		public UmfrageLoeschenAbbrechenClickHandler(UmfrageLoeschenDialogBox umfrageLoeschenDB) {
			this.umfrageLoeschenDB = umfrageLoeschenDB;
		}

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			umfrageLoeschenDB.hide();

		}

	}

	/*
	 * Konstruktor
	 */
	public UmfrageAnzeigenForm(Umfrage umfrage) {
		this.umfrage = umfrage;

	}

	/***********************************************
	 * CLICKHANDLER 
	 * 
	 *
	 **********************************************/

	/**
	 * Wenn der Nutzer, die angezeigte Umfrage löschen möchte, kann er dies über den
	 * Löschen-Button tun. Automatisch öffnet sich die DialogBox machen. Diese
	 * Dialogbox bittet den Nutzer darum, erneut zu bestätigen, dass er die Umfrage
	 * löschen möchte.
	 * 
	 * @author 
	 *
	 */

	public class UmfrageLoeschenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			UmfrageLoeschenDialogBox umfrageLoeschenDB = new UmfrageLoeschenDialogBox();
			umfrageLoeschenDB.center();

		}

	}

	/**
	 * Angezeigte Umfragen können gespeichert werden, solange sie noch offen sind.
	 * Ist eine Umfrage abgeschlossen/geschlossen, gibt der ClickHandler eine
	 * Meldung zurück. Denn die geschlossene Umfrage wird nicht mehr in der
	 * UmfrageAnzeigenForm angezeigt, sondern in der ErgebnisAnzeigenForm.
	 * 
	 *
	 */
	private class SpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			kinoplaner.getUmfrageById(umfrage.getId(), new AsyncCallback<Umfrage>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onSuccess(Umfrage result) {
					if (result.isOpen() == true) {
						uat.speichern();
					} else {
						Window.alert("Speichern nicht mehr möglich, die Umfrage wurde inzwischen abgeschlossen.");
						RootPanel.get("details").clear();
						ErgebnisAnzeigenForm anzeigen = new ErgebnisAnzeigenForm(result);
						RootPanel.get("details").add(anzeigen);
					}

				}
			});

		}

	}

	/***
	 * Möchte der Nutzer sich die Votings in der Umfrage anzeigen lassen, so kann er
	 * dies mit einem Klick auf dem Button machen. Der Nutzer gelangt zur
	 * VotingAnzeigenForm der Votings
	 * 
	 * @author fiona
	 *
	 */
	private class VotingsAnzeigenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			VotingsAnzeigenForm anzeigen = new VotingsAnzeigenForm(umfrage);
			RootPanel.get("details").add(anzeigen);
		}

	}

	/***
	 * Wenn der Nutzer die Umfrage bearbeiten möchte, gelangt er mit einem Klick auf
	 * den Bearbeiten-Button in die UmfrageErstellenForm, in welcher die Umfrage
	 * bearbeitet werden kann.
	 * 
	 * @author fiona
	 *
	 */

	private class UmfrageBearbeitenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			UmfrageErstellenForm bearbeitenForm = new UmfrageErstellenForm();
			bearbeitenForm.setUmfrage(umfrage);
			RootPanel.get("details").add(bearbeitenForm);

		}

	}

	/***************************************************
	 * CALLBACKS **********************************************
	 * 
	 *
	 */

	private class UmfrageLoeschenCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {

		}

		/**
		 * Bei erfolgreichem UmfrageLöschenCallback wird die Umfrage aus der
		 * UmfrageAnzeigenForm gelöscht. Der Nutzer wird automatisch auf die
		 * UmfrageAnzeigenForm weitergeleitet.
		 * 
		 */

		@Override
		public void onSuccess(Void result) {
			RootPanel.get("details").clear();
			UmfragenAnzeigenForm anzeigen = new UmfragenAnzeigenForm();
			RootPanel.get("details").add(anzeigen);

		}

	}

}
