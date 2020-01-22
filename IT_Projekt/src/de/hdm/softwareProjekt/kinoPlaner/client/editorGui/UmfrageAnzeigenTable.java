package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.i18n.shared.DefaultDateTimeFormatInfo;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
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

/**
 * Die Klasse UmfrageAnzeigenTable dient als Vorlage um Umfragen in einer
 * UmfragenAnzeigenForm anzuzeigen.
 *
 */
public class UmfrageAnzeigenTable extends ScrollPanel {

	private KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

	private Umfrage umfrage;
	String ka = "Keine Auswahl";
	String t = "Teilnehmen";
	String nt = "Nicht Teilnehmen";

	// Konstruktor

	public UmfrageAnzeigenTable(Umfrage umfrage) {
		this.umfrage = umfrage;

	}

	private class UmfrageoptionInfo {

		Umfrageoption u;

		String filmName;
		String kinoName;
		String spielzeit;
		String stadt;

		private Boolean isVoteTeilnahme = null;

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
	 * onLoad()-Methode
	 */
	@Override
	public void onLoad() {
		this.setHeight("380px");
		

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

		List<String> voteMoeglichkeiten = new ArrayList<String>();

		voteMoeglichkeiten.add(ka);
		voteMoeglichkeiten.add(t);
		voteMoeglichkeiten.add(nt);

		SelectionCell votingCell = new SelectionCell(voteMoeglichkeiten);

		Column<UmfrageoptionInfo, String> voteColumn = new Column<UmfrageoptionInfo, String>(votingCell) {

			@Override
			public String getValue(UmfrageoptionInfo object) {
				if (object.isVoteTeilnahme() == null)
					return ka;
				else if (object.isVoteTeilnahme() == true)
					return t;
				else
					return nt;
			}

		};

		umfrageoptionCellTable.addColumn(voteColumn, "Voten");

		voteColumn.setFieldUpdater(new FieldUpdater<UmfrageoptionInfo, String>() {

			@Override
			public void update(int index, UmfrageoptionInfo object, String value) {
				if (value.equals(ka)) {
					object.setVoteTeilnahme(null);
				} else if (value.equals(t)) {
					object.setVoteTeilnahme(true);
				} else {
					object.setVoteTeilnahme(false);
				}

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

		kinoplaner.getUmfrageoptionenByUmfrage(umfrage, new GetUmfrageoptionenByUmfrageCallback());

	}

	public void speichern() {
		ArrayList<Auswahl> umfrageoptionAuswahlArray = new ArrayList<Auswahl>();

		for (UmfrageoptionInfo ui : umfraoptionArray) {
			if (ui.isVoteTeilnahme() != null) {
				Auswahl auswahl = new Auswahl();
				if (ui.isVoteTeilnahme == true) {

					auswahl.setName(ui.getV().getName());
					auswahl.setVoting(1);
					auswahl.setUmfrageoptionId(ui.getV().getId());

				} else {

					auswahl.setName(ui.getV().getName());
					auswahl.setVoting(-1);
					auswahl.setUmfrageoptionId(ui.getV().getId());

				}

				umfrageoptionAuswahlArray.add(auswahl);
			}
		}

		kinoplaner.auswahlenErstellen(umfrageoptionAuswahlArray, alteAuswahlen, umfrageoptionAuswahlArray.size(),
				umfrage, new AuswahlErstellenCallback());

	}

	/**********************************************************
	 * CALLBACKS
	 **********************************************************/
	/**
	 * Callback: Alle Umfrageoptionen einer vorgegebenen Umfrage sollen ausgegeben
	 * werden. Hat die gewünschte keine Umfrageoptionen, wird eine leere CellTable
	 * zurückgegeben mit einem Hinweis für den Nutzer. Die Umfrageoptionen sollen in
	 * einer Liste wiedergegeben werden (UmfrageoptionInfo)
	 */
	private class GetUmfrageoptionenByUmfrageCallback implements AsyncCallback<ArrayList<Umfrageoption>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(ArrayList<Umfrageoption> result) {
			if (result == null) {

				umfrageoptionCellTable.setEmptyTableWidget(new Label("Keine Umfrageoptionen verfügbar!"));

			} else {
				for (Umfrageoption u : result) {

					UmfrageoptionInfo uI = new UmfrageoptionInfo();

					uI.setV(u);

					list.add(uI);
					umfraoptionArray.add(uI);

					kinoplaner.getAuswahlByAnwenderAndUmfrageoption(u, new AuswahlCallback(uI));
					kinoplaner.getFilmByUmfrageoption(u, new FilmByUmfrageoptionCallback(uI));
					kinoplaner.getKinoByUmfrageoption(u, new KinoCallback(uI));
					kinoplaner.getSpielzeitByUmfrageoption(u, new SpielzeitCallback(uI));

				}

			}
		}

	}

	/**
	 * Callback Rückgabe eines Filmes, welcher zu einer vorgegebenen Umfrageoption
	 * gehört. Das Filmname soll der UmfrageoptionInfo hinzugefügt werden.
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

	/**
	 * Callback: Zur UmfrageoptionInformation der Kinoname und die jeweilige Stadt
	 * hinzugefügt.
	 * 
	 *
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

	/**
	 * Callback: Zur UmfrageoptionInfo wird die Spielzeit hinzugefügt.
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
	 * Callback: Zur UmfrageoptionInfo soll die Auswahl hinzugefügt werden, die zu
	 * besagter Umfrageoption gehört. Dazu wird das entsprechende Voting abgerufen
	 * und hinzugefügt. Außerdem wird die VotingTeilnahme des Users bei der
	 * Umfrageoption hinterlegt.
	 */
	private class AuswahlCallback implements AsyncCallback<Auswahl> {

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
		public void onSuccess(Auswahl result) {

			if (result != null) {
				alteAuswahlen.add(result);
				if (result.getVoting() == 1) {
					info.setVoteTeilnahme(true);
				} else if (result.getVoting() == -1) {
					info.setVoteTeilnahme(false);
				}
				dataProvider.refresh();

			}

		}

	}

	/**
	 * Callback: Das Ergebnis einer Umfrage soll angezeigt werden -->
	 * ErgebnisAnzeigenForm. Andernfalls, wenn die Umfrage noch läuft/offen ist,
	 * sollen die bisherigen Votings angezeigt werden (--> VotingAnzeigenForm).
	 *
	 */
	private class AuswahlErstellenCallback implements AsyncCallback<Umfrage> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(Umfrage result) {
			if (result.isOpen() == true) {
				RootPanel.get("details").clear();
				VotingsAnzeigenForm anzeigen = new VotingsAnzeigenForm(result);
				RootPanel.get("details").add(anzeigen);
			} else {
				Window.alert("Ergebnis AnzeigenForm");
				RootPanel.get("details").clear();
				ErgebnisAnzeigenForm anzeigen = new ErgebnisAnzeigenForm(result);
				RootPanel.get("details").add(anzeigen);
			}

		}

	}

}
