package client.gui;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import client.gui.synchronization.BatchState;
import client.gui.synchronization.BatchStateListener;

@SuppressWarnings("serial")
public class FormEntryPanel extends JPanel implements BatchStateListener {
	
	BatchState bchS;
	JList recordList;

	
	public FormEntryPanel(BatchState bchS){
		this.bchS = bchS;
		bchS.addListener(this);
	}
	
	private void createComponents(){
		this.setLayout(new BorderLayout());
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		recordList = new JList<String>(listModel);
		//add items to the list
		
		for(int i = 1; i <= bchS.getProject().getRecordsPerImage(); i++){
			listModel.addElement(i+"");
		}
		//add this to the layout
		this.add(recordList, BorderLayout.WEST);
		
		
		
		//Make forms JPanel
		//make the forms
		for(int i = 0; i < bchS.getFields().size(); i++){
			
		}
		
	}

	@Override
	public void batchActionPerformed(BatchActions ba) {
		if(ba == BatchActions.BATCHDOWNLOADED){
			createComponents();
		}
		
	}

	@Override
	public void batchActionPerformed(BatchActions ba, int row, int col) {
		// TODO Auto-generated method stub
		
	}
	

}
