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
public class RaumMapper {
	/**
	 * damit sie in der statischen Methode raumMapper() benützt werden kann muss
	 * es auch hier <code>static</code> sein
	 * 
	 * @see raumMapper()
	 */
	private static RaumMapper raumMapper = null;

	/**
	 * mit protected wird die Instanziierung mit <code>new</code> verhindert
	 */
	protected RaumMapper() {
	}

	/**
	 * die raumMapper() kann man mit der Klasse aufrufen. So kann man sicher
	 * gehen, dass nur ein Objekt instanziiert wird.
	 * 
	 * @return DAS <code>RaumMapper</code>-Objekt.
	 * @see raumMapper
	 */
	public static RaumMapper raumMapper() {
		if (raumMapper == null) {
			raumMapper = new RaumMapper();
		}

		return raumMapper;
	}

	/**
	 * Methode um ein Semesterverband in die Datenbank anzulegen
	 * 
	 * @param m
	 * @return
	 * @throws Exception
	 */
	public Raum anlegen(Raum m) throws Exception {
		Connection con = DBVerbindung.connection();

		try {
			Statement stmt = con.createStatement();

			// Jetzt erst erfolgt die tatsächliche Einfügeoperation
			stmt.executeUpdate("INSERT INTO raum (RaumNr, Bezeichnung, Kapazitaet) "
					+ "VALUES ( "
					+ "NULL,'"
					+ m.getBezeichnung()
					+ "','"
					+ m.getKapazitaet() + "')");
			// }
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		}

		return m;

	}

	/**
	 * Methode, mit dem man ein Datensatz verändern kann
	 * 
	 * @param raum
	 * @return
	 * @throws Exception
	 */
	public Raum modifizieren(Raum raum) throws Exception {
		Connection con = DBVerbindung.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE raum " + "SET Bezeichnung=\""
					+ raum.getBezeichnung() + "\" ,Kapazitaet="
					+ raum.getKapazitaet() + " WHERE RaumNr=" + raum.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		}

		// Um Analogie zu insert(Raum a) zu wahren, geben wir a zurück
		return raum;
	}

	/**
	 * Methode um einen Datensatz aus der Datenbank zu löschen
	 * 
	 * @param raum
	 * @return
	 * @throws Exception
	 */
	public Raum loeschen(Raum raum) throws Exception {
		Connection con = DBVerbindung.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM raum " + "WHERE RaumNr="
					+ raum.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		}
		return raum;
	}

	/**
	 * Methode, mit dem man mittels dem Raumobjekt den Namen des Raums finden
	 * kann.
	 * 
	 * @param r
	 * @return
	 * @throws Exception
	 */
	public Raum findeName(Raum r) throws Exception {
		// DB-Verbindung holen
		Connection con = DBVerbindung.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt
					.executeQuery("SELECT RaumNr, Bezeichnung, Kapazitaet FROM raum "
							+ "WHERE Bezeichnung="
							+ r.getBezeichnung()
							+ " ORDER BY Bezeichnung");

			/*
			 * Da raum Primärschlüssel ist, kann raumx. nur ein Tupel
			 * zurückgegeben werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Raum raum = new Raum();
				raum.setId(rs.getInt("RaumNr"));
				raum.setBezeichnung(rs.getString("Bezeichnung"));
				raum.setKapazitaet(rs.getInt("Kapazitaet"));

				return raum;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
			// return null;
		}

		return null;
	}

	/**
	 * Methode, mit dem man mittels dem Raumbezeichnung den Raum findet.
	 * 
	 * @param r
	 * @return
	 * @throws Exception
	 */
	public Raum findeName(String r) throws Exception {
		// DB-Verbindung holen
		Connection con = DBVerbindung.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt
					.executeQuery("SELECT RaumNr, Bezeichnung, Kapazitaet FROM raum "
							+ "WHERE Bezeichnung="
							+ "'"
							+ r
							+ "'"
							+ " ORDER BY Bezeichnung");

			/*
			 * Da raum Primärschlüssel ist, kann raumx. nur ein Tupel
			 * zurückgegeben werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Raum raum = new Raum();
				raum.setId(rs.getInt("RaumNr"));
				raum.setBezeichnung(rs.getString("Bezeichnung"));
				raum.setKapazitaet(rs.getInt("Kapazitaet"));

				return raum;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
			// return null;
		}

		return null;
	}

	/**
	 * Methode, mit dem man mittels der ID den Raum finden kann.
	 * 
	 * @param i
	 * @return
	 * @throws Exception
	 */
	public Raum findeId(int i) throws Exception {
		// DB-Verbindung holen
		Connection con = DBVerbindung.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt
					.executeQuery("SELECT RaumNr, Bezeichnung, Kapazitaet FROM raum "
							+ "WHERE RaumNr=" + i + " ORDER BY Bezeichnung");

			/*
			 * Da raum Primärschlüssel ist, kann raumx. nur ein Tupel
			 * zurückgegeben werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Raum raum = new Raum();
				raum.setId(rs.getInt("RaumNr"));
				raum.setBezeichnung(rs.getString("Bezeichnung"));
				raum.setKapazitaet(rs.getInt("Kapazitaet"));

				return raum;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
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
	public Vector<Raum> findeAlle() throws Exception {
		Connection con = DBVerbindung.connection();

		// Ergebnisvektor vorbereiten
		Vector<Raum> result = new Vector<Raum>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT RaumNr, Bezeichnung, Kapazitaet FROM raum ORDER BY RaumNr");

			// F�r jeden Eintrag im Suchergebnis wird nun ein Account-Objekt
			// erstellt.
			while (rs.next()) {
				Raum r = new Raum();
				// r.setId(rs.getInt("id"));
				// r.setOwnerID(rs.getInt("owner"));
				r.setId(rs.getInt("RaumNr"));
				r.setBezeichnung(rs.getString("Bezeichnung"));
				r.setKapazitaet(rs.getInt("Kapazitaet"));

				// Hinzuf�gen des neuen Objekts zum Ergebnisvektor
				result.addElement(r);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		}

		// Ergebnisvektor zur�ckgeben
		return result;
	}

	/**
	 * Mit dieser Methode kann man die Vorlesung mit dem Raum raum finden
	 * 
	 * @param raum
	 * @return
	 * @throws Exception
	 */
	public Lehrveranstaltung findeVL(Raum raum) throws Exception {

		return LehrveranstaltungMapper.lvMapper().findeId(raum.getId());
	}

	/**
	 * Mit dieser Methode kann man die belegung des Zeitslots mittels dem raum
	 * finden kann
	 * 
	 * @param raum
	 * @return
	 * @throws Exception
	 */
	public Zeitslot belegt(Raum raum) throws Exception {

		return ZeitslotMapper.zeitslotMapper().findeId(raum.getId());
	}

}
