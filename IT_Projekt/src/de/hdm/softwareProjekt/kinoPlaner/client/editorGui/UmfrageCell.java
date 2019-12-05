package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;

public class UmfrageCell extends AbstractCell<Umfrage> {
	Gruppe gruppe;

	@Override
	public void render(Context context, Umfrage value, SafeHtmlBuilder sb) {
		if (value == null) {
			return;
		}
		KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();
		sb.appendHtmlConstant("<div>");
		sb.appendHtmlConstant("Umfrage: ");
		sb.appendEscaped(value.getName());
		sb.appendHtmlConstant(" Gruppe: ");
		kinoplaner.getGruppeById(value.getGruppenId(), new GruppeByIdCallback());
		sb.appendEscaped(gruppe.getName());			
		sb.appendHtmlConstant("</div>");
	}
	
	private class GruppeByIdCallback implements AsyncCallback<Gruppe> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Gruppe result) {
			gruppe = result;
			
		}
		
	}
}
