package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.List;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrageoption;

public class UmfrageCellTable extends VerticalPanel{
	
	KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();
	
	// CellTable der Umfrageoptionen

		private CellTable<Umfrageoption> umfrageCellTable = new CellTable<Umfrageoption>(KEY_PROVIDER);
		private final MultiSelectionModel<Umfrageoption> selectionModel = new MultiSelectionModel<Umfrageoption>();
		private ListDataProvider<Umfrageoption> dataProvider = new ListDataProvider<Umfrageoption>();
		private List<Umfrageoption> list = dataProvider.getList();

		private static final ProvidesKey<Umfrageoption> KEY_PROVIDER = new ProvidesKey<Umfrageoption>() {
			public Object getKey(Umfrageoption u) {
				return u.getId();
			}
		};
		
		public void onLoad() {
			
			this.add(umfrageCellTable);
			
			/***********************************************************************
			 * CELL TABLE
			 ***********************************************************************/
			
			ButtonCell buttonCell = new ButtonCell();
			Column<Umfrageoption, String> buttonColumn = new Column<Umfrageoption, String>(buttonCell) {

				public String getValue(Umfrageoption object) {

					return "-";

				}
			};

			buttonColumn.setFieldUpdater(new FieldUpdater<Umfrageoption, String>() {

				@Override
				public void update(int index, Umfrageoption object, String value) {
					// TODO Auto-generated method stub

					//kinoplaner.umfrageoptionHinzufuegen(object, new UmfrageOptionHinzufuegenCallback());

					dataProvider.getList().remove(object);
					dataProvider.refresh();

				}

			});

			umfrageCellTable.addColumn(buttonColumn, "Auswählen");

			TextCell filmCell = new TextCell();
			Column<Umfrageoption, String> filmColumn = new Column<Umfrageoption, String>(filmCell) {

				@Override
				public String getValue(Umfrageoption object) {
					// TODO Auto-generated method stub
//					kinoplaner.getFilmById(object.getFilmId(), new FilmByIdCallback());
//					return film.getName();
					return null;
				}

			};

			umfrageCellTable.addColumn(filmColumn, "Film");

			TextCell spielzeitCell = new TextCell();
			Column<Umfrageoption, String> spielzeitColumn = new Column<Umfrageoption, String>(spielzeitCell) {

				@Override
				public String getValue(Umfrageoption object) {
					// TODO Auto-generated method stub
					return null;
					// return date.toString();
				}

			};

			umfrageCellTable.addColumn(spielzeitColumn, "Spielzeit");

			TextCell kinoCell = new TextCell();
			Column<Umfrageoption, String> kinoColumn = new Column<Umfrageoption, String>(kinoCell) {

				@Override
				public String getValue(Umfrageoption object) {
					// TODO Auto-generated method stub
//					kinoplaner.getKinoById(kino.getId(), new KinoByIdCallback());
//					return kino.getName();
					return null;
				}

			};

			umfrageCellTable.addColumn(kinoColumn, "Kino");

			TextCell kinokettenCell = new TextCell();
			Column<Umfrageoption, String> kinokettenCellColumn = new Column<Umfrageoption, String>(kinokettenCell) {

				@Override
				public String getValue(Umfrageoption object) {
					// TODO Auto-generated method stub
//					kinoplaner.getKinoketteById(kino.getKinokettenId(), new KinoketteByIdCallback());
//					return kinokette.getName();
					return null;
				}

			};

			umfrageCellTable.addColumn(kinokettenCellColumn, "Kinokette");

			TextCell stadtCell = new TextCell();
			Column<Umfrageoption, String> stadtCellColumn = new Column<Umfrageoption, String>(stadtCell) {

				@Override
				public String getValue(Umfrageoption object) {
					// TODO Auto-generated method stub
//					return kino.getStadt();
					return null;
				}

			};

			umfrageCellTable.addColumn(stadtCellColumn, "Ort");

			umfrageCellTable.setSelectionModel(selectionModel,
					DefaultSelectionEventManager.<Umfrageoption>createCheckboxManager());

			dataProvider.addDataDisplay(umfrageCellTable);
			
		}

}
