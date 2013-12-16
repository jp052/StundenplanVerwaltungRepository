package de.hdm.gruppe3.stundenplanverwaltung.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe3.stundenplanverwaltung.server.db.*;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService;
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
public class StundenplanVerwaltungImpl extends RemoteServiceServlet implements StundenplanVerwaltungService{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SemesterverbandMapper svMapper = null;

	private ZeitslotMapper zMapper = null;

	private RaumMapper rMapper = null;

	private LehrveranstaltungMapper lvMapper = null;

	private DozentMapper dMapper = null;

	private DurchfuehrungMapper dfMapper = null;  //siehe StundenplanVerwaltungImpl, Datentyp DurchfuehrungMapper, dvMapper heißt dfMapper



	public StundenplanVerwaltungImpl () throws IllegalArgumentException {}

	@Override
	public void init () throws IllegalArgumentException {

		svMapper = SemesterverbandMapper.svMapper();
		zMapper = ZeitslotMapper.zeitslotMapper();
		rMapper = RaumMapper.raumMapper();
		lvMapper = LehrveranstaltungMapper.lvMapper();
		dMapper = DozentMapper.dozentMapper();
		dfMapper = DurchfuehrungMapper.dfMapper();
		}


	//Methoden Semesterverband
	@Override
	public Semesterverband anlegenSemesterverband(String semesterhalbjahr, int anzahlStudierende, int jahrgang) {

		Semesterverband sv = new Semesterverband();
		sv.setSemester (semesterhalbjahr);	
		sv.setAnzahlStudenten (anzahlStudierende);
		sv.setJahrgang (jahrgang);
		return svMapper.anlegen(sv);
		}

	@Override
	public Semesterverband modifizierenSemesterverband (String semesterhalbjahr, int anzahlStudierende, int jahrgang){ //StundenplanVerwaltung anpassen !!!
		
		Semesterverband sv = new Semesterverband ();
		sv.setSemester (semesterhalbjahr);	
		sv.setAnzahlStudenten (anzahlStudierende);
		sv.setJahrgang (jahrgang); 
		return svMapper.modifizieren(sv);
		}

	@Override
	public Semesterverband getSemesterverbandByNummer (int nr){
		
		return svMapper.findeId(nr);
		}


/*	public Semesterverband getSemesterverbandBySemesterHalbjahr (String semesterHalbjahr){

		return svMapper.ffindeSemester(semesterHalbjahr);
		}


	public Semesterverband loeschenSemesterverband (Semesterverband sv){

		return svMapper.loeschen(sv.getId());
		}*/


	//TODO Methoden Zeitslot, Zeitslot muss verändert werden, da die Attribute noch nicht vollständig sind
	@Override
	public Zeitslot anlegenZeitslot (String wochentag) {	//Kleinschreibung Diagramm übernehmen, Aktuelle Attribute von der Klasse Zeitslot hier einfügen
		
		Zeitslot z = new Zeitslot();
		z.setWochentag (wochentag);
		return zMapper.anlegen(z);
		}

	@Override
	public Zeitslot modifizierenZeitslot (String wochentag){ //wochentag in Diagramm übernehmen bzw. ersetzen
		
		Zeitslot z = new Zeitslot ();
		z.setWochentag (wochentag);
		return zMapper.modifizieren(z);
		}

	@Override
	public Zeitslot loeschenZeitslot (Zeitslot z){

		return zMapper.loeschen(z);
		}
	
	@Override
	public Zeitslot getZeitslotByNummer (int nr){
		
		return zMapper.findeId(nr);
		}


	//Methoden Raum
	@Override
	public Raum anlegenRaum (String bez, int kapa){
		
		Raum r = new Raum ();
		r.setBezeichnung (bez);
		r.setKapazitaet (kapa);
		return rMapper.anlegen(r);
		}

	@Override
	public Raum modifizierenRaum (String bez, int kapa){ //Diagramm anpassen

		Raum r = new Raum ();
		r.setBezeichnung (bez);
		r.setKapazitaet (kapa);
		return rMapper.modifizieren(r);
		}

	@Override
	public Raum loeschenRaum (Raum r){

		return rMapper.loeschen(r);
		}

	@Override
	public Raum getRaumbyNummer (int nr){

		return rMapper.findeId(nr);
		}

	@Override
	public Raum getRaumByBezeichnung (Raum r){

		return rMapper.findeName(r);
		}


	//public Vector getAllRaeume () //Schleife einbauen??





	//Methoden Lehrveranstaltung
	@Override
	public Lehrveranstaltung anlegenLehrveranstaltung (String bezeichnung, int semester, int umfang){ //Kleinschreibung Diagramm übernehmen

		Lehrveranstaltung l = new Lehrveranstaltung ();
		l.setBezeichnung (bezeichnung);
		l.setSemester (semester);
		l.setUmfang (umfang);
		return lvMapper.anlegen(l);
		}

	@Override
	public Lehrveranstaltung modifizierenLehrveranstaltung (String bezeichnung, int semester, int umfang){ //Diagramm anpassen

		Lehrveranstaltung l = new Lehrveranstaltung ();
		l.setBezeichnung (bezeichnung);
		l.setSemester (semester);
		l.setUmfang (umfang);
		return lvMapper.modifizieren(l);
		}

	@Override
	public Lehrveranstaltung loeschenLehrveranstaltung (Lehrveranstaltung lv){

		return lvMapper.loeschen(lv);
		}

	@Override
	public Lehrveranstaltung getLehrveranstaltungByNummer (int nr){

		return lvMapper.findeId(nr);
		}

	@Override
	public Lehrveranstaltung getLehrveranstaltungByBezeichnung (String bez){

		return lvMapper.findeName(bez);
		}


	//Methoden Dozent
	@Override
	public Dozent anlegenDozent (String vorname, String nachname){ //Kleinschreibung Diagramm übernehmen

		Dozent d = new Dozent ();
		d.setVorname (vorname);
		d.setNachname (nachname);
		return dMapper.anlegen(d);
		}

	@Override
	public Dozent modifizierenDozent (String vorname, String nachname){ //Diagramm anpassen
		
		Dozent d = new Dozent ();
		d.setVorname (vorname);
		d.setNachname (nachname);
		return dMapper.modifizieren(d);
		}

	@Override
	public Dozent loeschenDozent (Dozent d){

		return dMapper.loeschen(d);
		}
		
	@Override
	public Dozent getDozentByNummer (int nr){

		return dMapper.findeId(nr);
		}

	@Override
	public Dozent getDozentByName (Dozent name){

		return dMapper.findeName (name);
		}


	//Methoden LVDurchführung
	@Override
	public LVDurchfuehrung anlegenDurchfuehrung (int svId, int raumId, int lvId, int zIds){

		LVDurchfuehrung lvd = new LVDurchfuehrung();
		lvd.setId (svId);
		lvd.setRaum (raumId);
		lvd.setLV(lvId);
		lvd.setZIds (zIds);
		return dfMapper.anlegen(lvd);
		}

	@Override
	public LVDurchfuehrung modifizierenDurchfuehrung (int svId, int raumId, int lvId, int zIds){

		LVDurchfuehrung lvd = new LVDurchfuehrung();
		lvd.setId (svId);
		lvd.setRaum (raumId);
		lvd.setLV(lvId);
		lvd.setZIds(zIds);
		return dfMapper.modifizieren(lvd);
		}
		
	@Override
	public LVDurchfuehrung loeschenDurchfuehrung (LVDurchfuehrung d){ //Diagramm anpassen
		
		return dfMapper.loeschen(d);	
		}

	@Override
	public LVDurchfuehrung getDurchfuehrungByNummer (LVDurchfuehrung nr){

		return dfMapper.findeId(nr);
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
		return null;
	}

	@Override
	public String test(String test) {
		 // DB-Verbindung holen
	      Connection con = DBVerbindung.connection();

	      try {
	        // Leeres SQL-Statement (JDBC) anlegen
	        Statement stmt = con.createStatement();

	        // Statement ausfÃ¼llen und als Query an die DB schicken
	        ResultSet rs = stmt.executeQuery("SELECT * from dozent");

	        /*
	         * Da dozent PrimÃ¤rschlÃ¼ssel ist, kann dozentx. nur ein Tupel zurÃ¼ckgegeben
	         * werden. PrÃ¼fe, ob ein Ergebnis vorliegt.
	         */
	        if (rs.next()) {

	          test = rs.getString("vorname");

	          return test;
	        }
	      }
	      catch (SQLException e2) {
	        e2.printStackTrace();
	        return test + " db test failed";
	      } 
		return test + " db test failed";
	}
}
