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
	public void erstellenAnwender(int id, String name, String gmail) throws IllegalArgumentException {
		Anwender a = new Anwender();
		a.setId(id);
		a.setName(name);
		a.setGmail(gmail);
		
		this.anwenderMapper.insert(a);
	}
	
	@Override
	public void erstellenGruppe(int id, String name, int besitzerId) throws IllegalArgumentException {
		Gruppe g = new Gruppe();
		g.setId(id);
		g.setName(name);
		g.setBesitzerId(id);
		
		this.gruppeMapper.insert(g);
	}
	
	@Override
	public void erstellenKino(int id, String name, int besitzerId, int plz, String stadt, String strassse, String hausnummer, int kinokettenId) throws IllegalArgumentException {
		Kino k = new Kino();
		k.setId(id);
		k.setName(name);
		k.setBesitzerId(id);
		k.setPLZ(plz);
		k.setStadt(stadt);
		k.setStrassse(strassse);
		k.setHausnummer(hausnummer);
		k.setKinokettenId(kinokettenId);
		
		this.kinoMapper.insert(k);
	}
	
	public void erstellenKino(int id, String name, int besitzerId, int plz, String stadt, String strassse, String hausnummer) throws IllegalArgumentException {
		Kino k = new Kino();
		k.setId(id);
		k.setName(name);
		k.setBesitzerId(id);
		k.setPLZ(plz);
		k.setStadt(stadt);
		k.setStrassse(strassse);
		k.setHausnummer(hausnummer);
		
		this.kinoMapper.insert(k);
	}
	
	@Override
	public void erstellenKinokette(int id, String name, int besitzerId,  String sitz, String website) throws IllegalArgumentException {
		Kinokette k = new Kinokette();
		k.setId(id);
		k.setName(name);
		k.setBesitzerId(id);
		k.setSitz(sitz);
		k.setWebsite(website);
		
		this.kinoketteMapper.insert(k);		
	}
	
	@Override
	public void erstellenSpielplan(int id, String name, int besitzerId,  int kinoId) throws IllegalArgumentException {
		Spielplan s = new Spielplan();
		s.setId(id);
		s.setName(name);
		s.setBesitzerId(id);
		s.setKinoId(kinoId);
		
		this.spielplanMapper.insert(s);
	}
	
	@Override
	public void erstellenVorstellung(int id, String name, int spielplanId, int spielzeitId, int filmId) throws IllegalArgumentException {
		Vorstellung v = new Vorstellung();
		v.setId(id);
		v.setName(name);
		v.setSpielplanId(spielplanId);
		v.setSpielzeitId(spielzeitId);
		v.setFilmId(filmId);
		
		this.vorstellungMapper.insert(v);
	}
	
	@Override
	public void erstellenUmfrage(int id, String name, int besitzerId, int gruppenId) throws IllegalArgumentException {
		Umfrage u = new Umfrage();
		u.setId(id);
		u.setName(name);
		u.setBesitzerId(id);
		u.setGruppenId(gruppenId);
		
		this.umfrageMapper.insert(u);
	}
	
	@Override
	public void erstellenUmfrageoption(int id, String name, int umfrageId, int vorstellungId) throws IllegalArgumentException {
		Umfrageoption u = new Umfrageoption();
		u.setId(id);
		u.setName(name);
		u.setUmfrageId(umfrageId);
		u.setVorstellungId(vorstellungId);
		
		this.umfrageoptionMapper.insert(u);		
	}
	
	@Override
	public void erstellenFilm(int id, String name, int besitzerId, String beschreibung, int bewertung) throws IllegalArgumentException {
		Film f = new Film();
		f.setId(id);
		f.setName(name);
		f.setBesitzerId(besitzerId);
		f.setBeschreibung(beschreibung);
		f.setBewertung(bewertung);
		
		this.filmMapper.insert(f);
	}
	
	@Override
	public void erstellenSpielzeit(int id, String name, int besitzerId, Calendar zeit) throws IllegalArgumentException {
		Spielzeit s = new Spielzeit();
		s.setId(id);
		s.setName(name);
		s.setBesitzerId(id);
		s.setZeit(zeit);
		
		this.spielzeitMapper.insert(s);
	}
	
	@Override
	public void erstellenAuswahl(int id, String name, int besitzerId, int voting, int umfrageoptionId) throws IllegalArgumentException {
		Auswahl a = new Auswahl();
		a.setId(id);
		a.setName(name);
		a.setBesitzerId(besitzerId);
		a.setVoting(voting);
		a.setUmfrageoptionId(umfrageoptionId);
		
		this.umfrageoptionMapper.insert(a);
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


	@Override
	public ArrayList<Gruppe> getGruppenByAnwender(Anwender anwender) throws IllegalArgumentException {
		return this.gruppeMapper.findAllByGruppenmitglied(anwender);
	}


	@Override
	public ArrayList<Gruppe> getGruppenByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		return this.gruppeMapper.findAllByAnwenderOwner(anwender);
	}


	@Override
	public ArrayList<Umfrage> getUmfragenByAnwender(Anwender anwender) throws IllegalArgumentException {
		return this.umfrageMapper.finAllByA
	}


	@Override
	public ArrayList<Umfrage> getUmfragenByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<Umfrage> getClosedUmfragenByAnwender(Anwender anwender) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<Kinokette> getKinokettenByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<Kino> getKinosByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<Kino> getKinosByKinokette(Kinokette kinokette) throws IllegalArgumentException{
		// TODO Auto-generated method stub
		return null;
	}
	


	@Override
	public ArrayList<Spielplan> getSpielplaeneByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<Spielplan> getSpielplaeneByKino(Kino kino) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<Film> getFilmeByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<Spielzeit> getSpielzeitenByAnwenderOwner(Anwender anwender) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<Vorstellung> getVorstellungenBySpielplan(Spielplan spielplan) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Anwender getAnwenderByName(String name) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<Umfrageoption> getUmfrageoptionenByUmfrage(Umfrage umfrage) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<Umfrageoption> getUmfrageoptionenByVorstellung(Vorstellung vorstellung) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<Anwender> getGruppenmitgliederByGruppe(Gruppe gruppe) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<Umfrage> getUmfragenByGruppe(Gruppe gruppe) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<Vorstellung> getAllVorstellungen() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<Kinokette> getAllKinoketten() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<Kino> getAllKinos() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<Film> getAllFilme() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<Spielzeiten> getAllSpielzeiten() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<Vorstellung> filterResultVorstellungenByKinoOrKinokette(ArrayList<Vorstellung> resultSet,
			Kino kino) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<Vorstellung> filterResultVorstellungenByKinoOrKinokette(ArrayList<Vorstellung> resultSet,
			Kinokette kino) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<Vorstellung> filterResultVorstellungenByFilm(ArrayList<Vorstellung> resultSet, Film film)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<Vorstellung> filterResultVorstellungenBySpielzeit(ArrayList<Vorstellung> resultSet,
			Spielzeit spielzeit) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setSpielplanForKinosByKinokette(Spielplan spielplan, Kino kino) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public ArrayList<Auswahl> getAuswahlenByUmfrageoption(Umfrageoption umfrageoption) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Auswahl getAuswahlByAnwenderAndUmfrageoption(Anwender anwender, Umfrageoption umfrageoption)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public int berechneAuswahlenByUmfrageoption(Umfrageoption umfrageoption) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Umfrage stichwahlStarten(Umfrage umfrage) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean ergebnisOderStichwahl(Umfrage umfrage) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}


}
