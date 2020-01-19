package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

//import java.sql.Date;
import java.util.Date;

public class DateFormaterSpielzeit {
	
	Date date;
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	
	public DateFormaterSpielzeit (Date date) {
		this.date = date;
		year = date.getYear();
		month = date.getMonth()+1;
		day = date.getDate();
		hour = date.getHours();
		minute = date.getMinutes();
	}
	
	public String toString() {
		StringBuffer buffi = new StringBuffer();
		buffi.append(year);
		buffi.append("-");
		if (month<10) {
			buffi.append("0");
		}
		buffi.append(month);
		buffi.append("-");
		if (day<10) {
			buffi.append("0");
		}
		buffi.append(day);
		buffi.append(" ");
		buffi.append(hour);
		buffi.append(":");
		buffi.append(minute);		
		
		return buffi.toString();
	}

}
