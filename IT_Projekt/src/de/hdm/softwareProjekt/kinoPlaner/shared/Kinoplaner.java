package de.hdm.softwareProjekt.kinoPlaner.shared;

import java.util.ArrayList;
import java.util.Calendar;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.softwareProjekt.kinoPlaner.server.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.server.Kino;
import de.hdm.softwareProjekt.kinoPlaner.server.Spielplan;
import de.hdm.softwareProjekt.kinoPlaner.server.Umfrageoption;
import de.hdm.softwareProjekt.kinoPlaner.server.Vorstellung;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("PathEinfügen")
public interface Kinoplaner extends RemoteService {
	
	public void init() throws IllegalArgumentException;
	
	public Anwender getAnwender()  throws IllegalArgumentException;
	
	public void setAnwender(Anwender anwender)  throws IllegalArgumentException;
	
	public void erstellenAnwender(int id, String name, String gmail) throws IllegalArgumentException;
	
	public void erstellenGruppe(int id, String name, int besitzerId) throws IllegalArgumentException;
	
	public void erstellenKino(int id, String name, int besitzerId, int plz, String stadt, String strassse, String hausnummer, int kinokettenId) throws IllegalArgumentException;
	
	public void erstellenKino(int id, String name, int besitzerId, int plz, String stadt, String strassse, String hausnummer) throws IllegalArgumentException;
	
	public void erstellenKinokette(int id, String name, int besitzerId,  String sitz, String website) throws IllegalArgumentException;
	
	public void erstellenSpielplan(int id, String name, int besitzerId,  int kinoId) throws IllegalArgumentException;
	
	public void erstellenVorstellung(int id, String name, int spielplanId, int spielzeitId, int filmId) throws IllegalArgumentException;
	
	public void erstellenUmfrage(int id, String name, int besitzerId, int gruppenId) throws IllegalArgumentException;
	
	public void erstellenUmfrageoption(int id, String name, int umfrageId, int vorstellungId) throws IllegalArgumentException;
	
	public void erstellenFilm(int id, String name, int besitzerId, String beschreibung, int bewertung) throws IllegalArgumentException;
	
	public void erstellenSpielzeit(int id, String name, int besitzerId, Calendar zeit) throws IllegalArgumentException;
	
	public void erstellenAuswahl(int id, String name, int besitzerId, int voting, int umfrageoptionId) throws IllegalArgumentException;
	
	public void speichern(Anwender anwender) throws IllegalArgumentException;
	
	public void speichern(Gruppe gruppe) throws IllegalArgumentException;
	
	public void speichern(Kino kino) throws IllegalArgumentException;
	
	public void speichern(Kinokette kinokette) throws IllegalArgumentException;
	
	public void speichern(Spielplan spielplan) throws IllegalArgumentException;
	
	public void speichern(Vorstellung vorstellung) throws IllegalArgumentException;
	
	public void speichern(Umfrage umfrage) throws IllegalArgumentException;
	
	public void speichern(Umfrageoption umfrageoption) throws IllegalArgumentException;
	
	public void speichern(Film film) throws IllegalArgumentException;
	
	public void speichern(Spielzeit spielzeit) throws IllegalArgumentException;
	
	public void speichern(Auswahl auswahl) throws IllegalArgumentException;
	
	public void löschen(Anwender anwender) throws IllegalArgumentException;

	public void löschen(Gruppe gruppe) throws IllegalArgumentException;
	
	public void löschen(Kino kino) throws IllegalArgumentException;
	
	public void löschen(Kinokette kinokette) throws IllegalArgumentException;
	
	public void löschen(Spielplan spielplan) throws IllegalArgumentException;
	
	public void löschen(Vorstellung vorstellung) throws IllegalArgumentException;
	
	public void löschen(Umfrage umfrage) throws IllegalArgumentException;
	
	public void löschen(Umfrageoption umfrageoption) throws IllegalArgumentException;
	
	public void löschen(Film film) throws IllegalArgumentException;
	
	public void löschen(Spielzeit spielzeit) throws IllegalArgumentException;
	
	public void löschen(Auswahl auswahl) throws IllegalArgumentException;
	
	public Anwender getAnwenderById(int anwenderId) throws IllegalArgumentException;
	
	public ArrayList<Gruppe> getGruppenByAnwender(Anwender anwender) throws IllegalArgumentException;
	
	public ArrayList<Gruppe> getGruppenByAnwenderOwner(Anwender anwender) throws IllegalArgumentException;
	
	public ArrayList<Umfrage> getUmfragenByAnwender(Anwender anwender) throws IllegalArgumentException;
	
	public ArrayList<Umfrage> getUmfragenByAnwenderOwner(Anwender anwender) throws IllegalArgumentException;
	
	public ArrayList<Umfrage> getClosedUmfragenByAnwender(Anwender anwender) throws IllegalArgumentException;
	
	public ArrayList<Kinokette> getKinokettenByAnwenderOwner(Anwender anwender) throws IllegalArgumentException;
	
	public ArrayList<Kino> getKinosByAnwenderOwner(Anwender anwender) throws IllegalArgumentException;
	
	public ArrayList<Kino> getKinosByKinokette(Kinokette kinokette) throws IllegalArgumentException;
	
	public ArrayList<Spielplan> getSpielplaeneByAnwenderOwner(Anwender anwender) throws IllegalArgumentException;
	
	public ArrayList<Spielplan> getSpielplaeneByKino(Kino kino) throws IllegalArgumentException;
	
	public ArrayList<Film> getFilmeByAnwenderOwner(Anwender anwender) throws IllegalArgumentException;
	
	public ArrayList<Spielzeit> getSpielzeitenByAnwenderOwner(Anwender anwender) throws IllegalArgumentException;
	
	public ArrayList<Auswahl> getAuswahlenByAnwenderOwner(Anwender anwender) throws IllegalArgumentException;
	
	public ArrayList<Vorstellung> getVorstellungenBySpielplan(Spielplan spielplan) throws IllegalArgumentException;
	
	public Anwender getAnwenderByName(String name) throws IllegalArgumentException;
	
	public ArrayList<Umfrageoption> getUmfrageoptionenByUmfrage(Umfrage umfrage) throws IllegalArgumentException;
	
	public ArrayList<Umfrageoption> getUmfrageoptionenByVorstellung(Vorstellung vorstellung) throws IllegalArgumentException;
	
	public ArrayList<Anwender> getGruppenmitgliederByGruppe(Gruppe gruppe) throws IllegalArgumentException;
	
	public ArrayList<Umfrage> getUmfragenByGruppe(Gruppe gruppe) throws IllegalArgumentException;
	
	public ArrayList<Vorstellung> getAllVorstellungen() throws IllegalArgumentException;
	
	public ArrayList<Kinokette> getAllKinoketten() throws IllegalArgumentException;
	
	public ArrayList<Kino> getAllKinos() throws IllegalArgumentException;
	
	public ArrayList<Film> getAllFilme() throws IllegalArgumentException;
	
	public ArrayList<Spielzeiten> getAllSpielzeiten() throws IllegalArgumentException;
	
	public ArrayList<Vorstellung> filterResultVorstellungenByKinoOrKinokette(ArrayList<Vorstellung> resultSet, Kino kino) throws IllegalArgumentException;
	
	public ArrayList<Vorstellung> filterResultVorstellungenByKinoOrKinokette(ArrayList<Vorstellung> resultSet, Kinokette kino) throws IllegalArgumentException;
	
	public ArrayList<Vorstellung> filterResultVorstellungenByFilm(ArrayList<Vorstellung> resultSet, Film film) throws IllegalArgumentException;
	
	public ArrayList<Vorstellung> filterResultVorstellungenBySpielzeit(ArrayList<Vorstellung> resultSet, Spielzeit spielzeit) throws IllegalArgumentException;
	
	public void setSpielplanForKinosByKinokette(Spielplan spielplan, Kino kino) throws IllegalArgumentException;
	
	public ArrayList<Auswahl> getAuswahlenByUmfrageoption(Umfrageoption umfrageoption) throws IllegalArgumentException;
	
	public Auswahl getAuswahlByAnwenderAndUmfrageoption(Anwender anwender, Umfrageoption umfrageoption) throws IllegalArgumentException;
	
	public int berechneAuswahlenByUmfrageoption(Umfrageoption umfrageoption);
	
	public Umfrage stichwahlStarten(Umfrage umfrage);
	
	public boolean ergebnisOderStichwahl(Umfrage umfrage);
	
	
}
