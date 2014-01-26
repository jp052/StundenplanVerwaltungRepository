package de.hdm.gruppe3.stundenplanverwaltung.client.report;

import java.util.Vector;
import java.sql.Connection;

import sun.awt.HorizBagLayout;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.gruppe3.stundenplanverwaltung.client.ClientsideSettings;
import de.hdm.gruppe3.stundenplanverwaltung.shared.ConstantsStdpln;
import de.hdm.gruppe3.stundenplanverwaltung.shared.ReportGenerator;
import de.hdm.gruppe3.stundenplanverwaltung.shared.ReportGeneratorAsync;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungServiceAsync;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Dozent;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Lehrveranstaltung;

/**
 * @author Yasemin Karakoc, Jan Plank, Selim Karazehir, Julia Hammerer, Denis
 *         Fürst, Daniel Krakow
 *         In Anlehnung an Hr. Prof. Dr. Thies
 */
public class DozentReport {
	VerticalPanel mainPanel = new VerticalPanel();

	StundenplanVerwaltungServiceAsync stundenplanVerwaltung = GWT
			.create(StundenplanVerwaltungService.class);
	ListBox dozentenListBox = new ListBox();
	// ReportGeneratorAsync r = ClientsideSettings.getReportGenerator();
	// ReportGeneratorAsync report = GWT.create(ReportGenerator.class);
	Dozent shownDozent = null;

	public DozentReport() {
	}

	/**
	 * Die Button und die Listbox wird hier in die eine FlexTable
	 * reingeschrieben. Wenn der Button betätigt wird dann läuft die onClick
	 * Methode und löscht den vorherigen mainPanel. Danach wird die Methode
	 * zeigen() durchgeführt und die Werte vom Listbox übergeben.
	 * 
	 * @return mainPanel
	 */
	public Widget reportDozent() {
		FlexTable flexDozent = new FlexTable();
		Button d = new Button("Report");

		mainPanel.clear();
		d.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				mainPanel.clear();
				zeigen(Integer.parseInt(dozentenListBox
						.getValue(dozentenListBox.getSelectedIndex())));

			}
		});

		setDozentenListBox();
		flexDozent.setWidget(0, 0, dozentenListBox);
		flexDozent.setWidget(0, 1, d);
		mainPanel.add(flexDozent);
		return mainPanel;
	}

	/**
	 * Um die Lehrveranstaltungen in die den Report zu schreiben, benötigt diese
	 * Methode den Parameter d, die sie erst von der Methode reportDozent()
	 * übergeben wird.
	 * 
	 * @see reportDozent() dann wird ein FlexTable dt instanziiert um die
	 *      Tabelle für den Dozenten zu gestalten. In der onSuccess() Methode
	 *      wird die Lehrveranstaltung rausgelesen und in die FlexTable t
	 *      reingeschrieben.
	 * @param d
	 */
	public void zeigen(int d) {

		final FlexTable dt = new FlexTable();
		dt.setText(0, 0, "Dozent:");
		RootPanel.get("starter").clear();
		stundenplanVerwaltung.getDozentByNummer(d, new AsyncCallback<Dozent>() {

			@Override
			public void onFailure(Throwable caught) {

			}

			@Override
			public void onSuccess(Dozent result) {
				dt.setText(0, 1, result.getNachname() + ", ");
				dt.setText(0, 2, result.getVorname());

			}

		});

		final FlexTable t = new FlexTable();
		//
		t.setText(0, 0, "Lehrveranstaltung");
		t.setText(0, 1, "Semester");
		t.setText(0, 2, "Anzahl der Studierende");

		stundenplanVerwaltung.reportLVbyDozent(d,
				new AsyncCallback<Vector<Lehrveranstaltung>>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Vector<Lehrveranstaltung> result) {
						// Window.alert("Es wurden " + result.size()
						// + " Eintraege gefunden");
						int i = 1;
						for (final Lehrveranstaltung lv : result) {

							String bezeichnung, semester, umfang;

							bezeichnung = lv.getBezeichnung();
							semester = String.valueOf(lv.getSemester());
							umfang = String.valueOf(lv.getUmfang());

							Label lBez = new Label(bezeichnung);
							Label lSem = new Label(semester);
							Label lUmfang = new Label(umfang);

							t.setWidget(i, 0, lBez);
							t.setWidget(i, 1, lSem);
							t.setWidget(i, 2, lUmfang);
							i++;
						}

					}

				});

		Button refresh = new Button("Zurück");
		refresh.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				Window.Location.reload();
			}
		});

		mainPanel.add(dt);
		mainPanel.add(t);
		mainPanel.add(refresh);
		// return mainPanel;
		RootPanel.get("starter").add(mainPanel);
	}

	public void setDozentenListBox() {

		stundenplanVerwaltung
				.getAllDozenten(new AsyncCallback<Vector<Dozent>>() {

					@Override
					public void onFailure(Throwable caught) {
						// ClientsideSettings.getLogger().severe("Bef�llen der DozentenListBox fehlgeschlagen");
					}

					@Override
					public void onSuccess(Vector<Dozent> result) {

						dozentenListBox.addItem("--Bitte wählen--", "0");
						for (Dozent d : result) {
							// Der zweite Parameter von addItem ist die gew�hlte
							// Dozenten Id welche beim anlegen der
							// Lehrveranstaltung
							// ben�tigt wird.
							dozentenListBox.addItem(d.toString(),
									String.valueOf(d.getId()));

						}

					}
				});
	}

}
