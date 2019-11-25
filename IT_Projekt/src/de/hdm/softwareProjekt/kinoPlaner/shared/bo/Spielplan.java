package de.hdm.softwareProjekt.kinoPlaner.shared.bo;

public class Spielplan extends BesitzerBusinessObjekt {
	
	private static final long serialVersionUID= 1L;
	
	private int KinoId;
	
	public Spielplan() {
		super();
	}
	

	public int getKinoId() {
		return KinoId;
	}

	public void setKinoId(int kinoId) {
		KinoId = kinoId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return super.toString() + "Spielplan [KinoId=" + KinoId + "]";
	}
	
	
	

}
