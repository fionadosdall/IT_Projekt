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
 * Website enthalten. Sie sorgt für eine dauerhafte Konsitenz der Methoden und Daten. Die Klasse 
 * bedient sich Mapper Klassen, welche der Datenbank-Schicht angehören. Diese bilden die Sicht der 
 * Applikationslogik auf der realtionalen Datenbank ab.
 * </p>
 * <p>
 * Die Klasse steht mit weiteren Datentypen in Verbindung:
 * <ol>
 * <li>{@link Kinoplaner}: Ist das lokale serverseitige Interface, in welchem die dem System 
 * verfügbaren Funktionen deklariert sind.</li>
 * <li>{@link KinoplanerAsync}: Mit <code>KinplanerImpl</code> und der <code>Kinplaner</code> wird
 * die serverseitige Sicht der Applikationslogik abgebildet, welche funktional vollständig synchron 
 * abläuft. Für die clientseiteige Sicht müssen asynchrone Aufrufe ermöglicht werden, wozu dieses 
 * zusätzliche Interface dient.Das Async Interface wird durch das Google Plugin semiautomatisch bei 
 * der Erstellung und Pflege unterstützt.</li>
 * <li>{@link RemoteServiceServlet}: Ist eine Klasse serverseitig instantiierbar und clientseitig 
 * über GWT RPC nutzbar, so muss sie die Klasse <code>RemoteServiceServlet</code> implementieren. 
 * Diese ist die funktionale Basis für die Anbindung an die Runtime des GWT RPC-Mechanismus.</li>
 * </ol>
 * </p>
 * <p>
 * Jede der folgenden Methode die mithilfe von GWT RPC aufgerufen werden kann, muss 
 * <code>throws IllegalArgumentException</code> in der Methodendeklaration aufweisen. Das Bedeutet 
 * das diese Methoden in der Lage sind eine Instant von {@link IllegalArgument Exception} auszuwerfen. 
 * Damit können Probleme von der serverseite einfach auf die clientseite transportiert und dann
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
	 * Hier wird der Anwender gespeichert, in dessen Ansicht die Website momentan ausgeführt wird.
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
	 * Referenziert auf den Datenbankmapper, welcher Spielpläne in der Datenbank verwaltet.
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
	 * No-Argument Konstruktor benötigt. Ein anderer Konstrukor kann derzeit nicht aufgerufen 
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
	 * wird für jede Instanz von <code>KinoplanerImpl</code> aufgerufen. Bei der Initiialisierung wird 
	 * ein vollständiger Satz Mapper erzeugt, so dass die KLasse mit der Datenbank kommunizieren kann.
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
	 * Rückgabe des Anwenders, in dessen Ansicht die Website ausgeführt wird.
	 * </p>
	 */
	@Override
	public Anwender getAnwender()  throws IllegalArgumentException {
		return this.anwender;
	}
	
	/**
	 * <p>
	 * Setzen des Anwenders, in dessen Ansicht die Website ausgeführt wird.
	 * </p>
	 */
	@Override
	public void setAnwender(Anwender anwender)  throws IllegalArgumentException {
		this.anwender = anwender;
	}
	
	/**
	 * <p>
	 * Ein neuer Anwender wird angelegt und anschließend in der Datenbank gespeichert.
	 * </p>
	 */
	@Override
	public Anwender erstellenAnwender(int id, String name, String gmail) throws IllegalArgumentException {
		// Ein neues Anwender Objekt wird erstellt.
		Anwender a = new Anwender();
		
		//Die Attribute des Objekts werden mit Werten befüllt.
		a.setId(id);
		a.setName(name);
		a.setGmail(gmail);
		a.setErstellDatum(new Timestamp(System.currentTimeMillis()));
	
		//Das Objekt wird in der Datenbank gespeichert und wiedergeben
		return this.anwenderMapper.insert(a);
	}
	
	/**
	 * <p>
	 * Eine neue Gruppe wird angelegt und anschließend in der Datenbank gespeichert.
	 * </p>
	 */
	@Override
	public Gruppe erstellenGruppe(int id, String name, int besitzerId) throws IllegalArgumentException {
		// Ein neues Gruppe Objekt wird erstellt.
		Gruppe g = new Gruppe();
		
		//Die Attribute des Objekts werden mit Werten befüllt.
		g.setId(id);
		g.setName(name);
		g.setBesitzerId(id);
		g.setErstellDatum(new Timestamp(System.currentTimeMillis()));
		
		//Das Objekt wird in der Datenbank gespeichert und wiedergeben
		return this.gruppeMapper.insert(g);
	}
	
	/**
	 * <p>
	 * Ein neues Kino wird angelegt und anschließend in der Datenbank gespeichert. Hierbei 
	 * mit der Verknüpfung zur Kinokette.
	 * </p>
	 */
	@Override
	public Kino erstellenKino(int id, String name, int besitzerId, int plz, String stadt, String strassse, String hausnummer, int kinokettenId) throws IllegalArgumentException {
		// Ein neues Kino Objekt wird erstellt.
		Kino k = new Kino();
		
		//Die Attribute des Objekts werden mit Werten befüllt.
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
	 * Ein neues Kino wird angelegt und anschließend in der Datenbank gespeichert. Hierbei 
	 * ohne die Verknüpfung zur Kinokette.
	 * </p>
	 */
	public Kino erstellenKino(int id, String name, int besitzerId, int plz, String stadt, String strassse, String hausnummer) throws IllegalArgumentException {
		// Ein neues Kino Objekt wird erstellt.
		Kino k = new Kino();
		
		//Die Attribute des Objekts werden mit Werten befüllt.
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
	 * Eine neue Kinokette wird angelegt und anschließend in der Datenbank gespeichert.
	 * </p>
	 */
	@Override
	public Kinokette erstellenKinokette(int id, String name, int besitzerId,  String sitz, String website) throws IllegalArgumentException {
		// Ein neues Kinokette Objekt wird erstellt.
		Kinokette k = new Kinokette();
		
		//Die Attribute des Objekts werden mit Werten befüllt.
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
	 * Ein neuer Spielplan wird angelegt und anschließend in der Datenbank gespeichert.
	 * </p>
	 */
	@Override
	public Spielplan erstellenSpielplan(int id, String name, int besitzerId,  int kinoId) throws IllegalArgumentException {
		// Ein neues Spielplan Objekt wird erstellt.
		Spielplan s = new Spielplan();
		
		//Die Attribute des Objekts werden mit Werten befüllt.
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
	 * Eine neue Vorstellung wird angelegt und anschließend in der Datenbank gespeichert.
	 * </p>
	 */
	@Override
	public Vorstellung erstellenVorstellung(int id, String name, int spielplanId, int spielzeitId, int filmId) throws IllegalArgumentException {
		// Ein neues Vorstellung Objekt wird erstellt.
		Vorstellung v = new Vorstellung();
		
		//Die Attribute des Objekts werden mit Werten befüllt.
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
	 * Eine neue Umfrage wird angelegt und anschließend in der Datenbank gespeichert.
	 * </p>
	 */
	@Override
	public Umfrage erstellenUmfrage(int id, String name, int besitzerId, int gruppenId) throws IllegalArgumentException {
		// Ein neues Umfrage Objekt wird erstellt.
		Umfrage u = new Umfrage();
		
		//Die Attribute des Objekts werden mit Werten befüllt.
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
	 * Eine neue Umfrageoption wird angelegt und anschließend in der Datenbank gespeichert.
	 * </p>
	 */
	@Override
	public Umfrageoption erstellenUmfrageoption(int id, String name, int umfrageId, int vorstellungId) throws IllegalArgumentException {
		// Ein neues Umfrageoption Objekt wird erstellt.
		Umfrageoption u = new Umfrageoption();
		
		//Die Attribute des Objekts werden mit Werten befüllt.
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
	 * Ein neuer Film wird angelegt und anschließend in der Datenbank gespeichert.
	 * </p>
	 */
	@Override
	public Film erstellenFilm(int id, String name, int besitzerId, String beschreibung, int bewertung) throws IllegalArgumentException {
		// Ein neues Film Objekt wird erstellt.
		Film f = new Film();
		
		//Die Attribute des Objekts werden mit Werten befüllt.
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
	 * Ein Spielzeit wird angelegt und anschließend in der Datenbank gespeichert.
	 * </p>
	 */
	@Override
	public Spielzeit erstellenSpielzeit(int id, String name, int besitzerId, Date zeit) throws IllegalArgumentException {
		// Ein neues Spielzeit Objekt wird erstellt.
		Spielzeit s = new Spielzeit();
		
		//Die Attribute des Objekts werden mit Werten befüllt.
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
	 * Eine neue Auswahl wird angelegt,die zugehörige Umfrage als gevotet markiert und anschließend 
	 * die Auswahl in der Datenbank gespeichert.
	 * </p>
	 */
	@Override
	public Auswahl erstellenAuswahl(int id, String name, int besitzerId, int voting, int umfrageoptionId) throws IllegalArgumentException {
		// Ein neues Auswahl Objekt wird erstellt.
		Auswahl a = new Auswahl();
		
		//Die Attribute des Objekts werden mit Werten befüllt.
		a.setId(id);
		a.setName(name);
		a.setBesitzerId(besitzerId);
		a.setVoting(voting);
		a.setUmfrageoptionId(umfrageoptionId);
		a.setErstellDatum(new Timestamp(System.currentTimeMillis()));
		
		//Die zugehörige Umfrage wird als votiert markiert.
		this.isVoted(a);
		
		//Die Umfrage ggegebenfalls schließen
		this.isClosedSetzen(a);
		
		//Das Objekt wird in der Datenbank gespeichert und wiedergeben
		return this.auswahlMapper.insert(a);

	}
	
	/**
	 * <p>
	 * Es wird geprüft ob der Boolean isVoted der Klasse Umfrage bereits auf True gesetzt wurde, da diese gevotet wurde. 
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
	 * Löschen eines Anwenders mit Löschweitergabe.
	 * </p>
	 */
	@Override
	public void löschen(Anwender anwender) throws IllegalArgumentException {
		//Löschen aller zugehörigen Spielpläne
		ArrayList<Spielplan> spielplaene = this.getSpielplaeneByAnwenderOwner(anwender);
		if (spielplaene != null) {
			for (Spielplan s : spielplaene) {
				this.löschen(s);
			}
		}
		
		//Löschen aller zugehörigen Filme
		ArrayList<Film> filme = this.getFilmeByAnwenderOwner(anwender);
		if (filme != null) {
			for (Film f : filme) {
				this.löschen(f);
			}
		}
		
		//Löschen aller zugehörigen Spielzeiten
		ArrayList<Spielzeit> spielzeiten = this.getSpielzeitenByAnwenderOwner(anwender);
		if (spielzeiten != null) {
			for (Spielzeit s : spielzeiten) {
				this.löschen(s);
			}
		}
		
		//Löschen aller zugehörigen Kinos
		ArrayList<Kino> kinos = this.getKinosByAnwenderOwner(anwender);
		if (kinos != null) {
			for (Kino k : kinos) {
				this.löschen(k);
			}
		}
		
		
		//Löschen aller zugehöigen Kinoketten
		ArrayList<Kinokette> kinoketten = this.getKinokettenByAnwenderOwner(anwender);
		if (kinoketten != null) {
			for (Kinokette k : kinoketten) {
				this.löschen(k);
			}
		}
		
		//Löschen aller zugehörigen Auswahlen
		ArrayList<Auswahl> auswahlen = this.getAuswahlenByAnwenderOwner(anwender);
		if (auswahlen != null) {
			for (Auswahl a : auswahlen) {
				this.löschen(a);
			}
		}
		
		//Löschen aller zugehörigen Gruppen
		ArrayList<Gruppe> gruppen = this.getGruppenByAnwenderOwner(anwender);
		if (gruppen != null) {
			for (Gruppe g : gruppen) {
				this.löschen(g);
			}
		}
		
		//Löschen aller zugehörigen Umfragen
		ArrayList<Umfrage> umfragen = this.getUmfragenByAnwenderOwner(anwender);
		if (umfragen != null) {
			for (Umfrage u : umfragen) {
				this.löschen(u);
			}
		}
		
		//Löschen des Anwenders
		this.anwenderMapper.delete(anwender);
		
	}

	/**
	 * <p>
	 * Löschen einer Gruppe mit Löschweitergabe.
	 * </p>
	 */
	@Override
	public void löschen(Gruppe gruppe) throws IllegalArgumentException {
		//Löschen aller zugehörigen Umfragen
		ArrayList<Umfrage> umfragen = this.getUmfragenByAnwenderOwner(anwender);
		if (umfragen != null) {
			for (Umfrage u : umfragen) {
				this.löschen(u);
			}
		}
		
		//Löschen der Gruppe
		this.gruppeMapper.delete(gruppe);
			
	}

	/**
	 * <p>
	 * Löschen eines Kinos mit Löschweitergabe.
	 * </p>
	 */
	@Override
	public void löschen(Kino kino) throws IllegalArgumentException {
		//Löschen aller zugehörigen Kinos
		ArrayList<Spielplan> spielplaene = this.getSpielplaeneByKino(kino);
		if (spielplaene != null) {
			for (Spielplan s : spielplaene) {
				this.löschen(s);
			}
		}
		
		//Löschen des Kinos
		this.kinoMapper.delete(kino);
		
	}

	/**
	 * <p>
	 * Löschen einer Kinokette mit Löschweitergabe.
	 * </p>
	 */
	@Override
	public void löschen(Kinokette kinokette) throws IllegalArgumentException {
		//Löschen aller zugehörigen Kinoketten
		ArrayList<Kino> kinos = this.getKinosByKinoketteId(kinokette);
		if (kinos != null) {
			for (Kino k : kinos) {
				this.kinoMapper.deleteKinokette( k);
			}
		}
		
		//Löschen der Kinokette
		this.kinoketteMapper.delete(kinokette);
		
	}

	/**
	 * <p>
	 * Löschen eines Spielplans mit Löschweitergabe.
	 * </p>
	 */
	@Override
	public void löschen(Spielplan spielplan) throws IllegalArgumentException {
		//Löschen aller zugehörigen Vorstellungen
		ArrayList<Vorstellung> vorstellungen = this.getVorstellungenBySpielplan(spielplan);
		if (vorstellungen != null) {
			for (Vorstellung v : vorstellungen) {
				this.vorstellungMapper.delete(v);
			}
		}
		
		//Löschen des Spielplans
		this.spielplanMapper.delete(spielplan);
		
	}

	/**
	 * <p>
	 * Löschen einer Vorstellung mit Löschweitergabe.
	 * </p>
	 */
	@Override
	public void löschen(Vorstellung vorstellung) throws IllegalArgumentException {
		//Löschen aller zugehörigen Umfrageoptionen
		ArrayList<Umfrageoption> umfrageoptionen = this.getUmfrageoptionenByVorstellung(vorstellung);
		if (umfrageoptionen != null) {
			for (Umfrageoption u : umfrageoptionen) {
				this.umfrageoptionMapper.delete(u);
			}
		}
		
		//Löschen der Vorstellung
		this.vorstellungMapper.delete(vorstellung);
		
	}

	/**
	 * <p>
	 * Löschen einer Umfrage mit Löschweitergabe.
	 * </p>
	 */
	@Override
	public void löschen(Umfrage umfrage) throws IllegalArgumentException {
		//Löschen aller zugehörigen Umfrageoptionen
		ArrayList<Umfrageoption> umfrageoptionen = this.getUmfrageoptionenByUmfrage(umfrage);
		if (umfrageoptionen != null) {
			for (Umfrageoption u : umfrageoptionen) {
				this.umfrageoptionMapper.delete(u);
			}
		}
		
		//Löschen der Umfrage
		this.umfrageMapper.delete(umfrage);
		
	}

	/**
	 * <p>
	 * Löschen einer Umfrageoption mit Löschweitergabe.
	 * </p>
	 */
	@Override
	public void löschen(Umfrageoption umfrageoption) throws IllegalArgumentException {
		//Löschen aller zugehörigen Auswahlen
		ArrayList<Auswahl> auswahlen = this.getAuswahlenByUmfrageoption(umfrageoption);
		if (auswahlen != null) {
			for (Auswahl a : auswahlen) {
				this.löschen(a);
			}
		}
		
		//Löschen der Umfrageoption
		this.umfrageoptionMapper.delete(umfrageoption);
		
	}

	/**
	 * <p>
	 * Löschen eines Films mit Löschweitergabe.
	 * </p>
	 */
	@Override
	public void löschen(Film film) throws IllegalArgumentException {
		/** Problem bei der löschweitergabe: entweder nur löschbar wenn nur 
		*einmal benutzt oder löschbar und loch in den daten oder löschbar und 
		*alle spielplaneintrage werden auch gelöscht
		**/
	}

	/**
	 * <p>
	 * Löschen einer Spielzeit mit Löschweitergabe.
	 * </p>
	 */
	@Override
	public void löschen(Spielzeit spielzeit) throws IllegalArgumentException {
		// same wie film
		
	}

	/**
	 * <p>
	 * Löschen einer Auswahl.
	 * </p>
	 */
	@Override
	public void löschen(Auswahl auswahl) throws IllegalArgumentException {
		////Die Umfrage ggegebenfalls öffnen
		this.isClosedEntfernen(auswahl);
		
		//Löschen der Auswahl
		this.auswahlMapper.delete(auswahl);
		
	}

	/**
	 * <p>
	 * Rückgabe des Anwenders mit einer bestimmten Id.
	 * </p>
	 */
	@Override
	public Anwender getAnwenderById(int anwenderId) throws IllegalArgumentException {
		return this.anwenderMapper.findById(anwenderId);
	}
	
	/**
	 * <p>
	 * Rückgabe eines Spielplans mit einer bestimmten Id.
	 * </p>
	 */
	public Spielplan getSpielplanById(int spielplanId) throws IllegalArgumentException{
		return this.spielplanMapper.findById(spielplanId);
	}
	
	/**
	 * <p>
	 * Rückgabe eines Kinos mit einer bestimmten Id.
	 * </p>
	 */
	public Kino getKinoById(int kinoId) throws IllegalArgumentException{
		return this.kinoMapper.findById(kinoId);
	}

	/**
	 * <p>
	 * Rückgabe aller Gruppen in denen der Anwender Mitglied ist.
	 * </p>
	 */
	@Override
	public ArrayList<Gruppe> getGruppenByAnwender(Anwender anwender) throws IllegalArgumentException {
		return this.gruppeMapper.findAllByAnwender(anwender);
	}

	/**
	 * <p>
	 * Rückgabe aller Gruppen die einem bestimmten Anwender gehören.
	 * </p>
	 */
	@Override
	public ArrayList<Gruppe> getGruppenByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		return this.gruppeMapper.findAllByAnwenderOwner(anwender);
	}

	/**
	 * <p>
	 * Rückgabe aller Umfragen eines Anwenders.
	 * </p>
	 */
	@Override
	public ArrayList<Umfrage> getUmfragenByAnwender(Anwender anwender) throws IllegalArgumentException {
		return this.umfrageMapper.findAllByAnwender(anwender);
	}

	/**
	 * <p>
	 * Rückgabe aller Umfragen die der Anwender besitzt.
	 * </p>
	 */
	@Override
	public ArrayList<Umfrage> getUmfragenByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		return this.umfrageMapper.findAllByAnwenderOwner(anwender);
	}

	/**
	 * <p>
	 * Rückgabe aller geschlossenen Umfragen des Anwenders
	 * </p>
	 */
	@Override
	public ArrayList<Umfrage> getClosedUmfragenByAnwender(Anwender anwender) {
		return this.umfrageMapper.findAllClosedByAnwender(anwender);
	}

	/**
	 * <p>
	 * Rückgabe aller Kinoketten die der Anwender besitzt.
	 * </p>
	 */
	@Override
	public ArrayList<Kinokette> getKinokettenByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		return this.kinoketteMapper.findAllByAnwenderOwner(anwender);
	}

	/**
	 * <p>
	 * Rückgabe aller Kinos die der Anwender besitzt.
	 * </p>
	 */
	@Override
	public ArrayList<Kino> getKinosByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		return this.kinoMapper.findAllByAnwenderOwner(anwender);
	}
	
	/**
	 * <p>
	 * Rückgabe aller Kinos die zu einer Kinokette gehören.
	 * </p>
	 */
	@Override
	public ArrayList<Kino> getKinosByKinoketteId(Kinokette kinokette) throws IllegalArgumentException{
		return this.kinoMapper.findAllByKinokette(kinokette);
		
	}
	
	/**
	 * <p>
	 * Rückgabe aller Kinos einer Kinokette.
	 * </p>
	 */
	@Override
	public ArrayList<Kino> getKinosByKinoketteId(int kinoketteId) throws IllegalArgumentException{
		return this.getKinosByKinoketteId(this.kinoketteMapper.findById(kinoketteId));
	}
	
	/**
	 * <p>
	 * Rückgabe aller Spielpläne die einer Anwender besitzt.
	 * </p>
	 */
	@Override
	public ArrayList<Spielplan> getSpielplaeneByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		return this.spielplanMapper.findAllByAnwenderOwner(anwender);
	}
	
	/**
	 * <p>
	 * Rückgabe aller Spielpläne eines Kinos.
	 * </p>
	 */
	@Override
	public ArrayList<Spielplan> getSpielplaeneByKino(Kino kino) throws IllegalArgumentException {
		return this.spielplanMapper.findAllByKino(kino);
	}

	/**
	 * <p>
	 * Rückgabe aller Filme die ein Anwender besitzt.
	 * </p>
	 */
	@Override
	public ArrayList<Film> getFilmeByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		return this.filmMapper.findAllByAnwenderOwner(anwender);
	}

	/**
	 * <p>
	 * Rückgabe aller Spielzeiten die ein Anwender besitzt.
	 * </p>
	 */
	@Override
	public ArrayList<Spielzeit> getSpielzeitenByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		return this.spielzeitMapper.findAllByAnwenderOwner(anwender);
	}

	/**
	 * <p>
	 * Rückgabe aller Vorstellungen eines Spielplans.
	 * </p>
	 */
	@Override
	public ArrayList<Vorstellung> getVorstellungenBySpielplan(Spielplan spielplan) throws IllegalArgumentException {
		return this.vorstellungMapper.findAllBySpielplan(spielplan);
	}

	/**
	 * <p>
	 * Rückgabe eines Anwenders der durch den Namen gesucht wird.
	 * </p>
	 */
	@Override
	public Anwender getAnwenderByName(String name) throws IllegalArgumentException {
		return this.anwenderMapper.findByName(name);
	}

	/**
	 * <p>
	 * Rückgabe aller Umfrageoptionen einer Umfrage.
	 * </p>
	 */
	@Override
	public ArrayList<Umfrageoption> getUmfrageoptionenByUmfrage(Umfrage umfrage) throws IllegalArgumentException {
		return this.umfrageoptionMapper.findAllByUmfrage(umfrage);
	}
	
	/**
	 * <p>
	 * Rückgabe aller Umfrageoptionen einer Vorstellung.
	 * </p>
	 */
	@Override
	public ArrayList<Umfrageoption> getUmfrageoptionenByVorstellung(Vorstellung vorstellung) throws IllegalArgumentException {
		return this.umfrageoptionMapper.findAllByVorstellung(vorstellung);
	}

	/**
	 * <p>
	 * Rückgabe aller Gruppenmitglieder (Anwender) einer Gruppe.
	 * </p>
	 */
	@Override
	public ArrayList<Anwender> getGruppenmitgliederByGruppe(Gruppe gruppe) throws IllegalArgumentException {
		return this.anwenderMapper.findAllByGruppe(gruppe);
	}

	/**
	 * <p>
	 * Rückgabe aller Umfragen einer Gruppe.
	 * </p>
	 */
	@Override
	public ArrayList<Umfrage> getUmfragenByGruppe(Gruppe gruppe) throws IllegalArgumentException {
		return this.umfrageMapper.findAllByGruppe(gruppe);
	}

	/**
	 * <p>
	 * Rückgabe aller Vorstellungen im System.
	 * </p>
	 */
	@Override
	public ArrayList<Vorstellung> getAllVorstellungen() throws IllegalArgumentException {
		return this.vorstellungMapper.findAllVorstellungen();
	}

	/**
	 * <p>
	 * Rückgabe aller Kinoketten im System.
	 * </p>
	 */
	@Override
	public ArrayList<Kinokette> getAllKinoketten() throws IllegalArgumentException {
		return this.kinoketteMapper.findAllKinoketten();
	}

	/**
	 * <p>
	 * Rückgabe aller Kinos im System
	 * </p>
	 */
	@Override
	public ArrayList<Kino> getAllKinos() throws IllegalArgumentException {
		return this.kinoMapper.findAllKinos();
	}

	/**
	 * <p>
	 * Rückgabe aller Filme im System
	 * </p>
	 */
	@Override
	public ArrayList<Film> getAllFilme() throws IllegalArgumentException {
		return this.filmMapper.findAll();
	}

	/**
	 * <p>
	 * Rückgabe aller Spielzeiten im System.
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
		

		//Prüfen ob es überhaupt Vorstllungen gibt
		if (resultSet != null) {
			
			//Leere ArrayList anlegen für das Filterergebnis
			ArrayList<Vorstellung> newResultSet = new ArrayList<Vorstellung>();
		
			//Vorstellungen filtern und die Matches dem Filterergebnis hinzufügen
			for (Vorstellung v : resultSet) {
				if (kino.getId() == this.getSpielplanById(v.getSpielplanId()).getKinoId()) {
					newResultSet.add(v);
				}
			}
			
			//Ergebnis zurückgeben
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
		

		//Prüfen ob es überhaupt Vorstllungen gibt
		if (resultSet != null) {
			
			//Leere ArrayList anlegen für das Filterergebnis
			ArrayList<Vorstellung> newResultSet = new ArrayList<Vorstellung>();
			
			//Vorstellungen filtern und die Matches dem Filterergebnis hinzufügen
			for (Vorstellung v : resultSet) {
				if (kinokette.getId() == this.getKinoById(this.getSpielplanById(v.getSpielplanId()).getKinoId()).getKinokettenId()) {
					newResultSet.add(v);
				}
			}
			//Ergebnis zurückgeben
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
		

		//Prüfen ob es überhaupt Vorstllungen gibt
		if (resultSet != null) {
			
			//Leere ArrayList anlegen für das Filterergebnis
			ArrayList<Vorstellung> newResultSet = new ArrayList<Vorstellung>();
			
			//Vorstellungen filtern und die Matches dem Filterergebnis hinzufügen
			for (Vorstellung v : resultSet) {
				if (film.getId() == v.getFilmId()) {
					newResultSet.add(v);
				}
			}
			//Ergebnis zurückgeben
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
		
	
		//Prüfen ob es überhaupt Vorstllungen gibt
		if (resultSet != null) {
			
			//Leere ArrayList anlegen für das Filterergebnis
			ArrayList<Vorstellung> newResultSet = new ArrayList<Vorstellung>();
			
			//Vorstellungen filtern und die Matches dem Filterergebnis hinzufügen
			for (Vorstellung v : resultSet) {
				if (spielzeit.getId() == v.getSpielzeitId()) {
					newResultSet.add(v);
				}
			}
			
			//Ergebnis zurückgeben
			return newResultSet;
		}else {
			return resultSet;
		}
	}

	/**
	 * <p>
	 * Setzen eines Spielplans für alle Kinos einer Kinokette.
	 * </p>
	 */
	@Override
	public void setSpielplanForKinosByKinokette(Spielplan spielplan, Kino kino) throws IllegalArgumentException {
		
		//Alle Kinos einer Kinokette suchen.
		ArrayList<Kino> resultSet = this.getKinosByKinoketteId(kino.getKinokettenId());
		
		//Den Spielplan für alle Kinos setzen.
		if(resultSet != null) {
			for (Kino k : resultSet) {
				this.kinoMapper.addKinokette(this.kinoketteMapper.findById(kino.getKinokettenId()), kino);
			}
		}
		
	}

	/**
	 * <p>
	 * Rückgabe aller Auswahlen einer Umfrageoption
	 * </p>
	 */
	@Override
	public ArrayList<Auswahl> getAuswahlenByUmfrageoption(Umfrageoption umfrageoption) throws IllegalArgumentException {
		return this.auswahlMapper.findAllByUmfrageoption(umfrageoption);
	}

	/**
	 * <p>
	 * Rückgabe einer Auswahl eines Anwenders bei einer Umfrageoption
	 * </p>
	 */
	@Override
	public Auswahl getAuswahlByAnwenderAndUmfrageoption(Anwender anwender, Umfrageoption umfrageoption)
			throws IllegalArgumentException {
		return this.auswahlMapper.findByAnwenderAndUmfrageoption(anwender,umfrageoption);
	}
	
	/**
	 * <p>
	 * Rückgabe der Auswahlen die ein Anwender besitzt.
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
	 * Prüfen ob ein Ergebnis gefunden wurde. Bei einem Ergebnis wird true zurückgegeben 
	 * bei einer Stichwahl wird false zurückgegeben.
	 * </p>
	 */
	@Override
	public boolean ergebnisGefunden(Umfrage umfrage) throws IllegalArgumentException {
		//Alle Umfrageoptionen der Umfrage suchen
		ArrayList<Umfrageoption> resultSet = this.getUmfrageoptionenByUmfrage(umfrage);
		
		//Prüfen ob es Umfrageoptionen gibt
		if(resultSet != null) {
			
			//Ergebnisse berechnen
			for (Umfrageoption u : resultSet) {
				u.setVoteErgebnis(this.berechneAuswahlenByUmfrageoption(u));
			}	
			
			Umfrageoption max = null;
			
			//Höchstes Ergebnis suchen
			for (Umfrageoption u : resultSet) {
				if (max == null) {
					max = u;
				} else if(max.getVoteErgebnis() < u.getVoteErgebnis()) {
					max = u;
				}
			}
			
			//Rausfinden ob sich das höchste Ergebnis doppelt
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
	 * Rückgabe der Umfrageoption die die meisten Stimmen bekommen hat bei einer Umfrage.
	 * </p>
	 */
	public Umfrageoption umfrageGewinnerErmitteln(Umfrage umfrage) throws IllegalArgumentException {
		//Umfrageoptionen anhand der Umfrage suchen
		ArrayList<Umfrageoption> resultSet = this.getUmfrageoptionenByUmfrage(umfrage);
		
		Umfrageoption max = null;
		
		//Prüfen ob es Umfrageoptionen gibt
		if(resultSet != null) {
			
			//Ergebnisse berechenen
			for (Umfrageoption u : resultSet) {
				u.setVoteErgebnis(this.berechneAuswahlenByUmfrageoption(u));
			}	
			
			//Höchstes Ergebnis ermitteln
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
	 * Rückgabe der Umfrageoptionen, die bei einer Stichwahl für die Umfrage verwendet werden 
	 * müssen.
	 * </p>
	 */
	public ArrayList<Umfrageoption> stichwahlUmfrageoptionenErmitteln(Umfrage umfrage) {
		//Umfrageoptionen der Umfrage suchen
		ArrayList<Umfrageoption> resultSet = this.getUmfrageoptionenByUmfrage(umfrage);
		
		//Leere ArrayList für die Ergebnisse bereitstellen
		ArrayList<Umfrageoption> stichwahlResultSet = null;
		
		//Prüfen ob es Umfrageoptionen gibt
		if(resultSet != null) {
			
			//Ergebnisse berechnen
			for (Umfrageoption u : resultSet) {
				u.setVoteErgebnis(this.berechneAuswahlenByUmfrageoption(u));
			}	
					
			Umfrageoption max = null;
			
			//Höchstes Ergebnis finden
			for (Umfrageoption u : resultSet) {
				if (max == null) {
					max = u;
				} else if(max.getVoteErgebnis() < u.getVoteErgebnis()) {
					max = u;
				}
			}
			
			//Stichwahlopionen suchen und hinzufügen
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
	 * Starten einer Stichwahl für eine Umfrage.
	 * </p>
	 */
	@Override
	public Umfrage stichwahlStarten(Umfrage umfrage) throws IllegalArgumentException {
		
		//Umfragename erstellen
		String name = "Stichwahl " + umfrage.getName();
		
		//Umfrage für die Stichwahl erstellen
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
				
		//Erstellen einer leeren ArrayList für die Auswahlen
		ArrayList<Auswahl> resAuswahlen = null;
		
		//Suchen aller Auswahlen für die Umfrageoptionen und hinzufügen in die Auswahlen ArrayList
		for (Umfrageoption u : umfrageoptionen) {
			ArrayList<Auswahl> auswahlen = this.getAuswahlenByUmfrageoption(u);
			for (Auswahl a : auswahlen) {
				resAuswahlen.add(a);
			}
		}
		
		//Suchen aller Gruppenmitglieder die an der Umfrage teilnehemn
		ArrayList<Anwender> gruppenmitglieder = this.getGruppenmitgliederByGruppe(this.gruppeMapper.findById(umfrage.getGruppenId()));
		
		//Alle Auswahlen für jeden Anwender durchlaufen und zählen wie oft er gevotet hat
		for(Anwender a : gruppenmitglieder) {
			int count = 0;
			for(Auswahl aus : resAuswahlen) {
				if(aus.getBesitzerId() == a.getId()) {
					count++;
				}
			}
			
			//Wenn der Anwender noch keinen Vote erstellt hat für die Umfrage, so ist die Umfrage noch offen
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
				
		//Erstellen einer leeren ArrayList für die Auswahlen
		ArrayList<Auswahl> resAuswahlen = new ArrayList<Auswahl>();
		
		//Suchen aller Auswahlen für die Umfrageoptionen und hinzufügen in die Auswahlen ArrayList
		for (Umfrageoption u : umfrageoptionen) {
			ArrayList<Auswahl> auswahlen = this.getAuswahlenByUmfrageoption(u);
			for (Auswahl a : auswahlen) {
				resAuswahlen.add(a);
			}
		}
				
		//Alle Auswahlen für den Anwender durchlaufen und zählen wie oft er gevotet hat
		int count = 0;
		for(Auswahl aus : resAuswahlen) {
			if(aus.getBesitzerId() == auswahl.getBesitzerId()) {
				count++;
		}
						
		/**
		 * Wenn der Anwender mehr als einen Vote erstellt hat für die Umfrage, so ist die Umfrage noch geschlossen, 
		 * nachdem die Auswahl gelöscht wurde
		 */
			if(count > 1) {
				return;
			}
		}
		
		//Wenn der Anwender nur einen Vote erstellt hat, so ist sie wieder geöffnet nach dem löschen
		umfrage.setOpen(true);
		
	}
	
	/**
	 * <p>
	 * Rückgabe aller geschlosssenen Umfragen, die zeitlich noch gültig sind.
	 * </p>
	 */
	@Override
	public ArrayList<Umfrage> anzeigenVonClosedUmfragen(Anwender anwender) throws IllegalArgumentException {
		ArrayList<Umfrage> umfragen = this.getClosedUmfragenByAnwender(anwender);
		ArrayList<Umfrage> zeitgültigeUmfragen = null;
		Date date = new Date(System.currentTimeMillis());
		
		for (Umfrage u : umfragen) {
			if ((this.spielzeitMapper.findById(this.vorstellungMapper.findById(this.umfrageGewinnerErmitteln(u).getVorstellungsId()).getSpielzeitId()).getZeit()).after(date)) {
				zeitgültigeUmfragen.add(u);
			}
		}
		
		return zeitgültigeUmfragen;
	}
	
}
