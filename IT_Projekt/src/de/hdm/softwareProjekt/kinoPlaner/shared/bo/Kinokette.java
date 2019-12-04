package de.hdm.softwareProjekt.kinoPlaner.shared.bo;

public class Kinokette extends BesitzerBusinessObjekt {
	
	private static final long serialVersionUID= 1L;
	
	private String sitz;
	private String website;
	private int KinokettenId;
	private boolean KinokettenSpielplan;
	
	

	public Kinokette() {
		super();
	}
	
/**
 * Auslesen des Sitzes des Kinos
 * @return
 */
	public String getSitz() {
		return sitz;
	}

/**
 * Setzen des Sitzes des Kinos
 * @param sitz
 */
	public void setSitz(String sitz) {
		this.sitz = sitz;
	}
	
/**
 * Auslesen der Website des Kinos
 * @return
 */
	public String getWebsite() {
		return website;
	}

/**
 * Setzen der Website des Kinos
 */
	public void setWebsite(String website) {
		this.website = website;
	}
	
/**
 * Auslesen der KinokettenId
 * @return
 */
	public int getKinokettenId() {
		return KinokettenId;
	}

	/** 
	 * Setzen der Id der Kinokette
	 * @param kinokttenId
	 */
	
	public void setKinokettenId(int kinokettenId) {
		KinokettenId = kinokettenId;
	}

/**
 * Überprüfen ob Spielplan für eine gesamte Kinokette angelegt werden soll,
 * oder nur für ein einzelnes Kino
 * @return
 */
	public boolean isKinokettenSpielplan() {
		return KinokettenSpielplan;
	}

/*
 * wird nur benötigt wenn ein Spielplan für eine gesamte Kinokette
* anlegen will. 
*/
	public void setKinokettenSpielplan(boolean kinokettenSpielplan) {
		KinokettenSpielplan = kinokettenSpielplan;
	}

/**
 * Auslesen der SerialversionUid
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
		return super.toString() + "Kinokette [sitz=" + sitz + ", website=" + website + "]";
	}
	
	
	

}
