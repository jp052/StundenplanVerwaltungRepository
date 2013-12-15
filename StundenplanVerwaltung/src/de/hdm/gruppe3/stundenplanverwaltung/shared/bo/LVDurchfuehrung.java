package de.hdm.gruppe3.stundenplanverwaltung.shared.bo;

import java.util.Vector;

/**
 * @author Yasemin Karakoc, Jan Plank
 *
 */
public class LVDurchfuehrung extends BusinessObject {

	// Instanzvariablen bzw. Membervariablen
	private static final long serialVersionUID = 1L;

	private Lehrveranstaltung veranstaltung;
	private Vector<Zeitslot> zeitslots = new Vector<Zeitslot>();
	private Raum raum;
	private Semesterverband semesterverband;

	// Konstruktor
	public LVDurchfuehrung(Lehrveranstaltung veranstaltung,
			Vector<Zeitslot> zeitslots, Raum raum,
			Semesterverband semesterverband) {
		this.veranstaltung = veranstaltung;
		this.zeitslots = zeitslots;
		this.raum = raum;
		this.semesterverband = semesterverband;
	}
	
	
	public LVDurchfuehrung() {
	}


	// Getter und Setter
	public Lehrveranstaltung getVeranstaltung() {
		return veranstaltung;
	}

	public void setVeranstaltung(Lehrveranstaltung veranstaltung) {
		this.veranstaltung = veranstaltung;
	}

	public Vector<Zeitslot> getZeitslots() {
		return zeitslots;
	}

	public void setZeitslots(Vector<Zeitslot> zeitslots) {
		this.zeitslots = zeitslots;
	}

	public Raum getRaum() {
		return raum;
	}

	public void setRaum(Raum raum) {
		this.raum = raum;
	}

	public Semesterverband getSemesterverband() {
		return semesterverband;
	}

	public void setSemesterverband(Semesterverband semesterverband) {
		this.semesterverband = semesterverband;
	}

}
