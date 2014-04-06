package client.gui;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;

import client.gui.synchronization.BatchState;


@SuppressWarnings("serial")
public class EntryPanel extends JTabbedPane implements TableColumnModelListener, ListSelectionListener  {
	//Global Variables
	DataTable dTable;
	FormEntryPanel fEntry;
	BatchState bchS;
	

	
	
	/**
	 * Default constructor
	 */
	public EntryPanel(BatchState bchS){
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
		dTable.getColumnModel().addColumnModelListener(this);
		dTable.getSelectionModel().addListSelectionListener(this);
	}
	
	
	
	//implemented methods from TableColumnModelListener
	@Override
	public void columnAdded(TableColumnModelEvent arg0) {
		// purposely left blank
		
	}

	@Override
	public void columnMarginChanged(ChangeEvent arg0) {
		// purposely left blank
		
	}

	@Override
	public void columnMoved(TableColumnModelEvent arg0) {
		// purposely left blank
		
	}

	@Override
	public void columnRemoved(TableColumnModelEvent arg0) {
		//purposely left blank
		
	}

	@Override
	public void columnSelectionChanged(ListSelectionEvent arg0) {
		int column = dTable.getColumnModel().getSelectedColumns()[0];
		if(column <= 0){
			column = 0;
		}
		else
			column = column-1;
		
		bchS.setSelectedCellCol(column);		
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		int selRow = dTable.getSelectionModel().getMinSelectionIndex();
		if(selRow < 0){
			selRow = 0;
		}
		bchS.setSelectedCellRow(selRow);
		
	}
	
	
	
	
	

	
	

}
