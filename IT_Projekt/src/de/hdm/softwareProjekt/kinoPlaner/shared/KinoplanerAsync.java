package de.hdm.softwareProjekt.kinoPlaner.shared;

import java.util.ArrayList;
import java.sql.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Auswahl;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielplan;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrageoption;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

/**
 * <p>
 * Das Interface <code>KinplanerAsync</code> ist das asynchrone Gegenstück zum
 * Interface {@link Kinoplaner} und wird semiautomatisch durch das GooglePlugin
 * erstellt und gepflegt.
 */

public interface KinoplanerAsync {

	public void init(AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void getAnwender(AsyncCallback<Anwender> callback) throws IllegalArgumentException;

	public void setAnwender(Anwender anwender, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void erstellenAnwender(String name, String gmail, AsyncCallback<Anwender> callback)
			throws IllegalArgumentException;

	public void erstellenGruppe(String name, AsyncCallback<Gruppe> callback) throws IllegalArgumentException;

	public void erstellenKino(String name, int plz, String stadt, String strassse, String hausnummer, int kinokettenId,
			AsyncCallback<Kino> callback) throws IllegalArgumentException;

	public void erstellenKino(String name, int plz, String stadt, String strassse, String hausnummer,
			AsyncCallback<Kino> callback) throws IllegalArgumentException;

	public void erstellenKinokette(String name, String sitz, String website, AsyncCallback<Kinokette> callback)
			throws IllegalArgumentException;

	public void erstellenSpielplanKino(String name, int kinoId, AsyncCallback<Spielplan> callback)
			throws IllegalArgumentException;

	public void erstellenVorstellung(String name, int spielplanId, int spielzeitId, int filmId,
			AsyncCallback<Vorstellung> callback) throws IllegalArgumentException;

	public void erstellenUmfrage(String name, int gruppenId, AsyncCallback<Umfrage> callback)
			throws IllegalArgumentException;

	public void erstellenUmfrageoption(String name, int umfrageId, int vorstellungId,
			AsyncCallback<Umfrageoption> callback) throws IllegalArgumentException;

	public void erstellenFilm(String name, String beschreibung, int bewertung, AsyncCallback<Film> callback)
			throws IllegalArgumentException;

	public void erstellenSpielzeit(String name, Date zeit, AsyncCallback<Spielzeit> callback)
			throws IllegalArgumentException;

	public void erstellenAuswahl(String name, int voting, int umfrageoptionId, AsyncCallback<Auswahl> callback)
			throws IllegalArgumentException;

	public void isVoted(Auswahl auswahl, AsyncCallback<Void> callback);

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

	public void loeschen(Anwender anwender, AsyncCallback<Void> loeschenCallback) throws IllegalArgumentException;

	public void loeschen(Gruppe gruppe, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void loeschen(Kino kino, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void loeschen(Kinokette kinokette, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void loeschen(Spielplan spielplan, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void loeschen(Vorstellung vorstellung, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void loeschen(Umfrage umfrage, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void loeschen(Umfrageoption umfrageoption, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void loeschen(Film film, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	public void loeschen(Spielzeit spielzeit, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	public void loeschen(Auswahl auswahl, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void getAnwenderById(int anwenderId, AsyncCallback<Anwender> callback) throws IllegalArgumentException;

	public void getSpielplanById(int spielplanId, AsyncCallback<Spielplan> callback) throws IllegalArgumentException;

	public void getKinoById(int kinoId, AsyncCallback<Kino> asyncCallback) throws IllegalArgumentException;

	public void getGruppenByAnwender(AsyncCallback<ArrayList<Gruppe>> callback) throws IllegalArgumentException;

	public void getGruppenByAnwenderOwner(AsyncCallback<ArrayList<Gruppe>> callback) throws IllegalArgumentException;

	public void getUmfragenByAnwender(AsyncCallback<ArrayList<Umfrage>> callback) throws IllegalArgumentException;

	public void getUmfragenByAnwenderOwner(AsyncCallback<ArrayList<Umfrage>> callback) throws IllegalArgumentException;

	public void getClosedUmfragenByAnwender(AsyncCallback<ArrayList<Umfrage>> callback);

	public void getKinokettenByAnwenderOwner(AsyncCallback<ArrayList<Kinokette>> callback)
			throws IllegalArgumentException;

	public void getKinosByAnwenderOwner(AsyncCallback<ArrayList<Kino>> callback) throws IllegalArgumentException;

	public void getKinosByKinoketteId(Kinokette kinokette, AsyncCallback<ArrayList<Kino>> callback)
			throws IllegalArgumentException;

	public void getKinosByKinoketteId(int kinoketteId, AsyncCallback<ArrayList<Kino>> callback)
			throws IllegalArgumentException;

	public void getSpielplaeneByAnwenderOwner(AsyncCallback<ArrayList<Spielplan>> callback)
			throws IllegalArgumentException;

	public void getSpielplaeneByKino(Kino kino, AsyncCallback<ArrayList<Spielplan>> callback)
			throws IllegalArgumentException;

	public void getFilmeByAnwenderOwner(AsyncCallback<ArrayList<Film>> callback) throws IllegalArgumentException;

	public void getSpielzeitenByAnwenderOwner(AsyncCallback<ArrayList<Spielzeit>> callback)
			throws IllegalArgumentException;

	public void getAuswahlenByAnwenderOwner(AsyncCallback<ArrayList<Auswahl>> callback) throws IllegalArgumentException;

	public void getVorstellungenBySpielplan(Spielplan spielplan, AsyncCallback<ArrayList<Vorstellung>> callback)
			throws IllegalArgumentException;

	public void getAnwenderByName(String name, AsyncCallback<Anwender> callback) throws IllegalArgumentException;

	public void getUmfrageoptionenByUmfrage(Umfrage umfrage, AsyncCallback<ArrayList<Umfrageoption>> callback)
			throws IllegalArgumentException;

	public void getUmfrageoptionenByVorstellung(Vorstellung vorstellung,
			AsyncCallback<ArrayList<Umfrageoption>> callback) throws IllegalArgumentException;

	public void getGruppenmitgliederByGruppe(Gruppe gruppe, AsyncCallback<ArrayList<Anwender>> callback)
			throws IllegalArgumentException;

	public void getUmfragenByGruppe(Gruppe gruppe, AsyncCallback<ArrayList<Umfrage>> callback)
			throws IllegalArgumentException;

	public void getAllAnwender(AsyncCallback<ArrayList<Anwender>> callback) throws IllegalArgumentException;

	public void getAllVorstellungen(AsyncCallback<ArrayList<Vorstellung>> callback) throws IllegalArgumentException;

	public void getAllKinoketten(AsyncCallback<ArrayList<Kinokette>> callback) throws IllegalArgumentException;

	public void getAllKinos(AsyncCallback<ArrayList<Kino>> callback) throws IllegalArgumentException;

	public void getAllFilme(AsyncCallback<ArrayList<Film>> callback) throws IllegalArgumentException;

	public void getAllSpielzeiten(AsyncCallback<ArrayList<Spielzeit>> callback) throws IllegalArgumentException;

	public void filterResultVorstellungenByKinoOrKinokette(ArrayList<Vorstellung> resultSet, Kino kino,
			AsyncCallback<ArrayList<Vorstellung>> callback) throws IllegalArgumentException;

	public void filterResultVorstellungenByKinoOrKinokette(ArrayList<Vorstellung> resultSet, Kinokette kino,
			AsyncCallback<ArrayList<Vorstellung>> callback) throws IllegalArgumentException;

	public void filterResultVorstellungenByFilm(ArrayList<Vorstellung> resultSet, Film film,
			AsyncCallback<ArrayList<Vorstellung>> callback) throws IllegalArgumentException;

	public void filterResultVorstellungenBySpielzeit(ArrayList<Vorstellung> resultSet, Spielzeit spielzeit,
			AsyncCallback<ArrayList<Vorstellung>> callback) throws IllegalArgumentException;

	public void setSpielplanForKinosByKinokette(Spielplan spielplan, Kino kino, AsyncCallback<Void> callback)
			throws IllegalArgumentException;

	public void getAuswahlenByUmfrageoption(Umfrageoption umfrageoption, AsyncCallback<ArrayList<Auswahl>> callback)
			throws IllegalArgumentException;

	public void getAuswahlByAnwenderAndUmfrageoption(Umfrageoption umfrageoption, AsyncCallback<Auswahl> callback)
			throws IllegalArgumentException;

	public void berechneAuswahlenByUmfrageoption(Umfrageoption umfrageoption, AsyncCallback<Integer> callback);

	public void stichwahlStarten(Umfrage umfrage, AsyncCallback<Umfrage> callback) throws IllegalArgumentException;

	public void ergebnisGefunden(Umfrage umfrage, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	public void umfrageGewinnerErmitteln(Umfrage umfrage, AsyncCallback<Umfrageoption> callback)
			throws IllegalArgumentException;

	public void stichwahlUmfrageoptionenErmitteln(Umfrage umfrage, AsyncCallback<ArrayList<Umfrageoption>> callback);

	public void isClosedSetzen(Auswahl auswahl, AsyncCallback<Void> callback);

	public void isClosedEntfernen(Auswahl auswahl, AsyncCallback<Void> callback);

	public void anzeigenVonClosedUmfragen(AsyncCallback<ArrayList<Umfrage>> callback) throws IllegalArgumentException;

	public void gruppenmitgliedHinzufuegen(Anwender anwender, Gruppe gruppe, AsyncCallback<Anwender> callback)
			throws IllegalArgumentException;

	public void gruppenmitgliedEntfernen(Anwender anwender, Gruppe gruppe, AsyncCallback<Anwender> callback)
			throws IllegalArgumentException;

	public void erstellenSpielplaeneKinokette(String name, int kinoketteId,
			AsyncCallback<ArrayList<Spielplan>> callback) throws IllegalArgumentException;

	public void nameVerfuegbarAnwender(String name, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	public void nameVerfuegbarGruppe(String name, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	public void nameVerfuegbarKino(String name, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	public void nameVerfuegbarKinokette(String name, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	public void nameVerfuegbarSpielplan(String name, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	public void nameVerfuegbarVorstellung(String name, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	public void nameVerfuegbarUmfrage(String name, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	public void nameVerfuegbarUmfrageoption(String name, AsyncCallback<Boolean> callback)
			throws IllegalArgumentException;

	public void nameVerfuegbarFilm(String name, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	public void nameVerfuegbarSpielzeit(String name, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	public void nameVerfuegbarAuswahl(String name, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	public void gruppenmitgliedEntfernen(Anwender anwender, AsyncCallback<Anwender> callback)
			throws IllegalArgumentException;

	public void kinoDerKinoketteHinzufuegen(Kino kino, Kinokette kinokette, AsyncCallback<Kino> callback)
			throws IllegalArgumentException;

	public void kinoketteEntfernen(Kino kino, AsyncCallback<Kino> loeschenCallback) throws IllegalArgumentException;

	public void gruppenmitgliedHinzufuegen(Anwender anwender, AsyncCallback<Anwender> callback)
			throws IllegalArgumentException;

	public void getSpielplaeneByKinokette(Kinokette kinokette, AsyncCallback<ArrayList<Spielplan>> callback)
			throws IllegalArgumentException;

	public void volltextSucheGruppen(String text, AsyncCallback<ArrayList<Gruppe>> callback)
			throws IllegalArgumentException;

	public void volltextSucheUmfragen(String text, AsyncCallback<ArrayList<Umfrage>> callback)
			throws IllegalArgumentException;

	public void volltextSucheErgebnisse(String text, AsyncCallback<ArrayList<Umfrage>> callback)
			throws IllegalArgumentException;

	public void getUmfrageById(int umfrageId, AsyncCallback<Umfrage> callback) throws IllegalArgumentException;

	public void getGruppeById(int gruppeId, AsyncCallback<Gruppe> callback) throws IllegalArgumentException;

	public void getVorstellungByUmfrageoption(Umfrageoption umfrageoption, AsyncCallback<Vorstellung> callback);

	public void getFilmByUmfrageoption(Umfrageoption umfrageoption, AsyncCallback<Film> callback)
			throws IllegalArgumentException;

	public void getSpielzeitByUmfrageoption(Umfrageoption umfrageoption, AsyncCallback<Spielzeit> callback)
			throws IllegalArgumentException;

	public void getKinoByUmfrageoption(Umfrageoption umfrageoption, AsyncCallback<Kino> callback)
			throws IllegalArgumentException;

	public void getKinoketteByUmfrageoption(Umfrageoption umfrageoption, AsyncCallback<Kinokette> callback)
			throws IllegalArgumentException;

	public void getAllSpielplaene(AsyncCallback<ArrayList<Spielplan>> callback) throws IllegalArgumentException;

	public void getSpielplanByUmfrageoption(Umfrageoption umfrageoption, AsyncCallback<Spielplan> callback)
			throws IllegalArgumentException;

	public void getKinoketteById(int kinoketteId, AsyncCallback<Kinokette> callback) throws IllegalArgumentException;

	public void getFilmById(int filmId, AsyncCallback<Film> callback) throws IllegalArgumentException;

	public void umfrageoptionHinzufuegen(Vorstellung vorstellung, Umfrage umfrageFertig,
			AsyncCallback<Umfrageoption> callback) throws IllegalArgumentException;

	public void umfrageoptionHinzufuegen(Vorstellung vorstellung, AsyncCallback<Vorstellung> callback)
			throws IllegalArgumentException;

	public void umfrageoptionEntfernen(Umfrageoption umfrageoption, Umfrage umfrageFertig,
			AsyncCallback<Vorstellung> callback) throws IllegalArgumentException;

	public void umfrageoptionEntfernen(Vorstellung vorstellung, AsyncCallback<Vorstellung> callback)
			throws IllegalArgumentException;

	public void gruppenmitgliedHinzufuegen(String anwenderName, AsyncCallback<Anwender> callback)
			throws IllegalArgumentException;

	public void loeschenKinoketteById(int id, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void volltextSucheKinoketten(String text, AsyncCallback<ArrayList<Kinokette>> callback)
			throws IllegalArgumentException;

	public void volltextSucheKinos(String text, AsyncCallback<ArrayList<Kino>> callback)
			throws IllegalArgumentException;

	public void volltextSucheSpielplaene(String text, AsyncCallback<ArrayList<Spielplan>> callback)
			throws IllegalArgumentException;

	public void volltextSucheSpielzeit(String text, AsyncCallback<ArrayList<Spielzeit>> callback)
			throws IllegalArgumentException;

	public void volltextSucheFilm(String text, AsyncCallback<ArrayList<Film>> callback) throws IllegalArgumentException;

	public void getSpielzeitById(int spielzeitId, AsyncCallback<Spielzeit> callback) throws IllegalArgumentException;

	public void getKinoByVorstellung(Vorstellung vorstellung, AsyncCallback<Kino> callback)
			throws IllegalArgumentException;

	public void getKinoketteByVorstellung(Vorstellung vorstellung, AsyncCallback<Kinokette> callback)
			throws IllegalArgumentException;
}
