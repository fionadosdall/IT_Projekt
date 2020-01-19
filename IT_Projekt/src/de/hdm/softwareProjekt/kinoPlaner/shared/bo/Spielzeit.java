package de.hdm.softwareProjekt.kinoPlaner.shared.bo;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.shared.DefaultDateTimeFormatInfo;

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

		return zeit.toString();
	}
	
	

}
