package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.core.client.GWT;
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

import de.hdm.softwareProjekt.kinoPlaner.client.gui.BurgerMenueAdmin;
import de.hdm.softwareProjekt.kinoPlaner.client.AdminEntry.AktuellerAnwender;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.UserForm;



public class Header extends FlowPanel{
	
	private FlowPanel headerLinks = new FlowPanel();
	private FlowPanel headerMitte = new FlowPanel();
	private FlowPanel headerRechts = new FlowPanel();
	private FlowPanel headerLogo = new FlowPanel();
	private FlowPanel headerRechtsEins = new FlowPanel();
	private FlowPanel headerRechtsZwei = new FlowPanel();	
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
	private Anchor clientAnchor = new Anchor("Client");
	private Button einstellungenButton = new Button("USER");
	private Image suchenImage = new Image();
	private BurgerMenueAdmin burgerMenueAdmin = new BurgerMenueAdmin();
	
	private AdminDashboardForm home;
	private VolltextSucheAdminForm vsf;
	private UserForm uf;
	
	public Header() {
		
	}
	
	public void onLoad() {
		
		this.addStyleName("headerGesamt");

		headerLinks.addStyleName("headerLinks");
		headerLogo.addStyleName("headerLogo");
		headerLogoInput.addStyleName("headerLogoInput");

		
		headerRechts.addStyleName("headerRechts");
		headerRechtsEins.addStyleName("headerRechtsEins");
		headerRechtsZwei.addStyleName("headerRechtsZwei");
		headerRechtsElementSuchen.addStyleName("headerRechtsElementSuchen");
		headerRechtsElementLupe.addStyleName("headerRechtsElement");
		headerRechtsElementHome.addStyleName("headerRechtsElement");
		headerRechtsElementUser.addStyleName("headerRechtsElement");

		headerImage.addStyleName("headerImage");
		suchenImage.addStyleName("suchenImage");
		homeAnchor.addStyleName("headerAnchor");
		clientAnchor.addStyleName("headerAnchor");
		einstellungenButton.addStyleName("userButton");

		suchenImage.setUrl("/images/suchen.png");
		

		suchenTextBox.addStyleName("headerTextBox");

		suchenTextBox.getElement().setPropertyString("placeholder", "Suchen...");

		// Zusammenbauen der Widgets

		this.add(headerLinks);
		this.add(headerRechts);
		
		headerRechts.add(headerRechtsEins);
		headerRechts.add(headerRechtsZwei);

		headerLinks.add(headerLogo);
		headerLogo.add(headerLogoInput);
		
		headerRechtsEins.add(headerRechtsElementHome);
		headerRechtsEins.add(headerRechtsElementUser);
		headerRechtsEins.add(headerRechtsElementSuchen);


		headerRechtsElementSuchen.add(suchenTextBox);
		headerRechtsElementLupe.add(headerImage);
		headerImage.add(suchenImage);
		
		headerRechtsElementHome.add(homeAnchor);
		headerRechtsElementHome.add(clientAnchor);
		headerRechtsElementUser.add(einstellungenButton);
		headerRechtsElementSuchen.add(suchenTextBox);
		headerRechtsElementSuchen.add(headerImage);
		headerImage.add(suchenImage);

		headerRechts.add(burgerMenueAdmin);
		

		// Click-Handler

		homeAnchor.addClickHandler(new HomeButtonClickHandler());
		einstellungenButton.addClickHandler(new EinstellungenButtonClickHandler());
		suchenImage.addClickHandler(new SuchenClickHandler());
		clientAnchor.addClickHandler(new ClientAnchorClickHandler());


		
		
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
			RootPanel.get("details").clear();
			uf = new UserForm(AktuellerAnwender.getAnwender());
			RootPanel.get("details").add(uf);
		}
		
	}
	
	private class SuchenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			vsf = new VolltextSucheAdminForm(suchenTextBox.getText());
			RootPanel.get("details").add(vsf);

		}

	}
	
	
	private class ClientAnchorClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			clientAnchor.setHref(GWT.getHostPageBaseURL() + "IT_Projekt.html");
		}
		
	}

}
