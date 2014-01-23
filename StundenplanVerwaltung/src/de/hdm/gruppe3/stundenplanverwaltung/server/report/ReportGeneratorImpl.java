package de.hdm.gruppe3.stundenplanverwaltung.server.report;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.gruppe3.stundenplanverwaltung.client.StundenplanVerwaltung;
import de.hdm.gruppe3.stundenplanverwaltung.server.StundenplanVerwaltungImpl;

public class ReportGeneratorImpl extends RemoteServiceServlet {

///**
//* Ein ReportGenerator benötigt Zugriff auf die BankAdministration, da diese die
//* essentiellen Methoden für die Koexistenz von Datenobjekten (vgl.
//* bo-Package) bietet.
//*/
//private StundenplanVerwaltung stdnplanVerwaltung = null;
//
///**
//* <p>
//* Ein <code>RemoteServiceServlet</code> wird unter GWT mittels
//* <code>GWT.create(Klassenname.class)</code> Client-seitig erzeugt. Hierzu
//* ist ein solcher No-Argument-Konstruktor anzulegen. Ein Aufruf eines anderen
//* Konstruktors ist durch die Client-seitige Instantiierung durch
//* <code>GWT.create(Klassenname.class)</code> nach derzeitigem Stand nicht
//* möglich.
//* </p>
//* <p>
//* Es bietet sich also an, eine separate Instanzenmethode zu erstellen, die
//* Client-seitig direkt nach <code>GWT.create(Klassenname.class)</code>
//* aufgerufen wird, um eine Initialisierung der Instanz vorzunehmen.
//* </p>
//*/
//public ReportGeneratorImpl() throws IllegalArgumentException {
//}
//
///**
//* Initialsierungsmethode. Siehe dazu Anmerkungen zum No-Argument-Konstruktor.
//* 
//* @see #ReportGeneratorImpl()
//*/
//public void init() throws IllegalArgumentException {
///*
// * Ein ReportGeneratorImpl-Objekt instantiiert für seinen Eigenbedarf eine
// * BankVerwaltungImpl-Instanz.
// */
//	StundenplanVerwaltungImpl a = new StundenplanVerwaltungImpl();
//a.init();
//this.stdnplanVerwaltung = a;
//}
//
///**
//* Auslesen der zugehörigen BankAdministration (interner Gebrauch).
//* 
//* @return das BankVerwaltungsobjekt
//*/
//protected StundenplanVerwaltung getStundenPlan() {
//return this.stdnplanVerwaltung;
//}
//
///**
//* Setzen des zugehörigen Bank-Objekts.
//*/
////public void setBank(Bank b) {
////this.administration.setBank(b);
////}
//
///**
//* Hinzufügen des Report-Impressums. Diese Methode ist aus den
//* <code>create...</code>-Methoden ausgegliedert, da jede dieser Methoden
//* diese Tätigkeiten redundant auszuführen hätte. Stattdessen rufen die
//* <code>create...</code>-Methoden diese Methode auf.
//* 
//* @param r der um das Impressum zu erweiternde Report.
//*/
//protected void addImprint(Report r) {
///*
// * Das Impressum soll wesentliche Informationen über die Bank enthalten.
// */
//Bank bank = this.administration.getBank();
//
///*
// * Das Imressum soll mehrzeilig sein.
// */
//CompositeParagraph imprint = new CompositeParagraph();
//
//imprint.addSubParagraph(new SimpleParagraph(bank.getName()));
//imprint.addSubParagraph(new SimpleParagraph(bank.getStreet()));
//imprint.addSubParagraph(new SimpleParagraph(bank.getZip() + " "
//    + bank.getCity()));
//
//// Das eigentliche Hinzufügen des Impressums zum Report.
//r.setImprint(imprint);
//
//}
//
///**
//* Erstellen von <code>AllAccountsOfCustomerReport</code>-Objekten.
//* 
//* @param c das Kundenobjekt bzgl. dessen der Report erstellt werden soll.
//* @return der fertige Report
//*/
//public AllAccountsOfCustomerReport createAllAccountsOfCustomerReport(
//  Customer c) throws IllegalArgumentException {
//
//if (this.getBankVerwaltung() == null)
//  return null;
//
///*
// * Zunächst legen wir uns einen leeren Report an.
// */
//AllAccountsOfCustomerReport result = new AllAccountsOfCustomerReport();
//
//// Jeder Report hat einen Titel (Bezeichnung / Überschrift).
//result.setTitle("Alle Konten des Kunden");
//
//// Imressum hinzufügen
//this.addImprint(result);
//
///*
// * Datum der Erstellung hinzufügen. new Date() erzeugt autom. einen
// * "Timestamp" des Zeitpunkts der Instantiierung des Date-Objekts.
// */
//result.setCreated(new Date());
//
///*
// * Ab hier erfolgt die Zusammenstellung der Kopfdaten (die Dinge, die oben
// * auf dem Report stehen) des Reports. Die Kopfdaten sind mehrzeilig, daher
// * die Verwendung von CompositeParagraph.
// */
//CompositeParagraph header = new CompositeParagraph();
//
//// Name und Vorname des Kunden aufnehmen
//header.addSubParagraph(new SimpleParagraph(c.getLastName() + ", "
//    + c.getFirstName()));
//
//// Kundennummer aufnehmen
//header.addSubParagraph(new SimpleParagraph("Kd.-Nr.: " + c.getId()));
//
//// Hinzufügen der zusammengestellten Kopfdaten zu dem Report
//result.setHeaderData(header);
//
///*
// * Ab hier erfolgt ein zeilenweises Hinzufügen von Konto-Informationen.
// */
//
///*
// * Zunächst legen wir eine Kopfzeile für die Konto-Tabelle an.
// */
//Row headline = new Row();
//
///*
// * Wir wollen Zeilen mit 2 Spalten in der Tabelle erzeugen. In die erste
// * Spalte schreiben wir die jeweilige Kontonummer und in die zweite den
// * aktuellen Kontostand. In der Kopfzeile legen wir also entsprechende
// * Überschriften ab.
// */
//headline.addColumn(new Column("Kto.-Nr."));
//headline.addColumn(new Column("Kto.-Stand"));
//
//// Hinzufügen der Kopfzeile
//result.addRow(headline);
//
///*
// * Nun werden sämtliche Konten des Kunden ausgelesen und deren Kto.-Nr. und
// * Kontostand sukzessive in die Tabelle eingetragen.
// */
//Vector<Account> accounts = this.administration.getAccountsOf(c);
//
//for (Account a : accounts) {
//  // Eine leere Zeile anlegen.
//  Row accountRow = new Row();
//
//  // Erste Spalte: Kontonummer hinzufügen
//  accountRow.addColumn(new Column(String.valueOf(a.getId())));
//
//  // Zweite Spalte: Kontostand hinzufügen
//  accountRow.addColumn(new Column(String.valueOf(this.administration
//      .getBalanceOf(a))));
//
//  // und schließlich die Zeile dem Report hinzufügen.
//  result.addRow(accountRow);
//}
//
///*
// * Zum Schluss müssen wir noch den fertigen Report zurückgeben.
// */
//return result;
//}
//
///**
//* Erstellen von <code>AllAccountsOfAllCustomersReport</code>-Objekten.
//* 
//* @return der fertige Report
//*/
//public AllAccountsOfAllCustomersReport createAllAccountsOfAllCustomersReport()
//  throws IllegalArgumentException {
//
//if (this.getBankVerwaltung() == null)
//  return null;
//
///*
// * Zunächst legen wir uns einen leeren Report an.
// */
//AllAccountsOfAllCustomersReport result = new AllAccountsOfAllCustomersReport();
//
//// Jeder Report hat einen Titel (Bezeichnung / überschrift).
//result.setTitle("Alle Konten aller Kunden");
//
//// Imressum hinzufügen
//this.addImprint(result);
//
///*
// * Datum der Erstellung hinzufügen. new Date() erzeugt autom. einen
// * "Timestamp" des Zeitpunkts der Instantiierung des Date-Objekts.
// */
//result.setCreated(new Date());
//
///*
// * Da AllAccountsOfAllCustomersReport-Objekte aus einer Sammlung von
// * AllAccountsOfCustomerReport-Objekten besteht, benötigen wir keine
// * Kopfdaten für diesen Report-Typ. Wir geben einfach keine Kopfdaten an...
// */
//
///*
// * Nun müssen sämtliche Kunden-Objekte ausgelesen werden. Anschließend wir
// * für jedes Kundenobjekt c ein Aufruf von
// * createAllAccountsOfCustomerReport(c) durchgeführt und somit jeweils ein
// * AllAccountsOfCustomerReport-Objekt erzeugt. Diese Objekte werden
// * sukzessive der result-Variable hinzugefügt. Sie ist vom Typ
// * AllAccountsOfAllCustomersReport, welches eine Subklasse von
// * CompositeReport ist.
// */
//Vector<Customer> allCustomers = this.administration.getAllCustomers();
//
//for (Customer c : allCustomers) {
//  /*
//   * Anlegen des jew. Teil-Reports und Hinzufügen zum Gesamt-Report.
//   */
//  result.addSubReport(this.createAllAccountsOfCustomerReport(c));
//}
//
///*
// * Zu guter Letzt müssen wir noch den fertigen Report zurückgeben.
// */
//return result;
//}
}
