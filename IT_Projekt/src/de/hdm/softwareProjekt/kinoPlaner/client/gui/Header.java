package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;

import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.Home;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.VolltextSucheForm;


public class Header extends FlowPanel{
	
	private FlowPanel headerLinks = new FlowPanel();
	private FlowPanel headerRechts = new FlowPanel();
	private FlowPanel headerLogo = new FlowPanel();
	private FlowPanel headerRechtsElementSuchen = new FlowPanel();
	private FlowPanel headerRechtsElementLupe = new FlowPanel();
	private FlowPanel headerRechtsElementHome = new FlowPanel();
	private FlowPanel headerRechtsElementUser = new FlowPanel();
	private FlowPanel headerRechtsElementEinstellungen = new FlowPanel();
	private FlowPanel headerImage = new FlowPanel();
	
	private Label headerLogoInput = new Label("A D M I N C L I E N T");
	
	private MultiWordSuggestOracle alleDaten = new MultiWordSuggestOracle();
	private SuggestBox suchenTextBox = new SuggestBox(alleDaten);
	
	
	private Anchor homeAnchor = new Anchor("HOME");
	private Anchor einstellungenAnchor = new Anchor("Einstellungen");
	private Button clientButton = new Button("Client");
	private Image suchenImage = new Image();
	
	private AdminDashboardForm home;
	private VolltextSucheForm vsf;
	
	public Header() {
		
	}
	
	public void onLoad() {
		
		this.addStyleName("headerGesamt");

		headerLinks.addStyleName("headerLinks");
		headerLogo.addStyleName("headerLogo");
		headerLogoInput.addStyleName("headerLogoInput");

		headerRechts.addStyleName("headerRechts");
		headerRechtsElementSuchen.addStyleName("headerRechtsElement");
		headerRechtsElementLupe.addStyleName("headerRechtsElement");
		headerRechtsElementHome.addStyleName("headerRechtsElement");
		headerRechtsElementUser.addStyleName("headerRechtsElement");
		headerRechtsElementEinstellungen.addStyleName("headerRechtsElement");

		headerImage.addStyleName("headerImage");
		suchenImage.addStyleName("suchenImage");
		homeAnchor.addStyleName("homeAnchor");
		einstellungenAnchor.addStyleName("homeAnchor");
		clientButton.addStyleName("userButton");

		suchenImage.setUrl("/images/suchen.png");

		suchenTextBox.addStyleName("nameTextBox");

		suchenTextBox.getElement().setPropertyString("placeholder", "Suchen...");

		// Zusammenbauen der Widgets

		this.add(headerLinks);
		this.add(headerRechts);

		headerLinks.add(headerLogo);
		headerLogo.add(headerLogoInput);

		headerRechts.add(headerRechtsElementSuchen);
		headerRechts.add(headerRechtsElementLupe);
		headerRechts.add(headerRechtsElementHome);
		headerRechts.add(headerRechtsElementUser);
		headerRechts.add(headerRechtsElementEinstellungen);

		headerRechtsElementSuchen.add(suchenTextBox);
		headerRechtsElementLupe.add(headerImage);
		headerImage.add(suchenImage);

		headerRechtsElementHome.add(homeAnchor);
		headerRechtsElementHome.add(einstellungenAnchor);
		headerRechtsElementUser.add(clientButton);
		

		// Click-Handler

		homeAnchor.addClickHandler(new HomeButtonClickHandler());
		clientButton.addClickHandler(new ClientButtonClickHandler());
		suchenImage.addClickHandler(new SuchenClickHandler());
		einstellungenAnchor.addClickHandler(new EinstellungenButtonClickHandler());

		//kinoplaner.getGruppenByAnwender(new GetGruppenByAnwenderCallback());
		//kinoplaner.getUmfragenByAnwender(new GetUmfragenByAnwenderCallback());
		//kinoplaner.anzeigenVonClosedUmfragen(new AnzeigenVonClosedUmfragenCallback());

		
		
	}
	
	private class HomeButtonClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			home = new AdminDashboardForm();
			RootPanel.get("details").add(home);
		}
		
	}
	
	private class EinstellungenButtonClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class SuchenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			vsf = new VolltextSucheForm(suchenTextBox.getText());
			RootPanel.get("details").add(vsf);

		}

	}
	
	
	private class ClientButtonClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
