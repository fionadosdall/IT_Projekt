package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

	import com.google.gwt.event.dom.client.ClickEvent;
	import com.google.gwt.event.dom.client.ClickHandler;
	import com.google.gwt.user.client.ui.Button;
	import com.google.gwt.user.client.ui.FlowPanel;
	import com.google.gwt.user.client.ui.Grid;
	import com.google.gwt.user.client.ui.Image;
	import com.google.gwt.user.client.ui.RootPanel;

	public class BurgerMenue extends FlowPanel {
		Image menue = new Image();
		Grid menueDropdown = new Grid(2, 1);
		FlowPanel fl = new FlowPanel();
		

		@Override
		protected void onLoad() {
			super.onLoad();
			this.setStyleName("headerRechtsElement");
			fl.setStyleName("headerRechtsElement");
			menue.setStyleName("suchenImage");
			fl.add(menue);
			menue.addClickHandler(new BurgerMenueClickHandler());
			this.add(fl);

		}

		private class BurgerMenueClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				Button buttonErstellenGruppe = new Button("Gruppe erstellen");
				Button buttonErstellenUmfrage = new Button("Umfrage erstellen");
				buttonErstellenGruppe.setStyleName("userButton");
				buttonErstellenGruppe.setStyleName("userButton");
				buttonErstellenGruppe.addClickHandler(new GruppeErstellenHandler());
				buttonErstellenUmfrage.addClickHandler(new UmfrageErstellenHandler());
				menueDropdown.setWidget(0, 0, buttonErstellenGruppe);
				menueDropdown.setWidget(1, 0, buttonErstellenUmfrage);
				fl.add(menueDropdown);

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



