package de.hdm.softwareProjekt.kinoPlaner.shared;

public class Spielplan extends BesitzerBusinessObjekt {
	
	private static final long serialVersionUID= 1L;
	
	private int KinoId;
	

	public int getKinoId() {
		return KinoId;
	}

	public void setKinoId(int kinoId) {
		KinoId = kinoId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
