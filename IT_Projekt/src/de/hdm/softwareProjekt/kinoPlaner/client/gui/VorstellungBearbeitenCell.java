package de.hdm.softwareProjekt.kinoPlaner.client.gui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Vorstellung;

public class VorstellungBearbeitenCell extends AbstractCell<Vorstellung> {

	interface Template extends SafeHtmlTemplates {
		@Template("<img src=\"{0}\"/>")
		SafeHtml img(String url);
	}
	
	private SpielplanVorstellungenCellTable vct;

	private static Template template;

	/**
	 * Construct a new ImageCell.
	 */
	public VorstellungBearbeitenCell(Vorstellung vorstellung, SpielplanVorstellungenCellTable vct) {
		super("click");
		
		this.vct=vct;
		
		if (template == null) {
			template = GWT.create(Template.class);
		}
		
	}

	@Override
	public void render(Context context, Vorstellung value, SafeHtmlBuilder sb) {
		if (value != null) {
			// The template will sanitize the URI.
			sb.append(template.img("/images/bearbeiten.png"));
		}
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
		SpielplaneintragForm neuerSpielplaneintrag = new SpielplaneintragForm(vct, value);
		neuerSpielplaneintrag.show();
	
	}

}
