package de.hdm.gruppe3.stundenplanverwaltung.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.*;

//Import Impl Klasse Dozent
//Import bo Dozent

public class LehrveranstaltungMapper {
	/**
	   * Die Klasse LehrveranstaltungMapper wird nur einlvl instantiiert. Man spricht hierbei
	   * von einem sogenannten <b>Singleton</b>.
	   * <p>
	   * Diese Variable ist durch den Bezeichner <code>static</code> nur einlvl für
	   * sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	   * einzige Instanz dieser Klasse.
	   * 
	   * @see lvMapper()
	   */
	  private static LehrveranstaltungMapper lvMapper = null;

	  /**
	   * Geschützter Konstruktor - verhindert die Möglichkeit, mit <code>new</code>
	   * neue Instanzen dieser Klasse zu erzeugen.
	   */
	  protected LehrveranstaltungMapper() {
	  }

	  /**
	   * Diese statische Methode kann aufgrufen werden durch
	   * <code>LehrveranstaltungMapper.lvMapper()</code>. Sie stellt die
	   * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine einzige
	   * Instanz von <code>LehrveranstaltungMapper</code> existiert.
	   * <p>
	   * 
	   * <b>Fazit:</b> LehrveranstaltungMapper sollte nicht mittels <code>new</code>
	   * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
	   * 
	   * @return DAS <code>LehrveranstaltungMapper</code>-Objekt.
	   * @see lvMapper
	   */
	  public static LehrveranstaltungMapper lvMapper() {
	    if (lvMapper == null) {
	      lvMapper = new LehrveranstaltungMapper();
	    }

	    return lvMapper;
	  }
	  public Lehrveranstaltung anlegen(Lehrveranstaltung m ){
			 Connection con = DBVerbindung.connection();

			    try {
			      Statement stmt = con.createStatement();

//			      /*
//			       * Zunächst schauen wir nach, welches der momentan höchste
//			       * Primärschlüsselwert ist.
//			       */
//			      ResultSet rs = stmt.executeQuery("SELECT MAX(lv) AS lvxlv "
//			          + "FROM lv ");
	//
//			      // Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
//			      if (rs.next()) {
//			        /*
//			         * a erhält den bisher lvxlvozentlen, nun um 1 inkrementierten
//			         * Primärschlüssel.
//			         */
//			        m.setID(rs.getInt("lvxlv") + 1);
	//
//			        stmt = con.createStatement();

			        // Jetzt erst erfolgt die tatsächliche Einfügeoperation
			        stmt.executeUpdate("INSERT INTO Lehrveranstaltung (EDVNr, Bezeichnung, Umfang, Semester) " + "VALUES ( "
			        	+ "NULL,'" + m.getBezeichnung() + "','" + m.getUmfang() +"','" +m.getSemester()+"')");
			      //}
			    }
			    catch (SQLException e2) {
			      e2.printStackTrace();
			    } 

			    /*
			     * Rückgabe, des evtl. korrigierten Accounts.
			     * 
			     * HINWEIS: Da in Java nur Referenzen auf Objekte und keine physischen
			     * Objekte übergeben werden, wäre die Anpassung des Lehrveranstaltung-Objekts auch
			     * ohne diese explizite Rückgabe au�erhalb dieser Methode sichtbar. Die
			     * explizite Rückgabe von a ist eher ein Stilmittel, um zu signalisieren,
			     * dass sich das Objekt evtl. im Laufe der Methode verändert hat.
			     */
			    return m;
			
		}
		
		public Lehrveranstaltung modifizieren(Lehrveranstaltung lv){
		    Connection con = DBVerbindung.connection();

		    try {
		      Statement stmt = con.createStatement();

		      stmt.executeUpdate("UPDATE Lehrveranstaltung " + "SET Bezeichnung=\"" + lv.getBezeichnung() + "\" SET Umfang=\"" + lv.getUmfang() + "\" "+ "SET Semester=\"" + lv.getSemester() + "WHERE LVNr=" + lv.getId());

		    }
		    catch (SQLException e2) {
		      e2.printStackTrace();
		    } 

		    // Um Analogie zu insert(Lehrveranstaltung a) zu wahren, geben wir a zurück
		    return lv;
		}
		
		public Lehrveranstaltung loeschen(Lehrveranstaltung lv){
		    Connection con = DBVerbindung.connection();

		    try {
		      Statement stmt = con.createStatement();

		      stmt.executeUpdate("DELETE FROM Lehrveranstaltung " + "WHERE LVNr=" + lv.getId());

		    }
		    catch (SQLException e2) {
		      e2.printStackTrace();
		    }
			return lv; 
		}
		
		public Lehrveranstaltung findeName(String bez){
		    // DB-Verbindung holen
		    Connection con = DBVerbindung.connection();

		    try {
		      // Leeres SQL-Statement (JDBC) anlegen
		      Statement stmt = con.createStatement();

		      // Statement ausfüllen und als Query an die DB schicken
		      ResultSet rs = stmt.executeQuery("SELECT LVNr, Bezeichnung, Umfang, Semester FROM Lehrveranstaltung "
		          + "WHERE Bezeichnung=" + bez + " ORDER BY bezeichnung");

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
		    }
		    catch (SQLException e2) {
		      e2.printStackTrace();
		      return null;
		    } 

		    return null;
		}
	  

		public Lehrveranstaltung findeId(int i){
		    // DB-Verbindung holen
		    Connection con = DBVerbindung.connection();

		    try {
		      // Leeres SQL-Statement (JDBC) anlegen
		      Statement stmt = con.createStatement();

		      // Statement ausfüllen und als Query an die DB schicken
		      ResultSet rs = stmt.executeQuery("SELECT LVNr, Bezeichnung, Umfang, Semester FROM Lehrveranstaltung "
		          + "WHERE LVNr=" + i + " ORDER BY Bezeichnung");

		      /*
		       * Da lv Primärschlüssel ist, kann lvx. nur ein Tupel zurückgegeben
		       * werden. Prüfe, ob ein Ergebnis vorliegt.
		       */
		      if (rs.next()) {
		        // Ergebnis-Tupel in Objekt umwandeln
		    	Lehrveranstaltung lv = new Lehrveranstaltung();
		        lv.setId(rs.getInt("LVNr"));
		        lv.setBezeichnung(rs.getString("Bezeichnung"));
		        lv.setUmfang(rs.getDouble("Umfang"));
		        lv.setSemester(rs.getInt("Semester"));
		        
		        return lv;
		      }
		    }
		    catch (SQLException e2) {
		      e2.printStackTrace();
		      return null;
		    } 
		    return null;
		}
		
		
				public Vector<Lehrveranstaltung> findeAlle(){
			 Connection con = DBVerbindung.connection();

			    // Ergebnisvektor vorbereiten
			    Vector<Lehrveranstaltung> result = new Vector<Lehrveranstaltung>();

			    try {
			      Statement stmt = con.createStatement();

			      ResultSet rs = stmt.executeQuery("SELECT LVNr, Bezeichnung, Umfang, Semester FROM Lehrveranstaltung "
			          + " ORDER BY LVNr");

			      // F¸r jeden Eintrag im Suchergebnis wird nun ein Account-Objekt erstellt.
			      while (rs.next()) {
			        Lehrveranstaltung d = new Lehrveranstaltung();
			        d.setId(rs.getInt("LVNr"));
			        d.setBezeichnung(rs.getString("Bezeichnung"));
					d.setUmfang(rs.getInt("Umfang"));
					d.setSemester(rs.getInt("Semester"));

			        // Hinzuf¸gen des neuen Objekts zum Ergebnisvektor
			        result.addElement(d);
			      }
			    }
			    catch (SQLException e2) {
			      e2.printStackTrace();
			    }

			 // Ergebnisvektor zur¸ckgeben
			 return result;
		}
		
		public Dozent findeDozent(Lehrveranstaltung lv) {
		    /*
		     * Wir bedienen uns hier einfach des CustomerMapper. Diesem geben wir
		     * einfach den in dem Account-Objekt enthaltenen Fremdschl¸ssel f¸r den
		     * Kontoinhaber. Der CustomerMapper l‰sst uns dann diese ID in ein Objekt
		     * auf.
		     */
		    return DozentMapper.dozentMapper().findeId(lv.getId());
		  }
		  
		  public Zeitslot findeTermin(Lehrveranstaltung lv) {
		    /*
		     * Wir bedienen uns hier einfach des CustomerMapper. Diesem geben wir
		     * einfach den in dem Account-Objekt enthaltenen Fremdschl¸ssel f¸r den
		     * Kontoinhaber. Der CustomerMapper l‰sst uns dann diese ID in ein Objekt
		     * auf.
		     */
		    return ZeitslotMapper.zeitslotMapper().findeId(lv.getId());
		  }
		  
		  public Raum findeRaum(Lehrveranstaltung lv) {
		    /*
		     * Wir bedienen uns hier einfach des CustomerMapper. Diesem geben wir
		     * einfach den in dem Account-Objekt enthaltenen Fremdschl¸ssel f¸r den
		     * Kontoinhaber. Der CustomerMapper l‰sst uns dann diese ID in ein Objekt
		     * auf.
		     */
		    return RaumMapper.raumMapper().findeId(lv.getId());
		  }
}
