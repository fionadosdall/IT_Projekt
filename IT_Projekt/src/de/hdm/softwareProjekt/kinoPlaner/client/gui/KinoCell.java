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


/*
 * Klasse KinoCell dient zur Darstellung der KIiono-BO's
 *in der  BO-View (--> render) alle Klassen die auf .....Celll enden;
 *sind abgewandelte/ veränderte Abstract Cells. 
 */
public class KinoCell extends AbstractCell<Kino>{
	
	private static Boolean edit = true;


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

/*
 * Methode wenn Ereignis im Browser passiert
 * (non-Javadoc)
 * @see com.google.gwt.cell.client.AbstractCell#onBrowserEvent(com.google.gwt.cell.client.Cell.Context, com.google.gwt.dom.client.Element, java.lang.Object, com.google.gwt.dom.client.NativeEvent, com.google.gwt.cell.client.ValueUpdater)
 */
	      @Override
	      public void onBrowserEvent(Context context, Element parent, Kino value, NativeEvent event,
	          ValueUpdater<Kino> valueUpdater) {
	   
	        if ("click".equals(event.getType())) {
	    
	          EventTarget eventTarget = event.getEventTarget();
	          if (parent.getFirstChildElement().isOrHasChild(Element.as(eventTarget))) {
	            doAction(value, valueUpdater);
	          }
	        }
	      }
	      
	      private void doAction(Kino value, ValueUpdater<Kino> valueUpdater) {
				RootPanel.get("details").clear();
				KinoErstellenForm bearbeiten = new KinoErstellenForm(value);
				RootPanel.get("details").add(bearbeiten);
	      }
	
	

}
