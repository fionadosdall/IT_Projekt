package de.hdm.softwareProjekt.kinoPlaner.server;

import de.hdm.softwareProjekt.kinoPlaner.shared.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.Auswahl;
import de.hdm.softwareProjekt.kinoPlaner.shared.Film;
import de.hdm.softwareProjekt.kinoPlaner.shared.Gruppe;
import de.hdm.softwareProjekt.kinoPlaner.shared.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.Kinokette;
import de.hdm.softwareProjekt.kinoPlaner.shared.Kinoplaner;
import de.hdm.softwareProjekt.kinoPlaner.shared.Spielplan;
import de.hdm.softwareProjekt.kinoPlaner.shared.Spielzeit;
import de.hdm.softwareProjekt.kinoPlaner.shared.Spielzeiten;
import de.hdm.softwareProjekt.kinoPlaner.shared.Umfrage;
import de.hdm.softwareProjekt.kinoPlaner.shared.Umfrageoption;
import de.hdm.softwareProjekt.kinoPlaner.shared.Vorstellung;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class KinoplanerImpl extends RemoteServiceServlet implements Kinoplaner {
	
	private Anwender anwender;
	
	private AnwenderMapper anwenderMapper;
	
	private GruppeMapper gruppeMapper;
	
	private UmfrageMapper umfrageMapper;
	
	private SpielplanMapper spielplanMapper;
	 
	private VorstellungMapper vorstellungMapper;
	
	private KinoMapper kinoMapper;
	
	private KinoketteMapper kinoketteMapper;
	
	private FilmMapper filmMapper;
	
	private SpielzeitMapper spielzeitMapper;
	
	private UmfrageoptionMapper umfrageoptionMapper;
	
	private AuswahlMapper auswahlMapper;

	public KinoplanerImpl () throws IllegalArgumentException {
		
	}
	
	
	@Override
	public void init() throws IllegalArgumentException {
		
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
	
	@Override
	public Anwender getAnwender()  throws IllegalArgumentException {
		return this.anwender;
	}
	
	@Override
	public void setAnwender(Anwender anwender)  throws IllegalArgumentException {
		this.anwender = anwender;
	}
	
	@Override
	public Anwender erstellenAnwender(int id, String name, String gmail) throws IllegalArgumentException {
		Anwender a = new Anwender();
		a.setId(id);
		a.setName(name);
		a.setGmail(gmail);
		
		return this.anwenderMapper.insert(a);
	}
	
	@Override
	public Gruppe erstellenGruppe(int id, String name, int besitzerId) throws IllegalArgumentException {
		Gruppe g = new Gruppe();
		g.setId(id);
		g.setName(name);
		g.setBesitzerId(id);
		
		return this.gruppeMapper.insert(g);
	}
	
	@Override
	public Kino erstellenKino(int id, String name, int besitzerId, int plz, String stadt, String strassse, String hausnummer, int kinokettenId) throws IllegalArgumentException {
		Kino k = new Kino();
		k.setId(id);
		k.setName(name);
		k.setBesitzerId(id);
		k.setPLZ(plz);
		k.setStadt(stadt);
		k.setStrassse(strassse);
		k.setHausnummer(hausnummer);
		k.setKinokettenId(kinokettenId);
		
		return this.kinoMapper.insert(k);
	}
	
	public Kino erstellenKino(int id, String name, int besitzerId, int plz, String stadt, String strassse, String hausnummer) throws IllegalArgumentException {
		Kino k = new Kino();
		k.setId(id);
		k.setName(name);
		k.setBesitzerId(id);
		k.setPLZ(plz);
		k.setStadt(stadt);
		k.setStrassse(strassse);
		k.setHausnummer(hausnummer);
		
		return this.kinoMapper.insert(k);
	}
	
	@Override
	public Kinokette erstellenKinokette(int id, String name, int besitzerId,  String sitz, String website) throws IllegalArgumentException {
		Kinokette k = new Kinokette();
		k.setId(id);
		k.setName(name);
		k.setBesitzerId(id);
		k.setSitz(sitz);
		k.setWebsite(website);
		
		return this.kinoketteMapper.insert(k);		
	}
	
	@Override
	public Spielplan erstellenSpielplan(int id, String name, int besitzerId,  int kinoId) throws IllegalArgumentException {
		Spielplan s = new Spielplan();
		s.setId(id);
		s.setName(name);
		s.setBesitzerId(id);
		s.setKinoId(kinoId);
		
		return this.spielplanMapper.insert(s);
	}
	
	@Override
	public Vorstellung erstellenVorstellung(int id, String name, int spielplanId, int spielzeitId, int filmId) throws IllegalArgumentException {
		Vorstellung v = new Vorstellung();
		v.setId(id);
		v.setName(name);
		v.setSpielplanId(spielplanId);
		v.setSpielzeitId(spielzeitId);
		v.setFilmId(filmId);
		
		return this.vorstellungMapper.insert(v);
	}
	
	@Override
	public Umfrage erstellenUmfrage(int id, String name, int besitzerId, int gruppenId) throws IllegalArgumentException {
		Umfrage u = new Umfrage();
		u.setId(id);
		u.setName(name);
		u.setBesitzerId(id);
		u.setGruppenId(gruppenId);
		
		return this.umfrageMapper.insert(u);
	}
	
	@Override
	public Umfrageoption erstellenUmfrageoption(int id, String name, int umfrageId, int vorstellungId) throws IllegalArgumentException {
		Umfrageoption u = new Umfrageoption();
		u.setId(id);
		u.setName(name);
		u.setUmfrageId(umfrageId);
		u.setVorstellungId(vorstellungId);
		
		return this.umfrageoptionMapper.insert(u);		
	}
	
	@Override
	public Film erstellenFilm(int id, String name, int besitzerId, String beschreibung, int bewertung) throws IllegalArgumentException {
		Film f = new Film();
		f.setId(id);
		f.setName(name);
		f.setBesitzerId(besitzerId);
		f.setBeschreibung(beschreibung);
		f.setBewertung(bewertung);
		
		return this.filmMapper.insert(f);
	}
	
	@Override
	public Spielzeit erstellenSpielzeit(int id, String name, int besitzerId, Calendar zeit) throws IllegalArgumentException {
		Spielzeit s = new Spielzeit();
		s.setId(id);
		s.setName(name);
		s.setBesitzerId(id);
		s.setZeit(zeit);
		
		return this.spielzeitMapper.insert(s);
	}
	
	@Override
	public Auswahl erstellenAuswahl(int id, String name, int besitzerId, int voting, int umfrageoptionId) throws IllegalArgumentException {
		Auswahl a = new Auswahl();
		a.setId(id);
		a.setName(name);
		a.setBesitzerId(besitzerId);
		a.setVoting(voting);
		a.setUmfrageoptionId(umfrageoptionId);
		
		this.isVoted(a);
		return this.umfrageoptionMapper.insert(a);
		

	}
	
	public void isVoted(Auswahl auswahl) throws IllegalArgumentException {
		Umfrage u = this.umfrageMapper.findById(this.umfrageoptionMapper.findById(auswahl.getUmfrageoptionId()).getUmfrageId());
		if (u.isVoted() == false) {
			u.setVoted(true);
		}
	}
	
	@Override
	public void speichern(Anwender anwender) throws IllegalArgumentException {
		this.anwenderMapper.update(anwender);
		
	}


	@Override
	public void speichern(Gruppe gruppe) throws IllegalArgumentException {
		this.gruppeMapper.update(gruppe);
		
	}


	@Override
	public void speichern(Kino kino) throws IllegalArgumentException {
		this.kinoMapper.update(kino);
		
	}


	@Override
	public void speichern(Kinokette kinokette) throws IllegalArgumentException {
		this.kinoketteMapper.update(kinokette);
		
	}


	@Override
	public void speichern(Spielplan spielplan) throws IllegalArgumentException {
		this.spielplanMapper.update(spielplan);
		
	}


	@Override
	public void speichern(Vorstellung vorstellung) throws IllegalArgumentException {
		this.vorstellungMapper.update(vorstellung);
		
	}


	@Override
	public void speichern(Umfrage umfrage) throws IllegalArgumentException {
		this.umfrageMapper.update(umfrage);
		
	}


	@Override
	public void speichern(Umfrageoption umfrageoption) throws IllegalArgumentException {
		this.umfrageoptionMapper.update(umfrageoption);
		
	}


	@Override
	public void speichern(Film film) throws IllegalArgumentException {
		this.filmMapper.update(film);
		
	}


	@Override
	public void speichern(Spielzeit spielzeit) throws IllegalArgumentException {
		this.spielzeitMapper.update(spielzeit);
		
	}


	@Override
	public void speichern(Auswahl auswahl) throws IllegalArgumentException {
		this.auswahlMapper.update(auswahl);
		
	}


	@Override
	public void löschen(Anwender anwender) throws IllegalArgumentException {
		
		ArrayList<Spielplan> spielplaene = this.getSpielplaeneByAnwenderOwner(anwender);
		if (spielplaene != null) {
			for (Spielplan s : spielplaene) {
				this.löschen(s);
			}
		}
				
		ArrayList<Film> filme = this.getFilmeByAnwenderOwner(anwender);
		if (filme != null) {
			for (Film f : filme) {
				this.löschen(f);
			}
		}
		
		ArrayList<Spielzeit> spielzeiten = this.getSpielzeitenByAnwenderOwner(anwender);
		if (spielzeiten != null) {
			for (Spielzeit s : spielzeiten) {
				this.löschen(s);
			}
		}
		
		ArrayList<Kino> kinos = this.getKinosByAnwenderOwner(anwender);
		if (kinos != null) {
			for (Kino k : kinos) {
				this.löschen(k);
			}
		}
		
		ArrayList<Kinoketten> kinoketten = this.getKinokettenByAnwenderOwner(anwender);
		if (kinoketten != null) {
			for (Kinoketten k : kinoketten) {
				this.löschen(k);
			}
		}
		
		ArrayList<Auswahl> auswahlen = this.getAuswahlenByAnwenderOwner(anwender);
		if (auswahlen != null) {
			for (Auswahl a : auswahlen) {
				this.löschen(a);
			}
		}
				
		ArrayList<Gruppe> gruppen = this.getGruppenByAnwenderOwner(anwender);
		if (gruppen != null) {
			for (Gruppen g : gruppen) {
				this.löschen(g);
			}
		}
		
		ArrayList<Umfrage> umfragen = this.getUmfragenByAnwenderOwner(anwender);
		if (umfragen != null) {
			for (Umfrage u : umfragen) {
				this.löschen(u);
			}
		}
			
		this.anwenderMapper.delete(anwender);
		
	}


	@Override
	public void löschen(Gruppe gruppe) throws IllegalArgumentException {
		
		ArrayList<Umfrage> umfragen = this.getUmfragenByAnwenderOwner(anwender);
		if (umfragen != null) {
			for (Umfrage u : umfragen) {
				this.löschen(u);
			}
		}
		
		this.gruppeMapper.delete(gruppe);
			
	}


	@Override
	public void löschen(Kino kino) throws IllegalArgumentException {
		ArrayList<Spielplan> spielplaene = this.getSpielplaeneByKino(kino);
		if (spielplaene != null) {
			for (Spielplan s : spielplaene) {
				this.löschen(s);
			}
		}
		
		this.kinoMapper.delete(kino);
		
	}


	@Override
	public void löschen(Kinokette kinokette) throws IllegalArgumentException {
		ArrayList<Kino> kinos = this.getKinosByKinokette(kinokette);
		if (kinos != null) {
			for (Kino k : kinos) {
				this.kinoMapper.deleteKinokette(kinokette, k);
			}
		}
		
		this.kinoketteMapper.delete(kinokette);
		
	}


	@Override
	public void löschen(Spielplan spielplan) throws IllegalArgumentException {
		ArrayList<Vorstellung> vorstellungen = this.getVorstellungenBySpielplan(spielplan);
		if (vorstellungen != null) {
			for (Vorstellung v : vorstellungen) {
				this.delete(v);
			}
		}
		
		this.spielplanMapperMapper.delete(spielplan);
		
	}


	@Override
	public void löschen(Vorstellung vorstellung) throws IllegalArgumentException {
		ArrayList<Umfrageoption> umfrageoptionen = this.getUmfrageoptionenByVorstellung(vorstellung);
		if (umfrageoptionen != null) {
			for (Umfrageoption u : umfrageoptionen) {
				this.delete(u);
			}
		}
		
		this.vorstellungMapper.delete(vorstellung);
		
	}


	@Override
	public void löschen(Umfrage umfrage) throws IllegalArgumentException {
		ArrayList<Umfrageoption> umfrageoptionen = this.getUmfrageoptionenByUmfrage(umfrage);
		if (umfrageoptionen != null) {
			for (Umfrageoption u : umfrageoptionen) {
				this.delete(u);
			}
		}
		
		this.umfrageMapper.delete(umfrage);
		
	}


	@Override
	public void löschen(Umfrageoption umfrageoption) throws IllegalArgumentException {
		ArrayList<Auswahl> auswahlen = this.getAuswahlenByUmfrageoption(umfrageoption;)
		if (auswahlen != null) {
			for (Auswahl a : auswahlen) {
				this.löschen(a);
			}
		}
		
		this.umfrageoptionMapper.delete(umfrageoption);
		
	}


	@Override
	public void löschen(Film film) throws IllegalArgumentException {
		/** Problem bei der löschweitergabe: entweder nur löschbar wenn nur 
		*einmal benutzt oder löschbar und loch in den daten oder löschbar und 
		*alle spielplaneintrage werden auch gelöscht
		**/
	}


	@Override
	public void löschen(Spielzeit spielzeit) throws IllegalArgumentException {
		// same wie film
		
	}


	@Override
	public void löschen(Auswahl auswahl) throws IllegalArgumentException {
		this.auswahlMapper.delete(auswahl);
		
	}


	@Override
	public Anwender getAnwenderById(int anwenderId) throws IllegalArgumentException {
		return this.anwenderMapper.findById(anwenderId);
	}
	
	public Spielplan getSpielplanById(int spielplanId) throws IllegalArgumentException{
		return this.spielplanMapper.findById(spielplanId);
	}
	
	public Kino getKinoById(int kinoId) throws IllegalArgumentException{
		return this.kinoMapper.findById(kinoId);
	}


	@Override
	public ArrayList<Gruppe> getGruppenByAnwender(Anwender anwender) throws IllegalArgumentException {
		return this.gruppeMapper.findAllByAnwender(anwender);
	}


	@Override
	public ArrayList<Gruppe> getGruppenByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		return this.gruppeMapper.findAllByAnwenderOwner(anwender);
	}


	@Override
	public ArrayList<Umfrage> getUmfragenByAnwender(Anwender anwender) throws IllegalArgumentException {
		return this.umfrageMapper.findAllByAnwender(anwender);
	}


	@Override
	public ArrayList<Umfrage> getUmfragenByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		return this.umfrageMapper.findAllByAnwenderOwner(anwender);
	}


	@Override
	public ArrayList<Umfrage> getClosedUmfragenByAnwender(Anwender anwender) throws IllegalArgumentException {
		return this.umfrageMapper.findAllClosedByAnwender(anwender);
	}


	@Override
	public ArrayList<Kinokette> getKinokettenByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		return this.kinoketteMapper.findAllByAnwenderOwner(anwender);
	}


	@Override
	public ArrayList<Kino> getKinosByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		return this.kinoMapper.findAllByAnwenderOwner(anwender);
	}
	
	@Override
	public ArrayList<Kino> getKinosByKinoketteId(Kinokette kinokette) throws IllegalArgumentException{
		return this.getKinosByKinoketteId(kinokette.getId());
	}
	
	@Override
	public ArrayList<Kino> getKinosByKinoketteId(int kinoketteId) throws IllegalArgumentException{
		return this.kinoMapper.findAllByKinokette(kinoketteId);
	}
	

	@Override
	public ArrayList<Spielplan> getSpielplaeneByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		return this.spielplanMapper.findAllByAnwenderOwner(anwender);
	}
	
	@Override
	public ArrayList<Spielplan> getSpielplaeneByKino(Kino kino) throws IllegalArgumentException {
		return this.spielplanMapper.findAllByKino(kino);
	}


	@Override
	public ArrayList<Film> getFilmeByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		return this.filmMapper.findAllByAnwenderOwner(anwender);
	}


	@Override
	public ArrayList<Spielzeit> getSpielzeitenByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		return this.spielzeitMapper.findAllByAnwenderOwner(anwender);
	}


	@Override
	public ArrayList<Vorstellung> getVorstellungenBySpielplan(Spielplan spielplan) throws IllegalArgumentException {
		return this.vorstellungMapper.findAllBySpielplan(spielplan);
	}


	@Override
	public Anwender getAnwenderByName(String name) throws IllegalArgumentException {
		return this.anwenderMapper.findByName(name);
	}


	@Override
	public ArrayList<Umfrageoption> getUmfrageoptionenByUmfrage(Umfrage umfrage) throws IllegalArgumentException {
		return this.umfrageoptionMapper.findAllByUmfrage(umfrage);
	}
	
	@Override
	public ArrayList<Umfrageoption> getUmfrageoptionenByVorstellung(Vorstellung vorstellung) throws IllegalArgumentException {
		return this.umfrageoptionMapper.findAllByVorstellung(vorstellung);
	}


	@Override
	public ArrayList<Anwender> getGruppenmitgliederByGruppe(Gruppe gruppe) throws IllegalArgumentException {
		return this.anwenderMapper.findAllByGruppe(gruppe);
	}


	@Override
	public ArrayList<Umfrage> getUmfragenByGruppe(Gruppe gruppe) throws IllegalArgumentException {
		return this.umfrageMapper.findAllByGruppe(gruppe);
	}


	@Override
	public ArrayList<Vorstellung> getAllVorstellungen() throws IllegalArgumentException {
		return this.vorstellungMapper.findAll();
	}


	@Override
	public ArrayList<Kinokette> getAllKinoketten() throws IllegalArgumentException {
		return this.kinoketteMapper.findAll();
	}


	@Override
	public ArrayList<Kino> getAllKinos() throws IllegalArgumentException {
		return this.kinoMapper.findAll();
	}


	@Override
	public ArrayList<Film> getAllFilme() throws IllegalArgumentException {
		return this.filmMapper.findAll();
	}


	@Override
	public ArrayList<Spielzeiten> getAllSpielzeiten() throws IllegalArgumentException {
		return this.spielzeitMapper.findAll();
	}


	@Override
	public ArrayList<Vorstellung> filterResultVorstellungenByKinoOrKinokette(ArrayList<Vorstellung> resultSet,
			Kino kino) throws IllegalArgumentException {
		
		ArrayList<Vorstellung> newResultSet = new ArrayList<Vorstellung>;
	
		if (resultSet != null) {
			for (Vorstellung v : resultSet) {
				if (kino.getId() == this.getSpielplanById(v.getSpielplanId()).getKinoId()) {
					newResultSet.add(v);
				}
			}
			return newResultSet;
		}else {
			return resultSet;
		}
	}


	@Override
	public ArrayList<Vorstellung> filterResultVorstellungenByKinoOrKinokette(ArrayList<Vorstellung> resultSet,
			Kinokette kinokette) throws IllegalArgumentException {
		
		ArrayList<Vorstellung> newResultSet = new ArrayList<Vorstellung>;
		
		if (resultSet != null) {
			for (Vorstellung v : resultSet) {
				if (kinokette.getId() == this.getKinoById(this.getSpielplanById(v.getSpielplanId()).getKinoId()).getKinokettenId()) {
					newResultSet.add(v);
				}
			}
			return newResultSet;
		}else {
			return resultSet;
		}
	}


	@Override
	public ArrayList<Vorstellung> filterResultVorstellungenByFilm(ArrayList<Vorstellung> resultSet, Film film)
			throws IllegalArgumentException {
		
		ArrayList<Vorstellung> newResultSet = new ArrayList<Vorstellung>;
		
		if (resultSet != null) {
			for (Vorstellung v : resultSet) {
				if (film.getId() == v.getFilmId()) {
					newResultSet.add(v);
				}
			}
			return newResultSet;
		}else {
			return resultSet;
		}
	}


	@Override
	public ArrayList<Vorstellung> filterResultVorstellungenBySpielzeit(ArrayList<Vorstellung> resultSet,
			Spielzeit spielzeit) throws IllegalArgumentException {
		
		ArrayList<Vorstellung> newResultSet = new ArrayList<Vorstellung>;
		
		if (resultSet != null) {
			for (Vorstellung v : resultSet) {
				if (spielzeit.getId() == v.getSpielzeitId()) {
					newResultSet.add(v);
				}
			}
			return newResultSet;
		}else {
			return resultSet;
		}
	}


	@Override
	public void setSpielplanForKinosByKinokette(Spielplan spielplan, Kino kino) throws IllegalArgumentException {
		
		ArrayList<Kino> resultSet = this.getKinosByKinoketteId(kino.getKinokettenId());
		
		if(resultSet != null) {
			for (Kino k : resultSet) {
				this.kinoMapper.addKinokette(this.kinoketteMapper.findById(kino.getKinokettenId()));
			}
		}
		
	}


	@Override
	public ArrayList<Auswahl> getAuswahlenByUmfrageoption(Umfrageoption umfrageoption) throws IllegalArgumentException {
		return this.auswahlMapper.findAllByUmfrageoption(umfrageoption);
	}


	@Override
	public Auswahl getAuswahlByAnwenderAndUmfrageoption(Anwender anwender, Umfrageoption umfrageoption)
			throws IllegalArgumentException {
		return this.auswahlMapper.findByAnwenderAndUmfrageoption(anwender,umfrageoption);
	}
	

	@Override
	public int berechneAuswahlenByUmfrageoption(Umfrageoption umfrageoption) {
		ArrayList<Kino> resultSet = this.getAuswahlenByUmfrageoption(umfrageoption));
		
		if(resultSet != null) {
			int result = 0;
			for (Auswahl a : resultSet) {
				result += a.getVote();
			}
			return result;
		}else {
			return 0;
		}
		
	}
	
	@Override
	public boolean ergebnisGefunden(Umfrage umfrage) {
		ArrayList<Umfrageoption> resultSet = this.getUmfrageoptionenByUmfrage(umfrage)
		if(resultSet != null) {
			for (Umfrageoption u : resultSet) {
				u.setVoteErgebnis(this.berechneAuswahlenByUmfrageoption(u));
			}	
			
			Umfrageoption max = null;
			
			for (Umfrageoption u : resultSet) {
				if (max == null) {
					max = u;
				} else if(max.getVoteErgebnis() < u.getVoteErgebnis()) {
					max = u;
				}
			}
			
			for (Umfrageoption u : resultSet) {
				if (max.getVoteErgebnis() == u.getVoteErgebnis()) {
					return false;
				} 
			}
			return true;
		
	}
	
	public Umfrageoption umfrageGewinnerErmitteln(Umfrage umfrage) {
		ArrayList<Umfrageoption> resultSet = this.getUmfrageoptionenByUmfrage(umfrage)
		if(resultSet != null) {
			for (Umfrageoption u : resultSet) {
				u.setVoteErgebnis(this.berechneAuswahlenByUmfrageoption(u));
			}	
			
			Umfrageoption max = null;
			
			for (Umfrageoption u : resultSet) {
				if (max == null) {
					max = u;
				} else if(max.getVoteErgebnis() < u.getVoteErgebnis()) {
					max = u;
				}
			}
			
			return max;				
			
		}
		
	}
		
	
	public ArrayList<Umfrageoption> stichwahlUmfrageoptionenErmitteln(Umfrage umfrage) {
		ArrayList<Umfrageoption> resultSet = this.getUmfrageoptionenByUmfrage(umfrage)
		if(resultSet != null) {
			for (Umfrageoption u : resultSet) {
				u.setVoteErgebnis(this.berechneAuswahlenByUmfrageoption(u));
			}	
					
			Umfrageoption max = null;
					
			for (Umfrageoption u : resultSet) {
				if (max == null) {
					max = u;
				} else if(max.getVoteErgebnis() < u.getVoteErgebnis()) {
					max = u;
				}
			}
					
			ArrayList<Umfrageoption> stichwahlResultSet = null;
					
			for (Umfrageoption u : resultSet) {
				if (max.getVoteErgebnis() == u.getVoteErgebnis()) {
					stichwahlResultSet.add(u);
				} 
			}
		
		return stichwahlResultSet;
		
		}
	}

	@Override
	public Umfrage stichwahlStarten(Umfrage umfrage, ArrayList<Umfrageoption> umfrageoptionen) {
		
		String name = "Stichwahl " + umfrage.getName();
		
		Umfrage u = this.erstellenUmfrage(1, name, umfrage.getBesitzerId(), umfrage.getGruppenId());
		
		if(umfrageoptionen != null) {
			for (Umfrageoption u : umfrageoptionen) {
				String nameUmfrageoption = "Stichwahl " + u.getName();
				this.erstellenUmfrageoption(1, nameUmfrageoption, u.getUmfrageId(), u.getVorstellungId());
			}	
		}
		
		return u;
	}
	






}
