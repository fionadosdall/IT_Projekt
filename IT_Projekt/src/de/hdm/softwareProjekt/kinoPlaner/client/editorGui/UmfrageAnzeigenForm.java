package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.text.SimpleDateFormat;
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

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Auswahl;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Film;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielzeit;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrageoption;

public class UmfrageAnzeigenForm extends FlowPanel {
	private Umfrage umfrage = null;
	private ArrayList<Umfrageoption> umfrageoptionen = null;
	private KinoplanerAsync kinoplaner;
	private Film film = null;
	private Spielzeit spielzeit = null;
	private Kino kino = null;
	private Kinokette kinokette = null;
	private ArrayList<VotingRadioButtonPanel> vrbpList;
	private Auswahl auswahl = null;

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
	private Button speichern = new Button("speichern");
	private Button votingsAnzeigen = new Button("Votings anzeigen");

	public void onLoad() {
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

		speichern.setStyleName("");
		votingsAnzeigen.setStyleName("");

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
				VotingRadioButtonPanel vrbp = new VotingRadioButtonPanel(u);
				kinoplaner.getAuswahlByAnwenderAndUmfrageoption(u, new GetAuswahlByUmfrageoptionAndAnwender());
				if (auswahl != null) {
					if (auswahl.getVoting() == 1) {
						vrbp.setValueJa(true);
						vrbp.setValueNein(false);
					} else {
						vrbp.setValueJa(false);
						vrbp.setValueNein(true);
					}
				}
				vrbpList.add(vrbp);
				grid.setWidget(i, 0, vrbp);
				grid.setWidget(i, 1, new Label(film.getName()));
				SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy HH:mm");
				date.format(spielzeit.getZeit());
				grid.setWidget(i, 2, new Label(date.toPattern()));
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

		detailsunten.add(speichern);
		detailsunten.add(votingsAnzeigen);

		speichern.addClickHandler(new SpeichernClickHandler());
		votingsAnzeigen.addClickHandler(new VotingsAnzeigenClickHandler());

	}

	public UmfrageAnzeigenForm(Umfrage umfrage) {
		this.umfrage = umfrage;

	}

	private class SpeichernClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			for (VotingRadioButtonPanel rb : vrbpList) {
				rb.save();
			}

			RootPanel.get("details").clear();
			VotingsAnzeigenForm anzeigen = new VotingsAnzeigenForm(umfrage);
			RootPanel.get("details").add(anzeigen);

		}

	}

	private class VotingsAnzeigenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			VotingsAnzeigenForm anzeigen = new VotingsAnzeigenForm(umfrage);
			RootPanel.get("details").add(anzeigen);

		}

	}

	private class GetAuswahlByUmfrageoptionAndAnwender implements AsyncCallback<Auswahl> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Auswahl nicht findbar!");

		}

		@Override
		public void onSuccess(Auswahl result) {
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
