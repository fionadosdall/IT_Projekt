package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;

public class ErgebnisCell extends AbstractCell<Umfrage> {
	
	SafeHtmlBuilder shb;

	@Override
	public void render(Context context, Umfrage value, SafeHtmlBuilder sb) {
		if (value == null) {
			return;
		}
		
		shb = sb;
		
		KinoplanerAsync kinoplaner = ClientsideSettings.getKinoplaner();

		shb.appendHtmlConstant("<div>");
		shb.appendHtmlConstant("Umfrage: ");
		shb.appendEscaped(value.getName());
		
		kinoplaner.getGruppeById(value.getGruppenId(), new AsyncCallback<Gruppe>() {

			@Override
			public void onSuccess(Gruppe result) {
				shb.appendHtmlConstant(" Gruppe: ");
				shb.appendEscaped(result.getName());
				shb.appendHtmlConstant("</div>");
				
				
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});

	}

	public ErgebnisCell() {

		super("click");
	}

	@Override
	public void onBrowserEvent(Context context, Element parent, Umfrage value, NativeEvent event,
			ValueUpdater<Umfrage> valueUpdater) {
		// Handle the click event.
		if ("click".equals(event.getType())) {
			// Ignore clicks that occur outside of the outermost element.
			EventTarget eventTarget = event.getEventTarget();
			if (parent.getFirstChildElement().isOrHasChild(Element.as(eventTarget))) {
				doAction(value, valueUpdater);
			}
		}
	}

	private void doAction(Umfrage value, ValueUpdater<Umfrage> valueUpdater) {
		RootPanel.get("details").clear();
		ErgebnisAnzeigenForm anzeigen = new ErgebnisAnzeigenForm(value);
		RootPanel.get("details").add(anzeigen);
	}
}
