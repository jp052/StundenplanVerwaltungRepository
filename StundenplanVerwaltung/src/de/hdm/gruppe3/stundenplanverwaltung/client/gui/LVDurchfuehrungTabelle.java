/* 
 * LVDurchfuehrungTabelle.java 
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
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.LVDurchfuehrung;

/**
 * Enthält die Tabelle um alle in der Datenbank verfügbaren Elemente anzuzeigen.
 * 
 * @author Yasemin Karakoc, Jan Plank, Selim Karazehir, Julia Hammerer, Denis
 *         Fuerst, Daniel Krakow 
 *         In Anlehnung an Hr. Prof. Dr. Thies
 */
public class LVDurchfuehrungTabelle {

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
		
		//HTML class hinzufügen, damit die Tabelle das Design annimmt.
		DOM.setElementAttribute(flexTable.getElement(), "class", "table table-striped table table-bordered");
		
		// Die Tabellen Überschrift
		flexTable.setText(0, 0, "ID");
		flexTable.setText(0, 1, "Zeit");
		flexTable.setText(0, 2, "Tag");
		flexTable.setText(0, 3, "Semesterverband");
		flexTable.setText(0, 4, "Raum");
		flexTable.setText(0, 5, "Lehrveranstaltung");

		// Liest alle Einträg aus der Datebank füllt sie in die Tabelle. Bei
		// einem Fehler wird eine Meldung ausgegeben.
		stundenplanVerwaltung
				.getAllDurchfuehrungen(new AsyncCallback<Vector<LVDurchfuehrung>>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Ein Fehler ist aufgetreten! - " + caught);

					}

					@Override
					public void onSuccess(Vector<LVDurchfuehrung> result) {
						// Zählt die Zeilenanzahl
						int zeileCounter = 1;

						// läuft durch alle erhaltene Objekte
						// Das aktuelle Objekt muss final sein, damit es in der
						// Inner Class ClickHandler
						// verwendet werden kann.
						for (final LVDurchfuehrung lvd : result) {

							Button bModifizieren = new Button(
									ConstantsStdpln.AENDERN);

							String zeitslot = lvd.getZeitslot()
									.getAnfangszeit()
									+ "-"
									+ lvd.getZeitslot().getEndzeit();
							String tag = lvd.getZeitslot().getWochentag();
							String sv = lvd.getSemesterverband().toString();
							String raum = lvd.getRaum().getBezeichnung();
							String lv = lvd.getLehrveranstaltung()
									.getBezeichnung();

							// Label mit Inhalt füllen
							Label lId = new Label(String.valueOf(lvd.getId()));
							Label lZeitslot = new Label(zeitslot);
							Label lTag = new Label(tag);
							Label lSemesterverband = new Label(sv);
							Label lRaum = new Label(raum);
							Label lLehrveranstaltung = new Label(lv);

							// Label in die die richtige Zeile und Spalte
							// setzen.
							flexTable.setWidget(zeileCounter, 0, lId);
							flexTable.setWidget(zeileCounter, 1, lZeitslot);
							flexTable.setWidget(zeileCounter, 2, lTag);
							flexTable.setWidget(zeileCounter, 3,
									lSemesterverband);
							flexTable.setWidget(zeileCounter, 4, lRaum);
							flexTable.setWidget(zeileCounter, 5,
									lLehrveranstaltung);

							flexTable.setWidget(zeileCounter, 6, bModifizieren);

							// setzt den click handler auf den
							// Modifizieren Button und ruft dann das
							// Form
							// auf und setzt das in der for schleife
							// aktuell durchlaufene Element in das Form.
							bModifizieren.addClickHandler(new ClickHandler() {

								@Override
								public void onClick(ClickEvent event) {
									LVDurchfuehrungForm lvdForm = new LVDurchfuehrungForm();
									lvdForm.setSelected(lvd);
									// Panel leeren und das Formular dafür
									// einfügen
									mainPanel.clear();
									mainPanel.add(lvdForm);
								}
							});
							// Zeile hochzählen
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
