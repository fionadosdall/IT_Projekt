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
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

public class VorstellungCellTable extends VerticalPanel {

	public interface CellTableResources extends CellTable.Resources {

		@Source({ CellTable.Style.DEFAULT_CSS, "CellTable.css" })
		TableStyle cellTableStyle();

		interface TableStyle extends CellTable.Style {
		}
	}

	CellTable.Resources tableRes = GWT.create(CellTableResources.class);

	KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

	private ArrayList<Vorstellung> vorstellungen = null;

	// CellTable der Vorstellungen

	private CellTable<Vorstellung> vorstellungenCellTable = new CellTable<Vorstellung>(10, tableRes, KEY_PROVIDER);

	private ListDataProvider<Vorstellung> dataProvider = new ListDataProvider<Vorstellung>();
	private List<Vorstellung> list = dataProvider.getList();

	private Column<Vorstellung, String> buttonColumn;
	private Column<Vorstellung, Film> filmColumn;
	//private Column<Vorstellung, Sting> filmColumn;
	private Column<Vorstellung, String> spielzeitColumn;
	private Column<Vorstellung, String> kinoColumn;
	private Column<Vorstellung, String> kinokettenCellColumn;
	private Column<Vorstellung, String> stadtCellColumn;

	private ButtonCell buttonCell;
	private FilmCell filmCell;
	//private TextCell filmCell;
	private TextCell spielzeitCell;
	private TextCell kinoCell;
	private TextCell kinokettenCell;
	private TextCell stadtCell;

	private static final ProvidesKey<Vorstellung> KEY_PROVIDER = new ProvidesKey<Vorstellung>() {
		public Object getKey(Vorstellung v) {
			return v.getId();
		}
	};

	private Film film = null;
	private Spielzeit spielzeit = null;
	private Kino kino = null;
	private Kinokette kinokette = null;

	private Vorstellung vorstellung = null;

//	 DateFormaterSpielzeit date = new DateFormaterSpielzeit(spielzeit.getZeit());

	public Vorstellung getVorstellung() {
		return vorstellung;
	}

	public void setVorstellung(Vorstellung vorstellung) {
		this.vorstellung = vorstellung;
	}

	public void onLoad() {

		this.add(vorstellungenCellTable);

		kinoplaner.getAllVorstellungen(new AsyncCallback<ArrayList<Vorstellung>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(ArrayList<Vorstellung> result) {
				// TODO Auto-generated method stub
				
				vorstellungen = result;

				if (vorstellungen != null) {

					for (Vorstellung v : vorstellungen) {

						Window.alert("Vorstellungsname " + v.getName());

						list.add(v);

						kinoplaner.getFilmById(v.getFilmId(), new FilmByIdCallback());
						kinoplaner.getKinoByVorstellung(v, new KinoByVorstellungCallback());
						kinoplaner.getSpielzeitById(v.getSpielzeitId(), new SpielzeitCallback());
					

					}

				} else {

					vorstellungenCellTable.setEmptyTableWidget(new Label("Es sind noch keine Vorstellungen vorhanden"));
				}
			}

			
			
		});
	

		// Cells

		buttonCell = new ButtonCell();
		filmCell = new FilmCell();
		spielzeitCell = new TextCell();
		kinoCell = new TextCell();
		kinokettenCell = new TextCell();
		stadtCell = new TextCell();

		// Columns

		buttonColumn = new Column<Vorstellung, String>(buttonCell) {

			public String getValue(Vorstellung object) {

				return "+";

			}
		};

		buttonColumn.setFieldUpdater(new FieldUpdater<Vorstellung, String>() {

			@Override
			public void update(int index, Vorstellung object, String value) {
				// TODO Auto-generated method stub

				vorstellung = object;

				kinoplaner.umfrageoptionHinzufuegen(object, new UmfrageOptionHinzufuegenCallback());

			}

		});

		filmColumn = new Column<Vorstellung, Film>(filmCell) {
			

			@Override
			public Film getValue(Vorstellung object) {
				// TODO Auto-generated method stub
			//	FilmByIdCallback f = new FilmByIdCallback();
				
				Window.alert("Film Column");

				vorstellung = object;

				kinoplaner.getFilmById(object.getFilmId(), new FilmByIdCallback());
				
				return film;


			}

		};

		spielzeitColumn = new Column<Vorstellung, String>(spielzeitCell) {

			@Override
			public String getValue(Vorstellung object) {
				// TODO Auto-generated method stub

				vorstellung = object;

				kinoplaner.getSpielzeitById(object.getSpielzeitId(), new SpielzeitCallback());

				return "";

			}

		};

		kinoColumn = new Column<Vorstellung, String>(kinoCell) {

			@Override
			public String getValue(Vorstellung object) {
				// TODO Auto-generated method stub

				vorstellung = object;

				kinoplaner.getKinoByVorstellung(object, new KinoByVorstellungCallback());

				return "";
			}

		};

		kinokettenCellColumn = new Column<Vorstellung, String>(kinokettenCell) {

			@Override
			public String getValue(Vorstellung object) {
				// TODO Auto-generated method stub

				vorstellung = object;

				return "";
			}

		};

		stadtCellColumn = new Column<Vorstellung, String>(stadtCell) {

			@Override
			public String getValue(Vorstellung object) {
				// TODO Auto-generated method stub

				return "";

//				return kino.getStadt();
			}

		};

		vorstellungenCellTable.addColumn(buttonColumn, "Auswählen");
		vorstellungenCellTable.addColumn(filmColumn, "Film");
		vorstellungenCellTable.addColumn(spielzeitColumn, "Spielzeit");
		vorstellungenCellTable.addColumn(kinoColumn, "Kino");
		vorstellungenCellTable.addColumn(kinokettenCellColumn, "Kinokette");
		vorstellungenCellTable.addColumn(stadtCellColumn, "Ort");

		vorstellungenCellTable.setWidth("100%");

		dataProvider.addDataDisplay(vorstellungenCellTable);

	}

	/***********************************************************************
	 * CALLBACKS
	 ***********************************************************************/

//	private class VorstellungenCallback implements AsyncCallback<ArrayList<Vorstellung>> {
//
//		@Override
//		public void onFailure(Throwable caught) {
//			// TODO Auto-generated method stub
//			Window.alert("Es sind noch keine Vorstellungen verfügbar");
//
//		}
//
//		@Override
//		public void onSuccess(ArrayList<Vorstellung> result) {
//			// TODO Auto-generated method stub
//
//			vorstellungen = result;
//
//			if (vorstellungen != null) {
//
//				for (Vorstellung v : vorstellungen) {
//
//					Window.alert("Vorstellungsname " + v.getName());
//
//					list.add(v);
//
//					kinoplaner.getFilmById(v.getFilmId(), new FilmByIdCallback());
//					kinoplaner.getKinoByVorstellung(v, new KinoByVorstellungCallback());
//					kinoplaner.getSpielzeitById(v.getSpielzeitId(), new SpielzeitCallback());
//				
//
//				}
//
//			} else {
//
//				vorstellungenCellTable.setEmptyTableWidget(new Label("Es sind noch keine Vorstellungen vorhanden"));
//			}
//		}
//
//	}

	private class FilmByIdCallback implements AsyncCallback<Film> {
		
//		 Film film; 
//		
//		
//		public Film getFilm() {
//			return this.film;
//		}
//
//		public void setFilm(Film film) {
//			this.film = film;
//		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Film wird nicht aufgerufen");

		}

		@Override
		public void onSuccess(Film result) {
			// TODO Auto-generated method stub
			
			film = result;
		
			Window.alert(String.valueOf("Film ID: " + film.getId()));
			Window.alert("Film " + film.getName());

			filmColumn = new Column<Vorstellung, Film>(filmCell) {
			

				@Override
				public Film getValue(Vorstellung object) {
					// TODO Auto-generated method stub
			
					return film;
				

				}
			};


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

			Window.alert(String.valueOf("Spielzeit ID: " + spielzeit.getId()));

			spielzeitColumn = new Column<Vorstellung, String>(spielzeitCell) {

				@Override
				public String getValue(Vorstellung object) {
					// TODO Auto-generated method stub

					return spielzeit.getZeit().toString();

				}

			};

		}

	}

	private class KinoByVorstellungCallback implements AsyncCallback<Kino> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Kino result) {
			// TODO Auto-generated method stub
			kino = result;

			Window.alert(String.valueOf("Kino ID: " + kino.getId()));

			kinoColumn = new Column<Vorstellung, String>(kinoCell) {

				@Override
				public String getValue(Vorstellung object) {
					// TODO Auto-generated method stub

					return kino.getName();
				}

			};

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

			kinokettenCellColumn = new Column<Vorstellung, String>(kinokettenCell) {

				@Override
				public String getValue(Vorstellung object) {
					// TODO Auto-generated method stub

					return kinokette.getName();
				}

			};

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

			vorstellung = result;

			dataProvider.getList().remove(result);
			dataProvider.refresh();

		}

	}

}
