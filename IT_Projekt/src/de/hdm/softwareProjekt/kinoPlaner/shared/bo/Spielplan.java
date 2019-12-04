package de.hdm.softwareProjekt.kinoPlaner.shared.bo;

public class Spielplan extends BesitzerBusinessObjekt {

	private static final long serialVersionUID = 1L;

	private int kinoId;
	
	private int kinokettenId;

	private boolean kinokettenSpielplan;

	public Spielplan() {
		super();
	}

	/**
	 * Auslesen der KinoId
	 */
	public int getKinoId() {
		return kinoId;
	}

	/**
	 * Setzen der KinoId
	 */
	public void setKinoId(int kinoId) {
		this.kinoId = kinoId;
	}

	/**
	 * Auslesen der KinokettenId
	 */
	public int getKinokettenId() {
		return kinokettenId;
	}

	/**
	 * Setzen der KinokettenId
	 */
	public void setKinokettenId(int kinokettenId) {
		this.kinokettenId = kinokettenId;
	}

	/**
	 * R�ckgabe ob es sich um einen Spielplan f�r ein einzelnes Kino (false) oder
	 * eine ganze Kinokette (true) handelt.
	 */
	public boolean isKinokettenSpielplan() {
		return kinokettenSpielplan;
	}

	/**
	 * Setzen ob es sich um einen Spielplan f�r ein einzelnes Kino (false) oder eine
	 * ganze Kinokette (true) handelt.
	 * 
	 * @param kinokettenSpielplan
	 *            Einzelnes Kino (false) oder eine ganze Kinokette (true)
	 */
	public void setKinokettenSpielplan(boolean kinokettenSpielplan) {
		this.kinokettenSpielplan = kinokettenSpielplan;
	}

	/**
	 * Auslesen der SerialversionuId
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
		if(kinokettenSpielplan==false) {
		return super.toString() + "Spielplan [KinoId=" + kinoId + "]";
		}else {
			return super.toString() + "Spielplan [KinokettenId=" + kinokettenId + "]";
		}
	}

}
