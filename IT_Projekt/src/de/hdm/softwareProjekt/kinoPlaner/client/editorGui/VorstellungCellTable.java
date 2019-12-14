package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

public class VorstellungCellTable extends VerticalPanel {

	KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

	private ArrayList<Vorstellung> vorstellungen = null;

	// CellTable der Vorstellungen

	private CellTable<Vorstellung> vorstellungenCellTable = new CellTable<Vorstellung>(KEY_PROVIDER);
	private final MultiSelectionModel<Vorstellung> selectionModel = new MultiSelectionModel<Vorstellung>();
	private ListDataProvider<Vorstellung> dataProvider = new ListDataProvider<Vorstellung>();
	private List<Vorstellung> list = dataProvider.getList();

	private static final ProvidesKey<Vorstellung> KEY_PROVIDER = new ProvidesKey<Vorstellung>() {
		public Object getKey(Vorstellung v) {
			return v.getId();
		}
	};

	private Film film = null;
	private Spielzeit spielzeit = null;
	private Kino kino = null;
	private Kinokette kinokette = null;

	// DateFormaterSpielzeit date = new DateFormaterSpielzeit(spielzeit.getZeit());

	public void onLoad() {

		this.add(vorstellungenCellTable);

		/***********************************************************************
		 * CELL TABLE
		 ***********************************************************************/

		kinoplaner.getAllVorstellungen(new VorstellungenCallback());

		if (vorstellungen != null) {

			for (Vorstellung v : vorstellungen) {

				list.add(v);

			}

		} else {

			this.add(new Label("Es sind noch keine Vorstellungen vorhanden!"));
		}

		ButtonCell buttonCell = new ButtonCell();
		Column<Vorstellung, String> buttonColumn = new Column<Vorstellung, String>(buttonCell) {

			public String getValue(Vorstellung object) {

				return "+";

			}
		};

		buttonColumn.setFieldUpdater(new FieldUpdater<Vorstellung, String>() {

			@Override
			public void update(int index, Vorstellung object, String value) {
				// TODO Auto-generated method stub

				kinoplaner.umfrageoptionHinzufuegen(object, new UmfrageOptionHinzufuegenCallback());

				dataProvider.getList().remove(object);
				dataProvider.refresh();

			}

		});

		vorstellungenCellTable.addColumn(buttonColumn, "Auswählen");

		TextCell filmCell = new TextCell();
		Column<Vorstellung, String> filmColumn = new Column<Vorstellung, String>(filmCell) {

			@Override
			public String getValue(Vorstellung object) {
				// TODO Auto-generated method stub
				kinoplaner.getFilmById(object.getFilmId(), new FilmByIdCallback());
				return film.getName();
			}

		};

		vorstellungenCellTable.addColumn(filmColumn, "Film");

		TextCell spielzeitCell = new TextCell();
		Column<Vorstellung, String> spielzeitColumn = new Column<Vorstellung, String>(spielzeitCell) {

			@Override
			public String getValue(Vorstellung object) {
				// TODO Auto-generated method stub
				return null;
				// return date.toString();
			}

		};

		vorstellungenCellTable.addColumn(spielzeitColumn, "Spielzeit");

		TextCell kinoCell = new TextCell();
		Column<Vorstellung, String> kinoColumn = new Column<Vorstellung, String>(kinoCell) {

			@Override
			public String getValue(Vorstellung object) {
				// TODO Auto-generated method stub
				kinoplaner.getKinoById(kino.getId(), new KinoByIdCallback());
				return kino.getName();
			}

		};

		vorstellungenCellTable.addColumn(kinoColumn, "Kino");

		TextCell kinokettenCell = new TextCell();
		Column<Vorstellung, String> kinokettenCellColumn = new Column<Vorstellung, String>(kinokettenCell) {

			@Override
			public String getValue(Vorstellung object) {
				// TODO Auto-generated method stub
				kinoplaner.getKinoketteById(kino.getKinokettenId(), new KinoketteByIdCallback());
				return kinokette.getName();
			}

		};

		vorstellungenCellTable.addColumn(kinokettenCellColumn, "Kinokette");

		TextCell stadtCell = new TextCell();
		Column<Vorstellung, String> stadtCellColumn = new Column<Vorstellung, String>(stadtCell) {

			@Override
			public String getValue(Vorstellung object) {
				// TODO Auto-generated method stub
				return kino.getStadt();
			}

		};

		vorstellungenCellTable.addColumn(stadtCellColumn, "Ort");

		vorstellungenCellTable.setSelectionModel(selectionModel,
				DefaultSelectionEventManager.<Vorstellung>createCheckboxManager());

		dataProvider.addDataDisplay(vorstellungenCellTable);

	}

	private class VorstellungenCallback implements AsyncCallback<ArrayList<Vorstellung>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Es sind noch keine Vorstellungen verfügbar");

		}

		@Override
		public void onSuccess(ArrayList<Vorstellung> result) {
			// TODO Auto-generated method stub
			vorstellungen = result;
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

		}

	}

	private class KinoByIdCallback implements AsyncCallback<Kino> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Kino result) {
			// TODO Auto-generated method stub
			kino = result;

		}

	}

	private class KinoketteByIdCallback implements AsyncCallback<Kinokette> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Kinokette result) {
			// TODO Auto-generated method stub
			kinokette = result;

		}

	}

	private class UmfrageOptionHinzufuegenCallback implements AsyncCallback<Vorstellung> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Erstellen einer Umfrageoption war NICHT erfolgreich");

		}

		@Override
		public void onSuccess(Vorstellung result) {
			// TODO Auto-generated method stub
			Window.alert("Erstellen einer Umfrageoption war erfolgreich");

		}

	}

}
