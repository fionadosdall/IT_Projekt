package de.hdm.softwareProjekt.kinoPlaner.shared;

import java.util.ArrayList;
import java.util.Calendar;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.softwareProjekt.kinoPlaner.server.Kino;
import de.hdm.softwareProjekt.kinoPlaner.server.Spielplan;
import de.hdm.softwareProjekt.kinoPlaner.server.Umfrage;
import de.hdm.softwareProjekt.kinoPlaner.server.Umfrageoption;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface KinoplanerAsync {
	
	public void init(AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void getAnwender(AsyncCallback<Anwender> callback)  throws IllegalArgumentException;
	
	public void setAnwender(Anwender anwender, AsyncCallback<Void> callback)  throws IllegalArgumentException;
	
	public void erstellenAnwender(int id, String name, String gmail, AsyncCallback<Anwender> callback) throws IllegalArgumentException;
	
	public void erstellenGruppe(int id, String name, int besitzerId, AsyncCallback<Gruppe> callback) throws IllegalArgumentException;
	
	public void erstellenKino(int id, String name, int besitzerId, int plz, String stadt, String strassse, String hausnummer, int kinokettenId, AsyncCallback<Kino> callback) throws IllegalArgumentException;
	
	public void erstellenKino(int id, String name, int besitzerId, int plz, String stadt, String strassse, String hausnummer, AsyncCallback<Kino> callback) throws IllegalArgumentException;
	
	public void erstellenKinokette(int id, String name, int besitzerId,  String sitz, String website, AsyncCallback<Kinokette> callback) throws IllegalArgumentException;
	
	public void erstellenSpielplan(int id, String name, int besitzerId,  int kinoId, AsyncCallback<Spielplan> callback) throws IllegalArgumentException;
	
	public void erstellenVorstellung(int id, String name, int spielplanId, int spielzeitId, int filmId, AsyncCallback<Vorstellung> callback) throws IllegalArgumentException;
	
	public void erstellenUmfrage(int id, String name, int besitzerId, int gruppenId, AsyncCallback<Umfrage> callback) throws IllegalArgumentException;
	
	public void erstellenUmfrageoption(int id, String name, int umfrageId, int vorstellungId, AsyncCallback<Umfrageoption> callback) throws IllegalArgumentException;
	
	public void erstellenFilm(int id, String name, int besitzerId, String beschreibung, int bewertung, AsyncCallback<Film> callback) throws IllegalArgumentException;
	
	public void erstellenSpielzeit(int id, String name, int besitzerId, Calendar zeit, AsyncCallback<Spielzeit> callback) throws IllegalArgumentException;
	
	public void erstellenAuswahl(int id, String name, int besitzerId, int voting, int umfrageoptionId, AsyncCallback<Auswahl> callback) throws IllegalArgumentException;
	
	public void isVoted(Auswahl auswahl, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void speichern(Anwender anwender, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void speichern(Gruppe gruppe, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void speichern(Kino kino, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void speichern(Kinokette kinokette, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void speichern(Spielplan spielplan, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void speichern(Vorstellung vorstellung, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void speichern(Umfrage umfrage, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void speichern(Umfrageoption umfrageoption, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void speichern(Film film, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void speichern(Spielzeit spielzeit, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void speichern(Auswahl auswahl, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void löschen(Anwender anwender, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void löschen(Gruppe gruppe, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void löschen(Kino kino, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void löschen(Kinokette kinokette, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void löschen(Spielplan spielplan, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void löschen(Vorstellung vorstellung, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void löschen(Umfrage umfrage, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void löschen(Umfrageoption umfrageoption, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void löschen(Film film, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void löschen(Spielzeit spielzeit, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void löschen(Auswahl auswahl, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void getAnwenderById(int anwenderId, AsyncCallback<Anwender> callback) throws IllegalArgumentException;
	
	public void getSpielplanById(int spielplanId, AsyncCallback<Spielplan> callback) throws IllegalArgumentException;
	
	public void getKinoById(int kinoId, AsyncCallback<ArrayList<Kino>> callback) throws IllegalArgumentException;
	
	public void getGruppenByAnwender(Anwender anwender, AsyncCallback<ArrayList<Gruppe>> callback) throws IllegalArgumentException;
	
	public void getGruppenByAnwenderOwner(Anwender anwender, AsyncCallback<ArrayList<Gruppe>> callback) throws IllegalArgumentException;
	
	public void getUmfragenByAnwender(Anwender anwender, AsyncCallback<ArrayList<Umfrage>> callback) throws IllegalArgumentException;
	
	public void getUmfragenByAnwenderOwner(Anwender anwender, AsyncCallback<ArrayList<Umfrage>> callback) throws IllegalArgumentException;
	
	public void getClosedUmfragenByAnwender(Anwender anwender, AsyncCallback<ArrayList<Umfrage>> callback) throws IllegalArgumentException;
	
	public void getKinokettenByAnwenderOwner(Anwender anwender, AsyncCallback<ArrayList<Kinokette>> callback) throws IllegalArgumentException;
	
	public void getKinosByAnwenderOwner(Anwender anwender, AsyncCallback<ArrayList<Kino>> callback) throws IllegalArgumentException;
	
	public void getKinosByKinoketteId(Kinokette kinokette, AsyncCallback<ArrayList<Kino>> callback) throws IllegalArgumentException;
	
	public void getKinosByKinoketteId(int kinoketteId, AsyncCallback<ArrayList<Kino>> callback) throws IllegalArgumentException;
		
	public void getSpielplaeneByAnwenderOwner(Anwender anwender, AsyncCallback<ArrayList<Spielplan>> callback) throws IllegalArgumentException;
	
	public void getSpielplaeneByKino(Kino kino, AsyncCallback<ArrayList<Spielplan>> callback) throws IllegalArgumentException;
	
	public void getFilmeByAnwenderOwner(Anwender anwender, AsyncCallback<ArrayList<Film>> callback) throws IllegalArgumentException;
	
	public void getSpielzeitenByAnwenderOwner(Anwender anwender, AsyncCallback<ArrayList<Spielzeit>> callback) throws IllegalArgumentException;
	
	public void getAuswahlenByAnwenderOwner(Anwender anwender, AsyncCallback<ArrayList<Auswahl>> callback) throws IllegalArgumentException;
	
	public void getVorstellungenBySpielplan(Spielplan spielplan, AsyncCallback<ArrayList<Vorstellung>> callback) throws IllegalArgumentException;
	
	public void getAnwenderByName(String name, AsyncCallback<Anwender> callback) throws IllegalArgumentException;
	
	public void getUmfrageoptionenByUmfrage(Umfrage umfrage, AsyncCallback<ArrayList<Umfrageoption>> callback) throws IllegalArgumentException;
	
	public void getUmfrageoptionenByVorstellung(Vorstellung vorstellung, AsyncCallback<ArrayList<Umfrageoption>> callback) throws IllegalArgumentException;
	
	public void getGruppenmitgliederByGruppe(Gruppe gruppe, AsyncCallback<ArrayList<Anwender>> callback) throws IllegalArgumentException;
	
	public void getUmfragenByGruppe(Gruppe gruppe, AsyncCallback<ArrayList<Umfrage>> callback) throws IllegalArgumentException;
	
	public void getAllVorstellungen(AsyncCallback<ArrayList<Vorstellung>> callback) throws IllegalArgumentException;
	
	public void getAllKinoketten(AsyncCallback<ArrayList<Kinokette>> callback) throws IllegalArgumentException;
	
	public void getAllKinos(AsyncCallback<ArrayList<Kino>> callback) throws IllegalArgumentException;
	
	public void getAllFilme(AsyncCallback<ArrayList<Film>> callback) throws IllegalArgumentException;
	
	public void getAllSpielzeiten(AsyncCallback<ArrayList<Spielzeiten>> callback) throws IllegalArgumentException;
	
	public void filterResultVorstellungenByKinoOrKinokette(ArrayList<Vorstellung> resultSet, Kino kino, AsyncCallback<ArrayList<Vorstellung>> callback) throws IllegalArgumentException;
	
	public void filterResultVorstellungenByKinoOrKinokette(ArrayList<Vorstellung> resultSet, Kinokette kino, AsyncCallback<ArrayList<Vorstellung>> callback) throws IllegalArgumentException;
	
	public void filterResultVorstellungenByFilm(ArrayList<Vorstellung> resultSet, Film film, AsyncCallback<ArrayList<Vorstellung>> callback) throws IllegalArgumentException;
	
	public void filterResultVorstellungenBySpielzeit(ArrayList<Vorstellung> resultSet, Spielzeit spielzeit, AsyncCallback<ArrayList<Vorstellung>> callback) throws IllegalArgumentException;
	
	public void setSpielplanForKinosByKinokette(Spielplan spielplan, Kino kino , AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void getAuswahlenByUmfrageoption(Umfrageoption umfrageoption, AsyncCallback<ArrayList<Auswahl>> callback) throws IllegalArgumentException;
	
	public void getAuswahlByAnwenderAndUmfrageoption(Anwender anwender, Umfrageoption umfrageoption, AsyncCallback<Auswahl> callback) throws IllegalArgumentException;
	
	public void berechneAuswahlenByUmfrageoption(Umfrageoption umfrageoption, AsyncCallback<Int> callback);
	
	public void stichwahlStarten(Umfrage umfrage,ArrayList<Umfrageoption> umfrageoptionen, AsyncCallback<Umfrage> callback);
	
	public void ergebnisGefunden(Umfrage umfrage, AsyncCallback<Boolean> callback);
	
	public void umfrageGewinnerErmitteln(Umfrage umfrage, AsyncCallback<Umfrageoption> callback);
	
	public void stichwahlUmfrageoptionenErmitteln(Umfrage umfrage, AsyncCallback<ArrayList<Umfrageoption>> callback);
}
