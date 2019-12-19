package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;

public class KinokettenCell extends AbstractCell<Kinokette>{

	@Override
	public void render(Context context, Kinokette value, SafeHtmlBuilder sb) {
		if (value == null) {
			return;
		}
		
		sb.appendHtmlConstant("<div>");
		sb.appendEscaped(value.getName());
		sb.appendHtmlConstant("</div>");
		
	}
	
	

}
