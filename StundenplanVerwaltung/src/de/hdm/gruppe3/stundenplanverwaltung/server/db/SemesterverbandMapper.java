package de.hdm.gruppe3.stundenplanverwaltung.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.*;

/**
 * @author Selim Karazehir, Julia Hammerer
 *
 */
public class SemesterverbandMapper {
	/**
	   * damit sie in der statischen Methode svMapper() benützt werden kann muss es auch hier <code>static</code> sein
	   * @see svMapper()
	   */
	  private static SemesterverbandMapper svMapper = null;

	  /**
	   * Geschützter Konstruktor - verhindert die Möglichkeit, mit <code>new</code>
	   * neue Instanzen dieser Klasse zu erzeugen.
	   */
	  protected SemesterverbandMapper() {
	  }

	  /**
	   * die svMapper() kann man mit der Klasse aufrufen. So kann man sicher gehen, dass nur ein
	   * Objekt instanziiert wird.
	   * @see svMapper
	   */
	  public static SemesterverbandMapper svMapper() {
	    if (svMapper == null) {
	      svMapper = new SemesterverbandMapper();
	    }

	    return svMapper;
	  }
	  
	  /**
	   * Methode um ein Semesterverband in die Datenbank anzulegen
	   * @param sv
	   * @return
	   */
	  public Semesterverband anlegen(Semesterverband sv ){
			 Connection con = DBVerbindung.connection();

			    try {
			      Statement stmt = con.createStatement();

			        // Hier findet die SQL Statement statt
			        stmt.executeUpdate("INSERT INTO Semesterverband (SVNr, AnzahlStudierende, SemesterHalbjahr, Jahrgang) " + "VALUES ( "
			        	+ "NULL,'" + sv.getAnzahlStudenten() + "','" + sv.getSemester() +"','" +sv.getJahrgang()+ "')");
			      //}
			    }
			    catch (SQLException e2) {
			      e2.printStackTrace();
			    } 


			    return sv;
			
		}
	  
		/**
		 * Methode, mit dem man ein Datensatz verändern kann
		 * @param sv
		 * @return
		 */
		public Semesterverband modifizieren(Semesterverband sv){
		    Connection con = DBVerbindung.connection();

		    try {
		      Statement stmt = con.createStatement();

		      stmt.executeUpdate("UPDATE Semesterverband " + "SET AnzahlStudierende=" + sv.getAnzahlStudenten() + " , SemesterHalbjahr=" + sv.getSemester() + ", Jahrgang=" + sv.getJahrgang() + " WHERE SVNr=" + sv.getId());

		    }
		    catch (SQLException e2) {
		      e2.printStackTrace();
		    } 

		    return sv;
		}
		
		/**
		 * Methode um einen Datensatz aus der Datenbank zu löschen
		 * @param sv
		 * @return
		 */
		
		public Semesterverband loeschen(Semesterverband sv){
		    Connection con = DBVerbindung.connection();

		    try {
		      Statement stmt = con.createStatement();

		      stmt.executeUpdate("DELETE FROM Semesterverband " + "WHERE SVNr=" + sv.getId());

		    }
		    catch (SQLException e2) {
		      e2.printStackTrace();
		    }
			return sv; 
		}	  

		/**
		 * Methode, mit dem man mittels der id den Semesterverband findet.
		 * @param i
		 * @return
		 */
		public Semesterverband findeId(int i){
		    // DB-Verbindung holen
		    Connection con = DBVerbindung.connection();

		    try {
		      // Leeres SQL-Statement (JDBC) anlegen
		      Statement stmt = con.createStatement();

		      // Statement ausfüllen und als Query an die DB schicken
		      ResultSet rs = stmt.executeQuery("SELECT SVNr, AnzahlStudierende, SemesterHalbjahr, Jahrgang FROM Semesterverband "
		          + "WHERE SVNr=" + i + " ORDER BY SVNr");

		      if (rs.next()) {
		        // Ergebnis-Tupel in Objekt umwandeln
		    	Semesterverband sv = new Semesterverband();
		        sv.setId(rs.getInt("SVNr"));
		        sv.setAnzahlStudenten(rs.getInt("AnzahlStudierende"));
				sv.setSemester(rs.getInt("SemesterHalbjahr"));
				sv.setJahrgang(rs.getInt("Jahrgang"));

		        return sv;
		      }
		    }
		    catch (SQLException e2) {
		      e2.printStackTrace();
		      return null;
		    } 
		    return null;
		}
		
		/**
		 * Mit dieser Methode kann man mit dem svHalbjahr den Semester aus der Datenbank finden.
		 * @param svHalbjahr
		 * @return
		 */
		public Semesterverband findeSVHalbjahr(int svHalbjahr) {
			// DB-Verbindung holen
			Connection con = DBVerbindung.connection();

			try {
				// Leeres SQL-Statement (JDBC) anlegen
				Statement stmt = con.createStatement();

				// Statement ausfüllen und als Query an die DB schicken
				ResultSet rs = stmt
						.executeQuery("SELECT * FROM raum "
								+ "WHERE semesterHalbjahr="
								+ "'"+svHalbjahr+"'");

				/*
				 * Da raum Primärschlüssel ist, kann raumx. nur ein Tupel
				 * zurückgegeben werden. Prüfe, ob ein Ergebnis vorliegt.
				 */
			if (rs.next()) {
			    // Ergebnis-Tupel in Objekt umwandeln
			   	Semesterverband sv = new Semesterverband();
			    sv.setId(rs.getInt("SVNr"));
			    sv.setAnzahlStudenten(rs.getInt("AnzahlStudierende"));
			    sv.setSemester(rs.getInt("SemesterHalbjahr"));
				sv.setJahrgang(rs.getInt("Jahrgang"));

				        return sv;
				      }
			} catch (SQLException e2) {
				e2.printStackTrace();
				return null;
			}

			return null;
		}
		
		/**
		 * Alle Datensätze aus der Tabelle Semesterverband werden herausgelesen und in ein Objekt gespeichert.
		 * @return
		 */
		public Vector<Semesterverband> findeAlle(){
			 Connection con = DBVerbindung.connection();

			    //Vector mit angeforderten Objekten
			    Vector<Semesterverband> result = new Vector<Semesterverband>();

			    try {
			      Statement stmt = con.createStatement();
			      
			      String sql = "SELECT * FROM Semesterverband  ORDER BY SVNr";

			      ResultSet rs = stmt.executeQuery(sql);

			      
			      while (rs.next()) {
			        Semesterverband sv = new Semesterverband();
					sv.setId(rs.getInt("SVNr"));
					sv.setAnzahlStudenten(rs.getInt("AnzahlStudierende"));
					sv.setSemester(rs.getInt("SemesterHalbjahr"));
					sv.setJahrgang(rs.getInt("Jahrgang"));

			        
			        result.addElement(sv);
			      }
			    }
			    catch (SQLException e2) {
			      e2.printStackTrace();
			    }

		
			 return result;
		}
			      
//			      String sql = "SELECT FROM Semesterverband LEFT JOIN Lehrveranstaltung ON semesterHalbjahr =4";
	
	
		/**
		 * Mit dieser Methode kann man die Vorlesung mit dem Semesterverband sv finden
		 * @param sv
		 * @return
		 */
		public Lehrveranstaltung findeVL(Semesterverband sv) {

		    return LehrveranstaltungMapper.lvMapper().findeId(sv.getId());
		  }
}
