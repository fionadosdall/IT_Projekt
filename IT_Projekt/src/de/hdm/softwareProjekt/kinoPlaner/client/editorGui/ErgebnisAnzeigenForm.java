package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrageoption;

public class ErgebnisAnzeigenForm extends FlowPanel {

	private Umfrage umfrage;
	private Umfrage umfrageStichwahl;
	private KinoplanerAsync kinoplaner;

	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();
	private FlowPanel detailsboxInhalt = new FlowPanel();

	private Label title = new Label();

	private TextArea txt = new TextArea();

	private ErgebnisAnzeigenTable eat;

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
		this.add(detailsboxInhalt);
		this.add(detailsunten);

		detailsoben.add(title);

		eat = new ErgebnisAnzeigenTable(umfrage);
		eat.setErgebnisAnzeigenForm(this);
		title.setText("Umfrage: " + umfrage.getName());

		detailsboxInhalt.add(eat);

		detailsunten.add(txt);

		kinoplaner.ergebnisGefunden(umfrage, new ErgebnisGefundenCallback());

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
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(ArrayList<Umfrage> result) {
			
			StringBuffer buffi = new StringBuffer();
			buffi.append("Für deine Umfrage ");
			buffi.append(umfrage.getName());

			buffi.append(
					" konnte kein Gewinner ermittelt werden! \n Wir haben eine Stichwahl für deine Gruppe gestaret! ");
			txt.setText(buffi.toString());
			Button stichwahl = new Button("Stichwahl");
			stichwahl.addClickHandler(new StichwahlClickHandler());
			detailsunten.add(stichwahl);

			String text = buffi.toString();
			txt.setText(text);
			txt.setWidth("99%");
			
			for (Umfrage u : result) {
				if (u.isOpen() == true) {
					umfrageStichwahl = u;
				}
			}

		}

	}

	private class ErgebnisGefundenCallback implements AsyncCallback<Boolean> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
			caught.printStackTrace();

		}

		@Override
		public void onSuccess(Boolean result) {

			if (result == true) {

				eat.gewinnerErmitteln();

			} else {

				kinoplaner.volltextSucheUmfragen("Stichwahl " + umfrage.getName(), new VolltextSucheUmfragenCallback());

			}

		}

	}

	public void setGewinner(String zeit, String film, String kino, String stadt) {

		StringBuffer buffi = new StringBuffer();
		buffi.append("Für deine Umfrage ");
		buffi.append(umfrage.getName());
		buffi.append(" konnte ein Gewinner ermittelt werden!\nIhr geht am ");

		buffi.append(zeit);
		buffi.append(" in den Film " + film + " im Kino " + kino + ". Das Kino liegt in " + stadt + ".");
		buffi.append("\nViel Spaß!");
		txt.setText(buffi.toString());

		String text = buffi.toString();
		txt.setText(text);
		txt.setWidth("99%");
		txt.setHeight("100%");
	}

}
