package de.hdm.softwareProjekt.kinoPlaner.shared;

public class Umfrageoption extends BusinessObjekt {
	
	private static final long serialVersionUID= 1L;
	
	private int umfrageId;
	private int vorstellungsId;
	
	public int getUmfrageId() {
		return umfrageId;
	}
	public void setUmfrageId(int umfrageId) {
		this.umfrageId = umfrageId;
	}
	public int getVorstellungsId() {
		return vorstellungsId;
	}
	public void setVorstellungsId(int vorstellungsId) {
		this.vorstellungsId = vorstellungsId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
