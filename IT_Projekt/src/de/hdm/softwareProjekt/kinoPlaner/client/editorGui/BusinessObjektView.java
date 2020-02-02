package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.gui.KinoCell;
import de.hdm.softwareProjekt.kinoPlaner.client.gui.KinokettenCell;
import de.hdm.softwareProjekt.kinoPlaner.client.gui.SpielplanCell;
import de.hdm.softwareProjekt.kinoPlaner.client.gui.SpielplanEintragCell;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielplan;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

/**
 * Die Klasse BusinessObjektView, stellt die passenden BO's für die CellLists
 * zur Verfügung
 *
 */
public class BusinessObjektView extends VerticalPanel {

	private HorizontalPanel head = new HorizontalPanel();
	private VerticalPanel actions = new VerticalPanel();
	private ScrollPanel cellList = new ScrollPanel();
	private Label titel = new Label();
	private CellList<Gruppe> listGruppe;
	private CellList<Umfrage> listUmfrage;
	private CellList<Umfrage> listErgebnis;
	private CellList<Kino> listKino;
	private CellList<Kinokette> listKinokette;
	private CellList<Spielplan> listSpielplan;
	private CellList<Vorstellung> listVorstellung;

	// onLoad()- Methode, fügt die Panels, nach Laden der Seite hinzu.

	public void onLoad() {
		head.setStyleName("");
		actions.setStyleName("");
		titel.setStyleName("detailsboxLabels");
		head.add(titel);
		head.add(actions);
		this.add(head);
		cellList.setSize("250px", "300px");
		this.add(cellList);

	}

	public void setTitel(String titel) {
		this.titel.setText(titel);
	}

	/*
	 * Erstellung der CellListen für die BusinessObjekte, durch Übergabe der
	 * entsprechenden Klassen als Liste, wird für jedes BO eine eigene Liste
	 * angelegt.
	 */

	public void setGruppen(ArrayList<Gruppe> gruppen) {
		GruppeCell cell = new GruppeCell();
		listGruppe = new CellList<Gruppe>(cell);
		listGruppe.setStyleName("");
		listGruppe.setPageSize(30);
		listGruppe.setRowData(gruppen);

		cellList.add(listGruppe);
	}

	public void setUmfragen(ArrayList<Umfrage> umfragen) {
		UmfrageCell cell = new UmfrageCell();
		listUmfrage = new CellList<Umfrage>(cell);
		listUmfrage.setStyleName("");
		listUmfrage.setPageSize(30);
		listUmfrage.setRowData(umfragen);
		cellList.add(listUmfrage);
	}

	public void setErgebnisse(ArrayList<Umfrage> ergebnisse) {
		ErgebnisCell cell = new ErgebnisCell();
		listErgebnis = new CellList<Umfrage>(cell);
		listErgebnis.setStyleName("");
		listErgebnis.setPageSize(30);
		listErgebnis.setRowData(ergebnisse);
		cellList.add(listErgebnis);
	}

	public void setKinos(ArrayList<Kino> kinos) {
		KinoCell cell = new KinoCell();
		listKino = new CellList<Kino>(cell);
		listKino.setStyleName("");
		listKino.setPageSize(30);
		listKino.setRowData(kinos);
		cellList.add(listKino);
	}

	public void setKinoketten(ArrayList<Kinokette> kinoketten) {
		KinokettenCell cell = new KinokettenCell();
		listKinokette = new CellList<Kinokette>(cell);
		listKinokette.setStyleName("");
		listKinokette.setPageSize(30);
		listKinokette.setRowData(kinoketten);
		cellList.add(listKinokette);
	}

	public void setSpielplaene(ArrayList<Spielplan> spielplaene) {
		SpielplanCell cell = new SpielplanCell();
		listSpielplan = new CellList<Spielplan>(cell);
		listSpielplan.setStyleName("");
		listSpielplan.setPageSize(30);
		listSpielplan.setRowData(spielplaene);
		cellList.add(listSpielplan);
	}

	public void setVorstellungen(ArrayList<Vorstellung> vorstellungen) {
		SpielplanEintragCell cell = new SpielplanEintragCell();
		listVorstellung = new CellList<Vorstellung>(cell);
		listVorstellung.setStyleName("");
		listVorstellung.setPageSize(30);
		listVorstellung.setRowData(vorstellungen);
		cellList.add(listVorstellung);
	}

	// Methodendeklaration

	public void addAction(Image image, ClickHandler clickHandler) {
		image.setStyleName("");
		image.addClickHandler(clickHandler);
		actions.add(image);
	}

}
