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
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Raum;

/**
 * Enthält die Tabelle um alle in der Datenbank verfügbaren Elemente anzuzeigen.
 * 
 * @author Yasemin Karakoc, Jan Plank, Selim Karazehir, Julia Hammerer, Denis
 *         Fuerst, Daniel Krakow
 *         In Anlehnung an Hr. Prof. Dr. Thies
 */
public class RaumTabelle {

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
		
		flexTable.setText(0, 0, "ID");
		flexTable.setText(0, 1, "Raum");
		flexTable.setText(0, 2, "Kapazität");

		// Liest alle Einträg aus der Datebank füllt sie in die Tabelle. Bei
		// einem Fehler wird eine Meldung ausgegeben.
		stundenplanVerwaltung.getAllRaeume(new AsyncCallback<Vector<Raum>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Ein Fehler ist aufgetreten! - " + caught);

			}

			@Override
			public void onSuccess(Vector<Raum> result) {
				// Zählt die Zeilenanzahl
				int zeileCounter = 1;
				
				// läuft durch alle erhaltene Objekte
				// Das aktuelle Objekt muss final sein, damit es in der
				// Inner Class ClickHandler
				// verwendet werden kann.
				for (final Raum r : result) {
					Button a = new Button(ConstantsStdpln.AENDERN);

					String raumNr, bezeichnung, kapa;

					bezeichnung = r.getBezeichnung();
					raumNr = String.valueOf(r.getId());
					kapa = String.valueOf(r.getKapazitaet());
					
					// Label mit Inhalt füllen
					Label lBez = new Label(bezeichnung);
					Label lRaumnr = new Label(raumNr);
					Label lKapa = new Label(kapa);
					
					// Label in die die richtige Zeile und Spalte
					// setzen.
					flexTable.setText(zeileCounter, 0, String.valueOf(r.getId()));
					flexTable.setWidget(zeileCounter, 1, lBez);
					flexTable.setWidget(zeileCounter, 2, lKapa);


					flexTable.setWidget(zeileCounter, 6, a);

					// setzt den click handler auf den
					// Modifizieren Button und ruft dann das
					// Form
					// auf und setzt das in der for schleife
					// aktuell durchlaufene Element in das Form.
					a.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							RaumForm raumForm = new RaumForm();
							raumForm.setSelected(r);
							// Panel leeren und das Formular dafür
							// einfügen
							mainPanel.clear();
							mainPanel.add(raumForm);

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
