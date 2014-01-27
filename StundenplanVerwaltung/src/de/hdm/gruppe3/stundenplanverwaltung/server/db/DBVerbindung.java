package de.hdm.gruppe3.stundenplanverwaltung.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.appengine.api.rdbms.AppEngineDriver;

/**
 * *Enthält alle Elemente und nötigen Methoden für das Durchführungs Formular
 * 
 * @author Yasemin Karakoc, Jan Plank, Selim Karazehir, Julia Hammerer, Denis Fuerst, Daniel Krakow
 *In Anlehnung an Hr. Prof. Dr. Thies
 */
public class DBVerbindung {

	
	private static Connection con = null;
	
//Alte Versionen
//	private static String url = "jdbc:google:rdbms://itprojectgruppe3:group3/stundenplanverwaltung?user=root";
//	private static String url = "jdbc:google:rdbms://itprojectgruppe3:group3/stundenplanverwaltung?user=itgruppe3";
//	private static String url = "jdbc:google:rdbms://itprojectgruppe3:group3two/stundenplanverwaltung?user=root&";
//	private static String url = "jdbc:google:rdbms://itprojectgruppe3:group3/stundenplanverwaltung?user=root&password";
//	private static String url = "jdbc:google:rdbms://itprojectgruppe3:group3/stundenplanverwaltung?user=root&password=";
	
	//lokal
//	private static String url = "jdbc:mysql://127.0.0.1:3306/stundenplanverwaltung?user=root&password=";
	
//	cloud sql
	private static String url = "jdbc:google:rdbms://itprojectgruppe3:group3three/stundenplanverwaltung?user=root&";
	
	
	
	
	/**
	 * Baut die Verbindung zur Datenbank auf die Google Cloud SQL
	 * @return Die Verbindung
	 * @throws Exception 
	 */
	public static Connection connection() throws Exception  {
		//Verbindung erstllen wenn es noch keine gibt
//		if (con.isClosed() || con == null) {
			try {
				//Google connection
				DriverManager.registerDriver(new AppEngineDriver());
				
				con = DriverManager.getConnection(url);
			} 
			catch (SQLException e1) {
				con = null;
				e1.printStackTrace();
				throw new Exception("DBVerbindung Fehler!" + e1.toString());
			} 
			
			
//		}
		
		//Die Verbindung
		return con;
	}
	
	public static void closeAll(ResultSet rs, Statement stmt, Connection con) {
		
	}
	
//	
//	/**
//	 * Baut die Verbindung zur Datenbank Lokal auf
//	 * @return Die Verbindung
//	 * @throws Exception 
//	 */
//	public static Connection connection() throws Exception  {
//		//Verbindung erstllen wenn es noch keine gibt
//		if ( con == null ) {
//			try {
//
//				try {
//					Class.forName("com.mysql.jdbc.Driver");
//				} catch (ClassNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//				con = DriverManager.getConnection(url);
//			} 
//			catch (SQLException e1) {
//				con = connectToGoogle();
//			} 
//		}
//		
//		//Die Verbindung
//		return con;
//	}
//	
//	private  static Connection connectToGoogle() throws Exception {
//		//Verbindung erstllen wenn es noch keine gibt
//		if ( con == null ) {
//			try {
//				//Google connection
//				DriverManager.registerDriver(new AppEngineDriver());
//				
//				con = DriverManager.getConnection(googleUrl);
//			} 
//			catch (SQLException e1) {
//				con = null;
//				throw new Exception("Datenbank fehler!" + e1.toString());
//			}
//		}
//		
//		//Die Verbindung
//		return con;
//	}

}
