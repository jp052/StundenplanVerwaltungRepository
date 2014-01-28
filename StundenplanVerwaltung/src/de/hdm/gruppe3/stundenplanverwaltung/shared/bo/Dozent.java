/* 
 * Dozent.java 
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
public class Dozent extends BusinessObject {

	// Instanzvariablen bzw. Membervariablen
	private static final long serialVersionUID = 1L;

	private String vorname;
	private String nachname;
	private Vector<Lehrveranstaltung> veranstaltungen = new Vector<Lehrveranstaltung>();

	// Konstruktor
	public Dozent(String vorname, String nachname,
			Vector<Lehrveranstaltung> veranstaltungen) {
		this.vorname = vorname;
		this.nachname = nachname;
		this.veranstaltungen = veranstaltungen;
	}
	
	public Dozent(){
		
	}

	// Getter und Setter
	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public Vector<Lehrveranstaltung> getVeranstaltungen() {
		return veranstaltungen;
	}

	public void setVeranstaltungen(Vector<Lehrveranstaltung> veranstaltungen) {
		this.veranstaltungen = veranstaltungen;
	}
	
	public String toString(){
		return vorname + " " + nachname;
	}

}
