package client.gui;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import client.gui.synchronization.BatchState;


@SuppressWarnings("serial")
public class TablePanel extends JTabbedPane  {
	//Global Variables
	DataTable dTable;
	FormEntryPanel fEntry;
	BatchState bchS;
	

	
	
	/**
	 * Default constructor
	 */
	public TablePanel(BatchState bchS){
		this.bchS = bchS;
		createComponents();
	}
	
	/**
	 * Creates the tabs for the panel
	 */
	private void createComponents(){
		//set up table
		dTable = new DataTable(bchS);
		fEntry = new FormEntryPanel(bchS);
		
		
		this.addTab("Table Entry", new JScrollPane(dTable));
		this.addTab("Form Entry", fEntry);
	}
	
	

	
	

}
