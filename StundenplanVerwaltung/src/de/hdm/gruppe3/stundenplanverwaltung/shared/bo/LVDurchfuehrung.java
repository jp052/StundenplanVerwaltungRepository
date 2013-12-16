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
	private Semesterverband sv;
	
	private int r;
	private int semesterverband;
	private int lvId;
	private int zIds;

	// Konstruktor
	public LVDurchfuehrung(Lehrveranstaltung veranstaltung,
			Vector<Zeitslot> zeitslots, Raum raum,
			Semesterverband semesterverband) {
		this.veranstaltung = veranstaltung;
		this.zeitslots = zeitslots;
		this.raum = raum;
		this.sv = sv;
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
	

	public int getLvId() {
		return lvId;
	}


	public void setLV(int lvId) {
		this.lvId = lvId;
	}

	public Vector<Zeitslot> getZeitslots() {
		return zeitslots;
	}

	public void setZeitslots(Vector<Zeitslot> zeitslots) {
		this.zeitslots = zeitslots;
	}

	public int getRaum() {
		return r;
	}

	public void setRaum(int raumId) {
		r = raumId;
	}

	public int getSemesterverband() {
		return semesterverband;
	}

	public void setSemesterverband(int semesterverband) {
		this.semesterverband = semesterverband;
	}


	public void setZIds(int zIds) {
		// TODO Auto-generated method stub
		
	}
	
	public int getZIds(){
		return zIds;
	}

}
