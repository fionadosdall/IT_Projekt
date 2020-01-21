package de.hdm.softwareProjekt.kinoPlaner.client.editorGui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Gruppe;

/**
 * Klasse GruppeCell dient zur Darstellung der Gruppen-BusinessObjekte in der
 * BusinessObject-View. (--> rendern). Alle Klassen, die auf ...Cell enden, sind
 * abgewandeltete/veränderte AbstractCells.
 *
 */
public class GruppeCell extends AbstractCell<Gruppe> {

	@Override
	public void render(Context context, Gruppe value, SafeHtmlBuilder sb) {
		if (value == null) {
			return;
		}

		sb.appendHtmlConstant("<div>");
		sb.appendEscaped(value.getName());
		sb.appendHtmlConstant("</div>");

	}

	public GruppeCell() {

		super("click");
	}

	/**
	 * Methode wenn Ereignis im Browser passiert
	 * 
	 */
	@Override
	public void onBrowserEvent(Context context, Element parent, Gruppe value, NativeEvent event,
			ValueUpdater<Gruppe> valueUpdater) {
		// Handle the click event.
		if ("click".equals(event.getType())) {
			// Ignore clicks that occur outside of the outermost element.
			EventTarget eventTarget = event.getEventTarget();
			if (parent.getFirstChildElement().isOrHasChild(Element.as(eventTarget))) {
				doAction(value, valueUpdater);
			}
		}
	}

	private void doAction(Gruppe value, ValueUpdater<Gruppe> valueUpdater) {
		RootPanel.get("details").clear();
		GruppeAnzeigenForm anzeigen = new GruppeAnzeigenForm(value);
		RootPanel.get("details").add(anzeigen);
	}

}
