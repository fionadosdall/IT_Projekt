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
	
	private Label kinoFormLabel = new Label("Neue Kinokette");
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
	private Grid kinoGrid = new Grid(2, 5);
	
	private KinoketteErstellenForm kinoErstellenForm;
	
	public KinoErstellenForm() {
		
		speichernButton.addClickHandler(new SpeichernClickHandler());
		untenPanel.add(speichernButton);
		
		
	}
	
	public void onLoad() {
		
		kinoFormLabel.setStylePrimaryName("kinoFormLabel");
		nameLabel.setStylePrimaryName("nameLabel");
		strasseLabel.setStylePrimaryName("strasseLabel");
		hnrLabel.setStylePrimaryName("hnrLabel");
		plzLabel.setStylePrimaryName("plzLabel");
		stadtLabel.setStylePrimaryName("stadtLabel");
		speichernButton.setStylePrimaryName("speichernButton");
		
		
		obenPanel.add(kinoFormLabel);
		
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

		
	
		untenPanel.add(speichernButton);
		
		
	}
	
	
	private class SpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
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
