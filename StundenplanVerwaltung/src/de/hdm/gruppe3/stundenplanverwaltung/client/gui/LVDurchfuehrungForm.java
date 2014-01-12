package de.hdm.gruppe3.stundenplanverwaltung.client.gui;

import java.util.Vector;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe3.stundenplanverwaltung.client.ClientsideSettings;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungServiceAsync;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.*;



/**
 * @author Yasemin Karakoc, Jan Plank
 *
 */
public class LVDurchfuehrungForm extends VerticalPanel{
	ListBox lbLehrveranstatlung = new ListBox();
	ListBox lbRaum = new ListBox();
	ListBox lbSemesterverband = new ListBox();
	ListBox lbZeitslotVon = new ListBox();
	ListBox lbZeitslotBis = new ListBox();
	ListBox lbZeitslotTag = new ListBox();
	
	Label idValueLabel = new Label();

	StundenplanVerwaltungServiceAsync stundenplanVerwaltung = GWT.create(StundenplanVerwaltungService.class);
	LVDurchfuehrung selectedLVDurchfuehrung = null;
	StundenplanVerwaltungTreeViewModel treeModel = null;

	/**
	 * Formular für die Darstellung des selektierten Kunden
	 */
	public LVDurchfuehrungForm() {
		Grid customerGrid = new Grid(5, 2);
		this.add(customerGrid);

		Label lLehrveranstaltung = new Label("Lehrveranstaltung");
		customerGrid.setWidget(0, 0, lLehrveranstaltung);
		customerGrid.setWidget(0, 1, lbLehrveranstatlung);

		Label lRaum = new Label("Raum");
		customerGrid.setWidget(1, 0, lRaum);
		customerGrid.setWidget(1, 1, lbRaum);
		
		Label lSemesterverband = new Label("Semesterverband");
		customerGrid.setWidget(2, 0, lSemesterverband);
		customerGrid.setWidget(2, 1, lbSemesterverband);
		
		
		HorizontalPanel hPanelZeitslot = new HorizontalPanel();
		hPanelZeitslot.add(lbZeitslotVon);
		hPanelZeitslot.add(lbZeitslotBis);
		hPanelZeitslot.add(lbZeitslotTag);
		
		Label lZeitslot = new Label("Zeitslot");
		customerGrid.setWidget(3, 0, lZeitslot);
		customerGrid.setWidget(3, 1, hPanelZeitslot);
		//this.add(hPanelZeitslot);
		
		//List Boxen  füllen
		setAnfangszeitListBox();
//		setEndzeitListBox();
//		setWochentagListBox();

		Label idLabel = new Label("ID");
		customerGrid.setWidget(4, 0, idLabel);
		customerGrid.setWidget(4, 1, idValueLabel);

		HorizontalPanel hPanelLVDruchfuehrungButtons = new HorizontalPanel();
		this.add(hPanelLVDruchfuehrungButtons);

		Button modifizierenButton = new Button("Ändern");
		modifizierenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//löst das ändern aus
				modifizierenSelectedLVDurchfuehrung();
			}
		});
		hPanelLVDruchfuehrungButtons.add(modifizierenButton);
		
		
		Button loeschenButton = new Button("Löschen");
		loeschenButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				//löst das löschen aus
				loeschenSelectedLVDurchfuehrung();
			}
		});
		
		hPanelLVDruchfuehrungButtons.add(loeschenButton);
		
		Button neuButton = new Button("Neu");
		neuButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				anlegenLVDurchfuehrung();
				
			}
		});
		
		hPanelLVDruchfuehrungButtons.add(neuButton);
	}


	void setFields() {
//		lbLehrveranstatlung.setText(selectedLehrveranstaltung.getBezeichnung());
////		Integer in String umwandeln
//		lbRaum.setText(Integer.toString(selectedLehrveranstaltung.getSemester()));
//		lbSemesterverband.setText(Integer.toString(selectedLehrveranstaltung.getUmfang()));
//		
//		//richten Eintrag in der ListBox wählen wenn eine Lehrveranstaltung existiert
//		if(selectedLehrveranstaltung != null) {
//			lbZeitslotBis.setSelectedIndex(selectedLehrveranstaltung.getDozent().getId());
//		}
//		
//		idValueLabel.setText(Integer.toString(selectedLehrveranstaltung.getId()));
	}
	
	/**
	 * Füllt die Anfangszeit ListBox
	 */
	private void setAnfangszeitListBox() {
//		stundenplanVerwaltung.getAllDozenten(new AsyncCallback<Vector<Dozent>>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
////				ClientsideSettings.getLogger().severe("Befüllen der DozentenListBox fehlgeschlagen");				
//			}
//
//			@Override
//			public void onSuccess(Vector<Dozent> result) {
//				for(Dozent d : result) {
//					//Der zweite Parameter von addItem ist die gewählte Dozenten Id welche beim anlegen der Lehrveranstaltung 
//					//benötigt wird.
//					lbZeitslotBis.addItem(d.toString(), String.valueOf(d.getId()));
//				}
//				
//				
//			}
//		});
	}

	public void clearFields() {
		lbLehrveranstatlung.setItemSelected(0, true);
		lbRaum.setItemSelected(0, true);
		lbSemesterverband.setItemSelected(0, true);
		lbZeitslotVon.setItemSelected(0, true);
		lbZeitslotBis.setItemSelected(0, true);
		lbZeitslotTag.setItemSelected(0, true);
		idValueLabel.setText("");
	}

	public void setSelected(LVDurchfuehrung lvd) {
		if (lvd != null) {
			selectedLVDurchfuehrung = lvd;
			setFields();
		} else {
			clearFields();
		}
	}
	
	public void modifizierenSelectedLVDurchfuehrung() {
//		if (this.selectedLVDurchfuehrung!=null){
//			//Die ausgewählten Id des gewählten Elementes auswählen und am ende and die entsprechende Async Methode schicken.
//			int zeitNr = Integer.valueOf(lbZ.getValue(lbLehrveranstatlung.getSelectedIndex()));
//			
//			int lvNr = Integer.valueOf(lbLehrveranstatlung.getValue(lbLehrveranstatlung.getSelectedIndex()));
//			
//			int
//			
//			selectedLVDurchfuehrung.setLehrveranstaltung(lv);
//			//String in Integer umwandeln
//			selectedLVDurchfuehrung.setSemester(Integer.valueOf(lbRaum.getText()));
//			selectedLVDurchfuehrung.setUmfang(Integer.valueOf(lbSemesterverband.getText()));
//			
//			//Dozent Object das nur die Id enthält, mehr ist nicht vorhanden und auch nicht nötig.
//			Dozent d = new Dozent();
//			int dozentId = Integer.valueOf(lbZeitslotBis.getValue(lbZeitslotBis.getSelectedIndex()));
//			d.setId(dozentId);
//			
//			selectedLVDurchfuehrung.setDozent(d);
//			//Ruft Serverseitige Methode auf
//			stundenplanVerwaltung.modifizierenLehrveranstaltung(selectedLVDurchfuehrung, new AsyncCallback<Lehrveranstaltung>() {
//
//				@Override
//				public void onFailure(Throwable caught) {
//					// TODO Auto-generated method stub
//					
//				}
//
//				@Override
//				public void onSuccess(Lehrveranstaltung result) {
//					System.out.println("Lehrveranstaltung geändert");
////					treeModel.updateDozent(shownDozent);
//					
//				}
//			});
//		}
	}
	
	public void loeschenSelectedLVDurchfuehrung() {
//		if(this.selectedLVDurchfuehrung != null) {
//			//Ruft Serverseitige Methode auf
//			stundenplanVerwaltung.loeschenLehrveranstaltung(selectedLVDurchfuehrung, new AsyncCallback<Lehrveranstaltung>() {
//
//				@Override
//				public void onFailure(Throwable caught) {
//					// TODO Auto-generated method stub
//					
//				}
//
//				@Override
//				public void onSuccess(Lehrveranstaltung result) {
//					if (result != null) {
//						System.out.println("Lehrveranstaltung gelöscht");
//						setSelected(null);
//						//TODO: Liste oder Tree aktualisieren
//					}					
//				}				
//			});
//		}
	}
	
	public void anlegenLVDurchfuehrung() {
		//Die ausgewählten Id des gewählten Elementes auswählen und am ende and die entsprechende Async Methode schicken.
		
		//Werte für Zeitslot Objekt aus Listbox auslesen
		int anfangsZeit = Integer.valueOf(lbZeitslotVon.getValue(lbZeitslotVon.getSelectedIndex()));
		int endZeit = Integer.valueOf(lbZeitslotBis.getValue(lbZeitslotBis.getSelectedIndex()));
		String wochentag = lbZeitslotTag.getItemText(lbZeitslotTag.getSelectedIndex());
		
		//Zeitslot Objekt mit aus der ListBox gelesenen Werten füllen.
		Zeitslot zeitslot = new Zeitslot();
		zeitslot.setAnfangszeit(anfangsZeit);
		zeitslot.setEndzeit(endZeit);
		zeitslot.setWochentag(wochentag);
		
		//Wert für Semesterverband auslsen
		int svId = Integer.valueOf(lbSemesterverband.getValue(lbSemesterverband.getSelectedIndex()));
		
		//Wert für Raum auslsen
		int raumId = Integer.valueOf(lbRaum.getValue(lbRaum.getSelectedIndex()));
		
		//Wert für Lehrveranstaltung auslsen
		int lvId = Integer.valueOf(lbLehrveranstatlung.getValue(lbLehrveranstatlung.getSelectedIndex()));
		
		
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
