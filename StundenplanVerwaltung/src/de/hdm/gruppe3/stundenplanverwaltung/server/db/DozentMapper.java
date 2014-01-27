package de.hdm.gruppe3.stundenplanverwaltung.server.db;

import java.sql.*;
import java.util.Vector;

import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.*;

/**
 * @author Yasemin Karakoc, Jan Plank, Selim Karazehir, Julia Hammerer, Denis
 *         Fürst, Daniel Krakow 
 *         In Anlehnung an Hr. Prof. Dr. Thies
 */
public class DozentMapper {

	/**
	 * damit sie in der statischen Methode svMapper() benützt werden kann muss
	 * es auch hier <code>static</code> sein
	 * 
	 * @see dozentMapper()
	 */
	private static DozentMapper dozentMapper = null;

	/**
	 * mit protected wird die Instanziierung mit <code>new</code> verhindert
	 */
	protected DozentMapper() {
	}

	/**
	 * die dozentMapper() kann man mit der Klasse aufrufen. So kann man sicher
	 * gehen, dass nur ein Objekt instanziiert wird.
	 * 
	 * @return dozentMapper
	 * @see dozentMapper
	 */
	public static DozentMapper dozentMapper() {
		if (dozentMapper == null) {
			dozentMapper = new DozentMapper();
		}

		return dozentMapper;
	}

	/**
	 * Methode um ein Dozent in die Datenbank anzulegen
	 * 
	 * @param d
	 * @return
	 * @throws Exception
	 */
	public Dozent anlegen(Dozent d) throws Exception {
		Connection con = DBVerbindung.connection();
		Statement stmt = null;

		try {
			stmt = con.createStatement();

			// Jetzt erst erfolgt die tatsächliche Einfügeoperation
			stmt.executeUpdate("INSERT INTO dozent (PersonalNr, Vorname, Nachname) "
					+ "VALUES ( "
					+ "NULL,'"
					+ d.getVorname()
					+ "','"
					+ d.getNachname() + "')");
			// }
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				} 
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new Exception("Connection close Fehler!" + e.toString());
			}
		}

		return d;

	}

	/**
	 * Methode, mit dem man ein Datensatz verändern kann
	 * 
	 * @param dozent
	 * @return
	 * @throws Exception
	 */
	public Dozent modifizieren(Dozent dozent) throws Exception {
		Connection con = DBVerbindung.connection();

		try {
			Statement stmt = con.createStatement();
			// UPDATE `stundenplanverwaltung`.`dozent` SET `Vorname` = 'test2'
			// WHERE `dozent`.`PersonalNr` =2;
			stmt.executeUpdate("UPDATE dozent " + "SET Nachname=\""
					+ dozent.getNachname() + "\", Vorname=\""
					+ dozent.getVorname() + "\" WHERE PersonalNr="
					+ dozent.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		}

		// Um Analogie zu insert(Dozent a) zu wahren, geben wir a zurück
		return dozent;
	}

	/**
	 * Methode um einen Datensatz aus der Datenbank zu löschen
	 * 
	 * @param dozent
	 * @return
	 * @throws Exception
	 */
	public Dozent loeschen(Dozent dozent) throws Exception {
		Connection con = DBVerbindung.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM dozent " + "WHERE PersonalNr="
					+ dozent.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		}

		return dozent;
	}

	/**
	 * Methode, mit dem man mittels dem Namen den Dozenten finden kann.
	 * 
	 * @param dozent
	 * @return
	 * @throws Exception
	 */
	public Dozent findeName(Dozent dozent) throws Exception {
		// DB-Verbindung holen
		Connection con = DBVerbindung.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT PersonalNr, Name, Vorname"
					+ "WHERE Name=" + dozent.getNachname() + " ORDER BY Name");

			/*
			 * Da dozent Primärschlüssel ist, kann dozentx. nur ein Tupel
			 * zurückgegeben werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Dozent d = new Dozent();
				d.setId(rs.getInt("PersonalNr"));
				d.setNachname(rs.getString("Name"));
				d.setVorname(rs.getString("Vorname"));

				return d;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		}

		return null;
	}

	/**
	 * Methode, mit dem man mittels der id den Dozenten finden kann.
	 * 
	 * @param dozentId
	 * @return
	 * @throws Exception
	 */
	public Dozent findeId(int dozentId) throws Exception {
		// DB-Verbindung holen
		Connection con = DBVerbindung.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT * FROM dozent "
					+ "WHERE PersonalNr=" + dozentId);

			/*
			 * Da dozent Primärschlüssel ist, kann dozentx. nur ein Tupel
			 * zurückgegeben werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Dozent dozent = new Dozent();
				// Lehrveranstaltung lv = new Lehrveranstaltung();
				// lv = lvMapper.findeId(rs.getInt("LVNr"));

				dozent.setId(rs.getInt("PersonalNr"));
				dozent.setNachname(rs.getString("Nachname"));
				dozent.setVorname(rs.getString("Vorname"));
				
				return dozent;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());

		}
		return null;
	}

	/**
	 * Alle Datensätze aus der Tabelle Dozent werden herausgelesen und in ein
	 * Objekt gespeichert.
	 * 
	 * @return
	 * @throws Exception
	 */
	public Vector<Dozent> findeAlle() throws Exception {
		Connection con = DBVerbindung.connection();
		Statement stmt = null;
		ResultSet rs = null;
		// Ergebnisvektor vorbereiten
		Vector<Dozent> result = new Vector<Dozent>();

		try {
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT * FROM dozent "
					+ " ORDER BY PersonalNr");

			while (rs.next()) {
				Dozent d = new Dozent();
				d.setId(rs.getInt("PersonalNr"));
				d.setVorname(rs.getString("Vorname"));
				d.setNachname(rs.getString("Nachname"));

				// Hinzufügen des neuen Objekts zum Ergebnisvektor
				result.addElement(d);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				} 
				if (con != null) {
					con.close();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new Exception("Connection close Fehler!" + e.toString());
			}
		}

		// Ergebnisvektor zurückgeben
		return result;
	}

	/**
	 * Methode, mit dem man die Vorlesung mit dem Dozentenobjekt finden kann.
	 * 
	 * @param dozent
	 * @return
	 * @throws Exception
	 */
	public Lehrveranstaltung findeVL(Dozent dozent) throws Exception {

		return LehrveranstaltungMapper.lvMapper().findeId(dozent.getId());
	}

	/**
	 * Methode, mit dem man den Raum des Dozenten herausfinden kann
	 * 
	 * @param dozent
	 * @return
	 * @throws Exception
	 */
	public Raum findeRaum(Dozent dozent) throws Exception {

		return RaumMapper.raumMapper().findeId(dozent.getId());
	}
}
