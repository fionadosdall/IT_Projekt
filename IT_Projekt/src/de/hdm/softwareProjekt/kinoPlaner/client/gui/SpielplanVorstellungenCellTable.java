package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle.Source;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.NeueCellTable;
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
	private NeueCellTable nct = null;
	private VorstellungInfo uI;
	private Vorstellung vorstellung;
	
	private ArrayList<VorstellungInfo> vorstellungenInfo = new ArrayList<VorstellungInfo>();
	private ArrayList<Vorstellung> neueVorstellung = null;
	
	private ListDataProvider<VorstellungInfo> dataProvider = new ListDataProvider<VorstellungInfo>();
	private List<VorstellungInfo> vorstellungList = dataProvider.getList();
	
	private CellTable<VorstellungInfo> vorstellungenTable = new CellTable<VorstellungInfo>(100, tableRes);
	
	private ButtonCell buttonCell = new ButtonCell();
	private TextCell filmCell = new TextCell();
	private TextCell spielzeitCell = new TextCell();
	
	

	public SpielplanVorstellungenCellTable(NeueCellTable nct) {
		this.nct = nct;
		nct.setSpielplanVorstellungenCellTable(this);
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

	public ArrayList<VorstellungInfo> getVorstellungenArray(){
	return vorstellungenInfo;
	
}

	public void setVorstellungArray(ArrayList<VorstellungInfo> vorstellungenInfo){
	this.vorstellungenInfo = vorstellungenInfo;
}
	

	@Override
	public void onLoad() {
		
		this.add(vorstellungenTable);
		
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
				// TODO Auto-generated method stub
				
				dataProvider.getList().remove(object); 
				dataProvider.refresh();
				nct.addVorstellung(object.getVorstellung());
				

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
		
		
		
		Column<VorstellungInfo, String> spielzeitColumn = new Column<VorstellungInfo, String>(spielzeitCell) {

			@Override
			public String getValue(VorstellungInfo object) {
				// TODO Auto-generated method stub

				return object.getSpielzeit();

			}
		};
		spielzeitColumn.setSortable(true);
		vorstellungenTable.addColumn(spielzeitColumn, "Spielzeit");
		
		dataProvider.addDataDisplay(vorstellungenTable);

		this.addUmfrageoption(neueVorstellung);
	}
	
	/*Methoden*/
	
	public void addUmfrageoption(ArrayList<Vorstellung> neueUmfrageoptionen) {

		this.neueVorstellung = nct.getUmfrageOptionen();

		if (this.neueVorstellung != null) {
			
			vorstellungList.clear();

			for (Vorstellung v : this.neueVorstellung) {

				//Window.alert(v.getName());

				uI = new VorstellungInfo();

				uI.setU(v);

				//Window.alert(v.getName());

				vorstellungList.add(uI);

				administration.getFilmById(v.getFilmId(), new FilmByIdCallback(uI));
				administration.getSpielzeitById(v.getSpielzeitId(), new SpielzeitCallback(uI));
			}
		} else {
			vorstellungenTable.setEmptyTableWidget(new Label("leer"));
		}

	}
	/*Callbacks*/
	
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
	
}
