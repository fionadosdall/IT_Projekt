package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;

public class GruppeCell extends AbstractCell<Gruppe>{

	@Override
	public void render(Context context, Gruppe value, SafeHtmlBuilder sb) {
		if (value == null) {
			return;
		}
		
		sb.appendHtmlConstant("<div>");
		sb.appendEscaped(value.getName());
		sb.appendHtmlConstant("</div>");
		
	}

}
