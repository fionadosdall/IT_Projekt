package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;

/**
 * Klasse zur Volltextsuche: Auf allen Seiten des Kinoplaners steht dem Nutzer
 * im Header eine Volltextsuche zur Verfügung. Gesucht werden können Umfragen,
 * Gruppen und Ergebnisse.
 * 
 * @author
 *
 */
public class VolltextSucheForm extends FlowPanel {
	private String suchText;
	private ArrayList<Gruppe> gruppen;
	private ArrayList<Umfrage> umfragen;
	private ArrayList<Umfrage> ergebnisse;

	KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();
	private FlowPanel speichernBox = new FlowPanel();
	private FlowPanel detailsBoxObenMitte = new FlowPanel();
	private FlowPanel detailsBoxMitteMitte = new FlowPanel();
	private FlowPanel detailsBoxMitteUnten = new FlowPanel();
	private FlowPanel detailsBoxUntenMitte = new FlowPanel();
	private FlowPanel detailsBoxUnten = new FlowPanel();
	private FlowPanel detailsObenBox = new FlowPanel();
	private FlowPanel detailsMitteBox = new FlowPanel();
	private FlowPanel detailsUntenBox = new FlowPanel();

	private Label title = new Label("Suche");
	private Label gruppe = new Label("Gruppen");
	private Label umfrage = new Label("Umfragen");
	private Label ergebnis = new Label("Ergebnisse");

	private Grid gruppenGrid = new Grid(3, 2);
	private Grid umfragenGrid = new Grid(3, 2);
	private Grid ergebnisseGrid = new Grid(3, 2);

	/**
	 * Konstruktor
	 * 
	 * @param text
	 */

	public VolltextSucheForm(String text) {
		this.suchText = text;
	}

	@Override
	protected void onLoad() {

		super.onLoad();

		// Stylenamen vergeben

		detailsoben.addStyleName("detailsoben");
		detailsunten.addStyleName("detailsunten");

		detailsObenBox.addStyleName("detailsuntenBoxen");
		detailsMitteBox.addStyleName("detailsuntenBoxen");
		detailsUntenBox.addStyleName("detailsuntenBoxen");

		speichernBox.addStyleName("speichernBox");
		detailsBoxObenMitte.addStyleName("detailsBoxMitte");
		detailsBoxMitteMitte.addStyleName("detailsBoxMitte");
		detailsBoxMitteUnten.addStyleName("detailsBoxMitte");
		detailsBoxUntenMitte.addStyleName("detailsBoxMitte");
		detailsBoxUnten.addStyleName("detailsBoxUnten");

		title.addStyleName("title");
		gruppe.addStyleName("detailsboxLabels");
		umfrage.addStyleName("detailsboxLabels");
		ergebnis.addStyleName("detailsboxLabels");

		// Widgets zusammen bauen

		this.add(detailsoben);
		this.add(detailsunten);

		detailsoben.add(title);

		detailsunten.add(detailsObenBox);
		detailsunten.add(detailsMitteBox);
		detailsunten.add(detailsUntenBox);

		detailsObenBox.add(gruppe);
		detailsObenBox.add(detailsBoxObenMitte);
		detailsBoxObenMitte.add(gruppenGrid);

		detailsMitteBox.add(umfrage);
		detailsMitteBox.add(detailsBoxMitteMitte);
		detailsBoxMitteMitte.add(umfragenGrid);

		detailsUntenBox.add(ergebnis);
		detailsUntenBox.add(detailsBoxUntenMitte);
		detailsBoxUntenMitte.add(ergebnisseGrid);

		/**
		 * Hinzufügen der Suche-Callbacks von Gruppe, Umfrage und Ergebnis
		 */

		kinoplaner.volltextSucheGruppen(suchText, new VolltextSucheGruppenCallback());

		kinoplaner.volltextSucheUmfragen(suchText, new VolltextSucheUmfrageCallback());

		kinoplaner.volltextSucheErgebnisse(suchText, new VolltextSucheErgebnisseCallback());

	}

	/******************************
	 * CLICKHANDLER
	 * 
	 *
	 *******************************/

	/**
	 * ClickHandler, um bei der Volltextsuche gefundene Ergebnisse auswählen zu
	 * können. Durch Klicken auf das Ergebnis wird der Nutzer automatisch zur
	 * ErgebnisAnzeigenForm weitergeleitet.
	 * 
	 *
	 */

	private class ErgebnisAuswaehlenClickHandler implements DoubleClickHandler {

		private Umfrage ergebnis;

		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			RootPanel.get("details").clear();
			ErgebnisAnzeigenForm anzeigen = new ErgebnisAnzeigenForm(ergebnis);
			RootPanel.get("details").add(anzeigen);

		}

		public void setErgebnis(Umfrage e) {
			this.ergebnis = e;

		}

	}

	/**
	 * Neue Umfragen können erstellt werden, durch Klicken gelangt der Nutzer
	 * automatisch auf das Formular, um Umfragen erstellen zu können
	 * (UmfrageErstellenForm).
	 * 
	 *
	 */
	private class UmfrageErstellenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			UmfrageErstellenForm erstellen = new UmfrageErstellenForm();
			RootPanel.get("details").add(erstellen);

		}

	}

	/**
	 * ClickHandler, um bei der Volltextsuche gefundene Umfragen auswählen zu
	 * können. Durch Klicken auf die Umfrage, wird der Nutzer automatisch zur
	 * UmfrageAnzeigenForm weitergeleitet.
	 */

	private class UmfrageAuswaehlenClickHandler implements DoubleClickHandler {
		private Umfrage umfrage;

		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			RootPanel.get("details").clear();
			UmfrageAnzeigenForm anzeigen = new UmfrageAnzeigenForm(umfrage);
			RootPanel.get("details").add(anzeigen);

		}

		public void setUmfrage(Umfrage u) {
			umfrage = u;

		}

	}

	/**
	 * Eine neue Gruppe kann erstellt werden. Durch Klicken wird der Nutzer
	 * automatisch zum Formular weitergeleitet, in dem er eine Gruppe anlegen kann
	 * (GruppeErstellenForm)
	 * 
	 * @author
	 *
	 */
	private class GruppeErstellenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			GruppeErstellenForm erstellen = new GruppeErstellenForm();
			RootPanel.get("details").add(erstellen);

		}

	}

	/**
	 * ClickHandler, um bei der Volltextsuche gefundene Gruppen auswählen zu können.
	 * Durch Klicken auf die Gruppe, wird der Nutzer automatisch zur
	 * GruppeAnzeigenForm weitergeleitet.
	 */

	private class GruppeAuswaehlenClickHandler implements DoubleClickHandler {

		private Gruppe gruppe;

		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			RootPanel.get("details").clear();
			GruppeAnzeigenForm anzeigen = new GruppeAnzeigenForm(gruppe);
			RootPanel.get("details").add(anzeigen);

		}

		public void setGruppe(Gruppe gruppe) {
			this.gruppe = gruppe;
		}

	}

	/*********************************
	 * CALLBACK
	 *
	 *********************************/

	/**
	 * Callback für Umfragenergebnisse, die durch die Volltextsuche gefunden wurden. Result
	 * der Suche = alle gefundenen Umfragenergebnisse in einer ArrayList. Ergebnisse werden in
	 * einem Grid dargestellt. Die Einträge im Grid reagieren auf Doppelklicks. Per
	 * Doppelklick gelangt der Nutzer in die Detailsansicht des jeweiligen Elements
	 * (hier das Umfrageergebnis).
	 */
	private class VolltextSucheErgebnisseCallback implements AsyncCallback<ArrayList<Umfrage>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(ArrayList<Umfrage> result) {
			ergebnisse = result;

			if (ergebnisse.size() != 0) {
				ergebnisseGrid.resizeRows(ergebnisse.size() + 2);
				int i = 0;
				for (Umfrage e : ergebnisse) {
					Label ergebnissename = new Label(e.getName());
					ErgebnisAuswaehlenClickHandler click = new ErgebnisAuswaehlenClickHandler();
					click.setErgebnis(e);
					ergebnissename.addDoubleClickHandler(click);
					ergebnisseGrid.setWidget(i, 0, ergebnissename);
					i++;
				}
				ergebnisseGrid.setWidget(i + 1, 0, new Label("Doppelklicken um das Ergebniss anzuzeigen"));
			} else {
				ergebnisseGrid.setWidget(0, 0, new Label("Keine Ergebnisse gefunden."));

			}

		}

	}

	/**
	 * Callback für Umfragen, die durch die Volltextsuche gefunden wurden. Result
	 * der Suche = alle gefundenen Umfragen in einer ArrayList. Ergebnisse werden in
	 * einem Grid dargestellt. Die Einträge im Grid reagieren auf Doppelklicks. Per
	 * Doppelklick gelangt der Nutzer in die Detailsansicht des jeweiligen Elements
	 * (hier die Umfrage).
	 */
	
	private class VolltextSucheUmfrageCallback implements AsyncCallback<ArrayList<Umfrage>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(ArrayList<Umfrage> result) {
			umfragen = result;

			if (umfragen.size() != 0) {
				umfragenGrid.resizeRows(umfragen.size() + 2);
				int i = 0;
				for (Umfrage u : umfragen) {
					Label umfragename = new Label(u.getName());
					UmfrageAuswaehlenClickHandler click = new UmfrageAuswaehlenClickHandler();
					click.setUmfrage(u);
					umfragename.addDoubleClickHandler(click);
					umfragenGrid.setWidget(i, 0, umfragename);
					i++;
				}
				umfragenGrid.setWidget(i + 1, 0, new Label("Doppelklicken um die Umfrage anzuzeigen"));
			} else {
				umfragenGrid.setWidget(0, 0, new Label("Keine Umfragen gefunden."));
				Button erstellenButton = new Button("Erstelle eine Umfrage!");
				erstellenButton.setStyleName("navButton");
				erstellenButton.addClickHandler(new UmfrageErstellenClickHandler());

				umfragenGrid.setWidget(1, 0, erstellenButton);
			}

		}

	}

	/**
	 * Callback für Gruppen, die durch die Volltextsuche gefunden wurden. Result
	 * der Suche = alle gefundenen Gruppen in einer ArrayList. Ergebnisse werden in
	 * einem Grid dargestellt. Die Einträge im Grid reagieren auf Doppelklicks. Per
	 * Doppelklick gelangt der Nutzer in die Detailsansicht des jeweiligen Elements
	 * (hier die Gruppe).
	 */
	private class VolltextSucheGruppenCallback implements AsyncCallback<ArrayList<Gruppe>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(ArrayList<Gruppe> result) {
			gruppen = result;

			if (gruppen.size() != 0) {
				gruppenGrid.resizeRows(gruppen.size() + 2);
				int i = 0;
				for (Gruppe g : gruppen) {
					Label gruppenename = new Label(g.getName());
					GruppeAuswaehlenClickHandler click = new GruppeAuswaehlenClickHandler();
					click.setGruppe(g);
					gruppenename.addDoubleClickHandler(click);
					gruppenGrid.setWidget(i, 0, gruppenename);
					i++;
				}
				gruppenGrid.setWidget(i + 1, 0, new Label("Doppelklicken um die Gruppe anzuzeigen"));
			} else {
				gruppenGrid.setWidget(0, 0, new Label("Keine Gruppen gefunden."));
				Button erstellenButton = new Button("Erstelle eine Gruppe!");
				erstellenButton.setStyleName("navButton");
				erstellenButton.addClickHandler(new GruppeErstellenClickHandler());

				gruppenGrid.setWidget(1, 0, erstellenButton);
			}

		}

	}

}
