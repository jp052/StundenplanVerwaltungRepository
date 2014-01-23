package de.hdm.gruppe3.stundenplanverwaltung.server.db;

import java.sql.*;
import java.util.Vector;

import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.*;


//Import Impl Klasse Dozent
// Import bo Dozent

public class DozentMapper {

 /**
    * Die Klasse DozentMapper wird nur eindozentl instantiiert. Man spricht hierbei
    * von einem sogenannten <b>Singleton</b>.
    * <p>
    * Diese Variable ist durch den Bezeichner <code>static</code> nur eindozentl für
    * sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
    * einzige Instanz dieser Klasse.
    * 
    * @see dozentMapper()
    */
   private static DozentMapper dozentMapper = null;

   /**
    * Geschützter Konstruktor - verhindert die Möglichkeit, mit <code>new</code>
    * neue Instanzen dieser Klasse zu erzeugen.
    */
   protected DozentMapper() {
   }

   /**
    * Diese statische Methode kann aufgrufen werden durch
    * <code>DozentMapper.dozentMapper()</code>. Sie stellt die
    * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine einzige
    * Instanz von <code>DozentMapper</code> existiert.
    * <p>
    * 
    * <b>Fazit:</b> DozentMapper sollte nicht mittels <code>new</code>
    * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
    * 
    * @return DAS <code>DozentMapper</code>-Objekt.
    * @see dozentMapper
    */
   public static DozentMapper dozentMapper() {
     if (dozentMapper == null) {
       dozentMapper = new DozentMapper();
     }

     return dozentMapper;
   }
   public Dozent anlegen(Dozent d ){
    Connection con = DBVerbindung.connection();

       try {
         Statement stmt = con.createStatement();

//         /*
//          * Zunächst schauen wir nach, welches der momentan höchste
//          * Primärschlüsselwert ist.
//          */
//         ResultSet rs = stmt.executeQuery("SELECT MAX(dozent) AS dozentxdozent "
//             + "FROM dozent ");
 //
//         // Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
//         if (rs.next()) {
//           /*
//            * a erhält den bisher dozentxdozentozentlen, nun um 1 inkrementierten
//            * Primärschlüssel.
//            */
//           m.setID(rs.getInt("dozentxdozent") + 1);
 //
//           stmt = con.createStatement();

           // Jetzt erst erfolgt die tatsächliche Einfügeoperation
           stmt.executeUpdate("INSERT INTO dozent (PersonalNr, Vorname, Nachname) " + "VALUES ( "
            + "NULL,'" + d.getVorname() + "','" + d.getNachname() +"')");
         //}
       }
       catch (SQLException e2) {
         e2.printStackTrace();
       } 

       /*
        * Rückgabe, des evtl. korrigierten Accounts.
        * 
        * HINWEIS: Da in Java nur Referenzen auf Objekte und keine physischen
        * Objekte übergeben werden, wäre die Anpassung des Dozent-Objekts auch
        * ohne diese explizite Rückgabe au�erhalb dieser Methode sichtbar. Die
        * explizite Rückgabe von a ist eher ein Stilmittel, um zu signalisieren,
        * dass sich das Objekt evtl. im Laufe der Methode verändert hat.
        */
       return d;
   
  }
  
  public Dozent modifizieren(Dozent dozent){
      Connection con = DBVerbindung.connection();

      try {
        Statement stmt = con.createStatement();
//        UPDATE  `stundenplanverwaltung`.`dozent` SET  `Vorname` =  'test2' WHERE  `dozent`.`PersonalNr` =2;
        stmt.executeUpdate("UPDATE dozent " + "SET Nachname=\"" + dozent.getNachname() + "\", Vorname=\"" + dozent.getVorname() + "\" WHERE PersonalNr=" + dozent.getId());
        int i = 0;
      }
      catch (SQLException e2) {
        e2.printStackTrace();
      } 

      // Um Analogie zu insert(Dozent a) zu wahren, geben wir a zurück
      return dozent;
  }
  
  public Dozent loeschen(Dozent dozent){
      Connection con = DBVerbindung.connection();

      try {
        Statement stmt = con.createStatement();

        stmt.executeUpdate("DELETE FROM dozent " + "WHERE PersonalNr=" + dozent.getId());

      }
      catch (SQLException e2) {
        e2.printStackTrace();
      } 
      
      return dozent;
  }
  
  public Dozent findeName(Dozent dozent){
      // DB-Verbindung holen
      Connection con = DBVerbindung.connection();

      try {
        // Leeres SQL-Statement (JDBC) anlegen
        Statement stmt = con.createStatement();

        // Statement ausfüllen und als Query an die DB schicken
        ResultSet rs = stmt.executeQuery("SELECT PersonalNr, Name, Vorname"
            + "WHERE Name=" + dozent.getNachname() + " ORDER BY Name");

        /*
         * Da dozent Primärschlüssel ist, kann dozentx. nur ein Tupel zurückgegeben
         * werden. Prüfe, ob ein Ergebnis vorliegt.
         */
        if (rs.next()) {
          // Ergebnis-Tupel in Objekt umwandeln
          Dozent d = new Dozent();
          d.setId(rs.getInt("PersonalNr"));
          d.setNachname(rs.getString("Name"));
          d.setVorname(rs.getString("Vorname"));

          return d;
        }
      }
      catch (SQLException e2) {
        e2.printStackTrace();
        return null;
      } 

      return null;
  }
   

  public Dozent findeId(int dozentId){
      // DB-Verbindung holen
      Connection con = DBVerbindung.connection();
 
      try {
        // Leeres SQL-Statement (JDBC) anlegen
        Statement stmt = con.createStatement();

        // Statement ausfüllen und als Query an die DB schicken
        ResultSet rs = stmt.executeQuery("SELECT * FROM dozent "
            + "WHERE PersonalNr=" + dozentId);

        /*
         * Da dozent Primärschlüssel ist, kann dozentx. nur ein Tupel zurückgegeben
         * werden. Prüfe, ob ein Ergebnis vorliegt.
         */
        if (rs.next()) {
          LehrveranstaltungMapper lvMapper = LehrveranstaltungMapper.lvMapper();
          // Ergebnis-Tupel in Objekt umwandeln
          Dozent dozent = new Dozent();
//          Lehrveranstaltung lv = new Lehrveranstaltung();
//          lv = lvMapper.findeId(rs.getInt("LVNr"));
          
          dozent.setId(rs.getInt("PersonalNr"));
          dozent.setNachname(rs.getString("Nachname"));
          dozent.setVorname(rs.getString("Vorname"));
          //TODO: Dozent mit Lehveranstaltung Vector füllen lvMapper.findeByDozent

          return dozent;
        }
      }
      catch (SQLException e2) {
        e2.printStackTrace();
        return null;
      } 
      return null;
  }
   
   
    public Vector<Dozent> findeAlle(){
    Connection con = DBVerbindung.connection();

       // Ergebnisvektor vorbereiten
       Vector<Dozent> result = new Vector<Dozent>();

       try {
         Statement stmt = con.createStatement();

         ResultSet rs = stmt.executeQuery("SELECT * FROM dozent "
             + " ORDER BY PersonalNr");

         // F¸r jeden Eintrag im Suchergebnis wird nun ein Account-Objekt erstellt.
         while (rs.next()) {
           Dozent d = new Dozent();
           d.setId(rs.getInt("PersonalNr"));
           d.setVorname(rs.getString("Vorname"));
           d.setNachname(rs.getString("Nachname"));
           
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
  
  public Lehrveranstaltung findeVL(Dozent dozent) {
      /*
       * Wir bedienen uns hier einfach des CustomerMapper. Diesem geben wir
       * einfach den in dem Account-Objekt enthaltenen Fremdschl¸ssel f¸r den
       * Kontoinhaber. Der CustomerMapper l‰sst uns dann diese ID in ein Objekt
       * auf.
       *
       */      
       return LehrveranstaltungMapper.lvMapper().findeId(dozent.getId());
    }
    
  public Raum findeRaum(Dozent dozent) {
      /*
       * Wir bedienen uns hier einfach des CustomerMapper. Diesem geben wir
       * einfach den in dem Account-Objekt enthaltenen Fremdschl¸ssel f¸r den
       * Kontoinhaber. Der CustomerMapper l‰sst uns dann diese ID in ein Objekt
       * auf.
       */
      return RaumMapper.raumMapper().findeId(dozent.getId());
    }  
}
