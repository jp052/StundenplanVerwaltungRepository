package de.hdm.gruppe3.stundenplanverwaltung.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import de.hdm.gruppe3.stundenplanverwaltung.shared.ConstantsStdpln;
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
		Statement stmt = null;
		ResultSet rs = null;

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
				stmt = con.createStatement();
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

				rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					// Holt den generierte Primärschlüssel der die Id es
					// Zeitslots sind.
					zeitslot.setId(rs.getInt(1));
				}

			} catch (SQLException e2) {
				e2.printStackTrace();
				throw new Exception("Datenbank fehler!" + e2.toString());
			} finally {
				//Alles schließen, finally wird immer ausgeführt, egal ob es einen Fehler gibt oder nicht.
				DBVerbindung.closeAll(rs, stmt, con);
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
		Statement stmt = null;

		try {
			stmt = con.createStatement();

			stmt.executeUpdate("UPDATE zeitslot " + "SET Wochentag=\""
					+ zeitslot.getWochentag() + "\" " + "SET Anfangszeit=\""
					+ zeitslot.getAnfangszeit() + "WHERE ZeitNr="
					+ zeitslot.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		} finally {
			//Alles schließen, finally wird immer ausgeführt, egal ob es einen Fehler gibt oder nicht.
			DBVerbindung.closeAll(null, stmt, con);
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
		Statement stmt = null;

		try {
			stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM zeitslot " + "WHERE ZeitNr="
					+ zeitslot.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		} finally {
			//Alles schließen, finally wird immer ausgeführt, egal ob es einen Fehler gibt oder nicht.
			DBVerbindung.closeAll(null, stmt, con);
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
		 Statement stmt = null;
		 ResultSet rs = null;
		
		Zeitslot zeitslot = new Zeitslot();

		try {

			stmt = con.createStatement();

			String sql = "SELECT *FROM zeitslot WHERE ZeitNr=" + zeitId;

			rs = stmt.executeQuery(sql);

			if (rs.next()) {

				zeitslot.setId(rs.getInt("ZeitNr"));
				zeitslot.setWochentag(rs.getString("Wochentag"));
				zeitslot.setEndzeit(rs.getInt("Endzeit"));
				zeitslot.setAnfangszeit(rs.getInt("Anfangszeit"));

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		} finally {
			//Alles schließen, finally wird immer ausgeführt, egal ob es einen Fehler gibt oder nicht.
			DBVerbindung.closeAll(rs, stmt, con);
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
		Statement stmt = null;
		ResultSet rs = null;

		Vector<Zeitslot> result = new Vector<Zeitslot>();

		try {
			stmt = con.createStatement();

			rs = stmt
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
		} finally {
			//Alles schließen, finally wird immer ausgeführt, egal ob es einen Fehler gibt oder nicht.
			DBVerbindung.closeAll(rs, stmt, con);
		}

		return result;
	}

	/**
	 * Prüft ob ein bestimmter Zeitslot angelegt werden darf. Es wird Ein
	 * Zeitslot Array erstellt der für jede Belegte Stunde true als inhalt
	 * erhält Dieser Array wird dann mit der Anzulegenden Zeit überprüft und es
	 * wird false zurückgegeben.
	 * 
	 * @param zeitslot
	 *            , raumId
	 * @return true wenn der Zeitraum in dem Raum frei ist, false wenn der Raum
	 *         nicht frei ist
	 * @throws Exception
	 */
	public boolean checkVerfuegbarkeit(Zeitslot zeitslot, int raumId)
			throws Exception {
		Connection con = DBVerbindung.connection();
		Statement stmt = null;
		ResultSet rs = null;

		// Verfügbarkeit standardmäßig auf true setzen.
		boolean isVerfuegbar = true;

		// Der leere zeitslots Array mit 11 Zeitslots.
		ArrayList<Boolean> zeitslots = new ArrayList<Boolean>();

		// Zeitslots füllen und alles auf false für frei setzen.
		// Hier 11 Zeitslots da zwischen 8 und 18 uhr 11 Zeitslots liegen.
		for (int counter = ConstantsStdpln.ERSTE_STUNDE; counter < ConstantsStdpln.LETZTE_STUNDE; counter++) {
			zeitslots.add(false);
		}

		try {
			stmt = con.createStatement();

			// Join über 3 Tabellen, Zeitslot, Durchfuehrung und Raum
			String sql = "Select * From zeitslot "
					+ "Inner Join (durchfuehrung Inner Join raum on durchfuehrung.RaumNr=raum.RaumNr) on zeitslot.ZeitNr=durchfuehrung.ZeitNr "
					+ "Where raum.raumNr=" + raumId + " and Wochentag=\""
					+ zeitslot.getWochentag() + "\"";

			rs = stmt.executeQuery(sql);

			// Die belegten Slots füllen
			while (rs.next()) {
				int belegteAnfangszeit = rs.getInt("zeitslot.Anfangszeit");
				int belegteEndzeit = rs.getInt("zeitslot.Endzeit");

				// Die Dauer muss bekannt sein um zu wissen wie viele Zeitslots
				// belegt werden müssen
				int belegtDauer = belegteEndzeit - belegteAnfangszeit;
				// arrayPos ist die Position an der angefangen werden muss die
				// Belegung einzutragen z.B
				// wenn Uhrzeit von 10-12 ist muss an der 3. Stelle im array,
				// also bei index 2 angefangen werden zu prüfen.
				int arrayPos = belegteAnfangszeit
						- ConstantsStdpln.ERSTE_STUNDE;

				for (int dauerCounter = 1; dauerCounter <= belegtDauer; dauerCounter++) {
					zeitslots.add(arrayPos, true);
					arrayPos++;
				}


			}

			int neueAnfangszeit = zeitslot.getAnfangszeit();
			int neueEndzeit = zeitslot.getEndzeit();
			int neuedauer = neueEndzeit - neueAnfangszeit;

			// Das ist die Position an der angefangen werden muss zu Prüfen z.B
			// wenn Uhrzeit von 10-12 ist muss an der
			// 3. Stelle im array, also bei index 2 angefangen werden zu prüfen.
			int arrayPos = neueAnfangszeit - ConstantsStdpln.ERSTE_STUNDE;

			for (int dauerCounter = 1; dauerCounter <= neuedauer; dauerCounter++) {
				boolean isBelegt = zeitslots.get(arrayPos);
				if (isBelegt) {
					isVerfuegbar = false; // Der Raum ist belegt und setz isVerfuegbr auf false
				}
				arrayPos++;
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
			isVerfuegbar = false;
			throw new RaumBelegtException();

		} finally {
			// Alles schließen, finally wird immer ausgeführt, egal ob es einen
			// Fehler gibt oder nicht.
			DBVerbindung.closeAll(rs, stmt, con);
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
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = con.createStatement();
			String sql = "Select * From zeitslot Where Wochentag=\""
					+ zeitslotNeu.getWochentag() + "\"" + " and Anfangszeit="
					+ zeitslotNeu.getAnfangszeit() + " and Endzeit="
					+ zeitslotNeu.getEndzeit();

			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				// Wenn es ein ergebnis gab die Id füllen.
				zeitslotNeu.setId(rs.getInt("ZeitNr"));
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		} finally {
			//Alles schließen, finally wird immer ausgeführt, egal ob es einen Fehler gibt oder nicht.
			DBVerbindung.closeAll(rs, stmt, con);
		}

		return zeitslotNeu;
	}

}
