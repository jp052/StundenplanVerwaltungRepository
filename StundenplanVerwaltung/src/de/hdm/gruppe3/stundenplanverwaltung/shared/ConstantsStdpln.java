package de.hdm.gruppe3.stundenplanverwaltung.shared;


/**
 * Enthält verschiedene Konstanten die immer wieder im Projekt verwendet werden an einer zentralen Stelle.
 * @author Yasemin Karakoc, Jan Plank
 *
 */
public class ConstantsStdpln {
	public static String NEU = "Neu";
	public static String AENDERN = "Ändern";
	public static String LOESCHEN = "Löschen";
	
	//Uhrzeiten die für den Zeitslot ausgewählt werden können
	public static int[]  UHRZEITEN =  {8,9,10,11,12,13,14,15,16,17,18};
	public static String[]  WOCHENTAGE =  {"Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag"};
	
	public static String REGEX_TEXTZAHL = "^[A-Za-z0-9- ]+$";
}
