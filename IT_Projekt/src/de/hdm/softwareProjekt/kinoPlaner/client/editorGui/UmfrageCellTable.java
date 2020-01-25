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

		@Source({ CellTable.Style.DEFAULT_CSS, "CellTable.css" })
		TableStyle cellTableStyle();

		interface TableStyle extends CellTable.Style {
		}
	}

	CellTable.Resources tableRes = GWT.create(CellTableResources.class);

	KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

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

	private ListDataProvider<VorstellungInfo> dataProviderUmfrage = new ListDataProvider<VorstellungInfo>();

	public ListDataProvider<VorstellungInfo> getDataProviderUmfrage() {
		return dataProviderUmfrage;
	}

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

	public void onLoad() {
		
		this.setSize("100%", "250px");

		this.add(umfrageCellTable);
		umfrageCellTable.setWidth("100%");
		
		ListHandler<VorstellungInfo> sortHandler = new ListHandler<VorstellungInfo>(umfrageList);
		umfrageCellTable.addColumnSortHandler(sortHandler);

		Column<VorstellungInfo, String> buttonColumn = new Column<VorstellungInfo, String>(buttonCell) {

			@Override
			public String getValue(VorstellungInfo object) {
				// TODO Auto-generated method stub
				return "-";
			}

		};

		umfrageCellTable.addColumn(buttonColumn, "Entfernen");
		
		

		buttonColumn.setFieldUpdater(new FieldUpdater<VorstellungInfo, String>() {

			@Override
			public void update(int index, VorstellungInfo object, String value) {
				// TODO Auto-generated method stub
				
				dataProviderUmfrage.getList().remove(object); 
				dataProviderUmfrage.refresh();
				nct.addVorstellung(object.getU());
				

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

		umfrageCellTable.addColumn(filmColumn, "Film");

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

		dataProviderUmfrage.addDataDisplay(umfrageCellTable);

		this.addUmfrageoption(neueUmfrageoptionen);


	}

	public void addUmfrageoption(ArrayList<Vorstellung> neueUmfrageoptionen) {

		this.neueUmfrageoptionen = nct.getUmfrageOptionen();

		if (this.neueUmfrageoptionen.size() != 0) {
			
			umfrageList.clear();

			for (Vorstellung v : this.neueUmfrageoptionen) {

				//Window.alert(v.getName());

				uI = new VorstellungInfo();

				uI.setU(v);

				//Window.alert(v.getName());

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

			info.filmName = result.getName();

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

			info.kinoName = result.getName();

			info.stadt = result.getStadt();

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
			DefaultDateTimeFormatInfo infoDDTFI = new DefaultDateTimeFormatInfo();
			String pattern = "EEEE dd.MM.yyyy HH:mm";
			DateTimeFormat dft = new DateTimeFormat(pattern, infoDDTFI) {
			};

			info.spielzeit = dft.format(result.getZeit());

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
