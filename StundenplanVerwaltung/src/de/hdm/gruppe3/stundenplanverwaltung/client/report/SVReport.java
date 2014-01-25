/**
 * 
 */
package de.hdm.gruppe3.stundenplanverwaltung.client.report;

import java.util.Vector;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.gruppe3.stundenplanverwaltung.shared.ConstantsStdpln;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungServiceAsync;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Dozent;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Lehrveranstaltung;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Raum;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Semesterverband;

/**
 * @author Selim Karazehir, Julia Hammerer
 *
 */
public class SVReport {

	VerticalPanel mainPanel = new VerticalPanel();
	ListBox svListBox = new ListBox();
	StundenplanVerwaltungServiceAsync stundenplanVerwaltung = GWT
			.create(StundenplanVerwaltungService.class);
	
	/**
	 * reportSemesterverband() ist die Maske, die man unter der Menuleiste Report/Stundenplan für Semesterverband.
	 * @return mainPanel
	 */
	public Widget reportSemesterverband(){
		FlexTable flexSV = new FlexTable();
		Button d = new Button("Report");
	
		d.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {

				mainPanel.clear();
				zeigeSVReport(Integer.parseInt(svListBox.getValue(svListBox.getSelectedIndex())));
				
			}
		});
		
		setSVListBox();
		flexSV.setWidget(0, 0, svListBox);
		flexSV.setWidget(0, 1, d); 	
		mainPanel.clear();
		mainPanel.add(flexSV);
		return mainPanel;
	}
	
	/**
	 * Mit dieser Methode können wir den Report rauslesen.
	 * @param sv
	 */
	
	public void zeigeSVReport(final int sv){
		
		final FlexTable rt = new FlexTable();
		mainPanel.clear();
		rt.setText(0,0, "Semesterverband: ");
		
//		stundenplanVerwaltung.getSemesterverbandBySemesterHalbjahr(sv, new AsyncCallback<Semesterverband>(){

//			@Override
//			public void onFailure(Throwable caught) {
//				// TODO Auto-generated method stub
//				Window.alert("Problem" +caught.getMessage());
//			}

//			@Override
//			public void onSuccess(Semesterverband result) {

			//Window.alert("Klappt");
			rt.setText(0, 1, String.valueOf(svListBox.getValue(svListBox.getSelectedIndex())));
			mainPanel.add(rt);
			zeigeTabelle(sv); 
//			}
			
//		});
	
	}
	
	/**
	 * Diese Methode wird in der Methode zeigeSVReport aufgerufen. 
	 * @param sv
	 * @return mainPanel
	 */
	public void zeigeTabelle(int sv){
		final FlexTable t = new FlexTable();
		RootPanel.get("starter").clear();
		
		//
		t.setText(0, 0, "Zeit");
		t.setText(0, 1, "Montag");
		t.setText(0, 2, "Dienstag");
		t.setText(0, 3, "Mittwoch");
		t.setText(0, 4, "Donnerstag");
		t.setText(0, 5, "Freitag");
		
		t.setText(1, 0, "8-9");
		t.setText(2, 0, "9-10");
		t.setText(3, 0, "10-11");
		t.setText(4, 0, "11-12");
		t.setText(5, 0, "12-13");
		t.setText(6, 0, "13-14");
		t.setText(7, 0, "14-15");
		t.setText(8, 0, "15-16");
		t.setText(9, 0, "16-17");
		t.setText(10, 0, "17-18");
		
		stundenplanVerwaltung.reportLVbySV(sv, new AsyncCallback<Vector<Lehrveranstaltung>>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Vector<Lehrveranstaltung> result) {
				Window.alert("Klappt im zweiten Teil");
				
				int spalteTag = 0;
				int zeitAnf = 0;
				int zeitEnde = 0;
				String[] tage = ConstantsStdpln.WOCHENTAGE;
			    int [] zeit = ConstantsStdpln.UHRZEITEN;
//				die For Schleife um den Stundenplan zu bauen
			    for (final Lehrveranstaltung lv : result) {
					
					//Variable für die Flextable position
					String wochentag = lv.getRaumWochentag();
					int raumzeit = lv.getRaumZeit();
					int raumzeitEnde = lv.getRaumZeitEnde();
					int dif = raumzeitEnde-raumzeit+1;
//					die Erste For schleife sucht nach dem Tag
				    for (int tagCounter = 0; tagCounter<= 4; tagCounter++) {
						   if (tage[tagCounter].equals(wochentag)){ 
							   System.out.println(wochentag); 
//							   hier erhält die Variable die aktuelle Array Index. Es wird auch noch um eins addiert
//							   da das die Spalte der Tabelle ist.
							   spalteTag = tagCounter+1;
							   					   							   
//							   Hier wird nach der Anfangszeit gesucht
							   for (int anfZeitCounter = 0; anfZeitCounter < zeit.length; anfZeitCounter++) {
								   if (zeit[anfZeitCounter] == raumzeit){ 
									   System.out.println(raumzeit);
									   zeitAnf = anfZeitCounter+1;

//									   Hier wird nach der Endzeit gesucht
									   for(int endZeitCounter = 0; endZeitCounter< zeit.length; endZeitCounter++){
										   if (zeit[endZeitCounter] == raumzeitEnde) {
											zeitEnde = endZeitCounter+1;
											
//											Die dif enthält die Differenz von Endzeit und Anfangszeit.
											int dif2 = zeitEnde - zeitAnf;
											
//											Hier wird geprüft ob die Endzeit nicht kleiner ist als die Anfangszeit
											if((zeitEnde - zeitAnf)>=1){
												
//												t.setText(zeitAnf, anzahlTag, lv.getBezeichnung());
//												 t.setText(zeitEnde-1, anzahlTag, lv.getBezeichnung());
//												zeitEnde -=zeitAnf;
//											   break;
												
//												Diese Variable wird benötigt um die Zeilen bei mehreren Veranstaltungen zu ergänzen.
												int zeileCounter = 0; 
//												 Hier wird solange geloopt bis die Variable m kleiner gleich dif ist
											   for (int anzahlZeile = 1; anzahlZeile <=dif2; anzahlZeile++) {
													
													t.setText(zeitAnf+zeileCounter, spalteTag, lv.getBezeichnung());
													zeileCounter++;
													
													if ((zeitAnf+anzahlZeile) == zeitEnde) {
														t.setText(zeitEnde-1, spalteTag, lv.getBezeichnung());
														break;
													} 
											   }
											   
											}
										}
									   }
									   
									   
									   
								   }
								}
						   } 
					}
				}
			    
//			    Ende der For
			}
			
		});
		
		Button refresh = new Button("Zurück");
		refresh.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {

				Window.Location.reload();
			}
		});
//		mainPanel.add(rt);
		mainPanel.add(t);
		mainPanel.add(refresh);
		RootPanel.get().add(mainPanel);
	}	
	
	public void setSVListBox() {
		
		stundenplanVerwaltung
				.getAllSemesterverband(new AsyncCallback<Vector<Semesterverband>>() {

					@Override
					public void onFailure(Throwable caught) {
						// ClientsideSettings.getLogger().severe("Bef�llen der DozentenListBox fehlgeschlagen");
					}

					@Override
					public void onSuccess(Vector<Semesterverband> result) {

						svListBox.addItem("--Bitte wählen--", "0");
						for (Semesterverband sv : result) {
							// Der zweite Parameter von addItem ist die gewählte
							// Semesterverband Id welche beim anlegen der
							// Lehrveranstaltung
							// ben�tigt wird.
							svListBox.addItem(sv.toString(), String.valueOf(sv.getId()));							

						}


					}
				});
	}
	
}
