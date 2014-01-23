package de.hdm.gruppe3.stundenplanverwaltung.client;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class StundenplanVerwaltung implements EntryPoint {

	/**
	 * Started das Frontend
	 */
	public void onModuleLoad() {
		//Ruft die Navigation auf.
		StundenplanVerwaltungFrontend copy = new StundenplanVerwaltungFrontend();		
		copy.showMenue();
		
	}
}
