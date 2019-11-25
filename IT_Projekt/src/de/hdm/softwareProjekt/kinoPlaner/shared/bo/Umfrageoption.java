package de.hdm.softwareProjekt.kinoPlaner.shared.bo;

public class Umfrageoption extends BusinessObjekt {
	
	private static final long serialVersionUID= 1L;
	
	private int umfrageId;
	private int vorstellungsId;
	private int voteErgebnis;
	
	public Umfrageoption() {
		super();
	}
/**
 * Auslesen des VoteErgebnis
 */
	public int getVoteErgebnis() {
		return voteErgebnis;
	}
	
/**
 * Setzen des VoteErgebnis
 */
	public void setVoteErgebnis(int voteErgebnis) {
		this.voteErgebnis = voteErgebnis;
	}
	
/**
 *Auslesen der UmfrageId
 */
	public int getUmfrageId() {
		return umfrageId;
	}
	
/**
 * Setzen der UmfrageId
 */
	public void setUmfrageId(int umfrageId) {
		this.umfrageId = umfrageId;
	}
	
/**
 * Auslesen der VorstellungsId
 */
	public int getVorstellungsId() {
		return vorstellungsId;
	}

/**
 * Setzen der VorstellungsId
 */
	public void setVorstellungsId(int vorstellungsId) {
		this.vorstellungsId = vorstellungsId;
	}
	
/**
 * Setzen der Serialversionuid
 * @return
 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
/**
 * Erzeugen einer einfachen textuellen Darstellung der jeweiligen Instanz
 *
 * Dies kann selbstverständlich in Subklassen ueberschrieben werden
*/
	@Override
	public String toString() {
		return super.toString() +"Umfrageoption [umfrageId=" + umfrageId + ", vorstellungsId=" + vorstellungsId + ", voteErgebnis="
				+ voteErgebnis + "]";
	}
	
	
	

}
