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

/*
 * Klasse stellt das Formular um Einstellungen zu bearbeiten
 */
public class EinstellungenBearbeitenForm extends VerticalPanel {

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

		/*
		 * Vergeben der Style-Namen
		 */

		einstellungenFormLabel.setStylePrimaryName("FormHeaderLabel");
		nicknameLabel.setStylePrimaryName("textLabel");
		emailLabel.setStylePrimaryName("textLabel");
		obenPanel.setStylePrimaryName("obenPanel");
		untenPanel.setStylePrimaryName("untenPanel");
		speichernButton.setStylePrimaryName("speichernButton");

		obenPanel.add(einstellungenFormLabel);
		this.add(obenPanel);

		einstellungenGrid.setWidget(0, 0, nicknameLabel);
		einstellungenGrid.setWidget(0, 1, nicknameTextBox);
		einstellungenGrid.setWidget(1, 0, emailLabel);
		einstellungenGrid.setWidget(1, 1, emailTextBox);

		this.add(einstellungenGrid);

		untenPanel.add(speichernButton);
		this.add(untenPanel);
	}

	/*
	 * ClickHandler um die Einstellungen wie Nickname, Email etc des Anwenders zu s
	 * speichern
	 */
	private class SpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			administration.erstellenAnwender(nicknameTextBox.getText(), emailTextBox.getText(),
					new EinstellungenBearbeitenCallback());
		}

	}

	/* Callback */

	/*
	 * Callback um die Einstellungen des Anwenders zu bearbeiten
	 */

	private class EinstellungenBearbeitenCallback implements AsyncCallback<Anwender> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Die Aenderungen konnten leider nicht gespeichert werden.");
		}

		@Override
		public void onSuccess(Anwender result) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Aenderungen gespeichert.");
		}

	}
}
