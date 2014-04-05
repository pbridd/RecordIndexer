package client.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import client.gui.synchronization.BatchState;
import client.gui.synchronization.BatchStateListener;

public class TablePanel extends JTabbedPane  {
	//Global Variables
	
	DataTable dTable;
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
		
		this.addTab("Table Entry", new JScrollPane(dTable));
	}

	
	

}
