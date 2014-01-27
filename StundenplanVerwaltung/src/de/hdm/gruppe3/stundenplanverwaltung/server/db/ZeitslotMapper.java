package de.hdm.gruppe3.stundenplanverwaltung.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import de.hdm.gruppe3.stundenplanverwaltung.shared.RaumBelegtException;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.*;

/**
 * @author Yasemin Karakoc, Jan Plank, Selim Karazehir, Julia Hammerer, Denis
 *         Fürst, Daniel Krakow 
 *         In Anlehnung an Hr. Prof. Dr. Thies
 */
public class ZeitslotMapper {
	/**
	 * damit sie in der statischen Methode zeitMapper() benützt werden kann muss
	 * es auch hier <code>static</code> sein
	 * 
	 * @see zeitslotMapper()
	 */
	private static ZeitslotMapper zeitslotMapper = null;

	/**
	 * mit protected wird die Instanziierung mit <code>new</code> verhindert
	 */
	protected ZeitslotMapper() {
	}

	/**
	 * die zeitslotMapper() kann man mit der Klasse aufrufen. So kann man sicher
	 * gehen, dass nur ein Objekt instanziiert wird.
	 * 
	 * @return
	 */
	public static ZeitslotMapper zeitslotMapper() {
		if (zeitslotMapper == null) {
			zeitslotMapper = new ZeitslotMapper();
		}

		return zeitslotMapper;
	}

	/**
	 * Methode um ein Zeitslot in die Datenbank anzulegen
	 * 
	 * @param zeitslot
	 * @return zeitslot
	 * @throws Exception
	 */
	public Zeitslot anlegen(Zeitslot zeitslot) throws Exception {
		Connection con = DBVerbindung.connection();

		// Fall Zeitslot mit selber Anfangszeit, Endzeit und Wochentag existiert
		// muss in der Durchführung nur noch auf dem
		// Primäschlüssen verwiesen werden und kein neuer Zeitslot muss angelegt
		// werden.
		// Ein Zeitslot kann einmal in jedem Raum verwendet werden.
		zeitslot = this.insertIdIntoZeitslot(zeitslot);

		// Wenn checkExistenz kein Id in den Zeitslot gefüllt hat muss dieser
		// angelegt werden
		if (zeitslot.getId() == 0) {
			try {
				Statement stmt = con.createStatement();
				String sql = "INSERT INTO zeitslot (ZeitNr, Wochentag, Anfangszeit, Endzeit) "
						+ "VALUES ( "
						+ "NULL, \""
						+ zeitslot.getWochentag()
						+ "\","
						+ zeitslot.getAnfangszeit()
						+ ","
						+ zeitslot.getEndzeit() + ")";

				stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.getGeneratedKeys();

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					// Holt den generierte Primärschlüssel der die Id es
					// Zeitslots sind.
					zeitslot.setId(rs.getInt(1));
				}

			} catch (SQLException e2) {
				e2.printStackTrace();
				throw new Exception("Datenbank fehler!" + e2.toString());
			}

		}
		return zeitslot;
	}

	/**
	 * Methode, mit dem man ein Datensatz verändern kann
	 * 
	 * @param zeitslot
	 * @return
	 * @throws Exception
	 */
	public Zeitslot modifizieren(Zeitslot zeitslot) throws Exception {
		Connection con = DBVerbindung.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE zeitslot " + "SET Wochentag=\""
					+ zeitslot.getWochentag() + "\" " + "SET Anfangszeit=\""
					+ zeitslot.getAnfangszeit() + "WHERE ZeitNr="
					+ zeitslot.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		}

		// Um Analogie zu insert(Zeitslot a) zu wahren, geben wir a zurück
		return zeitslot;
	}

	/**
	 * Methode um einen Datensatz aus der Datenbank zu löschen
	 * 
	 * @param zeitslot
	 * @return
	 * @throws Exception
	 */
	public Zeitslot loeschen(Zeitslot zeitslot) throws Exception {
		Connection con = DBVerbindung.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM zeitslot " + "WHERE ZeitNr="
					+ zeitslot.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		}
		return zeitslot;
	}

	/**
	 * Methode, mit dem man mittels der id den Semesterverband findet.
	 * 
	 * @param zeitId
	 * @return
	 * @throws Exception
	 */
	public Zeitslot findeId(int zeitId) throws Exception {

		Connection con = DBVerbindung.connection();
		Zeitslot zeitslot = new Zeitslot();

		try {

			Statement stmt = con.createStatement();

			String sql = "SELECT *FROM zeitslot WHERE ZeitNr=" + zeitId;

			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {

				zeitslot.setId(rs.getInt("ZeitNr"));
				zeitslot.setWochentag(rs.getString("Wochentag"));
				zeitslot.setEndzeit(rs.getInt("Endzeit"));
				zeitslot.setAnfangszeit(rs.getInt("Anfangszeit"));

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		}
		return zeitslot;
	}

	/**
	 * Alle Datensätze aus der Tabelle Semesterverband werden herausgelesen und
	 * in ein Objekt gespeichert.
	 * 
	 * @return
	 * @throws Exception
	 */
	public Vector<Zeitslot> findeAlle() throws Exception {
		Connection con = DBVerbindung.connection();

		Vector<Zeitslot> result = new Vector<Zeitslot>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT * FROM zeitslot "
							+ " ORDER BY ZeitNr");

			while (rs.next()) {
				Zeitslot z = new Zeitslot();
				// r.setId(rs.getInt("id"));
				// r.setOwnerID(rs.getInt("owner"));

				result.addElement(z);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		}

		return result;
	}

	/**
	 * Prüft ob ein bestimmter Zeitslot angelegt werden darf. Es wird eine
	 * Abfrage an die Datenbank geschickt, leifert diese kein Ergenis zurück
	 * existiert zu der angegbenen Zeit in dem angegebenen Raum noch keine
	 * Durchführung, also kann sie angelgt werden. Gibt es jedoch ein Ergenisf
	 * aus der Abfrage ist der Raum zu der Zeit bereits belegt.
	 * 
	 * @param zeitslot
	 *            , raumId
	 * @return
	 * @throws Exception 
	 */
	public boolean checkVerfuegbarkeit(Zeitslot zeitslot, int raumId)
			throws Exception {
		Connection con = DBVerbindung.connection();

		// Verfügbarkeit standardmäßig auf true setzen.
		boolean isVerfuegbar = true;

		// Der
		ArrayList<Boolean> belegungsArray = new ArrayList<Boolean>();

		for (int counter = 8; counter < 18; counter++) {
			belegungsArray.add(false);
		}

		try {
			Statement stmt = con.createStatement();

			// Join über 3 Tabellen, Zeitslot, Durchfuehrung und Raum
			String sql = "Select * From zeitslot "
					+ "Inner Join (durchfuehrung Inner Join raum on durchfuehrung.RaumNr=raum.RaumNr) on zeitslot.ZeitNr=durchfuehrung.ZeitNr "
					+ "Where raum.raumNr=" + raumId + " and Wochentag=\""
					+ zeitslot.getWochentag() + "\"";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int belegteAnfangszeit = rs.getInt("zeitslot.Anfangszeit");
				int belegteEndzeit = rs.getInt("zeitslot.Endzeit");
				int belegtDauer = belegteEndzeit - belegteAnfangszeit;
				int arrayPos = belegteAnfangszeit - 8;

				for (int dauerCounter = 1; dauerCounter <= belegtDauer; dauerCounter++) {
					belegungsArray.add(arrayPos, true);
					arrayPos++;
				}

				// Wenn das ResultSet ein erebnis liefert, isVerfuegbar auf false
				// setzen.

			}

			int neueAnfangszeit = zeitslot.getAnfangszeit();
			int neueEndzeit = zeitslot.getEndzeit();
			int neuedauer = neueEndzeit - neueAnfangszeit;
			int arrayPos = neueAnfangszeit - 8;
			for (int dauerCounter = 1; dauerCounter <= neuedauer; dauerCounter++) {
				boolean isBelegt = belegungsArray.get(arrayPos);
				if (isBelegt) {
					isVerfuegbar = false;
				}
				arrayPos++;
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
			isVerfuegbar = false;
			throw new RaumBelegtException();

		}

		return isVerfuegbar;
	}

	/**
	 * Schaut ob ein Zeitslot mit Wochentag, Anfangs und Endzeit schon
	 * existiert, wenn ja wird dieser mit der entsprechenden Id gefüllt, wenn
	 * nein bleibt er leer.
	 * 
	 * @param zeitslotNeu
	 * @return Gefundener Zeitslot oder null
	 * @throws Exception
	 */
	public Zeitslot insertIdIntoZeitslot(Zeitslot zeitslotNeu) throws Exception {
		Connection con = DBVerbindung.connection();

		try {
			Statement stmt = con.createStatement();
			String sql = "Select * From zeitslot Where Wochentag=\""
					+ zeitslotNeu.getWochentag() + "\"" + " and Anfangszeit="
					+ zeitslotNeu.getAnfangszeit() + " and Endzeit="
					+ zeitslotNeu.getEndzeit();

			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				// Wenn es ein ergebnis gab die Id füllen.
				zeitslotNeu.setId(rs.getInt("ZeitNr"));
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		}

		return zeitslotNeu;
	}

}
