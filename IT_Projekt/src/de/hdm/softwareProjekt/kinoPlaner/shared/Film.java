package de.hdm.softwareProjekt.kinoPlaner.shared;

import java.sql.Time;

public class Film extends BusinessObjekt{
	
	private static final long serialVersionUID= 1L;
	
	private Time Filmlaenge;
	private String beschreibung;
	private int bewertung;
	
	
	public Time getFilmlaenge() {
		return Filmlaenge;
	}
	public void setFilmlaenge(Time filmlaenge) {
		Filmlaenge = filmlaenge;
	}
	public String getBeschreibung() {
		return beschreibung;
	}
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
	public int getBewertung() {
		return bewertung;
	}
	public void setBewertung(int bewertung) {
		this.bewertung = bewertung;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
