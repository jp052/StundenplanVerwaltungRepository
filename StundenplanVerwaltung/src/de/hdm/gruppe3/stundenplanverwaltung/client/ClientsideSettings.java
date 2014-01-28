/* 
 * ClientsideSettings.java 
 * 
 * Version: 
 *     1.0
 * 
 * Revisions: 
 *     1.0
 */

package de.hdm.gruppe3.stundenplanverwaltung.client;

import java.util.logging.Logger;

/**
 * Beinhaltet verschiedene Einstellungen die auf der Client Seite ben�tigt werden.
 * @author Jan Plank
 *
 */
public class ClientsideSettings {
	

	  private static final Logger logger = Logger.getLogger("StundenplanVerwaltung Web Client");

//	  private static ReportGeneratorAsync reportGenerator = null;
	  
	  public static Logger getLogger() {
		    return logger;
		  }
	  
}
