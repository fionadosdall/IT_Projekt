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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.client.EditorEntry.AktuellerAnwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;

/**
 * Klasse dient zur Darstellung des User-Profils eines eingeloggten Users.
 * Übersicht über Name und E-Mail, Funktionen um Name zu ändern, speichern, User
 * löschen und User abmelden.
 * 
 * @author
 *
 */
public class UserForm extends FlowPanel {

	KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

	Anwender anwender = null;

	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();
	private FlowPanel detailsboxlöschen = new FlowPanel();
	private FlowPanel löschenImage = new FlowPanel();
	private FlowPanel detailsbox = new FlowPanel();
	private FlowPanel detailsboxInhalt = new FlowPanel();
	private FlowPanel detailsboxspeichern = new FlowPanel();
	private FlowPanel detailsboxLabels = new FlowPanel();
	private FlowPanel detailsboxAbmelden = new FlowPanel();

	private Label title = new Label("Dein Profil");
	private Label nameLabel = new Label("Name");
	private Label mailLabel = new Label("Mail");
	private Label emailAnzeigenLabel = new Label();
	private Label helbTextName = new Label("Name ändern");
	private Label helpTextMail = new Label("Die Mailadresse kann nicht geändert werden");

	private Button speichernButton = new Button("Speichern");
	private Button abmeldenButton = new Button("Abmelden");

	private TextBox nameTextBox = new TextBox();

	private Image papierkorb = new Image();
	
	private String alterName;

	String logoutUrl;

	/**
	 * Konstruktor
	 * 
	 * @param anwender
	 */
	public UserForm(Anwender anwender) {
		this.anwender = anwender;
	}

	public void onLoad() {

		// Vergeben der Stylenames

		this.addStyleName("detailscontainer");
		// this.addStyleName("center");

		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");
		detailsboxlöschen.addStyleName("detailsboxlöschen");
		detailsboxspeichern.addStyleName("detailsboxspeichern");
		löschenImage.addStyleName("löschenImage");
		papierkorb.addStyleName("papierkorb");
		detailsbox.addStyleName("detailsbox");
		detailsboxInhalt.addStyleName("detailsboxInhalt");
		detailsboxLabels.addStyleName("detailsboxLabels");
		detailsboxAbmelden.addStyleName("detailsboxabmelden");

		title.addStyleName("title");
		nameLabel.addStyleName("detailsboxLabels");
		mailLabel.addStyleName("detailsboxLabels");
		emailAnzeigenLabel.addStyleName("mailLabel");
		helbTextName.addStyleName("helpTextLabel");
		helpTextMail.addStyleName("helpTextLabel");

		nameTextBox.addStyleName("nameTextBox");

		speichernButton.addStyleName("speichernButton");
		abmeldenButton.addStyleName("navButton");

		papierkorb.setUrl("/images/papierkorb.png");

		// Zusammenbauen der Widgets
		this.add(detailsoben);
		this.add(detailsunten);

		detailsoben.add(title);

		detailsunten.add(detailsboxlöschen);
		detailsunten.add(detailsbox);

		detailsboxlöschen.add(löschenImage);

		löschenImage.add(papierkorb);

		detailsbox.add(detailsboxInhalt);
		detailsbox.add(detailsboxInhalt);

		detailsboxInhalt.add(nameLabel);
		detailsboxInhalt.add(nameTextBox);
		detailsboxInhalt.add(helbTextName);
		detailsboxInhalt.add(detailsboxspeichern);

		detailsboxspeichern.add(speichernButton);

		detailsboxInhalt.add(mailLabel);
		detailsboxInhalt.add(emailAnzeigenLabel);
		detailsboxInhalt.add(helpTextMail);
		detailsboxInhalt.add(detailsboxAbmelden);

		detailsboxAbmelden.add(abmeldenButton);

		/**
		 * Click-Handler zum Speicher-, Abmelden- und Papierkorb-Button hinzufügen
		 */
		speichernButton.addClickHandler(new AnwenderSpeichernClickHandler());
		abmeldenButton.addClickHandler(new AbmeldenClickHandler());

		papierkorb.addClickHandler(new BenutzerLoeschenClickHandler());

		if (anwender == null) {
			emailAnzeigenLabel.setText("Es ist noch keine Mailadresse vorhanden");
		} else {

			emailAnzeigenLabel.setText(anwender.getGmail());

		}

		if (anwender == null) {
			nameTextBox.getElement().setPropertyString("placeholder", "Es ist noch kein Name vorhanden");
		} else {

			nameTextBox.getElement().setPropertyString("placeholder", anwender.getName());
		}

		// Window.alert(""+aktuellerAnwender.getAnwender().getId());
	}

	/******************************
	 * Löschen-Dialogbox
	 * 
	 *
	 ******************************/

	/**
	 * Clickhandler, um den Benutzer zu löschen. Durch Klick auf den Löschen-Button
	 * wird eine Dialogbox zur Bestätigung des Löschvorgangs aufgerufen.
	 * 
	 *
	 */
	private class BenutzerLoeschenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			LoeschenUserBox loeschenUB = new LoeschenUserBox();
			loeschenUB.center();

		}

	}

	/**
	 * Vor dem Löschen seines Users wird der Nutzer über eine Dialogbox nochmal um
	 * Bestätigung des Löschvorgangs gebeten.
	 * 
	 *
	 */
	private class LoeschenUserBox extends DialogBox {

		private VerticalPanel verticalPanel = new VerticalPanel();
		private HorizontalPanel buttonPanel = new HorizontalPanel();

		private Label abfrage = new Label("Bist Du Dir sicher Dein Profil zu entfernen?");

		private Button jaButton = new Button("Ja");
		private Button neinButton = new Button("Nein");

		// Konstruktor der Löschen-Dialogbox

		public LoeschenUserBox() {

			abfrage.addStyleName("Abfrage");
			jaButton.addStyleName("buttonAbfrage");
			neinButton.addStyleName("buttonAbfrage");

			buttonPanel.add(jaButton);
			buttonPanel.add(neinButton);
			verticalPanel.add(abfrage);
			verticalPanel.add(buttonPanel);

			this.add(verticalPanel);

			// ClickHandler zu den Ja- und Nein-Buttons hinzufügen, Buttons befinden sich in
			// der LoeschenUserBox
			jaButton.addClickHandler(new LoeschenClickHandler(this));
			neinButton.addClickHandler(new AbbrechenClickHandler(this));
		}

	}

	/***********************************************************************
	 * CLICKHANDLER
	 ***********************************************************************/

	/**
	 * ClickHandler für Klick auf den Löschen-Button: Neue LöschenUserBox zur
	 * Bestätigung des Löschvorgangs wird erstellt. Nach Bestätigung wird der
	 * User/Anwender durch LogoutUrl gelöscht, neues LoeschenAnwenderCallback
	 * 
	 *
	 */
	private class LoeschenClickHandler implements ClickHandler {

		private LoeschenUserBox loeschenUserBox;

		public LoeschenClickHandler(LoeschenUserBox loeschenUserBox) {
			this.loeschenUserBox = loeschenUserBox;
		}

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			this.loeschenUserBox.hide();
			anwender.setLogoutUrl(anwender.getLogoutUrl());
			Window.open(anwender.getLogoutUrl(), "_self", "");

			kinoplaner.loeschen(anwender, new LoeschenAnwenderCallback());
		}

	}

	/**
	 * Wenn der Nutzer auf den Löschen-Button klickt, wird er in einer Dialogbox
	 * nochmal gefragt, ob er das Objekt wirklich löschen will. Hier besteht die
	 * Möglichkeit, den Löschvorgang durch Klick auf den Nein-Button abzubrechen,
	 * dann greift diese AbbrechenClickHandler-Klasse.
	 * 
	 *
	 */
	private class AbbrechenClickHandler implements ClickHandler {

		private LoeschenUserBox loeschenUserBox;

		public AbbrechenClickHandler(LoeschenUserBox loeschenUserBox) {
			this.loeschenUserBox = loeschenUserBox;
		}

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			this.loeschenUserBox.hide();
		}

	}

	/**
	 * ClickHandler um Änderungen am User zu speichern, z.B. wenn der Name des Users
	 * geändert wurde. Der AnwenderSpeichernClickHandler initialisiert ein neues
	 * SpeichernUserBox-Objekt. In dieser Dialogbox wird der User gefragt, ob er den
	 * neuen Namen speichern will.
	 * 
	 *
	 */
	private class AnwenderSpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub

			if (nameTextBox.getValue() == "") {
				Window.alert("Es wurde kein neuer Name eingegeben");

			} else {
				SpeichernUserBox speichernUB = new SpeichernUserBox();
				speichernUB.center();

			}

		}

	}

	/**
	 * SpeichernUserBox: Dialogbox, die den User vor dem Speichern einer Änderung
	 * fragt, ob er seine Änderung wirklich speichern will.
	 *
	 */
	private class SpeichernUserBox extends DialogBox {

		private VerticalPanel verticalPanel = new VerticalPanel();
		private HorizontalPanel buttonPanel = new HorizontalPanel();

		private Label abfrage = new Label("Willst Du den geänderten Namen speichern?");

		private Button yesButton = new Button("Ja");
		private Button noButton = new Button("Nein");

		/**
		 * Konstruktor der SpeichernUserBox
		 */

		public SpeichernUserBox() {

			abfrage.addStyleName("Abfrage");
			yesButton.addStyleName("buttonAbfrage");
			noButton.addStyleName("buttonAbfrage");

			buttonPanel.add(yesButton);
			buttonPanel.add(noButton);
			verticalPanel.add(abfrage);
			verticalPanel.add(buttonPanel);

			this.add(verticalPanel);

			// ClickHandler für die Ja- und Nein-Buttons der DailogBox
			yesButton.addClickHandler(new SpeichernClickHanlder(this));
			noButton.addClickHandler(new SpeichernAbbrechenClickHandler(this));

		}
	}

	/**
	 * SpeichernClickHandler ruft die Dialogbox SpeichernUserBox auf. Dadurch wird
	 * der User zur Bestätigung des Speichervorgangs gebeten. Nach Bestätigung wird
	 * der neue Name des Users gespeichert, neues UpdateAnwenderCallback
	 * 
	 *
	 */
	private class SpeichernClickHanlder implements ClickHandler {

		private SpeichernUserBox speichernUserBox;

		public SpeichernClickHanlder(SpeichernUserBox speichernUserBox) {
			this.speichernUserBox = speichernUserBox;

		}

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			String neuerName = nameTextBox.getValue();

			// Prüfen ob TextBox leer ist
			if (neuerName.isEmpty()) {
				Window.alert("Der Name ist leer");
			} else {
				alterName = anwender.getName();
				anwender.setName(neuerName);
			}

			kinoplaner.speichern(anwender, new UpdateAnwenderCallback());

			this.speichernUserBox.hide();

		}

	}

	/**
	 * Der User kann seinen Namen ändern, anschließend muss er die Änderung
	 * speichern. Zuvor wird er durch eine Dialogbox um Bestätigung des
	 * Speichervorgangs gebeten. Dieser Speichervorgang kann mit Klick auf den
	 * Nein-Button in der Dialogbox abgebrochen werde. Dann greift diese
	 * SpeichernAbbrechenClickHandler-Klasse.
	 * 
	 *
	 */
	private class SpeichernAbbrechenClickHandler implements ClickHandler {

		private SpeichernUserBox speichernUserBox;

		public SpeichernAbbrechenClickHandler(SpeichernUserBox speichernUserBox) {
			this.speichernUserBox = speichernUserBox;

		}

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			this.speichernUserBox.hide();

		}

	}

	/**
	 * Über den AbmeldenClickHandler kann sich der User temporär aus der Anwendung
	 * ausloggen, über einen LogoutUrl. (Nicht zu verwechseln mit User löschen, dort
	 * wird der User dauerhaft aus der Kinoplaner-DB gelöscht.)
	 * 
	 * 
	 *
	 */
	private class AbmeldenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			anwender.setLogoutUrl(anwender.getLogoutUrl());
			Window.open(anwender.getLogoutUrl(), "_self", "");

		}

	}

	/***********************************************************************
	 * CALLBACKS
	 ***********************************************************************/

	
	private class LoeschenAnwenderCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Dein Profil konnte nicht gelöscht werden");

		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			Window.alert("Dein Profil wurde erfolgreich gelöscht");
			Window.Location.assign(logoutUrl);

		}

	}

	private class UpdateAnwenderCallback implements AsyncCallback<Anwender> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("UpdateAnwenderCallback hat nicht funktioniert");

		}

		@Override
		public void onSuccess(Anwender result) {
			if (result == null) {
				Window.alert("Nickname bereits vergeben!");
				anwender.setName(alterName);
			} else {
				AktuellerAnwender.setAnwender(anwender);
				de.hdm.softwareProjekt.kinoPlaner.client.AdminEntry.AktuellerAnwender.setAnwender(anwender);
			}

		}

	}

}
