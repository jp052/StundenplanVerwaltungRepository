package de.hdm.gruppe3.stundenplanverwaltung.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.gruppe3.stundenplanverwaltung.shared.RaumBelegtException;
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
	 * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine
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
	 * Anlegen der LVDurchf�hrung
	 * 
	 * @param lvd
	 * @return LVDurchfuehrung
	 * @throws RaumBelegtException 
	 */
	public LVDurchfuehrung anlegen(int svId, int raumId, int lvId, Zeitslot zeitslot) throws RaumBelegtException {			
		Connection con = DBVerbindung.connection();
		
		LVDurchfuehrung lvd = new LVDurchfuehrung();	
		try {
			Statement stmt = con.createStatement();
			
			//TODO Zeitslot zuerst anlgend und die Id auslesen
			String sql = "INSERT INTO Durchfuehrung (ZeitNr, SVNr, RaumNr, LVNr) "+ "VALUES (" + zeitslot.getId() + "," + svId+ "," + raumId + "," + lvId + ")";

			// Ausf�hren des SQL Statement
			stmt.executeUpdate(sql);
			
			
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		//TODO Lehrveranstaltungs Objekt mit Daten f�llen!
		return lvd;

	}

	public LVDurchfuehrung modifizieren(int lvdNr, int svNr, int raumNr, int lvNr, Zeitslot zeitslot) {
		// DB-Verbindung holen
		Connection con = DBVerbindung.connection();
		
		LVDurchfuehrung lvd = new LVDurchfuehrung();

		try {
			Statement stmt = con.createStatement();

			// Die Query die ausgef�hrt werden soll.
			String sql = "UPDATE Durchfuehrung SET " +				
					"ZeitNr="	+ zeitslot.getId() + 
					", SVNr=" + svNr + 
					", RaumNr="	+ raumNr + 
					", LVNr=" + lvNr + 
					" WHERE LVDNr="	+ lvdNr;

			// Query ausf�hren
			stmt.executeUpdate(sql);

		} catch (SQLException e2) {
			// Den Fehler in der Kommandozeile Anzeigen.
			e2.printStackTrace();
		}

		// R�ckgabe des Objektes
		//TODO Lehrveranstaltungs Objekt mit Daten f�llen!
		return lvd;
	}

	public LVDurchfuehrung loeschen(LVDurchfuehrung lvd) {
		// DB-Verbindung holen
		Connection con = DBVerbindung.connection();

		try {
			Statement stmt = con.createStatement();

			// Die SQL Query die ausgef�hrt werden soll.
			String sql = "DELETE FROM Durchfuehrung " + "WHERE LVDNr="
					+ lvd.getId();

			// Die SQL Query ausf�hren.
			stmt.executeUpdate(sql);

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return lvd;
	}

	public LVDurchfuehrung findeId(int lvdNr) {
		// DB-Verbindung holen
		Connection con = DBVerbindung.connection();
		
		LVDurchfuehrung lvd = new LVDurchfuehrung();

		try {
			Statement stmt = con.createStatement();

			// Die SQL Query die ausgef�hrt werden soll.
			String sql = "SELECT * FROM Durchfuehrung WHERE LVDNr=" + lvdNr
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
		}

		return lvd;
	}

}
