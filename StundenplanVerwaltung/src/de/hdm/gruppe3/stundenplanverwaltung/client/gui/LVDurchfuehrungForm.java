package de.hdm.gruppe3.stundenplanverwaltung.client.gui;

import java.util.ArrayList;
import java.util.Vector;

import sun.awt.CausedFocusEvent.Cause;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe3.stundenplanverwaltung.client.ClientsideSettings;
import de.hdm.gruppe3.stundenplanverwaltung.shared.ConstantsStdpln;
import de.hdm.gruppe3.stundenplanverwaltung.shared.RaumBelegtException;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungServiceAsync;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.*;



/**
* *Enthält alle Elemente und nötigen Methoden für das Durchführungs Formular
* @author Yasemin Karakoc, Jan Plank
*
*/
public class LVDurchfuehrungForm extends VerticalPanel{
//Gui Elemente
ListBox lbLehrveranstaltung = new ListBox();
ListBox lbRaum = new ListBox();
ListBox lbSemesterverband = new ListBox();
ListBox lbAnfangszeit = new ListBox();
ListBox lbEndzeit = new ListBox();
ListBox lbWochentag = new ListBox();

Label lbIdValue = new Label();
HorizontalPanel hPanelLVDruchfuehrungButtons = new HorizontalPanel();
Button modifizierenButton = new Button(ConstantsStdpln.AENDERN);
Button loeschenButton = new Button(ConstantsStdpln.LOESCHEN);
Button neuButton = new Button(ConstantsStdpln.NEU);

StundenplanVerwaltungServiceAsync stundenplanVerwaltung = GWT.create(StundenplanVerwaltungService.class);
LVDurchfuehrung selectedLVDurchfuehrung = null;

/**
* *Das Formular wird immer bei Konstruktoraufruf aufgerufen und zeigt alle GUI Elemente an.
*/
public LVDurchfuehrungForm() {
Grid customerGrid = new Grid(5, 2);
this.add(customerGrid);

Label lLehrveranstaltung = new Label("Lehrveranstaltung");
customerGrid.setWidget(0, 0, lLehrveranstaltung);
customerGrid.setWidget(0, 1, lbLehrveranstaltung);

Label lRaum = new Label("Raum");
customerGrid.setWidget(1, 0, lRaum);
customerGrid.setWidget(1, 1, lbRaum);

Label lSemesterverband = new Label("Semesterverband");
customerGrid.setWidget(2, 0, lSemesterverband);
customerGrid.setWidget(2, 1, lbSemesterverband);


HorizontalPanel hPanelZeitslot = new HorizontalPanel();
hPanelZeitslot.add(new Label("Anfangszeit:"));
hPanelZeitslot.add(lbAnfangszeit);
hPanelZeitslot.add(new Label("Endzeit:"));
hPanelZeitslot.add(lbEndzeit);
hPanelZeitslot.add(new Label("Wochentag:"));
hPanelZeitslot.add(lbWochentag);

Label lZeitslot = new Label("Zeitslot");
customerGrid.setWidget(3, 0, lZeitslot);
customerGrid.setWidget(3, 1, hPanelZeitslot);
//this.add(hPanelZeitslot);

//List Boxen  füllen
setLehrveranstaltungListBox();
setRaumListBox();
setSemesterverbandListBox();
setZeitListBox();
setWochentagListBox();

Label idLabel = new Label("ID");
customerGrid.setWidget(4, 0, idLabel);
customerGrid.setWidget(4, 1, lbIdValue);

this.add(hPanelLVDruchfuehrungButtons);


modifizierenButton.addClickHandler(new ClickHandler() {
public void onClick(ClickEvent event) {
//l�st das �ndern aus
modifizierenSelectedLVDurchfuehrung();
}
});	


loeschenButton.addClickHandler(new ClickHandler() {

public void onClick(ClickEvent event) {
//l�st das l�schen aus
loeschenSelectedLVDurchfuehrung();
}
});

neuButton.addClickHandler(new ClickHandler() {

public void onClick(ClickEvent event) {
anlegenLVDurchfuehrung();

}
});

//	 Buttons anzeigen
showButtons();
}



/**
* Fügt alle Elemente in die entsprechende List Box
*/
private void setLehrveranstaltungListBox() {
stundenplanVerwaltung.getAllLV(new AsyncCallback<Vector<Lehrveranstaltung>>() {

@Override
public void onFailure(Throwable caught) {
System.out.println("Fehler!");
}

@Override
public void onSuccess(Vector<Lehrveranstaltung> result) {
//Listbox leeren falls schon alte Werte drin sind
lbLehrveranstaltung.clear();

//Erster Eintrag soll leer sein bzw. Bitte wählen enthalten
lbLehrveranstaltung.addItem("--Bitte wählen--", "0");

for(Lehrveranstaltung lv : result) {	
//Der zweite Parameter von addItem ist die gew�hlte Lehrveranstatlungs Id welche beim anlegen der Durchführung 
//benötigt wird.
lbLehrveranstaltung.addItem(lv.getBezeichnung(), String.valueOf(lv.getId()));
}	

//Listbox setzen wenn im editier Modus
if(selectedLVDurchfuehrung != null) {
int lehrveranstaltungId = selectedLVDurchfuehrung.getLehrveranstaltung().getId();
selectListenAuswahl(lbLehrveranstaltung, lehrveranstaltungId);
}


}
});
}

/**
* Fügt alle Elemente in die entsprechende List Box
*/
private void setRaumListBox(){
stundenplanVerwaltung.getAllRaeume(new AsyncCallback<Vector<Raum>>() {

@Override
public void onFailure(Throwable caught) {
System.out.println("Fehler!");
}

@Override
public void onSuccess(Vector<Raum> result) {
//Listbox leeren falls schon alte Werte drin sind
lbRaum.clear();

//Erster Eintrag soll leer sein bzw. Bitte wählen enthalten
lbRaum.addItem("--Bitte wählen--", "0");

for(Raum r : result) {
//Der zweite Parameter von addItem ist die gew�hlte Raum Id welche beim anlegen der Durchführung 
//v wird.
lbRaum.addItem(r.getBezeichnung(), String.valueOf(r.getId()));
}	

//Listbox setzen wenn im editier Modus
if(selectedLVDurchfuehrung != null) {
int raumId = selectedLVDurchfuehrung.getRaum().getId();
selectListenAuswahl(lbRaum, raumId);
}
}
});
}

/**
* Fügt alle Elemente in die entsprechende List Box
*/
private void setSemesterverbandListBox(){
stundenplanVerwaltung.getAllSemesterverband(new AsyncCallback<Vector<Semesterverband>>() {

@Override
public void onFailure(Throwable caught) {
System.out.println("Fehler!");

}

@Override
public void onSuccess(Vector<Semesterverband> result) {
//Listbox leeren falls schon alte Werte drin sind
lbSemesterverband.clear();


//Erster Eintrag soll leer sein bzw. Bitte wählen enthalten
lbSemesterverband.addItem("--Bitte wählen--", "0");

for(Semesterverband sv : result) {
//Der erste Prameter von addItem enthält den anzeige String, er wird in der toString methode zu einem lesbaren String umgewandelt.
//Der zweite Parameter von addItem ist die gewählte Semesterverband Id welche beim anlegen der Durchführung 
//benögtigt wird.
lbSemesterverband.addItem(sv.toString(), String.valueOf(sv.getId()));
}

//Listbox setzen wenn im editier Modus
if(selectedLVDurchfuehrung != null) {
int semesterverbandId = selectedLVDurchfuehrung.getSemesterverband().getId();
selectListenAuswahl(lbSemesterverband, semesterverbandId);
}

}
});
}

/**
* Füllt anfangszeit und endzeit ListBox
*/
private void setZeitListBox() {
//Listbox leeren falls schon alte Werte drin sind
lbAnfangszeit.clear();
lbEndzeit.clear();

//Erster Eintrag soll leer sein bzw. Bitte wählen enthalten
lbAnfangszeit.addItem("--Bitte wählen--", "0");
lbEndzeit.addItem("--Bitte wählen--", "0");

for(int zeit : ConstantsStdpln.UHRZEITEN) {
//Vorher int in String umwandeln mit String.valueOf
lbAnfangszeit.addItem(String.valueOf(zeit));
lbEndzeit.addItem(String.valueOf(zeit));
}

if(selectedLVDurchfuehrung != null) {
int anfangZeit = selectedLVDurchfuehrung.getZeitslot().getAnfangszeit();
int endZeit = selectedLVDurchfuehrung.getZeitslot().getEndzeit();
selectListenAuswahl(lbAnfangszeit, anfangZeit);
selectListenAuswahl(lbEndzeit, endZeit);

}
}

/**
* Füllte die Tage in die ListBox
*/
private void setWochentagListBox() {
//Erster Eintrag soll leer sein bzw. Bitte wählen enthalten
lbWochentag.addItem("--Bitte wählen--", "0");
//Läuft durch den ganzen Wochentag Array der in ConstantsStdpln definiert ist 
//und füllt diese in die Wochentag ListBox.
for(String tag : ConstantsStdpln.WOCHENTAGE) {
lbWochentag.addItem(tag);
}

if(selectedLVDurchfuehrung != null) {
String wochentag = selectedLVDurchfuehrung.getZeitslot().getWochentag();
selectListenAuswahl(lbWochentag, wochentag);
}
}



/**
* Setzt beim editieren den richtigen Eintrag.
* In GWT können die Felder nur nach einem Index gesetzt werden. Der Index unterscheidet sich von der Value der HTML SelectBox welches die
* Id des gewählten Business Objektes ist. Diese Objekt Id wird beim modifizieren benötigt damit sie mit dem Datensatz in der
* Datenbank in verbindung gebracht werden kann. Id ist gleich Primärschlüssel.
* @param listBox: Die zu setzende Listbox
* @param zuSetzendeValue: die Value die ausgewählt werden soll
*/
private void selectListenAuswahl(ListBox listBox, int zuSetzendeValue) {
//Nur ausführen wenn selected Objekt gefüllt ist und die Objekt Id vorhanden ist, sonst gibt es Fehler.
if (selectedLVDurchfuehrung != null && zuSetzendeValue > 0) {	
for(int pos = 0; pos < listBox.getItemCount(); pos++){
// Erzeugt einen int aus der value in der listbox
//Holt die dozentId der gewählten Lehrveranstaltung
//Wenn die beiden Werte gleich sind, dann soll die Position in der Select

int currentValue = Integer.valueOf(listBox.getValue(pos)); 

if(currentValue == zuSetzendeValue) {
//Wenn die Vaulue gleich die zuSetzendeValue ist muss der listbox eintrag an der aktuellen Position gewählt werden.
listBox.setSelectedIndex(pos);
}
}
}
}

/**
* Das gleiche wie {@link #selectListenAuswahl(ListBox, int)}, nur für die Zeit und Wochentage.
* Da dort Strings und keine Integer als Value verwendet werden ist eine extra Methode nötig.
* @param listBox
* @param zuSetzendeValue
*/
private void selectListenAuswahl(ListBox listBox, String zuSetzendeValue) {
//Nur ausführen wenn selected Objekt gefüllt ist und die Objekt Id vorhanden ist, sonst gibt es Fehler.
if (selectedLVDurchfuehrung != null && zuSetzendeValue != null) {	
for(int listBoxPos = 0; listBoxPos < listBox.getItemCount(); listBoxPos++){
// Erzeugt einen int aus der value in der listbox
//Holt die dozentId der gewählten Lehrveranstaltung
//Wenn die beiden Werte gleich sind, dann soll die Position in der Select

String currentValue = listBox.getValue(listBoxPos); 

if(currentValue.equals(zuSetzendeValue)) {
//Wenn die Vaulue gleich zuSetzendeValuet Id ist muss der listbox eintrag an der aktuellen Position gewählt werden.
listBox.setSelectedIndex(listBoxPos);
}
}
}
}




/**
* Setzt das gewählte Element zum editieren in die Instanz Variable und
* zeigt Buttons und Felder an.
* @param lvd: Die LVDurchfuehrung
*/
public void setSelected(LVDurchfuehrung lvd) {
if (lvd != null) {
selectedLVDurchfuehrung = lvd;
setFields();
showButtons();
} else {
clearFields();
}
}

/**
* Zeigt alle benötigten Buttons an.
*/
public void showButtons() {
// Nur wenn Business Objekt geändert wird, dann werden der modifizieren und
// löschen Button angezeigt
if (selectedLVDurchfuehrung != null) {
hPanelLVDruchfuehrungButtons.add(modifizierenButton);
hPanelLVDruchfuehrungButtons.add(loeschenButton);
hPanelLVDruchfuehrungButtons.remove(neuButton);
} else {
hPanelLVDruchfuehrungButtons.add(neuButton);

}
}

/**
* Setzt alle Felder auf den gewählten Wert beim editieren
*/
public void setFields() {
//richten Eintrag in den Eingabefeldern wählen wenn eine Lehrveranstaltung existiert
if(selectedLVDurchfuehrung != null) {
lbIdValue.setText(String.valueOf(selectedLVDurchfuehrung.getId()));

//Richtiger wert in den Listboxen wählen
setLehrveranstaltungListBox();
setRaumListBox();
setSemesterverbandListBox();
setZeitListBox();
setWochentagListBox();

}
}

/**
* Löscht den Inhalt alle Eingabe Felder
*/
public void clearFields() {
//	 lbLehrveranstaltung.setItemSelected(0, true);
//	 lbRaum.setItemSelected(0, true);
//	 lbSemesterverband.setItemSelected(0, true);
//	 lbAnfangszeit.setItemSelected(0, true);
//	 lbEndzeit.setItemSelected(0, true);
//	 lbWochentag.setItemSelected(0, true);
//	 idValueLabel.setText("");
}

/**
* Ändert das ausgewählte Business Objekt im Editiermodus 
*/
public void modifizierenSelectedLVDurchfuehrung() {
//Die ausgew�hlten Id des gew�hlten Elementes ausw�hlen und am ende and die entsprechende Async Methode schicken.

//Schauen ob der Benutzer alles richtig eingegeben hat, wenn false zurück kommt wird mit return abgebrochen und die Fehlermeldung angezeit.
if(!validiereBenutzerEingabe()) {
return;
}

//Werte f�r Zeitslot Objekt aus Listbox auslesen
int anfangsZeit = Integer.valueOf(lbAnfangszeit.getValue(lbAnfangszeit.getSelectedIndex()));
int endZeit = Integer.valueOf(lbEndzeit.getValue(lbEndzeit.getSelectedIndex()));
String wochentag = lbWochentag.getItemText(lbWochentag.getSelectedIndex());

//Zeitslot Objekt mit aus der ListBox gelesenen Werten f�llen, die id fehlt und wird in den mapper emittelt.
Zeitslot zeitslot = new Zeitslot();
zeitslot.setAnfangszeit(anfangsZeit);
zeitslot.setEndzeit(endZeit);
zeitslot.setWochentag(wochentag);

//Wert der Durchführungs Id auslsen
int lvdId = Integer.valueOf(lbIdValue.getText());
//Wert f�r Semesterverband auslsen
int svId = Integer.valueOf(lbSemesterverband.getValue(lbSemesterverband.getSelectedIndex()));

//Wert f�r Raum auslsen
int raumId = Integer.valueOf(lbRaum.getValue(lbRaum.getSelectedIndex()));

//Wert f�r Lehrveranstaltung auslsen
int lvId = Integer.valueOf(lbLehrveranstaltung.getValue(lbLehrveranstaltung.getSelectedIndex()));


//Ruft Serverseitige Methode auf
stundenplanVerwaltung.modifizierenDurchfuehrung(lvdId, svId, raumId, lvId, zeitslot, new AsyncCallback<LVDurchfuehrung>() {

@Override
public void onFailure(Throwable caught) {
//Unbekannte Fehlermeldung
String message = caught.getMessage();

//Fehlermedlung wenn der Raum belegt ist
if(caught instanceof RaumBelegtException) {
message = ((RaumBelegtException)caught).getFehlerMessage();
}
//Das Popup Panel mit der Nachricht
Window.alert(message);	
}

@Override
public void onSuccess(LVDurchfuehrung result) {
Window.alert("Änderung erfolgreich!");	
}	
});
}

/**
* Löscht das ausgewählte Business Objekt im Editiermodus 
*/
public void loeschenSelectedLVDurchfuehrung() {
if(this.selectedLVDurchfuehrung != null) {
//Ruft Serverseitige Methode auf
stundenplanVerwaltung.loeschenDurchfuehrung(selectedLVDurchfuehrung, new AsyncCallback<LVDurchfuehrung>() {

@Override
public void onFailure(Throwable caught) {
//Unbekannte Fehlermeldung
String message = caught.getMessage();
//Das Popup Panel mit der Nachricht
Window.alert(message);
}

@Override
public void onSuccess(LVDurchfuehrung result) {
Window.alert("Löschen erfolgreich!");
setSelected(null);	
}	
});
}
}

/**
* Legt das  das ausgewählte Business Objekt an
*/
public void anlegenLVDurchfuehrung() {
//Die ausgew�hlten Id des gew�hlten Elementes ausw�hlen und am ende and die entsprechende Async Methode schicken.

//Schauen ob der Benutzer alles richtig eingegeben hat, wenn false zurück kommt wird mit return abgebrochen und die Fehlermeldung angezeit.
if(!validiereBenutzerEingabe()) {
return;
}


//Werte f�r Zeitslot Objekt aus Listbox auslesen
int anfangsZeit = Integer.valueOf(lbAnfangszeit.getValue(lbAnfangszeit.getSelectedIndex()));
int endZeit = Integer.valueOf(lbEndzeit.getValue(lbEndzeit.getSelectedIndex()));
String wochentag = lbWochentag.getItemText(lbWochentag.getSelectedIndex());

//Zeitslot Objekt mit aus der ListBox gelesenen Werten f�llen, die id fehlt und wird in den mapper emittelt.
Zeitslot zeitslot = new Zeitslot();
zeitslot.setAnfangszeit(anfangsZeit);
zeitslot.setEndzeit(endZeit);
zeitslot.setWochentag(wochentag);

//Wert f�r Semesterverband auslsen
int svId = Integer.valueOf(lbSemesterverband.getValue(lbSemesterverband.getSelectedIndex()));

//Wert f�r Raum auslsen
int raumId = Integer.valueOf(lbRaum.getValue(lbRaum.getSelectedIndex()));

//Wert f�r Lehrveranstaltung auslsen
int lvId = Integer.valueOf(lbLehrveranstaltung.getValue(lbLehrveranstaltung.getSelectedIndex()));


//Ruft Serverseitige Methode auf
stundenplanVerwaltung.anlegenDurchfuehrung(svId, raumId, lvId, zeitslot, new AsyncCallback<LVDurchfuehrung>() {

@Override
public void onFailure(Throwable caught) {
//Unbekannte Fehlermeldung
String message = caught.getMessage();

//Fehlermedlung wenn der Raum belegt ist
if(caught instanceof RaumBelegtException) {
message = ((RaumBelegtException)caught).getFehlerMessage();
}
//Das Popup Panel mit der Nachricht
Window.alert(message);

}

@Override
public void onSuccess(LVDurchfuehrung result) {
Window.alert("Anlegen erfolgreich!");
clearFields();
}

});

}


/**
* Zeigt eine Fehlermeldung wenn der Benutzer etwas falsches eingegeben hat.
* @return true wenn alles ok ist
*/
private boolean validiereBenutzerEingabe() {
boolean isValid = true;
//Die indexs der ListBox auslesen um zu schauen ob überall etwas gewählt wurde.
int indexLV = lbLehrveranstaltung.getSelectedIndex();
int indexRaum = lbRaum.getSelectedIndex();
int indexSV = lbSemesterverband.getSelectedIndex();
int indexAnfangsZeit = lbAnfangszeit.getSelectedIndex();
int indexEndZeit = lbEndzeit.getSelectedIndex();
int indexWochentag = lbWochentag.getSelectedIndex();

if(indexLV < 1 || indexRaum < 1 || indexSV < 1|| indexAnfangsZeit < 1 || indexEndZeit < 1 || indexWochentag < 1 ) {
Window.alert("Alle Eingeabefelder müssen augefüllt werden!");
isValid = false;
} else if(indexAnfangsZeit > indexEndZeit) {
Window.alert("Die Anfangszeit darf nicht vor der Enzeit liegen!");
isValid = false;
}

return isValid;	

}




}