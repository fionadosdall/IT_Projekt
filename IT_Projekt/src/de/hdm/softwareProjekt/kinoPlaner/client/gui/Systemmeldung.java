package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class Systemmeldung {

	
	public Systemmeldung() {
		
	}
	
	
	private static Label systemmeldungLabel = new Label();
	
	
	public static void anzeigen(String meldung) {
		
		
		systemmeldungLabel.setStyleName("systemmeldungLabel");
		systemmeldungLabel.setText(meldung);
		
		RootPanel.get("details").add(systemmeldungLabel);
		
		final Timer anzeigedauer = new Timer() {
			
			@Override
			public void run() {
				
				RootPanel.get("details").remove(RootPanel.get("details").getWidgetIndex(systemmeldungLabel));
			
			}
		};
		
		anzeigedauer.schedule(5000);
	}
}
