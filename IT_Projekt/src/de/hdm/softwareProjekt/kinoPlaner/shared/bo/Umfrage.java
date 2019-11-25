package de.hdm.softwareProjekt.kinoPlaner.shared.bo;

public class Umfrage extends BesitzerBusinessObjekt {
	private static final long serialVersionUID= 1L;
	
	
	private int gruppenId;
	private boolean isOpen;
	private boolean isVoted;
	
	public Umfrage() {
		super();
	}
	
/**
 * Auslesen der GruppenId
 */
	public int getGruppenId() {
		return gruppenId;
	}
	
/**
 * Setzen der GruppenId
 */
	public void setGruppenId(int gruppenId) {
		this.gruppenId = gruppenId;
	}
	public boolean isOpen() {
		return isOpen;
	}

/**
 * Setzen des open-Parameter
 * @param isOpen
 */
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	public boolean isVoted() {
		return isVoted;
	}
	
/**
 * Setzen des Votes
 * @param isVoted
 */
	public void setVoted(boolean isVoted) {
		this.isVoted = isVoted;
	}
	
/**
 * Auslesen der SerialVersionUid
 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
/**
 * Erzeugen einer einfachen textuellen Darstellung der jeweiligen Instanz
 * Dies kann selbstverständlich in Subklassen ueberschrieben werden
 */
	@Override
	public String toString() {
		return super.toString() + "Umfrage [gruppenId=" + gruppenId + ", isOpen=" + isOpen + ", isVoted=" + isVoted + "]";
	}
	
	

}
