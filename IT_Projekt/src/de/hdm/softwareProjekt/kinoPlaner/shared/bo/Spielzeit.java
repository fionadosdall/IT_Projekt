package de.hdm.softwareProjekt.kinoPlaner.shared.bo;

import java.util.Calendar;

public class Spielzeit extends BesitzerBusinessObjekt{
	
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

	@Override
	public String toString() {
		return "Spielzeit [zeit=" + zeit + "]";
	}
	
	

}
