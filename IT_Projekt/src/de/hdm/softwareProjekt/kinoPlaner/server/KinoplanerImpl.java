package de.hdm.softwareProjekt.kinoPlaner.server;

import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.NeueVorstellungenCellTable;
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
import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * <p>
 * Die Klasse KinoplanerImpl serverseitige Implementierung des RPC Service und
 * damit des Interface <code>Kinoplaner</code>. In den Methoden dieser Klasse
 * ist die gesamte Applikationslogik der Website enthalten. Sie sorgt für eine
 * dauerhafte Konsitenz der Methoden und Daten. Die Klasse bedient sich Mapper
 * Klassen, welche der Datenbank-Schicht angehoeren. Diese bilden die Sicht der
 * Applikationslogik auf der realtionalen Datenbank ab.
 * </p>
 * <p>
 * Die Klasse steht mit weiteren Datentypen in Verbindung:
 * <ol>
 * <li>{@link Kinoplaner}: Ist das lokale serverseitige Interface, in welchem
 * die dem System verfuegbaren Funktionen deklariert sind.</li>
 * <li>{@link KinoplanerAsync}: Mit <code>KinplanerImpl</code> und der
 * <code>Kinplaner</code> wird die serverseitige Sicht der Applikationslogik
 * abgebildet, welche funktional vollstaendig synchron ablaeuft. Fuer die
 * clientseiteige Sicht muessen asynchrone Aufrufe eroeglicht werden, wozu
 * dieses zusaetzliche Interface dient.Das Async Interface wird durch das Google
 * Plugin semiautomatisch bei der Erstellung und Pflege unterstuetzt.</li>
 * <li>{@link RemoteServiceServlet}: Ist eine Klasse serverseitig instantiierbar
 * und clientseitig ueber GWT RPC nutzbar, so muss sie die Klasse
 * <code>RemoteServiceServlet</code> implementieren. Diese ist die funktionale
 * Basis für die Anbindung an die Runtime des GWT RPC-Mechanismus.</li>
 * </ol>
 * </p>
 * <p>
 * Jede der folgenden Methode die mithilfe von GWT RPC aufgerufen werden kann,
 * muss <code>throws IllegalArgumentException</code> in der Methodendeklaration
 * aufweisen. Das Bedeutet das diese Methoden in der Lage sind eine Instant von
 * {@link IllegalArgument Exception} auszuwerfen. Damit koennen Probleme von der
 * serverseite einfach auf die clientseite transportiert und dann mit einem
 * Catch-Block abgearbeitet werden.
 * </p>
 * 
 * @see Kinoplaner
 * @see KinoplanerAsync
 * @see RemoteServiceServlet
 */

@SuppressWarnings("serial")
public class KinoplanerImpl extends RemoteServiceServlet implements Kinoplaner {

	/**
	 * *****************************************************************************
	 * Abschnitt: ATTRIBUTE
	 * *****************************************************************************
	 */

	/**
	 * <p>
	 * Hier wird der Anwender gespeichert, in dessen Ansicht die Website momentan
	 * ausgefuehrt wird.
	 * </p>
	 */
	private Anwender anwender = null;

	/**
	 * <p>
	 * Referenziert auf den Datenbankmapper, welcher Anwender in der Datenbank
	 * verwaltet.
	 * </p>
	 */
	private AnwenderMapper anwenderMapper = null;

	/**
	 * <p>
	 * Referenziert auf den Datenbankmapper, welcher Gruppen in der Datenbank
	 * verwaltet.
	 * </p>
	 */
	private GruppeMapper gruppeMapper = null;

	/**
	 * <p>
	 * Referenziert auf den Datenbankmapper, welcher Umfragen in der Datenbank
	 * verwaltet.
	 * </p>
	 */
	private UmfrageMapper umfrageMapper = null;

	/**
	 * <p>
	 * Referenziert auf den Datenbankmapper, welcher Spielpläne in der Datenbank
	 * verwaltet.
	 * </p>
	 */
	private SpielplanMapper spielplanMapper = null;

	/**
	 * <p>
	 * Referenziert auf den Datenbankmapper, welcher Vorstellungen in der Datenbank
	 * verwaltet.
	 * </p>
	 */
	private VorstellungMapper vorstellungMapper = null;

	/**
	 * <p>
	 * Referenziert auf den Datenbankmapper, welcher Kinos in der Datenbank
	 * verwaltet.
	 * </p>
	 */
	private KinoMapper kinoMapper = null;

	/**
	 * <p>
	 * Referenziert auf den Datenbankmapper, welcher Kinoketten in der Datenbank
	 * verwaltet.
	 * </p>
	 */
	private KinoketteMapper kinoketteMapper = null;

	/**
	 * <p>
	 * Referenziert auf den Datenbankmapper, welcher Filme in der Datenbank
	 * verwaltet.
	 * </p>
	 */
	private FilmMapper filmMapper = null;

	/**
	 * <p>
	 * Referenziert auf den Datenbankmapper, welcher Spielzeiten in der Datenbank
	 * verwaltet.
	 * </p>
	 */
	private SpielzeitMapper spielzeitMapper = null;

	/**
	 * <p>
	 * Referenziert auf den Datenbankmapper, welcher Umfrageoptionenin der Datenbank
	 * verwaltet.
	 * </p>
	 */
	private UmfrageoptionMapper umfrageoptionMapper = null;

	/**
	 * <p>
	 * Referenziert auf den Datenbankmapper, welcher Auswahlen in der Datenbank
	 * verwaltet.
	 * </p>
	 */
	private AuswahlMapper auswahlMapper = null;

	/**
	 * **************************************************************************
	 * Abschnitt Ende: ATTRIBUTE
	 * **************************************************************************
	 */

	/**
	 * **************************************************************************
	 * Abschnitt: INITIALISIERUNG
	 * **************************************************************************
	 */

	/**
	 * <p>
	 * Hier wird ein <code>RemoteServiceServlet</code> unter GWT mithilfe von
	 * <code>GWT.create(Klassenname.class)</code> Client-seitig erzeugt. Dabei wird
	 * der folgende No-Argument Konstruktor benoetigt. Ein anderer Konstrukor kann
	 * derzeit nicht aufgerufen werden.
	 * </p>
	 * 
	 * @see #init()
	 * @throws IllegalArgumentException
	 */
	public KinoplanerImpl() throws IllegalArgumentException {

	}

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
	 * **************************************************************************
	 * Abschnitt Ende: INITIALISIERUNG
	 * **************************************************************************
	 */

	/**
	 * **************************************************************************
	 * Abschnitt: ERSTELLEN, LOESCHEN SPEICHERN VON BOS
	 * **************************************************************************
	 */

	/**
	 * <p>
	 * Ein neuer Anwender wird angelegt und anschließend in der Datenbank
	 * gespeichert.
	 * </p>
	 */
	@Override
	public Anwender erstellenAnwender(String name, String gmail) throws IllegalArgumentException {
		if (anwenderMapper.findByName(name) == null) {
			// Ein neues Anwender Objekt wird erstellt.
			Anwender a = new Anwender();

			// Die Attribute des Objekts werden mit Werten befüllt.
			a.setName(name);
			a.setGmail(gmail);
			a.setErstellDatum(new Timestamp(System.currentTimeMillis()));

			// Das Objekt wird in der Datenbank gespeichert und wiedergeben
			return this.anwenderMapper.insert(a);
		} else {
			return null;
		}
	}

	/**
	 * <p>
	 * Eine neue Gruppe wird angelegt und anschließend in der Datenbank gespeichert.
	 * </p>
	 */
	@Override
	public Gruppe erstellenGruppe(String name, ArrayList<Anwender> list) throws IllegalArgumentException {

		if (gruppeMapper.findByName(name) == null) {
			// Ein neues Gruppe Objekt wird erstellt.
			Gruppe g = new Gruppe();

			// Die Attribute des Objekts werden mit Werten befuellt.

			g.setName(name);
			g.setBesitzerId(this.anwender.getId());
			g.setErstellDatum(new Timestamp(System.currentTimeMillis()));

			Gruppe gFertig = this.gruppeMapper.insert(g);
			this.gruppenmitgliedHinzufuegen(anwender, gFertig);
			// Pruefen ob noch Gruppenmitglieder hinzugefuegt werden muessen und dies tun
			if (list != null) {
				for (Anwender a : list) {
					this.gruppenmitgliedHinzufuegen(a, gFertig);

				}

			}

			// Das Objekt wird in der Datenbank gespeichert und wiedergeben
			return gFertig;
		}
		return null;
	}

	/**
	 * <p>
	 * Ein neues Kino wird angelegt und anschließend in der Datenbank gespeichert.
	 * Hierbei mit der Verknüpfung zur Kinokette.
	 * </p>
	 */
	@Override
	public Kino erstellenKino(String name, int plz, String stadt, String strassse, String hausnummer, int kinokettenId)
			throws IllegalArgumentException {
		if (kinoMapper.findByName(name) == null) {
			// Ein neues Kino Objekt wird erstellt.
			Kino k = new Kino();

			// Die Attribute des Objekts werden mit Werten befuellt.
			k.setName(name);
			k.setBesitzerId(this.anwender.getId());
			k.setPlz(plz);
			k.setStadt(stadt);
			k.setStrasse(strassse);
			k.setHausnummer(hausnummer);
			k.setErstellDatum(new Timestamp(System.currentTimeMillis()));
			k.setKinokettenId(kinokettenId);

			// Das Objekt wird in der Datenbank gespeichert und wiedergeben
			return this.kinoMapper.insert(k);
		} else {
			return null;
		}
	}

	/**
	 * <p>
	 * Ein neues Kino wird angelegt und anschließend in der Datenbank gespeichert.
	 * Hierbei ohne die Verknuepfung zur Kinokette.
	 * </p>
	 */
	@Override
	public Kino erstellenKino(String name, int plz, String stadt, String strassse, String hausnummer)
			throws IllegalArgumentException {
		if (kinoMapper.findByName(name) == null) {
			// Ein neues Kino Objekt wird erstellt.
			Kino k = new Kino();

			// Die Attribute des Objekts werden mit Werten befuellt.
			k.setName(name);
			k.setBesitzerId(this.anwender.getId());
			k.setPlz(plz);
			k.setStadt(stadt);
			k.setStrasse(strassse);
			k.setHausnummer(hausnummer);
			k.setErstellDatum(new Timestamp(System.currentTimeMillis()));
			k.setKinokettenId(0);

			// Das Objekt wird in der Datenbank gespeichert und wiedergeben
			return this.kinoMapper.insert(k);
		} else {
			return null;
		}
	}

	/**
	 * <p>
	 * Eine neue Kinokette wird angelegt und anschließend in der Datenbank
	 * gespeichert.
	 * </p>
	 */
	@Override
	public Kinokette erstellenKinokette(String name, String sitz, String website) throws IllegalArgumentException {
		if (kinoketteMapper.findByName(name) == null) {
			// Ein neues Kinokette Objekt wird erstellt.
			Kinokette k = new Kinokette();

			// Die Attribute des Objekts werden mit Werten befuellt.
			k.setName(name);
			k.setBesitzerId(this.anwender.getId());
			k.setSitz(sitz);
			k.setWebsite(website);
			k.setErstellDatum(new Timestamp(System.currentTimeMillis()));

			// Das Objekt wird in der Datenbank gespeichert und wiedergeben
			return this.kinoketteMapper.insert(k);
		} else {
			return null;
		}
	}

	/**
	 * <p>
	 * Ein neuer Spielplan wird angelegt und anschließend in der Datenbank
	 * gespeichert.
	 * </p>
	 */
	@Override
	public Spielplan erstellenSpielplanKino(String name, int kinoId, ArrayList<Vorstellung> neueVorstellungen)
			throws IllegalArgumentException {

		if (spielplanMapper.findByName(name) == null) {
			// Ein neues Spielplan Objekt wird erstellt.
			Spielplan s = new Spielplan();

			// Die Attribute des Objekts werden mit Werten befuellt.
			s.setName(name);
			s.setBesitzerId(this.anwender.getId());
			s.setKinoId(kinoId);
			s.setErstellDatum(new Timestamp(System.currentTimeMillis()));
			s.setKinokettenSpielplan(false);

			// Das Objekt wird in der Datenbank gespeichert und wiedergeben
			Spielplan fertigerSpielplan = this.spielplanMapper.insertKino(s);

			if (neueVorstellungen.size() != 0) {
				for (Vorstellung v : neueVorstellungen) {
					erstellenVorstellung(fertigerSpielplan.getId(), v.getSpielzeitId(), v.getFilmId());
				}
			}

			return fertigerSpielplan;

		}
		return null;
	}

	/**
	 * <p>
	 * Ein neuer Spielplan wird fuer alle Kinos der Kinokette angelegt und
	 * anschließend in der Datenbank gespeichert.
	 * </p>
	 */
	@Override
	public ArrayList<Spielplan> erstellenSpielplaeneKinokette(String name, int kinoketteId,
			ArrayList<Vorstellung> neueVorstellungen) throws IllegalArgumentException {

		if (spielplanMapper.findByName(name) == null) {
			ArrayList<Kino> kinos = this.getKinosByKinoketteId(kinoketteId);
			ArrayList<Spielplan> spielplaene = new ArrayList<Spielplan>();

			for (Kino k : kinos) {
				// Ein neues Spielplan Objekt wird erstellt.
				Spielplan s = new Spielplan();

				// Die Attribute des Objekts werden mit Werten befuellt.
				s.setName(name);
				s.setBesitzerId(this.anwender.getId());
				s.setKinoId(k.getId());
				s.setKinokettenId(kinoketteId);
				s.setErstellDatum(new Timestamp(System.currentTimeMillis()));
				s.setKinokettenSpielplan(true);
				// Das Objekt wird in der Datenbank gespeichert und wiedergeben
				Spielplan fertigerSpielplan = this.spielplanMapper.insertKinokette(s);
				spielplaene.add(fertigerSpielplan);

				if (neueVorstellungen.size() != 0) {
					for (Vorstellung v : neueVorstellungen) {
						erstellenVorstellung(fertigerSpielplan.getId(), v.getSpielzeitId(), v.getFilmId());
					}
				}

			}

			return spielplaene;
		}
		return null;
	}

	/**
	 * <p>
	 * Eine neue Vorstellung wird angelegt und anschließend in der Datenbank
	 * gespeichert.
	 * </p>
	 */
	@Override
	public Vorstellung erstellenVorstellung(int spielplanId, int spielzeitId, int filmId)
			throws IllegalArgumentException {
		// Ein neues Vorstellung Objekt wird erstellt.
		Vorstellung v = new Vorstellung();

		// Die Attribute des Objekts werden mit Werten befuellt.
		String name = (getSpielplanById(spielplanId).getName() + spielzeitId + filmId);
		v.setName(name);
		v.setSpielplanId(spielplanId);
		v.setSpielzeitId(spielzeitId);
		v.setFilmId(filmId);
		v.setErstellDatum(new Timestamp(System.currentTimeMillis()));

		// Das Objekt wird in der Datenbank gespeichert und wiedergeben
		return this.vorstellungMapper.insert(v);
	}

	/**
	 * <p>
	 * Eine neue Umfrage wird angelegt und anschließend in der Datenbank
	 * gespeichert.
	 * </p>
	 */
	@Override
	public Umfrage erstellenUmfrage(String name, ArrayList<Vorstellung> list, int gruppenId)
			throws IllegalArgumentException {
		if (umfrageMapper.findByName(name) == null) {
			// Ein neues Umfrage Objekt wird erstellt.
			Umfrage u = new Umfrage();

			// Die Attribute des Objekts werden mit Werten befuellt.
			u.setName(name);
			u.setBesitzerId(this.anwender.getId());
			u.setGruppenId(gruppenId);
			u.setOpen(true);
			u.setVoted(false);

			Umfrage uFertig = this.umfrageMapper.insert(u);
			// Pruefen ob noch Gruppenmitglieder hinzugefuegt werden muessen und dies tun
			if (list != null) {
				for (Vorstellung v : list) {
					this.umfrageoptionHinzufuegen(v, uFertig);
				}

			}

			// Das Objekt wird in der Datenbank gespeichert und wiedergeben
			return uFertig;
		}
		return null;

	}

	/**
	 * <p>
	 * Eine neue Stichwahl wird angelegt und anschließend in der Datenbank
	 * gespeichert.
	 * </p>
	 */
	@Override
	public Umfrage erstellenStichwahl(String name, int gruppenId) throws IllegalArgumentException {
		// Ein neues Umfrage Objekt wird erstellt.
		Umfrage u = new Umfrage();

		// Die Attribute des Objekts werden mit Werten befuellt.
		u.setName(name);
		u.setBesitzerId(this.anwender.getId());
		u.setGruppenId(gruppenId);
		u.setErstellDatum(new Timestamp(System.currentTimeMillis()));
		u.setOpen(true);
		u.setVoted(false);

		// Das Objekt wird in der Datenbank gespeichert und wiedergeben
		return u;

	}

	/**
	 * <p>
	 * Eine neue Umfrageoption wird angelegt und anschließend in der Datenbank
	 * gespeichert.
	 * </p>
	 */
	@Override
	public Umfrageoption erstellenUmfrageoption(String name, int umfrageId, int vorstellungId)
			throws IllegalArgumentException {
		// Ein neues Umfrageoption Objekt wird erstellt.
		Umfrageoption u = new Umfrageoption();

		// Die Attribute des Objekts werden mit Werten befuellt.
		u.setName(name);
		u.setUmfrageId(umfrageId);
		u.setVorstellungsId(vorstellungId);
		u.setVoteErgebnis(0);
		u.setErstellDatum(new Timestamp(System.currentTimeMillis()));

		// Das Objekt wird in der Datenbank gespeichert und wiedergeben
		return this.umfrageoptionMapper.insert(u);
	}

	/**
	 * <p>
	 * Ein neuer Film wird angelegt und anschließend in der Datenbank gespeichert.
	 * </p>
	 */
	@Override
	public Film erstellenFilm(String name, String beschreibung, String bewertung) throws IllegalArgumentException {
		if (filmMapper.findByName(name) == null) {
			// Ein neues Film Objekt wird erstellt.
			Film f = new Film();

			// Die Attribute des Objekts werden mit Werten befuellt.
			f.setName(name);
			f.setBesitzerId(this.anwender.getId());
			f.setBeschreibung(beschreibung);
			f.setBewertung(bewertung);
			f.setErstellDatum(new Timestamp(System.currentTimeMillis()));

			// Das Objekt wird in der Datenbank gespeichert und wiedergeben
			return this.filmMapper.insert(f);
		} else {
			return null;
		}
	}

	/**
	 * <p>
	 * Ein Spielzeit wird angelegt und anschließend in der Datenbank gespeichert.
	 * </p>
	 */
	@Override
	public Spielzeit erstellenSpielzeit(String name, String zeit) throws IllegalArgumentException {
		if (spielzeitMapper.findByName(name) == null) {
			// Ein neues Spielzeit Objekt wird erstellt.
			Spielzeit s = new Spielzeit();

			// Die Attribute des Objekts werden mit Werten befuellt.
			s.setName(name);
			s.setBesitzerId(this.anwender.getId());
			s.setDatetoString(zeit);
			s.setErstellDatum(new Timestamp(System.currentTimeMillis()));

			// Das Objekt wird in der Datenbank gespeichert und wiedergeben
			return this.spielzeitMapper.insert(s);
		} else {
			return null;
		}
	}

	/**
	 * <p>
	 * Eine neue Auswahl wird angelegt,die zugehoerige Umfrage als gevotet markiert
	 * und anschließend die Auswahl in der Datenbank gespeichert.
	 * </p>
	 */
	@Override
	public Auswahl erstellenAuswahl(String name, int voting, int umfrageoptionId) throws IllegalArgumentException {
		// Ein neues Auswahl Objekt wird erstellt.
		Auswahl a = new Auswahl();

		// Die Attribute des Objekts werden mit Werten befuellt.
		a.setName(name);
		a.setBesitzerId(this.anwender.getId());
		a.setVoting(voting);
		a.setUmfrageoptionId(umfrageoptionId);
		a.setErstellDatum(new Timestamp(System.currentTimeMillis()));

		// Die zugehoerige Umfrage wird als votiert markiert.
		this.isVoted(a);

		// Das Objekt wird in der Datenbank gespeichert
		Auswahl object = this.auswahlMapper.insert(a);

		// Die Umfrage gegebenfalls schließen
		this.isClosedSetzen(a);

		return object;

	}

	/**
	 * <p>
	 * Speichern eines Anwenders.
	 * </p>
	 */
	@Override
	public Anwender speichern(Anwender anwender) throws IllegalArgumentException {
		if (nameVerfuegbarAnwender(anwender) == true) {
			return this.anwenderMapper.update(anwender);
		} else {
			return null;
		}

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
	public Kino speichern(Kino kino) throws IllegalArgumentException {
		if (nameVerfuegbarKino(kino) == true) {
			return this.kinoMapper.update(kino);
		} else {
			return null;
		}

	}

	/**
	 * <p>
	 * Speichern einer Kinokette.
	 * </p>
	 */
	@Override
	public Kinokette speichern(Kinokette kinokette) throws IllegalArgumentException {
		if (nameVerfuegbarKinokette(kinokette) == true) {
			return this.kinoketteMapper.update(kinokette);
		} else {
			return null;
		}

	}

	/**
	 * <p>
	 * Speichern eines Spielplans.
	 * </p>
	 */
	@Override
	public Spielplan speichern(Spielplan spielplan) throws IllegalArgumentException {
		ArrayList<Spielplan> spielpl = this.spielplanMapper.findAllByName(spielplan.getName());
		if (spielplan.isKinokettenSpielplan() == false) {
			if (spielpl.size() > 1) {
				for (Spielplan sp : spielpl) {
					if (sp.getId() != spielplan.getId()) {
						this.loeschen(sp);
					}
				}
			}

		} else {
			for (Spielplan s : spielpl) {
				s.setName(spielplan.getName());
				s.setBesitzerId(spielplan.getBesitzerId());
				s.setKinoId(spielplan.getKinoId());
				s.setKinokettenId(spielplan.getKinokettenId());
				s.setKinokettenSpielplan(true);
				this.spielplanMapper.update(s);

			}
		}
		return this.spielplanMapper.update(spielplan);

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
	public Film speichern(Film film) throws IllegalArgumentException {
		if (nameVerfuegbarFilm(film) == true) {
			return this.filmMapper.update(film);
		} else {
			return null;
		}

	}

	/**
	 * <p>
	 * Speichern einer Spielzeit.
	 * </p>
	 */
	@Override
	public Spielzeit speichern(Spielzeit spielzeit) throws IllegalArgumentException {
		if (nameVerfuegbarSpielzeit(spielzeit) == true) {
			return this.spielzeitMapper.update(spielzeit);
		} else {
			return null;
		}
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
	 * Loeschen eines Anwenders mit Loeschweitergabe.
	 * </p>
	 */
	@Override
	public void loeschen(Anwender anwender) throws IllegalArgumentException {
		// Loeschen aller zugehoerigen Spielplaene
		ArrayList<Spielplan> spielplaene = this.getSpielplaeneByAnwenderOwner();
		if (spielplaene != null) {
			for (Spielplan s : spielplaene) {
				this.loeschen(s);
			}
		}

		// Loeschen aller zugehoerigen Filme
		ArrayList<Film> filme = this.getFilmeByAnwenderOwner();
		if (filme != null) {
			for (Film f : filme) {
				this.loeschen(f);
			}
		}

		// Loeschen aller zugehoerigen Spielzeiten
		ArrayList<Spielzeit> spielzeiten = this.getSpielzeitenByAnwenderOwner();
		if (spielzeiten != null) {
			for (Spielzeit s : spielzeiten) {
				this.loeschen(s);
			}
		}

		// Loeschen aller zugehoerigen Kinos
		ArrayList<Kino> kinos = this.getKinosByAnwenderOwner();
		if (kinos != null) {
			for (Kino k : kinos) {
				this.loeschen(k);
			}
		}

		// Loeschen aller zugehoerigen Kinoketten
		ArrayList<Kinokette> kinoketten = this.getKinokettenByAnwenderOwner();
		if (kinoketten != null) {
			for (Kinokette k : kinoketten) {
				this.loeschen(k);
			}
		}

		// Loeschen aller zugehoerigen Auswahlen
		ArrayList<Auswahl> auswahlen = this.getAuswahlenByAnwenderOwner();
		if (auswahlen != null) {
			for (Auswahl a : auswahlen) {
				this.loeschen(a);
			}
		}

		// Weitergabe des Gruppenbesitz an anderes Gruppenmitglied
		ArrayList<Gruppe> gruppen = this.getGruppenByAnwenderOwner();
		if (gruppen != null) {
			for (Gruppe g : gruppen) {
				ArrayList<Anwender> gruppenmitglieder = this.anwenderMapper.findAllByGruppe(g);
				if (gruppenmitglieder != null) {
					if (gruppenmitglieder.get(0).equals(anwender)) {
						if (gruppenmitglieder.get(1) != null) {
							g.setBesitzerId(gruppenmitglieder.get(1).getId());
						} else {
							this.loeschen(g);
						}
					} else {
						g.setBesitzerId(gruppenmitglieder.get(0).getId());
					}
				} else {
					this.loeschen(g);
				}
			}
		}

		// Weitergabe des Umfragebesitz an ein anderes Gruppenmitlgied
		ArrayList<Umfrage> umfragen = this.getUmfragenByAnwenderOwner();
		if (umfragen != null) {
			for (Umfrage u : umfragen) {
				ArrayList<Anwender> gruppenmitglieder = this.anwenderMapper
						.findAllByGruppe(this.gruppeMapper.findById(u.getGruppenId()));
				if (gruppenmitglieder != null) {
					if (gruppenmitglieder.get(0).equals(anwender)) {
						if (gruppenmitglieder.get(1) != null) {
							u.setBesitzerId(gruppenmitglieder.get(1).getId());
						} else {
							this.loeschen(u);
						}
					} else {
						u.setBesitzerId(gruppenmitglieder.get(0).getId());
					}
				} else {
					this.loeschen(u);
				}
			}
		}

		// Loeschen des Anwenders aus allen Gruppen
		ArrayList<Gruppe> gruppenAnwender = this.gruppeMapper.findAllByAnwender(anwender);
		for (Gruppe g : gruppenAnwender) {
			this.gruppeMapper.deleteGruppenmitgliedschaft(anwender, g);
		}

		// Loeschen des Anwenders
		this.anwenderMapper.delete(anwender);

	}

	/**
	 * <p>
	 * Loeschen einer Gruppe mit Loeschweitergabe.
	 * </p>
	 */
	@Override
	public void loeschen(Gruppe gruppe) throws IllegalArgumentException {
		// Loeschen aller zugehoerigen Umfragen
		ArrayList<Umfrage> umfragen = this.getUmfragenByAnwenderOwner();
		if (umfragen != null) {
			for (Umfrage u : umfragen) {
				this.loeschen(u);
			}
		}

		// Loeschen der Gruppe
		this.gruppeMapper.delete(gruppe);

	}

	/**
	 * <p>
	 * Loeschen eines Kinos mit Loeschweitergabe.
	 * </p>
	 */
	@Override
	public void loeschen(Kino kino) throws IllegalArgumentException {
		// Loeschen aller zugehoerigen Spielplaene
		ArrayList<Spielplan> spielplaene = this.getSpielplaeneByKino(kino);
		if (spielplaene != null) {
			for (Spielplan s : spielplaene) {
				this.loeschen(s);
			}
		}

		// Loeschen des Kinos
		this.kinoMapper.delete(kino);

	}

	/**
	 * <p>
	 * Loeschen einer Kinokette mit Loeschweitergabe.
	 * </p>
	 */
	@Override
	public void loeschen(Kinokette kinokette) throws IllegalArgumentException {
		// Loeschen aller zugehoerigen Spielplaene
		ArrayList<Spielplan> spielplaene = this.getSpielplaeneByKinokette(kinokette);
		if (spielplaene != null) {
			for (Spielplan s : spielplaene) {
				this.loeschen(s);
			}
		}

		// Loeschen aller zugehoerigen Kinoketten
		ArrayList<Kino> kinos = this.getKinosByKinoketteId(kinokette);
		if (kinos != null) {
			for (Kino k : kinos) {
				this.kinoMapper.deleteKinokette(k);
			}
		}

		// Loeschen der Kinokette
		this.kinoketteMapper.delete(kinokette);

	}

	/**
	 * <p>
	 * Loeschen eines Spielplans mit Loeschweitergabe.
	 * </p>
	 */
	@Override
	public void loeschen(Spielplan spielplan) throws IllegalArgumentException {
		ArrayList<Spielplan> spielpl = this.spielplanMapper.findAllByName(spielplan.getName());

		// Loeschen aller zugehoerigen Vorstellungen
		for (Spielplan sp : spielpl) {
			ArrayList<Vorstellung> vorstellungen = this.getVorstellungenBySpielplan(sp);
			if (vorstellungen != null) {
				for (Vorstellung v : vorstellungen) {
					this.loeschen(v);
				}
			}
		}

		// Loeschen des Spielplans
		for (Spielplan sp : spielpl) {
			this.spielplanMapper.delete(sp);
		}

	}

	/**
	 * <p>
	 * Loeschen einer Vorstellung mit Loeschweitergabe.
	 * </p>
	 */
	@Override
	public void loeschen(Vorstellung vorstellung) throws IllegalArgumentException {
		// Loeschen aller zugehoerigen Umfrageoptionen
		ArrayList<Umfrageoption> umfrageoptionen = this.getUmfrageoptionenByVorstellung(vorstellung);
		if (umfrageoptionen != null) {
			for (Umfrageoption u : umfrageoptionen) {
				this.loeschen(u);
			}
		}

		// Loeschen der Vorstellung
		this.vorstellungMapper.delete(vorstellung);

	}

	/**
	 * <p>
	 * Loeschen einer Umfrage mit Loeschweitergabe.
	 * </p>
	 */
	@Override
	public void loeschen(Umfrage umfrage) throws IllegalArgumentException {
		// Loeschen aller zugehoerigen Umfrageoptionen
		ArrayList<Umfrageoption> umfrageoptionen = this.getUmfrageoptionenByUmfrage(umfrage);
		if (umfrageoptionen != null) {
			for (Umfrageoption u : umfrageoptionen) {
				this.loeschen(u);
			}
		}

		// Loeschen der Umfrage
		this.umfrageMapper.delete(umfrage);

	}

	/**
	 * <p>
	 * Loeschen einer Umfrageoption mit Loeschweitergabe.
	 * </p>
	 */
	@Override
	public void loeschen(Umfrageoption umfrageoption) throws IllegalArgumentException {
		// Loeschen aller zugehoerigen Auswahlen
		ArrayList<Auswahl> auswahlen = this.getAuswahlenByUmfrageoption(umfrageoption);
		if (auswahlen != null) {
			for (Auswahl a : auswahlen) {
				this.loeschen(a);
			}
		}

		// Loeschen der Umfrageoption
		this.umfrageoptionMapper.delete(umfrageoption);

	}

	/**
	 * <p>
	 * Loeschen eines Films mit Loeschweitergabe wenn moeglich (true). Moeglich ist
	 * das Loeschen wenn das Objekt maximal einmal verwendet wird.
	 * </p>
	 */
	@Override
	public boolean loeschen(Film film) throws IllegalArgumentException {
		// Alle Vorstellungen mit dem Film zuruekgeben
		ArrayList<Vorstellung> vorstellungen = this.vorstellungMapper.findByFilm(film);

		// Wenn der Film weniger oder gleich 1 mal benutzt wird
		if (vorstellungen.size() <= 1) {

			// Und wenn der Film genau einmal benutz wird
			if (vorstellungen.size() == 1) {

				// Dann loesche die zugehörige Vorstellung
				this.loeschen(vorstellungen.get(0));
			}

			// Dannach loesche die Spielzeit
			this.filmMapper.delete(film);

			// Gib zurueck, dass erfolgreich geloescht wurde
			return true;
		} else {

			// Gib zurueck das nicht erfolgreich geloescht wurde
			return false;
		}
	}

	/**
	 * <p>
	 * Loeschen einer Spielzeit mit Loeschweitergabe wenn moeglich (true). Moeglich
	 * ist das Loeschen wenn das Objekt maximal einmal verwendet wird.
	 * </p>
	 */
	@Override
	public boolean loeschen(Spielzeit spielzeit) throws IllegalArgumentException {
		// Alle Vorstellungen mit der Spielzeit zuruekgeben
		ArrayList<Vorstellung> spielzeiten = this.vorstellungMapper.findBySpielzeit(spielzeit);

		// Wenn die Spielzeit weniger oder gleich 1 mal benutzt wird
		if (spielzeiten.size() <= 1) {

			// Und wenn die Spielzeit genau einmal benutz wird
			if (spielzeiten.size() == 1) {

				// Dann loesche die zugehörige Vorstellung
				this.loeschen(spielzeiten.get(0));
			}
			// Dannach loesche die Spielzeit
			this.spielzeitMapper.delete(spielzeit);

			// Gib zurueck, dass erfolgreich geloescht wurde
			return true;
		} else {

			// Gib zurueck das nicht erfolgreich geloescht wurde
			return false;
		}
	}

	/**
	 * <p>
	 * Loeschen einer Auswahl.
	 * </p>
	 */
	@Override
	public void loeschen(Auswahl auswahl) throws IllegalArgumentException {

		// Prüfen ob die Umfrage noch gevotet ist
		this.isVotedEntfernen(auswahl);

		// Loeschen der Auswahl
		this.auswahlMapper.delete(auswahl);

		// Die Umfrage ggegebenfalls oeffnen
		this.isClosedEntfernen(auswahl);

	}

	/**
	 * <p>
	 * Loeschen einer Kinokette durch die ID.
	 * </p>
	 */
	@Override
	public void loeschenKinoketteById(int id) throws IllegalArgumentException {
		// Loeschen aller zugehoerigen Spielplaene
		ArrayList<Spielplan> spielplaene = this.getSpielplaeneByKinokette(this.kinoketteMapper.findById(id));
		if (spielplaene != null) {
			for (Spielplan s : spielplaene) {
				this.loeschen(s);
			}
		}

		// Loeschen aller zugehoerigen Kinoketten
		ArrayList<Kino> kinos = this.getKinosByKinoketteId(this.kinoketteMapper.findById(id));
		if (kinos != null) {
			for (Kino k : kinos) {
				this.kinoMapper.deleteKinokette(k);
			}
		}

		// Loeschen der Kinokette
		this.kinoketteMapper.delete(this.kinoketteMapper.findById(id));
	}

	/**
	 * <p>
	 * Rueckgabe ob der Name des Anwenders verfuegbar ist (true)
	 * </p>
	 */
	@Override
	public boolean nameVerfuegbarAnwender(Anwender name) throws IllegalArgumentException {
		return this.anwenderMapper.nameVerfügbar(name);
	}

	/**
	 * <p>
	 * Rueckgabe ob der Name der Gruppe verfuegbar ist (true)
	 * </p>
	 */
	@Override
	public boolean nameVerfuegbarGruppe(Gruppe name) throws IllegalArgumentException {
		return this.gruppeMapper.nameVerfügbar(name);
	}

	/**
	 * <p>
	 * Rueckgabe ob der Name des Kinos verfuegbar ist (true)
	 * </p>
	 */
	@Override
	public boolean nameVerfuegbarKino(Kino name) throws IllegalArgumentException {
		return this.kinoMapper.nameVerfügbar(name);
	}

	/**
	 * <p>
	 * Rueckgabe ob der Name der Kinokette verfuegbar ist (true)
	 * </p>
	 */
	@Override
	public boolean nameVerfuegbarKinokette(Kinokette name) throws IllegalArgumentException {
		return this.kinoketteMapper.nameVerfügbar(name);
	}

	/**
	 * <p>
	 * Rueckgabe ob der Name des Spielplans verfuegbar ist (true)
	 * </p>
	 */
	@Override
	public boolean nameVerfuegbarSpielplan(Spielplan name) throws IllegalArgumentException {
		return this.spielplanMapper.nameVerfügbar(name);
	}

	/**
	 * <p>
	 * Rueckgabe ob der Name der Vorstellung verfuegbar ist (true)
	 * </p>
	 */
	@Override
	public boolean nameVerfuegbarVorstellung(String name) throws IllegalArgumentException {
		return this.vorstellungMapper.nameVerfügbar(name);
	}

	/**
	 * <p>
	 * Rueckgabe ob der Name der Umfrage verfuegbar ist (true)
	 * </p>
	 */
	@Override
	public boolean nameVerfuegbarUmfrage(Umfrage umfrage) throws IllegalArgumentException {
		return this.umfrageMapper.nameVerfügbar(umfrage);
	}

	/**
	 * <p>
	 * Rueckgabe ob der Name der Umfrageoption verfügbar ist (true)
	 * </p>
	 */
	@Override
	public boolean nameVerfuegbarUmfrageoption(String name) throws IllegalArgumentException {
		return this.umfrageoptionMapper.nameVerfügbar(name);
	}

	/**
	 * <p>
	 * Rueckgabe ob der Name des Films verfuegbar ist (true)
	 * </p>
	 */
	@Override
	public boolean nameVerfuegbarFilm(Film name) throws IllegalArgumentException {
		return this.filmMapper.nameVerfügbar(name);
	}

	/**
	 * <p>
	 * Rueckgabe ob der Name der Spielzeit verfuegbar ist (true)
	 * </p>
	 */
	@Override
	public boolean nameVerfuegbarSpielzeit(Spielzeit name) throws IllegalArgumentException {
		return this.spielzeitMapper.nameVerfügbar(name);
	}

	/**
	 * <p>
	 * Rueckgabe ob der Name der Auswahl verfuegbar ist (true)
	 * </p>
	 */
	@Override
	public boolean nameVerfuegbarAuswahl(String name) throws IllegalArgumentException {
		return this.auswahlMapper.nameVerfügbar(name);
	}

	/**
	 * **************************************************************************
	 * Abschnitt Ende: ERSTELLEN, LOESCHEN SPEICHERN VON BOS
	 * **************************************************************************
	 */

	/**
	 * **************************************************************************
	 * Abschnitt: GETTER UND SETTER
	 * **************************************************************************
	 */

	/**
	 * <p>
	 * Rueckgabe des Anwenders, in dessen Ansicht die Website ausgefuehrt wird.
	 * </p>
	 */
	@Override
	public Anwender getAnwender() throws IllegalArgumentException {
		return this.anwender;
	}

	/**
	 * <p>
	 * Setzen des Anwenders, in dessen Ansicht die Website ausgefuehrt wird.
	 * </p>
	 */
	@Override
	public void setAnwender(Anwender anwender) throws IllegalArgumentException {
		this.anwender = anwender;
	}

	/**
	 * **************************************************************************
	 * Abschnitt Ende: GETTER UND SETTER
	 * **************************************************************************
	 */

	/**
	 * **************************************************************************
	 * Abschnitt: DB GETTER
	 * **************************************************************************
	 */

	/**
	 * <p>
	 * Rueckgabe des Anwenders mit einer bestimmten Id.
	 * </p>
	 */
	@Override
	public Anwender getAnwenderById(int anwenderId) throws IllegalArgumentException {
		return this.anwenderMapper.findById(anwenderId);
	}

	/**
	 * <p>
	 * Rueckgabe eines Spielplans mit einer bestimmten Id.
	 * </p>
	 */
	@Override
	public Spielplan getSpielplanById(int spielplanId) throws IllegalArgumentException {
		return this.spielplanMapper.findById(spielplanId);
	}

	/**
	 * <p>
	 * Rueckgabe eines Kinos mit einer bestimmten Id.
	 * </p>
	 */
	@Override
	public Kino getKinoById(int kinoId) throws IllegalArgumentException {
		return this.kinoMapper.findById(kinoId);
	}

	/**
	 * <p>
	 * Rueckgabe einer Umfrage mit einer bestimmten Id.
	 * </p>
	 */
	@Override
	public Umfrage getUmfrageById(int umfrageId) throws IllegalArgumentException {
		return this.umfrageMapper.findById(umfrageId);
	}

	/**
	 * <p>
	 * Rueckgabe einer Gruppe mit einer bestimmten Id.
	 * </p>
	 */
	@Override
	public Gruppe getGruppeById(int gruppeId) throws IllegalArgumentException {
		return this.gruppeMapper.findById(gruppeId);
	}

	/**
	 * <p>
	 * Rueckgabe einer Gruppe mit einem bestimmten Namen
	 * </p>
	 */
	@Override
	public Gruppe getGruppeByName(String name) throws IllegalArgumentException {
		return this.gruppeMapper.findByName(name);
	}

	/**
	 * <p>
	 * Rueckgabe eines Films mit einer bestimmten Id.
	 * </p>
	 */
	@Override
	public Film getFilmById(int filmId) throws IllegalArgumentException {
		return this.filmMapper.findById(filmId);
	}

	/**
	 * <p>
	 * Rueckgabe einer Gruppe mit einer bestimmten Id.
	 * </p>
	 */
	@Override
	public Kinokette getKinoketteById(int kinoketteId) throws IllegalArgumentException {
		return this.kinoketteMapper.findById(kinoketteId);
	}

	/**
	 * <p>
	 * Rueckgabe einer Spielzeit mit einer bestimmten Id.
	 * </p>
	 */
	@Override
	public Spielzeit getSpielzeitById(int spielzeitId) throws IllegalArgumentException {
		return this.spielzeitMapper.findById(spielzeitId);
	}

	/**
	 * <p>
	 * Rueckgabe einer Vorstellung mit einer bestimmten Id.
	 * </p>
	 */
	@Override
	public Vorstellung getVorstellungById(int vorstellungId) throws IllegalArgumentException {

		return this.vorstellungMapper.findById(vorstellungId);
	}

	/**
	 * <p>
	 * Alle Spielplaene ausgeben
	 * </p>
	 */
	@Override
	public ArrayList<Spielplan> getAllSpielplaene() throws IllegalArgumentException {
		return this.spielplanMapper.findAll();
	}

	/**
	 * <p>
	 * Spielplan der Umfrageoption ausgeben
	 * </p>
	 */
	@Override
	public Spielplan getSpielplanByUmfrageoption(Umfrageoption umfrageoption) throws IllegalArgumentException {
		return this.spielplanMapper
				.findById(this.vorstellungMapper.findById(umfrageoption.getVorstellungsId()).getSpielplanId());
	}

	/**
	 * <p>
	 * Rueckgabe aller Gruppen in denen der Anwender Mitglied ist.
	 * </p>
	 */
	@Override
	public ArrayList<Gruppe> getGruppenByAnwender() throws IllegalArgumentException {
		return this.gruppeMapper.findAllByAnwender(this.anwender);
	}

	/**
	 * <p>
	 * Rueckgabe aller Gruppen die einem bestimmten Anwender gehoeren.
	 * </p>
	 */
	@Override
	public ArrayList<Gruppe> getGruppenByAnwenderOwner() throws IllegalArgumentException {
		return this.gruppeMapper.findAllByAnwenderOwner(this.anwender);
	}

	/**
	 * <p>
	 * Rueckgabe aller Umfragen eines Anwenders.
	 * </p>
	 */
	@Override
	public ArrayList<Umfrage> getUmfragenByAnwender() throws IllegalArgumentException {
		return this.umfrageMapper.findAllByAnwender(this.anwender);
	}

	/**
	 * <p>
	 * Rueckgabe aller Umfragen die der Anwender besitzt.
	 * </p>
	 */
	@Override
	public ArrayList<Umfrage> getUmfragenByAnwenderOwner() throws IllegalArgumentException {
		return this.umfrageMapper.findAllByAnwenderOwner(this.anwender);
	}

	/**
	 * <p>
	 * Rueckgabe des Films einer Umfrageoption
	 * </p>
	 */
	@Override
	public Film getFilmByUmfrageoption(Umfrageoption umfrageoption) throws IllegalArgumentException {
		return this.filmMapper.findById(this.vorstellungMapper.findById(umfrageoption.getVorstellungsId()).getFilmId());
	}

	/**
	 * <p>
	 * Rueckgabe der Spielzeit einer Umfrageoption
	 * </p>
	 */
	@Override
	public Spielzeit getSpielzeitByUmfrageoption(Umfrageoption umfrageoption) throws IllegalArgumentException {
		return this.spielzeitMapper
				.findById(this.vorstellungMapper.findById(umfrageoption.getVorstellungsId()).getSpielzeitId());
	}

	/**
	 * <p>
	 * Rueckgabe des Kinos einer Umfrageoption
	 * </p>
	 */
	@Override
	public Kino getKinoByUmfrageoption(Umfrageoption umfrageoption) throws IllegalArgumentException {
		return this.kinoMapper.findById(this.spielplanMapper
				.findById(this.vorstellungMapper.findById(umfrageoption.getVorstellungsId()).getSpielplanId())
				.getKinoId());
	}

	/**
	 * <p>
	 * Rueckgabe des Kinos einer Vorstellung
	 * </p>
	 */
	@Override
	public Kino getKinoByVorstellung(Vorstellung vorstellung) throws IllegalArgumentException {
		return this.kinoMapper.findById(this.spielplanMapper.findById(vorstellung.getSpielplanId()).getKinoId());
	}

	/**
	 * <p>
	 * Rueckgabe des Kinos einer Vorstellung
	 * </p>
	 */
	@Override
	public Kinokette getKinoketteByVorstellung(Vorstellung vorstellung) throws IllegalArgumentException {
		return this.kinoketteMapper
				.findById(this.spielplanMapper.findById(vorstellung.getSpielplanId()).getKinokettenId());
	}

	/**
	 * <p>
	 * Rueckgabe der Kinokette einer Umfrageoption
	 * </p>
	 */
	@Override
	public Kinokette getKinoketteByUmfrageoption(Umfrageoption umfrageoption) throws IllegalArgumentException {
		return this.kinoketteMapper.findById(this.spielplanMapper
				.findById(this.vorstellungMapper.findById(umfrageoption.getVorstellungsId()).getSpielplanId())
				.getKinokettenId());
	}

	/**
	 * <p>
	 * Rueckgabe aller geschlossenen Umfragen des Anwenders
	 * </p>
	 */
	@Override
	public ArrayList<Umfrage> getClosedUmfragenByAnwender() {
		return this.umfrageMapper.findAllClosedByAnwender(this.anwender);
	}

	/**
	 * <p>
	 * Rueckgabe aller offenen Umfragen des Anwenders
	 * </p>
	 */
	@Override
	public ArrayList<Umfrage> getOpenUmfragenByAnwender() {
		return this.umfrageMapper.findAllOpenByAnwender(this.anwender);
	}

	/**
	 * <p>
	 * Rueckgabe aller Kinoketten die der Anwender besitzt.
	 * </p>
	 */
	@Override
	public ArrayList<Kinokette> getKinokettenByAnwenderOwner() throws IllegalArgumentException {
		return this.kinoketteMapper.findAllByAnwenderOwner(this.anwender);
	}

	/**
	 * <p>
	 * Rueckgabe aller Kinos die der Anwender besitzt.
	 * </p>
	 */
	@Override
	public ArrayList<Kino> getKinosByAnwenderOwner() throws IllegalArgumentException {
		return this.kinoMapper.findAllByAnwenderOwner(this.anwender);
	}

	/**
	 * <p>
	 * Rueckgabe aller Kinos die zu einer Kinokette gehoeren.
	 * </p>
	 */
	@Override
	public ArrayList<Kino> getKinosByKinoketteId(Kinokette kinokette) throws IllegalArgumentException {
		return this.kinoMapper.findAllByKinokette(kinokette);

	}

	/**
	 * <p>
	 * Rueckgabe aller Kinos einer Kinokette.
	 * </p>
	 */
	@Override
	public ArrayList<Kino> getKinosByKinoketteId(int kinoketteId) throws IllegalArgumentException {
		return this.getKinosByKinoketteId(this.kinoketteMapper.findById(kinoketteId));
	}

	/**
	 * <p>
	 * Rueckgabe aller Spielplaene die einer Anwender besitzt.
	 * </p>
	 */
	@Override
	public ArrayList<Spielplan> getSpielplaeneByAnwenderOwner() throws IllegalArgumentException {
		ArrayList<Spielplan> array = new ArrayList<Spielplan>();
		array = this.spielplanMapper.findAllByAnwenderOwner(this.anwender);

		return array;
	}

	/**
	 * <p>
	 * Rueckgabe aller Spielpläne eines Kinos.
	 * </p>
	 */
	@Override
	public ArrayList<Spielplan> getSpielplaeneByKino(Kino kino) throws IllegalArgumentException {
		return this.spielplanMapper.findAllByKino(kino);
	}

	/**
	 * <p>
	 * Rueckgabe aller Spielplaene einer Kinokette.
	 * </p>
	 */
	@Override
	public ArrayList<Spielplan> getSpielplaeneByKinokette(Kinokette kinokette) throws IllegalArgumentException {
		return this.spielplanMapper.findAllByKinokette(kinokette);
	}

	/**
	 * <p>
	 * Rueckgabe aller Filme die ein Anwender besitzt.
	 * </p>
	 */
	@Override
	public ArrayList<Film> getFilmeByAnwenderOwner() throws IllegalArgumentException {
		return this.filmMapper.findAllByAnwenderOwner(this.anwender);
	}

	/**
	 * <p>
	 * Rueckgabe aller Spielzeiten die ein Anwender besitzt.
	 * </p>
	 */
	@Override
	public ArrayList<Spielzeit> getSpielzeitenByAnwenderOwner() throws IllegalArgumentException {
		return this.spielzeitMapper.findAllByAnwenderOwner(this.anwender);
	}

	/**
	 * <p>
	 * Rueckgabe aller Vorstellungen eines Spielplans.
	 * </p>
	 */
	@Override
	public ArrayList<Vorstellung> getVorstellungenBySpielplan(Spielplan spielplan) throws IllegalArgumentException {
		return this.vorstellungMapper.findAllBySpielplan(spielplan);
	}

	/**
	 * <p>
	 * Rueckgabe eines Anwenders der durch den Namen gesucht wird.
	 * </p>
	 */
	@Override
	public Anwender getAnwenderByName(String name) throws IllegalArgumentException {
		return this.anwenderMapper.findByName(name);
	}

	/**
	 * <p>
	 * Rueckgabe eines Kinos der durch den Namen gesucht wird.
	 * </p>
	 */
	@Override
	public Kino getKinoByName(String name) throws IllegalArgumentException {
		return this.kinoMapper.findByName(name);
	}

	/**
	 * <p>
	 * Rueckgabe einer Kinokette der durch den Namen gesucht wird.
	 * </p>
	 */
	@Override
	public Kinokette getKinoketteByName(String name) throws IllegalArgumentException {
		return this.kinoketteMapper.findByName(name);
	}

	/**
	 * <p>
	 * Rueckgabe einer Spielzeit der durch den Namen gesucht wird.
	 * </p>
	 */
	@Override
	public Spielzeit getSpielzeitByName(String name) throws IllegalArgumentException {
		return this.spielzeitMapper.findByName(name);
	}

	/**
	 * <p>
	 * Rueckgabe aller Umfrageoptionen einer Umfrage.
	 * </p>
	 */
	@Override
	public ArrayList<Umfrageoption> getUmfrageoptionenByUmfrage(Umfrage umfrage) throws IllegalArgumentException {
		return this.umfrageoptionMapper.findAllByUmfrage(umfrage);
	}

	/**
	 * <p>
	 * Rueckgabe aller Umfrageoptionen einer Vorstellung.
	 * </p>
	 */
	@Override
	public ArrayList<Umfrageoption> getUmfrageoptionenByVorstellung(Vorstellung vorstellung)
			throws IllegalArgumentException {
		return this.umfrageoptionMapper.findAllByVorstellung(vorstellung);
	}

	/**
	 * <p>
	 * Rueckgabe aller Gruppenmitglieder (Anwender) einer Gruppe.
	 * </p>
	 */
	@Override
	public ArrayList<Anwender> getGruppenmitgliederByGruppe(Gruppe gruppe) throws IllegalArgumentException {
		return this.anwenderMapper.findAllByGruppe(gruppe);
	}

	/**
	 * <p>
	 * Rueckgabe aller Umfragen einer Gruppe.
	 * </p>
	 */
	@Override
	public ArrayList<Umfrage> getUmfragenByGruppe(Gruppe gruppe) throws IllegalArgumentException {
		return this.umfrageMapper.findAllByGruppe(gruppe);
	}

	/**
	 * <p>
	 * Zurueckgeben aller Anwender im System
	 * </p>
	 */

	@Override
	public ArrayList<Anwender> getAllAnwender() throws IllegalArgumentException {
		return this.anwenderMapper.findAll();
	}

	/**
	 * <p>
	 * Rueckgabe aller Vorstellungen im System.
	 * </p>
	 */
	@Override
	public ArrayList<Vorstellung> getAllVorstellungen() throws IllegalArgumentException {
		return this.vorstellungMapper.findAllVorstellungen();
	}

	/**
	 * <p>
	 * Rueckgabe aller Kinoketten im System.
	 * </p>
	 */
	@Override
	public ArrayList<Kinokette> getAllKinoketten() throws IllegalArgumentException {
		return this.kinoketteMapper.findAllKinoketten();
	}

	/**
	 * <p>
	 * Rueckgabe aller Kinos im System
	 * </p>
	 */
	@Override
	public ArrayList<Kino> getAllKinos() throws IllegalArgumentException {
		return this.kinoMapper.findAllKinos();
	}

	/**
	 * <p>
	 * Rueckgabe aller Filme im System
	 * </p>
	 */
	@Override
	public ArrayList<Film> getAllFilme() throws IllegalArgumentException {
		return this.filmMapper.findAll();
	}

	/**
	 * <p>
	 * Rueckgabe aller Spielzeiten im System.
	 * </p>
	 */
	@Override
	public ArrayList<Spielzeit> getAllSpielzeiten() throws IllegalArgumentException {
		return this.spielzeitMapper.findAllSpielzeiten();
	}

	/**
	 * <p>
	 * Rueckgabe aller Auswahlen einer Umfrageoption
	 * </p>
	 */
	@Override
	public ArrayList<Auswahl> getAuswahlenByUmfrageoption(Umfrageoption umfrageoption) throws IllegalArgumentException {
		return this.auswahlMapper.findAllByUmfrageoption(umfrageoption);
	}

	/**
	 * <p>
	 * Rueckgabe einer Auswahl eines Anwenders bei einer Umfrageoption
	 * </p>
	 */
	@Override
	public Auswahl getAuswahlByAnwenderAndUmfrageoption(Umfrageoption umfrageoption) throws IllegalArgumentException {
		return this.auswahlMapper.findByAnwenderAndUmfrageoption(this.anwender, umfrageoption);
	}

	/**
	 * <p>
	 * Rueckgabe der Auswahlen die ein Anwender besitzt.
	 * </p>
	 */
	@Override
	public ArrayList<Auswahl> getAuswahlenByAnwenderOwner() throws IllegalArgumentException {
		return this.auswahlMapper.findAllByAnwenderOwner(this.anwender);
	}

	/*
	 * <p> Rueckgabe der Vorstellung zu einer Umfrageoption </p>
	 */
	@Override
	public Vorstellung getVorstellungByUmfrageoption(Umfrageoption umfrageoption) {
		return this.vorstellungMapper.findById(umfrageoption.getVorstellungsId());
	}

	/**
	 * **************************************************************************
	 * Abschnitt Ende: DB GETTER
	 * **************************************************************************
	 */

	/**
	 * **************************************************************************
	 * Abschnitt: DB Beziehungen von BOs
	 * **************************************************************************
	 */

	/**
	 * <p>
	 * Hinzufuegen einer Umfrageoption zu einer Gruppe
	 * </p>
	 */
	@Override
	public Umfrageoption umfrageoptionHinzufuegen(Vorstellung vorstellung, Umfrage umfrageFertig)
			throws IllegalArgumentException {
		return this.erstellenUmfrageoption(umfrageFertig.getName(), umfrageFertig.getId(), vorstellung.getId());

	}

	/**
	 * <p>
	 * Entfernen einer Umfrageoption von einer Gruppe
	 * </p>
	 */
	@Override
	public Vorstellung umfrageoptionEntfernen(Umfrageoption umfrageoption, Umfrage umfrageFertig)
			throws IllegalArgumentException {
		this.loeschen(umfrageoption);
		return this.vorstellungMapper.findById(umfrageoption.getVorstellungsId());

	}

	/**
	 * <p>
	 * Updaten einer Umfrage mitsamt der Veränderungen der Gruppenmitglieder
	 * </p>
	 */
	@Override
	public Gruppe updateGruppe(Gruppe gruppe, ArrayList<Anwender> gruppenmitglieder) throws IllegalArgumentException {
		if (gruppeMapper.nameVerfügbar(gruppe) == true) {
			speichern(gruppe);
			ArrayList<Anwender> alteGruppenmitglieder = getGruppenmitgliederByGruppe(gruppe);
			alteGruppenmitglieder.remove(this.anwender);

			ArrayList<Anwender> fertigeGruppenmitglieder = new ArrayList<Anwender>();

			for (Anwender a : alteGruppenmitglieder) {
				if (gruppenmitglieder != null) {
					int counter = 0;

					for (Anwender aNeu : gruppenmitglieder) {
						if (a.equals(aNeu)) {
							fertigeGruppenmitglieder.add(a);
							break;
						} else {
							counter++;
						}
						if (counter == gruppenmitglieder.size()) {
							gruppenmitgliedEntfernen(a, gruppe);
							counter = 0;
						}
					}
				} else {
					gruppenmitgliedEntfernen(a, gruppe);
				}
			}

			for (Anwender aNeu : gruppenmitglieder) {
				int counter = 0;
				for (Anwender aFertig : fertigeGruppenmitglieder) {
					if (aNeu.equals(aFertig)) {
						break;
					} else {
						counter++;
					}
					if (counter == fertigeGruppenmitglieder.size()) {
						gruppenmitgliedHinzufuegen(aNeu, gruppe);
						counter = 0;
					}
				}
			}
			return gruppe;

		}
		return null;

	}

	/**
	 * <p>
	 * Updaten einer Umfrage mitsamt der Veränderungen der Umfrageoptionen
	 * </p>
	 */
	public Umfrage updateUmfrage(Umfrage umfrage, ArrayList<Vorstellung> umfrageoptionen)
			throws IllegalArgumentException {
		if (umfrageMapper.nameVerfügbar(umfrage) == true) {
			speichern(umfrage);
			ArrayList<Umfrageoption> alteUmfrageoptionen = getUmfrageoptionenByUmfrage(umfrage);

			ArrayList<Umfrageoption> fertigeUmfrageoptionen = new ArrayList<Umfrageoption>();

			for (Umfrageoption u : alteUmfrageoptionen) {
				if (umfrageoptionen != null) {
					int counter = 0;
					for (Vorstellung uNeu : umfrageoptionen) {
						if (u.getVorstellungsId() == uNeu.getId()) {
							fertigeUmfrageoptionen.add(u);
							break;
						} else {
							counter++;
						}
						if (counter == umfrageoptionen.size()) {
							loeschen(u);
							counter = 0;
						}
					}
				} else {
					loeschen(u);
				}
			}

			for (Vorstellung uNeu : umfrageoptionen) {
				int counter = 0;
				for (Umfrageoption uFertig : fertigeUmfrageoptionen) {
					if (uNeu.getId() == uFertig.getVorstellungsId()) {
						break;
					} else {
						counter++;
					}
					if (counter == fertigeUmfrageoptionen.size()) {
						erstellenUmfrageoption(umfrage.getName(), umfrage.getId(), uNeu.getId());
						counter = 0;
					}
				}
			}
			return umfrage;

		}
		return null;

	}

	/**
	 * <p>
	 * Hinzufuegen eines Gruppenmitglieds zu einer Gruppe
	 * </p>
	 */
	@Override
	public Anwender gruppenmitgliedHinzufuegen(Anwender anwender, Gruppe gruppe) throws IllegalArgumentException {
		return this.gruppeMapper.addGruppenmitgliedschaft(anwender, gruppe);

	}

	/**
	 * <p>
	 * Entfernen eines Gruppenmitglieds aus einer Gruppe.
	 * </p>
	 */
	@Override
	public Anwender gruppenmitgliedEntfernen(Anwender anwender, Gruppe gruppe) throws IllegalArgumentException {
		this.gruppeMapper.deleteGruppenmitgliedschaft(anwender, gruppe);
		return anwender;
	}

	/**
	 * <p>
	 * Kinokette zu einem Kino hinzufuegen.
	 * </p>
	 */
	@Override
	public Kino kinoDerKinoketteHinzufuegen(Kino kino, Kinokette kinokette) throws IllegalArgumentException {
		this.kinoMapper.addKinokette(kinokette, kino);
		kino.setKinokettenId(kinokette.getId());
		return kino;
	}

	/**
	 * <p>
	 * Kinokette von einem Kino entfernen.
	 * </p>
	 */
	@Override
	public Kino kinoketteEntfernen(Kino kino) throws IllegalArgumentException {
		this.kinoMapper.deleteKinokette(kino);
		kino.setKinokettenId(0);
		return kino;
	}

	/**
	 * **************************************************************************
	 * Abschnitt Ende: DB Beziehungen von BOs
	 * **************************************************************************
	 */

	/**
	 * **************************************************************************
	 * Abschnitt: Methoden
	 * **************************************************************************
	 */

	/**
	 * <p>
	 * Es wird geprueft ob der Boolean isVoted der Klasse Umfrage bereits auf True
	 * gesetzt wurde, da diese gevotet wurde. Ist dies nicht der Fall so wird der
	 * Boolean auf True gesetzt.
	 * </p>
	 */
	@Override
	public void isVoted(Auswahl auswahl) {
		// Umfrage zur Auswahl herausfinden
		Umfrage u = this.umfrageMapper
				.findById(this.umfrageoptionMapper.findById(auswahl.getUmfrageoptionId()).getUmfrageId());

		// Wenn das Attribut isVoted noch auf false steht, auf true setzen
		if (u.isVoted() == false) {
			u.setVoted(true);
		}
		umfrageMapper.update(u);
	}

	/**
	 * <p>
	 * Es wird geprueft ob der Boolean isVoted der Klasse Umfrage noch true sein
	 * darf, nach löschen der Auswahl.
	 * </p>
	 */
	@Override
	public void isVotedEntfernen(Auswahl auswahl) {

		// Umfrage zur Auswahl herausfinden
		Umfrage u = this.umfrageMapper
				.findById(this.umfrageoptionMapper.findById(auswahl.getUmfrageoptionId()).getUmfrageId());

		// Alle Umfrageoptionen zur Umfrage herausfinden
		ArrayList<Umfrageoption> uO = this.umfrageoptionMapper.findAllByUmfrage(u);

		// Alle Auswahlen zur Umfrage herausfinden
		ArrayList<Auswahl> a = new ArrayList<Auswahl>();

		for (Umfrageoption umf : uO) {
			a.addAll(this.auswahlMapper.findAllByUmfrageoption(umf));
		}

		if (a.size() == 1) {
			u.setVoted(false);
		}
		umfrageMapper.update(u);

	}

	/**
	 * <p>
	 * Filtern von Vorstellungen nach Kino oder Kinokette.
	 * </p>
	 */
	@Override
	public ArrayList<Vorstellung> filterResultVorstellungenByKino(ArrayList<Vorstellung> resultSet, Kino kino)
			throws IllegalArgumentException {

		// Pruefen ob es überhaupt Vorstllungen gibt
		if (resultSet != null) {
			if (kino != null) {

				// Leere ArrayList anlegen für das Filterergebnis
				ArrayList<Vorstellung> newResultSet = new ArrayList<Vorstellung>();

				// Vorstellungen filtern und die Matches dem Filterergebnis hinzufuegen
				for (Vorstellung v : resultSet) {
					if (kino.getId() == this.getSpielplanById(v.getSpielplanId()).getKinoId()) {
						newResultSet.add(v);
					}
				}

				// Ergebnis zurueckgeben
				return newResultSet;
			} else {
				return resultSet;
			}
		}
		return resultSet;

	}

	/**
	 * <p>
	 * Filtern von Vorstellungen nach Kino oder Kinokette.
	 * </p>
	 */
	@Override
	public ArrayList<Vorstellung> filterResultVorstellungenByKinokette(ArrayList<Vorstellung> resultSet,
			Kinokette kinokette) throws IllegalArgumentException {

		// Prüfen ob es ueberhaupt Vorstllungen gibt
		if (resultSet != null) {

			if (kinokette != null) {

				// Leere ArrayList anlegen für das Filterergebnis
				ArrayList<Vorstellung> newResultSet = new ArrayList<Vorstellung>();

				// Vorstellungen filtern und die Matches dem Filterergebnis hinzufuegen
				for (Vorstellung v : resultSet) {
					if (kinokette.getId() == this.getKinoById(this.getSpielplanById(v.getSpielplanId()).getKinoId())
							.getKinokettenId()) {
						newResultSet.add(v);
					}
				}
				// Ergebnis zurueckgeben
				return newResultSet;
			} else {
				return resultSet;
			}
		}
		return resultSet;
	}

	/**
	 * <p>
	 * Filtern von Vorstellungen nach einem Film.
	 * </p>
	 */
	@Override
	public ArrayList<Vorstellung> filterResultVorstellungenByFilm(ArrayList<Vorstellung> resultSet, Film film)
			throws IllegalArgumentException {

		if (resultSet != null) {
			if (film != null) {

				// Leere ArrayList anlegen für das Filterergebnis
				ArrayList<Vorstellung> newResultSet = new ArrayList<Vorstellung>();

				// Vorstellungen filtern und die Matches dem Filterergebnis hinzufuegen
				for (Vorstellung v : resultSet) {
					if (film.getId() == v.getFilmId()) {
						newResultSet.add(v);
					}
				}
				// Ergebnis zurueckgeben
				return newResultSet;
			} else {
				return resultSet;
			}

		}
		return resultSet;
	}

	/**
	 * <p>
	 * Filtern von Vorstellungen nach einer Spielzeit.
	 * </p>
	 */
	@Override
	public ArrayList<Vorstellung> filterResultVorstellungenBySpielzeit(ArrayList<Vorstellung> resultSet,
			Spielzeit spielzeit) throws IllegalArgumentException {

		// Pruefen ob es ueberhaupt Vorstllungen gibt
		if (resultSet != null) {

			if (spielzeit != null) {

				// Leere ArrayList anlegen für das Filterergebnis
				ArrayList<Vorstellung> newResultSet = new ArrayList<Vorstellung>();

				// Vorstellungen filtern und die Matches dem Filterergebnis hinzufügen
				for (Vorstellung v : resultSet) {
					if (spielzeit.getId() == v.getSpielzeitId()) {
						newResultSet.add(v);
					}
				}

				// Ergebnis zurueckgeben
				return newResultSet;
			} else {
				return resultSet;
			}
		}
		return resultSet;
	}

	/**
	 * <p>
	 * Setzen eines Spielplans für alle Kinos einer Kinokette.
	 * </p>
	 */
	@Override
	public void setSpielplanForKinosByKinokette(Spielplan spielplan, Kino kino) throws IllegalArgumentException {
		// Setzen des Spielplans als Kinokettenspielplan
		spielplan.setKinokettenSpielplan(true);
		this.spielplanMapper.update(spielplan);

		// Alle Kinos einer Kinokette suchen.
		ArrayList<Kino> resultSet = this.getKinosByKinoketteId(kino.getKinokettenId());

		// Den Spielplan für alle Kinos setzen.
		if (resultSet != null) {
			for (Kino k : resultSet) {
				this.kinoMapper.addKinokette(this.kinoketteMapper.findById(kino.getKinokettenId()), kino);
			}
		}

	}

	/**
	 * <p>
	 * Berechnen des Ergebnisses der Auswahlen bei einer Umfrageoption.
	 * </p>
	 */
	@Override
	public int berechneAuswahlenByUmfrageoption(Umfrageoption umfrageoption) throws IllegalArgumentException {
		// Alle Auswahlen der Umfrageoption suchen
		ArrayList<Auswahl> resultSet = this.getAuswahlenByUmfrageoption(umfrageoption);

		// Auswahlen aufadieren
		if (resultSet != null) {
			int result = 0;
			for (Auswahl a : resultSet) {
				result += a.getVoting();
			}
			return result;
		} else {
			return 0;
		}

	}

	/**
	 * <p>
	 * Pruefen ob ein Ergebnis gefunden wurde. Bei einem Ergebnis wird true
	 * zurueckgegeben bei einer Stichwahl wird false zurueckgegeben.
	 * </p>
	 */
	@Override
	public boolean ergebnisGefunden(Umfrage umfrage) throws IllegalArgumentException {
		// Alle Umfrageoptionen der Umfrage suchen
		ArrayList<Umfrageoption> resultSet = this.getUmfrageoptionenByUmfrage(umfrage);

		// Pruefen ob es Umfrageoptionen gibt
		if (resultSet != null) {

			// Ergebnisse berechnen
			for (Umfrageoption u : resultSet) {
				u.setVoteErgebnis(this.berechneAuswahlenByUmfrageoption(u));
				umfrageoptionMapper.update(u);
			}

			Umfrageoption max = null;

			// Hoechstes Ergebnis suchen
			for (Umfrageoption u : resultSet) {
				if (max == null) {
					max = u;
				} else if (max.getVoteErgebnis() < u.getVoteErgebnis()) {
					max = u;
				}
			}

			// Max aus der ArrayList entfernen
			for (Umfrageoption u : resultSet) {
				if (u.getId() == max.getId()) {
					resultSet.remove(u);
				}
			}

			// Rausfinden ob sich das hoechste Ergebnis doppelt
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
	 * Rueckgabe der Umfrageoption die die meisten Stimmen bekommen hat bei einer
	 * Umfrage.
	 * </p>
	 */
	@Override
	public Umfrageoption umfrageGewinnerErmitteln(Umfrage umfrage) throws IllegalArgumentException {
		// Umfrageoptionen anhand der Umfrage suchen
		ArrayList<Umfrageoption> resultSet = new ArrayList<Umfrageoption>();
		resultSet = this.getUmfrageoptionenByUmfrage(umfrage);

		Umfrageoption max = null;

		// Pruefen ob es Umfrageoptionen gibt
		if (resultSet.size() != 0) {

			// Ergebnisse berechenen
			for (Umfrageoption u : resultSet) {
				u.setVoteErgebnis(this.berechneAuswahlenByUmfrageoption(u));
			}

			// Hoechstes Ergebnis ermitteln
			for (Umfrageoption u : resultSet) {
				if (max == null) {
					max = u;
				} else if (max.getVoteErgebnis() < u.getVoteErgebnis()) {
					max = u;
				}
			}

		}
		return max;
	}

	/**
	 * <p>
	 * Rueckgabe der Umfrageoptionen, die bei einer Stichwahl für die Umfrage
	 * verwendet werden muessen.
	 * </p>
	 */
	@Override
	public ArrayList<Umfrageoption> stichwahlUmfrageoptionenErmitteln(Umfrage umfrage) {
		// Umfrageoptionen der Umfrage suchen
		ArrayList<Umfrageoption> resultSet = new ArrayList<Umfrageoption>();
		resultSet = this.getUmfrageoptionenByUmfrage(umfrage);

		// Leere ArrayList für die Ergebnisse bereitstellen
		ArrayList<Umfrageoption> stichwahlResultSet = new ArrayList<Umfrageoption>();

		// Pruefen ob es Umfrageoptionen gibt
		if (resultSet.size() != 0) {

			// Ergebnisse berechnen
			for (Umfrageoption u : resultSet) {
				u.setVoteErgebnis(this.berechneAuswahlenByUmfrageoption(u));
			}

			Umfrageoption max = null;

			// Hoechstes Ergebnis finden
			for (Umfrageoption u : resultSet) {
				if (max == null) {
					max = u;
				} else if (max.getVoteErgebnis() < u.getVoteErgebnis()) {
					max = u;
				}
			}

			stichwahlResultSet.add(max);

			// Max aus der ArrayList entfernen
			for (Umfrageoption u : resultSet) {
				if (u.getId() == max.getId()) {
					resultSet.remove(u);
				}
			}

			// Stichwahlopionen suchen und hinzufuegen
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

		// Umfragename erstellen
		String name = "Stichwahl " + umfrage.getName();

		// Umfrage für die Stichwahl erstellen
		Umfrage u = this.erstellenStichwahl(name, umfrage.getGruppenId());
		u.setBesitzerId(umfrage.getBesitzerId());
		Umfrage fertigeUmfrage = this.umfrageMapper.insert(u);

		// Stichwahlumfrageoptionen suchen
		ArrayList<Umfrageoption> umfrageoptionen = new ArrayList<Umfrageoption>();
		umfrageoptionen = this.stichwahlUmfrageoptionenErmitteln(umfrage);

		// Stichwahlvorstellungen suchen
		if (umfrageoptionen.size() != 0) {
			for (Umfrageoption umfr : umfrageoptionen) {
				String nameUmfrageoption = "Stichwahl " + umfr.getName();
				this.erstellenUmfrageoption(nameUmfrageoption, fertigeUmfrage.getId(), umfr.getVorstellungsId());
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
		// Suchen der Umfrage zur Auswahl
		Umfrage umfrage = this.umfrageMapper
				.findById((this.umfrageoptionMapper.findById(auswahl.getUmfrageoptionId()).getUmfrageId()));

		// Suchen aller Umfrageoptionen der Umfrage
		ArrayList<Umfrageoption> umfrageoptionen = this.getUmfrageoptionenByUmfrage(umfrage);

		// Erstellen einer leeren ArrayList für die Auswahlen
		ArrayList<Auswahl> resAuswahlen = new ArrayList<Auswahl>();

		// Suchen aller Auswahlen für die Umfrageoptionen und hinzufuegen in die
		// Auswahlen ArrayList
		for (Umfrageoption u : umfrageoptionen) {
			resAuswahlen.addAll(this.getAuswahlenByUmfrageoption(u));
		}

		// Suchen aller Gruppenmitglieder die an der Umfrage teilnehemn
		ArrayList<Anwender> gruppenmitglieder = this
				.getGruppenmitgliederByGruppe(this.gruppeMapper.findById(umfrage.getGruppenId()));

		// Alle Auswahlen für jeden Anwender durchlaufen und zaehlen wie oft er gevotet
		// hat
		for (Anwender a : gruppenmitglieder) {
			int count = 0;

			if (resAuswahlen != null) {
				for (Auswahl aus : resAuswahlen) {
					if (aus.getBesitzerId() == a.getId()) {
						count++;

					}
				}
			}

			// Wenn der Anwender noch keinen Vote erstellt hat für die Umfrage, so ist die
			// Umfrage noch offen

			if (count == 0) {
				return;
			}
		}

		// Wenn alle Anwender gevotet haben so ist die Umfrage geschlossen
		umfrage.setOpen(false);
		umfrageMapper.update(umfrage);

		// Dann wird geprüft ob eine Stichwahl oder ein Ergebnis vorliegt
		if (ergebnisGefunden(umfrage) == false) {
			this.stichwahlStarten(umfrage);
		}
	}

	/**
	 * <p>
	 * Setzen einer Umfrage auf den Zustand Open
	 * </p>
	 */
	@Override
	public void isClosedEntfernen(Auswahl auswahl) {
		// Suchen der Umfrage zur Auswahl
		Umfrage umfrage = this.umfrageMapper
				.findById((this.umfrageoptionMapper.findById(auswahl.getUmfrageoptionId()).getUmfrageId()));

		// Suchen aller Umfrageoptionen der Umfrage
		ArrayList<Umfrageoption> umfrageoptionen = this.getUmfrageoptionenByUmfrage(umfrage);

		// Erstellen einer leeren ArrayList für die Auswahlen
		ArrayList<Auswahl> resAuswahlen = new ArrayList<Auswahl>();

		// Suchen aller Auswahlen für die Umfrageoptionen und hinzufuegen in die
		// Auswahlen ArrayList
		for (Umfrageoption u : umfrageoptionen) {
			ArrayList<Auswahl> auswahlen = this.getAuswahlenByUmfrageoption(u);
			for (Auswahl a : auswahlen) {
				resAuswahlen.add(a);
			}
		}

		// Alle Auswahlen für den Anwender durchlaufen und zaehlen wie oft er gevotet
		// hat
		int count = 0;
		for (Auswahl aus : resAuswahlen) {
			if (aus.getBesitzerId() == auswahl.getBesitzerId()) {
				count++;
			}

			/**
			 * Wenn der Anwender mehr als einen Vote erstellt hat für die Umfrage, so ist
			 * die Umfrage noch geschlossen, nachdem die Auswahl gelöscht wurde
			 */
			if (count > 1) {
				return;
			}
		}

		// Wenn der Anwender nur einen Vote erstellt hat, so ist sie wieder geoeffnet
		// nach dem löschen
		umfrage.setOpen(true);
		umfrageMapper.update(umfrage);

		// Dann wird geprüft ob eine Stichwahl gestartet wurde
		ArrayList<Umfrage> stichwahlen = this.volltextSucheUmfragen("Stichwahl " + umfrage.getName());
		if (stichwahlen != null) {
			for (Umfrage u : stichwahlen) {
				this.loeschen(u);
			}
		}

	}

	/**
	 * <p>
	 * Rueckgabe aller geschlosssenen Umfragen, die zeitlich noch gueltig sind.
	 * </p>
	 */
	@Override
	public ArrayList<Umfrage> anzeigenVonClosedUmfragen() throws IllegalArgumentException {
		// Alle geschlossenen Umfragen fuer den Anwender suchen
		ArrayList<Umfrage> umfragen = this.getClosedUmfragenByAnwender();

		// Leeres Ergebnissarray anlegen
		ArrayList<Umfrage> zeitgueltigeUmfragen = new ArrayList<Umfrage>();

		// Aktuelle Zeit abrufen
		Date date = new Date(System.currentTimeMillis());

		// Für jede Umfrage sehen, ob der Gewinner der Umfrage noch nach der aktuellen
		// Zeit liegt und wenn ja, dem Ergebnissarray hinzufügen
		for (Umfrage u : umfragen) {
			if ((this.spielzeitMapper.findById(this.vorstellungMapper
					.findById(this.umfrageGewinnerErmitteln(u).getVorstellungsId()).getSpielzeitId()).getZeit())
							.after(date)) {
				zeitgueltigeUmfragen.add(u);
			}
		}

		return zeitgueltigeUmfragen;
	}

	/**
	 * <p>
	 * Volltextsuche nach Gruppen die den Text im Namen tragen.
	 * </p>
	 */
	@Override
	public ArrayList<Gruppe> volltextSucheGruppen(String text) throws IllegalArgumentException {
		// Leeres Ergebnissarray anlegen
		ArrayList<Gruppe> ergebnisse = new ArrayList<Gruppe>();

		// Text in Kleinbuchstaben umwandeln 
		String textLowerCase = text.toLowerCase();

		// Mögliche Ergebnisse abrufen
		ArrayList<Gruppe> gruppen = this.gruppeMapper.findAllByAnwender(this.getAnwender());

		// Nach Ergebnissen suchen, die den Text im Namen enthalten und diese dem
		// Ergebnissarray hinzufügen
		if (gruppen != null) {
			for (Gruppe g : gruppen) {
				String name = g.getName().toLowerCase();
				if (name.contains(textLowerCase)) {
					ergebnisse.add(g);
				}
			}
		}
		return ergebnisse;

	}

	/**
	 * <p>
	 * Volltextsuche nach Umfragen die den Text im Namen tragen.
	 * </p>
	 */
	@Override
	public ArrayList<Umfrage> volltextSucheUmfragen(String text) throws IllegalArgumentException {
		// Leeres Ergebnissarray anlegen
		ArrayList<Umfrage> ergebnisse = new ArrayList<Umfrage>();

		// Text in Kleinbuchstaben umwandeln
		String textLowerCase = text.toLowerCase();

		// Mögliche Ergebnisse abrufen
		ArrayList<Umfrage> umfragen = this.umfrageMapper.findAllByAnwender(this.getAnwender());

		// Nach Ergebnissen suchen, die den Text im Namen enthalten und diese dem
		// Ergebnissarray hinzufügen
		if (umfragen != null) {
			for (Umfrage u : umfragen) {
				String name = u.getName().toLowerCase();
				if (name.contains(textLowerCase)) {
					ergebnisse.add(u);
				}
			}
		}
		return ergebnisse;
	}

	/**
	 * <p>
	 * Volltextsuche nach Ergebnissen die den Text im Namen tragen.
	 * </p>
	 */
	@Override
	public ArrayList<Umfrage> volltextSucheErgebnisse(String text) throws IllegalArgumentException {
		// Leeres Ergebnissarray anlegen
		ArrayList<Umfrage> ergebnisse = new ArrayList<Umfrage>();

		// Text in Kleinbuchstaben umwandeln
		String textLowerCase = text.toLowerCase();

		// Mögliche Ergebnisse abrufen
		ArrayList<Umfrage> closedUmfragen = this.anzeigenVonClosedUmfragen();

		// Nach Ergebnissen suchen, die den Text im Namen enthalten und diese dem
		// Ergebnissarray hinzufügen
		if (closedUmfragen != null) {
			for (Umfrage u : closedUmfragen) {
				String name = u.getName().toLowerCase();
				if (name.contains(textLowerCase)) {
					ergebnisse.add(u);
				}
			}
		}

		return ergebnisse;

	}

	/**
	 * <p>
	 * Volltextsuche nach Kinoketten die den Text im Namen tragen.
	 * </p>
	 */
	@Override
	public ArrayList<Kinokette> volltextSucheKinoketten(String text) throws IllegalArgumentException {
		// Leeres Ergebnissarray anlegen
		ArrayList<Kinokette> ergebnisse = new ArrayList<Kinokette>();

		// Text in Kleinbuchstaben umwandeln
		String textLowerCase = text.toLowerCase();

		// Mögliche Kinoketten abrufen
		ArrayList<Kinokette> mglKinoketten = this.getKinokettenByAnwenderOwner();

		// Nach Kinoketten suchen, die den Text im Namen enthalten und diese dem
		// Ergebnissarray hinzufügen
		if (mglKinoketten != null) {
			for (Kinokette k : mglKinoketten) {
				String name = k.getName().toLowerCase();
				if (name.contains(textLowerCase)) {
					ergebnisse.add(k);
				}
			}
		}

		return ergebnisse;

	}

	/**
	 * <p>
	 * Volltextsuche nach Kinos die den Text im Namen tragen.
	 * </p>
	 */
	@Override
	public ArrayList<Kino> volltextSucheKinos(String text) throws IllegalArgumentException {
		// Leeres Ergebnissarray anlegen
		ArrayList<Kino> ergebnisse = new ArrayList<Kino>();

		// Text in Kleinbuchstaben umwandeln
		String textLowerCase = text.toLowerCase();

		// Mögliche Ergebnisse abrufen
		ArrayList<Kino> kinos = this.getKinosByAnwenderOwner();

		// Nach Ergebnissen suchen, die den Text im Namen enthalten und diese dem
		// Ergebnissarray hinzufügen
		if (kinos != null) {
			for (Kino k : kinos) {
				String name = k.getName().toLowerCase();
				if (name.contains(textLowerCase)) {
					ergebnisse.add(k);
				}
			}
		}

		return ergebnisse;

	}

	/**
	 * <p>
	 * Volltextsuche nach Spielplaenen die den Text im Namen tragen.
	 * </p>
	 */
	@Override
	public ArrayList<Spielplan> volltextSucheSpielplaene(String text) throws IllegalArgumentException {
		// Leeres Ergebnissarray anlegen
		ArrayList<Spielplan> ergebnisse = new ArrayList<Spielplan>();

		// Text in Kleinbuchstaben umwandeln
		String textLowerCase = text.toLowerCase();

		// Mögliche Ergebnisse abrufen
		ArrayList<Spielplan> spielplaene = this.getSpielplaeneByAnwenderOwner();

		// Nach Ergebnissen suchen, die den Text im Namen enthalten und diese dem
		// Ergebnissarray hinzufügen
		if (spielplaene != null) {
			for (Spielplan s : spielplaene) {
				String name = s.getName().toLowerCase();
				if (name.contains(textLowerCase)) {
					ergebnisse.add(s);
				}
			}
		}

		return ergebnisse;

	}

	/**
	 * <p>
	 * Volltextsuche nach Ergebnissen die den Text im Namen tragen.
	 * </p>
	 */
	@Override
	public ArrayList<Spielzeit> volltextSucheSpielzeit(String text) throws IllegalArgumentException {
		// Leeres Ergebnissarray anlegen
		ArrayList<Spielzeit> ergebnisse = new ArrayList<Spielzeit>();

		// Text in Kleinbuchstaben umwandeln
		String textLowerCase = text.toLowerCase();

		// Mögliche Ergebnisse abrufen
		ArrayList<Spielzeit> spielzeiten = this.getSpielzeitenByAnwenderOwner();

		// Nach Ergebnissen suchen, die den Text im Namen enthalten und diese dem
		// Ergebnissarray hinzufügen
		if (spielzeiten != null) {
			for (Spielzeit s : spielzeiten) {
				String name = s.getName().toLowerCase();
				if (name.contains(textLowerCase)) {
					ergebnisse.add(s);
				}
			}
		}

		return ergebnisse;

	}

	/**
	 * <p>
	 * Volltextsuche nach Ergebnissen die den Text im Namen tragen.
	 * </p>
	 */
	@Override
	public ArrayList<Film> volltextSucheFilm(String text) throws IllegalArgumentException {
		// Leeres Ergebnissarray anlegen
		ArrayList<Film> ergebnisse = new ArrayList<Film>();

		// Text in Kleinbuchstaben umwandeln
		String textLowerCase = text.toLowerCase();

		// Mögliche Ergebnisse abrufen
		ArrayList<Film> filme = this.getFilmeByAnwenderOwner();

		// Nach Ergebnissen suchen, die den Text im Namen enthalten und diese dem
		// Ergebnissarray hinzufügen
		if (filme != null) {
			for (Film f : filme) {
				String name = f.getName().toLowerCase();
				if (name.contains(textLowerCase)) {
					ergebnisse.add(f);
				}
			}
		}

		return ergebnisse;

	}

	/**
	 * <p>
	 * Erstellen von Auswahlen aus einem Array, mit vergleich, löschung und update
	 * bestehnder Auswahlen
	 * </p>
	 */
	@Override
	public Umfrage auswahlenErstellen(ArrayList<Auswahl> zuErstellendeAuswahlen, ArrayList<Auswahl> alteAuswahlen,
			int size, Umfrage umfrage) throws IllegalArgumentException {

		ArrayList<Auswahl> fertigeAuswahlen = new ArrayList<Auswahl>();

		for (Auswahl a : alteAuswahlen) {
			int counter = 0;
			if (size != 0) {
				for (Auswahl aGesamt : zuErstellendeAuswahlen) {
					if (a.getUmfrageoptionId() == aGesamt.getUmfrageoptionId()) {
						a.setVoting(aGesamt.getVoting());
						fertigeAuswahlen.add(a);
						speichern(a);
						break;
					} else {
						counter++;
					}
					if (counter == zuErstellendeAuswahlen.size()) {
						loeschen(a);
						counter = 0;
					}
				}
			} else {
				loeschen(a);
			}
		}

		if (size != 0) {
			for (Auswahl aGesamt : zuErstellendeAuswahlen) {
				int counter = 0;
				if (fertigeAuswahlen.size() != 0) {
					for (Auswahl aFertig : fertigeAuswahlen) {
						if (aGesamt.getUmfrageoptionId() == aFertig.getUmfrageoptionId()) {
							break;
						} else {
							counter++;
						}
						if (counter == fertigeAuswahlen.size()) {
							erstellenAuswahl(aGesamt.getName(), aGesamt.getVoting(), aGesamt.getUmfrageoptionId());
							counter = 0;
						}
					}
				} else {
					erstellenAuswahl(aGesamt.getName(), aGesamt.getVoting(), aGesamt.getUmfrageoptionId());
				}
			}
		}

		return this.umfrageMapper.findById(umfrage.getId());
	}

	/**
	 * <p>
	 * Erstellen von Vorstellunegn aus einem Array, mit vergleich, löschung und
	 * update bestehnderVorstellung, als Teil des Kinokettenspielplan
	 * </p>
	 */
	@Override
	public ArrayList<Spielplan> updateSpielplanKinokette(ArrayList<Vorstellung> zuErstellendeVorstellungen,
			Spielplan spielplan) throws IllegalArgumentException {

		speichern(spielplan);

		ArrayList<Vorstellung> fertigeVorstellungen = new ArrayList<Vorstellung>();
		ArrayList<Vorstellung> alteVorstellungen = new ArrayList<Vorstellung>();
		alteVorstellungen = getVorstellungenBySpielplan(spielplan);

		for (Vorstellung v : alteVorstellungen) {
			int counter = 0;
			if (zuErstellendeVorstellungen.size() != 0) {
				for (Vorstellung vGesamt : zuErstellendeVorstellungen) {
					if (v.getId() == vGesamt.getId()) {
						fertigeVorstellungen.add(vGesamt);
						speichern(vGesamt);
						break;
					} else {
						counter++;
					}
					if (counter == zuErstellendeVorstellungen.size()) {
						loeschen(v);
						counter = 0;
					}
				}
			} else {
				loeschen(v);
			}
		}

		if (zuErstellendeVorstellungen.size() != 0) {
			for (Vorstellung vGesamt : zuErstellendeVorstellungen) {

				if (fertigeVorstellungen.size() != 0) {

					if (vGesamt.getId() == 0) {
						erstellenVorstellung(spielplan.getId(), vGesamt.getFilmId(), vGesamt.getSpielzeitId());

					}

				} else {
					erstellenVorstellung(spielplan.getId(), vGesamt.getFilmId(), vGesamt.getSpielzeitId());
				}
			}
		}

		return null;
	}

	/**
	 * <p>
	 * Erstellen von Vorstellungen aus einem Array, mit vergleich, löschung und
	 * update bestehnder Vorstellungen
	 * </p>
	 */
	@Override
	public Spielplan updateSpielplanKino(ArrayList<Vorstellung> zuErstellendeVorstellungen, Spielplan spielplan)
			throws IllegalArgumentException {

		if (spielplanMapper.nameVerfügbar(spielplan) == true) {

			Spielplan fertigSpielplan = speichern(spielplan);

			ArrayList<Vorstellung> fertigeVorstellungen = new ArrayList<Vorstellung>();
			ArrayList<Vorstellung> alteVorstellungen = new ArrayList<Vorstellung>();
			alteVorstellungen = getVorstellungenBySpielplan(spielplan);

			for (Vorstellung v : alteVorstellungen) {
				int counter = 0;
				if (zuErstellendeVorstellungen.size() != 0) {
					for (Vorstellung vGesamt : zuErstellendeVorstellungen) {
						if (v.getId() == vGesamt.getId()) {
							fertigeVorstellungen.add(vGesamt);
							speichern(vGesamt);
							break;
						} else {
							counter++;
						}
						if (counter == zuErstellendeVorstellungen.size()) {
							loeschen(v);
							counter = 0;
						}
					}
				} else {
					loeschen(v);
				}
			}

			if (zuErstellendeVorstellungen.size() != 0) {
				for (Vorstellung vGesamt : zuErstellendeVorstellungen) {

					if (fertigeVorstellungen.size() != 0) {

						if (vGesamt.getId() == 0) {

							erstellenVorstellung(fertigSpielplan.getId(), vGesamt.getSpielzeitId(),
									vGesamt.getFilmId());

						}

					} else {
						erstellenVorstellung(fertigSpielplan.getId(), vGesamt.getSpielzeitId(), vGesamt.getFilmId());
					}
				}
			}

			return this.spielplanMapper.findById(fertigSpielplan.getId());
		}
		return null;
	}

	/**
	 * **************************************************************************
	 * Abschnitt Ende: Methoden
	 * **************************************************************************
	 */

	@Override
	public void sinnloserCallback() throws IllegalArgumentException {
		this.anwenderMapper.findAll();
	}

	@Override
	public Film getFilmByName(String name) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.filmMapper.findByName(name);
	}

}
