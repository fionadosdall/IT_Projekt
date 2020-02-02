package de.hdm.softwareProjekt.kinoPlaner.shared.bo;

/**
 * Realisierung eines exemplarischen Anwenders. Ein Anwender besitzt eine gmail,
 * einen loginUrl als auch einen logoutUrl, und eine Methode in welcher gepr�ft
 * wird ob er eingeloogt ist (istEingeloggt)
 * 
 * @author fiona
 *
 */

public class Anwender extends BusinessObjekt {

	private static final long serialVersionUID = 1L;

	private String gmail;
	private boolean istEingeloggt;
	private String loginUrl;
	private String logoutUrl;

	public Anwender() {
		super();
	}

	/**
	 * Auslesen der Gmail Adresse
	 * 
	 * @return
	 */
	public String getGmail() {
		return gmail;
	}

	/**
	 * Setzen der Gmail
	 * 
	 * @param gmail
	 */

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}

	public boolean isIstEingeloggt() {
		return istEingeloggt;
	}

	/**
	 * Setzen des Einlogg-Satutses
	 */

	public void setIstEingeloggt(boolean istEingeloggt) {
		this.istEingeloggt = istEingeloggt;
	}

	/**
	 * Auslesen des LoginUrl
	 */
	public String getLoginUrl() {
		return loginUrl;
	}

	/**
	 * Setzen des LoginUrl
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	/**
	 * Auslesen des LogoutUrl
	 */
	public String getLogoutUrl() {
		return logoutUrl;
	}

	/**
	 * Setzen des LogoutUrl
	 */
	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
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
	 * Erzeugen einer einfachen textuellen Darstellung der jeweiligen Instanzen
	 * Diese besteht aus dem Text, der durch die <code>toString()</code>-Methode der
	 * Superklasse erzeugt wird, erg�nzt durch die gMail des Anwenders, seinen
	 * einloggStatuses, den LoginUrl und Logout Url
	 */

	@Override
	public String toString() {
		return super.toString() + "Anwender [gmail=" + gmail + ", istEingeloggt=" + istEingeloggt + ", loginUrl="
				+ loginUrl + ", logoutUrl=" + logoutUrl + "]";
	}

}
