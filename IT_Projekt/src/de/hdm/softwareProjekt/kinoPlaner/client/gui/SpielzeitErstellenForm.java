package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DefaultDateTimeFormatInfo;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.DateFormaterSpielzeit;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

public class SpielzeitErstellenForm extends PopupPanel {
	
	private static Boolean edit = false;
	
	private KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();
	
	private VerticalPanel popupPanel = new VerticalPanel();
	private HorizontalPanel obenPanel = new HorizontalPanel();
	private HorizontalPanel untenPanel = new HorizontalPanel();

	private Label title = new Label ("Neue Spielzeit erstellen");
	private Label spielzeit = new Label ("Spielzeit ");
	private Label spielzeitBearbeiten = new Label("Spielzeit bearbeiten");
	private Label datum = new Label ("Datum: ");
	
	private TextBox spielzeitTB = new TextBox();
	private DateBox dateBox = new DateBox();
 //   private DatePicker datePicker = new DatePicker();
	
	DateFormaterSpielzeit dfs;

	private Button loeschenButton = new Button("Löschen");
	private Button speichernButton = new Button("Speichern");
	
	private Grid spielzeitGrid = new Grid (3, 2);

	private Spielzeit spielzeit2 = null;
	
	/** Kunstruktor zur Übergabe des zu bearbeiteden Spielplan **/
	
	public SpielzeitErstellenForm(Spielzeit spz) {
		this.spielzeit2 = spz;
	}
	

	/** Default-Konstruktor **/
	
	public SpielzeitErstellenForm() {
		super(true);
	}
	
	public void onLoad() {
		
	/** Vergeben der Stylenames **/
	
		this.addStyleName("center");
		this.addStyleName("detailscontainer");
		this.addStyleName("popupPanel");

		title.addStyleName("formHeaderLabel");
		spielzeit.addStyleName("textLabel");
		datum.addStyleName("textLabel");
		
		obenPanel.addStyleName("popupObenPanel");
		untenPanel.addStyleName("popupUntenPanel");
		
		spielzeitTB.addStyleName("formularTextBox");
	//	dateBox.addStyleName("DatumB");

		speichernButton.addStyleName("speichernButton");
		
		dateBox.getElement().setPropertyString("placeholder", "Spielzeit auswählen");
		
		
		if (edit == true) {
			
			obenPanel.add(spielzeitBearbeiten);
		}else {
			obenPanel.add(title);
			clearFormular();
		}
		
		popupPanel.add(obenPanel);
		
		spielzeitGrid.setWidget(0, 0, datum);
		spielzeitGrid.setWidget(0, 1, dateBox);
		
//		spielzeitGrid.setWidget(1, 0, datum);
//		spielzeitGrid.setWidget(1, 1, dateBox);
		

		popupPanel.add(spielzeitGrid);

		if(edit == true) {
			untenPanel.add(loeschenButton);
			untenPanel.add(speichernButton);
			
		} else {
			clearFormular();
			untenPanel.add(speichernButton);
		}
		
		
		popupPanel.add(untenPanel);

		this.add(popupPanel);
		
		
		/* ClickHandler */

		speichernButton.addClickHandler(new SpeichernClickHandler());

		
/**
 * Bei der Instanziierung  wird der ClickHandler dem Button und dem Panel hinzugefügt
 */	
	
/**
 * CLICKHANDLER
 */
	
}

	private class SpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			

			kinoplaner.erstellenSpielzeit("", dateBox.getValue(), new SpielzeitErstellenCallback());
			
			Window.alert(dateBox.getValue().toString());
			
		}
		
	}
	
	private class EntfernenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
			RootPanel.get("details").clear();
			
		}
		
	}
	
	private void clearFormular() {
		// TODO Auto-generated method stub
		

//		dateBox.setTitle("");
		
	}
	
	
		
	/* Callback */
				
	private class SpielzeitErstellenCallback implements AsyncCallback<Spielzeit> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Systemmeldung.anzeigen("Eine neue Spielzeit konnte leider nicht erstellt werden");
			
			Window.alert(caught.getMessage());
		}

		@Override
		public void onSuccess(Spielzeit result) {
			// TODO Auto-generated method stub
			
				Window.alert("Spielzeit wurde erstellt");
				
				SpielplaneintragForm sef = new SpielplaneintragForm();
				SpielplaneintragForm.getSpeilzeitListBox().addItem(result.getZeit().toString());
				
				Window.alert(result.getZeit().toString());
				
				SpielzeitErstellenForm.this.hide();
				
				
			}
		}
		
	}
