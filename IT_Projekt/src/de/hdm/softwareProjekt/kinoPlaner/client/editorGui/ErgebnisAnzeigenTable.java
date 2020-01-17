package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.view.client.ListDataProvider;


import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrageoption;

public class ErgebnisAnzeigenTable extends FlowPanel{
	
	private KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

	private Umfrage umfrage;

	public ErgebnisAnzeigenTable(Umfrage umfrage) {
		this.umfrage = umfrage;

	}
	
	private ErgebnisInfo ergebnisInfo = new ErgebnisInfo();
	
	public ErgebnisInfo getErgebnisInfo() {
		return ergebnisInfo;
	}

	class ErgebnisInfo {

		Umfrageoption u;;

		private String filmName;
		private String kinoName;
		private String spielzeit;
		private String stadt;
		private int ergebnis;

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
	
	CellTable<ErgebnisInfo> ergebnisCellTable;
	
	private ListDataProvider<ErgebnisInfo> dataProvider;
	private List<ErgebnisInfo> list;

	@Override
	public void onLoad() {

		dataProvider = new ListDataProvider<ErgebnisInfo>();
		
		list = dataProvider.getList();

		ergebnisCellTable = new CellTable<ErgebnisInfo>();
		ergebnisCellTable.setWidth("100%");

		ergebnisCellTable.setAutoHeaderRefreshDisabled(true);

		ergebnisCellTable.setEmptyTableWidget(new Label("Keine Ergebnisse verfügbar!"));
		
		//CellTable
		
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
	
	private class GetUmfrageoptionenByUmfrageCallback implements AsyncCallback<ArrayList<Umfrageoption>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(ArrayList<Umfrageoption> result) {
			if (result == null) {

				ergebnisCellTable.setEmptyTableWidget(new Label("Keine Umfrageoptionen verfügbar!"));

			} else {
				for (Umfrageoption u : result) {

					ErgebnisInfo eI = new ErgebnisInfo();

					eI.setU(u);

					list.add(eI);
					
					kinoplaner.berechneAuswahlenByUmfrageoption(u, new AuswahlCallback(eI));
					kinoplaner.getFilmByUmfrageoption(u, new FilmByUmfrageoptionCallback(eI));
					kinoplaner.getKinoByUmfrageoption(u, new KinoCallback(eI));
					kinoplaner.getSpielzeitByUmfrageoption(u, new SpielzeitCallback(eI));

				}

			}
		}

	}

	private class FilmByUmfrageoptionCallback implements AsyncCallback<Film> {

		ErgebnisInfo info = null;

		FilmByUmfrageoptionCallback(ErgebnisInfo info) {
			this.info = info;
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

		}

	}

	private class KinoCallback implements AsyncCallback<Kino> {

		ErgebnisInfo info = null;

		KinoCallback(ErgebnisInfo info) {
			this.info = info;
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

		}

	}

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

			info.spielzeit = result.getZeit().toString();

			dataProvider.refresh();

		}

	}

	private class AuswahlCallback implements AsyncCallback<Integer> {

		ErgebnisInfo info = null;

		AuswahlCallback(ErgebnisInfo info) {
			this.info = info;
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

			}

		}


}
