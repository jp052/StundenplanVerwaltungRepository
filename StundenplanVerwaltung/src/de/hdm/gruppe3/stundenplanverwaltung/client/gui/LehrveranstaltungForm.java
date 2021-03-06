/* 
 * LehrveranstaltungsForm.java 
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe3.stundenplanverwaltung.shared.ConstantsStdpln;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungServiceAsync;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Dozent;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Lehrveranstaltung;

/**
 * Enthält alle Elemente und nötigen Methoden für das Lehrveranstaltung Formular
 * 
 * @author Yasemin Karakoc, Jan Plank, Selim Karazehir, Julia Hammerer, Denis Fuerst, Daniel Krakow
 *In Anlehnung an Hr. Prof. Dr. Thies
 */
public class LehrveranstaltungForm extends VerticalPanel {

	// Gui Elemente
	TextBox bezeichnungTextBox = new TextBox();
	TextBox semesterTextBox = new TextBox();
	TextBox umfangTextBox = new TextBox();
	ListBox dozentenListBox = new ListBox();
	Label idValueLabel = new Label();
	HorizontalPanel lehrveranstaltungsButtonsPanel = new HorizontalPanel();
	Button modifizierenButton = new Button(ConstantsStdpln.AENDERN);
	Button neuButton = new Button(ConstantsStdpln.NEU);
	Button loeschenButton = new Button(ConstantsStdpln.LOESCHEN);

	StundenplanVerwaltungServiceAsync stundenplanVerwaltung = GWT
			.create(StundenplanVerwaltungService.class);
	Lehrveranstaltung selectedLehrveranstaltung = null;

	/**
	 * Das Formular wird immer bei Konstruktoraufruf aufgerufen und zeigt alle
	 * GUI Elemente an.
	 */
	public LehrveranstaltungForm() {
		Grid customerGrid = new Grid(5, 2);
		this.add(customerGrid);

		Label bezeichnungStudentenLabel = new Label("Bezeichnung");
		customerGrid.setWidget(0, 0, bezeichnungStudentenLabel);
		customerGrid.setWidget(0, 1, bezeichnungTextBox);

		Label semesterLabel = new Label("Semester");
		customerGrid.setWidget(1, 0, semesterLabel);
		customerGrid.setWidget(1, 1, semesterTextBox);

		Label jahrgangLabel = new Label("Anzahl der Studierenden");
		customerGrid.setWidget(2, 0, jahrgangLabel);
		customerGrid.setWidget(2, 1, umfangTextBox);

		Label dozentLabel = new Label("Dozent");
		customerGrid.setWidget(3, 0, dozentLabel);
		customerGrid.setWidget(3, 1, dozentenListBox);

		// dozentenListBox f�llen
		setDozentenListBox();

		Label idLabel = new Label("ID");
		customerGrid.setWidget(4, 0, idLabel);
		customerGrid.setWidget(4, 1, idValueLabel);

		this.add(lehrveranstaltungsButtonsPanel);

		modifizierenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				modifizierenSelectedLehrveranstaltung();
			}
		});

		loeschenButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// l�st das l�schen aus
				loeschenSelectedLehrveranstaltung();
			}
		});

		neuButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				anlegenLehrveranstaltung();

			}
		});

		// Buttons anzeigen
		showButtons();
	}

	/**
	 * F�llt die dozentenListBox mit allen in der Datenbank vorhandenen
	 * Dozenten.
	 * 
	 */
	public void setDozentenListBox() {

		stundenplanVerwaltung
				.getAllDozenten(new AsyncCallback<Vector<Dozent>>() {

					@Override
					public void onFailure(Throwable caught) {
						// ClientsideSettings.getLogger().severe("Bef�llen der DozentenListBox fehlgeschlagen");
					}

					@Override
					public void onSuccess(Vector<Dozent> result) {
						// Listbox leeren falls schon alte Werte drin sind
						dozentenListBox.clear();

						dozentenListBox.addItem("--Bitte wählen--", "0");
						for (Dozent d : result) {
							// Der zweite Parameter von addItem ist die gew�hlte
							// Dozenten Id welche beim anlegen der
							// Lehrveranstaltung
							// ben�tigt wird.
							dozentenListBox.addItem(d.toString(),
									String.valueOf(d.getId()));

						}

						// ListBox fürs editieren setzen.
						selectCurrentDozent();

					}
				});
	}

	/**
	 * Wählt den aktuellen dozent aus wenn man im editier modus ist.
	 */
	private void selectCurrentDozent() {
		if (selectedLehrveranstaltung != null) {
			int dozentId = selectedLehrveranstaltung.getDozent().getId();

			for (int pos = 0; pos < dozentenListBox.getItemCount(); pos++) {
				// Erzeugt einen int aus der value in der listbox
				// Holt die dozentId der gewählten Lehrveranstaltung
				// Wenn die beiden Werte gleich sind, dann soll die Position in
				// der Select

				int currentValue = Integer.valueOf(dozentenListBox
						.getValue(pos));

				if (currentValue == dozentId) {
					dozentenListBox.setSelectedIndex(pos);
				}
			}
		}
	}

	/**
	 * Setzt das gewählte Element zum editieren in die Instanz Variable und
	 * zeigt Buttons und Felder an.
	 * 
	 * @param lv
	 *            : Die Lehrveranstaltung
	 */
	public void setSelected(Lehrveranstaltung lv) {
		if (lv != null) {
			selectedLehrveranstaltung = lv;
			showButtons();
			setFields();
		} else {
			clearFields();
		}
	}

	/**
	 * Setzt alle Felder auf den gewählten Wert beim editieren
	 */
	public void setFields() {
		bezeichnungTextBox.setText(selectedLehrveranstaltung.getBezeichnung());
		// Integer in String umwandeln
		semesterTextBox.setText(Integer.toString(selectedLehrveranstaltung
				.getSemester()));
		umfangTextBox.setText(Integer.toString(selectedLehrveranstaltung
				.getUmfang()));
		idValueLabel
				.setText(Integer.toString(selectedLehrveranstaltung.getId()));

		// Die Listbox mit dem aktuelle Dozent setzen.
		this.setDozentenListBox();

	}

	/**
	 * Löscht den Inhalt alle Eingabe Felder
	 */
	public void clearFields() {
		bezeichnungTextBox.setText("");
		semesterTextBox.setText("");
		idValueLabel.setText("");
		umfangTextBox.setText("");
	}

	/**
	 * Zeigt alle benötigten Buttons an.
	 */
	public void showButtons() {
		// Nur wenn Dozent geändert wird, dann werden der modifizieren und
		// löschen Button angezeigt
		if (selectedLehrveranstaltung != null) {
			lehrveranstaltungsButtonsPanel.add(modifizierenButton);
			lehrveranstaltungsButtonsPanel.add(loeschenButton);
			lehrveranstaltungsButtonsPanel.remove(neuButton);
		} else {
			lehrveranstaltungsButtonsPanel.add(neuButton);

		}
	}

	/**
	 * Ändert das ausgewählte Business Objekt im Editiermodus
	 */
	public void modifizierenSelectedLehrveranstaltung() {
		final VerticalPanel aktuellePanel = this;

		if (this.selectedLehrveranstaltung != null) {
			// Schauen ob der Benutzer alles richtig eingegeben hat, wenn false
			// zurück kommt wird mit return abgebrochen und die Fehlermeldung
			// angezeit.
			if (!validiereBenutzerEingabe()) {
				return;
			}

			selectedLehrveranstaltung.setBezeichnung(bezeichnungTextBox
					.getText());
			// String in Integer umwandeln
			selectedLehrveranstaltung.setSemester(Integer
					.valueOf(semesterTextBox.getText()));
			selectedLehrveranstaltung.setUmfang(Integer.valueOf(umfangTextBox
					.getText()));

			// Dozent Object das nur die Id enth�lt, mehr ist nicht vorhanden
			// und auch nicht n�tig.
			Dozent d = new Dozent();
			int dozentId = Integer.valueOf(dozentenListBox
					.getValue(dozentenListBox.getSelectedIndex()));
			d.setId(dozentId);

			selectedLehrveranstaltung.setDozent(d);
			// Ruft Serverseitige Methode auf
			stundenplanVerwaltung.modifizierenLehrveranstaltung(
					selectedLehrveranstaltung,
					new AsyncCallback<Lehrveranstaltung>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Fehler beim ändern!");

						}

						@Override
						public void onSuccess(Lehrveranstaltung result) {
							Window.alert("Änderung erfolgreich!");
							//Nach dem Ändern wieder die Tabelle mit allen Einträgen anzeigen.
							LehrveranstaltungTabelle lvTabelle = new LehrveranstaltungTabelle();
							aktuellePanel.clear();
							aktuellePanel.add(lvTabelle.zeigeTabelle());
						}
					});
		}
	}

	/**
	 * Löscht das ausgewählte Business Objekt im Editiermodus
	 */
	public void loeschenSelectedLehrveranstaltung() {
		final VerticalPanel aktuellePanel = this;
		if (this.selectedLehrveranstaltung != null) {
			// Ruft Serverseitige Methode auf
			stundenplanVerwaltung.loeschenLehrveranstaltung(
					selectedLehrveranstaltung,
					new AsyncCallback<Lehrveranstaltung>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Fehler beim löschen!");

						}

						@Override
						public void onSuccess(Lehrveranstaltung result) {
							Window.alert("Löschen erfolgreich!");
							setSelected(null);
							//Nach dem Löschen wieder die Tabelle mit allen Einträgen anzeigen.
							LehrveranstaltungTabelle lvTabelle = new LehrveranstaltungTabelle();
							aktuellePanel.clear();
							aktuellePanel.add(lvTabelle.zeigeTabelle());
						}
					});
		}
	}

	/**
	 * Legt das das ausgewählte Business Objekt an
	 */
	public void anlegenLehrveranstaltung() {
		// Schauen ob der Benutzer alles richtig eingegeben hat, wenn false
		// zurück kommt wird mit return abgebrochen und die Fehlermeldung
		// angezeit.
		if (!validiereBenutzerEingabe()) {
			return;
		}

		String bezeichnung = bezeichnungTextBox.getText();
		int semester = Integer.valueOf(semesterTextBox.getText());
		int umfang = Integer.valueOf(umfangTextBox.getText());
		int dozentId = Integer.valueOf(dozentenListBox.getValue(dozentenListBox
				.getSelectedIndex()));
		// Ruft Serverseitige Methode auf
		stundenplanVerwaltung.anlegenLehrveranstaltung(bezeichnung, semester,
				umfang, dozentId, new AsyncCallback<Lehrveranstaltung>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Fehler beim anlegen!");

					}

					@Override
					public void onSuccess(Lehrveranstaltung result) {
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
		String bezeichnung = bezeichnungTextBox.getText();
		String semester = semesterTextBox.getText();
		String umfang = umfangTextBox.getText();
		int indexDozent = dozentenListBox.getSelectedIndex();

		// Per Regular Expression schauen ob der Name gültig ist
		// Erklärung:
		// ^ = Start derzeile
		// [A-Za-z] = Erlaubt nur Buchstaben von A-Z groß oder klein geschrieben
		// * = Erlaubt beliebing viele Buchstaben von A-Z
		// $ = Ende der Zeile
		// {1} = Darf nur 1 mal vorkommen
		// Dann wird geschaut
		if (!bezeichnung.matches("^[A-Za-z0-9- ]+$")) {
			Window.alert("Bezeichnung darf nur Buchstaben von a-z und keine Umlaute enthalten und darf nicht leer sein!");
			isValid = false;
		}
		if (!semester.matches("^[1-7]{1}$")) {
			Window.alert("Semester muss zwischen 1 und 7 sein!");
			isValid = false;
		}
		if (!umfang.matches("^[1-9][0-9]*$")) {
			Window.alert("Anzahl der Studierenden muss 1 oder mehr sein!");
			isValid = false;
		}
		if (indexDozent < 1) {
			Window.alert("Ein Dozent muss gewählt sein!");
			isValid = false;
		}

		return isValid;
	}

}