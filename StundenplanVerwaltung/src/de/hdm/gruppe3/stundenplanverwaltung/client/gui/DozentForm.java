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

import de.hdm.gruppe3.stundenplanverwaltung.shared.*;

import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Dozent;

/**
 * @author Yasemin Karakoc, Jan Plank
 * 
 */
public class DozentForm extends VerticalPanel {
	
	TextBox vornameTextBox = new TextBox();
	TextBox nachnameTextBox = new TextBox();
	Label idValueLabel = new Label();
	HorizontalPanel dozentButtonsPanel = new HorizontalPanel();
	Button modifizierenButton = new Button(ConstantsStdpln.AENDERN);
	Button neuButton = new Button(ConstantsStdpln.NEU);
	Button loeschenButton = new Button(ConstantsStdpln.LOESCHEN);



	StundenplanVerwaltungServiceAsync stundenplanVerwaltung = GWT
			.create(StundenplanVerwaltungService.class);
	Dozent shownDozent = null;
	StundenplanVerwaltungTreeViewModel treeModel = null;

	/**
	 * Formular f�r die Darstellung des selektierten Kunden
	 */
	public DozentForm() {
		Grid customerGrid = new Grid(3, 2);
		this.add(customerGrid);

		Label vornameLabel = new Label("Vorname");
		customerGrid.setWidget(0, 0, vornameLabel);
		customerGrid.setWidget(0, 1, vornameTextBox);

		Label lastNameLabel = new Label("Nachname");
		customerGrid.setWidget(1, 0, lastNameLabel);
		customerGrid.setWidget(1, 1, nachnameTextBox);

		// Nur wenn Dozent geändert wird, dann wird das ID Feld angezeigt
		if (shownDozent != null) {
			Label idLabel = new Label("ID");
			customerGrid.setWidget(2, 0, idLabel);
			customerGrid.setWidget(2, 1, idValueLabel);
		}

		
		this.add(dozentButtonsPanel);

		
		modifizierenButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				modifizierenSelectedDozent();
			}
		});

		loeschenButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				stundenplanVerwaltung.loeschenDozent(shownDozent,
						new LoeschenDozentCallback(shownDozent));
			}
		});

		
		neuButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				String vorname = vornameTextBox.getText();
				String nachname = nachnameTextBox.getText();
				stundenplanVerwaltung.anlegenDozent(vorname, nachname,
						new AnlegenDozentCallback());
			}
		});
		
		showButtons();
		
	}

	public void setCatvm(StundenplanVerwaltungTreeViewModel treeModel) {
		this.treeModel = treeModel;
	}

	void setFields() {
		vornameTextBox.setText(shownDozent.getVorname());
		nachnameTextBox.setText(shownDozent.getNachname());
		idValueLabel.setText(Integer.toString(shownDozent.getId()));
	}

	public void clearFields() {
		vornameTextBox.setText("");
		nachnameTextBox.setText("");
		idValueLabel.setText("");
	}

	public void setSelected(Dozent d) {		
		if (d != null) {
			shownDozent = d;
			showButtons();
			setFields();
		} else {
			clearFields();
		}
	}
	
	public void showButtons(){
		// Nur wenn Dozent geändert wird, dann werden der modifizieren und
				// löschen Button angezeigt
				if (shownDozent != null) {
					dozentButtonsPanel.add(modifizierenButton);
					dozentButtonsPanel.add(loeschenButton);
					dozentButtonsPanel.remove(neuButton);
				}else {
					dozentButtonsPanel.add(neuButton);

				}
				
	}

	public void modifizierenSelectedDozent() {
		if (this.shownDozent != null) {
			shownDozent.setVorname(vornameTextBox.getText());
			shownDozent.setNachname(nachnameTextBox.getText());
			stundenplanVerwaltung.modifizierenDozent(shownDozent,
					new AsyncCallback<Dozent>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onSuccess(Dozent result) {
							System.out.println("Dozent ge�ndert");
							// treeModel.updateDozent(shownDozent);

						}
					});
		}
	}

	class LoeschenDozentCallback implements AsyncCallback<Dozent> {

		Dozent dozent = null;

		// Konstruktor um callback mit Parameter aufzurufen
		LoeschenDozentCallback(Dozent d) {
			dozent = d;
		}

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Dozent result) {
			if (dozent != null) {
				System.out.println("Dozent gel�scht");
				setSelected(null);
				// treeModel.loeschenDozent(dozent);
			}
		}

	}

	class AnlegenDozentCallback implements AsyncCallback<Dozent> {

		@Override
		public void onFailure(Throwable caught) {
			System.out.println("Dozent fehler");
		}

		public void onSuccess(Dozent dozent) {
			if (dozent != null) {
				UserInformation.popup("Dozent angelegt");
				//Felder Leeren, damit gleich der nächste angelegt werden kann
				clearFields();
			}
		}
	}

}

