package de.hdm.gruppe3.stundenplanverwaltung.shared;

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
 * @author Yasemin Karakoc, Jan Plank, Selim Karazehir, Julia Hammerer, Denis
 *         Fürst, Daniel Krakow 
 *         In Anlehnung an Hr. Prof. Dr. Thies
 */
@RemoteServiceRelativePath("stundenplanVerwaltung")
public interface StundenplanVerwaltungService extends RemoteService {

	public Semesterverband anlegenSemesterverband(int semesterhalbjahr,
			int anzahlStudierende, int jahrgang) throws Exception;

	Semesterverband modifizierenSemesterverband(Semesterverband semeserverband)
			throws Exception;

	public Vector<Semesterverband> getAllSemesterverband() throws Exception;

	public Semesterverband getSemesterverbandByNummer(int nr) throws Exception;

	public Semesterverband getSemesterverbandBySemesterHalbjahr(int sv)
			throws Exception;

	public Semesterverband loeschenSemesterverband(Semesterverband sv) throws Exception;

	// public Zeitslot anlegenZeitslot (String wochentag, int anfangszeit);

	// public Zeitslot modifizierenZeitslot (Zeitslot z);

	public Zeitslot loeschenZeitslot(Zeitslot z) throws Exception;

	public Zeitslot getZeitslotByNummer(int nr) throws Exception;

	public Raum anlegenRaum(String Bezeichnung, int Kapazitaet)
			throws Exception;// klein
	// schreiben !

	// public Raum modifizierenRaum (Raum r);

	public Raum loeschenRaum(Raum r) throws Exception;

	public Raum getRaumbyNummer(int nr) throws Exception;

	Lehrveranstaltung anlegenLehrveranstaltung(String Bezeichnung,
			int Semester, int Umfang, int dozentId) throws Exception;

	// public Lehrveranstaltung modifizierenLehrveranstaltung (Lehrveranstaltung
	// lv);

	public Lehrveranstaltung loeschenLehrveranstaltung(Lehrveranstaltung lv)
			throws Exception;

	public Lehrveranstaltung getLehrveranstaltungByNummer(int nr)
			throws Exception;

	public Lehrveranstaltung getLehrveranstaltungByBezeichnung(String bez)
			throws Exception;

	public Dozent anlegenDozent(String Vorname, String Nachname)
			throws Exception;

	// public Dozent modifizierenDozent (Dozent d);

	public Dozent loeschenDozent(Dozent d) throws Exception;

	public Dozent getDozentByNummer(int nr) throws Exception;

	// public Dozent getDozentByName (String name);

	LVDurchfuehrung anlegenDurchfuehrung(int svId, int raumId, int lvId,
			Zeitslot zeitslot) throws Exception;

	LVDurchfuehrung modifizierenDurchfuehrung(int lvdId, int svId, int raumId,
			int lvId, Zeitslot zeitslot) throws RaumBelegtException, Exception;

	LVDurchfuehrung loeschenDurchfuehrung(LVDurchfuehrung d) throws Exception;

	public Vector<LVDurchfuehrung> getAllDurchfuehrungen() throws Exception;

	// public LVDurchfuehrung getDurchfuehrungByNummer (int nr);

	// public Raum getRaumByBezeichnung (String bez);

	Vector<Raum> getAllRaeume() throws Exception;

	Lehrveranstaltung modifizierenLehrveranstaltung(Lehrveranstaltung lv)
			throws Exception;

	Dozent getDozentByName(Dozent name) throws Exception;

	LVDurchfuehrung getDurchfuehrungByNummer(int lvdNr) throws Exception;

	Vector<Dozent> getAllDozenten() throws Exception;

	Dozent modifizierenDozent(Dozent d) throws Exception;

	Raum getRaumByBezeichnung(Raum r) throws Exception;

	Zeitslot anlegenZeitslot(String wochentag) throws Exception;

	Zeitslot modifizierenZeitslot(String wochentag) throws Exception;

	Raum modifizierenRaum(Raum r) throws Exception;

	Vector<Lehrveranstaltung> getAllLV() throws Exception;

	Vector<Lehrveranstaltung> getLVBySV(int sv);

	// Report
	public Vector<Lehrveranstaltung> reportLVbyDozent(int dozentID)
			throws Exception;

	public Vector<Lehrveranstaltung> reportLVbyRaum(int raumID)
			throws Exception;

	Raum getRaumByBezeichnung(String r) throws Exception;

	Vector<Lehrveranstaltung> reportLVbySV(int sv) throws Exception;

}
