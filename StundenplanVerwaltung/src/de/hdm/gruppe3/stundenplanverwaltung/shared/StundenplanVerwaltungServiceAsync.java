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

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#anlegenDozent(java.lang.String, java.lang.String)
	 */
	void anlegenDozent(String Vorname, String Nachname,
			AsyncCallback<Dozent> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#anlegenDurchfuehrung(int, int, int, java.util.Vector)
	 */
	void anlegenDurchfuehrung(int svId, int raumId, int lvId,
			Vector<Integer> zIds, AsyncCallback<LVDurchfuehrung> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#anlegenLehrveranstaltung(java.lang.String, int, int)
	 */
	void anlegenLehrveranstaltung(String Bezeichnung, int Semester, int Umfang,
			AsyncCallback<Lehrveranstaltung> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#anlegenRaum(java.lang.String, int)
	 */
	void anlegenRaum(String Bezeichnung, int kapazitaet,
			AsyncCallback<Raum> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#anlegenSemesterverband(java.lang.String, int, int)
	 */
	void anlegenSemesterverband(String semesterhalbjahr, int anzahlStudierende,
			int Jahrgang, AsyncCallback<Semesterverband> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#anlegenZeitslot(java.lang.String, java.sql.Time, java.sql.Time)
	 */
	void anlegenZeitslot(String Wochentag, Time Anfangszeit, Time Endzeit,
			AsyncCallback<Zeitslot> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#getAllRaeume()
	 */
	void getAllRaeume(AsyncCallback<Vector<Raum>> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#getDozentByName(java.lang.String)
	 */
	void getDozentByName(String name, AsyncCallback<Dozent> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#getDozentByNummer(int)
	 */
	void getDozentByNummer(int nr, AsyncCallback<Dozent> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#getDurchfuehrungByNummer(int)
	 */
	void getDurchfuehrungByNummer(int nr,
			AsyncCallback<LVDurchfuehrung> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#getLehrveranstaltungByBezeichnung(java.lang.String)
	 */
	void getLehrveranstaltungByBezeichnung(String bez,
			AsyncCallback<Lehrveranstaltung> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#getLehrveranstaltungByNummer(int)
	 */
	void getLehrveranstaltungByNummer(int nr,
			AsyncCallback<Lehrveranstaltung> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#getRaumByBezeichnung(java.lang.String)
	 */
	void getRaumByBezeichnung(String bez, AsyncCallback<Raum> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#getRaumbyNummer(int)
	 */
	void getRaumbyNummer(int nr, AsyncCallback<Raum> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#getSemesterverbandByNummer(int)
	 */
	void getSemesterverbandByNummer(int nr,
			AsyncCallback<Semesterverband> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#getSemesterverbandBySemesterHalbjahr(java.lang.String)
	 */
	void getSemesterverbandBySemesterHalbjahr(String semesterHalbjahr,
			AsyncCallback<Semesterverband> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#getZeitslotByNummer(int)
	 */
	void getZeitslotByNummer(int nr, AsyncCallback<Zeitslot> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#init()
	 */
	void init(AsyncCallback<Void> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#loeschenDozent(de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Dozent)
	 */
	void loeschenDozent(Dozent d, AsyncCallback<Dozent> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#loeschenDurchfuehrung(de.hdm.gruppe3.stundenplanverwaltung.shared.bo.LVDurchfuehrung)
	 */
	void loeschenDurchfuehrung(LVDurchfuehrung d, AsyncCallback<Void> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#loeschenLehrveranstaltung(de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Lehrveranstaltung)
	 */
	void loeschenLehrveranstaltung(Lehrveranstaltung lv,
			AsyncCallback<Lehrveranstaltung> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#loeschenRaum(de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Raum)
	 */
	void loeschenRaum(Raum r, AsyncCallback<Raum> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#loeschenSemesterverband(de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Semesterverband)
	 */
	void loeschenSemesterverband(Semesterverband sv,
			AsyncCallback<Semesterverband> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#loeschenZeitslot(de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Zeitslot)
	 */
	void loeschenZeitslot(Zeitslot z, AsyncCallback<Zeitslot> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#modifizierenDozent(de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Dozent)
	 */
	void modifizierenDozent(Dozent d, AsyncCallback<Dozent> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#modifizierenDurchfuehrung(int, int, int, java.util.Vector)
	 */
	void modifizierenDurchfuehrung(int svId, int raumId, int lvId,
			Vector<Integer> zIds, AsyncCallback<LVDurchfuehrung> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#modifizierenLehrveranstaltung(de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Lehrveranstaltung)
	 */
	void modifizierenLehrveranstaltung(Lehrveranstaltung lv,
			AsyncCallback<Lehrveranstaltung> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#modifizierenRaum(de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Raum)
	 */
	void modifizierenRaum(Raum r, AsyncCallback<Raum> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#modifizierenSemesterverband(de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Semesterverband)
	 */
	void modifizierenSemesterverband(Semesterverband sv,
			AsyncCallback<Semesterverband> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#modifizierenZeitslot(de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Zeitslot)
	 */
	void modifizierenZeitslot(Zeitslot z, AsyncCallback<Zeitslot> callback);

	/**
	 * 
	 * @see de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService#test(java.lang.String)
	 */
	void test(String test, AsyncCallback<String> callback);

}
