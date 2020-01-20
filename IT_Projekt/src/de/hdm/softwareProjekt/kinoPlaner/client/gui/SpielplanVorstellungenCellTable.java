package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.i18n.shared.DefaultDateTimeFormatInfo;
import com.google.gwt.resources.client.ClientBundle.Source;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.DateFormaterSpielzeit;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielplan;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

public class SpielplanVorstellungenCellTable extends VerticalPanel {

	public interface CellTableResources extends CellTable.Resources {

		@Source({ CellTable.Style.DEFAULT_CSS, "CellTable.css" })
		TableStyle cellTableStyle();

		interface TableStyle extends CellTable.Style {
		}
	}

	CellTable.Resources tableRes = GWT.create(CellTableResources.class);

	private KinoplanerAsync administration = ClientsideSettings.getKinoplaner();

	private Spielplan spielplan;

	private Vorstellung vorstellung;

	private ArrayList<Vorstellung> neueVorstellungen = new ArrayList<Vorstellung>();

	private ListDataProvider<VorstellungInfo> dataProvider = new ListDataProvider<VorstellungInfo>();
	private List<VorstellungInfo> vorstellungList = dataProvider.getList();

	private CellTable<VorstellungInfo> vorstellungenTable = new CellTable<VorstellungInfo>(100, tableRes);

	private ButtonCell buttonCell = new ButtonCell();
	private TextCell filmCell = new TextCell();
	private TextCell spielzeitCell = new TextCell();

	private SpielplanErstellenForm parent;

	public void setParent(SpielplanErstellenForm parent) {
		this.parent = parent;
	}

	public SpielplanVorstellungenCellTable() {

	}

	public SpielplanVorstellungenCellTable(Spielplan spielplan) {
		this.spielplan = spielplan;
	}

	public Vorstellung getVortellung() {
		return vorstellung;
	}

	public ListDataProvider<VorstellungInfo> getDataProvider() {
		return dataProvider;
	}

	public List<VorstellungInfo> getVorstellungList() {
		return vorstellungList;
	}

	public void setUmfrageList(List<VorstellungInfo> vorstellungList) {
		this.vorstellungList = vorstellungList;
	}

	private class VorstellungInfo {

		Vorstellung vorstellung;

		private String filmName;
		private String spielzeit;

		public String getFilmName() {
			return filmName;
		}

		public void setFilmName(String filmName) {
			this.filmName = filmName;
		}

		public String getSpielzeit() {
			return spielzeit;
		}

		public void setSpielzeit(String spielzeit) {
			this.spielzeit = spielzeit;
		}

		public Vorstellung getVorstellung() {
			return vorstellung;
		}

		public void setU(Vorstellung vorstellung) {
			this.vorstellung = vorstellung;
		}

	}

	public ArrayList<Vorstellung> getVorstellungenArray() {
		return neueVorstellungen;

	}

	public void setVorstellungArray(ArrayList<Vorstellung> vorstellungenInfo) {
		this.neueVorstellungen = vorstellungenInfo;
	}

	@Override
	public void onLoad() {

		this.add(vorstellungenTable);

		ListHandler<VorstellungInfo> sortHandler = new ListHandler<VorstellungInfo>(vorstellungList);
		vorstellungenTable.addColumnSortHandler(sortHandler);

		vorstellungenTable.setWidth("100%");
		vorstellungenTable.setEmptyTableWidget(new Label("Keine Vorstellungen hinzugefügt."));

		Column<VorstellungInfo, String> buttonColumn = new Column<VorstellungInfo, String>(buttonCell) {

			@Override
			public String getValue(VorstellungInfo object) {
				// TODO Auto-generated method stub
				return "-";
			}

		};

		vorstellungenTable.addColumn(buttonColumn, "Entfernen");

		buttonColumn.setFieldUpdater(new FieldUpdater<VorstellungInfo, String>() {

			@Override
			public void update(int index, VorstellungInfo object, String value) {

				int count = 0;
				for (Vorstellung v : neueVorstellungen) {
					if (v.getId() == 0) {
						if (v.getFilmId() == object.getVorstellung().getFilmId()
								&& v.getSpielzeitId() == object.getVorstellung().getSpielzeitId()) {
							neueVorstellungen.remove(count);

							break;

						}
					} else {
						if (v.getId() == object.getVorstellung().getId()) {
							neueVorstellungen.remove(count);
							break;
						}
					}
					count++;
				}
				dataProvider.getList().remove(object);
				dataProvider.refresh();

			}
		});

		Column<VorstellungInfo, String> filmColumn = new Column<VorstellungInfo, String>(filmCell) {

			@Override
			public String getValue(VorstellungInfo object) {
				// TODO Auto-generated method stub

				return object.getFilmName();

			}
		};

		vorstellungenTable.addColumn(filmColumn, "Film");

		filmColumn.setSortable(true);

		sortHandler.setComparator(filmColumn, new Comparator<VorstellungInfo>() {
			public int compare(VorstellungInfo o1, VorstellungInfo o2) {
				return o1.getFilmName().compareTo(o2.getFilmName());
			}
		});

		Column<VorstellungInfo, String> spielzeitColumn = new Column<VorstellungInfo, String>(spielzeitCell) {

			@Override
			public String getValue(VorstellungInfo object) {
				// TODO Auto-generated method stub

				return object.getSpielzeit();

			}
		};

		vorstellungenTable.addColumn(spielzeitColumn, "Spielzeit");

		spielzeitColumn.setSortable(true);

		sortHandler.setComparator(spielzeitColumn, new Comparator<VorstellungInfo>() {
			public int compare(VorstellungInfo o1, VorstellungInfo o2) {
				return o1.getSpielzeit().compareTo(o2.getSpielzeit());
			}
		});

		dataProvider.addDataDisplay(vorstellungenTable);
		if (spielplan != null) {
			administration.getVorstellungenBySpielplan(spielplan, new GetVorstellungenBySpielplanCallback());
		}
	}

	/* Methoden */

	public void addVorstellung(Vorstellung neueVorstellung) {

		neueVorstellungen.add(neueVorstellung);

		VorstellungInfo vI = new VorstellungInfo();

		vI.setU(neueVorstellung);

		vorstellungList.add(vI);

		dataProvider.refresh();

		parent.closeSpielplaneintragForm();

		administration.getFilmById(neueVorstellung.getFilmId(), new FilmByIdCallback(vI));
		administration.getSpielzeitById(neueVorstellung.getSpielzeitId(), new SpielzeitCallback(vI));

	}

	/* Callbacks */

	private class GetVorstellungenBySpielplanCallback implements AsyncCallback<ArrayList<Vorstellung>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(ArrayList<Vorstellung> result) {

			for (Vorstellung v : result) {

				neueVorstellungen.add(v);

				VorstellungInfo vI = new VorstellungInfo();

				vI.setU(v);

				vorstellungList.add(vI);

				dataProvider.refresh();

				administration.getFilmById(v.getFilmId(), new FilmByIdCallback(vI));
				administration.getSpielzeitById(v.getSpielzeitId(), new SpielzeitCallback(vI));

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
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(Film result) {
			// TODO Auto-generated method stub

			info.filmName = result.getName();

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
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(Spielzeit result) {
			// TODO Auto-generated method stub

			DefaultDateTimeFormatInfo infoDDTFI = new DefaultDateTimeFormatInfo();
			String pattern = "EEEE dd.MM.yyyy HH:mm";
			DateTimeFormat dft = new DateTimeFormat(pattern, infoDDTFI) {
			};
			info.spielzeit = dft.format(result.getZeit());

			dataProvider.refresh();

		}

	}

}
