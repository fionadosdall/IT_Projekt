package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;


import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.client.EditorEntry.AktuellerAnwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;

public class RegistrierungsForm extends FlowPanel {

	KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

	private Anwender anwender;
	private Anchor destinationUrl = new Anchor();

	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();
	private FlowPanel detailsbox = new FlowPanel();
	private FlowPanel detailsboxInhalt = new FlowPanel();
	private FlowPanel detailsboxsButtons = new FlowPanel();

	private Label title = new Label("Anmelden");
	private Label nameLabel = new Label("Dein Name");

	private TextBox nameTextbox = new TextBox();

	private Button anmeldeButton = new Button("Anmelden");
	private Button abbrechenButton = new Button("Abbrechen");

	public RegistrierungsForm(Anchor destinationUrl, Anwender anwender) {
		this.destinationUrl = destinationUrl;
		this.anwender = anwender;

	}

	public void onLoad() {

		this.addStyleName("detailscontainer");

		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");
		detailsbox.addStyleName("detailsbox");
		detailsboxInhalt.addStyleName("detailsboxInhalt");
		detailsboxsButtons.addStyleName("detailsboxspeichern");
		
		title.addStyleName("title");
		nameLabel.addStyleName("detailsboxLabels");
		nameTextbox.addStyleName("nameTextBox");

		anmeldeButton.addStyleName("speichernButton");
		abbrechenButton.addStyleName("abbrechenButton");

		nameTextbox.getElement().setPropertyString("placeholder", "Gib Deinen Namen ein...");

		// Zusammenbauen der Widgets
		this.add(detailsoben);
		this.add(detailsunten);

		detailsoben.add(title);

		detailsunten.add(detailsbox);

		detailsbox.add(detailsboxInhalt);

		detailsboxInhalt.add(nameLabel);
		detailsboxInhalt.add(nameTextbox);

		detailsboxInhalt.add(detailsboxsButtons);
		detailsboxsButtons.add(anmeldeButton);
		detailsboxsButtons.add(abbrechenButton);

		anmeldeButton.addClickHandler(new AnmeldenClickHandler());
		abbrechenButton.addClickHandler(new AbbrechenClickHandler());

	}

	private class AnmeldenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			if(nameTextbox.getText().equals("")) {
				Window.alert("Bitte Namen eingeben!");
			}else {
			String name = nameTextbox.getText();
			anwender.setName(name);

			kinoplaner.speichern(anwender, new ErstelleAnwenderCallback());
			}
		}

	}

	private class AbbrechenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			Window.open(anwender.getLogoutUrl(), "_self", "");

		}

	}

	private class ErstelleAnwenderCallback implements AsyncCallback<Anwender> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Erstellen des Anwenders ist fehlgeschlagen");

		}

		@Override
		public void onSuccess(Anwender result) {
			
			anwender = result;
			
			if(result==null) {
				
				Window.alert("Name bereits verwendet!");
				
			} else {
				
			AktuellerAnwender.setAnwender(result);
		
		
			Window.open(destinationUrl.getHref(), "_self", "");
		

			
			}

		}

	}

}
