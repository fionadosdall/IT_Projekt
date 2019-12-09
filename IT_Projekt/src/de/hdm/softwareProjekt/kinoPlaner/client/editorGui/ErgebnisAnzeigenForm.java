package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrageoption;

public class ErgebnisAnzeigenForm extends FlowPanel {
	private Umfrage umfrage;
	private Umfrage umfrageStichwahl;
	private ArrayList<Umfrageoption> umfrageoptionen;
	private KinoplanerAsync kinoplaner;
	private Film film;
	private Spielzeit spielzeit;
	private Kino kino;
	private Kinokette kinokette;
	private Umfrageoption umfrageoption;
	private int auswahl;
	private boolean ergebnis;

	private HomeBar hb = new HomeBar();

	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();
	private FlowPanel detailsboxInhalt = new FlowPanel();

	private Label filmLabel = new Label("Film");
	private Label spielzeitLabel = new Label("Spielzeit");
	private Label kinoLabel = new Label("Kino");
	private Label kinoketteLabel = new Label("Kinokette");
	private Label votingLabel = new Label("Voten");
	private Label stadtLabel = new Label("Stadt");

	private Label title = new Label("Umfrage: " + umfrage.getName());
	private Grid grid = new Grid(2, 4);
	private TextArea txt = new TextArea();

	public ErgebnisAnzeigenForm(Umfrage umfrage) {
		this.umfrage = umfrage;

	}

	@Override
	protected void onLoad() {
		super.onLoad();
		kinoplaner = ClientsideSettings.getKinoplaner();

		this.addStyleName("detailscontainer");

		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");
		detailsboxInhalt.addStyleName("detailsboxInhalt");

		title.addStyleName("title");

		this.add(detailsoben);
		this.add(detailsunten);
		this.add(detailsboxInhalt);

		detailsoben.add(hb);
		detailsoben.add(title);

		kinoplaner.getUmfrageoptionenByUmfrage(umfrage, new GetUmfrageoptionenByUmfrageCallback());

		filmLabel.setStyleName("detailsboxLabels");
		spielzeitLabel.setStyleName("detailsboxLabels");
		kinoLabel.setStyleName("detailsboxLabels");
		kinoketteLabel.setStyleName("detailsboxLabels");
		votingLabel.setStyleName("detailsboxLabels");
		stadtLabel.setStyleName("detailsboxLabels");

		if (umfrageoptionen != null) {
			grid.setWidget(0, 0, votingLabel);
			grid.setWidget(0, 1, filmLabel);
			grid.setWidget(0, 2, spielzeitLabel);
			grid.setWidget(0, 3, kinoLabel);
			int j = 4;

			if (RootPanel.get("container").getOffsetHeight() > 800
					& RootPanel.get("container").getOffsetWidth() > 480) {
				j++;
				grid.resizeColumns(j);
				grid.setWidget(0, 4, stadtLabel);
			}

			if (RootPanel.get("container").getOffsetHeight() > 800
					& RootPanel.get("container").getOffsetWidth() > 480) {
				j++;
				grid.resizeColumns(j);
				grid.setWidget(0, 5, kinoketteLabel);
			}
			grid.resizeRows(umfrageoptionen.size() + 1);
			int i = 1;
			for (Umfrageoption u : umfrageoptionen) {
				kinoplaner.getFilmByUmfrageoption(u, new GetFilmByUmfrageoptionCallback());
				kinoplaner.getSpielzeitByUmfrageoption(u, new GetSpielzeitByUmfrageoptionCallback());
				kinoplaner.getKinoByUmfrageoption(u, new GetKinoByUmfrageoptionCallback());
				kinoplaner.berechneAuswahlenByUmfrageoption(u, new BerechneAuswahlenByUmfrageoptionCallback());
				String st = "" + auswahl;
				grid.setWidget(i, 0, new Label(st));
				grid.setWidget(i, 1, new Label(film.getName()));
				DateFormaterSpielzeit date = new DateFormaterSpielzeit(spielzeit.getZeit());
				grid.setWidget(i, 2, new Label(date.toString()));
				grid.setWidget(i, 3, new Label(kino.getName()));

				if (j == 5) {
					grid.setWidget(i, 4, new Label(kino.getStadt()));
				}

				if (j == 6) {
					if (kino.getKinokettenId() != 0) {
						kinoplaner.getKinoketteById(kino.getKinokettenId(), new GetKinoketteByIdCallback());
						grid.setWidget(i, 5, new Label(kinokette.getName()));
					} else {
						grid.setWidget(i, 5, new Label("Keine"));
					}
				}
				i++;
			}
			detailsboxInhalt.add(grid);
		} else {
			detailsboxInhalt.add(new Label("Keine Umfrageoptionen verfügbar!"));
		}
		detailsunten.add(txt);
		StringBuffer buffi = new StringBuffer();
		kinoplaner.ergebnisGefunden(umfrage, new ErgebnisGefundenCallback());
		buffi.append("Für deine Umfrage ");
		buffi.append(umfrage.getName());
		if (ergebnis == true) {
			buffi.append(" konnte ein Gewinner ermittelt werden!\nIhr geht am ");
			kinoplaner.umfrageGewinnerErmitteln(umfrage, new UmfrageGewinnerErmittelnCallback());
			kinoplaner.getFilmByUmfrageoption(umfrageoption, new GetFilmByUmfrageoptionCallback());
			kinoplaner.getSpielzeitByUmfrageoption(umfrageoption, new GetSpielzeitByUmfrageoptionCallback());
			kinoplaner.getKinoByUmfrageoption(umfrageoption, new GetKinoByUmfrageoptionCallback());
			String date = spielzeit.getZeit().toString();
			buffi.append(date);
			buffi.append(" in den Film " + film.getName() + " im Kino " + kino.getName() + ". Das Kino liegt in "
					+ kino.getStadt() + ".");
			if (kino.getKinokettenId() != 0) {
				kinoplaner.getKinoketteById(kino.getKinokettenId(), new GetKinoketteByIdCallback());
				buffi.append(" Das Kino gehört zur Kinokette " + kinokette.getName() + ".");
			}
			buffi.append("\n Viel Spaß!");
			txt.setText(buffi.toString());
		} else {
			buffi.append(
					" konnte kein Gewinner ermittelt werden! \n Wie haben eine Stichwahl für deine Gruppe gestaret! ");
			txt.setText(buffi.toString());
			Button stichwahl = new Button("Stichwahl");
			kinoplaner.volltextSucheUmfragen("Stichwahl " + umfrage.getName(), new VolltextSucheUmfragenCallback());
			stichwahl.addClickHandler(new StichwahlClickHandler());
			detailsunten.add(stichwahl);
		}

		String text = buffi.toString();
		txt.setText(text);
		detailsunten.add(txt);

	}

	private class StichwahlClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			UmfrageAnzeigenForm anzeigen = new UmfrageAnzeigenForm(umfrageStichwahl);
			RootPanel.get("details").add(anzeigen);

		}

	}

	private class VolltextSucheUmfragenCallback implements AsyncCallback<ArrayList<Umfrage>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(ArrayList<Umfrage> result) {
			for (Umfrage u : result) {
				if (u.isOpen() == true) {
					umfrageStichwahl = u;
				}
			}

		}

	}

	private class UmfrageGewinnerErmittelnCallback implements AsyncCallback<Umfrageoption> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Gewinner konnte nicht ermittelt werden.");

		}

		@Override
		public void onSuccess(Umfrageoption result) {
			umfrageoption = result;

		}

	}

	private class ErgebnisGefundenCallback implements AsyncCallback<Boolean> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Ergebnis nicht ermittelbar.");

		}

		@Override
		public void onSuccess(Boolean result) {
			ergebnis = result;

		}

	}

	private class BerechneAuswahlenByUmfrageoptionCallback implements AsyncCallback<Integer> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Auswahlen konnten nicht berechnet werden.");

		}

		@Override
		public void onSuccess(Integer result) {
			auswahl = result;

		}

	}

	private class GetUmfrageoptionenByUmfrageCallback implements AsyncCallback<ArrayList<Umfrageoption>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Keine Umfraeoptionen abrufbar.");

		}

		@Override
		public void onSuccess(ArrayList<Umfrageoption> result) {
			umfrageoptionen = result;

		}

	}

	private class GetFilmByUmfrageoptionCallback implements AsyncCallback<Film> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Kein Film abrufbar.");

		}

		@Override
		public void onSuccess(Film result) {
			film = result;

		}

	}

	private class GetSpielzeitByUmfrageoptionCallback implements AsyncCallback<Spielzeit> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Keine Spielzeit abrufbar.");

		}

		@Override
		public void onSuccess(Spielzeit result) {
			spielzeit = result;

		}

	}

	private class GetKinoByUmfrageoptionCallback implements AsyncCallback<Kino> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Kein Kino abrufbar.");

		}

		@Override
		public void onSuccess(Kino result) {
			kino = result;

		}

	}

	private class GetKinoketteByIdCallback implements AsyncCallback<Kinokette> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Keine Kinokette abrufbar.");

		}

		@Override
		public void onSuccess(Kinokette result) {
			kinokette = result;

		}

	}

}
