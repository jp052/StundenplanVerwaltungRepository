package de.hdm.gruppe3.stundenplanverwaltung.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.*;

//Import Impl Klasse Dozent
//Import bo Dozent


public class ZeitslotMapper {
	/**
	   * Die Klasse ZeitslotMapper wird nur einzeitslotl instantiiert. Man spricht hierbei
	   * von einem sogenannten <b>Singleton</b>.
	   * <p>
	   * Diese Variable ist durch den Bezeichner <code>static</code> nur einzeitslotl für
	   * sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	   * einzige Instanz dieser Klasse.
	   * 
	   * @see zeitslotMapper()
	   */
	  private static ZeitslotMapper zeitslotMapper = null;

	  /**
	   * Geschützter Konstruktor - verhindert die Möglichkeit, mit <code>new</code>
	   * neue Instanzen dieser Klasse zu erzeugen.
	   */
	  protected ZeitslotMapper() {
	  }

	  /**
	   * Diese statische Methode kann aufgrufen werden durch
	   * <code>ZeitslotMapper.zeitslotMapper()</code>. Sie stellt die
	   * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine einzige
	   * Instanz von <code>ZeitslotMapper</code> existiert.
	   * <p>
	   * 
	   * <b>Fazit:</b> ZeitslotMapper sollte nicht mittels <code>new</code>
	   * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
	   * 
	   * @return DAS <code>ZeitslotMapper</code>-Objekt.
	   * @see zeitslotMapper
	   */
	  public static ZeitslotMapper zeitslotMapper() {
	    if (zeitslotMapper == null) {
	      zeitslotMapper = new ZeitslotMapper();
	    }

	    return zeitslotMapper;
	  }
	  public Zeitslot anlegen(Zeitslot m){
			 Connection con = DBVerbindung.connection();

			    try {
			      Statement stmt = con.createStatement();

//			      /*
//			       * Zunächst schauen wir nach, welches der momentan höchste
//			       * Primärschlüsselwert ist.
//			       */
//			      ResultSet rs = stmt.executeQuery("SELECT MAX(zeitslot) AS zeitslotxzeitslot "
//			          + "FROM zeitslot ");
	//
//			      // Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
//			      if (rs.next()) {
//			        /*
//			         * a erhält den bisher zeitslotxzeitslotozentlen, nun um 1 inkrementierten
//			         * Primärschlüssel.
//			         */
//			        m.setID(rs.getInt("zeitslotxzeitslot") + 1);
	//
//			        stmt = con.createStatement();

			        // Jetzt erst erfolgt die tatsächliche Einfügeoperation
			        stmt.executeUpdate("INSERT INTO zeitslot (ZeitNr, Wochentag, Anfangszeit) " + "VALUES ( "
			        	+ "NULL,'" + m.getWochentag()  +"','" +m.getAnfangszeit()+ "')");
			      //}
			    }
			    catch (SQLException e2) {
			      e2.printStackTrace();
			    } 

			    /*
			     * Rückgabe, des evtl. korrigierten Accounts.
			     * 
			     * HINWEIS: Da in Java nur Referenzen auf Objekte und keine physischen
			     * Objekte übergeben werden, wäre die Anpassung des Zeitslot-Objekts auch
			     * ohne diese explizite Rückgabe au�erhalb dieser Methode sichtbar. Die
			     * explizite Rückgabe von a ist eher ein Stilmittel, um zu signalisieren,
			     * dass sich das Objekt evtl. im Laufe der Methode verändert hat.
			     */
			    return m;
			
		}
		
		public Zeitslot modifizieren(Zeitslot zeitslot){
		    Connection con = DBVerbindung.connection();

		    try {
		      Statement stmt = con.createStatement();

		      stmt.executeUpdate("UPDATE zeitslot " + "SET Wochentag=\"" + zeitslot.getWochentag() +  "\" "+ "SET Anfangszeit=\"" + zeitslot.getAnfangszeit() + "WHERE ZeitNr=" + zeitslot.getId());

		    }
		    catch (SQLException e2) {
		      e2.printStackTrace();
		    } 

		    // Um Analogie zu insert(Zeitslot a) zu wahren, geben wir a zurück
		    return zeitslot;
		}
		
		public Zeitslot loeschen(Zeitslot zeitslot){
		    Connection con = DBVerbindung.connection();

		    try {
		      Statement stmt = con.createStatement();

		      stmt.executeUpdate("DELETE FROM zeitslot " + "WHERE ZeitNr=" + zeitslot.getId());

		    }
		    catch (SQLException e2) {
		      e2.printStackTrace();
		    }
			return zeitslot; 
		}
		
		public Zeitslot findeId(int i){
		    // DB-Verbindung holen
		    Connection con = DBVerbindung.connection();

		    try {
		      // Leeres SQL-Statement (JDBC) anlegen
		      Statement stmt = con.createStatement();

		      // Statement ausfüllen und als Query an die DB schicken
		      ResultSet rs = stmt.executeQuery("SELECT ID, Name, Vorname, Anschrift, PLZ, Ort FROM zeitslot "
		          + "WHERE ID=" + i + " ORDER BY Name");

		      /*
		       * Da zeitslot Primärschlüssel ist, kann zeitslotx. nur ein Tupel zurückgegeben
		       * werden. Prüfe, ob ein Ergebnis vorliegt.
		       */
		      if (rs.next()) {
		        // Ergebnis-Tupel in Objekt umwandeln
		    	Zeitslot zeitslot = new Zeitslot();
		        zeitslot.setId(rs.getInt("ID"));
		        zeitslot.setWochentag(rs.getString("Wochentag"));
				//zeitslot.setEndzeit(rs.getString("Endzeit"));   To-Do
				//zeitslot.setAnfangszeit(rs.getString("Anfangszeit")); To-Do
		        return zeitslot;
		      }
		    }
		    catch (SQLException e2) {
		      e2.printStackTrace();
		      return null;
		    } 
		    return null;
		}
		
		public Vector<Zeitslot> findeAlle(){
			 Connection con = DBVerbindung.connection();

			    // Ergebnisvektor vorbereiten
			    Vector<Zeitslot> result = new Vector<Zeitslot>();

			    try {
			      Statement stmt = con.createStatement();

			      ResultSet rs = stmt.executeQuery("SELECT ZeitNr, Wochentag, Endzeit, Anfangszeit name FROM Zeitslot "
			          + " ORDER BY ZeitNr");

			      // F�r jeden Eintrag im Suchergebnis wird nun ein Account-Objekt erstellt.
			      while (rs.next()) {
			    	Zeitslot z = new Zeitslot();
//			        r.setId(rs.getInt("id"));
//			        r.setOwnerID(rs.getInt("owner"));

			        // Hinzuf�gen des neuen Objekts zum Ergebnisvektor
			        result.addElement(z);
			      }
			    }
			    catch (SQLException e2) {
			      e2.printStackTrace();
			    }

			 // Ergebnisvektor zur�ckgeben
			 return result;
		}
		
		
}
