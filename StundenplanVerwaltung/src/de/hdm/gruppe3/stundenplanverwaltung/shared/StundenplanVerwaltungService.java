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
	
	public Vector<Semesterverband> getAllSemesterverband();

	public Semesterverband getSemesterverbandByNummer(int nr);

	public Semesterverband getSemesterverbandBySemesterHalbjahr(
			int sv);

	public Semesterverband loeschenSemesterverband(Semesterverband sv);

	// public Zeitslot anlegenZeitslot (String wochentag, int anfangszeit);

	// public Zeitslot modifizierenZeitslot (Zeitslot z);

	public Zeitslot loeschenZeitslot(Zeitslot z) throws Exception;

	public Zeitslot getZeitslotByNummer(int nr) throws Exception;

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
			Zeitslot zeitslot) throws Exception;

	LVDurchfuehrung modifizierenDurchfuehrung(int lvdId, int svId, int raumId, int lvId,
			Zeitslot zeitslot) throws RaumBelegtException, Exception;

	LVDurchfuehrung loeschenDurchfuehrung(LVDurchfuehrung d);
	
	public Vector<LVDurchfuehrung> getAllDurchfuehrungen() throws Exception;

	// public LVDurchfuehrung getDurchfuehrungByNummer (int nr);

	// public Raum getRaumByBezeichnung (String bez);

	Vector<Raum> getAllRaeume();

	Lehrveranstaltung modifizierenLehrveranstaltung(Lehrveranstaltung lv);

	Dozent getDozentByName(Dozent name);

	LVDurchfuehrung getDurchfuehrungByNummer(int lvdNr) throws Exception;

	Vector<Dozent> getAllDozenten();

	Dozent modifizierenDozent(Dozent d);

	Raum getRaumByBezeichnung(Raum r);

	Zeitslot anlegenZeitslot(String wochentag) throws Exception;

	Zeitslot modifizierenZeitslot(String wochentag) throws Exception;

	String test(String string);

	Raum modifizierenRaum(Raum r);
	
	Vector<Lehrveranstaltung> getAllLV();

	Vector<Lehrveranstaltung> getLVBySV(int sv);
	
	
	// Report 
	public Vector<Lehrveranstaltung> reportLVbyDozent(int dozentID);
	public Vector<Lehrveranstaltung> reportLVbyRaum(int raumID);

	Raum getRaumByBezeichnung(String r);

	Vector<Lehrveranstaltung> reportLVbySV(int sv);

}
