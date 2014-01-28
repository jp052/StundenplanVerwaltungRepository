/* 
 * SVReport.java 
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
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Semesterverband;

/**
 * @author Yasemin Karakoc, Jan Plank, Selim Karazehir, Julia Hammerer, Denis
 *         Fürst, Daniel Krakow 
 *         In Anlehnung an Hr. Prof. Dr. Thies
 */
public class SVReport {

	VerticalPanel mainPanel = new VerticalPanel();
	ListBox svListBox = new ListBox();
	StundenplanVerwaltungServiceAsync stundenplanVerwaltung = GWT
			.create(StundenplanVerwaltungService.class);

	/**
	 * reportSemesterverband() ist die Maske, die man unter der Menuleiste
	 * Report/Stundenplan für Semesterverband.
	 * 
	 * @return mainPanel
	 */
	public Widget reportSemesterverband() {
		FlexTable navigationSVReport = new FlexTable();
		Button d = new Button("Report");

		d.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				//Wenn nichts gewählt wurde, nicht tun und Meldung ausgeben.
				if(validiereBenutzerEingabe()) {
					mainPanel.clear();
					zeigeSVReport(Integer.parseInt(svListBox.getValue(svListBox
							.getSelectedIndex())));
				}
								
			}
		});

		setSVListBox();
		navigationSVReport.setWidget(0, 0, svListBox);
		navigationSVReport.setWidget(0, 1, d);
		mainPanel.clear();
		mainPanel.add(navigationSVReport);
		return mainPanel;
	}

	/**
	 * Mit dieser Methode können wir den Report rauslesen.
	 * 
	 * @param svId
	 */

	public void zeigeSVReport(final int svId) {

		final FlexTable svBezeichnungTabelle = new FlexTable();
		mainPanel.clear();
		svBezeichnungTabelle.setText(0, 0, "Semesterverband: ");

		stundenplanVerwaltung.getSemesterverbandByNummer(svId,
				new AsyncCallback<Semesterverband>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Problem" + caught.getMessage());
					}

					@Override
					public void onSuccess(Semesterverband result) {

						// Window.alert("Klappt");
						svBezeichnungTabelle.setText(0, 1, String.valueOf(svListBox
								.getItemText(svListBox.getSelectedIndex())));
						mainPanel.add(svBezeichnungTabelle);
						zeigeTabelle(result.getId());
					}
				});

	}

	/**
	 * Diese Methode wird in der Methode zeigeSVReport aufgerufen.
	 * 
	 * @param sv
	 * @return mainPanel
	 */
	public void zeigeTabelle(int sv) {
		final FlexTable svReportTabelle = new FlexTable();
		//HTML class hinzufügen, damit die Tabelle das Design annimmt.
		DOM.setElementAttribute(svReportTabelle.getElement(), "class", "table table-striped table table-bordered");
		
		RootPanel.get("starter").clear();

		//
		svReportTabelle.setText(0, 0, "Zeit");
		svReportTabelle.setText(0, 1, "Montag");
		svReportTabelle.setText(0, 2, "Dienstag");
		svReportTabelle.setText(0, 3, "Mittwoch");
		svReportTabelle.setText(0, 4, "Donnerstag");
		svReportTabelle.setText(0, 5, "Freitag");

		svReportTabelle.setText(1, 0, "8-9");
		svReportTabelle.setText(2, 0, "9-10");
		svReportTabelle.setText(3, 0, "10-11");
		svReportTabelle.setText(4, 0, "11-12");
		svReportTabelle.setText(5, 0, "12-13");
		svReportTabelle.setText(6, 0, "13-14");
		svReportTabelle.setText(7, 0, "14-15");
		svReportTabelle.setText(8, 0, "15-16");
		svReportTabelle.setText(9, 0, "16-17");
		svReportTabelle.setText(10, 0, "17-18");

		stundenplanVerwaltung.reportLVbySV(sv,
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

													// Hier wird geprüft ob die
													// Endzeit nicht kleiner ist
													// als die Anfangszeit
													if ((zeitEnde - zeitAnf) >= 1) {

														// t.setText(zeitAnf,
														// anzahlTag,
														// lv.getBezeichnung());
														// t.setText(zeitEnde-1,
														// anzahlTag,
														// lv.getBezeichnung());
														// zeitEnde -=zeitAnf;
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

															svReportTabelle.setText(
																	zeitAnf
																			+ zeileCounter,
																	spalteTag,
																	lv.getBezeichnung());
															zeileCounter++;

															if ((zeitAnf + anzahlZeile) == zeitEnde) {
																svReportTabelle.setText(
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
		mainPanel.add(svReportTabelle);
		mainPanel.add(refresh);
		RootPanel.get().add(mainPanel);
	}

	public void setSVListBox() {

		stundenplanVerwaltung
				.getAllSemesterverband(new AsyncCallback<Vector<Semesterverband>>() {

					@Override
					public void onFailure(Throwable caught) {
						// ClientsideSettings.getLogger().severe("Bef�llen der DozentenListBox fehlgeschlagen");
					}

					@Override
					public void onSuccess(Vector<Semesterverband> result) {

						svListBox.addItem("--Bitte wählen--", "0");
						for (Semesterverband sv : result) {
							// Der zweite Parameter von addItem ist die gewählte
							// Semesterverband Id welche beim anlegen der
							// Lehrveranstaltung
							// ben�tigt wird.
							svListBox.addItem(sv.toString(),
									String.valueOf(sv.getId()));

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
		int indexSV = svListBox.getSelectedIndex();


		if (indexSV < 1) {
			Window.alert("Ein Semesterverband muss gewählt sein!");
			isValid = false;
		}

		return isValid;
	}

}
