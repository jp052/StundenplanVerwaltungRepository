package de.hdm.gruppe3.stundenplanverwaltung.shared.bo;

import java.util.Vector;

/**
 * @author Yasemin Karakoc, Jan Plank
 * 
 */
public class Raum extends BusinessObject {

	// Instanzvariablen bzw. Membervariablen
	private static final long serialVersionUID = 1L;

	private String bezeichnung;
	private int kapazitaet;
	private Vector<LVDurchfuehrung> durchfuehrungen = new Vector<LVDurchfuehrung>();

	// Konstruktor
	public Raum(String bezeichnung, int kapazitaet,
			Vector<LVDurchfuehrung> durchfuehrungen) {
		this.bezeichnung = bezeichnung;
		this.kapazitaet = kapazitaet;
		this.durchfuehrungen = durchfuehrungen;
	}

	public Raum() {
		// TODO Auto-generated constructor stub
	}

	// Getter und Setter
	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public int getKapazitaet() {
		return kapazitaet;
	}

	public void setKapazitaet(int kapazitaet) {
		this.kapazitaet = kapazitaet;
	}

	public Vector<LVDurchfuehrung> getDurchfuehrungen() {
		return durchfuehrungen;
	}

	public void setDurchfuehrungen(Vector<LVDurchfuehrung> durchfuehrungen) {
		this.durchfuehrungen = durchfuehrungen;
	}
}
