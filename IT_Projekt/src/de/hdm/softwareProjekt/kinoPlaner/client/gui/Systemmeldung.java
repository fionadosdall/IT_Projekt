package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Systemmeldung {

	
	public Systemmeldung() {
		
	}
	
	
	private static Label systemmeldungLabel = new Label();
	private static VerticalPanel systemmeldungPanel = new VerticalPanel();
	
	
	public static void anzeigen(String meldung) {
		
		
		systemmeldungLabel.setStyleName("systemmeldungLabel");
		systemmeldungLabel.setText(meldung);
		systemmeldungPanel.addStyleName("systemmeldungPanel");
		systemmeldungPanel.add(systemmeldungLabel);
		
		RootPanel.get("details").add(systemmeldungPanel);
		
		final Timer anzeigedauer = new Timer() {
			
			@Override
			public void run() {
				
				RootPanel.get("details").remove(RootPanel.get("details").getWidgetIndex(systemmeldungPanel));
			
			}
		};
		
		anzeigedauer.schedule(5000);
	}
}
