package de.hdm.gruppe3.stundenplanverwaltung.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import de.hdm.gruppe3.stundenplanverwaltung.shared.RaumBelegtException;
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
	  
	  /**
	   * Schaut ob ein Zeitslot mit den identischen Attributen schon existiert. Falls ja wird nur dessen Id in den den aktuellen zeitslot gefüllt,
	   * falls nicht muss der Zeitslot neu angelegt werden.
	   * 
	   * @param zeitslot
	   * @return
	   */
	  public Zeitslot anlegen(Zeitslot zeitslot){
			Connection con = DBVerbindung.connection();			
			
			//Fall Zeitslot mit selber Anfangszeit, Endzeit und Wochentag existiert muss in der Durchführung nur noch auf dem
			//Primäschlüssen verwiesen werden und kein neuer Zeitslot muss angelegt werden. 
			//Ein Zeitslot kann einmal in jedem Raum verwendet werden.
			zeitslot = this.insertIdIntoZeitslot(zeitslot);
			
			//Wenn checkExistenz kein Id in den Zeitslot gefüllt hat muss dieser angelegt werden
			if(zeitslot.getId() == 0) {
				 try {
				      Statement stmt = con.createStatement();
				      String sql = "INSERT INTO zeitslot (ZeitNr, Wochentag, Anfangszeit, Endzeit) " + "VALUES ( "
					        	+ "NULL, \"" + zeitslot.getWochentag()  +"\"," + zeitslot.getAnfangszeit() + "," + zeitslot.getEndzeit() + ")";

				        stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
				        stmt.getGeneratedKeys();
				        
				        ResultSet rs = stmt.getGeneratedKeys();
				        if ( rs.next() ) {
				        	//Holt den generierte Primärschlüssel der die Id es Zeitslots sind.
				        	zeitslot.setId(rs.getInt(1));
				        }
				      
				    }
				    catch (SQLException e2) {
				      e2.printStackTrace();
				    } 
				
			}		
		    return zeitslot;		
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

			    Vector<Zeitslot> result = new Vector<Zeitslot>();

			    try {
			      Statement stmt = con.createStatement();

			      ResultSet rs = stmt.executeQuery("SELECT ZeitNr, Wochentag, Endzeit, Anfangszeit name FROM Zeitslot "
			          + " ORDER BY ZeitNr");

			      while (rs.next()) {
			    	Zeitslot z = new Zeitslot();
//			        r.setId(rs.getInt("id"));
//			        r.setOwnerID(rs.getInt("owner"));


			        result.addElement(z);
			      }
			    }
			    catch (SQLException e2) {
			      e2.printStackTrace();
			    }


			 return result;
		}
		
		/**
		 * Pfüft ob ein bestimmter Zeitslot angelegt werden darf.
		 * Es wird eine Abfrage an die Datenbank geschickt, leifert diese kein Ergenis zurück existiert zu der angegbenen
		 * Zeit in dem angegebenen Raum noch keine Durchführung, also kann sie angelgt werden.
		 * Gibt es jedoch ein Ergenisf aus der Abfrage ist der Raum zu der Zeit bereits belegt.
		 * @param zeitslot, raumId
		 * @return
		 * @throws RaumBelegtException 
		 */
		public boolean checkVerfuegbarkeit(Zeitslot zeitslot, int raumId) throws RaumBelegtException {
			Connection con = DBVerbindung.connection();
			
			//Verfügbarkeit standardmäßig auf true setzen.
			boolean isVerfuegbar = true;
			
			//Der 
			ArrayList<Boolean> belegungsArray = new ArrayList<Boolean>();
			
			for(int counter=8; counter < 18; counter++) {
				belegungsArray.add(false);
			}
			
			try {
			      Statement stmt = con.createStatement();
			      
			      //Join über 3 Tabellen, Zeitslot, Durchfuehrung und Raum
			      String sql = "Select * From Zeitslot "
			      				+ "Inner Join (Durchfuehrung Inner Join Raum on Durchfuehrung.RaumNr=Raum.RaumNr) on Zeitslot.ZeitNr=Durchfuehrung.ZeitNr "
			      				+ "Where Raum.RaumNr=" + raumId + " and Wochentag=\"" + zeitslot.getWochentag() + "\"";

			      ResultSet rs = stmt.executeQuery(sql);
			      
			      while (rs.next()) {
			    	int belegteAnfangszeit = rs.getInt("Zeitslot.Anfangszeit");
				    int belegteEndzeit = rs.getInt("Zeitslot.Endzeit");
				    int belegtDauer = belegteEndzeit - belegteAnfangszeit;
				    int arrayPos = belegteAnfangszeit - 8;
				    
				    for(int dauerCounter = 1; dauerCounter <= belegtDauer; dauerCounter++) {
				    	belegungsArray.add(arrayPos, true);
				    	arrayPos++;
				    }
				    
			    	//Wenn das ResultSet ein erebnis lifert, isVerfuegbar auf false setzen.

			      }

			      int neueAnfangszeit = zeitslot.getAnfangszeit();
			      int neueEndzeit = zeitslot.getEndzeit();
			      int neuedauer = neueEndzeit - neueAnfangszeit;
			      int arrayPos = neueAnfangszeit -8;
			      for(int dauerCounter = 1; dauerCounter <= neuedauer; dauerCounter++) {
				    	boolean isBelegt = belegungsArray.get(arrayPos);
				    	if(isBelegt) {
				    		isVerfuegbar = false;
				    	}
				    	arrayPos++;
				    }

			    }
			    catch (SQLException e2) {
			      e2.printStackTrace();
			      isVerfuegbar = false;
			      throw new RaumBelegtException();
			      
			    }

			 return isVerfuegbar;
		}
		
		
		/**
		 * Schaut ob ein Zeitslot mit Wochentag, Anfangs und Endzeit schon existiert,
		 * wenn ja wird dieser mit der entsprechenden Id gefüllt, wenn nein bleibt er leer.
		 * @param zeitslotNeu
		 * @return Gefundener Zeitslot oder null
		 */
		public Zeitslot insertIdIntoZeitslot(Zeitslot zeitslotNeu) {
			Connection con = DBVerbindung.connection();

		    try {
		      Statement stmt = con.createStatement();
		      String sql = "Select * From Zeitslot Where Wochentag=\"" + zeitslotNeu.getWochentag() + "\"" + " and Anfangszeit=" + zeitslotNeu.getAnfangszeit() + " and Endzeit=" + zeitslotNeu.getEndzeit();

		      ResultSet rs = stmt.executeQuery(sql);

		      if (rs.next()) {
		    	//Wenn es ein ergebnis gab die Id füllen.
		    	zeitslotNeu.setId(rs.getInt("ZeitNr"));
		      }
		    }
		    catch (SQLException e2) {
		      e2.printStackTrace();
		    }
			
			return zeitslotNeu;
		}
		
		
}
