package de.hdm.gruppe3.stundenplanverwaltung.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.*;

//Import Impl Klasse Dozent
//Import bo Dozent

public class SemesterverbandMapper {
	/**
	   * Die Klasse SemesterverbandMapper wird nur einsvl instantiiert. Man spricht hierbei
	   * von einem sogenannten <b>Singleton</b>.
	   * <p>
	   * Diese Variable ist durch den Bezeichner <code>static</code> nur einsvl für
	   * sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	   * einzige Instanz dieser Klasse.
	   * 
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
	   * Diese statische Methode kann aufgrufen werden durch
	   * <code>SemesterverbandMapper.svMapper()</code>. Sie stellt die
	   * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine einzige
	   * Instanz von <code>SemesterverbandMapper</code> existiert.
	   * <p>
	   * 
	   * <b>Fazit:</b> SemesterverbandMapper sollte nicht mittels <code>new</code>
	   * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
	   * 
	   * @return DAS <code>SemesterverbandMapper</code>-Objekt.
	   * @see svMapper
	   */
	  public static SemesterverbandMapper svMapper() {
	    if (svMapper == null) {
	      svMapper = new SemesterverbandMapper();
	    }

	    return svMapper;
	  }
	  public Semesterverband anlegen(Semesterverband sv ){
			 Connection con = DBVerbindung.connection();

			    try {
			      Statement stmt = con.createStatement();

//			      /*
//			       * Zunächst schauen wir nach, welches der momentan höchste
//			       * Primärschlüsselwert ist.
//			       */
//			      ResultSet rs = stmt.executeQuery("SELECT MAX(sv) AS svxsv "
//			          + "FROM sv ");
	//
//			      // Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
//			      if (rs.next()) {
//			        /*
//			         * a erhält den bisher svxsvozentlen, nun um 1 inkrementierten
//			         * Primärschlüssel.
//			         */
//			        m.setId(rs.getInt("svxsv") + 1);
	//
//			        stmt = con.createStatement();

			        // Jetzt erst erfolgt die tatsächliche Einfügeoperation
			        stmt.executeUpdate("INSERT INTO Semesterverband (SVNr, AnzahlStudierende, SemesterHalbjahr, Jahrgang) " + "VALUES ( "
			        	+ "NULL,'" + sv.getAnzahlStudenten() + "','" + sv.getSemester() +"','" +sv.getJahrgang()+ "')");
			      //}
			    }
			    catch (SQLException e2) {
			      e2.printStackTrace();
			    } 

			    /*
			     * Rückgabe, des evtl. korrigierten Accounts.
			     * 
			     * HINWEIS: Da in Java nur Referenzen auf Objekte und keine physischen
			     * Objekte übergeben werden, wäre die Anpassung des Semesterverband-Objekts auch
			     * ohne diese explizite Rückgabe au�erhalb dieser Methode sichtbar. Die
			     * explizite Rückgabe von a ist eher ein Stilmittel, um zu signalisieren,
			     * dass sich das Objekt evtl. im Laufe der Methode verändert hat.
			     */
			    return sv;
			
		}
		
		public Semesterverband modifizieren(Semesterverband sv){
		    Connection con = DBVerbindung.connection();

		    try {
		      Statement stmt = con.createStatement();

		      stmt.executeUpdate("UPDATE Semesterverband " + "SET AnzahlStudierende=" + sv.getAnzahlStudenten() + " , SemesterHalbjahr=" + sv.getSemester() + ", Jahrgang=" + sv.getJahrgang() + " WHERE SVNr=" + sv.getId());

		    }
		    catch (SQLException e2) {
		      e2.printStackTrace();
		    } 

		    // Um Analogie zu insert(Semesterverband a) zu wahren, geben wir a zurück
		    return sv;
		}
		
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

		public Semesterverband findeId(int i){
		    // DB-Verbindung holen
		    Connection con = DBVerbindung.connection();

		    try {
		      // Leeres SQL-Statement (JDBC) anlegen
		      Statement stmt = con.createStatement();

		      // Statement ausfüllen und als Query an die DB schicken
		      ResultSet rs = stmt.executeQuery("SELECT SVNr, AnzahlStudierende, SemesterHalbjahr, Jahrgang FROM Semesterverband "
		          + "WHERE SVNr=" + i + " ORDER BY SVNr");

		      /*
		       * Da sv Primärschlüssel ist, kann svx. nur ein Tupel zurückgegeben
		       * werden. Prüfe, ob ein Ergebnis vorliegt.
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
		    }
		    catch (SQLException e2) {
		      e2.printStackTrace();
		      return null;
		    } 
		    return null;
		}
		
		
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
	
		public Lehrveranstaltung findeVL(Semesterverband sv) {
		    /*
		     * Wir bedienen uns hier einfach des CustomerMapper. Diesem geben wir
		     * einfach den in dem Account-Objekt enthaltenen Fremdschl¸ssel f¸r den
		     * Kontoinhaber. Der CustomerMapper l‰sst uns dann diese ID in ein Objekt
		     * auf.
		     */
		    return LehrveranstaltungMapper.lvMapper().findeId(sv.getId());
		  }
}
