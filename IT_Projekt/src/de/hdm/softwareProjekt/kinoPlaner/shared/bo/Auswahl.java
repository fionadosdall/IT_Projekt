package de.hdm.softwareProjekt.kinoPlaner.shared.bo;

public class Auswahl extends BesitzerBusinessObjekt{
	
	private static final long serialVersionUID= 1L;
	
	private int voting;
	private int umfrageoptionId;
	
	
	public int getVoting() {
		return voting;
	}
	public void setVoting(int voting) {
		this.voting = voting;
	}
	public int getUmfrageoptionId() {
		return umfrageoptionId;
	}
	public void setUmfrageoptionId(int umfrageoptionId) {
		this.umfrageoptionId = umfrageoptionId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
