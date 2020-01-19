package de.hdm.softwareProjekt.kinoPlaner.shared.bo;

import java.sql.Date;


public class Spielzeit extends BesitzerBusinessObjekt{
	
	private static final long serialVersionUID= 1L;
	
	public Spielzeit() {
		super();
	}
	
	private Date zeit;

/**
 * Auslesen der Zeit
 * @return
 */
	public Date getZeit() {
		return zeit;
	}

/**
 * Setzen der Zeit
 */

	public void setZeit(Date zeit) {
		this.zeit = zeit;
	}

/**
 * Auslesen der Serialversionuid
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
		return "Spielzeit [zeit=" + zeit + "]";
	}
	
	

}
