package de.hdm.gruppe3.stundenplanverwaltung.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.*;

public class DurchfuehrungMapper {

	private static DurchfuehrungMapper dfMapper = null;

	/**
	 * Protected Verhindert, dass mit new ein neuer Mapper erstellt werden kann.
	 */
	protected DurchfuehrungMapper() {
	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>DurchfuehrungMapper.dfMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafÃ¼r sorgt, dass nur eine
	 * einzige Instanz von <code>DurchfuehrungMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> DurchfuehrungMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return DAS <code>DurchfuehrungMapper</code>-Objekt.
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
	 */
	public LVDurchfuehrung anlegen(int svId, int raumId, int lvId, Zeitslot z) {
		Connection con = DBVerbindung.connection();
		
		LVDurchfuehrung lvd = new LVDurchfuehrung();
		
		try {
			Statement stmt = con.createStatement();
			
			//TODO Zeitslot zuerst anlgend und die Id auslesen
			String sql = "INSERT INTO Durchfuehrung (ZeitNr, SVNr, RaumNr, LVNr) "+ "VALUES (" + 1 + "," + svId+ "," + raumId + "," + lvId + ")";

			// Ausführen des SQL Statement
			stmt.executeUpdate(sql);
			
			
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		//TODO Lehrveranstaltungs Objekt mit Daten füllen!
		return lvd;

	}

	public LVDurchfuehrung modifizieren(int lvdNr, int svNr, int raumNr, int lvNr, int zeitNr) {
		// DB-Verbindung holen
		Connection con = DBVerbindung.connection();
		
		LVDurchfuehrung lvd = new LVDurchfuehrung();

		try {
			Statement stmt = con.createStatement();

			// Die Query die ausgeführt werden soll.
			String sql = "UPDATE LVDurchfuerhung SET " +				
					"ZeitNr="	+ zeitNr + 
					", SVNr=" + svNr + 
					", RaumNr="	+ raumNr + 
					", LVNr=" + lvNr + 
					" WHERE LVDNr="	+ lvdNr;

			// Query ausführen
			stmt.executeUpdate(sql);

		} catch (SQLException e2) {
			// Den Fehler in der Kommandozeile Anzeigen.
			e2.printStackTrace();
		}

		// Rückgabe des Objektes
		//TODO Lehrveranstaltungs Objekt mit Daten füllen!
		return lvd;
	}

	public LVDurchfuehrung loeschen(LVDurchfuehrung lvd) {
		// DB-Verbindung holen
		Connection con = DBVerbindung.connection();

		try {
			Statement stmt = con.createStatement();

			// Die SQL Query die ausgeführt werden soll.
			String sql = "DELETE FROM LVDurchfuerhung " + "WHERE LVDNr="
					+ lvd.getId();

			// Die SQL Query ausführen.
			stmt.executeUpdate(sql);

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return lvd;
	}

	public LVDurchfuehrung findeId(int lvdNr) {
		// DB-Verbindung holen
		Connection con = DBVerbindung.connection();

		try {
			Statement stmt = con.createStatement();

			// Die SQL Query die ausgeführt werden soll.
			String sql = "SELECT * FROM Durchfuehrung WHERE LVDNr=" + lvdNr
					+ " ORDER BY LVDNr";

			// Die SQL Query ausführen.
			ResultSet rs = stmt.executeQuery(sql);

			// Nur ausführen wenn das Result nicht null ist
			if (rs.next()) {
				// Benögtigtes Objekte aus dem ResultSet erstellen.

				// RaumMapper um alle Attribute aus der Datebank zu lesen, ind
				// dem ResultSet ist nur die Id vorhanden.
				// Raum Objekt erstellen indem die Id and den Mapper übergeben
				// wird
				RaumMapper rMapper = RaumMapper.raumMapper();
				Raum raum = new Raum();
				raum = rMapper.findeId(rs.getInt("RaumNr"));

				// SemesterverbandMapper um alle Attribute aus der Datebank zu
				// lesen, ind dem ResultSet ist nur die Id vorhanden.
				// Semesterverband Objekt erstellen indem die Id and den Mapper
				// übergeben wird
				SemesterverbandMapper svMapper = SemesterverbandMapper
						.svMapper();
				Semesterverband sv = new Semesterverband();
				sv = svMapper.findeId(rs.getInt("SVNr"));

				// LehrveranstaltungMapper um alle Attribute aus der Datebank zu
				// lesen, ind dem ResultSet ist nur die Id vorhanden.
				// Lehrveranstaltung Objekt erstellen indem die Id and den
				// Mapper übergeben wird.
				LehrveranstaltungMapper lvMapper = LehrveranstaltungMapper
						.lvMapper();
				Lehrveranstaltung lv = new Lehrveranstaltung();
				lv = lvMapper.findeId(rs.getInt("LVId"));

				// LehrveranstaltungMapper um alle Attribute aus der Datebank zu
				// lesen, ind dem ResultSet ist nur die Id vorhanden.
				// Lehrveranstaltung Objekt erstellen indem die Id and den
				// Mapper übergeben wird.
				ZeitslotMapper zMapper = ZeitslotMapper.zeitslotMapper();
				Zeitslot z = new Zeitslot();
				z = zMapper.findeId(rs.getInt("ZeitNr"));

				// Jetzt das Durchführungs Objekt mit allen Objekten füllen
				LVDurchfuehrung lvd = new LVDurchfuehrung();
				lvd.setId(rs.getInt("LVDNr"));
				lvd.setId(rs.getInt("LVDNr"));
				lvd.setRaum(raum);
				lvd.setSemesterverband(sv);
				lvd.setLehrveranstaltung(lv);
				lvd.setZeitslot(z);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}

		return null;
	}

}
