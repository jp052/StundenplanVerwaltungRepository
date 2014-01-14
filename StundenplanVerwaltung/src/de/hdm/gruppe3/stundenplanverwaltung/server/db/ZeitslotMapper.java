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


			        stmt.executeUpdate("INSERT INTO zeitslot (ZeitNr, Wochentag, Anfangszeit) " + "VALUES ( "
			        	+ "NULL,'" + m.getWochentag()  +"','" +m.getAnfangszeit()+ "')");
			      
			    }
			    catch (SQLException e2) {
			      e2.printStackTrace();
			    } 

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
		
		public Zeitslot findeId(int zeitId){

		    Connection con = DBVerbindung.connection();
		    Zeitslot zeitslot = new Zeitslot();

		    try {

		      Statement stmt = con.createStatement();

		      String sql = "SELECT *FROM Zeitslot WHERE ZeitNr=" + zeitId;
		      
		      ResultSet rs = stmt.executeQuery(sql);

		     
		      
		      if (rs.next()) {
		    	
		        zeitslot.setId(rs.getInt("ZeitNr"));
		        zeitslot.setWochentag(rs.getString("Wochentag"));
				zeitslot.setEndzeit(rs.getInt("Endzeit"));
				zeitslot.setAnfangszeit(rs.getInt("Anfangszeit"));
		        
		      }
		    }
		    catch (SQLException e2) {
		      e2.printStackTrace();
		    } 
		    return zeitslot;
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
