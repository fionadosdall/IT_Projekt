package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

public class NeueCellTable extends VerticalPanel {

	KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

	private CellTable<Vorstellung> vorstellungenCellTable = new CellTable<Vorstellung>();
	private ListDataProvider<Vorstellung> dataProvider = new ListDataProvider<Vorstellung>();
	private List<Vorstellung> list = dataProvider.getList();

	private ArrayList<Vorstellung> vorstellungen = null;

//	private Column<Vorstellung, String> buttonColumn;

//	private Column<Vorstellung, String> kinokettenCellColumn;
//	private Column<Vorstellung, String> stadtCellColumn;

	// private ButtonCell buttonCell = new ButtonCell();
	private TextCell filmCell = new TextCell();
	private TextCell spielzeitCell = new TextCell();;
	private TextCell kinoCell = new TextCell();;
//	private TextCell kinokettenCell = new TextCell();;
//	private TextCell stadtCell = new TextCell();;

	private Film film = null;
	private Spielzeit spielzeit = null;
	private Kino kino = null;
//	private Kinokette kinokette = null;

	public void onLoad() {

		this.add(vorstellungenCellTable);

		kinoplaner.getAllVorstellungen(new VorstellungCallback());

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

				list.add(v);

				kinoplaner.getFilmById(v.getFilmId(), new FilmByIdCallback());
				kinoplaner.getSpielzeitById(v.getSpielzeitId(), new SpielzeitCallback());
				kinoplaner.getKinoByVorstellung(v, new KinoCallback());

			}

			dataProvider.addDataDisplay(vorstellungenCellTable);
		}

	}

	private class FilmByIdCallback implements AsyncCallback<Film> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Film result) {
			// TODO Auto-generated method stub
			film = result;

			Column<Vorstellung, String> filmColumn = new Column<Vorstellung, String>(filmCell) {

				@Override
				public String getValue(Vorstellung object) {
					// TODO Auto-generated method stub

					return film.getName();

				}
			};
			Window.alert("1");
			vorstellungenCellTable.addColumn(filmColumn, "Film");

		}

	}

	private class SpielzeitCallback implements AsyncCallback<Spielzeit> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Spielzeit result) {
			// TODO Auto-generated method stub
			spielzeit = result;

			Column<Vorstellung, String> spielzeitColumn = new Column<Vorstellung, String>(spielzeitCell) {

				@Override
				public String getValue(Vorstellung object) {
					// TODO Auto-generated method stub

					return spielzeit.getZeit().toString();

				}

			};
			Window.alert("2");
			vorstellungenCellTable.addColumn(spielzeitColumn, "Spielzeit");

		}

	}

	private class KinoCallback implements AsyncCallback<Kino> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Kino result) {
			// TODO Auto-generated method stub
			kino = result;

			Column<Vorstellung, String> kinoColumn = new Column<Vorstellung, String>(kinoCell) {

				@Override
				public String getValue(Vorstellung object) {
					// TODO Auto-generated method stub

					return kino.getName();
				}

			};
			Window.alert("3");
			vorstellungenCellTable.addColumn(kinoColumn, "Kino");

		}

	}

}

