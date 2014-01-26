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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.gruppe3.stundenplanverwaltung.shared.ConstantsStdpln;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungServiceAsync;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Dozent;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Lehrveranstaltung;

/**
 * Enthält die Tabelle um alle in der Datenbank verfügbaren Elemente anzuzeigen.
 * 
 * @author Yasemin Karakoc, Jan Plank, Selim Karazehir, Julia Hammerer, Denis
 *         Fuerst, Daniel Krakow
 *         In Anlehnung an Hr. Prof. Dr. Thies
 */
public class LehrveranstaltungTabelle {

	VerticalPanel mainPanel = new VerticalPanel();

	StundenplanVerwaltungServiceAsync stundenplanVerwaltung = GWT
			.create(StundenplanVerwaltungService.class);
	Dozent shownDozent = null;

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
		flexTable.setText(0, 1, "Bezeichnung");
		flexTable.setText(0, 2, "Semester");
		flexTable.setText(0, 3, "Umfang");
		flexTable.setText(0, 4, "Dozent");

		// Liest alle Einträg aus der Datebank füllt sie in die Tabelle. Bei
		// einem Fehler wird eine Meldung ausgegeben.
		stundenplanVerwaltung
				.getAllLV(new AsyncCallback<Vector<Lehrveranstaltung>>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Ein Fehler ist aufgetreten! - " + caught);

					}

					@Override
					public void onSuccess(Vector<Lehrveranstaltung> result) {
						// Zählt die Zeilenanzahl
						int zeileCounter = 1;
						
						// läuft durch alle erhaltene Objekte
						// Das aktuelle Objekt muss final sein, damit es in der
						// Inner Class ClickHandler
						// verwendet werden kann.
						for (final Lehrveranstaltung lv : result) {
							// Button l = new Button("X");
							// Button b = new Button("Speichern");
							Button bModifizieren = new Button(
									ConstantsStdpln.AENDERN);

							String bezeichnung, semester, umfang, dozent;

							bezeichnung = lv.getBezeichnung();
							semester = String.valueOf(lv.getSemester());
							umfang = String.valueOf(lv.getUmfang());
							dozent = lv.getDozentName();
							
							// Label mit Inhalt füllen
							Label lBez = new Label(bezeichnung);
							Label lSem = new Label(semester);
							Label lUmfang = new Label(umfang);
							Label lDozent = new Label(dozent);
							
							// Label in die die richtige Zeile und Spalte
							// setzen.
							flexTable.setText(zeileCounter, 0, String.valueOf(lv.getId()));
							flexTable.setWidget(zeileCounter, 1, lBez);
							flexTable.setWidget(zeileCounter, 2, lSem);
							flexTable.setWidget(zeileCounter, 3, lUmfang);
							flexTable.setWidget(zeileCounter, 4, lDozent);

							flexTable.setWidget(zeileCounter, 6, bModifizieren);

							// setzt den click handler auf den
							// Modifizieren Button und ruft dann das
							// Form
							// auf und setzt das in der for schleife
							// aktuell durchlaufene Element in das Form.
							bModifizieren.addClickHandler(new ClickHandler() {

								@Override
								public void onClick(ClickEvent event) {
									LehrveranstaltungForm lehrveranstaltungForm = new LehrveranstaltungForm();
									lehrveranstaltungForm.setSelected(lv);
									// Panel leeren und das Formular dafür
									// einfügen
									mainPanel.clear();
									mainPanel.add(lehrveranstaltungForm);
								}
							});
							
							//Zeile hochzählen
							zeileCounter++;
						}

					}

				}

				);
		
		// die Tabelle dem mainPanel hinzufügen und es zurück geben.
		mainPanel.add(flexTable);
		return mainPanel;
	}

}
