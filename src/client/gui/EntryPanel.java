package client.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;

import client.gui.synchronization.BatchState;



public class EntryPanel extends JTabbedPane implements TableColumnModelListener, 
	ListSelectionListener, MouseListener, Serializable  {
	/**
	 * Automatically Generated by Eclipse
	 */
	private static final long serialVersionUID = -581792692319539317L;


	//Global Variables
	DataTable dTable;
	FormEntryPanel fEntry;
	BatchState bchS;
	int rightClickedRow;
	int rightClickedCol;




	/**
	 * Default constructor
	 */
	public EntryPanel(BatchState bchS){
		this.bchS = bchS;
		this.createComponents();
	}

	/**
	 * Creates the tabs for the panel
	 */
	private void createComponents(){
		//set up table
		dTable = new DataTable(bchS);
		fEntry = new FormEntryPanel(bchS, dTable);


		this.addTab("Table Entry", new JScrollPane(dTable));
		this.addTab("Form Entry", fEntry);
		dTable.getColumnModel().addColumnModelListener(this);
		dTable.getSelectionModel().addListSelectionListener(this);
	}



	public DataTable getTable(){
		return dTable;
	}

	public FormEntryPanel getForms(){
		return fEntry;
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
		if(dTable.getColumnModel().getSelectedColumns().length == 0)
			return;
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

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	/*	DataTable source = (DataTable)arg0.getSource();
		int row = source.rowAtPoint(arg0.getPoint());
		int col = source.columnAtPoint(arg0.getPoint());
		System.out.println("The table thinks you clicked row " + row + " and column " + col);
		if(arg0.getButton() == MouseEvent.BUTTON3){
			if(col > 0){
				//check to see if the entry at that point is in the dictionary
				if(!source.getIsKnownWord(source.getBchS().getValues()[row][col-1].getDataValue(),
						col)){
					rightClickedRow = row;
					rightClickedCol = col;
					popup.show(arg0.getComponent(), arg0.getX(), arg0.getY());
					
				}
			}
		}*/
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}