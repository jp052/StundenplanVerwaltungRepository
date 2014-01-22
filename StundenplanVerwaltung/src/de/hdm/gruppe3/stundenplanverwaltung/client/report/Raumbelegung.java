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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe3.stundenplanverwaltung.shared.ConstantsStdpln;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungServiceAsync;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Lehrveranstaltung;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Raum;

/**
 * @author Selim Karazehir, Julia Hammerer
 *
 */
public class Raumbelegung {
VerticalPanel mainPanel = new VerticalPanel();
	
	StundenplanVerwaltungServiceAsync stundenplanVerwaltung = GWT
			.create(StundenplanVerwaltungService.class);
	
	public void reportRaumbelegung(){
		Button d = new Button("Report");
		final TextBox t = new TextBox();
		
		d.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {

				mainPanel.clear();
				zeigeRaumbelegung(t.getText());
				
			}
		});
		mainPanel.add(d);
		mainPanel.add(t);
		RootPanel.get().add(mainPanel);
	}
	
	public void zeigeRaumbelegung(final String bez){
		
		final FlexTable rt = new FlexTable();
		
		rt.setText(0,0, "Raum: ");
		
		stundenplanVerwaltung.getRaumByBezeichnung(bez, new AsyncCallback<Raum>(){

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
				zeigeTabelle(bez); 
			}
			
		});
	
	}
	
	public void zeigeTabelle(String bez){
		final FlexTable t = new FlexTable();
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
		
		stundenplanVerwaltung.reportLVbyRaum(bez, new AsyncCallback<Vector<Lehrveranstaltung>>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Vector<Lehrveranstaltung> result) {
				Window.alert("Klappt im zweiten Teil");
				
				int anzahlTag = 0;
				int zeitAnzahl = 0;
//				bezeichnung = lv.getBezeichnung();
				String[] tage = ConstantsStdpln.WOCHENTAGE;
			    int [] zeit = ConstantsStdpln.UHRZEITEN;

			    for (final Lehrveranstaltung lv : result) {
					
//					String bezeichnung;
					//Variable für die Flextable position
					String wochentag = lv.getRaumWochentag();
					int raumzeit = lv.getRaumZeit();
				    for (int j = 0; j<= 4; j++) {
						   if (tage[j].equals(wochentag)){ 
							   System.out.println(wochentag); 
							   anzahlTag = j+1;
							   for (int k = 0; k<= zeit.length; k++) {
								   if (zeit[k] == raumzeit){ 
									   System.out.println(raumzeit);
									   zeitAnzahl = k+1;
									   t.setText(zeitAnzahl, anzahlTag, lv.getBezeichnung());
									   break;
								   }
								}
						   } 
					}
				    
					
				}
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
	
//	public int tagIndex(String wochentag, String[] tage){
//		int anzahlTag = 0;
//	    for (int j = 0; j<= 4; j++) {
//			   if (tage[j].equals(wochentag)){ 
//				   anzahlTag = j+1;
//			   }
//	    }
//	    return anzahlTag;
//		
//	}
	
	
	
}
