package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.i18n.shared.DefaultDateTimeFormatInfo;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.ListDataProvider;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrageoption;

/**
 * Die Klasse ErgebnisAnzeigenTable dient als Vorlage um Ergebnisse 
 * in einer ErgebnisAnzeigenForm darzustellen. 
 *
 */

public class ErgebnisAnzeigenTable extends ScrollPanel {

	private KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

	private Umfrage umfrage;
	
	//Konstruktor 

	public ErgebnisAnzeigenTable(Umfrage umfrage) {
		this.umfrage = umfrage;

	}

	
	class ErgebnisInfo {

		Umfrageoption u;;

		private String filmName;
		private String kinoName;
		private String spielzeit;
		private String stadt;
		private int ergebnis;
		public boolean lastItem;

		public int getErgebnis() {
			return ergebnis;
		}

		public void setErgebnis(int ergebnis) {
			this.ergebnis = ergebnis;
		}

		public String getStadt() {
			return stadt;
		}

		public void setStadt(String stadt) {
			this.stadt = stadt;
		}

		public Umfrageoption getU() {
			return u;
		}

		public void setU(Umfrageoption u) {
			this.u = u;
		}

		public String getFilmName() {
			return filmName;
		}

		public void setFilmName(String filmName) {
			this.filmName = filmName;
		}

		public String getKinoName() {
			return kinoName;
		}

		public String getSpielzeit() {
			return spielzeit;
		}

		public void setSpielzeit(String spielzeit) {
			this.spielzeit = spielzeit;
		}

	}

	private ErgebnisAnzeigenForm eaf;

	public void setErgebnisAnzeigenForm(ErgebnisAnzeigenForm eaf) {
		this.eaf = eaf;
	}

	CellTable<ErgebnisInfo> ergebnisCellTable;

	private ListDataProvider<ErgebnisInfo> dataProvider;
	private List<ErgebnisInfo> list;

	//onLoad()-Methode
	
	@Override
	public void onLoad() {
		
		this.setHeight("200px");
		

		
		dataProvider = new ListDataProvider<ErgebnisInfo>();

		list = dataProvider.getList();

		// CellTable
		
		ergebnisCellTable = new CellTable<ErgebnisInfo>();
		ergebnisCellTable.setWidth("100%");

		ergebnisCellTable.setAutoHeaderRefreshDisabled(true);

		ergebnisCellTable.setEmptyTableWidget(new Label("Keine Ergebnisse verfügbar!"));

		

		Column<ErgebnisInfo, String> filmColumn = new Column<ErgebnisInfo, String>(new TextCell()) {

			@Override
			public String getValue(ErgebnisInfo object) {

				return object.getFilmName();

			}
		};

		ergebnisCellTable.addColumn(filmColumn, "Film");

		Column<ErgebnisInfo, String> kinoColumn = new Column<ErgebnisInfo, String>(new TextCell()) {

			@Override
			public String getValue(ErgebnisInfo object) {

				return object.getKinoName();

			}
		};

		ergebnisCellTable.addColumn(kinoColumn, "Kino");

		Column<ErgebnisInfo, String> speilzeitColumn = new Column<ErgebnisInfo, String>(new TextCell()) {

			@Override
			public String getValue(ErgebnisInfo object) {

				return object.getSpielzeit();

			}
		};

		ergebnisCellTable.addColumn(speilzeitColumn, "Spielzeit");

		Column<ErgebnisInfo, String> stadtColumn = new Column<ErgebnisInfo, String>(new TextCell()) {

			@Override
			public String getValue(ErgebnisInfo object) {

				return object.getStadt();

			}
		};

		ergebnisCellTable.addColumn(stadtColumn, "Ort");

		Column<ErgebnisInfo, String> voteColumn = new Column<ErgebnisInfo, String>(new TextCell()) {

			@Override
			public String getValue(ErgebnisInfo object) {

				return "" + object.getErgebnis();
			}

		};

		ergebnisCellTable.addColumn(voteColumn, "Votings");

		dataProvider.addDataDisplay(ergebnisCellTable);

		this.add(ergebnisCellTable);

		kinoplaner.getUmfrageoptionenByUmfrage(umfrage, new GetUmfrageoptionenByUmfrageCallback());

	}
	
	/** Callback: Alle Umfrageoptionen einer vorgegebenen Umfrage sollen ausgegeben
	 * werden. Hat die gewünschte keine Umfrageoptionen, wird eine leere CellTable
	 * zurückgegeben mit einem Hinweis für den Nutzer, dass keine Umfrageoption verfügbar ist. 
	 * Die Umfrageoptionen sollen in einer Liste wiedergegeben werden. (ErgebnisInfo)
	 * 
	 */

	private class GetUmfrageoptionenByUmfrageCallback implements AsyncCallback<ArrayList<Umfrageoption>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(ArrayList<Umfrageoption> result) {

			if (result.size()==0) {

				ergebnisCellTable.setEmptyTableWidget(new Label("Keine Umfrageoptionen verfügbar!"));

			} else {
		
				for (Umfrageoption u : result) {

					ErgebnisInfo eI = new ErgebnisInfo();

					eI.setU(u);

					list.add(eI);
					
					if(result.indexOf(u)+1 == result.size()) {
						eI.lastItem = true;
					}

					kinoplaner.berechneAuswahlenByUmfrageoption(u, new AuswahlCallback(eI, u));

				}

			}
		}

	}

	public void gewinnerErmitteln() {
		kinoplaner.umfrageGewinnerErmitteln(umfrage, new UmfrageGewinnerErmittelnCallback());
	}
	
	/**
	 * Callback Rückgabe eines Filmes, welcher zu einer vorgegebenen ErgebnisInfo
	 * gehört. Das Filmname soll der ErgebnisInfo hinzugefügt werden.
	 *
	 */

	private class FilmByUmfrageoptionCallback implements AsyncCallback<Film> {

		ErgebnisInfo info = null;
		Umfrageoption u = null;

		FilmByUmfrageoptionCallback(ErgebnisInfo info, Umfrageoption u) {
			this.info = info;
			this.u=u;
		}

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(Film result) {

			info.filmName = result.getName();

			dataProvider.refresh();
			
			kinoplaner.getKinoByUmfrageoption(u, new KinoCallback(info, u));

		}

	}
	
	/**
	 * Callback: Zur ErgebnisInfo wird der Kinoname und die jeweilige Stadt
	 * hinzugefügt.
	 * 
	 *
	 */

	private class KinoCallback implements AsyncCallback<Kino> {

		ErgebnisInfo info = null;
		Umfrageoption u=null;

		KinoCallback(ErgebnisInfo info, Umfrageoption u) {
			this.info = info;
			this.u=u;
		}

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(Kino result) {

			info.kinoName = result.getName();

			info.stadt = result.getStadt();

			dataProvider.refresh();
			
			kinoplaner.getSpielzeitByUmfrageoption(u, new SpielzeitCallback(info));

		}

	}
	
	/**
	 * Callback: Zur ErgebnisInfo wird die Spielzeit hinzugefügt.
	 */

	private class SpielzeitCallback implements AsyncCallback<Spielzeit> {

		ErgebnisInfo info = null;

		SpielzeitCallback(ErgebnisInfo info) {
			this.info = info;
		}

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(Spielzeit result) {
			
			DefaultDateTimeFormatInfo infoDDTFI = new DefaultDateTimeFormatInfo();
			String pattern = "EEEE dd.MM.yyyy HH:mm";
			DateTimeFormat dft = new DateTimeFormat(pattern, infoDDTFI) {
			};

			info.spielzeit = dft.format(result.getZeit());

			dataProvider.refresh();
			
			if(info.lastItem==true) {
				eaf.suchGewinner();
			}
			
			

		}

	}
	
	/**
	 * Callback: Zur ErgebnisInfo soll die Auswahl hinzugefügt werden, die zu
	 * der ErgebnisInfo gehört. Dazu wird das entsprechende Ergebnis abgerufen
	 * und hinzugefügt. 
	 */

	private class AuswahlCallback implements AsyncCallback<Integer> {

		ErgebnisInfo info = null;
		Umfrageoption u = null;

		AuswahlCallback(ErgebnisInfo info, Umfrageoption u) {
			this.info = info;
			this.u=u;
		}

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(Integer result) {

			info.setErgebnis(result);

			dataProvider.refresh();
			
			kinoplaner.getFilmByUmfrageoption(u, new FilmByUmfrageoptionCallback(info, u));

		}

	}
	
	/** 
	 * Callback: die Klasse UmfrageGewinnerErmitteln liefert mit der onSuccess() Methode 
	 * das Ergebnis einer Umfrage und liefert hierfür das entsprechnde Ergebnis bzw. Gewinner, 
	 * indem die Umfrageoptionen miteinander verglichen werden. 
	 * 
	 *
	 */

	private class UmfrageGewinnerErmittelnCallback implements AsyncCallback<Umfrageoption> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();
		}

		@Override
		public void onSuccess(Umfrageoption result) {

			for (ErgebnisInfo e : list) {
				if (e.getU().getId() == result.getId()) {
					eaf.setGewinner(e.getSpielzeit(), e.getFilmName(), e.getKinoName(), e.getStadt());
				}
			}

		}

	}

}
