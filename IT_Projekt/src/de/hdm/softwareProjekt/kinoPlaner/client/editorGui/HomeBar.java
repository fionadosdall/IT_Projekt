package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Auf der Startseite des Editors wird dem eingeloggten Nutzer ein Dashboard
 * dargestellt. Die Klasse HomeBar ermöglicht es ihm, auf diesem Dashboard
 * zwischen seinen Gruppen, (offenen) Umfragen und seinen Ergebnissen (der
 * geschlossenen Umfragen) zu navigieren. Das wird durch die Verwendung von
 * Anchors umgesetzt.
 *
 */
public class HomeBar extends FlowPanel {

	private GruppenAnzeigenForm gaf;
	private UmfragenAnzeigenForm uaf;
	private ErgebnisseAnzeigenForm eaf;

	private FlowPanel homeBarLinks = new FlowPanel();
	private FlowPanel homeBarMitte = new FlowPanel();
	private FlowPanel homeBarRechts = new FlowPanel();

	private Anchor gruppen = new Anchor("Gruppen");
	private Anchor umfragen = new Anchor("Umfragen");
	private Anchor ergebnisse = new Anchor("Ergebnisse");

	/**
	 * onLoad()-Methode: Die ClickHandler werden dem HomeBar bzw. den Anchors
	 * hinzugefügt.
	 */
	public void onLoad() {

		// Stylenamen vergeben

		this.addStyleName("homeBar");

		homeBarLinks.addStyleName("homeBarItem");
		homeBarMitte.addStyleName("homeBarItem");
		homeBarRechts.addStyleName("homeBarItem");

		gruppen.addStyleName("homeBarAnchor");
		umfragen.addStyleName("homeBarAnchor");
		ergebnisse.addStyleName("homeBarAnchor");

		homeBarLinks.add(gruppen);
		homeBarMitte.add(umfragen);
		homeBarRechts.add(ergebnisse);

		/**
		 * Zusammenbauen - der linke, mittlere und rechte Homebar-Bereich werden dem
		 * FlowPanel hinzugefügt.
		 */

		this.add(homeBarLinks);
		this.add(homeBarMitte);
		this.add(homeBarRechts);

		/**
		 * ClickHandler zu den Anchors hinzufügen.
		 */

		gruppen.addClickHandler(new GruppenAnzeigenClickHandler());
		umfragen.addClickHandler(new UmfragenAnzeigenClickHandler());
		ergebnisse.addClickHandler(new ErgebnisseAnzeigenClickHandler());

	}

	/**
	 * ClickHandler um alle Gruppen anzuzeigen, mithilfe eines Objekts der
	 * GruppenAnzeigenForm-Klasse.
	 *
	 */
	private class GruppenAnzeigenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			gaf = new GruppenAnzeigenForm();
			RootPanel.get("details").add(gaf);

		}

	}

	/**
	 * ClickHandler um alle Umfragen anzuzeigen, mithilfe eines Objekts der
	 * UmfragenAnzeigenForm-Klasse.
	 *
	 */
	private class UmfragenAnzeigenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			uaf = new UmfragenAnzeigenForm();
			RootPanel.get("details").add(uaf);

		}
	}

	/**
	 * ClickHandler um alle Ergebnisse anzuzeigen, mithilfe eines Objekts der
	 * ErgebnisseAnzeigenForm-Klasse.
	 *
	 */
	private class ErgebnisseAnzeigenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			eaf = new ErgebnisseAnzeigenForm();
			RootPanel.get("details").add(eaf);

		}
	}

}
