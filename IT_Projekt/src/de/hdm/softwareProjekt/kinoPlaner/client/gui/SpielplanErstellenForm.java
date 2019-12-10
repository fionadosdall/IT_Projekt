package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.client.gui.SpielzeitErstellenForm.SpeichernClickHandler;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielplan;

public class SpielplanErstellenForm extends VerticalPanel {
	
	private int kinoId;

	private KinoplanerAsync administration = ClientsideSettings.getKinoplaner();
	
	private HorizontalPanel obenPanel = new HorizontalPanel();
	private HorizontalPanel untenPanel = new HorizontalPanel();
	
	private Label spielplanFormLabel = new Label("Spielplan erstellen");
	private Label nameLabel = new Label("name:");
	private Label spielzeitLabel = new Label("Spielzeit:");
	private Label filmHinzufuegenLabel = new Label("Film  hinzuf&uuml;gen");
	private Label filmBearbeitenLabel = new Label("Film bearbeiten");
	private Label spielzeitHinzufuegenLabel = new Label("Spielzeit  hinzuf&uuml;gen");
	private Label spielzeitBearbeitenLabel = new Label("Spielzeit bearbeiten");
	
	private TextBox nameTextBox = new TextBox();
	
	private Button speichernButton = new Button("Speichern");
	
	/**
	 * Bei der Instanziierung  wird der ClickHandler dem Button und dem Panel hinzugefügt
	 */	
		
		
	public SpielplanErstellenForm() {
		
		speichernButton.addClickHandler(new SpeichernClickHandler());
		untenPanel.add(speichernButton);
		
	}	
	
public void onLoad() {
		
		spielplanFormLabel.setStylePrimaryName("spielzeitFormLabel");
		nameLabel.setStylePrimaryName("textLabel");
		filmHinzufuegenLabel.setStylePrimaryName("textLabel");
		filmBearbeitenLabel.setStylePrimaryName("textLabel");
		spielzeitHinzufuegenLabel.setStylePrimaryName("textLabel");
		spielzeitBearbeitenLabel.setStylePrimaryName("textLabel");
		
		spielzeitLabel.setStylePrimaryName("spielzeitLabel");
		speichernButton.setStylePrimaryName("speichernButton");
		obenPanel.setStylePrimaryName("obenPanel");
		untenPanel.setStylePrimaryName("untenPanel");
		
		
		obenPanel.add(spielplanFormLabel);
		
		
		
	
		untenPanel.add(speichernButton);
	}
	
	
	
	
	
	private class SpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			administration.erstellenSpielplanKino(nameTextBox.getText(), kinoId,
					new SpielplanErstellenCallback() );
			
		}		
		
	}

		
	/* Callback */
				
	private class SpielplanErstellenCallback implements AsyncCallback<Spielplan> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Eine neuer Spielplan konnte leider nicht erstellt werden");
		}

		@Override
		public void onSuccess(Spielplan result) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Spielplan wurde angelegt");
		}
		
	}
	
	
}
	

