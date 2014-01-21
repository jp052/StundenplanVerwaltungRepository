package de.hdm.gruppe3.stundenplanverwaltung.client.gui;

import java.util.Vector;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
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

/**
 * 
 * @author Selim Karazehir, Julia Hammerer
 *
 */

public class LehrveranstaltungTabelle extends VerticalPanel{

	VerticalPanel mainPanel = new VerticalPanel();
	
	StundenplanVerwaltungServiceAsync stundenplanVerwaltung = GWT
			.create(StundenplanVerwaltungService.class);
	Dozent shownDozent = null;
	
	public LehrveranstaltungTabelle(){}
	
	public Widget zeigeTabelle() {

		final FlexTable t = new FlexTable();
		//
		t.setText(0, 0, "ID");
		t.setText(0, 1, "Bezeichnung");
		t.setText(0, 2, "Semester");
		t.setText(0, 3, "Umfang");
		t.setText(0, 4, "Dozent");

		// dann irgendwann aufruf der methode von Stundenplanverwaltung

		stundenplanVerwaltung.getAllLV(new AsyncCallback<Vector<Lehrveranstaltung>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Ein Fehler ist aufgetreten! - " + caught);

			}

			@Override
			public void onSuccess(Vector<Lehrveranstaltung> result) {
				Window.alert("Es wurden " + result.size()
						+ " Eintraege gefunden");
				int i = 1;
				for (final Lehrveranstaltung lv : result) {
//					Button l = new Button("X");
//					Button b = new Button("Speichern");
					Button a = new Button(ConstantsStdpln.AENDERN);

					final TextBox tbN = new TextBox();
					final TextBox tbV = new TextBox();
					
					String bezeichnung, semester, umfang, dozent;
					
					bezeichnung = lv.getBezeichnung();
					semester = String.valueOf(lv.getSemester());
					umfang = String.valueOf(lv.getUmfang());
					dozent = lv.getDozentName();
					
					
				    Label lBez = new Label(bezeichnung);
				    Label lSem = new Label(semester);
				    Label lUmfang = new Label(umfang);
				    Label lDozent = new Label(dozent);
				    
					
//					tbN.setReadOnly(true);
//					tbV.setReadOnly(true);
//					
//					tbN.setText(d.getNachname());
//				    tbV.setText(d.getVorname());

					t.setText(i, 0, String.valueOf(lv.getId()));
					t.setWidget(i, 1, lBez);
					t.setWidget(i, 2, lSem);
					t.setWidget(i, 3, lUmfang);
					t.setWidget(i, 4, lDozent);

					t.setWidget(i, 6, a);
//					t.setWidget(i, 7, b);
//					t.setWidget(i, 8, l); 
					// ------------------------------------------------------------------------------------
					// L�schen Button
//					l.addClickHandler(new ClickHandler() {
//
//						@Override
//						public void onClick(ClickEvent event) {
//							// TODO hier bei der "loeschenDozent" Methode
//							// br�uchte man evt. ein Int statt Object im ersten
//							// Argument!
//							stundenplanVerwaltung.loeschenLehrveranstaltung(lv,
//									new AsyncCallback<Lehrveranstaltung>() {
//
//										@Override
//										public void onFailure(Throwable caught) {
//
//											Window.alert("Fehler beim loeschen");
//											Window.Location.reload();
//										}
//
//										@Override
//										public void onSuccess(Lehrveranstaltung result) {
//
//											t.clear();
//
//											Window.alert("Loeschen erfolgreich!");
//											Window.Location.reload();
//										}
//									});
//						}
//					});

					// Bearbeiten Button
//					b.addClickHandler(new ClickHandler() {
//
//						@Override
//						public void onClick(ClickEvent event) {
//							// TODO k�nnte mehr Argumente als ein Objekt
//							// brauchen.
//							
//							stundenplanVerwaltung.modifizierenLehrveranstaltung(lv,
//									new AsyncCallback<Lehrveranstaltung>() {
//
//										@Override
//										public void onFailure(Throwable caught) {
//											Window.alert("Fehler beim aendern "
//													+ caught);
//											Window.Location.reload();
//										}
//
//										@Override
//										public void onSuccess(Lehrveranstaltung result) {
//
//											Window.alert("Aendern erfolgreich! "
//													+ tbN.getText()
//													+ tbV.getText());
//											Window.Location.reload();
//
//										}
//									});
//
//						}
//					});
					
					// TODO Label in Textfeld �ndern.
					a.addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							LehrveranstaltungForm lehrveranstaltungForm = new LehrveranstaltungForm();
							lehrveranstaltungForm.setSelected(lv);
							mainPanel.clear();
							mainPanel.add(lehrveranstaltungForm);							
						}
					});
					
					i++;
				}

			}

		}

		);
		mainPanel.add(t);
		return mainPanel;
//		 RootPanel.get("starter").add(mainPanel);

		// return t;
	}
	
	
//	TODO Suchen
//	public Widget sucheLV(){
//		VerticalPanel mainPanel = new VerticalPanel();
//		Button btSuche = new Button("Suchen");
//		final TextBox tbSuchen = new TextBox();
//		
//		mainPanel.add(btSuche);
//		mainPanel.add(tbSuchen);
//		
//		btSuche.addClickHandler(new ClickHandler(){
//
//			@Override
//			public void onClick(ClickEvent event) {
//						LehrveranstaltungTabelle lvtb = new LehrveranstaltungTabelle();
//						lvtb.zeigeLV(tbSuchen.getText()); 
//			}
//			
//		});
//		
//		return mainPanel;
//	}
//	
////	Funktioniert noch nicht!!! ist in Bearbeitung.
//	public void zeigeLV( String lv){
//		
//		final FlexTable t = new FlexTable();
//				
//		t.setText(0, 0, "ID");
//		t.setText(0, 1, "Bezeichnung");
//		t.setText(0, 2, "Semester");
//		t.setText(0, 3, "Umfang");
//		t.setText(0, 4, "Dozent");
//		
//		stundenplanVerwaltung.getLehrveranstaltungByBezeichnung(lv, new AsyncCallback<Lehrveranstaltung>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				Window.alert("Ein Fehler ist aufgetreten! - " + caught);				
//			}
//
//			@Override
//			public void onSuccess(<Vector<Lehrveranstaltung>  result) {
//
//				int i = 1;
//				for (Lehrveranstaltung lv : result) {
//					if(result.getBezeichnung() == lv){
//					Button l = new Button("X");
//					Button b = new Button("Speichern");
//					Button a = new Button(StdplnConstants.AENDERN);
//
//					final TextBox tbN = new TextBox();
//					final TextBox tbV = new TextBox();
//					
//					String bezeichnung, semester, umfang, dozent;
//					
//					bezeichnung = result.getBezeichnung();
//					semester = String.valueOf(result.getSemester());
//					umfang = String.valueOf(result.getUmfang());
//					dozent = result.getDozentName();
//					
//					
//				    Label lBez = new Label(bezeichnung);
//				    Label lSem = new Label(semester);
//				    Label lUmfang = new Label(umfang);
//				    Label lDozent = new Label(dozent);
//				    
//					
////					tbN.setReadOnly(true);
////					tbV.setReadOnly(true);
////					
////					tbN.setText(d.getNachname());
////				    tbV.setText(d.getVorname());
//
//					t.setText(i, 0, String.valueOf(result.getId()));
//					t.setWidget(i, 1, lBez);
//					t.setWidget(i, 2, lSem);
//					t.setWidget(i, 3, lUmfang);
//					t.setWidget(i, 4, lDozent);
//				}
//			}
//			}
//		});
//
//	}
	
//	TODO Suchen 
	
	
}
