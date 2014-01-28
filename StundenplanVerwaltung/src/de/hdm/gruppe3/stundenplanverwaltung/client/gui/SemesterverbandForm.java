/* 
 * SemesterverbandForm.java 
 * 
 * Version: 
 *     1.0
 * 
 * Revisions: 
 *     1.0
 */

package de.hdm.gruppe3.stundenplanverwaltung.client.gui;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe3.stundenplanverwaltung.shared.ConstantsStdpln;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungServiceAsync;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Semesterverband;


/**
 * @author Yasemin Karakoc, Jan Plank, Selim Karazehir, Julia Hammerer, Denis Fuerst, Daniel Krakow
 *In Anlehnung an Hr. Prof. Dr. Thies
 */
public class SemesterverbandForm extends VerticalPanel {
	// Gui Elemente
	TextBox anzahlStudentenTextBox = new TextBox();
	TextBox semesterHalbjahrTextBox = new TextBox();
	TextBox jahrgangTextBox = new TextBox();
	Label idValueLabel = new Label();
	HorizontalPanel semesterverbandButtonsPanel = new HorizontalPanel();
	Button modifizierenButton = new Button(ConstantsStdpln.AENDERN);
	Button neuButton = new Button(ConstantsStdpln.NEU);
	Button loeschenButton = new Button(ConstantsStdpln.LOESCHEN);

	StundenplanVerwaltungServiceAsync stundenplanVerwaltung = GWT
			.create(StundenplanVerwaltungService.class);
	Semesterverband selectedSemesterverband = null;

	/**
	 * Das Formular wird immer bei Konstruktoraufruf aufgerufen und zeigt alle
	 * GUI Elemente an.
	 */
	public SemesterverbandForm() {
		Grid customerGrid = new Grid(4, 2);
		this.add(customerGrid);

		Label anzahlStudierendeLabel = new Label("Anzahl Studierende");
		customerGrid.setWidget(0, 0, anzahlStudierendeLabel);
		customerGrid.setWidget(0, 1, anzahlStudentenTextBox);

		Label semesterHalbjahrLabel = new Label("Semester");
		customerGrid.setWidget(1, 0, semesterHalbjahrLabel);
		customerGrid.setWidget(1, 1, semesterHalbjahrTextBox);

		Label jahrgangLabel = new Label("Jahrgang");
		customerGrid.setWidget(2, 0, jahrgangLabel);
		customerGrid.setWidget(2, 1, jahrgangTextBox);

		Label idLabel = new Label("ID");
		customerGrid.setWidget(3, 0, idLabel);
		customerGrid.setWidget(3, 1, idValueLabel);

		this.add(semesterverbandButtonsPanel);

		modifizierenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				modifizierenSelectedSemesterverband();
			}
		});

		loeschenButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// l�st das l�schen aus
				loeschenSelectedSemesterverband();
			}
		});

		neuButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				anlegenSelectedSemesterverband();

			}
		});

		showButtons();
	}

	/**
	 * Setzt alle Felder auf den gewählten Wert beim editieren
	 */
	void setFields() {
		anzahlStudentenTextBox.setText(Integer.toString(selectedSemesterverband
				.getAnzahlStudenten()));
		// Integer in String umwandeln
		semesterHalbjahrTextBox.setText(Integer
				.toString(selectedSemesterverband.getSemester()));
		jahrgangTextBox.setText(Integer.toString(selectedSemesterverband
				.getJahrgang()));
		idValueLabel.setText(Integer.toString(selectedSemesterverband.getId()));
	}

	/**
	 * Löscht den Inhalt alle Eingabe Felder.
	 */
	public void clearFields() {
		anzahlStudentenTextBox.setText("");
		semesterHalbjahrTextBox.setText("");
		idValueLabel.setText("");
		jahrgangTextBox.setText("");
	}

	/**
	 * Setzt das gewählte Element zum editieren in die Instanz Variable und
	 * zeigt Buttons und Felder an.
	 * 
	 * @param s
	 *            : Der Semesterverband
	 */
	public void setSelected(Semesterverband s) {
		if (s != null) {
			selectedSemesterverband = s;
			showButtons();
			setFields();
		} else {
			clearFields();
		}
	}

	/**
	 * Zeigt die Buttons an, je nachdem ob Neu oder Ändern.
	 */
	public void showButtons() {
		// Nur wenn Dozent geändert wird, dann werden der modifizieren und
		// löschen Button angezeigt
		if (selectedSemesterverband != null) {
			semesterverbandButtonsPanel.add(modifizierenButton);
			semesterverbandButtonsPanel.add(loeschenButton);
			// neu Button enfernen, da er beim ändern nicht angezeigt werden
			// soll.
			semesterverbandButtonsPanel.remove(neuButton);
		} else {
			semesterverbandButtonsPanel.add(neuButton);

		}

	}

	/**
	 * Ändert das ausgewählte Business Objekt im Editiermodus .
	 */
	public void modifizierenSelectedSemesterverband() {
		final VerticalPanel aktuellePanel = this;
		if (this.selectedSemesterverband != null) {
			// Schauen ob der Benutzer alles richtig eingegeben hat, wenn false
			// zurück kommt wird mit return abgebrochen und die Fehlermeldung
			// angezeit.
			if (!validiereBenutzerEingabe()) {
				return;
			}

			selectedSemesterverband.setAnzahlStudenten(Integer
					.valueOf(anzahlStudentenTextBox.getText()));
			// String in Integer umwandeln
			selectedSemesterverband.setSemester(Integer
					.valueOf(semesterHalbjahrTextBox.getText()));
			selectedSemesterverband.setJahrgang(Integer.valueOf(jahrgangTextBox
					.getText()));
			// Ruft Serverseitige Methode auf
			stundenplanVerwaltung.modifizierenSemesterverband(
					selectedSemesterverband,
					new AsyncCallback<Semesterverband>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Fehler beim ändern!");

						}

						@Override
						public void onSuccess(Semesterverband result) {
							Window.alert("Änderung erfolgreich!");
							//Nach dem Ändern wieder die Tabelle mit allen Einträgen anzeigen.
							SemesterverbandTabelle semTabelle = new SemesterverbandTabelle();
							aktuellePanel.clear();
							aktuellePanel.add(semTabelle.zeigeTabelle());
						}
					});
		}
	}

	/**
	 * Löscht das ausgewählte Business Objekt im Editiermodus
	 */
	public void loeschenSelectedSemesterverband() {
		if (this.selectedSemesterverband != null) {
			final VerticalPanel aktuellePanel = this;
			// Ruft Serverseitige Methode auf
			stundenplanVerwaltung.loeschenSemesterverband(
					selectedSemesterverband,
					new AsyncCallback<Semesterverband>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Fehler beim löschen!");
						}

						@Override
						public void onSuccess(Semesterverband result) {
							Window.alert("Löschen erfolgreich!");
							setSelected(null);
							//Nach dem Löschen wieder die Tabelle mit allen Einträgen anzeigen.
							SemesterverbandTabelle semTabelle = new SemesterverbandTabelle();
							aktuellePanel.clear();
							aktuellePanel.add(semTabelle.zeigeTabelle());

						}

					});
		}
	}

	/**
	 * Legt das das eingegebene Business Objekt an.
	 */
	public void anlegenSelectedSemesterverband() {
		// Schauen ob der Benutzer alles richtig eingegeben hat, wenn false
		// zurück kommt wird mit return abgebrochen und die Fehlermeldung
		// angezeit.
		if (!validiereBenutzerEingabe()) {
			return;
		}

		int anzahlStudenten = Integer.valueOf(anzahlStudentenTextBox.getText());
		int semesterHalbjahr = Integer.valueOf(semesterHalbjahrTextBox
				.getText());
		int jahrgang = Integer.valueOf(jahrgangTextBox.getText());
		// Ruft Serverseitige Methode auf
		stundenplanVerwaltung.anlegenSemesterverband(semesterHalbjahr,
				anzahlStudenten, jahrgang,
				new AsyncCallback<Semesterverband>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Fehler beim anlegen!");

					}

					@Override
					public void onSuccess(Semesterverband result) {
						Window.alert("Anlegen erfolgreich!");
						clearFields();
					}

				});

	}

	/**
	 * Zeigt eine Fehlermeldung wenn der Benutzer etwas falsches eingegeben hat.
	 * 
	 * @return true wenn alles ok ist
	 */
	private boolean validiereBenutzerEingabe() {
		boolean isValid = true;
		// Die indexs der ListBox auslesen um zu schauen ob überall etwas
		// gewählt wurde.
		String studierende = anzahlStudentenTextBox.getText();
		String semesterHalbjahr = semesterHalbjahrTextBox.getText();
		String jahrgang = jahrgangTextBox.getText();

		// Per Regular Expression schauen ob der Name gültig ist
		// Erklärung:
		// ^ = Start derzeile
		// [A-Za-z] = Erlaubt nur Buchstaben von A-Z groß oder klein geschrieben
		// * = Erlaubt beliebing viele Buchstaben von A-Z
		// $ = Ende der Zeile
		// Dann wird geschaut
		if (!studierende.matches("^[1-9][0-9]*$")) {
			Window.alert("Anzahl der Studierenden muss größer 0 sein!");
			isValid = false;
		}

		if (!semesterHalbjahr.matches("^[1-7]{1}$")) {
			Window.alert("Semester muss zwischen 1 und 7 sein!");
			isValid = false;
		}

		if (!jahrgang.matches("^[0-9]{4}$")) {
			Window.alert("Jahrgang muss vierstellige Zahl sein!");
			isValid = false;
		}

		return isValid;
	}

}