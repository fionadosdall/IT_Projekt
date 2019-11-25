package de.hdm.softwareProjekt.kinoPlaner.shared.bo;

import java.sql.Time;

public class Film extends BesitzerBusinessObjekt{
	
	private static final long serialVersionUID= 1L;
	
	private Time Filmlaenge;
	private String beschreibung;
	private int bewertung;
	
	public Film() {
		super();
	}
	
	
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
	@Override
	public String toString() {
		return super.toString() + "Film [Filmlaenge=" + Filmlaenge + ", beschreibung=" + beschreibung + ", bewertung=" + bewertung + "]";
	}
	
	

}
