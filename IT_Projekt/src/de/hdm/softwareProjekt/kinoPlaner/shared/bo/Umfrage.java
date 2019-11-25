package de.hdm.softwareProjekt.kinoPlaner.shared.bo;

public class Umfrage extends BesitzerBusinessObjekt {
	private static final long serialVersionUID= 1L;
	
	
	private int gruppenId;
	private boolean isOpen;
	private boolean isVoted;
	
	public Umfrage() {
		super();
	}
	
	public int getGruppenId() {
		return gruppenId;
	}
	public void setGruppenId(int gruppenId) {
		this.gruppenId = gruppenId;
	}
	public boolean isOpen() {
		return isOpen;
	}
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	public boolean isVoted() {
		return isVoted;
	}
	public void setVoted(boolean isVoted) {
		this.isVoted = isVoted;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return super.toString() + "Umfrage [gruppenId=" + gruppenId + ", isOpen=" + isOpen + ", isVoted=" + isVoted + "]";
	}
	
	

}
