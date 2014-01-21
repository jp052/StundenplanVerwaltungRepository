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

import de.hdm.gruppe3.stundenplanverwaltung.shared.ConstantsStdpln;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungServiceAsync;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Dozent;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Raum;



/**
 * @author Selim Karazehir, Julia Hammerer
 *
 */

public class RaumTabelle extends VerticalPanel{
	
	VerticalPanel mainPanel = new VerticalPanel();
	
	StundenplanVerwaltungServiceAsync stundenplanVerwaltung = GWT
			.create(StundenplanVerwaltungService.class);
	
	public RaumTabelle(){}

	public void zeigeTabelle() {

		final FlexTable t = new FlexTable();
		//
		t.setText(0, 0, "ID");
		t.setText(0, 1, "Raum");
		t.setText(0, 2, "Kapazität");

		// dann irgendwann aufruf der methode von Stundenplanverwaltung
		stundenplanVerwaltung.getAllRaeume(new AsyncCallback<Vector<Raum>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Ein Fehler ist aufgetreten! - " + caught);

			}

			@Override
			public void onSuccess(Vector<Raum> result) {
				Window.alert("Es wurden " + result.size()
						+ " Eintraege gefunden");
				int i = 1;
				for (final Raum r : result) {
					Button l = new Button("X");
					Button b = new Button("Speichern");
//					Button a = new Button(StdplnConstants.AENDERN);

					final TextBox tbN = new TextBox();
					final TextBox tbV = new TextBox();
					
					String raumNr, bezeichnung, kapa;
					
					bezeichnung = r.getBezeichnung();
					raumNr = String.valueOf(r.getId());
					kapa = String.valueOf(r.getKapazitaet());
					//dozent = lv.getDozentName();
					
					
				    Label lBez = new Label(bezeichnung);
				    Label lRaumnr = new Label(raumNr);
				    Label lKapa = new Label(kapa);
				    //Label lDozent = new Label(dozent);
				    
					
//					tbN.setReadOnly(true);
//					tbV.setReadOnly(true);
//					
//					tbN.setText(d.getNachname());
//				    tbV.setText(d.getVorname());

					t.setText(i, 0, String.valueOf(r.getId()));
					t.setWidget(i, 1, lBez);
					t.setWidget(i, 2, lKapa);
//					t.setWidget(i, 3, lKapa);
//					t.setWidget(i, 4, lDozent);

					t.setWidget(i, 6, l);
//					t.setWidget(i, 7, b);
//					t.setWidget(i, 8, a); 
					// ------------------------------------------------------------------------------------
					// L�schen Button
					l.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							// TODO hier bei der "loeschenDozent" Methode
							// br�uchte man evt. ein Int statt Object im ersten
							// Argument!
							stundenplanVerwaltung.loeschenRaum(r,
									new AsyncCallback<Raum>() {

										@Override
										public void onFailure(Throwable caught) {

											Window.alert("Fehler beim loeschen");
											Window.Location.reload();
										}

										@Override
										public void onSuccess(Raum result) {

											t.clear();

											Window.alert("Loeschen erfolgreich!");
											Window.Location.reload();
										}
									});
						}
					});

					// Bearbeiten Button
					b.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							// TODO k�nnte mehr Argumente als ein Objekt
							// brauchen.
							
//							stundenplanVerwaltung.modifizierenRaum(r,
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

						}
					});
					
					// TODO Label in Textfeld �ndern.
//					a.addClickHandler(new ClickHandler() {
//						
//						@Override
//						public void onClick(ClickEvent event) {
//							// TODO Auto-generated method stub
////							tbNBoolean = tbN.setReadOnly(true);
////							tbVBoolean = tbV.setReadOnly(true);
//							if( && ){
//								
//							}
//							tbN.setReadOnly(false);
//							tbV.setReadOnly(false);
//							
//						}
//					});
					
					i++;
				}

			}

		}

		);
		mainPanel.add(t);
//		return mainPanel;
		 RootPanel.get("starter").add(mainPanel);

		// return t;
	}
}
