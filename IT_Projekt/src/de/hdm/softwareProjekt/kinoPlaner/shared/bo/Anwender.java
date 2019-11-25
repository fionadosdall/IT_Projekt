package de.hdm.softwareProjekt.kinoPlaner.shared.bo;

public class Anwender extends BusinessObjekt {
	
	private static final long serialVersionUID= 1L;
	
	private String gmail;
	private boolean istEingeloggt;
	private String loginUrl;
	private String logoutUrl;
	
	
	public String getGmail() {
		return gmail;
	}
	
	public void setGmail(String gmail) {
		this.gmail = gmail;
	}
	
	public boolean isIstEingeloggt() {
		return istEingeloggt;
	}
	
	public void setIstEingeloggt(boolean istEingeloggt) {
		this.istEingeloggt = istEingeloggt;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return super.toString() + "Anwender [gmail=" + gmail + ", istEingeloggt=" + istEingeloggt + ", loginUrl=" + loginUrl
				+ ", logoutUrl=" + logoutUrl + "]";
	}
	
	
	

}
