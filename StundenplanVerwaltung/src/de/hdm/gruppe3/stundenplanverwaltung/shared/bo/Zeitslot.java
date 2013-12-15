package de.hdm.gruppe3.stundenplanverwaltung.shared.bo;

import java.util.Vector;


/**
 * @author Yasemin Karakoc, Jan Plank
 *
 */
public class Zeitslot extends BusinessObject {

	// Instanzvariablen bzw. Membervariablen
	private static final long serialVersionUID = 1L;

	private int anfangszeit;
	private int dauer;
	private String Wochentag;
	private Vector<LVDurchfuehrung> durchfuehrungen = new Vector<LVDurchfuehrung>();

	// Konstruktor
	public Zeitslot(int anfangszeit, int dauer, String wochentag,
			Vector<LVDurchfuehrung> durchfuehrungen) {
		this.anfangszeit = anfangszeit;
		this.dauer = dauer;
		Wochentag = wochentag;
		this.durchfuehrungen = durchfuehrungen;
	}
	
	public Zeitslot (){}

	// Getter und Setter
	public int getAnfangszeit() {
		return anfangszeit;
	}

	public void setAnfangszeit(int anfangszeit) {
		this.anfangszeit = anfangszeit;
	}

	public int getDauer() {
		return dauer;
	}

	public void setDauer(int dauer) {
		this.dauer = dauer;
	}

	public String getWochentag() {
		return Wochentag;
	}

	public void setWochentag(String wochentag) {
		Wochentag = wochentag;
	}

	public Vector<LVDurchfuehrung> getDurchfuehrungen() {
		return durchfuehrungen;
	}

	public void setDurchfuehrungen(Vector<LVDurchfuehrung> durchfuehrungen) {
		this.durchfuehrungen = durchfuehrungen;
	}
}
