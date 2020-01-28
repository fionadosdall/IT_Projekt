package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;


/**
 * Klasse SpielplaneintragCell dient zur Darstellung der Spielplaneintrag-BusinessObjekte in der
 * BusinessObject-View. (--> rendern). Alle Klassen, die auf ...Cell enden, sind
 * abgewandeltete/veränderte AbstractCells.
 *
 */
public class SpielplanEintragCell extends AbstractCell<Vorstellung> {

	

	@Override
	public void render(Context context, Vorstellung value, SafeHtmlBuilder sb) {
		
		if (value == null) {
			return;
		}
		
		sb.appendHtmlConstant("<div>");
		sb.appendEscaped(value.getName());
		sb.appendHtmlConstant("</div>");
	}
	
	
	
	public SpielplanEintragCell() {
		super("click");
	}
	
	
	@Override
	  public void onBrowserEvent(Context context, Element parent, Vorstellung value, NativeEvent event,
	      ValueUpdater<Vorstellung> valueUpdater) {
		// Handle the click event.
	    if ("click".equals(event.getType())) {
	      // Ignore clicks that occur outside of the outermost element.
	      EventTarget eventTarget = event.getEventTarget();
	      if (parent.getFirstChildElement().isOrHasChild(Element.as(eventTarget))) {
	        doAction(value, valueUpdater);
	      }
	    }
	
	}
	
	private void doAction(Vorstellung value, ValueUpdater<Vorstellung> valueUpdater) {
		RootPanel.get("details").clear();
		//SpielplaneintragForm anzeigen = new SpielplaneintragForm(value);
		//RootPanel.get("details").add(anzeigen);
  }

}
