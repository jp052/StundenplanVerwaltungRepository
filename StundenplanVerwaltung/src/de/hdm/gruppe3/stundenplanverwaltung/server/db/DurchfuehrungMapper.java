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
public class DurchfuehrungMapper {

	/**
	 * damit sie in der statischen Methode dfMapper() benützt werden kann muss
	 * es auch hier <code>static</code> sein
	 */
	private static DurchfuehrungMapper dfMapper = null;

	/**
	 * Protected Verhindert, dass mit new ein neuer Mapper erstellt werden kann.
	 */
	protected DurchfuehrungMapper() {
	}

	/**
	 * die svMapper() kann man mit der Klasse aufrufen. So kann man sicher
	 * gehen, dass nur ein Objekt instanziiert wird.
	 * 
	 * @return dfMapper
	 * @see dfMapper
	 */
	public static DurchfuehrungMapper dfMapper() {
		if (dfMapper == null) {
			dfMapper = new DurchfuehrungMapper();
		}

		return dfMapper;
	}

	/**
	 * Anlegen der LVDurchführung
	 * 
	 * @param lvd
	 * @return LVDurchfuehrung
	 * @throws Exception
	 */
	public LVDurchfuehrung anlegen(int svId, int raumId, int lvId,
			Zeitslot zeitslot) throws Exception {
		Connection con = DBVerbindung.connection();

		LVDurchfuehrung lvd = new LVDurchfuehrung();
		try {
			Statement stmt = con.createStatement();

			// Zeitslot zuerst anlgend und die Id auslesen
			String sql = "INSERT INTO durchfuehrung (ZeitNr, SVNr, RaumNr, LVNr) "
					+ "VALUES ("
					+ zeitslot.getId()
					+ ","
					+ svId
					+ ","
					+ raumId
					+ "," + lvId + ")";

			// Ausf�hren des SQL Statement
			stmt.executeUpdate(sql);

		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!");
		}

		// Lehrveranstaltungs Objekt mit Daten füllen!
		return lvd;

	}

	/**
	 * Methode, mit dem man ein Datensatz verändern kann
	 * 
	 * @param lvdNr
	 * @param svNr
	 * @param raumNr
	 * @param lvNr
	 * @param zeitslot
	 * @return
	 * @throws Exception
	 */
	public LVDurchfuehrung modifizieren(int lvdNr, int svNr, int raumNr,
			int lvNr, Zeitslot zeitslot) throws Exception {
		// DB-Verbindung holen
		Connection con = DBVerbindung.connection();

		LVDurchfuehrung lvd = new LVDurchfuehrung();

		try {
			Statement stmt = con.createStatement();

			// Die Query die ausgef�hrt werden soll.
			String sql = "UPDATE durchfuehrung SET " + "ZeitNr="
					+ zeitslot.getId() + ", SVNr=" + svNr + ", RaumNr="
					+ raumNr + ", LVNr=" + lvNr + " WHERE LVDNr=" + lvdNr;

			// Query ausf�hren
			stmt.executeUpdate(sql);

		} catch (SQLException e2) {
			// Den Fehler in der Kommandozeile Anzeigen.
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!");
		}

		// Rückgabe des Objektes
		// Lehrveranstaltungs Objekt mit Daten füllen!
		return lvd;
	}

	/**
	 * Methode um einen Datensatz aus der Datenbank zu löschen
	 * 
	 * @param lvd
	 * @return
	 * @throws Exception
	 */
	public LVDurchfuehrung loeschen(LVDurchfuehrung lvd) throws Exception {
		// DB-Verbindung holen
		Connection con = DBVerbindung.connection();

		try {
			Statement stmt = con.createStatement();

			// Die SQL Query die ausgef�hrt werden soll.
			String sql = "DELETE FROM durchfuehrung " + "WHERE LVDNr="
					+ lvd.getId();

			// Die SQL Query ausf�hren.
			stmt.executeUpdate(sql);

		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!");
		}
		return lvd;
	}

	/**
	 * Methode, mit dem man mittels der id den LVDurchführung findet.
	 * 
	 * @param lvdNr
	 * @return
	 * @throws Exception
	 */
	public LVDurchfuehrung findeId(int lvdNr) throws Exception {
		// DB-Verbindung holen
		Connection con = DBVerbindung.connection();

		LVDurchfuehrung lvd = new LVDurchfuehrung();

		try {
			Statement stmt = con.createStatement();

			// Die SQL Query die ausgef�hrt werden soll.
			String sql = "SELECT * FROM durchfuehrung WHERE LVDNr=" + lvdNr
					+ " ORDER BY LVDNr";

			// Die SQL Query ausf�hren.
			ResultSet rs = stmt.executeQuery(sql);

			// Nur ausf�hren wenn das Result nicht null ist
			if (rs.next()) {
				// Ben�gtigtes Objekte aus dem ResultSet erstellen.

				// RaumMapper um alle Attribute aus der Datebank zu lesen, ind
				// dem ResultSet ist nur die Id vorhanden.
				// Raum Objekt erstellen indem die Id and den Mapper �bergeben
				// wird
				RaumMapper rMapper = RaumMapper.raumMapper();
				Raum raum = new Raum();
				raum = rMapper.findeId(rs.getInt("RaumNr"));

				// SemesterverbandMapper um alle Attribute aus der Datebank zu
				// lesen, ind dem ResultSet ist nur die Id vorhanden.
				// Semesterverband Objekt erstellen indem die Id and den Mapper
				// �bergeben wird
				SemesterverbandMapper svMapper = SemesterverbandMapper
						.svMapper();
				Semesterverband sv = new Semesterverband();
				sv = svMapper.findeId(rs.getInt("SVNr"));

				// LehrveranstaltungMapper um alle Attribute aus der Datebank zu
				// lesen, ind dem ResultSet ist nur die Id vorhanden.
				// Lehrveranstaltung Objekt erstellen indem die Id and den
				// Mapper �bergeben wird.
				LehrveranstaltungMapper lvMapper = LehrveranstaltungMapper
						.lvMapper();
				Lehrveranstaltung lv = new Lehrveranstaltung();
				lv = lvMapper.findeId(rs.getInt("LVNr"));

				// LehrveranstaltungMapper um alle Attribute aus der Datebank zu
				// lesen, ind dem ResultSet ist nur die Id vorhanden.
				// Lehrveranstaltung Objekt erstellen indem die Id and den
				// Mapper �bergeben wird.
				ZeitslotMapper zMapper = ZeitslotMapper.zeitslotMapper();
				Zeitslot z = new Zeitslot();
				z = zMapper.findeId(rs.getInt("ZeitNr"));

				// Jetzt das Durchf�hrungs Objekt mit allen Objekten f�llen

				lvd.setId(rs.getInt("LVDNr"));
				lvd.setId(rs.getInt("LVDNr"));
				lvd.setRaum(raum);
				lvd.setSemesterverband(sv);
				lvd.setLehrveranstaltung(lv);
				lvd.setZeitslot(z);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!");
		}

		return lvd;
	}

	/**
	 * Holt alle vorhandenen Durchführungen mit allen Elementen.
	 * 
	 * @return Vector mit LVDurchführungsobjekten
	 * @throws Exception
	 */
	public Vector<LVDurchfuehrung> findeAlle() throws Exception {
		Connection con = DBVerbindung.connection();

		// Ergebnisvektor vorbereiten
		Vector<LVDurchfuehrung> result = new Vector<LVDurchfuehrung>();

		try {
			Statement stmt = con.createStatement();

			String sql = "SELECT * FROM durchfuehrung ORDER BY LVDNr";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				// Benötigte Objekte
				LVDurchfuehrung lvd = new LVDurchfuehrung();
				Zeitslot zeitslot = new Zeitslot();
				Semesterverband sv = new Semesterverband();
				Raum raum = new Raum();
				Lehrveranstaltung lv = new Lehrveranstaltung();

				// Mapper holen
				ZeitslotMapper zMapper = ZeitslotMapper.zeitslotMapper();
				SemesterverbandMapper svMapper = SemesterverbandMapper
						.svMapper();
				RaumMapper rMapper = RaumMapper.raumMapper();
				LehrveranstaltungMapper lvMapper = LehrveranstaltungMapper
						.lvMapper();

				// Objekte füllen mit den entsprechenden Mapper Methoden
				zeitslot = zMapper.findeId(rs.getInt("ZeitNr"));
				sv = svMapper.findeId(rs.getInt("SVNr"));
				raum = rMapper.findeId(rs.getInt("RaumNr"));
				lv = lvMapper.findeId(rs.getInt("LVNr"));

				// LVDurchführungsobjekt befüllen
				lvd.setId(rs.getInt("LVDNr"));
				lvd.setZeitslot(zeitslot);
				lvd.setSemesterverband(sv);
				lvd.setRaum(raum);
				lvd.setLehrveranstaltung(lv);

				// Befüllte Durchführung dem Vector hinzufügen
				result.addElement(lvd);

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		}

		// Ergebnisvektor zur¸ckgeben
		return result;
	}

}
