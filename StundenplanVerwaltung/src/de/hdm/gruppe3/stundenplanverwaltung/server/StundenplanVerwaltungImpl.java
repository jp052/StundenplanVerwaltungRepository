package de.hdm.gruppe3.stundenplanverwaltung.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe3.stundenplanverwaltung.server.db.DBVerbindung;
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

	public void init() throws IllegalArgumentException {
		
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
	         * Da dozent Primärschlüssel ist, kann dozentx. nur ein Tupel zurückgegeben
	         * werden. Prüfe, ob ein Ergebnis vorliegt.
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
	

	@Override
	public Semesterverband anlegenSemesterverband(String semesterhalbjahr,
			int anzahlStudierende, int Jahrgang) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Semesterverband modifizierenSemesterverband(Semesterverband sv) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Semesterverband getSemesterverbandByNummer(int nr) {
		// TODO Auto-generated method stub
		return null;
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
	public Zeitslot anlegenZeitslot(String Wochentag, Time Anfangszeit,
			Time Endzeit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Zeitslot modifizierenZeitslot(Zeitslot z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Zeitslot loeschenZeitslot(Zeitslot z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Zeitslot getZeitslotByNummer(int nr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Raum anlegenRaum(String Bezeichnung, int kapazitaet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Raum modifizierenRaum(Raum r) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Raum loeschenRaum(Raum r) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Raum getRaumbyNummer(int nr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Lehrveranstaltung anlegenLehrveranstaltung(String Bezeichnung,
			int Semester, int Umfang) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Lehrveranstaltung modifizierenLehrveranstaltung(Lehrveranstaltung lv) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Lehrveranstaltung loeschenLehrveranstaltung(Lehrveranstaltung lv) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Lehrveranstaltung getLehrveranstaltungByNummer(int nr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Lehrveranstaltung getLehrveranstaltungByBezeichnung(String bez) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dozent anlegenDozent(String Vorname, String Nachname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dozent modifizierenDozent(Dozent d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dozent loeschenDozent(Dozent d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dozent getDozentByNummer(int nr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dozent getDozentByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LVDurchfuehrung anlegenDurchfuehrung(int svId, int raumId, int lvId,
			Vector<Integer> zIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LVDurchfuehrung modifizierenDurchfuehrung(int svId, int raumId,
			int lvId, Vector<Integer> zIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loeschenDurchfuehrung(LVDurchfuehrung d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LVDurchfuehrung getDurchfuehrungByNummer(int nr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Raum getRaumByBezeichnung(String bez) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Raum> getAllRaeume() {
		// TODO Auto-generated method stub
		return null;
	}

}
