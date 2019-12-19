package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielplan;

public class SpielplanCell extends AbstractCell<Spielplan> 	{


@Override
public void render(Context context, Spielplan value, SafeHtmlBuilder sb) {
	if (value == null) {
		return;
	}
	
	sb.appendHtmlConstant("<div>");
	sb.appendEscaped(value.getName());
	sb.appendHtmlConstant("</div>");
	
}
}
