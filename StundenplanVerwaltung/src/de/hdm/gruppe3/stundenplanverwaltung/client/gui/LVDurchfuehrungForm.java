package de.hdm.gruppe3.stundenplanverwaltung.client.gui;

import java.util.ArrayList;
import java.util.Vector;

import sun.awt.CausedFocusEvent.Cause;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe3.stundenplanverwaltung.client.ClientsideSettings;
import de.hdm.gruppe3.stundenplanverwaltung.shared.ConstantsStdpln;
import de.hdm.gruppe3.stundenplanverwaltung.shared.RaumBelegtException;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungServiceAsync;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.*;



/**
 * @author Yasemin Karakoc, Jan Plank
 *
 */
public class LVDurchfuehrungForm extends VerticalPanel{
	ListBox lbLehrveranstaltung = new ListBox();
	ListBox lbRaum = new ListBox();
	ListBox lbSemesterverband = new ListBox();
	ListBox lbAnfangszeit = new ListBox();
	ListBox lbEndzeit = new ListBox();
	ListBox lbWochentag = new ListBox();
	
	Label lbIdValue = new Label();

	StundenplanVerwaltungServiceAsync stundenplanVerwaltung = GWT.create(StundenplanVerwaltungService.class);
	LVDurchfuehrung selectedLVDurchfuehrung = null;
	StundenplanVerwaltungTreeViewModel treeModel = null;

	/**
	 * Formular f�r die Darstellung des selektierten Kunden
	 */
	public LVDurchfuehrungForm() {
		Grid customerGrid = new Grid(5, 2);
		this.add(customerGrid);

		Label lLehrveranstaltung = new Label("Lehrveranstaltung");
		customerGrid.setWidget(0, 0, lLehrveranstaltung);
		customerGrid.setWidget(0, 1, lbLehrveranstaltung);

		Label lRaum = new Label("Raum");
		customerGrid.setWidget(1, 0, lRaum);
		customerGrid.setWidget(1, 1, lbRaum);
		
		Label lSemesterverband = new Label("Semesterverband");
		customerGrid.setWidget(2, 0, lSemesterverband);
		customerGrid.setWidget(2, 1, lbSemesterverband);
		
		
		HorizontalPanel hPanelZeitslot = new HorizontalPanel();
		hPanelZeitslot.add(lbAnfangszeit);
		hPanelZeitslot.add(lbEndzeit);
		hPanelZeitslot.add(lbWochentag);
		
		Label lZeitslot = new Label("Zeitslot");
		customerGrid.setWidget(3, 0, lZeitslot);
		customerGrid.setWidget(3, 1, hPanelZeitslot);
		//this.add(hPanelZeitslot);
		
		//List Boxen  füllen
		setLehrveranstaltungListBox();
		setRaumListBox();
		setSemesterverbandListBox();
		setZeitListBox();
		setWochentagListBox();

		Label idLabel = new Label("ID");
		customerGrid.setWidget(4, 0, idLabel);
		customerGrid.setWidget(4, 1, lbIdValue);

		HorizontalPanel hPanelLVDruchfuehrungButtons = new HorizontalPanel();
		this.add(hPanelLVDruchfuehrungButtons);

		Button modifizierenButton = new Button("Ändern");
		modifizierenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//l�st das �ndern aus
				modifizierenSelectedLVDurchfuehrung();
			}
		});
		hPanelLVDruchfuehrungButtons.add(modifizierenButton);
		
		
		Button loeschenButton = new Button(ConstantsStdpln.LOESCHEN);
		loeschenButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				//l�st das l�schen aus
				loeschenSelectedLVDurchfuehrung();
			}
		});
		
		hPanelLVDruchfuehrungButtons.add(loeschenButton);
		
		Button neuButton = new Button(ConstantsStdpln.NEU);
		neuButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				anlegenLVDurchfuehrung();
				
			}
		});
		
		hPanelLVDruchfuehrungButtons.add(neuButton);
	}



	
	private void setLehrveranstaltungListBox() {
		stundenplanVerwaltung.getAllLV(new AsyncCallback<Vector<Lehrveranstaltung>>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Fehler!");
			}

			@Override
			public void onSuccess(Vector<Lehrveranstaltung> result) {
//				Erster Eintrag soll leer sein bzw. Bitte wählen enthalten
				lbLehrveranstaltung.addItem("--Bitte wählen--", "0");
				
				for(Lehrveranstaltung lv : result) {					
					//Der zweite Parameter von addItem ist die gew�hlte Lehrveranstatlungs Id welche beim anlegen der Durchführung 
					//benötigt wird.
					lbLehrveranstaltung.addItem(lv.getBezeichnung(), String.valueOf(lv.getId()));
				}				
			}
		});
	}
	private void setRaumListBox(){
		stundenplanVerwaltung.getAllRaeume(new AsyncCallback<Vector<Raum>>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Fehler!");
			}

			@Override
			public void onSuccess(Vector<Raum> result) {
				//Erster Eintrag soll leer sein bzw. Bitte wählen enthalten
				lbRaum.addItem("--Bitte wählen--", "0");
				
				for(Raum r : result) {
					//Der zweite Parameter von addItem ist die gew�hlte Raum Id welche beim anlegen der Durchführung 
					//v wird.
					lbRaum.addItem(r.getBezeichnung(), String.valueOf(r.getId()));
				}				
			}
		});
	}
	
	private void setSemesterverbandListBox(){
		stundenplanVerwaltung.getAllSemesterverband(new AsyncCallback<Vector<Semesterverband>>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Fehler!");
				
			}

			@Override
			public void onSuccess(Vector<Semesterverband> result) {
				//Erster Eintrag soll leer sein bzw. Bitte wählen enthalten
				lbSemesterverband.addItem("--Bitte wählen--", "0");
				
				for(Semesterverband sv : result) {
					StringBuilder listBoxItem = new StringBuilder();
					
					//Vorraussetzung ist, dass sich Studenten nur zum Wintersemester einschreiben können!
					//Ansonsten stimmt die Logik zur bestimmung ob ein Semester im Winter Semester (WS) oder dem Sommersemester (SS) nicht.
					//ist die Semesterzahl ungerade ist das Semester im WS, ansonste SS.
					//Der Modulo Operator(%) mit der Zahl 2 liefert  bei einer ungeraden Zahl einne 1 und bei einer geraden Zahl eine 0 zurück.
					String halbjahr = "SS";
					int semester = sv.getSemester();
					if(semester % 2 == 1) {						
						halbjahr = "WS";
					} 
					
					//Beispiel des Stings der in der ListBox angezeigt werden wird:
					//WS 2013-Semester 3
					listBoxItem.append(halbjahr); //WS oder SS
					listBoxItem.append(" "); //Abstand
					listBoxItem.append(sv.getJahrgang()); //Jahr z.B. 2013
					listBoxItem.append("-"); //Trennung mit Minus#
					listBoxItem.append("Semester " + semester); //Aktuelles Semester z.B. Semester 3
					
					//Der erste Prameter von addItem enthält den anzeige String, er wird vom StringBuffer in einen normalen String umgewandelt.
					//Der zweite Parameter von addItem ist die gewählte Semesterverband Id welche beim anlegen der Durchführung 
					//benögtigt wird.
					lbSemesterverband.addItem(listBoxItem.toString(), String.valueOf(sv.getId()));
//					lbSemesterverband.setValue(index, value);
				}
				
			}
		});
	}
	
	/**
	 * Füllt anfangszeit und endzeit ListBox
	 */
	private void setZeitListBox() {
		//Erster Eintrag soll leer sein bzw. Bitte wählen enthalten
		lbAnfangszeit.addItem("--Bitte wählen--", "0");
		lbEndzeit.addItem("--Bitte wählen--", "0");
		
		for(int zeit : ConstantsStdpln.UHRZEITEN) {
			//Vorher int in String umwandeln mit String.valueOf
			lbAnfangszeit.addItem(String.valueOf(zeit));
			lbEndzeit.addItem(String.valueOf(zeit));
		}
	}
	
	/**
	 * Füllte die Tage in die ListBox
	 */
	private void setWochentagListBox() {
		//Erster Eintrag soll leer sein bzw. Bitte wählen enthalten
		lbWochentag.addItem("--Bitte wählen--", "0");
		//Läuft durch den ganzen Wochentag Array der in ConstantsStdpln definiert ist 
		//und füllt diese in die Wochentag ListBox.
		for(String tag : ConstantsStdpln.WOCHENTAGE) {
			lbWochentag.addItem(tag);
		}
	}



	public void setSelected(LVDurchfuehrung lvd) {
		if (lvd != null) {
			selectedLVDurchfuehrung = lvd;
			setFields();
		} else {
			clearFields();
		}
	}
	
	void setFields() {
	
		//richten Eintrag in der ListBox w�hlen wenn eine Lehrveranstaltung existiert
		if(selectedLVDurchfuehrung != null) {
			lbWochentag.setSelectedIndex(3);
			lbRaum.setSelectedIndex(2);
			lbAnfangszeit.setSelectedIndex(4);
			lbIdValue.setText(String.valueOf(selectedLVDurchfuehrung.getId()));
//			lbLehrveranstatlung.setSelectedIndex(selectedLVDurchfuehrung.getLehrveranstaltung().getId());
//			lbLehrveranstaltung.setSelectedIndex(1);
//			lbRaum.setSelectedIndex(1);
//			lbZeitslotBis.setSelectedIndex(selectedLehrveranstaltung.getDozent().getId());
		}
	}
	
	public void clearFields() {
//		lbLehrveranstaltung.setItemSelected(0, true);
//		lbRaum.setItemSelected(0, true);
//		lbSemesterverband.setItemSelected(0, true);
//		lbAnfangszeit.setItemSelected(0, true);
//		lbEndzeit.setItemSelected(0, true);
//		lbWochentag.setItemSelected(0, true);
//		idValueLabel.setText("");
	}
	
	public void modifizierenSelectedLVDurchfuehrung() {
		//Die ausgew�hlten Id des gew�hlten Elementes ausw�hlen und am ende and die entsprechende Async Methode schicken.
		
		//Werte f�r Zeitslot Objekt aus Listbox auslesen
		int anfangsZeit = Integer.valueOf(lbAnfangszeit.getValue(lbAnfangszeit.getSelectedIndex()));
		int endZeit = Integer.valueOf(lbEndzeit.getValue(lbEndzeit.getSelectedIndex()));
		String wochentag = lbWochentag.getItemText(lbWochentag.getSelectedIndex());
		
		//Zeitslot Objekt mit aus der ListBox gelesenen Werten f�llen, die id fehlt und wird in den mapper emittelt.
		Zeitslot zeitslot = new Zeitslot();
		zeitslot.setAnfangszeit(anfangsZeit);
		zeitslot.setEndzeit(endZeit);
		zeitslot.setWochentag(wochentag);
		
		//Wert der Durchführungs Id auslsen
		int lvdId = Integer.valueOf(lbIdValue.getText());
		//Wert f�r Semesterverband auslsen
		int svId = Integer.valueOf(lbSemesterverband.getValue(lbSemesterverband.getSelectedIndex()));
		
		//Wert f�r Raum auslsen
		int raumId = Integer.valueOf(lbRaum.getValue(lbRaum.getSelectedIndex()));
		
		//Wert f�r Lehrveranstaltung auslsen
		int lvId = Integer.valueOf(lbLehrveranstaltung.getValue(lbLehrveranstaltung.getSelectedIndex()));
		
		
		//Ruft Serverseitige Methode auf
		stundenplanVerwaltung.modifizierenDurchfuehrung(lvdId, svId, raumId, lvId, zeitslot, new AsyncCallback<LVDurchfuehrung>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub				
			}

			@Override
			public void onSuccess(LVDurchfuehrung result) {
				System.out.println("Geändert");			
			}	
		});
	}
	
	public void loeschenSelectedLVDurchfuehrung() {
		if(this.selectedLVDurchfuehrung != null) {
			//Ruft Serverseitige Methode auf
			stundenplanVerwaltung.loeschenDurchfuehrung(selectedLVDurchfuehrung, new AsyncCallback<LVDurchfuehrung>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
				}

				@Override
				public void onSuccess(LVDurchfuehrung result) {
					System.out.println("Gelöscht");			
				}				
			});
		}
	}
	
	public void anlegenLVDurchfuehrung() {
		//Die ausgew�hlten Id des gew�hlten Elementes ausw�hlen und am ende and die entsprechende Async Methode schicken.
		
		//Werte f�r Zeitslot Objekt aus Listbox auslesen
		int anfangsZeit = Integer.valueOf(lbAnfangszeit.getValue(lbAnfangszeit.getSelectedIndex()));
		int endZeit = Integer.valueOf(lbEndzeit.getValue(lbEndzeit.getSelectedIndex()));
		String wochentag = lbWochentag.getItemText(lbWochentag.getSelectedIndex());
		
		//Zeitslot Objekt mit aus der ListBox gelesenen Werten f�llen, die id fehlt und wird in den mapper emittelt.
		Zeitslot zeitslot = new Zeitslot();
		zeitslot.setAnfangszeit(anfangsZeit);
		zeitslot.setEndzeit(endZeit);
		zeitslot.setWochentag(wochentag);
		
		//Wert f�r Semesterverband auslsen
		int svId = Integer.valueOf(lbSemesterverband.getValue(lbSemesterverband.getSelectedIndex()));
		
		//Wert f�r Raum auslsen
		int raumId = Integer.valueOf(lbRaum.getValue(lbRaum.getSelectedIndex()));
		
		//Wert f�r Lehrveranstaltung auslsen
		int lvId = Integer.valueOf(lbLehrveranstaltung.getValue(lbLehrveranstaltung.getSelectedIndex()));
		
		
		//Ruft Serverseitige Methode auf
		stundenplanVerwaltung.anlegenDurchfuehrung(svId, raumId, lvId, zeitslot, new AsyncCallback<LVDurchfuehrung>() {

			@Override
			public void onFailure(Throwable caught) {
//				ClientsideSettings.getLogger().severe("Lehrveranstaltung anlegen Fehler");	
				
			}

			@Override
			public void onSuccess(LVDurchfuehrung result) {
				if (result != null) {
					System.out.println("LVDurchfuehrung angelegt");
					//TODO: Liste oder Tree aktualisieren
				}
			}
			
		});
		
	}
	

	

}
