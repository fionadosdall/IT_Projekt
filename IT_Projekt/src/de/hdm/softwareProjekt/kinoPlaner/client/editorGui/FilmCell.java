package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;

public class FilmCell extends AbstractCell<Film>{

	@Override
	public void render(Context context, Film value, SafeHtmlBuilder sb) {
		// TODO Auto-generated method stub
		
		if (value == null) {
			return;
		}
		
		sb.appendHtmlConstant("<div>");
		sb.appendEscaped(value.getName());
		sb.appendHtmlConstant("</div>");
		
	}
		
	}

