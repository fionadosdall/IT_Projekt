package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwareProjekt.kinoPlaner.client.editorGui.UmfrageAnzeigenForm;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Umfrage;

public class KinoCell extends AbstractCell<Kino>{

	@Override
	public void render(Context context, Kino value, SafeHtmlBuilder sb) {
		if (value == null) {
			return;
		}
		
		sb.appendHtmlConstant("<div>");
		sb.appendEscaped(value.getName());
		sb.appendHtmlConstant("</div>");
		
	}
	
	   public KinoCell() {

	        super("click");
	      }


	      @Override
	      public void onBrowserEvent(Context context, Element parent, Kino value, NativeEvent event,
	          ValueUpdater<Kino> valueUpdater) {
	        // Handle the click event.
	        if ("click".equals(event.getType())) {
	          // Ignore clicks that occur outside of the outermost element.
	          EventTarget eventTarget = event.getEventTarget();
	          if (parent.getFirstChildElement().isOrHasChild(Element.as(eventTarget))) {
	            doAction(value, valueUpdater);
	          }
	        }
	      }
	      
	      private void doAction(Kino value, ValueUpdater<Kino> valueUpdater) {
				RootPanel.get("details").clear();
				KinoBearbeitenForm anzeigen = new KinoBearbeitenForm(value);
				RootPanel.get("details").add(anzeigen);
	      }
	
	

}
