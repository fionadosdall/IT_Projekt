package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;

public class FilmBearbeiten extends FlowPanel {

private KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();
	


	
	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();
	private FlowPanel speichernBox = new FlowPanel();
	private FlowPanel detailsBoxObenMitte = new FlowPanel();
	private FlowPanel detailsBoxMitteMitte = new FlowPanel();
	private FlowPanel detailsBoxMitteUnten = new FlowPanel();
	private FlowPanel detailsBoxUntenMitte = new FlowPanel();
	private FlowPanel detailsBoxUnten = new FlowPanel();
	private FlowPanel detailsObenBox = new FlowPanel();
	private FlowPanel detailsMitteBox = new FlowPanel();
	private FlowPanel detailsUntenBox = new FlowPanel();

	private Label title = new Label("Film beabeiten");
	private Label filmname = new Label("Filmname:");
	private Label beschreibungLabel = new Label("Beschreibung:");
	private Label bewertungLabel = new Label("Bewertung:");
	private Label laengeLabel = new Label("L&auml;nge:");
	
	private TextBox filmTB = new TextBox();
	private TextBox beschreibungTextBox = new TextBox();
	private TextBox bewertungTextBox = new TextBox();
	private TextBox laengeTextBox = new TextBox();
	
	
	private Button hinzufuegenButton = new Button("Hinzufügen");
	private Button entfernenButton = new Button("Film entfernen");
	private Button speichernButton = new Button("Speichern");
	
	private Image papierkorb = new Image();
	
	
	public void onLoad() {
		
		this.addStyleName("detailscontainer");

		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");

		detailsObenBox.addStyleName("detailsuntenBoxen");
		detailsMitteBox.addStyleName("detailsuntenBoxen");
		detailsUntenBox.addStyleName("detailsuntenBoxen");

		speichernBox.addStyleName("speichernBox");
		detailsBoxObenMitte.addStyleName("detailsBoxMitte");
		detailsBoxMitteMitte.addStyleName("detailsBoxMitte");
		detailsBoxMitteUnten.addStyleName("detailsBoxMitte");
		detailsBoxUntenMitte.addStyleName("detailsBoxMitte");
		detailsBoxUnten.addStyleName("detailsBoxUnten");

		title.addStyleName("title");
		filmname.addStyleName("detailsboxLabels");
		//mitglied.addStyleName("detailsboxLabels");
		//mitglieder.addStyleName("detailsboxLabels");

		filmTB.addStyleName("gruppenameTB");
		//mitgliedTB.addStyleName("nameTextBox");

		hinzufuegenButton.addStyleName("hinzufuegenButton");
		entfernenButton.addStyleName("entfernenButton");
		speichernButton.addStyleName("speichernButton");

		filmTB.getElement().setPropertyString("placeholder", "Gruppenname: " + gruppe.getName());
		//mitgliedTB.getElement().setPropertyString("placeholder", "User suchen");

		papierkorb.setUrl("/images/papierkorb.png");
		
		
		//Zusammenbauen der Widgets
		
		this.add(detailsoben);
		this.add(detailsunten);

		detailsoben.add(title);

		detailsunten.add(detailsObenBox);
		detailsunten.add(detailsMitteBox);
		detailsunten.add(detailsUntenBox);

		detailsObenBox.add(filmname);
		detailsObenBox.add(detailsBoxObenMitte);
		detailsBoxObenMitte.add(filmTB);

		//detailsMitteBox.add(mitglied);
		detailsMitteBox.add(detailsBoxMitteMitte);
		//detailsBoxMitteMitte.add(mitgliedTB);
		detailsMitteBox.add(detailsBoxMitteUnten);
		detailsBoxMitteUnten.add(hinzufuegenButton);

		//detailsUntenBox.add(mitglieder);
		detailsUntenBox.add(detailsBoxUntenMitte);
		//detailsBoxUntenMitte.add(anwenderCellTable);
		detailsUntenBox.add(detailsBoxUnten);
		detailsBoxUnten.add(entfernenButton);

		detailsunten.add(speichernBox);
		speichernBox.add(speichernButton);

		
	}
	
}
