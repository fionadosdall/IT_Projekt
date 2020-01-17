package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

public class NeueCellTable extends VerticalPanel {

	private UmfrageCellTable uct = null;

	public void setUmfrageCellTable(UmfrageCellTable uct) {
		this.uct = uct;
	}

	public interface CellTableResources extends CellTable.Resources {

		@Source({ CellTable.Style.DEFAULT_CSS, "CellTable.css" })
		TableStyle cellTableStyle();

		interface TableStyle extends CellTable.Style {
		}
	}

	CellTable.Resources tableRes = GWT.create(CellTableResources.class);

	private int i = 0;

	KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

	private class VorstellungInfo {

		Vorstellung v;

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

		public Vorstellung getV() {
			return v;
		}

		public void setV(Vorstellung v) {
			this.v = v;
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

	private CellTable<VorstellungInfo> vorstellungenCellTable = new CellTable<VorstellungInfo>(100, tableRes);

	private ListDataProvider<VorstellungInfo> dataProvider = new ListDataProvider<VorstellungInfo>();
	private List<VorstellungInfo> list = dataProvider.getList();

	private ArrayList<Vorstellung> vorstellungen = new ArrayList<Vorstellung>();
	private ArrayList<Vorstellung> vorFilterVorstellungen = new ArrayList<Vorstellung>();

	public ArrayList<Vorstellung> getVorstellungen() {
		return vorstellungen;
	}

	public ArrayList<Vorstellung> getVorFilterVorstellungen() {
		return vorFilterVorstellungen;
	}

	private ArrayList<Vorstellung> umfrageOptionen = new ArrayList<Vorstellung>();

	public ArrayList<Vorstellung> getUmfrageOptionen() {
		return umfrageOptionen;
	}

	private ButtonCell buttonCell = new ButtonCell();
	private TextCell filmCell = new TextCell();
	private TextCell kinoCell = new TextCell();
	private TextCell spielzeitCell = new TextCell();
	private TextCell stadtCell = new TextCell();

	private VorstellungInfo vI;
	private Vorstellung vorstellung;

	public Vorstellung getVorstellung() {
		return vorstellung;
	}

	public void setVorstellung(Vorstellung vorstellung) {
		this.vorstellung = vorstellung;
	}

	public void onLoad() {

		this.add(vorstellungenCellTable);

		ListHandler<VorstellungInfo> sortHandler = new ListHandler<VorstellungInfo>(list);
		vorstellungenCellTable.addColumnSortHandler(sortHandler);

		Column<VorstellungInfo, String> buttonColumn = new Column<VorstellungInfo, String>(buttonCell) {

			@Override
			public String getValue(VorstellungInfo object) {
				// TODO Auto-generated method stub
				return "+";
			}

		};

		buttonColumn.setFieldUpdater(new FieldUpdater<VorstellungInfo, String>() {

			@Override
			public void update(int index, VorstellungInfo object, String value) {
				// TODO Auto-generated method stub
				dataProvider.getList().remove(object);

				vorstellung = object.getV();

				umfrageOptionen.add(vorstellung);
				vorstellungen.remove(vorstellung);
				vorFilterVorstellungen.remove(vorstellung);

				uct.addUmfrageoption(umfrageOptionen);

				for (Vorstellung v : umfrageOptionen) {
					Window.alert("for Schliefe " + v.getName());
				}

			}
		});

		Column<VorstellungInfo, String> filmColumn = new Column<VorstellungInfo, String>(filmCell) {

			@Override
			public String getValue(VorstellungInfo object) {
				// TODO Auto-generated method stub

				return object.getFilmName();

			}
		};

		filmColumn.setSortable(true);

		sortHandler.setComparator(filmColumn, new Comparator<VorstellungInfo>() {
			public int compare(VorstellungInfo o1, VorstellungInfo o2) {
				return o1.getFilmName().compareTo(o2.getFilmName());
			}
		});

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

		Column<VorstellungInfo, String> speilzeitColumn = new Column<VorstellungInfo, String>(spielzeitCell) {

			@Override
			public String getValue(VorstellungInfo object) {
				// TODO Auto-generated method stub

				return object.getSpielzeit();

			}
		};

		Column<VorstellungInfo, String> stadtColumn = new Column<VorstellungInfo, String>(stadtCell) {

			@Override
			public String getValue(VorstellungInfo object) {
				// TODO Auto-generated method stub

				return object.getStadt();

			}
		};

		stadtColumn.setSortable(true);

		sortHandler.setComparator(stadtColumn, new Comparator<VorstellungInfo>() {
			public int compare(VorstellungInfo o1, VorstellungInfo o2) {
				return o1.getStadt().compareTo(o2.getStadt());
			}
		});

		kinoplaner.getAllVorstellungen(new VorstellungCallback());

		vorstellungenCellTable.addColumn(buttonColumn, "Auswählen");
		vorstellungenCellTable.addColumn(filmColumn, "Film");
		vorstellungenCellTable.addColumn(kinoColumn, "Kino");
		vorstellungenCellTable.addColumn(speilzeitColumn, "Spielzeit");
		vorstellungenCellTable.addColumn(stadtColumn, "Ort");

		dataProvider.addDataDisplay(vorstellungenCellTable);

	}

	public void filterResultUpdaten(ArrayList<Vorstellung> vorstellungen) {
		this.vorstellungen = vorstellungen;

		// Löschen des bisherigen Anzeigeinhalts
		list.clear();

		for (Vorstellung v : vorstellungen) {
			
			Window.alert(v.getName());
			
			vI = new VorstellungInfo();

			vI.setV(v);

			list.add(vI);

			kinoplaner.getFilmById(v.getFilmId(), new FilmByIdCallback(vI));
			kinoplaner.getKinoByVorstellung(v, new KinoCallback(vI));
			kinoplaner.getSpielzeitById(v.getSpielzeitId(), new SpielzeitCallback(vI));

		}

	}

	private class VorstellungCallback implements AsyncCallback<ArrayList<Vorstellung>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(ArrayList<Vorstellung> result) {
			// TODO Auto-generated method stub
			vorFilterVorstellungen = result;
			vorstellungen = result;

			for (Vorstellung v : vorstellungen) {

				vI = new VorstellungInfo();

				vI.setV(v);

				list.add(vI);

				kinoplaner.getFilmById(v.getFilmId(), new FilmByIdCallback(vI));
				kinoplaner.getKinoByVorstellung(v, new KinoCallback(vI));
				kinoplaner.getSpielzeitById(v.getSpielzeitId(), new SpielzeitCallback(vI));
			}

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

			info.filmName = result.getName();

			dataProvider.refresh();

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

			info.kinoName = result.getName();

			info.stadt = result.getStadt();

			dataProvider.refresh();

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
			// TODO Auto-generated method stub
			info.spielzeit = result.getZeit().toString();

			dataProvider.refresh();

		}

	}

	public void addVorstellung(Vorstellung vorstellung2) {

		vorstellungen.add(vorstellung2);
		vorFilterVorstellungen.add(vorstellung2);
		umfrageOptionen.remove(vorstellung2);

		vI = new VorstellungInfo();

		vI.setV(vorstellung2);

		list.add(vI);

		kinoplaner.getFilmById(vorstellung2.getFilmId(), new FilmByIdCallback(vI));
		kinoplaner.getKinoByVorstellung(vorstellung2, new KinoCallback(vI));
		kinoplaner.getSpielzeitById(vorstellung2.getSpielzeitId(), new SpielzeitCallback(vI));

	}

}
