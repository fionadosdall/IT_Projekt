package de.hdm.softwareProjekt.kinoPlaner.shared.bo;

/**
 * 
 * @author fiona
 *
 */

public class BesitzerBusinessObjekt extends BusinessObjekt{
	
	
	private static final long serialVersionUID= 1L;
	
	private int besitzerId;
	
	public BesitzerBusinessObjekt() {
		super();
	}
/**
 * Auslesen der BesitzerId
 * @return
 */

	public int getBesitzerId() {
		return besitzerId;
	}
/**
 * Setzen der BesitzerId
 * @param besitzerId
 */

	public void setBesitzerId(int besitzerId) {
		this.besitzerId = besitzerId;
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
		return super.toString() + "BesitzerBusinessObjekt [besitzerId=" + besitzerId + "]";
	}
	
	
	

}
