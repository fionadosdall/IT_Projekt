package de.hdm.softwareProjekt.kinoPlaner.shared.bo;

public class Kinokette extends BesitzerBusinessObjekt {
	
	private static final long serialVersionUID= 1L;
	
	private String sitz;
	private String website;
	
	public Kinokette() {
		super();
	}
	
	
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
	@Override
	public String toString() {
		return super.toString() + "Kinokette [sitz=" + sitz + ", website=" + website + "]";
	}
	
	
	

}
