package de.hdm.softwareProjekt.kinoPlaner.shared.bo;

import java.io.Serializable;
import java.sql.Timestamp;

/** 
 * <P>
 * Die Klasse <code>BusinessObjekt</code> stellt die Basisklassen aller in diesem Projekt fuer
 * die Umsetzung des Fachkonzepts releanten Klassen da 
 * </p>
 * <p>
 * Zentrales Merkmal ist, dass jedes <code>BusinessObjekt</code> eine Nummer
 * besitzt, die man in einer relationalen Datenbnak auch als Primaerschlüssle
 * bezeichnen wuerde. Ferner ist jedes <code>BusinessObjekt</code> als
 * {@link Serializable} gekennzeichnet. Durh diese eigentschaft kann jedes
 *  <code>BusinessObjekt</code> automatisch in eine textuell Form uberfuehrt werden
 *  z.B. zwischen Client und Server transportiert werden.
 *   Bei GWT RPC ist diese textuelle Notation in JSON
 *  </p>
 * @author fiona
 *
 */

public abstract class BusinessObjekt implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
/**
 *  Die eindeutige Identifikationsnummer einer Instanz dieser Klasse
 *  Das erstell Datum der Instanz
 *  Und der Name einer Instanz dieser Klasse
 */
	
	private int id;
	private Timestamp erstellDatum;
	private String name;
	
/**
 *  Auslesen der Id
 
 */
	
	
	public int getId() {
		return id;
	}

/** 
 * Setzen der Id
 */
	
	public void setId(int id) {
		this.id = id;
	}

/**
 * Auslesen des erstell Datums
 * @return
 */
	
	public Timestamp getErstellDatum() {
		return erstellDatum;
	}
/**
 * Setzen des erstellDatum
 * @param erstellDatum
 */
	
	public void setErstellDatum(Timestamp erstellDatum) {
		this.erstellDatum = erstellDatum;
	}

/**
 * Auslesen des Namens des BO
 * @return
 */
	
	public String getName() {
		return name;
	}

/**
 * Setzen des Namens
 * @param name
 */
	
	public void setName(String name) {
		this.name = name;
	}

/**
 * Auslesen der Serialversionuid
 * @return
 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean equals (Object obj) {
		if (obj != null && obj instanceof BusinessObjekt) {
			BusinessObjekt bo = (BusinessObjekt) obj;
			
			try {
				if (bo.getId() == this.id) {
					return true;
					
				}
			} catch (IllegalArgumentException e) {
				
				return false;
			}
		}
		
		return false;
	}
/**
 * Erzeugen einer texutellen Darstellung der jeweiligen Instanz
 * Kann in Subklassen überschrieben werden
 */

	@Override
	public String toString() {
		return "BusinessObjekt [id=" + id + ", erstellDatum=" + erstellDatum + ", name=" + name + "]";
	}
	
	
}