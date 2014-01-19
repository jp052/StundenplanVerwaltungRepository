package de.hdm.gruppe3.stundenplanverwaltung.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.gruppe3.stundenplanverwaltung.client.ClientsideSettings;
import de.hdm.gruppe3.stundenplanverwaltung.shared.ReportGenerator;
import de.hdm.gruppe3.stundenplanverwaltung.shared.ReportGeneratorAsync;

/**
 * Beinhaltet verschiedene Einstellungen die auf der Client Seite ben�tigt werden.
 * @author Jan Plank
 *
 */
public class ClientsideSettings {
	

	  private static final Logger logger = Logger.getLogger("StundenplanVerwaltung Web Client");

	  private static ReportGeneratorAsync reportGenerator = null;
	  
	  public static Logger getLogger() {
		    return logger;
		  }
	  
	  public static ReportGeneratorAsync getReportGenerator() {
		    // Gab es bislang noch keine ReportGenerator-Instanz, dann...
		    if (reportGenerator == null) {
		      // Zunächst instantiieren wir ReportGenerator
		      reportGenerator = GWT.create(ReportGenerator.class);

		      final AsyncCallback<Void> initReportGeneratorCallback = new AsyncCallback<Void>() {
		        public void onFailure(Throwable caught) {
		          ClientsideSettings.getLogger().severe(
		              "Der ReportGenerator konnte nicht initialisiert werden!");
		        }

		        public void onSuccess(Void result) {
		          ClientsideSettings.getLogger().info(
		              "Der ReportGenerator wurde initialisiert.");
		        }
		      };

		      reportGenerator.init(initReportGeneratorCallback);
		    }

		    // So, nun brauchen wir den ReportGenerator nur noch zurückzugeben.
		    return reportGenerator;
		  }
}
