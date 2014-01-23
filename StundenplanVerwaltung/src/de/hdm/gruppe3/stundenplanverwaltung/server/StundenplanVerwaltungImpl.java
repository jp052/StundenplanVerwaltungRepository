package de.hdm.gruppe3.stundenplanverwaltung.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
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
 * @author Yasemin Karakoc, Jan Plank, Selim Karazehir
 * 
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
			int anzahlStudierende, int jahrgang) {

		Semesterverband sv = new Semesterverband();
		sv.setSemester(semesterhalbjahr);
		sv.setAnzahlStudenten(anzahlStudierende);
		sv.setJahrgang(jahrgang);
		return svMapper.anlegen(sv);
	}

	@Override
	public Semesterverband modifizierenSemesterverband(Semesterverband sv) { // StundenplanVerwaltung
																				// anpassen
																				// !!!

		return svMapper.modifizieren(sv);
	}
	
	public Vector<Semesterverband> getAllSemesterverband() {
		return svMapper.findeAlle();
	}

	@Override
	public Semesterverband getSemesterverbandByNummer(int nr) {

		return svMapper.findeId(nr);
	}

	/*
	 * public Semesterverband getSemesterverbandBySemesterHalbjahr (String
	 * semesterHalbjahr){
	 * 
	 * return svMapper.ffindeSemester(semesterHalbjahr); }
	 * 
	 * 
	 * public Semesterverband loeschenSemesterverband (Semesterverband sv){
	 * 
	 * return svMapper.loeschen(sv.getId()); }
	 */
	

	// TODO Methoden Zeitslot, Zeitslot muss ver�ndert werden, da die Attribute
	// noch nicht vollst�ndig sind
	@Override
	public Zeitslot anlegenZeitslot(String wochentag) { // Kleinschreibung
														// Diagramm �bernehmen,
														// Aktuelle Attribute
														// von der Klasse
														// Zeitslot hier
														// einf�gen

		Zeitslot z = new Zeitslot();
		z.setWochentag(wochentag);
		return zMapper.anlegen(z);
	}

	@Override
	public Zeitslot modifizierenZeitslot(String wochentag) { // wochentag in
																// Diagramm
																// �bernehmen
																// bzw. ersetzen

		Zeitslot z = new Zeitslot();
		z.setWochentag(wochentag);
		return zMapper.modifizieren(z);
	}

	@Override
	public Zeitslot loeschenZeitslot(Zeitslot z) {

		return zMapper.loeschen(z);
	}

	@Override
	public Zeitslot getZeitslotByNummer(int nr) {

		return zMapper.findeId(nr);
	}

	// Methoden Raum
	@Override
	public Raum anlegenRaum(String bez, int kapa) {

		Raum r = new Raum();
		r.setBezeichnung(bez);
		r.setKapazitaet(kapa);
		return rMapper.anlegen(r);
	}

	@Override
	public Raum modifizierenRaum(Raum r) { // Diagramm anpassen
		return rMapper.modifizieren(r);
	}

	@Override
	public Raum loeschenRaum(Raum r) {

		return rMapper.loeschen(r);
	}

	@Override
	public Raum getRaumbyNummer(int nr) {

		return rMapper.findeId(nr);
	}

	@Override
	public Raum getRaumByBezeichnung(Raum r) {

		return rMapper.findeName(r);
	}
	
	@Override
	public Raum getRaumByBezeichnung(String r) {

		return rMapper.findeName(r);
	}

	// public Vector getAllRaeume () //Schleife einbauen??

	// Methoden Lehrveranstaltung
	@Override
	public Lehrveranstaltung anlegenLehrveranstaltung(String bezeichnung,
			int semester, int umfang, int dozentId) { // Kleinschreibung
														// Diagramm �bernehmen

		// Erst Dozent mit der Id bef�llen damit er der Lehrveranstaltung
		// hinzugef�gt werden kann
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
	public Lehrveranstaltung modifizierenLehrveranstaltung(Lehrveranstaltung l) { // Diagramm
																					// anpassen

		return lvMapper.modifizieren(l);
	}

	@Override
	public Lehrveranstaltung loeschenLehrveranstaltung(Lehrveranstaltung lv) {

		return lvMapper.loeschen(lv);
	}

	@Override
	public Lehrveranstaltung getLehrveranstaltungByNummer(int nr) {

		return lvMapper.findeId(nr);
	}

	@Override
	public Lehrveranstaltung getLehrveranstaltungByBezeichnung(String bez) {

		return lvMapper.findeName(bez);
	}

	// Methoden Dozent
	@Override
	public Dozent anlegenDozent (String vorname, String nachname){ //Kleinschreibung Diagramm �bernehmen
		Dozent d = new Dozent ();
		d.setVorname (vorname);
		d.setNachname (nachname);

		return dMapper.anlegen(d);
	}

	@Override
	public Dozent modifizierenDozent(Dozent d) { // Diagramm anpassen
		return dMapper.modifizieren(d);
	}

	@Override
	public Dozent loeschenDozent(Dozent d) {

		return dMapper.loeschen(d);
	}

	@Override

	public Dozent getDozentByName (Dozent name){

		return dMapper.findeName (name);
		}
	
	/**
	 * Prüft zuerst ob der angegbene Raum verfügbar ist, legt den Zeitslot an und dann die Durchführung.
	 */
	@Override
	public LVDurchfuehrung anlegenDurchfuehrung (int svId, int raumId, int lvId, Zeitslot zeitslot) throws Exception {
		//Erst prüfen ob der Raum zu dem Zeitunkt verfügbar ist
		boolean isVerfuegbar = zMapper.checkVerfuegbarkeit(zeitslot, raumId);
		
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
	public Dozent getDozentByNummer(int nr) {
	
		return dMapper.findeId(nr);
	}

	/**
	 * Prüft zuerst ob der angegbene Raum verfügbar ist, legt den Zeitslot an und ändert dann die Durchführung.
	 */
	@Override
	public LVDurchfuehrung modifizierenDurchfuehrung(int lvdId, int svId, int raumId, int lvId, Zeitslot zeitslot) throws RaumBelegtException {
		//Erst prüfen ob der Raum zu dem Zeitunkt verfügbar ist
		boolean isVerfuegbar = zMapper.checkVerfuegbarkeit(zeitslot, raumId);
		
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
	public LVDurchfuehrung loeschenDurchfuehrung(LVDurchfuehrung d) { // Diagramm
																		// anpassen

		return dfMapper.loeschen(d);
	}
	
	public Vector<LVDurchfuehrung> getAllDurchfuehrungen() throws Exception {
		return dfMapper.findeAlle();
	}

	@Override
	public LVDurchfuehrung getDurchfuehrungByNummer (int lvdNr){

		return dfMapper.findeId(lvdNr);
		}

	@Override
	public Semesterverband getSemesterverbandBySemesterHalbjahr(
			String semesterHalbjahr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Semesterverband loeschenSemesterverband(Semesterverband sv) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Raum> getAllRaeume() {
		// TODO Auto-generated method stub
		return rMapper.findeAlle();
	}

	@Override
	public String test(String test) {
		// DB-Verbindung holen
		Connection con = DBVerbindung.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT * from dozent");

			/*
			 * Da dozent Primärschlüssel ist, kann dozentx. nur ein Tupel
			 * zurückgegeben werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {

				test = rs.getString("vorname");

				return test;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return test + " db test failed";
		}
		return test + " db test failed";
	}

	@Override
	public Vector<Dozent> getAllDozenten() {
		return dMapper.findeAlle();
	}

	@Override
	public Vector<Lehrveranstaltung> getAllLV() {
		// TODO Auto-generated method stub
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
	 */
	
	// Report für die Lehrveranstaltung mit dem Dozenten
	@Override
	public Vector<Lehrveranstaltung> reportLVbyDozent(int dozentID) {
		return lvMapper.findeLVbyDozent(dozentID);
	}

	@Override
	public Vector<Lehrveranstaltung> reportLVbyRaum(String bez) {
		return lvMapper.findeLVbyRaum(bez);
	}


//	@Override
//	public Vector<Lehrveranstaltung> getLVBySV(int sv) {
//		// Lehrveranstaltungen nach Semesterverband suchen und zurück geben.
//		return lvMapper.findeLVbySV(sv);	}
}
