package de.hdm.gruppe3.stundenplanverwaltung.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.*;

/**
 * @author Yasemin Karakoc, Jan Plank, Selim Karazehir, Julia Hammerer, Denis
 *         Fürst, Daniel Krakow 
 *         In Anlehnung an Hr. Prof. Dr. Thies
 */
public class LehrveranstaltungMapper {
	/**
	 * damit sie in der statischen Methode svMapper() benützt werden kann muss
	 * es auch hier <code>static</code> sein
	 * 
	 * @see lvMapper()
	 */
	private static LehrveranstaltungMapper lvMapper = null;

	/**
	 * mit protected wird die Instanziierung mit <code>new</code> verhindert
	 */
	protected LehrveranstaltungMapper() {
	}

	/**
	 * die svMapper() kann man mit der Klasse aufrufen. So kann man sicher
	 * gehen, dass nur ein Objekt instanziiert wird.
	 * 
	 * @return lvMapper
	 * @see lvMapper
	 */
	public static LehrveranstaltungMapper lvMapper() {
		if (lvMapper == null) {
			lvMapper = new LehrveranstaltungMapper();
		}

		return lvMapper;
	}

	/**
	 * Methode um ein Semesterverband in die Datenbank anzulegen
	 * 
	 * @param lv
	 * @return
	 * @throws Exception
	 */
	public Lehrveranstaltung anlegen(Lehrveranstaltung lv) throws Exception {
		Connection con = DBVerbindung.connection();
		Statement stmt = null;
		try {
			stmt = con.createStatement();

			String sql = "INSERT INTO lehrveranstaltung (LVNr, Bezeichnung, Umfang, Semester, PersonalNr) "
					+ "VALUES ( "
					+ "NULL,"
					+ "\""
					+ lv.getBezeichnung()
					+ "\","
					+ lv.getUmfang()
					+ ","
					+ lv.getSemester()
					+ ","
					+ lv.getDozent().getId() + ")";

			stmt.executeUpdate(sql);

		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		} finally {
			//Alles schließen, finally wird immer ausgeführt, egal ob es einen Fehler gibt oder nicht.
			DBVerbindung.closeAll(null, stmt, con);
		}

		return lv;

	}

	/**
	 * Methode, mit dem man ein Datensatz verändern kann
	 * 
	 * @param lv
	 * @return
	 * @throws Exception
	 */
	public Lehrveranstaltung modifizieren(Lehrveranstaltung lv)
			throws Exception {
		Connection con = DBVerbindung.connection();
		Statement stmt = null;
		
		try {
			stmt = con.createStatement();
			// UPDATE Lehrveranstaltung SET Bezeichnung="Software-t" ,

			String sql = "UPDATE lehrveranstaltung " + "SET Bezeichnung=\""
					+ lv.getBezeichnung() + "\", Umfang=" + lv.getUmfang()
					+ ", Semester=" + lv.getSemester() + ", PersonalNr="
					+ lv.getDozent().getId() + " WHERE LVNr=" + lv.getId();

			stmt.executeUpdate(sql);

		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		} finally {
			//Alles schließen, finally wird immer ausgeführt, egal ob es einen Fehler gibt oder nicht.
			DBVerbindung.closeAll(null, stmt, con);
		}

		// Um Analogie zu insert(Lehrveranstaltung a) zu wahren, geben wir a
		// zurück
		return lv;
	}

	/**
	 * Methode um einen Datensatz aus der Datenbank zu löschen
	 * 
	 * @param lv
	 * @return
	 * @throws Exception
	 */
	public Lehrveranstaltung loeschen(Lehrveranstaltung lv) throws Exception {
		Connection con = DBVerbindung.connection();
		Statement stmt = null;
		
		try {
			stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM lehrveranstaltung " + "WHERE LVNr="
					+ lv.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		} finally {
			//Alles schließen, finally wird immer ausgeführt, egal ob es einen Fehler gibt oder nicht.
			DBVerbindung.closeAll(null, stmt, con);
		}
		
		return lv;
	}

	/**
	 * Methode, mit dem man mittels der Bezeichnung den Lehrveranstaltung finden
	 * kann.
	 * 
	 * @param bez
	 * @return
	 * @throws Exception
	 */
	public Lehrveranstaltung findeName(String bez) throws Exception {
		// DB-Verbindung holen
		Connection con = DBVerbindung.connection();
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			// Leeres SQL-Statement (JDBC) anlegen
			stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			rs = stmt
					.executeQuery("SELECT LVNr, Bezeichnung, Umfang, Semester FROM lehrveranstaltung "
							+ "WHERE Bezeichnung="
							+ bez
							+ " ORDER BY Bezeichnung");

			/*
			 * Da lv Primärschlüssel ist, kann lvx. nur ein Tupel zurückgegeben
			 * werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Lehrveranstaltung lv1 = new Lehrveranstaltung();
				lv1.setId(rs.getInt("ID"));
				lv1.setBezeichnung(rs.getString("Bezeichnung"));
				lv1.setUmfang(rs.getInt("Umfang"));
				lv1.setSemester(rs.getInt("Semester"));
				return lv1;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		}  finally {
			//Alles schließen, finally wird immer ausgeführt, egal ob es einen Fehler gibt oder nicht.
			DBVerbindung.closeAll(rs, stmt, con);
		}

		return null;
	}

	/**
	 * Methode, mit dem man mittels der id den Lehrveranstaltung findet.
	 * 
	 * @param lvId
	 * @return
	 * @throws Exception
	 */
	public Lehrveranstaltung findeId(int lvId) throws Exception {
		// DB-Verbindung holen
		Connection con = DBVerbindung.connection();
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();

			// Die Query die ausgeführt werden soll
			String sql = "SELECT * FROM lehrveranstaltung INNER JOIN dozent On lehrveranstaltung.PersonalNr=dozent.PersonalNr"
					+ " WHERE LVNr=" + lvId;

			// Query ausführen
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				// Lehrveranstaltungsobjekt aus ResultSet erstellen
				Lehrveranstaltung lv = new Lehrveranstaltung();
				lv.setId(rs.getInt("LVNr"));
				lv.setBezeichnung(rs.getString("Bezeichnung"));
				lv.setUmfang(rs.getInt("Umfang"));
				lv.setSemester(rs.getInt("Semester"));

				// Dozent aus ResultSet erstellen
				Dozent d = new Dozent();
				d.setId(rs.getInt("dozent.PersonalNr"));
				d.setVorname(rs.getString("dozent.Vorname"));
				d.setNachname(rs.getString("dozent.Nachname"));
				lv.setDozent(d);

				return lv;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		} finally {
			//Alles schließen, finally wird immer ausgeführt, egal ob es einen Fehler gibt oder nicht.
			DBVerbindung.closeAll(rs, stmt, con);
		}
		
		return null;
	}

	/**
	 * Alle Datensätze aus der Tabelle Lehrveranstaltung werden herausgelesen
	 * und in ein Objekt gespeichert.
	 * 
	 * @return
	 * @throws Exception
	 */
	public Vector<Lehrveranstaltung> findeAlle() throws Exception {
		Connection con = DBVerbindung.connection();
		Statement stmt = null;
		ResultSet rs = null;
		
		// Ergebnisvektor vorbereiten
		Vector<Lehrveranstaltung> result = new Vector<Lehrveranstaltung>();

		try {
			stmt = con.createStatement();

			rs = stmt
					.executeQuery("SELECT * FROM lehrveranstaltung INNER JOIN dozent ON lehrveranstaltung.PersonalNr = dozent.PersonalNr ");

			// Für jeden Eintrag im Suchergebnis wird nun ein Account-Objekt
			// erstellt.
			while (rs.next()) {
				Lehrveranstaltung lv = new Lehrveranstaltung();
				Dozent dozent = new Dozent();

				// Dozent füllen
				dozent.setId(rs.getInt("dozent.PersonalNr"));
				dozent.setVorname(rs.getString("dozent.Vorname"));
				dozent.setNachname(rs.getString("dozent.Nachname"));

				// Lv füllen
				lv.setId(rs.getInt("lehrveranstaltung.LVNr"));
				lv.setBezeichnung(rs.getString("lehrveranstaltung.Bezeichnung"));
				lv.setUmfang(rs.getInt("lehrveranstaltung.Umfang"));
				lv.setSemester(rs.getInt("lehrveranstaltung.Semester"));
				lv.setDozentName(rs.getString("dozent.Nachname"));
				lv.setDozent(dozent);

				// Hinzuf¸gen des neuen Objekts zum Ergebnisvektor
				result.addElement(lv);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		}  finally {
			//Alles schließen, finally wird immer ausgeführt, egal ob es einen Fehler gibt oder nicht.
			DBVerbindung.closeAll(rs, stmt, con);
		}

		// Ergebnisvektor zur¸ckgeben
		return result;
	}

	/**
	 * Mit dieser Methode kann man den Dozenten mit der Lehrveranstaltung lv
	 * finden
	 * 
	 * @param lv
	 * @return
	 * @throws Exception
	 */
	public Dozent findeDozent(Lehrveranstaltung lv) throws Exception {

		return DozentMapper.dozentMapper().findeId(lv.getId());
	}

	/**
	 * Termin für eine Lehrveranstaltung finden
	 * @param lv
	 * @return
	 * @throws Exception
	 */
	public Zeitslot findeTermin(Lehrveranstaltung lv) throws Exception {

		return ZeitslotMapper.zeitslotMapper().findeId(lv.getId());
	}

	public Raum findeRaum(Lehrveranstaltung lv) throws Exception {

		return RaumMapper.raumMapper().findeId(lv.getId());
	}

	/**
	 * Mit dieser Methode suchen wir alle Vorlesungen mit der gleichen ID vom
	 * Dozenten
	 * 
	 * @param dozentID
	 * @return
	 * @throws Exception
	 */
	public Vector<Lehrveranstaltung> findeLVbyDozent(int dozentID)
			throws Exception {
		Connection con = DBVerbindung.connection();
		Statement stmt = null;
		ResultSet rs = null;
		
		// Vector mit angeforderten Objekten
		Vector<Lehrveranstaltung> result = new Vector<Lehrveranstaltung>();

		try {
			stmt = con.createStatement();

			String sql = "SELECT * FROM dozent JOIN lehrveranstaltung  ON lehrveranstaltung.PersonalNr = dozent.PersonalNr WHERE dozent.PersonalNr = "
					+ dozentID;

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Lehrveranstaltung lv = new Lehrveranstaltung();
				lv.setId(rs.getInt("lehrveranstaltung.LVNr"));
				lv.setBezeichnung(rs.getString("lehrveranstaltung.Bezeichnung"));
				lv.setUmfang(rs.getInt("lehrveranstaltung.Umfang"));
				lv.setSemester(rs.getInt("lehrveranstaltung.Semester"));

				result.addElement(lv);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		} finally {
			//Alles schließen, finally wird immer ausgeführt, egal ob es einen Fehler gibt oder nicht.
			DBVerbindung.closeAll(rs, stmt, con);
		}

		return result;
	}

	/**
	 * Findet Alle Lehrveranstaltungen im Raum
	 * 
	 * @param bez
	 * @return
	 * @throws Exception
	 */
	public Vector<Lehrveranstaltung> findeLVbyRaum(int bez) throws Exception {
		Connection con = DBVerbindung.connection();
		Statement stmt = null;
		ResultSet rs = null;
		
		// Vector mit angeforderten Objekten
		Vector<Lehrveranstaltung> result = new Vector<Lehrveranstaltung>();

		try {
			stmt = con.createStatement();

			// String sql =
			// "SELECT * FROM Raum LEFT JOIN Lehrveranstaltung ON raum.bezeichnung = "
			// + bez;

			String sql = "SELECT zeitslot.Wochentag, zeitslot.Anfangszeit, raum.Bezeichnung, zeitslot.Endzeit, lehrveranstaltung.Bezeichnung "
					+ " FROM durchfuehrung "
					+ " JOIN raum ON raum.RaumNr = durchfuehrung.RaumNr "
					+ " JOIN zeitslot ON zeitslot.ZeitNr = durchfuehrung.ZeitNr "
					+ " JOIN lehrveranstaltung ON lehrveranstaltung.LVNr = durchfuehrung.LVNr "
					+ " WHERE (raum.RaumNr = '" + bez + "')";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Lehrveranstaltung lv = new Lehrveranstaltung();
				lv.setRaumWochentag(rs.getString("zeitslot.Wochentag"));
				lv.setBezeichnung(rs.getString("lehrveranstaltung.Bezeichnung"));
				lv.setRaumZeit(rs.getInt("zeitslot.Anfangszeit"));
				lv.setRaumZeitEnde(rs.getInt("zeitslot.Endzeit"));

				result.addElement(lv);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		} finally {
			//Alles schließen, finally wird immer ausgeführt, egal ob es einen Fehler gibt oder nicht.
			DBVerbindung.closeAll(rs, stmt, con);
		}

		return result;
	}

	/**
	 * Findet alle Lehrveranstaltungen im Semesterverband
	 * 
	 * @param sv
	 * @return
	 * @throws Exception
	 */
	public Vector<Lehrveranstaltung> findeLVbySV(int sv) throws Exception {
		Connection con = DBVerbindung.connection();
		Statement stmt = null;
		ResultSet rs = null;
		
		// Vector mit angeforderten Objekten
		Vector<Lehrveranstaltung> result = new Vector<Lehrveranstaltung>();

		try {
			stmt = con.createStatement();

			// String sql =
			// "SELECT * FROM Raum LEFT JOIN Lehrveranstaltung ON raum.bezeichnung = "
			// + bez;

			String sql = "SELECT zeitslot.Wochentag, zeitslot.Anfangszeit, semesterverband.semesterHalbjahr, zeitslot.Endzeit, lehrveranstaltung.Bezeichnung FROM durchfuehrung "
					+ " JOIN semesterverband ON semesterverband.SVNr = durchfuehrung.SVNr "
					+ " JOIN zeitslot ON zeitslot.ZeitNr = durchfuehrung.ZeitNr  "
					+ " JOIN lehrveranstaltung ON lehrveranstaltung.LVNr = durchfuehrung.LVNr  "
					+ " WHERE (semesterverband.SemesterHalbjahr = " + sv + ")";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Lehrveranstaltung lv = new Lehrveranstaltung();
				lv.setRaumWochentag(rs.getString("zeitslot.Wochentag"));
				lv.setBezeichnung(rs.getString("lehrveranstaltung.Bezeichnung"));
				lv.setRaumZeit(rs.getInt("zeitslot.Anfangszeit"));
				lv.setRaumZeitEnde(rs.getInt("zeitslot.Endzeit"));

				result.addElement(lv);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		} finally {
			//Alles schließen, finally wird immer ausgeführt, egal ob es einen Fehler gibt oder nicht.
			DBVerbindung.closeAll(rs, stmt, con);
		}

		return result;
	}
}
