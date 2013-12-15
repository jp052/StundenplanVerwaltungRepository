package de.hdm.gruppe3.stundenplanverwaltung.shared.bo;

import java.util.Vector;


/**
 * @author Yasemin Karakoc, Jan Plank
 *
 */
public class Semesterverband extends BusinessObject {

	// Instanzvariablen bzw. Membervariablen
	private static final long serialVersionUID = 1L;

	private String semester;
	private int jahrgang;
	private int anzahlStudenten;

	// Konstruktor
	public Semesterverband(String semester, int jahrgang, int anzahlStudenten,
			Vector<LVDurchfuehrung> durchfuehungen) {
		this.semester = semester;
		this.jahrgang = jahrgang;
		this.anzahlStudenten = anzahlStudenten;
		this.durchfuehungen = durchfuehungen;
	}

	public Semesterverband() {
		// TODO Auto-generated constructor stub
	}

	// Getter und Setter
	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public int getJahrgang() {
		return jahrgang;
	}

	public void setJahrgang(int jahrgang) {
		this.jahrgang = jahrgang;
	}

	public int getAnzahlStudenten() {
		return anzahlStudenten;
	}

	public void setAnzahlStudenten(int anzahlStudenten) {
		this.anzahlStudenten = anzahlStudenten;
	}

	public Vector<LVDurchfuehrung> getDurchfuehungen() {
		return durchfuehungen;
	}

	public void setDurchfuehungen(Vector<LVDurchfuehrung> durchfuehungen) {
		this.durchfuehungen = durchfuehungen;
	}

	private Vector<LVDurchfuehrung> durchfuehungen = new Vector<LVDurchfuehrung>();
}
