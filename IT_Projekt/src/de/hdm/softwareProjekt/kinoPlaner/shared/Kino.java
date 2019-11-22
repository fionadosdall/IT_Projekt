package de.hdm.softwareProjekt.kinoPlaner.shared;

public class Kino extends BesitzerBusinessObjekt{
	
	private static final long serialVersionUID= 1L;
	
	private int plz;
	private String stadt;
	
	
	public int getPlz() {
		return plz;
	}
	public void setPlz(int plz) {
		this.plz = plz;
	}
	public String getStadt() {
		return stadt;
	}
	public void setStadt(String stadt) {
		this.stadt = stadt;
	}
	public String getStrasse() {
		return strasse;
	}
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}
	public int getHausnummer() {
		return hausnummer;
	}
	public void setHausnummer(int hausnummer) {
		this.hausnummer = hausnummer;
	}
	public int getKinokettenId() {
		return kinokettenId;
	}
	public void setKinokettenId(int kinokettenId) {
		this.kinokettenId = kinokettenId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private String strasse;
	private int hausnummer;
	private int kinokettenId;
	

}
