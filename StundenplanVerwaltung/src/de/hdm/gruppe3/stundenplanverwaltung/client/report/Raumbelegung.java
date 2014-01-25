package de.hdm.gruppe3.stundenplanverwaltung.client.report;

import java.util.Vector;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.gruppe3.stundenplanverwaltung.shared.ConstantsStdpln;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungServiceAsync;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Lehrveranstaltung;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Raum;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Semesterverband;

/**
 * @author Selim Karazehir, Julia Hammerer
 *
 */
public class Raumbelegung {
	
	VerticalPanel mainPanel = new VerticalPanel();
	ListBox raumListBox = new ListBox();
	
	StundenplanVerwaltungServiceAsync stundenplanVerwaltung = GWT
			.create(StundenplanVerwaltungService.class);
	
	/**
	 * Die Button und die Listbox wird hier in die eine FlexTable reingeschrieben.
	 * Wenn der Button betätigt wird dann läuft die onClick Methode und löscht den vorherigen
	 * mainPanel. Danach wird die Methode zeigeRaumbelegung() durchgeführt und die Werte vom Listbox übergeben.
	 * @return mainPanel
	 */
	public Widget reportRaumbelegung(){
		FlexTable flexRaum = new FlexTable();
		Button d = new Button("Report");
		final TextBox t = new TextBox();
	
		d.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {

				mainPanel.clear();
				zeigeRaumbelegung(raumListBox.getValue(raumListBox.getSelectedIndex()));
				
			}
		});
		setRaumListBox();
		flexRaum.setWidget(0, 0, raumListBox);
		flexRaum.setWidget(0, 1, d); 	
		mainPanel.clear();
		mainPanel.add(flexRaum);
		return mainPanel;
	}
	
	/**
	 * Um die Raumbezeichnung in die den Report zu schreiben, benötigt diese Methode den Parameter bez, die sie 
	 * erst von der Methode reportRaumbelegung() übergeben wird. 
	 * @see reportRaumbelegung()
	 * dann wird ein FlexTable rt instanziiert um die Tabelle für die Raumbezeichnung zu gestalten. In der onSuccess()
	 * Methode wird der Raum rausgelesen und in die nullte Zeile, erste Spalte reingeschrieben. 
	 * @param raumID
	 */
	public void zeigeRaumbelegung(final String id){
		
		final FlexTable rt = new FlexTable();
		final int raumID;
		raumID = Integer.parseInt(id);
		mainPanel.clear();
		rt.setText(0,0, "Raum: ");
		
		stundenplanVerwaltung.getRaumbyNummer(raumID, new AsyncCallback<Raum>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("Problem" +caught.getMessage());
			}

			@Override
			public void onSuccess(Raum result) {
				Window.alert("Klappt");
				rt.setText(0, 1, result.getBezeichnung());
				mainPanel.add(rt);
				zeigeTabelle(raumID); 
			}
			
		});
	
	}
	
	/**
	 * Mit dieser Methode wird der Stundenplan für den jeweiligen Raum erzeugt. Dazu braucht man den Parameter bez.
	 * Es wird dann der Grundgerüst eines Stundenplans gebaut. Anschließend wird die reportLVbyRaum aufgerufen 
	 * und den Parameter übergeben. In der onSuccess() Methode wird die Lehrveranstaltung erst nach dem Tag, dann nach der 
	 * Anfangs- und Endzeit gesucht. Diese Werte werden dann mit der Bezeichnung der Lehrveranstaltung in den Stundenplan 
	 * reingeschrieben. Am Ende der Methode wird ein Button erzeugt refresh. Zum Schluss wird alles in den mainPanel 
	 * hinzugefügt.
	 * @param bez
	 * 
	 */
	public void zeigeTabelle(int raumID){
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
		
		stundenplanVerwaltung.reportLVbyRaum(raumID, new AsyncCallback<Vector<Lehrveranstaltung>>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Vector<Lehrveranstaltung> result) {
				Window.alert("Klappt im zweiten Teil");
				
				int anzahlTag = 0;
				int zeitAnf = 0;
				int zeitEnde = 0;
//				bezeichnung = lv.getBezeichnung();
				String[] tage = ConstantsStdpln.WOCHENTAGE;
			    int [] zeit = ConstantsStdpln.UHRZEITEN;
//				die For Schleife um den Stundenplan zu bauen
			    for (final Lehrveranstaltung lv : result) {
					
//					String bezeichnung;
					//Variable für die Flextable position
					String wochentag = lv.getRaumWochentag();
					int raumzeit = lv.getRaumZeit();
					int raumzeitEnde = lv.getRaumZeitEnde();
				    for (int j = 0; j<= 4; j++) {
						   if (tage[j].equals(wochentag)){ 
							   System.out.println(wochentag); 
							   anzahlTag = j+1;
							   for (int k = 0; k < zeit.length; k++) {
								   if (zeit[k] == raumzeit){ 
									   System.out.println(raumzeit);
									   zeitAnf = k+1;
//									   break;

									   for(int l = 0; l< zeit.length; l++){
										   if (zeit[l] == raumzeitEnde) {
											zeitEnde = l+1;
											
											int dif2 = zeitEnde - zeitAnf;
											if((zeitEnde - zeitAnf)>=1){
//												t.setText(zeitAnzahl, anzahlTag, lv.getBezeichnung());
//												 t.setText(zeitEnde-1, anzahlTag, lv.getBezeichnung());
//												zeitEnde -=zeitAnzahl;
//											   break;
												
//												   Forschleife für die Anzahl der Lehrveranstaltungen
													int n = 0; 
												   for (int m = 1; m <=dif2; m++) {
														
														t.setText(zeitAnf+n, anzahlTag, lv.getBezeichnung());
														n++;
														
														if ((zeitAnf+m) == zeitEnde) {
															t.setText(zeitEnde-1, anzahlTag, lv.getBezeichnung());
															break;
														} 
//														n++;
//														else {
//															int n = 0; 
//															t.setText(zeitAnf+n, anzahlTag, lv.getBezeichnung());
//															n++;
//													}
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
	
	public void setRaumListBox() {
		
		stundenplanVerwaltung
				.getAllRaeume(new AsyncCallback<Vector<Raum>>() {

					@Override
					public void onFailure(Throwable caught) {
						// ClientsideSettings.getLogger().severe("Bef�llen der DozentenListBox fehlgeschlagen");
					}

					@Override
					public void onSuccess(Vector<Raum> result) {

						raumListBox.addItem("--Bitte wählen--", "0");
						for (Raum r : result) {
							// Der zweite Parameter von addItem ist die gewählte
							// Semesterverband Id welche beim anlegen der
							// Lehrveranstaltung
							// ben�tigt wird.
							raumListBox.addItem(r.getBezeichnung(), String.valueOf(r.getId()));							

						}


					}
				});
	}
	
}
