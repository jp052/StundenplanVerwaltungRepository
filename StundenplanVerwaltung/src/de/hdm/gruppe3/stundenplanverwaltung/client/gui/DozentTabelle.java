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

import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungServiceAsync;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Dozent;


/**
 * 
 * @author Selim Karazehir, Julia Hammerer
 *
 */
public class DozentTabelle {

	VerticalPanel mainPanel = new VerticalPanel();
	
	StundenplanVerwaltungServiceAsync stundenplanVerwaltung = GWT
			.create(StundenplanVerwaltungService.class);
	Dozent shownDozent = null;
	
	public Widget zeigeTabelle() {

		final FlexTable t = new FlexTable();
		//
		t.setText(0, 0, "ID");
		t.setText(0, 1, "Name");
		t.setText(0, 2, "Vorname");

		// dann irgendwann aufruf der methode von Stundenplanverwaltung

		stundenplanVerwaltung.getAllDozenten(new AsyncCallback<Vector<Dozent>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Ein Fehler ist aufgetreten! - " + caught);

			}

			@Override
			public void onSuccess(Vector<Dozent> result) {
				Window.alert("Es wurden " + result.size()
						+ " Eintraege gefunden");
				int i = 1;
				for (final Dozent d : result) {
					Button l = new Button("X");
					Button b = new Button("Speichern");
					Button a = new Button("�ndern");

					final TextBox tbN = new TextBox();
					final TextBox tbV = new TextBox();
					
					String nachname, vorname;
					nachname = d.getNachname();
					vorname = d.getVorname();
					
				    Label lN = new Label(nachname);
				    Label lV = new Label(vorname);
					
//					tbN.setReadOnly(true);
//					tbV.setReadOnly(true);
//					
//					tbN.setText(d.getNachname());
//				    tbV.setText(d.getVorname());

					t.setText(i, 0, String.valueOf(d.getId()));
					t.setWidget(i, 1, lN);
					t.setWidget(i, 2, lV);

					t.setWidget(i, 6, l);
					t.setWidget(i, 7, b);
//					t.setWidget(i, 8, a); 
					// ------------------------------------------------------------------------------------
					// L�schen Button
					l.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							// TODO hier bei der "loeschenDozent" Methode
							// br�uchte man evt. ein Int statt Object im ersten
							// Argument!
							stundenplanVerwaltung.loeschenDozent(d,
									new AsyncCallback<Dozent>() {

										@Override
										public void onFailure(Throwable caught) {

											Window.alert("Fehler beim loeschen");
											Window.Location.reload();
										}

										@Override
										public void onSuccess(Dozent result) {

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
							
							stundenplanVerwaltung.modifizierenDozent(d,
									new AsyncCallback<Dozent>() {

										@Override
										public void onFailure(Throwable caught) {
											Window.alert("Fehler beim aendern "
													+ caught);
											Window.Location.reload();
										}

										@Override
										public void onSuccess(Dozent result) {

											Window.alert("Aendern erfolgreich! "
													+ tbN.getText()
													+ tbV.getText());
											Window.Location.reload();

										}
									});

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
		return mainPanel;
//		RootPanel.get("starter").add(mainPanel);
		// return t;
	}
}
