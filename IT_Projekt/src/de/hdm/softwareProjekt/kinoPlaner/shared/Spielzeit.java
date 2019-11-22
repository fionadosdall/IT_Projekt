package de.hdm.softwareProjekt.kinoPlaner.shared;

import java.util.Calendar;

public class Spielzeit extends BusinessObjekt{
	
	private static final long serialVersionUID= 1L;
	
	private Calendar zeit;

	public Calendar getZeit() {
		return zeit;
	}

	public void setZeit(Calendar zeit) {
		this.zeit = zeit;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
