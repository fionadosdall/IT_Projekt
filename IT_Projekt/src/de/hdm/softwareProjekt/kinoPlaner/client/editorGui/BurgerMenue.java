package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BurgerMenue extends FlowPanel {
	Image menue = new Image();
	VerticalPanel menueDropdown = new VerticalPanel();
	VerticalPanel fl = new VerticalPanel();
	FlowPanel detailsOben = new FlowPanel();
	VerticalPanel dropdown = new  VerticalPanel();

	@Override
	protected void onLoad() {
		super.onLoad();
		this.setStyleName("headerRechtsElement");
		detailsOben.setStyleName("");
		fl.setStyleName("headerRechtsElement");
		menue.setStyleName("löschenImage");
		menue.setUrl("/images/burger-menu.png");
		fl.add(menue);
		menue.addClickHandler(new BurgerMenueClickHandler());
		this.add(fl);
		dropdown.setStyleName("dropdownGesamt");
		RootPanel.get("dropdown").add(dropdown);

	}

	private class BurgerMenueClickHandler implements ClickHandler {
		private int clickCounter = 0;

		@Override
		public void onClick(ClickEvent event) {
			if (clickCounter == 0) {
				menueDropdown.setStyleName("menueBar");
				Button buttonErstellenGruppe = new Button("Gruppe erstellen");
				Button buttonErstellenUmfrage = new Button("Umfrage erstellen");
				buttonErstellenGruppe.setStyleName("dropdownButton");
				buttonErstellenUmfrage.setStyleName("dropdownButton");
				buttonErstellenGruppe.addClickHandler(new GruppeErstellenHandler());
				buttonErstellenUmfrage.addClickHandler(new UmfrageErstellenHandler());
				menueDropdown.add(buttonErstellenGruppe);
				menueDropdown.add(buttonErstellenUmfrage);
				dropdown.add(menueDropdown);
				clickCounter++;
			}	else if (clickCounter % 2 == 0) {
				
				dropdown.add(menueDropdown);
				clickCounter++;
					
				
				
				
			} else {
				dropdown.clear();
				clickCounter++;
			}

		}

	}

	private class GruppeErstellenHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			GruppeErstellenForm erstellen = new GruppeErstellenForm();
			RootPanel.get("details").add(erstellen);

		}

	}

	private class UmfrageErstellenHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			UmfrageErstellenForm erstellen = new UmfrageErstellenForm();
			RootPanel.get("details").add(erstellen);

		}

	}

}
