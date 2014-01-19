package de.hdm.gruppe3.stundenplanverwaltung.client.gui;

import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * Enthält verschiedene Popup Message Zentral zur anzeige von Fehlern und Informationen für den Benutzer.
 * @author Yasemin Karakoc, Jan Plank
 *
 */
public class UserInformation {
	
	public static void popup(String message) {
		PopupPanel popupFehler = new DecoratedPopupPanel(true);				 
	    popupFehler.setWidth("300px");
	    popupFehler.setWidget(new HTML("<p>" + message + "</p>"));
	    popupFehler.center();
	    popupFehler.show();
	}
}
