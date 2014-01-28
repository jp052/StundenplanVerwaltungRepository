/* 
 * Semesterverband.java 
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
public class Semesterverband extends BusinessObject {

	// Instanzvariablen bzw. Membervariablen
	private static final long serialVersionUID = 1L;

	private int semester;
	private int jahrgang;
	private int anzahlStudenten;

	// Konstruktor
	public Semesterverband(int semester, int jahrgang, int anzahlStudenten,
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
	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
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
	
	/**
	 * Erzeugt einen lesbaren String in welchem sichtbar ist um welches Semester es sich handelt.
	 * Vorraussetzung ist, dass sich Studenten nur zum Wintersemester einschreiben können!
	 * @return String mit dem Jahr und dem Semseter z.B: WS 2013-Semester 3
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		//Vorraussetzung ist, dass sich Studenten nur zum Wintersemester einschreiben können!
		//Ansonsten stimmt die Logik zur bestimmung ob ein Semester im Winter Semester (WS) oder dem Sommersemester (SS) nicht.
		//ist die Semesterzahl ungerade ist das Semester im WS, ansonste SS.
		//Der Modulo Operator(%) mit der Zahl 2 liefert  bei einer ungeraden Zahl einne 1 und bei einer geraden Zahl eine 0 zurück.
		String halbjahr = "SS";

		if(this.semester % 2 == 1) {						
			halbjahr = "WS";
		} 
		
		//Beispiel des Stings der in der ListBox angezeigt werden wird:
		//WS 2013-Semester 3
		builder.append(halbjahr); //WS oder SS
		builder.append(" "); //Abstand
		builder.append(this.jahrgang); //Jahr z.B. 2013
		builder.append("-"); //Trennung mit Minus#
		builder.append("Semester " + this.semester); //Aktuelles Semester z.B. Semester 3
		
		//Der erste Prameter von addItem enthält den anzeige String, er wird vom StringBuffer in einen normalen String umgewandelt.
		//Der zweite Parameter von addItem ist die gewählte Semesterverband Id welche beim anlegen der Durchführung 
		//benögtigt wird.
		return builder.toString();
	}

	private Vector<LVDurchfuehrung> durchfuehungen = new Vector<LVDurchfuehrung>();
}
