package de.hdm.gruppe3.stundenplanverwaltung.client.gui;

import com.google.gwt.view.client.TreeViewModel;

import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Dozent;

public class StundenplanVerwaltungTreeViewModel implements TreeViewModel {

	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isLeaf(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	public void updateDozent(Dozent shownDozent) {
		// TODO Auto-generated method stub
		
	}

	public void loeschenDozent(Dozent dozent) {
		// TODO Auto-generated method stub
		
	}
	
	public void anlegenDozent(Dozent dozent) {
		// TODO Auto-generated method stub
		
	}
	
	

}
