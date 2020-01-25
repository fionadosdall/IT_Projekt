package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.i18n.shared.DefaultDateTimeFormatInfo;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.ListDataProvider;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

public class UmfrageCellTable extends ScrollPanel {

	public interface CellTableResources extends CellTable.Resources {
		/**
		 * Styling der CellTable erfolgt über das CellTable.css Stylesheet
		 */
		@Source({ CellTable.Style.DEFAULT_CSS, "CellTable.css" })
		TableStyle cellTableStyle();

		interface TableStyle extends CellTable.Style {
		}
	}

	CellTable.Resources tableRes = GWT.create(CellTableResources.class);

	KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

	/**
	 * Das zugrundeliegende Objekt der UmfrageCelltables sind Vorstellungen. Jedoch
	 * speichert das gewöhnliche BusinessObject Vorstellung seine Attribute nicht in
	 * Klarnamen. Aufgrund dessen erstellen wir hier für die CellTable das Objekt
	 * VorstellungInfo. Dort werden die Attribute filmName, kinoName, spielzeit und
	 * stadt in Klarnamen gespeichert. Inklusive Getter und Setter.
	 * 
	 *
	 */
	private class VorstellungInfo {

		Vorstellung u;

		String filmName;
		String kinoName;
		String spielzeit;
		String stadt;

		public String getStadt() {
			return stadt;
		}

		public void setStadt(String stadt) {
			this.stadt = stadt;
		}

		public Vorstellung getU() {
			return u;
		}

		public void setU(Vorstellung u) {
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

	private CellTable<VorstellungInfo> umfrageCellTable = new CellTable<VorstellungInfo>(100, tableRes);

	/**
	 * ListDataProvider kümmert sich darum, dass die Daten dargestellt werden und wo
	 * sie dargestellt werden (in der CellTable). Später kann dem ListDataProvider
	 * den kompletten Datensatz übergeben und er kümmert sich automatisch darum.
	 * 
	 */
	private ListDataProvider<VorstellungInfo> dataProviderUmfrage = new ListDataProvider<VorstellungInfo>();

	public ListDataProvider<VorstellungInfo> getDataProviderUmfrage() {
		return dataProviderUmfrage;
	}

	/**
	 * Der ListDataProvider hat eine Liste im Hintergrund, in der er zunächst alles
	 * speichert.
	 */
	private List<VorstellungInfo> umfrageList = dataProviderUmfrage.getList();

	public List<VorstellungInfo> getUmfrageList() {
		return umfrageList;
	}

	public void setUmfrageList(List<VorstellungInfo> umfrageList) {
		this.umfrageList = umfrageList;
	}

	private ButtonCell buttonCell = new ButtonCell();
	private TextCell filmCell = new TextCell();
	private TextCell kinoCell = new TextCell();
	private TextCell spielzeitCell = new TextCell();
	private TextCell stadtCell = new TextCell();

	private VorstellungInfo uI;

	private Vorstellung vorstellung;

	private ArrayList<Vorstellung> neueUmfrageoptionen = new ArrayList<Vorstellung>();

	private NeueVorstellungenCellTable nct = null;

	public UmfrageCellTable(NeueVorstellungenCellTable nct) {
		this.nct = nct;
		nct.setUmfrageCellTable(this);
	}

	public Vorstellung getVortellung() {
		return vorstellung;
	}

	/**
	 * onLoad()-Methode
	 */
	public void onLoad() {

		this.setSize("100%", "250px");

		this.add(umfrageCellTable);
		umfrageCellTable.setWidth("100%");

		ListHandler<VorstellungInfo> sortHandler = new ListHandler<VorstellungInfo>(umfrageList);
		umfrageCellTable.addColumnSortHandler(sortHandler);

		/**
		 * Eine neue Spalte soll erstellt werden. Sie beinhaltet zwei Datentypen:
		 * VorstellungInfo ist das der CellTable zugrundeliegende Objekt, String ist der
		 * Datentyp, welcher in dieser Spalte dargestellt wird.
		 */
		Column<VorstellungInfo, String> buttonColumn = new Column<VorstellungInfo, String>(buttonCell) {

			/**
			 * Wenn man eine neue Spalte erstellt, muss man die Methode getValue()
			 * überschreiben (wegen Annotation Override). In der Methode wird festgelegt,
			 * was der Inhalt der Spalte ist/ bzw. returnt.
			 */
			@Override
			public String getValue(VorstellungInfo object) {
				// TODO Auto-generated method stub
				return "-";
			}

		};

		/**
		 * Nun muss die neu erstellte Spalte der UmfrageCellTable hinzugefügt werden.
		 * Unter Angabe der Bezeichnung der Spalte und ihrer Überschrift. Hinweis: In
		 * der Reihenfolge, in der die Spalten der CellTable hinzugefügt werden, werden
		 * sie auch später für den Nutzer angezeigt.
		 */
		umfrageCellTable.addColumn(buttonColumn, "Entfernen");

		/**
		 * Spalten kann ein FieldUpdater hinzugefügt werden. FieldUpdater schaut, ob
		 * sich etwas in der Spalte verändert hat und updatet entsprechend. Dazu müssen
		 * dem FieldUpdater die selben Datentypen zugewiesen werden, wie der Spalte
		 * selbst.
		 */
		buttonColumn.setFieldUpdater(new FieldUpdater<VorstellungInfo, String>() {

			/**
			 * FieldUpdater (Interface) fordert, dass Methode update() überschrieben wird
			 * (Annotation Override). Methode update() beschreibt was gemacht werden soll, wenn der Nutzer
			 * auf den CellButton drückt. Hier: Es handelt sich um Entfernen-Button, also .remove(object)
			 * 
			 */
			@Override
			public void update(int index, VorstellungInfo object, String value) {
				// TODO Auto-generated method stub

				dataProviderUmfrage.getList().remove(object);
				dataProviderUmfrage.refresh();
				nct.addVorstellung(object.getU());

			}
		});

		/**
		 * Erstellen der zweiten Column:
		 */
		Column<VorstellungInfo, String> filmColumn = new Column<VorstellungInfo, String>(filmCell) {

			@Override
			public String getValue(VorstellungInfo object) {
				// TODO Auto-generated method stub

				return object.getFilmName();

			}
		};

		/**
		 * Spalte soll die Möglichkeit zu Sortieren bieten. Spalten werden durch einen
		 * Vergleichsmechanismus sortiert.
		 */
		filmColumn.setSortable(true);

		sortHandler.setComparator(filmColumn, new Comparator<VorstellungInfo>() {
			public int compare(VorstellungInfo o1, VorstellungInfo o2) {
				return o1.getFilmName().compareTo(o2.getFilmName());
			}
		});

		umfrageCellTable.addColumn(filmColumn, "Film");

		/**
		 * Erstellen der dritten Column:
		 */
		Column<VorstellungInfo, String> kinoColumn = new Column<VorstellungInfo, String>(kinoCell) {

			@Override
			public String getValue(VorstellungInfo object) {
				// TODO Auto-generated method stub

				return object.getKinoName();

			}
		};

		kinoColumn.setSortable(true);

		sortHandler.setComparator(kinoColumn, new Comparator<VorstellungInfo>() {
			public int compare(VorstellungInfo o1, VorstellungInfo o2) {
				return o1.getKinoName().compareTo(o2.getKinoName());
			}
		});

		umfrageCellTable.addColumn(kinoColumn, "Kino");

		/**
		 * Erstellen der vierten Column
		 */
		Column<VorstellungInfo, String> speilzeitColumn = new Column<VorstellungInfo, String>(spielzeitCell) {

			@Override
			public String getValue(VorstellungInfo object) {
				// TODO Auto-generated method stub

				return object.getSpielzeit();

			}
		};

		umfrageCellTable.addColumn(speilzeitColumn, "Spielzeit");

		speilzeitColumn.setSortable(true);

		sortHandler.setComparator(speilzeitColumn, new Comparator<VorstellungInfo>() {
			public int compare(VorstellungInfo o1, VorstellungInfo o2) {
				return o1.getSpielzeit().compareTo(o2.getSpielzeit());
			}
		});

		/**
		 * Erstellen der fünften Column
		 */
		Column<VorstellungInfo, String> stadtColumn = new Column<VorstellungInfo, String>(stadtCell) {

			@Override
			public String getValue(VorstellungInfo object) {
				// TODO Auto-generated method stub

				return object.getStadt();

			}
		};

		umfrageCellTable.addColumn(stadtColumn, "Ort");

		stadtColumn.setSortable(true);

		sortHandler.setComparator(stadtColumn, new Comparator<VorstellungInfo>() {
			public int compare(VorstellungInfo o1, VorstellungInfo o2) {
				return o1.getStadt().compareTo(o2.getStadt());
			}
		});

		/**
		 * Die Methode addDataDisplay() legt über ihren Übergabeparameter für den
		 * DataProvider fest, wo er nun seine Informationen bzw. den Umfrage-Datensatz
		 * verwalten soll.
		 */
		dataProviderUmfrage.addDataDisplay(umfrageCellTable);

		this.addUmfrageoption(neueUmfrageoptionen);

	}

	/**
	 * Alle add/hinzufügen-Vorgänge, um der CellTable etwas hinzuzufügen, erfolgen
	 * über die Liste, die im Hintergrund des DataProviders arbeitet. (Hier:
	 * umfrageList)
	 * 
	 * @param neueUmfrageoptionen
	 */
	public void addUmfrageoption(ArrayList<Vorstellung> neueUmfrageoptionen) {

		this.neueUmfrageoptionen = nct.getUmfrageOptionen();

		if (this.neueUmfrageoptionen.size() != 0) {

			umfrageList.clear();

			for (Vorstellung v : this.neueUmfrageoptionen) {

				// Window.alert(v.getName());

				uI = new VorstellungInfo();

				uI.setU(v);

				// Window.alert(v.getName());

				umfrageList.add(uI);

				kinoplaner.getFilmById(v.getFilmId(), new FilmByIdCallback(uI));
				kinoplaner.getKinoByVorstellung(v, new KinoCallback(uI));
				kinoplaner.getSpielzeitById(v.getSpielzeitId(), new SpielzeitCallback(uI));
			}
		} else {
			umfrageCellTable.setEmptyTableWidget(new Label("leer"));
		}

	}

	private class FilmByIdCallback implements AsyncCallback<Film> {

		VorstellungInfo info = null;

		FilmByIdCallback(VorstellungInfo info) {
			this.info = info;
		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Film result) {
			// TODO Auto-generated method stub

			/**
			 * Die Informationen, die man über den Film erhalten hat, werden dem
			 * zugrundeliegenden Objekt VorstellungInfo hinzugefügt.
			 */
			info.filmName = result.getName();

			/**
			 * DataProvider liefert die Methode refresh(), dadruch kann der Anzeigeinhalt
			 * bei erfolgreichem Callback, hier: um den Film, aktualisiert werden.
			 */
			dataProviderUmfrage.refresh();

		}

	}

	private class KinoCallback implements AsyncCallback<Kino> {

		VorstellungInfo info = null;

		KinoCallback(VorstellungInfo info) {
			this.info = info;
		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Kino result) {
			// TODO Auto-generated method stub

			/**
			 * Die Informationen, die man über das Kino erhalten hat, werden dem
			 * zugrundeliegenden Objekt VorstellungInfo hinzugefügt.
			 */
			info.kinoName = result.getName();

			info.stadt = result.getStadt();

			/**
			 * Über die refresh()-Methode des DataProviders kann der Anzeigeninhalt
			 * entsprechend der Informationen zum Kino aktualisiert werden.
			 */
			dataProviderUmfrage.refresh();

		}

	}

	private class SpielzeitCallback implements AsyncCallback<Spielzeit> {

		VorstellungInfo info = null;

		SpielzeitCallback(VorstellungInfo info) {
			this.info = info;
		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Spielzeit result) {
			/**
			 * Spielzeit formatieren
			 */
			DefaultDateTimeFormatInfo infoDDTFI = new DefaultDateTimeFormatInfo();
			String pattern = "EEEE dd.MM.yyyy HH:mm";
			DateTimeFormat dft = new DateTimeFormat(pattern, infoDDTFI) {
			};

			/**
			 * Spielzeit zum VorstellungInfo-Objekt
			 */
			info.spielzeit = dft.format(result.getZeit());
			/**
			 * Anzeigeninhalt bezüglich Spielzeit aktualisieren.
			 */
			dataProviderUmfrage.refresh();

		}

	}

//	private class UmfrageByIdCallback implements AsyncCallback<Umfrageoption> {
//		
//		UmfrageInfo info = null;
//
//		UmfrageByIdCallback(UmfrageInfo info) {
//			this.info = info;
//		}
//
//	
//
//		@Override
//		public void onFailure(Throwable caught) {
//			// TODO Auto-generated method stub
//			Window.alert(caught.getMessage());
//			
//		}
//
//		@Override
//		public void onSuccess(Umfrageoption result) {
//			// TODO Auto-generated method stub
//			
//			info.u = result;
//			
//			
//		}
//		
//	}

}
