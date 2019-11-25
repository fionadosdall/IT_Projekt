package de.hdm.softwareProjekt.kinoPlaner.shared;

import java.util.ArrayList;
import java.util.Calendar;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

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
 * Das Interface <code>Kinoplaner</code> ist die synchrone Schnittstelle f�r die RPC f�hige Implementierung
 * der Klasse {@link KinoplanerImpl}. Das asynchrone Pendant hierzu ist das Interface {@link KinoplanerAsync}.
 * Das Interface erweitert das Interface {@link RemoteService}.
 * 
 * Der <code>@RemoteServiceRealtivPath("administartion")</code> wird bei der Adressierung des aus der zugeh�rigen
 * KinoplanerImpl entstehenden Servelt-Kompilats verwendet. Hier wird ein Teil der URL des Servlets angegeben.
 * </p>
 */
@RemoteServiceRelativePath("administration")
public interface Kinoplaner extends RemoteService {
	
	/** 
	 * <p>
	 * Da derzeit kein anderer Konstruktor als der No-Argument Konstruktor aufgerufen werden kann, ist 
	 * eine seperate Instanzenmethode notwendig, welche direkt nach 
	 * <code>GWT.create(Klassenname.class)</code> aufgerufen wird und die Initiialisierung bewirkt. Sie 
	 * wird f�r jede Instanz von <code>KinoplanerImpl</code> aufgerufen. Bei der Initiialisierung wird 
	 * ein vollst�ndiger Satz Mapper erzeugt, so dass die KLasse mit der Datenbank kommunizieren kann.
	 * </p>
	 * 
	 * @see #KinoplanerImpl()
	 * @throws IllegalArgumentException
	 */
	public void init() throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe des Anwenders, in dessen Ansicht die Website ausgef�hrt wird.
	 * </p>
	 * @return Anwender der gerade die Website nutzt
	 * @throws IllegalArgumentException
	 */
	public Anwender getAnwender()  throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Setzen des Anwenders, in dessen Ansicht die Website ausgef�hrt wird.
	 * </p>
	 * @param anwender Anwender der die Seite nutzt
	 * @throws IllegalArgumentException
	 */
	public void setAnwender(Anwender anwender)  throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Ein neuer Anwender wird angelegt und anschlie�end in der Datenbank gespeichert.
	 * </p>
	 * @param id Kennung
	 * @param name Nickname
	 * @param gmail EMail Account
	 * @return erstelltes Anwender Objekt
	 * @throws IllegalArgumentException
	 */
	public Anwender erstellenAnwender(int id, String name, String gmail) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Eine neue Gruppe wird angelegt und anschlie�end in der Datenbank gespeichert.
	 * </p>
	 * @param id Kennung
	 * @param name Objektname
	 * @param besiterId Kennung des Besitzer
	 * @return erstelltes Gruppe Objekt
	 * @throws IllegalArgumentException
	 */
	public Gruppe erstellenGruppe(int id, String name, int besitzerId) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Ein neues Kino wird angelegt und anschlie�end in der Datenbank gespeichert. Hierbei 
	 * mit der Verkn�pfung zur Kinokette.
	 * </p>
	 * @param id Kennung
	 * @param name Objektname
	 * @param besiterId Kennung des Besitzer
	 * @param plz Postleitzahl
	 * @param stadt Stadt
	 * @param strassse Stra�e
	 * @param hausnummer Hausnummer
	 * @param kinoketteId Kennung der Kinokette
	 * @return erstelltes Kino Objekt
	 * @throws IllegalArgumentException
	 */
	public Kino erstellenKino(int id, String name, int besitzerId, int plz, String stadt, String strassse, String hausnummer, int kinokettenId) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Ein neues Kino wird angelegt und anschlie�end in der Datenbank gespeichert. Hierbei 
	 * ohne die Verkn�pfung zur Kinokette.
	 * </p>
	 * @param id Kennung
	 * @param name Objektname
	 * @param besiterId Kennung des Besitzer
	 * @param plz Postleitzahl
	 * @param stadt Stadt
	 * @param strassse Stra�e
	 * @param hausnummer Hausnummer
	 * @return erstelltes Kino Objekt
	 * @throws IllegalArgumentException
	 */
	public Kino erstellenKino(int id, String name, int besitzerId, int plz, String stadt, String strassse, String hausnummer) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Eine neue Kinokette wird angelegt und anschlie�end in der Datenbank gespeichert.
	 * </p>
	 * @param id Kennung
	 * @param name Objektname
	 * @param besiterId Kennung des Besitzer
	 * @param sitz Sitz des Unternehmens
	 * @param website Webseite
	 * @return erstelltes Kinoketten Objekt
	 * @throws IllegalArgumentException
	 */
	public Kinokette erstellenKinokette(int id, String name, int besitzerId,  String sitz, String website) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Ein neuer Spielplan wird angelegt und anschlie�end in der Datenbank gespeichert.
	 * </p>
	 * @param id Kennung
	 * @param name Objektname
	 * @param besiterId Kennung des Besitzer
	 * @param kinoId Kennung des Kinos
	 * @return erstelltes Spielplan Objekt
	 * @throws IllegalArgumentException
	 */
	public Spielplan erstellenSpielplan(int id, String name, int besitzerId,  int kinoId) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Eine neue Vorstellung wird angelegt und anschlie�end in der Datenbank gespeichert.
	 * </p>
	 * @param id Kennung
	 * @param name Objektname
	 * @param spielplanId Kennung des Spielplans
	 * @param spielzeitId Kennung der Spielzeit
	 * @param filmId Kennung des Films
	 * @return erstelltes Vorstellung Objekt
	 * @throws IllegalArgumentException
	 */
	public Vorstellung erstellenVorstellung(int id, String name, int spielplanId, int spielzeitId, int filmId) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Eine neue Umfrage wird angelegt und anschlie�end in der Datenbank gespeichert.
	 * </p>
	 * @param id Kennung
	 * @param name Objektname
	 * @param besitzerId Kennung des Besitzers
	 * @param gruppeId Kennung der Gruppe
	 * @return erstelltes Umfrage Objekt
	 * @throws IllegalArgumentException
	 */
	public Umfrage erstellenUmfrage(int id, String name, int besitzerId, int gruppenId) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Eine neue Umfrageoption wird angelegt und anschlie�end in der Datenbank gespeichert.
	 * </p>
	 * @param id Kennung
	 * @param name Objektname
	 * @param umfrageId Kennung der Umfrage
	 * @param vorstellungId Kennung der Vorstellung
	 * @return erstelltes Umfrageoption Objekt
	 * @throws IllegalArgumentException
	 */
	public Umfrageoption erstellenUmfrageoption(int id, String name, int umfrageId, int vorstellungId) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Ein neuer Film wird angelegt und anschlie�end in der Datenbank gespeichert.
	 * </p>
	 * @param id Kennung
	 * @param name Objektname
	 * @param besitzerId Kennung des besitzer
	 * @param beschreibung Beschreibung des Films
	 * @param bewertung Bewertung des Films
	 * @return erstelltes Film Objekt
	 * @throws IllegalArgumentException
	 */
	public Film erstellenFilm(int id, String name, int besitzerId, String beschreibung, int bewertung) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Ein Spielzeit wird angelegt und anschlie�end in der Datenbank gespeichert.
	 * </p>
	 * @param id Kennung
	 * @param name Objektname
	 * @param besitzerId Kennung des besitzer
	 * @param zeit Startzeitpunkt
	 * @return erstelltes Spielzeit Objekt
	 * @throws IllegalArgumentException
	 */
	public Spielzeit erstellenSpielzeit(int id, String name, int besitzerId, Calendar zeit) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Eine neue Auswahl wird angelegt,die zugeh�rige Umfrage als gevotet markiert und anschlie�end 
	 * die Auswahl in der Datenbank gespeichert.
	 * </p>
	 * @param id Kennung
	 * @param name Objektname
	 * @param besitzerId Kennung des besitzer
	 * @param voting Voting
	 * @param umfrageoptionId Kennung der Umfrageoption
	 * @return erstelltes Auswahl Objekt
	 * @throws IllegalArgumentException
	 */
	public Auswahl erstellenAuswahl(int id, String name, int besitzerId, int voting, int umfrageoptionId) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Es wird gepr�ft ob der Boolean isVoted der Klasse Umfrage bereits auf True gesetzt wurde, da diese gevotet wurde. 
	 * Ist dies nicht der Fall so wird der Boolean auf True gesetzt.
	 * </p>
	 * @param auswahl Auswahlobjekt
	 */
	public void isVoted(Auswahl auswahl);
	
	/**
	 * <p>
	 * Speichern eines Anwenders.
	 * </p>
	 * @param anwender Anwenderobjekt
	 * @throws IllegalArgumentException
	 */
	public void speichern(Anwender anwender) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Speichern einer Gruppe.
	 * </p>
	 * @param gruppe Gruppeobjekt
	 * @throws IllegalArgumentException
	 */
	public void speichern(Gruppe gruppe) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Speichern eines Kinos.
	 * </p>
	 * @param kino Kinoobjekt
	 * @throws IllegalArgumentException
	 */
	public void speichern(Kino kino) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Speichern einer Kinokette.
	 * </p>
	 * @param kinokette Kinokettenobjekt
	 * @throws IllegalArgumentException
	 */
	public void speichern(Kinokette kinokette) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Speichern eines Spielplans.
	 * </p>
	 * @param spielplan Spielplanobjekt
	 * @throws IllegalArgumentException
	 */
	public void speichern(Spielplan spielplan) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Speichern einer Vorstellung.
	 * </p>
	 * @param vorstellung Vorstellungobjekt
	 * @throws IllegalArgumentException
	 */
	public void speichern(Vorstellung vorstellung) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Speichern einer Umfrage.
	 * </p>
	 * @param umfrage Umfrageobjekt
	 * @throws IllegalArgumentException
	 */
	public void speichern(Umfrage umfrage) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Speichern einer Umfrageoption.
	 * </p>
	 * @param umfrageoption Umfrageoptionobjekt
	 * @throws IllegalArgumentException
	 */
	public void speichern(Umfrageoption umfrageoption) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Speichern eines Films.
	 * </p>
	 * @param film Filmobjekt
	 * @throws IllegalArgumentException
	 */
	public void speichern(Film film) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Speichern einer Spielzeit.
	 * </p>
	 * @param spielzeit Spielzeitobjekt
	 * @throws IllegalArgumentException
	 */
	public void speichern(Spielzeit spielzeit) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Speichern einer Auswahl.
	 * </p>
	 * @param auswahl Auswahlobjekt
	 * @throws IllegalArgumentException
	 */
	public void speichern(Auswahl auswahl) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * L�schen eines Anwenders mit L�schweitergabe.
	 * </p>
	 * @param anwender Anwenderobjekt
	 * @throws IllegalArgumentException
	 */
	public void l�schen(Anwender anwender) throws IllegalArgumentException;

	/**
	 * <p>
	 * L�schen einer Gruppe mit L�schweitergabe.
	 * </p>
	 * @param gruppe Gruppeobjekt
	 * @throws IllegalArgumentException
	 */
	public void l�schen(Gruppe gruppe) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * L�schen eines Kinos mit L�schweitergabe.
	 * </p>
	 * @param kino Kinoobjekt
	 * @throws IllegalArgumentException
	 */
	public void l�schen(Kino kino) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * L�schen einer Kinokette mit L�schweitergabe.
	 * </p>
	 * @param kinokette Kinokettenobjekt
	 * @throws IllegalArgumentException
	 */
	public void l�schen(Kinokette kinokette) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * L�schen eines Spielplans mit L�schweitergabe.
	 * </p>
	 * @param spielplan Spielplanobjekt
	 * @throws IllegalArgumentException
	 */
	public void l�schen(Spielplan spielplan) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * L�schen einer Vorstellung mit L�schweitergabe.
	 * </p>
	 * @param vorstellung Vorstellungobjekt
	 * @throws IllegalArgumentException
	 */
	public void l�schen(Vorstellung vorstellung) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * L�schen einer Umfrage mit L�schweitergabe.
	 * </p>
	 * @param umfrage Umfrageobjekt
	 * @throws IllegalArgumentException
	 */
	public void l�schen(Umfrage umfrage) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * L�schen einer Umfrageoption mit L�schweitergabe.
	 * </p>
	 * @param umfrageoption Umfrageoptionobjekt
	 * @throws IllegalArgumentException
	 */
	public void l�schen(Umfrageoption umfrageoption) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * L�schen eines Films mit L�schweitergabe.
	 * </p>
	 */
	public void l�schen(Film film) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * L�schen einer Spielzeit mit L�schweitergabe.
	 * </p>
	 * @param film Filmobjekt
	 * @throws IllegalArgumentException
	 */
	public void l�schen(Spielzeit spielzeit) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * L�schen einer Auswahl.
	 * </p>
	 * @param spielzeit Spielzeitobjekt
	 * @throws IllegalArgumentException
	 */
	public void l�schen(Auswahl auswahl) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe des Anwenders mit einer bestimmten Id.
	 * </p>
	 * @param anwenderId Kennung des Anwenders
	 * @return gefundenes Anwenderobjekt
	 * @throws IllegalArgumentException
	 */
	public Anwender getAnwenderById(int anwenderId) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe eines Spielplans mit einer bestimmten Id.
	 * </p>
	 * @param spielplanId Kennung des Spielplans
	 * @return gefundenes Spielplanobjekt
	 * @throws IllegalArgumentException
	 */
	public Spielplan getSpielplanById(int spielplanId) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe eines Kinos mit einer bestimmten Id.
	 * </p>
	 * @param kinoId Kennung des Kinos
	 * @return gefundenes Kinoobjekt
	 * @throws IllegalArgumentException
	 */
	public Kino getKinoById(int kinoId) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe aller Gruppen in denen der Anwender Mitglied ist.
	 * </p>
	 * @param anwender Anwenderobjekt
	 * @return ArrayListe der gefundenen Gruppen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Gruppe> getGruppenByAnwender(Anwender anwender) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe aller Gruppen die einem bestimmten Anwender geh�ren.
	 * </p>
	 * @param anwender Anwenderobjekt
	 * @return ArrayList der gefundenen Gruppen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Gruppe> getGruppenByAnwenderOwner(Anwender anwender) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe aller Umfragen eines Anwenders.
	 * </p>
	 * @param anwender Anwenderobjekt
	 * @return ArrayList der gefundenen Umfragen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Umfrage> getUmfragenByAnwender(Anwender anwender) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe aller Umfragen die der Anwender besitzt.
	 * </p>
	 * @param anwender Anwenderobjekt
	 * @return ArrayList der gefundenen Umfragen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Umfrage> getUmfragenByAnwenderOwner(Anwender anwender) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe aller geschlossenen Umfragen des Anwenders
	 * </p>
	 * @param anwender Anwenderobjekt
	 * @return ArrayList der gefundenen Umfragen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Umfrage> getClosedUmfragenByAnwender(Anwender anwender) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe aller Kinoketten die der Anwender besitzt.
	 * </p>
	 * @param anwender Anwenderobjekt
	 * @return ArrayList der gefundenen Kinoketten
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Kinokette> getKinokettenByAnwenderOwner(Anwender anwender) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe aller Kinos die der Anwender besitzt.
	 * </p>
	 * @param anwender Anwenderobjekt
	 * @return ArrayList der gefundenen Kinos
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Kino> getKinosByAnwenderOwner(Anwender anwender) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe aller Kinos die zu einer Kinokette geh�ren.
	 * </p>
	 * @param kinokette Kinokettenobjekt
	 * @return ArrayList der gefundenen Kinos
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Kino> getKinosByKinoketteId(Kinokette kinokette) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe aller Kinos die zu einer Kinokette geh�ren.
	 * </p>
	 * @param akinoketteId Kennung der Kinokette
	 * @return ArrayList der gefundenen Kinos
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Kino> getKinosByKinoketteId(int kinoketteId) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe aller Spielpl�ne die einer Anwender besitzt.
	 * </p>
	 * @param anwender Anwenderobjekt
	 * @return ArrayList der gefundenen Spielpl�ne
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Spielplan> getSpielplaeneByAnwenderOwner(Anwender anwender) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe aller Spielpl�ne eines Kinos.
	 * </p>
	 * @param kino Kinoobjekt
	 * @return ArrayList der gefundenen Spielpl�ne
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Spielplan> getSpielplaeneByKino(Kino kino) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe aller Filme die ein Anwender besitzt.
	 * </p>
	 * @param anwender Anwenderobjekt
	 * @return ArrayList der gefundenen Filme
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Film> getFilmeByAnwenderOwner(Anwender anwender) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe aller Spielzeiten die ein Anwender besitzt.
	 * </p>
	 * @param anwender Anwenderobjekt
	 * @return ArrayList der gefundenen Spielzeiten
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Spielzeit> getSpielzeitenByAnwenderOwner(Anwender anwender) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe der Auswahlen die ein Anwender besitzt.
	 * </p>
	 * @param anwender Anwenderobjekt
	 * @return ArrayList der gefundenen Auswahlen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Auswahl> getAuswahlenByAnwenderOwner(Anwender anwender) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe aller Vorstellungen eines Spielplans.
	 * </p>
	 * @param spielplan Spielplanobjekt
	 * @return ArrayList der gefundenen Vorstellungen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Vorstellung> getVorstellungenBySpielplan(Spielplan spielplan) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe eines Anwenders der durch den Namen gesucht wird.
	 * </p>
	 * @param name Name des Anwenders
	 * @return gefundenes Anwenderobjekt
	 * @throws IllegalArgumentException
	 */
	public Anwender getAnwenderByName(String name) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe aller Umfrageoptionen einer Umfrage.
	 * </p>
	 * @param umfrage Umfrageobjekt
	 * @return ArrayList der gefundenen Umfrageoptionen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Umfrageoption> getUmfrageoptionenByUmfrage(Umfrage umfrage) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe aller Umfrageoptionen einer Vorstellung.
	 * </p>
	 * @param vorstellung Vorstellungobjekt
	 * @return ArrayList der gefundenen Umfrageoptionen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Umfrageoption> getUmfrageoptionenByVorstellung(Vorstellung vorstellung) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe aller Gruppenmitglieder (Anwender) einer Gruppe.
	 * </p>
	 * @param gruppe Gruppenobjekt
	 * @return ArrayList der gefundenen Gruppenmitglieder
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Anwender> getGruppenmitgliederByGruppe(Gruppe gruppe) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe aller Umfragen einer Gruppe.
	 * </p>
	 * @param gruppe Gruppenobjekt
	 * @return ArrayList der gefundenen Umfragen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Umfrage> getUmfragenByGruppe(Gruppe gruppe) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe aller Vorstellungen im System.
	 * </p>
	 * @return ArrayList aller Vorstellungen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Vorstellung> getAllVorstellungen() throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe aller Kinoketten im System.
	 * </p>
	 * @return ArrayList aller Kinoketten
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Kinokette> getAllKinoketten() throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe aller Kinos im System
	 * </p>
	 * @return ArrayList aller Kinos
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Kino> getAllKinos() throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe aller Filme im System
	 * </p>
	 * @return ArrayList aller Filme
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Film> getAllFilme() throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe aller Spielzeiten im System.
	 * </p>
	 * @return ArrayList aller Spielzeiten
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Spielzeit> getAllSpielzeiten() throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Filtern von Vorstellungen nach Kino oder Kinokette.
	 * </p>
	 * @param resultSet Bisherige ArrayList der m�glichen Vorstellungen
	 * @param kino Kinoobjekt
	 * @return ArrayList der gefilterten Vorstellungen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Vorstellung> filterResultVorstellungenByKinoOrKinokette(ArrayList<Vorstellung> resultSet, Kino kino) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Filtern von Vorstellungen nach Kino oder Kinokette.
	 * </p>
	 * @param resultSet Bisherige ArrayList der m�glichen Vorstellungen
	 * @param kinokette Kinoketteobjekt
	 * @return ArrayList der gefilterten Vorstellungen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Vorstellung> filterResultVorstellungenByKinoOrKinokette(ArrayList<Vorstellung> resultSet, Kinokette kino) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Filtern von Vorstellungen nach einem Film.
	 * </p>
	 * @param resultSet Bisherige ArrayList der m�glichen Vorstellungen
	 * @param film Filmobjekt
	 * @return ArrayList der gefilterten Vorstellungen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Vorstellung> filterResultVorstellungenByFilm(ArrayList<Vorstellung> resultSet, Film film) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Filtern von Vorstellungen nach einer Spielzeit.
	 * </p>
	 * @param resultSet Bisherige ArrayList der m�glichen Vorstellungen
	 * @param spielzeit Spielzeitobjekt
	 * @return ArrayList der gefilterten Vorstellungen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Vorstellung> filterResultVorstellungenBySpielzeit(ArrayList<Vorstellung> resultSet, Spielzeit spielzeit) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Setzen eines Spielplans f�r alle Kinos einer Kinokette.
	 * </p>
	 * @param spielplan Spielplanobjekt
	 * @param kino Kinoobjekt
	 * @throws IllegalArgumentException
	 */
	public void setSpielplanForKinosByKinokette(Spielplan spielplan, Kino kino) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe aller Auswahlen einer Umfrageoption
	 * </p>
	 * @param umfrageoption Umfrageoptionobjekt
	 * @return ArrayList der gefundenen Auswahlen
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Auswahl> getAuswahlenByUmfrageoption(Umfrageoption umfrageoption) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe einer Auswahl eines Anwenders bei einer Umfrageoption
	 * </p>
	 * @param anwender Anwenderobjekt
	 * @param umfrageoption Umfrageoptionobjekt
	 * @return Gefundene Auswahl
	 * @throws IllegalArgumentException
	 */
	public Auswahl getAuswahlByAnwenderAndUmfrageoption(Anwender anwender, Umfrageoption umfrageoption) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Berechnen des Ergebnisses der Auswahlen bei einer Umfrageoption.
	 * </p>
	 * @param umfrageoption Umfrageoptionobjekt
	 * @throws IllegalArgumentException
	 */
	public void berechneAuswahlenByUmfrageoption(Umfrageoption umfrageoption)throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Starten einer Stichwahl f�r eine Umfrage.
	 * </p>
	 * @param umfrage Umfrageobjekt
	 * @return Erstellte Umfrage
	 * @throws IllegalArgumentException
	 */
	public Umfrage stichwahlStarten(Umfrage umfrage) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Pr�fen ob ein Ergebnis gefunden wurde. Bei einem Ergebnis wird true zur�ckgegeben 
	 * bei einer Stichwahl wird false zur�ckgegeben.
	 * </p>
	 * @param umfrage Umfrageobjekt
	 * @return Aussage ob das Ergebnis gefunden wurde = true oder nicht = false (Stichwahl)
	 * @throws IllegalArgumentException
	 */
	public boolean ergebnisGefunden(Umfrage umfrage)throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe der Umfrageoption die die meisten Stimmen bekommen hat bei einer Umfrage.
	 * </p>
	 * @param umfrage Umfrageobjekt
	 * @return Umfrageoption die Gewonnen hat
	 * @throws IllegalArgumentException
	 */
	public Umfrageoption umfrageGewinnerErmitteln(Umfrage umfrage) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * R�ckgabe der Umfrageoptionen, die bei einer Stichwahl f�r die Umfrage verwendet werden 
	 * m�ssen.
	 * </p>
	 * @param umfrage Umfrageobjekt
	 * @return ArrayList der gefunden Umfrageoptionen f�r die Stichwahl
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Umfrageoption> stichwahlUmfrageoptionenErmitteln(Umfrage umfrage);
	
	/**
	 * <p>
	 * Setzen einer Umfrage auf den Zustand Closed
	 * </p>
	 * @param auswahl Auswahlobjekt
	 */
	public void isClosedSetzen(Auswahl auswahl);
	
	/**
	 * <p>
	 * Setzen einer Umfrage auf den Zustand Open
	 * </p>
	 * @param auswahl Auswahlobjekt
	 */
	public void isClosedEntfernen(Auswahl auswahl);
	
	
	
}

