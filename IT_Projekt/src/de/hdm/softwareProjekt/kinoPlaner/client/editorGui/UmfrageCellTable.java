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
	
	private ArrayList<Umfrageoption> umfrageoptionen = null;
	
	// CellTable der Umfrageoptionen

		private CellTable<Umfrageoption> umfrageCellTable = new CellTable<Umfrageoption>(10, tableRes, KEY_PROVIDER);
		private final MultiSelectionModel<Umfrageoption> selectionModel = new MultiSelectionModel<Umfrageoption>();
		private ListDataProvider<Umfrageoption> dataProvider = new ListDataProvider<Umfrageoption>();
		private List<Umfrageoption> list = dataProvider.getList();

		private static final ProvidesKey<Umfrageoption> KEY_PROVIDER = new ProvidesKey<Umfrageoption>() {
			public Object getKey(Umfrageoption u) {
				return u.getId();
			}
		};
		
		private Film film = null;
		private Spielzeit spielzeit = null;
		private Kino kino = null;
		private Kinokette kinokette = null;
		
		private VorstellungCellTable vct = new VorstellungCellTable();
		
		public void onLoad() {
			
			this.add(umfrageCellTable);
			
			umfrageCellTable.setWidth("100%");
			
			/***********************************************************************
			 * CELL TABLE
			 ***********************************************************************/
			
			// Methode muss noch geändert werden auf getVorstellungenFuerNeueUmfrage()
			
			kinoplaner.getUmfrageoptionenByVorstellung(vct.getVorstellung(), new UmfrageoptionenCallback());
			
			if (umfrageoptionen != null) {
				
				for (Umfrageoption u : umfrageoptionen) {
					list.add(u);
				}

			} else {

				umfrageCellTable.setEmptyTableWidget(new Label("Es sind noch keine Optionen vorhanden"));
			}
		
			
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
				
					AsyncCallback<Vorstellung> entfernenCallback = new AsyncCallback<Vorstellung>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							Window.alert("Umfrageoption entfernen war NICHT erfolgreich");
							
						}

						@Override
						public void onSuccess(Vorstellung result) {
							// TODO Auto-generated method stub
							Window.alert("Umfrageoption entfernen war erfolgreich");
							
						}
						
					};

					kinoplaner.umfrageoptionEntfernen(vct.getVorstellung(), entfernenCallback);

					dataProvider.getList().remove(object);
					dataProvider.refresh();

				}

			});

			umfrageCellTable.addColumn(buttonColumn, "Entfernen");

			TextCell filmCell = new TextCell();
			Column<Umfrageoption, String> filmColumn = new Column<Umfrageoption, String>(filmCell) {

				@Override
				public String getValue(Umfrageoption object) {
					// TODO Auto-generated method stub
					kinoplaner.getFilmById(object.getId(), new FilmByIdCallback());
					return film.getName();
	
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
					kinoplaner.getKinoById(kino.getId(), new KinoByIdCallback());
					return kino.getName();
				
				}

			};

			umfrageCellTable.addColumn(kinoColumn, "Kino");

			TextCell kinokettenCell = new TextCell();
			Column<Umfrageoption, String> kinokettenCellColumn = new Column<Umfrageoption, String>(kinokettenCell) {

				@Override
				public String getValue(Umfrageoption object) {
					// TODO Auto-generated method stub
					kinoplaner.getKinoketteById(kino.getKinokettenId(), new KinoketteByIdCallback());
					return kinokette.getName();
			
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
		
		/***********************************************************************
		 * CALLBACKS
		 ***********************************************************************/
		
		private class UmfrageoptionenCallback implements AsyncCallback<ArrayList<Umfrageoption>> {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(ArrayList<Umfrageoption> result) {
				// TODO Auto-generated method stub
				umfrageoptionen = result;
				
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
}
