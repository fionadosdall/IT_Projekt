package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class Header extends FlowPanel {

	private FlowPanel headerLinks = new FlowPanel();
	private FlowPanel headerRechts = new FlowPanel();
	private FlowPanel headerLogo = new FlowPanel();
	private FlowPanel headerRechtsElement = new FlowPanel();
	private FlowPanel headerImage = new FlowPanel();

	private Label headerLogoInput = new Label("K I N O P L A N E R");
	private Image suchenImage = new Image();
	private Anchor homeAnchor = new Anchor("HOME");
	private Button userButton = new Button("USER");

	private GruppenAnzeigenForm gaf;
	private UserForm uf;

	public void onLoad() {

		this.addStyleName("headerGesamt");

		headerLinks.addStyleName("headerLinks");
		headerLogo.addStyleName("headerLogo");
		headerLogoInput.addStyleName("headerLogoInput");

		headerRechts.addStyleName("headerRechts");
		headerRechtsElement.addStyleName("headerRechtsElement");

		headerImage.addStyleName("headerImage");
		suchenImage.addStyleName("suchenImage");
		homeAnchor.addStyleName("homeAnchor");
		userButton.addStyleName("userButton");

		suchenImage.setUrl("/images/suchen.png");

		// Zusammenbauen der Widgets

		this.add(headerLinks);
		this.add(headerRechts);

		headerLinks.add(headerLogo);
		headerLogo.add(headerLogoInput);

		headerRechts.add(headerRechtsElement);
		headerRechtsElement.add(headerImage);
		headerImage.add(suchenImage);

		headerRechtsElement.add(homeAnchor);
		headerRechtsElement.add(userButton);

		// Click-Handler
		
		homeAnchor.addClickHandler(new HomeClickHandler());
		userButton.addClickHandler(new UserFormClickHandler());

	}
	
	private class HomeClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			gaf = new GruppenAnzeigenForm();
			//RootPanel.get("details").add(gaf);
			
		}
		
	}
	
	private class UserFormClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			uf = new UserForm();
			//RootPanel.get("details").add(uf);
			
		}
		
	}

}
