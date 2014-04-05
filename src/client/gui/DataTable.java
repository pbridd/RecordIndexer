package client.gui;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import client.gui.synchronization.BatchState;
import client.gui.synchronization.BatchStateListener;
import client.gui.synchronization.BatchStateListener.BatchActions;

public class DataTable extends JTable implements BatchStateListener {

	//Global variables
	BatchState bchS;
	
	/**
	 * Automatically generated by Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	public DataTable(BatchState bchS){
		this.bchS = bchS;
		bchS.addListener(this);
		
	}



	@Override
	public void batchActionPerformed(BatchActions ba) {
		if(ba == BatchActions.BATCHDOWNLOADED){
			DataTableModel currModel = new DataTableModel(bchS);
			this.setModel(currModel);
		}
		
	}

}

class DataTableModel extends AbstractTableModel{
	
	BatchState bchS;
	int columns;
	int rows;

	/**
	 * Automatically generated by eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	public DataTableModel(BatchState bchS){
		this.bchS = bchS;		
	}

	@Override
	public int getColumnCount() {
		return bchS.getFields().size() + 1;
	}

	@Override
	public int getRowCount() {
		return bchS.getProject().getRecordsPerImage();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {	
		if(arg1 == 0){
			return arg0+1;
		}
		return bchS.getValues()[arg0][arg1-1].getDataValue();
	}
	
	@Override
	public boolean isCellEditable(int row, int column){
		if(column == 0)
			return false;
		else
			return true;
	}
	
	@Override
	public void setValueAt(Object value, int row, int col){
		bchS.setValueAt((String) value, row, col-1);
	}
	
	@Override
	public String getColumnName(int col){
		if(col == 0){
			return "Record Number";
		}
		return bchS.getField(col-1).getFieldName();
	}
	
}
