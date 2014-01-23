package de.hdm.gruppe3.stundenplanverwaltung.client.report;

import java.util.Vector;
import java.sql.Connection;

import sun.awt.HorizBagLayout;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe3.stundenplanverwaltung.client.ClientsideSettings;
import de.hdm.gruppe3.stundenplanverwaltung.shared.ConstantsStdpln;
import de.hdm.gruppe3.stundenplanverwaltung.shared.ReportGenerator;
import de.hdm.gruppe3.stundenplanverwaltung.shared.ReportGeneratorAsync;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungServiceAsync;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Dozent;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Lehrveranstaltung;

public class DozentReport {
VerticalPanel mainPanel = new VerticalPanel();
	
	StundenplanVerwaltungServiceAsync stundenplanVerwaltung = GWT
			.create(StundenplanVerwaltungService.class);
	
//	ReportGeneratorAsync r = ClientsideSettings.getReportGenerator();
//	ReportGeneratorAsync report = GWT.create(ReportGenerator.class);
	Dozent shownDozent = null;
	
	public DozentReport(){}
	
	public void reportDozent(){
		Button d = new Button("Report");
		final TextBox t = new TextBox();
		
		d.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {

				mainPanel.clear();
				zeigen(Integer.parseInt(t.getText()));
				
			}
		});
		mainPanel.add(d);
		mainPanel.add(t);
		RootPanel.get().add(mainPanel);
	}
	
	public void zeigen(int d){
	
		final FlexTable dt = new FlexTable();
		dt.setText(0, 0, "Dozent:");
		
		stundenplanVerwaltung.getDozentByNummer(d,new AsyncCallback<Dozent>(){

			@Override
			public void onFailure(Throwable caught) {
				
				
			}

			@Override
			public void onSuccess(Dozent result) {
				dt.setText(0, 1, result.getNachname()+",");
				dt.setText(0, 2, result.getVorname());
				
			}
			
		});
		
		
		final FlexTable t = new FlexTable();
		//
		t.setText(0, 0, "Lehrveranstaltung");
		t.setText(0, 1, "Semester");
		t.setText(0, 2, "Anzahl der Studierende");
		
		stundenplanVerwaltung.reportLVbyDozent(d, new AsyncCallback<Vector<Lehrveranstaltung>>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Vector<Lehrveranstaltung> result) {
				Window.alert("Es wurden " + result.size()
						+ " Eintraege gefunden");
				int i = 1;
				for (final Lehrveranstaltung lv : result) {

					
					String bezeichnung, semester, umfang;
					
					bezeichnung = lv.getBezeichnung();
					semester = String.valueOf(lv.getSemester());
					umfang = String.valueOf(lv.getUmfang());

				    Label lBez = new Label(bezeichnung);
				    Label lSem = new Label(semester);
				    Label lUmfang = new Label(umfang);

					t.setWidget(i, 0, lBez);
					t.setWidget(i, 1, lSem);
					t.setWidget(i, 2, lUmfang);
					i++;
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
		
		mainPanel.add(dt); 
		mainPanel.add(t);
		mainPanel.add(refresh);
//		return mainPanel;
		 RootPanel.get("starter").add(mainPanel);
	}
	
	public void zeigeReport() {

		int d = 1;
		
		final FlexTable t = new FlexTable();
		//
		t.setText(0, 0, "Lehrveranstaltung");
		t.setText(0, 1, "Semester");
		t.setText(0, 2, "Anzahl der Studierende");



		// dann irgendwann aufruf der methode von Stundenplanverwaltung
//		report.reportLVbyDozent(d, new AsyncCallback<Vector<Lehrveranstaltung>>(){
//
//			@Override
//			public void onFailure(Throwable caught) {
//				Window.alert("Ein Fehler ist aufgetreten! Kein Report verfügbar - " + caught);				
//			}
//
//			@Override
//			public void onSuccess(Vector<Lehrveranstaltung> result) {
//				Window.alert("Es wurden " + result.size()
//						+ " Eintraege gefunden");
//				int i = 1;
//				for (final Lehrveranstaltung lv : result) {
//
//					final TextBox tbN = new TextBox();
//					final TextBox tbV = new TextBox();
//					
//					String bezeichnung, semester, umfang, dozent;
//					
//					bezeichnung = lv.getBezeichnung();
//					semester = String.valueOf(lv.getSemester());
//					umfang = String.valueOf(lv.getUmfang());
//					dozent = lv.getDozentName();
//					
//					
//				    Label lBez = new Label(bezeichnung);
//				    Label lSem = new Label(semester);
//				    Label lUmfang = new Label(umfang);
//				    Label lDozent = new Label(dozent);
//				    
//					t.setText(i, 0, String.valueOf(lv.getId()));
//					t.setWidget(i, 1, lBez);
//					t.setWidget(i, 2, lSem);
//					t.setWidget(i, 3, lUmfang);
//				}
//				i++;
//			}
//		});
//	
//		mainPanel.add(t);
////		return mainPanel;
//		 RootPanel.get("starter").add(mainPanel);
	}
}
