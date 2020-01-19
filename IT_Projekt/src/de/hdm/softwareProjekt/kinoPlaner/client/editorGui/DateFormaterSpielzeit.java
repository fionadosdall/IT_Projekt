package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

//import java.sql.Date;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.shared.DefaultDateTimeFormatInfo;

public class DateFormaterSpielzeit {
	
	Date date;

	
	public DateFormaterSpielzeit (Date date) {
		this.date = date;

	}
	
	public String toString() {
		DefaultDateTimeFormatInfo infoDDTFI = new DefaultDateTimeFormatInfo();
		String pattern ="dd.MM.yy HH:mm";
		DateTimeFormat dft = new DateTimeFormat(pattern) {};
		return dft.format(date);
	}

}
