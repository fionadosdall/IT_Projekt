package de.hdm.softwareProjekt.kinoPlaner.shared;

import java.util.ArrayList;

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

	public void erstellenAnwender(String name, String gmail, AsyncCallback<Anwender> callback)
			throws IllegalArgumentException;

	public void erstellenGruppe(String name, ArrayList<Anwender> list, Anwender besitzer,
			AsyncCallback<Gruppe> callback) throws IllegalArgumentException;

	public void erstellenKino(String name, int plz, String stadt, String strassse, String hausnummer, int kinokettenId,
			Anwender besitzer, AsyncCallback<Kino> callback) throws IllegalArgumentException;

	public void erstellenKino(String name, int plz, String stadt, String strassse, String hausnummer, Anwender besitzer,
			AsyncCallback<Kino> callback) throws IllegalArgumentException;

	public void erstellenKinokette(String name, String sitz, String website, Anwender besitzer,
			AsyncCallback<Kinokette> callback) throws IllegalArgumentException;

	public void erstellenSpielplanKino(String name, int kinoId, ArrayList<Vorstellung> neueVorstellungen,
			Anwender besitzer, AsyncCallback<Spielplan> callback) throws IllegalArgumentException;

	public void erstellenVorstellung(int spielplanId, int spielzeitId, int filmId, AsyncCallback<Vorstellung> callback)
			throws IllegalArgumentException;

	public void erstellenUmfrage(String name, ArrayList<Vorstellung> list, int gruppenId, Anwender besitzer,
			AsyncCallback<Umfrage> callback) throws IllegalArgumentException;

	public void erstellenUmfrageoption(String name, int umfrageId, int vorstellungId,
			AsyncCallback<Umfrageoption> callback) throws IllegalArgumentException;

	public void erstellenFilm(String name, String beschreibung, String bewertung, Anwender besitzer,
			AsyncCallback<Film> callback) throws IllegalArgumentException;

	public void erstellenSpielzeit(String name, String zeit, Anwender besitzer, AsyncCallback<Spielzeit> callback)
			throws IllegalArgumentException;

	public void erstellenAuswahl(String name, int voting, int umfrageoptionId, Anwender besitzer,
			AsyncCallback<Auswahl> callback) throws IllegalArgumentException;

	public void isVoted(Auswahl auswahl, AsyncCallback<Void> callback);

	public void speichern(Anwender anwender, AsyncCallback<Anwender> callback) throws IllegalArgumentException;

	public void speichern(Gruppe gruppe, AsyncCallback<Gruppe> callback) throws IllegalArgumentException;

	public void speichern(Kino kino, AsyncCallback<Kino> callback) throws IllegalArgumentException;

	public void speichern(Kinokette kinokette, AsyncCallback<Kinokette> callback) throws IllegalArgumentException;

	public void speichern(Spielplan spielplan, Anwender anwender, AsyncCallback<Spielplan> callback)
			throws IllegalArgumentException;

	public void speichern(Vorstellung vorstellung, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void speichern(Umfrage umfrage, AsyncCallback<Umfrage> callback) throws IllegalArgumentException;

	public void speichern(Umfrageoption umfrageoption, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void speichern(Film film, AsyncCallback<Film> callback) throws IllegalArgumentException;

	public void speichern(Spielzeit spielzeit, AsyncCallback<Spielzeit> callback) throws IllegalArgumentException;

	public void speichern(Auswahl auswahl, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void loeschen(Anwender anwender, AsyncCallback<Void> loeschenCallback) throws IllegalArgumentException;

	public void loeschen(Gruppe gruppe, Anwender anwender, AsyncCallback<Void> callback)
			throws IllegalArgumentException;

	public void loeschen(Kino kino, Anwender anwender, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void loeschen(Kinokette kinokette, Anwender anwender, AsyncCallback<Void> callback)
			throws IllegalArgumentException;

	public void loeschen(Spielplan spielplan, Anwender anwender, AsyncCallback<Void> callback)
			throws IllegalArgumentException;

	public void loeschen(Vorstellung vorstellung, Anwender anwender, AsyncCallback<Void> callback)
			throws IllegalArgumentException;

	public void loeschen(Umfrage umfrage, Anwender anwender, AsyncCallback<Void> callback)
			throws IllegalArgumentException;

	public void loeschen(Umfrageoption umfrageoption, Anwender anwender, AsyncCallback<Void> callback)
			throws IllegalArgumentException;

	public void loeschen(Film film, Anwender anwender, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	public void loeschen(Spielzeit spielzeit, Anwender anwender, AsyncCallback<Boolean> callback)
			throws IllegalArgumentException;

	public void loeschen(Auswahl auswahl, Anwender anwender, AsyncCallback<Void> callback)
			throws IllegalArgumentException;

	public void getAnwenderById(int anwenderId, AsyncCallback<Anwender> callback) throws IllegalArgumentException;

	public void getSpielplanById(int spielplanId, AsyncCallback<Spielplan> callback) throws IllegalArgumentException;

	public void getVorstellungById(int vorstellungId, AsyncCallback<Vorstellung> callback)
			throws IllegalArgumentException;

	public void getKinoById(int kinoId, AsyncCallback<Kino> asyncCallback) throws IllegalArgumentException;

	public void getGruppenByAnwender(Anwender anwender, AsyncCallback<ArrayList<Gruppe>> callback)
			throws IllegalArgumentException;

	public void getGruppenByAnwenderOwner(Anwender anwender, AsyncCallback<ArrayList<Gruppe>> callback)
			throws IllegalArgumentException;

	public void getUmfragenByAnwender(Anwender anwender, AsyncCallback<ArrayList<Umfrage>> callback)
			throws IllegalArgumentException;

	public void getUmfragenByAnwenderOwner(Anwender anwender, AsyncCallback<ArrayList<Umfrage>> callback)
			throws IllegalArgumentException;

	public void getClosedUmfragenByAnwender(Anwender anwender, AsyncCallback<ArrayList<Umfrage>> callback);

	public void getKinokettenByAnwenderOwner(Anwender anwender, AsyncCallback<ArrayList<Kinokette>> callback)
			throws IllegalArgumentException;

	public void getKinosByAnwenderOwner(Anwender anwender, AsyncCallback<ArrayList<Kino>> callback)
			throws IllegalArgumentException;

	public void getKinosByKinoketteId(Kinokette kinokette, AsyncCallback<ArrayList<Kino>> callback)
			throws IllegalArgumentException;

	public void getKinosByKinoketteId(int kinoketteId, AsyncCallback<ArrayList<Kino>> callback)
			throws IllegalArgumentException;

	public void getSpielplaeneByAnwenderOwner(Anwender anwender, AsyncCallback<ArrayList<Spielplan>> callback)
			throws IllegalArgumentException;

	public void getSpielplaeneByKino(Kino kino, AsyncCallback<ArrayList<Spielplan>> callback)
			throws IllegalArgumentException;

	public void getFilmeByAnwenderOwner(Anwender anwender, AsyncCallback<ArrayList<Film>> callback)
			throws IllegalArgumentException;

	public void getSpielzeitenByAnwenderOwner(Anwender anwender, AsyncCallback<ArrayList<Spielzeit>> callback)
			throws IllegalArgumentException;

	public void getAuswahlenByAnwenderOwner(Anwender anwender, AsyncCallback<ArrayList<Auswahl>> callback)
			throws IllegalArgumentException;

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

	public void filterResultVorstellungenByKino(ArrayList<Vorstellung> resultSet, Kino kino,
			AsyncCallback<ArrayList<Vorstellung>> callback) throws IllegalArgumentException;

	public void filterResultVorstellungenByKinokette(ArrayList<Vorstellung> resultSet, Kinokette kino,
			AsyncCallback<ArrayList<Vorstellung>> callback) throws IllegalArgumentException;

	public void filterResultVorstellungenByFilm(ArrayList<Vorstellung> resultSet, Film film,
			AsyncCallback<ArrayList<Vorstellung>> callback) throws IllegalArgumentException;

	public void filterResultVorstellungenBySpielzeit(ArrayList<Vorstellung> resultSet, Spielzeit spielzeit,
			AsyncCallback<ArrayList<Vorstellung>> callback) throws IllegalArgumentException;

	public void setSpielplanForKinosByKinokette(Spielplan spielplan, Kino kino, AsyncCallback<Void> callback)
			throws IllegalArgumentException;

	public void getAuswahlenByUmfrageoption(Umfrageoption umfrageoption, AsyncCallback<ArrayList<Auswahl>> callback)
			throws IllegalArgumentException;

	public void getAuswahlByAnwenderAndUmfrageoption(Umfrageoption umfrageoption, Anwender anwender,
			AsyncCallback<Auswahl> callback) throws IllegalArgumentException;

	public void berechneAuswahlenByUmfrageoption(Umfrageoption umfrageoption, AsyncCallback<Integer> callback);

	public void stichwahlStarten(Umfrage umfrage, Anwender anwender, AsyncCallback<Umfrage> callback)
			throws IllegalArgumentException;

	public void ergebnisGefunden(Umfrage umfrage, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	public void umfrageGewinnerErmitteln(Umfrage umfrage, AsyncCallback<Umfrageoption> callback)
			throws IllegalArgumentException;

	public void stichwahlUmfrageoptionenErmitteln(Umfrage umfrage, AsyncCallback<ArrayList<Umfrageoption>> callback);

	public void isClosedSetzen(Auswahl auswahl, Anwender anwender, AsyncCallback<Void> callback);

	public void isClosedEntfernen(Auswahl auswahl, Anwender anwender, AsyncCallback<Void> callback);

	public void anzeigenVonClosedUmfragen(Anwender anwender, AsyncCallback<ArrayList<Umfrage>> callback)
			throws IllegalArgumentException;

	public void gruppenmitgliedHinzufuegen(Anwender anwender, Gruppe gruppe, AsyncCallback<Anwender> callback)
			throws IllegalArgumentException;

	public void gruppenmitgliedEntfernen(Anwender anwender, Gruppe gruppe, AsyncCallback<Anwender> callback)
			throws IllegalArgumentException;

	public void erstellenSpielplaeneKinokette(String name, int kinoketteId, ArrayList<Vorstellung> neueVorstellungen,
			Anwender besitzer, AsyncCallback<ArrayList<Spielplan>> callback) throws IllegalArgumentException;

	public void nameVerfuegbarAnwender(Anwender name, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	public void nameVerfuegbarGruppe(Gruppe name, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	public void nameVerfuegbarKino(Kino name, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	public void nameVerfuegbarKinokette(Kinokette name, AsyncCallback<Boolean> callback)
			throws IllegalArgumentException;

	public void nameVerfuegbarSpielplan(Spielplan name, AsyncCallback<Boolean> callback)
			throws IllegalArgumentException;

	public void nameVerfuegbarVorstellung(String name, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	public void nameVerfuegbarUmfrage(Umfrage name, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	public void nameVerfuegbarUmfrageoption(String name, AsyncCallback<Boolean> callback)
			throws IllegalArgumentException;

	public void nameVerfuegbarFilm(Film name, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	public void nameVerfuegbarSpielzeit(Spielzeit name, AsyncCallback<Boolean> callback)
			throws IllegalArgumentException;

	public void nameVerfuegbarAuswahl(String name, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	public void kinoDerKinoketteHinzufuegen(Kino kino, Kinokette kinokette, AsyncCallback<Kino> callback)
			throws IllegalArgumentException;

	public void kinoketteEntfernen(Kino kino, AsyncCallback<Kino> loeschenCallback) throws IllegalArgumentException;

	public void getSpielplaeneByKinokette(Kinokette kinokette, AsyncCallback<ArrayList<Spielplan>> callback)
			throws IllegalArgumentException;

	public void volltextSucheGruppen(String text, Anwender anwender, AsyncCallback<ArrayList<Gruppe>> callback)
			throws IllegalArgumentException;

	public void volltextSucheUmfragen(String text, Anwender anwender, AsyncCallback<ArrayList<Umfrage>> callback)
			throws IllegalArgumentException;

	public void volltextSucheErgebnisse(String text, Anwender anwender, AsyncCallback<ArrayList<Umfrage>> callback)
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

	public void umfrageoptionEntfernen(Umfrageoption umfrageoption, Umfrage umfrageFertig, Anwender anwender,
			AsyncCallback<Vorstellung> callback) throws IllegalArgumentException;

	public void loeschenKinoketteById(int id, Anwender anwender, AsyncCallback<Void> callback)
			throws IllegalArgumentException;

	public void volltextSucheKinoketten(String text, Anwender anwender, AsyncCallback<ArrayList<Kinokette>> callback)
			throws IllegalArgumentException;

	public void volltextSucheKinos(String text, Anwender anwender, AsyncCallback<ArrayList<Kino>> callback)
			throws IllegalArgumentException;

	public void volltextSucheSpielplaene(String text, Anwender anwender, AsyncCallback<ArrayList<Spielplan>> callback)
			throws IllegalArgumentException;

	public void volltextSucheSpielzeit(String text, Anwender anwender, AsyncCallback<ArrayList<Spielzeit>> callback)
			throws IllegalArgumentException;

	public void volltextSucheFilm(String text, Anwender anwender, AsyncCallback<ArrayList<Film>> callback)
			throws IllegalArgumentException;

	public void getSpielzeitById(int spielzeitId, AsyncCallback<Spielzeit> callback) throws IllegalArgumentException;

	public void getKinoByVorstellung(Vorstellung vorstellung, AsyncCallback<Kino> callback)
			throws IllegalArgumentException;

	public void getKinoketteByVorstellung(Vorstellung vorstellung, AsyncCallback<Kinokette> callback)
			throws IllegalArgumentException;

	public void getGruppeByName(String name, AsyncCallback<Gruppe> callback) throws IllegalArgumentException;

	public void erstellenStichwahl(String name, int gruppenId, Anwender besitzer, AsyncCallback<Umfrage> callback)
			throws IllegalArgumentException;

	public void updateGruppe(Gruppe gruppe, ArrayList<Anwender> gruppenmitglieder, Anwender anwender,
			AsyncCallback<Gruppe> callback) throws IllegalArgumentException;

	public void sinnloserCallback(AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void auswahlenErstellen(ArrayList<Auswahl> zuErstellendeAuswahlen, ArrayList<Auswahl> alteAuswahlen,
			int size, Umfrage umfrage, Anwender anwender, AsyncCallback<Umfrage> callback)
			throws IllegalArgumentException;

	public void getFilmByName(String name, AsyncCallback<Film> callback) throws IllegalArgumentException;

	public void getKinoByName(String name, AsyncCallback<Kino> callback) throws IllegalArgumentException;

	public void getKinoketteByName(String name, AsyncCallback<Kinokette> callback) throws IllegalArgumentException;

	public void getSpielzeitByName(String name, AsyncCallback<Spielzeit> callback) throws IllegalArgumentException;

	public void updateUmfrage(Umfrage umfrage, ArrayList<Vorstellung> umfrageoptionen, Anwender anwender,
			AsyncCallback<Umfrage> callback) throws IllegalArgumentException;

	public void isVotedEntfernen(Auswahl auswahl, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void getOpenUmfragenByAnwender(Anwender anwender, AsyncCallback<ArrayList<Umfrage>> callback)
			throws IllegalArgumentException;

	public void updateSpielplanKino(ArrayList<Vorstellung> zuErstellendeVorstellungen, Spielplan spielplan,
			Anwender anwender, AsyncCallback<Spielplan> callback) throws IllegalArgumentException;

	public void updateSpielplanKinokette(ArrayList<Vorstellung> zuErstellendeVorstellungen, Spielplan spielplan,
			Anwender anwender, AsyncCallback<ArrayList<Spielplan>> callback) throws IllegalArgumentException;

	public void anzeigenVonZeitgueltigenVorstellungen(AsyncCallback<ArrayList<Vorstellung>> callback)
			throws IllegalArgumentException;
}
