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
 * @author Yasemin Karakoc, Jan Plank
 *
 */
public class SemesterverbandForm extends VerticalPanel{
	TextBox anzahlStudentenTextBox = new TextBox();
	TextBox semesterHalbjahrTextBox = new TextBox();
	TextBox jahrgangTextBox = new TextBox();
	Label idValueLabel = new Label();

	StundenplanVerwaltungServiceAsync stundenplanVerwaltung = GWT.create(StundenplanVerwaltungService.class);
	Semesterverband selectedSemesterverband = null;
	StundenplanVerwaltungTreeViewModel treeModel = null;

	/**
	 * Formular f�r die Darstellung des selektierten Kunden
	 */
	public SemesterverbandForm() {
		Grid customerGrid = new Grid(4, 2);
		this.add(customerGrid);

		Label anzahlStudierendeLabel = new Label("Anzahl Studierende");
		customerGrid.setWidget(0, 0, anzahlStudierendeLabel);
		customerGrid.setWidget(0, 1, anzahlStudentenTextBox);

		Label semesterHalbjahrLabel = new Label("Semesterhalbjahr");
		customerGrid.setWidget(1, 0, semesterHalbjahrLabel);
		customerGrid.setWidget(1, 1, semesterHalbjahrTextBox);
		
		Label jahrgangLabel = new Label("Jahrgang");
		customerGrid.setWidget(2, 0, jahrgangLabel);
		customerGrid.setWidget(2, 1, jahrgangTextBox);

		Label idLabel = new Label("ID");
		customerGrid.setWidget(3, 0, idLabel);
		customerGrid.setWidget(3, 1, idValueLabel);

		HorizontalPanel semesterverbandButtonsPanel = new HorizontalPanel();
		this.add(semesterverbandButtonsPanel);

		Button modifizierenButton = new Button(ConstantsStdpln.AENDERN);
		modifizierenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				modifizierenSelectedSemesterverband();
			}
		});
		semesterverbandButtonsPanel.add(modifizierenButton);
		
		
		Button loeschenButton = new Button(ConstantsStdpln.LOESCHEN);
		loeschenButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				//l�st das l�schen aus
				loeschenSelectedSemesterverband();
			}
		});
		
		semesterverbandButtonsPanel.add(loeschenButton);
		
		Button neuButton = new Button(ConstantsStdpln.NEU);
		neuButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				anlegenSelectedSemesterverband();
				
			}
		});
		
		semesterverbandButtonsPanel.add(neuButton);
	}

	public void setCatvm(StundenplanVerwaltungTreeViewModel treeModel) {
		this.treeModel = treeModel;
	}

	void setFields() {
		anzahlStudentenTextBox.setText(Integer.toString(selectedSemesterverband.getAnzahlStudenten()));
//		Integer in String umwandeln
		semesterHalbjahrTextBox.setText(Integer.toString(selectedSemesterverband.getSemester()));
		jahrgangTextBox.setText(Integer.toString(selectedSemesterverband.getJahrgang()));
		idValueLabel.setText(Integer.toString(selectedSemesterverband.getId()));
	}

	public void clearFields() {
		anzahlStudentenTextBox.setText("");
		semesterHalbjahrTextBox.setText("");
		idValueLabel.setText("");
	}

	public void setSelected(Semesterverband s) {
		if (s != null) {
			selectedSemesterverband = s;
			setFields();
		} else {
			clearFields();
		}
	}
	
	public void modifizierenSelectedSemesterverband() {
		if (this.selectedSemesterverband!=null){
			selectedSemesterverband.setAnzahlStudenten(Integer.valueOf(anzahlStudentenTextBox.getText()));
			//String in Integer umwandeln
			selectedSemesterverband.setSemester(Integer.valueOf(semesterHalbjahrTextBox.getText()));
			selectedSemesterverband.setJahrgang(Integer.valueOf(jahrgangTextBox.getText()));
			//Ruft Serverseitige Methode auf
			stundenplanVerwaltung.modifizierenSemesterverband(selectedSemesterverband, new AsyncCallback<Semesterverband>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(Semesterverband result) {
					System.out.println("Semesterverband ge�ndert");
//					treeModel.updateDozent(shownDozent);
					
				}
			});
		}
	}
	
	public void loeschenSelectedSemesterverband() {
		if(this.selectedSemesterverband != null) {
			//Ruft Serverseitige Methode auf
			stundenplanVerwaltung.loeschenSemesterverband(selectedSemesterverband, new AsyncCallback<Semesterverband>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(Semesterverband result) {
					if (result != null) {
						System.out.println("Semesterverband gel�scht");
						setSelected(null);
						//TODO: Liste oder Tree aktualisieren
					}
					
				}
				
			});
		}
	}
	
	public void anlegenSelectedSemesterverband() {
		int anzahlStudenten = Integer.valueOf(anzahlStudentenTextBox.getText());
		int semesterHalbjahr = Integer.valueOf(semesterHalbjahrTextBox.getText());
		int jahrgang = Integer.valueOf(jahrgangTextBox.getText());
		//Ruft Serverseitige Methode auf
		stundenplanVerwaltung.anlegenSemesterverband(semesterHalbjahr, anzahlStudenten, jahrgang,  new AsyncCallback<Semesterverband>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Semesterverband fehler");
				
			}

			@Override
			public void onSuccess(Semesterverband result) {
				if (result != null) {
					System.out.println("Semesterverband angelegt");
					//TODO: Liste oder Tree aktualisieren
				}
			}
			
		});
		
	}
	

	

}
