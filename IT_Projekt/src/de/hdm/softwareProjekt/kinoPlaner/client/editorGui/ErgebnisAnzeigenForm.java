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
	private Umfrageoption umfrageoption;
	private boolean ergebnis;

	private HomeBar hb = new HomeBar();

	private FlowPanel detailsoben = new FlowPanel();
	private FlowPanel detailsunten = new FlowPanel();
	private FlowPanel detailsboxInhalt = new FlowPanel();

	private Label title = new Label("Umfrage: " + umfrage.getName());

	private TextArea txt = new TextArea();
	
	private ErgebnisAnzeigenTable eat;
	
	String zeit = eat.getErgebnisInfo().getSpielzeit();
	String film = eat.getErgebnisInfo().getFilmName();
	String kino = eat.getErgebnisInfo().getKinoName();
	String stadt = eat.getErgebnisInfo().getStadt();

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
		
		eat = new ErgebnisAnzeigenTable(umfrage);
		
		detailsunten.add(eat);
		
		StringBuffer buffi = new StringBuffer();
		kinoplaner.ergebnisGefunden(umfrage, new ErgebnisGefundenCallback());
		buffi.append("Für deine Umfrage ");
		buffi.append(umfrage.getName());
		
		if (ergebnis == true) {
			buffi.append(" konnte ein Gewinner ermittelt werden!\nIhr geht am ");
			kinoplaner.umfrageGewinnerErmitteln(umfrage, new UmfrageGewinnerErmittelnCallback());
			
			buffi.append(zeit);
			buffi.append(" in den Film " + film + " im Kino " + kino + ". Das Kino liegt in "
					+ stadt + ".");
			buffi.append("\n Viel Spaß!");
			txt.setText(buffi.toString());
		} else {
			buffi.append(
					" konnte kein Gewinner ermittelt werden! \n Wir haben eine Stichwahl für deine Gruppe gestaret! ");
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



}
