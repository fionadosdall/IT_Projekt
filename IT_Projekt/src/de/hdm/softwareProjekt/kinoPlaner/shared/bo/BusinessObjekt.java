package de.hdm.softwareProjekt.kinoPlaner.shared.bo;

import java.io.Serializable;
import java.sql.Timestamp;

public abstract class BusinessObjekt implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
/**
 * * Die eindeutige Identifikationsnummer einer Instanz dieser Klasse
 */
	
	private int id;
	private Timestamp erstellDatum;
	private String name;
	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Timestamp getErstellDatum() {
		return erstellDatum;
	}
	
	public void setErstellDatum(Timestamp erstellDatum) {
		this.erstellDatum = erstellDatum;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	public String toString() {
		return this.getClass().getName()+"#" + this.getId();
	}
}