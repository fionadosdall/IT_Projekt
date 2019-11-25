package de.hdm.softwareProjekt.kinoPlaner.shared.bo;

public class Umfrageoption extends BusinessObjekt {
	
	private static final long serialVersionUID= 1L;
	
	private int umfrageId;
	private int vorstellungsId;
	private int voteErgebnis;
	
	

	public int getVoteErgebnis() {
		return voteErgebnis;
	}
	public void setVoteErgebnis(int voteErgebnis) {
		this.voteErgebnis = voteErgebnis;
	}
	public int getUmfrageId() {
		return umfrageId;
	}
	public void setUmfrageId(int umfrageId) {
		this.umfrageId = umfrageId;
	}
	public int getVorstellungsId() {
		return vorstellungsId;
	}
	public void setVorstellungsId(int vorstellungsId) {
		this.vorstellungsId = vorstellungsId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return super.toString() +"Umfrageoption [umfrageId=" + umfrageId + ", vorstellungsId=" + vorstellungsId + ", voteErgebnis="
				+ voteErgebnis + "]";
	}
	
	
	

}
