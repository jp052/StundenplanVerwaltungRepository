package de.hdm.gruppe3.stundenplanverwaltung.client;

import de.hdm.gruppe3.stundenplanverwaltung.client.gui.LehrveranstaltungForm;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungServiceAsync;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class StundenplanVerwaltung implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	private final StundenplanVerwaltungServiceAsync stdplnVerwService = GWT
			.create(StundenplanVerwaltungService.class);

	private Button sendButton = new Button("Send");
	private TextBox nameField = new TextBox();
	private TextBox output = new TextBox();

	public void onModuleLoad() {
		// Test1 funktioniert nicht, da Forms und Tree verwsendet werden m�ssen
		// TestGUI gui = new TestGUI();
		// gui.testFelder();

		LehrveranstaltungForm lForm = new LehrveranstaltungForm();		
		StundenplanVerwaltungFrontend copy = new StundenplanVerwaltungFrontend();		
		copy.showMenue();
		
//		LehrveranstaltungTabelle t = new LehrveranstaltungTabelle();
//		RaumTabelle rt = new RaumTabelle();
		
//		rt.zeigeTabelle();

//		t.zeigeTabelle();
//		guiForms.formsAnzeigen();
		//guiForms.zeigeTabelle();

	}
}
