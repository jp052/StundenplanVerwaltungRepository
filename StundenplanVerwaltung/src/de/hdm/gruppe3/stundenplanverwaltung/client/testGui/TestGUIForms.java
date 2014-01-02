package de.hdm.gruppe3.stundenplanverwaltung.client.testGui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe3.stundenplanverwaltung.client.gui.DozentForm;
import de.hdm.gruppe3.stundenplanverwaltung.client.gui.RaumForm;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungServiceAsync;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.*;

public class TestGUIForms {
	VerticalPanel mainPanel = new VerticalPanel();
	private final StundenplanVerwaltungServiceAsync stdplnVerwService = GWT.create(StundenplanVerwaltungService.class);
	
	public void formsAnzeigen() {
		//liest Dozent mit Id 1 aus
		stdplnVerwService.getDozentByNummer(1, new AsyncCallback<Dozent>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Dozent result) {
				DozentForm df = new DozentForm();
				df.setSelected(result);
				mainPanel.add(df);
				
			}
		});
		
		stdplnVerwService.getRaumbyNummer(1, new AsyncCallback<Raum>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Raum result) {
				RaumForm rf = new RaumForm();
				rf.setSelected(result);
				mainPanel.add(rf);
			}
		});
		
		 RootPanel.get("starter").add(mainPanel);
	}

}
