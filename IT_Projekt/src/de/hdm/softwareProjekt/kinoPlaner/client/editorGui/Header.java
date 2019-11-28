package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class Header extends FlowPanel {
	
	private FlowPanel headerLinks = new FlowPanel();
	private FlowPanel headerRechts = new FlowPanel(); 
	private FlowPanel headerLogo = new FlowPanel();
	private FlowPanel headerRechtsElement = new FlowPanel();
	
	private Label headerLogoInput = new Label("K I N O P L A N E R");
	private Image suchen = new Image();
	private Anchor home = new Anchor("HOME");
	private Button user = new Button("USER");
	
	
	public void onLoad() {
		
		this.addStyleName("headerGesamt");
		
		headerLinks.addStyleName("headerLinks");
		headerLogo.addStyleName("headerLogo");
		headerLogoInput.addStyleName("headerLogoInput");
		
		headerRechts.addStyleName("headerRechts");
		headerRechtsElement.addStyleName("headerRechtsElement");

	}

}
