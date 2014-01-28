/* 
 * SemesterverbandTabelle.java 
 * 
 * Version: 
 *     1.0
 * 
 * Revisions: 
 *     1.0
 */

package de.hdm.gruppe3.stundenplanverwaltung.client.gui;

import java.util.Vector;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
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
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Semesterverband;

/**
 * Enthält die Tabelle um alle in der Datenbank verfügbaren Elemente anzuzeigen.
 * 
 * @author Yasemin Karakoc, Jan Plank, Selim Karazehir, Julia Hammerer, Denis
 *         Fuerst, Daniel Krakow
 *         In Anlehnung an Hr. Prof. Dr. Thies
 */
public class SemesterverbandTabelle {

	VerticalPanel mainPanel = new VerticalPanel();

	StundenplanVerwaltungServiceAsync stundenplanVerwaltung = GWT
			.create(StundenplanVerwaltungService.class);

	/**
	 * Stellt die Tabelle dar.
	 * 
	 * @return Das Panel mit der Tabelle
	 */
	public VerticalPanel zeigeTabelle() {
		// Eine flexible Tabelle die sich je nach anzahl der vorhandenen
		// Datensätze anpasst.
		final FlexTable flexTable = new FlexTable();
		
		//HTML class hinzufügen, damit die Tabelle das Design annimmt.
		DOM.setElementAttribute(flexTable.getElement(), "class", "table table-striped table table-bordered");
		
		flexTable.setText(0, 0, "ID");
		flexTable.setText(0, 1, "Anzahl Studenten");
		flexTable.setText(0, 2, "Semester");
		flexTable.setText(0, 3, "Jahrgang");

		// Liest alle Einträg aus der Datebank füllt sie in die Tabelle. Bei
		// einem Fehler wird eine Meldung ausgegeben.
		stundenplanVerwaltung
				.getAllSemesterverband(new AsyncCallback<Vector<Semesterverband>>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Ein Fehler ist aufgetreten! - " + caught);

					}

					@Override
					public void onSuccess(Vector<Semesterverband> result) {
						// Zählt die Zeilenanzahl
						int zeileCounter = 1;
						// läuft durch alle erhaltene Objekte
						// Das aktuelle Objekt muss final sein, damit es in der
						// Inner Class ClickHandler
						// verwendet werden kann.

						for (final Semesterverband semesterverband : result) {

							Button bModifizieren = new Button(
									ConstantsStdpln.AENDERN);
							int anzahlStudenten, jahrgang, id;
							String semester = null;
							id = semesterverband.getId();
							anzahlStudenten = semesterverband
									.getAnzahlStudenten();
							semester = semesterverband.toString();
							jahrgang = semesterverband.getJahrgang();

							// Label mit Inhalt füllen
							Label lAnzahlStudenten = new Label(String
									.valueOf(anzahlStudenten));
							Label lSemesterHalbjahr = new Label(semester);
							Label lJahrgang = new Label(String
									.valueOf(jahrgang));
							Label lId = new Label(String.valueOf(id));

							// Label in die die richtige Zeile und Spalte
							// setzen.
							flexTable.setWidget(zeileCounter, 0, lId);
							flexTable.setWidget(zeileCounter, 1,
									lAnzahlStudenten);
							flexTable.setWidget(zeileCounter, 2,
									lSemesterHalbjahr);
							flexTable.setWidget(zeileCounter, 3, lJahrgang);

							flexTable.setWidget(zeileCounter, 4, bModifizieren);

							bModifizieren.addClickHandler(new ClickHandler() {

								@Override
								public void onClick(ClickEvent event) {
									// setzt den click handler auf den
									// Modifizieren Button und ruft dann das
									// Form
									// auf und setzt das in der for schleife
									// aktuell durchlaufene Element in das Form.
									SemesterverbandForm svForm = new SemesterverbandForm();
									svForm.setSelected(semesterverband);
									// Panel leeren und das Formular dafür
									// einfügen
									mainPanel.clear();
									mainPanel.add(svForm);
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
