package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HomeBarAdmin extends HorizontalPanel {

	private MeineKinokettenForm mkkf;
	private MeineKinosForm mkf;
	private MeineSpielplaeneForm mspf;

	private FlowPanel homeBarLinks = new FlowPanel();
	private FlowPanel homeBarMitte = new FlowPanel();
	private FlowPanel homeBarRechts = new FlowPanel();

	private Anchor kinoketten = new Anchor("Meine Kinoketten");
	private Anchor kinos = new Anchor("Meine Kinos");
	private Anchor spielplaene = new Anchor("Meine Spielpläne");

	public void onLoad() {

		this.addStyleName("homeBar");

		homeBarLinks.addStyleName("homeBarItem");
		homeBarMitte.addStyleName("homeBarItem");
		homeBarRechts.addStyleName("homeBarItem");

		kinoketten.addStyleName("homeBarAnchor");
		kinos.addStyleName("homeBarAnchor");
		spielplaene.addStyleName("homeBarAnchor");

		homeBarLinks.add(kinoketten);
		homeBarMitte.add(kinos);
		homeBarRechts.add(spielplaene);

		this.add(homeBarLinks);
		this.add(homeBarMitte);
		this.add(homeBarRechts);

		kinoketten.addClickHandler(new KinokettenAnzeigenClickHandler());
		kinos.addClickHandler(new KinosAnzeigenClickHandler());
		spielplaene.addClickHandler(new SpielplaeneAnzeigenClickHandler());

	}

	private class KinokettenAnzeigenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			mkkf = new MeineKinokettenForm();
			RootPanel.get("details").add(mkkf);

		}

	}

	private class KinosAnzeigenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			mkf = new MeineKinosForm();
			RootPanel.get("details").add(mkf);

		}
	}

	private class SpielplaeneAnzeigenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			mspf = new MeineSpielplaeneForm();
			RootPanel.get("details").add(mspf);

		}
	}

}
