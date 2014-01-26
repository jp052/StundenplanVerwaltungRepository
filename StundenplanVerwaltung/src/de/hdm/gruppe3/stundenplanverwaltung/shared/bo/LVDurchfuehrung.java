package de.hdm.gruppe3.stundenplanverwaltung.shared.bo;

/**
 * @author Yasemin Karakoc, Jan Plank
 *
 */
public class LVDurchfuehrung extends BusinessObject {

	// Instanzvariablen bzw. Membervariablen
	private static final long serialVersionUID = 1L;

	private Lehrveranstaltung veranstaltung;
	private Zeitslot zeitslot;
	private Raum raum;
	private Semesterverband semesterverband;

	// Konstruktor
	public LVDurchfuehrung(Lehrveranstaltung veranstaltung,
			Zeitslot zeitslot, Raum raum,
			Semesterverband semesterverband) {
		this.veranstaltung = veranstaltung;
		this.zeitslot = zeitslot;
		this.raum = raum;
		this.semesterverband = semesterverband;
	}
	
	
	public LVDurchfuehrung() {
	}


	// Getter und Setter
	public Lehrveranstaltung getLehrveranstaltung() {
		return veranstaltung;
	}

	public void setLehrveranstaltung(Lehrveranstaltung veranstaltung) {
		this.veranstaltung = veranstaltung;
	}

	public Zeitslot getZeitslot() {
		return zeitslot;
	}

	public void setZeitslot(Zeitslot zeitslot) {
		this.zeitslot = zeitslot;
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
