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
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.LVDurchfuehrung;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Semesterverband;


/**
 * 
 * @author Selim Karazehir, Julia Hammerer
 *
 */
public class LVDurchfuehrungTabelle extends VerticalPanel{

	VerticalPanel mainPanel = new VerticalPanel();
	
	StundenplanVerwaltungServiceAsync stundenplanVerwaltung = GWT
			.create(StundenplanVerwaltungService.class);
	
	public Widget zeigeTabelle() {

		final FlexTable flexTable = new FlexTable();
		//Die Tabellen Überschrift
		flexTable.setText(0, 0, "ID");
		flexTable.setText(0, 1, "Zeit");
		flexTable.setText(0, 2, "Tag");
		flexTable.setText(0, 3, "Semesterverband");
		flexTable.setText(0, 4, "Raum");
		flexTable.setText(0, 5, "Lehrveranstaltung");

		

		stundenplanVerwaltung.getAllDurchfuehrungen(new AsyncCallback<Vector<LVDurchfuehrung>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Ein Fehler ist aufgetreten! - " + caught);

			}

			@Override
			public void onSuccess(Vector<LVDurchfuehrung> result) {
				int zeileCounter = 1;
				for (final LVDurchfuehrung lvd : result) {
					
					Button bModifizieren = new Button(ConstantsStdpln.AENDERN);
					
					String zeitslot = lvd.getZeitslot().getAnfangszeit() + "-" + lvd.getZeitslot().getEndzeit();
					String tag = lvd.getZeitslot().getWochentag();
					String sv = lvd.getSemesterverband().toString();
					String raum = lvd.getRaum().getBezeichnung();
					String lv = lvd.getLehrveranstaltung().getBezeichnung();
		
									
					//Label mit Inhalt füllen
					Label lId = new Label(String.valueOf(lvd.getId()));
				    Label lZeitslot = new Label(zeitslot);
				    Label lTag = new Label(tag);
				    Label lSemesterverband = new Label(sv);
				    Label lRaum = new Label(raum);
				    Label lLehrveranstaltung = new Label(lv);
				    
					
				    //Label der Tabelle hinzufügen
					flexTable.setWidget(zeileCounter, 0, lId);
					flexTable.setWidget(zeileCounter, 1, lZeitslot);
					flexTable.setWidget(zeileCounter, 2, lTag);
					flexTable.setWidget(zeileCounter, 3, lSemesterverband);
					flexTable.setWidget(zeileCounter, 4, lRaum);
					flexTable.setWidget(zeileCounter, 5, lLehrveranstaltung);

					flexTable.setWidget(zeileCounter, 6, bModifizieren);
					

					bModifizieren.addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							//setzt den click handler auf den Modifizieren Button und ruft dann das Form
							//auf und setzt das in der for schleife aktuell durchlaufene Element in das Form. 
							LVDurchfuehrungForm lvdForm = new LVDurchfuehrungForm();
							lvdForm.setSelected(lvd);
							//Panel leeren und das Formular dafür einfügen
							mainPanel.clear();
							mainPanel.add(lvdForm);							
						}							
					});
					
					zeileCounter++;
				}
			}
		}

		);
		
		//die Tabelle dem mainPanel hinzufügen und es zurück geben.
		mainPanel.add(flexTable);
		return mainPanel;
	}
}
