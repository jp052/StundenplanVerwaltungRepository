package de.hdm.gruppe3.stundenplanverwaltung.shared;

import java.sql.Time;
import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Dozent;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.LVDurchfuehrung;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Lehrveranstaltung;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Raum;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Semesterverband;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Zeitslot;

/**
 * @author Yasemin Karakoc, Jan Plank
 * 
 */

@RemoteServiceRelativePath("stundenplanVerwaltung")
public interface StundenplanVerwaltungService extends RemoteService {

	public Semesterverband anlegenSemesterverband(int semesterhalbjahr,
			int anzahlStudierende, int jahrgang);

	Semesterverband modifizierenSemesterverband(Semesterverband semeserverband);

	public Semesterverband getSemesterverbandByNummer(int nr);

	public Semesterverband getSemesterverbandBySemesterHalbjahr(
			String semesterHalbjahr);

	public Semesterverband loeschenSemesterverband(Semesterverband sv);

	// public Zeitslot anlegenZeitslot (String wochentag, int anfangszeit);

	// public Zeitslot modifizierenZeitslot (Zeitslot z);

	public Zeitslot loeschenZeitslot(Zeitslot z);

	public Zeitslot getZeitslotByNummer(int nr);

	public Raum anlegenRaum(String Bezeichnung, int Kapazitaet);// klein
																// schreiben !

	// public Raum modifizierenRaum (Raum r);

	public Raum loeschenRaum(Raum r);

	public Raum getRaumbyNummer(int nr);

	Lehrveranstaltung anlegenLehrveranstaltung(String Bezeichnung,
			int Semester, int Umfang, int dozentId);

	// public Lehrveranstaltung modifizierenLehrveranstaltung (Lehrveranstaltung
	// lv);

	public Lehrveranstaltung loeschenLehrveranstaltung(Lehrveranstaltung lv);

	public Lehrveranstaltung getLehrveranstaltungByNummer(int nr);

	public Lehrveranstaltung getLehrveranstaltungByBezeichnung(String bez);

	public Dozent anlegenDozent(String Vorname, String Nachname);

	// public Dozent modifizierenDozent (Dozent d);

	public Dozent loeschenDozent(Dozent d);

	public Dozent getDozentByNummer(int nr);

	// public Dozent getDozentByName (String name);


	LVDurchfuehrung anlegenDurchfuehrung(int svId, int raumId, int lvId,
			Zeitslot zeitslot);

	LVDurchfuehrung modifizierenDurchfuehrung(int lvdNr, int svNr, int raumNr,
			int lvNr, int zeitNr);

	LVDurchfuehrung loeschenDurchfuehrung(LVDurchfuehrung d);

	// public LVDurchfuehrung getDurchfuehrungByNummer (int nr);

	// public Raum getRaumByBezeichnung (String bez);

	Vector<Raum> getAllRaeume();

	Lehrveranstaltung modifizierenLehrveranstaltung(Lehrveranstaltung lv);

	Dozent getDozentByName(Dozent name);

	LVDurchfuehrung getDurchfuehrungByNummer(int lvdNr);

	Vector<Dozent> getAllDozenten();

	Dozent modifizierenDozent(Dozent d);

	Raum getRaumByBezeichnung(Raum r);

	Zeitslot anlegenZeitslot(String wochentag);

	Zeitslot modifizierenZeitslot(String wochentag);

	String test(String string);

	Raum modifizierenRaum(Raum r);
	
	Vector<Lehrveranstaltung> getAllLV();

}
