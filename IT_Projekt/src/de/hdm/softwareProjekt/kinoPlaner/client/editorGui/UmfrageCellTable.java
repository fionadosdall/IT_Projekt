package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;


import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrageoption;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

public class UmfrageCellTable extends VerticalPanel{
	
	public interface CellTableResources extends CellTable.Resources {

		@Source({ CellTable.Style.DEFAULT_CSS, "CellTable.css" })
		TableStyle cellTableStyle();

		interface TableStyle extends CellTable.Style {
		}
	}
	
	CellTable.Resources tableRes = GWT.create(CellTableResources.class);
	
	KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

	private class UmfrageInfo {

		Umfrageoption u;

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

	private CellTable<UmfrageInfo> umfrageCellTable = new CellTable<UmfrageInfo>(100, tableRes);

	private ListDataProvider<UmfrageInfo> dataProvider = new ListDataProvider<UmfrageInfo>();
	private List<UmfrageInfo> list = dataProvider.getList();

	private ArrayList<Umfrageoption> umfragen = null;

	private ButtonCell buttonCell = new ButtonCell();
	private TextCell filmCell = new TextCell();
	private TextCell kinoCell = new TextCell();
	private TextCell spielzeitCell = new TextCell();
	private TextCell stadtCell = new TextCell();

	private UmfrageInfo uI;
	private Umfrageoption umfrageoption;
	private Vorstellung vorstellung;

	public Vorstellung getVorstellung() {
		return vorstellung;
	}

	public void setVorstellung(Vorstellung vorstellung) {
		this.vorstellung = vorstellung;
	}

	public void onLoad() {

		this.add(umfrageCellTable);
		
		Column<UmfrageInfo, String> buttonColumn = new Column<UmfrageInfo, String>(buttonCell) {

			@Override
			public String getValue(UmfrageInfo object) {
				// TODO Auto-generated method stub
				return "-";
			}
			
		};
		
		umfrageCellTable.addColumn(buttonColumn, "Entfernen");
		
		buttonColumn.setFieldUpdater(new FieldUpdater<UmfrageInfo, String>() {

			@Override
			public void update(int index, UmfrageInfo object, String value) {
				// TODO Auto-generated method stub
			
			}
		});
		

		Column<UmfrageInfo, String> filmColumn = new Column<UmfrageInfo, String>(filmCell) {

			@Override
			public String getValue(UmfrageInfo object) {
				// TODO Auto-generated method stub

				return object.getFilmName();

			}
		};

		umfrageCellTable.addColumn(filmColumn, "Film");

		Column<UmfrageInfo, String> kinoColumn = new Column<UmfrageInfo, String>(kinoCell) {

			@Override
			public String getValue(UmfrageInfo object) {
				// TODO Auto-generated method stub

				return object.getKinoName();

			}
		};

		umfrageCellTable.addColumn(kinoColumn, "Kino");

		Column<UmfrageInfo, String> speilzeitColumn = new Column<UmfrageInfo, String>(spielzeitCell) {

			@Override
			public String getValue(UmfrageInfo object) {
				// TODO Auto-generated method stub
	
				return object.getSpielzeit();

			}
		};

		umfrageCellTable.addColumn(speilzeitColumn, "Spielzeit");

		Column<UmfrageInfo, String> stadtColumn = new Column<UmfrageInfo, String>(stadtCell) {

			@Override
			public String getValue(UmfrageInfo object) {
				// TODO Auto-generated method stub

				return object.getStadt();

			}
		};

		umfrageCellTable.addColumn(stadtColumn, "Ort");
		
		kinoplaner.getUmfrageoptionenByVorstellung(vorstellung, new UmfrageCallback());

		dataProvider.addDataDisplay(umfrageCellTable);

	}
	
	public UmfrageCellTable(Vorstellung vorstellung) {
		this.vorstellung = vorstellung;
	}

	private class UmfrageCallback implements AsyncCallback<ArrayList<Umfrageoption>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("onFailure UmfrageCallback");

		}

		@Override
		public void onSuccess(ArrayList<Umfrageoption> result) {
			// TODO Auto-generated method stub
			umfragen = result;

			for (Umfrageoption u : umfragen) {

				uI = new UmfrageInfo();

				uI.setU(u);

				list.add(uI);
				
				Window.alert(u.getName());
				
				kinoplaner.getFilmByUmfrageoption(u, new GetFilmByUmfrageoptionCallback(uI));
				kinoplaner.getKinoByUmfrageoption(u, new GetKinoByUmfrageoptionCallback(uI));
				kinoplaner.getSpielzeitByUmfrageoption(u, new GetSpielzeitByUmfrageoptionCallback(uI));
				
				kinoplaner.getUmfrageById(u.getId(), new UmfrageByIdCallback(uI));
			

			}

		}

	}

	private class GetFilmByUmfrageoptionCallback implements AsyncCallback<Film> {

		UmfrageInfo info = null;

		GetFilmByUmfrageoptionCallback(UmfrageInfo info) {
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

	private class GetKinoByUmfrageoptionCallback implements AsyncCallback<Kino> {

		UmfrageInfo info = null;

		GetKinoByUmfrageoptionCallback(UmfrageInfo info) {
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

	private class GetSpielzeitByUmfrageoptionCallback implements AsyncCallback<Spielzeit> {

		UmfrageInfo info = null;

		GetSpielzeitByUmfrageoptionCallback(UmfrageInfo info) {
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
