
package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import com.google.gwt.user.client.ui.Button;
//import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
//import com.google.gwt.user.client.ui.TextBox;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
//import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;

public class GruppenAnzeigenForm extends FlowPanel {
	
	private KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner(); 												
	//private Anwender anwender = CurrentAnwender.getAnwender();
//	private Anwender newAnwender= null;
//	
//	
//	private TextBox addAnwenderTextBox = new TextBox ();
//	private Button speicherGruppenButton = new Button ("Speichern");
//	private FlexTable mitgliedFlexTable = new FlexTable();
//	
	
	HomeBar hb = new HomeBar();
	
	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();
	
	
	public void onLoad() {

		this.addStyleName("detailscontainer");
		
		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");
		
		this.add(detailsoben);
		this.add(detailsunten);
		
		detailsoben.add(hb);
	
		
		
		
	
	}

	
}




