package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Spielplan;

/**
 * Klasse SpielplanCell dient zur Darstellung der Spielplan-BusinessObjekte in der
 * BusinessObject-View. (--> rendern). Alle Klassen, die auf ...Cell enden, sind
 * abgewandeltete/veränderte AbstractCells.
 *
 */
public class SpielplanCell extends AbstractCell<Spielplan> 	{


@Override
public void render(Context context, Spielplan value, SafeHtmlBuilder sb) {
	if (value == null) {
		return;
	}
	
	sb.appendHtmlConstant("<div>");
	sb.appendEscaped(value.getName());
	sb.appendHtmlConstant("</div>");
	
}
public SpielplanCell() {

    super("click");
  }


  @Override
  public void onBrowserEvent(Context context, Element parent, Spielplan value, NativeEvent event,
      ValueUpdater<Spielplan> valueUpdater) {

    if ("click".equals(event.getType())) {
     
      EventTarget eventTarget = event.getEventTarget();
      if (parent.getFirstChildElement().isOrHasChild(Element.as(eventTarget))) {
        doAction(value, valueUpdater);
      }
    }
  }
  
  private void doAction(Spielplan value, ValueUpdater<Spielplan> valueUpdater) {
		RootPanel.get("details").clear();
		SpielplanErstellenForm anzeigen = new SpielplanErstellenForm(value);
		RootPanel.get("details").add(anzeigen);
  }

}
