package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
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
	
	private TextBox nameTextBox = new TextBox();
	private TextBox plzTextBox = new TextBox();
	private TextBox stadtTextBox = new TextBox();
	private TextBox strasseTextBox = new TextBox();
	private TextBox hnrTextBox = new TextBox();
	private ListBox kinokettenListBox = new ListBox();
	
	private Button speichernButton = new Button("Speichern");
	private Button loeschenButton = new Button("Löschen");
	private Grid kinoGrid = new Grid(6, 2);
	
	private static Boolean edit = false;
	private MeineKinosForm mkf;
	private Kino kinoBearbeiten;
	private Kino k;
	
	
	/**
	 * Bei der Instanziierung  wird der ClickHandler dem Button und dem Panel hinzugefügt
	 */	
	
	public KinoErstellenForm() {
		
		speichernButton.addClickHandler(new SpeichernClickHandler());
		untenPanel.add(speichernButton);
		//loeschenButton.addClickHandler(new KinoLoeschenClickHandler());
		
		
	}
	
	public KinoErstellenForm(Kino k) {
		this.k = k;
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
		
		
		if(edit == true) {
			untenPanel.add(loeschenButton);
			untenPanel.add(speichernButton);
		} else {
			clearForm();
			untenPanel.add(speichernButton);
		}
		
		this.add(untenPanel);
		
	}
	
	
	/* ClickHandler */


	private class SpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			administration.erstellenKino(nameTextBox.getText(), Integer.parseInt(plzTextBox.getText()), 
					stadtTextBox.getText(), strasseTextBox.getText(), hnrTextBox.getText(), 
					new KinoErstellenCallback());
		}		
		
	}
	
	private class KinoLoeschenClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
			// administration.kinoEntfernen(kinoBearbeiten.getId(), new KinoLoeschenCallback());
			RootPanel.get("details").clear();
			mkf = new MeineKinosForm();
			RootPanel.get("details").add(mkf);
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
	
	
	private class KinoLoeschenCallback implements AsyncCallback<Kino> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Kino konnte nicht gelöscht werden.");
			
		}

		@Override
		public void onSuccess(Kino result) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Kino wurde gelöscht.");
			
		}
		
	}
	
	
/**Methoden***/
	
	public Boolean getEdit() {
		return edit;
	}

	public static void setEdit(Boolean edit) {
		KinoErstellenForm.edit = edit;
	}

	/* TODO
	public static void setBearbeiten(Kino kino) {
		
		
		
			nameTextBox.setText(kino.getName());
			plzTextBox.setText(kino.getPlz());
			strasseTextBox.setText(kino.getStrasse());
			hnrTextBox.setText(kino.getHausnummer());
			stadtTextBox.setText(kino.getStadt());
			
		
	} */
	
	public void clearForm() {
		nameTextBox.setText("");
		plzTextBox.setText("");
		strasseTextBox.setText("");
		hnrTextBox.setText("");
		stadtTextBox.setText("");
		
	}
	
	
}
