package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.Kinoplaner;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;

public class KinoketteErstellenForm extends VerticalPanel {

	private KinoplanerAsync administration = ClientsideSettings.getKinoplaner();

	private HorizontalPanel obenPanel = new HorizontalPanel();
	private HorizontalPanel untenPanel = new HorizontalPanel();

	private MeineKinokettenForm mkkf;

	private Label kinoketteFormLabel = new Label("Neue Kinokette");
	private Label kinoketteBearbeitenFormLabel = new Label("Kinokette bearbeiten");
	private Label nameLabel = new Label("Kinokettenname:");
	private Label sitzLabel = new Label("Sitz:");
	private Label websiteLabel = new Label("Website:");

	private TextBox nameTextBox = new TextBox();
	private TextBox sitzTextBox = new TextBox();
	private TextBox websiteTextBox = new TextBox();

	private Grid kinoketteGrid = new Grid(4, 2);
	private Button speichernButton = new Button("Speichern");
	private Button aenderungSpeichernButton = new Button("Änderung speichern");
	private Button loeschenButton = new Button("Löschen");

	private Boolean edit = false;
	private Kinokette kinoketteBearbeiten;
	private Kinokette kk;

	/**
	 * Bei der Instanziierung wird der ClickHandler dem Button hinzugefügt
	 */

	public KinoketteErstellenForm() {

	}

	public KinoketteErstellenForm(Kinokette kk) {
		this.kk = kk;

		setEdit(true);
	}

	public void onLoad() {

		/* Setzen der Style-Namen */
		this.addStyleName("center");
		this.addStyleName("detailscontainer");

		kinoketteFormLabel.addStyleName("formHeaderLabel");
		kinoketteBearbeitenFormLabel.addStyleName("formHeaderLabel");
		nameLabel.addStyleName("textLabel");
		sitzLabel.addStyleName("textLabel");
		;
		websiteLabel.addStyleName("textLabel");
		speichernButton.addStyleName("speichernButton");
		aenderungSpeichernButton.addStyleName("speichernButton");
		loeschenButton.addStyleName("loeschenButton");
		obenPanel.addStyleName("obenPanel");
		untenPanel.addStyleName("untenPanel");
		nameTextBox.addStyleName("formularTextBox");
		sitzTextBox.addStyleName("formularTextBox");
		websiteTextBox.addStyleName("formularTextBox");

		/* Zusammensetzen der Widgets */

		if (edit == true) {

			obenPanel.add(kinoketteBearbeitenFormLabel);
		} else {
			obenPanel.add(kinoketteFormLabel);

		}

		this.add(obenPanel);

		kinoketteGrid.setWidget(0, 0, nameLabel);
		kinoketteGrid.setWidget(0, 1, nameTextBox);
		kinoketteGrid.setWidget(1, 0, sitzLabel);
		kinoketteGrid.setWidget(1, 1, sitzTextBox);
		kinoketteGrid.setWidget(2, 0, websiteLabel);
		kinoketteGrid.setWidget(2, 1, websiteTextBox);

		this.add(kinoketteGrid);

		if (edit == true) {
			untenPanel.add(loeschenButton);
			untenPanel.add(aenderungSpeichernButton);
		} else {
			clearForm();
			untenPanel.add(speichernButton);
		}

		this.add(untenPanel);

		speichernButton.addClickHandler(new SpeichernClickHandler());
		loeschenButton.addClickHandler(new KinoketteLoeschenClickHandler());
		aenderungSpeichernButton.addClickHandler(new AenderungSpeichernClickHandler());
		setBearbeiten(kk);
	}

	private class KinoketteLoeschenDialogBox extends DialogBox {

		private VerticalPanel verticalPanel = new VerticalPanel();
		private HorizontalPanel buttonPanel = new HorizontalPanel();

		private Label abfrage = new Label("Kinokette entgültig löschen?");

		private Button jaButton = new Button("Ja");
		private Button neinButton = new Button("Nein");

		// Konstruktor

		public KinoketteLoeschenDialogBox() {

			abfrage.addStyleName("Abfrage");
			jaButton.addStyleName("buttonAbfrage");
			neinButton.addStyleName("buttonAbfrage");

			buttonPanel.add(jaButton);
			buttonPanel.add(neinButton);
			verticalPanel.add(abfrage);
			verticalPanel.add(buttonPanel);

			this.add(verticalPanel);

			// ClickHandler für die DailogBox
			jaButton.addClickHandler(new LoeschenClickHandler(this));
			neinButton.addClickHandler(new AbbrechenClickHandler(this));
		}
	}

	/*** ClickHandler ***/

	public class SpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			administration.erstellenKinokette(nameTextBox.getText(), sitzTextBox.getText(), websiteTextBox.getText(),
					new KinoketteErstellenCallback());

		}

	}

	private class KinoketteLoeschenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			KinoketteLoeschenDialogBox kinokinoketteloeschenDB = new KinoketteLoeschenDialogBox();
			kinokinoketteloeschenDB.center();
		}

	}

	private class LoeschenClickHandler implements ClickHandler {

		private KinoketteLoeschenDialogBox kinoketteloeschenDB;

		public LoeschenClickHandler(KinoketteLoeschenDialogBox kinoketteloeschenDB) {
			this.kinoketteloeschenDB = kinoketteloeschenDB;
		}

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			kinoketteloeschenDB.hide();
			administration.loeschenKinoketteById(kk.getId(), new KinoketteLoeschenCallback());
			RootPanel.get("details").clear();
			mkkf = new MeineKinokettenForm();
			RootPanel.get("details").add(mkkf);
		}

	}

	private class AbbrechenClickHandler implements ClickHandler {

		private KinoketteLoeschenDialogBox kinoketteloeschenDB;

		public AbbrechenClickHandler(KinoketteLoeschenDialogBox kinoketteloeschenDB) {
			this.kinoketteloeschenDB = kinoketteloeschenDB;

		}

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			kinoketteloeschenDB.hide();
		}

	}

	private class AenderungSpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			kk.setName(nameTextBox.getText());
			kk.setSitz(sitzTextBox.getText());
			kk.setWebsite(websiteTextBox.getText());
			administration.speichern(kk, new AenderungSpeichernCallback());
			RootPanel.get("details").clear();
			mkkf = new MeineKinokettenForm();
			RootPanel.get("details").add(mkkf);
		}

	}

	/* Callback */

	private class KinoketteErstellenCallback implements AsyncCallback<Kinokette> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Eine neue Kinokette konnte leider nicht erstellt werden.");
		}

		@Override
		public void onSuccess(Kinokette result) {
			if (result == null) {
				Window.alert("Kinokettenname bereits verwendet!");
			} else {
				Systemmeldung.anzeigen("Kinokette wurde angelegt");
				RootPanel.get("details").clear();
				mkkf = new MeineKinokettenForm();
				RootPanel.get("details").add(mkkf);
			}

		}

	}

	private class AenderungSpeichernCallback implements AsyncCallback<Kinokette> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Änderungen konnten nicht gespeichert werden.");
		}

		@Override
		public void onSuccess(Kinokette result) {
			if (result == null) {
				Window.alert("Kinokettenname bereits verwendet!");
			} else {
				Systemmeldung.anzeigen("Änderung gespeichert");
				RootPanel.get("details").clear();
				mkkf = new MeineKinokettenForm();
				RootPanel.get("details").add(mkkf);
			}
		}

	}

	private class KinoketteLoeschenCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("<Kinokette konnte nicht gelöscht werden.");

		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Kinokette wurde gelöscht.");
			RootPanel.get("details").clear();
			mkkf = new MeineKinokettenForm();
			RootPanel.get("details").add(mkkf);
		}
	}

	/** Methoden ***/

	public Boolean getEdit() {
		return edit;
	}

	public void setEdit(Boolean edit) {
		this.edit = edit;
	}

	public void setBearbeiten(Kinokette kinokette) {

		nameTextBox.setText(kinokette.getName());
		sitzTextBox.setText(kinokette.getSitz());
		websiteTextBox.setText(kinokette.getWebsite());

	}

	public void clearForm() {
		nameTextBox.setText("");
		sitzTextBox.setText("");
		websiteTextBox.setText("");
	}
}
