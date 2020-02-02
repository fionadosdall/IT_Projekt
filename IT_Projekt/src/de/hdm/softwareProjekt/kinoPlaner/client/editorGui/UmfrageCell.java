package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.ClientsideSettings;
import de.hdm.softwareProjekt.kinoPlaner.shared.KinoplanerAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;


/***
 * Klasse zur Darstellung von Umfrage-Objekten für den Navigator.
 * Alle Klassen die auf -Cell enden sind abgewandelte/ veränderte Abstact Cells
 * 
 *
 */
public class UmfrageCell extends AbstractCell<Umfrage> {


	@Override
	public void render(Context context, Umfrage value, SafeHtmlBuilder sb) {
		if (value == null) {
			return;
		}
		sb.appendHtmlConstant("<div>");
		sb.appendEscaped(value.getName());
		sb.appendHtmlConstant("</div>");

	}

	public UmfrageCell() {

		super("click");


	}
	/*****
	 * Methoden wenn Ereignis im Broswer passiert
	 */

	@Override
	public void onBrowserEvent(Context context, Element parent, Umfrage value, NativeEvent event,
			ValueUpdater<Umfrage> valueUpdater) {
	
		if ("click".equals(event.getType())) {
		
			EventTarget eventTarget = event.getEventTarget();
			if (parent.getFirstChildElement().isOrHasChild(Element.as(eventTarget))) {
				doAction(value, valueUpdater);
			}
		}
	}

	private void doAction(Umfrage value, ValueUpdater<Umfrage> valueUpdater) {
		RootPanel.get("details").clear();
		UmfrageAnzeigenForm anzeigen = new UmfrageAnzeigenForm(value);
		RootPanel.get("details").add(anzeigen);
	}
}
