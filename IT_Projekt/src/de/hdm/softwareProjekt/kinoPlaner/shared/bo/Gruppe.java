package de.hdm.softwareProjekt.kinoPlaner.shared.bo;

public class Gruppe extends BesitzerBusinessObjekt {
	
	
	private static final long serialVersionUID= 1L;
	
	private int gruppenId;


	/*
	 * 
	 */
	
	public int getGruppenId() {
		return gruppenId;
	}
	
/*
 * Setzen der GruppenId
 */

	public void setGruppenId(int gruppenId) {
		this.gruppenId = gruppenId;
	}

	public Gruppe() {
		super();
	}

/**
 * Auslesen der Serialversionuid
 * @return
 */

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
