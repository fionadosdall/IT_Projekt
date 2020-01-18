package de.hdm.softwareProjekt.kinoPlaner.shared;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Auswahl;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.BusinessObjekt;
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
 * Das Interface <code>Kinoplaner</code> ist die synchrone Schnittstelle für die
 * RPC fähige Implementierung der Klasse {@link KinoplanerImpl}. Das asynchrone
 * Pendant hierzu ist das Interface {@link KinoplanerAsync}. Das Interface
 * erweitert das Interface {@link RemoteService}.
 * 
 * Der <code>@RemoteServiceRealtivPath("administartion")</code> wird bei der
 * Adressierung des aus der zugehörigen KinoplanerImpl entstehenden
 * Servelt-Kompilats verwendet. Hier wird ein Teil der URL des Servlets
 * angegeben.
 * </p>
 */
@RemoteServiceRelativePath("administration")
public interface Kinoplaner extends RemoteService {

	/**
	 * <p>
	 * Da derzeit kein anderer Konstruktor als der No-Argument Konstruktor
	 * aufgerufen werden kann, ist eine seperate Instanzenmethode notwendig, welche
	 * direkt nach <code>GWT.create(Klassenname.class)</code> aufgerufen wird und
	 * die Initiialisierung bewirkt. Sie wird für jede Instanz von
	 * <code>KinoplanerImpl</code> aufgerufen. Bei der Initiialisierung wird ein
	 * vollständiger Satz Mapper erzeugt, so dass die KLasse mit der Datenbank
	 * kommunizieren kann.
	 * </p>
	 * 
	 * @see #KinoplanerImpl()
	 * @throws IllegalArgumentException
	 */
	public void init() throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe des Anwenders, in dessen Ansicht die Website ausgeführt wird.
	 * </p>
	 * 
	 * @return Anwender der gerade die Website nutzt
	 * @throws IllegalArgumentException
	 */
	public Anwender getAnwender() throws IllegalArgumentException;

	/**
	 * <p>
	 * Setzen des Anwenders, in dessen Ansicht die Website ausgeführt wird.
	 * </p>
	 * 
	 * @param anwender
	 *            Anwender der die Seite nutzt
	 * @throws IllegalArgumentException
	 */
	public void setAnwender(Anwender anwender) throws IllegalArgumentException;

	/**
	 * <p>
	 * Ein neuer Anwender wird angelegt und anschließend in der Datenbank
	 * gespeichert.
	 * </p>
	 * 
	 * @param id
	 *            Kennung
	 * @param name
	 *            Nickname
	 * @param gmail
	 *            EMail Account
	 * @return erstelltes Anwender Objekt
	 * @throws IllegalArgumentException
	 */
	public Anwender erstellenAnwender(String name, String gmail) throws IllegalArgumentException;

	/**
	 * <p>
	 * Eine neue Gruppe wird angelegt und anschließend in der Datenbank gespeichert.
	 * </p>
	 * 
	 * @param id
	 *            Kennung
	 * @param name
	 *            Objektname
	 * @param besiterId
	 *            Kennung des Besitzer
	 * @return erstelltes Gruppe Objekt
	 * @throws IllegalArgumentException
	 */
	public Gruppe erstellenGruppe(String name, ArrayList<Anwender> list) throws IllegalArgumentException;

	/**
	 * <p>
	 * Ein neues Kino wird angelegt und anschließend in der Datenbank gespeichert.
	 * Hierbei mit der Verknüpfung zur Kinokette.
	 * </p>
	 * 
	 * @param id
	 *            Kennung
	 * @param name
	 *            Objektname
	 * @param besiterId
	 *            Kennung des Besitzer
	 * @param plz
	 *            Postleitzahl
	 * @param stadt
	 *            Stadt
	 * @param strassse
	 *            Straße
	 * @param hausnummer
	 *            Hausnummer
	 * @param kinoketteId
	 *            Kennung der Kinokette
	 * @return erstelltes Kino Objekt
	 * @throws IllegalArgumentException
	 */
	public Kino erstellenKino(String name, int plz, String stadt, String strassse, String hausnummer, int kinokettenId)
			throws IllegalArgumentException;

	/**
	 * <p>
	 * Ein neues Kino wird angelegt und anschließend in der Datenbank gespeichert.
	 * Hierbei ohne die Verknüpfung zur Kinokette.
	 * </p>
	 * 
	 * @param id
	 *            Kennung
	 * @param name
	 *            Objektname
	 * @param besiterId
	 *            Kennung des Besitzer
	 * @param plz
	 *            Postleitzahl
	 * @param stadt
	 *            Stadt
	 * @param strassse
	 *            Straße
	 * @param hausnummer
	 *            Hausnummer
	 * @return erstelltes Kino Objekt
	 * @throws IllegalArgumentException
	 */
	public Kino erstellenKino(String name, int plz, String stadt, String strassse, String hausnummer)
			throws IllegalArgumentException;

	/**
	 * <p>
	 * Eine neue Kinokette wird angelegt und anschließend in der Datenbank
	 * gespeichert.
	 * </p>
	 * 
	 * @param id
	 *            Kennung
	 * @param name
	 *            Objektname
	 * @param besiterId
	 *            Kennung des Besitzer
	 * @param sitz
	 *            Sitz des Unternehmens
	 * @param website
	 *            Webseite
	 * @return erstelltes Kinoketten Objekt
	 * @throws IllegalArgumentException
	 */
	public Kinokette erstellenKinokette(String name, String sitz, String website) throws IllegalArgumentException;

	/**
	 * <p>
	 * Ein neuer Spielplan wird angelegt und anschließend in der Datenbank
	 * gespeichert.
	 * </p>
	 * 
	 * @param id
	 *            Kennung
	 * @param name
	 *            Objektname
	 * @param besiterId
	 *            Kennung des Besitzer
	 * @param kinoId
	 *            Kennung des Kinos
	 * @return erstelltes Spielplan Objekt
	 * @throws IllegalArgumentException
	 */
	public Spielplan erstellenSpielplanKino(String name, int kinoId) throws IllegalArgumentException;

	/**
	 * <p>
	 * Eine neue Vorstellung wird angelegt und anschließend in der Datenbank
	 * gespeichert.
	 * </p>
	 * 
	 * @param id
	 *            Kennung
	 * @param name
	 *            Objektname
	 * @param spielplanId
	 *            Kennung des Spielplans
	 * @param spielzeitId
	 *            Kennung der Spielzeit
	 * @param filmId
	 *            Kennung des Films
	 * @return erstelltes Vorstellung Objekt
	 * @throws IllegalArgumentException
	 */
	public Vorstellung erstellenVorstellung(int spielplanId, int spielzeitId, int filmId)
			throws IllegalArgumentException;

	/**
	 * <p>
	 * Eine neue Umfrage wird angelegt und anschließend in der Datenbank
	 * gespeichert.
	 * </p>
	 * 
	 * @param id
	 *            Kennung
	 * @param name
	 *            Objektname
	 * @param besitzerId
	 *            Kennung des Besitzers
	 * @param gruppeId
	 *            Kennung der Gruppe
	 * @return erstelltes Umfrage Objekt
	 * @throws IllegalArgumentException
	 */
	public Umfrage erstellenUmfrage(String name, ArrayList<Vorstellung> list, int gruppenId)
			throws IllegalArgumentException;

	/**
	 * <p>
	 * Eine neue Umfrageoption wird angelegt und anschließend in der Datenbank
	 * gespeichert.
	 * </p>
	 * 
	 * @param id
	 *            Kennung
	 * @param name
	 *            Objektname
	 * @param umfrageId
	 *            Kennung der Umfrage
	 * @param vorstellungId
	 *            Kennung der Vorstellung
	 * @return erstelltes Umfrageoption Objekt
	 * @throws IllegalArgumentException
	 */
	public Umfrageoption erstellenUmfrageoption(String name, int umfrageId, int vorstellungId)
			throws IllegalArgumentException;

	/**
	 * <p>
	 * Ein neuer Film wird angelegt und anschließend in der Datenbank gespeichert.
	 * </p>
	 * 
	 * @param id
	 *            Kennung
	 * @param name
	 *            Objektname
	 * @param besitzerId
	 *            Kennung des besitzer
	 * @param beschreibung
	 *            Beschreibung des Films
	 * @param bewertung
	 *            Bewertung des Films
	 * @return erstelltes Film Objekt
	 * @throws IllegalArgumentException
	 */
	public Film erstellenFilm(String name, String beschreibung, String bewertung) throws IllegalArgumentException;

	/**
	 * <p>
	 * Ein Spielzeit wird angelegt und anschließend in der Datenbank gespeichert.
	 * </p>
	 * 
	 * @param id
	 *            Kennung
	 * @param name
	 *            Objektname
	 * @param besitzerId
	 *            Kennung des besitzer
	 * @param zeit
	 *            Startzeitpunkt
	 * @return erstelltes Spielzeit Objekt
	 * @throws IllegalArgumentException
	 */
	public Spielzeit erstellenSpielzeit(String name, Date zeit) throws IllegalArgumentException;

	/**
	 * <p>
	 * Eine neue Auswahl wird angelegt,die zugehörige Umfrage als gevotet markiert
	 * und anschließend die Auswahl in der Datenbank gespeichert.
	 * </p>
	 * 
	 * @param id
	 *            Kennung
	 * @param name
	 *            Objektname
	 * @param besitzerId
	 *            Kennung des besitzer
	 * @param voting
	 *            Voting
	 * @param umfrageoptionId
	 *            Kennung der Umfrageoption
	 * @return erstelltes Auswahl Objekt
	 * @throws IllegalArgumentException
	 */
	public Auswahl erstellenAuswahl(String name, int voting, int umfrageoptionId) throws IllegalArgumentException;

	/**
	 * <p>
	 * Es wird geprüft ob der Boolean isVoted der Klasse Umfrage bereits auf True
	 * gesetzt wurde, da diese gevotet wurde. Ist dies nicht der Fall so wird der
	 * Boolean auf True gesetzt.
	 * </p>
	 * 
	 * @param auswahl
	 *            Auswahlobjekt
	 */
	public void isVoted(Auswahl auswahl);

	/**
	 * <p>
	 * Speichern eines Anwenders.
	 * </p>
	 * 
	 * @param anwender
	 *            Anwenderobjekt
	 * @throws IllegalArgumentException
	 */
	public void speichern(Anwender anwender) throws IllegalArgumentException;

	/**
	 * <p>
	 * Speichern einer Gruppe.
	 * </p>
	 * 
	 * @param gruppe
	 *            Gruppeobjekt
	 * @throws IllegalArgumentException
	 */
	public void speichern(Gruppe gruppe) throws IllegalArgumentException;

	/**
	 * <p>
	 * Speichern eines Kinos.
	 * </p>
	 * 
	 * @param kino
	 *            Kinoobjekt
	 * @throws IllegalArgumentException
	 */
	public void speichern(Kino kino) throws IllegalArgumentException;

	/**
	 * <p>
	 * Speichern einer Kinokette.
	 * </p>
	 * 
	 * @param kinokette
	 *            Kinokettenobjekt
	 * @throws IllegalArgumentException
	 */
	public void speichern(Kinokette kinokette) throws IllegalArgumentException;

	/**
	 * <p>
	 * Speichern eines Spielplans.
	 * </p>
	 * 
	 * @param spielplan
	 *            Spielplanobjekt
	 * @throws IllegalArgumentException
	 */
	public void speichern(Spielplan spielplan) throws IllegalArgumentException;

	/**
	 * <p>
	 * Speichern einer Vorstellung.
	 * </p>
	 * 
	 * @param vorstellung
	 *            Vorstellungobjekt
	 * @throws IllegalArgumentException
	 */
	public void speichern(Vorstellung vorstellung) throws IllegalArgumentException;

	/**
	 * <p>
	 * Speichern einer Umfrage.
	 * </p>
	 * 
	 * @param umfrage
	 *            Umfrageobjekt
	 * @throws IllegalArgumentException
	 */
	public void speichern(Umfrage umfrage) throws IllegalArgumentException;

	/**
	 * <p>
	 * Speichern einer Umfrageoption.
	 * </p>
	 * 
	 * @param umfrageoption
	 *            Umfrageoptionobjekt
	 * @throws IllegalArgumentException
	 */
	public void speichern(Umfrageoption umfrageoption) throws IllegalArgumentException;

	/**
	 * <p>
	 * Speichern eines Films.
	 * </p>
	 * 
	 * @param film
	 *            Filmobjekt
	 * @throws IllegalArgumentException
	 */
	public void speichern(Film film) throws IllegalArgumentException;

	/**
	 * <p>
	 * Speichern einer Spielzeit.
	 * </p>
	 * 
	 * @param spielzeit
	 *            Spielzeitobjekt
	 * @throws IllegalArgumentException
	 */
	public void speichern(Spielzeit spielzeit) throws IllegalArgumentException;

	/**
	 * <p>
	 * Speichern einer Auswahl.
	 * </p>
	 * 
	 * @param auswahl
	 *            Auswahlobjekt
	 * @throws IllegalArgumentException
	 */
	public void speichern(Auswahl auswahl) throws IllegalArgumentException;

	/**
	 * <p>
	 * Löschen eines Anwenders mit Löschweitergabe.
	 * </p>
	 * 
	 * @param anwender
	 *            Anwenderobjekt
	 * @throws IllegalArgumentException
	 */
	public void loeschen(Anwender anwender) throws IllegalArgumentException;

	/**
	 * <p>
	 * Löschen einer Gruppe mit Löschweitergabe.
	 * </p>
	 * 
	 * @param gruppe
	 *            Gruppeobjekt
	 * @throws IllegalArgumentException
	 */
	public void loeschen(Gruppe gruppe) throws IllegalArgumentException;

	/**
	 * <p>
	 * Löschen eines Kinos mit Löschweitergabe.
	 * </p>
	 * 
	 * @param kino
	 *            Kinoobjekt
	 * @throws IllegalArgumentException
	 */
	public void loeschen(Kino kino) throws IllegalArgumentException;

	/**
	 * <p>
	 * Löschen einer Kinokette mit Löschweitergabe.
	 * </p>
	 * 
	 * @param kinokette
	 *            Kinokettenobjekt
	 * @throws IllegalArgumentException
	 */
	public void loeschen(Kinokette kinokette) throws IllegalArgumentException;

	/**
	 * <p>
	 * Löschen eines Spielplans mit Löschweitergabe.
	 * </p>
	 * 
	 * @param spielplan
	 *            Spielplanobjekt
	 * @throws IllegalArgumentException
	 */
	public void loeschen(Spielplan spielplan) throws IllegalArgumentException;

	/**
	 * <p>
	 * Löschen einer Vorstellung mit Löschweitergabe.
	 * </p>
	 * 
	 * @param vorstellung
	 *            Vorstellungobjekt
	 * @throws IllegalArgumentException
	 */
	public void loeschen(Vorstellung vorstellung) throws IllegalArgumentException;

	/**
	 * <p>
	 * Löschen einer Umfrage mit Löschweitergabe.
	 * </p>
	 * 
	 * @param umfrage
	 *            Umfrageobjekt
	 * @throws IllegalArgumentException
	 */
	public void loeschen(Umfrage umfrage) throws IllegalArgumentException;

	/**
	 * <p>
	 * Löschen einer Umfrageoption mit Löschweitergabe.
	 * </p>
	 * 
	 * @param umfrageoption
	 *            Umfrageoptionobjekt
	 * @throws IllegalArgumentException
	 */
	public void loeschen(Umfrageoption umfrageoption) throws IllegalArgumentException;

	/**
	 * <p>
	 * Loeschen eines Films mit Loeschweitergabe, wenn moeglich (true). Moeglich ist
	 * es wenn das Objekt nur einmal verwendet wird.
	 * </p>
	 * 
	 * @param film
	 *            Filobjekt das geloescht werden soll
	 * @return Rueckgabe ob das Loeschen moeglich ist (true).
	 * @throws IllegalArgumentException
	 */
	public boolean loeschen(Film film) throws IllegalArgumentException;

	/**
	 * <p>
	 * Loeschen einer Spielzeit mit Loeschweitergabe, wenn moeglich (ture). Moeglich
	 * ist es wenn das Objekt nur einmal verwendet wird.
	 * </p>
	 * 
	 * @param spielzeit
	 *            Spielzeitobjekt das geloescht werden soll
	 * @return Rueckgabe ob das Loeschen moeglich ist (true).
	 * @throws IllegalArgumentException
	 */
	public boolean loeschen(Spielzeit spielzeit) throws IllegalArgumentException;

	/**
	 * <p>
	 * Loeschen einer Auswahl.
	 * </p>
	 * 
	 * @param spielzeit
	 *            Spielzeitobjekt
	 * @throws IllegalArgumentException
	 */
	public void loeschen(Auswahl auswahl) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rueckgabe des Anwenders mit einer bestimmten Id.
	 * </p>
	 * 
	 * @param anwenderId
	 *            Kennung des Anwenders
	 * @return gefundenes Anwenderobjekt
	 * @throws IllegalArgumentException
	 */
	public Anwender getAnwenderById(int anwenderId) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rueckgabe eines Spielplans mit einer bestimmten Id.
	 * </p>
	 * 
	 * @param spielplanId
	 *            Kennung des Spielplans
	 * @return gefundenes Spielplanobjekt
	 * @throws IllegalArgumentException
	 */
	public Spielplan getSpielplanById(int spielplanId) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rueckgabe eines Kinos mit einer bestimmten Id.
	 * </p>
	 * 
	 * @param kinoId
	 *            Kennung des Kinos
	 * @return gefundenes Kinoobjekt
	 * @throws IllegalArgumentException
	 */

	public Kino getKinoById(int kinoId) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe aller Gruppen in denen der Anwender Mitglied ist.
	 * </p>
	 * 
	 * @param anwender
	 *            Anwenderobjekt
	 * @return ArrayListe der gefundenen Gruppen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Gruppe> getGruppenByAnwender() throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe aller Gruppen die einem bestimmten Anwender gehören.
	 * </p>
	 * 
	 * @param anwender
	 *            Anwenderobjekt
	 * @return ArrayList der gefundenen Gruppen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Gruppe> getGruppenByAnwenderOwner() throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe aller Umfragen eines Anwenders.
	 * </p>
	 * 
	 * @param anwender
	 *            Anwenderobjekt
	 * @return ArrayList der gefundenen Umfragen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Umfrage> getUmfragenByAnwender() throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe aller Umfragen die der Anwender besitzt.
	 * </p>
	 * 
	 * @param anwender
	 *            Anwenderobjekt
	 * @return ArrayList der gefundenen Umfragen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Umfrage> getUmfragenByAnwenderOwner() throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe aller geschlossenen Umfragen des Anwenders
	 * </p>
	 * 
	 * @param anwender
	 *            Anwenderobjekt
	 * @return ArrayList der gefundenen Umfragen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Umfrage> getClosedUmfragenByAnwender();

	/**
	 * <p>
	 * Rückgabe aller Kinoketten die der Anwender besitzt.
	 * </p>
	 * 
	 * @param anwender
	 *            Anwenderobjekt
	 * @return ArrayList der gefundenen Kinoketten
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Kinokette> getKinokettenByAnwenderOwner() throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe aller Kinos die der Anwender besitzt.
	 * </p>
	 * 
	 * @param anwender
	 *            Anwenderobjekt
	 * @return ArrayList der gefundenen Kinos
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Kino> getKinosByAnwenderOwner() throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe aller Kinos die zu einer Kinokette gehören.
	 * </p>
	 * 
	 * @param kinokette
	 *            Kinokettenobjekt
	 * @return ArrayList der gefundenen Kinos
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Kino> getKinosByKinoketteId(Kinokette kinokette) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe aller Kinos die zu einer Kinokette gehören.
	 * </p>
	 * 
	 * @param akinoketteId
	 *            Kennung der Kinokette
	 * @return ArrayList der gefundenen Kinos
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Kino> getKinosByKinoketteId(int kinoketteId) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe aller Spielpläne die einer Anwender besitzt.
	 * </p>
	 * 
	 * @param anwender
	 *            Anwenderobjekt
	 * @return ArrayList der gefundenen Spielpläne
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Spielplan> getSpielplaeneByAnwenderOwner() throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe aller Spielpläne eines Kinos.
	 * </p>
	 * 
	 * @param kino
	 *            Kinoobjekt
	 * @return ArrayList der gefundenen Spielpläne
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Spielplan> getSpielplaeneByKino(Kino kino) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe aller Filme die ein Anwender besitzt.
	 * </p>
	 * 
	 * @param anwender
	 *            Anwenderobjekt
	 * @return ArrayList der gefundenen Filme
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Film> getFilmeByAnwenderOwner() throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe aller Spielzeiten die ein Anwender besitzt.
	 * </p>
	 * 
	 * @param anwender
	 *            Anwenderobjekt
	 * @return ArrayList der gefundenen Spielzeiten
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Spielzeit> getSpielzeitenByAnwenderOwner() throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe der Auswahlen die ein Anwender besitzt.
	 * </p>
	 * 
	 * @param anwender
	 *            Anwenderobjekt
	 * @return ArrayList der gefundenen Auswahlen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Auswahl> getAuswahlenByAnwenderOwner() throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe aller Vorstellungen eines Spielplans.
	 * </p>
	 * 
	 * @param spielplan
	 *            Spielplanobjekt
	 * @return ArrayList der gefundenen Vorstellungen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Vorstellung> getVorstellungenBySpielplan(Spielplan spielplan) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe eines Anwenders der durch den Namen gesucht wird.
	 * </p>
	 * 
	 * @param name
	 *            Name des Anwenders
	 * @return gefundenes Anwenderobjekt
	 * @throws IllegalArgumentException
	 */
	public Anwender getAnwenderByName(String name) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe aller Umfrageoptionen einer Umfrage.
	 * </p>
	 * 
	 * @param umfrage
	 *            Umfrageobjekt
	 * @return ArrayList der gefundenen Umfrageoptionen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Umfrageoption> getUmfrageoptionenByUmfrage(Umfrage umfrage) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe aller Umfrageoptionen einer Vorstellung.
	 * </p>
	 * 
	 * @param vorstellung
	 *            Vorstellungobjekt
	 * @return ArrayList der gefundenen Umfrageoptionen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Umfrageoption> getUmfrageoptionenByVorstellung(Vorstellung vorstellung)
			throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe aller Gruppenmitglieder (Anwender) einer Gruppe.
	 * </p>
	 * 
	 * @param gruppe
	 *            Gruppenobjekt
	 * @return ArrayList der gefundenen Gruppenmitglieder
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Anwender> getGruppenmitgliederByGruppe(Gruppe gruppe) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe aller Umfragen einer Gruppe.
	 * </p>
	 * 
	 * @param gruppe
	 *            Gruppenobjekt
	 * @return ArrayList der gefundenen Umfragen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Umfrage> getUmfragenByGruppe(Gruppe gruppe) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe aller Anwender im System.
	 * </p>
	 * 
	 * @return ArrayList aller Vorstellungen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Anwender> getAllAnwender() throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe aller Vorstellungen im System.
	 * </p>
	 * 
	 * @return ArrayList aller Vorstellungen
	 * @throws IllegalArgumentException
	 */

	public ArrayList<Vorstellung> getAllVorstellungen() throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe aller Kinoketten im System.
	 * </p>
	 * 
	 * @return ArrayList aller Kinoketten
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Kinokette> getAllKinoketten() throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe aller Kinos im System
	 * </p>
	 * 
	 * @return ArrayList aller Kinos
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Kino> getAllKinos() throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe aller Filme im System
	 * </p>
	 * 
	 * @return ArrayList aller Filme
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Film> getAllFilme() throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe aller Spielzeiten im System.
	 * </p>
	 * 
	 * @return ArrayList aller Spielzeiten
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Spielzeit> getAllSpielzeiten() throws IllegalArgumentException;

	/**
	 * <p>
	 * Filtern von Vorstellungen nach Kino oder Kinokette.
	 * </p>
	 * 
	 * @param resultSet
	 *            Bisherige ArrayList der möglichen Vorstellungen
	 * @param kino
	 *            Kinoobjekt
	 * @return ArrayList der gefilterten Vorstellungen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Vorstellung> filterResultVorstellungenByKino(ArrayList<Vorstellung> resultSet, Kino kino)
			throws IllegalArgumentException;

	/**
	 * <p>
	 * Filtern von Vorstellungen nach Kino oder Kinokette.
	 * </p>
	 * 
	 * @param resultSet
	 *            Bisherige ArrayList der möglichen Vorstellungen
	 * @param kinokette
	 *            Kinoketteobjekt
	 * @return ArrayList der gefilterten Vorstellungen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Vorstellung> filterResultVorstellungenByKinokette(ArrayList<Vorstellung> resultSet, Kinokette kino)
			throws IllegalArgumentException;

	/**
	 * <p>
	 * Filtern von Vorstellungen nach einem Film.
	 * </p>
	 * 
	 * @param resultSet
	 *            Bisherige ArrayList der möglichen Vorstellungen
	 * @param film
	 *            Filmobjekt
	 * @return ArrayList der gefilterten Vorstellungen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Vorstellung> filterResultVorstellungenByFilm(ArrayList<Vorstellung> resultSet, Film film)
			throws IllegalArgumentException;

	/**
	 * <p>
	 * Filtern von Vorstellungen nach einer Spielzeit.
	 * </p>
	 * 
	 * @param resultSet
	 *            Bisherige ArrayList der möglichen Vorstellungen
	 * @param spielzeit
	 *            Spielzeitobjekt
	 * @return ArrayList der gefilterten Vorstellungen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Vorstellung> filterResultVorstellungenBySpielzeit(ArrayList<Vorstellung> resultSet,
			Spielzeit spielzeit) throws IllegalArgumentException;

	/**
	 * <p>
	 * Setzen eines Spielplans für alle Kinos einer Kinokette.
	 * </p>
	 * 
	 * @param spielplan
	 *            Spielplanobjekt
	 * @param kino
	 *            Kinoobjekt
	 * @throws IllegalArgumentException
	 */
	public void setSpielplanForKinosByKinokette(Spielplan spielplan, Kino kino) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe aller Auswahlen einer Umfrageoption
	 * </p>
	 * 
	 * @param umfrageoption
	 *            Umfrageoptionobjekt
	 * @return ArrayList der gefundenen Auswahlen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Auswahl> getAuswahlenByUmfrageoption(Umfrageoption umfrageoption) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe einer Auswahl eines Anwenders bei einer Umfrageoption
	 * </p>
	 * 
	 * @param anwender
	 *            Anwenderobjekt
	 * @param umfrageoption
	 *            Umfrageoptionobjekt
	 * @return Gefundene Auswahl
	 * @throws IllegalArgumentException
	 */
	public Auswahl getAuswahlByAnwenderAndUmfrageoption(Umfrageoption umfrageoption) throws IllegalArgumentException;

	/**
	 * <p>
	 * Berechnen des Ergebnisses der Auswahlen bei einer Umfrageoption.
	 * </p>
	 * 
	 * @param umfrageoption
	 *            Umfrageoptionobjekt
	 * @throws IllegalArgumentException
	 */
	public int berechneAuswahlenByUmfrageoption(Umfrageoption umfrageoption) throws IllegalArgumentException;

	/**
	 * <p>
	 * Starten einer Stichwahl für eine Umfrage.
	 * </p>
	 * 
	 * @param umfrage
	 *            Umfrageobjekt
	 * @return Erstellte Umfrage
	 * @throws IllegalArgumentException
	 */
	public Umfrage stichwahlStarten(Umfrage umfrage) throws IllegalArgumentException;

	/**
	 * <p>
	 * Prüfen ob ein Ergebnis gefunden wurde. Bei einem Ergebnis wird true
	 * zurückgegeben bei einer Stichwahl wird false zurückgegeben.
	 * </p>
	 * 
	 * @param umfrage
	 *            Umfrageobjekt
	 * @return Aussage ob das Ergebnis gefunden wurde = true oder nicht = false
	 *         (Stichwahl)
	 * @throws IllegalArgumentException
	 */
	public boolean ergebnisGefunden(Umfrage umfrage) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe der Umfrageoption die die meisten Stimmen bekommen hat bei einer
	 * Umfrage.
	 * </p>
	 * 
	 * @param umfrage
	 *            Umfrageobjekt
	 * @return Umfrageoption die Gewonnen hat
	 * @throws IllegalArgumentException
	 */
	public Umfrageoption umfrageGewinnerErmitteln(Umfrage umfrage) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe der Umfrageoptionen, die bei einer Stichwahl für die Umfrage
	 * verwendet werden müssen.
	 * </p>
	 * 
	 * @param umfrage
	 *            Umfrageobjekt
	 * @return ArrayList der gefunden Umfrageoptionen für die Stichwahl
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Umfrageoption> stichwahlUmfrageoptionenErmitteln(Umfrage umfrage);

	/**
	 * <p>
	 * Setzen einer Umfrage auf den Zustand Closed
	 * </p>
	 * 
	 * @param auswahl
	 *            Auswahlobjekt
	 */
	public void isClosedSetzen(Auswahl auswahl);

	/**
	 * <p>
	 * Setzen einer Umfrage auf den Zustand Open
	 * </p>
	 * 
	 * @param auswahl
	 *            Auswahlobjekt
	 */
	public void isClosedEntfernen(Auswahl auswahl);

	/**
	 * <p>
	 * Rückgabe aller geschlosssenen Umfragen, die zeitlich noch gültig sind.
	 * </p>
	 * 
	 * @param anwender
	 *            Anwenderobjekt
	 * @return ArrayList der zeitlich noch gültigen Umfragen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Umfrage> anzeigenVonClosedUmfragen() throws IllegalArgumentException;

	/**
	 * <p>
	 * Hinzufügen eines Gruppenmitglieds zu einer Gruppe.
	 * </p>
	 * 
	 * @param anwender
	 *            Gruppenmitglied
	 * @param gruppe
	 *            Gruppenobjekt
	 * @return Gruppenmitglied das jetzt in der Gruppe ist
	 * @throws IllegalArgumentException
	 */
	public Anwender gruppenmitgliedHinzufuegen(Anwender anwender, Gruppe gruppe) throws IllegalArgumentException;

	/**
	 * <p>
	 * Entfernen eines Gruppenmitglieds aus einer Gruppe.
	 * </p>
	 * 
	 * @param anwender
	 *            Gruppenmitglied
	 * @param gruppe
	 *            Gruppenobjekt
	 * @return Gruppenmitglied das nicht mehr in der Gruppe ist
	 * @throws IllegalArgumentException
	 */
	public Anwender gruppenmitgliedEntfernen(Anwender anwender, Gruppe gruppe) throws IllegalArgumentException;

	/**
	 * <p>
	 * Ein neuer Spielplan wird für eine Kinokette angelegt und anschließend in der
	 * Datenbank gespeichert.
	 * </p>
	 * 
	 * @param id
	 *            Id des Spielplans
	 * @param name
	 *            Name
	 * @param besitzerId
	 *            Kennung des Besitzers
	 * @param kinoketteId
	 *            Kennung der Kinokette für die der Spielplan erstellt wird
	 * @return fertiges Objekt
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Spielplan> erstellenSpielplaeneKinokette(String name, int kinoketteId)
			throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe ob der Name des Anwenders verfügbar ist (true)
	 * </p>
	 * 
	 * @param name
	 *            Name der benutzt werden soll
	 * @return Verfügbar (true), nicht Verfügbar (False)
	 * @throws IllegalArgumentException
	 */
	public boolean nameVerfuegbarAnwender(String name) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe ob der Name der Gruppe verfügbar ist (true)
	 * </p>
	 * 
	 * @param name
	 *            Name der benutzt werden soll
	 * @return Verfügbar (true), nicht Verfügbar (False)
	 * @throws IllegalArgumentException
	 */
	public boolean nameVerfuegbarGruppe(String name) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe ob der Name des Kinos verfügbar ist (true)
	 * </p>
	 * 
	 * @param name
	 *            Name der benutzt werden soll
	 * @return Verfügbar (true), nicht Verfügbar (False)
	 * @throws IllegalArgumentException
	 */
	public boolean nameVerfuegbarKino(String name) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe ob der Name der Kinokette verfügbar ist (true)
	 * </p>
	 * 
	 * @param name
	 *            Name der benutzt werden soll
	 * @return Verfügbar (true), nicht Verfügbar (False)
	 * @throws IllegalArgumentException
	 */
	public boolean nameVerfuegbarKinokette(String name) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe ob der Name des Spielplans verfügbar ist (true)
	 * </p>
	 * 
	 * @param name
	 *            Name der benutzt werden soll
	 * @return Verfügbar (true), nicht Verfügbar (False)
	 * @throws IllegalArgumentException
	 */
	public boolean nameVerfuegbarSpielplan(String name) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe ob der Name der Veranstaltung verfügbar ist (true)
	 * </p>
	 * 
	 * @param name
	 *            Name der benutzt werden soll
	 * @return Verfügbar (true), nicht Verfügbar (False)
	 * @throws IllegalArgumentException
	 */
	public boolean nameVerfuegbarVorstellung(String name) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rückgabe ob der Name der Umfrage verfügbar ist (true)
	 * </p>
	 * 
	 * @param name
	 *            Name der benutzt werden soll
	 * @return Verfügbar (true), nicht Verfügbar (False)
	 * @throws IllegalArgumentException
	 */
	public boolean nameVerfuegbarUmfrage(String name) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rueckgabe ob der Name der Umfrageoption verfügbar ist (true)
	 * </p>
	 * 
	 * @param name
	 *            Name der benutzt werden soll
	 * @return Verfügbar (true), nicht Verfügbar (False)
	 * @throws IllegalArgumentException
	 */
	public boolean nameVerfuegbarUmfrageoption(String name) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rueckgabe ob der Name des Films verfügbar ist (true)
	 * </p>
	 * 
	 * @param name
	 *            Name der benutzt werden soll
	 * @return Verfügbar (true), nicht Verfügbar (False)
	 * @throws IllegalArgumentException
	 */
	public boolean nameVerfuegbarFilm(String name) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rueckgabe ob der Name der Spielzeit verfügbar ist (true)
	 * </p>
	 * 
	 * @param name
	 *            Name der benutzt werden soll
	 * @return Verfügbar (true), nicht Verfügbar (False)
	 * @throws IllegalArgumentException
	 */
	public boolean nameVerfuegbarSpielzeit(String name) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rueckgabe ob der Name der Auswahl verfügbar ist (true)
	 * </p>
	 * 
	 * @param name
	 *            Name der benutzt werden soll
	 * @return Verfügbar (true), nicht Verfügbar (False)
	 * @throws IllegalArgumentException
	 */
	public boolean nameVerfuegbarAuswahl(String name) throws IllegalArgumentException;

	/**
	 * <p>
	 * Kinokette zu einem Kino hinzufuegen.
	 * </p>
	 * 
	 * @param kino
	 *            Kinoobjekt
	 * @param kinokette
	 *            Kinokettenobjekt
	 * @return Kino das hinzugefügt wurde
	 * @throws IllegalArgumentException
	 */
	public Kino kinoDerKinoketteHinzufuegen(Kino kino, Kinokette kinokette) throws IllegalArgumentException;

	/**
	 * <p>
	 * Kinokette von einem Kino entfernen.
	 * </p>
	 * 
	 * @param kino
	 *            Kinoobjekt
	 * @return Kino das entfernt wurde
	 * @throws IllegalArgumentException
	 */
	public Kino kinoketteEntfernen(Kino kino) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rueckgabe aller Spielplaene einer Kinokette.
	 * </p>
	 * 
	 * @param kinokette
	 *            Kinokettenobjekt
	 * @return ArrayList der gefundenen Spielplaene
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Spielplan> getSpielplaeneByKinokette(Kinokette kinokette) throws IllegalArgumentException;

	/**
	 * <p>
	 * Volltextsuche nach Gruppen die den Text im Namen tragen.
	 * </p>
	 * 
	 * @param text
	 *            String des Textes der zu suchen ist
	 * @return ArrayList der Gruppen die gefunden wurden
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Gruppe> volltextSucheGruppen(String text) throws IllegalArgumentException;

	/**
	 * <p>
	 * Volltextsuche nach Umfragen die den Text im Namen tragen.
	 * </p>
	 * 
	 * @param text
	 *            String des Textes der zu suchen ist
	 * @return ArrayList der Umfragen die gefunden wurden
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Umfrage> volltextSucheUmfragen(String text) throws IllegalArgumentException;

	/**
	 * <p>
	 * Volltextsuche nach Ergebnissen die den Text im Namen tragen.
	 * </p>
	 * 
	 * @param text
	 *            String des Textes der zu suchen ist
	 * @return ArrayList der Ergebnissen die gefunden wurden
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Umfrage> volltextSucheErgebnisse(String text) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rueckgabe einer Umfrage mit einer bestimmten Id.
	 * </p>
	 * 
	 * @param umfrageId
	 *            Kennung der Umfrage
	 * @return Umfragebojekt
	 * @throws IllegalArgumentException
	 */
	public Umfrage getUmfrageById(int umfrageId) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rueckgabe einer Gruppe mit einer bestimmten Id.
	 * </p>
	 * 
	 * @param gruppeId
	 *            Kennung der Gruppe
	 * @return Gruppenobjekt
	 * @throws IllegalArgumentException
	 */
	public Gruppe getGruppeById(int gruppeId) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rueckgabe der Vorstellung zu einer Umfrageoption.
	 * </p>
	 * 
	 * @param umfrageoption
	 *            Umfrageoptionobjekt
	 * @return Zugehoeriges Vorstellungsobejekt
	 */
	public Vorstellung getVorstellungByUmfrageoption(Umfrageoption umfrageoption);

	/**
	 * <p>
	 * Rueckgabe des Films einer Umfrageoption
	 * </p>
	 * 
	 * @param umfrageoption
	 *            Umfrageoptionobjekt
	 * @return Film der Umfrageoption
	 * @throws IllegalArgumentException
	 */
	public Film getFilmByUmfrageoption(Umfrageoption umfrageoption) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rueckgabe der Spielzeit einer Umfrageoption
	 * </p>
	 * 
	 * @param umfrageoption
	 *            Umfrageoptionobjekt
	 * @return Spielzeit der Umfrageoption
	 * @throws IllegalArgumentException
	 */
	public Spielzeit getSpielzeitByUmfrageoption(Umfrageoption umfrageoption) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rueckgabe des Kinos einer Umfrageoption
	 * </p>
	 * 
	 * @param umfrageoption
	 *            Umfrageoptionobjekt
	 * @return Kino der Umfrageoption
	 * @throws IllegalArgumentException
	 */
	public Kino getKinoByUmfrageoption(Umfrageoption umfrageoption) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rueckgabe der Kinokette einer Umfrageoption
	 * </p>
	 * 
	 * @param umfrageoption
	 *            Umfrageoptionobjekt
	 * @return Kinokette der Umfrageoption
	 * @throws IllegalArgumentException
	 */
	public Kinokette getKinoketteByUmfrageoption(Umfrageoption umfrageoption) throws IllegalArgumentException;

	/**
	 * <p>
	 * Alle Spielplaene ausgeben
	 * </p>
	 * 
	 * @return Alle Spielplaene
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Spielplan> getAllSpielplaene() throws IllegalArgumentException;

	/**
	 * <p>
	 * Spielplan der Umfrageoption ausgeben
	 * </p>
	 * 
	 * @param umfrageoption
	 *            Umfrageoptionobjekt
	 * @return Spielplan der Umfrageoption
	 * @throws IllegalArgumentException
	 */
	public Spielplan getSpielplanByUmfrageoption(Umfrageoption umfrageoption) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rueckgabe einer Kinokette mit einer bestimmten Id.
	 * </p>
	 * 
	 * @param kinoketteId
	 *            Kennung der Kinokette
	 * @return Gesuchte Kinokette
	 * @throws IllegalArgumentException
	 */
	public Kinokette getKinoketteById(int kinoketteId) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rueckgabe eines Films mit einer bestimmten Id.
	 * </p>
	 * 
	 * @param filmId
	 *            Kennung des Films
	 * @return Filmobjekt
	 * @throws IllegalArgumentException
	 */
	public Film getFilmById(int filmId) throws IllegalArgumentException;

	/**
	 * <p>
	 * Hinzufuegen einer Umfrageoption zu einer Gruppe
	 * </p>
	 * 
	 * @param vorstellung
	 *            Vorstellung der Umfrageoption
	 * @param umfrageFertig
	 *            Umfrageobjekt
	 * @return Erstellte Umfrageoption
	 * @throws IllegalArgumentException
	 */
	public Umfrageoption umfrageoptionHinzufuegen(Vorstellung vorstellung, Umfrage umfrageFertig)
			throws IllegalArgumentException;

	/**
	 * <p>
	 * Entfernen einer Umfrageoption von einer Gruppe
	 * </p>
	 * 
	 * @param umfrageoption
	 *            Umfrageoption die entfernt werden soll
	 * @param umfrageFertig
	 *            Umfrage zur Umfrageoption
	 * @return Vorstellung die keine Umfrageoption mehr ist in der Umfrage
	 * @throws IllegalArgumentException
	 */
	public Vorstellung umfrageoptionEntfernen(Umfrageoption umfrageoption, Umfrage umfrageFertig)
			throws IllegalArgumentException;

	/**
	 * <p>
	 * Loeschen einer Kinokette durch die ID.
	 * </p>
	 * 
	 * @param id
	 *            Kennung der Kinokette
	 * @throws IllegalArgumentException
	 */
	public void loeschenKinoketteById(int id) throws IllegalArgumentException;

	/**
	 * <p>
	 * Volltextsuche nach Kinoketten die den Text im Namen tragen.
	 * </p>
	 * 
	 * @param text
	 *            Zu suchender Text
	 * @return ArrayList der gefundenen Kinoketten
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Kinokette> volltextSucheKinoketten(String text) throws IllegalArgumentException;

	/**
	 * <p>
	 * Volltextsuche nach Kinos die den Text im Namen tragen.
	 * </p>
	 * 
	 * @param text
	 *            Zu suchender Text
	 * @return ArrayList der gefundenen Kinos
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Kino> volltextSucheKinos(String text) throws IllegalArgumentException;

	/**
	 * <p>
	 * Volltextsuche nach Spielplaenen die den Text im Namen tragen.
	 * </p>
	 * 
	 * @param text
	 *            Zu suchender Text
	 * @return ArrayList der gefundenen Spielplaene
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Spielplan> volltextSucheSpielplaene(String text) throws IllegalArgumentException;

	/**
	 * <p>
	 * Volltextsuche nach Ergebnissen die den Text im Namen tragen.
	 * </p>
	 * 
	 * @param text
	 *            Zu suchender Text
	 * @return ArrayList der gefundenen Spielzeiten
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Spielzeit> volltextSucheSpielzeit(String text) throws IllegalArgumentException;

	/**
	 * <p>
	 * Volltextsuche nach Ergebnissen die den Text im Namen tragen.
	 * </p>
	 * 
	 * @param text
	 *            Zu suchender Text
	 * @return ArrayList der gefundenen Filme
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Film> volltextSucheFilm(String text) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rueckgabe einer Spielzeit mit einer bestimmten Id.
	 * </p>
	 * 
	 * @param spielzeitId
	 *            Kennung der Spielzeit
	 * @return Spuielzeitobjekt
	 * @throws IllegalArgumentException
	 */
	public Spielzeit getSpielzeitById(int spielzeitId) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rueckgabe des Kinos einer Vorstellung
	 * </p>
	 * 
	 * @param vorstellung
	 *            Verstellungsobjekt
	 * @return Zugehoeriges Kinoobjekt
	 * @throws IllegalArgumentException
	 */
	public Kino getKinoByVorstellung(Vorstellung vorstellung) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rueckgabe des Kinos einer Vorstellung
	 * </p>
	 * 
	 * @param vorstellung
	 *            Vorstellungsobkjekt
	 * @return Zugehoeriges Kinokettenobjekt
	 * @throws IllegalArgumentException
	 */
	public Kinokette getKinoketteByVorstellung(Vorstellung vorstellung) throws IllegalArgumentException;

	public Vorstellung getVorstellungById(int vorstellungId) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rueckgabe einer Gruppe mit einem bestimmten Namen
	 * </p>
	 * 
	 * @param name
	 *            Name der gesuchten Gruppe
	 * @return Gruppenobjekt
	 * @throws IllegalArgumentException
	 */
	public Gruppe getGruppeByName(String name) throws IllegalArgumentException;

	/**
	 * <p>
	 * Eine neue Stichwahl wird angelegt und anschließend in der Datenbank
	 * gespeichert.
	 * </p>
	 * 
	 * @param name
	 *            Name der Umfrage
	 * @param gruppenId
	 * @return
	 * @throws IllegalArgumentException
	 */

	public Umfrage erstellenStichwahl(String name, int gruppenId) throws IllegalArgumentException;

	/**
	 * <p>
	 * Updaten einer Umfrage mitsamt der Veraenderungen der Gruppenmitglieder
	 * </p>
	 * 
	 * @param gruppe
	 *            Gruppe die Geaendert wird
	 * @param gruppenmitglieder
	 *            Aktuelle Gruppenmitglieder
	 * @throws IllegalArgumentException
	 */
	public Gruppe updateGruppe(Gruppe gruppe, ArrayList<Anwender> gruppenmitglieder) throws IllegalArgumentException;

	public void sinnloserCallback() throws IllegalArgumentException;

	/**
	 * <p>
	 * Erstellen von Auswahlen aus einem Array, mit vergleich, löschung und update
	 * bestehnder Auswahlen
	 * </p>
	 * 
	 * @param zuErstellendeAuswahlen
	 *            ArrayList der zu erstellenden Auswahlen
	 * @param alteAuswahlen
	 *            ArrayList der zu bisherigen Auswahlen
	 * @return Zugehörige Umfrage
	 * @throws IllegalArgumentException
	 */
	public Umfrage auswahlenErstellen(ArrayList<Auswahl> zuErstellendeAuswahlen, ArrayList<Auswahl> alteAuswahlen,
			int size, Umfrage umfrage) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rueckgabe eines Film der durch den Namen gesucht wird.
	 * </p>
	 * 
	 * @param name
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Film getFilmByName(String name) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rueckgabe eines Kinos der durch den Namen gesucht wird.
	 * </p>
	 * 
	 * @param name
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Kino getKinoByName(String name) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rueckgabe einer Kinokette der durch den Namen gesucht wird.
	 * </p>
	 * 
	 * @param name
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Kinokette getKinoketteByName(String name) throws IllegalArgumentException;

	/**
	 * <p>
	 * Rueckgabe einer Spielzeit der durch den Namen gesucht wird.
	 * </p>
	 * 
	 * @param name
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Spielzeit getSpielzeitByName(String name) throws IllegalArgumentException;

	/**
	 * <p>
	 * Updaten einer Umfrage mitsamt der Veränderungen der Umfrageoptionen
	 * </p>
	 * 
	 * @param umfrage
	 *            Umfrageobjekt
	 * @param umfrageoptionen
	 *            Zu erstellende Umfrageobjete
	 * @return gespeichertes Umfrageobjekt
	 * @throws IllegalArgumentException
	 */
	public Umfrage updateUmfrage(Umfrage umfrage, ArrayList<Vorstellung> umfrageoptionen)
			throws IllegalArgumentException;

	/**
	 * <p>
	 * Es wird geprueft ob der Boolean isVoted der Klasse Umfrage noch true sein
	 * darf, nach löschen der Auswahl.
	 * </p>
	 * 
	 * @param auswahl
	 *            Auswahlobjekt
	 * @throws IllegalArgumentException
	 */
	public void isVotedEntfernen(Auswahl auswahl) throws IllegalArgumentException;

}
