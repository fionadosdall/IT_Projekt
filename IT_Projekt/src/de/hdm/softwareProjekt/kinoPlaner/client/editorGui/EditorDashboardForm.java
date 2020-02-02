package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class EditorDashboardForm extends FlowPanel {

	private HorizontalPanel obenPanel = new HorizontalPanel();
	private FlowPanel dahboardPanel = new FlowPanel();

	private Button umfragenButton = new Button("Meine Umfragen");
	private Button gruppenButton = new Button("Meine Gruppen");
	private Button ergebnisseButton = new Button("Meine Ergebnisse");

	private Label dashboardFormLabel = new Label("Dashboard");

	private UmfragenAnzeigenForm uaf = new UmfragenAnzeigenForm();
	private GruppenAnzeigenForm gaf = new GruppenAnzeigenForm();
	private ErgebnisseAnzeigenForm eaf = new ErgebnisseAnzeigenForm();

	public void onLoad() {

		this.addStyleName("center");
		this.addStyleName("detailscontainer");

		umfragenButton.addStyleName("dashboardButton");
		gruppenButton.addStyleName("dashboardButton");
		ergebnisseButton.addStyleName("dashboardButton");
		dashboardFormLabel.addStyleName("formHeaderLabel");
		obenPanel.addStyleName("obenPanel");
		dahboardPanel.addStyleName("dashboardKachelnPanel");

		umfragenButton.addClickHandler(new UmfragenAnzeigenClickHandler());
		gruppenButton.addClickHandler(new GruppenAnzeigenClickHandler());
		ergebnisseButton.addClickHandler(new ErgebnisseAnzeigenClickHandler());

		this.add(obenPanel);
		obenPanel.add(dashboardFormLabel);

		this.add(dahboardPanel);
		dahboardPanel.add(gruppenButton);
		dahboardPanel.add(umfragenButton);
		dahboardPanel.add(ergebnisseButton);

	}

	private class UmfragenAnzeigenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			RootPanel.get("details").add(uaf);

		}

	}

	private class GruppenAnzeigenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			RootPanel.get("details").add(gaf);

		}

	}

	private class ErgebnisseAnzeigenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			RootPanel.get("details").add(eaf);
		}

	}

}
