package de.hdm.gruppe3.stundenplanverwaltung.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungServiceAsync;
import de.hdm.gruppe3.stundenplanverwaltung.client.gui.DozentForm;
import de.hdm.gruppe3.stundenplanverwaltung.client.gui.DozentTabelle;
import de.hdm.gruppe3.stundenplanverwaltung.client.gui.DozentTabelleTest;
import de.hdm.gruppe3.stundenplanverwaltung.client.gui.LehrveranstaltungForm;
import de.hdm.gruppe3.stundenplanverwaltung.client.gui.RaumForm;
import de.hdm.gruppe3.stundenplanverwaltung.client.gui.SemesterverbandForm;
import de.hdm.gruppe3.stundenplanverwaltung.client.testGui.TestGUIForms;

/**
 * 
 * @author Denis Fürst, Daniel Krakow
 * 
 * 
 */

public class StundenplanVerwaltungTest implements EntryPoint {
	
	private VerticalPanel mainPanel = new VerticalPanel();
	VerticalPanel vertpan = new VerticalPanel();
	HorizontalPanel hor1 = new HorizontalPanel();
	HorizontalPanel left = new HorizontalPanel();
	VerticalPanel right = new VerticalPanel();
	HorizontalPanel infoPanel = new HorizontalPanel();
	FlexTable testFlexTable = new FlexTable();
	TextBox VornameTextBox = new TextBox();
	TextBox NachnameTextBox = new TextBox();
	Button addStockButton = new Button("Add");
	Label lastUpdatedLabel = new Label();
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	private final StundenplanVerwaltungServiceAsync stdplnVerwService = GWT.create(StundenplanVerwaltungService.class);

	TestGUIForms testguiforms = new TestGUIForms();

	/**
	 * 
	 * This is the entry point method.
	 */

	public void onModuleLoad() {

		// Commands hinzugefügt zum Anlegen
		Command cmdDozent = new Command() {

			public void execute() {

				right.clear();

				right.add(new HTML("<h3>" + "TEST AFTER CLICK" + "</h3>"));

				DozentForm doz = new DozentForm();

				right.add(doz);

			}

		};

		Command cmdRaum = new Command() {

			public void execute() {

				right.clear();

				right.add(new HTML("<h3>" + "TEST AFTER CLICK" + "</h3>"));

				RaumForm raum = new RaumForm();

				right.add(raum);

			}

		};

		Command cmdLehrveranstaltung = new Command() {

			public void execute() {

				right.clear();

				right.add(new HTML("<h3>" + "TEST AFTER CLICK" + "</h3>"));

				LehrveranstaltungForm lehrver = new LehrveranstaltungForm();

				right.add(lehrver);

			}

		};

		Command cmdSemester = new Command() {

			public void execute() {

				right.clear();

				right.add(new HTML("<h3>" + "TEST AFTER CLICK" + "</h3>"));

				SemesterverbandForm semverb = new SemesterverbandForm();

				right.add(semverb);

			}

		};

		Command cmdVorlesung = new Command() {

			public void execute() {

				right.clear();

				right.add(new HTML("<h3>" + "TEST AFTER CLICK" + "</h3>"));

				LehrveranstaltungForm vorl = new LehrveranstaltungForm();

				right.add(vorl);

			}

		};
		
		//Commands zum Anzeigen der bestehenden 
		
		Command cmdDozentTabelle = new Command() {

			public void execute() {

				right.clear();

				right.add(new HTML("<h3>" + "TEST AFTER CLICK" + "</h3>"));

				DozentTabelleTest dozTest = new DozentTabelleTest();

				right.add(dozTest);

			}

		};

		
		
		
		

		mainPanel.add(vertpan);

		mainPanel.add(hor1);

		MenuBar dozMenu = new MenuBar(true);

		MenuBar raumMenu = new MenuBar(true);

		MenuBar lvMenu = new MenuBar(true);

		MenuBar zeitMenu = new MenuBar(true);

		MenuBar semMenu = new MenuBar(true);

		MenuBar vorlesMenu = new MenuBar(true);

		MenuBar repoMenu = new MenuBar(true);

		dozMenu.addItem("Dozent anzeigen", cmdDozentTabelle);

		dozMenu.addItem("Dozent anlegen", cmdDozent);

		raumMenu.addItem("Raum anzeigen", cmdRaum);

		raumMenu.addItem("Raum anlegen", cmdRaum);

		lvMenu.addItem("Lehrveranstaltung anzeigen", cmdLehrveranstaltung);

		lvMenu.addItem("Lehrveranstaltung anlegen", cmdLehrveranstaltung);

		semMenu.addItem("Semesterverband anzeigen", cmdSemester);

		semMenu.addItem("Semesterverband anlegen", cmdSemester);

		vorlesMenu.addItem("Vorlesung anzeigen", cmdVorlesung);

		vorlesMenu.addItem("Vorlesung anlegen", cmdVorlesung);

		repoMenu.addItem("Report anzeigen", cmdVorlesung);

		repoMenu.addItem("Report anlegen", cmdVorlesung);

		MenuBar menu = new MenuBar();

		menu.addItem("Dozent", dozMenu);

		menu.addItem("Raum", raumMenu);

		menu.addItem("Lehrveranstaltung", lvMenu);

		menu.addItem("Zeitslot", zeitMenu);

		menu.addItem("Semesterverband", semMenu);

		menu.addItem("Vorlesung", vorlesMenu);

		menu.addItem("Report", repoMenu);

		vertpan.add(new HTML(

				"<img src=\"/Users/df/Documents/workspace/schedule.png\" alt=\"schedule\"><h1>TESTTESTTEST</h1>"));

		hor1.add(left);

		left.add(new HTML("<h2>TreePanel</h2>"));

		hor1.add(right);

		right.add(new HTML("<h2>DetailPanel</h2>"));

		hor1.setSpacing(5);

		vertpan.add(menu);

		RootPanel.get("starter").add(mainPanel);

	}

}