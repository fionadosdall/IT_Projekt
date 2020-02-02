package de.hdm.softwareProjekt.kinoPlaner.shared.bo;

public class Auswahl extends BesitzerBusinessObjekt {

	private static final long serialVersionUID = 1L;

	private int voting;
	private int umfrageoptionId;

	public Auswahl() {
		super();
	}

	/**
	 * Auslesen des Voting
	 * 
	 * @return
	 */
	public int getVoting() {
		return voting;
	}

	/**
	 * Setzen des Voting
	 * 
	 * @param voting
	 */
	public void setVoting(int voting) {
		this.voting = voting;
	}

	/**
	 * Auslesen der UmfrageoptionId
	 * 
	 * @return
	 */
	public int getUmfrageoptionId() {
		return umfrageoptionId;
	}

	/**
	 * Setzen der UmfrageoptionId
	 * 
	 * @param umfrageoptionId
	 */
	public void setUmfrageoptionId(int umfrageoptionId) {
		this.umfrageoptionId = umfrageoptionId;
	}

	/**
	 * Aulsesen der Serialversionuid
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
		return super.toString() + "Auswahl [voting=" + voting + ", umfrageoptionId=" + umfrageoptionId + "]";
	}

}
