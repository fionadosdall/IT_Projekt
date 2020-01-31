package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.i18n.shared.DefaultDateTimeFormatInfo;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;

import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Auswahl;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrageoption;

public class VotingsAnzeigenTable extends ScrollPanel{
	
	private KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

	private Umfrage umfrage;

	public VotingsAnzeigenTable(Umfrage umfrage) {
		this.umfrage = umfrage;

	}

	private class UmfrageoptionInfo {

		Umfrageoption u;

		private String filmName;
		private String kinoName;
		private String spielzeit;
		private String stadt;
		private Boolean isVoteTeilnahme = null;
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

		public Umfrageoption getV() {
			return u;
		}

		public void setV(Umfrageoption u) {
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

		public Boolean isVoteTeilnahme() {
			return isVoteTeilnahme;
		}

		public void setVoteTeilnahme(Boolean vote) {
			this.isVoteTeilnahme = vote;
		}

	}

	ArrayList<Auswahl> alteAuswahlen = new ArrayList<Auswahl>();

	ArrayList<UmfrageoptionInfo> umfraoptionArray = new ArrayList<UmfrageoptionInfo>();

	public ArrayList<UmfrageoptionInfo> getUmfraoptionArray() {
		return umfraoptionArray;
	}

	public void setUmfraoptionArray(ArrayList<UmfrageoptionInfo> umfraoptionArray) {
		this.umfraoptionArray = umfraoptionArray;
	}

	// interface Binder extends UiBinder<Widget, UmfrageAnzeigenTable > {
	// }

	// @UiField(provided = true)
	CellTable<UmfrageoptionInfo> umfrageoptionCellTable;

	// @UiField(provided = true)
	// SimplePager pager;

	private ListDataProvider<UmfrageoptionInfo> dataProvider;
	private List<UmfrageoptionInfo> list;

	/** 
	 * onLoad():
	 * Beschreibt was passiert wenn das Widget der Seite hinzugefügt wird
	 */
	@Override
	public void onLoad() {
		
		this.setHeight("380px");
		kinoplaner.getUmfrageoptionenByUmfrage(umfrage, new GetUmfrageoptionenByUmfrageCallback());
		

		dataProvider = new ListDataProvider<UmfrageoptionInfo>();
		list = dataProvider.getList();

		umfrageoptionCellTable = new CellTable<UmfrageoptionInfo>();
		umfrageoptionCellTable.setWidth("100%");

		umfrageoptionCellTable.setAutoHeaderRefreshDisabled(true);

		umfrageoptionCellTable.setEmptyTableWidget(new Label("Keine Umfrageoptionen verfügbar!"));

		ListHandler<UmfrageoptionInfo> sortHandler = new ListHandler<UmfrageoptionInfo>(list);
		umfrageoptionCellTable.addColumnSortHandler(sortHandler);

		final SelectionModel<UmfrageoptionInfo> selectionModelVote = new SingleSelectionModel<UmfrageoptionInfo>();
		umfrageoptionCellTable.setSelectionModel(selectionModelVote);

		initTabellenSpalten(selectionModelVote, sortHandler);

		dataProvider.addDataDisplay(umfrageoptionCellTable);

		// Binder uiBinder = GWT.create(Binder.class);
		// uiBinder.createAndBindUi(this);

		this.add(umfrageoptionCellTable);

	}

	private void initTabellenSpalten(final SelectionModel<UmfrageoptionInfo> selectionModelVote,
			ListHandler<UmfrageoptionInfo> sortHandler) {

		Column<UmfrageoptionInfo, String> voteColumn = new Column<UmfrageoptionInfo, String>(new TextCell()) {

			@Override
			public String getValue(UmfrageoptionInfo object) {
				
				return ""+object.getErgebnis();
			}

		};

		umfrageoptionCellTable.addColumn(voteColumn, "Voting Stand");
		
		voteColumn.setSortable(true);

		sortHandler.setComparator(voteColumn, new Comparator<UmfrageoptionInfo>() {
			public int compare(UmfrageoptionInfo o1, UmfrageoptionInfo o2) {
				return o1.getStadt().compareTo(o2.getStadt());
			}
		});

		umfrageoptionCellTable.setColumnWidth(voteColumn, 100, Unit.PX);

		Column<UmfrageoptionInfo, String> filmColumn = new Column<UmfrageoptionInfo, String>(new TextCell()) {

			@Override
			public String getValue(UmfrageoptionInfo object) {

				return object.getFilmName();

			}
		};

		umfrageoptionCellTable.addColumn(filmColumn, "Film");
		
		filmColumn.setSortable(true);

		sortHandler.setComparator(filmColumn, new Comparator<UmfrageoptionInfo>() {
			public int compare(UmfrageoptionInfo o1, UmfrageoptionInfo o2) {
				return o1.getStadt().compareTo(o2.getStadt());
			}
		});

		Column<UmfrageoptionInfo, String> kinoColumn = new Column<UmfrageoptionInfo, String>(new TextCell()) {

			@Override
			public String getValue(UmfrageoptionInfo object) {

				return object.getKinoName();

			}
		};
		
		umfrageoptionCellTable.addColumn(kinoColumn, "Kino");
		
		kinoColumn.setSortable(true);

		sortHandler.setComparator(kinoColumn, new Comparator<UmfrageoptionInfo>() {
			public int compare(UmfrageoptionInfo o1, UmfrageoptionInfo o2) {
				return o1.getStadt().compareTo(o2.getStadt());
			}
		});

		Column<UmfrageoptionInfo, String> spielzeitColumn = new Column<UmfrageoptionInfo, String>(new TextCell()) {

			@Override
			public String getValue(UmfrageoptionInfo object) {

				return object.getSpielzeit();

			}
		};

		umfrageoptionCellTable.addColumn(spielzeitColumn, "Spielzeit");
		
		spielzeitColumn.setSortable(true);

		sortHandler.setComparator(spielzeitColumn, new Comparator<UmfrageoptionInfo>() {
			public int compare(UmfrageoptionInfo o1, UmfrageoptionInfo o2) {
				return o1.getStadt().compareTo(o2.getStadt());
			}
		});

		Column<UmfrageoptionInfo, String> stadtColumn = new Column<UmfrageoptionInfo, String>(new TextCell()) {

			@Override
			public String getValue(UmfrageoptionInfo object) {

				return object.getStadt();

			}
		};

		umfrageoptionCellTable.addColumn(stadtColumn, "Ort");
		
		stadtColumn.setSortable(true);

		sortHandler.setComparator(stadtColumn, new Comparator<UmfrageoptionInfo>() {
			public int compare(UmfrageoptionInfo o1, UmfrageoptionInfo o2) {
				return o1.getStadt().compareTo(o2.getStadt());
			}
		});

		

	}

	/*
	 * Private Klasse um alle Umfrageoptionen-Instanzen, welche zu der Umfrage gehören
	 * aus dem System bekommen
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
			if (result.size() == 0) {

				umfrageoptionCellTable.setEmptyTableWidget(new Label("Keine Umfrageoptionen verfügbar!"));

			} else {
				for (Umfrageoption u : result) {

					UmfrageoptionInfo uI = new UmfrageoptionInfo();

					uI.setV(u);

					list.add(uI);
					umfraoptionArray.add(uI);
					
					kinoplaner.berechneAuswahlenByUmfrageoption(u, new AuswahlCallback(uI));
					kinoplaner.getFilmByUmfrageoption(u, new FilmByUmfrageoptionCallback(uI));
					kinoplaner.getKinoByUmfrageoption(u, new KinoCallback(uI));
					kinoplaner.getSpielzeitByUmfrageoption(u, new SpielzeitCallback(uI));

				}

			}
		}

	}
	
	/**
	 * privarte Klasse um alle Film-Instanzen, welche zur Umfrageoption gehören, 
	 * aus dem System zu bekommen
	 * 
	 *
	 */

	private class FilmByUmfrageoptionCallback implements AsyncCallback<Film> {

		UmfrageoptionInfo info = null;

		FilmByUmfrageoptionCallback(UmfrageoptionInfo info) {
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
	
	/*
	 * Private Klasse um alle Kino-instanzen aus dem System zu bekommen
	 */

	private class KinoCallback implements AsyncCallback<Kino> {

		UmfrageoptionInfo info = null;

		KinoCallback(UmfrageoptionInfo info) {
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
	
	
	/***
	 * private Klasse um alle Spielzeit-Instanzen aus dem System zu bekommen
	 * 
	 *
	 */

	private class SpielzeitCallback implements AsyncCallback<Spielzeit> {

		UmfrageoptionInfo info = null;

		SpielzeitCallback(UmfrageoptionInfo info) {
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

		}

	}
	
	/**
	 * private Klasse um alle Auswahl-Instanzen aus dem System zu bekommen
	 * 
	 * 
	 *
	 */

	private class AuswahlCallback implements AsyncCallback<Integer> {

		UmfrageoptionInfo info = null;

		AuswahlCallback(UmfrageoptionInfo info) {
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





