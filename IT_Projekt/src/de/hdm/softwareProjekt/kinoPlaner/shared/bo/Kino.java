package de.hdm.softwareProjekt.kinoPlaner.shared.bo;

public class Kino extends BesitzerBusinessObjekt {

	private static final long serialVersionUID = 1L;

	private int plz;
	private String stadt;
	private String strasse;
	private String hausnummer;
	private int kinokettenId;

	public Kino() {
		super();
	}

	/**
	 * Auslesen der PLZ des Kinos
	 * 
	 * @return
	 */
	public int getPlz() {
		return plz;
	}

	/**
	 * Setzen der PLZ des Kinos
	 * 
	 * @param plz
	 */
	public void setPlz(int plz) {
		this.plz = plz;
	}

	/**
	 * Auslesen der Stadt des Kinos
	 * 
	 * @return
	 */
	public String getStadt() {
		return stadt;
	}

	/**
	 * Setzen der Stadt des Kinos
	 * 
	 * @param stadt
	 */
	public void setStadt(String stadt) {
		this.stadt = stadt;
	}

	/**
	 * Auslesen der Strasse des Kinos
	 * 
	 * @return
	 */
	public String getStrasse() {
		return strasse;
	}

	/**
	 * Setzen der Strasse des Kinos
	 * 
	 * @param strasse
	 */
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	/**
	 * Auslesen de Hausnummer
	 * 
	 * @return
	 */
	public String getHausnummer() {
		return hausnummer;
	}

	/**
	 * Setzen der Hausnummer
	 * 
	 * @param hausnummer
	 */
	public void setHausnummer(String hausnummer) {
		this.hausnummer = hausnummer;
	}

	/**
	 * Auslesen der KinokettenId
	 * 
	 * @return
	 */
	public int getKinokettenId() {
		return kinokettenId;
	}

	/**
	 * Setzen der KinokettenId
	 * 
	 * @param kinokettenId
	 */
	public void setKinokettenId(int kinokettenId) {
		this.kinokettenId = kinokettenId;
	}

	/**
	 * Setzen der Serialversionuid
	 * 
	 * @return
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Erzeugen einer einfachen textuellen Darstellung der jeweiligen Instanz Dies
	 * kann selbstverst�ndlich in Subklassen ueberschrieben werden
	 */
	@Override
	public String toString() {
		return super.toString() + "Kino [plz=" + plz + ", stadt=" + stadt + ", strasse=" + strasse + ", hausnummer="
				+ hausnummer + ", kinokettenId=" + kinokettenId + "]";
	}

}
