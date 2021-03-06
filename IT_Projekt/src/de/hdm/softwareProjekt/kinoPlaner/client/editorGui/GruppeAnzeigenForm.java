package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.client.EditorEntry.AktuellerAnwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;

/*
 * Die Klasse stellt das Formular für das Anzeigen einer Gruppe
 */
public class GruppeAnzeigenForm extends FlowPanel {

	Anwender aktuellerAnwender = AktuellerAnwender.getAnwender();

	private KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

	private Gruppe gruppe;

	public Gruppe getGruppe() {
		return gruppe;
	}

	public void setGruppe(Gruppe gruppe) {
		this.gruppe = gruppe;
	}

	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsrechts = new FlowPanel();
	private FlowPanel detailslinks = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();
	private FlowPanel umfrageWidgets = new FlowPanel();
	private FlowPanel detailsboxloeschen = new FlowPanel();
	private FlowPanel loeschenImage = new FlowPanel();

	private Label title = new Label("Gruppenname");
	private Label mitgliederLabel = new Label("Gruppenmitglieder");
	private Label umfrageLabel = new Label("Umfragen");

	private Image papierkorb = new Image();

	private Button bearbeiten = new Button("Gruppe bearbeiten");
	private Button umfrageErstellen = new Button("Umfrage erstellen");

	private AnwenderCell gruppenmitgliederCell = new AnwenderCell();
	private CellList<Anwender> gruppenmitgliederCellList;
	private ListDataProvider<Anwender> dataProviderAnwender = new ListDataProvider<Anwender>();

	static class AnwenderCell extends AbstractCell<Anwender> {

		/**
		 * Das HTML templates rendert die Cell
		 */
		interface Templates extends SafeHtmlTemplates {

			@SafeHtmlTemplates.Template("<div style=\"{0}\">{1}</div>")
			SafeHtml cell(SafeStyles styles, SafeHtml value);
		}

		/**
		 * Erstellen einer einzelnen Instanz des Templates, das genutzt wird um die Cell
		 * zu rendern
		 * 
		 */
		private static Templates templates = GWT.create(Templates.class);

		@Override
		public void render(Context context, Anwender value, SafeHtmlBuilder sb) {

			if (value == null) {
				return;
			}

			SafeHtml safeValue = SafeHtmlUtils.fromString(value.getName());
			SafeStyles styles = SafeStylesUtils.forTrustedColor(safeValue.asString());
			SafeHtml rendered = templates.cell(styles, safeValue);
			sb.append(rendered);
		}
	}

	private UmfrageCell umfragenCell = new UmfrageCell();
	private CellList<Umfrage> umfragenCellList;
	private ListDataProvider<Umfrage> dataProviderUmfrage = new ListDataProvider<Umfrage>();

	/*
	 * Konstruktor
	 */
	public GruppeAnzeigenForm(Gruppe gruppe) {
		this.gruppe = gruppe;
	}

	public void onLoad() {

		/*
		 * Style Namen vergeben
		 */

		this.addStyleName("detailscontainer");
		detailsboxloeschen.addStyleName("detailsboxlöschen");
		umfrageWidgets.addStyleName("umfrageWidgets");
		detailsoben.addStyleName("detailsoben");
		detailslinks.addStyleName("detailslinks");
		detailsrechts.addStyleName("detailsrechts");
		detailsunten.addStyleName("detailsunten");
		bearbeiten.addStyleName("bearbeitenButton");

		title.addStyleName("title");
		mitgliederLabel.addStyleName("detailsboxLabels");
		umfrageLabel.addStyleName("detailsboxLabels");
		loeschenImage.addStyleName("löschenImage");

		umfrageErstellen.addStyleName("speichernButton");

		/*
		 * Widgets zusammensetzen
		 */

		this.add(detailsoben);
		// this.add(detailslinks);
		// this.add(detailsrechts);
		this.add(detailsunten);
		detailsunten.add(detailsboxloeschen);

		detailsoben.add(title);
		title.setText(gruppe.getName());
		kinoplaner.getUmfragenByGruppe(gruppe, new SucheUmfrageByGruppeCallback());
		// detailslinks.add(mitgliederLabel);
		detailsunten.add(mitgliederLabel);
		gruppenmitgliederCellList = new CellList<Anwender>(gruppenmitgliederCell);
		gruppenmitgliederCellList.setStyleName("");
		gruppenmitgliederCellList.setPageSize(5);
		dataProviderAnwender.addDataDisplay(gruppenmitgliederCellList);
		// detailslinks.add(gruppenmitgliederCellList);
		detailsunten.add(gruppenmitgliederCellList);
		kinoplaner.getGruppenmitgliederByGruppe(gruppe, new SucheGruppenmitgliederByGruppeCallback());

		// detailsrechts.add(umfrageLabel);
		detailsunten.add(umfrageWidgets);
		umfrageWidgets.add(umfrageLabel);
		umfragenCellList = new CellList<Umfrage>(umfragenCell);
		umfragenCellList.setStyleName("");
		umfragenCellList.setPageSize(20);
		dataProviderUmfrage.addDataDisplay(umfragenCellList);
		// detailsrechts.add(umfragenCellList);
		detailsunten.add(umfragenCellList);

		if (AktuellerAnwender.getAnwender().getId() == gruppe.getBesitzerId()) {
			detailsunten.add(bearbeiten);
			bearbeiten.addClickHandler(new UmfrageBearbeitenClickHandler());
			papierkorb.addClickHandler(new GruppeLoeschenClickHandler());
			detailsboxloeschen.add(papierkorb);
			// löschenImage.add(papierkorb);

			papierkorb.setUrl("/images/papierkorb.png");
		}

	}
	/*
	 * Vor dem Löschen einer Gruppe, soll der Nutzer über eine Dialogbox noch einmal
	 * um Bestätigung des Löschvorgangs gebeten werden
	 */

	private class GruppeLoeschenDialogBox extends DialogBox {

		private VerticalPanel verticalPanel = new VerticalPanel();
		private HorizontalPanel buttonPanel = new HorizontalPanel();

		private Label nachfrage = new Label("Gruppe endgültig löschen?");

		private Button jaButton = new Button("Ja");
		private Button neinButton = new Button("Nein");

		// Konstruktor

		public GruppeLoeschenDialogBox() {

			nachfrage.addStyleName("Abfrage");
			jaButton.addStyleName("buttonAbfrage");
			neinButton.addStyleName("buttonAbfrage");

			buttonPanel.add(jaButton);
			buttonPanel.add(neinButton);
			verticalPanel.add(nachfrage);
			verticalPanel.add(buttonPanel);

			this.add(verticalPanel);

			// ClickHandler für SpielzeitLöschenDialogBox

			jaButton.addClickHandler(new GruppeLoeschenBestaetigenClickHandler(this));
			neinButton.addClickHandler(new GruppeLoeschenAbbrechenClickHandler(this));

		}
	}

	/*
	 * ClickHandler zur Lösch-Bestätigung der Gruppe
	 */
	private class GruppeLoeschenBestaetigenClickHandler implements ClickHandler {

		private GruppeLoeschenDialogBox gruppeLoeschenDB;

		public GruppeLoeschenBestaetigenClickHandler(GruppeLoeschenDialogBox gruppeLoeschenDB) {
			this.gruppeLoeschenDB = gruppeLoeschenDB;
		}

		@Override
		public void onClick(ClickEvent event) {
			kinoplaner.loeschen(gruppe, aktuellerAnwender, new GruppeLoeschenCallback());
			gruppeLoeschenDB.hide();

		}

	}
	/*
	 * ClickHandler um das Löschen der Gruppe abzubrechen
	 */

	private class GruppeLoeschenAbbrechenClickHandler implements ClickHandler {

		private GruppeLoeschenDialogBox gruppeLoeschenDB;

		public GruppeLoeschenAbbrechenClickHandler(GruppeLoeschenDialogBox gruppeLoeschenDB) {
			this.gruppeLoeschenDB = gruppeLoeschenDB;

		}

		@Override
		public void onClick(ClickEvent event) {
			gruppeLoeschenDB.hide();
		}

	}

	/*
	 * Wenn der Nutzer die angezeigte Gruppe löschen möchte, kann er dies über den
	 * Lösch-button tun. Dabei öffnet sich automatisch die DialogBox. Die DialogBox
	 * bitten den Nutzer erneut zu bestätigten, dass er die Gruppe löschen möchte.
	 */

	private class GruppeLoeschenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			GruppeLoeschenDialogBox gruppeLoeschenDB = new GruppeLoeschenDialogBox();
			gruppeLoeschenDB.center();

		}

	}
	/*
	 * ClickHandler um eine Umfrage zu erstellen
	 */

	private class UmfrageErstellenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			UmfrageErstellenForm erstellen = new UmfrageErstellenForm();
			RootPanel.get("details").add(erstellen);

		}

	}

	/*
	 * CLickHandler um eine Umfrage zu bearbeiten
	 */

	private class UmfrageBearbeitenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			GruppeErstellenForm erstellen = new GruppeErstellenForm(gruppe);
			RootPanel.get("details").add(erstellen);

		}

	}

	/******************************************************
	 * CALLBACKS *****************************************************
	 * 
	 *
	 */

	/*
	 * Private Instanz um eine Gruppen-Instanz aus dem System zu bekommen
	 */

	private class GruppeLoeschenCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(Void result) {
			RootPanel.get("details").clear();
			GruppenAnzeigenForm anzeigen = new GruppenAnzeigenForm();
			RootPanel.get("details").add(anzeigen);

		}

	}

	/*
	 * Private Klasse um alle Gruppenitglieder-Instanzen, aufgrund der der Gruppe
	 * aus dem System zu bekommen
	 */

	private class SucheGruppenmitgliederByGruppeCallback implements AsyncCallback<ArrayList<Anwender>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(ArrayList<Anwender> result) {
			for (Anwender a : result) {
				dataProviderAnwender.getList().add(a);
			}
			dataProviderAnwender.refresh();

		}

	}

	/*
	 * Private Klasse um alle Umfragen-Instanzen, aufgrund der Gruppe aus dem System
	 * zu bekommen
	 */

	private class SucheUmfrageByGruppeCallback implements AsyncCallback<ArrayList<Umfrage>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(ArrayList<Umfrage> result) {
			if (result.size() == 0) {
				umfrageWidgets.add(new Label("Noch keine Umfrage verfügbar!"));
				umfrageErstellen.addClickHandler(new UmfrageErstellenClickHandler());

				umfrageWidgets.add(umfrageErstellen);
				umfragenCellList.setEmptyListWidget(umfrageWidgets);

			} else {
				for (Umfrage u : result) {
					if (u.isOpen() == true) {
						dataProviderUmfrage.getList().add(u);
					}
				}
				dataProviderUmfrage.refresh();

			}

		}

	}

}
