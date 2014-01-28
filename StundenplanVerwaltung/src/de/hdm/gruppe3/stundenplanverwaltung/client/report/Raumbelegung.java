/* 
 * Raumbelegung.java 
 * 
 * Version: 
 *     1.0
 * 
 * Revisions: 
 *     1.0
 */

package de.hdm.gruppe3.stundenplanverwaltung.client.report;

import java.util.Vector;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.gruppe3.stundenplanverwaltung.shared.ConstantsStdpln;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungServiceAsync;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Lehrveranstaltung;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Raum;

/**
 * @author Yasemin Karakoc, Jan Plank, Selim Karazehir, Julia Hammerer, Denis
 *         Fürst, Daniel Krakow 
 *         In Anlehnung an Hr. Prof. Dr. Thies
 */
public class Raumbelegung {

	VerticalPanel mainPanel = new VerticalPanel();
	ListBox raumListBox = new ListBox();

	StundenplanVerwaltungServiceAsync stundenplanVerwaltung = GWT
			.create(StundenplanVerwaltungService.class);

	/**
	 * Die Button und die Listbox wird hier in die eine FlexTable
	 * reingeschrieben. Wenn der Button betätigt wird dann läuft die onClick
	 * Methode und löscht den vorherigen mainPanel. Danach wird die Methode
	 * zeigeRaumbelegung() durchgeführt und die Werte vom Listbox übergeben.
	 * 
	 * @return mainPanel
	 */
	public Widget reportRaumbelegung() {
		FlexTable navigationRaumbelegungReport = new FlexTable();
		navigationRaumbelegungReport.setText(0, 0, "Dozent:");
		Button d = new Button("Report");

		d.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(validiereBenutzerEingabe()) {
					mainPanel.clear();
					zeigeRaumbelegung(raumListBox.getValue(raumListBox
							.getSelectedIndex()));
				}				
			}
		});
		setRaumListBox();
		navigationRaumbelegungReport.setWidget(0, 0, raumListBox);
		navigationRaumbelegungReport.setWidget(0, 1, d);
		mainPanel.clear();
		mainPanel.add(navigationRaumbelegungReport);
		return mainPanel;
	}

	/**
	 * Um die Raumbezeichnung in die den Report zu schreiben, benötigt diese
	 * Methode den Parameter bez, die sie erst von der Methode
	 * reportRaumbelegung() übergeben wird.
	 * 
	 * @see reportRaumbelegung() dann wird ein FlexTable rt instanziiert um die
	 *      Tabelle für die Raumbezeichnung zu gestalten. In der onSuccess()
	 *      Methode wird der Raum rausgelesen und in die nullte Zeile, erste
	 *      Spalte reingeschrieben.
	 * @param raumID
	 */
	public void zeigeRaumbelegung(final String id) {

		final FlexTable raumbezeichnungTabelle = new FlexTable();
		final int raumID;
		raumID = Integer.parseInt(id);
		mainPanel.clear();
		raumbezeichnungTabelle.setText(0, 0, "Raum: ");

		stundenplanVerwaltung.getRaumbyNummer(raumID,
				new AsyncCallback<Raum>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Problem" + caught.getMessage());
					}

					@Override
					public void onSuccess(Raum result) {
						raumbezeichnungTabelle.setText(0, 1, result.getBezeichnung());
						mainPanel.add(raumbezeichnungTabelle);
						zeigeTabelle(raumID);
					}

				});

	}

	/**
	 * Mit dieser Methode wird der Stundenplan für den jeweiligen Raum erzeugt.
	 * Dazu braucht man den Parameter bez. Es wird dann der Grundgerüst eines
	 * Stundenplans gebaut. Anschließend wird die reportLVbyRaum aufgerufen und
	 * den Parameter übergeben. In der onSuccess() Methode wird die
	 * Lehrveranstaltung erst nach dem Tag, dann nach der Anfangs- und Endzeit
	 * gesucht. Diese Werte werden dann mit der Bezeichnung der
	 * Lehrveranstaltung in den Stundenplan reingeschrieben. Am Ende der Methode
	 * wird ein Button erzeugt refresh. Zum Schluss wird alles in den mainPanel
	 * hinzugefügt.
	 * 
	 * @param bez
	 * 
	 */
	public void zeigeTabelle(int raumID) {
		final FlexTable raumbelegungsReportTabelle = new FlexTable();
		
		//HTML class hinzufügen, damit die Tabelle das Design annimmt.
		DOM.setElementAttribute(raumbelegungsReportTabelle.getElement(), "class", "table table-striped table table-bordered");
		
		
		RootPanel.get("starter").clear();
		//
		raumbelegungsReportTabelle.setText(0, 0, "Zeit");
		raumbelegungsReportTabelle.setText(0, 1, "Montag");
		raumbelegungsReportTabelle.setText(0, 2, "Dienstag");
		raumbelegungsReportTabelle.setText(0, 3, "Mittwoch");
		raumbelegungsReportTabelle.setText(0, 4, "Donnerstag");
		raumbelegungsReportTabelle.setText(0, 5, "Freitag");

		raumbelegungsReportTabelle.setText(1, 0, "8-9");
		raumbelegungsReportTabelle.setText(2, 0, "9-10");
		raumbelegungsReportTabelle.setText(3, 0, "10-11");
		raumbelegungsReportTabelle.setText(4, 0, "11-12");
		raumbelegungsReportTabelle.setText(5, 0, "12-13");
		raumbelegungsReportTabelle.setText(6, 0, "13-14");
		raumbelegungsReportTabelle.setText(7, 0, "14-15");
		raumbelegungsReportTabelle.setText(8, 0, "15-16");
		raumbelegungsReportTabelle.setText(9, 0, "16-17");
		raumbelegungsReportTabelle.setText(10, 0, "17-18");

		stundenplanVerwaltung.reportLVbyRaum(raumID,
				new AsyncCallback<Vector<Lehrveranstaltung>>() {

					@Override
					public void onFailure(Throwable caught) {

					}

					@Override
					public void onSuccess(Vector<Lehrveranstaltung> result) {

						int spalteTag = 0;
						int zeitAnf = 0;
						int zeitEnde = 0;
						String[] tage = ConstantsStdpln.WOCHENTAGE;
						int[] zeit = ConstantsStdpln.UHRZEITEN;
						// die For Schleife um den Stundenplan zu bauen
						for (final Lehrveranstaltung lv : result) {

							// Variable für die Flextable position
							String wochentag = lv.getRaumWochentag();
							int raumzeit = lv.getRaumZeit();
							int raumzeitEnde = lv.getRaumZeitEnde();

							// die Erste For schleife sucht nach dem Tag
							for (int tagCounter = 0; tagCounter <= 4; tagCounter++) {
								if (tage[tagCounter].equals(wochentag)) {
									System.out.println(wochentag);
									// hier erhält die Variable die aktuelle
									// Array Index. Es wird auch noch um eins
									// addiert
									// da das die Spalte der Tabelle ist.
									spalteTag = tagCounter + 1;

									// Hier wird nach der Anfangszeit gesucht
									for (int anfZeitCounter = 0; anfZeitCounter < zeit.length; anfZeitCounter++) {
										if (zeit[anfZeitCounter] == raumzeit) {
											System.out.println(raumzeit);

											zeitAnf = anfZeitCounter + 1;

											// Hier wird nach der Endzeit
											// gesucht
											for (int endZeitCounter = 0; endZeitCounter < zeit.length; endZeitCounter++) {
												if (zeit[endZeitCounter] == raumzeitEnde) {
													zeitEnde = endZeitCounter + 1;

													// Die dif enthält die
													// Differenz von Endzeit und
													// Anfangszeit.
													int dif = zeitEnde
															- zeitAnf;
													if ((zeitEnde - zeitAnf) >= 1) {
														// t.setText(zeitAnzahl,
														// anzahlTag,
														// lv.getBezeichnung());
														// t.setText(zeitEnde-1,
														// anzahlTag,
														// lv.getBezeichnung());
														// zeitEnde
														// -=zeitAnzahl;
														// break;

														// Diese Variable wird
														// benötigt um die
														// Zeilen bei mehreren
														// Veranstaltungen zu
														// ergänzen.
														int zeileCounter = 0;

														// Hier wird solange
														// geloopt bis die
														// Variable m kleiner
														// gleich dif ist
														for (int anzahlZeile = 1; anzahlZeile <= dif; anzahlZeile++) {

															raumbelegungsReportTabelle.setText(
																	zeitAnf
																			+ zeileCounter,
																	spalteTag,
																	lv.getBezeichnung());
															zeileCounter++;

															if ((zeitAnf + anzahlZeile) == zeitEnde) {
																raumbelegungsReportTabelle.setText(
																		zeitEnde - 1,
																		spalteTag,
																		lv.getBezeichnung());
																break;
															}
														}
													}
												}
											}

										}
									}
								}
							}
						}

						// Ende der For
					}

				});

		Button refresh = new Button("Zurück");
		refresh.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				Window.Location.reload();
			}
		});
		// mainPanel.add(rt);
		mainPanel.add(raumbelegungsReportTabelle);
		mainPanel.add(refresh);
		RootPanel.get().add(mainPanel);
	}

	public void setRaumListBox() {

		stundenplanVerwaltung.getAllRaeume(new AsyncCallback<Vector<Raum>>() {

			@Override
			public void onFailure(Throwable caught) {
				// ClientsideSettings.getLogger().severe("Bef�llen der DozentenListBox fehlgeschlagen");
			}

			@Override
			public void onSuccess(Vector<Raum> result) {

				raumListBox.addItem("--Bitte wählen--", "0");
				for (Raum r : result) {
					// Der zweite Parameter von addItem ist die gewählte
					// Semesterverband Id welche beim anlegen der
					// Lehrveranstaltung
					// ben�tigt wird.
					raumListBox.addItem(r.getBezeichnung(),
							String.valueOf(r.getId()));

				}

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
		int indexSV = raumListBox.getSelectedIndex();


		if (indexSV < 1) {
			Window.alert("Ein Raum muss gewählt sein!");
			isValid = false;
		}

		return isValid;
	}

}
