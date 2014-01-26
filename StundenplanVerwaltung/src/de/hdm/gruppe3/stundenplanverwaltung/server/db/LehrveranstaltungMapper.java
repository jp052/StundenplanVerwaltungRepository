package de.hdm.gruppe3.stundenplanverwaltung.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.google.gwt.user.client.ui.RootPanel;

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

		try {
			Statement stmt = con.createStatement();

			String sql = "INSERT INTO Lehrveranstaltung (LVNr, Bezeichnung, Umfang, Semester, PersonalNr) "
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
			throw new Exception("Datenbank fehler!");
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

		try {
			Statement stmt = con.createStatement();
			// UPDATE Lehrveranstaltung SET Bezeichnung="Software-t" ,

			String sql = "UPDATE Lehrveranstaltung " + "SET Bezeichnung=\""
					+ lv.getBezeichnung() + "\", Umfang=" + lv.getUmfang()
					+ ", Semester=" + lv.getSemester() + ", PersonalNr="
					+ lv.getDozent().getId() + " WHERE LVNr=" + lv.getId();

			stmt.executeUpdate(sql);

		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!");
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

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM Lehrveranstaltung " + "WHERE LVNr="
					+ lv.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!");
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

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt
					.executeQuery("SELECT LVNr, Bezeichnung, Umfang, Semester FROM Lehrveranstaltung "
							+ "WHERE Bezeichnung="
							+ bez
							+ " ORDER BY bezeichnung");

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
			throw new Exception("Datenbank fehler!");
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

		try {
			Statement stmt = con.createStatement();

			// Die Query die ausgeführt werden soll
			String sql = "SELECT * FROM Lehrveranstaltung INNER JOIN Dozent On Lehrveranstaltung.PersonalNr=Dozent.PersonalNr"
					+ " WHERE LVNr=" + lvId;

			// Query ausführen
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				// Lehrveranstaltungsobjekt aus ResultSet erstellen
				Lehrveranstaltung lv = new Lehrveranstaltung();
				lv.setId(rs.getInt("LVNr"));
				lv.setBezeichnung(rs.getString("Bezeichnung"));
				lv.setUmfang(rs.getInt("Umfang"));
				lv.setSemester(rs.getInt("Semester"));

				// Dozent aus ResultSet erstellen
				Dozent d = new Dozent();
				d.setId(rs.getInt("Dozent.PersonalNr"));
				d.setVorname(rs.getString("Dozent.Vorname"));
				d.setNachname(rs.getString("Dozent.Nachname"));
				lv.setDozent(d);

				return lv;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!");
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

		// Ergebnisvektor vorbereiten
		Vector<Lehrveranstaltung> result = new Vector<Lehrveranstaltung>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT * FROM Lehrveranstaltung INNER JOIN Dozent ON Lehrveranstaltung.personalNr = Dozent.personalNr ");

			// Für jeden Eintrag im Suchergebnis wird nun ein Account-Objekt
			// erstellt.
			while (rs.next()) {
				Lehrveranstaltung lv = new Lehrveranstaltung();
				Dozent dozent = new Dozent();

				// Dozent füllen
				dozent.setId(rs.getInt("Dozent.PersonalNr"));
				dozent.setVorname(rs.getString("Dozent.Vorname"));
				dozent.setNachname(rs.getString("Dozent.Nachname"));

				// Lv füllen
				lv.setId(rs.getInt("Lehrveranstaltung.LVNr"));
				lv.setBezeichnung(rs.getString("Lehrveranstaltung.Bezeichnung"));
				lv.setUmfang(rs.getInt("Lehrveranstaltung.Umfang"));
				lv.setSemester(rs.getInt("Lehrveranstaltung.Semester"));
				lv.setDozentName(rs.getString("dozent.nachname"));
				lv.setDozent(dozent);

				// Hinzuf¸gen des neuen Objekts zum Ergebnisvektor
				result.addElement(lv);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!");
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
	 * Findet alle Lehrveranstaltungen von einem Dozent
	 * 
	 * @param dozentId
	 * @return
	 */
	public Vector<Lehrveranstaltung> findeByDozent(int dozentId) {
		// TODO: mit logik füllen
		return null;
	}

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

		// Vector mit angeforderten Objekten
		Vector<Lehrveranstaltung> result = new Vector<Lehrveranstaltung>();

		try {
			Statement stmt = con.createStatement();

			String sql = "SELECT * FROM Dozent JOIN Lehrveranstaltung  ON lehrveranstaltung.personalNr = dozent.personalNr WHERE dozent.personalNr = "
					+ dozentID;

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Lehrveranstaltung lv = new Lehrveranstaltung();
				lv.setId(rs.getInt("Lehrveranstaltung.LVNr"));
				lv.setBezeichnung(rs.getString("Lehrveranstaltung.Bezeichnung"));
				lv.setUmfang(rs.getInt("Lehrveranstaltung.Umfang"));
				lv.setSemester(rs.getInt("Lehrveranstaltung.Semester"));

				result.addElement(lv);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!");
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

		// Vector mit angeforderten Objekten
		Vector<Lehrveranstaltung> result = new Vector<Lehrveranstaltung>();

		try {
			Statement stmt = con.createStatement();

			// String sql =
			// "SELECT * FROM Raum LEFT JOIN Lehrveranstaltung ON raum.bezeichnung = "
			// + bez;

			String sql = "SELECT zeitslot.Wochentag, zeitslot.Anfangszeit, raum.Bezeichnung, zeitslot.Endzeit, lehrveranstaltung.bezeichnung "
					+ " FROM durchfuehrung "
					+ " JOIN raum ON raum.RaumNr = durchfuehrung.RaumNr "
					+ " JOIN zeitslot ON zeitslot.ZeitNr = durchfuehrung.ZeitNr "
					+ " JOIN lehrveranstaltung ON lehrveranstaltung.LVNr = durchfuehrung.LVNr "
					+ " WHERE (raum.RaumNr = '" + bez + "')";
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Lehrveranstaltung lv = new Lehrveranstaltung();
				lv.setRaumWochentag(rs.getString("zeitslot.Wochentag"));
				lv.setBezeichnung(rs.getString("Lehrveranstaltung.Bezeichnung"));
				lv.setRaumZeit(rs.getInt("zeitslot.Anfangszeit"));
				lv.setRaumZeitEnde(rs.getInt("zeitslot.Endzeit"));

				result.addElement(lv);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!");
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

		// Vector mit angeforderten Objekten
		Vector<Lehrveranstaltung> result = new Vector<Lehrveranstaltung>();

		try {
			Statement stmt = con.createStatement();

			// String sql =
			// "SELECT * FROM Raum LEFT JOIN Lehrveranstaltung ON raum.bezeichnung = "
			// + bez;

			String sql = "SELECT zeitslot.Wochentag, zeitslot.Anfangszeit, semesterverband.semesterHalbjahr, zeitslot.Endzeit, lehrveranstaltung.bezeichnung FROM durchfuehrung "
					+ " JOIN semesterverband ON semesterverband.svnr = durchfuehrung.SVNr "
					+ " JOIN zeitslot ON zeitslot.ZeitNr = durchfuehrung.ZeitNr  "
					+ " JOIN lehrveranstaltung ON lehrveranstaltung.LVNr = durchfuehrung.LVNr  "
					+ " WHERE (semesterverband.semesterHalbjahr = " + sv + ")";
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Lehrveranstaltung lv = new Lehrveranstaltung();
				lv.setRaumWochentag(rs.getString("zeitslot.Wochentag"));
				lv.setBezeichnung(rs.getString("Lehrveranstaltung.Bezeichnung"));
				lv.setRaumZeit(rs.getInt("zeitslot.Anfangszeit"));
				lv.setRaumZeitEnde(rs.getInt("zeitslot.Endzeit"));

				result.addElement(lv);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!");
		}

		return result;
	}
}
