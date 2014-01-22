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
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Semesterverband;


/**
 * 
 * @author Selim Karazehir, Julia Hammerer
 *
 */
public class SemesterverbandTabelle extends VerticalPanel{

	VerticalPanel mainPanel = new VerticalPanel();
	
	StundenplanVerwaltungServiceAsync stundenplanVerwaltung = GWT
			.create(StundenplanVerwaltungService.class);
	
	public Widget zeigeTabelle() {

		final FlexTable flexTable = new FlexTable();
		//
		flexTable.setText(0, 0, "ID");
		flexTable.setText(0, 1, "Anzahl Studenten");
		flexTable.setText(0, 2, "Semesterhalbjahr");
		flexTable.setText(0, 3, "Jahrgang");

		// dann irgendwann aufruf der methode von Stundenplanverwaltung

		stundenplanVerwaltung.getAllSemesterverband(new AsyncCallback<Vector<Semesterverband>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Ein Fehler ist aufgetreten! - " + caught);

			}

			@Override
			public void onSuccess(Vector<Semesterverband> result) {
				int zeileCounter = 1;
				for (final Semesterverband semesterverband : result) {
					
					Button bModifizieren = new Button(ConstantsStdpln.AENDERN);

//					final TextBox tbN = new TextBox();
//					final TextBox tbV = new TextBox();
					
					int anzahlStudenten, semesterHalbjahr, jahrgang, id;
					id = semesterverband.getId();
					anzahlStudenten = semesterverband.getAnzahlStudenten();
					semesterHalbjahr = semesterverband.getSemester();
					jahrgang = semesterverband.getJahrgang();
									
					//Label mit Inhalt füllen
				    Label lAnzahlStudenten = new Label(String.valueOf(anzahlStudenten));
				    Label lSemesterHalbjahr = new Label(String.valueOf(semesterHalbjahr));
				    Label lJahrgang = new Label(String.valueOf(jahrgang));
				    Label lId = new Label(String.valueOf(id));
					

					flexTable.setWidget(zeileCounter, 0, lId);
					flexTable.setWidget(zeileCounter, 1, lAnzahlStudenten);
					flexTable.setWidget(zeileCounter, 2, lSemesterHalbjahr);
					flexTable.setWidget(zeileCounter, 3, lJahrgang);

					flexTable.setWidget(zeileCounter, 4, bModifizieren);
//					t.setWidget(i, 7, b);
//					t.setWidget(i, 8, a); #
					
					// TODO Label in Textfeld �ndern.
					bModifizieren.addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							//setzt den click handler auf den Modifizieren Button und ruft dann das Form
							//auf und setzt das in der for schleife aktuell durchlaufene Element in das Form. 
							SemesterverbandForm svForm = new SemesterverbandForm();
							svForm.setSelected(semesterverband);
							//Panel leeren und das Formular dafür einfügen
							mainPanel.clear();
							mainPanel.add(svForm);							
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
