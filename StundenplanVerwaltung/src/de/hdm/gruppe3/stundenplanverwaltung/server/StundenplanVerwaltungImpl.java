package de.hdm.gruppe3.stundenplanverwaltung.server;

import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe3.stundenplanverwaltung.server.db.*;
import de.hdm.gruppe3.stundenplanverwaltung.shared.RaumBelegtException;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Dozent;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.LVDurchfuehrung;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Lehrveranstaltung;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Raum;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Semesterverband;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Zeitslot;


/**
 * @author Yasemin Karakoc, Jan Plank, Selim Karazehir, Julia Hammerer, Denis Fürst, Daniel Krakow
 * In Anlehnung an Hr. Prof. Dr. Thies
 */
public class StundenplanVerwaltungImpl extends RemoteServiceServlet implements
		StundenplanVerwaltungService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SemesterverbandMapper svMapper = null;

	private ZeitslotMapper zMapper = null;

	private RaumMapper rMapper = null;

	private LehrveranstaltungMapper lvMapper = null;

	private DozentMapper dMapper = null;

	private DurchfuehrungMapper dfMapper = null; // siehe
													// StundenplanVerwaltungImpl,
													// Datentyp
													// DurchfuehrungMapper,
													// dvMapper hei�t dfMapper

	public StundenplanVerwaltungImpl() throws IllegalArgumentException {
	}

	@Override
	public void init() throws IllegalArgumentException {

		svMapper = SemesterverbandMapper.svMapper();
		zMapper = ZeitslotMapper.zeitslotMapper();
		rMapper = RaumMapper.raumMapper();
		lvMapper = LehrveranstaltungMapper.lvMapper();
		dMapper = DozentMapper.dozentMapper();
		dfMapper = DurchfuehrungMapper.dfMapper();
	}

	// Methoden Semesterverband
	@Override
	public Semesterverband anlegenSemesterverband(int semesterhalbjahr,
			int anzahlStudierende, int jahrgang) throws Exception {

		Semesterverband sv = new Semesterverband();
		sv.setSemester(semesterhalbjahr);
		sv.setAnzahlStudenten(anzahlStudierende);
		sv.setJahrgang(jahrgang);
		return svMapper.anlegen(sv);
	}

	@Override
	public Semesterverband modifizierenSemesterverband(Semesterverband sv) throws Exception { // StundenplanVerwaltung
																				// anpassen
																				// !!!

		return svMapper.modifizieren(sv);
	}
	
	public Vector<Semesterverband> getAllSemesterverband() throws Exception {
		return svMapper.findeAlle();
	}

	@Override
	public Semesterverband getSemesterverbandByNummer(int nr) throws Exception {

		return svMapper.findeId(nr);
	}

	@Override
	public Zeitslot anlegenZeitslot(String wochentag) throws Exception { 

		Zeitslot z = new Zeitslot();
		z.setWochentag(wochentag);
		return zMapper.anlegen(z);
	}

	@Override
	public Zeitslot modifizierenZeitslot(String wochentag) throws Exception { 

		Zeitslot z = new Zeitslot();
		z.setWochentag(wochentag);
		return zMapper.modifizieren(z);
	}

	@Override
	public Zeitslot loeschenZeitslot(Zeitslot z) throws Exception {

		return zMapper.loeschen(z);
	}

	@Override
	public Zeitslot getZeitslotByNummer(int nr) throws Exception {

		return zMapper.findeId(nr);
	}

	// Methoden Raum
	@Override
	public Raum anlegenRaum(String bez, int kapa) throws Exception {

		Raum r = new Raum();
		r.setBezeichnung(bez);
		r.setKapazitaet(kapa);
		return rMapper.anlegen(r);
	}

	@Override
	public Raum modifizierenRaum(Raum r) throws Exception { 
		return rMapper.modifizieren(r);
	}

	@Override
	public Raum loeschenRaum(Raum r) throws Exception {

		return rMapper.loeschen(r);
	}

	@Override
	public Raum getRaumbyNummer(int nr) throws Exception {

		return rMapper.findeId(nr);
	}

	@Override
	public Raum getRaumByBezeichnung(Raum r) throws Exception {

		return rMapper.findeName(r);
	}
	
	@Override
	public Raum getRaumByBezeichnung(String r) throws Exception {

		return rMapper.findeName(r);
	}

	// Methoden Lehrveranstaltung
	@Override
	public Lehrveranstaltung anlegenLehrveranstaltung(String bezeichnung,
			int semester, int umfang, int dozentId) throws Exception {
		
		// Erst Dozent mit der Id befüllen damit er der Lehrveranstaltung
		// hinzugefügt werden kann
		Dozent d = new Dozent();
		d.setId(dozentId);

		Lehrveranstaltung l = new Lehrveranstaltung();
		l.setBezeichnung(bezeichnung);
		l.setSemester(semester);
		l.setUmfang(umfang);
		l.setDozent(d);
		return lvMapper.anlegen(l);
	}

	@Override
	public Lehrveranstaltung modifizierenLehrveranstaltung(Lehrveranstaltung l) throws Exception { 

		return lvMapper.modifizieren(l);
	}

	@Override
	public Lehrveranstaltung loeschenLehrveranstaltung(Lehrveranstaltung lv) throws Exception {

		return lvMapper.loeschen(lv);
	}

	@Override
	public Lehrveranstaltung getLehrveranstaltungByNummer(int nr) throws Exception {

		return lvMapper.findeId(nr);
	}

	@Override
	public Lehrveranstaltung getLehrveranstaltungByBezeichnung(String bez) throws Exception {

		return lvMapper.findeName(bez);
	}

	// Methoden Dozent
	@Override
	public Dozent anlegenDozent (String vorname, String nachname) throws Exception{ 
		Dozent d = new Dozent ();
		d.setVorname (vorname);
		d.setNachname (nachname);

		return dMapper.anlegen(d);
	}

	@Override
	public Dozent modifizierenDozent(Dozent d) throws Exception { // Diagramm anpassen
		return dMapper.modifizieren(d);
	}

	@Override
	public Dozent loeschenDozent(Dozent d) throws Exception {

		return dMapper.loeschen(d);
	}

	@Override

	public Dozent getDozentByName (Dozent name) throws Exception{

		return dMapper.findeName (name);
		}
	
	/**
	 * Prüft zuerst ob der angegbene Raum verfügbar ist, legt den Zeitslot an und dann die Durchführung.
	 */
	@Override
	public LVDurchfuehrung anlegenDurchfuehrung (int svId, int raumId, int lvId, Zeitslot zeitslot) throws Exception {
		//Erst prüfen ob der Raum zu dem Zeitunkt verfügbar ist
		boolean isVerfuegbar = zMapper.checkVerfuegbarkeit(zeitslot, raumId, 0);
		
		//Wenn isVerfuegbar nicht true ist, dann abbrechen und null zurückgeben
		if(!isVerfuegbar) {
			throw new RaumBelegtException();
		}
		
		//Den übergebenen Zeitslot wird mit dem dann angelegten überschrieben. Der Übergebene hat bis hierher noch keine Id.
		//Es wird auch noch weitere Überprüfungslogik ausgeführt, um mehr zu sehen siehe innerhalt der anlegen Methode.
		zeitslot = zMapper.anlegen(zeitslot);
		return dfMapper.anlegen(svId, raumId, lvId, zeitslot);
		}
	
	@Override
	public Dozent getDozentByNummer(int nr) throws Exception {
	
		return dMapper.findeId(nr);
	}

	/**
	 * Prüft zuerst ob der angegbene Raum verfügbar ist, legt den Zeitslot an und ändert dann die Durchführung.
	 * @throws Exception 
	 */
	@Override
	public LVDurchfuehrung modifizierenDurchfuehrung(int lvdId, int svId, int raumId, int lvId, Zeitslot zeitslot) throws Exception {
		//Erst prüfen ob der Raum zu dem Zeitunkt verfügbar ist
		boolean isVerfuegbar = zMapper.checkVerfuegbarkeit(zeitslot, raumId, lvdId);
		
		//Wenn isVerfuegbar nicht true ist wird eine Ausnahme Fehler geworfen
		if(!isVerfuegbar) {
			throw new RaumBelegtException();
		}
		
		//Den übergebenen Zeitslot wird mit dem dann angelegten überschrieben. Der Übergebene hat bis hierher noch keine Id.
		//Es wird auch noch weitere Überprüfungslogik ausgeführt, um mehr zu sehen siehe innerhalt der anlegen Methode.
		zeitslot = zMapper.anlegen(zeitslot);
		return dfMapper.modifizieren(lvdId, svId, raumId, lvId, zeitslot);
	}
		

	@Override
	public LVDurchfuehrung loeschenDurchfuehrung(LVDurchfuehrung d) throws Exception { // Diagramm
																		// anpassen

		return dfMapper.loeschen(d);
	}
	
	public Vector<LVDurchfuehrung> getAllDurchfuehrungen() throws Exception {
		return dfMapper.findeAlle();
	}

	@Override
	public LVDurchfuehrung getDurchfuehrungByNummer (int lvdNr) throws Exception{

		return dfMapper.findeId(lvdNr);
		}

	@Override
	public Semesterverband getSemesterverbandBySemesterHalbjahr(
			int sv) throws Exception {
		// TODO Auto-generated method stub
		return svMapper.findeSVHalbjahr(sv);
	}

	@Override
	public Semesterverband loeschenSemesterverband(Semesterverband sv) {

		return null;
	}

	@Override
	public Vector<Raum> getAllRaeume() throws Exception {

		return rMapper.findeAlle();
	}



	@Override
	public Vector<Dozent> getAllDozenten() throws Exception {
		return dMapper.findeAlle();
	}

	@Override
	public Vector<Lehrveranstaltung> getAllLV() throws Exception {

		return lvMapper.findeAlle();
	}

	@Override
	public Vector<Lehrveranstaltung> getLVBySV(int sv) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Diese Methode wird für den Report benötigt um alle Lehrveranstaltungen im selben Semesterverband zu finden
	 * @param sv
	 * @return
	 * @throws Exception 
	 */
	
	// Report für die Lehrveranstaltung mit dem Dozenten
	@Override
	public Vector<Lehrveranstaltung> reportLVbyDozent(int dozentID) throws Exception {
		return lvMapper.findeLVbyDozent(dozentID);
	}

	@Override
	public Vector<Lehrveranstaltung> reportLVbyRaum(int raumID) throws Exception {
		return lvMapper.findeLVbyRaum(raumID);
	}

	@Override
	public Vector<Lehrveranstaltung> reportLVbySV(int sv) throws Exception {
		// TODO Auto-generated method stub
		return lvMapper.findeLVbySV(sv);
	}


//	@Override
//	public Vector<Lehrveranstaltung> getLVBySV(int sv) {
//		// Lehrveranstaltungen nach Semesterverband suchen und zurück geben.
//		return lvMapper.findeLVbySV(sv);	}
}
