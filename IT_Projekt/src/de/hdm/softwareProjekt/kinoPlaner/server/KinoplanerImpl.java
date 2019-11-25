package de.hdm.softwareProjekt.kinoPlaner.server;

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
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Auswahl;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;
import de.hdm.softwareProjekt.kinoPlaner.shared.Kinoplaner;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielplan;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrageoption;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * <p>
 * Die Klasse KinoplanerImpl serverseitige Implementierung des RPC Service und damit des Interface 
 * <code>Kinoplaner</code>. In den Methoden dieser Klasse ist die gesamte Applikationslogik der 
 * Website enthalten. Sie sorgt f�r eine dauerhafte Konsitenz der Methoden und Daten. Die Klasse 
 * bedient sich Mapper Klassen, welche der Datenbank-Schicht angeh�ren. Diese bilden die Sicht der 
 * Applikationslogik auf der realtionalen Datenbank ab.
 * </p>
 * <p>
 * Die Klasse steht mit weiteren Datentypen in Verbindung:
 * <ol>
 * <li>{@link Kinoplaner}: Ist das lokale serverseitige Interface, in welchem die dem System 
 * verf�gbaren Funktionen deklariert sind.</li>
 * <li>{@link KinoplanerAsync}: Mit <code>KinplanerImpl</code> und der <code>Kinplaner</code> wird
 * die serverseitige Sicht der Applikationslogik abgebildet, welche funktional vollst�ndig synchron 
 * abl�uft. F�r die clientseiteige Sicht m�ssen asynchrone Aufrufe erm�glicht werden, wozu dieses 
 * zus�tzliche Interface dient.Das Async Interface wird durch das Google Plugin semiautomatisch bei 
 * der Erstellung und Pflege unterst�tzt.</li>
 * <li>{@link RemoteServiceServlet}: Ist eine Klasse serverseitig instantiierbar und clientseitig 
 * �ber GWT RPC nutzbar, so muss sie die Klasse <code>RemoteServiceServlet</code> implementieren. 
 * Diese ist die funktionale Basis f�r die Anbindung an die Runtime des GWT RPC-Mechanismus.</li>
 * </ol>
 * </p>
 * <p>
 * Jede der folgenden Methode die mithilfe von GWT RPC aufgerufen werden kann, muss 
 * <code>throws IllegalArgumentException</code> in der Methodendeklaration aufweisen. Das Bedeutet 
 * das diese Methoden in der Lage sind eine Instant von {@link IllegalArgument Exception} auszuwerfen. 
 * Damit k�nnen Probleme von der serverseite einfach auf die clientseite transportiert und dann
 * mit einem Catch-Block abgearbeitet werden.
 * </p>
 *  
 * @see Kinoplaner
 * @see KinoplanerAsync
 * @see RemoteServiceServlet
 */

@SuppressWarnings("serial")
public class KinoplanerImpl extends RemoteServiceServlet implements Kinoplaner {
	
	/**
	 * <p>
	 * Hier wird der Anwender gespeichert, in dessen Ansicht die Website momentan ausgef�hrt wird.
	 * </p>
	 */
	private Anwender anwender = null;
	
	/**
	 * <p>
	 * Referenziert auf den Datenbankmapper, welcher Anwender in der Datenbank verwaltet.
	 * </p>
	 */
	private AnwenderMapper anwenderMapper = null;
	
	/**
	 * <p>
	 * Referenziert auf den Datenbankmapper, welcher Gruppen in der Datenbank verwaltet.
	 * </p>
	 */
	private GruppeMapper gruppeMapper = null;
	
	/**
	 * <p>
	 * Referenziert auf den Datenbankmapper, welcher Umfragen in der Datenbank verwaltet.
	 * </p>
	 */
	private UmfrageMapper umfrageMapper = null;
	
	/**
	 * <p>
	 * Referenziert auf den Datenbankmapper, welcher Spielpl�ne in der Datenbank verwaltet.
	 * </p>
	 */
	private SpielplanMapper spielplanMapper = null;
	
	/**
	 * <p>
	 * Referenziert auf den Datenbankmapper, welcher Vorstellungen in der Datenbank verwaltet.
	 * </p>
	 */
	private VorstellungMapper vorstellungMapper = null;
	
	/**
	 * <p>
	 * Referenziert auf den Datenbankmapper, welcher Kinos in der Datenbank verwaltet.
	 * </p>
	 */
	private KinoMapper kinoMapper = null;
	
	/**
	 * <p>
	 * Referenziert auf den Datenbankmapper, welcher Kinoketten in der Datenbank verwaltet.
	 * </p>
	 */
	private KinoketteMapper kinoketteMapper = null;
	
	/**
	 * <p>
	 * Referenziert auf den Datenbankmapper, welcher Filme in der Datenbank verwaltet.
	 * </p>
	 */
	private FilmMapper filmMapper = null;
	
	/**
	 * <p>
	 * Referenziert auf den Datenbankmapper, welcher Spielzeiten in der Datenbank verwaltet.
	 * </p>
	 */
	private SpielzeitMapper spielzeitMapper = null;
	
	/**
	 * <p>
	 * Referenziert auf den Datenbankmapper, welcher Umfrageoptionenin der Datenbank verwaltet.
	 * </p>
	 */
	private UmfrageoptionMapper umfrageoptionMapper = null;
	
	/**
	 * <p>
	 * Referenziert auf den Datenbankmapper, welcher Auswahlen in der Datenbank verwaltet.
	 * </p>
	 */
	private AuswahlMapper auswahlMapper = null;
	
	/**
	 * <p>
	 * Hier wird ein <code>RemoteServiceServlet</code> unter GWT mithilfe von 
	 * <code>GWT.create(Klassenname.class)</code> Client-seitig erzeugt. Dabei wird der folgende 
	 * No-Argument Konstruktor ben�tigt. Ein anderer Konstrukor kann derzeit nicht aufgerufen 
	 * werden. 
	 * </p>
	 * 
	 * @see #init()
	 * @throws IllegalArgumentException
	 */
	public KinoplanerImpl () throws IllegalArgumentException {
		
	}
	
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
	@Override
	public void init() throws IllegalArgumentException {
		// Die Mapper werden Initiiert.
		this.anwenderMapper = AnwenderMapper.anwenderMapper();
		this.gruppeMapper = GruppeMapper.gruppeMapper();
		this.umfrageMapper = UmfrageMapper.umfrageMapper();
		this.spielplanMapper = SpielplanMapper.spielplanMapper();
		this.vorstellungMapper = VorstellungMapper.vorstellungMapper();
		this.kinoMapper = KinoMapper.kinoMapper();
		this.kinoketteMapper = KinoketteMapper.kinoketteMapper();
		this.filmMapper = FilmMapper.filmMapper();
		this.spielzeitMapper = SpielzeitMapper.spielzeitMapper();
		this.umfrageoptionMapper = UmfrageoptionMapper.umfrageoptionMapper();
		this.auswahlMapper = AuswahlMapper.auswahlMapper();
		
	}
	/**
	 * <p>
	 * R�ckgabe des Anwenders, in dessen Ansicht die Website ausgef�hrt wird.
	 * </p>
	 */
	@Override
	public Anwender getAnwender()  throws IllegalArgumentException {
		return this.anwender;
	}
	
	/**
	 * <p>
	 * Setzen des Anwenders, in dessen Ansicht die Website ausgef�hrt wird.
	 * </p>
	 */
	@Override
	public void setAnwender(Anwender anwender)  throws IllegalArgumentException {
		this.anwender = anwender;
	}
	
	/**
	 * <p>
	 * Ein neuer Anwender wird angelegt und anschlie�end in der Datenbank gespeichert.
	 * </p>
	 */
	@Override
	public Anwender erstellenAnwender(int id, String name, String gmail) throws IllegalArgumentException {
		// Ein neues Anwender Objekt wird erstellt.
		Anwender a = new Anwender();
		
		//Die Attribute des Objekts werden mit Werten bef�llt.
		a.setId(id);
		a.setName(name);
		a.setGmail(gmail);
		a.setErstellDatum(new Timestamp(System.currentTimeMillis()));
	
		//Das Objekt wird in der Datenbank gespeichert und wiedergeben
		return this.anwenderMapper.insert(a);
	}
	
	/**
	 * <p>
	 * Eine neue Gruppe wird angelegt und anschlie�end in der Datenbank gespeichert.
	 * </p>
	 */
	@Override
	public Gruppe erstellenGruppe(int id, String name, int besitzerId) throws IllegalArgumentException {
		// Ein neues Gruppe Objekt wird erstellt.
		Gruppe g = new Gruppe();
		
		//Die Attribute des Objekts werden mit Werten bef�llt.
		g.setId(id);
		g.setName(name);
		g.setBesitzerId(id);
		g.setErstellDatum(new Timestamp(System.currentTimeMillis()));
		
		//Das Objekt wird in der Datenbank gespeichert und wiedergeben
		return this.gruppeMapper.insert(g);
	}
	
	/**
	 * <p>
	 * Ein neues Kino wird angelegt und anschlie�end in der Datenbank gespeichert. Hierbei 
	 * mit der Verkn�pfung zur Kinokette.
	 * </p>
	 */
	@Override
	public Kino erstellenKino(int id, String name, int besitzerId, int plz, String stadt, String strassse, String hausnummer, int kinokettenId) throws IllegalArgumentException {
		// Ein neues Kino Objekt wird erstellt.
		Kino k = new Kino();
		
		//Die Attribute des Objekts werden mit Werten bef�llt.
		k.setId(id);
		k.setName(name);
		k.setBesitzerId(id);
		k.setPlz(plz);
		k.setStadt(stadt);
		k.setStrasse(strassse);
		k.setHausnummer(hausnummer);
		k.setErstellDatum(new Timestamp(System.currentTimeMillis()));
		this.kinoMapper.addKinokette(this.kinoketteMapper.findById(kinokettenId), k);
		
		//Das Objekt wird in der Datenbank gespeichert und wiedergeben
		return this.kinoMapper.insert(k);
	}
	
	/**
	 * <p>
	 * Ein neues Kino wird angelegt und anschlie�end in der Datenbank gespeichert. Hierbei 
	 * ohne die Verkn�pfung zur Kinokette.
	 * </p>
	 */
	public Kino erstellenKino(int id, String name, int besitzerId, int plz, String stadt, String strassse, String hausnummer) throws IllegalArgumentException {
		// Ein neues Kino Objekt wird erstellt.
		Kino k = new Kino();
		
		//Die Attribute des Objekts werden mit Werten bef�llt.
		k.setId(id);
		k.setName(name);
		k.setBesitzerId(id);
		k.setPlz(plz);
		k.setStadt(stadt);
		k.setStrasse(strassse);
		k.setHausnummer(hausnummer);
		k.setErstellDatum(new Timestamp(System.currentTimeMillis()));
		
		//Das Objekt wird in der Datenbank gespeichert und wiedergeben
		return this.kinoMapper.insert(k);
	}
	
	/**
	 * <p>
	 * Eine neue Kinokette wird angelegt und anschlie�end in der Datenbank gespeichert.
	 * </p>
	 */
	@Override
	public Kinokette erstellenKinokette(int id, String name, int besitzerId,  String sitz, String website) throws IllegalArgumentException {
		// Ein neues Kinokette Objekt wird erstellt.
		Kinokette k = new Kinokette();
		
		//Die Attribute des Objekts werden mit Werten bef�llt.
		k.setId(id);
		k.setName(name);
		k.setBesitzerId(id);
		k.setSitz(sitz);
		k.setWebsite(website);
		k.setErstellDatum(new Timestamp(System.currentTimeMillis()));
		
		//Das Objekt wird in der Datenbank gespeichert und wiedergeben
		return this.kinoketteMapper.insert(k);		
	}
	
	/**
	 * <p>
	 * Ein neuer Spielplan wird angelegt und anschlie�end in der Datenbank gespeichert.
	 * </p>
	 */
	@Override
	public Spielplan erstellenSpielplan(int id, String name, int besitzerId,  int kinoId) throws IllegalArgumentException {
		// Ein neues Spielplan Objekt wird erstellt.
		Spielplan s = new Spielplan();
		
		//Die Attribute des Objekts werden mit Werten bef�llt.
		s.setId(id);
		s.setName(name);
		s.setBesitzerId(id);
		s.setKinoId(kinoId);
		s.setErstellDatum(new Timestamp(System.currentTimeMillis()));
		
		//Das Objekt wird in der Datenbank gespeichert und wiedergeben
		return this.spielplanMapper.insert(s);
	}
	
	/**
	 * <p>
	 * Eine neue Vorstellung wird angelegt und anschlie�end in der Datenbank gespeichert.
	 * </p>
	 */
	@Override
	public Vorstellung erstellenVorstellung(int id, String name, int spielplanId, int spielzeitId, int filmId) throws IllegalArgumentException {
		// Ein neues Vorstellung Objekt wird erstellt.
		Vorstellung v = new Vorstellung();
		
		//Die Attribute des Objekts werden mit Werten bef�llt.
		v.setId(id);
		v.setName(name);
		v.setSpielplanId(spielplanId);
		v.setSpielzeitId(spielzeitId);
		v.setFilmId(filmId);
		v.setErstellDatum(new Timestamp(System.currentTimeMillis()));
		
		//Das Objekt wird in der Datenbank gespeichert und wiedergeben
		return this.vorstellungMapper.insert(v);
	}
	
	/**
	 * <p>
	 * Eine neue Umfrage wird angelegt und anschlie�end in der Datenbank gespeichert.
	 * </p>
	 */
	@Override
	public Umfrage erstellenUmfrage(int id, String name, int besitzerId, int gruppenId) throws IllegalArgumentException {
		// Ein neues Umfrage Objekt wird erstellt.
		Umfrage u = new Umfrage();
		
		//Die Attribute des Objekts werden mit Werten bef�llt.
		u.setId(id);
		u.setName(name);
		u.setBesitzerId(id);
		u.setGruppenId(gruppenId);
		u.setErstellDatum(new Timestamp(System.currentTimeMillis()));
		
		//Das Objekt wird in der Datenbank gespeichert und wiedergeben
		return this.umfrageMapper.insert(u);
	}
	
	/**
	 * <p>
	 * Eine neue Umfrageoption wird angelegt und anschlie�end in der Datenbank gespeichert.
	 * </p>
	 */
	@Override
	public Umfrageoption erstellenUmfrageoption(int id, String name, int umfrageId, int vorstellungId) throws IllegalArgumentException {
		// Ein neues Umfrageoption Objekt wird erstellt.
		Umfrageoption u = new Umfrageoption();
		
		//Die Attribute des Objekts werden mit Werten bef�llt.
		u.setId(id);
		u.setName(name);
		u.setUmfrageId(umfrageId);
		u.setVorstellungsId(vorstellungId);
		u.setErstellDatum(new Timestamp(System.currentTimeMillis()));
		
		//Das Objekt wird in der Datenbank gespeichert und wiedergeben
		return this.umfrageoptionMapper.insert(u);		
	}
	
	/**
	 * <p>
	 * Ein neuer Film wird angelegt und anschlie�end in der Datenbank gespeichert.
	 * </p>
	 */
	@Override
	public Film erstellenFilm(int id, String name, int besitzerId, String beschreibung, int bewertung) throws IllegalArgumentException {
		// Ein neues Film Objekt wird erstellt.
		Film f = new Film();
		
		//Die Attribute des Objekts werden mit Werten bef�llt.
		f.setId(id);
		f.setName(name);
		f.setBesitzerId(besitzerId);
		f.setBeschreibung(beschreibung);
		f.setBewertung(bewertung);
		f.setErstellDatum(new Timestamp(System.currentTimeMillis()));
		
		//Das Objekt wird in der Datenbank gespeichert und wiedergeben
		return this.filmMapper.insert(f);
	}
	
	
	/**
	 * <p>
	 * Ein Spielzeit wird angelegt und anschlie�end in der Datenbank gespeichert.
	 * </p>
	 */
	@Override
	public Spielzeit erstellenSpielzeit(int id, String name, int besitzerId, Date zeit) throws IllegalArgumentException {
		// Ein neues Spielzeit Objekt wird erstellt.
		Spielzeit s = new Spielzeit();
		
		//Die Attribute des Objekts werden mit Werten bef�llt.
		s.setId(id);
		s.setName(name);
		s.setBesitzerId(besitzerId);
		s.setZeit(zeit);
		s.setErstellDatum(new Timestamp(System.currentTimeMillis()));
		
		//Das Objekt wird in der Datenbank gespeichert und wiedergeben
		return this.spielzeitMapper.insert(s);
	}
		
	/**
	 * <p>
	 * Eine neue Auswahl wird angelegt,die zugeh�rige Umfrage als gevotet markiert und anschlie�end 
	 * die Auswahl in der Datenbank gespeichert.
	 * </p>
	 */
	@Override
	public Auswahl erstellenAuswahl(int id, String name, int besitzerId, int voting, int umfrageoptionId) throws IllegalArgumentException {
		// Ein neues Auswahl Objekt wird erstellt.
		Auswahl a = new Auswahl();
		
		//Die Attribute des Objekts werden mit Werten bef�llt.
		a.setId(id);
		a.setName(name);
		a.setBesitzerId(besitzerId);
		a.setVoting(voting);
		a.setUmfrageoptionId(umfrageoptionId);
		a.setErstellDatum(new Timestamp(System.currentTimeMillis()));
		
		//Die zugeh�rige Umfrage wird als votiert markiert.
		this.isVoted(a);
		
		//Die Umfrage ggegebenfalls schlie�en
		this.isClosedSetzen(a);
		
		//Das Objekt wird in der Datenbank gespeichert und wiedergeben
		return this.auswahlMapper.insert(a);

	}
	
	/**
	 * <p>
	 * Es wird gepr�ft ob der Boolean isVoted der Klasse Umfrage bereits auf True gesetzt wurde, da diese gevotet wurde. 
	 * Ist dies nicht der Fall so wird der Boolean auf True gesetzt.
	 * </p>
	 */
	public void isVoted(Auswahl auswahl) {
		//Umfrage zur Auswahl herausfinden
		Umfrage u = this.umfrageMapper.findById(this.umfrageoptionMapper.findById(auswahl.getUmfrageoptionId()).getUmfrageId());
		
		//Wenn das Attribut isVoted noch auf false steht, auf true setzen
		if (u.isVoted() == false) {
			u.setVoted(true);
		}
	}
	
	/**
	 * <p>
	 * Speichern eines Anwenders.
	 * </p>
	 */
	@Override
	public void speichern(Anwender anwender) throws IllegalArgumentException {
		this.anwenderMapper.update(anwender);
		
	}

	/**
	 * <p>
	 * Speichern einer Gruppe.
	 * </p>
	 */
	@Override
	public void speichern(Gruppe gruppe) throws IllegalArgumentException {
		this.gruppeMapper.update(gruppe);
		
	}

	/**
	 * <p>
	 * Speichern eines Kinos.
	 * </p>
	 */
	@Override
	public void speichern(Kino kino) throws IllegalArgumentException {
		this.kinoMapper.update(kino);
		
	}

	/**
	 * <p>
	 * Speichern einer Kinokette.
	 * </p>
	 */
	@Override
	public void speichern(Kinokette kinokette) throws IllegalArgumentException {
		this.kinoketteMapper.update(kinokette);
		
	}

	/**
	 * <p>
	 * Speichern eines Spielplans.
	 * </p>
	 */
	@Override
	public void speichern(Spielplan spielplan) throws IllegalArgumentException {
		this.spielplanMapper.update(spielplan);
		
	}

	/**
	 * <p>
	 * Speichern einer Vorstellung.
	 * </p>
	 */
	@Override
	public void speichern(Vorstellung vorstellung) throws IllegalArgumentException {
		this.vorstellungMapper.update(vorstellung);
		
	}

	/**
	 * <p>
	 * Speichern einer Umfrage.
	 * </p>
	 */
	@Override
	public void speichern(Umfrage umfrage) throws IllegalArgumentException {
		this.umfrageMapper.update(umfrage);
		
	}

	/**
	 * <p>
	 * Speichern einer Umfrageoption.
	 * </p>
	 */
	@Override
	public void speichern(Umfrageoption umfrageoption) throws IllegalArgumentException {
		this.umfrageoptionMapper.update(umfrageoption);
		
	}

	/**
	 * <p>
	 * Speichern eines Films.
	 * </p>
	 */
	@Override
	public void speichern(Film film) throws IllegalArgumentException {
		this.filmMapper.update(film);
		
	}

	/**
	 * <p>
	 * Speichern einer Spielzeit.
	 * </p>
	 */
	@Override
	public void speichern(Spielzeit spielzeit) throws IllegalArgumentException {
		this.spielzeitMapper.update(spielzeit);
		
	}

	/**
	 * <p>
	 * Speichern einer Auswahl.
	 * </p>
	 */
	@Override
	public void speichern(Auswahl auswahl) throws IllegalArgumentException {
		this.auswahlMapper.update(auswahl);
		
	}

	/**
	 * <p>
	 * L�schen eines Anwenders mit L�schweitergabe.
	 * </p>
	 */
	@Override
	public void l�schen(Anwender anwender) throws IllegalArgumentException {
		//L�schen aller zugeh�rigen Spielpl�ne
		ArrayList<Spielplan> spielplaene = this.getSpielplaeneByAnwenderOwner(anwender);
		if (spielplaene != null) {
			for (Spielplan s : spielplaene) {
				this.l�schen(s);
			}
		}
		
		//L�schen aller zugeh�rigen Filme
		ArrayList<Film> filme = this.getFilmeByAnwenderOwner(anwender);
		if (filme != null) {
			for (Film f : filme) {
				this.l�schen(f);
			}
		}
		
		//L�schen aller zugeh�rigen Spielzeiten
		ArrayList<Spielzeit> spielzeiten = this.getSpielzeitenByAnwenderOwner(anwender);
		if (spielzeiten != null) {
			for (Spielzeit s : spielzeiten) {
				this.l�schen(s);
			}
		}
		
		//L�schen aller zugeh�rigen Kinos
		ArrayList<Kino> kinos = this.getKinosByAnwenderOwner(anwender);
		if (kinos != null) {
			for (Kino k : kinos) {
				this.l�schen(k);
			}
		}
		
		
		//L�schen aller zugeh�igen Kinoketten
		ArrayList<Kinokette> kinoketten = this.getKinokettenByAnwenderOwner(anwender);
		if (kinoketten != null) {
			for (Kinokette k : kinoketten) {
				this.l�schen(k);
			}
		}
		
		//L�schen aller zugeh�rigen Auswahlen
		ArrayList<Auswahl> auswahlen = this.getAuswahlenByAnwenderOwner(anwender);
		if (auswahlen != null) {
			for (Auswahl a : auswahlen) {
				this.l�schen(a);
			}
		}
		
		//L�schen aller zugeh�rigen Gruppen
		ArrayList<Gruppe> gruppen = this.getGruppenByAnwenderOwner(anwender);
		if (gruppen != null) {
			for (Gruppe g : gruppen) {
				this.l�schen(g);
			}
		}
		
		//L�schen aller zugeh�rigen Umfragen
		ArrayList<Umfrage> umfragen = this.getUmfragenByAnwenderOwner(anwender);
		if (umfragen != null) {
			for (Umfrage u : umfragen) {
				this.l�schen(u);
			}
		}
		
		//L�schen des Anwenders
		this.anwenderMapper.delete(anwender);
		
	}

	/**
	 * <p>
	 * L�schen einer Gruppe mit L�schweitergabe.
	 * </p>
	 */
	@Override
	public void l�schen(Gruppe gruppe) throws IllegalArgumentException {
		//L�schen aller zugeh�rigen Umfragen
		ArrayList<Umfrage> umfragen = this.getUmfragenByAnwenderOwner(anwender);
		if (umfragen != null) {
			for (Umfrage u : umfragen) {
				this.l�schen(u);
			}
		}
		
		//L�schen der Gruppe
		this.gruppeMapper.delete(gruppe);
			
	}

	/**
	 * <p>
	 * L�schen eines Kinos mit L�schweitergabe.
	 * </p>
	 */
	@Override
	public void l�schen(Kino kino) throws IllegalArgumentException {
		//L�schen aller zugeh�rigen Kinos
		ArrayList<Spielplan> spielplaene = this.getSpielplaeneByKino(kino);
		if (spielplaene != null) {
			for (Spielplan s : spielplaene) {
				this.l�schen(s);
			}
		}
		
		//L�schen des Kinos
		this.kinoMapper.delete(kino);
		
	}

	/**
	 * <p>
	 * L�schen einer Kinokette mit L�schweitergabe.
	 * </p>
	 */
	@Override
	public void l�schen(Kinokette kinokette) throws IllegalArgumentException {
		//L�schen aller zugeh�rigen Kinoketten
		ArrayList<Kino> kinos = this.getKinosByKinoketteId(kinokette);
		if (kinos != null) {
			for (Kino k : kinos) {
				this.kinoMapper.deleteKinokette( k);
			}
		}
		
		//L�schen der Kinokette
		this.kinoketteMapper.delete(kinokette);
		
	}

	/**
	 * <p>
	 * L�schen eines Spielplans mit L�schweitergabe.
	 * </p>
	 */
	@Override
	public void l�schen(Spielplan spielplan) throws IllegalArgumentException {
		//L�schen aller zugeh�rigen Vorstellungen
		ArrayList<Vorstellung> vorstellungen = this.getVorstellungenBySpielplan(spielplan);
		if (vorstellungen != null) {
			for (Vorstellung v : vorstellungen) {
				this.vorstellungMapper.delete(v);
			}
		}
		
		//L�schen des Spielplans
		this.spielplanMapper.delete(spielplan);
		
	}

	/**
	 * <p>
	 * L�schen einer Vorstellung mit L�schweitergabe.
	 * </p>
	 */
	@Override
	public void l�schen(Vorstellung vorstellung) throws IllegalArgumentException {
		//L�schen aller zugeh�rigen Umfrageoptionen
		ArrayList<Umfrageoption> umfrageoptionen = this.getUmfrageoptionenByVorstellung(vorstellung);
		if (umfrageoptionen != null) {
			for (Umfrageoption u : umfrageoptionen) {
				this.umfrageoptionMapper.delete(u);
			}
		}
		
		//L�schen der Vorstellung
		this.vorstellungMapper.delete(vorstellung);
		
	}

	/**
	 * <p>
	 * L�schen einer Umfrage mit L�schweitergabe.
	 * </p>
	 */
	@Override
	public void l�schen(Umfrage umfrage) throws IllegalArgumentException {
		//L�schen aller zugeh�rigen Umfrageoptionen
		ArrayList<Umfrageoption> umfrageoptionen = this.getUmfrageoptionenByUmfrage(umfrage);
		if (umfrageoptionen != null) {
			for (Umfrageoption u : umfrageoptionen) {
				this.umfrageoptionMapper.delete(u);
			}
		}
		
		//L�schen der Umfrage
		this.umfrageMapper.delete(umfrage);
		
	}

	/**
	 * <p>
	 * L�schen einer Umfrageoption mit L�schweitergabe.
	 * </p>
	 */
	@Override
	public void l�schen(Umfrageoption umfrageoption) throws IllegalArgumentException {
		//L�schen aller zugeh�rigen Auswahlen
		ArrayList<Auswahl> auswahlen = this.getAuswahlenByUmfrageoption(umfrageoption);
		if (auswahlen != null) {
			for (Auswahl a : auswahlen) {
				this.l�schen(a);
			}
		}
		
		//L�schen der Umfrageoption
		this.umfrageoptionMapper.delete(umfrageoption);
		
	}

	/**
	 * <p>
	 * L�schen eines Films mit L�schweitergabe.
	 * </p>
	 */
	@Override
	public void l�schen(Film film) throws IllegalArgumentException {
		/** Problem bei der l�schweitergabe: entweder nur l�schbar wenn nur 
		*einmal benutzt oder l�schbar und loch in den daten oder l�schbar und 
		*alle spielplaneintrage werden auch gel�scht
		**/
	}

	/**
	 * <p>
	 * L�schen einer Spielzeit mit L�schweitergabe.
	 * </p>
	 */
	@Override
	public void l�schen(Spielzeit spielzeit) throws IllegalArgumentException {
		// same wie film
		
	}

	/**
	 * <p>
	 * L�schen einer Auswahl.
	 * </p>
	 */
	@Override
	public void l�schen(Auswahl auswahl) throws IllegalArgumentException {
		////Die Umfrage ggegebenfalls �ffnen
		this.isClosedEntfernen(auswahl);
		
		//L�schen der Auswahl
		this.auswahlMapper.delete(auswahl);
		
	}

	/**
	 * <p>
	 * R�ckgabe des Anwenders mit einer bestimmten Id.
	 * </p>
	 */
	@Override
	public Anwender getAnwenderById(int anwenderId) throws IllegalArgumentException {
		return this.anwenderMapper.findById(anwenderId);
	}
	
	/**
	 * <p>
	 * R�ckgabe eines Spielplans mit einer bestimmten Id.
	 * </p>
	 */
	public Spielplan getSpielplanById(int spielplanId) throws IllegalArgumentException{
		return this.spielplanMapper.findById(spielplanId);
	}
	
	/**
	 * <p>
	 * R�ckgabe eines Kinos mit einer bestimmten Id.
	 * </p>
	 */
	public Kino getKinoById(int kinoId) throws IllegalArgumentException{
		return this.kinoMapper.findById(kinoId);
	}

	/**
	 * <p>
	 * R�ckgabe aller Gruppen in denen der Anwender Mitglied ist.
	 * </p>
	 */
	@Override
	public ArrayList<Gruppe> getGruppenByAnwender(Anwender anwender) throws IllegalArgumentException {
		return this.gruppeMapper.findAllByAnwender(anwender);
	}

	/**
	 * <p>
	 * R�ckgabe aller Gruppen die einem bestimmten Anwender geh�ren.
	 * </p>
	 */
	@Override
	public ArrayList<Gruppe> getGruppenByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		return this.gruppeMapper.findAllByAnwenderOwner(anwender);
	}

	/**
	 * <p>
	 * R�ckgabe aller Umfragen eines Anwenders.
	 * </p>
	 */
	@Override
	public ArrayList<Umfrage> getUmfragenByAnwender(Anwender anwender) throws IllegalArgumentException {
		return this.umfrageMapper.findAllByAnwender(anwender);
	}

	/**
	 * <p>
	 * R�ckgabe aller Umfragen die der Anwender besitzt.
	 * </p>
	 */
	@Override
	public ArrayList<Umfrage> getUmfragenByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		return this.umfrageMapper.findAllByAnwenderOwner(anwender);
	}

	/**
	 * <p>
	 * R�ckgabe aller geschlossenen Umfragen des Anwenders
	 * </p>
	 */
	@Override
	public ArrayList<Umfrage> getClosedUmfragenByAnwender(Anwender anwender) {
		return this.umfrageMapper.findAllClosedByAnwender(anwender);
	}

	/**
	 * <p>
	 * R�ckgabe aller Kinoketten die der Anwender besitzt.
	 * </p>
	 */
	@Override
	public ArrayList<Kinokette> getKinokettenByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		return this.kinoketteMapper.findAllByAnwenderOwner(anwender);
	}

	/**
	 * <p>
	 * R�ckgabe aller Kinos die der Anwender besitzt.
	 * </p>
	 */
	@Override
	public ArrayList<Kino> getKinosByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		return this.kinoMapper.findAllByAnwenderOwner(anwender);
	}
	
	/**
	 * <p>
	 * R�ckgabe aller Kinos die zu einer Kinokette geh�ren.
	 * </p>
	 */
	@Override
	public ArrayList<Kino> getKinosByKinoketteId(Kinokette kinokette) throws IllegalArgumentException{
		return this.kinoMapper.findAllByKinokette(kinokette);
		
	}
	
	/**
	 * <p>
	 * R�ckgabe aller Kinos einer Kinokette.
	 * </p>
	 */
	@Override
	public ArrayList<Kino> getKinosByKinoketteId(int kinoketteId) throws IllegalArgumentException{
		return this.getKinosByKinoketteId(this.kinoketteMapper.findById(kinoketteId));
	}
	
	/**
	 * <p>
	 * R�ckgabe aller Spielpl�ne die einer Anwender besitzt.
	 * </p>
	 */
	@Override
	public ArrayList<Spielplan> getSpielplaeneByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		return this.spielplanMapper.findAllByAnwenderOwner(anwender);
	}
	
	/**
	 * <p>
	 * R�ckgabe aller Spielpl�ne eines Kinos.
	 * </p>
	 */
	@Override
	public ArrayList<Spielplan> getSpielplaeneByKino(Kino kino) throws IllegalArgumentException {
		return this.spielplanMapper.findAllByKino(kino);
	}

	/**
	 * <p>
	 * R�ckgabe aller Filme die ein Anwender besitzt.
	 * </p>
	 */
	@Override
	public ArrayList<Film> getFilmeByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		return this.filmMapper.findAllByAnwenderOwner(anwender);
	}

	/**
	 * <p>
	 * R�ckgabe aller Spielzeiten die ein Anwender besitzt.
	 * </p>
	 */
	@Override
	public ArrayList<Spielzeit> getSpielzeitenByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		return this.spielzeitMapper.findAllByAnwenderOwner(anwender);
	}

	/**
	 * <p>
	 * R�ckgabe aller Vorstellungen eines Spielplans.
	 * </p>
	 */
	@Override
	public ArrayList<Vorstellung> getVorstellungenBySpielplan(Spielplan spielplan) throws IllegalArgumentException {
		return this.vorstellungMapper.findAllBySpielplan(spielplan);
	}

	/**
	 * <p>
	 * R�ckgabe eines Anwenders der durch den Namen gesucht wird.
	 * </p>
	 */
	@Override
	public Anwender getAnwenderByName(String name) throws IllegalArgumentException {
		return this.anwenderMapper.findByName(name);
	}

	/**
	 * <p>
	 * R�ckgabe aller Umfrageoptionen einer Umfrage.
	 * </p>
	 */
	@Override
	public ArrayList<Umfrageoption> getUmfrageoptionenByUmfrage(Umfrage umfrage) throws IllegalArgumentException {
		return this.umfrageoptionMapper.findAllByUmfrage(umfrage);
	}
	
	/**
	 * <p>
	 * R�ckgabe aller Umfrageoptionen einer Vorstellung.
	 * </p>
	 */
	@Override
	public ArrayList<Umfrageoption> getUmfrageoptionenByVorstellung(Vorstellung vorstellung) throws IllegalArgumentException {
		return this.umfrageoptionMapper.findAllByVorstellung(vorstellung);
	}

	/**
	 * <p>
	 * R�ckgabe aller Gruppenmitglieder (Anwender) einer Gruppe.
	 * </p>
	 */
	@Override
	public ArrayList<Anwender> getGruppenmitgliederByGruppe(Gruppe gruppe) throws IllegalArgumentException {
		return this.anwenderMapper.findAllByGruppe(gruppe);
	}

	/**
	 * <p>
	 * R�ckgabe aller Umfragen einer Gruppe.
	 * </p>
	 */
	@Override
	public ArrayList<Umfrage> getUmfragenByGruppe(Gruppe gruppe) throws IllegalArgumentException {
		return this.umfrageMapper.findAllByGruppe(gruppe);
	}

	/**
	 * <p>
	 * R�ckgabe aller Vorstellungen im System.
	 * </p>
	 */
	@Override
	public ArrayList<Vorstellung> getAllVorstellungen() throws IllegalArgumentException {
		return this.vorstellungMapper.findAllVorstellungen();
	}

	/**
	 * <p>
	 * R�ckgabe aller Kinoketten im System.
	 * </p>
	 */
	@Override
	public ArrayList<Kinokette> getAllKinoketten() throws IllegalArgumentException {
		return this.kinoketteMapper.findAllKinoketten();
	}

	/**
	 * <p>
	 * R�ckgabe aller Kinos im System
	 * </p>
	 */
	@Override
	public ArrayList<Kino> getAllKinos() throws IllegalArgumentException {
		return this.kinoMapper.findAllKinos();
	}

	/**
	 * <p>
	 * R�ckgabe aller Filme im System
	 * </p>
	 */
	@Override
	public ArrayList<Film> getAllFilme() throws IllegalArgumentException {
		return this.filmMapper.findAll();
	}

	/**
	 * <p>
	 * R�ckgabe aller Spielzeiten im System.
	 * </p>
	 */
	@Override
	public ArrayList<Spielzeit> getAllSpielzeiten() throws IllegalArgumentException {
		return this.spielzeitMapper.findAllSpielzeiten();
	}

	/**
	 * <p>
	 * Filtern von Vorstellungen nach Kino oder Kinokette.
	 * </p>
	 */
	@Override
	public ArrayList<Vorstellung> filterResultVorstellungenByKinoOrKinokette(ArrayList<Vorstellung> resultSet,
			Kino kino) throws IllegalArgumentException {
		

		//Pr�fen ob es �berhaupt Vorstllungen gibt
		if (resultSet != null) {
			
			//Leere ArrayList anlegen f�r das Filterergebnis
			ArrayList<Vorstellung> newResultSet = new ArrayList<Vorstellung>();
		
			//Vorstellungen filtern und die Matches dem Filterergebnis hinzuf�gen
			for (Vorstellung v : resultSet) {
				if (kino.getId() == this.getSpielplanById(v.getSpielplanId()).getKinoId()) {
					newResultSet.add(v);
				}
			}
			
			//Ergebnis zur�ckgeben
			return newResultSet;
		}else {
			return resultSet;
		}
	}

	/**
	 * <p>
	 * Filtern von Vorstellungen nach Kino oder Kinokette.
	 * </p>
	 */
	@Override
	public ArrayList<Vorstellung> filterResultVorstellungenByKinoOrKinokette(ArrayList<Vorstellung> resultSet,
			Kinokette kinokette) throws IllegalArgumentException {
		

		//Pr�fen ob es �berhaupt Vorstllungen gibt
		if (resultSet != null) {
			
			//Leere ArrayList anlegen f�r das Filterergebnis
			ArrayList<Vorstellung> newResultSet = new ArrayList<Vorstellung>();
			
			//Vorstellungen filtern und die Matches dem Filterergebnis hinzuf�gen
			for (Vorstellung v : resultSet) {
				if (kinokette.getId() == this.getKinoById(this.getSpielplanById(v.getSpielplanId()).getKinoId()).getKinokettenId()) {
					newResultSet.add(v);
				}
			}
			//Ergebnis zur�ckgeben
			return newResultSet;
		}else {
			return resultSet;
		}
	}

	/**
	 * <p>
	 * Filtern von Vorstellungen nach einem Film.
	 * </p>
	 */
	@Override
	public ArrayList<Vorstellung> filterResultVorstellungenByFilm(ArrayList<Vorstellung> resultSet, Film film)
			throws IllegalArgumentException {
		

		//Pr�fen ob es �berhaupt Vorstllungen gibt
		if (resultSet != null) {
			
			//Leere ArrayList anlegen f�r das Filterergebnis
			ArrayList<Vorstellung> newResultSet = new ArrayList<Vorstellung>();
			
			//Vorstellungen filtern und die Matches dem Filterergebnis hinzuf�gen
			for (Vorstellung v : resultSet) {
				if (film.getId() == v.getFilmId()) {
					newResultSet.add(v);
				}
			}
			//Ergebnis zur�ckgeben
			return newResultSet;
		}else {
			return resultSet;
		}
	}

	/**
	 * <p>
	 * Filtern von Vorstellungen nach einer Spielzeit.
	 * </p>
	 */
	@Override
	public ArrayList<Vorstellung> filterResultVorstellungenBySpielzeit(ArrayList<Vorstellung> resultSet,
			Spielzeit spielzeit) throws IllegalArgumentException {
		
	
		//Pr�fen ob es �berhaupt Vorstllungen gibt
		if (resultSet != null) {
			
			//Leere ArrayList anlegen f�r das Filterergebnis
			ArrayList<Vorstellung> newResultSet = new ArrayList<Vorstellung>();
			
			//Vorstellungen filtern und die Matches dem Filterergebnis hinzuf�gen
			for (Vorstellung v : resultSet) {
				if (spielzeit.getId() == v.getSpielzeitId()) {
					newResultSet.add(v);
				}
			}
			
			//Ergebnis zur�ckgeben
			return newResultSet;
		}else {
			return resultSet;
		}
	}

	/**
	 * <p>
	 * Setzen eines Spielplans f�r alle Kinos einer Kinokette.
	 * </p>
	 */
	@Override
	public void setSpielplanForKinosByKinokette(Spielplan spielplan, Kino kino) throws IllegalArgumentException {
		
		//Alle Kinos einer Kinokette suchen.
		ArrayList<Kino> resultSet = this.getKinosByKinoketteId(kino.getKinokettenId());
		
		//Den Spielplan f�r alle Kinos setzen.
		if(resultSet != null) {
			for (Kino k : resultSet) {
				this.kinoMapper.addKinokette(this.kinoketteMapper.findById(kino.getKinokettenId()), kino);
			}
		}
		
	}

	/**
	 * <p>
	 * R�ckgabe aller Auswahlen einer Umfrageoption
	 * </p>
	 */
	@Override
	public ArrayList<Auswahl> getAuswahlenByUmfrageoption(Umfrageoption umfrageoption) throws IllegalArgumentException {
		return this.auswahlMapper.findAllByUmfrageoption(umfrageoption);
	}

	/**
	 * <p>
	 * R�ckgabe einer Auswahl eines Anwenders bei einer Umfrageoption
	 * </p>
	 */
	@Override
	public Auswahl getAuswahlByAnwenderAndUmfrageoption(Anwender anwender, Umfrageoption umfrageoption)
			throws IllegalArgumentException {
		return this.auswahlMapper.findByAnwenderAndUmfrageoption(anwender,umfrageoption);
	}
	
	/**
	 * <p>
	 * R�ckgabe der Auswahlen die ein Anwender besitzt.
	 * </p>
	 */
	@Override
	public ArrayList<Auswahl> getAuswahlenByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		return this.auswahlMapper.findAllByAnwenderOwner(anwender);
	}
	
	/**
	 * <p>
	 * Berechnen des Ergebnisses der Auswahlen bei einer Umfrageoption.
	 * </p>
	 */
	@Override
	public int berechneAuswahlenByUmfrageoption(Umfrageoption umfrageoption) throws IllegalArgumentException {
		//Alle Auswahlen der Umfrageoption suchen
		ArrayList<Auswahl> resultSet = this.getAuswahlenByUmfrageoption(umfrageoption);
		
		//Auswahlen aufadieren
		if(resultSet != null) {
			int result = 0;
			for (Auswahl a : resultSet) {
				result += a.getVoting();
			}
			return result;
		}else {
			return 0;
		}
		
	}
	
	/**
	 * <p>
	 * Pr�fen ob ein Ergebnis gefunden wurde. Bei einem Ergebnis wird true zur�ckgegeben 
	 * bei einer Stichwahl wird false zur�ckgegeben.
	 * </p>
	 */
	@Override
	public boolean ergebnisGefunden(Umfrage umfrage) throws IllegalArgumentException {
		//Alle Umfrageoptionen der Umfrage suchen
		ArrayList<Umfrageoption> resultSet = this.getUmfrageoptionenByUmfrage(umfrage);
		
		//Pr�fen ob es Umfrageoptionen gibt
		if(resultSet != null) {
			
			//Ergebnisse berechnen
			for (Umfrageoption u : resultSet) {
				u.setVoteErgebnis(this.berechneAuswahlenByUmfrageoption(u));
			}	
			
			Umfrageoption max = null;
			
			//H�chstes Ergebnis suchen
			for (Umfrageoption u : resultSet) {
				if (max == null) {
					max = u;
				} else if(max.getVoteErgebnis() < u.getVoteErgebnis()) {
					max = u;
				}
			}
			
			//Rausfinden ob sich das h�chste Ergebnis doppelt
			for (Umfrageoption u : resultSet) {
				if (max.getVoteErgebnis() == u.getVoteErgebnis()) {
					return false;
				} 
			}
			
		}
		return true; 
	}
	
	/**
	 * <p>
	 * R�ckgabe der Umfrageoption die die meisten Stimmen bekommen hat bei einer Umfrage.
	 * </p>
	 */
	public Umfrageoption umfrageGewinnerErmitteln(Umfrage umfrage) throws IllegalArgumentException {
		//Umfrageoptionen anhand der Umfrage suchen
		ArrayList<Umfrageoption> resultSet = this.getUmfrageoptionenByUmfrage(umfrage);
		
		Umfrageoption max = null;
		
		//Pr�fen ob es Umfrageoptionen gibt
		if(resultSet != null) {
			
			//Ergebnisse berechenen
			for (Umfrageoption u : resultSet) {
				u.setVoteErgebnis(this.berechneAuswahlenByUmfrageoption(u));
			}	
			
			//H�chstes Ergebnis ermitteln
			for (Umfrageoption u : resultSet) {
				if (max == null) {
					max = u;
				} else if(max.getVoteErgebnis() < u.getVoteErgebnis()) {
					max = u;
				}
			}	
							
		}	
		return max;	
	}
		
	/**
	 * <p>
	 * R�ckgabe der Umfrageoptionen, die bei einer Stichwahl f�r die Umfrage verwendet werden 
	 * m�ssen.
	 * </p>
	 */
	public ArrayList<Umfrageoption> stichwahlUmfrageoptionenErmitteln(Umfrage umfrage) {
		//Umfrageoptionen der Umfrage suchen
		ArrayList<Umfrageoption> resultSet = this.getUmfrageoptionenByUmfrage(umfrage);
		
		//Leere ArrayList f�r die Ergebnisse bereitstellen
		ArrayList<Umfrageoption> stichwahlResultSet = null;
		
		//Pr�fen ob es Umfrageoptionen gibt
		if(resultSet != null) {
			
			//Ergebnisse berechnen
			for (Umfrageoption u : resultSet) {
				u.setVoteErgebnis(this.berechneAuswahlenByUmfrageoption(u));
			}	
					
			Umfrageoption max = null;
			
			//H�chstes Ergebnis finden
			for (Umfrageoption u : resultSet) {
				if (max == null) {
					max = u;
				} else if(max.getVoteErgebnis() < u.getVoteErgebnis()) {
					max = u;
				}
			}
			
			//Stichwahlopionen suchen und hinzuf�gen
			for (Umfrageoption u : resultSet) {
				if (max.getVoteErgebnis() == u.getVoteErgebnis()) {
					stichwahlResultSet.add(u);
				} 
			}
		
		}
		return stichwahlResultSet;
	}

	/**
	 * <p>
	 * Starten einer Stichwahl f�r eine Umfrage.
	 * </p>
	 */
	@Override
	public Umfrage stichwahlStarten(Umfrage umfrage) throws IllegalArgumentException {
		
		//Umfragename erstellen
		String name = "Stichwahl " + umfrage.getName();
		
		//Umfrage f�r die Stichwahl erstellen
		Umfrage u = this.erstellenUmfrage(1, name, umfrage.getBesitzerId(), umfrage.getGruppenId());
		
		//Stichwahlumfrageoptionen suchen
		ArrayList<Umfrageoption> umfrageoptionen = this.stichwahlUmfrageoptionenErmitteln(umfrage);
		
		//Stichwahlumfrageoptionen erstellen
		if(umfrageoptionen != null) {
			for (Umfrageoption umfr : umfrageoptionen) {
				String nameUmfrageoption = "Stichwahl " + umfr.getName();
				this.erstellenUmfrageoption(1, nameUmfrageoption, umfr.getUmfrageId(), umfr.getVorstellungsId());
			}	
		}
		
		return u;
	}
	
	/**
	 * <p>
	 * Setzen einer Umfrage auf den Zustand Closed
	 * </p>
	 */
	@Override
	public void isClosedSetzen(Auswahl auswahl) {
		//Suchen der Umfrage zur Auswahl
		Umfrage umfrage = this.umfrageMapper.findById((this.umfrageoptionMapper.findById(auswahl.getUmfrageoptionId()).getUmfrageId()));
		
		//Suchen aller Umfrageoptionen der Umfrage
		ArrayList<Umfrageoption> umfrageoptionen = this.getUmfrageoptionenByUmfrage(umfrage);
				
		//Erstellen einer leeren ArrayList f�r die Auswahlen
		ArrayList<Auswahl> resAuswahlen = null;
		
		//Suchen aller Auswahlen f�r die Umfrageoptionen und hinzuf�gen in die Auswahlen ArrayList
		for (Umfrageoption u : umfrageoptionen) {
			ArrayList<Auswahl> auswahlen = this.getAuswahlenByUmfrageoption(u);
			for (Auswahl a : auswahlen) {
				resAuswahlen.add(a);
			}
		}
		
		//Suchen aller Gruppenmitglieder die an der Umfrage teilnehemn
		ArrayList<Anwender> gruppenmitglieder = this.getGruppenmitgliederByGruppe(this.gruppeMapper.findById(umfrage.getGruppenId()));
		
		//Alle Auswahlen f�r jeden Anwender durchlaufen und z�hlen wie oft er gevotet hat
		for(Anwender a : gruppenmitglieder) {
			int count = 0;
			for(Auswahl aus : resAuswahlen) {
				if(aus.getBesitzerId() == a.getId()) {
					count++;
				}
			}
			
			//Wenn der Anwender noch keinen Vote erstellt hat f�r die Umfrage, so ist die Umfrage noch offen
			if(count == 0) {
				return;
			}
		}
		
		//Wenn alle Anwender gevotet haben so ist die Umfrage geschlossen
		umfrage.setOpen(false);
	}
	
	/**
	 * <p>
	 * Setzen einer Umfrage auf den Zustand Open
	 * </p>
	 */
	@Override
	public void isClosedEntfernen(Auswahl auswahl) {
		//Suchen der Umfrage zur Auswahl
		Umfrage umfrage = this.umfrageMapper.findById((this.umfrageoptionMapper.findById(auswahl.getUmfrageoptionId()).getUmfrageId()));
		
		//Suchen aller Umfrageoptionen der Umfrage
		ArrayList<Umfrageoption> umfrageoptionen = this.getUmfrageoptionenByUmfrage(umfrage);
				
		//Erstellen einer leeren ArrayList f�r die Auswahlen
		ArrayList<Auswahl> resAuswahlen = new ArrayList<Auswahl>();
		
		//Suchen aller Auswahlen f�r die Umfrageoptionen und hinzuf�gen in die Auswahlen ArrayList
		for (Umfrageoption u : umfrageoptionen) {
			ArrayList<Auswahl> auswahlen = this.getAuswahlenByUmfrageoption(u);
			for (Auswahl a : auswahlen) {
				resAuswahlen.add(a);
			}
		}
				
		//Alle Auswahlen f�r den Anwender durchlaufen und z�hlen wie oft er gevotet hat
		int count = 0;
		for(Auswahl aus : resAuswahlen) {
			if(aus.getBesitzerId() == auswahl.getBesitzerId()) {
				count++;
		}
						
		/**
		 * Wenn der Anwender mehr als einen Vote erstellt hat f�r die Umfrage, so ist die Umfrage noch geschlossen, 
		 * nachdem die Auswahl gel�scht wurde
		 */
			if(count > 1) {
				return;
			}
		}
		
		//Wenn der Anwender nur einen Vote erstellt hat, so ist sie wieder ge�ffnet nach dem l�schen
		umfrage.setOpen(true);
		
	}
	
	/**
	 * <p>
	 * R�ckgabe aller geschlosssenen Umfragen, die zeitlich noch g�ltig sind.
	 * </p>
	 */
	@Override
	public ArrayList<Umfrage> anzeigenVonClosedUmfragen(Anwender anwender) throws IllegalArgumentException {
		ArrayList<Umfrage> umfragen = this.getClosedUmfragenByAnwender(anwender);
		ArrayList<Umfrage> zeitg�ltigeUmfragen = null;
		Date date = new Date(System.currentTimeMillis());
		
		for (Umfrage u : umfragen) {
			if ((this.spielzeitMapper.findById(this.vorstellungMapper.findById(this.umfrageGewinnerErmitteln(u).getVorstellungsId()).getSpielzeitId()).getZeit()).after(date)) {
				zeitg�ltigeUmfragen.add(u);
			}
		}
		
		return zeitg�ltigeUmfragen;
	}
	
}
