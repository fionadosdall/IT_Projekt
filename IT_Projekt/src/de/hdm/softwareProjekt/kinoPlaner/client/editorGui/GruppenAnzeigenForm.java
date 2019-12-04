/**
 * package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;
 * import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;

public class GruppenAnzeigenForm extends FlowPanel {
	
	private KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner(); 												
	private Anwender anwender = CurrentAnwender.getAnwender();
	private Anwender newAnwender= null;
	
	
	private TextBox addAnwenderTextBox = new TextBox ();
	private Button speicherGruppenButton = new Button ("Speichern");
	private FlexTable mitgliedFlexTable = new FlexTable();
	
	
	
}
 */



