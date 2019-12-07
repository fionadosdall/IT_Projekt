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
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;

public class EinstellungenBearbeiten extends VerticalPanel {
	
	private HorizontalPanel obenPanel = new HorizontalPanel();
	private HorizontalPanel untenPanel = new HorizontalPanel();

	private KinoplanerAsync administration = ClientsideSettings.getKinoplaner();
	
	private Label einstellungenFormLabel = new Label("Kontoeinstellungen");
	private Label nicknameLabel = new Label("Nickname:");
	private Label emailLabel = new Label("G-Mail Adresse:");
	
	
	private TextBox nicknameTextBox = new TextBox();
	private TextBox emailTextBox = new TextBox();
	
	private Button speichernButton = new Button("Speichern");
	private Grid einstellungenGrid = new Grid(2, 2);
	
public void onLoad() {
		
		einstellungenFormLabel.setStylePrimaryName("FormHeaderLabel");
		obenPanel.setStylePrimaryName("obenPanel");
		untenPanel.setStylePrimaryName("untenPanel");
		speichernButton.setStylePrimaryName("speichernButton");
		
		
		obenPanel.add(einstellungenFormLabel);
		
		einstellungenGrid.setWidget(0, 0, nicknameLabel);
		einstellungenGrid.setWidget(0, 1, nicknameTextBox);
		einstellungenGrid.setWidget(1, 0, emailLabel);
		einstellungenGrid.setWidget(1, 1, emailTextBox);
	
		
	
		untenPanel.add(speichernButton);
	}
	
private class SpeichernClickHandler implements ClickHandler {

	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}		
	
}

	
/* Callback */
			
private class EinstellungenBearbeitenCallback implements AsyncCallback<Anwender> {

	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		Systemmeldung.anzeigen("Die Änderungen konnten leider nicht gespeichert werden.");
	}

	@Override
	public void onSuccess(Anwender result) {
		// TODO Auto-generated method stub
		Systemmeldung.anzeigen("A&auml;nderungen gespeichert.");
	}
	
}
}
