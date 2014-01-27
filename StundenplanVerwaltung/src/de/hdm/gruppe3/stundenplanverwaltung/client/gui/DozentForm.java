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

import de.hdm.gruppe3.stundenplanverwaltung.shared.*;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Dozent;

/**
 * Enthält alle Elemente und nötigen Methoden für das Dozenten Formular
 * 
 * @author Yasemin Karakoc, Jan Plank, Selim Karazehir, Julia Hammerer, Denis Fuerst, Daniel Krakow
 *In Anlehnung an Hr. Prof. Dr. Thies
 * 
 */
public class DozentForm extends VerticalPanel {

	// Gui Elemente
	TextBox vornameTextBox = new TextBox();
	TextBox nachnameTextBox = new TextBox();
	Label idValueLabel = new Label();
	HorizontalPanel dozentButtonsPanel = new HorizontalPanel();
	Button modifizierenButton = new Button(ConstantsStdpln.AENDERN);
	Button neuButton = new Button(ConstantsStdpln.NEU);
	Button loeschenButton = new Button(ConstantsStdpln.LOESCHEN);

	StundenplanVerwaltungServiceAsync stundenplanVerwaltung = GWT
			.create(StundenplanVerwaltungService.class);
	Dozent selectedDozent = null;

	/**
	 * Das Formular wird immer bei Konstruktoraufruf aufgerufen und zeigt alle
	 * GUI Elemente an.
	 */
	public DozentForm() {
		Grid customerGrid = new Grid(3, 2);
		this.add(customerGrid);

		Label vornameLabel = new Label("Vorname");
		customerGrid.setWidget(0, 0, vornameLabel);
		customerGrid.setWidget(0, 1, vornameTextBox);

		Label lastNameLabel = new Label("Nachname");
		customerGrid.setWidget(1, 0, lastNameLabel);
		customerGrid.setWidget(1, 1, nachnameTextBox);

		// Nur wenn Dozent geändert wird, dann wird das ID Feld angezeigt
		if (selectedDozent != null) {
			Label idLabel = new Label("ID");
			customerGrid.setWidget(2, 0, idLabel);
			customerGrid.setWidget(2, 1, idValueLabel);
		}

		this.add(dozentButtonsPanel);

		modifizierenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				modifizierenSelectedDozent();
			}
		});

		loeschenButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				loeschenDozent();
			}
		});

		neuButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				anlegenDozent();
			}
		});

		showButtons();

	}

	/**
	 * Setzt den Inhalt aller Eingabe Felder auf den gewählten Wert beim
	 * editieren
	 */
	void setFields() {
		vornameTextBox.setText(selectedDozent.getVorname());
		nachnameTextBox.setText(selectedDozent.getNachname());
		idValueLabel.setText(Integer.toString(selectedDozent.getId()));
	}

	/**
	 * Löscht den Inhalt alle Eingabe Felder
	 */
	public void clearFields() {
		vornameTextBox.setText("");
		nachnameTextBox.setText("");
		idValueLabel.setText("");
	}

	/**
	 * Setzt das gewählte Element zum editieren in die Instanz Variable und
	 * zeigt Buttons und Felder an.
	 * 
	 * @param d
	 *            Dozent
	 */
	public void setSelected(Dozent d) {
		if (d != null) {
			selectedDozent = d;
			showButtons();
			setFields();
		} else {
			clearFields();
		}
	}

	/**
	 * Zeigt alle benötigten Buttons an.
	 */
	public void showButtons() {
		// Nur wenn Dozent geändert wird, dann werden der modifizieren und
		// löschen Button angezeigt
		if (selectedDozent != null) {
			dozentButtonsPanel.add(modifizierenButton);
			dozentButtonsPanel.add(loeschenButton);
			dozentButtonsPanel.remove(neuButton);
		} else {
			dozentButtonsPanel.add(neuButton);

		}

	}

	/**
	 * Ändert das ausgewählte Objekt im Editiermodus
	 */
	public void modifizierenSelectedDozent() {
		if (this.selectedDozent != null) {
			selectedDozent.setVorname(vornameTextBox.getText());
			selectedDozent.setNachname(nachnameTextBox.getText());
			stundenplanVerwaltung.modifizierenDozent(selectedDozent,
					new AsyncCallback<Dozent>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Fehler beim ändern!");

						}

						@Override
						public void onSuccess(Dozent result) {
							Window.alert("Änderung erfolgreich!");
						}
					});
		}
	}

	/**
	 * Legt das das eingegebene Business Objekt an
	 */
	public void anlegenDozent() {
		// Schauen ob der Benutzer alles richtig eingegeben hat, wenn false
		// zurück kommt wird mit return abgebrochen und die Fehlermeldung
		// angezeit.
		if (!validiereBenutzerEingabe()) {
			return;
		}

		String vorname = vornameTextBox.getText();
		String nachname = nachnameTextBox.getText();

		stundenplanVerwaltung.anlegenDozent(vorname, nachname,
				new AsyncCallback<Dozent>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Fehler beim anlegen!");
					}

					public void onSuccess(Dozent dozent) {
						Window.alert("Anlegen erfolgreich!");
						clearFields();
					}
				});

	}

	/**
	 * Löscht das ausgewählte Objekt im Editiermodus
	 */
	public void loeschenDozent() {
		stundenplanVerwaltung.loeschenDozent(selectedDozent,
				new AsyncCallback<Dozent>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Fehler beim löschen!");
					}

					@Override
					public void onSuccess(Dozent result) {
						Window.alert("Löschen erfolgreich!");
						setSelected(null);
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
		String vorname = vornameTextBox.getText();
		String nachname = nachnameTextBox.getText();

		// Per Regular Expression schauen ob der Name gültig ist
		// Erklärung:
		// ^ = Start derzeile
		// [A-Za-z] = Erlaubt nur Buchstaben von A-Z groß oder klein geschrieben
		// + = Erlaubt beliebing viele Buchstaben von A-Z, aber mindestens einer
		// muss vorhanden sein
		// $ = Ende der Zeile
		if (!vorname.matches("^[A-Za-z- ]+$") || !nachname.matches("^[A-Za-z- ]+$")) {
			Window.alert("Vorname und Nachname dürfen nur Buchstaben von a-z und keine Umlaute enthalten und dürfen nicht leer sein!");
			isValid = false;
		}

		return isValid;
	}

}
