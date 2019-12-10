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
import de.hdm.softwareProjekt.kinoPlaner.shared.Kinoplaner;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;


public class KinoketteErstellenForm extends VerticalPanel{
	
	private KinoplanerAsync administration = ClientsideSettings.getKinoplaner();
	
	private HorizontalPanel obenPanel = new HorizontalPanel();
	private HorizontalPanel untenPanel = new HorizontalPanel();
	
	private Label kinoketteFormLabel = new Label("Neue Kinokette");
	private Label nameLabel = new Label("Kinokettenname:");
	private Label sitzLabel = new Label("Sitz:");
	private Label websiteLabel = new Label("Website:");
	
	private TextBox nameTextBox = new TextBox();
	private TextBox sitzTextBox = new TextBox();
	private TextBox websiteTextBox = new TextBox();
	
	private Grid kinoketteGrid = new Grid(4, 2);
	private Button speichernButton = new Button("Speichern");
	
	private KinoketteErstellenForm kinoketteErstellenForm;
	
	
	/**
	 * Bei der Instanziierung  wird der ClickHandler dem Button und dem Panel hinzugefügt
	 */	
	
	public KinoketteErstellenForm() {
		
		speichernButton.addClickHandler(new SpeichernClickHandler());
		untenPanel.add(speichernButton);
		
		
	}
	
	
	public void onLoad() {
		
		kinoketteFormLabel.setStylePrimaryName("FormHeaderLabel");
		nameLabel.setStylePrimaryName("textLabel");
		sitzLabel.setStylePrimaryName("textLabel");;
		websiteLabel.setStylePrimaryName("textLabel");
		speichernButton.setStylePrimaryName("speichernButton");
		obenPanel.setStylePrimaryName("obenPanel");
		untenPanel.setStylePrimaryName("untenPanel");
		
		
		obenPanel.add(kinoketteFormLabel);
		
		kinoketteGrid.setWidget(0, 0, nameLabel);
		kinoketteGrid.setWidget(0, 1, nameTextBox);
		kinoketteGrid.setWidget(1, 0, sitzLabel);
		kinoketteGrid.setWidget(1, 1, sitzTextBox);
		kinoketteGrid.setWidget(2, 0, websiteLabel);
		kinoketteGrid.setWidget(2, 1, websiteTextBox);
		
	
		untenPanel.add(speichernButton);
	}
		
		
		
	public class SpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			administration.erstellenKinokette(nameTextBox.getText(), sitzTextBox.getText(),
					websiteTextBox.getText(), new KinoketteErstellenCallback());
		}		
		
	}

		
	/* Callback */
				
	private class KinoketteErstellenCallback implements AsyncCallback<Kinokette> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Eine neue Kinokette konnte leider nicht erstellt werden");
		}

		@Override
		public void onSuccess(Kinokette result) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Kinokette wurde angelegt");
		}
		
	}
	

}
