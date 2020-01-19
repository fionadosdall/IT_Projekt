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

public class KinoErstellenForm extends VerticalPanel {
	
	private HorizontalPanel obenPanel = new HorizontalPanel();
	private HorizontalPanel untenPanel = new HorizontalPanel();
	
	private KinoplanerAsync administration = ClientsideSettings.getKinoplaner();
	
	private Label kinoFormLabel = new Label("Neues Kino");
	private Label kinokBearbeitenFormLabel = new Label("Kino bearbeiten");
	private Label nameLabel = new Label("Kinoname:");
	private Label kinokettenLabel= new Label("Kinokette:");
	private Label plzLabel = new Label("PLZ:");
	private Label stadtLabel = new Label("Stadt:");
	private Label strasseLabel = new Label("Straße:");
	private Label hnrLabel = new Label("Hausnummer:");
	
	private static TextBox nameTextBox = new TextBox();
	private static TextBox plzTextBox = new TextBox();
	private static TextBox stadtTextBox = new TextBox();
	private static TextBox strasseTextBox = new TextBox();
	private static TextBox hnrTextBox = new TextBox();
	private static ListBox kinokettenListBox = new ListBox();
	
	private Button speichernButton = new Button("Speichern");
	private Button loeschenButton = new Button("Löschen");
	private Grid kinoGrid = new Grid(6, 2);
	
	private static Boolean edit = false;
	private MeineKinosForm mkf;
	private ArrayList<Kinokette> kinoketten = new ArrayList<Kinokette>();
	private Kino k;
	private Kinokette kk;
	
	
	/**
	 * Bei der Instanziierung  wird der ClickHandler dem Button und dem Panel hinzugefügt
	 */	
	
	public KinoErstellenForm() {
		
		
		
		
	}
	
	public KinoErstellenForm(Kino k) {
		this.k = k;
		setBearbeiten(k);
		setEdit(true);
	}
	
	

	public void onLoad() {
		
		/*Vergeben der Style-Namen*/
		
		kinoFormLabel.addStyleName("formHeaderLabel");
		kinokBearbeitenFormLabel.addStyleName("formHeaderLabel");
		nameLabel.addStyleName("textLabel");
		kinokettenLabel.addStyleName("textLabel");
		strasseLabel.addStyleName("textLabel");
		hnrLabel.addStyleName("textLabel");
		plzLabel.addStyleName("textLabel");
		stadtLabel.addStyleName("textLabel");
		speichernButton.addStyleName("speichernButton");
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
		
		
		
		/*Zusammensetzen der Widgets */
		
		if(edit == true) {
			obenPanel.add(kinokBearbeitenFormLabel);
		} else {
			obenPanel.add(kinoFormLabel);
			clearForm();
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
		
		if(edit == true) {
			untenPanel.add(loeschenButton);
			untenPanel.add(speichernButton);
		} else {
			clearForm();
			untenPanel.add(speichernButton);
		}
		
		this.add(untenPanel);
		
		speichernButton.addClickHandler(new SpeichernClickHandler());
		untenPanel.add(speichernButton);
		loeschenButton.addClickHandler(new KinoLoeschenClickHandler());
		
	}
	
private class KinoLoeschenDialogBox extends DialogBox{
		
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
	
	
	/* ClickHandler */


	private class SpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			String kinoketteName = kinokettenListBox.getSelectedItemText();
			
			
			administration.getKinoketteByName(kinoketteName,new KinoketteByNameCallback());
		
			
			
			
		}		
		
	}
	
	private class KinoLoeschenClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
			KinoLoeschenDialogBox kinoLoeschenDB = new KinoLoeschenDialogBox();
			kinoLoeschenDB.center();
		}
		
	}
	
private class LoeschenClickHandler implements ClickHandler{

		
		private KinoLoeschenDialogBox kinoloeschenDB;
		
		public LoeschenClickHandler(KinoLoeschenDialogBox kinoloeschenDB) {
			this.kinoloeschenDB = kinoloeschenDB;
		}
		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			kinoloeschenDB.hide();
			administration.loeschen(k, new KinoLoeschenCallback());
			RootPanel.get("details").clear();
			mkf = new MeineKinosForm();
			RootPanel.get("details").add(mkf);
		}
		
	}
	
	private class AbbrechenClickHandler implements ClickHandler{

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

		
	/* Callback */
				
	private class KinoErstellenCallback implements AsyncCallback<Kino> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Ein neues Kino konnte leider nicht erstellt werden");
		}

		@Override
		public void onSuccess(Kino result) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Kino wurde angelegt");
		}
		
	}
	
	
	private class KinoLoeschenCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Kino konnte nicht gelöscht werden.");
			
		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Kino wurde gelöscht.");
			
		}
		
	}
	
	private class KinokettenCallback implements AsyncCallback<ArrayList<Kinokette>>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(ArrayList<Kinokette> result) {
			
			kinoketten = result;

			if (result != null) {

				for (Kinokette kk : result) {

					kinokettenListBox.addItem(kk.getName());

				}

			} else {

				kinokettenListBox.addItem("Keine Gruppen verfügbar");
				kinokettenListBox.setEnabled(false);

			}
			
		}
		
	}
	
	private class KinoketteByNameCallback implements AsyncCallback<Kinokette>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Kinokette result) {
			// TODO Auto-generated method stub
			
			administration.erstellenKino(nameTextBox.getText(), Integer.parseInt(plzTextBox.getText()), 
					stadtTextBox.getText(), strasseTextBox.getText(), hnrTextBox.getText(), result.getId(),
					new KinoErstellenCallback());
			clearForm();
			
		}
		
	}
	
	
/**Methoden***/
	
	public Boolean getEdit() {
		return edit;
	}

	public static void setEdit(Boolean edit) {
		KinoErstellenForm.edit = edit;
	}

	
	public static void setBearbeiten(Kino kino) {
		
		
		
			nameTextBox.setText(kino.getName());
			plzTextBox.setText(plzTextBox.getText().toString());
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
