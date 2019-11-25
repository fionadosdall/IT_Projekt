package de.hdm.softwareProjekt.kinoPlaner.shared.bo;

public class Vorstellung extends BusinessObjekt{
	
	private static final long serialVersionUID= 1L;
	
	private int filmId;
	private int spielzeitId;
	private int spielplanId;
	
	public Vorstellung () {
		super();
	}
	
/**
 * Auslesen der FilmId
 */
	
	public int getFilmId() {
		return filmId;
	}
/*
 * Setzen der FilmId
 */
	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}
	
/**
 * Auslesen der SpielzeitId
 */
	public int getSpielzeitId() {
		return spielzeitId;
	}
	
/**
 * Setzen der SpielzeitId
 */
	public void setSpielzeitId(int spielzeitId) {
		this.spielzeitId = spielzeitId;
	}

/**
 * Auslesen der SpielplanId
*/
	public int getSpielplanId() {
		return spielplanId;
	}
	
/**
 * setzen der SpielplanId
 */
	public void setSpielplanId(int spielplanId) {
		this.spielplanId = spielplanId;
	}
	
/**
 * Auslesen der SerialversionUid
 * @return
 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
/**
 * Erzeugen einer einfachen textuellen Darstellung der jeweiligen Instanz
 * Dies kann selbstverständlich in Subklassen ueberschrieben werden
 */
	@Override
	public String toString() {
		return super.toString() + "Vorstellung [filmId=" + filmId + ", spielzeitId=" + spielzeitId + ", spielplanId=" + spielplanId + "]";
	}
	
	

}
