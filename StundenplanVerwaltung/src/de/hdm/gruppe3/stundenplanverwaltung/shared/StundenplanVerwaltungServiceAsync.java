/**
 * 
 */
package de.hdm.gruppe3.stundenplanverwaltung.shared;

/**
 * @author Yasemin Karakoc, Jan Plank
 *
 */
import java.sql.Time;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

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
public interface StundenplanVerwaltungServiceAsync {

	void anlegenDozent(String Vorname, String Nachname,
			AsyncCallback<Dozent> callback);

	void anlegenDurchfuehrung(int svId, int raumId, int lvId,
			Zeitslot zeitslot, AsyncCallback<LVDurchfuehrung> callback);

	void anlegenLehrveranstaltung(String Bezeichnung, int Semester, int Umfang,
			int dozentId, AsyncCallback<Lehrveranstaltung> callback);

	void anlegenRaum(String Bezeichnung, int Kapazitaet,
			AsyncCallback<Raum> callback);

	void anlegenSemesterverband(int semesterhalbjahr, int anzahlStudierende,
			int jahrgang, AsyncCallback<Semesterverband> callback);

	void anlegenZeitslot(String wochentag, AsyncCallback<Zeitslot> callback);

	void getAllRaeume(AsyncCallback<Vector<Raum>> callback);

	void getDozentByName(Dozent name, AsyncCallback<Dozent> callback);

	void getDozentByNummer(int nr, AsyncCallback<Dozent> callback);

	void getDurchfuehrungByNummer(int lvdNr,
			AsyncCallback<LVDurchfuehrung> callback);

	void getLehrveranstaltungByBezeichnung(String bez,
			AsyncCallback<Lehrveranstaltung> callback);

	void getLehrveranstaltungByNummer(int nr,
			AsyncCallback<Lehrveranstaltung> callback);

	void getRaumByBezeichnung(Raum r, AsyncCallback<Raum> callback);

	void getRaumbyNummer(int nr, AsyncCallback<Raum> callback);

	void getSemesterverbandByNummer(int nr,
			AsyncCallback<Semesterverband> callback);

	void getSemesterverbandBySemesterHalbjahr(String semesterHalbjahr,
			AsyncCallback<Semesterverband> callback);

	void getZeitslotByNummer(int nr, AsyncCallback<Zeitslot> callback);

	void loeschenDozent(Dozent d, AsyncCallback<Dozent> callback);

	void loeschenDurchfuehrung(LVDurchfuehrung d,
			AsyncCallback<LVDurchfuehrung> callback);

	void loeschenLehrveranstaltung(Lehrveranstaltung lv,
			AsyncCallback<Lehrveranstaltung> callback);

	void loeschenRaum(Raum r, AsyncCallback<Raum> callback);

	void loeschenSemesterverband(Semesterverband sv,
			AsyncCallback<Semesterverband> callback);

	void loeschenZeitslot(Zeitslot z, AsyncCallback<Zeitslot> callback);

	void modifizierenDozent(Dozent d, AsyncCallback<Dozent> callback);

	void modifizierenDurchfuehrung(int lvdId, int svId, int raumId, int lvId,
			Zeitslot zeitslot, AsyncCallback<LVDurchfuehrung> asyncCallback);

	void modifizierenLehrveranstaltung(Lehrveranstaltung lv,
			AsyncCallback<Lehrveranstaltung> callback);

	void modifizierenRaum(Raum r, AsyncCallback<Raum> callback);

	void modifizierenSemesterverband(Semesterverband semeserverband,
			AsyncCallback<Semesterverband> callback);

	void modifizierenZeitslot(String wochentag, AsyncCallback<Zeitslot> callback);

	void test(String string, AsyncCallback<String> asyncCallback);

	void getAllDozenten(AsyncCallback<Vector<Dozent>> callback);

	void getAllLV(AsyncCallback<Vector<Lehrveranstaltung>> callback);

	void getAllSemesterverband(AsyncCallback<Vector<Semesterverband>> callback);

	void getLVBySV(int sv, AsyncCallback<Vector<Lehrveranstaltung>> callback);

	void reportLVbyDozent(int dozentID,
			AsyncCallback<Vector<Lehrveranstaltung>> callback);

	void reportLVbyRaum(String bez,
			AsyncCallback<Vector<Lehrveranstaltung>> callback);

	void getRaumByBezeichnung(String r, AsyncCallback<Raum> callback);


	void getAllDurchfuehrungen(AsyncCallback<Vector<LVDurchfuehrung>> callback);

	void getLVBySV(int sv, AsyncCallback<Vector<Lehrveranstaltung>> callback);

	void reportLVbyDozent(int dozentID,
			AsyncCallback<Vector<Lehrveranstaltung>> callback);

	void reportLVbyRaum(String bez,
			AsyncCallback<Vector<Lehrveranstaltung>> callback);

	void getRaumByBezeichnung(String r, AsyncCallback<Raum> callback);



}
