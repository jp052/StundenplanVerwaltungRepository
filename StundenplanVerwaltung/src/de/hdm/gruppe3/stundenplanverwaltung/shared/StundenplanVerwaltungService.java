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
	
	public Semesterverband anlegenSemesterverband(String semesterhalbjahr, int anzahlStudierende, int jahrgang);

	public Semesterverband modifizierenSemesterverband (String semesterhalbjahr, int anzahlStudierende, int jahrgang); //StundenplanVewaltung anpassen)

	public Semesterverband getSemesterverbandByNummer (int nr);

	public Semesterverband getSemesterverbandBySemesterHalbjahr (String semesterHalbjahr);

	public Semesterverband loeschenSemesterverband (Semesterverband sv);

	//public Zeitslot anlegenZeitslot (String wochentag, int anfangszeit);

	//public Zeitslot modifizierenZeitslot (Zeitslot z);

	public Zeitslot loeschenZeitslot (Zeitslot z);

	public Zeitslot getZeitslotByNummer (int nr);

	public Raum anlegenRaum (String Bezeichnung, int Kapazitaet);//klein schreiben !

	//public Raum modifizierenRaum (Raum r);

	public Raum loeschenRaum (Raum r);

	public Raum getRaumbyNummer (int nr);

	public Lehrveranstaltung anlegenLehrveranstaltung (String Bezeichnung, int Semester, int Umfang);

	//public Lehrveranstaltung modifizierenLehrveranstaltung (Lehrveranstaltung lv);

	public Lehrveranstaltung loeschenLehrveranstaltung (Lehrveranstaltung lv);

	public Lehrveranstaltung getLehrveranstaltungByNummer (int nr);

	public Lehrveranstaltung getLehrveranstaltungByBezeichnung (String bez);

	public Dozent anlegenDozent (String Vorname, String Nachname);

	//public Dozent modifizierenDozent (Dozent d);

	public Dozent loeschenDozent (Dozent d);

	public Dozent getDozentByNummer (int nr);

	//public Dozent getDozentByName (String name);

	public LVDurchfuehrung anlegenDurchfuehrung (int svId, int raumId, int lvId, int zIds);

	public LVDurchfuehrung modifizierenDurchfuehrung (int svId, int raumId, int lvId, int zIds);

	LVDurchfuehrung loeschenDurchfuehrung(LVDurchfuehrung d);

	//public LVDurchfuehrung getDurchfuehrungByNummer (int nr);

	//public Raum getRaumByBezeichnung (String bez);

	Vector<Raum> getAllRaeume();

	Lehrveranstaltung modifizierenLehrveranstaltung(String bezeichnung,
			int semester, int umfang);

	Dozent getDozentByName(Dozent name);

	LVDurchfuehrung getDurchfuehrungByNummer(LVDurchfuehrung nr);
	
	Vector<Dozent> getAllDozenten();

	Dozent modifizierenDozent(Dozent d);

	Raum getRaumByBezeichnung(Raum r);

	Zeitslot anlegenZeitslot(String wochentag);

	Zeitslot modifizierenZeitslot(String wochentag);

	Raum modifizierenRaum(String bez, int kapa);

	String test(String string);

	

}
