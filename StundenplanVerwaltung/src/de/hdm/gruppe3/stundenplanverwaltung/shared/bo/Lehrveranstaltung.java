/* 
 * Lehrveranstaltung.java 
 * 
 * Version: 
 *     1.0
 * 
 * Revisions: 
 *     1.0
 */

package de.hdm.gruppe3.stundenplanverwaltung.shared.bo;

import java.util.Vector;

/**
 * @author Yasemin Karakoc, Jan Plank
 *
 */
public class Lehrveranstaltung extends BusinessObject {

	// Instanzvariablen bzw. Membervariablen
	private static final long serialVersionUID = 1L;

	private String bezeichnung;
	private int umfang;
	private int semester;
	private Dozent dozent;
	private String dozentString;
	private String raumWochentag;
	private int raumZeit;
	private int raumZeitEnde;
	
	public int getRaumZeitEnde() {
		return raumZeitEnde;
	}

	public void setRaumZeitEnde(int raumZeitEnde) {
		this.raumZeitEnde = raumZeitEnde;
	}

	public String getRaumWochentag() {
		return raumWochentag;
	}

	public void setRaumWochentag(String raumWochentag) {
		this.raumWochentag = raumWochentag;
	}

	public int getRaumZeit() {
		return raumZeit;
	}

	public void setRaumZeit(int raumZeit) {
		this.raumZeit = raumZeit;
	}

	private Vector<LVDurchfuehrung> durchfuehungen = new Vector<LVDurchfuehrung>();

	// Konstruktor
	public Lehrveranstaltung(String bezeichnung, int umfang, int semester,
			Dozent dozent, Vector<LVDurchfuehrung> durchfuehungen) {
		this.bezeichnung = bezeichnung;
		this.umfang = umfang;
		this.semester = semester;
		this.dozent = dozent;
		this.durchfuehungen = durchfuehungen;
	}
	
	public Lehrveranstaltung(){
		
	}

	// Getter und Setter
	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public int getUmfang() {
		return umfang;
	}

	public void setUmfang(int umfang) {
		this.umfang = umfang;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public Dozent getDozent() {
		return dozent;
	}

	public void setDozent(Dozent dozent) {
		this.dozent = dozent;
	}
	
	public void setDozentName(String n){
		this.dozentString = n;
	}
	
	public String getDozentName(){
		return this.dozentString;
	}

	public Vector<LVDurchfuehrung> getDurchfuehungen() {
		return durchfuehungen;
	}

	public void setDurchfuehungen(Vector<LVDurchfuehrung> durchfuehungen) {
		this.durchfuehungen = durchfuehungen;
	}
}
