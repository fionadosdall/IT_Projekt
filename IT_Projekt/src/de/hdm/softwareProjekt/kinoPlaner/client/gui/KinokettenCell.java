package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kino;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Kinokette;

public class KinokettenCell extends AbstractCell<Kinokette>{
	
	private static Boolean edit = true;


	@Override
	public void render(Context context, Kinokette value, SafeHtmlBuilder sb) {
		if (value == null) {
			return;
		}
		
		sb.appendHtmlConstant("<div>");
		sb.appendEscaped(value.getName());
		sb.appendHtmlConstant("</div>");
		
	}
	
	   public KinokettenCell() {

	        super("click");
	      }


	      @Override
	      public void onBrowserEvent(Context context, Element parent, Kinokette value, NativeEvent event,
	          ValueUpdater<Kinokette> valueUpdater) {
	        // Handle the click event.
	        if ("click".equals(event.getType())) {
	          // Ignore clicks that occur outside of the outermost element.
	          EventTarget eventTarget = event.getEventTarget();
	          if (parent.getFirstChildElement().isOrHasChild(Element.as(eventTarget))) {
	            doAction(value, valueUpdater);
	          }
	        }
	      }
	      
	      private void doAction(Kinokette value, ValueUpdater<Kinokette> valueUpdater) {
				RootPanel.get("details").clear();
				/*KinoketteErstellenForm anzeigen = new KinoketteErstellenForm(value);
				RootPanel.get("details").add(anzeigen);*/
	    	  
	    	  	//KinoketteErstellenForm.setEdit(edit);
	    	  	KinoketteErstellenForm bearbeiten = new KinoketteErstellenForm(value);
				//KinoketteErstellenForm.setBearbeiten(value);
				RootPanel.get("details").add(bearbeiten);
	      }
	
	

}
