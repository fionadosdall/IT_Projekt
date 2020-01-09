package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
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
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

public class NeueCellTable extends VerticalPanel {
	
	private int i = 0;

	KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();
	
	private class VorstellungInfo {
		
		Vorstellung v;
		
		String filmName;
		String kinoName;

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

		
	}

	private CellTable<VorstellungInfo> vorstellungenCellTable = new CellTable<VorstellungInfo>();
	
	private ListDataProvider<VorstellungInfo> dataProvider = new ListDataProvider<VorstellungInfo>();
	private List<VorstellungInfo> list = dataProvider.getList();

	private ArrayList<Vorstellung> vorstellungen = null;

	private TextCell filmCell = new TextCell();
	private TextCell kinoCell = new TextCell();

	private VorstellungInfo vI;


	public void onLoad() {

		this.add(vorstellungenCellTable);

		kinoplaner.getAllVorstellungen(new VorstellungCallback());

		dataProvider.addDataDisplay(vorstellungenCellTable);

	}
	

	private class VorstellungCallback implements AsyncCallback<ArrayList<Vorstellung>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(ArrayList<Vorstellung> result) {
			// TODO Auto-generated method stub
			vorstellungen = result;
		
		
			for (Vorstellung v : vorstellungen) {
				
				vI = new VorstellungInfo();
				
				vI.setV(v);
				
				list.add(vI);
				
				kinoplaner.getFilmById(v.getFilmId(), new FilmByIdCallback(vI));
				kinoplaner.getKinoByVorstellung(v, new KinoCallback(vI));

			}


		}

	}

	private class FilmByIdCallback implements AsyncCallback<Film> {
		
		VorstellungInfo info = null;
		
		FilmByIdCallback(VorstellungInfo info){
			this.info=info;
		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Film result) {
			// TODO Auto-generated method stub
			
			info.filmName=result.getName();


			Column<VorstellungInfo, String> filmColumn = new Column<VorstellungInfo, String>(filmCell) {

				@Override
				public String getValue(VorstellungInfo object) {
					// TODO Auto-generated method stub
					
					info = object;

					return info.getFilmName();

				}
			};
			Window.alert("1");
			
			vorstellungenCellTable.addColumn(filmColumn, "Film");
			
			
		}

	}
	
	private class KinoCallback implements AsyncCallback<Kino> {
		
		VorstellungInfo info = null;
		
		KinoCallback(VorstellungInfo info){
			this.info=info;
		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Kino result) {
			// TODO Auto-generated method stub
			
			info.kinoName=result.getName();
			
			Column<VorstellungInfo, String> kinoColumn = new Column<VorstellungInfo, String>(kinoCell) {

				@Override
				public String getValue(VorstellungInfo object) {
					// TODO Auto-generated method stub
					
					info = object;

					return info.getKinoName();

				}
			};
			vorstellungenCellTable.addColumn(kinoColumn, "Kino");

							
		}
		
	}

}


