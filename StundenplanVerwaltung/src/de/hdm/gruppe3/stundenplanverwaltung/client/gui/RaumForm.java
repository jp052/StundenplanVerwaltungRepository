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

import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungServiceAsync;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.*;



/**
 * @author Yasemin Karakoc, Jan Plank
 *
 */
public class RaumForm extends VerticalPanel{
	TextBox bezeichnungTextBox = new TextBox();
	TextBox kapazitaetTextBox = new TextBox();
	Label idValueLabel = new Label();

	StundenplanVerwaltungServiceAsync stundenplanVerwaltung = GWT.create(StundenplanVerwaltungService.class);
	Raum selectedRaum = null;
	StundenplanVerwaltungTreeViewModel treeModel = null;

	/**
	 * Formular für die Darstellung des selektierten Kunden
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

		Label idLabel = new Label("ID");
		customerGrid.setWidget(2, 0, idLabel);
		customerGrid.setWidget(2, 1, idValueLabel);

		HorizontalPanel raumButtonsPanel = new HorizontalPanel();
		this.add(raumButtonsPanel);

		Button modifizierenButton = new Button("Ändern");
		modifizierenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				modifizierenSelectedRaum();
			}
		});
		raumButtonsPanel.add(modifizierenButton);
		
		
		Button loeschenButton = new Button("Löschen");
		loeschenButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				//löst das löschen aus
				loeschenSelectedRaum();
			}
		});
		
		raumButtonsPanel.add(loeschenButton);
		
		Button neuButton = new Button("Neu");
		neuButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				anlegenSelectedRaum();
				
			}
		});
		
		raumButtonsPanel.add(neuButton);
	}

	public void setCatvm(StundenplanVerwaltungTreeViewModel treeModel) {
		this.treeModel = treeModel;
	}

	void setFields() {
		bezeichnungTextBox.setText(selectedRaum.getBezeichnung());
//		Integer in String umwandeln
		kapazitaetTextBox.setText(Integer.toString(selectedRaum.getKapazitaet()));
		idValueLabel.setText(Integer.toString(selectedRaum.getId()));
	}

	public void clearFields() {
		bezeichnungTextBox.setText("");
		kapazitaetTextBox.setText("");
		idValueLabel.setText("");
	}

	public void setSelected(Raum r) {
		if (r != null) {
			selectedRaum = r;
			setFields();
		} else {
			clearFields();
		}
	}
	
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
					System.out.println("Raum geändert");
//					treeModel.updateDozent(shownDozent);
					
				}
			});
		}
	}
	
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
						System.out.println("Raum gelöscht");
						setSelected(null);
						//TODO: Liste oder Tree aktualisieren
					}
					
				}
				
			});
		}
	}
	
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
