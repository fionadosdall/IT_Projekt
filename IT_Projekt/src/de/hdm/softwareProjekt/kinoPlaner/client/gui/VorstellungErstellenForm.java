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
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

public class VorstellungErstellenForm extends VerticalPanel{
	
	private HorizontalPanel obenPanel = new HorizontalPanel();
	private HorizontalPanel untenPanel = new HorizontalPanel();

	private KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();
	
	private Label VorstellungFormLabel = new Label( "Neue Vorstellung:");
	private Label filmlabel = new Label ("Film:");
	private Label spielzeitlabel = new Label ("Spielzeit");
	private Label nameLabel = new Label ("Vorstellungsname:");
	
	
	private TextBox nameTextBox = new TextBox ();
	private TextBox filmTextBox = new TextBox ();
	private TextBox spielzeitTextBox = new TextBox ();

	private Button speichernButton = new Button ("Speichern");
	private Grid vorstellungsgrid = new Grid (3,2);
	
	public void onLoad() {
		VorstellungFormLabel.setStylePrimaryName("FormHeaderLabel");
		obenPanel.setStylePrimaryName("obenPanel");
		untenPanel.setStylePrimaryName("untenPanel");
		speichernButton.setStylePrimaryName("speichernButton");
		
		obenPanel.add(VorstellungFormLabel);
		
		vorstellungsgrid.setWidget(0, 0, nameLabel);
		vorstellungsgrid.setWidget(0, 1, nameTextBox);
		vorstellungsgrid.setWidget(1, 0, filmlabel);
		vorstellungsgrid.setWidget(1, 1, filmTextBox);
		vorstellungsgrid.setWidget(2, 0, spielzeitlabel);
		vorstellungsgrid.setWidget(2, 1, spielzeitTextBox);
		
		
		untenPanel.add(speichernButton);
	}
	
	private class SpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/*
	 * Callback
	 * 
	 */
	private class VorstellungErstellenCallback implements AsyncCallback <Vorstellung> {

		@Override
		public void onFailure(Throwable caught) {
			Systemmeldung.anzeigen("Eine neue Vorstellung konnte leider nicht erstellt werden");
			
			
		}

		@Override
		public void onSuccess(Vorstellung result) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Vorstellung wurde angelegt");
		}
		
	}
	
}
