package de.hdm.softwareProjekt.kinoPlaner.shared.bo;

import java.util.Date;

public class Spielzeit extends BesitzerBusinessObjekt {

	private static final long serialVersionUID = 1L;

	public Spielzeit() {
		super();
	}

	private Date zeit;
	private String dateToString;

	/**
	 * Auslesen der Zeit
	 * 
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

	public void setDatetoString(String dateString) {
		this.dateToString = dateString;
	}

	public String dateToString() {
		return dateToString;
	}

	/**
	 * Auslesen der Serialversionuid
	 * 
	 * @return
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Erzeugen einer einfachen textuellen Darstellung der jeweiligen Instanz Dies
	 * kann selbstverst�ndlich in Subklassen ueberschrieben werden
	 */

	@Override
	public String toString() {

		return zeit.toGMTString();
	}

}
