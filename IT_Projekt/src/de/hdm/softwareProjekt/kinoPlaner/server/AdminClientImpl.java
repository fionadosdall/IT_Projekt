package de.hdm.softwareProjekt.kinoPlaner.server;

import java.sql.Timestamp;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.softwareProjekt.kinoPlaner.server.db.AnwenderMapper;
import de.hdm.softwareProjekt.kinoPlaner.server.db.AuswahlMapper;
import de.hdm.softwareProjekt.kinoPlaner.server.db.FilmMapper;
import de.hdm.softwareProjekt.kinoPlaner.server.db.GruppeMapper;
import de.hdm.softwareProjekt.kinoPlaner.server.db.KinoMapper;
import de.hdm.softwareProjekt.kinoPlaner.server.db.KinoketteMapper;
import de.hdm.softwareProjekt.kinoPlaner.server.db.SpielplanMapper;
import de.hdm.softwareProjekt.kinoPlaner.server.db.SpielzeitMapper;
import de.hdm.softwareProjekt.kinoPlaner.server.db.UmfrageMapper;
import de.hdm.softwareProjekt.kinoPlaner.server.db.UmfrageoptionMapper;
import de.hdm.softwareProjekt.kinoPlaner.server.db.VorstellungMapper;
import de.hdm.softwareProjekt.kinoPlaner.shared.AdminClient;
import de.hdm.softwareProjekt.kinoPlaner.shared.Kinoplaner;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;

public class AdminClientImpl extends RemoteServiceServlet implements AdminClient {

	private AnwenderMapper anwenderMapper = null;
	
	public AdminClientImpl() throws IllegalArgumentException {

	}
	
	@Override
	public void init() throws IllegalArgumentException {
		// Die Mapper werden Initiiert.
		this.anwenderMapper = AnwenderMapper.anwenderMapper();


	}
	
	@Override
	public Anwender erstellenAnwender(String name, String gmail) throws IllegalArgumentException {
		// Ein neues Anwender Objekt wird erstellt.
		Anwender a = new Anwender();

		// Die Attribute des Objekts werden mit Werten befüllt.
		a.setName(name);
		a.setGmail(gmail);
		a.setErstellDatum(new Timestamp(System.currentTimeMillis()));

		// Das Objekt wird in der Datenbank gespeichert und wiedergeben
		return this.anwenderMapper.insert(a);
	}

}
