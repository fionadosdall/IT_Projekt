package de.hdm.softwareProjekt.kinoPlaner.client.gui;




import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DefaultDateTimeFormatInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.DateFormaterSpielzeit;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;

public class SpielzeitErstellenForm extends PopupPanel {
	
	private KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();
	
	private VerticalPanel popupPanel = new VerticalPanel();
	private HorizontalPanel obenPanel = new HorizontalPanel();
	private HorizontalPanel untenPanel = new HorizontalPanel();

	private Label title = new Label ("Neue Spielzeit erstellen");
	private Label spielzeitLabel = new Label ("Spielzeit ");
	private Label spielzeitBearbeiten = new Label("Spielzeit bearbeiten");
	private Label datum = new Label ("Datum: ");
	
	private TextBox spielzeitTB = new TextBox();
	private DateBox dateBox = new DateBox();
	
	private SpielplaneintragForm parent;
	
	DateFormaterSpielzeit dfs;

	private Button loeschenButton = new Button("Löschen");
	private Button speichernButton = new Button("Speichern");
	
	private Grid spielzeitGrid = new Grid (3, 2);

	private Spielzeit spielzeit;
	

	/** Default-Konstruktor **/
	
	public SpielzeitErstellenForm() {
		super(true);
	}
	
	public SpielzeitErstellenForm(Spielzeit spielzeit) {
		super(true);
		this.spielzeit = spielzeit;
	}
	
	public SpielzeitErstellenForm(SpielplaneintragForm parent) {
		this.parent = parent;
	}
	
	public SpielzeitErstellenForm(SpielplaneintragForm parent, Spielzeit spielzeit) {
		this.parent = parent;
		this.spielzeit = spielzeit;
	}
	
	public void onLoad() {
		
	/** Vergeben der Stylenames **/
	
		this.addStyleName("center");
		this.addStyleName("detailscontainer");
		this.addStyleName("popupPanel");

		title.addStyleName("formHeaderLabel");
		spielzeitLabel.addStyleName("textLabel");
		datum.addStyleName("textLabel");
		
		obenPanel.addStyleName("popupObenPanel");
		untenPanel.addStyleName("popupUntenPanel");
		
		dateBox.addStyleName("formularTextBox");
	

		speichernButton.addStyleName("speichernButton");
		
		dateBox.getElement().setPropertyString("placeholder", "Spielzeit auswählen");
		
		
		if (spielzeit != null) {
			
			obenPanel.add(spielzeitBearbeiten);
		
		} else {
			obenPanel.add(title);
			clearFormular();
		}
		
		popupPanel.add(obenPanel);
		
		spielzeitGrid.setWidget(0, 0, datum);
		spielzeitGrid.setWidget(0, 1, dateBox);

	

		popupPanel.add(spielzeitGrid);

		if (spielzeit != null) {
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
			
			DefaultDateTimeFormatInfo infoDDTFI = new DefaultDateTimeFormatInfo();
			String pattern ="yyyy-MM-dd HH:mm:ss";
			DateTimeFormat dft = new DateTimeFormat(pattern, infoDDTFI) {};
			String formatiert = dft.format(dateBox.getValue());
			
			Window.alert("formatiert" + formatiert);
		
			kinoplaner.erstellenSpielzeit("", formatiert, new SpielzeitErstellenCallback());
			
			
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
								
				SpielzeitErstellenForm.this.hide();
				
				parent.spielzeitRefresh();
				
				
			}
		}
		
	}
