package de.hdm.gruppe3.stundenplanverwaltung.client.testGui;

import java.util.List;
import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.gruppe3.stundenplanverwaltung.client.gui.DozentForm;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungService;
import de.hdm.gruppe3.stundenplanverwaltung.shared.StundenplanVerwaltungServiceAsync;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.BusinessObject;
import de.hdm.gruppe3.stundenplanverwaltung.shared.bo.Dozent;

public class TestGUI {
	VerticalPanel mainPanel = new VerticalPanel();
	VerticalPanel vertpan = new VerticalPanel();
	HorizontalPanel hor1 = new HorizontalPanel();
	HorizontalPanel left = new HorizontalPanel(); 
	HorizontalPanel right = new HorizontalPanel(); 
	
	HorizontalPanel infoPanel = new HorizontalPanel();
	FlexTable testFlexTable = new FlexTable();

	TextBox VornameTextBox = new TextBox();
	TextBox NachnameTextBox = new TextBox();

	Button addStockButton = new Button("Add");
	Label lastUpdatedLabel = new Label();
	Button loeschen = new Button("Löschen");
	
	int rowPosition;
	
	Vector<? extends BusinessObject> bo;
	
	private final StundenplanVerwaltungServiceAsync stdplnVerwService = GWT.create(StundenplanVerwaltungService.class);
	
	public TestGUI() {
		loeschen.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void testFelder() {
	
			//COMMAND
			final Command dozentTabelleCMD = new Command(){ 
	            public void execute(){ 
		              stdplnVerwService.getAllDozenten(new AsyncCallback<Vector<Dozent>>() {

							@Override
							public void onFailure(Throwable caught) {
								testFlexTable.setText(1, 0, "fail");
								
							}

							@Override
							public void onSuccess(Vector<Dozent> dozentenFromDatenbank) {
								bo = dozentenFromDatenbank;
								right.clear();
								
								Button editieren = new Button("Ändern");
								testFlexTable.setText(1, 0, "Vorname");
				            	testFlexTable.setText(1, 1, "Nachname");
				            	//zählt die aktuelle zeile hoch, zeile fängt bei 1 an.
				            	rowPosition = 2;
								for(final Dozent d : dozentenFromDatenbank) {
									
								    Button loeschen = new Button("x");
								   							    
								    loeschen.addClickHandler(new ClickHandler() {
								      public void onClick(ClickEvent event) {
								        stdplnVerwService.loeschenDozent(d, new AsyncCallback<Dozent>() {
											@Override
											public void onFailure(
													Throwable caught) {
												// TODO Auto-generated method stub
												
											}
								        	

											@Override
											public void onSuccess(Dozent dozent) {
//												int removeIndex = dozentenFromDatenbank.indexOf(dozent);
//												testFlexTable.removeRow(removeIndex);
												
											}

	
								        });
								      }
								    });
									testFlexTable.setText(rowPosition, 0, d.getVorname());
									testFlexTable.setText(rowPosition, 1, d.getNachname());
									testFlexTable.setWidget(rowPosition, 2, loeschen);
									testFlexTable.setWidget(rowPosition, 3, editieren);
		              
		              				rowPosition++;
								}
				            	

				            	right.add(testFlexTable);
								
							}


			            	  
			              });
	               
	            } 
	        };
	        
	        Command dlCmd = new Command(){ 
	            public void execute(){ 
	            	right.clear();
	            	Label myLabel = new Label();
	            	myLabel.setText("noch nix");
	            	right.add(myLabel);
	               
	            } 
	        };
	        
	        //COMMAND
	      //COMMAND
	      		Command cmd = new Command(){ 
	                  public void execute(){ 
	                    
	                	  right.clear(); 
	                      right.add(new HTML("<h3>"+"TEST AFTER CLICK"+"</h3>"));
	                      
	                      
	                      testFlexTable.setText(1, 0, "Vorname");
	                      testFlexTable.setWidget(1, 1, VornameTextBox);
	                      testFlexTable.setText(2, 0, "Nachname");
	                      testFlexTable.setWidget(2, 1, NachnameTextBox);
	                      
	                      right.add(testFlexTable);
	                	  
	                  } 
	              };

	            
//	            stdplnVerwService.(nameField.getText()+"stpln", new AsyncCallback<String>() {
//	              
//	              			@Override
//	              			public void onFailure(Throwable caught) {
//	              				// TODO Auto-generated method stub
//	              				
//	              			}
//	              
//	              			@Override
//	              			public void onSuccess(String result) {
//	              				output.setText(result);
//	              				
//	              			}
//	                      	
//	                      });
			
			
			
//			mainPanel.add(vertpan);
	        
	        
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

			mainPanel.add(hor1);
	              
	        MenuBar dozMenu = new MenuBar(true); 
	        MenuBar raumMenu = new MenuBar(true); 
	        MenuBar lvMenu = new MenuBar(true); 
	        MenuBar zeitMenu = new MenuBar(true); 
	        MenuBar semMenu = new MenuBar(true); 
	        MenuBar vorlesMenu = new MenuBar(true); 
	        MenuBar repoMenu = new MenuBar(true); 
	        
	        dozMenu.addItem("Dozent anzeigen", dozentTabelleCMD); 
	        dozMenu.addItem("Dozent anlegen", dlCmd); 
	        
	        raumMenu.addItem("Raum anzeigen", cmd); 
	        raumMenu.addItem("Raum anlegen", dlCmd); 
	        
	        lvMenu.addItem("Lehrveranstaltung anzeigen", cmd); 
	        lvMenu.addItem("Lehrveranstaltung anlegen", dlCmd); 
	        
	        zeitMenu.addItem("Zeitslot anzeigen", cmd); 
	        zeitMenu.addItem("Zeitslot anlegen", dlCmd); 
	        
	        semMenu.addItem("Semesterverband anzeigen", cmd); 
	        semMenu.addItem("Semesterverband anlegen", dlCmd); 
	        
	        vorlesMenu.addItem("Vorlesung anzeigen", cmd); 
	        vorlesMenu.addItem("Vorlesung anlegen", dlCmd); 
	        
	        repoMenu.addItem("Report anzeigen", cmd); 
	        repoMenu.addItem("Report anlegen", dlCmd); 
	       
	        
	        MenuBar menu = new MenuBar(); 
	        menu.addItem("Dozent", dozMenu); 
	        menu.addItem("Raum", raumMenu); 
	        menu.addItem("Lehrveranstaltung", lvMenu);
	        menu.addItem("Zeitslot", zeitMenu); 
	        menu.addItem("Semesterverband", semMenu); 
	        menu.addItem("Vorlesung", vorlesMenu); 
	        menu.addItem("Report", repoMenu); 

	        
	        
	        vertpan.add(new HTML("<h1>TESTTESTTEST</h1>"));
	        
	        hor1.add(left);
	        left.add(new HTML("<h2>TreePanel</h2>"));        
	        hor1.add(right);
	        right.add(new HTML("<h2>DetailPanel</h2>"));
	        hor1.setSpacing(5);        
	        vertpan.add(menu);
	        
	        RootPanel.get("starter").add(mainPanel);
	}
}
