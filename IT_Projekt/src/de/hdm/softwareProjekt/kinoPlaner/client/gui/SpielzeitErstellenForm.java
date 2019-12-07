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
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;

public class SpielzeitErstellenForm extends VerticalPanel {
	
	private HorizontalPanel obenPanel = new HorizontalPanel();
	private HorizontalPanel untenPanel = new HorizontalPanel();

	private KinoplanerAsync administration = ClientsideSettings.getKinoplaner();
	
	private Label spielzeitFormLabel = new Label("Neue Spielzeit");
	private Label nameLabel = new Label("Name:");
	private Label spielzeitLabel = new Label("Spielzeit:");
	private Label datumLabel = new Label("Datum:");
	
	private TextBox nameTextBox = new TextBox();
	private TextBox spielzeitTextBox = new TextBox();
	private DateBox dateBox = new DateBox();

	private Button speichernButton = new Button("Speichern");
	private Grid spielzeitGrid = new Grid(2, 2);
	
public void onLoad() {
		
		spielzeitFormLabel.setStylePrimaryName("spielzeitFormLabel");
		nameLabel.setStylePrimaryName("nameLabel");
		spielzeitLabel.setStylePrimaryName("spielzeitLabel");
		speichernButton.setStylePrimaryName("speichernButton");
		
		
		obenPanel.add(spielzeitFormLabel);
		
		spielzeitGrid.setWidget(0, 0, nameLabel);
		spielzeitGrid.setWidget(0, 1, nameTextBox);
		spielzeitGrid.setWidget(1, 0, spielzeitLabel);
		spielzeitGrid.setWidget(1, 1, spielzeitTextBox);
		spielzeitGrid.setWidget(2, 0, datumLabel);
		spielzeitGrid.setWidget(2, 0, dateBox);
		
		
	
		untenPanel.add(speichernButton);
	}
	
	
	
	
	
	private class SpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
		}		
		
	}

		
	/* Callback */
				
	private class SpielzeitErstellenCallback implements AsyncCallback<Spielzeit> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Eine neue Spielzeit konnte leider nicht erstellt werden");
		}

		@Override
		public void onSuccess(Spielzeit result) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Spielzeit wurde angelegt");
		}
		
	}
	
	
}
