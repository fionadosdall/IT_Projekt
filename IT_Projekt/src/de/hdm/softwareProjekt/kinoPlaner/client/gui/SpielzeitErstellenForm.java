package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import java.sql.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.client.gui.KinoketteErstellenForm.SpeichernClickHandler;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;

public class SpielzeitErstellenForm extends FlowPanel {
	
	
	KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();
	
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

	
	private Label title = new Label ("Neue Spielzeit erstellen");
	private Label spielzeitname = new Label ("Name: ");
	private Label spielzeit = new Label ("Spielzeit ");
	private Label datum = new Label ("Datum: ");


	
	private TextBox nameTB = new TextBox();
	private TextBox spielzeitTB = new TextBox();
	private DateBox dateBox = new DateBox();
	
	
	private Button hinzufuegenButton = new Button("Hinzufügen");
	private Button entfernenButton = new Button ("Spielzeit entfernen");
	private Button speichernButton = new Button("Speichern");
	
	

private Spielzeit neueSpielzeit = null;


	
	public void onLoad() {
		
	// Vergeben der Stylenames
	
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
		spielzeitname.addStyleName("detailsboxLabels");
		spielzeit.addStyleName("detailsboxLabels");
		datum.addStyleName("detailsboxLabels");
		
		
		nameTB.addStyleName("nameTB");
		spielzeitTB.addStyleName("SpielzeitTB");
		dateBox.addStyleName("DatumB");
		
		hinzufuegenButton.addStyleName("hinzufuegenButton");
		entfernenButton.addStyleName("entfernenButton");
		speichernButton.addStyleName("speichernButton");
		
		nameTB.getElement().setPropertyString("placeholder", "Name eingeben");
		spielzeitTB.getElement().setPropertyString("placeholder", "Spielzeit eingeben");
		
		
		// Zusammenbauen der Widgets
		
		this.add(detailsoben);
		this.add(detailsunten);

		detailsoben.add(title);

		detailsunten.add(detailsObenBox);
		detailsunten.add(detailsMitteBox);
		detailsunten.add(detailsUntenBox);

		detailsObenBox.add(spielzeit);
		detailsObenBox.add(detailsBoxObenMitte);
		detailsBoxObenMitte.add(spielzeitTB);

		detailsMitteBox.add(spielzeitname);
		detailsMitteBox.add(detailsBoxMitteMitte);
		detailsBoxMitteMitte.add(nameTB);
		detailsMitteBox.add(detailsBoxMitteUnten);
		detailsBoxMitteUnten.add(hinzufuegenButton);

		detailsUntenBox.add(datum);
		detailsUntenBox.add(detailsBoxUntenMitte);
		detailsBoxUntenMitte.add(dateBox);
		detailsUntenBox.add(detailsBoxUnten);
		detailsBoxUnten.add(entfernenButton);

		detailsunten.add(speichernBox);
		speichernBox.add(speichernButton);
		
		
		hinzufuegenButton.addClickHandler(new SpielzeitHinzufuegenClickHandler());
		entfernenButton.addClickHandler(new SpielzeitEntfernenClickHandler());
		speichernButton.addClickHandler(new SpeichernClickHandler());

	}
	
/**
 * Bei der Instanziierung  wird der ClickHandler dem Button und dem Panel hinzugefügt
 */	
	
	
	private class SpielzeitHinzufuegenClickHandler implements ClickHandler {

	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub
		//kinoplaner.getSpielzeitenByAnwenderOwner(spielzeitTB.getValue(), new SpielzeitCallback());
		
	}
		
	}
	
	
	private class SpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			kinoplaner.erstellenSpielzeit(spielzeitTB.getValue(), (Date) dateBox.getValue(), new SpielzeitErstellenCallback());
			
		}
		
	}
	
	private class SpielzeitEntfernenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
/**
 * Callbacks
 * @author fiona
 *
 */
	
private class SpielzeitCallback implements AsyncCallback<Spielzeit> {

	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		Systemmeldung.anzeigen("SpielzeitCallback funktioniert nicht");
		
	}

	@Override
	public void onSuccess(Spielzeit spielzeit) {
		
		
		neueSpielzeit = spielzeit;
		spielzeit.getZeit();
		
		kinoplaner.erstellenSpielzeit(neueSpielzeit,date, new SpielzeitHinzufuegenCallback());
		
	}
	
}
	
	
	
private class SpielzeitHinzufuegenCallback implements AsyncCallback<Spielzeit> {

	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		Systemmeldung.anzeigen("SpielzeitHinzufügenCallback funktioniert nicht");
	}

	@Override
	public void onSuccess(Spielzeit result) {
		// TODO Auto-generated method stub
		Systemmeldung.anzeigen("Spielzeit wurde hinzugefügt");
	}
	
	
	
private class SpielzeitEntfernenCallback implements AsyncCallback<Spielzeit> {

	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(Spielzeit result) {
		// TODO Auto-generated method stub
		
	}
	
}
	
		
	/* Callback */
				
	private class SpielzeitErstellenCallback implements AsyncCallback<Spielzeit> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Eine neue Spielzeit konnte leider nicht erstellt werden");
		}

		@Override
		public void onSuccess(Spielzeit result) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Spielzeit wurde angelegt");
		}
		
	}

	


	
	
}}
