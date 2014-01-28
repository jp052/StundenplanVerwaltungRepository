/* 
 * SemesterverbandMapper.java 
 * 
 * Version: 
 *     1.0
 * 
 * Revisions: 
 *     1.0
 */

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
public class SemesterverbandMapper {
	/**
	 * damit sie in der statischen Methode svMapper() benützt werden kann muss
	 * es auch hier <code>static</code> sein
	 * 
	 * @see svMapper()
	 */
	private static SemesterverbandMapper svMapper = null;

	/**
	 * mit protected wird die Instanziierung mit <code>new</code> verhindert
	 */
	protected SemesterverbandMapper() {
	}

	/**
	 * die svMapper() kann man mit der Klasse aufrufen. So kann man sicher
	 * gehen, dass nur ein Objekt instanziiert wird.
	 * 
	 * @see svMapper
	 */
	public static SemesterverbandMapper svMapper() {
		if (svMapper == null) {
			svMapper = new SemesterverbandMapper();
		}

		return svMapper;
	}

	/**
	 * Methode um ein Semesterverband in die Datenbank anzulegen
	 * 
	 * @param sv
	 * @return
	 * @throws Exception
	 */
	public Semesterverband anlegen(Semesterverband sv) throws Exception {
		Connection con = DBVerbindung.connection();
		Statement stmt = null;

		try {
			stmt = con.createStatement();

			// Hier findet die SQL Statement statt
			stmt.executeUpdate("INSERT INTO semesterverband (SVNr, AnzahlStudierende, SemesterHalbjahr, Jahrgang) "
					+ "VALUES ( "
					+ "NULL,'"
					+ sv.getAnzahlStudenten()
					+ "','"
					+ sv.getSemester() + "','" + sv.getJahrgang() + "')");
			// }
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		} finally {
			//Alles schließen, finally wird immer ausgeführt, egal ob es einen Fehler gibt oder nicht.
			DBVerbindung.closeAll(null, stmt, con);
		}

		return sv;

	}

	/**
	 * Methode, mit dem man ein Datensatz verändern kann
	 * 
	 * @param sv
	 * @return
	 * @throws Exception
	 */
	public Semesterverband modifizieren(Semesterverband sv) throws Exception {
		Connection con = DBVerbindung.connection();
		Statement stmt = null;

		try {
			stmt = con.createStatement();

			stmt.executeUpdate("UPDATE semesterverband "
					+ "SET AnzahlStudierende=" + sv.getAnzahlStudenten()
					+ " , SemesterHalbjahr=" + sv.getSemester() + ", Jahrgang="
					+ sv.getJahrgang() + " WHERE SVNr=" + sv.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		} finally {
			//Alles schließen, finally wird immer ausgeführt, egal ob es einen Fehler gibt oder nicht.
			DBVerbindung.closeAll(null, stmt, con);
		}

		return sv;
	}

	/**
	 * Methode um einen Datensatz aus der Datenbank zu löschen
	 * 
	 * @param sv
	 * @return
	 * @throws Exception
	 */
	public Semesterverband loeschen(Semesterverband sv) throws Exception {
		Connection con = DBVerbindung.connection();
		Statement stmt = null;

		try {
			stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM semesterverband " + "WHERE SVNr="
					+ sv.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		} finally {
			//Alles schließen, finally wird immer ausgeführt, egal ob es einen Fehler gibt oder nicht.
			DBVerbindung.closeAll(null, stmt, con);
		}
		
		return sv;
	}

	/**
	 * Methode, mit dem man mittels der id den Semesterverband finden kann.
	 * 
	 * @param i
	 * @return
	 * @throws Exception
	 */
	public Semesterverband findeId(int i) throws Exception {
		// DB-Verbindung holen
		Connection con = DBVerbindung.connection();
		Statement stmt = null;
		ResultSet rs = null;

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			rs = stmt.executeQuery("SELECT SVNr, AnzahlStudierende, SemesterHalbjahr, Jahrgang FROM semesterverband "
							+ "WHERE SVNr=" + i + " ORDER BY SVNr");

			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Semesterverband sv = new Semesterverband();
				sv.setId(rs.getInt("SVNr"));
				sv.setAnzahlStudenten(rs.getInt("AnzahlStudierende"));
				sv.setSemester(rs.getInt("SemesterHalbjahr"));
				sv.setJahrgang(rs.getInt("Jahrgang"));

				return sv;
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
	 * Mit dieser Methode kann man mit dem svHalbjahr den Semester aus der
	 * Datenbank finden.
	 * 
	 * @param svHalbjahr
	 * @return
	 * @throws Exception
	 */
	public Semesterverband findeSVHalbjahr(int svHalbjahr) throws Exception {
		// DB-Verbindung holen
		Connection con = DBVerbindung.connection();
		Statement stmt = null;
		ResultSet rs = null;

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			rs = stmt.executeQuery("SELECT * FROM semesterverband "
					+ "WHERE svnr=" + "'" + svHalbjahr + "'");

			/*
			 * Da raum Primärschlüssel ist, kann raumx. nur ein Tupel
			 * zurückgegeben werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Semesterverband sv = new Semesterverband();
				sv.setId(rs.getInt("SVNr"));
				sv.setAnzahlStudenten(rs.getInt("AnzahlStudierende"));
				sv.setSemester(rs.getInt("SemesterHalbjahr"));
				sv.setJahrgang(rs.getInt("Jahrgang"));

				return sv;
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
	 * Alle Datensätze aus der Tabelle Semesterverband werden herausgelesen und
	 * in ein Objekt gespeichert.
	 * 
	 * @return
	 * @throws Exception
	 */
	public Vector<Semesterverband> findeAlle() throws Exception {
		Connection con = DBVerbindung.connection();
		Statement stmt = null;
		ResultSet rs = null;

		// Vector mit angeforderten Objekten
		Vector<Semesterverband> result = new Vector<Semesterverband>();

		try {
			stmt = con.createStatement();

			String sql = "SELECT * FROM semesterverband  ORDER BY SemesterHalbjahr";

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Semesterverband sv = new Semesterverband();
				sv.setId(rs.getInt("SVNr"));
				sv.setAnzahlStudenten(rs.getInt("AnzahlStudierende"));
				sv.setSemester(rs.getInt("SemesterHalbjahr"));
				sv.setJahrgang(rs.getInt("Jahrgang"));

				result.addElement(sv);
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

	// String sql =
	// "SELECT FROM Semesterverband LEFT JOIN Lehrveranstaltung ON semesterHalbjahr =4";

	/**
	 * Mit dieser Methode kann man die Vorlesung mit dem Semesterverband sv
	 * finden
	 * 
	 * @param sv
	 * @return
	 * @throws Exception
	 */
	public Lehrveranstaltung findeVL(Semesterverband sv) throws Exception {

		return LehrveranstaltungMapper.lvMapper().findeId(sv.getId());
	}
}
