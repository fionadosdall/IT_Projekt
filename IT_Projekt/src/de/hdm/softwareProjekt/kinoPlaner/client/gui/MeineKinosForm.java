package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.client.AdminEntry.AktuellerAnwender;
import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.BusinessObjektView;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;

/*
 * Klasse stellt das Formular bereit um die Kinos anzuzeigen
 */
public class MeineKinosForm extends VerticalPanel {

	private HorizontalPanel obenPanel = new HorizontalPanel();
	private HorizontalPanel hbPanel = new HorizontalPanel();
	private VerticalPanel inhaltPanel = new VerticalPanel();
	private HorizontalPanel untenPanel = new HorizontalPanel();

	private BusinessObjektView bov = new BusinessObjektView();

	private Button kinoErstellenButton = new Button("Neues Kino erstellen");

	private KinoErstellenForm erstellen;
	private Label formHeaderLabel = new Label("Dashboard");
	private Label bearbeitenLabel = new Label("Zum Bearbeiten gewünschtes Kino anklicken.");

	private HomeBarAdmin hb = new HomeBarAdmin();

	public void onLoad() {
		KinoplanerAsync administration = ClientsideSettings.getKinoplaner();

		/* Vergeben der Stylename */

		this.addStyleName("detailscontainer");
		this.addStyleName("center");

		obenPanel.addStyleName("obenPanel");
		hbPanel.addStyleName("hbPanel");
		inhaltPanel.addStyleName("inhaltPanel");

		untenPanel.addStyleName("untenPanel");
		formHeaderLabel.addStyleName("formHeaderLabel");

		kinoErstellenButton.addStyleName("speichernButton");

		/* Zusammen bauen der Widgets */

		obenPanel.add(formHeaderLabel);
		this.add(obenPanel);
		hbPanel.add(hb);
		this.add(hbPanel);

		bov.setTitel("Meine Kinos");
		administration.getKinosByAnwenderOwner(AktuellerAnwender.getAnwender(), new SucheKinosByAnwenderCallback());

		inhaltPanel.add(bov);

		this.add(inhaltPanel);

		untenPanel.add(kinoErstellenButton);
		this.add(untenPanel);

		kinoErstellenButton.addClickHandler(new KinoErstellenClickHandler());

	}

	/* CLickHandler */

	/*
	 * ClickHandler um ein Kino zu erstellen
	 */

	private class KinoErstellenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			erstellen = new KinoErstellenForm();
			RootPanel.get("details").add(erstellen);

		}

	}

	/* Callbacks */

	/**
	 * Callback für Abfrage ein ausgewähltes Kino-Objekt mit dem Anwender
	 * 
	 * @author fiona
	 *
	 */

	private class SucheKinosByAnwenderCallback implements AsyncCallback<ArrayList<Kino>> {

		@Override
		public void onFailure(Throwable caught) {
			Systemmeldung.anzeigen("Kinos nicht abrufbar");

		}

		@Override
		public void onSuccess(ArrayList<Kino> result) {
			bov.setKinos(result);
			inhaltPanel.add(bov);
			inhaltPanel.add(bearbeitenLabel);

		}

	}

}
