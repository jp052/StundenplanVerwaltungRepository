package de.hdm.gruppe3.stundenplanverwaltung.client;

import com.google.gwt.core.client.GWT;
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

import de.hdm.gruppe3.stundenplanverwaltung.server.db.DurchfuehrungMapper;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungServiceAsync;
import de.hdm.gruppe3.stundenplanverwaltung.client.gui.DozentForm;
import de.hdm.gruppe3.stundenplanverwaltung.client.gui.DozentTabelle;
import de.hdm.gruppe3.stundenplanverwaltung.client.gui.LVDurchfuehrungForm;
import de.hdm.gruppe3.stundenplanverwaltung.client.gui.LVDurchfuehrungTabelle;
import de.hdm.gruppe3.stundenplanverwaltung.client.gui.LehrveranstaltungForm;
import de.hdm.gruppe3.stundenplanverwaltung.client.gui.LehrveranstaltungTabelle;
import de.hdm.gruppe3.stundenplanverwaltung.client.gui.RaumForm;
import de.hdm.gruppe3.stundenplanverwaltung.client.gui.RaumTabelle;
import de.hdm.gruppe3.stundenplanverwaltung.client.gui.SemesterverbandForm;
import de.hdm.gruppe3.stundenplanverwaltung.client.gui.SemesterverbandTabelle;
import de.hdm.gruppe3.stundenplanverwaltung.client.report.DozentReport;
import de.hdm.gruppe3.stundenplanverwaltung.client.report.Raumbelegung;
import de.hdm.gruppe3.stundenplanverwaltung.client.report.SVReport;

/**
 * Enthält das Menü und dazugehörige Methoden.
 * @author Denis Fürst, Daniel Krakow
 * 
 * 
 */

public class StundenplanVerwaltungFrontend{
	
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
	private final StundenplanVerwaltungServiceAsync stdplnVerwService = GWT.create(StundenplanVerwaltungService.class);
	

	/**
	 * 
	 * Zeigt das gesammte Menü an.
	 */

	public void showMenue() {

		// Commands hinzugefügt zum Anlegen
		Command createDozent = new Command() {
			public void execute() {
				right.clear();
				right.add(new HTML("<h3>" + "Anlegen eines Dozenten" + "</h3>"));
				DozentForm doz = new DozentForm();
				right.add(doz);
			}
		};

		Command createRaum = new Command() {
			public void execute() {
				right.clear();
				right.add(new HTML("<h3>" + "Anlegen eines Raums" + "</h3>"));
				RaumForm raum = new RaumForm();
				right.add(raum);
			}
		};

		Command createLehrveranstaltung = new Command() {
			public void execute() {
				right.clear();
				right.add(new HTML("<h3>" + "Anlegen einer Lehrveranstaltung" + "</h3>"));
				LehrveranstaltungForm lehrver = new LehrveranstaltungForm();
				right.add(lehrver);
			}
		};

		Command createSemesterverband = new Command() {
			public void execute() {
				right.clear();
				right.add(new HTML("<h3>" + "Anlegen eines Semesterverbands" + "</h3>"));
				SemesterverbandForm semverb = new SemesterverbandForm();
				right.add(semverb);
			}
		};

		Command createDurchfuerhung = new Command() {
			public void execute() {
				right.clear();
				right.add(new HTML("<h3>" + "Anlegen einer Durchführung" + "</h3>"));
				LVDurchfuehrungForm lvdForm = new LVDurchfuehrungForm();
				right.add(lvdForm);
			}
		};
		
		
		//////COMMANDS ZUM ANLEGEN DES REPORTS
		
		
		
		//Commands zum Anzeigen der bestehenden 		
		Command showDozent = new Command() {
			public void execute() {
				right.clear();
				right.add(new HTML("<h3>" + "Dozentenliste" + "</h3>"));
				DozentTabelle dozTabelle = new DozentTabelle();
				right.add(dozTabelle.zeigeTabelle());
			}
		};
		
		Command showRaum = new Command() {
			public void execute() {
				right.clear();
				right.add(new HTML("<h3>" + "Raumliste" + "</h3>"));
				RaumTabelle raumTabelle = new RaumTabelle();
				right.add(raumTabelle.zeigeTabelle());
			}
		};		
		
		Command showLehrveranstaltung = new Command() {
			public void execute() {
				right.clear();
				right.add(new HTML("<h3>" + "Liste der Lehrveranstaltungen" + "</h3>"));
				LehrveranstaltungTabelle lehrTabelle = new LehrveranstaltungTabelle();
				right.add(lehrTabelle.zeigeTabelle());
			}
		};
		
		
		//Semeserverband TABELLE
		Command showSemesterverband = new Command() {
			public void execute() {
				right.clear();
				right.add(new HTML("<h3>" + "Liste der Semesterverbände" + "</h3>"));
				SemesterverbandTabelle svTabelle = new SemesterverbandTabelle();
				right.add(svTabelle.zeigeTabelle());
			}
		};		
		
		//Durchführungstabelle
		Command showDurchfuehrung = new Command() {
			public void execute() {
				right.clear();
				right.add(new HTML("<h3>" + "Liste der Durchführungen" + "</h3>"));
				LVDurchfuehrungTabelle lvdTAbelle = new LVDurchfuehrungTabelle();
				right.add(lvdTAbelle.zeigeTabelle());
			}
		};		
		
		//Command für den Raumbelegung
		Command showRaumReport = new Command() {
			public void execute() {
				right.clear();
				right.add(new HTML("<h3>" + "Raumbelegung" + "</h3>"));
				Raumbelegung raumReport = new Raumbelegung();
				right.add(raumReport.reportRaumbelegung());
			}
		};	
		
		//Command für den Dozent Report
		Command showDozentReport = new Command() {
			public void execute() {
				right.clear();
				right.add(new HTML("<h3>" + "Dozent Report" + "</h3>"));
				DozentReport dReport = new DozentReport();
				right.add(dReport.reportDozent());
			}
		};	
		
		Command showSemesterverbandReport = new Command() {
			public void execute() {
				right.clear();
				right.add(new HTML("<h3>" + "Semesterverband Report" + "</h3>"));
				SVReport svReport = new SVReport();
				right.add(svReport.reportSemesterverband());
			}
		};

		mainPanel.add(vertpan);
		mainPanel.add(hor1);
		MenuBar dozMenu = new MenuBar(true);
		MenuBar raumMenu = new MenuBar(true);
		MenuBar lvMenu = new MenuBar(true);
		MenuBar semMenu = new MenuBar(true);
		MenuBar durchfuer = new MenuBar(true);
		MenuBar repoMenu = new MenuBar(true);
		dozMenu.addItem("Dozent anzeigen", showDozent);
		dozMenu.addItem("Dozent anlegen", createDozent);
		raumMenu.addItem("Raum anzeigen", showRaum);
		raumMenu.addItem("Raum anlegen", createRaum);
		lvMenu.addItem("Lehrveranstaltung anzeigen", showLehrveranstaltung);
		lvMenu.addItem("Lehrveranstaltung anlegen", createLehrveranstaltung);
		semMenu.addItem("Semesterverband anzeigen", showSemesterverband);
		semMenu.addItem("Semesterverband anlegen", createSemesterverband);
		durchfuer.addItem("Durchführung anzeigen", showDurchfuehrung);
		durchfuer.addItem("Durchführung anlegen", createDurchfuerhung);
		
		//HIER NOCH DIE WEITEREN COMMANDS EINFÜGEN
		repoMenu.addItem("Dozentreport", showDozentReport);
		repoMenu.addItem("Stundenplan für Semesterverband", showSemesterverbandReport);
		repoMenu.addItem("Raumreport", showRaumReport);
		
		MenuBar menu = new MenuBar();
		menu.addItem("Dozent", dozMenu);
		menu.addItem("Raum", raumMenu);
		menu.addItem("Lehrveranstaltung", lvMenu);
		menu.addItem("Semesterverband", semMenu);
		menu.addItem("Durchführung", durchfuer);
		menu.addItem("Report", repoMenu);
		
		vertpan.add(new HTML(

				"<img src=\"/Users/df/Documents/workspace/schedule.png\" alt=\"schedule\"><h1>Stundenplan Verwaltungs Tool</h1>"));

//		hor1.add(left);
//		left.add(new HTML("<h2>TreePanel</h2>"));
		hor1.add(right);
		right.add(new HTML("<h2>DetailPanel</h2>"));
		hor1.setSpacing(5);
		vertpan.add(menu);
		RootPanel.get("starter").add(mainPanel);

	}

}