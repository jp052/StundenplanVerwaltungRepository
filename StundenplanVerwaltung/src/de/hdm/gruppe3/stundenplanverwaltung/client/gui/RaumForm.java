package de.hdm.gruppe3.stundenplanverwaltung.client.gui;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe3.stundenplanverwaltung.shared.ConstantsStdpln;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungServiceAsync;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.*;



/**
 * Enthält alle Elemente und nötigen Methoden für das Raum Formular
 * @author Yasemin Karakoc, Jan Plank
 *
 */
public class RaumForm extends VerticalPanel{
	//Gui Elemente
	TextBox bezeichnungTextBox = new TextBox();
	TextBox kapazitaetTextBox = new TextBox();
	Label idValueLabel = new Label();
	HorizontalPanel raumButtonsPanel = new HorizontalPanel();
	Button modifizierenButton = new Button(ConstantsStdpln.AENDERN);
	Button loeschenButton = new Button(ConstantsStdpln.LOESCHEN);
	Button neuButton = new Button(ConstantsStdpln.NEU);


	StundenplanVerwaltungServiceAsync stundenplanVerwaltung = GWT.create(StundenplanVerwaltungService.class);
	Raum selectedRaum = null;

	/**
	 * Das Formular wird immer bei Konstruktoraufruf aufgerufen und zeigt alle GUI Elemente an.
	 */
	public RaumForm() {
		Grid customerGrid = new Grid(3, 2);
		this.add(customerGrid);

		Label bezeichnungLabel = new Label("Bezeichnung");
		customerGrid.setWidget(0, 0, bezeichnungLabel);
		customerGrid.setWidget(0, 1, bezeichnungTextBox);

		Label kapazitaetLabel = new Label("Kapazität");
		customerGrid.setWidget(1, 0, kapazitaetLabel);
		customerGrid.setWidget(1, 1, kapazitaetTextBox);

		
		
		if(selectedRaum != null){
			Label idLabel = new Label("ID");
			customerGrid.setWidget(2, 0, idLabel);
			customerGrid.setWidget(2, 1, idValueLabel);			
		}

		this.add(raumButtonsPanel);

		modifizierenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				modifizierenSelectedRaum();
			}
		});
		
		
		loeschenButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				//l�st das l�schen aus
				loeschenSelectedRaum();
			}
		});
		
		
		neuButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				anlegenSelectedRaum();
				
			}
		});
		
	showButtons();
		
	}

	/**
	 * Setzt alle Felder auf den gewählten Wert beim editieren
	 */
	void setFields() {
		bezeichnungTextBox.setText(selectedRaum.getBezeichnung());
//		Integer in String umwandeln
		kapazitaetTextBox.setText(Integer.toString(selectedRaum.getKapazitaet()));
		idValueLabel.setText(Integer.toString(selectedRaum.getId()));
	}
	
	/**
	 * Löscht den Inhalt alle Eingabe Felder
	 */
	public void clearFields() {
		bezeichnungTextBox.setText("");
		kapazitaetTextBox.setText("");
		idValueLabel.setText("");
	}
	
	/**
	* Setzt das gewählte Element zum editieren in die Instanz Variable und
	* zeigt Buttons und Felder an.
	* @param d Dozent
	*/
	public void setSelected(Raum r) {
		if (r != null) {
			selectedRaum = r;
			showButtons();
			setFields();
		} else {
			clearFields();
		}
	}
	
	/**
	 * Zeigt alle benötigten Buttons an.
	 */
	private void showButtons() {
		// Nur wenn Raum geändert wird, dann werden der modifizieren und
		// löschen Button angezeigt
		if (selectedRaum != null) {
			raumButtonsPanel.remove(neuButton);
			raumButtonsPanel.add(loeschenButton);
			raumButtonsPanel.add(modifizierenButton);
		}else {
			raumButtonsPanel.add(neuButton);

		}
		
	}
	
	/**
	 * Ändert das ausgewählte Business Objekt im Editiermodus 
	 */
	public void modifizierenSelectedRaum() {
		if (this.selectedRaum!=null){
			selectedRaum.setBezeichnung(bezeichnungTextBox.getText());
			//String in Integer umwandeln
			selectedRaum.setKapazitaet(Integer.valueOf(kapazitaetTextBox.getText()));
			//Ruft Serverseitige Methode auf
			stundenplanVerwaltung.modifizierenRaum(selectedRaum, new AsyncCallback<Raum>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(Raum result) {
					System.out.println("Raum ge�ndert");
//					treeModel.updateDozent(shownDozent);
					
				}
			});
		}
	}
	
	/**
	 * Löscht das ausgewählte Business Objekt im Editiermodus 
	 */
	public void loeschenSelectedRaum() {
		if(this.selectedRaum != null) {
			//Ruft Serverseitige Methode auf
			stundenplanVerwaltung.loeschenRaum(selectedRaum, new AsyncCallback<Raum>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(Raum result) {
					if (result != null) {
						System.out.println("Raum gel�scht");
						setSelected(null);
						//TODO: Liste oder Tree aktualisieren
					}
					
				}
				
			});
		}
	}
	
	/**
	 * Legt das  das eingegebene Business Objekt an
	 */
	public void anlegenSelectedRaum() {
		String bezeichnung = bezeichnungTextBox.getText();
		int kapazitaet = Integer.valueOf(kapazitaetTextBox.getText());
		//Ruft Serverseitige Methode auf
		stundenplanVerwaltung.anlegenRaum(bezeichnung, kapazitaet,  new AsyncCallback<Raum>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Raum fehler");
				
			}

			@Override
			public void onSuccess(Raum result) {
				if (result != null) {
					System.out.println("Raum angelegt");
					//TODO: Liste oder Tree aktualisieren
				}
			}
			
		});
		
	}

}
