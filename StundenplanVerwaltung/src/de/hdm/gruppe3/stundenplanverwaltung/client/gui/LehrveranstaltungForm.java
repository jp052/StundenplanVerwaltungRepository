package de.hdm.gruppe3.stundenplanverwaltung.client.gui;

import java.util.Vector;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe3.stundenplanverwaltung.client.ClientsideSettings;
import de.hdm.gruppe3.stundenplanverwaltung.shared.ConstantsStdpln;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungServiceAsync;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.*;



/**
 * @author Yasemin Karakoc, Jan Plank
 *
 */
public class LehrveranstaltungForm extends VerticalPanel{
	TextBox bezeichnungTextBox = new TextBox();
	TextBox semesterTextBox = new TextBox();
	TextBox umfangTextBox = new TextBox();
	ListBox dozentenListBox = new ListBox();
	Label idValueLabel = new Label();

	StundenplanVerwaltungServiceAsync stundenplanVerwaltung = GWT.create(StundenplanVerwaltungService.class);
	Lehrveranstaltung selectedLehrveranstaltung = null;
	StundenplanVerwaltungTreeViewModel treeModel = null;

	/**
	 * Formular f�r die Darstellung des selektierten Kunden
	 */
	public LehrveranstaltungForm() {
		Grid customerGrid = new Grid(5, 2);
		this.add(customerGrid);

		Label bezeichnungStudentenLabel = new Label("Bezeichnung");
		customerGrid.setWidget(0, 0, bezeichnungStudentenLabel);
		customerGrid.setWidget(0, 1, bezeichnungTextBox);

		Label semesterLabel = new Label("Semester");
		customerGrid.setWidget(1, 0, semesterLabel);
		customerGrid.setWidget(1, 1, semesterTextBox);
		
		Label jahrgangLabel = new Label("Umfang");
		customerGrid.setWidget(2, 0, jahrgangLabel);
		customerGrid.setWidget(2, 1, umfangTextBox);
		
		Label dozentLabel = new Label("Dozent");
		customerGrid.setWidget(3, 0, dozentLabel);
		customerGrid.setWidget(3, 1, dozentenListBox);
		
		//dozentenListBox f�llen
		setDozentenListBox();

		Label idLabel = new Label("ID");
		customerGrid.setWidget(4, 0, idLabel);
		customerGrid.setWidget(4, 1, idValueLabel);

		HorizontalPanel semesterverbandButtonsPanel = new HorizontalPanel();
		this.add(semesterverbandButtonsPanel);

		Button modifizierenButton = new Button(ConstantsStdpln.AENDERN);
		modifizierenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				modifizierenSelectedLehrveranstaltung();
			}
		});
		semesterverbandButtonsPanel.add(modifizierenButton);
		
		
		Button loeschenButton = new Button(ConstantsStdpln.LOESCHEN);
		loeschenButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				//l�st das l�schen aus
				loeschenSelectedLehrveranstaltung();
			}
		});
		
		semesterverbandButtonsPanel.add(loeschenButton);
		
		Button neuButton = new Button(ConstantsStdpln.NEU);
		neuButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				anlegenLehrveranstaltung();
				
			}
		});
		
		semesterverbandButtonsPanel.add(neuButton);
	}

	public void setCatvm(StundenplanVerwaltungTreeViewModel treeModel) {
		this.treeModel = treeModel;
	}


	
	/**
	 * F�llt die dozentenListBox mit allen in der Datenbank vorhandenen Dozenten.
	 * 
	 */
	private void setDozentenListBox() {
		stundenplanVerwaltung.getAllDozenten(new AsyncCallback<Vector<Dozent>>() {

			@Override
			public void onFailure(Throwable caught) {
//				ClientsideSettings.getLogger().severe("Bef�llen der DozentenListBox fehlgeschlagen");				
			}

			@Override
			public void onSuccess(Vector<Dozent> result) {
				int valueIndex = 1; //Bei 1 angangen, da 0 der "Bitte wählen" Text ist.
//				dozentenListBox.addItem("Bitte, value);
				for(Dozent d : result) {
					//Der zweite Parameter von addItem ist die gew�hlte Dozenten Id welche beim anlegen der Lehrveranstaltung 
					//ben�tigt wird.
					dozentenListBox.addItem(d.toString(), String.valueOf(d.getId()));
//					dozentenListBox.addItem(d.toString(), String.valueOf(valueIndex));
//					dozentenListBox.setValue(valueIndex, String.valueOf(d.getId()));
					valueIndex++;
					
				}
				
				dozentenListBox.setSelectedIndex(2);
				
			}
		});
	}



	public void setSelected(Lehrveranstaltung s) {
		if (s != null) {
			selectedLehrveranstaltung = s;
			setFields();
		} else {
			clearFields();
		}
	}
	
	public void setFields() {
		bezeichnungTextBox.setText(selectedLehrveranstaltung.getBezeichnung());
//		Integer in String umwandeln
		semesterTextBox.setText(Integer.toString(selectedLehrveranstaltung.getSemester()));
		umfangTextBox.setText(Integer.toString(selectedLehrveranstaltung.getUmfang()));
		
		//richten Eintrag in der ListBox w�hlen wenn eine Lehrveranstaltung existiert
//		dozentenListBox.setSelectedIndex(2);
		if(selectedLehrveranstaltung != null) {
//			dozentenListBox.setSelectedIndex(2);
		}
		
		idValueLabel.setText(Integer.toString(selectedLehrveranstaltung.getId()));
	}
	
	public void clearFields() {
		bezeichnungTextBox.setText("");
		semesterTextBox.setText("");
		idValueLabel.setText("");
	}
	
	public void modifizierenSelectedLehrveranstaltung() {
		if (this.selectedLehrveranstaltung!=null){
			selectedLehrveranstaltung.setBezeichnung(bezeichnungTextBox.getText());
			//String in Integer umwandeln
			selectedLehrveranstaltung.setSemester(Integer.valueOf(semesterTextBox.getText()));
			selectedLehrveranstaltung.setUmfang(Integer.valueOf(umfangTextBox.getText()));
			
			//Dozent Object das nur die Id enth�lt, mehr ist nicht vorhanden und auch nicht n�tig.
			Dozent d = new Dozent();
			int dozentId = Integer.valueOf(dozentenListBox.getValue(dozentenListBox.getSelectedIndex()));
			d.setId(dozentId);
			
			selectedLehrveranstaltung.setDozent(d);
			//Ruft Serverseitige Methode auf
			stundenplanVerwaltung.modifizierenLehrveranstaltung(selectedLehrveranstaltung, new AsyncCallback<Lehrveranstaltung>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(Lehrveranstaltung result) {
					System.out.println("Lehrveranstaltung ge�ndert");
//					treeModel.updateDozent(shownDozent);
					
				}
			});
		}
	}
	
	public void loeschenSelectedLehrveranstaltung() {
		if(this.selectedLehrveranstaltung != null) {
			//Ruft Serverseitige Methode auf
			stundenplanVerwaltung.loeschenLehrveranstaltung(selectedLehrveranstaltung, new AsyncCallback<Lehrveranstaltung>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(Lehrveranstaltung result) {
					if (result != null) {
						System.out.println("Lehrveranstaltung gel�scht");
						setSelected(null);
						//TODO: Liste oder Tree aktualisieren
					}					
				}				
			});
		}
	}
	
	public void anlegenLehrveranstaltung() {
		String bezeichnung = bezeichnungTextBox.getText();
		int semester = Integer.valueOf(semesterTextBox.getText());
		int umfang = Integer.valueOf(umfangTextBox.getText());
		int dozentId = Integer.valueOf(dozentenListBox.getValue(dozentenListBox.getSelectedIndex()));
		//Ruft Serverseitige Methode auf
		stundenplanVerwaltung.anlegenLehrveranstaltung(bezeichnung, semester, umfang, dozentId,  new AsyncCallback<Lehrveranstaltung>() {

			@Override
			public void onFailure(Throwable caught) {
//				ClientsideSettings.getLogger().severe("Lehrveranstaltung anlegen Fehler");	
				
			}

			@Override
			public void onSuccess(Lehrveranstaltung result) {
				if (result != null) {
					System.out.println("Lehrveranstaltung angelegt");
					//TODO: Liste oder Tree aktualisieren
				}
			}
			
		});
		
	}
	

	

}
