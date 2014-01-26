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

/**
 * Enthält die Tabelle um alle in der Datenbank verfügbaren Elemente anzuzeigen.
 * 
 * @author Yasemin Karakoc, Jan Plank, Selim Karazehir, Julia Hammerer, Denis
 *         Fuerst, Daniel Krakow In Anlehnung an Hr. Prof. Dr. Thies
 */
public class DozentTabelle {

	VerticalPanel mainPanel = new VerticalPanel();

	StundenplanVerwaltungServiceAsync stundenplanVerwaltung = GWT
			.create(StundenplanVerwaltungService.class);

	/**
	 * Stellt die Tabelle dar.
	 * 
	 * @return Das Panel mit der Tabelle
	 */
	public Widget zeigeTabelle() {
		// Eine flexible Tabelle die sich je nach anzahl der vorhandenen
		// Datensätze anpasst.
		final FlexTable flexTable = new FlexTable();
		//
		flexTable.setText(0, 0, "ID");
		flexTable.setText(0, 1, "Name");
		flexTable.setText(0, 2, "Vorname");

		// Liest alle Einträg aus der Datebank füllt sie in die Tabelle. Bei
		// einem Fehler wird eine Meldung ausgegeben.
		stundenplanVerwaltung
				.getAllDozenten(new AsyncCallback<Vector<Dozent>>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Ein Fehler ist aufgetreten! - " + caught);

					}

					@Override
					public void onSuccess(Vector<Dozent> result) {
						// Zählt die Zeilenanzahl
						int zeileCounter = 1;

						// läuft durch alle erhaltene Objekte
						// Das aktuelle Objekt muss final sein, damit es in der
						// Inner Class ClickHandler
						// verwendet werden kann.
						for (final Dozent d : result) {

							Button a = new Button(ConstantsStdpln.AENDERN);

							String nachname, vorname;
							nachname = d.getNachname();
							vorname = d.getVorname();
							
							// Label mit Inhalt füllen
							Label lN = new Label(nachname);
							Label lV = new Label(vorname);
							
							// Label in die die richtige Zeile und Spalte
							// setzen.
							flexTable.setText(zeileCounter, 0, String.valueOf(d.getId()));
							flexTable.setWidget(zeileCounter, 1, lN);
							flexTable.setWidget(zeileCounter, 2, lV);

							flexTable.setWidget(zeileCounter, 6, a);

							a.addClickHandler(new ClickHandler() {

								@Override
								public void onClick(ClickEvent event) {
									// setzt den click handler auf den
									// Modifizieren Button und ruft dann das
									// Form
									// auf und setzt das in der for schleife
									// aktuell durchlaufene Element in das Form.
									DozentForm dozForm = new DozentForm();
									dozForm.setSelected(d);
									// Panel leeren und das Formular dafür
									// einfügen
									mainPanel.clear();
									mainPanel.add(dozForm);

								}

							});

							//Zeile hochzählen
							zeileCounter++;
						}

					}

				}

				);
		mainPanel.add(flexTable);
		// die Tabelle dem mainPanel hinzufügen und es zurück geben.
		return mainPanel;
	}
}
