package de.hdm.softwareProjekt.kinoPlaner.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.Footer;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.Header;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.Navigator;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class EditorEntry implements EntryPoint {
	
	Header header = new Header();
	Navigator navigator = new Navigator();
	Footer footer = new Footer();

	
	public void onModuleLoad() {
		
		RootPanel.get("header").add(header);
		RootPanel.get("navigator").add(navigator);
		RootPanel.get("footer").add(footer);
		
	}
}
