package de.hdm.softwareProjekt.kinoPlaner.shared;

import java.io.Serializable;

public class LoginInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
/**
 * Ein Objekt dieser Klasse wird erzeugt und mit den entsprechenden Attributen
 * versehen sobald der Login erfolgreich war.
 */
	
	
	private boolean isteingeloggt = false;
	private String loginUrl;
	private String logoutUrl;
	
	
	private String gmail;
	private int gruppenId;
	
	public LoginInfo() {
		
	}
	
	
	public boolean isIsteingeloggt() {
		return isteingeloggt;
	}

	public void setIsteingeloggt(boolean isteingeloggt) {
		this.isteingeloggt = isteingeloggt;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	public String getGmail() {
		return gmail;
	}

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}

	public int getGruppenId() {
		return gruppenId;
	}

	public void setGruppenId(int gruppenId) {
		this.gruppenId = gruppenId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	

}
