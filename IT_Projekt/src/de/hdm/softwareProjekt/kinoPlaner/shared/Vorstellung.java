package de.hdm.softwareProjekt.kinoPlaner.shared;

public class Vorstellung extends BusinessObjekt{
	
	private static final long serialVersionUID= 1L;
	
	private int filmId;
	private int spielzeitId;
	private int spielplanId;
	
	public int getFilmId() {
		return filmId;
	}
	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}
	public int getSpielzeitId() {
		return spielzeitId;
	}
	public void setSpielzeitId(int spielzeitId) {
		this.spielzeitId = spielzeitId;
	}
	public int getSpielplanId() {
		return spielplanId;
	}
	public void setSpielplanId(int spielplanId) {
		this.spielplanId = spielplanId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
