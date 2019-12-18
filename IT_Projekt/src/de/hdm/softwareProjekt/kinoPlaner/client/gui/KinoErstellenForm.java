package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;

public class KinoErstellenForm extends VerticalPanel {
	
	private HorizontalPanel obenPanel = new HorizontalPanel();
	private HorizontalPanel untenPanel = new HorizontalPanel();
	
	private KinoplanerAsync administration = ClientsideSettings.getKinoplaner();
	
	private Label kinoFormLabel = new Label("Neues Kino");
	private Label nameLabel = new Label("Kinoname:");
	private Label plzLabel = new Label("PLZ:");
	private Label stadtLabel = new Label("Stadt:");
	private Label strasseLabel = new Label("Straße:");
	private Label hnrLabel = new Label("Hausnummer:");
	
	private TextBox nameTextBox = new TextBox();
	private TextBox plzTextBox = new TextBox();
	private TextBox stadtTextBox = new TextBox();
	private TextBox strasseTextBox = new TextBox();
	private TextBox hnrTextBox = new TextBox();
	
	private Button speichernButton = new Button("Speichern");
	private Grid kinoGrid = new Grid(5, 2);
	
	
	
	
	/**
	 * Bei der Instanziierung  wird der ClickHandler dem Button und dem Panel hinzugefügt
	 */	
	
	public KinoErstellenForm() {
		
		speichernButton.addClickHandler(new SpeichernClickHandler());
		untenPanel.add(speichernButton);
		
		
	}
	
	public void onLoad() {
		
		/*Vergeben der Style-Namen*/
		
		kinoFormLabel.addStyleName("formHeaderLabel");
		nameLabel.addStyleName("textLabel");
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
		this.addStyleName("center");
		
		
		
		/*Zusammensetzen der Widgets */
		
		obenPanel.add(kinoFormLabel);
		
		this.add(obenPanel);
		
		
		kinoGrid.setWidget(0, 0, nameLabel);
		kinoGrid.setWidget(0, 1, nameTextBox);
		kinoGrid.setWidget(1, 0, strasseLabel);
		kinoGrid.setWidget(1, 1, strasseTextBox);
		kinoGrid.setWidget(2, 0, hnrLabel);
		kinoGrid.setWidget(2, 1, hnrTextBox);
		kinoGrid.setWidget(3, 0, plzLabel);
		kinoGrid.setWidget(3, 1, plzTextBox);
		kinoGrid.setWidget(4, 0, stadtLabel);
		kinoGrid.setWidget(4, 1, stadtTextBox);

		this.add(kinoGrid);
	
		untenPanel.add(speichernButton);
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

		
	/* Callback */
				
	private class KinoErstellenCallback implements AsyncCallback<Kino> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Eine neue Kino konnte leider nicht erstellt werden");
		}

		@Override
		public void onSuccess(Kino result) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Kino wurde angelegt");
		}
		
	}
	
	
}
