package de.hdm.softwareProjekt.kinoPlaner.shared.bo;

import java.sql.Time;

public class Film extends BesitzerBusinessObjekt{
	
	private static final long serialVersionUID= 1L;
	
	private Time Filmlaenge;
	private String beschreibung;
	private String bewertung;
	
	public Film() {
		super();
	}
	
/**
 * Auslesen der Filmlaenge
 */
	public Time getFilmlaenge() {
		return Filmlaenge;
	}
	
/**
 * Setzen der filmlaenge
 */
	public void setFilmlaenge(Time filmlaenge) {
		Filmlaenge = filmlaenge;
	}

/**
 * Auslesen der Beschreibung
 
 */
	public String getBeschreibung() {
		return beschreibung;
	}

/**
 * Setzen der Beschreibung des Films
 * @param beschreibung
 */
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
	
/**
 * Auslesen der Bewertung
 */
	public String getBewertung() {
		return bewertung;
	}

/**
 * Setzen der Bewertung
 * @param bewertung
 */
	public void setBewertung(String bewertung) {
		this.bewertung = bewertung;
	}
	
/**
 * Auslesen der Serialversionuid
 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

/** 
 * Erzeugen einer einfachen textuellen Darstellung der jeweiligen Instanz
 * Dies kann selbstverst�ndlich in Subklassen ueberschrieben werden
 */
	@Override
	public String toString() {
		return super.toString() + "Film [Filmlaenge=" + Filmlaenge + ", beschreibung=" + beschreibung + ", bewertung=" + bewertung + "]";
	}
	
	

}
