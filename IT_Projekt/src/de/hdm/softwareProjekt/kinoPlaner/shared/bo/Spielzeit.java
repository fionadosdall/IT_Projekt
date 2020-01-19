package de.hdm.softwareProjekt.kinoPlaner.shared.bo;

import java.util.Date;

import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.DateFormaterSpielzeit;


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

	public void setZeit(Date date) {
		this.zeit = date;
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
 * Dies kann selbstverst�ndlich in Subklassen ueberschrieben werden
 */

	@Override
	public String toString() {
		DateFormaterSpielzeit dfs = new DateFormaterSpielzeit(zeit);
		return dfs.toString();
	}
	
	

}
