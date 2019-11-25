package de.hdm.softwareProjekt.kinoPlaner.shared.bo;

public class BesitzerBusinessObjekt extends BusinessObjekt{
	
	
	private static final long serialVersionUID= 1L;
	
	private int besitzerId;

	public int getBesitzerId() {
		return besitzerId;
	}

	public void setBesitzerId(int besitzerId) {
		this.besitzerId = besitzerId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return super.toString() + "BesitzerBusinessObjekt [besitzerId=" + besitzerId + "]";
	}
	
	
	

}
