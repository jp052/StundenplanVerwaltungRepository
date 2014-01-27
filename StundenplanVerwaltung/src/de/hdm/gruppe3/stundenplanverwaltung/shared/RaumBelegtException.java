package de.hdm.gruppe3.stundenplanverwaltung.shared;

import java.io.Serializable;

/**
 * Wird als Fehler vom Server an den Client übergeben und in der Bereich onFailiur() nach
 * dem RPC-Aufruf ausgelesen und enthält dann denn Fehlertext.
 * @author Yasemin Karakoc, Jan Plank
 *
 */
public class RaumBelegtException extends Exception implements Serializable{

	private static final long serialVersionUID = 1L;
	
	  private String fehlerMessage = "Dieser Raum ist zum angegebenen Zeitraum belegt! Schauen Sie in den Raum Report um einen freien Raum zu finden.";

	  public RaumBelegtException() {
	  }

	  public RaumBelegtException(String symbol) {
	    this.fehlerMessage = symbol;
	  }

	  public String getFehlerMessage() {
	    return this.fehlerMessage;
	  }
}
