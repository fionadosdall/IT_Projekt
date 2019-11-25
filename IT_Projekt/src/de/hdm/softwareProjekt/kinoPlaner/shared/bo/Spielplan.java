package de.hdm.softwareProjekt.kinoPlaner.shared.bo;

public class Spielplan extends BesitzerBusinessObjekt {
	
	private static final long serialVersionUID= 1L;
	
	private int KinoId;
	
	public Spielplan() {
		super();
	}
	
/**
 * Auslesen der KinoId
 */
	public int getKinoId() {
		return KinoId;
	}
/**
 * Setzen der KinoId
 */
	public void setKinoId(int kinoId) {
		KinoId = kinoId;
	}

/**
 * Auslesen der SerialversionuId
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
		return super.toString() + "Spielplan [KinoId=" + KinoId + "]";
	}
	
	
	

}
