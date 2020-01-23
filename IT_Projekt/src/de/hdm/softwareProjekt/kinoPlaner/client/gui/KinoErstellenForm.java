package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;


/*
 * Klasse stellt das Formular um ein Kino zu erstellen bereit
 */
public class KinoErstellenForm extends VerticalPanel {

	private HorizontalPanel obenPanel = new HorizontalPanel();
	private HorizontalPanel untenPanel = new HorizontalPanel();

	private KinoplanerAsync administration = ClientsideSettings.getKinoplaner();

	private Label kinoFormLabel = new Label("Neues Kino");
	private Label kinokBearbeitenFormLabel = new Label("Kino bearbeiten");
	private Label nameLabel = new Label("Kinoname:");
	private Label kinokettenLabel = new Label("Kinokette:");
	private Label plzLabel = new Label("PLZ:");
	private Label stadtLabel = new Label("Stadt:");
	private Label strasseLabel = new Label("Straße:");
	private Label hnrLabel = new Label("Hausnummer:");

	private TextBox nameTextBox = new TextBox();
	private TextBox plzTextBox = new TextBox();
	private TextBox stadtTextBox = new TextBox();
	private TextBox strasseTextBox = new TextBox();
	private TextBox hnrTextBox = new TextBox();
	private ListBox kinokettenListBox = new ListBox();

	private Button speichernButton = new Button("Speichern");
	private Button aenderungSpeichernButton = new Button("Änderung speichern");
	private Button loeschenButton = new Button("Löschen");
	private Grid kinoGrid = new Grid(6, 2);

	private Boolean edit = false;
	private MeineKinosForm mkf;

	private Kino kino;
	private Kinokette kk;

	/**
	 * Bei der Instanziierung wird der ClickHandler dem Button und dem Panel
	 * hinzugefügt
	 */

	public KinoErstellenForm() {

	}

	public KinoErstellenForm(Kino k) {
		this.kino = k;

		setEdit(true);
	}

	public void onLoad() {

		/* Vergeben der Style-Namen */

		kinoFormLabel.addStyleName("formHeaderLabel");
		kinokBearbeitenFormLabel.addStyleName("formHeaderLabel");
		nameLabel.addStyleName("textLabel");
		kinokettenLabel.addStyleName("textLabel");
		strasseLabel.addStyleName("textLabel");
		hnrLabel.addStyleName("textLabel");
		plzLabel.addStyleName("textLabel");
		stadtLabel.addStyleName("textLabel");
		speichernButton.addStyleName("speichernButton");
		aenderungSpeichernButton.addStyleName("speichernButton");
		loeschenButton.addStyleName("loeschenButton");
		obenPanel.addStyleName("obenPanel");
		untenPanel.addStyleName("untenPanel");
		nameTextBox.addStyleName("formularTextBox");
		strasseTextBox.addStyleName("formularTextBox");
		hnrTextBox.addStyleName("formularTextBox");
		plzTextBox.addStyleName("formularTextBox");
		stadtTextBox.addStyleName("formularTextBox");

		kinokettenListBox.setSize("180px", "25px");

		this.addStyleName("center");
		this.addStyleName("detailscontainer");

		/* Zusammensetzen der Widgets */

		if (edit == true) {
			obenPanel.add(kinokBearbeitenFormLabel);
		} else {
			obenPanel.add(kinoFormLabel);

		}

		this.add(obenPanel);

		kinoGrid.setWidget(0, 0, nameLabel);
		kinoGrid.setWidget(0, 1, nameTextBox);
		kinoGrid.setWidget(1, 0, kinokettenLabel);
		kinoGrid.setWidget(1, 1, kinokettenListBox);
		kinoGrid.setWidget(2, 0, strasseLabel);
		kinoGrid.setWidget(2, 1, strasseTextBox);
		kinoGrid.setWidget(3, 0, hnrLabel);
		kinoGrid.setWidget(3, 1, hnrTextBox);
		kinoGrid.setWidget(4, 0, plzLabel);
		kinoGrid.setWidget(4, 1, plzTextBox);
		kinoGrid.setWidget(5, 0, stadtLabel);
		kinoGrid.setWidget(5, 1, stadtTextBox);

		this.add(kinoGrid);

		administration.getKinokettenByAnwenderOwner(new KinokettenCallback());

		if (edit == true) {
			untenPanel.add(loeschenButton);
			untenPanel.add(aenderungSpeichernButton);
		} else {

			untenPanel.add(speichernButton);
		}

		this.add(untenPanel);

		speichernButton.addClickHandler(new SpeichernClickHandler());
		loeschenButton.addClickHandler(new KinoLoeschenClickHandler());
		aenderungSpeichernButton.addClickHandler(new AenderungSpeichernClickHandler());
		setBearbeiten(kino);

	}

	/*
	 * Vor dem Löschen eines Kinos soll der Nutzer über eine Dialogbox noch
	 * einmal um Bestätigung des Löschvorgangs gebeten werden
	 */

	private class KinoLoeschenDialogBox extends DialogBox {

		private VerticalPanel verticalPanel = new VerticalPanel();
		private HorizontalPanel buttonPanel = new HorizontalPanel();

		private Label abfrage = new Label("Kino entgültig löschen?");

		private Button jaButton = new Button("Ja");
		private Button neinButton = new Button("Nein");

		// Konstruktor

		public KinoLoeschenDialogBox() {

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

	/**********************************************
	 * ClickHandler ***************************************************
	 */

	/*
	 * ClickHandler um erstellte Kino-Instanz zu speichern
	 */

	private class SpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			String kinoketteName = kinokettenListBox.getSelectedValue();

			if (kinoketteName != "Keine Auswahl") {
				administration.getKinoketteByName(kinoketteName, new KinoketteByNameCallback());
			} else {
				administration.erstellenKino(nameTextBox.getText(), Integer.parseInt(plzTextBox.getText()),
						stadtTextBox.getText(), strasseTextBox.getText(), hnrTextBox.getText(), 0,
						new KinoErstellenCallback());
			}

		}

	}

	/*
	 * ClickHandler um die Änderungen einer Kino-Instanz zu speichern
	 */

	private class AenderungSpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			String kinoketteName = kinokettenListBox.getSelectedValue();
			if (kinoketteName != "Keine Auswahl") {
				administration.getKinoketteByName(kinoketteName, new AenderungKinoketteByNameCallback());
			} else {
				kino.setName(nameTextBox.getText());
				kino.setKinokettenId(0);
				kino.setStrasse(stadtTextBox.getText());
				kino.setHausnummer(hnrTextBox.getText());
				kino.setPlz(Integer.parseInt(plzTextBox.getText()));
				kino.setStadt(stadtTextBox.getText());
				administration.speichern(kino, new KinoAendernCallback());
			}
		}

	}

	/***
	 * Wenn der Nutzer das angezeigte Kino löschen möchte, kann er dies über den
	 * Löschen-Button tun. Dabei öffnet sich automatisch die DialogBox. Diese bitten
	 * den Nutzer, erneut zu bestätigen, dass er das Kino löschen möchte
	 * 
	 *
	 */

	private class KinoLoeschenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub

			KinoLoeschenDialogBox kinoLoeschenDB = new KinoLoeschenDialogBox();
			kinoLoeschenDB.center();
		}

	}

	/*
	 * ClickHandler zur Lösch-Bestätigung des Kinos
	 */

	private class LoeschenClickHandler implements ClickHandler {

		private KinoLoeschenDialogBox kinoloeschenDB;

		public LoeschenClickHandler(KinoLoeschenDialogBox kinoloeschenDB) {
			this.kinoloeschenDB = kinoloeschenDB;
		}

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			kinoloeschenDB.hide();
			administration.loeschen(kino, new KinoLoeschenCallback());
			RootPanel.get("details").clear();
			mkf = new MeineKinosForm();
			RootPanel.get("details").add(mkf);
		}

	}

	/*
	 * ClickHandler um das Löschen des Kinos abzubrechen
	 */
	private class AbbrechenClickHandler implements ClickHandler {

		private KinoLoeschenDialogBox kinoloeschenDB;

		public AbbrechenClickHandler(KinoLoeschenDialogBox kinoloeschenDB) {
			this.kinoloeschenDB = kinoloeschenDB;

		}

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			kinoloeschenDB.hide();
		}

	}

	/*
	 * *******************************************************
	 * 
	 * Callback *************************************************
	 */

	/*
	 * private Klasse um eeine Kino-Instanz im System zu erstellen
	 */

	private class KinoErstellenCallback implements AsyncCallback<Kino> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert(caught.getMessage());
			caught.printStackTrace();
			Systemmeldung.anzeigen("Ein neues Kino konnte leider nicht erstellt werden");
		}

		@Override
		public void onSuccess(Kino result) {
			if (result == null) {
				Window.alert("Kinoname bereits verwendet!");
			} else {
				Systemmeldung.anzeigen("Kino wurde angelegt");
				RootPanel.get("details").clear();
				mkf = new MeineKinosForm();
				RootPanel.get("details").add(mkf);
			}

		}

	}

	/**
	 * private Klasse um eine Kino-Instanz zu bearbeiten
	 * 
	 * @author fiona
	 *
	 */

	private class KinoAendernCallback implements AsyncCallback<Kino> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert(caught.getMessage());
			caught.printStackTrace();
			Systemmeldung.anzeigen("Änderungen konnten nicht gespeichert werden.");
		}

		@Override
		public void onSuccess(Kino result) {
			if (result != null) {
				Systemmeldung.anzeigen("Änderungen gespeichert.");
				RootPanel.get("details").clear();
				mkf = new MeineKinosForm();
				RootPanel.get("details").add(mkf);
			} else {
				Window.alert("Kinoname bereits verwendet!");
			}
		}

	}

	/*
	 * private Klasse um eine Kino-Instanz aus em System zu löschen
	 */

	private class KinoLoeschenCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Kino konnte nicht gelöscht werden.");
			Window.alert(caught.getMessage());
			caught.printStackTrace();
		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Kino wurde gelöscht.");

		}

	}

	/*
	 * private Klasse um alle Kinoketten aus dem System zu bekommen
	 */
	private class KinokettenCallback implements AsyncCallback<ArrayList<Kinokette>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert(caught.getMessage());
			caught.printStackTrace();
		}

		@Override
		public void onSuccess(ArrayList<Kinokette> result) {

			int indexSelected = 0;
			int counter = 1;

			kinokettenListBox.addItem("Keine Auswahl");

			if (result.size() != 0) {

				for (Kinokette kk : result) {

					kinokettenListBox.addItem(kk.getName());

					if (kino != null) {
						if (kk.getId() == kino.getKinokettenId()) {
							indexSelected = counter;

						} else {
							counter++;

						}
					}

				}
				if (kino != null) {
					kinokettenListBox.setSelectedIndex(indexSelected);
				}

			} else {

				kinokettenListBox.addItem("Kein Kino verfügbar");
				kinokettenListBox.setEnabled(false);

			}

		}

	}

	/*
	 * private KLasse um alle Kinoketten-Instanzen aufgrund des Names der Kinokette,
	 * aus dem System zu bekommen
	 */

	private class KinoketteByNameCallback implements AsyncCallback<Kinokette> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert(caught.getMessage());
			caught.printStackTrace();
		}

		@Override
		public void onSuccess(Kinokette result) {
			// TODO Auto-generated method stub

			/* if(edit = false) { */
			administration.erstellenKino(nameTextBox.getText(), Integer.parseInt(plzTextBox.getText()),
					stadtTextBox.getText(), strasseTextBox.getText(), hnrTextBox.getText(), result.getId(),
					new KinoErstellenCallback());

		}

	}

	/*
	 * Callback Klasse zur Abfrage des ausgewählten Kinokettenonjekts mit dem Namen,
	 * um dann am Kino änderungen vorzunehmen und dabei die richtige Kinokettenid zu
	 * verwenden
	 */

	private class AenderungKinoketteByNameCallback implements AsyncCallback<Kinokette> {

		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();
		}

		public void onSuccess(Kinokette result) {
			if (result != null) {
				kino.setKinokettenId(result.getId());
			} else {
				kino.setKinokettenId(0);
			}
			kino.setName(nameTextBox.getText());

			kino.setStrasse(stadtTextBox.getText());
			kino.setHausnummer(hnrTextBox.getText());
			kino.setPlz(Integer.parseInt(plzTextBox.getText()));
			kino.setStadt(stadtTextBox.getText());
			administration.speichern(kino, new KinoAendernCallback());

		}

	}

	/** Methoden ***/

	public Boolean getEdit() {
		return edit;
	}

	public void setEdit(Boolean edit) {
		this.edit = edit;
	}

	public void setBearbeiten(Kino kino) {

		nameTextBox.setText(kino.getName());
		plzTextBox.setText(Integer.toString(kino.getPlz()));
		strasseTextBox.setText(kino.getStrasse());
		hnrTextBox.setText(kino.getHausnummer());
		stadtTextBox.setText(kino.getStadt());

	}

	public void clearForm() {
		nameTextBox.setText("");
		plzTextBox.setText("");
		strasseTextBox.setText("");
		hnrTextBox.setText("");
		stadtTextBox.setText("");

	}

}
