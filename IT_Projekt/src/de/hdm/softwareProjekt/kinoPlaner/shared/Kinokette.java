package de.hdm.softwareProjekt.kinoPlaner.shared;

public class Kinokette extends BesitzerBusinessObjekt {
	
	private static final long serialVersionUID= 1L;
	
	private String sitz;
	private String website;
	
	
	public String getSitz() {
		return sitz;
	}
	public void setSitz(String sitz) {
		this.sitz = sitz;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
