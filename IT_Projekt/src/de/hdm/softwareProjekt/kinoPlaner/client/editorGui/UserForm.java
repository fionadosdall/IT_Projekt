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
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;

public class UserForm extends FlowPanel {

	KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

	Anwender anwender;

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

	String logoutUrl;

	public void onLoad() {
		

		// Vergeben der Stylenames

		this.addStyleName("detailscontainer");

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


		// Click-Handler
		papierkorb.addClickHandler(new BenutzerLoeschenClickHandler());
		speichernButton.addClickHandler(new AnwenderSpeichernClickHandler());
		abmeldenButton.addClickHandler(new AbmeldenClickHandler());
		
		if (anwender == null) {
			emailAnzeigenLabel.setText("Es ist noch keine Mailadresse vorhanden");
		} else {
			
			emailAnzeigenLabel.setText(anwender.getGmail());
			
		}
		
		if (anwender == null) { 
			nameTextBox.setText("Es ist noch kein Profil vorhanden");
		} else {
			
			nameTextBox.getElement().setPropertyString("placeholder", anwender.getName());
		}

	}

	private class BenutzerLoeschenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			LoeschenUserBox loeschenUB = new LoeschenUserBox();
			loeschenUB.center();

		}

	}

	private class LoeschenUserBox extends DialogBox {

		private VerticalPanel verticalPanel = new VerticalPanel();
		private HorizontalPanel buttonPanel = new HorizontalPanel();

		private Label abfrage = new Label("Bist Du Dir sicher Dein Profil zu entfernen?");

		private Button jaButton = new Button("Ja");
		private Button neinButton = new Button("Nein");

		// Konstruktor

		public LoeschenUserBox() {

			abfrage.addStyleName("Abfrage");
			jaButton.addStyleName("buttonAbfrage");
			neinButton.addStyleName("buttonAbfrage");

			buttonPanel.add(jaButton);
			buttonPanel.add(neinButton);
			verticalPanel.add(abfrage);
			verticalPanel.add(buttonPanel);

			this.add(verticalPanel);

			// ClickHandler für die DailogBox
			jaButton.addClickHandler(new LoeschenClickHanlder(this));
			neinButton.addClickHandler(new AbbrechenClickHandler(this));
		}

	}

	/***********************************************************************
	 * CLICKHANDLER
	 ***********************************************************************/
	private class LoeschenClickHanlder implements ClickHandler {

		private LoeschenUserBox loeschenUserBox;

		public LoeschenClickHanlder(LoeschenUserBox loeschenUserBox) {
			this.loeschenUserBox = loeschenUserBox;
		}

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			this.loeschenUserBox.hide();
			anwender.setLogoutUrl(anwender.getLogoutUrl());
			Window.open(anwender.getLogoutUrl(), "_self", "");

			//TODO kinoplaner.loeschen(anwender, new LoeschenAnwenderCallback());
		}

	}

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

	private class SpeichernUserBox extends DialogBox {

		private VerticalPanel verticalPanel = new VerticalPanel();
		private HorizontalPanel buttonPanel = new HorizontalPanel();

		private Label abfrage = new Label("Willst Du den geänderten Namen speichern?");

		private Button yesButton = new Button("Ja");
		private Button noButton = new Button("Nein");

		// Konstruktor

		public SpeichernUserBox() {

			abfrage.addStyleName("Abfrage");
			yesButton.addStyleName("buttonAbfrage");
			noButton.addStyleName("buttonAbfrage");

			buttonPanel.add(yesButton);
			buttonPanel.add(noButton);
			verticalPanel.add(abfrage);
			verticalPanel.add(buttonPanel);

			this.add(verticalPanel);

			// ClickHandler für die DailogBox
			yesButton.addClickHandler(new SpeichernClickHanlder(this));
			noButton.addClickHandler(new SpeichernAbbrechenClickHandler(this));

		}
	}

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
				anwender.setName(neuerName);
			}

			kinoplaner.speichern(anwender, new UpdateAnwenderCallback());

			this.speichernUserBox.hide();

		}

	}

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
			Window.alert("Dein Profil könnte  nicht gelöscht werden");

		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			Window.alert("Dein Profil wurde erfolgreich gelöscht");
			Window.Location.assign(logoutUrl);

		}

	}

	private class UpdateAnwenderCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("UpadteAnwenderCallback hat nicht funktioniert");

		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			Window.alert("UpadteAnwenderCallback war erfolgreich");
		}

	}

}
